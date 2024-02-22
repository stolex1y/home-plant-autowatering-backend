package ru.filimonov.hpa.data.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Range
import org.springframework.test.context.jdbc.Sql
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.data.repository.DeviceRepository
import ru.filimonov.hpa.data.repository.SoilMoistureReadingRepository
import ru.filimonov.hpa.data.toSensorReading
import ru.filimonov.hpa.domain.model.SensorReading
import ru.filimonov.hpa.domain.service.SoilMoistureReadingService
import java.util.*

@DataJpaTest
@Sql(
    value = ["/db/test_data.sql"],
    statements = ["select gen_test_data(100, 1, 1)"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@Sql(
    value = ["/db/clear_data.sql"],
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS
)
class SoilMoistureValueServiceImplTest @Autowired constructor(
    private val soilMoistureReadingRepository: SoilMoistureReadingRepository,
    private val deviceRepository: DeviceRepository
) {

    private val underTest: SoilMoistureReadingService = SoilMoistureReadingServiceImpl(soilMoistureReadingRepository)
    private val deviceId: UUID =
        deviceRepository.findAll().firstOrNull()?.uuid ?: throw AssertionError("No devices found!")

    @Test
    fun `get last sensor reading test`() {
        val expectedSensorReading =
            soilMoistureReadingRepository.findTopByDeviceIdOrderByTimestampDesc(deviceId)?.toSensorReading()
        val actualSensorReading = underTest.getLastValue(deviceId)
        assertEquals(expectedSensorReading, actualSensorReading)
    }

    @Test
    fun `add sensor reading test`() {
        val expectedSensorReading = SensorReading(Float.MAX_VALUE, Calendar.getInstance())
        assertNull(
            soilMoistureReadingRepository.findAllByDeviceIdOrderByTimestampDesc(deviceId)
                .find { it.reading == expectedSensorReading.reading })

        underTest.addReading(deviceId, expectedSensorReading.reading)
        val actualSensorReading =
            soilMoistureReadingRepository.findTopByDeviceIdOrderByTimestampDesc(deviceId)?.toSensorReading()
                ?: throw AssertionError("Last reading mustn't be null after adding.")

        assertEquals(expectedSensorReading.reading, actualSensorReading.reading)
        assertEquals(
            expectedSensorReading.timestamp.timeInMillis.toDouble(),
            actualSensorReading.timestamp.timeInMillis.toDouble(),
            100.0
        )
    }

    @Test
    fun `delete sensor reading for period test`() {
        val readings = soilMoistureReadingRepository.findAllByDeviceIdOrderByTimestampDesc(deviceId)
        assertTrue(readings.size > 2, "There are must be more than 2 readings")

        val leftBound = readings.last().timestamp
        val rightBound = readings[readings.size / 2].timestamp
        val expectedDeleted = readings.filter { it.timestamp in leftBound..rightBound }

        val deletedReadingCount: Long =
            underTest.deleteReadingsForPeriod(deviceId, Range.closed(leftBound.toCalendar(), rightBound.toCalendar()))
        val afterDeletion = soilMoistureReadingRepository.findAllByDeviceIdOrderByTimestampDesc(deviceId)

        assertEquals(expectedDeleted.size.toLong(), deletedReadingCount)
        assertEquals(0, afterDeletion.filter { it.timestamp in leftBound..rightBound }.size)
    }
}

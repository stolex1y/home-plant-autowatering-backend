package ru.filimonov.hpa.data.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Range
import org.springframework.test.context.jdbc.Sql
import ru.filimonov.hpa.core.CALENDAR_MAX
import ru.filimonov.hpa.core.CALENDAR_MIN
import ru.filimonov.hpa.core.toCalendar
import ru.filimonov.hpa.data.repository.DeviceRepository
import ru.filimonov.hpa.data.repository.SoilMoistureReadingRepository
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.SensorReading
import ru.filimonov.hpa.domain.service.DeviceService
import ru.filimonov.hpa.domain.service.sensors.SoilMoistureReadingService
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

    private val deviceService: DeviceService = mock()
    private lateinit var deviceId: UUID
    private lateinit var userId: String
    private lateinit var underTest: SoilMoistureReadingService

    init {
        val device = deviceRepository.findAll().firstOrNull() ?: throw AssertionError("No devices found!")
        deviceId = device.uuid
        userId = device.userId
        whenever(deviceService.isUserDevice(any(), any())).thenReturn(false)
        whenever(deviceService.isUserDevice(userId, deviceId)).thenReturn(true)
        underTest = SoilMoistureReadingServiceImpl(deviceService, soilMoistureReadingRepository)
    }

    @Test
    fun `get last sensor reading test`() {
        val expectedSensorReading =
            soilMoistureReadingRepository.findTopByDeviceIdOrderByTimestampDesc(deviceId)?.toDomain()
        val actualSensorReading = underTest.getLastReading(userId, deviceId)
        assertEquals(expectedSensorReading, actualSensorReading)
    }

    @Test
    fun `add sensor reading test`() {
        val expectedSensorReading = SensorReading(Float.MAX_VALUE, Calendar.getInstance())
        assertNull(
            soilMoistureReadingRepository.findAllByDeviceIdOrderByTimestampDesc(deviceId)
                .find { it.reading == expectedSensorReading.reading })

        underTest.addReading(userId, deviceId, expectedSensorReading.reading)
        val actualSensorReading =
            soilMoistureReadingRepository.findTopByDeviceIdOrderByTimestampDesc(deviceId)?.toDomain()
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
            underTest.deleteReadingsForPeriod(
                userId,
                deviceId,
                Range.closed(leftBound.toCalendar(), rightBound.toCalendar())
            )
        val afterDeletion = soilMoistureReadingRepository.findAllByDeviceIdOrderByTimestampDesc(deviceId)

        assertEquals(expectedDeleted.size.toLong(), deletedReadingCount)
        assertEquals(0, afterDeletion.filter { it.timestamp in leftBound..rightBound }.size)
    }

    @Test
    fun `get readings for period by day test`() {
        TODO("тест получения усредненных данных по дням")
    }

    @Test
    fun `get readings for period by hour test`() {
        TODO("тест получения усредненных данных по часам")
    }

    @Test
    fun `try to get last reading from not user's device`() {
        assertThrows(IllegalAccessException::class.java) {
            underTest.getLastReading(userId, UUID.randomUUID())
        }
    }

    @Test
    fun `try to get readings for period from not user's device`() {
        PeriodUnit.entries.forEach { periodUnit ->
            assertThrows(IllegalAccessException::class.java) {
                underTest.getReadingsForPeriodByTimeUnit(
                    userId,
                    UUID.randomUUID(),
                    Range.closed(CALENDAR_MIN, CALENDAR_MAX),
                    periodUnit
                )
            }
        }
    }

    @Test
    fun `try to add reading for not user's device`() {
        assertThrows(IllegalAccessException::class.java) {
            underTest.addReading(userId, UUID.randomUUID(), 0.0f)
        }
    }

    @Test
    fun `try to delete readings for not user's device`() {
        assertThrows(IllegalAccessException::class.java) {
            underTest.deleteReadingsForPeriod(userId, UUID.randomUUID(), Range.closed(CALENDAR_MIN, CALENDAR_MAX))
        }
    }
}

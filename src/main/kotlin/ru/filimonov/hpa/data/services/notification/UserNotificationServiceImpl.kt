package ru.filimonov.hpa.data.services.notification

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Range
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.filimonov.hpa.common.toCalendar
import ru.filimonov.hpa.domain.model.PeriodUnit
import ru.filimonov.hpa.domain.model.Plant
import ru.filimonov.hpa.domain.services.DeviceService
import ru.filimonov.hpa.domain.services.PlantService
import ru.filimonov.hpa.domain.services.notification.UserNotificationService
import ru.filimonov.hpa.domain.services.readings.AirHumidityReadingsService
import ru.filimonov.hpa.domain.services.readings.AirTempReadingsService
import ru.filimonov.hpa.domain.services.readings.BatteryChargeReadingsService
import ru.filimonov.hpa.domain.services.readings.LightLevelReadingsService
import java.time.ZonedDateTime
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

@Service
class UserNotificationServiceImpl(
    private val firebaseMessaging: FirebaseMessaging,
    private val batteryChargeReadingsService: BatteryChargeReadingsService,
    private val airHumidityReadingsService: AirHumidityReadingsService,
    private val airTempReadingsService: AirTempReadingsService,
    private val lightLevelReadingsService: LightLevelReadingsService,
    private val plantService: PlantService,
    private val deviceService: DeviceService,
) : UserNotificationService {
    companion object {
        private val VERIFICATION_DURATION = 1.days.toJavaDuration()
        private const val BATTERY_CHARGE_MIN = 10
        private const val NOTIFICATION_MSG_PLANT_NEEDS_ATTENTION_BODY = "check_these_devices"
        private const val NOTIFICATION_MSG_PLANT_NEEDS_ATTENTION_TITLE = "plant_needs_attention"
    }

    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun updateNotification(userId: String, notificationToken: String) {
        val devicesNeededAttention = deviceService.getAllDevicesByUserId(userId = userId)
            .filter { it.plantId != null }.associateBy { device ->
                plantService.getPlantById(userId = userId, plantId = device.plantId!!)
            }
            .filter { (plant, device) ->
                isBatteryChargeReadingsOutOfRange(userId = userId, deviceId = device.uuid) ||
                        isAirHumidityReadingsOutOfRange(userId = userId, deviceId = device.uuid, plant = plant) ||
                        isAirTempReadingsOutOfRange(userId = userId, deviceId = device.uuid, plant = plant)
            }.map { entry -> entry.value.name }
        if (devicesNeededAttention.isNotEmpty()) {
            logger.debug("${devicesNeededAttention.size} devices needed attention")
            val androidConfig = AndroidConfig.builder()
                .setNotification(
                    AndroidNotification.builder()
                        .setTitleLocalizationKey(NOTIFICATION_MSG_PLANT_NEEDS_ATTENTION_TITLE)
                        .setBodyLocalizationKey(NOTIFICATION_MSG_PLANT_NEEDS_ATTENTION_BODY)
                        .addBodyLocalizationArg(devicesNeededAttention.joinToString(", "))
                        .setPriority(AndroidNotification.Priority.HIGH)
                        .build()
                )
                .build()
            val message = Message.builder()
                .setToken(notificationToken)
                .setAndroidConfig(androidConfig)
                .build()
            val result = firebaseMessaging.send(message)
            logger.info("notification sending result: $result")
        }
    }

    private fun isBatteryChargeReadingsOutOfRange(userId: String, deviceId: UUID): Boolean {
        return batteryChargeReadingsService.getForPeriodByTimeUnit(
            userId = userId,
            deviceId = deviceId,
            period = getVerificationInterval(),
            periodUnit = PeriodUnit.HOUR
        )
            .count { it.reading < BATTERY_CHARGE_MIN } > 0
    }

    private fun isAirHumidityReadingsOutOfRange(userId: String, deviceId: UUID, plant: Plant): Boolean {
        return airHumidityReadingsService.getForPeriodByTimeUnit(
            userId = userId,
            deviceId = deviceId,
            period = getVerificationInterval(),
            periodUnit = PeriodUnit.HOUR
        )
            .count { it.reading !in plant.airHumidityRange } > 0
    }

    private fun isAirTempReadingsOutOfRange(userId: String, deviceId: UUID, plant: Plant): Boolean {
        return airTempReadingsService.getForPeriodByTimeUnit(
            userId = userId,
            deviceId = deviceId,
            period = getVerificationInterval(),
            periodUnit = PeriodUnit.HOUR
        )
            .count { it.reading !in plant.airTempRange } > 0
    }

    private fun getVerificationInterval(): Range<Calendar> {
        val now = ZonedDateTime.now()
        val fromTimestamp = now.minus(VERIFICATION_DURATION).toCalendar()
        val toTimestamp = now.toCalendar()
        return Range.closed(fromTimestamp, toTimestamp)
    }
}

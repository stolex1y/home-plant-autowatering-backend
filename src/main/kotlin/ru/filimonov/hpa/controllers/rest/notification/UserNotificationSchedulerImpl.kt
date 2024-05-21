package ru.filimonov.hpa.controllers.rest.notification

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Component
import ru.filimonov.hpa.common.nextTimePointFromNow
import ru.filimonov.hpa.domain.model.UserNotificationDetails
import ru.filimonov.hpa.domain.services.notification.UserNotificationDetailsGettingService
import ru.filimonov.hpa.domain.services.notification.UserNotificationScheduler
import ru.filimonov.hpa.domain.services.notification.UserNotificationService
import java.util.concurrent.ScheduledFuture
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

@Component
class UserNotificationSchedulerImpl(
    private val notificationService: UserNotificationService,
    private val notificationDetailsGettingService: UserNotificationDetailsGettingService,
) : UserNotificationScheduler {
    private val scheduler: ThreadPoolTaskScheduler = ThreadPoolTaskScheduler()
    private val scheduledTasks: MutableMap<String, ScheduledFuture<*>> = mutableMapOf()
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    init {
        log.info("starting user notification scheduler")
        scheduler.initialize()
        notificationDetailsGettingService.getAllNotificationDetails().forEach(::scheduleNotificationSending)
    }

    override fun scheduleNotificationSending(userId: String) {
        log.debug("schedule notification sending")
        scheduledTasks[userId]?.cancel(true)
        notificationDetailsGettingService.getAllUserNotificationDetails(userId).forEach {
            scheduleNotificationSending(notificationDetails = it)
        }
    }

    private fun scheduleNotificationSending(notificationDetails: UserNotificationDetails) {
        val task = Runnable {
            notificationService.updateNotification(
                userId = notificationDetails.userId,
                notificationToken = notificationDetails.token
            )
        }
        scheduledTasks[notificationDetails.userId] = scheduler.scheduleWithFixedDelay(
            task,
            notificationDetails.notificationTime.nextTimePointFromNow().toInstant(),
            1.days.toJavaDuration()
        )
    }
}

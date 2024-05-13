package org.example.filestreamerlib.api.entrypoint.scheduler

import org.example.filestreamerlib.api.entity.TaskStatus
import org.example.filestreamerlib.api.service.streamer.FileStreamer
import org.example.filestreamerlib.api.service.task.FileSendingService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SendingTaskScheduledService(
    private val fileSendingService: FileSendingService,
    private val fileStreamer: FileStreamer
) {

    @Scheduled(fixedDelay = 1000)
    fun findTaskForRunning() {
        val newTask = fileSendingService.findNewTask() ?: return
        try {
            fileSendingService.updateTaskStatus(newTask, TaskStatus.PROCESSING)
            fileStreamer.readFile(newTask.fileName, newTask.requiredColumns.split(","))
            fileSendingService.updateTaskStatus(newTask, TaskStatus.SUCCESS)
        } catch (e: Exception) {
            fileSendingService.updateTaskStatus(newTask, TaskStatus.FAILED)
        }
    }
}
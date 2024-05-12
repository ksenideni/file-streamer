package org.example.filestreamerclient.service

import org.example.filestreamerclient.dto.TaskStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SendingTaskScheduledService(
    private val fileSendingTaskService: FileSendingTaskService,
    private val fileReaderService: FileReaderService
) {

    @Scheduled(fixedDelay = 1000)
    fun findTaskForRunning() {
        val newTask = fileSendingTaskService.findNewTask() ?: return
        try {
            fileSendingTaskService.updateTaskStatus(newTask, TaskStatus.PROCESSING)
            fileReaderService.readFile(newTask.fileName, newTask.requiredColumns.split(","))
            fileSendingTaskService.updateTaskStatus(newTask, TaskStatus.SUCCESS)
        } catch (e: Exception) {
            fileSendingTaskService.updateTaskStatus(newTask, TaskStatus.FAILED)
        }
    }
}
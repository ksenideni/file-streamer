package org.example.filestreamerclient.service

import org.example.filestreamerclient.dto.TaskStatus
import org.example.filestreamerclient.entity.FileSendingTask
import org.example.filestreamerclient.repository.FileSendingTaskRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileSendingTaskService(
    private val fileSendingTaskRepository: FileSendingTaskRepository
) {

    fun createSendingTask(file: MultipartFile, requiredColumns: List<String>) {
        val task = FileSendingTask(
            fileName = file.name,
            status = TaskStatus.NEW,
            requiredColumns = requiredColumns.toString()
        )
        fileSendingTaskRepository.save(task)
    }

    fun findNewTask() = fileSendingTaskRepository.findFirstNewTask()

    fun updateTaskStatus(task: FileSendingTask, newStatus: TaskStatus) =
        fileSendingTaskRepository.save(task.apply { status = newStatus })

}
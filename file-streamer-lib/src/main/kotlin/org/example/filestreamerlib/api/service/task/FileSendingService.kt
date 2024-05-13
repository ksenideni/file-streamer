package org.example.filestreamerlib.api.service.task


import org.example.filestreamerlib.api.dao.FileSendingTaskRepository
import org.example.filestreamerlib.api.entity.FileSendingTask
import org.example.filestreamerlib.api.entity.TaskStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileSendingService(
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
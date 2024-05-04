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

    fun createSendingTask(file: MultipartFile) {
        val task = FileSendingTask(
            fileName = file.name,
            status = TaskStatus.NEW
        )
        fileSendingTaskRepository.save(task)
    }
}
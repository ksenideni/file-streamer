package org.example.filestreamerlib.api.dao

import org.example.filestreamerlib.api.entity.FileSendingTask
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface FileSendingTaskRepository : CrudRepository<FileSendingTask, Long> {
    @Query("SELECT * FROM file_sending_task WHERE status = 'NEW' ORDER BY id DESC LIMIT 1")
    fun findFirstNewTask(): FileSendingTask?
}
package org.example.filestreamerclient.entity

import org.example.filestreamerclient.dto.TaskStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("file_sending_task")
class FileSendingTask(
    @Id
    var id: Long? = null,
    val fileName: String,
    val status: TaskStatus
)

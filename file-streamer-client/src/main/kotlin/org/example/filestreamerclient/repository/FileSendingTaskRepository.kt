package org.example.filestreamerclient.repository

import org.example.filestreamerclient.entity.FileSendingTask
import org.springframework.data.repository.CrudRepository

interface FileSendingTaskRepository : CrudRepository<FileSendingTask, Long>
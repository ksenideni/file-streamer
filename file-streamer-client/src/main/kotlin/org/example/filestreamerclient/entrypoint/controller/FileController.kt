package org.example.filestreamerclient.entrypoint.controller

import org.example.filestreamerclient.service.FileReaderService
import org.example.filestreamerclient.service.FileSendingTaskService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


val logger = LoggerFactory.getLogger(FileController::class.java)

@RestController
@RequestMapping("/api/files")
class FileController(
    private val fileReaderService: FileReaderService,
    private val fileSendingTaskService: FileSendingTaskService
) {
    @GetMapping("/produce")
    fun readFile(@RequestParam requiredColumns: List<String>): Int {
        logger.info("produce in controller")
        fileReaderService.readFile(requiredColumns)
        return 1
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile, @RequestParam requiredColumns: List<String>): String {
        val status = fileReaderService.saveFile(file)
        fileSendingTaskService.createSendingTask(file, requiredColumns)
        return status
    }
}
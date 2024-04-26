package org.example.filestreamerclient.entrypoint.controller

import org.example.filestreamerclient.service.FileReaderService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

val logger = LoggerFactory.getLogger(FileController::class.java)

@RestController("/api/files")
class FileController(
    private val fileReader: FileReaderService
) {
    @GetMapping("/produce")
    fun readFile(@RequestParam requiredColumns: List<String>): Int {
        logger.info("produce in controller")
        fileReader.readFile(requiredColumns)
        return 1
    }
}
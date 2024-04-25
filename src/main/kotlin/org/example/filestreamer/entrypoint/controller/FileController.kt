package org.example.filestreamer.entrypoint.controller

import org.example.filestreamer.service.fileprocessor.FileReaderService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

val logger = LoggerFactory.getLogger(FileController::class.java)

@RestController("/api/files")
class FileController(
    private val fileReader: FileReaderService
) {
    @GetMapping("/produce")
    fun readFile(): Int {
        logger.info("produce in controller")
        fileReader.readFile()
        return 1
    }
}
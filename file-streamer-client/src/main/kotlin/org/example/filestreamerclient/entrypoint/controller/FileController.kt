package org.example.filestreamerclient.entrypoint.controller

import org.example.filestreamerclient.service.FileReaderService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


val logger = LoggerFactory.getLogger(FileController::class.java)

@RestController
@RequestMapping("/api/files")
class FileController(
    private val fileReaderService: FileReaderService
) {


    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile, @RequestParam requiredColumns: List<String>): String {
        return fileReaderService.saveFile(file, requiredColumns)
    }
}
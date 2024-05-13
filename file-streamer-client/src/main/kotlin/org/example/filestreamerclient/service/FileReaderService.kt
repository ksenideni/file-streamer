package org.example.filestreamerclient.service

import org.example.filestreamerlib.api.service.task.FileSendingService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileOutputStream


val logger = LoggerFactory.getLogger(FileReaderService::class.java)
private const val FILE_NAME = "file-streamer-client/src/main/resources/files/name-and-age.csv"
private const val FILE_PATH = "file-streamer-client/src/main/resources/files/"

@Service
class FileReaderService(
    val fileSendingService: FileSendingService
) {

    fun saveFile(file: MultipartFile, requiredColumns: List<String>): String {
        val filePath = FILE_PATH + file.originalFilename
        var fileUploadStatus = "File Uploaded Successfully"
        try {
            val fout = FileOutputStream(filePath)
            fout.write(file.bytes)
            fout.close()
            fileSendingService.createSendingTask(file, requiredColumns)
        } catch (e: Exception) {
            e.printStackTrace()
            fileUploadStatus = "Error in uploading file: $e"
        }
        return fileUploadStatus
    }
}
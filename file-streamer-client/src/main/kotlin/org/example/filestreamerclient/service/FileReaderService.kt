package org.example.filestreamerclient.service

import org.example.filestreamerclient.entrypoint.producer.KafkaProducer
import org.example.filestreamerlib.api.FileStreamer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream


val logger = LoggerFactory.getLogger(FileReaderService::class.java)
private const val FILE_NAME = "file-streamer-client/src/main/resources/files/name-and-age.csv"
private const val FILE_PATH = "file-streamer-client/src/main/resources/files/"

@Service
class FileReaderService(
    val kafkaProducer: KafkaProducer,
    val fileStreamer: FileStreamer = FileStreamer()
) {
    fun readFile(requiredColumns: List<String>) {
        readFileV2(requiredColumns)
    }

    fun readFile(fileName: String, requiredColumns: List<String>) {
        println("requiredColumns:$requiredColumns")
        val input = File("$FILE_PATH$fileName").inputStream()
        val jsons = fileStreamer.readFromInputStream(input, requiredColumns)
        jsons.forEach {
            kafkaProducer.sendEvent(it.toString())
        }
    }

    fun readFileV2(requiredColumns: List<String>) {
        println("requiredColumns:$requiredColumns")
        val input = File(FILE_NAME).inputStream()
        val jsons = fileStreamer.readFromInputStream(input, requiredColumns)
        jsons.forEach {
            kafkaProducer.sendEvent(it.toString())
        }
    }

    fun saveFile(file: MultipartFile): String {
        val filePath = FILE_PATH + file.originalFilename
        var fileUploadStatus = "File Uploaded Successfully"
        try {
            val fout = FileOutputStream(filePath)
            fout.write(file.bytes)
            fout.close()
        } catch (e: Exception) {
            e.printStackTrace()
            fileUploadStatus = "Error in uploading file: $e"
        }
        return fileUploadStatus
    }
}
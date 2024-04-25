package org.example.filestreamer.service.fileprocessor

import api.FileStreamer
import lombok.RequiredArgsConstructor
import org.example.filestreamer.entrypoint.producer.KafkaProducer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File


val logger = LoggerFactory.getLogger(FileReaderService::class.java)
private const val FILE_NAME = "src/main/resources/files/name-and-age.csv"

@Service
@RequiredArgsConstructor
class FileReaderService(
    val kafkaProducer: KafkaProducer,
    val fileStreamer: FileStreamer = FileStreamer()
) {
    fun readFile(requiredColumns: List<String>) {
        readFileV2(requiredColumns)
    }

    fun readFileV2(requiredColumns: List<String>) {
        println("requiredColumns:$requiredColumns")
        val input = File(FILE_NAME).inputStream()
        val jsons = fileStreamer.readFromInputStream(input, requiredColumns)
        jsons.forEach {
            kafkaProducer.sendEvent(it.toString())
        }
    }
}
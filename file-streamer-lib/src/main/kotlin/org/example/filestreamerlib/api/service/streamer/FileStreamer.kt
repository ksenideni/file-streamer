package org.example.filestreamerlib.api.service.streamer

import org.example.filestreamerlib.api.service.reader.FileReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import java.io.File

private const val FILE_PATH = "file-streamer-client/src/main/resources/files/"

class FileStreamer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val fileReader: FileReader,
    @Value("my.topic")
    private val topic: String
) {

    fun readFile(fileName: String, requiredColumns: List<String>) {
        println("requiredColumns:$requiredColumns")
        val input = File("$FILE_PATH$fileName").inputStream()
        val jsons = fileReader.readFromInputStream(input, requiredColumns)
        jsons.forEach {
            kafkaTemplate.send(topic, it.toString())
        }
    }
}
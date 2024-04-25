package org.example.filestreamer.service.fileprocessor

import api.FileStreamer
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import lombok.RequiredArgsConstructor
import org.example.filestreamer.dto.MyEvent
import org.example.filestreamer.dto.NameAgeDto
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

    fun readFileV1() {
        val csvFile = File(FILE_NAME)
        val orderLineSchema = CsvSchema.emptySchema().withHeader()
        val personIterator: MappingIterator<NameAgeDto> = CsvMapper()
            .readerFor(NameAgeDto::class.java)
            .with(orderLineSchema)
            .readValues(csvFile)
        val list: List<NameAgeDto> = personIterator.readAll()
        for (l in list)
            kafkaProducer.sendEvent(MyEvent(name = l.name))
    }

    fun readFileV2(requiredColumns: List<String>) {
        println("requiredColumns:$requiredColumns")
        val input = File(FILE_NAME).inputStream()
        fileStreamer.readFromInputStream(input, requiredColumns)
    }
}
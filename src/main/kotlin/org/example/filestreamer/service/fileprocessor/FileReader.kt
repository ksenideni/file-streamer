package org.example.filestreamer.service.fileprocessor

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
import java.nio.file.Files
import kotlin.io.path.Path


val logger = LoggerFactory.getLogger(FileReader::class.java)

@Service
@RequiredArgsConstructor
class FileReader(val kafkaProducer: KafkaProducer) {

    fun readFile() {
        val csvFile = File("src/main/resources/files/name-and-age.csv")
        val orderLineSchema = CsvSchema.emptySchema().withHeader()
        val personIterator: MappingIterator<NameAgeDto> = CsvMapper()
            .readerFor(NameAgeDto::class.java)
            .with(orderLineSchema)
            .readValues(csvFile)
        val list: List<NameAgeDto> = personIterator.readAll()
        for (l in list)
            kafkaProducer.sendEvent(MyEvent(name = l.name))

        parseFirstRow(Files.readAllLines(Path("src/main/resources/files/name-and-age.csv")))
    }

    fun readFileV2() {
        val lines = Files.readAllLines(Path("src/main/resources/files/name-and-age.csv"))


        val csvFile = File("src/main/resources/files/name-and-age.csv")

        val orderLineSchema = CsvSchema.emptySchema().withHeader()
        val personIterator: MappingIterator<NameAgeDto> = CsvMapper()
            .readerFor(NameAgeDto::class.java)
            .with(orderLineSchema)
            .readValues(csvFile)
        val list: List<NameAgeDto> = personIterator.readAll()
        for (l in list)
            kafkaProducer.sendEvent(MyEvent(name = l.name))
    }

    private fun getPositionUsingName(allColumnNames: List<String>, columnName: String): Int {
        return allColumnNames.indexOf(columnName)
    }

    private fun requiredColumns(): List<String> {
//        return listOf("name")
        return listOf("age")
//        return listOf("qwerty")
//        return listOf("name", "age")
    }

    private fun parseFirstRow(lines: List<String>) {
        logger.info("parseFirstRow and find headers")
        val separator = ";"
        val headLine = lines.first().split(separator).toTypedArray()
        headLine.forEach { println(it) }

    }
}
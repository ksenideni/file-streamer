package org.example.filestreamer.service.fileprocessor

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.opencsv.CSVReader
import lombok.RequiredArgsConstructor
import org.example.filestreamer.dto.MyEvent
import org.example.filestreamer.dto.NameAgeDto
import org.example.filestreamer.entrypoint.producer.KafkaProducer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileReader


val logger = LoggerFactory.getLogger(FileReaderService::class.java)
private const val FILE_NAME = "src/main/resources/files/name-and-age.csv"

@Service
@RequiredArgsConstructor
class FileReaderService(val kafkaProducer: KafkaProducer) {
    fun readFile() {
        readFileV2()
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

    fun readFileV2() {
        val csvReader = CSVReader(FileReader(FILE_NAME))
        var nextLine: Array<String>?

        val allColumnNames: Array<String> = csvReader.readNext()
//        val columnToPositionMap = allColumnNames.withIndex().associate { it.value to it.index }
        val requiredNames = requiredColumns()
//        val positions = requiredNames.map { getPositionUsingName(allColumnNames, it) }
        while ((csvReader.readNext().also { nextLine = it }) != null) {
            val indexOfNameFromFile =
                if (requiredNames.contains("name")) getPositionUsingName(allColumnNames, "name") else null
            val nameFromFile = indexOfNameFromFile?.let { nextLine!![indexOfNameFromFile] }

            val indexOfAgeFromFile =
                if (requiredNames.contains("age")) getPositionUsingName(allColumnNames, "age") else null
            val ageFromFile = indexOfAgeFromFile?.let { nextLine!![indexOfAgeFromFile] }
            val partlyEvent = MyEvent(
                name = nameFromFile,
                age = ageFromFile?.toInt()
            )
            println("Partly event:$partlyEvent")
        }
    }

    private fun getPositionUsingName(allColumnNames: Array<String>, columnName: String): Int {
        return allColumnNames.indexOf(columnName)
    }

    private fun requiredColumns(): List<String> {
//        return listOf("name")
//        return listOf("age")
//        return listOf("qwerty")
//        return listOf("name", "age")
        return listOf("age", "name")
    }

    private fun parseFirstRow(lines: List<String>) {
        logger.info("parseFirstRow and find headers")
        val separator = ";"
        val headLine = lines.first().split(separator).toTypedArray()
        headLine.forEach { println(it) }

    }
}
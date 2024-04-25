package org.example.filestreamer.service.fileprocessor

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.opencsv.CSVReader
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
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

        val allFileColumns: Array<String> = csvReader.readNext()
        val requiredNames = requiredColumns()

        //почистить от очевидно несуществующих колонок лист с колонками для чтения
        requiredNames.removeIf { !allFileColumns.contains(it) }
        println("list after cleaning junk columns=${requiredNames}")


        //определить позицию
        val columnNameToPositionMap = requiredNames.associateWith {
            getPositionUsingName(allFileColumns, it)
        }

//        val positions = requiredNames.map { getPositionUsingName(allColumnNames, it) }
        while ((csvReader.readNext().also { nextLine = it }) != null) {
            val newJson = buildJsonObject {
                columnNameToPositionMap.forEach { (columnName, i) ->
                    put(columnName, nextLine!![i])
                }
            }
            println("new Json Without PreKnown Columns:$newJson")
        }
    }

    private fun getPositionUsingName(allColumnNames: Array<String>, columnName: String): Int {
        return allColumnNames.indexOf(columnName)
    }

    private fun requiredColumns(): MutableList<String> {
//        return mutableListOf("name")
//        return mutableListOf("age")
        return mutableListOf("qwerty")
//        return mutableListOf("name", "age")
//        return mutableListOf("age", "name")
    }

    private fun parseFirstRow(lines: List<String>) {
        logger.info("parseFirstRow and find headers")
        val separator = ";"
        val headLine = lines.first().split(separator).toTypedArray()
        headLine.forEach { println(it) }

    }
}
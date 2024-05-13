package org.example.filestreamerlib.api.service.reader

import com.opencsv.CSVReader
import kotlinx.serialization.json.JsonObject
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.InputStreamReader

@Component
class FileReader {

    /***
     * Метод для чтения определенных полей из файла
     *
     * @param input стрим CSV-файла
     * @param selectedColumns колонки, которые необходимо вычитать из файла
     */
    fun readFromInputStream(input: InputStream, selectedColumns: List<String>): List<JsonObject> {
        val csvReader = CSVReader(InputStreamReader(input))
        var nextLine: Array<String>?

        val fileColumns: Array<String> = csvReader.readNext()

        //почистить от очевидно несуществующих колонок лист с колонками для чтения

        val mutableSelectedColumns = selectedColumns.toMutableList()
        mutableSelectedColumns.removeIf { !fileColumns.contains(it) }
        if (mutableSelectedColumns.isEmpty()) return listOf()
        println("list after cleaning junk columns=${mutableSelectedColumns}")

        //определить позицию
        val columnNameToPositionMap = columnToPosition(fileColumns, mutableSelectedColumns)


        val jsonList = ArrayList<JsonObject>()
        while ((csvReader.readNext().also { nextLine = it }) != null) {
            jsonList.add(constructJson(columnNameToPositionMap, nextLine!!))
        }
        return jsonList
    }

    /**
     * Метод для чтения всех полей из файла
     *
     * @param input стрим CSV-файла
     */
    fun readFromInputStream(input: InputStream): List<JsonObject> {
        val csvReader = CSVReader(InputStreamReader(input))
        var nextLine: Array<String>?

        val fileColumns: Array<String> = csvReader.readNext()

        //определить позицию
        val columnNameToPositionMap = columnToPosition(fileColumns)

        val jsonList = ArrayList<JsonObject>()
        while ((csvReader.readNext().also { nextLine = it }) != null) {
            jsonList.add(constructJson(columnNameToPositionMap, nextLine!!))
        }
        return jsonList
    }

}
package api

import com.opencsv.CSVReader
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.InputStream
import java.io.InputStreamReader

class FileStreamer {

    /***
     * inputStream стрим CSV-файла (todo по идее excel тоже зайдет)
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
        val columnNameToPositionMap = mutableSelectedColumns.associateWith {
            getPositionUsingName(fileColumns, it)
        }

        val jsonList = ArrayList<JsonObject>()
        while ((csvReader.readNext().also { nextLine = it }) != null) {
            jsonList.add(constructJson(columnNameToPositionMap, nextLine!!))
        }
        return jsonList
    }

    private fun constructJson(columnNameToPositionMap: Map<String, Int>, nextLine: Array<String>): JsonObject {
        val jsonObj = buildJsonObject {
            columnNameToPositionMap.forEach {
                put(it.key, nextLine[it.value])
            }
        }
        println("new Json Without PreKnown Columns:$jsonObj")
        return jsonObj
    }

    private fun getPositionUsingName(allColumnNames: Array<String>, columnName: String): Int {
        return allColumnNames.indexOf(columnName)
    }

}
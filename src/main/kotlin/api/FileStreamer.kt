package api

import com.opencsv.CSVReader
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.InputStream
import java.io.InputStreamReader

class FileStreamer {

    /***
     * inputStream стрим CSV-файла (todo по идее excel тоже зайдет)
     */
    fun readFromInputStream(input: InputStream, selectedColumns: List<String>) {
        val csvReader = CSVReader(InputStreamReader(input))
        var nextLine: Array<String>?

        val fileColumns: Array<String> = csvReader.readNext()

        //почистить от очевидно несуществующих колонок лист с колонками для чтения

        val mutableSelectedColumns = selectedColumns.toMutableList()
        mutableSelectedColumns.removeIf { !fileColumns.contains(it) }
        if (mutableSelectedColumns.isEmpty()) return
        println("list after cleaning junk columns=${mutableSelectedColumns}")

        //определить позицию
        val columnNameToPositionMap = mutableSelectedColumns.associateWith {
            getPositionUsingName(fileColumns, it)
        }

        while ((csvReader.readNext().also { nextLine = it }) != null) {
            val newJson = constructJson(columnNameToPositionMap, nextLine!!)
            println("new Json Without PreKnown Columns:$newJson")
        }
    }

    private fun constructJson(columnNameToPositionMap: Map<String, Int>, nextLine: Array<String>) = buildJsonObject {
        columnNameToPositionMap.forEach {
            put(it.key, nextLine[it.value])
        }
    }

    private fun getPositionUsingName(allColumnNames: Array<String>, columnName: String): Int {
        return allColumnNames.indexOf(columnName)
    }

}
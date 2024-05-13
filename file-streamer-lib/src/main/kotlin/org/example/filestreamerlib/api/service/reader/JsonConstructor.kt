package org.example.filestreamerlib.api.service.reader

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * Метод для создания json из необходимых позиций файла и набора всех полей fields
 *
 * @param columnNameToPositionMap мапа с необходимыми колонками, где
 * key - имя необходимого поля
 * value - позиция этого поля в массиве полей из строки файла
 *
 * @param fields - массив полей из строки файла
 */
fun constructJson(columnNameToPositionMap: Map<String, Int>, fields: Array<String>): JsonObject {
    val jsonObj = buildJsonObject {
        columnNameToPositionMap.forEach {
            put(it.key, fields[it.value])
        }
    }
    println("new Json Without PreKnown Columns:$jsonObj")
    return jsonObj
}

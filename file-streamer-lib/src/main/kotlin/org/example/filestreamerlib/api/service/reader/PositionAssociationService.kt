package org.example.filestreamerlib.api.service.reader

fun columnToPosition(columnsSource: Array<String>) =
    columnsSource.associateWith {
        getPositionUsingName(columnsSource, it)
    }

fun columnToPosition(columnsSource: Array<String>, undefinedList: List<String>) =
    undefinedList.associateWith {
        getPositionUsingName(columnsSource, it)
    }

private fun getPositionUsingName(allColumnNames: Array<String>, columnName: String): Int {
    return allColumnNames.indexOf(columnName)
}
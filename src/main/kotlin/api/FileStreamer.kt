package api

import java.io.InputStream

class FileStreamer<T>(

) {
    /***
     * inputStream стрим CSV-файла (todo по идее excel тоже зайдет)
     */
    fun returnListTs(inputStream: InputStream, requiredColumns: List<String>): List<String> {
        return listOf()
    }
}
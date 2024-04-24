package org.example.filestreamer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FileStreamerApplication

fun main(args: Array<String>) {
    runApplication<FileStreamerApplication>(*args)
}

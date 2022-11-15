package com.pxf.chess

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChessKotlinTypescriptApplication

fun main(args: Array<String>) {
    println("Hello Kotlin!")
    runApplication<ChessKotlinTypescriptApplication>(*args)
}

package com.pxf.chess

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ChessKotlinTypescriptApplication()

fun main(args: Array<String>) {
    runApplication<ChessKotlinTypescriptApplication>(*args)
}


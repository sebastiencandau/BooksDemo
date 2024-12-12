package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)

}

fun cipher(encode: Char, key: Int): Char {
    require(encode in 'A'..'Z')
    require(key >= 0)

    val normalizedKey = key % 26
    val newChar = encode + normalizedKey
    return if (newChar > 'Z') {
        'A' + (newChar - 'Z' - 1)
    } else {
        newChar
    }
}


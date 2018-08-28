package com.tobidaada.chatapp.utils

import java.lang.StringBuilder
import java.util.*

object NumberUtils {

    // TODO: Come up with a better algorithm
    fun generateRandomNumber(): String {

        val MAX_LENGTH = 10

        val generator = Random()
        val randomStringBuilder = StringBuilder()
        val randomLength = generator.nextInt(MAX_LENGTH)
        var tempChar: Char?

        for (i in 0..randomLength) {
            tempChar = (generator.nextInt(96) + 32).toChar()
            randomStringBuilder.append(tempChar)
        }

        return  randomStringBuilder.toString()
    }
}
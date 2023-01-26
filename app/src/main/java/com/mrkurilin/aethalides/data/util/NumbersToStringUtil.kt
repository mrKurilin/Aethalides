package com.mrkurilin.aethalides.data.util

class NumbersToStringUtil {

    companion object {

        private val numbers = mutableMapOf<Int, String>()

        fun getStringFromInt(number: Int): String {
            if (numbers[number] == null) {
                numbers[number] = number.toString()
            }
            return numbers[number] ?: number.toString()
        }
    }
}
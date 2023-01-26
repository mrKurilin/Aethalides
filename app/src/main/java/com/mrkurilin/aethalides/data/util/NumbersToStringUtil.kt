package com.mrkurilin.aethalides.data.util

class NumbersToStringUtil {

    companion object {

        private val stringsNumberOfInt = mutableMapOf<Int, String>()

        fun getStringFromInt(number: Int): String {
            if (stringsNumberOfInt[number] == null) {
                stringsNumberOfInt[number] = number.toString()
            }
            return stringsNumberOfInt[number] ?: number.toString()
        }
    }
}
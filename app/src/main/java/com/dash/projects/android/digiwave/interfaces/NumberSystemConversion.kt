package com.dash.projects.android.digiwave.interfaces

interface NumberSystemConversion {
    fun decimalToBinary(value: Number?): Number?
    fun decimalToOctal(value: Number?): Number?
    fun decimalToHexadecimal(value: Number?): Number?

    fun binaryToDecimal(value: Number?): Number?
    fun binaryToOctal(value: Number?): Number?
    fun binaryToHexadecimal(value: Number?): Number?

    fun octalToDecimal(value: Number?): Number?
    fun octalToBinary(value: Number?): Number?
    fun octalToHexadecimal(value: Number?): Number?

    fun hexadecimalToDecimal(value: Number?): Number?
    fun hexadecimalToBinary(value: Number?): Number?
    fun hexadecimalToOctal(value: Number?): Number?
}

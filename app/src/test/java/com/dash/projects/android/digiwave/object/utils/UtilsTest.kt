package com.dash.projects.android.digiwave.`object`.utils

import com.dash.projects.android.digiwave.`object`.utils.Utils.adds
import com.dash.projects.android.digiwave.`object`.utils.Utils.and
import com.dash.projects.android.digiwave.`object`.utils.Utils.castToBoolean
import com.dash.projects.android.digiwave.`object`.utils.Utils.drawableResListOf
import com.dash.projects.android.digiwave.`object`.utils.Utils.eachIsNotEmpty
import com.dash.projects.android.digiwave.`object`.utils.Utils.elementToString
import com.dash.projects.android.digiwave.`object`.utils.Utils.fon
import com.dash.projects.android.digiwave.`object`.utils.Utils.inverse
import com.dash.projects.android.digiwave.`object`.utils.Utils.isNotNull
import com.dash.projects.android.digiwave.`object`.utils.Utils.merge
import com.dash.projects.android.digiwave.`object`.utils.Utils.nand
import com.dash.projects.android.digiwave.`object`.utils.Utils.nor
import com.dash.projects.android.digiwave.`object`.utils.Utils.notContainedIn
import com.dash.projects.android.digiwave.`object`.utils.Utils.or
import com.dash.projects.android.digiwave.`object`.utils.Utils.splitDigit
import com.dash.projects.android.digiwave.`object`.utils.Utils.str
import com.dash.projects.android.digiwave.`object`.utils.Utils.stringResListOf
import com.dash.projects.android.digiwave.`object`.utils.Utils.toDecimal
import com.dash.projects.android.digiwave.`object`.utils.Utils.toGray
import com.dash.projects.android.digiwave.`object`.utils.Utils.xnor
import com.dash.projects.android.digiwave.`object`.utils.Utils.xor
import org.junit.Assert.*
import org.junit.Test

class UtilsTest {

    // 1
    @Test
    fun str_castIntegerToString_returnStringIntegerValue() {
        val dummyBit = 1010

        assertEquals("1010", str(dummyBit))
    }

    @Test
    fun str_castLongToString_returnStringLongValue() {
        val dummyBit = 8021L

        assertEquals("8021", str(dummyBit))
    }

    @Test
    fun str_castArrayListObjectToString_returnStringArrayListObject() {
        val dummyArrayList = ArrayList<Number>().apply {
            add(8)
            add(2)
        }

        assertEquals(dummyArrayList.toString(), str(dummyArrayList))
    }

    // 2
    @Test
    fun castToBoolean_castNumberHigherOrEqualThanOne_ReturnTrue() {
        // arrange
        val dummyDigit = 8
        val high = 1

        assertEquals(true, dummyDigit.castToBoolean())
        assertEquals(true, high.castToBoolean())
    }

    @Test
    fun castToBoolean_castNumberLowerThanOneOrCastNegativeNumber_ReturnFalse() {
        // arrange
        val dummyDigit = -8
        val low = 0

        assertEquals(false, dummyDigit.castToBoolean())
        assertEquals(false, low.castToBoolean())
    }

    @Test
    fun stringResListOf_checkItsElements_returnTrueIfElementsAreSame() {
        val expectedStringResourcesId = ArrayList<Int>().apply {
            add(1)
            add(7)
            add(90)
        }

        assertEquals(expectedStringResourcesId, stringResListOf(1, 7, 90))
    }

    @Test
    fun drawableResListOf_checkItsElements_returnTrueIfElementsAreSame() {
        val expectedDrawableResourceId = ArrayList<Int>().apply {
            add(8)
            add(4)
            add(100)
        }

        assertEquals(expectedDrawableResourceId, drawableResListOf(8, 4, 100))
    }

    @Test
    fun isNotNull_checkValuesIsNotNull_ReturnTrueIfValuesAreNotNull() {
        val dummyBit = 1010

        assertEquals(true, dummyBit.isNotNull())
    }

    @Test
    fun isNotNull_checkValuesIsNull_ReturnFalseIfValuesAreNull() {
        val nullableValue = null

        assertEquals(false, nullableValue.isNotNull())
    }

    @Test
    fun elementToString_castingArrayElementToStringWithNullRadix_returnArrayWithStringElements() {
        val listBit = ArrayList<Int>().apply {
            add(1010)
            add(1111)
            add(0)
        }.toList()

        val expectedListBit = ArrayList<String>().apply {
            add("1010")
            add("1111")
            add("0")
        }.toList()

        assertEquals(expectedListBit, listBit.elementToString())
    }

    @Test
    fun elementToString_castingArrayElementToStringWithBinaryRadix_returnArrayWithBinaryStringElements() {
        val binaryRadix = 2
        val listDigitDecimal = ArrayList<Int>().apply {
            add(8)
            add(4)
            add(6)
        }.toList()

        val expectedListDigitBinary = ArrayList<String>().apply {
            add("1000")
            add("100")
            add("110")
        }.toList()

        assertEquals(expectedListDigitBinary, listDigitDecimal.elementToString(binaryRadix))
    }

    @Test
    fun elementToString_expectElementsAreNybble_throwAssertionError() {
        val expectedExceptionMessage = "expected:<[1000, 0100, 0110]> but was:<[1000, 100, 110]>"
        val expectedException = assertThrows(AssertionError::class.java) {
            val binaryRadix = 2
            val listDigitDecimal = ArrayList<Int>().apply {
                add(8)
                add(4)
                add(6)
            }.toList()

            val listDigitBinaryAsNybble = ArrayList<String>().apply {
                add("1000")
                add("0100")
                add("0110")
            }.toList()

            assertEquals(listDigitBinaryAsNybble, listDigitDecimal.elementToString(binaryRadix))
        }

        assertEquals(expectedExceptionMessage, expectedException.message)
    }

    @Test
    fun elementToString_castingArrayElementsToStringAndGroupItAsNybbleWithPadStartAndBinaryRadix_returnArrayStringWithEachElementsIsNybbleBit() {
        val binaryRadix = 2
        val listDigitDecimal = ArrayList<Int>().apply {
            add(8)
            add(4)
            add(6)
        }.toList()

        val expectedListDigitBinaryAsNybbleWithPadStart = ArrayList<String>().apply {
            add("1000")
            add("0100")
            add("0110")
        }.toList()

        assertEquals(
            expectedListDigitBinaryAsNybbleWithPadStart, listDigitDecimal.elementToString(
                radix = binaryRadix, asNybble = true, padStart = true
            )
        )
    }

    @Test
    fun elementToString_castingArrayElementsToStringAndGroupItAsNybbleWithPadEndAndBinaryRadix_returnArrayStringWithEachElementsIsNybbleBit() {
        val binaryRadix = 2
        val listDigitDecimal = ArrayList<Int>().apply {
            add(8)
            add(4)
            add(6)
        }.toList()

        val expectedListDigitBinaryAsNybbleWithPadEnd = ArrayList<String>().apply {
            add("1000")
            add("1000")
            add("1100")
        }.toList()

        assertEquals(
            expectedListDigitBinaryAsNybbleWithPadEnd, listDigitDecimal.elementToString(
                radix = binaryRadix, asNybble = true, padStart = false
            )
        )
    }

    @Test
    fun elementToString_castingArrayElementsToStringWithBinaryRadixAndStringLengthIsMoreThan4_returnBinaryStringElementsWithItsLengthIsMoreThan4() {
        val binaryRadix = 2
        val listDigitDecimal = ArrayList<Int>().apply {
            add(18)
            add(44)
            add(61)
        }.toList()

        val expectedListDigitHexWithNoPadAndNotNybble = ArrayList<String>().apply {
            add("10010")
            add("101100")
            add("111101")
        }.toList()

        assertEquals(
            expectedListDigitHexWithNoPadAndNotNybble, listDigitDecimal.elementToString(
                radix = binaryRadix, asNybble = true, padStart = true
            )
        )
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun fon_compareTwoCollectionAndGetItsFirstIdendticalValue_returnTheFirstIdenticalValueFromTheComparassion() {
        val it = ArrayList<String>().apply {
            add("lorem")
            add("ipsum")
            add("dolor")
            add("consectetur")
        }

        val with = ArrayList<CharSequence>().apply {
            add("loren")
            add("gypsum")
            add("dollar")
            add("consectetur")
        }

        assertNotNull(it.fon(with))
        assertEquals("consectetur", it.fon(with))
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun fon_compareTwoCollectionAndGetItsFirstIdendticalValue_returnNullableValueBecauseThereIsNoIdenticalValueOnEachCollectionAfterComparassion() {
        val it = ArrayList<String>().apply {
            add("lorem")
            add("ipsum")
            add("dolor")
            add("consectetur")
        }

        val with = ArrayList<CharSequence>().apply {
            add("loren")
            add("gypsum")
            add("dollar")
            add("constructor")
        }

        assertNull(it.fon(with))
    }

    @Test
    fun toDecimal_convertBase1ValueToItsDecimalEquivalent_throwIllegalArgumentExceptionError() {
        val expectedExceptionMessage = "radix 1 was not in valid range 2..36"
        val expectedException = assertThrows(IllegalArgumentException::class.java) {
            val base1Radix = 1
            val base1Digits: CharSequence = "000"

            base1Digits.toDecimal(base1Radix)
        }

        assertEquals(expectedExceptionMessage, expectedException.message)
    }

    @Test
    fun toDecimal_convertBinaryForbiddenValue_throwIllegalArgumentException() {
        val expectedExceptionMessage = "Char 8 is not a digit in the given radix=2"
        val expectedException = assertThrows(IllegalArgumentException::class.java) {
            val binaryRadix = 2
            val bit: CharSequence = "874"

            bit.toDecimal(binaryRadix)
        }

        assertEquals(expectedExceptionMessage, expectedException.message)
    }

    @Test
    fun toDecimal_convertUnknownRadixCharacterFromCastingRandomStringToInteger_throwNumberFormatException() {
        val expectedExceptionMessage = "For input string: \"2zT3smMDk2\""
        val expectedException = assertThrows(NumberFormatException::class.java) {
            val randomString = "2zT3smMDk2"
            val digits: CharSequence = "1AF"

            digits.toDecimal(randomString.toInt())
        }

        assertEquals(expectedExceptionMessage, expectedException.message)
    }

    @Test
    fun toDecimal_convertBinaryValueToItsDecimalEquivalent_returnDecimalValueFromItsBinaryEquivalent() {
        val binaryRadix = 2
        val bit: CharSequence = "1110001"
        val expectedDecimalValue = 1 + 16 + 32 + 64L

        assertEquals(expectedDecimalValue, bit.toDecimal(binaryRadix))
    }

    @Test
    fun toDecimal_convertOctalValueToItsDecimalEquivalent_returnDecimalValueFromItsOctalEquivalent() {
        val octalRadix = 8
        val octalDigits: CharSequence = "731"
        val expectedDecimalValue = 473L

        assertEquals(expectedDecimalValue, octalDigits.toDecimal(octalRadix))
    }

    @Test
    fun toDecimal_convertBase16ValueToItsDecimalEquivalent_returnDecimalValueFromItsBase16Equivalent() {
        val base16Radix = 16
        val base16Digits: CharSequence = "1AF"
        val expectedDecimalValue = 431L

        assertEquals(expectedDecimalValue, base16Digits.toDecimal(base16Radix))
    }

    @Test
    fun toGray_encodeOctalValueToGrayCodeValueWithWrongRadixByCastingDoubleNumberCharacterToIntDirectlyWithoutCastItToDoubleFirst_throwNumberFormatException() {
        val expectedExceptionMessage = "For input string: \"8.0\""
        val expectedException = assertThrows(NumberFormatException::class.java) {
            val wrongOctalRadix = "8.0"
            val octalDigits = "275"

            octalDigits.toGray(wrongOctalRadix.toInt())
        }

        assertEquals(expectedExceptionMessage, expectedException.message)
    }

    @Test
    fun toGray_encodeBinaryValueToGrayCodeValue_returnGrayCodeValueFromEncodedBinaryValue() {
        val binaryRadix = 2
        val bit = "1010"
        val expectedGrayCodeValue = "1111"

        assertEquals(expectedGrayCodeValue, bit.toGray(binaryRadix))
    }

    @Test
    fun toGray_encodeHexValueToGrayCodeValue_returnGrayCodeValueFromEncodedHexValue() {
        val hexRadix = 16
        val hexDigits = "FEE"
        val expectedGrayCodeValue = "100000011001"

        assertEquals(expectedGrayCodeValue, hexDigits.toGray(hexRadix))
    }

    @Test
    fun toGray_encodeOctalValueToGrayCodeValue_returnGrayCodeValueFromEncodedOctalValue() {
        val octalRadix = 8
        val octalDigits = "652"
        val expectedGrayCodeValue = "101111111"

        assertEquals(expectedGrayCodeValue, octalDigits.toGray(octalRadix))
    }

    @Test
    fun splitDigit_splitDoubleStringCharacterButDirectlyCastingItToLong_throwNumberFormatException() {
        val expectedExceptionMessage = "For input string: \"88.9\""
        val expectedException = assertThrows(NumberFormatException::class.java) {
            val doubleDigitOnStringCharacter = "88.9"
            doubleDigitOnStringCharacter.toLong().splitDigit()
        }

        assertEquals(expectedExceptionMessage, expectedException.message)
    }

    @Test
    fun splitDigit_splitNegativeLongNumber_expectResultIsNegativeNumberButThrownAnAssertionError() {
        val expectedExceptionMessage = "expected:<[-2]> but was:<[2]>"
        val expectedException = assertThrows(AssertionError::class.java) {
            val negativeLongNumber = -2L
            val expectedResult = List(1) { -2 }

            assertEquals(expectedResult, negativeLongNumber.splitDigit())
        }

        assertEquals(expectedExceptionMessage, expectedException.message)
    }

    @Test
    fun splitDigit_splitNegativeLongNumber_returnOnlyItsPositiveIntegerNumbersAndIgnoringTheNegativeSymbol() {
        val randomCharacters = -2L
        val expectedResult = List(1) { 2 }

        assertEquals(expectedResult, randomCharacters.splitDigit())
    }

    @Test
    fun splitDigit_splitDoubleStringCharacter_returnListOfSeparatedDoubleNumberButOnlyItsIntegerNumberOrNumbersInFrontOfComma() {
        val doubleDigitOnStringCharacter = "8513.923"
        val expectedResult = ArrayList<Int>().apply {
            add(8)
            add(5)
            add(1)
            add(3)
        }.toList()

        assertEquals(expectedResult, doubleDigitOnStringCharacter.toDouble().toLong().splitDigit())
    }

    @Test
    fun merge_joinEmptyListToSingleStringWithNoSeparatorForSeparateEachElements_returnEmptyString() {
        val expectedResult = ""
        val anyList = ArrayList<Any?>()

        assertEquals(expectedResult, anyList.merge())
    }

    @Test
    fun merge_joinAnyListIntoSingleStringWithNoSeparatorForSeparateEachElements_returnAllElementsInAStringForm() {
        val expectedResult = "1.5" + "null" + "Hello" + "World" + "10" + "false"
        val anyList = ArrayList<Any?>().apply {
            add(1.5)
            add(null)
            add("Hello")
            add("World")
            add(10)
            add(false)
        }.toList()

        assertEquals(expectedResult, anyList.merge())
    }

    @Test
    fun merge_joinStringListIntoSingleStringWithCommasSeparatorForSeparateEachElements_returnAllElementsWhichSeparatedByCommaInAStringForm() {
        val commaSeparator = ","
        val expectedResult = "1.5" + commaSeparator +
                "null" + commaSeparator +
                "Hello" + commaSeparator +
                "World" + commaSeparator +
                "10" + commaSeparator +
                "false"

        val anyList = ArrayList<Any?>().apply {
            add(1.5)
            add(null)
            add("Hello")
            add("World")
            add(10)
            add(false)
        }.toList()

        assertEquals(expectedResult, anyList.merge(commaSeparator))
    }

    @Test
    fun eachIsNotEmpty_verifyThereIsNoEmptyValueOnArrayElement_returnFalse() {
        val listName = listOf("Allow", "Cancel", "Lorem", "Ipsum", "Dolor", "Hello", "World", "")

        assertEquals(false, eachIsNotEmpty(*listName.toTypedArray()))
    }

    @Test
    fun eachIsNotEmpty_verifyArrayIsNotEmpty_returnTrue() {
        val listName = listOf("Hello", "World", "Lorem", "Ipsum", "Dolor")

        assertEquals(true, eachIsNotEmpty(*listName.toTypedArray()))
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun notContainedIn_verifyTheStringIsNotContainedOnArrayElement_returnFalse() {
        val appName = "digiwave"
        val listOfDigitalElectronicsAppName = listOf(
            "sisdig corner's",
            "lorem digital app",
            "ipsum logic gate",
            "kmap consectetur",
            "digiwave"
        )

        assertEquals(false, appName.notContainedIn(*listOfDigitalElectronicsAppName.toTypedArray()))
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun notContainedIn_verifyTheStringIsNotContainedOnArrayElement_returnTrue() {
        val appName = "digiwave"
        val listOfDigitalElectronicsAppName = listOf(
            "sisdig corner's",
            "lorem digital app",
            "ipsum logic gate",
            "kmap consectetur",
        )

        assertEquals(true, appName.notContainedIn(*listOfDigitalElectronicsAppName.toTypedArray()))
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun testEachIsNotEmpty_checkResultIfArrayElementOnFirstParamIsNotEmptyButAnExtensionStringIsContainedOnArrayElementAtSecondParam_returnFalse() {
        val firstParam = listOf("sisdig corner's", "numsys", " lorem games", " kmap ai")
        val extensionString = "digiwave"

        assertEquals(false, extensionString.eachIsNotEmpty(*firstParam.toTypedArray()) {
            // secondParam
            add("logate mastery")
            add("beautiful kmap")
            add("digiwave")
            this
        })
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun testEachIsNotEmpty_checkingAResultIfArrayOnFirstParamIsHaveAnEmptyElementButAnExtensionStringIsContainedOnArrayElementAtSecondParam_returnFalse() {
        val firstParam = listOf("sisdig corner's", "numsys", " lorem games", "", "", "")
        val extensionString = "digiwave"

        assertEquals(false, extensionString.eachIsNotEmpty(*firstParam.toTypedArray()) {
            // secondParam
            add("logate mastery")
            add("beautiful kmap")
            add("digiwave")
            this
        })
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun testEachIsNotEmpty_checkingAResultIfArrayAtFirstParamHasNoEmptyElementButAnExtensionStringIsNotContainedOnArrayElementAtSecondParam_returnFalse() {
        val firstParam = listOf("sisdig corner's", "numsys", " lorem games", "", "", "")
        val extensionString = "digiwave"

        assertEquals(false, extensionString.eachIsNotEmpty(*firstParam.toTypedArray()) {
            // secondParam
            add("logate mastery")
            add("beautiful kmap")
            this
        })
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun testEachIsNotEmpty_checkingAResultIfArrayElementOnFirstParamIsNotEmptyAndAnExtensionStringIsNotContainedOnArrayElementAtSecondParam_returnTrue() {
        val firstParam = listOf("sisdig corner's", "numsys", " lorem games")
        val extensionString = "digiwave"

        assertEquals(true, extensionString.eachIsNotEmpty(*firstParam.toTypedArray()) {
            // secondParam
            add("logate mastery")
            add("beautiful kmap")
            this
        })
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun adds_addMultipleObjectToAnArrayListButLastObjectWasTypo_throwAnAssertionError() {
        val expectedExceptionMessage =
            "expected:<[lorem, digiwave]> but was:<[lorem, digywave]>"
        val expectedException = assertThrows(AssertionError::class.java) {
            val emptyArrayList = ArrayList<String>()
            val expectedResult = ArrayList<String>().apply {
                add("lorem")
                add("digiwave")
            }.toList()

            assertEquals(expectedResult, emptyArrayList.adds("lorem", "digywave"))
        }

        assertEquals(expectedExceptionMessage, expectedException.message)
    }

    @Test
    fun adds_addMultipleObjectToAnArrayList_returnArrayListWithNewObjectOnItsEachElement() {
        val emptyArrayList = ArrayList<String>()
        val expectedResult = ArrayList<String>().apply {
            add("lorem")
            add("ipsum")
            add("dolor")
            add("sit")
            add("amet")
            add("digiwave")
        }.toList()

        assertEquals(
            expectedResult, emptyArrayList.adds(
                "lorem", "ipsum", "dolor", "sit", "amet", "digiwave"
            )
        )
    }

    @Test
    fun and_checkResultIfFirstAndSecondParamArgumentIsNegativeNumber_returnZero() {
        val firstArgument = -1
        val secondArgument = -100L
        val expectedResult = 0

        assertEquals(expectedResult, and(firstArgument, secondArgument.toInt()))
    }

    @Test
    fun and_checkResultIfFirstArgumentIsEqualOrHigherThanOneAndSecondParamArgumentIsNegativeNumber_returnZero() {
        val expectedResult = 0
        val firstArgumentOneValue = 1
        val firstArgumentHigherThanOne = 200

        val secondArgument = -80

        assertEquals(expectedResult, and(firstArgumentOneValue, secondArgument))
        assertEquals(expectedResult, and(firstArgumentHigherThanOne, secondArgument))
    }

    @Test
    fun and_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsZero_returnZero() {
        val firstArgumentOneValue = -500
        val secondArgument = 0
        val expectedResult = 0

        assertEquals(expectedResult, and(firstArgumentOneValue, secondArgument))
    }

    @Test
    fun and_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsPositiveNumberAndHigherThanZero_returnZero() {
        val firstArgumentOneValue = -500

        val expectedResult = 0
        val secondArgumentOneValue = 1
        val secondArgumentHigherThanOne = 200

        assertEquals(expectedResult, and(firstArgumentOneValue, secondArgumentOneValue))
        assertEquals(expectedResult, and(firstArgumentOneValue, secondArgumentHigherThanOne))
    }

    @Test
    fun and_checkResultIfFirstAndSecondParamArgumentIsZeroNumber_returnZero() {
        val firstArgument = 0
        val secondArgument = 0
        val expectedResult = 0

        assertEquals(expectedResult, and(firstArgument, secondArgument))
    }

    @Test
    fun and_checkResultIfFirstAndSecondParamArgumentNumberIsEqualOrHigherThanOne_returnOne() {
        val expectedResult = 1

        val firstArgumentValueOne = 1
        val secondArgumentValueOne = 1

        val firstArgumentHigherThanOne = 5000
        val secondArgumentHigherThanOne = 2311

        assertEquals(expectedResult, and(firstArgumentValueOne, secondArgumentValueOne))
        assertEquals(expectedResult, and(firstArgumentHigherThanOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, and(firstArgumentValueOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, and(firstArgumentHigherThanOne, secondArgumentValueOne))
    }

    @Test
    fun or_checkResultIfFirstAndSecondParamArgumentIsNegativeNumber_returnZero() {
        val firstArgument = -54L
        val secondArgument = -99
        val expectedResult = 0

        assertEquals(expectedResult, or(firstArgument.toInt(), secondArgument))
    }

    @Test
    fun or_checkResultIfFirstArgumentIsEqualOrHigherThanOneAndSecondParamArgumentIsNegativeNumber_returnOne() {
        val expectedResult = 1
        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 1999

        val secondArgument = -5

        assertEquals(expectedResult, or(firstArgumentValueOne, secondArgument))
        assertEquals(expectedResult, or(firstArgumentHigherThanOne, secondArgument))
    }

    @Test
    fun or_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsZero_returnZero() {
        val expectedResult = 0
        val firstArgument = -77
        val secondArgument = 0

        assertEquals(expectedResult, or(firstArgument, secondArgument))
    }

    @Test
    fun or_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsPositiveNumberAndHigherThanZero_returnOne() {
        val expectedResult = 1

        val firstArgument = -4

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 88

        assertEquals(expectedResult, or(firstArgument, secondArgumentValueOne))
        assertEquals(expectedResult, or(firstArgument, secondArgumentHigherThanOne))
    }

    @Test
    fun or_checkResultIfFirstAndSecondParamArgumentIsZeroNumber_returnZero() {
        val expectedResult = 0
        val firstArgument = 0
        val secondArgument = 0

        assertEquals(expectedResult, or(firstArgument, secondArgument))
    }

    @Test
    fun or_checkResultIfFirstAndSecondParamArgumentNumberIsEqualOrHigherThanOne_returnOne() {
        val expectedResult = 1

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 20

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 99


        assertEquals(expectedResult, or(firstArgumentValueOne, secondArgumentValueOne))
        assertEquals(expectedResult, or(firstArgumentHigherThanOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, or(firstArgumentValueOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, or(firstArgumentHigherThanOne, secondArgumentValueOne))
    }

    @Test
    fun xor_checkResultIfFirstAndSecondParamArgumentIsNegativeNumber_returnZero() {
        val expectedResult = 0

        val firstArgument = -20
        val secondArgument = -44

        assertEquals(expectedResult, xor(firstArgument, secondArgument))
    }

    @Test
    fun xor_checkResultIfFirstArgumentIsEqualOrHigherThanOneAndSecondParamArgumentIsNegativeNumber_returnOne() {
        val expectedResult = 1

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 40

        val secondArgument = -3

        assertEquals(expectedResult, xor(firstArgumentValueOne, secondArgument))
        assertEquals(expectedResult, xor(firstArgumentHigherThanOne, secondArgument))
    }

    @Test
    fun xor_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsZero_returnZero() {
        val expectedResult = 0
        val firstArgument = -1
        val secondArgument = 0

        assertEquals(expectedResult, xor(firstArgument, secondArgument))
    }

    @Test
    fun xor_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsPositiveNumberAndHigherThanZero_returnOne() {
        val expectedResult = 1

        val firstArgument = -1

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 75

        assertEquals(expectedResult, xor(firstArgument, secondArgumentValueOne))
        assertEquals(expectedResult, xor(firstArgument, secondArgumentHigherThanOne))
    }

    @Test
    fun xor_checkResultIfFirstAndSecondParamArgumentIsZeroNumber_returnZero() {
        val expectedResult = 0
        val firstArgument = 0
        val secondArgument = 0

        assertEquals(expectedResult, xor(firstArgument, secondArgument))
    }

    @Test
    fun xor_checkResultIfFirstAndSecondParamArgumentNumberIsEqualOrHigherThanOne_returnZero() {
        val expectedResult = 0

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 32

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 44

        assertEquals(expectedResult, xor(firstArgumentValueOne, secondArgumentValueOne))
        assertEquals(expectedResult, xor(firstArgumentHigherThanOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, xor(firstArgumentValueOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, xor(firstArgumentHigherThanOne, secondArgumentValueOne))
    }

    @Test
    fun inverse_checkResultIfArgumentIsOneNumber_returnZero() {
        val argument = 1
        val expectedResult = 0

        assertEquals(expectedResult, inverse(argument))
    }

    @Test
    fun inverse_checkResultIfArgumentIsHigherThanOneNumber_returnZero() {
        val argument = 291831
        val expectedResult = 0

        assertEquals(expectedResult, inverse(argument))
    }

    @Test
    fun inverse_checkResultIfArgumentIsNegativeNumber_returnOne() {
        val argument = -49
        val expectedResult = 1

        assertEquals(expectedResult, inverse(argument))
    }

    @Test
    fun inverse_checkResultIfArgumentIsZeroNumber_returnOne() {
        val argument = 0
        val expectedResult = 1

        assertEquals(expectedResult, inverse(argument))
    }

    @Test
    fun nand_checkResultIfFirstAndSecondParamArgumentIsNegativeNumber_returnOne() {
        val expectedResult = 1
        val firstArgument = -5
        val secondArgument = -2

        assertEquals(expectedResult, nand(firstArgument, secondArgument))
    }

    @Test
    fun nand_checkResultIfFirstArgumentIsEqualOrHigherThanOneAndSecondParamArgumentIsNegativeNumber_returnOne() {
        val expectedResult = 1

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 6

        val secondArgument = -7

        assertEquals(expectedResult, nand(firstArgumentValueOne, secondArgument))
        assertEquals(expectedResult, nand(firstArgumentHigherThanOne, secondArgument))
    }

    @Test
    fun nand_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsZero_returnOne() {
        val expectedResult = 1

        val firstArgument = -2
        val secondArgument = 0

        assertEquals(expectedResult, nand(firstArgument, secondArgument))
    }

    @Test
    fun nand_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsPositiveNumberAndHigherThanZero_returnOne() {
        val expectedResult = 1

        val firstArgument = -2

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 88

        assertEquals(expectedResult, nand(firstArgument, secondArgumentValueOne))
        assertEquals(expectedResult, nand(firstArgument, secondArgumentHigherThanOne))
    }

    @Test
    fun nand_checkResultIfFirstAndSecondParamArgumentIsZeroNumber_returnOne() {
        val expectedResult = 1
        val firstArgument = 0
        val secondArgument = 0

        assertEquals(expectedResult, nand(firstArgument, secondArgument))
    }

    @Test
    fun nand_checkResultIfFirstAndSecondParamArgumentIsZeroNumber_returnZero() {
        val expectedResult = 0

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 45

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 50

        assertEquals(expectedResult, nand(firstArgumentValueOne, secondArgumentValueOne))
        assertEquals(expectedResult, nand(firstArgumentHigherThanOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, nand(firstArgumentValueOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, nand(firstArgumentHigherThanOne, secondArgumentValueOne))
    }

    @Test
    fun nor_checkResultIfFirstAndSecondParamArgumentIsNegativeNumber_returnOne() {
        val expectedResult = 1
        val firstArgument = -90
        val secondArgument = -23

        assertEquals(expectedResult, nor(firstArgument, secondArgument))
    }

    @Test
    fun nor_checkResultIfFirstArgumentIsEqualOrHigherThanOneAndSecondParamArgumentIsNegativeNumber_returnZero() {
        val expectedResult = 0

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 43

        val secondArgument = -23

        assertEquals(expectedResult, nor(firstArgumentValueOne, secondArgument))
        assertEquals(expectedResult, nor(firstArgumentHigherThanOne, secondArgument))
    }

    @Test
    fun nor_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsZero_returnOne() {
        val expectedResult = 1

        val firstArgument = -1
        val secondArgument = 0

        assertEquals(expectedResult, nor(firstArgument, secondArgument))
    }

    @Test
    fun nor_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsPositiveNumberAndHigherThanZero_returnZero() {
        val expectedResult = 0

        val firstArgument = -8

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 56

        assertEquals(expectedResult, nor(firstArgument, secondArgumentValueOne))
        assertEquals(expectedResult, nor(firstArgument, secondArgumentHigherThanOne))
    }

    @Test
    fun nor_checkResultIfFirstAndSecondParamArgumentIsZeroNumber_returnOne() {
        val expectedResult = 1
        val firstArgument = 0
        val secondArgument = 0

        assertEquals(expectedResult, nor(firstArgument, secondArgument))
    }

    @Test
    fun nor_checkResultIfFirstAndSecondParamArgumentNumberIsEqualOrHigherThanOne_returnZero() {
        val expectedResult = 0

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 39

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 89

        assertEquals(expectedResult, nor(firstArgumentValueOne, secondArgumentValueOne))
        assertEquals(expectedResult, nor(firstArgumentHigherThanOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, nor(firstArgumentValueOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, nor(firstArgumentHigherThanOne, secondArgumentValueOne))
    }

    @Test
    fun xnor_checkResultIfFirstAndSecondParamArgumentIsNegativeNumber_returnOne() {
        val expectedResult = 1
        val firstArgument = -3
        val secondArgument = -6

        assertEquals(expectedResult, xnor(firstArgument, secondArgument))
    }

    @Test
    fun xnor_checkResultIfFirstArgumentIsEqualOrHigherThanOneAndSecondParamArgumentIsNegativeNumber_returnZero() {
        val expectedResult = 0

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 100

        val secondArgument = -6

        assertEquals(expectedResult, xnor(firstArgumentValueOne, secondArgument))
        assertEquals(expectedResult, xnor(firstArgumentHigherThanOne, secondArgument))
    }

    @Test
    fun xnor_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsZero_returnOne() {
        val expectedResult = 1
        val firstArgument = -941
        val secondArgument = 0

        assertEquals(expectedResult, xnor(firstArgument, secondArgument))
    }

    @Test
    fun xnor_checkResultIfFirstArgumentIsNegativeNumberAndSecondParamArgumentIsPositiveNumberAndHigherThanZero_returnZero() {
        val expectedResult = 0

        val firstArgument = -41

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 82

        assertEquals(expectedResult, xnor(firstArgument, secondArgumentValueOne))
        assertEquals(expectedResult, xnor(firstArgument, secondArgumentHigherThanOne))
    }

    @Test
    fun xnor_checkResultIfFirstAndSecondParamArgumentIsZeroNumber_returnOne() {
        val expectedResult = 1
        val firstArgument = 0
        val secondArgument = 0

        assertEquals(expectedResult, xnor(firstArgument, secondArgument))
    }

    @Test
    fun xnor_checkResultIfFirstAndSecondParamArgumentNumberIsEqualOrHigherThanOne_returnOne() {
        val expectedResult = 1

        val firstArgumentValueOne = 1
        val firstArgumentHigherThanOne = 28

        val secondArgumentValueOne = 1
        val secondArgumentHigherThanOne = 21

        assertEquals(expectedResult, xnor(firstArgumentValueOne, secondArgumentValueOne))
        assertEquals(expectedResult, xnor(firstArgumentHigherThanOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, xnor(firstArgumentValueOne, secondArgumentHigherThanOne))
        assertEquals(expectedResult, xnor(firstArgumentHigherThanOne, secondArgumentValueOne))
    }
}

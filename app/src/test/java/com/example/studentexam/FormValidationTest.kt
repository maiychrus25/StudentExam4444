package com.example.studentexam

import org.junit.Assert.*
import org.junit.Test

class FormValidationTest {
    @Test fun phoneKeepsLeadingZerosAndAcceptsAnyDigitLength() {
        assertTrue(FormValidation.isPhoneNumeric("0912345678"))
        assertTrue(FormValidation.isPhoneNumeric("0"))
        assertTrue(FormValidation.isPhoneNumeric("9".repeat(1000)))
    }
    @Test fun phoneRejectsEmptyLettersSignsSpacesAndNonAsciiDigits() {
        for (input in listOf("", " ", "09a", "+8490", "09 12", "09\n", "１２３", "١٢٣")) {
            assertFalse(input, FormValidation.isPhoneNumeric(input))
        }
    }
    @Test fun requiredFieldsRejectWhitespaceButAcceptVietnamese() {
        assertTrue(FormValidation.isMissing(""))
        assertTrue(FormValidation.isMissing(" \t\n"))
        assertFalse(FormValidation.isMissing("Nguyễn Văn An"))
        assertFalse(FormValidation.isMissing("CNTT K43"))
    }
}

package com.example.studentexam

// Câu 3: giữ quy tắc độc lập với giao diện để kiểm thử cả chuỗi dán từ clipboard.
object FormValidation {
    fun isMissing(value: String): Boolean = value.isBlank()

    // SĐT là String: giữ số 0 đầu, không tràn Int; đề chỉ yêu cầu chữ số 0–9.
    fun isPhoneNumeric(value: String): Boolean =
        value.isNotEmpty() && value.all { it in '0'..'9' }
}

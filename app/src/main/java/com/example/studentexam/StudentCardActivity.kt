package com.example.studentexam

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class StudentCardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_card)
        // Câu 1: thẻ giống đề; chỉ tên có ô nhập tương ứng trong form.
        val fullName = intent.getStringExtra(EXTRA_NAME).orEmpty()
        findViewById<TextView>(R.id.studentName).text =
            fullName.ifBlank { getString(R.string.student_name_sample) }
        // Mã SV/lớp/trạng thái là dữ liệu mẫu. Ngành học không đồng nghĩa lớp.
        findViewById<ImageButton>(R.id.editButton).setOnClickListener {
            finish() // Về Activity form đang có, không tạo form mới và không mất dữ liệu.
        }
    }

    companion object {
        const val EXTRA_NAME = "student_name"
        const val EXTRA_EMAIL = "student_email"
        const val EXTRA_PHONE = "student_phone"
        const val EXTRA_MAJOR = "student_major"
        const val EXTRA_GENDER = "student_gender"
    }
}

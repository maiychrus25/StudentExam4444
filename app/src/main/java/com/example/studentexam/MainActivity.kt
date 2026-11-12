package com.example.studentexam

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var fullNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var majorInput: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var phoneError: TextView

    // Câu 3c (2 điểm): năm biến lưu thông tin đã lấy sau khi bấm Gửi thành công.
    var fullNameValue = ""; private set
    var emailValue = ""; private set
    var phoneValue = ""; private set
    var majorValue = ""; private set
    var genderValue = ""; private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Câu 1–2: nạp XML trước, sau đó mới tìm các View thông qua ID.
        setContentView(R.layout.activity_main)
        fullNameInput = findViewById(R.id.fullNameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        majorInput = findViewById(R.id.majorInput)
        genderGroup = findViewById(R.id.genderGroup)
        phoneError = findViewById(R.id.phoneError)
        if (Build.VERSION.SDK_INT >= 28) findViewById<View>(R.id.formTitle).isAccessibilityHeading = true

        for (input in listOf(fullNameInput, emailInput, phoneInput, majorInput)) {
            input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
                override fun afterTextChanged(s: Editable?) {
                    if (!FormValidation.isMissing(s?.toString().orEmpty())) input.error = null
                    // Câu 3a (2 điểm): cập nhật TextView cuối màn hình sau mỗi lần nhập.
                    if (input === phoneInput) updatePhoneMessage()
                }
            })
        }
        genderGroup.setOnCheckedChangeListener { _, _ ->
            findViewById<View>(R.id.genderError).visibility = View.GONE
        }
        findViewById<Button>(R.id.sendButton).setOnClickListener { submitInfo() }
        majorInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submitInfo()
                true
            } else false
        }

        // Bundle giữ năm biến qua xoay màn hình; đây không phải lưu vĩnh viễn.
        fullNameValue = savedInstanceState?.getString(StudentCardActivity.EXTRA_NAME).orEmpty()
        emailValue = savedInstanceState?.getString(StudentCardActivity.EXTRA_EMAIL).orEmpty()
        phoneValue = savedInstanceState?.getString(StudentCardActivity.EXTRA_PHONE).orEmpty()
        majorValue = savedInstanceState?.getString(StudentCardActivity.EXTRA_MAJOR).orEmpty()
        genderValue = savedInstanceState?.getString(StudentCardActivity.EXTRA_GENDER).orEmpty()
    }

    private fun updatePhoneMessage() {
        val phone = phoneInput.text.toString()
        phoneError.text = if (phone.isNotEmpty() && !FormValidation.isPhoneNumeric(phone)) {
            getString(R.string.phone_invalid)
        } else ""
    }

    private fun submitInfo() {
        var valid = true
        var firstInvalid: View? = null
        // Câu 3b (2 điểm): isBlank bắt được cả chuỗi toàn dấu cách, không chỉ "".
        for (input in listOf(fullNameInput, emailInput, phoneInput, majorInput)) {
            if (FormValidation.isMissing(input.text.toString())) {
                input.error = getString(R.string.required_field)
                valid = false
                if (firstInvalid == null) firstInvalid = input
            } else input.error = null
        }
        val phone = phoneInput.text.toString()
        val invalidPhone = phone.isNotEmpty() && !FormValidation.isPhoneNumeric(phone)
        if (invalidPhone) {
            phoneInput.error = getString(R.string.phone_invalid)
            valid = false
            if (firstInvalid == null) firstInvalid = phoneInput
        }
        val selectedId = genderGroup.checkedRadioButtonId
        val missingGender = selectedId == -1
        findViewById<View>(R.id.genderError).visibility = if (missingGender) View.VISIBLE else View.GONE
        if (missingGender) {
            valid = false
            if (firstInvalid == null) firstInvalid = findViewById(R.id.maleOption)
        }
        phoneError.text = when {
            invalidPhone -> getString(R.string.phone_invalid)
            !valid -> getString(R.string.required_summary)
            else -> ""
        }
        if (!valid) {
            firstInvalid?.requestFocus()
            return // Có lỗi thì dừng, không gán dữ liệu hoặc chuyển màn hình.
        }

        fullNameValue = fullNameInput.text.toString().trim()
        emailValue = emailInput.text.toString().trim()
        phoneValue = phone // Không chuyển Int: "0912" phải vẫn là "0912".
        majorValue = majorInput.text.toString().trim()
        genderValue = getString(if (selectedId == R.id.maleOption) R.string.male else R.string.female)

        // Nối hai giao diện: truyền đủ năm giá trị bằng Intent tường minh.
        val intent = Intent(this, StudentCardActivity::class.java).apply {
            putExtra(StudentCardActivity.EXTRA_NAME, fullNameValue)
            putExtra(StudentCardActivity.EXTRA_EMAIL, emailValue)
            putExtra(StudentCardActivity.EXTRA_PHONE, phoneValue)
            putExtra(StudentCardActivity.EXTRA_MAJOR, majorValue)
            putExtra(StudentCardActivity.EXTRA_GENDER, genderValue)
        }
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(StudentCardActivity.EXTRA_NAME, fullNameValue)
        outState.putString(StudentCardActivity.EXTRA_EMAIL, emailValue)
        outState.putString(StudentCardActivity.EXTRA_PHONE, phoneValue)
        outState.putString(StudentCardActivity.EXTRA_MAJOR, majorValue)
        outState.putString(StudentCardActivity.EXTRA_GENDER, genderValue)
        super.onSaveInstanceState(outState) // EditText/RadioGroup có ID tự lưu trạng thái View.
    }
}

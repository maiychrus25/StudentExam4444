# HỌC LIỆU BÀI KIỂM TRA 44844

Kotlin + XML Views truyền thống · Môn Lập trình cho thiết bị di động · Thực hành50 phút.

Tài liệu này giải thích đúng code trong dự án StudentExam44844. Các chú thích
`Câu1`, `Câu2`, `Câu3a/b/c` trong Kotlin/XML giúp anh tìm phần đáp ứng từng điểm.
Ảnh đề nằm trong `docs/reference/exam44844.png`. PDF cùng thư mục để anh đọc/in.

## 1. Phân tích đề và thang điểm

| Câu | Điểm | Yêu cầu | File thực hiện |
| --- | --- | --- | --- |
| 1 | 2 | Thiết kế hai giao diện như ảnh | activity_main.xml, activity_student_card.xml |
| 2 | 2 | Giải thích bố cục và các thành phần | Mục2–5 của học liệu này, chú thích XML |
| 3a | 2 | TextView cuối màn hình báo SĐT không phải số mỗi khi nhập | MainActivity.updatePhoneMessage, TextWatcher |
| 3b | 2 | Báo khi các ô nhập bị bỏ trống | MainActivity.submitInfo, FormValidation.isMissing |
| 3c | 2 | Bấm Gửi, lấy dữ liệu nhập/chọn và lưu vào biến | Năm property Value trong MainActivity |

Hình phóng to thẻ trong đề là chi tiết của màn hình2, không phải màn hình3.
Form có họ tên, email, điện thoại, ngành học và chọn giới tính. Mã SV/lớp/trạng
thái trên thẻ không có ô nhập tương ứng, nên dự án giữ mẫu123456789/CNTT K43/Đang học.
Không lấy ngành học để gán vào lớp: hai khái niệm này khác nhau.

## 2. Mở và chạy dự án

Android Studio → File → Open → chọn thư mục StudentExam44844. Chờ Gradle Sync,
chọn cấu hình app, máy ảo API24 trở lên, bấm Run. Không mở riêng thư mục app.
Nếu cần chọn JDK, dùng JDK17; bộ Gradle của dự án đã chỉ định toolchain17.

Luồng chạy: nhập form → chọn Nam/Nữ → bấm Gửi → xem thẻ → bấm bút để về sửa.
Tên trên thẻ lấy từ form. Bốn giá trị còn lại đã được lưu vào biến và truyền
Intent, dù mẫu thẻ chỉ trình bày tên cùng mã/lớp/trạng thái cố định.

## 3. Câu1 — Xây dựng màn hình form

File: `app/src/main/res/layout/activity_main.xml`.

FrameLayout ngoài nhận vùng an toàn hệ thống; LinearLayout bên trong giữ padding16dp
và sắp xếp con theo chiều dọc: tiêu đề, ScrollView, nút Gửi và
TextView phoneError ở cuối. Tiêu đề dùng TextView chữ đậm, xanh teal. ScrollView
có height0dp và weight1 nên nhận phần chiều cao còn lại; form cuộn khi màn hình
nhỏ hoặc bàn phím mở. ScrollView chỉ có một con trực tiếp là FrameLayout.

FrameLayout căn giữa khối formPanel, còn formPanel là LinearLayout dọc nền xanh
nhạt. Mỗi trường có nhãn TextView và ô EditText với hint. Bốn ID quan trọng là
fullNameInput, emailInput, phoneInput, majorInput. RadioGroup chứa maleOption và
femaleOption; trong cùng nhóm chỉ chọn được một RadioButton.

Nút sendButton nằm ngoài vùng cuộn để dễ bấm. TextView phoneError nằm sau nút,
minHeight24dp giữ chỗ khi chưa có lỗi; accessibilityLiveRegion=polite giúp đọc
thông báo thay đổi qua công cụ trợ năng. genderError chỉ hiện nếu chưa chọn giới tính.

Ảnh đề có khoảng trống lớn; dự án dùng trọng số và vùng cuộn thay vì cố định
tọa độ pixel. Nhờ vậy vẫn thao tác được ở màn hình rộng, chữ lớn và khi mở bàn phím.

## 4. Câu1 — Xây dựng thẻ sinh viên

File: `app/src/main/res/layout/activity_student_card.xml`.

Một LinearLayout ngang tạo thẻ bo góc, elevation2dp. ImageView64dp bên trái dùng
icon vector ic_student. Ở giữa là cột TextView có width0dp + weight1 để chiếm
phần còn lại. Tên in đậm; mã SV/lớp màu phụ; trạng thái xanh. ImageButton48dp
bên phải dùng ic_edit và contentDescription rõ nghĩa để công cụ trợ năng đọc được.

Màn hình rộng dùng form480dp/thẻ600dp, màn hình hẹp dùng match_parent. Các giá
trị này đặt tại values/dimens.xml và values-w600dp/dimens.xml. Nền, màu chữ,
lỗi và trạng thái có cặp light/night; icon ảnh trang trí không được đọc lặp lại.

## 5. Câu2 — Giải thích các thành phần và thuộc tính

| Thành phần / thuộc tính | Ý nghĩa trong bài |
| --- | --- |
| Activity | Đối tượng quản lý một màn hình và vòng đời của nó |
| onCreate | Android gọi khi tạo Activity; nạp XML và gắn sự kiện ở đây |
| setContentView | Chuyển XML layout thành cây View của Activity |
| findViewById | Tìm View qua ID đã khai báo trong XML |
| R.layout / R.id | Mã tài nguyên sinh tự động; không tự viết file R |
| EditText.text.toString() | Lấy chuỗi người dùng nhập; text bản thân là Editable |
| TextWatcher | Nhận callback khi nội dung ô nhập thay đổi |
| Button.setOnClickListener | Đăng ký phần code chạy khi bấm nút |
| RadioGroup.checkedRadioButtonId | ID nút đã chọn; bằng -1 nếu chưa chọn |
| padding / margin | Khoảng cách bên trong / bên ngoài View |
| match_parent / wrap_content | Chiếm vùng cha / vừa với nội dung |
| dp / sp | Đơn vị kích thước và khoảng cách / cỡ chữ theo tùy chọn người dùng |
| layout_weight | Chia phần không gian còn lại trong LinearLayout |
| inputType | Gợi ý kiểu nhập/bàn phím, không thay thế kiểm tra logic |
| Intent / putExtra | Mở Activity đích và gửi dữ liệu qua key |
| Bundle | Lưu trạng thái để khôi phục khi Activity được tạo lại |

Ngắn gọn khi vấn đáp: “XML tạo giao diện, Kotlin đọc/kiểm tra dữ liệu và xử lý
sự kiện. Em nạp XML trước rồi tìm View theo ID. EditText để nhập, RadioGroup để
chọn một giới tính, TextView để hiển thị, Button để gửi, ImageView để vẽ avatar.”

## 6. Câu3a — Báo SĐT không phải số ngay khi nhập

File: `FormValidation.kt`, `MainActivity.kt`.

```kotlin
fun isPhoneNumeric(value: String): Boolean =
    value.isNotEmpty() && value.all { it in '0'..'9' }
```

Điện thoại phải là String để giữ số0 đầu và không tràn Int. isNotEmpty loại
chuỗi rỗng, vì all trên chuỗi rỗng trả true. all duyệt từng ký tự; chỉ0–9 hợp lệ.
Đề không yêu cầu đúng10 số, đầu số hay kiểm tra tồn tại thật, nên không tự thêm luật.

TextWatcher có3 hàm: beforeTextChanged trước đổi; onTextChanged lúc đổi;
afterTextChanged sau đổi. Code dùng afterTextChanged để gọi updatePhoneMessage
khi chính phoneInput thay đổi. Hai callback không dùng trả Unit.

```kotlin
val phone = phoneInput.text.toString()
phoneError.text = if (phone.isNotEmpty() &&
    !FormValidation.isPhoneNumeric(phone)) {
    getString(R.string.phone_invalid)
} else ""
```

Ví dụ nhập09a → hiện lỗi; sửa0912 → xóa lỗi; xóa hết → lỗi định dạng tạm ẩn,
nhưng khi bấm Gửi sẽ báo bỏ trống. Không gọi setText lên phoneInput trong watcher,
tránh kích hoạt lại chính listener. Ô SĐT dùng kiểu nhập text có chủ đích để anh
thử được chữ sai; đây là lý do local lint TextFields được ghi chú tại ô này.

## 7. Câu3b — Kiểm tra bỏ trống

```kotlin
fun isMissing(value: String): Boolean = value.isBlank()
```

isEmpty chỉ đúng với chuỗi không có ký tự. isBlank còn đúng nếu toàn dấu cách,
tab hay xuống dòng. Vì vậy người dùng không thể nhập dấu cách để qua kiểm tra.

submitInfo duyệt cả4 EditText. Nếu thiếu thì gán input.error, đánh dấu valid=false
và nhớ ô sai đầu tiên để requestFocus. Nếu đủ thì xóa lỗi cũ. Code không return
ngay trong vòng lặp để tất cả ô thiếu đều được đánh dấu trong cùng lần bấm.

Sau đó kiểm tra lại SĐT để không thể gửi ký tự sai dù nhập từ clipboard. Kiểm tra
checkedRadioButtonId=-1 để báo chưa chọn giới tính. Dự án quy ước giới tính bắt buộc;
đề nêu rõ ô nhập bắt buộc, còn đây là lựa chọn triển khai để dữ liệu được đầy đủ.

Nếu valid=false thì cập nhật TextView cuối, focus lỗi đầu và return. Lúc này chưa
gán năm biến Value, chưa mở thẻ. Không chỉ báo lỗi rồi vẫn chạy phần “thành công”.

## 8. Câu3c — Đọc và lưu năm giá trị

```kotlin
fullNameValue = fullNameInput.text.toString().trim()
emailValue = emailInput.text.toString().trim()
phoneValue = phone
majorValue = majorInput.text.toString().trim()
genderValue = getString(
    if (selectedId == R.id.maleOption) R.string.male
    else R.string.female
)
```

Đây là5 property var của MainActivity, được khai báo ngoài onCreate để dùng trong
các hàm khác. private set chỉ cho code trong lớp gán, bên ngoài có thể đọc để kiểm thử.
trim bỏ khoảng trắng hai đầu của tên/email/ngành; vẫn giữ khoảng trắng giữa tên.
SĐT không trim vì quy ước chỉ cho0–9: dấu cách là dữ liệu không hợp lệ.

Biến trong bộ nhớ không phải database. Dự án lưu thêm5 giá trị vào Bundle trong
onSaveInstanceState, cùng state View tự lưu theo ID, để xoay màn hình không mất.
Không có lưu vĩnh viễn hay gửi dữ liệu qua mạng vì đề không yêu cầu.

## 9. Nối hai màn hình và nút bút

Intent(this, StudentCardActivity::class.java) chỉ rõ Activity đích. putExtra gửi
đủ5 giá trị với các hằng EXTRA_NAME/EMAIL/PHONE/MAJOR/GENDER. StudentCardActivity
đọc tên bằng getStringExtra và hiển thị studentName. Tên rỗng có fallback theo mẫu.
Các giá trị còn lại vẫn có trong Intent, không bị biến thành mã SV/lớp.

Nút bút gọi finish để đóng thẻ, quay về form đang có; không tạo MainActivity mới.
Do đó dữ liệu cũ vẫn sửa được. Đây là phần nối luồng thêm để hai giao diện dễ chạy
và trình bày, không phải lý do tự thêm chức năng mạng hay quản lý nhiều sinh viên.

## 10. Đọc nhanh cú pháp Kotlin

lateinit var: khai báo View sẽ được gán trong onCreate; dùng trước khi gán sẽ lỗi.
override: cài phương thức kế thừa. super.onCreate: chạy xử lý lớp cha.
?. gọi an toàn khi có thể null; orEmpty đổi null thành chuỗi rỗng.
=== so sánh đúng cùng đối tượng View; ! phủ định; return thoát hàm.
apply { ... } cấu hình Intent; this trong apply là Intent đó.
companion object chứa hằng key dùng chung giữa hai Activity.

## 11. Tự kiểm tra và trình bày trong50 phút

| Tình huống | Kết quả |
| --- | --- |
| Nhập0912345678 | Không lỗi, giữ số0 đầu |
| Nhập09a, +8490, 09 12 | TextView cuối báo chỉ được0–9 |
| Sửa ký tự sai thành số | Lỗi định dạng biến mất |
| Xóa từng ô rồi Gửi | Ô thiếu báo lỗi; không mở thẻ |
| Nhập toàn dấu cách | Không qua kiểm tra bắt buộc |
| Không chọn giới tính | Hiện lỗi lựa chọn |
| Dữ liệu hợp lệ, chọn Nam/Nữ | Năm biến nhận đúng dữ liệu, thẻ hiện tên |
| Bấm bút và sửa tên | Form cũ còn dữ liệu, gửi lại hiện tên mới |
| Xoay màn hình | Input/lựa chọn/năm biến đã lấy vẫn được giữ |
| Màn hình nhỏ/chữ lớn/bàn phím | Form cuộn được, nút và thông báo tiếp cận được |

Gợi ý chia thời gian:5 phút phân tích;15 phút XML form/thẻ;15 phút watcher và
submit;10 phút chạy/sửa lỗi;5 phút ôn giải thích từng thành phần. Ưu tiên yêu cầu
đúng thang điểm trước khi chỉnh màu/bóng chi tiết.

Mẫu trình bày: “Câu1 em dùng hai layout, form dọc có vùng cuộn và thẻ ngang có
ảnh/thông tin/nút bút. Câu2 em giải thích từng View và ID. Câu3 em dùng TextWatcher
để báo lỗi realtime, isBlank để chặn bỏ trống, rồi đọc text và RadioGroup vào5 biến.
Nếu có lỗi em return, nếu hợp lệ em mới mở màn hình thẻ.”

## 12. Các file cần đọc và giới hạn

app/src/main/java/com/example/studentexam/MainActivity.kt: luồng chính, biến và sự kiện.
FormValidation.kt: hai quy tắc nhập liệu. StudentCardActivity.kt: nhận tên và về sửa.
res/layout: hai giao diện. res/values và values-night: chữ, màu, theme, kích thước.
AndroidManifest.xml: khai báo launcher và Activity thẻ nội bộ.
app/src/test và androidTest: kiểm tra biên, giao diện và truyền dữ liệu.

Không kiểm tra email phải có@, SĐT phải10 số hay thêm nút chỉnh sửa mã SV vì đề
không yêu cầu. Nếu giáo viên bổ sung điều kiện, sửa quy tắc và học liệu cùng nhau.
Xem kết quả test thực tế trong SYSTEM_DOCUMENTATION.md; không hiểu “build được”
là đã kiểm tra mọi điện thoại Android.

## 13. Nguồn tra cứu chính thức

https://developer.android.com/develop/ui/views/layout/declaring-layout
https://developer.android.com/reference/android/text/TextWatcher
https://developer.android.com/reference/android/widget/RadioGroup
https://developer.android.com/guide/components/activities/activity-lifecycle
https://kotlinlang.org/docs/properties.html

Tài liệu diễn giải cho bài của anh, không chép nguyên văn nguồn. Chú thích trong
code và học liệu mô tả cùng một phiên bản triển khai trong folder này.

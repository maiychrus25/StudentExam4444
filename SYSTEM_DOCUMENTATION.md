# StudentExam44844

Phiên bản: 1.0

<!-- docs-check: exam44844-2026-11-10 -->

Native Kotlin Activity + XML Views, package com.example.studentexam. Source đề
trong docs/reference/exam44844.png. MainActivity là launcher; StudentCardActivity
không exported. Hai layout dựng form và một thẻ đúng ảnh đề.

## Quy tắc và dữ liệu

Form gồm tên/email/SĐT/ngành học, RadioGroup Nam/Nữ, nút Gửi và TextView phoneError
ở cuối. Tên/email/ngành được trim khi gửi. Tất cả bốn trường và giới tính bắt buộc
(giới tính là mặc định triển khai). Điện thoại được giữ String, chỉ ASCII0–9,
không tự kiểm tra số chữ số, đầu số hoặc định dạng email ngoài đề. TextWatcher
cập nhật lỗi realtime và submit kiểm tra lại; inputType text cho phép thử ký tự sai.

Khi hợp lệ, lưu fullNameValue/emailValue/phoneValue/majorValue/genderValue trong
Activity, truyền năm extras qua Intent tường minh tới thẻ. Mã SV/lớp/trạng thái
dùng mẫu trong strings.xml, không lấy ngành học thay lớp. Pencil finish thẻ để về
form cũ. EditText/RadioGroup lưu state qua ID; năm biến lưu thêm vào Bundle để
khôi phục khi tạo lại Activity. Không lưu trên đĩa, không mạng, không log thông tin.

## Build và verification

Kotlin2.2.10/AGP8.13.2/Gradle8.14.6/JDK17/SDK36/min24 tái dùng bộ công cụ bài cũ.
Runtime chỉ kotlin-stdlib; JUnit/Espresso/AndroidX Test là test-only. Lint strict,
chỉ bỏ advisory OldTargetApi vì giữSDK36 đúng mức AGP8.13 hỗ trợ. XML bỏ TextFields
riêng ô điện thoại để thử ký tự sai và UselessParent riêng các wrapper có nhiệm vụ
căn giữa/giữ padding khi nhận inset. Không bỏ kiểm tra tính đúng hay khả năng tiếp cận.

Kết quả kiểm tra ngày10/11/2026 trên máy ảo API37 riêng của bài:

| Vòng | Kích thước logic | Theme / cỡ chữ | Test giao diện |
| --- | --- | --- | --- |
| 1 | Điện thoại360×640dp | Sáng /1.0 | 5/5 |
| 1 | Màn hình lớn1600×1000dp | Tối /1.0 | 5/5 |
| 2 | Điện thoại360×640dp | Tối /1.4 | 5/5 |
| 2 | Màn hình lớn1600×1000dp | Sáng /1.4 | 5/5 |

make build: Build succeeded. make test:3 unit tests và20 lượt device tests ở bốn
cấu hình đều qua, không lỗi/bỏ qua. make lint:0 issues; docs-check passed.
Đã kiểm tra SĐT có số0 đầu/chữ/dấu/khoảng trắng/chữ số ngoài ASCII; từng ô rỗng;
Nam/Nữ; năm biến và Intent; xoay Activity; bút sửa/quay lại/gửi lại. Mỗi cấu hình
còn chạy kịch bản chạm/nhập qua ADB rồi mở thẻ, chụp form/thẻ.
Ảnh đã đối chiếu: không tràn ngang/chồng chữ, phần form cuộn khi chữ lớn; nút
Gửi và thông báo cuối vẫn tiếp cận được. Màn hình lớn là cấu hình Android giả lập,
không phải bản ứng dụng dành riêng cho hệ điều hành desktop.

Học liệu PDF17 trang được kiểm tra nội dung tiếng Việt, đủ Câu1–3 và phụ lục ba
file Kotlin thực tế; đã xem bản render. Bằng chứng test/ảnh nằm trong thư mục
build/verification/exam44844 (artifact cục bộ, không nằm trong mã nguồn ZIP).
Đã rà soát mã mới và đối chiếu SHA256 nguồn/config AndroidAssignments, Lab4
với trước khi làm: không đổi. Chưa chạy trên máy thật hoặc kiểm thử API24–36.
Sự cố SystemUI của emulator ở lần khởi động đầu đã được xử lý bằng khởi động lại
đúng máy ảo kiểm tra; kết quả trên là các lần chạy lại thành công.

Không push, deploy hay chỉnh các project anh đã có. Học liệu và chú thích mô tả
bản triển khai thực tế trong folder này. Superpowers/checklist tạo file/template
AGENTS không có ở vị trí cài đặt; giới hạn này được ghi trong AGENTS.md.

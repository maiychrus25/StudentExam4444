# StudentExam44844 — Kotlin + XML

Bài kiểm tra 44844: hai màn hình Thông tin sinh viên và thẻ sinh viên.

1. Android Studio → File → Open → mở thư mục StudentExam44844.
2. Chờ Gradle Sync; chọn app, máy ảo API24+ và Run.
3. Nhập bốn trường và chọn giới tính, bấm Gửi; bấm bút trên thẻ để về sửa.

File học liệu: **docs/LEARNING_GUIDE.md** và **docs/StudentExam44844Learning.pdf**.
Các file Kotlin/XML có chú thích theo Câu1–3 của đề. Học theo thứ tự:
activity_main.xml → MainActivity.kt → FormValidation.kt → StudentCardActivity.kt.

- Mọi ô nhập và giới tính đều bắt buộc; không ép email theo định dạng ngoài đề.
- SĐT chỉ chứa0–9, giữ số0 đầu, không giới hạn độ dài theo đề.
- Mã SV123456789/lớp CNTT K43/trạng thái Đang học là dữ liệu mẫu của thẻ.
- Biến/Bundles giữ dữ liệu trong phiên, không có database hay gửi lên máy chủ.

Toolchain dùng lại: Kotlin2.2.10, AGP8.13.2, Gradle8.14.6, JDK17, SDK36, min24.
Không có thư viện UI runtime; chỉ Kotlin stdlib. SDK máy này nằm trong ignored
local.properties; máy khác để Android Studio tạo theo SDK cục bộ.

Kiểm tra: make build, make test, make lint, node scripts/check-docs.js.
make test cần máy ảo; chọn bằng ANDROID_SERIAL nếu có nhiều thiết bị. Node.js cần
nằm trong PATH cho docs-check. APK: app/build/outputs/apk/debug/app-debug.apk.

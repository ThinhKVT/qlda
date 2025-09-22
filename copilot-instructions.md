# Hướng dẫn sử dụng Copilot cho dự án QLDA

## Giới thiệu dự án
Dự án QLDA là hệ thống quản lý dự án, bao gồm các chức năng quản lý dự án, gói thầu, nhân sự, tài liệu, tiến độ, quyết toán, nhật ký,...

## Quy tắc lập trình
- Sử dụng Java (Spring Boot nếu có backend) và HTML/CSS/JS cho frontend.
- Đặt tên biến, hàm, class rõ ràng, nhất quán.
- Comment bằng tiếng Việt khi cần thiết.
- Tuân thủ clean code và chuẩn code Java/HTML/CSS.

## Quy tắc UI/UX
- Giao diện theo đúng file SVG mẫu (uiux_project_detail.svg, uiux_package_list.svg, ...).
- Sử dụng thanh cuộn (scrollbar) khi dữ liệu vượt quá kích thước màn hình.
- Các tab: Tiến độ, Gói thầu, Nhân sự, Tài liệu, Quyết toán, Nhật ký.
- Các button/tab sử dụng thuộc tính `data-toggle="tab"`.
- Màu sắc, font chữ, spacing theo đúng thiết kế.

## Yêu cầu đặc biệt
- Luôn trả lời, comment, hướng dẫn bằng tiếng Việt.
- Khi có nhiều bản ghi, phải có thanh cuộn dọc.
- Các trường dropdown, textbox, file upload, ... phải đúng loại control như mô tả.

## Ví dụ đặt tên biến/class
```java
// Tên class
public class DuAnDetailController { ... }

// Tên biến
String tenDuAn;
int tongGiaTriHopDong;
```

## Quy trình phát triển
1. Đọc kỹ yêu cầu nghiệp vụ trong file `Yêu cầu nghiệp vụ.md`.
2. Tham khảo thiết kế giao diện SVG tương ứng.
3. Viết code theo chuẩn, kiểm tra kỹ trước khi commit.
4. Đảm bảo giao diện và chức năng đúng như mô tả.

---
Mọi thắc mắc hoặc góp ý, vui lòng liên hệ trưởng nhóm dự án.

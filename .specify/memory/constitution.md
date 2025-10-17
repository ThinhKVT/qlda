<!--
Báo cáo đồng bộ
Thay đổi phiên bản: (none → 1.0.0)
Các nguyên tắc đã chuyển sang tiếng Việt, bổ sung các mục: Yêu cầu bảo mật, Quy trình phát triển
Không xóa mục nào
Các template liên quan: plan-template.md, spec-template.md, tasks-template.md đều đã tương thích
Trạng thái: Đã phê duyệt ngày 2025-10-14
-->

# Hiến pháp QLDA

## Nguyên tắc cốt lõi

### Ưu tiên bảo mật
Tất cả các tính năng phải đảm bảo xác thực và phân quyền. Dữ liệu nhạy cảm phải được bảo vệ mọi lúc. Tuân thủ các tiêu chuẩn bảo mật ngành là bắt buộc.

### Phát triển kiểm thử trước
Mọi mã nguồn phải được kiểm thử tự động trước khi hợp nhất. Quy trình Red-Green-Refactor được áp dụng nghiêm ngặt. Kiểm thử chấp nhận người dùng là bắt buộc với mọi tính năng hướng tới người dùng.

### Đơn giản & Rõ ràng
Tính năng phải được thiết kế rõ ràng, dễ bảo trì. Tránh phức tạp không cần thiết. Tài liệu bắt buộc cho mọi giao diện công khai.

### Toàn vẹn dữ liệu
Mọi thao tác dữ liệu phải đảm bảo tính nhất quán và tin cậy. Sử dụng giao dịch cho các cập nhật quan trọng. Kiểm tra dữ liệu là bắt buộc ở mọi điểm nhập.

### Khả năng quan sát
Mọi dịch vụ phải cung cấp log có cấu trúc và giám sát. Lỗi và chỉ số hiệu năng phải được theo dõi và báo cáo.

## Yêu cầu bảo mật
Mọi dữ liệu người dùng phải được mã hóa khi truyền và lưu trữ. Kiểm soát truy cập phải được rà soát định kỳ mỗi quý. Sự cố bảo mật phải được ghi nhận và khắc phục trong vòng 48 giờ.

## Quy trình phát triển
Mọi thay đổi đều phải được review mã nguồn. Mọi lần triển khai đều cần được phê duyệt bởi ít nhất một người kiểm duyệt. Quy trình CI/CD phải kiểm tra toàn bộ kiểm thử và bảo mật.

## Quản trị
Hiến pháp này có giá trị cao nhất, vượt trên mọi quy trình khác. Mọi sửa đổi phải có tài liệu, phê duyệt và kế hoạch chuyển đổi. Mọi pull request và review phải kiểm tra tuân thủ các nguyên tắc này. Mọi phức tạp phải được giải trình. Sử dụng README.md để tham khảo hướng dẫn phát triển thực tế.

**Phiên bản**: 1.0.0 | **Phê duyệt**: 2025-10-14 | **Sửa đổi lần cuối**: 2025-10-14

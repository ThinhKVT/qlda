# Checklist chất lượng đặc tả: Thêm, sửa, xoá dự án (project)

**Mục đích**: Đánh giá độ hoàn thiện và chất lượng đặc tả trước khi lên kế hoạch triển khai
**Ngày tạo**: 2025-10-14
**Tính năng**: [Link đến spec.md]

## Chất lượng nội dung
- [X] Không có chi tiết triển khai (ngôn ngữ, framework, API)
- [X] Tập trung vào giá trị người dùng và nhu cầu nghiệp vụ
- [X] Viết cho đối tượng không chuyên kỹ thuật
- [X] Hoàn thành tất cả các mục bắt buộc

## Độ đầy đủ yêu cầu
- [X] Không còn marker [NEEDS CLARIFICATION]
- [X] Yêu cầu kiểm thử được và rõ ràng
- [X] Tiêu chí thành công đo lường được
- [X] Tiêu chí thành công không chứa chi tiết kỹ thuật
- [X] Đầy đủ kịch bản chấp nhận
- [X] Đã xác định các trường hợp biên
- [X] Phạm vi tính năng rõ ràng
- [X] Đã nêu giả định và phụ thuộc

## Sẵn sàng triển khai
- [X] Mọi yêu cầu chức năng đều có tiêu chí chấp nhận rõ ràng
- [X] Kịch bản người dùng bao phủ luồng chính
- [X] Tính năng đáp ứng các kết quả đo lường trong tiêu chí thành công
- [X] Không có chi tiết triển khai trong đặc tả

## Test Case Checklist cho Project (CRUD, filter, logic đặc biệt, trường mới)

### CRUD
- [X] Tạo mới dự án với đầy đủ thông tin hợp lệ (bao gồm các trường mới: lead_department, lead_staff, project_manager, requirement, options, docs)
- [X] Tạo mới dự án thiếu trường bắt buộc (name, project_type, plan_year, ...)
- [X] Tạo mới dự án với các trường mới rỗng hoặc null
- [X] Tạo mới dự án với requirement là mảng lớn, options là object phức tạp
- [X] Tạo mới dự án trùng mã (project_code) trong cùng năm kế hoạch (phải gen mã mới, không trùng)
- [X] Lấy chi tiết dự án, kiểm tra đầy đủ các trường, đặc biệt các trường mới
- [X] Sửa dự án: chỉ cập nhật các trường có giá trị, bỏ qua trường null/rỗng, không cho phép sửa id/project_code
- [X] Sửa dự án: truyền id/project_code, kiểm tra backend giữ nguyên giá trị cũ
- [X] Sửa dự án: cập nhật các trường mới (lead_department, lead_staff, project_manager, requirement, options, docs)
- [X] Xoá mềm dự án, kiểm tra trường deleted

### Filter & List
- [X] Lọc danh sách dự án theo project_code
- [X] Lọc theo status (1 giá trị, nhiều giá trị)
- [X] Lọc theo start_date, end_date trong khoảng
- [X] Lọc theo lead_department, lead_staff, project_manager (exact, partial match)
- [X] Lọc theo requirement (tìm dự án có chứa yêu cầu cụ thể)
- [X] Lọc không có kết quả (giá trị không tồn tại)
- [X] Lọc với dữ liệu lớn (10.000+ dự án)

### Edge Cases
- [X] Tạo/sửa dự án với ngày bắt đầu > ngày kết thúc (invalid)
- [X] Tạo/sửa dự án với các trường mới là mảng rỗng, object rỗng
- [X] Tạo/sửa dự án với các trường có ký tự đặc biệt, unicode
- [X] Tạo/sửa dự án với dữ liệu lớn (mô tả, requirement, options)
- [X] Xoá dự án đã bị xoá mềm (không cho phép xoá lại)
- [X] Lấy chi tiết dự án không tồn tại (báo lỗi đúng)

### Special Logic
- [X] Kiểm tra gen mã tự động project_code đúng định dạng DA-{plan_year}-{STT}
- [X] Kiểm tra update chỉ các trường có giá trị, giữ nguyên trường null/rỗng
- [X] Kiểm tra các trường mới trả về đúng khi get detail/list

### Security & Permission
- [X] Chỉ admin được phép tạo/sửa/xoá dự án
- [X] User chỉ được phép xem danh sách, chi tiết

## Ghi chú
- Các mục chưa hoàn thành cần cập nhật đặc tả trước khi chuyển sang bước clarify hoặc plan

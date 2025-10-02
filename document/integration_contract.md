# Integration Contract (Units API Surface)

Lưu ý
- Đây là hợp đồng tích hợp giữa các Units. Không mô tả thiết kế nội bộ hay schema chi tiết, chỉ công bố các endpoint bề mặt phục vụ tích hợp giữa các team.
- Theo quyết định: U-PAYMENTS là module trong cùng service với U-PROJECT, nhưng giữ DB/table tách biệt. Các endpoint dưới đây vẫn là ranh giới tích hợp.

## U-IDENT (Auth)
- POST /auth/login — Đăng nhập, trả JWT + refresh
- POST /auth/refresh — Cấp token mới từ refresh token
- GET /users/me — Thông tin người dùng hiện tại (id, roles)

## U-PROJECT (Projects, Packages, Contract readonly tab)
- GET /api/projects — Danh sách dự án (hỗ trợ filter/sort/paging)
- GET /api/projects/{id} — Chi tiết dự án
- POST /api/projects — Tạo dự án
- PUT /api/projects/{id} — Cập nhật dự án
- GET /api/projects/{id}/documents — Danh sách tài liệu của dự án
- POST /api/projects/{id}/export — Xuất Excel danh sách dự án theo filter hiện hành (REP-01)
- GET /api/projects/{projectId}/packages — Danh sách gói thầu theo dự án
- POST /api/projects/{projectId}/packages — Tạo gói thầu
- GET /api/packages/{id} — Chi tiết gói thầu
- PUT /api/packages/{id} — Cập nhật gói thầu
- GET /api/packages/{id}/contract — Lấy thông tin hợp đồng (readonly tab)
- POST /api/packages/{id}/contract — Lưu hợp đồng (từ popup ký hợp đồng)

## U-PAYMENTS (Payments)
- GET /api/contracts/{contractId}/payments — Danh sách đợt thanh toán
- POST /api/contracts/{contractId}/payments — Thêm đợt (planned)
- PUT /api/payments/{paymentId} — Cập nhật đợt (planned fields)
- DELETE /api/payments/{paymentId} — Xóa đợt (chỉ khi PENDING)
- PUT /api/payments/{paymentId}/confirm — Xác nhận PENDING→PAID (bắt buộc đủ 4 trường actual)
- GET /api/payments/{paymentId}/attachments — Danh sách file hồ sơ thanh toán
- POST /api/payments/{paymentId}/attachments — Upload hồ sơ thanh toán (liên kết U-DOCS)
- DELETE /api/payments/{paymentId}/attachments/{docId} — Xóa file đính kèm

## U-DOCS (Documents & Knowledge Base)
- POST /api/documents/upload — Upload tài liệu (multipart)
- POST /api/documents/download — Tải nhiều file (zip) theo danh sách id
- GET /api/documents — Tìm kiếm tài liệu theo projectId|packageId|contractId|paymentId|type

## U-CONTRACTOR (Contractors)
- GET /api/contractors — Danh sách nhà thầu (filter/sort)
- GET /api/contractors/{id} — Chi tiết nhà thầu
- POST /api/contractors — Tạo nhà thầu

## U-CONTRACTS (Contracts Menu)
- GET /api/contracts — Danh sách hợp đồng toàn hệ thống (readonly)
- GET /api/contracts/{id} — Thông tin hợp đồng (readonly)

## U-DASHBOARD (Summary)
- GET /api/dashboard/summary — Số liệu tổng hợp (dự án, gói thầu, hợp đồng, thanh toán, tài liệu...)

## U-PLATFORM (Infra)
- N/A (không expose endpoint nghiệp vụ; OpenAPI, DB, CI cung cấp nền tảng)

Quyền truy cập (tham chiếu roles U-IDENT)
- Áp dụng kiểm tra JWT Bearer ở tất cả endpoint (trừ login/refresh).
- Phân quyền chi tiết theo vai trò sẽ được định nghĩa trong tài liệu Security riêng; giai đoạn này chỉ yêu cầu có guard cơ bản.
# U-PROJECT — Dự án, Gói thầu, Thông tin Hợp đồng (readonly)

Phạm vi
- Danh sách Dự án, Popup Tìm kiếm nâng cao, Form Thêm/Sửa Dự án.
- Chi tiết Dự án (mặc định tab Tiến độ), quản lý Gói thầu (Danh sách/Form), Chi tiết Gói thầu (tab Thông tin Hợp đồng readonly).
- Xuất Excel Danh sách Dự án (REP-01) theo filter hiện hành.

User Stories & AC

1) PROJ-01 — Danh sách Dự án
- Mô tả: Xây dựng màn hình danh sách dự án theo SVG; sort, lọc nhanh, hiển thị tổng số bản ghi; tên dự án mở chi tiết.
- SVG: ../uiux_qlda/menu_project/uiux_project_list.svg
- AC:
  - Hiển thị đúng bố cục/cột theo SVG.
  - Dòng "Hiển thị x-y/N bản ghi" phản ánh đúng phân trang.
  - Sort theo: Tên, Năm kế hoạch, Trạng thái, Ngày bắt đầu/kết thúc.
  - Lọc nhanh: Loại dự án, Trạng thái, Năm kế hoạch.
  - Tên dự án là link mở chi tiết.
  - Có nút "Thêm mới" (mở PROJ-03) và "Tìm kiếm nâng cao" (mở PROJ-02).

2) PROJ-02 — Popup Tìm kiếm nâng cao Dự án
- SVG: ../uiux_qlda/menu_project/uiux_project_advanced_search_popup.svg
- AC:
  - Trường lọc: Loại dự án, Phòng đầu mối, Năm kế hoạch, Trạng thái, Cán bộ đầu mối, Cán bộ QLDA, Ngày bắt đầu, Ngày kết thúc.
  - Áp dụng filter lên PROJ-01; có nút Xóa bộ lọc.

3) PROJ-03 — Popup Form Thêm/Sửa Dự án
- SVG: ../uiux_qlda/menu_project/uiux_project_form.svg
- AC:
  - Dropdown/lookup theo quy tắc trong tài liệu (Loại dự án, Phòng đầu mối, Cán bộ...).
  - Lưu thành công: danh sách refresh; mã dự án sinh từ backend (readonly sau lưu).

4) PROJ-04 — Chi tiết Dự án (tab "Tiến độ")
- SVG: ../uiux_qlda/menu_project/uiux_project_detail.svg
- AC:
  - Mặc định tab "Tiến độ" khi mở chi tiết.
  - Scroll được khi >10 bản ghi; không vỡ layout.
  - Nút "Chỉnh sửa" từng giai đoạn hoạt động; riêng giai đoạn "Ký hợp đồng" mở PROJ-05.

5) PROJ-05 — Popup Chỉnh sửa giai đoạn "Ký hợp đồng"
- SVG: ../uiux_qlda/menu_project/uiux_contract_edit_form.svg
- AC:
  - Trường bắt buộc: Tên HĐ, Số HĐ, Ngày ký, Ngày hiệu lực, Thời gian (tháng), Giá trị + đơn vị, Nhà thầu, Số đợt thanh toán.
  - Ngày hết hiệu lực = Ngày hiệu lực + Thời gian (tháng) (tự tính khi lưu).
  - Nhóm Tiến độ: ràng buộc chuyển trạng thái như tài liệu.
  - Lưu thành công cập nhật PROJ-08 và paymentInstallments cho PROJ-09.

6) PROJ-06 — Tab "Gói thầu" (Danh sách gói thầu của dự án)
- SVG: ../uiux_qlda/menu_project/uiux_package_list.svg
- AC:
  - Danh sách đúng bố cục; "Thêm gói thầu" mở PROJ-07; "Xem" mở chi tiết gói thầu (PROJ-08/09/10).

7) PROJ-07 — Popup Form Thêm/Sửa Gói thầu
- SVG: ../uiux_qlda/menu_project/uiux_package_form.svg
- AC:
  - Trường theo SVG; validate bắt buộc; lưu thành công → list refresh.

8) PROJ-08 — Tab "Thông tin hợp đồng" (readonly)
- SVG: ../uiux_qlda/menu_project/uiux_package_detail.svg
- AC:
  - Mapping các trường: name, number, signedDate, type, effectiveDate, durationMonths, expireDate, value + currency, bidderName, paymentInstallments.
  - Định dạng ngày dd/MM/yyyy; tiền tệ theo VND/USD.

9) REP-01 — Xuất Excel Danh sách Dự án
- AC:
  - Endpoint export trả file Excel; cột/thứ tự đúng UI; tôn trọng filter/sort hiện hành.

Phụ thuộc
- U-IDENT: JWT/roles.
- U-PLATFORM: OpenAPI, DB, CI.
- U-CONTRACTOR: Tra cứu nhà thầu khi cập nhật hợp đồng.
- U-PAYMENTS: Sử dụng paymentInstallments khi sinh đợt.


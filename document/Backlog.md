# Product Backlog (Q4/2025)

Lưu ý chung
- Đơn vị ước lượng: Story Points (SP). Gợi ý: 1–3 SP: nhỏ; 5 SP: trung bình; 8 SP: lớn.
- Độ ưu tiên: P0 (Critical), P1 (High), P2 (Normal).
- Tham chiếu SVG sử dụng đường dẫn tương đối từ thư mục `document/` → `../uiux_qlda/...`.
- Mỗi ticket liệt kê Acceptance Criteria (AC) rõ ràng để kiểm thử/handover.

---

EPIC: Dự án

Ticket: PROJ-01 — Danh sách Dự án (Project List)
- Mô tả: Xây dựng màn hình danh sách dự án theo layout SVG; hỗ trợ sort, lọc nhanh, hiển thị tổng số bản ghi; tên dự án là link mở chi tiết.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_project_list.svg
- AC:
  - Hiển thị đầy đủ cột và bố cục đúng SVG.
  - Có dòng "Hiển thị x-y/N bản ghi" đúng dữ liệu phân trang.
  - Cho phép sort theo các cột chính (ít nhất: Tên, Năm KH, Trạng thái, Ngày bắt đầu/kết thúc).
  - Có lọc nhanh: Loại dự án, Trạng thái, Năm kế hoạch.
  - Tên dự án có thể bấm để mở chi tiết dự án.
  - Có nút "Thêm mới" (mở form) và "Tìm kiếm nâng cao" (mở popup).
- Ưu tiên: P0
- Ước lượng: 5 SP
- Phụ thuộc: PROJ-02, PROJ-03 (để hoàn thiện hành vi mở popup/form)

---

Ticket: PROJ-02 — Popup Tìm kiếm nâng cao Dự án
- Mô tả: Xây dựng popup tìm kiếm nâng cao với các trường lọc như thiết kế.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_project_advanced_search_popup.svg
- AC:
  - Trường lọc: Loại dự án, Phòng đầu mối, Năm kế hoạch, Trạng thái, Cán bộ đầu mối, Cán bộ QLDA, Ngày bắt đầu, Ngày kết thúc.
  - Nút Tìm kiếm áp dụng filter lên Project List.
  - Nút Xóa bộ lọc/Gỡ bỏ trả về mặc định.
  - Trạng thái filter phản ánh ở API query params.
- Ưu tiên: P1
- Ước lượng: 3 SP
- Phụ thuộc: PROJ-01

---

Ticket: PROJ-03 — Popup Form Thêm/Sửa Dự án
- Mô tả: Tạo popup form nhập liệu thêm/sửa dự án theo SVG, valid các trường, tương tác với API.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_project_form.svg
- AC:
  - Form hiển thị đầy đủ trường theo SVG, dropdown/lookup đúng quy tắc (Loại dự án, Phòng đầu mối, Cán bộ...).
  - Khi Lưu thành công, danh sách dự án tự động refresh; sinh Mã dự án do backend trả về (readonly trên UI sau khi lưu).
  - Hiển thị thông báo lỗi/validation thân thiện.
- Ưu tiên: P0
- Ước lượng: 5 SP
- Phụ thuộc: API-01, DB-01

---

Ticket: PROJ-04 — Chi tiết Dự án (tab mặc định "Tiến độ")
- Mô tả: Hiển thị màn hình chi tiết dự án, mặc định tab Tiến độ; hỗ trợ scroll khi >10 bản ghi; nút Chỉnh sửa theo từng giai đoạn.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_project_detail.svg
- AC:
  - Mở từ Project List vào chi tiết: mặc định tab "Tiến độ".
  - Khi danh sách giai đoạn >10, có scroll mượt, không vỡ layout.
  - Nút "Chỉnh sửa" tại từng dòng giai đoạn hoạt động; riêng giai đoạn "Ký hợp đồng" mở đúng popup hợp đồng (PROJ-05).
- Ưu tiên: P0
- Ước lượng: 5 SP
- Phụ thuộc: PROJ-01, PROJ-05

---

Ticket: PROJ-05 — Popup Chỉnh sửa giai đoạn "Ký hợp đồng"
- Mô tả: Form cập nhật thông tin hợp đồng và tiến độ giai đoạn ký HĐ.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_contract_edit_form.svg
- AC:
  - Trường bắt buộc: Tên hợp đồng, Số HĐ, Ngày ký, Ngày hiệu lực, Thời gian (tháng), Giá trị + đơn vị, Nhà thầu, Số đợt thanh toán.
  - Ngày hết hiệu lực tính tự động: Ngày hiệu lực + Thời gian (tháng).
  - Nhóm tiến độ: ràng buộc chuyển trạng thái như tài liệu (nhập ngày bắt đầu/kết thúc, ngày hoàn thành khi cần).
  - Lưu thành công cập nhật tab Thông tin hợp đồng (PROJ-08) và số đợt thanh toán.
- Ưu tiên: P0
- Ước lượng: 5 SP
- Phụ thuộc: API-01, PAY-API-01, DB-01

---

Ticket: PROJ-06 — Tab "Gói thầu" (Danh sách gói thầu của dự án)
- Mô tả: Hiển thị danh sách gói thầu thuộc dự án; mở form thêm; mở chi tiết gói thầu.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_package_list.svg
- AC:
  - Danh sách gói thầu đúng bố cục/cột.
  - Nút "Thêm gói thầu" mở form (PROJ-07).
  - Hành động "Xem" mở chi tiết gói thầu (PROJ-08/09/10 theo tab).
- Ưu tiên: P0
- Ước lượng: 3 SP
- Phụ thuộc: PROJ-07, PROJ-08

---

Ticket: PROJ-07 — Popup Form Thêm/Sửa Gói thầu
- Mô tả: Tạo popup form gói thầu; liên kết với dự án; validate.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_package_form.svg
- AC:
  - Form có các trường như SVG; ràng buộc required.
  - Lưu thành công → danh sách gói thầu refresh.
- Ưu tiên: P1
- Ước lượng: 3 SP
- Phụ thuộc: API-01, DB-01

---

Ticket: PROJ-08 — Tab "Thông tin hợp đồng" (Chi tiết gói thầu)
- Mô tả: Hiển thị thông tin hợp đồng ở chế độ readonly, mapping chính xác dữ liệu từ API.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_package_detail.svg
- AC:
  - Mapping đúng: name, number, signedDate, type, effectiveDate, durationMonths, expireDate, value + currency, bidderName, paymentInstallments.
  - Định dạng ngày dd/MM/yyyy; tiền tệ theo VND/USD.
- Ưu tiên: P0
- Ước lượng: 3 SP
- Phụ thuộc: PROJ-05, API-01

---

Ticket: PROJ-09 — Tab "Thanh toán" của gói thầu
- Mô tả: Quản lý đợt thanh toán theo số đợt; phân biệt dự kiến/thực tế; các hành vi thêm/sinh/chỉnh sửa; ràng buộc tổng; popup lần đầu và reopen.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_package_detail_payment.svg, ../uiux_qlda/menu_project/uiux_package_payment_update_form.svg, ../uiux_qlda/menu_project/uiux_package_payment_update_form_reopen.svg, ../uiux_qlda/menu_project/uiux_package_detail_payment_paid_state.svg
- AC:
  - "Thêm đợt" tự tăng số đợt; "Sinh theo số đợt" dựa vào paymentInstallments.
  - Chuyển PENDING→PAID yêu cầu: actualPayDate, actualAmount>0, voucherNo, ≥1 file; confirm qua modal lần đầu.
  - Re-open: cho phép sửa trường được phép, kèm audit.
  - Tổng (actual cho PAID; planned otherwise) ≤ Giá trị hợp đồng; validate khi lưu.
  - Cột hiển thị ngày/giá trị theo status (planned vs actual); tính tỷ lệ %.
- Ưu tiên: P0
- Ước lượng: 8 SP
- Phụ thuộc: PAY-API-01, DOC-API-01, LOG-01

---

Ticket: PROJ-10 — Tab "Tài liệu" của gói thầu
- Mô tả: Hiển thị danh sách tài liệu gắn với gói thầu; checkbox chọn nhiều; tải xuống nhiều file; hủy chọn.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_package_detail_documents.svg
- AC:
  - Checkbox chọn tất cả & từng dòng; nút "Tải xuống", "Huỷ chọn" dưới bảng (trái).
  - Tải zip nhiều file; tên file zip hợp lý.
  - Áp dụng phân quyền truy cập.
- Ưu tiên: P1
- Ước lượng: 5 SP
- Phụ thuộc: DOC-API-01, SEC-01

---

Ticket: PROJ-11 — Tab "Nhân sự" của dự án + Popup Thêm nhân sự
- Mô tả: Quản lý nhân sự dự án; thêm nhân sự qua popup.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_project_personnel.svg, ../uiux_qlda/menu_project/uiux_add_personnel_form.svg
- AC:
  - Danh sách nhân sự dự án đúng SVG; thêm nhân sự hoạt động.
  - Lookup cán bộ đúng quy tắc phòng/ID.
- Ưu tiên: P2
- Ước lượng: 3 SP
- Phụ thuộc: API-01

---

Ticket: PROJ-12 — Tab "Tài liệu" của dự án
- Mô tả: Quản lý tài liệu dự án, tương tự hành vi multi-select tải xuống.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_project_document.svg
- AC:
  - Checkbox, tải nhiều, hủy chọn giống PROJ-10.
- Ưu tiên: P1
- Ước lượng: 3 SP
- Phụ thuộc: DOC-API-01

---

Ticket: PROJ-13 — Tab "Quyết toán" + Popup Sửa/Upload
- Mô tả: Hiển thị quyết toán; popup sửa; popup upload chứng từ.
- Tham chiếu SVG: ../uiux_qlda/menu_project/uiux_project_settlement.svg, ../uiux_qlda/menu_project/uiux_project_settlement_edit_popup.svg, ../uiux_qlda/menu_project/uiux_project_settlement_upload_popup.svg
- AC:
  - Hiển thị đúng trạng thái & dữ liệu; popup hoạt động, lưu thành công.
- Ưu tiên: P2
- Ước lượng: 3 SP
- Phụ thuộc: DOC-API-01

---

EPIC: Hợp đồng (Menu riêng)

Ticket: CONT-01 — Danh sách Hợp đồng
- Mô tả: Màn hình danh sách Hợp đồng toàn hệ thống.
- Tham chiếu SVG: ../uiux_qlda/menu_hopdong/uiux_contract_list.svg
- AC:
  - Hiển thị đúng layout; filter cơ bản; hành động Xem mở popup.
- Ưu tiên: P1
- Ước lượng: 3 SP

---

Ticket: CONT-02 — Popup Xem thông tin Hợp đồng của Gói thầu
- Mô tả: Hiển thị thông tin hợp đồng ở dạng popup khi click "Xem".
- Tham chiếu SVG: ../uiux_qlda/menu_hopdong/uiux_package_contract_info.svg
- AC:
  - Mapping readonly các trường như PROJ-08; đóng/mở mượt.
- Ưu tiên: P1
- Ước lượng: 2 SP
- Phụ thuộc: API-01

---

EPIC: Nhà thầu

Ticket: BID-01 — Danh sách Nhà thầu
- Mô tả: Màn hình quản lý danh sách nhà thầu, filter, hành động.
- Tham chiếu SVG: ../uiux_qlda/menu_nhathau/uiux_contractor_list.svg
- AC:
  - Hiển thị đúng layout; filter cơ bản; hỗ trợ hành động Xem/Thêm mới.
- Ưu tiên: P1
- Ước lượng: 3 SP

---

Ticket: BID-02 — Popup Thêm Nhà thầu
- Mô tả: Form popup thêm nhà thầu mới.
- Tham chiếu SVG: ../uiux_qlda/menu_nhathau/uiux_contractor_add.svg
- AC:
  - Trường bắt buộc; lưu thành công; thông báo lỗi hợp lý.
- Ưu tiên: P1
- Ước lượng: 3 SP
- Phụ thuộc: API-01

---

Ticket: BID-03 — Popup Chi tiết Nhà thầu
- Mô tả: Xem chi tiết nhà thầu; lịch sử tham gia nếu có.
- Tham chiếu SVG: ../uiux_qlda/menu_nhathau/uiux_contractor_detail_popup.svg
- AC:
  - Hiển thị đúng thông tin; đóng/mở mượt.
- Ưu tiên: P2
- Ước lượng: 2 SP

---

EPIC: Kho tri thức

Ticket: KB-01 — Màn hình Kho tri thức
- Mô tả: Quản lý tài liệu tập trung; hỗ trợ chọn nhiều và tải xuống.
- Tham chiếu SVG: ../uiux_qlda/menu_khotrithuc/uiux_knowledge_base.svg
- AC:
  - Checkbox chọn nhiều; nút "Tải xuống" và "Huỷ chọn" dưới bảng.
- Ưu tiên: P1
- Ước lượng: 3 SP
- Phụ thuộc: DOC-API-01, SEC-01

---

Ticket: KB-02 — Popup Thêm tài liệu Kho tri thức
- Mô tả: Upload tài liệu vào kho; gắn metadata.
- Tham chiếu SVG: ../uiux_qlda/menu_khotrithuc/uiux_knowledge_base_add_document.svg
- AC:
  - Upload thành công; validate kích thước/định dạng; lưu metadata.
- Ưu tiên: P1
- Ước lượng: 3 SP
- Phụ thuộc: DOC-API-01

---

EPIC: Dashboard

Ticket: DASH-01 — Màn hình Dashboard tổng hợp
- Mô tả: Hiển thị số liệu tổng hợp (dự án, gói thầu, hợp đồng, thanh toán, tài liệu...).
- Tham chiếu SVG: ../uiux_qlda/dashboard/uiux_dashboard.svg
- AC:
  - Widget hiển thị đúng số liệu từ API; filter thời gian (nếu có); tải nhanh.
- Ưu tiên: P2
- Ước lượng: 5 SP
- Phụ thuộc: API-01, PAY-API-01, DOC-API-01

---

EPIC: Nền tảng/Backend/API

Ticket: API-01 — OpenAPI Spec (Projects/Packages/Contracts/Payments/Documents/Contractors)
- Mô tả: Soạn openapi.yaml cho các tài nguyên và sinh stub.
- AC:
  - Định nghĩa đầy đủ paths, schemas, responses; versioning; error codes chuẩn.
  - Sinh controller interface từ spec chạy compile được.
- Ưu tiên: P0
- Ước lượng: 5 SP

---

Ticket: DB-01 — ERD + Migration baseline (Flyway)
- Mô tả: Thiết kế lược đồ CSDL; tạo migration V1__baseline.sql.
- AC:
  - Bảng: projects, packages, contracts, payments, documents, contractors, users, roles, audit_logs.
  - Khóa/quan hệ đúng; index trường tìm kiếm; enum status payments.
  - Migration chạy thành công trên Postgres.
- Ưu tiên: P0
- Ước lượng: 5 SP

---

Ticket: SEC-01 — Xác thực JWT + Phân quyền theo vai trò
- Mô tả: Thiết lập auth/authorize cho API.
- AC:
  - Đăng nhập, refresh token; guard các endpoint; role-based.
- Ưu tiên: P0
- Ước lượng: 5 SP

---

Ticket: DOC-API-01 — Dịch vụ Tài liệu (upload/download nhiều file)
- Mô tả: API upload với metadata; download nhiều file dạng zip; quyền truy cập.
- AC:
  - Upload multipart; trả metadata; multi-download zip; kiểm soát quyền.
- Ưu tiên: P0
- Ước lượng: 5 SP

---

Ticket: PAY-API-01 — API Thanh toán theo đợt (planned/actual, confirm, reopen)
- Mô tả: Cung cấp endpoints CRUD/confirm/adjust cho payments; enforce ràng buộc tổng.
- AC:
  - Confirm PENDING→PAID yêu cầu đủ 4 trường; tổng ≤ giá trị HĐ.
  - Cho phép reopen edit theo chính sách; ghi audit log.
- Ưu tiên: P0
- Ước lượng: 8 SP

---

Ticket: LOG-01 — Audit Log
- Mô tả: Ghi các sự kiện quan trọng: CREATE/UPDATE/CONFIRM/ADJUST payment, upload/remove document...
- AC:
  - API ghi log; lưu userId, timestamp, action, old/new values (khi cần).
- Ưu tiên: P1
- Ước lượng: 3 SP

---

Ticket: NOTI-01 — Notification Email Service (sườn)
- Mô tả: Gửi email khi có sự kiện quan trọng (tạo mới, thay đổi trạng thái...), cấu hình SMTP.
- AC:
  - Cấu hình SMTP; service có thể gọi; mock/stub trong môi trường dev.
- Ưu tiên: P2
- Ước lượng: 3 SP

---

Ticket: CI-01 — DevOps tối thiểu (docker-compose Postgres + profile dev)
- Mô tả: docker-compose cho Postgres; profile dev; auto-migrate Flyway.
- AC:
  - docker-compose up; app dev kết nối DB; migration auto chạy.
- Ưu tiên: P0
- Ước lượng: 3 SP

---

Ticket: REP-01 — Xuất Excel danh sách Dự án
- Mô tả: API export Excel theo filter hiện tại trên Project List.
- AC:
  - Endpoint trả file Excel; cột/thứ tự đúng UI; áp dụng filter/sort hiện hành.
- Ưu tiên: P2
- Ước lượng: 3 SP

---

Phụ lục — Quy ước đặt độ ưu tiên & ước lượng
- P0: Bắt buộc để chạy demo E2E các luồng chính (Dự án, Gói thầu, Hợp đồng, Thanh toán, Tài liệu, Auth, DB, OpenAPI, DevOps).
- P1: Quan trọng, triển khai nối tiếp sau P0 (Hợp đồng menu, Nhà thầu, KB, Documents tab, Audit Log).
- P2: Hoàn thiện & tối ưu trải nghiệm (Dashboard, Settlement, Export Excel, chi tiết nhà thầu...).


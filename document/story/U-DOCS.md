# U-DOCS — Tài liệu & Kho tri thức

Phạm vi
- Dịch vụ tài liệu dùng chung: upload, multi-download zip, kiểm soát quyền; lưu trữ local giai đoạn đầu.
- Tài liệu của gói thầu & dự án; màn hình Kho tri thức.

User Stories & AC

1) DOC-API-01 — Dịch vụ Tài liệu (upload/download nhiều file)
- AC:
  - Upload multipart; lưu metadata (type, objectLink); lưu file local.
  - Download nhiều file: nhập danh sách id → zip → stream về client.
  - Kiểm soát quyền truy cập theo vai trò + liên kết đối tượng (project/package/contract/payment).

2) PROJ-10 — Tab "Tài liệu" của gói thầu
- SVG: ../uiux_qlda/menu_project/uiux_package_detail_documents.svg
- AC:
  - Checkbox chọn nhiều; nút "Tải xuống" và "Huỷ chọn" dưới bảng (trái).
  - Tải zip nhiều file; tên zip hợp lý.

3) PROJ-12 — Tab "Tài liệu" của dự án
- SVG: ../uiux_qlda/menu_project/uiux_project_document.svg
- AC:
  - Checkbox, tải nhiều, huỷ chọn tương tự PROJ-10.

4) KB-01 — Màn hình Kho tri thức
- SVG: ../uiux_qlda/menu_khotrithuc/uiux_knowledge_base.svg
- AC:
  - Hiển thị danh sách tài liệu tập trung; chọn nhiều; tải xuống.

5) KB-02 — Popup Thêm tài liệu Kho tri thức
- SVG: ../uiux_qlda/menu_khotrithuc/uiux_knowledge_base_add_document.svg
- AC:
  - Upload file + metadata; validate kích thước/định dạng; lưu local.

Phụ thuộc
- U-IDENT: JWT/roles.
- U-PLATFORM: OpenAPI, CI.
- Tích hợp U-PAYMENTS: type PAYMENT_SUPPORT cho hồ sơ thanh toán.


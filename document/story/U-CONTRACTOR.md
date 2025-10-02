# U-CONTRACTOR — Nhà thầu

Phạm vi
- Danh sách nhà thầu; popup thêm; popup chi tiết. Cung cấp tra cứu nhà thầu cho U-PROJECT khi cập nhật hợp đồng.

User Stories & AC

1) BID-01 — Danh sách Nhà thầu
- SVG: ../uiux_qlda/menu_nhathau/uiux_contractor_list.svg
- AC:
  - Hiển thị danh sách & filter cơ bản; hỗ trợ hành động Xem/Thêm mới.

2) BID-02 — Popup Thêm Nhà thầu
- SVG: ../uiux_qlda/menu_nhathau/uiux_contractor_add.svg
- AC:
  - Trường bắt buộc; lưu thành công; thông báo lỗi hợp lý.

3) BID-03 — Popup Chi tiết Nhà thầu
- SVG: ../uiux_qlda/menu_nhathau/uiux_contractor_detail_popup.svg
- AC:
  - Hiển thị thông tin chi tiết; đóng/mở mượt.

Phụ thuộc
- U-IDENT: JWT/roles.
- U-PLATFORM: OpenAPI, DB, CI.
- U-PROJECT: Dùng API tra cứu nhà thầu khi cập nhật hợp đồng.


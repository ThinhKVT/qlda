# U-CONTRACTS — Menu Hợp đồng (readonly)

Phạm vi
- Danh sách Hợp đồng toàn hệ thống; popup xem thông tin hợp đồng theo gói thầu (readonly).

User Stories & AC

1) CONT-01 — Danh sách Hợp đồng
- SVG: ../uiux_qlda/menu_hopdong/uiux_contract_list.svg
- AC:
  - Hiển thị đúng layout; filter cơ bản; hành động Xem mở popup.

2) CONT-02 — Popup Xem thông tin Hợp đồng
- SVG: ../uiux_qlda/menu_hopdong/uiux_package_contract_info.svg
- AC:
  - Mapping readonly như PROJ-08; đóng/mở mượt; không cho sửa.

Phụ thuộc
- U-IDENT: JWT/roles.
- U-PLATFORM: OpenAPI, DB, CI.
- U-PROJECT: Lấy dữ liệu hợp đồng cho hiển thị readonly.


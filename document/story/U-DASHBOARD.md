# U-DASHBOARD — Dashboard tổng hợp

Phạm vi
- Widget tổng hợp số liệu (dự án, gói thầu, hợp đồng, thanh toán, tài liệu). Không yêu cầu SLA đặc biệt giai đoạn đầu.

User Stories & AC

1) DASH-01 — Màn hình Dashboard tổng hợp
- SVG: ../uiux_qlda/dashboard/uiux_dashboard.svg
- AC:
  - Các widget hiển thị đúng số liệu từ API; hỗ trợ filter thời gian nếu có (tùy phạm vi sprint);
  - Tải nhanh, không yêu cầu SLA cụ thể ở giai đoạn này.

Phụ thuộc
- U-IDENT: JWT/roles.
- U-PLATFORM: OpenAPI, DB, CI.
- U-PROJECT/U-PAYMENTS/U-DOCS/U-CONTRACTS: Cung cấp các chỉ số.


# U-IDENT — Identity & Access

Phạm vi
- Xác thực người dùng bằng JWT, phân quyền theo vai trò.
- Bảo vệ các API của hệ thống; cung cấp thông tin phiên đăng nhập tối thiểu (current user, roles).

Vai trò (roles) ban đầu (có thể tùy biến về sau)
- Admin
- Quản lý dự án
- Cán bộ phòng kỹ thuật
- Lãnh đạo phòng kỹ thuật
- Ban Giám đốc

User Stories và Acceptance Criteria (AC)

1) SEC-01 — Xác thực JWT + Phân quyền theo vai trò
- Mô tả: Thiết lập cơ chế đăng nhập, cấp/refresh token, bảo vệ API theo vai trò.
- AC:
  - Cho phép đăng nhập và nhận JWT hợp lệ; có endpoint refresh token.
  - Bảo vệ các endpoint còn lại bằng Bearer Token; trả 401/403 đúng chuẩn khi không có quyền.
  - Phân quyền theo vai trò: tối thiểu hỗ trợ Admin, Quản lý dự án, Cán bộ phòng kỹ thuật, Lãnh đạo phòng kỹ thuật, Ban Giám đốc.
  - Có endpoint trả thông tin người dùng hiện tại (me) gồm danh sách roles.

Phụ thuộc & tích hợp
- Các unit khác (PROJECT, PAYMENTS, DOCS, CONTRACTOR, CONTRACTS, DASHBOARD) sử dụng JWT từ U-IDENT.
- Audit Log được quản lý bởi các unit nghiệp vụ (ví dụ U-PAYMENTS) nhưng bắt buộc kèm userId từ JWT.

Tham chiếu SVG
- Không áp dụng (không có UI riêng).


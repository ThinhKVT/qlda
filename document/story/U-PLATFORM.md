# U-PLATFORM — Nền tảng/OpenAPI/DB/CI/Notification

Phạm vi
- OpenAPI spec, ERD + migration baseline, CI/dev (docker-compose Postgres), Notification (sườn, optional dev).

User Stories & AC

1) API-01 — OpenAPI Spec
- AC:
  - Định nghĩa paths/schemas cho Projects, Packages, Contracts, Payments, Documents, Contractors, Dashboard, Auth;
  - Sinh stub controller có thể build; versioning; error code chuẩn.

2) DB-01 — ERD + Migration baseline (Flyway)
- AC:
  - Tạo bảng: projects, packages, contracts, payments, documents, contractors, users, roles, audit_logs;
  - Enum status payments; index các trường filter chính; migration chạy thành công.

3) CI-01 — DevOps tối thiểu
- AC:
  - docker-compose Postgres; profile dev; auto-migrate Flyway.

4) NOTI-01 — Notification (sườn)
- AC:
  - Cấu hình SMTP; service có thể gọi (mock trong dev); chưa bắt buộc gửi thật.

Phụ thuộc
- N/A (nền tảng cho các unit khác).


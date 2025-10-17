# Kế hoạch và tiến độ Sprint 1 (2 tuần, 1 team)

Mục tiêu Sprint 1
- Nền tảng: Spring Boot 3, Flyway, PostgreSQL (Docker), Security (JWT), OpenAPI (springdoc)
- Flow demo: Dự án → Gói thầu → Thông tin Hợp đồng (readonly)
- Tài liệu: upload local + tải nhiều file (zip)

Phạm vi (Units & Stories)
- U-PLATFORM: API-01, DB-01, CI-01
- U-IDENT: SEC-01
- U-PROJECT (nền): PROJ-01, PROJ-03, PROJ-04, PROJ-06, PROJ-08
- U-DOCS (nền): DOC-API-01

Phụ thuộc (triển khai)
- U-PLATFORM → nền cho tất cả
- U-IDENT → bảo vệ API
- U-PROJECT → phụ thuộc U-PLATFORM, U-IDENT
- U-DOCS → phụ thuộc U-PLATFORM, U-IDENT

---

Tiến độ tổng quan (Checklist)
- [x] Khởi tạo dự án Spring Boot 3 và cấu hình cơ bản
- [x] Docker Postgres + Flyway migration baseline
- [x] springdoc OpenAPI UI (Swagger)
- [x] JWT skeleton (login, users/me) — dev mode
- [x] Projects API (GET/POST/PUT, GET by id) + paging/sorting/filter (planYear, status)
- [x] Packages API (list/create theo project; GET/PUT package)
- [x] Contracts API (GET/POST thông tin HĐ theo package — readonly tab)
- [x] Documents API (upload multipart, download zip, list theo project)
- [x] Validation cơ bản cho Project/Package/Contract
- [x] Seed dữ liệu dev (3 dự án → mỗi dự án có 1 gói thầu + 1 hợp đồng)
- [ ] Bổ sung mô tả OpenAPI (@Operation/@Schema) cho 1–2 endpoint chính (tài liệu hóa) — optional
- [ ] Seed tài liệu mẫu (để test tab tài liệu nhanh) — optional
- [ ] Thiết lập CI (build + kiểm tra) tối thiểu — optional

---

Bản đồ Stories → Trạng thái
- API-01 (OpenAPI Spec): [x] Skeleton springdoc, UI truy cập; [ ] mô tả chi tiết schema (optional)
- DB-01 (Migration baseline): [x] V1 baseline (UUID do app sinh; index cơ bản)
- CI-01 (DevOps tối thiểu): [ ] Chưa bật CI (khuyến nghị Sprint 1: add job build + test)
- SEC-01 (JWT + Roles): [x] JWT dev; [ ] phân quyền chi tiết theo vai trò thực tế (để Sprint sau)
- PROJ-01 (Project List): [x] GET /api/projects hỗ trợ page/size/sort/filter
- PROJ-03 (Project Form): [x] POST/PUT /api/projects + validation
- PROJ-04 (Project Detail - Tiến độ): [x] Dữ liệu nền sẵn (contract/package), UI scroll thuộc FE
- PROJ-06 (Package List): [x] GET/POST /api/projects/{id}/packages
- PROJ-08 (Contract Info readonly): [x] GET/POST /api/packages/{id}/contract
- DOC-API-01 (Documents): [x] Upload, download zip, list theo project

---

Bản đồ Acceptance Criteria (AC) → Phủ trạng thái
- PROJ-01
  - [x] Hiển thị (BE trả Page): page/size/sort/filter (planYear, status)
  - [x] Tên dự án là link (thuộc FE; BE đã cung cấp id)
  - [ ] Dòng “Hiển thị x-y/N” (thuộc FE; BE đã trả total/pageable)
- PROJ-03
  - [x] Dropdown/lookup (thuộc FE; BE lưu, validation cơ bản)
  - [x] Lưu thành công, mã dự án quản lý bởi BE (trường code hiện nhận từ client; auto-code có thể bổ sung ở Sprint sau)
- PROJ-04
  - [x] Mặc định Tiến độ (thuộc FE); dữ liệu nền sẵn (packages/contracts)
- PROJ-06
  - [x] Danh sách gói thầu trong project; tạo gói thầu
- PROJ-08
  - [x] Mapping contract fields: number, dates, durationMonths, expireDate, value, currency, bidderName, type, paymentInstallments
- DOC-API-01
  - [x] Upload multipart; [x] Download zip nhiều file; [x] Tìm kiếm theo projectId

Ghi chú: Các AC thuần UI/UX (scroll, bố cục, label…) sẽ do FE; BE đã cung cấp dữ liệu và endpoints.

---

Kết quả kỹ thuật Sprint 1 (tóm tắt endpoints)
- Auth
  - POST /auth/login → { accessToken }
  - GET /auth/users/me → { username, roles }
- Projects
  - GET /api/projects?page=&size=&sort=&planYear=&status=
  - GET /api/projects/{id}
  - POST /api/projects, PUT /api/projects/{id}
  - GET /api/projects/{id}/packages, POST /api/projects/{id}/packages
  - GET /api/projects/{id}/documents
- Packages & Contracts
  - GET /api/packages/{id}, PUT /api/packages/{id}
  - GET /api/packages/{id}/contract, POST /api/packages/{id}/contract
- Documents
  - POST /api/documents/upload (multipart)
  - POST /api/documents/download (ids → zip)
  - GET /api/documents?projectId=...

Validation đã áp dụng
- Project: name @NotBlank, planYear @Positive
- Package: name @NotBlank
- Contract: number/effective/signed/expireDate @NotNull, durationMonths/value/paymentInstallments @Min(1), currency/bidderName/type @NotBlank
- Controllers: @Valid cho payload tạo/sửa

Seed dữ liệu dev
- 3 dự án (DA2025…), mỗi dự án có 1 gói thầu (Gói thầu 1..3) và 1 hợp đồng (HD-2025/001..003)
- Tự tạo khi `projectRepository.count() == 0`

Môi trường & chạy thử
- Docker Postgres: `docker compose up -d`
- Chạy app bằng Maven: `mvn -DskipTests spring-boot:run`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Lưu ý: Cần cài Maven (Homebrew: `brew install maven`) để tải dependencies và chạy ứng dụng

---

Rủi ro & tồn đọng
- [ ] CI-01: Chưa cấu hình CI build/test (khuyến nghị thêm workflow đơn giản)
- [ ] OpenAPI mô tả chi tiết schema (optional, để dễ trao đổi với FE/QC)
- [ ] Seed tài liệu mẫu (optional, để demo nhanh Documents)
- [ ] Phân quyền chi tiết theo vai trò thực tế (đưa sang Sprint 2/3)

Bước tiếp theo để chốt Sprint 1
- [ ] QA smoke test theo README (flow Dự án → Gói thầu → Thông tin HĐ)
- [ ] Bổ sung mô tả OpenAPI tối thiểu cho Projects/Packages/Contracts (nếu cần)
- [ ] (Optional) Thêm seed tài liệu mẫu, và job CI build

[Question] Bạn có muốn mình bật CI tối thiểu (build + test) ngay trong Sprint 1 không?
[Answer] Chưa cần bật CI ở Sprint 1, chỉ cần kiểm thử local trước.

[Question] Bạn có muốn mình bổ sung mô tả OpenAPI (@Operation/@Schema) cho các endpoint chính để FE/QC dễ bám theo không?
[Answer] Đồng ý bổ sung mô tả OpenAPI (@Operation/@Schema) cho các endpoint chính để FE/QC dễ bám theo.

[Question] Có cần seed thêm tài liệu mẫu (PDF giả lập) để test nhanh Documents không?
[Answer] Đồng ý seed thêm tài liệu mẫu (PDF giả lập) để test nhanh Documents.



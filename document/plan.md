# Kế hoạch nhóm user stories thành các đơn vị triển khai độc lập (Units)

Mục tiêu
- Nhóm các user stories trong `document/Backlog.md` thành nhiều đơn vị (units) có tính liên kết cao, có thể do một team triển khai độc lập.
- Các units lỏng lẻo (loosely coupled) với nhau, chỉ tích hợp qua hợp đồng tích hợp (integration contract).
- Chưa bắt đầu thiết kế kỹ thuật hệ thống; chỉ dừng ở phạm vi story, AC và hợp đồng tích hợp cấp API.

Định nghĩa đơn vị (Unit) đề xuất
1) Unit U-IDENT: Identity & Access
- Phạm vi: Xác thực JWT, phân quyền theo vai trò; lớp bảo vệ chung cho các API; nền tảng Audit Log cơ bản.
- Stories dự kiến: SEC-01, (liên quan LOG-01 ở mức tích hợp sự kiện, có thể chia sẻ với U-AUDIT nếu tách riêng).

2) Unit U-PROJECT: Quản lý Dự án & Gói thầu & Thông tin Hợp đồng (readonly)
- Phạm vi: Danh sách dự án, form thêm/sửa, popup tìm kiếm nâng cao; chi tiết dự án (tab Tiến độ), quản lý gói thầu (list/form), chi tiết gói thầu (tab Thông tin hợp đồng readonly).
- Stories dự kiến: PROJ-01, PROJ-02, PROJ-03, PROJ-04, PROJ-05, PROJ-06, PROJ-07, PROJ-08.

3) Unit U-PAYMENTS: Thanh toán theo đợt
- Phạm vi: Tab Thanh toán của gói thầu; nghiệp vụ planned/actual; confirm PENDING→PAID; reopen chỉnh sửa; ràng buộc tổng ≤ giá trị HĐ; audit sự kiện thanh toán.
- Stories dự kiến: PROJ-09, PAY-API-01, LOG-01 (sự kiện liên quan payments).

4) Unit U-DOCS: Tài liệu/Kho tri thức
- Phạm vi: Dịch vụ tài liệu dùng chung (upload, multi-download zip, quyền truy cập); Tài liệu của gói thầu & dự án; màn hình Kho tri thức.
- Stories dự kiến: DOC-API-01, PROJ-10, PROJ-12, KB-01, KB-02.

5) Unit U-CONTRACTOR: Nhà thầu
- Phạm vi: Danh sách nhà thầu, popup thêm, popup chi tiết; nền tảng tra cứu nhà thầu cho hợp đồng.
- Stories dự kiến: BID-01, BID-02, BID-03.

6) Unit U-CONTRACTS: Hợp đồng (Menu riêng)
- Phạm vi: Danh sách hợp đồng toàn hệ thống; popup xem thông tin hợp đồng (readonly) theo gói thầu.
- Stories dự kiến: CONT-01, CONT-02.

7) Unit U-DASHBOARD: Dashboard tổng hợp
- Phạm vi: Widget tổng hợp số liệu (dự án, gói thầu, hợp đồng, thanh toán, tài liệu); filter thời gian nếu có.
- Stories dự kiến: DASH-01.

8) Unit U-PLATFORM: Nền tảng/Backend/API & CSDL & CI
- Phạm vi: OpenAPI spec; ERD + migration baseline; CI/devops (docker-compose Postgres, profile dev); Notification (sườn).
- Stories dự kiến: API-01, DB-01, CI-01, NOTI-01.

Lưu ý tách ghép & phụ thuộc
- U-PROJECT phụ thuộc U-PLATFORM (API-01, DB-01) và U-IDENT (SEC-01).
- U-PAYMENTS phụ thuộc U-PROJECT (contract.value, package/contract id), U-DOCS (đính kèm), U-PLATFORM, U-IDENT.
- U-DOCS phụ thuộc U-PLATFORM, U-IDENT.
- U-CONTRACTOR độc lập tương đối; dùng bởi U-PROJECT khi chọn nhà thầu.
- U-CONTRACTS dựa trên dữ liệu hợp đồng của U-PROJECT (readonly) — integration qua API.
- U-DASHBOARD phụ thuộc các API công khai của các unit khác.

---

Kế hoạch thực hiện (chỉ tạo tài liệu, chưa thiết kế kỹ thuật)

- [x] Bước 1: Xác nhận ranh giới Units và ánh xạ stories
  - Đầu ra: Danh sách units như trên + mapping story → unit được chốt.

- [x] Bước 2: Tạo file stories cho từng Unit trong `document/story/`
  - Đầu ra: 1 file `.md`/unit, gồm:
    - Danh sách user stories (từ Backlog) và Acceptance Criteria đầy đủ (giữ nguyên/bổ sung chi tiết nếu cần, không đổi phạm vi UI/UX).
    - Tham chiếu SVG để dev/QA đối chiếu.
  - Danh sách file đã tạo:
    - `document/story/U-IDENT.md`
    - `document/story/U-PROJECT.md`
    - `document/story/U-PAYMENTS.md`
    - `document/story/U-DOCS.md`
    - `document/story/U-CONTRACTOR.md`
    - `document/story/U-CONTRACTS.md`
    - `document/story/U-DASHBOARD.md`
    - `document/story/U-PLATFORM.md`

- [x] Bước 3: Tạo hợp đồng tích hợp `document/integration_contract.md`
  - Đầu ra: Mô tả Integration Contract ở mức API cho từng Unit đã được biên soạn.

- [x] Bước 4: Rà soát phụ thuộc chéo & cập nhật hợp đồng nếu cần
  - Đầu ra: Phụ thuộc đã được ghi chú trong từng file story và integration contract; các Unit tích hợp qua API công khai.

- [x] Bước 5: Review cùng stakeholder
  - Đầu ra: Các chỉnh sửa/ghi chú cập nhật vào file story và integration contract.

---

Nguồn tham chiếu
- Backlog: `document/Backlog.md`
- Thiết kế UI/UX (SVG): `../uiux_qlda/...` như đã dùng trong Backlog.

---

[Question] 1. Bạn có muốn Payments (U-PAYMENTS) là một service tách biệt hoàn toàn (deploy độc lập) hay là module trong cùng service với U-PROJECT nhưng database/table tách biệt?
[Answer] Module trong cùng service với U-PROJECT; database/table tách biệt.

[Question] 2. Dịch vụ Tài liệu (U-DOCS) lưu trữ file ở đâu giai đoạn đầu? Local storage (dev) hay tích hợp S3/MinIO ngay từ Sprint 1?
[Answer] Lưu trữ local (giai đoạn đầu).

[Question] 3. Cho phép điều chỉnh actualAmount sau khi đã PAID? Nếu có, yêu cầu quyền đặc biệt và audit chi tiết — vui lòng xác nhận chính sách mong muốn.
[Answer] Không cho phép sửa actualAmount sau khi trạng thái đã PAID.

[Question] 4. Menu Hợp đồng (U-CONTRACTS) chỉ đọc (readonly) hay cần các thao tác chỉnh sửa trong tương lai? Hiện tại backlog ghi readonly — xác nhận để khóa phạm vi.
[Answer] Chỉ đọc (readonly).

[Question] 5. Dashboard (U-DASHBOARD) có SLA thời gian phản hồi mục tiêu (ví dụ < 800ms cho bộ widget mặc định) không? Nếu có, hãy cho biết để lên kế hoạch cache/tổng hợp.
[Answer] Chưa yêu cầu SLA cụ thể giai đoạn này.

[Question] 6. Khung thời gian Sprint đầu (số tuần) và số team song song? Điều này ảnh hưởng thứ tự ưu tiên triển khai các units.
[Answer] Sprint dài 2 tuần, có 1 team triển khai.

[Question] 7. Về xuất Excel (REP-01), bạn muốn đặt ở Unit nào? Đề xuất đặt trong U-PROJECT để bám đúng Project List.
[Answer] Đặt trong U-PROJECT để bám đúng Project List.

[Question] 8. Phân quyền (roles) có danh sách cố định (admin, qlda, lcnt, viewer...) hay cần tùy biến? Vui lòng xác nhận danh sách vai trò ban đầu.
[Answer] Cần tùy biến. Danh sách ban đầu: Admin, Quản lý dự án, Cán bộ phòng kỹ thuật, Lãnh đạo phòng kỹ thuật, Ban Giám đốc.

---

Tiêu chí hoàn thành giai đoạn này
- Có `document/story/*.md` cho từng Unit với stories + AC đầy đủ (từ Backlog, không thay đổi thiết kế UI/UX).
- Có `document/integration_contract.md` nêu rõ API công khai của từng Unit.
- Các câu hỏi trọng yếu được trả lời/ghi nhận để khóa phạm vi.

Sau khi bạn review & duyệt kế hoạch này, mình sẽ thực hiện lần lượt các bước và đánh dấu hoàn thành từng checkbox trong `document/plan.md`.

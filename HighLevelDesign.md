# HIGH-LEVEL DESIGN HỆ THỐNG QUẢN LÝ DỰ ÁN MUA SẮM ÁP DỤNG LUẬT ĐẤU THẦU

## 1. Kiến trúc tổng thể

- Hệ thống được xây dựng theo mô hình 3 lớp (Three-tier Architecture):
  - **Frontend (Client):** Angular
  - **Backend (Server):** Java Spring Boot (RESTful API)
  - **Database:** PostgreSQL

- Giao tiếp giữa Frontend và Backend thông qua REST API (JSON).
- Hệ thống hỗ trợ triển khai on-premise hoặc cloud (AWS, Azure, GCP).

---

## 2. Sơ đồ tổng quan kiến trúc

```
+-------------------+        HTTPS/REST        +---------------------+        JDBC        +-------------------+
|    Angular App    | <---------------------> |  Spring Boot API    | <---------------> |   PostgreSQL DB   |
+-------------------+                        +---------------------+                  +-------------------+
```

---

## 3. Các thành phần chính

### 3.1. Frontend (Angular)
- Xây dựng SPA (Single Page Application) với Angular.
- Các module chính:
  - Quản lý dự án
  - Quản lý gói thầu
  - Quản lý nhà thầu
  - Quản lý hồ sơ mời thầu/dự thầu
  - Quản lý hợp đồng
  - Quản lý tài liệu
  - Báo cáo, thống kê
  - Quản lý người dùng, phân quyền
  - Thông báo, gửi mail
- Sử dụng Angular Material hoặc PrimeNG cho UI/UX.
- Tích hợp xác thực JWT, phân quyền theo vai trò.

### 3.2. Backend (Java Spring Boot)
- Cung cấp RESTful API cho frontend và các hệ thống tích hợp.
- Các module/service chính:
  - ProjectService: Quản lý dự án
  - PackageService: Quản lý gói thầu
  - BidderService: Quản lý nhà thầu
  - TenderDocumentService: Quản lý hồ sơ mời thầu/dự thầu
  - ContractService: Quản lý hợp đồng
  - DocumentService: Quản lý tài liệu
  - ReportService: Báo cáo, thống kê
  - UserService: Quản lý người dùng, phân quyền
  - NotificationService: Gửi email, thông báo
  - AuditLogService: Nhật ký hoạt động
- Sử dụng Spring Security cho xác thực, phân quyền (JWT).
- Sử dụng JPA/Hibernate để truy cập PostgreSQL.
- Tích hợp gửi email (JavaMail), xuất/nhập Excel (Apache POI), PDF (iText).
- Lưu trữ file tài liệu trên server hoặc cloud (AWS S3, Azure Blob Storage...)

### 3.3. Database (PostgreSQL)
- Thiết kế các bảng dữ liệu:
  - projects, packages, bidders, tender_documents, contracts, documents, users, roles, permissions, audit_logs, notifications...
- Sử dụng các ràng buộc (constraint), chỉ mục (index), trigger để đảm bảo toàn vẹn dữ liệu.

---

## 4. Luồng hoạt động chính

1. Người dùng đăng nhập vào hệ thống (Angular gửi thông tin đăng nhập đến Spring Boot, xác thực JWT).
2. Người dùng thao tác trên giao diện (quản lý dự án, gói thầu, nhà thầu, hợp đồng, tài liệu...).
3. Angular gửi request đến REST API (Spring Boot), backend xử lý nghiệp vụ, truy vấn/ghi dữ liệu vào PostgreSQL.
4. Khi có sự kiện quan trọng (tạo mới, thay đổi trạng thái...), backend gửi email thông báo qua NotificationService.
5. Người dùng có thể xuất/nhập dữ liệu Excel, PDF từ frontend (gọi API backend xử lý file).
6. Tài liệu được upload/download qua backend, lưu trữ theo từng dự án/gói thầu/giai đoạn.
7. Mọi thao tác được ghi nhận vào AuditLog để truy vết.

---

## 5. Bảo mật & phân quyền
- Xác thực người dùng bằng JWT.
- Phân quyền theo vai trò (admin, cán bộ đấu thầu, lãnh đạo, nhà thầu...).
- Mã hóa dữ liệu nhạy cảm (nếu cần).
- Kiểm soát truy cập tài liệu theo dự án/gói thầu/giai đoạn.

---

## 6. Khả năng mở rộng & tích hợp
- Dễ dàng mở rộng thêm module mới (microservice hoặc monolith module).
- Tích hợp với hệ thống văn bản, tài chính, chữ ký số, SMS, cloud storage.
- Hỗ trợ triển khai CI/CD, logging, monitoring.

---

## 7. Công nghệ sử dụng
- **Frontend:** Angular, Angular Material/PrimeNG, RxJS, JWT
- **Backend:** Java 17+, Spring Boot, Spring Security, Spring Data JPA, JavaMail, Apache POI, iText, MapStruct
- **Database:** PostgreSQL
- **Khác:** Docker, Git, CI/CD (Jenkins/GitHub Actions), Cloud Storage (AWS S3/Azure Blob), Monitoring (Prometheus/Grafana)

---

## 8. Định hướng phát triển tương lai
- Tích hợp mobile app (Ionic/Flutter)
- Tích hợp AI hỗ trợ phân tích dữ liệu, dự báo tiến độ, chi phí
- Tích hợp chữ ký số, xác thực đa yếu tố (MFA)
- Tích hợp hệ thống quản lý văn bản, tài chính

---

*File này mô tả thiết kế tổng thể (high-level design) cho hệ thống quản lý dự án mua sắm áp dụng Luật Đấu thầu Việt Nam.*


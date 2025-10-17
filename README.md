# QLDA — Sprint 1 (Skeleton)

Mục tiêu Sprint 1
- Nền tảng: Spring Boot, Flyway, PostgreSQL (Docker), Security (JWT), OpenAPI (springdoc)
- Flow demo: Dự án → Gói thầu → Thông tin Hợp đồng (readonly)
- Tài liệu: upload local + tải nhiều file (zip)

Yêu cầu cài đặt (macOS)
- Docker Desktop
- Homebrew
- Maven (nếu chưa có):

```bash
brew install maven
```

Khởi động hạ tầng

```bash
# 1) PostgreSQL qua Docker
cd /Users/nhung/IdeaProjects/qlda
docker compose up -d
```

Chạy ứng dụng (chọn 1)

```bash
# A) Dùng Maven
mvn -DskipTests spring-boot:run

# B) Đóng gói rồi chạy jar
mvn -DskipTests package
java -jar target/qlda-1.0.0-SNAPSHOT.jar
```

Cấu hình
- application.yml dùng DB: jdbc:postgresql://localhost:5432/qlda (user/pass: qlda/qlda)
- OpenAPI UI: http://localhost:8080/swagger-ui.html
- Auth (dev): /auth/login trả JWT giả lập (role Admin)

Endpoints chính (Sprint 1)
- Auth:
  - POST /auth/login → { accessToken }
  - GET /auth/users/me → { username, roles }
- Projects:
  - GET /api/projects
  - GET /api/projects/{id}
  - POST /api/projects
  - PUT /api/projects/{id}
  - GET /api/projects/{id}/documents
  - GET /api/projects/{id}/packages
  - POST /api/projects/{id}/packages
- Packages & Contracts:
  - GET /api/packages/{id}
  - PUT /api/packages/{id}
  - GET /api/packages/{id}/contract
  - POST /api/packages/{id}/contract
- Documents:
  - POST /api/documents/upload (multipart)
  - POST /api/documents/download (ids → zip)
  - GET /api/documents?projectId=...

Ghi chú
- JWT dev: chấp nhận mọi username/password, dùng để thử nhanh.
- Flyway migration: không yêu cầu extension UUID trên DB (UUID do ứng dụng sinh).


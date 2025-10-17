# Implementation Plan: Manage project (create, update, delete, view list, view detail)

**Branch**: `001-th-m-s` | **Date**: 2025-10-14 | **Spec**: /specs/001-th-m-s/spec.md
**Input**: Feature specification from `/specs/001-th-m-s/spec.md` (đã cập nhật bổ sung các trường: phòng đầu mối, cán bộ đầu mối, cán bộ QLDA cho Project)

## Summary
Quản lý dự án gồm các thao tác: thêm mới, chỉnh sửa, xoá mềm, xem danh sách, xem chi tiết. Dữ liệu dự án liên kết với gói thầu, hợp đồng, tài liệu. Đặc tả đã làm rõ các trường, enum, nghiệp vụ, filter, phân quyền, quy tắc xoá mềm cho toàn bộ entity liên quan. 

**Cập nhật mới:** Project bổ sung các trường: phòng đầu mối (`lead_department`), cán bộ đầu mối (`lead_staff`), cán bộ QLDA (`project_manager`).

## Technical Context
- **Language/Version**: Java 17, Spring Boot
- **Primary Dependencies**: Spring Data JPA, Flyway, PostgreSQL, Spring Security, Lombok
- **Storage**: PostgreSQL
- **Testing**: JUnit, Mockito, Spring Test
- **Target Platform**: Web server (Linux, macOS)
- **Project Type**: Web backend (single project)
- **Performance Goals**: Đáp ứng <200ms cho các thao tác CRUD, filter danh sách dự án tối ưu cho 10.000+ bản ghi
- **Constraints**: Phân quyền chặt chẽ, dữ liệu không được xoá vật lý, các trường enum phải đồng bộ UI/DB, filter linh hoạt
- **Scale/Scope**: Quản lý tối thiểu 10.000 dự án, 100.000 hợp đồng, 500.000 tài liệu
- **Entity Update**: Project có thêm các trường lead_department, lead_staff, project_manager; cần đồng bộ ở data model, migration, API, UI, test.

## Constitution Check
- Đảm bảo tuân thủ hiến pháp QLDA: bảo mật, kiểm thử tự động, đơn giản, toàn vẹn dữ liệu, log đầy đủ
- Kiểm tra phân quyền: chỉ admin được thêm/sửa/xoá, user chỉ xem
- Kiểm tra quy tắc xoá mềm cho toàn bộ entity
- Kiểm tra enum, filter, các trường unique
- Kiểm tra bổ sung các trường mới cho Project

## Project Structure
### Documentation (this feature)
```
specs/001-th-m-s/
├── plan.md
├── spec.md
├── checklists/requirements.md
├── data-model.md
├── quickstart.md
├── contracts/
└── tasks.md
```
### Source Code (repository root)
- src/main/java/org/example/domain/Project.java
- src/main/java/org/example/domain/Contract.java
- src/main/java/org/example/domain/Document.java
- src/main/java/org/example/domain/Package.java
- src/main/java/org/example/repo/
- src/main/java/org/example/service/
- src/main/java/org/example/web/
- src/main/resources/db/migration/

## Next Steps
- Thiết kế data model chi tiết (data-model.md), bổ sung các trường lead_department, lead_staff, project_manager cho Project
- Viết migration cho các bảng, enum, ràng buộc, bổ sung các trường mới cho Project
- Thiết kế API CRUD, filter, phân quyền, đảm bảo các trường mới được hỗ trợ đầy đủ
- Viết test case cho các luồng chính và trường hợp biên, kiểm thử các trường mới
- Triển khai UI/UX đồng bộ với đặc tả, hiển thị và nhập liệu các trường mới
- Review, kiểm thử, hoàn thiện tài liệu

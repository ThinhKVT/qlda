# Tasks for Feature: Project Management - Create, Update, Delete

## Phase 1: Setup
T001. [X] [Setup] Initialize project repository and environment (Java 17, Spring Boot, PostgreSQL, Flyway, JUnit, Mockito) [P]
T002. [X] [Setup] Configure base project structure (src/main/java/org/example/domain, repo, service, web, resources/db/migration) [P]
T003. [X] [Setup] Setup authentication and basic user roles (admin, user) [P]
T004. [X] [Setup] Create initial database schema and baseline migration [P]

## Phase 2: Foundational Tasks
T005. [X] [Foundation] Implement core Project entity and relationships in domain model
T006. [X] [Foundation] Implement base repository and service for Project
T007. [X] [Foundation] Implement base API controller for Project
T008. [X] [Foundation] Implement migration for Project, Package, Contract, Document, Contractor tables
T009. [X] [Foundation] Implement basic error handling and validation framework

## Phase 3: User Story 1 - Add New Project (P1)
T010. [X] [US1] Implement Project creation API (POST /api/projects)
T011. [X] [US1] Implement logic for auto-generating project_code (DA-{plan_year}-{STT})
T012. [X] [US1] Implement validation for required fields (name, project_type, plan_year, start_date, end_date, lead_department, lead_staff, project_manager, investment_budget, currency, plan_type, requirement)
T013. [X] [US1] Implement support for new fields: requirement, options, docs
T014. [X] [US1] Implement integration with Document entity for docs field (GET detail returns docs; endpoints to link/unlink documents implemented; attach/detach in upload controller present)
T015. [X] [US1] Implement test cases for project creation (valid, missing required, large input, edge cases)
T016. [X] [US1] Implement test cases for auto-gen project_code logic
T017. [X] [US1] Implement test cases for new fields (requirement, options, docs)
T018. [X] [US1] Implement API documentation for project creation

## Phase 4: User Story 2 - Edit Project Information (P2)
T019. [X] [US2] Implement Project update API (PUT /api/projects/{id})
T020. [X] [US2] Implement logic to update only non-null/non-empty fields, ignore id/project_code
T021. [X] [US2] Implement validation for update (date logic, field constraints)
T022. [X] [US2] Implement support for updating new fields: requirement, options, docs
T023. [X] [US2] Implement test cases for project update (valid, edge cases, update only changed fields)
T024. [X] [US2] Implement test cases for update logic (ignore id/project_code, only update non-null)
T025. [X] [US2] Implement test cases for new fields (requirement, options, docs)
T026. [X] [US2] Implement API documentation for project update

## Phase 5: User Story 3 - Delete Project (P3)
T027. [X] [US3] Implement Project delete API (soft delete)
T028. [X] [US3] Implement logic for soft delete (set deleted flag, cascade to related entities)
T029. [X] [US3] Implement test cases for project delete (valid, already deleted, edge cases)
T030. [X] [US3] Implement test cases for soft delete logic (cascade, filter out deleted)
T031. [X] [US3] Implement API documentation for project delete

## Phase 6: Polish & Cross-Cutting Concerns
T032. [X] [Polish] Implement API for project list and detail (GET /api/projects, GET /api/projects/{id})
T033. [X] [Polish] Implement filtering logic for all supported filters (project_code, status, start_date range, end_date range, lead_department, lead_staff, project_manager, requirement)
T034. [X] [Polish] Implement test cases for list and filter (all filters, edge cases, large data)
T035. [X] [Polish] Implement security and permission checks (admin vs user)
T036. [X] [Polish] Finalize API documentation and OpenAPI spec
T037. [X] [Polish] Review and refactor codebase for maintainability
T038. [X] [Polish] Final integration and acceptance testing
T039. [X] [Polish] Kiểm thử hiệu năng với dữ liệu lớn (10.000+ dự án)
T040. [X] [Polish] Kiểm thử bảo mật và phân quyền (admin/user)
T041. [X] [Polish] Kiểm thử thông điệp lỗi và mã lỗi API
T042. [X] [Polish] Tạo và xác thực OpenAPI spec cho toàn bộ endpoint

## Dependencies
- Setup and foundational tasks (T001-T009) must complete before any user story phase
- US1 (T010-T018) is independent and can be delivered as MVP
- US2 (T019-T026) depends on US1
- US3 (T027-T031) depends on US1
- Polish phase (T032-T038) depends on completion of all user stories

## Parallel Execution Examples
- T001-T004 (Setup) can run in parallel
- T005-T009 (Foundation) can run in parallel
- Within each user story, test cases (e.g., T015-T017) can run in parallel with implementation tasks

## Implementation Strategy
- Deliver MVP after US1 (Add New Project) is complete and tested
- Incrementally deliver US2 (Edit) and US3 (Delete)
- Polish phase ensures cross-cutting concerns and final acceptance

---
Total tasks: 42
Tasks per user story: US1 (9), US2 (8), US3 (5)
Parallel opportunities: Setup, Foundation, test tasks within stories
Independent test criteria: Each user story has its own test cases and acceptance criteria
Suggested MVP scope: US1 (Add New Project)

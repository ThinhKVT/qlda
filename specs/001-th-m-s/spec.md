# Feature Specification: Project Management - Create, Update, Delete

**Feature Branch**: `001-th-m-s`  
**Created**: 2025-10-14  
**Status**: Draft  
**Input**: Manage projects (create, update, delete)

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Add New Project (Priority: P1)

The admin wants to create a new project with all the basic information.

**Why this priority**: This is the foundational data initialization function for the system.

**Independent Test**: Test creating a new project with valid information, confirm the project appears in the list.

**Acceptance Scenarios**:

1. **Given** no projects exist, **When** the admin enters information and clicks "Add Project", **Then** the new project is saved and displayed.
2. **Given** the project information is missing required fields, **When** clicking "Add Project", **Then** the system reports an error and does not save.

---

### User Story 2 - Edit Project Information (Priority: P2)

The admin wants to edit the information of an existing project.

**Why this priority**: To ensure data is always accurate and up-to-date.

**Independent Test**: Edit project information, confirm the changes are saved and displayed correctly.

**Acceptance Scenarios**:

1. **Given** a project already exists, **When** the admin edits the information and saves, **Then** the new information is updated.
2. **Given** invalid information is entered, **When** saving, **Then** the system reports an error and does not update.

---

### User Story 3 - Delete Project (Priority: P3)

The admin wants to delete a project that is no longer in use.

**Why this priority**: To help the system maintain clean data and avoid redundancy.

**Independent Test**: Delete a project, confirm the project no longer appears in the list.

**Acceptance Scenarios**:

1. **Given** a project already exists, **When** the admin selects delete, **Then** the project is removed from the system.
2. **Given** the project has related data, **When** deleting, **Then** the system warns and processes according to regulations (e.g., not allowing deletion if there are linked contracts). [Đã làm rõ: Khi xoá dự án có dữ liệu liên quan (hợp đồng, tài liệu, gói thầu), chỉ đánh dấu là deleted, không xoá khỏi hệ thống; các dữ liệu liên quan cũng đánh dấu deleted. Khi view list chỉ lấy các dự án không bị xoá.]

---

### User Story 4 - View Project List (Priority: P1)

The user wants to see a list of all projects currently in the system.

**Why this priority**: This is a basic function that helps users monitor, manage, and select projects for further action.

**Independent Test**: Access the project list screen, confirm that all projects' information is displayed correctly.

**Acceptance Scenarios**:

1. **Given** there are multiple projects in the system, **When** the user accesses the project list, **Then** all projects are displayed with the correct information.
2. **Given** there are no projects, **When** accessing the list, **Then** the system shows an appropriate message.

---

### User Story 5 - View Project Details (Priority: P2)

The user wants to view the detailed information of a specific project.

**Why this priority**: To help users understand the information of each project for further actions (edit, delete, link data, etc.).

**Independent Test**: Select any project from the list, confirm that the full detailed information of the project is displayed.

**Acceptance Scenarios**:

1. **Given** the project exists, **When** the user selects to view details, **Then** the system displays all the information of the project.
2. **Given** the project does not exist (invalid id), **When** accessing details, **Then** the system reports an error or not found message. [Đã làm rõ: Khi truy cập chi tiết dự án không tồn tại, báo lỗi "Project not existed".]

---

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST allow adding new projects with mandatory fields: name, description, start date, end date, status.
- **FR-002**: System MUST allow editing project information, validating data before saving.
- **FR-003**: System MUST allow deleting projects, checking related data constraints before execution.
- **FR-004**: System MUST allow viewing the list of all projects, displaying basic information (id, name, status, start/end date). [Bổ sung: Khi truy vấn danh sách dự án cần có các điều kiện filter (theo tên, trạng thái, ngày bắt đầu/kết thúc, ...)]
- **FR-005**: System MUST allow viewing detailed information of each project.
- **FR-006**: System MUST display clear messages when operations succeed or fail.

### Key Entities *(include if feature involves data)*

- **Project**:
  - `id` (Long): Mã định danh duy nhất cho dự án.
  - `name` (String): Tên dự án.
  - `description` (String): Mô tả chi tiết về dự án.
  - `start_date` (Date): Ngày bắt đầu dự án.
  - `end_date` (Date): Ngày kết thúc dự án.
  - `status` (Enum/String): Trạng thái dự án (active, deleted, completed, ...).
  - `deleted` (Boolean): Đánh dấu dự án đã bị xoá mềm (soft delete).
  - `docs` (List<Document>): Danh sách các tài liệu thuộc dự án (không thuộc package).

- **Contract (Hợp đồng)**:
  - Liên kết 1-n với Project (mỗi dự án có thể có nhiều hợp đồng).
  - Khi xoá dự án, các hợp đồng liên quan cũng được đánh dấu deleted.

- **Document (Tài liệu)**:
  - Liên kết 1-n với Project (mỗi dự án có thể có nhiều tài liệu).
  - Khi xoá dự án, các tài liệu liên quan cũng được đánh dấu deleted.

- **Package (Gói thầu)**:
  - Liên kết 1-n với Project (mỗi dự án có thể có nhiều gói thầu).
  - Khi xoá dự án, các gói thầu liên quan cũng được đánh dấu deleted.

**Mối quan hệ:**
- Project là thực thể trung tâm, liên kết với các thực thể Contract, Document, Package theo quan hệ 1-n.
- Khi thao tác xoá, chỉ đánh dấu deleted cho cả Project và các thực thể liên quan, không xoá vật lý khỏi hệ thống.

### Edge case cho requirement/options:

- requirement có thể là mảng lớn, rỗng, hoặc chứa ký tự đặc biệt.
- options có thể là object phức tạp, rỗng, hoặc có giá trị lớn.

### Chuẩn hóa thuật ngữ

- lead_staff = "Cán bộ đầu mối"
- project_manager = "Cán bộ QLDA" trên toàn bộ tài liệu.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% of add, edit, delete, view list, and view detail operations have clear confirmations for the user.
- **SC-002**: New/edited/deleted/viewed projects appear correctly in the list and details.
- **SC-003**: No data errors or abnormal operations.
- **SC-004**: Ensure security: only admins have the rights to create, update, delete; users can view list and details. [Đã làm rõ: Phiên bản 1 chỉ có admin và user, không cần phân quyền chi tiết hơn.]

## Assumptions

- Only admins have the rights to add, edit, delete projects.
- Users can view the list and details of projects.
- Projects may be linked to other entities (contracts, documents, etc.).
- If a project has related data, the rules for handling deletions need to be defined. [Đã làm rõ: Khi xoá dự án có dữ liệu liên quan, chỉ đánh dấu deleted cho cả dự án và dữ liệu liên quan, không xoá khỏi hệ thống.]

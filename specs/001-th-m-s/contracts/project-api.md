# Project API Contract

## Project Model (Response shape)
- Lưu ý: Không đánh dấu required tại phần Model; chỉ đánh dấu required ở request body của API POST.

```
{
  "id": UUID,
  "project_code": String,          // mã dự án sinh tự động: DA-{plan_year}-{STT}
  "name": String,
  "description": String,
  "plan_year": Integer,
  "start_date": Date (yyyy-MM-dd),
  "end_date": Date (yyyy-MM-dd),
  "status": String,
  "deleted": Boolean,
  "lead_department": String,       // Phòng đầu mối
  "lead_staff": String,            // Cán bộ đầu mối (phiên bản này lưu dạng String)
  "project_manager": String,       // Cán bộ QLDA (phiên bản này lưu dạng String)
  "requirement": String,           // JSON/text (có thể là mảng hoặc object ở tầng client)
  "options": String,               // JSON/text (có thể là mảng/object ở tầng client)
  "docs": List<Document>           // Danh sách tài liệu thuộc dự án (không thuộc package), chỉ trả về ở API GET detail
}
```

---

## Endpoints

### 1) Create Project
- Method: POST /api/projects
- Request body (required fields được đánh dấu):
```
{
  "name": "Tên dự án",               // required
  "plan_year": 2025,                  // required
  "start_date": "2025-01-01",        // required
  "end_date": "2025-12-31",          // required

  "description": "Mô tả dự án",
  "status": "active",
  "lead_department": "Phòng Kế hoạch",
  "lead_staff": "Nguyễn Văn A",
  "project_manager": "Trần Thị B",
  "requirement": "[\"YC1\",\"YC2\"]",    
  "options": "{\"optionA\":true}"
}
```
- Behavior/Logic:
  - Backend sinh tự động trường `project_code` theo định dạng: `DA-{plan_year}-{STT}`.
    - `STT` là số thứ tự tăng dần trong cùng năm `plan_year`, padding 3 chữ số (ví dụ: DA-2025-001, DA-2025-002...).
  - Không chấp nhận `project_code` trong request. Backend tự sinh và trả về ở response.
  - Kiểm tra hợp lệ: name không rỗng; plan_year có giá trị; start_date/end_date có giá trị và end_date >= start_date.
- Response:
  - 201 Created, body là Project Model (không kèm `docs`).

### 2) Update Project
- Method: PUT /api/projects/{id}
- Request body:
  - Cho phép truyền các trường của Project trừ `id` và `project_code`.
  - Chỉ cập nhật các trường có giá trị (non-null, non-empty). Trường null/empty sẽ bị bỏ qua và giữ nguyên giá trị cũ trong DB.
- Behavior/Logic:
  - Không cho phép thay đổi `id` và `project_code`.
  - Kiểm tra hợp lệ ngày: nếu có cả start_date và end_date thì end_date >= start_date.
- Response: 200 OK, body là Project Model.

### 3) Get Project Detail
- Method: GET /api/projects/{id}
- Response: 200 OK
```
{
  "id": "...",
  "project_code": "DA-2025-001",
  "name": "...",
  "description": "...",
  "plan_year": 2025,
  "start_date": "2025-01-01",
  "end_date": "2025-12-31",
  "status": "active",
  "deleted": false,
  "lead_department": "...",
  "lead_staff": "...",
  "project_manager": "...",
  "requirement": "...",     
  "options": "...",
  "docs": [                   
    { "id": "...", "name": "...", "path": "...", "size": 12345 },
    // ... chỉ gồm tài liệu thuộc trực tiếp dự án; không gồm tài liệu của package/contract
  ]
}
```
- 404 nếu không tồn tại hoặc đã bị xoá mềm.

### 4) List Projects
- Method: GET /api/projects
- Query params (hỗ trợ các filter):
  - `project_code`: exact match
  - `status`: 1 giá trị hoặc danh sách giá trị, phân tách bằng dấu phẩy
  - `start_date_from`, `start_date_to`: lọc theo khoảng ngày bắt đầu
  - `end_date_from`, `end_date_to`: lọc theo khoảng ngày kết thúc
  - `lead_department`, `lead_staff`, `project_manager`: partial match (không phân biệt hoa thường)
  - `requirement`: contains (tìm theo chuỗi trong requirement text)
- Response: Page<Project Model> (không kèm `docs`).
- Ghi chú: Tự động loại các bản ghi `deleted = true`.

---

## Security & Permission
- Quyền:
  - Admin (ROLE_admin): được phép tạo (POST), sửa (PUT), xoá (DELETE), xem (GET).
  - User (ROLE_user): chỉ được phép xem danh sách và chi tiết (GET).
- Áp dụng:
  - POST/PUT/DELETE yêu cầu ROLE_admin.
  - GET (list/detail) yêu cầu ROLE_admin hoặc ROLE_user.

## Soft Delete & Cascade
- Xoá dự án là xoá mềm (`deleted = true`).
- Cascade tối thiểu: các `Document` trực thuộc dự án sẽ được đánh dấu `deleted = true` cùng lúc.
- Các module khác (packages, contracts,...) loại trừ dữ liệu thuộc dự án đã `deleted = true`.

## Errors
- 400 Bad Request: Thiếu trường bắt buộc hoặc dữ liệu không hợp lệ (ví dụ: end_date < start_date).
- 404 Not Found: Dự án không tồn tại hoặc đã bị xoá.
- 403 Forbidden: Không đủ quyền thực hiện thao tác.

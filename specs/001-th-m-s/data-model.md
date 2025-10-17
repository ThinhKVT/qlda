# Data Model: Quản lý dự án

## Project
- id: Long, PK
- project_code: String, unique
- name: String
- description: String
- project_type: Enum (xây dựng, mua sắm hàng hoá, dịch vụ, bảo trì)
- plan_year: Integer/String
- start_date: Date
- end_date: Date
- status: Enum (active, deleted, completed, ...)
- deleted: Boolean
- lead_department: String // Phòng đầu mối quản lý dự án
- lead_staff: String/List // Cán bộ đầu mối dự án
- project_manager: String/List // Cán bộ QLDA
- stages: List<Stage>
- packages: List<Package>

## Package
- id: Long, PK
- package_code: String, unique
- project_id: Long, FK
- name: String
- field: String
- package_value: Decimal
- method: Enum (Đấu thầu rộng rãi, đấu thầu hạn chế, chỉ định thầu, ...)
- status: Enum (Đang đấu thầu, Đã hoàn thành, Chưa đấu thầu, ...)
- start_date: Date
- end_date: Date
- description: String
- deleted: Boolean

## Contract
- id: Long, PK
- contract_number: String, unique
- project_id: Long, FK
- package_id: Long, FK
- contractor_id: Long, FK
- name: String
- sign_date: Date
- effective_date: Date
- expire_date: Date
- value: Decimal
- paid_amount: Decimal
- contract_type: Enum (trọn gói, đơn giá, thời gian, tỷ lệ %)
- status: Enum (active, deleted, completed, ...)
- deleted: Boolean

## Document
- id: Long, PK
- project_id: Long, FK
- name: String
- document_number: String, unique
- sign_date: Date
- upload_date: Date
- uploader: String
- file_url: String
- type: Enum (Hợp đồng, Biên bản, Báo cáo, Quyết toán, Hồ sơ mời thầu, ...)
- status: Enum (active, deleted, ...)
- deleted: Boolean

## Contractor
- id: Long, PK
- name: String
- code: String, unique
- address: String
- phone: String
- email: String
- status: Enum (active, deleted, ...)
- deleted: Boolean

## Ràng buộc & Mối quan hệ
- Project 1-n Package, 1-n Contract, 1-n Document
- Package 1-n Contract
- Contract 1-1 Contractor
- Khi xoá mềm Project, các entity liên quan cũng đánh dấu deleted
- Các trường unique: project_code, package_code, contract_number, document_number, contractor.code
- Enum phải đồng bộ giữa DB và UI

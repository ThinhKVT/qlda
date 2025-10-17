-- Migration: Create tables for QLDA module
-- Date: 2025-10-14
-- Author: spec update

-- Project table
CREATE TABLE IF NOT EXISTS project (
  id BIGSERIAL PRIMARY KEY,
  project_code VARCHAR(100) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  project_type VARCHAR(50), -- Enum: xây dựng, mua sắm hàng hoá, dịch vụ, bảo trì
  plan_year VARCHAR(10),
  start_date DATE,
  end_date DATE,
  status VARCHAR(50), -- Enum: active, deleted, completed, ...
  deleted BOOLEAN DEFAULT FALSE,
  lead_department VARCHAR(255), -- Phòng đầu mối
  lead_staff VARCHAR(255), -- Cán bộ đầu mối
  project_manager VARCHAR(255) -- Cán bộ QLDA
);

-- Package table
CREATE TABLE IF NOT EXISTS package (
  id BIGSERIAL PRIMARY KEY,
  package_code VARCHAR(100) UNIQUE NOT NULL,
  project_id BIGINT REFERENCES project(id),
  name VARCHAR(255) NOT NULL,
  field VARCHAR(100),
  package_value NUMERIC(18,2),
  method VARCHAR(50), -- Enum: Đấu thầu rộng rãi, đấu thầu hạn chế, chỉ định thầu, ...
  status VARCHAR(50), -- Enum: Đang đấu thầu, Đã hoàn thành, Chưa đấu thầu, ...
  start_date DATE,
  end_date DATE,
  description TEXT,
  deleted BOOLEAN DEFAULT FALSE
);

-- Contractor table
CREATE TABLE IF NOT EXISTS contractor (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  code VARCHAR(100) UNIQUE NOT NULL,
  address VARCHAR(255),
  phone VARCHAR(50),
  email VARCHAR(100),
  status VARCHAR(50), -- Enum: active, deleted, ...
  deleted BOOLEAN DEFAULT FALSE
);

-- Contract table
CREATE TABLE IF NOT EXISTS contract (
  id BIGSERIAL PRIMARY KEY,
  contract_number VARCHAR(100) UNIQUE NOT NULL,
  project_id BIGINT REFERENCES project(id),
  package_id BIGINT REFERENCES package(id),
  contractor_id BIGINT REFERENCES contractor(id),
  name VARCHAR(255) NOT NULL,
  sign_date DATE,
  effective_date DATE,
  expire_date DATE,
  value NUMERIC(18,2),
  paid_amount NUMERIC(18,2),
  contract_type VARCHAR(50), -- Enum: trọn gói, đơn giá, thời gian, tỷ lệ %
  status VARCHAR(50), -- Enum: active, deleted, completed, ...
  deleted BOOLEAN DEFAULT FALSE
);

-- Document table
CREATE TABLE IF NOT EXISTS document (
  id BIGSERIAL PRIMARY KEY,
  project_id BIGINT REFERENCES project(id),
  name VARCHAR(255) NOT NULL,
  document_number VARCHAR(100) UNIQUE NOT NULL,
  sign_date DATE,
  upload_date DATE,
  uploader VARCHAR(100),
  file_url VARCHAR(255),
  type VARCHAR(50), -- Enum: Hợp đồng, Biên bản, Báo cáo, Quyết toán, Hồ sơ mời thầu, ...
  status VARCHAR(50), -- Enum: active, deleted, ...
  deleted BOOLEAN DEFAULT FALSE
);

-- Indexes and constraints
CREATE INDEX IF NOT EXISTS idx_project_code ON project(project_code);
CREATE INDEX IF NOT EXISTS idx_package_code ON package(package_code);
CREATE INDEX IF NOT EXISTS idx_contract_number ON contract(contract_number);
CREATE INDEX IF NOT EXISTS idx_document_number ON document(document_number);
CREATE INDEX IF NOT EXISTS idx_contractor_code ON contractor(code);

-- End of migration


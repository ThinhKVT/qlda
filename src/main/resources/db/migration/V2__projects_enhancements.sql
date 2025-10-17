-- V2: Enhance projects table with new fields and soft delete flag

ALTER TABLE projects ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE projects ADD COLUMN IF NOT EXISTS lead_department VARCHAR(255);
ALTER TABLE projects ADD COLUMN IF NOT EXISTS lead_staff VARCHAR(255);
ALTER TABLE projects ADD COLUMN IF NOT EXISTS project_manager VARCHAR(255);
ALTER TABLE projects ADD COLUMN IF NOT EXISTS requirement TEXT;
ALTER TABLE projects ADD COLUMN IF NOT EXISTS options TEXT;
ALTER TABLE projects ADD COLUMN IF NOT EXISTS deleted BOOLEAN DEFAULT FALSE;

CREATE INDEX IF NOT EXISTS idx_projects_code ON projects(code);
CREATE INDEX IF NOT EXISTS idx_projects_deleted ON projects(deleted);


-- Add deleted column to packages and contracts tables for soft delete support

-- Add deleted column to packages table
ALTER TABLE packages ADD COLUMN IF NOT EXISTS deleted BOOLEAN DEFAULT FALSE;

-- Add deleted column to contracts table
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS deleted BOOLEAN DEFAULT FALSE;

-- Update existing records to set deleted = false
UPDATE packages SET deleted = FALSE WHERE deleted IS NULL;
UPDATE contracts SET deleted = FALSE WHERE deleted IS NULL;

-- Add index for better query performance on soft delete queries
CREATE INDEX IF NOT EXISTS idx_packages_deleted ON packages(deleted);
CREATE INDEX IF NOT EXISTS idx_contracts_deleted ON contracts(deleted);


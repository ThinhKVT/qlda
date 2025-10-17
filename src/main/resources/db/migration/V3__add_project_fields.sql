-- Migration: Add new fields to projects table
-- project_type, investment_budget, currency, plan_type

ALTER TABLE projects
    ADD COLUMN IF NOT EXISTS project_type VARCHAR(50),
    ADD COLUMN IF NOT EXISTS investment_budget NUMERIC(19,2),
    ADD COLUMN IF NOT EXISTS currency VARCHAR(16),
    ADD COLUMN IF NOT EXISTS plan_type VARCHAR(64);


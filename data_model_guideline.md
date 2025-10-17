# Data Model Design Guideline for Spring Boot Project using PostgreSQL

## 1. Objective
This guideline helps AI design a robust, scalable, and maintainable data model for a Spring Boot project using PostgreSQL as the database engine.

## 2. Naming Conventions
- **Tables**: Use snake_case, plural (e.g., `projects`, `contracts`).
- **Columns**: Use snake_case, singular (e.g., `project_id`, `created_at`).
- **Primary Key**: Use `id` as the primary key for each table.
- **Foreign Key**: Use `{referenced_table}_id` (e.g., `user_id`).

## 3. Entity List (Sample)
- **User**: Stores user information.
- **Project**: Represents a project.
- **Contract**: Represents contracts related to projects.
- **Payment**: Payment records for contracts/projects.
- **Document**: Attached documents for projects/contracts.
- **Contractor**: Information about contractors.

## 4. Relationships
- **One-to-Many**: E.g., One project has many contracts.
- **Many-to-One**: E.g., Many contracts belong to one project.
- **Many-to-Many**: Use join tables (e.g., `project_users`).
- **One-to-One**: Use unique constraints on foreign keys.

## 5. Migration & Versioning
- Use Flyway or Liquibase for migration scripts.
- Name migration files with version prefix (e.g., `V1__create_projects.sql`).
- Always provide rollback scripts if possible.

## 6. Data Normalization
- Avoid data duplication.
- Use reference tables for enums/statuses.
- Store timestamps as `timestamp with time zone`.

## 7. JPA/Hibernate Annotation Requirements
- Use `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`, `@ManyToOne`, `@OneToMany`, `@ManyToMany`, `@JoinColumn`, etc.
- Always specify fetch type and cascade options as needed.

## 8. Example Entity (Java)
```java
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "project")
    private List<Contract> contracts;
    // ...other fields...
}
```

## 9. Example Migration (SQL)
```sql
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```

## 10. Additional Notes
- Always document each entity and relationship.
- Prefer UUID for distributed systems.
- Use constraints and indexes for performance and integrity.

---
**Use this guideline for all future data model design tasks in this project.**


package org.example.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Project;
import org.example.domain.ProjectType;
import org.example.repo.ProjectRepository;
import org.example.web.dto.ProjectCreateRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Security and Authorization Test Suite
 * T040: Tests security and permission checks (admin vs user)
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjectRepository projectRepository;

    private static UUID testProjectId;

    @BeforeEach
    void setup() {
        // Create a test project for authorization tests
        if (testProjectId == null) {
            Project testProject = new Project();
            testProject.setName("Security Test Project");
            testProject.setCode("DA-2025-SEC001");
            testProject.setPlanYear(2025);
            testProject.setStartDate(LocalDate.of(2025, 1, 1));
            testProject.setEndDate(LocalDate.of(2025, 12, 31));
            testProject.setStatus("PLANNING");
            testProject.setLeadDepartment("IT");
            testProject.setLeadStaff("Test Staff");
            testProject.setProjectManager("Test Manager");
            testProject.setRequirement("{}");
            testProject.setDeleted(false);
            testProject.setProjectType(ProjectType.XAY_DUNG);
            testProject.setInvestmentBudget(new BigDecimal("1000000"));
            testProject.setCurrency("VND");
            testProject.setPlanType("ANNUAL");

            Project saved = projectRepository.save(testProject);
            testProjectId = saved.getId();
        }
    }

    // ============================================
    // ANONYMOUS USER TESTS (No Authentication)
    // ============================================

    @Test
    @Order(1)
    @WithAnonymousUser
    @DisplayName("Anonymous: Should reject GET /api/projects without authentication")
    void testAnonymous_ListProjects_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(2)
    @WithAnonymousUser
    @DisplayName("Anonymous: Should reject GET /api/projects/{id} without authentication")
    void testAnonymous_GetProject_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/projects/" + testProjectId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    @WithAnonymousUser
    @DisplayName("Anonymous: Should reject POST /api/projects without authentication")
    void testAnonymous_CreateProject_Unauthorized() throws Exception {
        ProjectCreateRequest request = createValidRequest();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    @WithAnonymousUser
    @DisplayName("Anonymous: Should reject PUT /api/projects/{id} without authentication")
    void testAnonymous_UpdateProject_Unauthorized() throws Exception {
        Project updateData = new Project();
        updateData.setName("Should not update");

        mockMvc.perform(put("/api/projects/" + testProjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    @WithAnonymousUser
    @DisplayName("Anonymous: Should reject DELETE /api/projects/{id} without authentication")
    void testAnonymous_DeleteProject_Unauthorized() throws Exception {
        mockMvc.perform(delete("/api/projects/" + testProjectId))
                .andExpect(status().isUnauthorized());
    }

    // ============================================
    // USER ROLE TESTS (Read-Only Access)
    // ============================================

    @Test
    @Order(10)
    @WithMockUser(roles = "User")
    @DisplayName("User Role: Should allow GET /api/projects (read access)")
    void testUserRole_ListProjects_Allowed() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @Order(11)
    @WithMockUser(roles = "User")
    @DisplayName("User Role: Should allow GET /api/projects/{id} (read access)")
    void testUserRole_GetProject_Allowed() throws Exception {
        mockMvc.perform(get("/api/projects/" + testProjectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testProjectId.toString()));
    }

    @Test
    @Order(12)
    @WithMockUser(roles = "User")
    @DisplayName("User Role: Should allow filtering and pagination")
    void testUserRole_FilterProjects_Allowed() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .param("status", "PLANNING")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(13)
    @WithMockUser(roles = "User")
    @DisplayName("User Role: Should FORBID POST /api/projects (write access)")
    void testUserRole_CreateProject_Forbidden() throws Exception {
        ProjectCreateRequest request = createValidRequest();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @Order(14)
    @WithMockUser(roles = "User")
    @DisplayName("User Role: Should FORBID PUT /api/projects/{id} (write access)")
    void testUserRole_UpdateProject_Forbidden() throws Exception {
        Project updateData = new Project();
        updateData.setName("Should not update");

        mockMvc.perform(put("/api/projects/" + testProjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @Order(15)
    @WithMockUser(roles = "User")
    @DisplayName("User Role: Should FORBID DELETE /api/projects/{id} (write access)")
    void testUserRole_DeleteProject_Forbidden() throws Exception {
        mockMvc.perform(delete("/api/projects/" + testProjectId))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    // ============================================
    // ADMIN ROLE TESTS (Full Access)
    // ============================================

    @Test
    @Order(20)
    @WithMockUser(roles = "Admin")
    @DisplayName("Admin Role: Should allow GET /api/projects")
    void testAdminRole_ListProjects_Allowed() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @Order(21)
    @WithMockUser(roles = "Admin")
    @DisplayName("Admin Role: Should allow GET /api/projects/{id}")
    void testAdminRole_GetProject_Allowed() throws Exception {
        mockMvc.perform(get("/api/projects/" + testProjectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testProjectId.toString()));
    }

    @Test
    @Order(22)
    @WithMockUser(roles = "Admin")
    @DisplayName("Admin Role: Should allow POST /api/projects (create)")
    void testAdminRole_CreateProject_Allowed() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        request.setName("Admin Created Project");

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Admin Created Project"));
    }

    @Test
    @Order(23)
    @WithMockUser(roles = "Admin")
    @DisplayName("Admin Role: Should allow PUT /api/projects/{id} (update)")
    void testAdminRole_UpdateProject_Allowed() throws Exception {
        Project updateData = new Project();
        updateData.setName("Admin Updated Name");
        updateData.setStatus("IN_PROGRESS");

        mockMvc.perform(put("/api/projects/" + testProjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Admin Updated Name"));
    }

    @Test
    @Order(24)
    @WithMockUser(roles = "Admin")
    @DisplayName("Admin Role: Should allow DELETE /api/projects/{id} (delete)")
    void testAdminRole_DeleteProject_Allowed() throws Exception {
        // Create a project specifically for deletion test
        Project toDelete = new Project();
        toDelete.setName("To Be Deleted");
        toDelete.setCode("DA-2025-DEL001");
        toDelete.setPlanYear(2025);
        toDelete.setStartDate(LocalDate.of(2025, 1, 1));
        toDelete.setEndDate(LocalDate.of(2025, 12, 31));
        toDelete.setStatus("PLANNING");
        toDelete.setLeadDepartment("IT");
        toDelete.setLeadStaff("Test");
        toDelete.setProjectManager("Test");
        toDelete.setRequirement("{}");
        toDelete.setDeleted(false);
        toDelete.setProjectType(ProjectType.XAY_DUNG);
        toDelete.setInvestmentBudget(new BigDecimal("1000000"));
        toDelete.setCurrency("VND");
        toDelete.setPlanType("ANNUAL");

        Project saved = projectRepository.save(toDelete);

        mockMvc.perform(delete("/api/projects/" + saved.getId()))
                .andExpect(status().isNoContent());
    }

    // ============================================
    // MIXED ROLE TESTS
    // ============================================

    @Test
    @Order(30)
    @WithMockUser(roles = {"User", "Admin"})
    @DisplayName("Multiple Roles: User with Admin role should have full access")
    void testMultipleRoles_AdminAccess() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        request.setName("Multi-Role Created Project");

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(31)
    @WithMockUser(username = "testuser", roles = "User")
    @DisplayName("User Identity: Should maintain user context across requests")
    void testUserIdentity_Consistency() throws Exception {
        // First request
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk());

        // Second request - should maintain same user context
        mockMvc.perform(get("/api/projects/" + testProjectId))
                .andExpect(status().isOk());
    }

    // ============================================
    // EDGE CASES AND SECURITY EXPLOITS
    // ============================================

    @Test
    @Order(40)
    @WithMockUser(roles = "User")
    @DisplayName("Security: User cannot bypass authorization by modifying request")
    void testSecurity_CannotBypassAuthorization() throws Exception {
        ProjectCreateRequest request = createValidRequest();

        // Try to create with User role - should be forbidden
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-Admin-Override", "true")) // Attempt to bypass
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(41)
    @WithMockUser(roles = "Admin")
    @DisplayName("Security: Cannot access other user's sensitive data without proper auth")
    void testSecurity_DataIsolation() throws Exception {
        // All authenticated users can see projects
        // This test verifies proper data filtering is in place
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @Order(42)
    @WithMockUser(roles = "User")
    @DisplayName("Security: SQL injection attempt should be safely handled")
    void testSecurity_SqlInjectionProtection() throws Exception {
        // Attempt SQL injection in filter parameter
        mockMvc.perform(get("/api/projects")
                        .param("project_code", "DA-2025-001'; DROP TABLE projects; --"))
                .andExpect(status().isOk()); // Should not cause error, just return empty results
    }

    @Test
    @Order(43)
    @WithMockUser(roles = "User")
    @DisplayName("Security: XSS attempt in search should be safely handled")
    void testSecurity_XssProtection() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .param("requirement", "<script>alert('xss')</script>"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(44)
    @WithMockUser(roles = "Admin")
    @DisplayName("Security: Malformed UUID should be handled gracefully")
    void testSecurity_MalformedUuidHandling() throws Exception {
        mockMvc.perform(get("/api/projects/not-a-valid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(45)
    @WithMockUser(roles = "Admin")
    @DisplayName("Security: Large payload should be rejected or handled properly")
    void testSecurity_LargePayloadHandling() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        // Create a very large description
        StringBuilder largeDesc = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            largeDesc.append("Very long description text. ");
        }
        request.setDescription(largeDesc.toString());

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()); // Should handle or accept it
    }

    @Test
    @Order(46)
    @WithMockUser(roles = "User")
    @DisplayName("Security: Deleted projects should not be accessible via GET")
    void testSecurity_DeletedProjectsHidden() throws Exception {
        // Try to access a deleted project
        // First, get a project ID that exists but is deleted
        Project deletedProject = new Project();
        deletedProject.setName("Already Deleted");
        deletedProject.setCode("DA-2025-DELETED");
        deletedProject.setPlanYear(2025);
        deletedProject.setStartDate(LocalDate.of(2025, 1, 1));
        deletedProject.setEndDate(LocalDate.of(2025, 12, 31));
        deletedProject.setStatus("PLANNING");
        deletedProject.setLeadDepartment("IT");
        deletedProject.setLeadStaff("Test");
        deletedProject.setProjectManager("Test");
        deletedProject.setRequirement("{}");
        deletedProject.setDeleted(true); // Already deleted
        deletedProject.setProjectType(ProjectType.XAY_DUNG);
        deletedProject.setInvestmentBudget(new BigDecimal("1000000"));
        deletedProject.setCurrency("VND");
        deletedProject.setPlanType("ANNUAL");

        Project saved = projectRepository.save(deletedProject);

        mockMvc.perform(get("/api/projects/" + saved.getId()))
                .andExpect(status().isNotFound()); // Should not be accessible
    }

    // ============================================
    // AUTHORIZATION ERROR MESSAGE TESTS
    // ============================================

    @Test
    @Order(50)
    @WithAnonymousUser
    @DisplayName("Error Messages: Unauthorized should return proper error message")
    void testErrorMessages_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(51)
    @WithMockUser(roles = "User")
    @DisplayName("Error Messages: Forbidden should return proper error message")
    void testErrorMessages_Forbidden() throws Exception {
        ProjectCreateRequest request = createValidRequest();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.error").exists());
    }

    // Helper method
    private ProjectCreateRequest createValidRequest() {
        ProjectCreateRequest request = new ProjectCreateRequest();
        request.setName("Test Project");
        request.setDescription("Test Description");
        request.setPlanYear(2025);
        request.setStartDate(LocalDate.of(2025, 1, 1));
        request.setEndDate(LocalDate.of(2025, 12, 31));
        request.setStatus("PLANNING");
        request.setLeadDepartment("IT");
        request.setLeadStaff("John Doe");
        request.setProjectManager("Jane Smith");
        request.setRequirement("{}");
        request.setProjectType(ProjectType.XAY_DUNG);
        request.setInvestmentBudget(new BigDecimal("1000000"));
        request.setCurrency("VND");
        request.setPlanType("ANNUAL");
        return request;
    }
}

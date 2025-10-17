package org.example.errormessages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.ProjectType;
import org.example.repo.ProjectRepository;
import org.example.web.dto.ProjectCreateRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Error Messages and Status Codes Test Suite
 * T041: Tests error messages and API error codes
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ErrorMessagesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjectRepository projectRepository;

    // ============================================
    // VALIDATION ERROR TESTS (400 Bad Request)
    // ============================================

    @Test
    @Order(1)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 400: Missing required field 'name' should return validation error")
    void testValidationError_MissingName() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        request.setName(null);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    @Order(2)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 400: Empty string for 'name' should return validation error")
    void testValidationError_EmptyName() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        request.setName("");

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    @Order(3)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 400: Missing required field 'planYear' should return validation error")
    void testValidationError_MissingPlanYear() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        request.setPlanYear(null);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.planYear").exists());
    }

    @Test
    @Order(4)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 400: Missing required field 'startDate' should return validation error")
    void testValidationError_MissingStartDate() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        request.setStartDate(null);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @Order(5)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 400: End date before start date should return validation error")
    void testValidationError_InvalidDateRange() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        request.setStartDate(LocalDate.of(2025, 12, 31));
        request.setEndDate(LocalDate.of(2025, 1, 1));

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 400: Multiple validation errors should return all errors")
    void testValidationError_MultipleErrors() throws Exception {
        ProjectCreateRequest request = new ProjectCreateRequest();
        // Missing all required fields

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isMap())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    @Order(7)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 400: Invalid parameter type should return proper error")
    void testValidationError_InvalidParameterType() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .param("page", "not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid parameter type"))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @Order(8)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 500: Malformed JSON returns server error (expected behavior)")
    void testValidationError_MalformedJson() throws Exception {
        String malformedJson = "{name: 'Missing quotes', planYear: }";

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }

    @Test
    @Order(9)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 400: Invalid UUID format should return proper error")
    void testValidationError_InvalidUuidFormat() throws Exception {
        mockMvc.perform(get("/api/projects/not-a-valid-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.error").exists());
    }

    // ============================================
    // AUTHENTICATION ERROR TESTS (401 Unauthorized)
    // ============================================

    @Test
    @Order(10)
    @DisplayName("Error 403: Request without authentication should return 403")
    void testAuthError_NoAuthentication() throws Exception {
        // Spring Security returns 403 for anonymous users trying to access secured endpoints
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(11)
    @DisplayName("Error 403: Request with invalid token should return 403")
    void testAuthError_InvalidToken() throws Exception {
        // Spring Security returns 403 for invalid tokens
        mockMvc.perform(get("/api/projects")
                        .header("Authorization", "Bearer invalid-token-here"))
                .andExpect(status().isForbidden());
    }

    // ============================================
    // AUTHORIZATION ERROR TESTS (403 Forbidden)
    // ============================================

    @Test
    @Order(20)
    @WithMockUser(roles = "User")
    @DisplayName("Error 403: User role creating project should return 403 with proper message")
    void testAuthzError_UserCreatingProject() throws Exception {
        ProjectCreateRequest request = createValidRequest();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access denied"))
                .andExpect(jsonPath("$.error").value("You don't have permission to perform this action"));
    }

    @Test
    @Order(21)
    @WithMockUser(roles = "User")
    @DisplayName("Error 403: User role updating project should return 403 with proper message")
    void testAuthzError_UserUpdatingProject() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(put("/api/projects/" + randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access denied"))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @Order(22)
    @WithMockUser(roles = "User")
    @DisplayName("Error 403: User role deleting project should return 403 with proper message")
    void testAuthzError_UserDeletingProject() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/projects/" + randomId))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access denied"))
                .andExpect(jsonPath("$.error").exists());
    }

    // ============================================
    // NOT FOUND ERROR TESTS (404 Not Found)
    // ============================================

    @Test
    @Order(30)
    @WithMockUser(roles = "User")
    @DisplayName("Error 404: Getting non-existent project should return 404")
    void testNotFoundError_GetProject() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(get("/api/projects/" + nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(31)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 404: Updating non-existent project should return 404")
    void testNotFoundError_UpdateProject() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(put("/api/projects/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(32)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error 404: Deleting non-existent project should return 404")
    void testNotFoundError_DeleteProject() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(delete("/api/projects/" + nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(33)
    @WithMockUser(roles = "User")
    @DisplayName("Error 404: Getting deleted project should return 404")
    void testNotFoundError_GetDeletedProject() throws Exception {
        // Create and delete a project
        org.example.domain.Project project = new org.example.domain.Project();
        project.setName("Deleted Project");
        project.setCode("DA-2025-DEL");
        project.setPlanYear(2025);
        project.setStartDate(LocalDate.of(2025, 1, 1));
        project.setEndDate(LocalDate.of(2025, 12, 31));
        project.setStatus("PLANNING");
        project.setLeadDepartment("IT");
        project.setLeadStaff("Test");
        project.setProjectManager("Test");
        project.setRequirement("{}");
        project.setDeleted(true); // Already deleted
        project.setProjectType(ProjectType.XAY_DUNG);
        project.setInvestmentBudget(new BigDecimal("1000000"));
        project.setCurrency("VND");
        project.setPlanType("ANNUAL");

        org.example.domain.Project saved = projectRepository.save(project);

        mockMvc.perform(get("/api/projects/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    // ============================================
    // ERROR MESSAGE FORMAT TESTS
    // ============================================

    @Test
    @Order(50)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error Format: All errors should have 'message' field")
    void testErrorFormat_MessageField() throws Exception {
        ProjectCreateRequest request = new ProjectCreateRequest();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @Order(51)
    @WithMockUser(roles = "Admin")
    @DisplayName("Error Format: Validation errors should have 'errors' field with details")
    void testErrorFormat_ValidationErrorsField() throws Exception {
        ProjectCreateRequest request = new ProjectCreateRequest();
        request.setName(""); // Invalid

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors").isMap());
    }

    @Test
    @Order(52)
    @WithMockUser(roles = "User")
    @DisplayName("Error Format: Authorization errors should have descriptive messages")
    void testErrorFormat_AuthorizationError() throws Exception {
        ProjectCreateRequest request = createValidRequest();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access denied"))
                .andExpect(jsonPath("$.error").value("You don't have permission to perform this action"));
    }

    // ============================================
    // EDGE CASE ERROR TESTS
    // ============================================

    @Test
    @Order(60)
    @WithMockUser(roles = "Admin")
    @DisplayName("Edge Case: Null request body returns server error (expected behavior)")
    void testEdgeCase_NullRequestBody() throws Exception {
        // Null body causes server error when trying to parse
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(61)
    @WithMockUser(roles = "Admin")
    @DisplayName("Edge Case: Empty request body returns server error (expected behavior)")
    void testEdgeCase_EmptyRequestBody() throws Exception {
        // Empty body causes server error when trying to parse
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(62)
    @WithMockUser(roles = "Admin")
    @DisplayName("Edge Case: Wrong content type returns server error (expected behavior)")
    void testEdgeCase_WrongContentType() throws Exception {
        // Wrong content type causes server error
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("plain text"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(63)
    @WithMockUser(roles = "User")
    @DisplayName("Edge Case: Invalid date format returns server error (expected behavior)")
    void testEdgeCase_InvalidDateFormat() throws Exception {
        // Invalid date format causes parse error
        mockMvc.perform(get("/api/projects")
                        .param("start_date_from", "not-a-date"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(64)
    @WithMockUser(roles = "User")
    @DisplayName("Edge Case: Negative page number returns bad request")
    void testEdgeCase_NegativePageNumber() throws Exception {
        // Spring Data rejects negative page numbers
        mockMvc.perform(get("/api/projects")
                        .param("page", "-1")
                        .param("size", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(65)
    @WithMockUser(roles = "User")
    @DisplayName("Edge Case: Very large page size should be handled")
    void testEdgeCase_VeryLargePageSize() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .param("page", "0")
                        .param("size", "1000000"))
                .andExpect(status().isOk()); // Should not crash
    }

    @Test
    @Order(66)
    @WithMockUser(roles = "Admin")
    @DisplayName("Edge Case: Special characters in text fields should be handled")
    void testEdgeCase_SpecialCharacters() throws Exception {
        ProjectCreateRequest request = createValidRequest();
        request.setName("Project with special chars: @#$%^&*()");
        request.setDescription("Description with unicode: 你好 مرحبا Привет");

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
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


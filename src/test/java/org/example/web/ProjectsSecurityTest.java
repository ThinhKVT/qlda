package org.example.web;

import org.example.domain.Project;
import org.example.repo.DocumentRepository;
import org.example.repo.ProjectRepository;
import org.example.repo.ContractRepository;
import org.example.repo.TenderPackageRepository;
import org.example.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProjectsController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(ProjectsSecurityTest.TestConfig.class)
class ProjectsSecurityTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ProjectService projectService(ProjectRepository projectRepository,
                                            TenderPackageRepository packageRepository,
                                            ContractRepository contractRepository,
                                            DocumentRepository documentRepository) {
            return new ProjectService(projectRepository, packageRepository, contractRepository, documentRepository);
        }
    }

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    TenderPackageRepository packageRepository;

    @MockBean
    ContractRepository contractRepository;

    @MockBean
    DocumentRepository documentRepository;

    @Test
    void post_AsAdmin_Returns201() throws Exception {
        when(projectRepository.countByCodeStartingWith("DA-2025-")).thenReturn(0L);
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        String body = "{" +
                "\"name\":\"DA Test\"," +
                "\"plan_year\":2025," +
                "\"start_date\":\"2025-01-01\"," +
                "\"end_date\":\"2025-12-31\"," +
                "\"lead_department\":\"KH\"," +
                "\"lead_staff\":\"A\"," +
                "\"project_manager\":\"B\"," +
                "\"requirement\":\"[]\"," +
                "\"project_type\":\"XAY_DUNG\"," +
                "\"investment_budget\":0," +
                "\"currency\":\"VND\"," +
                "\"plan_type\":\"TypeA\"" +
                "}";

        mockMvc.perform(post("/api/projects")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void post_AsUser_Forbidden() throws Exception {
        String body = "{" +
                "\"name\":\"DA Test\"," +
                "\"plan_year\":2025," +
                "\"start_date\":\"2025-01-01\"," +
                "\"end_date\":\"2025-12-31\"," +
                "\"lead_department\":\"KH\"," +
                "\"lead_staff\":\"A\"," +
                "\"project_manager\":\"B\"," +
                "\"requirement\":\"[]\"," +
                "\"project_type\":\"XAY_DUNG\"," +
                "\"investment_budget\":0," +
                "\"currency\":\"VND\"," +
                "\"plan_type\":\"TypeA\"" +
                "}";

        mockMvc.perform(post("/api/projects")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    void delete_AsAnonymous_Unauthorized() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/projects/" + id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get_AsUser_Ok() throws Exception {
        UUID id = UUID.randomUUID();
        when(projectRepository.findById(eq(id))).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/projects/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user")))
                .andExpect(status().isNotFound());
    }
}

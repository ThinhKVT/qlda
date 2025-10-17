package org.example.web;

import org.example.domain.Project;
import org.example.domain.Document;
import org.example.repo.ProjectRepository;
import org.example.repo.DocumentRepository;
import org.example.repo.TenderPackageRepository;
import org.example.repo.ContractRepository;
import org.example.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProjectsController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
@Import(ProjectsControllerTest.TestConfig.class)
class ProjectsControllerTest {

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

    @BeforeEach
    void setup() {
        // default mocks
        when(projectRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(java.util.List.of()));
    }

    @Test
    @WithMockUser(roles = {"Admin"})
    void createProject_GeneratesCodeAndReturns201() throws Exception {
        when(projectRepository.countByCodeStartingWith("DA-2025-")).thenReturn(0L);
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        String body = "{" +
                "\"name\":\"Dự án A\"," +
                "\"plan_year\":2025," +
                "\"start_date\":\"2025-01-01\"," +
                "\"end_date\":\"2025-12-31\"," +
                "\"lead_department\":\"Kế hoạch\"," +
                "\"lead_staff\":\"Nguyen Van A\"," +
                "\"project_manager\":\"Tran Thi B\"," +
                "\"requirement\":\"[\\\"YC1\\\"]\"," +
                "\"project_type\":\"XAY_DUNG\"," +
                "\"investment_budget\":1000000," +
                "\"currency\":\"VND\"," +
                "\"plan_type\":\"TypeA\"" +
                "}";

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        Project saved = captor.getValue();
        assertThat(saved.getCode()).isEqualTo("DA-2025-001");
        assertThat(saved.getDeleted()).isFalse();
    }

    @Test
    @WithMockUser(roles = {"Admin"})
    void createProject_MissingRequired_Returns400() throws Exception {
        String body = "{" +
                "\"plan_year\":2025," +
                "\"start_date\":\"2025-01-01\"," +
                "\"end_date\":\"2025-12-31\"," +
                "\"lead_department\":\"Kế hoạch\"," +
                "\"lead_staff\":\"Nguyen Van A\"," +
                "\"project_manager\":\"Tran Thi B\"," +
                "\"requirement\":\"[\\\"YC1\\\"]\"," +
                "\"project_type\":\"XAY_DUNG\"," +
                "\"investment_budget\":1000000," +
                "\"currency\":\"VND\"," +
                "\"plan_type\":\"TypeA\"" +
                "}"; // thiếu name

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verify(projectRepository, never()).save(any());
    }

    @Test
    @WithMockUser(roles = {"Admin"})
    void updateProject_OnlyNonNullAndDoNotChangeCode() throws Exception {
        UUID id = UUID.randomUUID();
        Project ex = new Project();
        ex.setId(id);
        ex.setName("Old");
        ex.setPlanYear(2025);
        ex.setCode("DA-2025-001");
        ex.setStartDate(LocalDate.parse("2025-01-01"));
        ex.setEndDate(LocalDate.parse("2025-12-31"));
        when(projectRepository.findById(eq(id))).thenReturn(Optional.of(ex));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        String body = "{" +
                "\"name\":\"New\"," +
                "\"code\":\"SHOULD_IGNORE\"" +
                "}";
        mockMvc.perform(put("/api/projects/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        Project saved = captor.getValue();
        assertThat(saved.getName()).isEqualTo("New");
        assertThat(saved.getCode()).isEqualTo("DA-2025-001");
    }

    @Test
    @WithMockUser(roles = {"Admin"})
    void deleteProject_SoftDelete() throws Exception {
        UUID id = UUID.randomUUID();
        Project ex = new Project();
        ex.setId(id);
        ex.setDeleted(false);
        when(projectRepository.findById(eq(id))).thenReturn(Optional.of(ex));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(delete("/api/projects/" + id))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertThat(captor.getValue().getDeleted()).isTrue();
    }

    @Test
    @WithMockUser
    void listProjects_WithFilters_Returns200() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .param("project_code", "DA-2025-001")
                        .param("status", "active,completed")
                        .param("start_date_from", "2025-01-01")
                        .param("start_date_to", "2025-06-30")
                        .param("lead_department", "Kế hoạch"))
                .andExpect(status().isOk());

        verify(projectRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @WithMockUser(roles = {"Admin"})
    void deleteProject_CascadeMarksDocumentsDeleted() throws Exception {
        UUID id = UUID.randomUUID();
        Project ex = new Project();
        ex.setId(id);
        ex.setDeleted(false);
        when(projectRepository.findById(eq(id))).thenReturn(Optional.of(ex));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));
        Document d1 = new Document();
        d1.setId(UUID.randomUUID());
        d1.setProjectId(id);
        d1.setDeleted(false);
        when(documentRepository.findByProjectIdAndDeletedFalse(eq(id))).thenReturn(List.of(d1));

        mockMvc.perform(delete("/api/projects/" + id))
                .andExpect(status().isNoContent());

        verify(documentRepository).saveAll(argThat(list -> StreamSupport.stream(list.spliterator(), false).allMatch(doc -> Boolean.TRUE.equals(doc.getDeleted()))));
    }

    @Test
    @WithMockUser
    void getProjectDetail_ReturnsDetailWithDocs() throws Exception {
        UUID id = UUID.randomUUID();
        Project p = new Project();
        p.setId(id);
        p.setCode("DA-2025-001");
        p.setName("Project X");
        p.setDeleted(false);
        when(projectRepository.findById(eq(id))).thenReturn(Optional.of(p));
        Document d1 = new Document();
        d1.setId(UUID.randomUUID());
        d1.setProjectId(id);
        d1.setDeleted(false);
        d1.setName("doc1.pdf");
        d1.setPath("/uploads/doc1.pdf");
        d1.setSize(123L);
        when(documentRepository.findByProjectIdAndDeletedFalse(eq(id))).thenReturn(List.of(d1));

        mockMvc.perform(get("/api/projects/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project_code").value("DA-2025-001"))
                .andExpect(jsonPath("$.docs[0].name").value("doc1.pdf"));
    }
}

package org.example.web;

import org.example.domain.Document;
import org.example.repo.DocumentRepository;
import org.example.service.LocalStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Documents")
public class DocumentsController {
    private final LocalStorageService storageService;
    private final DocumentRepository documentRepository;

    public DocumentsController(LocalStorageService storageService, DocumentRepository documentRepository) {
        this.storageService = storageService;
        this.documentRepository = documentRepository;
    }

    @PostMapping(path = "/documents/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a document and optionally link to a project")
    public Document upload(@RequestPart("file") MultipartFile file,
                           @RequestParam(value = "projectId", required = false) UUID projectId) throws IOException {
        return storageService.save(file, projectId);
    }

    public record DownloadRequest(List<UUID> ids) {}

    @PostMapping("/documents/download")
    @Operation(summary = "Download multiple documents as a ZIP")
    public ResponseEntity<byte[]> downloadZip(@RequestBody DownloadRequest req) throws IOException {
        byte[] zip = storageService.zipByIds(req.ids());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documents.zip")
                .body(zip);
    }

    @GetMapping("/documents")
    @Operation(summary = "Search documents")
    public List<Document> search(@RequestParam Map<String, String> params) {
        if (params.containsKey("projectId")) {
            return documentRepository.findByProjectIdAndDeletedFalse(UUID.fromString(params.get("projectId")));
        }
        return documentRepository.findAll().stream().filter(d -> d.getDeleted() == null || !d.getDeleted()).toList();
    }

    @GetMapping("/projects/{projectId}/documents")
    @Operation(summary = "List documents by project (exclude deleted)")
    public List<Document> listByProject(@PathVariable UUID projectId) {
        return documentRepository.findByProjectIdAndDeletedFalse(projectId);
    }

    @PostMapping("/documents/{id}/link")
    @Operation(summary = "Link a document to a project (set projectId)")
    public ResponseEntity<Document> linkDocument(@PathVariable UUID id, @RequestParam("projectId") UUID projectId) {
        return documentRepository.findById(id)
                .map(d -> {
                    d.setProjectId(projectId);
                    Document saved = documentRepository.save(d);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/documents/{id}/unlink")
    @Operation(summary = "Unlink a document from its project (set projectId = null)")
    public ResponseEntity<Document> unlinkDocument(@PathVariable UUID id) {
        return documentRepository.findById(id)
                .map(d -> {
                    d.setProjectId(null);
                    Document saved = documentRepository.save(d);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

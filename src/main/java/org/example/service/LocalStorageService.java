package org.example.service;

import org.example.domain.Document;
import org.example.repo.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class LocalStorageService {
    private final Path root = Path.of("uploads");
    private final DocumentRepository documentRepository;

    public LocalStorageService(DocumentRepository documentRepository) throws IOException {
        this.documentRepository = documentRepository;
        Files.createDirectories(root);
    }

    public Document save(MultipartFile file, UUID projectId) throws IOException {
        Path dest = root.resolve(UUID.randomUUID() + "_" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setPath(dest.toString());
        doc.setSize(file.getSize());
        doc.setProjectId(projectId);
        return documentRepository.save(doc);
    }

    public byte[] zipByIds(List<UUID> ids) throws IOException {
        var docs = documentRepository.findAllById(ids);
        try (var baos = new java.io.ByteArrayOutputStream(); var zos = new ZipOutputStream(baos)) {
            for (var d : docs) {
                if (d.getPath() == null) continue;
                Path p = Path.of(d.getPath());
                if (!Files.exists(p)) continue;
                zos.putNextEntry(new ZipEntry(d.getName()));
                Files.copy(p, zos);
                zos.closeEntry();
            }
            zos.finish();
            return baos.toByteArray();
        }
    }
}


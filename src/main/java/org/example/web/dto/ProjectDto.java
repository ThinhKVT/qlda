package org.example.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ProjectDto(UUID id, String code, String name, Integer planYear, String status, LocalDate startDate, LocalDate endDate) {}


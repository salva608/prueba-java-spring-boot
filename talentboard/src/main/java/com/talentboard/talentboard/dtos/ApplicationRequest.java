package com.talentboard.talentboard.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {

    @NotNull(message = "Vacancy ID is required")
    private Long vacancyId;

    @NotNull(message = "Candidate ID is required")
    private Long candidateId;

    private String notes;
}
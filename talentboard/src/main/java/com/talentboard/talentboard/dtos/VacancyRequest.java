package com.talentboard.talentboard.dtos;

import com.talentboard.talentboard.models.enums.WorkMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class VacancyRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Work mode is required")
    private WorkMode workMode;

    @Positive(message = "Salary must be positive")
    private BigDecimal salary;

    @NotNull(message = "Recruiter ID is required")
    private Long recruiterId;
}
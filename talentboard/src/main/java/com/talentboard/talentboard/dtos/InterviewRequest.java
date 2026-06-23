package com.talentboard.talentboard.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InterviewRequest {

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Interview date and time is required")
    @FutureOrPresent(message = "Interview date cannot be in the past")
    private LocalDateTime interviewDate;

    @NotBlank(message = "Interview type is required")
    private String type;

    @NotNull(message = "Interviewer ID is required")
    private Long interviewerId;

    private String result;
    private String notes;
}
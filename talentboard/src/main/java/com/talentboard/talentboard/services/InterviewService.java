package com.talentboard.talentboard.services;

import com.talentboard.talentboard.dtos.InterviewRequest;
import com.talentboard.talentboard.models.Interview;
import java.util.List;

public interface InterviewService {
    Interview schedule(InterviewRequest request);
    List<Interview> findAll();
    Interview findById(Long id);
}
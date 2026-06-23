package com.talentboard.talentboard.services;

import com.talentboard.talentboard.dtos.ApplicationRequest;
import com.talentboard.talentboard.models.Application;
import com.talentboard.talentboard.models.enums.ApplicationStatus;
import java.util.List;

public interface ApplicationService {
    Application apply(ApplicationRequest request);
    List<Application> getHistoryByCandidate(Long candidateId);
    List<Application> findAll();
    Application findById(Long id);
    Application updateStatus(Long id, ApplicationStatus newStatus);
}
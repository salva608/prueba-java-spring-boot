package com.talentboard.talentboard.services.impl;

import com.talentboard.talentboard.dtos.ApplicationRequest;
import com.talentboard.talentboard.models.Application;
import com.talentboard.talentboard.models.User;
import com.talentboard.talentboard.models.Vacancy;
import com.talentboard.talentboard.models.enums.ApplicationStatus;
import com.talentboard.talentboard.models.enums.VacancyStatus;
import com.talentboard.talentboard.repositories.ApplicationRepository;
import com.talentboard.talentboard.repositories.UserRepository;
import com.talentboard.talentboard.repositories.VacancyRepository;
import com.talentboard.talentboard.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    @Override
    public Application apply(ApplicationRequest request) {
        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId())
                .orElseThrow(() -> new RuntimeException("Vacancy not found"));

        // Regla de negocio: No se podrán recibir postulaciones para vacantes cerradas [cite: 74]
        if (vacancy.getStatus() == VacancyStatus.CLOSED) {
            throw new RuntimeException("Cannot apply to a closed vacancy");
        }

        // Regla de negocio: Un candidato no podrá postularse más de una vez a la misma vacante [cite: 73]
        boolean alreadyApplied = applicationRepository.existsByCandidateIdAndVacancyId(
                request.getCandidateId(), request.getVacancyId());
        if (alreadyApplied) {
            throw new RuntimeException("Candidate has already applied to this vacancy");
        }

        User candidate = userRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Application application = Application.builder()
                .candidate(candidate)
                .vacancy(vacancy)
                .appliedAt(LocalDate.now())
                .status(ApplicationStatus.APPLIED) // Estado inicial del flujo [cite: 68]
                .notes(request.getNotes())
                .build();

        return applicationRepository.save(application);
    }

    @Override
    public List<Application> getHistoryByCandidate(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId);
    }

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public Application findById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    @Override
    public Application updateStatus(Long id, ApplicationStatus newStatus) {
        Application application = findById(id);
        application.setStatus(newStatus); // Modifica el avance del candidato en el proceso [cite: 67, 68]
        return applicationRepository.save(application);
    }
}
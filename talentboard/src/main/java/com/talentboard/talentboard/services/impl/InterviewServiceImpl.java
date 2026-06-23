package com.talentboard.talentboard.services.impl;

import com.talentboard.talentboard.dtos.InterviewRequest;
import com.talentboard.talentboard.models.Application;
import com.talentboard.talentboard.models.Interview;
import com.talentboard.talentboard.models.User;
import com.talentboard.talentboard.repositories.ApplicationRepository;
import com.talentboard.talentboard.repositories.InterviewRepository;
import com.talentboard.talentboard.repositories.UserRepository;
import com.talentboard.talentboard.services.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Override
    public Interview schedule(InterviewRequest request) {
        // Regla de negocio: Las entrevistas no podrán programarse en fechas anteriores a la fecha actual
        if (request.getInterviewDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Interview date cannot be in the past");
        }

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        User interviewer = userRepository.findById(request.getInterviewerId())
                .orElseThrow(() -> new RuntimeException("Interviewer not found"));

        Interview interview = Interview.builder()
                .application(application)
                .interviewDate(request.getInterviewDate())
                .type(request.getType())
                .interviewer(interviewer)
                .result(request.getResult())
                .notes(request.getNotes())
                .build();

        return interviewRepository.save(interview);
    }

    @Override
    public List<Interview> findAll() {
        return interviewRepository.findAll();
    }

    @Override
    public Interview findById(Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
    }
}
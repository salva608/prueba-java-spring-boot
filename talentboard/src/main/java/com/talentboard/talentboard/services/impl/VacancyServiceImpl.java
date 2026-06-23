package com.talentboard.talentboard.services.impl;

import com.talentboard.talentboard.dtos.VacancyRequest;
import com.talentboard.talentboard.models.User;
import com.talentboard.talentboard.models.Vacancy;
import com.talentboard.talentboard.models.enums.VacancyStatus;
import com.talentboard.talentboard.repositories.VacancyRepository;
import com.talentboard.talentboard.services.UserService;
import com.talentboard.talentboard.services.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Vacancy create(VacancyRequest request) {
        User recruiter = userService.findById(request.getRecruiterId());

        Vacancy vacancy = Vacancy.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .workMode(request.getWorkMode())
                .salary(request.getSalary())
                .createdAt(LocalDate.now())
                .status(VacancyStatus.OPEN) // Por defecto abre abierta
                .recruiter(recruiter)
                .build();

        return vacancyRepository.save(vacancy);
    }

    @Override
    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    @Override
    public Vacancy findById(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacancy not found with ID: " + id));
    }

    @Override
    public Vacancy update(Long id, VacancyRequest request) {
        Vacancy vacancy = findById(id);
        User recruiter = userService.findById(request.getRecruiterId());

        vacancy.setTitle(request.getTitle());
        vacancy.setDescription(request.getDescription());
        vacancy.setCategory(request.getCategory());
        vacancy.setWorkMode(request.getWorkMode());
        vacancy.setSalary(request.getSalary());
        vacancy.setRecruiter(recruiter);

        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy changeStatus(Long id, VacancyStatus status) {
        Vacancy vacancy = findById(id);
        vacancy.setStatus(status);
        return vacancyRepository.save(vacancy);
    }
}
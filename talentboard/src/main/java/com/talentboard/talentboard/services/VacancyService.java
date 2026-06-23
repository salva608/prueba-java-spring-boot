package com.talentboard.talentboard.services;

import com.talentboard.talentboard.models.Vacancy;
import com.talentboard.talentboard.dtos.VacancyRequest;
import com.talentboard.talentboard.models.enums.VacancyStatus;
import java.util.List;

public interface VacancyService {
    Vacancy create(VacancyRequest request);
    List<Vacancy> findAll();
    Vacancy findById(Long id);
    Vacancy update(Long id, VacancyRequest request);
    Vacancy changeStatus(Long id, VacancyStatus status);
}
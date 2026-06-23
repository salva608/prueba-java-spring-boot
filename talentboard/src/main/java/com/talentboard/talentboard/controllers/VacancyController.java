package com.talentboard.talentboard.controllers;

import com.talentboard.talentboard.dtos.VacancyRequest;
import com.talentboard.talentboard.models.Vacancy;
import com.talentboard.talentboard.models.enums.VacancyStatus;
import com.talentboard.talentboard.services.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<Vacancy> create(@Valid @RequestBody VacancyRequest request) {
        return ResponseEntity.ok(vacancyService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAll() {
        return ResponseEntity.ok(vacancyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(@PathVariable Long id, @Valid @RequestBody VacancyRequest request) {
        return ResponseEntity.ok(vacancyService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Vacancy> changeStatus(@PathVariable Long id, @RequestParam VacancyStatus status) {
        return ResponseEntity.ok(vacancyService.changeStatus(id, status));
    }
}
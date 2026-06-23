package com.talentboard.talentboard.controllers;

import com.talentboard.talentboard.dtos.ApplicationRequest;
import com.talentboard.talentboard.models.Application;
import com.talentboard.talentboard.models.enums.ApplicationStatus;
import com.talentboard.talentboard.services.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Application> apply(@Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.apply(request));
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAll() {
        return ResponseEntity.ok(applicationService.findAll());
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<Application>> getHistory(@PathVariable Long candidateId) {
        return ResponseEntity.ok(applicationService.getHistoryByCandidate(candidateId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(@PathVariable Long id, @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.updateStatus(id, status));
    }
}
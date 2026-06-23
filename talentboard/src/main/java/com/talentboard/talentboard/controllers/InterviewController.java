package com.talentboard.talentboard.controllers;

import com.talentboard.talentboard.dtos.InterviewRequest;
import com.talentboard.talentboard.models.Interview;
import com.talentboard.talentboard.services.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;


    @PostMapping
    public ResponseEntity<Interview> schedule(@Valid @RequestBody InterviewRequest request) {
        return ResponseEntity.ok(interviewService.schedule(request));
    }

    @GetMapping
    public ResponseEntity<List<Interview>> getAll() {
        return ResponseEntity.ok(interviewService.findAll());
    }
}
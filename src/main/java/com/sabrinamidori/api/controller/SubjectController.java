package com.sabrinamidori.api.controller;

import com.sabrinamidori.api.dto.subject.CreateSubjectRequest;
import com.sabrinamidori.api.dto.subject.SubjectResponse;
import com.sabrinamidori.api.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> create(@RequestBody CreateSubjectRequest request) {
        SubjectResponse response = subjectService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

package com.sabrinamidori.api.controller;

import com.sabrinamidori.api.dto.subject.SubjectRequest;
import com.sabrinamidori.api.dto.subject.SubjectResponse;
import com.sabrinamidori.api.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponse> createSubject(@RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.createSubject(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getSubjects(@RequestParam(required = false) String title) {
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(List.of(subjectService.getSubjectByTitle(title)));
        }

        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponse> updateSubject(@PathVariable UUID id,
                                                         @RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.updateSubject(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable UUID id) {
        subjectService.deleteSubject(id);

        return ResponseEntity.noContent().build();
    }
}

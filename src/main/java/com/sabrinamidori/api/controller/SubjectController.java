package com.sabrinamidori.api.controller;

import com.sabrinamidori.api.dto.subject.SubjectRequest;
import com.sabrinamidori.api.dto.subject.SubjectResponse;
import com.sabrinamidori.api.service.SubjectService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> post(@RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.createSubject(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(required = false) String title) {
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(List.of(subjectService.findSubjectByTitle(title)));
        }

        return ResponseEntity.ok(subjectService.findSubjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponse> update(@PathVariable UUID id,
                                                         @RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.updateSubject(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}

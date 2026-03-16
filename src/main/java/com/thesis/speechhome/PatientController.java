package com.thesis.speechhome;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PatientController {

    private final PatientRepository patientRepo;
    private final SessionRecordRepository sessionRepo;

    public PatientController(PatientRepository patientRepo,
                             SessionRecordRepository sessionRepo) {
        this.patientRepo = patientRepo;
        this.sessionRepo = sessionRepo;
    }

    @GetMapping("/patients/{patientId}")
    public Patient getPatient(@PathVariable Long patientId) {
        return patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    @GetMapping("/patients/{patientId}/sessions")
    public List<SessionRecord> getSessions(@PathVariable Long patientId) {
        return sessionRepo.findByPatientIdOrderByTimestampDesc(patientId);
    }

    @GetMapping("/therapist/patients")
    public List<Patient> getAllPatients() {
        return patientRepo.findAll().stream()
                .filter(p -> "PATIENT".equalsIgnoreCase(p.getRole()))
                .toList();
    }

    @PatchMapping("/patients/{patientId}/targets")
    public Patient updateTargets(@PathVariable Long patientId,
                                 @RequestParam Double targetLow,
                                 @RequestParam Double targetHigh) {
        Patient p = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        p.setTargetLow(targetLow);
        p.setTargetHigh(targetHigh);

        return patientRepo.save(p);
    }

    @PatchMapping("/patients/{patientId}/details")
    public Patient updatePatientDetails(@PathVariable Long patientId,
                                        @RequestParam String name,
                                        @RequestParam String language,
                                        @RequestParam(required = false) String dateOfBirth,
                                        @RequestParam String diagnosis,
                                        @RequestParam(required = false) String diagnosisOther) {
        Patient p = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        p.setName(name);
        p.setLanguage(language);

        if (dateOfBirth != null && !dateOfBirth.isBlank()) {
            p.setDateOfBirth(LocalDate.parse(dateOfBirth));
        } else {
            p.setDateOfBirth(null);
        }

        p.setDiagnosis(diagnosis);

        if ("Other".equalsIgnoreCase(diagnosis)) {
            p.setDiagnosisOther(diagnosisOther != null ? diagnosisOther.trim() : "");
        } else {
            p.setDiagnosisOther(null);
        }

        return patientRepo.save(p);
    }
}
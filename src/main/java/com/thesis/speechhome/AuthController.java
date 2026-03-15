package com.thesis.speechhome;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PatientRepository patientRepo;

    public AuthController(PatientRepository patientRepo) {
        this.patientRepo = patientRepo;
    }

    @PostMapping("/signup/patient")
    public Patient patientSignup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String language,
            @RequestParam String ageGroup) {

        return patientRepo.findByUsername(username)
                .orElseGet(() -> {
                    Patient p = new Patient();
                    p.setUsername(username);
                    p.setPassword(password);
                    p.setName(name);
                    p.setRole("PATIENT");
                    p.setLanguage(language);
                    p.setAgeGroup(ageGroup);
                    p.setDiagnosis("Dysarthria");
                    p.setTargetLow(2.8);
                    p.setTargetHigh(3.8);
                    return patientRepo.save(p);
                });
    }

    @PostMapping("/signup/therapist")
    public Patient therapistSignup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String name) {

        return patientRepo.findByUsername(username)
                .orElseGet(() -> {
                    Patient t = new Patient();
                    t.setUsername(username);
                    t.setPassword(password);
                    t.setName(name);
                    t.setRole("THERAPIST");
                    t.setLanguage("");
                    t.setAgeGroup("");
                    t.setDiagnosis("");
                    return patientRepo.save(t);
                });
    }

    @PostMapping("/login")
    public ResponseEntity<Patient> login(
            @RequestParam String username,
            @RequestParam String password) {

        return patientRepo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
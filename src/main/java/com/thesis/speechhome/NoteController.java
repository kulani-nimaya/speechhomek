package com.thesis.speechhome;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class NoteController {

    private final TherapistNoteRepository noteRepo;

    public NoteController(TherapistNoteRepository noteRepo) {
        this.noteRepo = noteRepo;
    }

    // ===============================
    // 🔹 GET FULL CHAT (ordered)
    // ===============================
    @GetMapping("/patients/{patientId}/messages")
    public List<TherapistNote> getMessages(@PathVariable Long patientId) {
        return noteRepo.findByPatientIdOrderByCreatedAtAsc(patientId);
    }

    // ===============================
    // 🔹 SEND MESSAGE (Therapist)
    // ===============================
    @PostMapping("/patients/{patientId}/messages/therapist")
    public TherapistNote sendFromTherapist(@PathVariable Long patientId,
                                           @RequestParam String content,
                                           @RequestParam(required = false) Long therapistId) {

        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setTherapistId(therapistId);
        note.setContent(content);
        note.setSender("THERAPIST");
        note.setType("TEXT");

        return noteRepo.save(note);
    }

    // ===============================
    // 🔹 SEND MESSAGE (Patient)
    // ===============================
    @PostMapping("/patients/{patientId}/messages/patient")
    public TherapistNote sendFromPatient(@PathVariable Long patientId,
                                         @RequestParam String content) {

        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setContent(content);
        note.setSender("PATIENT");
        note.setType("TEXT");

        return noteRepo.save(note);
    }

    // ===============================
    // 🔹 QUICK REPLY (Patient)
    // ===============================
    @PostMapping("/patients/{patientId}/messages/quick")
    public TherapistNote quickReply(@PathVariable Long patientId,
                                    @RequestParam String content) {

        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setContent(content);
        note.setSender("PATIENT");
        note.setType("QUICK");

        return noteRepo.save(note);
    }

    // ===============================
    // 🔹 SEND REMINDER (Therapist)
    // ===============================
    @PostMapping("/patients/{patientId}/reminder")
    public TherapistNote sendReminder(@PathVariable Long patientId,
                                      @RequestParam String content,
                                      @RequestParam(required = false) Long therapistId) {

        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setTherapistId(therapistId);
        note.setContent(content);
        note.setSender("THERAPIST");
        note.setType("REMINDER");

        return noteRepo.save(note);
    }

    // ===============================
    // 🔹 DELETE MESSAGE
    // ===============================
    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable Long id) {
        noteRepo.deleteById(id);
    }

    // ===============================
    // 🔹 MESSAGE COUNT (for badge)
    // ===============================
    @GetMapping("/patients/{patientId}/messages/count")
    public long getMessageCount(@PathVariable Long patientId) {
        return noteRepo.countByPatientId(patientId);
    }
}
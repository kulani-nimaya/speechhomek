package com.thesis.speechhome;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final TherapistNoteRepository noteRepo;

    public NoteController(TherapistNoteRepository noteRepo) {
        this.noteRepo = noteRepo;
    }

    // ✅ GET ALL MESSAGES
    @GetMapping("/patients/{patientId}/messages")
    public List<TherapistNote> getMessages(@PathVariable Long patientId) {
        return noteRepo.findByPatientIdOrderByCreatedAtAsc(patientId);
    }

    // ✅ THERAPIST SEND MESSAGE
    @PostMapping("/patients/{patientId}/messages/therapist")
    public TherapistNote sendTherapistMessage(
            @PathVariable Long patientId,
            @RequestParam String content) {

        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setContent(content);
        note.setSender("THERAPIST");
        note.setType("TEXT");

        return noteRepo.save(note);
    }

    // ✅ PATIENT SEND MESSAGE
    @PostMapping("/patients/{patientId}/messages/patient")
    public TherapistNote sendPatientMessage(
            @PathVariable Long patientId,
            @RequestParam String content) {

        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setContent(content);
        note.setSender("PATIENT");
        note.setType("TEXT");

        return noteRepo.save(note);
    }

    // ✅ QUICK REPLY
    @PostMapping("/patients/{patientId}/messages/quick")
    public TherapistNote quickReply(
            @PathVariable Long patientId,
            @RequestParam String content) {

        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setContent(content);
        note.setSender("PATIENT");
        note.setType("QUICK");

        return noteRepo.save(note);
    }

    // ✅ REMINDER
    @PostMapping("/patients/{patientId}/messages/reminder")
    public TherapistNote sendReminder(
            @PathVariable Long patientId,
            @RequestParam String content) {

        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setContent(content);
        note.setSender("THERAPIST");
        note.setType("REMINDER");

        return noteRepo.save(note);
    }

    // ✅ DELETE
    @DeleteMapping("/notes/{noteId}")
    public void delete(@PathVariable Long noteId) {
        noteRepo.deleteById(noteId);
    }
}
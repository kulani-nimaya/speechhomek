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

    @GetMapping("/patients/{patientId}/notes")
    public List<TherapistNote> getNotes(@PathVariable Long patientId) {
        return noteRepo.findTop8ByPatientIdOrderByCreatedAtDesc(patientId);
    }

    @PostMapping("/patients/{patientId}/notes")
    public TherapistNote addNote(@PathVariable Long patientId,
                                 @RequestParam String content,
                                 @RequestParam(required = false) Long therapistId) {
        TherapistNote note = new TherapistNote();
        note.setPatientId(patientId);
        note.setTherapistId(therapistId);
        note.setContent(content);
        return noteRepo.save(note);
    }

    @DeleteMapping("/notes/{noteId}")
    public void deleteNote(@PathVariable Long noteId) {
        noteRepo.deleteById(noteId);
    }
}
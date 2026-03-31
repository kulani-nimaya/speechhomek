package com.thesis.speechhome;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TherapistNoteRepository extends JpaRepository<TherapistNote, Long> {

    // 🔹 Full conversation (chat style - oldest → newest)
    List<TherapistNote> findByPatientIdOrderByCreatedAtAsc(Long patientId);

    // 🔹 Latest messages (for preview / badge count)
    List<TherapistNote> findTop8ByPatientIdOrderByCreatedAtDesc(Long patientId);

    // 🔹 Count messages (for notification badge)
    long countByPatientId(Long patientId);
}
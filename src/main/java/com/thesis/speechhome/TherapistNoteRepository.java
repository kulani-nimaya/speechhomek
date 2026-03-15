package com.thesis.speechhome;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TherapistNoteRepository extends JpaRepository<TherapistNote, Long> {

    List<TherapistNote> findTop8ByPatientIdOrderByCreatedAtDesc(Long patientId);
}
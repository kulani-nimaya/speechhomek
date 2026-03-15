package com.thesis.speechhome;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRecordRepository extends JpaRepository<SessionRecord, Long> {
    List<SessionRecord> findByPatientIdOrderByTimestampDesc(Long patientId);
}
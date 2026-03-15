package com.thesis.speechhome;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SessionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private LocalDateTime timestamp;

    private int syllables;
    private double recordingDurationSec;
    private double speakingSpanSec;
    private double syllablesPerSec;
    private String feedback;

    public Long getId() { return id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public int getSyllables() { return syllables; }
    public void setSyllables(int syllables) { this.syllables = syllables; }

    public double getRecordingDurationSec() { return recordingDurationSec; }
    public void setRecordingDurationSec(double recordingDurationSec) { this.recordingDurationSec = recordingDurationSec; }

    public double getSpeakingSpanSec() { return speakingSpanSec; }
    public void setSpeakingSpanSec(double speakingSpanSec) { this.speakingSpanSec = speakingSpanSec; }

    public double getSyllablesPerSec() { return syllablesPerSec; }
    public void setSyllablesPerSec(double syllablesPerSec) { this.syllablesPerSec = syllablesPerSec; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
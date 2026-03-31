package com.thesis.speechhome;

import jakarta.persistence.*;

@Entity
public class PracticeGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private int dailyTarget;
    private String reminderTime;

    public Long getId() { return id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public int getDailyTarget() { return dailyTarget; }
    public void setDailyTarget(int dailyTarget) { this.dailyTarget = dailyTarget; }

    public String getReminderTime() { return reminderTime; }
    public void setReminderTime(String reminderTime) { this.reminderTime = reminderTime; }
}
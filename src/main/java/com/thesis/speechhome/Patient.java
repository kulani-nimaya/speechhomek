package com.thesis.speechhome;

import jakarta.persistence.*;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String role;       // PATIENT or THERAPIST
    private String name;

    private String language;
    private String ageGroup;
    private String diagnosis;

    private Double targetLow;
    private Double targetHigh;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Double getTargetLow() {
        return targetLow;
    }

    public void setTargetLow(Double targetLow) {
        this.targetLow = targetLow;
    }

    public Double getTargetHigh() {
        return targetHigh;
    }

    public void setTargetHigh(Double targetHigh) {
        this.targetHigh = targetHigh;
    }
}
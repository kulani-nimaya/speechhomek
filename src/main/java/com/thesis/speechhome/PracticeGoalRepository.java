package com.thesis.speechhome;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PracticeGoalRepository extends JpaRepository<PracticeGoal, Long> {
    Optional<PracticeGoal> findByPatientId(Long patientId);
}
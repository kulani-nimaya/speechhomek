package com.thesis.speechhome;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PracticeGoalController {

    private final PracticeGoalRepository repo;

    public PracticeGoalController(PracticeGoalRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/patients/{id}/goal")
    public PracticeGoal getGoal(@PathVariable Long id) {
        return repo.findByPatientId(id).orElse(null);
    }

    @PostMapping("/patients/{id}/goal")
    public PracticeGoal saveGoal(@PathVariable Long id,
                                 @RequestParam int dailyTarget,
                                 @RequestParam String reminderTime) {

        PracticeGoal g = repo.findByPatientId(id).orElse(new PracticeGoal());
        g.setPatientId(id);
        g.setDailyTarget(dailyTarget);
        g.setReminderTime(reminderTime);

        return repo.save(g);
    }
}
package com.thesis.speechhome;

import java.util.List;

public class RateCalculator {

    public static class Result {
        public final int syllables;
        public final double recordingDurationSec;
        public final double speakingSpanSec;
        public final double syllablesPerSec;
        public final String feedback;
        public final double targetLow;
        public final double targetHigh;
        public final double averageRms;
        public final String loudnessFeedback;

        public Result(int syllables,
                      double recordingDurationSec,
                      double speakingSpanSec,
                      double syllablesPerSec,
                      String feedback,
                      double targetLow,
                      double targetHigh,
                      double averageRms,
                      String loudnessFeedback) {
            this.syllables = syllables;
            this.recordingDurationSec = recordingDurationSec;
            this.speakingSpanSec = speakingSpanSec;
            this.syllablesPerSec = syllablesPerSec;
            this.feedback = feedback;
            this.targetLow = targetLow;
            this.targetHigh = targetHigh;
            this.averageRms = averageRms;
            this.loudnessFeedback = loudnessFeedback;
        }
    }

    private final double targetLow;
    private final double targetHigh;

    public RateCalculator(double targetLow, double targetHigh) {
        this.targetLow = targetLow;
        this.targetHigh = targetHigh;
    }

    public Result compute(List<Double> nuclei, double recordingDurationSec, double averageRms) {
        int syllables = nuclei.size();

        double speakingSpanSec = recordingDurationSec;
        if (nuclei.size() >= 2) {
            speakingSpanSec = nuclei.get(nuclei.size() - 1) - nuclei.get(0);
        }

        double sps = (speakingSpanSec > 0) ? (syllables / speakingSpanSec) : 0.0;

        String feedback;
        if (sps < 2.0) feedback = "Very slow";
        else if (sps < targetLow) feedback = "Slightly slow";
        else if (sps <= targetHigh) feedback = "Good pace";
        else if (sps <= 4.5) feedback = "Slightly fast";
        else feedback = "Very fast";

        String loudnessFeedback;
        if (averageRms < 0.02) loudnessFeedback = "Soft voice";
        else if (averageRms < 0.06) loudnessFeedback = "Moderate voice";
        else loudnessFeedback = "Loud voice";

        return new Result(
                syllables,
                recordingDurationSec,
                speakingSpanSec,
                sps,
                feedback,
                targetLow,
                targetHigh,
                averageRms,
                loudnessFeedback
        );
    }
}
package com.thesis.speechhome;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final SessionRecordRepository sessionRepo;
    private final PatientRepository patientRepo;

    public AnalysisController(SessionRecordRepository sessionRepo,
                              PatientRepository patientRepo) {
        this.sessionRepo = sessionRepo;
        this.patientRepo = patientRepo;
    }

    @PostMapping("/patients/{patientId}/analyze")
    public RateCalculator.Result analyze(
            @PathVariable Long patientId,
            @RequestParam("audio") MultipartFile audioFile) throws Exception {

        File wav = File.createTempFile("upload-", ".wav");
        Files.write(wav.toPath(), audioFile.getBytes());

        PraatRunner runner = new PraatRunner();
        File tg = runner.runPraat(wav);

        SyllableExtractor extractor = new SyllableExtractor();
        List<Double> nuclei = extractor.readNuclei(tg);

        double duration = getDurationSeconds(wav);
        double averageRms = calculateAverageRms(wav);

        double defaultLow = 2.8;
        double defaultHigh = 3.8;

        Patient patient = patientRepo.findById(patientId).orElse(null);
        double targetLow = defaultLow;
        double targetHigh = defaultHigh;

        if (patient != null) {
            if (patient.getTargetLow() != null) targetLow = patient.getTargetLow();
            if (patient.getTargetHigh() != null) targetHigh = patient.getTargetHigh();
        }

        RateCalculator calc = new RateCalculator(targetLow, targetHigh);
        RateCalculator.Result result = calc.compute(nuclei, duration, averageRms);

        SessionRecord sr = new SessionRecord();
        sr.setPatientId(patientId);
        sr.setTimestamp(LocalDateTime.now());
        sr.setSyllables(result.syllables);
        sr.setRecordingDurationSec(result.recordingDurationSec);
        sr.setSpeakingSpanSec(result.speakingSpanSec);
        sr.setSyllablesPerSec(result.syllablesPerSec);
        sr.setFeedback(result.feedback);
        sessionRepo.save(sr);

        return result;
    }

    private double getDurationSeconds(File wav) throws Exception {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(wav)) {
            AudioFormat format = ais.getFormat();
            long frames = ais.getFrameLength();
            return frames / format.getFrameRate();
        }
    }

    private double calculateAverageRms(File wav) throws Exception {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(wav)) {
            AudioFormat format = ais.getFormat();

            if (format.getSampleSizeInBits() != 16) {
                throw new IllegalArgumentException("Only 16-bit WAV supported for RMS calculation.");
            }

            byte[] audioBytes = ais.readAllBytes();
            int bytesPerSample = format.getSampleSizeInBits() / 8;

            long sampleCount = 0;
            double sumSquares = 0.0;

            for (int i = 0; i + 1 < audioBytes.length; i += bytesPerSample) {
                short sample;
                if (format.isBigEndian()) {
                    sample = (short) (((audioBytes[i] & 0xFF) << 8) | (audioBytes[i + 1] & 0xFF));
                } else {
                    sample = (short) (((audioBytes[i + 1] & 0xFF) << 8) | (audioBytes[i] & 0xFF));
                }

                double normalized = sample / 32768.0;
                sumSquares += normalized * normalized;
                sampleCount++;
            }

            if (sampleCount == 0) return 0.0;
            return Math.sqrt(sumSquares / sampleCount);
        }
    }
}
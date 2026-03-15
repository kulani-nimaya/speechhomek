package com.thesis.speechhome;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SyllableExtractor {

    public List<Double> readNuclei(File textGrid) throws Exception {
        List<Double> nuclei = new ArrayList<>();
        List<String> lines = Files.readAllLines(textGrid.toPath());

        for (String line : lines) {
            String t = line.trim();
            if (t.startsWith("number =")) {
                String value = t.substring(t.indexOf('=') + 1).trim();
                try {
                    nuclei.add(Double.parseDouble(value));
                } catch (NumberFormatException ignored) {}
            }
        }
        return nuclei;
    }
}
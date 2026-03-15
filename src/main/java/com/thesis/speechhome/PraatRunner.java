package com.thesis.speechhome;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class PraatRunner {

    public File runPraat(File wav) throws IOException, InterruptedException {

        File rootDir = new File(System.getProperty("user.dir"));

        File praatExeGui = new File(rootDir, "tools\\Praat.exe");
        File praatExeCon = new File(rootDir, "tools\\praatcon.exe");
        File praatExe = praatExeCon.exists() ? praatExeCon : praatExeGui;

        File script = new File(rootDir, "tools\\detect_syllables.praat");
        File tgOut = new File(rootDir, "output.TextGrid");

        if (!praatExe.exists()) throw new IOException("Praat not found: " + praatExe.getAbsolutePath());
        if (!script.exists()) throw new IOException("Script not found: " + script.getAbsolutePath());
        if (!wav.exists()) throw new IOException("WAV not found: " + wav.getAbsolutePath());

        String[] cmd = new String[] {
                praatExe.getAbsolutePath(),
                "--run",
                script.getAbsolutePath(),
                wav.getAbsolutePath(),
                tgOut.getAbsolutePath()
        };

        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.directory(rootDir);
        pb.redirectErrorStream(true);

        Process p = pb.start();

        String output;
        try (InputStream is = p.getInputStream()) {
            output = new String(is.readAllBytes(), Charset.defaultCharset());
        }

        int code = p.waitFor();

        if (code != 0) {
            throw new IOException(
                    "Praat failed (exit " + code + ")\n\nCommand:\n" + String.join(" ", cmd) +
                            "\n\nOutput:\n" + output
            );
        }

        if (!tgOut.exists()) {
            throw new IOException("TextGrid was not created: " + tgOut.getAbsolutePath() + "\nOutput:\n" + output);
        }

        return tgOut;
    }
}
package org.Darwyi.logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SevereLogFilter implements LogFilter {

    @Override
    public void filter(String source_file, String target_file, LogLevel level) {
        long processed = 0;
        long matched = 0;

        try (
            FileReader     fr      = new FileReader(source_file);
            BufferedReader br      = new BufferedReader(fr);
            Scanner        scanner = new Scanner(br);

            FileWriter     fw      = new FileWriter(target_file);
            BufferedWriter bw      = new BufferedWriter(fw);
            PrintWriter    pw      = new PrintWriter(bw)
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processed++;

                LogLevel lineLevel = LogLevel.fromLine(line);
                if (lineLevel != null && lineLevel.isAtLeastAsSevereAs(level)) {
                    pw.println(line);
                    matched++;
                }
            }

            System.out.printf(
                "Оброблено рядків: %d; записано: %d; відсіяно: %d.%n",
                processed, matched, processed - matched);

        } catch (IOException e) {
            System.err.println("Помилка вводу/виводу: " + e.getMessage());
        }
    }
}

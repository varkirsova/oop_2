package oop_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvProcessor {

    public static void process(Path filePath) throws IOException {
        Statistic stats = new Statistic();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    String city = parts[0].trim();
                    String street = parts[1].trim();
                    String house = parts[2].trim();
                    int floor = parseFloor(parts[3].trim());

                    stats.addRecord(city, street, house, floor);
                }
            }
        }

        stats.printStatistics();
    }

    private static int parseFloor(String floorStr) {
        try {
            int f = Integer.parseInt(floorStr);
            if (f >= 1 && f <= 5) return f;
        } catch (NumberFormatException ignored) { }
        return 0;
    }
}

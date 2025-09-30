package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvUtils {
    private static final Logger logger = LogManager.getLogger(CsvUtils.class);

    public static String readFirstValue(String csvPath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(csvPath));
            for (String line : lines) {
                if (line == null) continue;
                String v = line.split(",")[0].trim();
                if (!v.isBlank()) {
                    logger.info("CSV'den okunan değer: '{}'", v);
                    return v;
                }
            }
        } catch (Exception e) {
            logger.error("CSV okunamadı: {}", csvPath, e);
        }
        throw new IllegalStateException("CSV'de uygun bir değer bulunamadı: " + csvPath);
    }
}

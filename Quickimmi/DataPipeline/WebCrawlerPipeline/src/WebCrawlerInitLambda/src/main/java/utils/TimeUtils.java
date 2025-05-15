package utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String getCurrentUTCTime() {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        ZoneId zoneId = ZoneId.of("UTC"); // Change to your desired time zone
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(zoneId);
        return formatter.format(instant);
    }
}

package servіce;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class TimeService {

    public static String getTimeDateTimeZoneByZoneId(String zoneId) {
        if(!checkTimeZoneValidity( zoneId)) {
            return "time zone does not exist";
        }
        ZoneId zone = ZoneId.of(zoneId);
        ZonedDateTime time = ZonedDateTime.now(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss  'UTC'xxx");
        String formattedDateTime = time.format(formatter);
        return formattedDateTime;
    }
    public static boolean checkTimeZoneValidity(String nameZone) {
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        return availableZoneIds.contains(nameZone);
    }
}

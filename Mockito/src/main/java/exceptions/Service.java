package exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Service
{
    public static final DateTimeFormatter LDT_FORMATTER  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

    public String getMessage()
    {
        String dateTimeString = LDT_FORMATTER.format(LocalDateTime.now());
        return "Current time is " + dateTimeString;
    }
}

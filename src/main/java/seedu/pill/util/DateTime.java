package seedu.pill.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    private LocalDateTime dateTime;

    public DateTime() {
        this.dateTime = LocalDateTime.now();
    }

    public DateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormattedDateTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    public String getFormattedDate() {
        return getFormattedDateTime("yyyy-MM-dd");
    }

    public String getFormattedTime() {
        return getFormattedDateTime("HH:mm:ss");
    }

    public boolean isAfter(DateTime other) {
        return this.dateTime.isAfter(other.getDateTime());
    }

    public boolean isBefore(DateTime other) {
        return this.dateTime.isBefore(other.getDateTime());
    }

    public long getDaysUntil(DateTime other) {
        return java.time.Duration.between(this.dateTime, other.getDateTime()).toDays();
    }

    public boolean isExpired(DateTime expirationDate) {
        return this.dateTime.isAfter(expirationDate.getDateTime());
    }

    public long getDaysUntilExpiration(DateTime expirationDate) {
        return getDaysUntil(expirationDate);
    }

    public boolean isWithinRefillPeriod(int daysBeforeRefill) {
        LocalDateTime refillDate = this.dateTime.plusDays(daysBeforeRefill);
        return LocalDateTime.now().isAfter(refillDate);
    }

    @Override
    public String toString() {
        return getFormattedDateTime("yyyy-MM-dd HH:mm:ss");
    }
}

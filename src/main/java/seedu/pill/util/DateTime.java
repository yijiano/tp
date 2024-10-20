package seedu.pill.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTime implements Comparable<DateTime> {
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

    @Override
    public int compareTo(DateTime other) {
        return this.dateTime.compareTo(other.getDateTime());
    }

    public boolean isAfter(DateTime other) {
        return this.compareTo(other) > 0;
    }

    public boolean isBefore(DateTime other) {
        return this.compareTo(other) < 0;
    }

    public long getDaysUntil(DateTime other) {
        return ChronoUnit.DAYS.between(this.dateTime, other.getDateTime());
    }

    public boolean isExpired(DateTime expirationDate) {
        return this.isAfter(expirationDate);
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

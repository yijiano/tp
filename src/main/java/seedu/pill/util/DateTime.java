package seedu.pill.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * A wrapper class for LocalDateTime that provides additional utility methods for date and time operations.
 * This class is used for handling dates, times, and datetime operations in the Pill application.
 * It implements Comparable to allow for chronological ordering of DateTime instances.
 */
public class DateTime implements Comparable<DateTime> {
    private LocalDateTime dateTime;

    /**
     * Constructs a new DateTime instance set to the current date and time.
     */
    public DateTime() {
        this.dateTime = LocalDateTime.now();
    }

    /**
     * Constructs a new DateTime instance with the specified LocalDateTime.
     *
     * @param dateTime - The LocalDateTime to initialize this DateTime with
     */
    public DateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns the underlying LocalDateTime instance.
     *
     * @return - The LocalDateTime instance representing this DateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the underlying LocalDateTime instance.
     *
     * @param dateTime - The new LocalDateTime to set
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Formats the datetime using the specified format pattern.
     *
     * @param format - The format pattern to use (following DateTimeFormatter patterns)
     * @return       - A string representation of the datetime in the specified format
     */
    public String getFormattedDateTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    /**
     * Returns the date component formatted as "yyyy-MM-dd".
     *
     * @return - A string representation of the date
     */
    public String getFormattedDate() {
        return getFormattedDateTime("yyyy-MM-dd");
    }

    /**
     * Returns the time component formatted as "HH:mm:ss".
     *
     * @return - A string representation of the time
     */
    public String getFormattedTime() {
        return getFormattedDateTime("HH:mm:ss");
    }

    /**
     * Compares this DateTime with another DateTime for ordering.
     * The comparison is based on chronological order.
     *
     * @param other - The DateTime to compare with
     * @return      - A negative integer if this DateTime is earlier, zero if they're equal,
     *                or a positive integer if this DateTime is later
     */
    @Override
    public int compareTo(DateTime other) {
        return this.dateTime.compareTo(other.getDateTime());
    }

    /**
     * Checks if this DateTime is chronologically after the specified DateTime.
     *
     * @param other - The DateTime to compare with
     * @return      - True if this DateTime is after the other DateTime, false otherwise
     */
    public boolean isAfter(DateTime other) {
        return this.compareTo(other) > 0;
    }

    /**
     * Checks if this DateTime is chronologically before the specified DateTime.
     *
     * @param other - The DateTime to compare with
     * @return      - True if this DateTime is before the other DateTime, false otherwise
     */
    public boolean isBefore(DateTime other) {
        return this.compareTo(other) < 0;
    }

    /**
     * Calculates the number of days between this DateTime and another DateTime.
     *
     * @param other - The DateTime to calculate the difference to
     * @return      - The number of days between the two DateTimes
     */
    public long getDaysUntil(DateTime other) {
        return ChronoUnit.DAYS.between(this.dateTime, other.getDateTime());
    }

    /**
     * Checks if this DateTime has passed the expiration DateTime.
     *
     * @param expirationDate - The DateTime representing the expiration date
     * @return               - True if this DateTime is after the expiration date, false otherwise
     */
    public boolean isExpired(DateTime expirationDate) {
        return this.isAfter(expirationDate);
    }

    /**
     * Calculates the number of days until the expiration date.
     *
     * @param expirationDate - The DateTime representing the expiration date
     * @return               - The number of days until expiration
     */
    public long getDaysUntilExpiration(DateTime expirationDate) {
        return getDaysUntil(expirationDate);
    }

    /**
     * Checks if this DateTime falls within the refill period.
     * A DateTime is within the refill period if the current time is after
     * this DateTime plus the specified number of days.
     *
     * @param daysBeforeRefill - The number of days to consider for the refill period
     * @return                 - True if within the refill period, false otherwise
     */
    public boolean isWithinRefillPeriod(int daysBeforeRefill) {
        LocalDateTime refillDate = this.dateTime.plusDays(daysBeforeRefill);
        return LocalDateTime.now().isAfter(refillDate);
    }

    /**
     * Returns a string representation of this DateTime in the format "yyyy-MM-dd HH:mm:ss".
     *
     * @return - A string representation of this DateTime
     */
    @Override
    public String toString() {
        return getFormattedDateTime("yyyy-MM-dd HH:mm:ss");
    }
}

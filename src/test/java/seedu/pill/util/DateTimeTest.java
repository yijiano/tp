package seedu.pill.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeTest {

    @Test
    public void constructor_noArguments_createsCurrentTime() {
        DateTime dateTime = new DateTime();
        LocalDateTime now = LocalDateTime.now();

        // Allow 1 second difference to account for test execution time
        long diffInSeconds = ChronoUnit.SECONDS.between(dateTime.getDateTime(), now);
        assertTrue(Math.abs(diffInSeconds) <= 1);
    }

    @Test
    public void constructor_withLocalDateTime_storesCorrectly() {
        LocalDateTime testDateTime = LocalDateTime.of(2024, 1, 1, 12, 0);
        DateTime dateTime = new DateTime(testDateTime);
        assertEquals(testDateTime, dateTime.getDateTime());
    }

    @Test
    public void getFormattedDateTime_customFormat_returnsCorrectFormat() {
        LocalDateTime testDateTime = LocalDateTime.of(2024, 1, 1, 12, 30, 45);
        DateTime dateTime = new DateTime(testDateTime);
        assertEquals("2024-01-01 12:30:45", dateTime.getFormattedDateTime("yyyy-MM-dd HH:mm:ss"));
        assertEquals("01/01/2024", dateTime.getFormattedDateTime("dd/MM/yyyy"));
    }

    @Test
    public void getFormattedDate_returnsCorrectFormat() {
        LocalDateTime testDateTime = LocalDateTime.of(2024, 1, 1, 12, 30, 45);
        DateTime dateTime = new DateTime(testDateTime);
        assertEquals("2024-01-01", dateTime.getFormattedDate());
    }

    @Test
    public void getFormattedTime_returnsCorrectFormat() {
        LocalDateTime testDateTime = LocalDateTime.of(2024, 1, 1, 12, 30, 45);
        DateTime dateTime = new DateTime(testDateTime);
        assertEquals("12:30:45", dateTime.getFormattedTime());
    }

    @Test
    public void compareTo_withDifferentDates_returnsCorrectOrder() {
        DateTime earlier = new DateTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        DateTime later = new DateTime(LocalDateTime.of(2024, 1, 2, 12, 0));

        assertTrue(earlier.compareTo(later) < 0);
        assertTrue(later.compareTo(earlier) > 0);
        assertEquals(0, earlier.compareTo(new DateTime(earlier.getDateTime())));
    }

    @Test
    public void isAfter_withDifferentDates_returnsCorrectBoolean() {
        DateTime earlier = new DateTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        DateTime later = new DateTime(LocalDateTime.of(2024, 1, 2, 12, 0));

        assertFalse(earlier.isAfter(later));
        assertTrue(later.isAfter(earlier));
    }

    @Test
    public void isBefore_withDifferentDates_returnsCorrectBoolean() {
        DateTime earlier = new DateTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        DateTime later = new DateTime(LocalDateTime.of(2024, 1, 2, 12, 0));

        assertTrue(earlier.isBefore(later));
        assertFalse(later.isBefore(earlier));
    }

    @Test
    public void getDaysUntil_withDifferentDates_returnsCorrectDays() {
        DateTime start = new DateTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        DateTime end = new DateTime(LocalDateTime.of(2024, 1, 10, 12, 0));

        assertEquals(9, start.getDaysUntil(end));
        assertEquals(-9, end.getDaysUntil(start));
    }

    @Test
    public void isExpired_withDifferentDates_returnsCorrectBoolean() {
        DateTime current = new DateTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        DateTime expired = new DateTime(LocalDateTime.of(2023, 12, 31, 12, 0));
        DateTime notExpired = new DateTime(LocalDateTime.of(2024, 1, 2, 12, 0));

        assertTrue(current.isExpired(expired));
        assertFalse(current.isExpired(notExpired));
    }

    @Test
    public void getDaysUntilExpiration_withDifferentDates_returnsCorrectDays() {
        DateTime current = new DateTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        DateTime expiration = new DateTime(LocalDateTime.of(2024, 1, 10, 12, 0));

        assertEquals(9, current.getDaysUntilExpiration(expiration));
    }

    @Test
    public void isWithinRefillPeriod_withDifferentScenarios_returnsCorrectBoolean() {
        DateTime baseDate = new DateTime(LocalDateTime.now().minusDays(10));

        // Test with 5 days refill period (should return true as base date is 10 days ago)
        assertTrue(baseDate.isWithinRefillPeriod(5));

        // Test with 15 days refill period (should return false as base date is only 10 days ago)
        assertFalse(baseDate.isWithinRefillPeriod(15));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        LocalDateTime testDateTime = LocalDateTime.of(2024, 1, 1, 12, 30, 45);
        DateTime dateTime = new DateTime(testDateTime);
        assertEquals("2024-01-01 12:30:45", dateTime.toString());
    }

    @Test
    public void setDateTime_changesDateTime() {
        DateTime dateTime = new DateTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        LocalDateTime newDateTime = LocalDateTime.of(2024, 1, 2, 12, 0);

        dateTime.setDateTime(newDateTime);
        assertEquals(newDateTime, dateTime.getDateTime());
    }
}

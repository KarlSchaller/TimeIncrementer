/**
 * Testcases for the TimeIncrementer system.
 *
 * @author Karl Schaller
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

public class TimeIncrementerTest {

    TimeIncrementer timeIncrementer;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setup() {
        timeIncrementer = new TimeIncrementer();
    }

    @After
    public void tearDown() {
    }

    /**
     * Military time should show hours from 0-23
     */
    @Test
    public void testMilitaryTime() {
        assertEquals("Military time should display 00:00:00 after 0 hours, 0 minutes, and 0 seconds",
                "00:00:00", timeIncrementer.getMilitaryTime());
        for (int i = 0; i < 23; i++)
            timeIncrementer.incrementHour();
        for (int i = 0; i < 7; i++)
            timeIncrementer.incrementMinute();
        for (int i = 0; i < 32; i++)
            timeIncrementer.incrementSecond();
        assertEquals("Military time should display 23:07:32 after 23 hours, 7 minutes, and 32 seconds",
                "23:07:32", timeIncrementer.getMilitaryTime());
    }

    /**
     * Standard time should show hours from 12, 1, 2, ..., 11 AM then 12, 1, 2, ..., 11 PM
     */
    @Test
    public void testStandardTime() {
        assertEquals("Standard time should display 12:00:00AM after 0 hours, 0 minutes, and 0 seconds",
                "12:00:00AM", timeIncrementer.getStandardTime());
        for (int i = 0; i < 15; i++)
            timeIncrementer.incrementHour();
        for (int i = 0; i < 7; i++)
            timeIncrementer.incrementMinute();
        for (int i = 0; i < 32; i++)
            timeIncrementer.incrementSecond();
        assertEquals("Standard time should display 03:07:32PM after 15 hours, 7 minutes, and 32 seconds",
                "03:07:32PM", timeIncrementer.getStandardTime());
    }

    /**
     * Default constructor should initialize time to 00:00:00
     */
    @Test
    public void testDefaultConstructor() {
        timeIncrementer = new TimeIncrementer();
        assertEquals("Default constructor should initialize time to 00:00:00",
                "00:00:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * Int constructor should initialize time to arg1:arg2:arg3, setting negatives to zero and rolling over time
     */
    @Test
    public void testIntConstructor() {
        timeIncrementer = new TimeIncrementer(12, 13, 14);
        assertEquals("Int constructor should initialize time to 12:13:14 with arguments (12, 13, 14)",
                "12:13:14", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer(-12, -13, 14);
        assertEquals("Int constructor should initialize time to 00:00:14 with arguments (-12, -13, 14)",
                "00:00:14", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer(50, 125, 125);
        assertEquals("Int constructor should initialize time to 04:07:05 with arguments (50, 125, 125)",
                "04:07:05", timeIncrementer.getMilitaryTime());
    }

    /**
     * String constructor should initialize time to the military time argument (subject to roll over) or 00:00:00 if invalid
     */
    @Test
    public void testStringConstructor() {
        timeIncrementer = new TimeIncrementer("15:07:32");
        assertEquals("String constructor should initialize time to 15:07:32 with argument \"15:07:32\"",
                "15:07:32", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer("29:07:72");
        assertEquals("String constructor should initialize time to 05:08:12 with argument \"29:07:72\"",
                "05:08:12", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer("15:invalid:32");
        assertEquals("String constructor should initialize time to 00:00:00 with argument \"15:invalid:32\"",
                "00:00:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * TimeIncrementer constructor should initialize time to the same time as the provided TimeIncrementer
     */
    @Test
    public void testTimeIncrementerConstructor() {
        TimeIncrementer temp = new TimeIncrementer();
        for (int i = 0; i < 15; i++)
            temp.incrementHour();
        for (int i = 0; i < 7; i++)
            temp.incrementMinute();
        for (int i = 0; i < 32; i++)
            temp.incrementSecond();
        timeIncrementer = new TimeIncrementer(temp);
        assertEquals("Default constructor should initialize time to 15:07:32",
                "15:07:32", timeIncrementer.getMilitaryTime());
    }

    /**
     * Incrementing seconds 7 times from 00:00:00 should result with 00:00:07
     */
    @Test
    public void testIncrementSeconds() {
        for (int i = 0; i < 7; i++)
            timeIncrementer.incrementSecond();
        assertEquals("Should display 00:00:07 after incrementing seconds 7 times",
                "00:00:07", timeIncrementer.getMilitaryTime());
    }

    /**
     * Incrementing minutes 7 times from 00:00:00 should result with 00:07:00
     */
    @Test
    public void testIncrementMinutes() {
        for (int i = 0; i < 7; i++)
            timeIncrementer.incrementMinute();
        assertEquals("Should display 00:07:00 after incrementing minutes 7 times",
                "00:07:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * Incrementing hours 7 times from 00:00:00 should result with 07:00:00
     */
    @Test
    public void testIncrementHours() {
        for (int i = 0; i < 7; i++)
            timeIncrementer.incrementHour();
        assertEquals("Should display 07:00:00 after incrementing hours 7 times",
                "07:00:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * Decrementing seconds 7 times from 00:00:14 should result with 00:00:07
     */
    @Test
    public void testDecrementSeconds() {
        timeIncrementer = new TimeIncrementer(0, 0, 14);
        for (int i = 0; i < 7; i++)
            timeIncrementer.decrementSecond();
        assertEquals("Should display 00:00:07 after decrementing seconds 7 times from 00:00:14",
                "00:00:07", timeIncrementer.getMilitaryTime());
    }

    /**
     * Decrementing minutes 7 times from 00:14:00 should result with 00:07:00
     */
    @Test
    public void testDecrementMinutes() {
        timeIncrementer = new TimeIncrementer(0, 14, 0);
        for (int i = 0; i < 7; i++)
            timeIncrementer.decrementMinute();
        assertEquals("Should display 00:07:00 after decrementing minutes 7 times from 00:14:00",
                "00:07:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * Decrementing hours 7 times from 14:00:00 should result with 07:00:00
     */
    @Test
    public void testDecrementHours() {
        timeIncrementer = new TimeIncrementer(14, 0, 0);
        for (int i = 0; i < 7; i++)
            timeIncrementer.decrementHour();
        assertEquals("Should display 07:00:00 after decrementing hours 7 times from 14:00:00",
                "07:00:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * Incrementing seconds above 59 should roll over
     */
    @Test
    public void testSecondRollOver() {
        timeIncrementer = new TimeIncrementer(0, 10, 59);
        timeIncrementer.incrementSecond();
        assertEquals("Should display 00:11:00 after incrementing seconds from 00:10:59",
                "00:11:00", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer(0, 59, 59);
        timeIncrementer.incrementSecond();
        assertEquals("Should display 01:00:00 after incrementing seconds from 00:59:59",
                "01:00:00", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer(23, 59, 59);
        timeIncrementer.incrementSecond();
        assertEquals("Should display 00:00:00 after incrementing seconds from 23:59:59",
                "00:00:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * Incrementing minutes above 59 should roll over
     */
    @Test
    public void testMinuteRollOver() {
        timeIncrementer = new TimeIncrementer(10, 59, 0);
        timeIncrementer.incrementMinute();
        assertEquals("Should display 11:00:00 after incrementing minute from 10:59:00",
                "11:00:00", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer(23, 59, 0);
        timeIncrementer.incrementMinute();
        assertEquals("Should display 00:00:00 after incrementing minute from 23:59:00",
                "00:00:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * Incrementing hours above 23 should roll over
     */
    @Test
    public void testHourRollOver() {
        timeIncrementer = new TimeIncrementer(23, 10, 59);
        timeIncrementer.incrementHour();
        assertEquals("Should display 00:10:59 after incrementing hours from 23:10:59",
                "00:10:59", timeIncrementer.getMilitaryTime());
    }

    /**
     * Decrementing seconds above 0 should roll under
     */
    @Test
    public void testSecondRollUnder() {
        timeIncrementer = new TimeIncrementer(0, 10, 0);
        timeIncrementer.decrementSecond();
        assertEquals("Should display 00:09:59 after decrementing seconds from 00:10:00",
                "00:09:59", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer(10, 0, 0);
        timeIncrementer.decrementSecond();
        assertEquals("Should display 09:59:59 after decrementing seconds from 10:00:00",
                "09:59:59", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer(0, 0, 0);
        timeIncrementer.decrementSecond();
        assertEquals("Should display 23:59:59 after decrementing seconds from 00:00:00",
                "23:59:59", timeIncrementer.getMilitaryTime());
    }

    /**
     * Decrementing minutes above 0 should roll under
     */
    @Test
    public void testMinuteRollUnder() {
        timeIncrementer = new TimeIncrementer(10, 0, 0);
        timeIncrementer.decrementMinute();
        assertEquals("Should display 09:59:00 after decrementing minute from 10:00:00",
                "09:59:00", timeIncrementer.getMilitaryTime());
        timeIncrementer = new TimeIncrementer(0, 0, 0);
        timeIncrementer.decrementMinute();
        assertEquals("Should display 23:59:00 after decrementing minute from 00:00:00",
                "23:59:00", timeIncrementer.getMilitaryTime());
    }

    /**
     * Decrementing hours above 0 should roll under
     */
    @Test
    public void testHourRollUnder() {
        timeIncrementer = new TimeIncrementer(0, 10, 59);
        timeIncrementer.decrementHour();
        assertEquals("Should display 23:10:59 after decrementing hours from 00:10:59",
                "23:10:59", timeIncrementer.getMilitaryTime());
    }
}

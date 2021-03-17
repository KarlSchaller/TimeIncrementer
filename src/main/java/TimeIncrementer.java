/**
 * The TimeIncrementer class provides functionality to increment/decrement using a time based format (60 seconds in a
 * minute, 60 minutes in an hour, 24 hours in a day, etc.). The counter may be initialized to any time, but once
 * initialized can only be incremented or decremented from.
 *
 * Increments and decrements are by one second, one minute, or one hour. Day is not part of the counter. In situations
 * where the provided increment or decrement would change the day, the time is rolled over/under (similar to a clock).
 *
 * Display output is available for both standard and military time.
 *
 * @author Karl Schaller
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeIncrementer {

    private int hours;
    private int minutes;
    private int seconds;

    /**
     * Creates a TimeIncrementer with the time 00:00:00.
     */
    public TimeIncrementer() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    /**
     * Creates a TimeIncrementer with the specified hours, minutes, and seconds.
     * Negative values will be set to zero and values greater than the intended range will be subject to roll over.
     *
     * @param hours The initial hours of the TimeIncrementer. Should be 0-23 inclusive.
     * @param minutes The initial minutes of the TimeIncrementer. Should be 0-59 inclusive.
     * @param seconds The initial seconds of the TimeIncrementer. Should be 0-59 inclusive.
     */
    public TimeIncrementer(int hours, int minutes, int seconds) {
        if (hours < 0)
            hours = 0;
        if (minutes < 0)
            minutes = 0;
        if (seconds < 0)
            seconds = 0;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        secondRollOver();
        minuteRollOver();
        hourRollOver();
    }

    /**
     * Creates a TimeIncrementer with the specified time.
     *
     * @param militaryTime The initial time of the TimeIncrementer.
     *                    Should be in military time format hh:mm:ss
     *                     with hours 0-23, minutes 0-59, and seconds 0-59 inclusive.
     */
    public TimeIncrementer(String militaryTime) {
        Pattern pattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
        Matcher matcher = pattern.matcher(militaryTime);
        boolean valid = matcher.matches();

        if (valid) {
            this.hours = Integer.parseInt(militaryTime.substring(0, 2));
            this.minutes = Integer.parseInt(militaryTime.substring(3, 5));
            this.seconds = Integer.parseInt(militaryTime.substring(6, 8));
            secondRollOver();
            minuteRollOver();
            hourRollOver();
        }
        else {
            this.hours = 0;
            this.minutes = 0;
            this.seconds = 0;
        }
    }

    /**
     * Creates a TimeIncrementer with the same time as the specified TimeIncrementer.
     *
     * @param timeIncrementer A TimeIncrementer to clone
     */
    public TimeIncrementer(TimeIncrementer timeIncrementer) {
        this.hours = timeIncrementer.hours;
        this.minutes = timeIncrementer.minutes;
        this.seconds = timeIncrementer.seconds;
    }

    /**
     * @return The seconds of the TimeIncrementer
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * @return The minutes of the TimeIncrementer
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @return The hours of the TimeIncrementer
     */
    public int getHours() {
        return hours;
    }

    /**
     * Increments the time by one second, rolling over if necessary.
     */
    public void incrementSecond() {
        seconds++;
        secondRollOver();
    }

    /**
     * Increments the time by one minute, rolling over if necessary.
     */
    public void incrementMinute() {
        minutes++;
        minuteRollOver();
    }

    /**
     * Increments the time by one hour, rolling over if necessary.
     */
    public void incrementHour() {
        hours++;
        hourRollOver();
    }

    /**
     * Decrements the time by one second, rolling under if necessary.
     */
    public void decrementSecond() {
        seconds--;
        secondRollUnder();
    }

    /**
     * Decrements the time by one minute, rolling under if necessary.
     */
    public void decrementMinute() {
        minutes--;
        minuteRollUnder();
    }

    /**
     * Decrements the time by one hour, rolling under if necessary.
     */
    public void decrementHour() {
        hours--;
        hourRollUnder();
    }

    /**
     * @return A String representing the time in standard time in the format hh:mm:ssAMPM.
     * For example, 12:04:32AM or 04:23:43PM.
     */
    public String getStandardTime() {
        String AMPM = "AM";
        int newHours = hours;
        if (hours > 12) {
            newHours -= 12;
            AMPM = "PM";
        }
        else if (hours == 0)
            newHours = 12;

        String hourStr = String.valueOf(newHours);
        String minuteStr = String.valueOf(minutes);
        String secondStr = String.valueOf(seconds);

        if (newHours < 10)
            hourStr = "0" + hourStr;
        if (minutes < 10)
            minuteStr = "0" + minuteStr;
        if (seconds < 10)
            secondStr = "0" + secondStr;

        return hourStr + ":" + minuteStr + ":" + secondStr + AMPM;
    }

    /**
     * @return A String representing the time in military time in the format hh:mm:ss.
     * For example, 00:04:32 or 16:23:43.
     */
    public String getMilitaryTime() {
        String hourStr = String.valueOf(hours);
        String minuteStr = String.valueOf(minutes);
        String secondStr = String.valueOf(seconds);

        if (hours < 10)
            hourStr = "0" + hourStr;
        if (minutes < 10)
            minuteStr = "0" + minuteStr;
        if (seconds < 10)
            secondStr = "0" + secondStr;

        return hourStr + ":" + minuteStr + ":" + secondStr;
    }

    private void secondRollOver() {
        if (seconds >= 60) {
            minutes += seconds/60;
            seconds %= 60;
            minuteRollOver();
        }
    }

    private void minuteRollOver() {
        if (minutes >= 60) {
            hours += minutes/60;
            minutes %= 60;
            hourRollOver();
        }
    }

    private void hourRollOver() {
        if (hours >= 24) {
            hours %= 24;
        }
    }

    private void secondRollUnder() {
        if (seconds < 0) {
            minutes += (seconds / 60 ) - 1;
            seconds = 60 + (seconds % 60);
            minuteRollUnder();
        }
    }

    private void minuteRollUnder() {
        if (minutes < 0) {
            hours += (minutes / 60) - 1;
            minutes = 60 + (minutes % 60);
            hourRollUnder();
        }
    }

    private void hourRollUnder() {
        if (hours < 0) {
            hours = 24 + (hours % 24);
        }
    }
}

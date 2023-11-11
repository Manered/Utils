package dev.manere.utils.scheduler;

import dev.manere.utils.scheduler.builder.SchedulerBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Utility class for converting between time units and game ticks.
 * <p>
 * This class provides constants and static methods to convert between
 * real-world time units (seconds, minutes, hours, days) and game ticks.
 * <p>
 * It assumes the following conversion rate:
 * <ul>
 * <li>20L ticks = 1 second</li>
 * <li>1200L ticks = 1 minute</li>
 * <li>72000L ticks = 1 hour</li>
 * <li>1728000L ticks = 1 day</li>
 * </ul>
 *
 * @see Schedulers
 * @see AsyncScheduler
 * @see SyncScheduler
 * @see SchedulerBuilder
 */
public class TickTimes {
    public static long SECOND = oneSecondToTicks();
    public static long MINUTE = oneMinuteToTicks();
    public static long HOUR = oneHourToTicks();
    public static long DAY = oneDayToTicks();

    /**
     * Converts 1 second to ticks.
     *
     * @return Ticks equivalent to 1 second.
     */
    public static long oneSecondToTicks() {
        return 20L;
    }

    /**
     * Converts 1 minute to ticks.
     *
     * @return Ticks equivalent to 1 minute.
     */
    public static long oneMinuteToTicks() {
        return 1200L;
    }

    /**
     * Converts 1 hour to ticks.
     *
     * @return Ticks equivalent to 1 hour.
     */
    public static long oneHourToTicks() {
        return 72000L;
    }

    /**
     * Converts 1 day to ticks.
     *
     * @return Ticks equivalent to 1 day.
     */
    public static long oneDayToTicks() {
        return 1728000L;
    }

    /**
     * Converts seconds to ticks.
     *
     * @param seconds Seconds value to convert
     * @return Equivalent ticks
     */
    public static long secondsToTicks(int seconds) {
        return (long) seconds * oneSecondToTicks();
    }

    /**
     * Converts minutes to ticks.
     *
     * @param minutes Minutes value to convert
     * @return Equivalent ticks
     */
    public static long minutesToTicks(int minutes) {
        return (long) minutes * oneMinuteToTicks();
    }

    /**
     * Converts hours to ticks.
     *
     * @param hours Hours value to convert
     * @return Equivalent ticks
     */
    public static long hoursToTicks(int hours) {
        return (long) hours * oneHourToTicks();
    }

    /**
     * Converts days to ticks.
     *
     * @param days Days value to convert
     * @return Equivalent ticks
     */
    public static long daysToTicks(int days) {
        return (long) days * oneDayToTicks();
    }

    /**
     * Converts seconds to ticks.
     *
     * @param seconds Seconds value to convert
     * @return Equivalent ticks
     */
    public static long secondsToTicks(long seconds) {
        return seconds * oneSecondToTicks();
    }

    /**
     * Converts minutes to ticks.
     *
     * @param minutes Minutes value to convert
     * @return Equivalent ticks
     */
    public static long minutesToTicks(long minutes) {
        return minutes * oneMinuteToTicks();
    }

    /**
     * Converts hours to ticks.
     *
     * @param hours Hours value to convert
     * @return Equivalent ticks
     */
    public static long hoursToTicks(long hours) {
        return hours * oneHourToTicks();
    }

    /**
     * Converts days to ticks.
     *
     * @param days Days value to convert
     * @return Equivalent ticks
     */
    public static long daysToTicks(long days) {
        return days * oneDayToTicks();
    }

    /**
     * Converts the given number of ticks to seconds.
     *
     * @param ticks The number of ticks
     * @return The number of seconds
     */
    public static long ticksToSeconds(int ticks) {
        return ticks / oneSecondToTicks();
    }

    /**
     * Converts the given number of ticks to minutes.
     *
     * @param ticks The number of ticks
     * @return The number of minutes
     */
    public static long ticksToMinutes(int ticks) {
        return ticks / oneMinuteToTicks();
    }

    /**
     * Converts the given number of ticks to hours.
     *
     * @param ticks The number of ticks
     * @return The number of hours
     */
    public static long ticksToHours(int ticks) {
        return ticks / oneHourToTicks();
    }

    /**
     * Converts the given number of days to seconds.
     *
     * @param ticks The number of days
     * @return The number of days
     */
    public static long ticksToDays(int ticks) {
        return ticks / oneDayToTicks();
    }

    /**
     * Converts the given number of ticks to seconds.
     *
     * @param ticks The number of ticks
     * @return The number of seconds
     */
    public static long ticksToSeconds(long ticks) {
        return ticks / oneSecondToTicks();
    }

    /**
     * Converts the given number of ticks to minutes.
     *
     * @param ticks The number of ticks
     * @return The number of minutes
     */
    public static long ticksToMinutes(long ticks) {
        return ticks / oneMinuteToTicks();
    }

    /**
     * Converts the given number of ticks to hours.
     *
     * @param ticks The number of ticks
     * @return The number of hours
     */
    public static long ticksToHours(long ticks) {
        return ticks / oneHourToTicks();
    }

    /**
     * Converts the given number of ticks to days.
     *
     * @param ticks The number of ticks
     * @return The number of days
     */
    public static long ticksToDays(long ticks) {
        return ticks / oneDayToTicks();
    }

    /**
     * Converts the given time unit value to ticks.
     *
     * @param val the amount of time units
     * @param unit the unit of time (seconds, minutes, etc.)
     * @return the number of ticks
     * @throws UnsupportedOperationException if the time unit is not supported
     */
    public static long ticks(int val, TimeUnit unit) {
        return switch (unit) {
            case NANOSECONDS, MILLISECONDS, MICROSECONDS -> throw new UnsupportedOperationException();
            case SECONDS -> secondsToTicks(val);
            case MINUTES -> minutesToTicks(val);
            case HOURS -> hoursToTicks(val);
            case DAYS -> daysToTicks(val);
        };
    }

    /**
     * Does the exact same thing as {@link #ticks(int, TimeUnit)} but in reversed order,
     * so if you provide 20L and a TimeUnit of SECONDS, this method will return 1.
     *
     * @param ticks the number of ticks
     * @param unit the unit of time (seconds, minutes, etc.)
     * @return the amount of time units
     * @throws UnsupportedOperationException if the time unit is not supported
     */
    public static int val(long ticks, TimeUnit unit) {
        return switch (unit) {
            case NANOSECONDS, MICROSECONDS, MILLISECONDS -> throw new UnsupportedOperationException();
            case SECONDS -> (int) ticksToSeconds(ticks);
            case MINUTES -> (int) ticksToMinutes(ticks);
            case HOURS -> (int) ticksToHours(ticks);
            case DAYS -> (int) ticksToDays(ticks);
        };
    }
}

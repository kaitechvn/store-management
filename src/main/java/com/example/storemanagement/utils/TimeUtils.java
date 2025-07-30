package com.example.storemanagement.utils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeUtils {

    private TimeUtils() {}

    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now(Clock.systemUTC());
    }
}

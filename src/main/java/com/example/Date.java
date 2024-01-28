package com.example;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Date {
    public String getDate(int timeZoneUtc,String pattern){
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now(ZoneOffset.ofHours(timeZoneUtc)).toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedTime = now.format(formatter);

        return formattedTime;
    }
}

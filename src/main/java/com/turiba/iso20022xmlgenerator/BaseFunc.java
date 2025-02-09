package com.turiba.iso20022xmlgenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BaseFunc {

    public String generateUniqueString() {
        LocalTime time = LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("H-m-s-n")).substring(0,12);
    }

    public String today(){
        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

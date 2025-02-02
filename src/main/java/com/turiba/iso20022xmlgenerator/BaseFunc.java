package com.turiba.iso20022xmlgenerator;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BaseFunc {

    public String generateUniqueString() {
        LocalTime time = LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("H-m-s-n"));
    }
}

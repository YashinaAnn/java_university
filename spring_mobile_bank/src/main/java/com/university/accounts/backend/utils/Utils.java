package com.university.accounts.backend.utils;

import java.time.LocalDate;

public class Utils {

    public static String getCurrentDate() {
        return LocalDate.now().toString();
    }
}

package com.pack.utils;

import java.util.UUID;

public class Utils {

    public static void validateToken(UUID token) {
        if (null == token) {
            throw new IllegalArgumentException("Please login to the system before");
        }
    }
}

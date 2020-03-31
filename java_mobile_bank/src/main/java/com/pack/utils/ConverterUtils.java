package com.pack.utils;

import com.pack.model.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ROUND_FLOOR;

public class ConverterUtils {

    public static final String USD_TO_RUB_STR = "66.91";
    public static final String EURO_TO_RUB_STR = "73.79";

    public static final double USD_TO_RUB = Double.parseDouble(USD_TO_RUB_STR);
    public static final double EURO_TO_RUB = Double.parseDouble(EURO_TO_RUB_STR);

    private static Map<Currency, BigDecimal> coefficients  = new HashMap<Currency, BigDecimal>() {{
        put(Currency.USD, new BigDecimal(USD_TO_RUB_STR));
        put(Currency.EURO, new BigDecimal(EURO_TO_RUB_STR));
        put(Currency.RUB, new BigDecimal("1."));
    }};

    public static BigDecimal convert(BigDecimal amount, Currency accCodeFrom, Currency accCodeTo) {
        if (accCodeFrom.equals(accCodeTo)) {
            return amount;
        }
        return amount.multiply(coefficients.get(accCodeFrom)).divide(coefficients.get(accCodeTo), 3, ROUND_FLOOR );
    }
}

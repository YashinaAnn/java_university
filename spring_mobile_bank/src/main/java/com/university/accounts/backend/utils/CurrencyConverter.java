package com.university.accounts.backend.utils;

import com.university.accounts.backend.model.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ROUND_FLOOR;

public class CurrencyConverter {

    public static final String USD_TO_RUB_STR = "66.91";
    public static final String EURO_TO_RUB_STR = "73.79";

    private static Map<Currency, BigDecimal> coefficients  = new HashMap<Currency, BigDecimal>() {{
        put(Currency.USD, new BigDecimal(USD_TO_RUB_STR));
        put(Currency.EURO, new BigDecimal(EURO_TO_RUB_STR));
        put(Currency.RUB, new BigDecimal("1."));
    }};

    public static BigDecimal convert(BigDecimal amount, Currency currencyFrom, Currency currencyTo) {
        if (currencyFrom.equals(currencyTo)) {
            return amount;
        }
        return amount.multiply(coefficients.get(currencyFrom)).divide(coefficients.get(currencyTo), 3, ROUND_FLOOR );
    }
}

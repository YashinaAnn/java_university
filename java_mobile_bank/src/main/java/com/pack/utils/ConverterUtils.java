package com.pack.utils;

import com.pack.model.AccCode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.pack.utils.Constants.EURO_TO_RUB_STR;
import static com.pack.utils.Constants.USD_TO_RUB_STR;
import static java.math.BigDecimal.ROUND_FLOOR;

public class ConverterUtils {

    private static Map<AccCode, BigDecimal> coefficients  = new HashMap<AccCode, BigDecimal>() {{
        put(AccCode.USD, new BigDecimal(USD_TO_RUB_STR));
        put(AccCode.EURO, new BigDecimal(EURO_TO_RUB_STR));
        put(AccCode.RUB, new BigDecimal("1."));
    }};

    public static BigDecimal convert(BigDecimal amount, AccCode accCodeFrom, AccCode accCodeTo) {
        if (accCodeFrom.equals(accCodeTo)) {
            return amount;
        }
        return amount.multiply(coefficients.get(accCodeFrom)).divide(coefficients.get(accCodeTo), 3, ROUND_FLOOR );
    }
}

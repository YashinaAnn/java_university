package com.pack.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static com.pack.model.AccCode.*;
import static com.pack.utils.Constants.*;

public class ConverterUtilsTest {

    @Test
    public void testConvertRub2Euro() {
        Assert.assertEquals(1., ConverterUtils.convert(new BigDecimal(EURO_TO_RUB_STR),
                RUB, EURO).doubleValue(), DELTA);
    }

    @Test
    public void testConvertEuro2Rub() {
        Assert.assertEquals(EURO_TO_RUB,
                ConverterUtils.convert(new BigDecimal(1), EURO, RUB).doubleValue(), DELTA);
    }

    @Test
    public void testConvertUsd2Rub() {
        Assert.assertEquals(USD_TO_RUB * 100, ConverterUtils.convert(new BigDecimal(100),
                USD, RUB).doubleValue(), DELTA);
    }

    @Test
    public void testConvertUsd2Usd() {
        Assert.assertEquals(1, ConverterUtils.convert(new BigDecimal(1),
                USD, USD).doubleValue(),  DELTA);
    }
}

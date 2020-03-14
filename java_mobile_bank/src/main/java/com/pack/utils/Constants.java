package com.pack.utils;

import java.text.SimpleDateFormat;

public class Constants {

    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String TOKEN = "token";

    public static final String CLIENT_ID = "client_id";
    public static final String ACC_CODE = "acc_code";
    public static final String AMOUNT = "amount";

    public static final String OPERATION_DATE = "operation_date";
    public static final String ACCOUNT_FROM = "account_from";
    public static final String ACCOUNT_TO = "account_to";
    public static final String AMOUNT_BEFORE = "amount_before";
    public static final String AMOUNT_AFTER = "amount_after";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final double DELTA = 0.001;
    public static final String USD_TO_RUB_STR = "66.91";
    public static final String EURO_TO_RUB_STR = "73.79";

    public static final double USD_TO_RUB = Double.parseDouble(USD_TO_RUB_STR);
    public static final double EURO_TO_RUB = Double.parseDouble(EURO_TO_RUB_STR);
}

package com.pack.task4;

import java.util.HashMap;
import java.util.Map;

public class PhoneDictionary {

    private static PhoneDictionary dictionary;
    private Map<Phone, PersonalInfo> phones;

    public static PhoneDictionary getInstance() {
        if (dictionary == null) {
            dictionary = new PhoneDictionary();
        }
        return dictionary;
    }

    private PhoneDictionary() {
        phones = new HashMap<>();
    }

    public PhoneDictionary(Map<Phone, PersonalInfo> phones) {
        setPhones(phones);
    }

    final Map<Phone, PersonalInfo> getPhones() {
        return phones;
    }

    void clearPhones() {
        phones.clear();
    }

    public void setPhones(Map<Phone, PersonalInfo> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone, PersonalInfo info) {
        if (null == phone || null == info) {
            throw new IllegalArgumentException("Incorrect data");
        }
        validatePhoneNotExists(phone);
        phones.put(phone, info);
    }

    public PersonalInfo deletePhone(Phone phone) {
        if (null == phone) {
            throw new IllegalArgumentException("Incorrect data");
        }
        validatePhoneExists(phone);
        PersonalInfo info = phones.get(phone);
        phones.remove(phone);
        return info;
    }

    public void updateInfo(Phone phone, PersonalInfo info) {
        if (null == phone || null == info) {
            throw new IllegalArgumentException("Incorrect data");
        }
        validatePhoneExists(phone);
        phones.replace(phone, info);
    }

    public void updatePhone(Phone oldPhone, Phone newPhone) {
        if (null == oldPhone || null == newPhone) {
            throw new IllegalArgumentException("Incorrect data");
        }
        PersonalInfo info = deletePhone(oldPhone);
        addPhone(newPhone, info);
    }

    private void validatePhoneExists(Phone phone) {
        if (!phones.containsKey(phone)) {
            throw new IllegalArgumentException("Phone does not exist in dictionary");
        }
    }

    private void validatePhoneNotExists(Phone phone) {
        if (phones.containsKey(phone)) {
            throw new IllegalArgumentException("Phone already exists in dictionary");
        }
    }
}

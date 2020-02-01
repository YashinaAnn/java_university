package com.pack.task4;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class PhoneDictionaryTest {

    private PhoneDictionary dictionary = PhoneDictionary.getInstance();
    private Phone phone = new Phone("12-12-12");
    private Phone newPhone = new Phone("123333312");
    private PersonalInfo info = new PersonalInfo("Ivan", "Petrov",
            new Address("Russia", "Omsk", "Street", "12a"));
    private PersonalInfo newInfo = new PersonalInfo("Ivan", "Petrov",
            new Address("Russia", "Saratov", "Street", "12b"));

    @Test
    public void testAddPhonePositive() {
        dictionary.clearPhones();
        dictionary.addPhone(phone, info);
        Map<Phone, PersonalInfo> phones = dictionary.getPhones();
        assertTrue(phones.containsKey(phone));
        assertEquals(phones.get(phone), info);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExistentPhone() {
        testAddPhonePositive();
        dictionary.addPhone(phone, info);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullPhone() {
        dictionary.addPhone(null, info);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullInfo() {
        dictionary.addPhone(phone, null);
    }

    @Test
    public void testDeletePhonePositive() {
        testAddPhonePositive();
        dictionary.deletePhone(phone);
        assertFalse(dictionary.getPhones().containsKey(phone));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNotExistentPhone() {
        dictionary.clearPhones();
        dictionary.deletePhone(phone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNullPhone() {
        dictionary.deletePhone(null);
    }

    @Test
    public void testUpdatePhonePositive() {
        testAddPhonePositive();
        dictionary.updatePhone(phone, newPhone);
        Map<Phone, PersonalInfo> phones = dictionary.getPhones();
        assertFalse(phones.containsKey(phone));
        assertTrue(phones.containsKey(newPhone));
        assertEquals(phones.get(newPhone), info);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNotExistentPhone() {
        dictionary.clearPhones();
        dictionary.updatePhone(phone, newPhone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullPhone1() {
        dictionary.updatePhone(null, newPhone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullPhone2() {
        dictionary.updatePhone(phone, null);
    }

    @Test
    public void testUpdateInfoPositive() {
        testAddPhonePositive();
        dictionary.updateInfo(phone, newInfo);
        Map<Phone, PersonalInfo> phones = dictionary.getPhones();
        assertTrue(phones.containsKey(phone));
        assertEquals(phones.get(phone), newInfo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateInfoForNotExistentPhone() {
        dictionary.clearPhones();
        dictionary.updateInfo(phone, info);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateInfoNullInfo() {
        dictionary.updateInfo(phone, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateInfoNullPhone() {
        dictionary.updateInfo(null, info);
    }
}

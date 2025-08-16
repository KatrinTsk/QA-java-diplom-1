package utils;

import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    public static String generateEmail() {
        return "test" + System.currentTimeMillis() + "@example.com";
    }

    public static String generatePassword() {
        return "password" + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    public static String generateName() {
        return "User" + ThreadLocalRandom.current().nextInt(100, 999);
    }
}
package utils;

import net.datafaker.Faker;
import java.util.Locale;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generatePassword() {
        return faker.internet().password(8, 16, true, true, true);
    }

    public static String generateName() {
        return faker.name().firstName();
    }
}
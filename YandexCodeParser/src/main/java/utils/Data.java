package utils;


import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Data {
    public static Faker faker = new Faker(Locale.forLanguageTag("RU"));
    private static final Logger log = LogManager.getLogger(Data.class.getName());

    private static String customTrim(String text) {
        return text.strip()
                .replace(" ", "")
                .replace("*", "")
                .replace("-", "")
                .replace("_", "")
                .replace("'", "")
                .replace("\"", "")
                .replaceAll("[0-9]", "");
    }

    private static List<String> shuffleItems(String... items) {
        var split = Arrays.asList(items).stream()
                .flatMap(it -> Arrays.stream(it.split("[ *_0-9]")))
                .collect(Collectors.toList());
        Collections.shuffle(split);
        return split;
    }

    public static String generatePackageName() {
        return customTrim(String.format("ru.yandex.praktikum.%s.%s",
                faker.space().galaxy(),
                faker.space().moon()));
    }

    public static String generateClassName() {
        var names = shuffleItems(faker.space().starCluster(), faker.space().nebula());
        return customTrim(String.join("", names));
    }

    public static String generateVariableName() {
        var names = shuffleItems(faker.ancient().god(), faker.ancient().titan());
        var variableName = customTrim(String.join("", names))
                .replace(".", "");

        var lowerCasedFirstChar = Character.toLowerCase(variableName.charAt(0));
        return lowerCasedFirstChar + variableName.substring(1);
    }

    public static int getSmallRandomInt() {
        return Data.faker.number().numberBetween(-9000, 9000);
    }

    public static String getRandomCarMake() {
        var carMakes = Arrays.asList("Audi",
                "BMW",
                "Buick",
                "Cadillac",
                "Chevrolet",
                "Chrysler",
                "Dodge",
                "Ferrari",
                "Ford",
                "GM",
                "GEM",
                "GMC",
                "Honda",
                "Hummer",
                "Hyundai",
                "Infiniti",
                "Isuzu",
                "Jaguar",
                "Jeep",
                "Kia",
                "Lamborghini",
                "Land Rover",
                "Lexus",
                "Lincoln",
                "Lotus",
                "Mazda",
                "Mercedes-Benz",
                "Mercury",
                "Mitsubishi",
                "Nissan",
                "Oldsmobile",
                "Peugeot",
                "Pontiac",
                "Porsche",
                "Regal",
                "Saab",
                "Saturn",
                "Subaru",
                "Suzuki",
                "Toyota",
                "Volkswagen",
                "Volvo");
        var position = faker.random().nextInt(0, carMakes.size());
        return carMakes.get(position);
    }

    public static Date getRandomDate() {
        var now = Instant.now();
        var from = Date.from(now.minus(faker.random().nextInt(366, 100 * 366), ChronoUnit.DAYS));
        log.trace("Date from: {} Date to: {}", from, now);
        return Data.faker.date().between(from, Date.from(now));
    }
}
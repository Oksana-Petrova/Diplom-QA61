package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    //данные по номеру карты
    public static String getApprovedCardNumber() {
        return ("4444 4444 4444 4441");
    }

    public static String getDeclinedCardNumber() {
        return ("4444 4444 4444 4442");
    }

    public static String getUnacceptableCardNumber() {
        return ("1478 2569 2369 0025");
    }

    public static String getShortCardNumber() {
        return ("4444 4444 4444 444");
    }

    //данные по месяцу
    public static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getMonthInOneSign() {
        return ("2");
    }

    public static String getMonth00() {
        return ("00");
    }

    public static String getMonthOver12() {
        return ("13");
    }

    //данные по году
    public static String getValidYear(int addYearToCurrent) {
        return LocalDate.now().plusYears(addYearToCurrent).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getYearLessCurrent(int minusYearFromCurrent) {
        return LocalDate.now().minusYears(minusYearFromCurrent).format(DateTimeFormatter.ofPattern("yy"));
    }

    //данные по владельцу
    public static String getValidCardOwnerName() {
        var firstName = faker.name().firstName();
        var lastname = faker.name().lastName();
        return firstName + ' ' + lastname;
    }

    public static String getCardOwnerNameInCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        var firstName = faker.name().firstName();
        var lastname = faker.name().lastName();
        return firstName + ' ' + lastname;
    }

    public static String getCardOwnerNameWithNumber() {
        return "Maks Swan3";
    }

    public static String getCardOwnerNameWithSimbol() {
        return "Maks/ Sw@n";
    }

    public static String getShortCardOwnerName() {
        return "A";
    }

    //данные по CVC-коду
    public static String getValidCvc() {
        return "147";
    }

    public static String getInvalidCvc() {
        return "20";
    }
}
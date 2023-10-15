package ru.netology.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentPage;

public class PaymentByMoneyTest {

    private PaymentPage paymentPage;

    @BeforeEach
    void setUpService() {
        paymentPage = new PaymentPage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void clearAll() {
        SQLHelper.clearAllData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("1.1.Успешная оплата по Approved карте")
    void shouldBePaidWhenUseApprovedCard() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.checkSuccessPaymentMessage();
    }

    @Test
    @DisplayName("1.2.Отклонена оплата по Declined карте")
    void shouldBeErrorWhenUseDeclinedCard() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getDeclinedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(1));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.checkErrorPaymentMessage();
    }

    @Test
    @DisplayName("1.3.Использование карты не из двух предложенных")
    void shouldNotPaidWhenUseUnacceptableCard() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getUnacceptableCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.checkErrorPaymentMessage();
    }

    @Test
    @DisplayName("1.4.Использование неполного номера карты")
    void shouldErrorWhenUseShortCardNumber() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getShortCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(4));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardNumberField();
    }

    @Test
    @DisplayName("1.5.Ввод месяца одним знаком")
    void shouldErrorWhenUseMonthInOneSign() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getMonthInOneSign());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnMonthField();
    }

    @Test
    @DisplayName("1.6.Ввод 00 в поле Месяц")
    void shouldErrorWhenUseMonth00() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getMonth00());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.checkErrorPaymentMessage();
    }

    @Test
    @DisplayName("1.7.Ввод месяца больше 12")
    void shouldErrorWhenUseMonthOver12() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getMonthOver12());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongDateMessage();
    }

    @Test
    @DisplayName("1.8.Ввод года меньше текущего")
    void shouldErrorWhenUseYearLessCurrent() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(-2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongYearMessage();
    }

    @Test
    @DisplayName("1.9.Ввод года на 5 лет больше текущего")
    void shouldErrorWhenUseCurrentYearPlus5() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(5));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.checkSuccessPaymentMessage();
    }

    @Test
    @DisplayName("1.10.Ввод года на 6 лет больше текущего")
    void shouldErrorWhenUseCurrentYearPlus6() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(6));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongDateMessage();
    }

    @Test
    @DisplayName("1.11.Имя владельца карты на русском языке")
    void shouldErrorWhenUseCardOwnerNameInCyrillic() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getCardOwnerNameInCyrillic());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardOwnerField();
    }

    @Test
    @DisplayName("1.12.Имя владельца карты с использованием цифр")
    void shouldErrorWhenUseCardOwnerNameWithNumber() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getCardOwnerNameWithNumber());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardOwnerField();
    }

    @Test
    @DisplayName("1.13.Имя владельца карты с использованием спецсимвола")
    void shouldErrorWhenUseCardOwnerNameWithSimbol() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getCardOwnerNameWithSimbol());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardOwnerField();
    }

    @Test
    @DisplayName("1.14.Имя владельца карты из одной буквы")
    void shouldErrorWhenUseShortCardOwnerName() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getShortCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardOwnerField();
    }

    @Test
    @DisplayName("1.15.Использование невалидного CVC")
    void shouldErrorWhenUseInvalidCvc() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getInvalidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCvcField();
    }

    @Test
    @DisplayName("1.16.Не заполнен номер карты (пустое поле)")
    void shouldErrorWhenEmptyCardNumberField() {
        paymentPage.paymentByMoney();
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("1.17.Не заполнен месяц (пустое поле)")
    void shouldBePaidWhenEmptyMonthField() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("1.18.Не заполнен год (пустое поле)")
    void shouldBePaidWhenEmptyYearField() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("1.19.Не заполнен владелец карты (пустое поле)")
    void shouldBePaidWhenEmptyCardOwnerField() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("1.20.Не заполнен CVC-код (пустое поле)")
    void shouldBePaidWhenEmptyCvcField() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessageOnCvc();
    }

    @Test
    @DisplayName("1.21.Все поля пустые")
    void shouldBePaidWhenEmptyAllFields() {
        paymentPage.paymentByMoney();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }
}
package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentPage;

import java.sql.SQLException;

public class PaymentByCreditTest {

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
    void clearAll() throws SQLException {
        SQLHelper.clearAllData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("2.1.Успешная оплата по Approved карте")
    void shouldBePaidWhenUseApprovedCard() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.successPaymentMessage();
    }

    @Test
    @DisplayName("2.2.Отклонена оплата по Declined карте")
    void shouldBeErrorWhenUseDeclinedCard() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getDeclinedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(1));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.errorPaymentMessage();
    }

    @Test
    @DisplayName("2.3.Использование карты не из двух предложенных")
    void shouldNotPaidWhenUseUnacceptableCard() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getUnacceptableCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.errorPaymentMessage();
    }

    @Test
    @DisplayName("2.4.Использование неполного номера карты")
    void shouldErrorWhenUseShortCardNumber() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getShortCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(4));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardNumberField();
    }

    @Test
    @DisplayName("2.5.Ввод месяца одним знаком")
    void shouldErrorWhenUseMonthInOneSign() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getMonthInOneSign());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnMonthField();
    }

    @Test
    @DisplayName("2.6.Ввод 00 в поле Месяц")
    void shouldErrorWhenUseMonth00() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getMonth00());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.errorPaymentMessage();
    }

    @Test
    @DisplayName("2.7.Ввод месяца больше 12")
    void shouldErrorWhenUseMonthOver12() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getMonthOver12());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongDateMessage();
    }

    @Test
    @DisplayName("2.8.Ввод года меньше текущего")
    void shouldErrorWhenUseYearLessCurrent() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getYearLessCurrent(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongYearMessage();
    }

    @Test
    @DisplayName("2.9.Ввод года на 5 лет больше текущего")
    void shouldErrorWhenUseCurrentYearPlus5() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(5));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.successPaymentMessage();
    }

    @Test
    @DisplayName("2.10.Ввод года на 6 лет больше текущего")
    void shouldErrorWhenUseCurrentYearPlus6() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(6));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongDateMessage();
    }

    @Test
    @DisplayName("2.11.Имя владельца карты на русском языке")
    void shouldErrorWhenUseCardOwnerNameInCyrillic() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getCardOwnerNameInCyrillic());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardOwnerField();
    }

    @Test
    @DisplayName("2.12.Имя владельца карты с использованием цифр")
    void shouldErrorWhenUseCardOwnerNameWithNumber() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getCardOwnerNameWithNumber());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardOwnerField();
    }

    @Test
    @DisplayName("2.13.Имя владельца карты с использованием спецсимвола")
    void shouldErrorWhenUseCardOwnerNameWithSimbol() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getCardOwnerNameWithSimbol());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardOwnerField();
    }

    @Test
    @DisplayName("2.14.Имя владельца карты из одной буквы")
    void shouldErrorWhenUseShortCardOwnerName() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getShortCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCardOwnerField();
    }

    @Test
    @DisplayName("2.15.Использование невалидного CVC")
    void shouldErrorWhenUseInvalidCvc() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getInvalidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.wrongFormatMessageOnCvcField();
    }

    @Test
    @DisplayName("2.16.Не заполнен номер карты (пустое поле)")
    void shouldErrorWhenEmptyCardNumberField() {
        paymentPage.paymentByCredit();
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(2));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("2.17.Не заполнен месяц (пустое поле)")
    void shouldBePaidWhenEmptyMonthField() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("2.18.Не заполнен год (пустое поле)")
    void shouldBePaidWhenEmptyYearField() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("2.19.Не заполнен владелец карты (пустое поле)")
    void shouldBePaidWhenEmptyCardOwnerField() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }

    @Test
    @DisplayName("2.20.Не заполнен CVC-код (пустое поле)")
    void shouldBePaidWhenEmptyCvcField() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessageOnCvc();
    }

    @Test
    @DisplayName("2.21.Все поля пустые")
    void shouldBePaidWhenEmptyAllFields() {
        paymentPage.paymentByCredit();
        paymentPage.setCardNumber(DataHelper.getApprovedCardNumber());
        paymentPage.setMonth(DataHelper.getValidMonth());
        paymentPage.setYear(DataHelper.getValidYear(3));
        paymentPage.setCardOwner(DataHelper.getValidCardOwnerName());
        paymentPage.setCvcNumber(DataHelper.getValidCvc());
        paymentPage.pushСontinueButton();
        paymentPage.verifyObligatoryFieldMessage();
    }
}
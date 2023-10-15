package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");

    private SelenideElement continueButton = $$(".button__content").find(exactText("Продолжить"));
    private SelenideElement cardNumberFieldError = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthFieldError = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearFieldError = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement cardOwnerFieldError = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvcFieldError = $(byText("CVC/CVV")).parent().$(".input__sub");

    public void paymentByMoney() {
        open(baseUrl, PaymentPage.class);
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
    }

    public void paymentByCredit() {
        open(baseUrl, PaymentPage.class);
        $$(".button__content").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
    }

    public void checkSuccessPaymentMessage() {
        $$(".notification__title").find(exactText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void checkErrorPaymentMessage() {
        $$(".notification__title").find(exactText("Ошибка")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void wrongFormatMessageOnCardNumberField() {
        cardNumberFieldError.shouldHave(exactText("Неверный формат")).shouldBe(visible);
    }

    public void wrongFormatMessageOnMonthField() {
        monthFieldError.shouldHave(exactText("Неверный формат")).shouldBe(visible);
    }

    public void wrongFormatMessageOnYearField() {
       yearFieldError.shouldHave(exactText("Неверный формат")).shouldBe(visible);
    }

    public void wrongFormatMessageOnCardOwnerField() {
        cardOwnerFieldError.shouldHave(exactText("Неверный формат")).shouldBe(visible);
    }

    public void wrongFormatMessageOnCvcField() {
        cvcFieldError.shouldHave(exactText("Неверный формат")).shouldBe(visible);
    }

    public void wrongDateMessage() {
        $$(".input__sub").find(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void wrongYearMessage() {
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    public void verifyObligatoryFieldMessage() {
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void verifyObligatoryFieldMessageOnCvc() {
        cvcFieldError.shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void setCardNumber(String numberCard) {
        cardNumberField.setValue(numberCard);
    }

    public void setMonth(String monthCard) {
        monthField.setValue(monthCard);
    }

    public void setYear(String yearCard) {
        yearField.setValue(yearCard);
    }

    public void setCardOwner(String ownerCard) {
        ownerField.setValue(ownerCard);
    }

    public void setCvcNumber(String cvcNumber) {
        cvcField.setValue(cvcNumber);
    }

    public void pushСontinueButton(){
       continueButton.click();
    }

    public SelenideElement getContinueButton() {
        return continueButton.shouldBe(visible, Duration.ofSeconds(15));
    }
}
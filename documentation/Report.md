# Отчет о проведенном тестировании

## Краткое описание
Было проведено тестирование комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение представляет собой веб-сервис, который предоставляет возможность купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте
2. Уникальная технология: выдача кредита по данным банковской карты

На начальном этапе было проведено ручное тестирование для ознакомления с проектом.  
На следующем этапе были созданы авто-тесты, согласно Плана автоматизации тестирования.

**Использованы следующие основные инструменты автоматизации:** Java 8, Junit 5, Selenide, Faker, Allure.

Тестирование было проведено для двух баз данных - MySQL и PostgreSQL.

## Количество и соотношение успешных/не успешных тест-кейсов:
Количество тест-кейсов - 47 шт., из них не пройдено 23 автотеста (49%).

**Отчет из фреймворка Allure:**
![отчет по автотестам_1510](https://github.com/Oksana-Petrova/Diplom-QA61/assets/124077557/8d8078da-f1c9-4815-8a06-7c609129f962)

## Общие рекомендации
- Разработать спецификацию с требованими для данного приложения;
- Исправить выявленные ошибки;
- Добавить тестовые локаторы-атрибуты в приложение для разработки и повышения устойчивости автотестов.

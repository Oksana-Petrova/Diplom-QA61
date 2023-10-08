# Дипломный проект 
Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение представляет собой веб-сервис, который предоставляет возможность купить тур по определённой цене с помощью двух способов:
- Обычная оплата по дебетовой карте
- Уникальная технология: выдача кредита по данным банковской карты


### Процедура запуска автотестов:

Предварительно запустить, настроить IntelliJ IDEA, DBeaver (либо другой инструмент администрирования базы данных), Docker Desktop, Google Chrome (либо другой браузер).

##### 1) Клонировать репозиторий с проектом;

##### 2) Открыть проект в Intellij IDEA;

##### 3) Запустить контейнер Docker через команду:

         docker-compose up 

##### 4) Запустить приложение (SUT) через команду:

         - на базе данных MySQL: java -jar ./artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app

         - на базе данных PostgreSQL: java -jar ./artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app
Примечание: перед запуском SUT и автотестов необходимо снять комментирование со строк с соответствующими базами данных в файлах build.gradle и application.properties.

##### 5) Запустить автотесты через команду:

         - на базе данных MySQL: .\gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app" "-Ddb.user=app" "-Ddb.password=pass"

         - на базе данных PostgreSQL: .\gradlew test "-Ddb.url=jdbc:postgresql://localhost:5432/app" "-Ddb.user=app" "-Ddb.password=pass"

##### 6) Сформировать отчет с использованием Allure через команду:

         .\gradlew allureReport

##### 7) Открыть отчет в браузере через команду:

         .\gradlew allureServe
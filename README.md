# Demo JUnit5 Testing + Google Spreadsheet and Gmail (starbucks)
Проект примеров тестирования веб-приложения starbucks на основе каркаса JUnit5 с интеграцией с Google Spreadsheet и Gmail
## Предварительные требования
- OS macOS Big Sur / Linux Ubuntu 20.04 / Другие современные настольные OS (не проверено)
- JDK 11
- Gradle 7.1
- браузеры Chrome, Firefox
## Скачивание и запуск
- скачать:

**git clone** https://github.com/tyaa10/demo-java-springboot-seleniumsamples4.git

- драйверы скачиваются по адресу https://www.selenium.dev/downloads/ и добавляются в каталог _drivers_ (версии драйверов должны соответствовать версиям браузеров), при запуске на OS Windows 10 необходимо добавить фрагмент имени драйвера .exe в строку 48 файла src/test/java/org/tyaa/demo/java/springboot/selenium/samples4/ui/AbstractPageTest.java

- запустить все тесты:

**./gradlew test**

- запустить только кейс givenIndexPage_whenLoaded_thenContentFound в классе IndexPageTest в браузере Firefox с выводом журнала в консоль:

**./gradlew test --tests** org.tyaa.demo.java.springboot.selenium.samples4.ui.IndexPageTest.givenIndexPage_whenLoaded_thenContentFound **--info -D**browser=gecko

- то же самое, но в браузере Chrome:

**./gradlew test --tests** org.tyaa.demo.java.springboot.selenium.samples4.ui.IndexPageTest.givenIndexPage_whenLoaded_thenContentFound **--info -D**browser=сhrome

или

**./gradlew test --tests** org.tyaa.demo.java.springboot.selenium.samples4.ui.IndexPageTest.givenIndexPage_whenLoaded_thenContentFound **--info**

- запустить все кейсы в классе IndexPageTest в браузере Firefox без вывода журнала в консоль:

**./gradlew test --tests** org.tyaa.demo.java.springboot.selenium.samples4.ui.IndexPageTest.givenIndexPage_whenLoaded_thenContentFound **-D**browser=gecko

## Показаны техники:
- BDD-именование методов тест-кейсов
- JUnit5
- мультибраузерное тестирование
- симуляция работы пользователя с письмами подтверждения (Gmail)
- интеграция с Google Spreadsheet (считывание текстов для сравнения с содержимым веб-страниц, сохранение подробных результатов сравнения)
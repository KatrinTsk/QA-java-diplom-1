Stellar Burgers
Автоматизированные UI-тесты для сервиса заказа бургеров.

Технологии
- Java 11
- JUnit 4.13.2
- Maven 3.9.0
- Selenium WebDriver 4.x
- Allure Framework 2.x

Запуск тестов
```bash
**Тесты регистрации**
mvn test -Dtest=RegistrationTest

**Тесты авторизации**
mvn test -Dtest=LoginTest

**Тесты конструктора**
mvn test -Dtest=ConstructorTest

**Для генерации отчета Allure:**
mvn clean test -e
mvn allure:serve
```
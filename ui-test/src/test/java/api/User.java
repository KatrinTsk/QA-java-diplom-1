package api;

/**
 * DTO (Data Transfer Object) класс для представления пользователя.
 * Используется для сериализации/десериализации JSON в API запросах.
 */
public class User {
    private String email;
    private String password;
    private String name;

    // Конструктор по умолчанию необходим для десериализации JSON в объект
    public User() {
    }

    // Конструктор для удобного создания объекта пользователя
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Геттеры и сеттеры обязательны для работы сериализатора RestAssured
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
package constants;

public class Endpoints {
    // Базовые URL
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String API_BASE_URL = BASE_URL + "/api";

    // Веб-страницы
    public static final String HOME_PAGE = BASE_URL + "/";

    // API эндпоинты
    public static final String API_REGISTER = API_BASE_URL + "/auth/register";
    public static final String API_USER = API_BASE_URL + "/auth/user";

    private Endpoints() {
        throw new IllegalStateException("Служебный класс");
    }
}
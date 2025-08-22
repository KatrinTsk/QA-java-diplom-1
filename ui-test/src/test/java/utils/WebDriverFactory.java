package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        WebDriver driver;

        switch (browser) {
            case "yandex":
                // Автоматически определяем совместимую версию ChromeDriver
                WebDriverManager.getInstance(DriverManagerType.CHROME).browserVersionDetectionCommand(
                        "C:/Users/ekaka/AppData/Local/Yandex/YandexBrowser/Application/browser.exe --version"
                ).setup();
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.setBinary("C:/Users/ekaka/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
                yandexOptions.addArguments("--remote-allow-origins=*");
                yandexOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(yandexOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setBrowserVersion("stable"); // или конкретную версию
                driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}
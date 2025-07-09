package org.example;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class SearchTest {

    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static SearchPage searchPage;
    public static TimerPage timerPage;
    public static WebDriver driver;

    /**
     * осуществление первоначальной настройки
     */

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

        // получаем путь до пвпки скачивания
        String downloadFilepath = ConfProperties.getProperty("downloadsDir");
        // создаём объект file указывающий на папку загрузки
        File downloadDir = new File(downloadFilepath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        // Создаём объект ChromeOptions, чтобы передать дополнительные параметры в Chrome.
        ChromeOptions options = new ChromeOptions();
        // Создаём словарь prefs для пользовательских настроек.
        HashMap<String, Object> prefs = new HashMap<>();
        // Отключаем всплывающие окна при скачивании (0 — блокировка).
        prefs.put("profile.default_content_settings.popups", 0);
        // Указываем папку, куда Chrome будет сохранять файлы по умолчанию.
        prefs.put("download.default_directory", downloadFilepath); // путь к твоей папке

        // Применяем настройки prefs в ChromeOptions.
        options.setExperimentalOption("prefs", prefs);

        // Инициализируем браузер Chrome с настроенными параметрами
        driver = new ChromeDriver(options);

        //создание экземпляра драйвера
//        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        searchPage = new SearchPage(driver);
        timerPage = new TimerPage(driver);

        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("loginpage")); }

    @RetryTest(3)
    public void searchTest() throws InterruptedException {
        //значение login/password берутся из файла настроек по аналогии с chromedriver
//и loginpage

        //нажимаем кнопку входа
        loginPage.clickloginButton();
        //вводим логин
        loginPage.inputLogin(ConfProperties.getProperty("login"));
        //вводим пароль
        loginPage.inputPasswd(ConfProperties.getProperty("password"));
        //нажимаем кнопку входа
        loginPage.clickConfermLoginBtn();
        // Взять и перетащить фильтр
        searchPage.selectFilter();
        // кликнуть на чек бокс
        searchPage.selectCheckboxMarketGPT();
        // кликнуть на кнопку принять
        searchPage.clickAplly();
        // кликнуть на кнопку поиск
        searchPage.clickSearch();
        // запуск таймера поиска сущностей
        timerPage.timerFindEntities();
        // собираем кол во найденных сущностей
        String entityCount = searchPage.getEntityCount();
        // проверяем, что на странице не 0 сущностей
        Assertions.assertNotEquals(ConfProperties.getProperty("incorrectEntityCount"),entityCount);
        // кликнуть на кнопку "сбросить фильтры"
        searchPage.clickButtonReset();
        // собираем кол во найденных сущностей
        String entityCountNew = searchPage.getEntityCount();
        // проверяем, что на странице 0 сущностей
        Assertions.assertEquals(ConfProperties.getProperty("incorrectEntityCount"),entityCountNew);
    }

    @RetryTest(3)
    public void fileDownloadTest() throws InterruptedException {
        //значение login/password берутся из файла настроек по аналогии с chromedriver
//и loginpage

        //нажимаем кнопку входа
        loginPage.clickloginButton();
        //вводим логин
        loginPage.inputLogin(ConfProperties.getProperty("login"));
        //вводим пароль
        loginPage.inputPasswd(ConfProperties.getProperty("password"));
        //нажимаем кнопку входа
        loginPage.clickConfermLoginBtn();
        // Взять и перетащить фильтр
        searchPage.selectFilter();
        // кликнуть на чек бокс
        searchPage.selectCheckboxMarketGPT();
        // кликнуть на кнопку принять
        searchPage.clickAplly();
        // кликнуть на кнопку поиск
        searchPage.clickSearch();
        // запуск таймера поиска сущностей
        timerPage.timerFindEntities();
        // собираем кол во найденных сущностей
        String entityCount = searchPage.getEntityCount();
        // проверяем, что на странице не 0 сущностей
        Assertions.assertNotEquals(ConfProperties.getProperty("incorrectEntityCount"),entityCount);
        searchPage.clickSearchResultCB1();
        // нажимаем на кнопку "Выгрузить в файл"
        searchPage.clickDownload();

        // Ожидание загрузки файла
        boolean isDownloaded = timerPage.waitForFileDownloadGPT(
                ConfProperties.getProperty("downloadsDir"),
                "Выгрузка (", // имя файла начинается с этой строки
                10            // таймаут в секундах
        );

        // Проверка загрузки
        Assertions.assertTrue(isDownloaded, "Файл не был загружен.");

    }

    @AfterEach
    public void tearDown() {
        profilePage.entryMenu();
        profilePage.userLogout();
        driver.quit(); }
}


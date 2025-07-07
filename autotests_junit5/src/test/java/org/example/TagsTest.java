package org.example;

import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TagsTest {
    public static LoginPage loginPage;
    public static SearchPage searchPage;
    public static ProfilePage profilePage;
    public static TimerPage timerPage;
    public static WebDriver driver;


    @BeforeAll
    public static void setup() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized");
//        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("useAutomationExtension", false);
//        driver = new ChromeDriver(options);
//        JavascriptExecutor js = (JavascriptExecutor) driver;

        //создание экземпляра драйвера
        driver = new ChromeDriver();
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("loginpage"));

        // Инициализация страниц
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
        profilePage = new ProfilePage(driver);
        timerPage = new TimerPage(driver);

        //нажимаем кнопку входа
        loginPage.clickloginButton();
        //вводим логин
        loginPage.inputLogin(ConfProperties.getProperty("login"));
        //вводим пароль
        loginPage.inputPasswd(ConfProperties.getProperty("password"));
        //нажимаем кнопку входа
        loginPage.clickConfermLoginBtn();
    }

    @BeforeEach
    public void openLink() {
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("searchpage"));
    }

//    @Before
//    public void setup() {
//        //определение пути до драйвера и его настройка
//        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
//        //создание экземпляра драйвера
//        driver = new ChromeDriver();
//        //окно разворачивается на полный экран
//        driver.manage().window().maximize();
//        //задержка на выполнение теста = 10 сек.
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
//        //получение ссылки на страницу входа из файла настроек
//        driver.get(ConfProperties.getProperty("loginpage"));
//
//        // Инициализация страниц
//        loginPage = new LoginPage(driver);
//        searchPage = new SearchPage(driver);
//        profilePage = new ProfilePage(driver);
//        timerPage = new TimerPage(driver);
//    }

    @RetryTest(3)
    public void TagsTest() throws InterruptedException {
//        //нажимаем кнопку входа
//        loginPage.clickloginButton();
//        //вводим логин
//        loginPage.inputLogin(ConfProperties.getProperty("login"));
//        //вводим пароль
//        loginPage.inputPasswd(ConfProperties.getProperty("password"));
//        //нажимаем кнопку входа
//        loginPage.clickloginBtn();
        // Взять и перетащить фильтр
        searchPage.selectFilter();
        // кликнуть на чек бокс
//        searchPage.selectCheckboxMarket();
        searchPage.selectCheckboxMarketGPT();
//        searchPage.selectCheckboxMarketJS();
        // кликнуть на кнопку принять
        searchPage.clickAplly();
        // кликнуть на кнопку поиск
        searchPage.clickSearch();
        // проверка на наличие окрашенных меток в выборке
        List<String> tagsNoColorCheck = searchPage.getAllNoTags();
        boolean allNoTagsCheckOk = tagsNoColorCheck.stream().allMatch(ConfProperties.getProperty("colorNoTag")::equals);
        Assertions.assertTrue(allNoTagsCheckOk, "В выборке присутствуют окрашенные метки");
        // кликнуть на чек бокс "выбрать все тендеры" после ожидания появления сущностей в выборке
        searchPage.clickSearchResultCB1();
        // кликнуть на троеточие всех тендеров
        searchPage.clickSettingsAllEntities();
        //gpt метод выбора первой метки
        searchPage.targetAndClickTag();
        //        запуск таймера
        timerPage.timerTags();
        // собираем присвоенный цвет метки
//        List<String> tagsColor = searchPage.getAllTags();
        List<String> tagsColor = searchPage.getAllTagsGPT(); // через Java script т. к. есть проблема с обновлением эллементов в DOM
        // сравнение с ожидаемым цветом
        boolean allOk = tagsColor.stream().allMatch(ConfProperties.getProperty("colorTag")::equals);
        Assertions.assertTrue(allOk, "Не все метки установились правильно");
        System.out.println("Найдено меток: " + tagsColor.size());
        // кликнуть на чек бокс "выбрать все тендеры"
        searchPage.clickSearchResultCB2();
        // кликнуть на троеточие всех тендеров
        searchPage.clickSettingsAllEntities();
        //gpt метод удаления метки
        searchPage.targetAndClickDeleteTag();
        // запуск таймера
        timerPage.timerNoTags();

        // собираем состояние сущности без метки и проверяем, что метка удалена
        //        List<String> tagsNoColor = searchPage.getAllNoTagsGPT();
        List<String> tagsNoColor = searchPage.getAllNoTags();
        boolean allNoTagsOk = tagsNoColor.stream().allMatch(ConfProperties.getProperty("colorNoTag")::equals);
        Assertions.assertTrue(allNoTagsOk, "Не все метки удалились");
        System.out.println("Найдено меток: " + tagsNoColor.size());
    }

    @RetryTest(3)
    public void TagTest() throws InterruptedException {
//        //нажимаем кнопку входа
//        loginPage.clickloginButton();
//        //вводим логин
//        loginPage.inputLogin(ConfProperties.getProperty("login"));
//        //вводим пароль
//        loginPage.inputPasswd(ConfProperties.getProperty("password"));
//        //нажимаем кнопку входа
//        loginPage.clickloginBtn();
        // Взять и перетащить фильтр
        searchPage.selectFilter();
        // кликнуть на чек бокс
        searchPage.selectCheckboxMarketGPT();
//        searchPage.selectCheckboxMarket();
//        searchPage.selectCheckboxMarketJS();
        // кликнуть на кнопку принять
        searchPage.clickAplly();
        // кликнуть на кнопку поиск
        searchPage.clickSearch();
        // кликнуть на троеточие тендера
        searchPage.clickSettingsEntities();
        //gpt метод выбора первой метки
        searchPage.targetAndClickTag1();
        // запуск таймера
        timerPage.timerTag();
        // собираем присвоенный цвет метки
        String tagColor = searchPage.getTags1();
        // сравнение с ожидаемым цветом
        Assertions.assertEquals(ConfProperties.getProperty("colorTagLime"), tagColor);
        // кликнуть на троеточие всех тендеров
        searchPage.clickSettingsEntities();
        //gpt метод удаления метки
        searchPage.targetAndClickDeleteTag1();
        //        запуск таймера
        timerPage.timerNoTag();
        // собираем состояние сущности без метки и проверяем, что метка удалена
        String tagNoColor = searchPage.getTags2();
        Assertions.assertEquals(ConfProperties.getProperty("colorNoTag"), tagNoColor);
    }


//    @After
//    public void exit() {
//        // тестовый афтер проверки меток
    ////        searchPage.checkTags();
//        profilePage.entryMenu();
//        profilePage.userLogout();
//        driver.quit();
//    }

    @AfterAll
    public static void tearDown() {
        profilePage.entryMenu();
        profilePage.userLogout();
        driver.quit();
    }
}
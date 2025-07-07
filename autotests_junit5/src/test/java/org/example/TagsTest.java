package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.List;

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

    @RetryTest(3)
    public void TagsTest() throws InterruptedException {
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
        List<String> tagsNoColor = searchPage.getAllNoTags();
        boolean allNoTagsOk = tagsNoColor.stream().allMatch(ConfProperties.getProperty("colorNoTag")::equals);
        Assertions.assertTrue(allNoTagsOk, "Не все метки удалились");
        System.out.println("Найдено меток: " + tagsNoColor.size());
    }

    @RetryTest(3)
    public void TagTest() throws InterruptedException {
        // Взять и перетащить фильтр
        searchPage.selectFilter();
        // кликнуть на чек бокс
        searchPage.selectCheckboxMarketGPT();
        // клик, работающий через раз для проверки
//        searchPage.selectCheckboxMarket();
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

    @AfterAll
    public static void tearDown() {
        profilePage.entryMenu();
        profilePage.userLogout();
        driver.quit();
    }
}
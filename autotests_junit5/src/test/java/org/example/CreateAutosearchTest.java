package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;


public class CreateAutosearchTest {
    public static LoginPage loginPage;
    public static SearchPage searchPage;
    public static WebDriver driver;


    @BeforeEach
    public void setup() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);

        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("loginpage")); }

    @RetryTest(3)
    public void createAndSaveAutoSearch() throws InterruptedException {
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
//        searchPage.clickSearch();
        // кликнуть на кнопку сохранить автопоиск
        searchPage.SaveAutoSearch();
        // вводим название автопоиска
        searchPage.enterNameAuto();
        // сохраняем название
        searchPage.saveNameAuto();
        // собираем название автотеста
        String nameAutoSearch = searchPage.getAutoSearchName();
        // проверка введённого названия и сохранившегося
        Assertions.assertEquals(ConfProperties.getProperty("nameAuto"), nameAutoSearch);
    }


    @AfterEach
    public void tearDown() throws InterruptedException {
        // Проверка: существует ли элемент для удаления
        if (searchPage.isElementPresent()) {
            searchPage.clickAutoSettings();
            searchPage.clickButtonDeleteAuto();
            searchPage.clickConfirmButtonDeleteAuto2();
        } else {
            System.out.println("Автопоиск не был создан, удалять нечего.");
        }
        driver.quit();
    }
}

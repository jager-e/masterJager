package org.example;

import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;



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
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        driver = new ChromeDriver();
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

    @AfterEach
    public void tearDown() {
        profilePage.entryMenu();
        profilePage.userLogout();
        driver.quit(); }
}

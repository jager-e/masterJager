package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class ViewedTest {

public static LoginPage loginPage;
public static SearchPage searchPage;
public static SearchPageViewed searchPageViewed;
public static ProfilePage profilePage;
public static TimerPage timerPage;

public static WebDriver driver;

@BeforeAll
public static void setup() {
    System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get(ConfProperties.getProperty("loginpage"));

    loginPage = new LoginPage(driver);
    searchPage = new SearchPage(driver);
    timerPage = new TimerPage(driver);
    profilePage = new ProfilePage(driver);
    searchPageViewed = new SearchPageViewed(driver);

    loginPage.clickloginButton();
    loginPage.inputLogin(ConfProperties.getProperty("login"));
    loginPage.inputPasswd(ConfProperties.getProperty("password"));
    loginPage.clickConfermLoginBtn();
}

@BeforeEach
public void openlink() {
    driver.get(ConfProperties.getProperty("searchpage"));
}

    @RetryTest(3)
    public void ViewedsTest() throws InterruptedException {
        searchPage.selectFilter();
        // кликнуть на чек бокс
        searchPage.selectCheckboxMarketGPT();
//        searchPage.selectCheckboxMarket();
//        searchPage.selectCheckboxMarketJS();
        // кликнуть на кнопку принять
        searchPage.clickAplly();
        // кликнуть на кнопку поиск
        searchPage.clickSearch();

        //метод проверки
//        List<String> tendersViewedsOffCheck = searchPageViewed.getAllViewedOff();
//        boolean allOkOffCheck = tendersViewedsOffCheck.stream().allMatch(ConfProperties.getProperty("viewOff")::equals);
//        Assertions.assertTrue(allOkOffCheck, "В выборке присутствуют 'Просмотренные' тендеры");

        searchPage.clickSearchResultCB1();
        searchPage.clickSettingsAllEntities();
        searchPageViewed.clickButtonAllViewedOn();
        List<String> tendersVieweds = searchPageViewed.getAllViewedOn();
        boolean allOk = tendersVieweds.stream().allMatch(ConfProperties.getProperty("viewOn")::equals);
        Assertions.assertTrue(allOk, "Не все сущности имеют состояние 'Просмотренные'");
        System.out.println("Найдено просмотренных: " + tendersVieweds.size());
        searchPage.clickSearchJS();
        List<String> tendersViewedsNew = searchPageViewed.getAllViewedOn();
        boolean allOkNew = tendersViewedsNew.stream().allMatch(ConfProperties.getProperty("viewOn")::equals);
        Assertions.assertTrue(allOkNew, "Не все сущности имеют состояние 'Просмотренные' после обновления страницы");
        System.out.println("Найдено просмотренных после обновления страницы: " + tendersViewedsNew.size());
        searchPage.clickSearchResultCB1();
        searchPage.clickSettingsAllEntities();
        searchPageViewed.clickButtonAllViewedOff();
        List<String> tendersViewedsOff = searchPageViewed.getAllViewedOff();
        boolean allOkOff = tendersViewedsOff.stream().allMatch(ConfProperties.getProperty("viewOff")::equals);
        Assertions.assertTrue(allOkOff, "Не все сущности имеют состояние 'Непросмотренные'");
        System.out.println("Найдено непросмотренных: " + tendersViewedsOff.size());
        searchPage.clickSearchJS();
        List<String> tendersViewedsOffNew = searchPageViewed.getAllViewedOff();
        boolean allOkOffNew = tendersViewedsOffNew.stream().allMatch(ConfProperties.getProperty("tenderBaseStatus")::equals);
        Assertions.assertTrue(allOkOffNew, "Не все сущности имеют состояние 'Непросмотренные' после обновления страницы");
        System.out.println("Найдено непросмотренных после обновления страницы: " + tendersViewedsOffNew.size());
    }

    @RetryTest(3)
    public void ViewedTest() throws InterruptedException {        // Взять и перетащить фильтр
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
        // кликнуть на строку "Отметить просмотренным"
        searchPageViewed.viewedOn();
        // запускаем таймер
        timerPage.timerViewOn();
        // собираем цвет(состояние) строки с тендером
        String viewStatusOn = searchPageViewed.getViewed();
        // проверка на соответствие цвету
        Assertions.assertEquals(ConfProperties.getProperty("viewOn"), viewStatusOn);
        // кликнуть на троеточие тендера
        searchPage.clickSettingsEntities();
        // кликнуть на строку "Отметить непросмотренным"
        searchPageViewed.viewedOff();
        timerPage.timerViewOff();
        // собираем цвет(состояние) строки с тендером
        String viewStatusOff = searchPageViewed.getViewed();
        // проверка на соответствие цвету
        Assertions.assertEquals(ConfProperties.getProperty("viewOff"), viewStatusOff);
    }

    @RetryTest(3)
    public void ViewedCardTenderTest() throws InterruptedException {        // Взять и перетащить фильтр
        searchPage.selectFilter();
        // кликнуть на чек бокс
        searchPage.selectCheckboxMarketGPT();
//        searchPage.selectCheckboxMarket();
//        searchPage.selectCheckboxMarketJS();
        // кликнуть на кнопку принять
        searchPage.clickAplly();
        // кликнуть на кнопку поиск
        searchPage.clickSearch();
        String viewStatusOffCheck = searchPageViewed.getViewed();
        Assertions.assertEquals(ConfProperties.getProperty("tenderBaseStatus"), viewStatusOffCheck, "Тендер уже просмотренный или не имеет базовое значение");
        searchPage.clickTender();
        String window1 = searchPage.switchToWindow1();
        String window2 = searchPage.switchToWindow2();
        driver.switchTo().window(window2);
        driver.close();
        driver.switchTo().window(window1);
        String tenderViewedOn = searchPageViewed.getViewed();
        Assertions.assertEquals(ConfProperties.getProperty("viewOn"), tenderViewedOn);
        searchPage.clickSearchJS();
        String tenderViewedOnAgain = searchPageViewed.getViewed();
        Assertions.assertEquals(ConfProperties.getProperty("viewOn"), tenderViewedOnAgain);
        // удаляем метку
        searchPage.clickSettingsEntities();
        // кликнуть на строку "Отметить непросмотренным"
        searchPageViewed.viewedOff();
        // собираем цвет(состояние) строки с тендером
        String viewStatusOff = searchPageViewed.getViewed();
        // проверка на соответствие цвету
        Assertions.assertEquals(ConfProperties.getProperty("viewOff"), viewStatusOff);
        searchPage.clickSearchJS();
        // собираем цвет(состояние) строки с тендером
        String viewStatusOffNew = searchPageViewed.getViewed();
        // повторная проверка на соответствие цвету
        Assertions.assertEquals(ConfProperties.getProperty("tenderBaseStatus"), viewStatusOffNew);
    }


    @AfterAll
    public static void tearDown() {
    profilePage.entryMenu();
    profilePage.userLogout();
    driver.quit();
    }
}

package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;


public class LoginTest {

    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static WebDriver driver;
    public long startTime;
    public long endTime;
    public long duration;

    /**
     * осуществление первоначальной настройки
     */
//    @BeforeClass
//    public static void setup() {
//        //определение пути до драйвера и его настройка
//        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
//        //создание экземпляра драйвера
//        driver = new ChromeDriver();
//        loginPage = new LoginPage(driver);
//        profilePage = new ProfilePage(driver);
//
//        //окно разворачивается на полный экран
//        driver.manage().window().maximize();
//        //задержка на выполнение теста = 10 сек.
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        //получение ссылки на страницу входа из файла настроек
//        driver.get(ConfProperties.getProperty("loginpage")); }



    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        startTime = System.currentTimeMillis();
        driver.get(ConfProperties.getProperty("loginpage"));

        // Инициализация страниц
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @RetryTest(3) // Вот здесь и происходит магия
    public void loginTest() {
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

//        profilePage.clickuserMenu();
        profilePage.entryMenu();
        //получаем отображаемый логин
        String user = profilePage.getUserName();
        //и сравниваем его с логином из файла настроек
        Assertions.assertEquals(ConfProperties.getProperty("login"), user);
    }

//    @AfterClass
//    public static void tearDown() {
//       profilePage.entryMenu();
//        profilePage.userLogout();
//        driver.quit(); }

    @AfterEach
    public void tearDown() {
        profilePage.userLogout();
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("LoginTest выполнился за " + duration + "ms");
        driver.quit(); }

}
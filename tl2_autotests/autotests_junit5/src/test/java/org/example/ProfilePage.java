package org.example;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ProfilePage {
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public WebDriver driver;
    public ProfilePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver; }
    /**
     * определение локатора меню пользователя
     */
    @FindBy(xpath = "/html/body/div[1]/div/nav/div[2]/div[2]/div[2]")
    private WebElement userMenu;

//    @FindBy(xpath = "/html/body/div[1]/div/nav/div[2]/div[2]/div[2]/div")
//    private WebElement username;

    //    тестовый
    @FindBy(xpath = "//*[@id=\"user-profile-popover-login\"]")
    private WebElement username;
    /**
     * определение локатора кнопки выхода из аккаунта
     */
    @FindBy(xpath = "//div[contains(text(), 'Выйти')]")
    private WebElement logoutBtn;
    /**
     * метод для получения имени пользователя из меню пользователя
     */


    public String getUserName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"user-profile-popover-login\"]")));
        String userName = username.getText();
        String cleanName = userName.replace("@", "");
        return cleanName; }
    /**
     * метод для нажатия кнопки меню пользователя
     */
    public void entryMenu() {
        userMenu.click(); }
    /**
     * метод для нажатия кнопки выхода из аккаунта
     */
    public void userLogout() {
        logoutBtn.click(); }

    // метод чата гпт
    public void tryClickOrExit(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Проверяем: элемент кликабелен
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click(); // Кликаем
            System.out.println("Элемент был кликабелен, клик выполнен.");
        } catch (TimeoutException clickableTimeout) {
            System.out.println("Элемент не стал кликабельным. Проверяем, есть ли он в DOM...");

            try {
                // Пробуем найти элемент в DOM (без ожидания кликабельности)
                WebElement fallbackElement = driver.findElement(locator);
                fallbackElement.click(); // Пробуем кликнуть, может получится
                System.out.println("Элемент найден в DOM и клик выполнен.");
                driver.quit();
            } catch (NoSuchElementException | ElementClickInterceptedException e) {
                System.out.println("Элемент не найден или невозможно кликнуть. Завершаем программу.");
                driver.quit();
                System.exit(1); // Завершаем программу с кодом ошибки
            }
        }
    }
}

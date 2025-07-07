package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    public WebDriver driver;
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver; }

    @FindBy(xpath = "//*[@id=\"login-button\"]/div")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement loginField;

    @FindBy(xpath = "//*[@id=\"password\"]/div/div[1]/input")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"landing-popup-login-button\"]/div")
    private WebElement loginBtn;

    public void clickloginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click(); }

    public void inputLogin(String login) {
        loginField.sendKeys(login); }

    public void inputPasswd(String password) {
        passwordField.sendKeys(password); }

    public void clickConfermLoginBtn() {
        loginBtn.click(); }

}
package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchPageViewed {
    public WebDriver driver;
    public SearchPageViewed (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy (xpath = "//div[@class='dx-item dx-menu-item']//*[text()='Отметить просмотренным']")
    private WebElement buttonViewedOn;

    @FindBy (xpath = "//div[text()='Отметить непросмотренным']")
    private WebElement buttonViewedOff;

    @FindBy (xpath = "//div[@class='common-context-menu-item'][text()='Отметить просмотренным']")
    private WebElement buttonAllViewedOn;

    @FindBy (xpath = "//div[@class='common-context-menu-item'][text()='Отметить непросмотренным']")
    private WebElement buttonAllViewedOff;

//    @FindBy (xpath = "//tr[@class='dx-row dx-data-row dx-row-lines']")
//    private WebElement tender;

    @FindBy (xpath = "//tr[@class='dx-row dx-data-row dx-row-lines'][@aria-rowindex='1']")
    private WebElement tender;

    @FindBy (xpath = "//*[@id='universal-search']")
    private WebElement universalSearch;


    public void viewedOn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dx-item dx-menu-item']//*[text()='Отметить просмотренным']")));
        buttonViewedOn.click();
    }

    public void viewedOff() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[text()='Отметить непросмотренным']")));
        buttonViewedOff.click();
    }

    public String getViewed() {
        Actions actions = new Actions(driver);
        actions.moveToElement(universalSearch).build().perform();
        String viewStatus1 = tender.getCssValue("background");
        String viewStatus = viewStatus1.replace(" none repeat scroll 0% 0% / auto padding-box border-box","");
        return viewStatus;
    }

    public void clickButtonAllViewedOn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='common-context-menu-item'][text()='Отметить просмотренным']")));
        buttonAllViewedOn.click();
    }

    public void clickButtonAllViewedOff() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='common-context-menu-item'][text()='Отметить непросмотренным']")));
        buttonViewedOff.click();
    }

    @SuppressWarnings("unchecked")
    public List<String> getAllViewedOn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> viewedsOn = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr[@class='dx-row dx-data-row dx-row-lines' and @style='background-color: rgb(227, 230, 236);']")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (List<String>) js.executeScript(
                "return Array.from(document.querySelectorAll('tr.dx-row.dx-data-row.dx-row-lines'))" +
                        "  .filter(e => getComputedStyle(e).backgroundColor === 'rgb(227, 230, 236)')" +
                        "  .map(e => getComputedStyle(e).backgroundColor.trim());"
        );

    }

    @SuppressWarnings("unchecked")
    public List<String> getAllViewedOff() {
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> viewedsOff = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr[@class='dx-row dx-data-row dx-row-lines']")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
//        return (List<String>) js.executeScript(
//                "return Array.from(document.querySelectorAll('tr.dx-row.dx-data-row.dx-row-lines'))" +
//                        "  .filter(e => getComputedStyle(e).backgroundColor === 'rgb(255, 255, 255)')" +
//                        "  .map(e => getComputedStyle(e).backgroundColor.trim());"
//        );

        return (List<String>) js.executeScript(
                "return Array.from(document.querySelectorAll('tr.dx-row.dx-data-row.dx-row-lines'))" +
                        ".map(e => getComputedStyle(e).backgroundColor.trim());"
        );
    }
}


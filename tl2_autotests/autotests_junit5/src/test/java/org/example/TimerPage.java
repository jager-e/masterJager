package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TimerPage {

    public WebDriver driver;
    public TimerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver; }

    @FindBy (xpath = "//tr[contains(@class,'dx-row dx-data-row dx-row-lines')][@aria-rowindex='1']")
    private WebElement tenderViewOff;

    public void timerTags () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        long startTime1 = System.currentTimeMillis();
        // ожидаем присвоение метки
        List<WebElement> tags = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]")));
        //Засекаем конечное время
        long endTime1 = System.currentTimeMillis();
        //Вычисляем и логируем результат
        long duration1 = endTime1 - startTime1;
        System.out.println("Цветовая метка 'Red' проставилась за " + duration1 + " мс");
    }

    public void timerNoTags () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // запуск таймера
        long startTime2 = System.currentTimeMillis();
        // ожидание исчезновения
        List<WebElement> tagsNoList = driver.findElements(By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]"));
        wait.until(ExpectedConditions.invisibilityOfAllElements(tagsNoList));
        //Засекаем конечное время
        long endTime2 = System.currentTimeMillis();

        //Вычисляем и логируем результат
        long duration2 = endTime2 - startTime2;
        System.out.println("Цветовая метка 'Red' убрана за " + duration2 + " мс");
    }

    public void timerTag () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        long startTime1 = System.currentTimeMillis();
        // ожидаем присвоение метки
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]")));
        //Засекаем конечное время
        long endTime1 = System.currentTimeMillis();
        //Вычисляем и логируем результат
        long duration1 = endTime1 - startTime1;
        System.out.println("Цветовая метка 'Lime' проставилась за " + duration1 + " мс");
    }

    public void timerNoTag () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // запуск таймера
        long startTime2 = System.currentTimeMillis();
        // ожидание исчезновения
        WebElement element = driver.findElement(By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]"));
        wait.until(ExpectedConditions.stalenessOf(element));
        //Засекаем конечное время
        long endTime2 = System.currentTimeMillis();

        //Вычисляем и логируем результат
        long duration2 = endTime2 - startTime2;
        System.out.println("Цветовая метка 'Lime' убрана за " + duration2 + " мс");
    }

    public void timerFindEntities () {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // запуск таймера
        long startTime2 = System.currentTimeMillis();
        // ожидание появления тендера
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='dx-row dx-data-row dx-row-lines']")));
        //Засекаем конечное время
        long endTime2 = System.currentTimeMillis();

        //Вычисляем и логируем результат
        long duration2 = endTime2 - startTime2;
        System.out.println("Сущности появились на странице за " + duration2 + " мс");

    }

    public void timerViewOn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        long startTime = System.currentTimeMillis();
//        WebElement tenderViewOn = driver.findElement(By.xpath("//tr[@class='dx-row dx-data-row dx-row-lines' and @style='background-color: rgb(227, 230, 236);']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='dx-row dx-data-row dx-row-lines' and @style='background-color: rgb(227, 230, 236);']")));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Статус 'Просмотренный' проставился за " + duration + " мс");
    }

    public void timerViewOff() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        long startTime3 = System.currentTimeMillis();
        wait.until(ExpectedConditions.attributeToBe(tenderViewOff, "style", "background-color: rgb(255, 255, 255);"));
        long endTime3 = System.currentTimeMillis();
        long duration3 = endTime3 - startTime3;
        System.out.println("Статус 'Просмотренный' убран за " + duration3 + " мс");
    }
}
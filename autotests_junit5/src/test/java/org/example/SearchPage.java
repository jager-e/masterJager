package org.example;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.not;

public class SearchPage {

    public WebDriver driver;

    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    long startTime1;
    long endTime1;
    long duration1;

    // Ожидание
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(xpath = "//*[@id=\"list-tenders-group-0\"]/div[14]")
    private WebElement filter;

    @FindBy(xpath = "//*[@class='dx-sortable tl-filter-content tl-filter-drop-area']")
    private WebElement activeZone;

    @FindBy(xpath = "(//*[@class='dx-checkbox-container'])[6]")
    private WebElement checkbox;

    //(//input[@type='hidden'][@checked='false'])[4]

    //div[text()='(ZAKUPKI.GOV.RU) ЕИС ЗАКУПКИ']/.

    //(//*[@class='dx-widget dx-checkbox dx-state-hover'])[5]

    @FindBy(xpath = "//*[@id=\"filter-apply-button\"]/div")
    private WebElement apply;

    @FindBy(xpath = "//*[@id=\"search-filters-search-button\"]")
    private WebElement search;

    @FindBy(xpath = "//*[@id=\"search-filters-save-autosearch-button\"]/div")
    private WebElement saveAutoSearch;

    @FindBy(xpath = "//*[@id=\"autosearch-name\"]/div[1]/div[1]/textarea")
    private WebElement nameAuto;

    @FindBy(xpath = "//*[@id=\"save-autosearch-button\"]/div")
    private WebElement save;

    @FindBy(xpath = "//*[@id=\"autosearch-label-textarea\"]/div/div[1]/textarea")
    private WebElement nameAutoSearch;

    @FindBy(xpath = "//*[@id=\"autosearch-more-button\"]")
    private WebElement buttonSetAutoSettings;

    @FindBy(xpath = "//*[@class='autosearch-context-menu-item autosearch-context-menu-item-header']")
    private WebElement buttonDeleteAutoSearch;

    @FindBy(xpath = "//div[@role='button']//span[text()='Удалить']")
    private WebElement buttonConfirmConDeleteAutoSearch;

    @FindBy(xpath = "//div[@class='dx-info' and contains(text(), 'Всего найдено -')]")
    private WebElement entityCount;

    @FindBy(xpath = "//span[text()='Сбросить фильтры']")
    private WebElement buttonReset;

    // для теста меток
    @FindBy(xpath = "//*[@id='search-result-checkbox']")
    private WebElement searchResultCB; // aria-checked true

    @FindBy(xpath = "//*[@class='dx-widget dx-checkbox dx-select-checkbox dx-datagrid-checkbox-size'][@aria-label='Выбрать строку']")
    private WebElement searchTenderCB;

    @FindBy(xpath = "//*[@id='search-panel-selection-entities']")
    private WebElement settingsAllEntities;

    @FindBy(xpath = "(//*[@class='dx-link dx-icon-overflow dx-link-icon'])[1]")
    private WebElement settingsEntities;

    @FindBy(xpath = "//*[@class='dx-item dx-menu-item dx-menu-item-expanded']")
    private WebElement setTag;

//    @FindBy (xpath = "//div[text()='Назначить метку']")
//    private WebElement setTag;
//    @FindBy (xpath = "//*[@class='common-context-menu-div-color']")
//    private WebElement tag;

    @FindBy (xpath = "//*[@class='tl-tag-tender']")
    private WebElement tag;

    @FindBy (xpath = "//tr[contains(., 'ZAKUPKI.GOV.RU')]//div[@role='checkbox']")
    private WebElement checkboxCheck1;

    @FindBy (xpath = "//*[@id='universal-search']")
    private WebElement universalSearch;

    @FindBy (xpath = "//tr[@class='dx-row dx-data-row dx-row-lines'][@aria-rowindex='1']")
    private WebElement tender;

//    @FindBy (xpath = "//div[@class='dx-loadindicator-segment-inner']")
//    private WebElement spinner;
    //------------------------------------------------------

    public String switchToWindow2() {
        String windowSearchPage = driver.getWindowHandle();
        Set<String> currentWindows = driver.getWindowHandles();
        String windowTender = null;

        for (String window : currentWindows) {
            if (!window.equals(windowSearchPage)) {
                windowTender = window;
                break;
            }
        }
        return windowTender;
    }

    public String switchToWindow1() {
        String windowSearchPage = driver.getWindowHandle();
        return windowSearchPage;
    }

    //----------------------------------------------------

    public void clickTender() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='dx-row dx-data-row dx-row-lines'][@aria-rowindex='1']")));
        tender.click();
    }

    public void selectFilter() {
        Actions actions = new Actions(driver);
        // Взять и перетащить фильтр
        actions.dragAndDrop(filter, activeZone).build().perform();
    }

    public void selectCheckboxMarket() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='dx-widget dx-checkbox dx-state-hover'])[5]")));
        checkbox.click();// (//*[@class='dx-checkbox-container'])[6]
//        try {
//            checkbox.click();
//            Thread.sleep(500);
//        } catch (ElementClickInterceptedException e) {
//            System.out.println("Обычный клик на чек бокс выбора площадки перехвачен. Используем js клик.");
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].click();", checkbox);
//            Thread.sleep(500);
//        }
    }

    public void selectCheckboxMarketJS() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='dx-checkbox-container'])[6]"))); // (//*[@class='dx-checkbox-container'])[6]

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", checkbox);

    }

    // метод проставляет чек бокс, при этом проверяет, действительно ли он проставился
    public void selectCheckboxMarketGPT() throws InterruptedException {

        int attempts = 0;


        while (attempts < 3) {
            try {
                // Пробуем обычный клик
                checkbox.click();
                Thread.sleep(500); // Ждём обновление UI
                String checkboxCheck = checkboxCheck1.getAttribute("class");


                // Проверяем, сработал ли клик
                if (checkboxCheck.contains("dx-checkbox-checked")) {

                    System.out.println("Чекбокс успешно отмечен обычным кликом с " + (attempts + 1) + " попытки");
                    return;
                }

            } catch (ElementClickInterceptedException e) {
                // Если обычный клик перехвачен — пробуем JS-клик
                System.out.println("Обычный клик перехвачен, пробуем JS-клик.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                Thread.sleep(500); // Ждём обновление UI
                String checkboxCheck = checkboxCheck1.getAttribute("class");

                // Проверяем, сработал ли JS-клик
                if (checkboxCheck.contains("dx-checkbox-checked")) {
                    System.out.println("Чекбокс успешно отмечен через JS-клик с " + (attempts + 1) + " попытки");
                    return;
                }
            }

            attempts++;
        }

// Если сюда дошло — ни обычный, ни JS-клик не сработали
//        throw new IllegalStateException("Чекбокс так и не был отмечен после 3 попыток!"); // генерирует исключение(метод не может выполнится из за не подходящего условия
        System.out.println("Чекбокс так и не был отмечен после 3 попыток!");
    }



    public void clickAplly() {
        apply.click();
    }

    // кликнуть на кнопку поиск
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(search));
        search.click();
    }

    public void clickSearchJS() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", search);
        js.executeScript("arguments[0].click();", search);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dx-loadindicator-segment-inner']")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='dx-loadindicator-segment-inner']")));

        Actions actions = new Actions(driver);
        actions.moveToElement(universalSearch).build().perform();
    }

    public void just() {
        Actions actions = new Actions(driver);
        actions.moveToElement(universalSearch).build().perform();
    }

    // кликнуть на кнопку сохранить автопоиск
    public void SaveAutoSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(saveAutoSearch));
        saveAutoSearch.click();
    }

    // вводим название теста
    public void enterNameAuto() {
        nameAuto.click();
        nameAuto.sendKeys("TEST", Keys.ENTER);
    }

    // сохраняем название
    public void saveNameAuto() {
        save.click();
    }

    // собираем название автотеста
    public String getAutoSearchName() {
        wait.until(ExpectedConditions.attributeToBe(nameAutoSearch, "value", "TEST"));
        String readyNameAutoSearch = nameAutoSearch.getAttribute("value");
        return readyNameAutoSearch;
    }

    // Ждём и кликаем по настройкам автопоиска при помощи JS
    public void clickAutoSettings() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"autosearch-more-button\"]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", buttonSetAutoSettings);
        js.executeScript("arguments[0].click();", buttonSetAutoSettings);
    }

    // Ждём и кликаем по кнопке удалить
    public void clickButtonDeleteAuto() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='autosearch-context-menu-item autosearch-context-menu-item-header']")));
        buttonDeleteAutoSearch.click();
    }

    // Ждём и кликаем по кнопке удалить
    public void clickConfirmButtonDeleteAuto1() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='button']//span[text()='Удалить']")));
        buttonConfirmConDeleteAutoSearch.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='button']//span[text()='Удалить']")));
    }

    // Ждём и кликаем по кнопке удалить с обработкой исключения
    public void clickConfirmButtonDeleteAuto2() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='button']//span[text()='Удалить']")));

        try {
            // Пытаемся кликнуть обычным способом
            buttonConfirmConDeleteAutoSearch.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Обычный клик перехвачен.");
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonConfirmConDeleteAutoSearch);
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='button']//span[text()='Удалить']")));
    }

    // получаем фразу с колличеством тендеров в выборке
    public String getEntityCount() throws InterruptedException {
        String count = this.entityCount.getText();
        return count;
    }

    public void clickButtonReset() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Сбросить фильтры']")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", buttonReset);
        js.executeScript("arguments[0].click();", buttonReset);
    }

    public boolean isElementPresent() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(1000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id='autosearch-more-button']")));
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public void clickSearchResultCB1() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='dx-row dx-data-row dx-row-lines']")));
        searchResultCB.click();
    }

    public void clickSearchResultCB2() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='dx-row dx-data-row dx-row-lines dx-row-modified']")));
        searchResultCB.click();
    }

    public void clickTenderCB1() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='dx-row dx-data-row dx-row-lines']")));
        searchTenderCB.click();
    }

    public void clickTenderCB2() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='dx-row dx-data-row dx-row-lines']")));
        searchTenderCB.click();
    }

    public void clickSettingsAllEntities() {
        settingsAllEntities.click();
    }

    public void clickSettingsEntities() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='dx-row dx-data-row dx-row-lines']")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", settingsEntities);
    }

    public void selectTags() {
        Actions actions = new Actions(driver);
        actions.moveToElement(setTag).moveToElement(tag).click().release().build().perform();
    }

    // gpt метод !!!!!!!!!!!!!!!!!!!!!! понять как работает
    public void targetAndClickTag() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // 1. Наводим курсор на первый элемент
        WebElement elementToHover = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='common-context-menu-item'][text()='Назначить метку']"))); // замените на свой XPath
        actions.moveToElement(elementToHover).perform();

        // 2. Ждём, пока появится второй элемент
        WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@class='common-context-menu-div-color'][@style='background-color: #EB0910']"))); // замените на XPath второго элемента

        // 3. Кликаем по второму элементу
        elementToClick.click();
    }

    public void targetAndClickDeleteTag() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // 1. Наводим курсор на первый элемент
        WebElement elementToHover = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='common-context-menu-item'][text()='Назначить метку']"))); // замените на свой XPath
        actions.moveToElement(elementToHover).perform();

        // 2. Ждём, пока появится второй элемент
        WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@class='common-context-menu-item-delete']"))); // замените на XPath второго элемента

        // 3. Кликаем по второму элементу
        elementToClick.click();
    }

    // gpt метод
    public void targetAndClickTag1() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // 1. Наводим курсор на первый элемент
        WebElement elementToHover = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='common-context-menu-item'][text()='Назначить метку']"))); // замените на свой XPath
        actions.moveToElement(elementToHover).perform();

        // 2. Ждём, пока появится второй элемент
        WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@class='common-context-menu-div-color'][@style='background-color: #daff0a']"))); // замените на XPath второго элемента

        // 3. Кликаем по второму элементу
        elementToClick.click();
    }

    public void targetAndClickDeleteTag1() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // 1. Наводим курсор на первый элемент
        WebElement elementToHover = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='common-context-menu-item'][text()='Назначить метку']"))); // замените на свой XPath
        actions.moveToElement(elementToHover).perform();

        // 2. Ждём, пока появится второй элемент
        WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@class='common-context-menu-item-delete']"))); // замените на XPath второго элемента

        // 3. Кликаем по второму элементу
        elementToClick.click();
    }

    // метод сбора цвета всех меток
    public List<String> getAllTags() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> tags = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]")
                )
        );
        List<String> colorsOn = new ArrayList<>();
        for (WebElement tag : tags) {
            String raw = tag.getCssValue("background");
            String raw1 = raw.replace(" none repeat scroll 0% 0% / auto padding-box border-box", "");
            colorsOn.add(raw1.trim());
        }
        return colorsOn;
    }

    // тестовые методы проверки и удаления меток. ТРЕБУЮТ ДОРАБОТКИ ----------------------------------------------

    @SuppressWarnings("unchecked")
    public List<WebElement> getColoredTagsJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (List<WebElement>) js.executeScript(
                "return Array.from(document.querySelectorAll('.tl-tag-tender')).filter(e => getComputedStyle(e).backgroundColor !== 'rgba(0, 0, 0, 0)');"
        );
    }

    public void checkTags () {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        List<WebElement> badTags = getColoredTagsJS();
        if (badTags.isEmpty()) {
            System.out.println("Окрашенных меток не осталось – уборка не нужна.");
            return;
        }

        System.out.println("Найдено меток для уборки: " + badTags.size());


        /* 3. Открываем контекстное меню → «Удалить метку» */
        try {

            searchResultCB.click();
            settingsAllEntities.click();

            Actions actions1 = new Actions(driver);

            // 1. Наводим курсор на первый элемент
            WebElement elementToHover = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='common-context-menu-item'][text()='Назначить метку']"))); // замените на свой XPath
            actions1.moveToElement(elementToHover).perform();

            // 2. Ждём, пока появится второй элемент
            WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@class='common-context-menu-item-delete']"))); // замените на XPath второго элемента

            // 3. Кликаем по второму элементу
            elementToClick.click();

        } catch (TimeoutException e) {
            System.out.println("Контекстного меню не появилось – возможно, UI изменился.");
            return;
        }

        /* 4. Ждём, пока все цветные метки исчезнут */
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]"), 0));
        // Альтернатива: presenceOfAllElementsLocatedBy(rawTag) – если должно стать «серым»
        System.out.println("Все метки успешно убраны.");
    }

    //-----------------------------------------------------------------------------------------------------------------

    // сбор состояния метки всех тендеров
    public List<String> getAllNoTags() throws InterruptedException {

        List<WebElement> tagsNo = driver.findElements(By.xpath("//div[@class='tl-tag-tender']"));

        List<String> colorsOff = new ArrayList<>();
        for (WebElement tagNo : tagsNo) {
            String raw = tagNo.getCssValue("background");
            String raw1 = raw.replace(" none repeat scroll 0% 0% / auto padding-box border-box", "");
            colorsOff.add(raw1.trim());
        }
        return colorsOff;
    }

    // метод gpt
    @SuppressWarnings("unchecked")
    public List<String> getAllNoTagsGPT() {
        WebElement element = driver.findElement(By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(element));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        return (List<String>) js.executeScript(
                "return Array.from(document.querySelectorAll('.tl-tag-tender'))"
                        + "        .map(e => getComputedStyle(e).backgroundColor.trim());"
        );
    }

    @SuppressWarnings("unchecked")
    public List<String> getAllTagsGPT() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> tags = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]"))
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;

        return (List<String>) js.executeScript(
                "return Array.from(document.querySelectorAll('.tl-tag-tender'))"
                        + "        .map(e => getComputedStyle(e).backgroundColor.trim());"
        );
    }

    // метод сбора цвета метки
    public String getTags1() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]")));
        String colorTag1 = tag.getCssValue("background");
        String colorTag = colorTag1.replace(" none repeat scroll 0% 0% / auto padding-box border-box", "");
        return colorTag;
    }

    // метод сбора удалённой метки
    public String getTags2() throws InterruptedException {
        String colorNoTag1 = tag.getCssValue("background");
        String colorNoTag = colorNoTag1.replace(" none repeat scroll 0% 0% / auto padding-box border-box", "");
        return colorNoTag;
    }

    // проверка на display элемента
    public void display() {
        WebElement element = driver.findElement(By.xpath("//div[@class='tl-tag-tender'][contains(@style,'background')]"));
        System.out.println(element.isDisplayed());
    }
}
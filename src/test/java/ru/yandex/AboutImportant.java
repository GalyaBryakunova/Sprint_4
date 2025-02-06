package ru.yandex;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.ui.WebDriverWait;


import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AboutImportant {

    private static WebDriver driver;

    public static By getQuestionBy(String numberOfQuestion) {
        return By.id("accordion__heading-" + numberOfQuestion);
    }

    public By getAccordionPanelBy(String numberOfPanel) {
        return By.id("accordion__panel-" + numberOfPanel);
    }

    public String getAccordionPanelTextBy(String numberOfQuestion){
        return driver.findElement(getAccordionPanelBy(numberOfQuestion)).getText();
    }

    private String panelText;
    private String accordionNumber;

    public AboutImportant(String a, String b) {
        this.panelText = a;
        this.accordionNumber = b;
    }


    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"Сутки — 400 рублей. Оплата курьеру — наличными или картой.", "0",},
                {"Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", "1",},
                {"Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", "2",},
                {"Только начиная с завтрашнего дня. Но скоро станем расторопнее.", "3",},
                {"Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", "4",},
                {"Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", "5",},
                {"Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", "6",},
                {"Да, обязательно. Всем самокатов! И Москве, и Московской области.", "7",},
        };
    }

    @BeforeClass
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }


    @Test
    public void testPageTitle() {
        WebElement questionElement = driver.findElement(getQuestionBy(accordionNumber));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", questionElement);

        questionElement.click();

        new WebDriverWait(driver,3).until(driver -> driver.findElement(getQuestionBy(accordionNumber)).getAttribute("hidden") == null);

        assertEquals("test", panelText, getAccordionPanelTextBy(accordionNumber));
    }
}

package ru.yandex;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HomePageScooter {
    private WebDriver driver;
    private String browser;

    private By firstOrderButton = By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/button[1]");

    private By secondOrderButton = By.xpath("//*[@id=\"root\"]/div/div[1]/div[2]/button[1]");


    public HomePageScooter( String browser) {
        this.browser = browser;
    }

    public void initDriver(String browser) {
        if(browser == "chrome") {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

           driver = new ChromeDriver(options);
        } else {
            String firefoxBinaryPath = "/Applications/Firefox.app/Contents/MacOS/firefox";

            FirefoxOptions options = new FirefoxOptions();
            options.setBinary(firefoxBinaryPath);
            options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

            WebDriverManager.firefoxdriver().setup();

            driver = new FirefoxDriver(options);
        }

    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"chrome"},
                {"firefox"},
        };
    }

    @Test
    public void clickOrderButtons(){
        initDriver(browser);
        driver.get("https://qa-scooter.praktikum-services.ru/");
        driver.findElement(firstOrderButton).click();
        WebElement findSecondOrderButton = driver.findElement(secondOrderButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", findSecondOrderButton);
        findSecondOrderButton.click();
        assertEquals(true, driver.findElement(By.className("Order_Content__bmtHS")).isDisplayed());
    }
}

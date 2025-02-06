package ru.yandex;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.Assert.assertEquals;



    @RunWith(Parameterized.class)
    public class CreateOrder {
        private final String name;
        private final String surname;
        private final String address;
        private final String phoneNumber;
        private final String comment;
        private final String browser;
        private final boolean result;

        public CreateOrder(String name, String surname, String address, String phoneNumber, String comment, String browser, boolean result) {
            this.name = name;
            this.surname = surname;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.comment = comment;
            this.browser = browser;
            this.result = result;
        }

        @Parameterized.Parameters
        public static Object[][] getCredentials() {
            return new Object[][]{
                    {"Иван", "Иванов", "Ленина, 3", "+79873332211", "Привезти с 10:00 до 14:00",  "chrome", true},
                    {"Петров", "Пётр", "К.Маркса, 10", "+79190001818", "Привезти с 10:00 до 14:00", "chrome", true},
                    {"Иван", "Иванов", "Ленина, 3", "+79873332211", "Привезти с 10:00 до 14:00",  "firefox", true},
                    {"Петров", "Пётр", "К.Маркса, 10", "+79190001818", "Привезти с 10:00 до 14:00", "firefox", true},
            };
        }

        public WebDriver initDriver(String browser) {
            if(browser == "chrome") {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

                return new ChromeDriver(options);
            } else {
                String firefoxBinaryPath = "/Applications/Firefox.app/Contents/MacOS/firefox";

                FirefoxOptions options = new FirefoxOptions();
                options.setBinary(firefoxBinaryPath);
                options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

                WebDriverManager.firefoxdriver().setup();

                return new FirefoxDriver(options);
            }

        }

        @Test
        public void ForWhomFormTest() {
            WebDriver driver = initDriver(browser);

            driver.get("https://qa-scooter.praktikum-services.ru/order");
            driver.findElement(By.id("rcc-confirm-button")).click();
            driver.findElement(By.xpath("//*[@placeholder=\"* Имя\"]")).sendKeys(name);
            driver.findElement(By.xpath("//*[@placeholder=\"* Фамилия\"]")).sendKeys(surname);
            driver.findElement(By.xpath("//*[@placeholder=\"* Адрес: куда привезти заказ\"]")).sendKeys(address);
            driver.findElement(By.xpath("//*[@placeholder=\"* Станция метро\"]")).click();
            driver.findElement(By.xpath("//*[@data-index=\"0\"]")).click();
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[5]/input")).sendKeys(phoneNumber);
            driver.findElement(By.xpath("//*[@placeholder=\"* Телефон: на него позвонит курьер\"]")).click();
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button")).click();
            driver.findElement(By.xpath("//*[@placeholder=\"* Когда привезти самокат\"]")).click();
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/div[2]/div[3]/div[3]")).click();
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/div/div[1]")).click();
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/div[2]/div[3]")).click();
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[3]/label[1]")).click();
            driver.findElement(By.xpath("//*[@placeholder=\"Комментарий для курьера\"]")).sendKeys(comment);
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button[2]")).click();
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[5]/div[2]/button[2]")).click();
            assertEquals(result, driver.findElement(By.className("Order_Content__bmtHS")).isDisplayed());
            assertEquals(result, driver.findElement(By.className("Order_ModalHeader__3FDaJ")).isDisplayed());

            driver.quit();
        }
}


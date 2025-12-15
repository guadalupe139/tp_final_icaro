// configurar y cerrar el webdriver
package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.InputStream;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.io.FileNotFoundException;

public class BaseTest {

    public static WebDriver driver;
    protected  static Properties config;

    public static WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public static void setup() throws IOException {

        config = new Properties();
        try (InputStream inStream = BaseTest.class.getClassLoader().getResourceAsStream("config/config.properties")) {

            if (inStream == null) {
                throw new FileNotFoundException("El archivo config/config.properties no fue encontrado en la carpeta 'resources/config'.");
            }
            config.load(inStream);


            String browser = config.getProperty("browser");
            String url = config.getProperty("baseUrl");

            if (browser == null) {
                throw new IllegalArgumentException("La clave 'browser' no fue encontrada en config.properties o es null.");
            }

            if (browser.equalsIgnoreCase("Chrome")) {
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.setAcceptInsecureCerts(true);
                options.addArguments("--ignore-certificate-errors");

                driver = new ChromeDriver(options);

            } else if (browser.equalsIgnoreCase("Firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else {
                throw new IllegalArgumentException("Navegador no soportado: " + browser);
            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            driver.get(url);
        }
    }

    @AfterSuite
    public static void tearDown(){
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
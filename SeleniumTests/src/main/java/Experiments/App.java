package Experiments;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class App 
{
    private static final String CHROMIUM_EXECUTABLE_PATH = "/snap/chromium/2565/usr/lib/chromium-browser/chrome";
    private static final String CHROMEDRIVER_PATH = "/usr/local/bin/chromedriver";

    static {
        //System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
    }

    private static void StartAndCloseBrowser() throws InterruptedException
    {
        final ChromeOptions options = new ChromeOptions();
        options.setBinary(CHROMIUM_EXECUTABLE_PATH);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        options.addArguments("--remote-debugging-port=9222");

        final ChromeDriver driver = new ChromeDriver(options);

        TimeUnit.SECONDS.sleep(5);

        driver.quit();
    }

    public static void main(String[] args) throws InterruptedException
    {
        StartAndCloseBrowser();
    }
}

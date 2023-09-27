package YandexPracticum;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumMetroTest {

    private WebDriver driver;
    private MetroHomePage metroPage;

    private static final String CITY_SAINTP = "Санкт-Петербург";
    private static final String STATION_SPORTIVNAYA = "Спортивная";
    private static final String STATION_LUBYANKA = "Лубянка";
    private static final String STATION_KRASNOGVARD = "Красногвардейская";

    private static final String CHROMIUM_EXECUTABLE_PATH = "/snap/chromium/2565/usr/lib/chromium-browser/chrome";
    private static final String CHROMEDRIVER_PATH = "/usr/local/bin/chromedriver";

    static {
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
    }

    @Before
    public void setUp() {
        // открыли браузер
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.setBinary(CHROMIUM_EXECUTABLE_PATH);
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        // перешли на страницу тестового приложения
        driver.get("https://qa-metro.stand-2.praktikum-services.ru/");

        // создали объект класса страницы стенда
        metroPage = new MetroHomePage(driver);
        // дождались загрузки страницы
        metroPage.waitForLoadHomePage();

    }

    // проверили выбор города
    @Test
    public void checkChooseCityDropdown() {
        // выбрали Санкт-Петербург в списке городов
        metroPage.chooseCity(CITY_SAINTP);
        // проверили, что видна станция метро «Спортивная»
        metroPage.waitForStationVisibility(STATION_SPORTIVNAYA);
    }

    /*
    // проверяем отображение времени маршрута
    @Test
    public void checkRouteApproxTimeIsDisplayed() {
        // построили маршрут от «Лубянки» до «Красногвардейской»
        metroPage.buildRoute(STATION_LUBYANKA, STATION_KRASNOGVARD);
        // проверили, что у первого маршрута списка отображается примерное время поездки
        Assert.assertEquals("≈ 36 мин.", metroPage.getApproximateRouteTime(0));
    }
    */

    @After
    public void tearDown() {
        // закрыли браузер
        driver.quit();
    }
}
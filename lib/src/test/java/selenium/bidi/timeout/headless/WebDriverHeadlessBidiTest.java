package selenium.bidi.timeout.headless;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.NavigationResult;
import org.openqa.selenium.bidi.browsingcontext.ReadinessState;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WebDriverHeadlessBidiTest {

    private final String url = "https://www.selenium.dev/documentation/webdriver/bidirectional/webdriver_bidi/";

    @Test public void userShouldSeeUrlLoadSuccessfully(){

        // Enable verbose logging
        System.setProperty("webdriver.chrome.logfile", "./chromedriver-logs");
        System.setProperty("webdriver.chrome.verboseLogging", "true");

        // Setup ChromeOptions

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("webSocketUrl", true);
        chromeOptions.addArguments("--headless=new");

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone 12 Pro");

        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        // Create WebDriver

        WebDriver webDriver = new ChromeDriver(chromeOptions);

        // Create BrowsingContext

        BrowsingContext browsingContext =
                new BrowsingContext(webDriver, webDriver.getWindowHandle());
        browsingContext.activate();

        // Navigate to https://www.selenium.dev/documentation/webdriver/bidirectional/webdriver_bidi/

        NavigationResult navigationResult =
                browsingContext.navigate(url, ReadinessState.COMPLETE);

        assertEquals(url, navigationResult.getUrl(), "User should see the webpage loaded successfully");

        // Find WebElement a[href='https://w3c.github.io/webdriver-bidi/']

        WebElement w3cBiDiLink =
                webDriver.findElement(new By.ByCssSelector("a[href='https://w3c.github.io/webdriver-bidi/']"));

        // Try to click WebElement
        assertTrue(w3cBiDiLink.isDisplayed(), "User should see the WebDriver W3C link displayed!");
        w3cBiDiLink.click();

        // Quit WebDriver

        webDriver.quit();
    }
}

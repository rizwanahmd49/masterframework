import gherkin.lexer.Ca;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverProvider {
    private static final String BROWSERSTACK_URL = "BROWSERSTACK_URL";
    private static final Logger log = LoggerFactory.getLogger(DriverProvider.class);

    @Value("${webdriver.remote.url}")
    private String webDriverRemoteUrl;

    public static WebDriver newDriver(Capabilities capabilities) throws MalformedURLException {
    if (isBrowserStack()){
        return newBrowserStackDriver(capabilities);
    }
    return newLocalDriver(capabilities);
    }

    private static String browserstackUrl() {
        return System.getenv(BROWSERSTACK_URL);
    }


    private static WebDriver newBrowserStackDriver(Capabilities capabilities) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities=new DesiredCapabilities(capabilities);
        desiredCapabilities.setCapability("build","IBD-SalesForce_BrowserStack+");
        desiredCapabilities.setCapability("project","IBD-Salesforce");
        desiredCapabilities.setCapability("name","IBD-Salesforce-Test"+capabilities);
        desiredCapabilities.setCapability("browserstack.local","true");
        desiredCapabilities.setCapability("browserstack.debug","true");
        desiredCapabilities.setCapability("browserstack.console","info");
        desiredCapabilities.setCapability("browserstack.networkLogs","true");
       /* System.getProperties().put("https.proxyHost","35.56.104.42");
        System.getProperties().put("https.proxyPort","8080");
        System.getProperties().put("http.proxyHost","35.56.104.42");
        System.getProperties().put("http.proxyPort","8080");
*/
       return new RemoteWebDriver(new URL(browserstackUrl()),desiredCapabilities);
    }
    private static boolean isBrowserStack() {
        if (browserstackUrl() != null) {
            return true;
        }
        return false;
    }


    private static WebDriver newLocalDriver(Capabilities capabilities) {
        WebDriver driver;
        if (capabilities.getBrowserName().equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver(new FirefoxOptions(capabilities));
            log.info("Firefox browser has been initialized");
        }else if (capabilities.getBrowserName().equalsIgnoreCase("chrome")){
            driver=new ChromeDriver(new ChromeOptions().merge(capabilities));
            log.info("chrome driver has been initialized");
        }else if (capabilities.getBrowserName().equalsIgnoreCase("ie")){
            driver=new InternetExplorerDriver(new InternetExplorerOptions(capabilities));
            log.info("internet explorer has been initialized");
        }else {
            String errorMessage = "Unrecognized Browser: {} " + capabilities.getBrowserName();
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;

    }
}

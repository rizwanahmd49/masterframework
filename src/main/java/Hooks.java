import org.openqa.selenium.WebDriver;

public class Hooks {
    private static ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();
    public static String url = "https://www.google.com/";

    public static WebDriver driver() {
        return drivers.get();
    }

    public static void setDriver(WebDriver driver) {
        if (driver() != null) {
            throw new IllegalStateException("Driver already set. Check your config for extraneous glue");
        }
        drivers.set(driver);
    }
    public static void closeDriver(){
        driver().quit();
        drivers.set(null);
    }
}

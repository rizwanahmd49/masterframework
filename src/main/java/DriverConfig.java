import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

public class DriverConfig {

private static final Logger log= LoggerFactory.getLogger(DriverConfig.class);

@Before
public void setUpHook() throws MalformedURLException {
    Hooks.setDriver(DriverProvider.newDriver(capabilities()));
}

@After
public void tearDown(){
    WebDriver driver=Hooks.driver();
    log.info("Closing driver ", driver);
    Hooks.closeDriver();
}
private Capabilities capabilities(){
    return new ChromeOptions();
}
}


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;


public class UtilClass {


    public static void doScroll(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) Hooks.driver();
        StringBuilder script = new StringBuilder("windows.scrollBy");
        script.append(x).append(",").append(y).append(")");
        js.executeScript(script.toString());
    }
}

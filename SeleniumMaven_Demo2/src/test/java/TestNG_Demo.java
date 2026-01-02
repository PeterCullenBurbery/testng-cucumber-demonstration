import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import java.time.Duration; 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;

public class TestNG_Demo {

    public WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        // REMOVED: System.setProperty line. 
        // Selenium 4+ manages the driver automatically.
        
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");
    }

    @Test
    public void Validation() {
        // Amazon's search bar ID
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("hard drive");
        
        // Amazon's search button (Updated to a more reliable selector)
        driver.findElement(By.id("nav-search-submit-button")).click();
    }

    @AfterTest
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }
}
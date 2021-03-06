package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class Page {

  public static void setDriver(WebDriver driver) {
    Page.driver = driver;
  }

  protected static WebDriver driver;

  public Page(){
    PageFactory.initElements(driver, this);
  }

  public static WebDriver getDriver() {
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    //System.setProperty("webdriver.chrome.driver", "C:/Users/Ekaterina/Documents/ААА/src/main/resources/chromedriver.exe");
    if (driver == null)
      driver = new ChromeDriver();
    return driver;
  }
}
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class VTBDBOTLAB1821 {


  private static WebDriver driver;

  @BeforeMethod
  public static void setUp() {
    System.setProperty("webdriver.chrome.driver", "C:/Users/Ekaterina/Documents/ААА/src/main/resources/chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().window().maximize(); //Разворачиваем окно
    driver.get("http://stand.vtb.jtcc.ru:16006/");
    WebDriverWait wait = new WebDriverWait(driver, 500);
    //Ждем появления и присваиваем полю Логин значение для входа
    WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Логин']")));
    userName.clear();
    userName.sendKeys("1111111111");
    //Ждем появления и присваиваем полю Пароль значение для входа
    WebElement userPass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Пароль']")));
    userPass.clear();
    userPass.sendKeys("1111111111");
    //Нажимаем кнопку вход
    WebElement ButtonInput = driver.findElement(By.xpath("//button[contains(.,'Войти')]"));
    ButtonInput.click();
    //Ждем появления и нажимаем кнопку "Создать ПП"
    WebElement ButtonNewPP = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='#/doc/payDocRu/new']")));
    ButtonNewPP.click();
  }

  @Test //
  public void Account() throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, 500);
    Actions action = new Actions(driver);

    WebElement ButtonAcc = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#appframe > form > div.Layout__base--1cl9w > div.LayoutContent__base--2Eko8.PayDocRu__contentPayDocRuForm--2joTZ > div.PayDocRu__tabContent--muDt2.PayDocRu__mainTab--3jpGE > div:nth-child(7) > div.fieldGroup__body > div:nth-child(2) > div.GridSpan__gridSpan--1jA-G.PayDocRu__narrow--1Erjg > div > div.field.field_input > div.field__label > button")));
    ButtonAcc.click();
    WebElement ButtonAll = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Все']")));
    ButtonAll.click();
    try {

      WebDriverWait element1 = new WebDriverWait(driver, 500);
      //!!!!WebElement str = driver.findElement(By.xpath("//span[contains(.,'40702')]"));//'30111' or '30231' or '30114' or '40702')]"));
      WebElement str = driver.findElement(By.xpath("//*[text()='40702']"));
    //  if (str.getSize() == null) str = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'30231')]")));
    //  if (str.getSize() == null) str = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'40702')]")));
//      if (str.getSize() == null) ;
              //"//span[text()='40702.840.4.00000001111']")));
      str.click();
      action.doubleClick(str).perform();
    }
    catch (Exception e) {Assert.fail("Не найдены подходящие счета!");}
    WebElement sss = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea.PayDocRu__purposeHeight--plT_F")));
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }
}

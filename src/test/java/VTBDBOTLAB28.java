import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

public class VTBDBOTLAB28 {

  private static WebDriver driver;
  private static LocalDate currentDate = LocalDate.now(); //Определяем текущую дату

  public static void changeDate(int change) throws IOException // Функция изменения даты на компе
  {
    Date d=new Date();
    d.setDate(d.getDate()+change);
    String pattern = "dd.MM.yyyy"; //set your date format
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    Runtime rt = Runtime.getRuntime();
    rt.exec("cmd /C date " +  format.format(d));
  }

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

  @Test //3. Ввести дату ранее, чем текущая -11 дней, сохранить документ
  public void DocDate_Minus11() throws Exception {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate.minusDays(11); //Убавляем у текущей даты 11 дней
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(String.format("%td%tm%tY", newDate, newDate, newDate)); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    try {
      driver.findElement(By.xpath("//div[contains(.,'Платежное поручение действительно в течение 10 дней после даты составления')]"));
    }
    catch (Exception e) {
      //new AssertionError(e.fillInStackTrace());//"Возникло исключение при поиске сообщения об ошибке");
      Assert.fail("Возникло исключение при поиске сообщения об ошибке");
    }
  }

  @Test //4. Ввести дату позднее, чем текущая +10 дней, сохранить документ
  public void DocDate_Plus10() throws Exception{
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate.plusDays(10); //Прибавляем к текущей дате 10 дней
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(String.format("%td%tm%tY", newDate, newDate, newDate)); // Поле даты документа
    driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    try {
      driver.findElement(By.xpath("//div[contains(.,'Платежное поручение действительно в течение 10 дней после даты составления')]"));
    }
    catch (Exception e) {
      Assert.fail("Возникло исключение при поиске сообщения об ошибке");
    }
  }

  @Test  //5. Ввести месяц > 12, сохранить документ
  public void DocDate_Month13() {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    String date = Integer.toString(newDate.getDayOfMonth()) +"13" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test //6. Ввести число > 31, сохранить документ
  public void DocDate_Day32() {
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys("32" + currentDate.getMonth() + currentDate.getYear()); // Поле даты документа
    driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    //if (driver.findElement(By.xpath("//div[contains(.,'Дата документа имеет недопустимое значение')]")) == null) Assert.fail();
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 7. Ввести 29 февраля для високосного года, сохранить документ
  public void DocDate_Visokosnyi() {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    while (!newDate.isLeapYear()) {
      newDate = newDate.plusYears(1);
    }
    String date = "2902" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertNotEquals(result, sss);
  }

  @Test // 8. Ввести 29 февраля для НЕ високосного года, сохранить документ
  public void DocDate_Novisokosnyi() {
    // Устанавливаем дату по условию
    LocalDate newDate = LocalDate.of(currentDate.getYear(), 2, 28);//currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    while (newDate.isLeapYear()) {
      newDate = newDate.plusYears(1);
    }
    String date = "2902" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 9. Ввести 30 февраля для високосного года, сохранить документ
  public void DocDate_Visokosnyi30() {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    while (!newDate.isLeapYear()) {
      newDate = newDate.plusYears(1);
    }
    String date = "3002" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 10. Ввести 31 Сентября, сохранить документ
  public void DocDate_3109() {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    String date = "3109" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 11. Ввести 31 Апреля, сохранить документ
  public void DocDate_3104() {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    String date = "3104" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 12. Ввести 31 Июня, сохранить документ
  public void DocDate_3106() {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    String date = "3106" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 13. Ввести 31 Ноября, сохранить документ
  public void DocDate_3111() {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    String date = "3111" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 14. Заполнить дату частично, сохранить документ
  public void DocDate_chast() {
    // Устанавливаем дату по условию
    String date = "0101";
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 15. Заполнить дату в формате, отличном от маски ДД.ММ.ГГГГ, сохранить документ
  public void DocDate_noformat(){
    // Устанавливаем дату по условию
    String date = "20181201";
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 16. Ввести в поле "Дата" символы, отличные от цифр, сохранить документ
  public void DocDate_symbol() {
    // Устанавливаем дату по условию
    String date = "aaaaaaaa";
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 18. Ввести 31 число месяца с 31 днями
  public void DocDate_31() {
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    while (newDate.lengthOfMonth() != 31) {
      newDate = newDate.plusMonths(1);
    }
    //Присваиваем дате 31е число месяца, в котором 31 день
    newDate = newDate.withDayOfMonth(31);
    String month = Integer.toString(newDate.getMonthValue());
    if (month.length() == 1) month = "0" + month;
    String date = Integer.toString(newDate.getDayOfMonth()) + month + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).sendKeys(date); // Поле даты документа
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertNotEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 19. Выбрать дату из календаря
  public void DocDate_calendar() {
    // Устанавливаем дату по условию
    driver.findElement(By.cssSelector("#appframe > form > div.Layout__base--1cl9w > div.LayoutContent__base--2Eko8.PayDocRu__contentPayDocRuForm--2joTZ > div.PayDocRu__tabContent--muDt2.PayDocRu__mainTab--3jpGE > div:nth-child(2) > div.fieldGroup > div > div > div:nth-child(2) > div > div.field__control > div:nth-child(1) > div.field__btn.field__btn_date > i")).click();
    LocalDate newDate = currentDate;
    newDate = newDate.plusDays(1);
    String str1 = "";
    switch (currentDate.getMonthValue()) {
      case 1: str1 = "января"; break;
      case 2: str1 = "февраля"; break;
      case 3: str1 = "марта"; break;
      case 4: str1 = "апреля"; break;
      case 5: str1 = "мая"; break;
      case 6: str1 = "июня"; break;
      case 7: str1 = "июля"; break;
      case 8: str1 = "августа"; break;
      case 9: str1 = "сентября"; break;
      case 10: str1 = "октября"; break;
      case 11: str1 = "ноября"; break;
      case 12: str1 = "декабря"; break;
    }
    String str = "td[title='" + Integer.toString(newDate.getDayOfMonth()) + " " + str1 + " " + Integer.toString(currentDate.getYear()) + " г.']";
    driver.findElement(By.cssSelector(str)).click(); //Выбираем в календаре дату на 1 день больше текущей даты
    driver.findElement(By.cssSelector("div.menuActions__group:nth-child(2) > div:nth-child(1) > button:nth-child(1)")).click(); //Кнопка "Сохранить"
    String result = driver.findElement(By.cssSelector("input[type='text'][class='field__input undefined ']")).getAttribute("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertNotEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

}
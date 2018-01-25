import model.User;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.AutoQALogin;
import pages.HomePage;
import pages.PayDocRuNew;
import pages.ReceiverAccount;
import static pages.Page.getDriver;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tests {

  private static LocalDate currentDate = LocalDate.now(); //Определяем текущую дату
  private static User user = new User("1111111111", "1111111111"); //Логин и пароль

  @BeforeMethod
  public void setUp(){
    getDriver().get("http://stand.vtb.jtcc.ru:16006/");
    AutoQALogin autoQALogin = new AutoQALogin();
    autoQALogin.loginToAutoQA(user);
    HomePage homePage = new HomePage();
    homePage.clickCreatePP();
  }

  @Test //3. Ввести дату ранее, чем текущая -11 дней, сохранить документ
  public void DocDateWMinus11() throws Exception {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate.minusDays(11); //Убавляем у текущей даты 11 дней
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(String.format("%td%tm%tY", newDate, newDate, newDate));
    payDocRuNew.clickSaveButton();
    try {
      payDocRuNew.messageField.isDisplayed();
    }
    catch (Exception e) {
      Assert.fail("Возникло исключение при поиске сообщения об ошибке");
    }
  }

  @Test //4. Ввести дату позднее, чем текущая +10 дней, сохранить документ
  public void DocDateWPlus10() throws Exception {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate.plusDays(10); //Убавляем у текущей даты 11 дней
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(String.format("%td%tm%tY", newDate, newDate, newDate));
    payDocRuNew.clickSaveButton();
    try {
      payDocRuNew.messageField.isDisplayed();
    }
    catch (Exception e) {
      Assert.fail("Возникло исключение при поиске сообщения об ошибке");
    }
  }

  @Test //5. Ввести месяц > 12, сохранить документ
  public void DocDateMonth13() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    String date = Integer.toString(newDate.getDayOfMonth()) +"13" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date);
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test //6. Ввести число > 31, сохранить документ
  public void DocDateDay32() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate("32" + currentDate.getMonth() + currentDate.getYear()); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 7. Ввести 29 февраля для високосного года, сохранить документ
  public void DocDateVisokosnyi() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    while (!newDate.isLeapYear()) {
      newDate = newDate.plusYears(1);
    }
    String date = "2902" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertNotEquals(result, sss);
  }

  @Test // 8. Ввести 29 февраля для НЕ високосного года, сохранить документ
  public void DocDateNovisokosnyi() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = LocalDate.of(currentDate.getYear(), 2, 28);//currentDate;
    //Прибавляем к году +1 пока год не станет НЕвисокосным
    while (newDate.isLeapYear()) {
      newDate = newDate.plusYears(1);
    }
    String date = "2902" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 9. Ввести 30 февраля для високосного года, сохранить документ
  public void DocDateVisokosnyi30() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    //Прибавляем к году +1 пока год не станет високосным
    while (!newDate.isLeapYear()) {
      newDate = newDate.plusYears(1);
    }
    String date = "3002" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 10. Ввести 31 Сентября, сохранить документ
  public void DocDate3109() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    String date = "3109" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 11. Ввести 31 Апреля, сохранить документ
  public void DocDate3104() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    String date = "3104" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 12. Ввести 31 Июня, сохранить документ
  public void DocDate3106() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    String date = "3106" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 13. Ввести 31 Ноября, сохранить документ
  public void DocDate3111() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    LocalDate newDate = currentDate;
    String date = "3111" + Integer.toString(newDate.getYear());
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 14. Заполнить дату частично, сохранить документ
  public void DocDateChast() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    String date = "0101";
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);//String.format("%td.%tm.%tY", currentDate, currentDate, currentDate));
  }

  @Test // 15. Заполнить дату в формате, отличном от маски ДД.ММ.ГГГГ, сохранить документ
  public void DocDateNoformat(){
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    String date = "20181201";
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 16. Ввести в поле "Дата" символы, отличные от цифр, сохранить документ
  public void DocDateSymbol() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    // Устанавливаем дату по условию
    String date = "aaaaaaaa";
    //Записываем дату в поле Дата документа
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertEquals(result, sss);
  }

  @Test // 18. Ввести 31 число месяца с 31 днями
  public void DocDate31() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
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
    payDocRuNew.sendKeysDocDate(date); // Поле даты документа
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertNotEquals(result, sss);
  }

  @Test // 19. Выбрать дату из календаря
  public void DocDateCalendar() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    payDocRuNew.clickCalendar();
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
    String str = "//td[@title='" + Integer.toString(newDate.getDayOfMonth()) + " " + str1 + " " + Integer.toString(currentDate.getYear()) + " г.']";
    PayDocRuNew.calendarCell(str).click(); //Выбираем в календаре дату на 1 день больше текущей даты
    payDocRuNew.clickSaveButton();
    String result = payDocRuNew.getAttributeDocDateField("value");
    String sss = String.format("%td.%tm.%tY", currentDate, currentDate, currentDate);
    Assert.assertNotEquals(result, sss);
  }

  @Test //https://jiraeu.epam.com/browse/VTBDBOTLAB-1821
  //Проверка заполнения адреса плательщика (если банк получателя является иностранным) !!!!!!!! Нет данных на стенде
  //Сделала просто простановку чек-боксов у счетов, начинающихся на 40817 и 40702, нажатие кнопки Ок (на форме выбора счетов контрагентов-получателей)
  //и Сохранить (на форме создания платежки)
  public void AccountReceiver() {
    PayDocRuNew payDocRuNew = new PayDocRuNew();
    payDocRuNew.clickButtonAcc();
    ReceiverAccount receiverAccount = new ReceiverAccount();
    receiverAccount.clickButtonAll();
    getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    List<WebElement> list = receiverAccount.tableField;
    for (WebElement row : list){
      List<WebElement> cells = row.findElements(By.xpath(".//div[contains(@class,'table__cell')]"));
      if ((cells.get(3).findElement(By.xpath(".//div")).getText().startsWith("40817")) || (cells.get(3).findElement(By.xpath(".//div")).getText().startsWith("40702"))){
        cells.get(0).click();
      }
    }
    receiverAccount.clickButtonOK();
    payDocRuNew.clickSaveButton();
  }
}

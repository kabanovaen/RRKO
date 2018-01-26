package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PayDocRuNew extends Page{

  public WebElement getSaveField() {
    return saveField;
  }

  public void setSaveField(WebElement saveField) {
    this.saveField = saveField;
  }

  public WebElement getDocDateField() {
    return docDateField;
  }

  public void setDocDateField(WebElement docDateField) {
    this.docDateField = docDateField;
  }

  public WebElement getMessageField() {
    return messageField;
  }

  public void setMessageField(WebElement messageField) {
    this.messageField = messageField;
  }

  public WebElement getCalendarField() {
    return calendarField;
  }

  public void setCalendarField(WebElement calendarField) {
    this.calendarField = calendarField;
  }

  public static WebElement getButtonAccField() {
    return buttonAccField;
  }

  public static void setButtonAccField(WebElement buttonAccField) {
    PayDocRuNew.buttonAccField = buttonAccField;
  }

  @FindBy(xpath = "//div[text()=\"Сохранить\"]")
  private WebElement saveField; //Кнопка "Сохранить"

  @FindBy(xpath = "//input[@type='text'][@class='field__input undefined ']")
  private WebElement docDateField; //Поле сообщение об ошибке для поля "Дата документа"

  @FindBy(xpath = "//div[contains(.,'Платежное поручение действительно в течение 10 дней после даты составления')]")
  public WebElement messageField; //Сообщение об ошибке "Платежное поручение действительно в течение 10 дней после даты составления" для поля "Дата доумента"

  @FindBy(xpath = "//div[contains(@class,'field__btn field__btn_date')]")
  private WebElement calendarField; //Элемент календаря

  @FindBy (xpath = "//div[contains(@class,'PayDocRu__receiverAccount')]//button")
  private static WebElement buttonAccField; //Кнопка выбора счета получателя

  public static WebElement calendarCell(String path) {
    return driver.findElement(By.xpath(path));
  }

  //TODO Нажать кнопку "Сохранить"
  public void clickSaveButton() {
    new WebDriverWait(getDriver(),10).until(ExpectedConditions.elementToBeClickable(saveField)).click();
  }

  //TODO Нажать кнопку раскрытия календаря
  public void clickCalendar() {
    new WebDriverWait(getDriver(),20).until(ExpectedConditions.elementToBeClickable(calendarField)).click();
  }

  //TODO Запись в поле "Дата документа" значения
  public void sendKeysDocDate(String str) {
    new WebDriverWait(getDriver(),20).until(ExpectedConditions.elementToBeClickable(docDateField)).sendKeys(str);
  }

  //TODO Считывание значения в поле "Дата документа"
  public String getAttributeDocDateField(String str) {
    return docDateField.getAttribute(str);
  }

  //TODO Нажать кнопку "Счет" получателя
  public ReceiverAccount clickButtonAcc() {
    new WebDriverWait(getDriver(),20).until(ExpectedConditions.elementToBeClickable(buttonAccField)).click();
    return new ReceiverAccount();
  }
}
package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends Page{

  @FindBy(xpath = "//div[contains(text(), 'Создать ПП')]")
  private WebElement createPPField;

  //TODO Нажать кнопку "Создать ПП"
  public PayDocRuNew clickCreatePP() {
    new WebDriverWait(getDriver(),20).until(ExpectedConditions.elementToBeClickable(createPPField)).click();
    return new PayDocRuNew();
  }
}
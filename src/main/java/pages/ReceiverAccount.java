package pages;

        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.List;

public class ReceiverAccount extends Page{

  public WebElement getButtonAllField() {
    return buttonAllField;
  }

  public void setButtonAllField(WebElement buttonAllField) {
    this.buttonAllField = buttonAllField;
  }

  public WebElement getButtonOKField() {
    return buttonOKField;
  }

  public void setButtonOKField(WebElement buttonOKField) {
    this.buttonOKField = buttonOKField;
  }

  public List<WebElement> getTableField() {
    return tableField;
  }

  public void setTableField(List<WebElement> tableField) {
    this.tableField = tableField;
  }

  @FindBy(xpath = "//span[text()='Все']")
  private WebElement buttonAllField; //Кнопка "Все"

  @FindBy(xpath = "//div[contains(text(), 'Ok')]")
  private  WebElement buttonOKField;

  @FindBy(xpath = "//div[contains(@class, 'table__row')]")
  public List<WebElement> tableField; //Таблица строк контрагентов - получателей

  public void clickButtonAll() {
    new WebDriverWait(getDriver(),20).until(ExpectedConditions.elementToBeClickable(buttonAllField)).click();
  }

  public PayDocRuNew clickButtonOK() {
    buttonOKField.click();
    return new PayDocRuNew();
  }
}

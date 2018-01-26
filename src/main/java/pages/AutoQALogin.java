package pages;

import model.User;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutoQALogin extends Page{
  public WebElement getLoginField() {
    return loginField;
  }

  public void setLoginField(WebElement loginField) {
    this.loginField = loginField;
  }

  public WebElement getPasswordField() {
    return passwordField;
  }

  public void setPasswordField(WebElement passwordField) {
    this.passwordField = passwordField;
  }

  public WebElement getButtonField() {
    return buttonField;
  }

  public void setButtonField(WebElement buttonField) {
    this.buttonField = buttonField;
  }

  @FindBy(xpath = "//input[contains(@placeholder, 'Логин')]")
  private WebElement loginField;

  @FindBy(xpath = "//input[contains(@placeholder, 'Пароль')]")
  private WebElement passwordField;

  @FindBy(xpath = "//button[contains(text(),'Войти')]")
  private WebElement buttonField;

//TODO Сделать логин
  public void setUserName(String strUserName) {
    loginField.clear();
    loginField.sendKeys(strUserName);
  }
//TODO Сделать пароль
  public void setPassword(String strPassword) {
    passwordField.clear();
    passwordField.sendKeys(strPassword);
  }
//TODO Нажать кнопку Вход
  public void clickEntry() {
    new WebDriverWait(getDriver(),20).until(ExpectedConditions.elementToBeClickable(buttonField)).click();
  }
//TODO Ввод логина, пароля и нажатие кнопки Вход на странице авторизации
  public HomePage loginToAutoQA(User user) {
        setUserName(user.getLogin());
        setPassword(user.getPassword());
        clickEntry();
        return new HomePage();
  }
}
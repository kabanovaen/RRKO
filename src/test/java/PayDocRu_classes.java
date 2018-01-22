import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class PayDocRu_classes {

  public static void main(String[] args) {
    String url = "http://stand.vtb.jtcc.ru:16006/";
    String pdr_url = "#/doc/payDocRu/new";
    Date currentDate = new Date();

    WebDriver driver = new ChromeDriver();
    System.getProperty("user.dir");

  }
  public static class User {
    String login, password;
    User() {
      this.login = "1111111111";
      this.password = "1111111111";
    }
  }

  public static class Bank {
   String bic;
    String corrAccount;
   String name;
  }
  public static class Payer {
    private static final String REGEX = "^([0-9]{9})?$"; //Паттерн
    private static final String REGEX1 = "^(([0-9]{12})|([0-9]{10}))?$";
    String innKio;
    String kpp;
    String name;
    String account;
    Bank bank_Paier;

    Payer () {
      this.innKio = "645003581107";
      this.kpp = "222222222";
      this.name = "Payer name";
      this.account = "40702810588880000001";
      this.bank_Paier.bic = "422028370";
      this.bank_Paier.corrAccount = "30233810204000100000";
      this.bank_Paier.name = "Филиал Банка ВТБ (Открытое акционерное общество) в г. Ставрополе";

    }
  }

  public static class Receiver {
    String innKio;
    String kpp;
    String name;
    String account;
    Bank bank_receiver;

    Receiver() {
      this.innKio = "772900273375";
      this.kpp = "333333333";
      this.name = "Payer name 2";
      this.account = "401178101111111112";
      this.bank_receiver.bic = "044525117";
      this.bank_receiver.corrAccount = "30101810245250000117";
      this.bank_receiver.name = "Bank name 2";
    }
  }

  public static class PaymentGround {
    int operationCode;
    float nds;
    String description;
    PaymentGround () {
      this.operationCode = 0;
      this.nds = 100;
      this.description = "В том числе НДС 10% - 100.00";
    }
  };

  public static class PayDocRu {
    SimpleDateFormat debitDate;// = new Date(); //Поле даты создания ПП
    SimpleDateFormat receiveDate;// = new Date(); //Поле даты отправки ПП
    int documentNumber;
    //documentDate
    String channel;
    int code;
    float amount;
    int paymentPriority;
    String operationType;

    PayDocRu() {
      this.debitDate = new SimpleDateFormat("YYYY-DD-MMTHH:mm:ss.SSSZ");
      this.receiveDate = new SimpleDateFormat("YYYY-DD-MMTHH:mm:ss.SSSZ");
      this.documentNumber = 1;
      this.channel = "WEB";
      this.code = 0;
      this.amount = 1000;
      this.paymentPriority = 5;
      this.operationType = "01";
    }
  }
}

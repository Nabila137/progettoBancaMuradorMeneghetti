package main.java.Bank;

public class DepositWithdraw {

  // Can be used to check whether I can make investment (same code/control)
  public static boolean canDepositWithdraw(double portafoglio, double depositMoney) {

    if ((depositMoney > 0) && (depositMoney <= portafoglio)) return true;

    return false;
  }
}

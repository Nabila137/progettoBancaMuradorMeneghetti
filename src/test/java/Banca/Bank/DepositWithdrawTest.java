package main.java.Bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DepositWithdrawTest {

  @Test
  void testCanDepositWithdrawWhenAmountIsPositiveAndWithinBalance() {
    double depositMoney = 50;
    double portafoglio = 100;

    boolean risultato = DepositWithdraw.canDepositWithdraw(portafoglio, depositMoney);

    Assert.assertTrue(risultato);
  }

  @Test
  void testCanDepositWithdrawWhenAmountIsEqualsToBalance() {
    double depositMoney = 50;
    double portafoglio = 50;

    boolean risultato = DepositWithdraw.canDepositWithdraw(portafoglio, depositMoney);

    Assert.assertTrue(risultato);
  }

  @Test
  void testCanNotDepositWithdrawWhenAmountExceedsBalance() {
    double depositMoney = 100;
    double portafoglio = 50;

    boolean risultato = DepositWithdraw.canDepositWithdraw(portafoglio, depositMoney);

    Assert.assertFalse(risultato);
  }

  @Test
  void testCanNotDepositWithdrawWhenAmountIsZero() {
    double depositMoney = 0;
    double portafoglio = 100;

    boolean risultato = DepositWithdraw.canDepositWithdraw(portafoglio, depositMoney);

    Assert.assertFalse(risultato);
  }

  @Test
  void testCanNotDepositWithdrawWhenAmountIsNegative() {
    double depositMoney = -50;
    double portafoglio = 100;

    boolean risultato = DepositWithdraw.canDepositWithdraw(portafoglio, depositMoney);

    Assert.assertFalse(risultato);
  }

  @Test
  void testCanDepositWithdrawWhenBalanceIsEmpty() {
    double depositMoney = 50;
    double portafoglio = 0;

    boolean risultato = DepositWithdraw.canDepositWithdraw(portafoglio, depositMoney);

    Assert.assertFalse(risultato);
  }

  @Test
  void testCanMakeInvestment() {
    double depositMoney = 50;
    double portafoglio = 0;

    boolean risultato = DepositWithdraw.canDepositWithdraw(portafoglio, depositMoney);

    Assert.assertFalse(risultato);
  }
}

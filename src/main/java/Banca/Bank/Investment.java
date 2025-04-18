package main.java.Bank;

import java.time.LocalDate;

public class Investment {
  private double amount;
  private int rischio;
  private int durata;
  private LocalDate startDate;
  private LocalDate endDate;
  private double guadagno;
  private double ritorno;
  private int successPercentage = Methods.randomNumber(1, 100);

  public Investment(double amount, int rischio, int durata, LocalDate startDate, double guadagno) {
    this.amount = amount;
    this.rischio = rischio;
    this.durata = durata;
    this.startDate = startDate;
    this.guadagno = guadagno;
    // is it okay to pass them here inside the constructor
    this.endDate = calculateEndDate(startDate, durata);
    this.ritorno = ricavo(successPercentage, rischio, amount, guadagno);
  }

  private LocalDate calculateEndDate(LocalDate startDate, int durata) {

    LocalDate data = LocalDate.now();

    if (durata >= 0) {
      int days = durata * 30;
      try {
        data = (startDate.plusDays(days));
      } catch (IllegalArgumentException e) {
        // return null;
        return LocalDate.now();
      }
    }
    return data;
  }

  public double ricavo(int percentualeSuccesso, int rischio, double investimento, double guadagno) {

    double ritorno = -1;
    double successRate = (double) percentualeSuccesso / 100;
    double gainLose = investimento * successRate * guadagno;
    if ((percentualeSuccesso <= 100)
        && (percentualeSuccesso >= 0)
        && (rischio <= 100)
        && (rischio > 0)
        && (investimento > 0)) {
      if (percentualeSuccesso >= rischio) {
        ritorno = investimento + gainLose;
      } else {
        ritorno = investimento - gainLose;
      }

      ritorno = Math.round(ritorno * 100.0) / 100.0;
    }

    return ritorno;
  }

  public double getAmount() {
    return amount;
  }

  public int getRischio() {
    return rischio;
  }

  public int getDurata() {
    return durata;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public double getGuadagno() {
    return guadagno;
  }

  public double getRitorno() {
    return ritorno;
  }

  public int getSuccessPercentage() {
    return successPercentage;
  }

  @Override
  public String toString() {
    String s = "";
    s += "Invested amount: " + amount + "\n";
    s += "Risk level: " + rischio + "\n";
    s += "Duration: " + durata + "\n";
    s += "Gain rate: " + guadagno + "\n";
    s += "Success percentage: " + successPercentage + "\n";
    s += "Start date: " + startDate + "\n";
    s += "End date: " + endDate + "\n";
    s += "Return: " + ritorno;
    return s;
  }
}

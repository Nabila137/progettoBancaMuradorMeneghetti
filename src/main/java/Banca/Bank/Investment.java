package main.java.Bank;

import java.time.LocalDate;

public class Investment {
  private double amount;
  private int rischio;
  private int durata;
  private LocalDate
      startDate; // take the initial date which changes every time I move to the next month
  private LocalDate endDate;
  private double guadagno;
  private double ritorno;
  private int successPercentage =
      Methods.randomNumber(
          1, 100); // this way it changes every time I make a move (a little bit casual).

  public Investment(double amount, int rischio, int durata, LocalDate startDate, double guadagno) {
    // this.successPercentage = randomNumber(1, 100);
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
    // System.out.println("Percentuale Successo: " + percentualeSuccesso);
    // System.out.println("Rischio: " + rischio);
    // System.out.println("investimento: " + investimento);
    // System.out.println("guadagno: " + guadagno);
    double successRate = (double) percentualeSuccesso / 100;
    // System.out.println("successRate: " + successRate);
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
    return endDate; // calculateEndDate(startDate, durata);
  }

  public double getGuadagno() {
    return guadagno;
  }

  public double getRitorno() {
    return ritorno; // ricavo(successPercentage, rischio, amount, guadagno);
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

  // public static void main (String [] args) {

  // System.out.println(ricavo (23, 50, 200.0, 0.05));
  // System.out.println (randomNumber(1, 100));
  // }
}

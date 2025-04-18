package main.java.Bank;

public class Utente {

  private String nome;
  private String cognome;
  private Account account;
  private double portafoglio = 0;
  private double salary = 100;

  public Utente(String nome, String cognome) {
    this.nome = nome;
    this.cognome = cognome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public double getPortafoglio() {
    return portafoglio;
  }

  public void setPortafoglio(double portafoglio) {
    this.portafoglio = portafoglio;
  }

  public void aggiungiSoldi(double soldi) {
    portafoglio += soldi;
  }

  public void togliSoldi(double soldi) {
    portafoglio -= soldi;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public void nextMonth(int months) {

    aggiungiSoldi(getSalary());
    account.setInitialDate(account.getInitialDate().plusMonths(months));
    // here I have to change the initial date
    // Initial date is the moment you create the account and it will change whenever you click on
    // "nextMonth". In this way you can track the investments without altering the "start date" of
    // the investment.
    // every investment takes the initial date as start date
  }

  @Override
  public String toString() {
    return "Utente [nome=" + nome + ", cognome=" + cognome + ", account=" + account + "]";
  }
}

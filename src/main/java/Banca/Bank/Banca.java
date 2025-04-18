package main.java.Bank;

public class Banca {

  Utente utente;

  public Banca(Utente utente) {
    this.utente = utente;
  }

  @Override
  public String toString() {
    return "Banca [utente=" + utente + "]";
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public void depositWithdraw(double money) {

    utente.getAccount().aumentaContoBancario(money);
    utente.togliSoldi(money);
  }

  public double withdrawal(double money) {

    return money *= -1;
  }
}

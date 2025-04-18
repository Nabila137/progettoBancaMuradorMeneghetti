package main.java.Bank;

import java.time.LocalDate;
import java.util.Vector;

public class Account {
  private String username;
  private String password;
  private Vector<Investment> investments = new Vector<Investment>();
  private double contoBancario;
  private LocalDate initialDate;
  private int numInvestimenti;

  public Account(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Vector<Investment> getInvestments() {
    return investments;
  }

  public void setInvestments(Vector<Investment> investments) {
    this.investments = investments;
  }

  public LocalDate getInitialDate() {
    return initialDate;
  }

  public void setInitialDate(LocalDate initialDate) {
    this.initialDate = initialDate;
  }

  public double getContoBancario() {
    return contoBancario;
  }

  public void setContoBancario(double contoBancario) {
    this.contoBancario = contoBancario;
  }

  public void aumentaContoBancario(double quantita) {
    this.contoBancario += quantita;
  }

  public void diminuisciContoBancario(double quantita) {
    this.contoBancario -= quantita;
  }

  public void addInvestimento(Investment x) {
    this.investments.add(x);
  }

  public int getNumInvestimenti() {
    return numInvestimenti;
  }

  public void setNumInvestimenti(int numInvestimenti) {
    this.numInvestimenti = numInvestimenti;
  }

  public void removeInvestment(Investment x) {
    investments.remove(x);
  }

  @Override
  public String toString() {
    return "Account [username="
        + username
        + ", password="
        + password
        + ", investments="
        + investments
        + ", contoBancario="
        + contoBancario
        + ", initialDate="
        + initialDate
        + ", numInvestimenti="
        + numInvestimenti
        + "]";
  }

  public Investment[] lookForReturns() {
    int size = investments.size();
    Investment[] returns = new Investment[size];
    int cont = 0;

    if (size > 0) {
      for (int i = 0; i < size; i++) {
        if (initialDate.isAfter(investments.get(i).getEndDate())
            || initialDate.isEqual(investments.get(i).getEndDate())) {
          returns[cont] = investments.get(i);
          cont++;
          aumentaContoBancario(investments.get(i).getRitorno());
          investments.remove(i);
          i--;
          size--;
        }
      }
    } else {
      return null;
    }

    if (cont > 0) {
      System.out.println("Contatore è maggiore di 0");
      System.out.println(cont);
      return returns;
    } else {
      return null;
    }
  }
}

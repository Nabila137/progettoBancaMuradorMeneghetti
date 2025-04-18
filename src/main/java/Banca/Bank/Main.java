package main.java.Bank;

import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {

    Utente utente = null;
    Banca banca = new Banca(utente);
    SwingUtilities.invokeLater(
        () -> {
          LoginFrame frame = new LoginFrame(banca);
          frame.setVisible(true);
        });
  }
}

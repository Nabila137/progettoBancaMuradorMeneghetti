package main.java.Banca;

import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		Utente utente = null;
		Bank bank = new Bank("banca", utente);
		new LoginFrame(bank);
	}

}

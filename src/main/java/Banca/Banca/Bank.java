package main.java.Banca;

import java.util.Vector;
import main.java.Tools.*;

public class Bank {
	
	private Vector <Utente> utenti;;
	private String nome;
	private final String countryCode = "IT";
	private int chackDigit = CommonMethods.randomNumberUnsigned(2);
	private char nationalCheckDigit = 'X';
	private int bankCode;
	/*try {
		number = (int) Integer.parseInt(Random.randomNumber(5));
	} catch (NumberFormatException e) {
		
	}*/
	private String s = "";
	private String branchCode;// = Random.randomString(5);
	
	public Bank (String nome, int bankCode, String branchCode) {
		
		 new Vector <Utente> (10, 5);
		 this.nome = nome;
		 this.bankCode = bankCode;
		 this.branchCode = branchCode;
		 
	}
	
	public String getNome () {
		return nome;
	}

	public Vector<Utente> getUtenti() {
		return utenti;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public int getChackDigit() {
		return chackDigit;
	}

	public char getNationalCheckDigit() {
		return nationalCheckDigit;
	}

	public int getBankCode() {
		return bankCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setUtenti(Vector<Utente> utenti) {
		this.utenti = utenti;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	/*public void meseSuccessivo () {
		
	}*/

	@Override
	public String toString() {
		return "Bank [utenti=" + utenti + ", nome=" + nome + ", countryCode=" + countryCode + ", chackDigit="
				+ chackDigit + ", nationalCheckDigit=" + nationalCheckDigit + ", bankCode=" + bankCode + ", branchCode="
				+ branchCode + "]";
	}
	
	
	

}

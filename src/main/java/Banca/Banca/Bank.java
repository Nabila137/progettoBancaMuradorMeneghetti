package main.java.Banca;

import java.util.Vector;
import main.java.Tools.*;

public class Bank {
	
	//private Vector <Utente> utenti;;
	private String nome;
	private Utente utente;
	private double contoBancario;
	private int numInvestimenti;
	//private final String countryCode = "IT";
	//private int chackDigit = CommonMethods.randomNumberUnsigned(2);
	//private char nationalCheckDigit = 'X';
	//private int bankCode;
	/*try {
		number = (int) Integer.parseInt(Random.randomNumber(5));
	} catch (NumberFormatException e) {
		
	}*/
	//private String s = "";
	//private String branchCode;// = Random.randomString(5);
	
	public Bank (String nome, Utente utente/*int bankCode, String branchCode*/) {
		
		// new Vector <Utente> (10, 5);
		 this.nome = nome;
		 this.utente = utente;
		 //this.bankCode = bankCode;
		 //this.branchCode = branchCode;
		 
	}
	
	public String getNome () {
		return nome;
	}
	
	

	/*public Vector<Utente> getUtenti() {
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
	}*/

	public int getNumInvestimenti() {
		return numInvestimenti;
	}

	public void setNumInvestimenti(int numInvestimenti) {
		this.numInvestimenti = numInvestimenti;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getContoBancario() {
		return contoBancario;
	}
	
	public void setContoBancario(double soldi) {
		contoBancario += soldi;
	}

	/*public void setBankCode(int bankCode) {
		this.bankCode = bankCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	/*public void meseSuccessivo () {
		
	}*/

	/*@Override
	public String toString() {
		return "Bank [utenti=" + utenti + ", nome=" + nome + ", countryCode=" + countryCode + ", chackDigit="
				+ chackDigit + ", nationalCheckDigit=" + nationalCheckDigit + ", bankCode=" + bankCode + ", branchCode="
				+ branchCode + "]";
	}*/
	
	public void deposit(double depositMoney) {
		
		//if (Deposit_Withdraw.canDeposit(utente.getPortafoglio().getSoldi(), balance, depositMoney))
		utente.getPortafoglio().togliSoldi(depositMoney);
		contoBancario += depositMoney;
		
	}
	
	public void withdraw(double withdrawal) {
		
		withdrawal *= -1;
		//if (Deposit_Withdraw.canWithdraw(utente.getPortafoglio().getSoldi(), balance, withdrawal))
		utente.getPortafoglio().togliSoldi(withdrawal);
		contoBancario += withdrawal;
		
	}
	
	public double ricavo (int percentualeSuccesso, int rischio, double investimento, double guadagno) {
		
		//int percentualeRandom = (int)(Math.random() * 100 + 1);
		double ritorno = -1;
		double successRate = percentualeSuccesso/100;
		double successLoss = investimento * successRate * guadagno; 
		if ((percentualeSuccesso <= 100) && (percentualeSuccesso >= 0) && (rischio <= 100) && (rischio > 0) && (investimento > 0)) {
			if (percentualeSuccesso >= rischio) {
				ritorno = investimento  + successLoss;
			} else {
				ritorno = investimento  - successLoss;			}
		}
		
		return ritorno;
	}
	

}

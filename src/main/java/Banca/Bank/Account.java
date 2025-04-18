package main.java.Bank;

import java.time.LocalDate;
import java.util.Vector;

public class Account {
	private String username;
	private String password;
	private Vector<Investment> investments = new Vector <Investment> ();
	private double contoBancario;
	private LocalDate initialDate;
	private int numInvestimenti;
	
	public Account (String username, String password) {
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
	
	public void aumentaContoBancario (double quantita) {
		this.contoBancario += quantita;
	}
	
	public void diminuisciContoBancario (double quantita) {
		this.contoBancario -= quantita;
	}

	/*public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}*/
	

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
		return "Account [username=" + username + ", password=" + password + ", investments=" + investments
				+ ", contoBancario=" + contoBancario + ", initialDate=" + initialDate + ", numInvestimenti="
				+ numInvestimenti + "]";
	}
	
	

	//devono essere o static class oppure classe astratte.
	/*public boolean canDeposit(double portafoglio, double contoBancario, double depositMoney) {

		if ((depositMoney <= portafoglio) && (depositMoney > 0))
			return true;

		return false;
	}

	public boolean canWithdraw(double portafoglio, double contoBancario, double withdrawal) {

		return canDeposit(contoBancario, portafoglio, withdrawal);

	}
	
	public void deposit(double depositMoney) {
		
		if (canDeposit (utente.getPortafoglio(), contoBancario, depositMoney)) {
			utente.aggiungiSoldi(depositMoney);
			contoBancario += depositMoney;
		}
		
	}

	public void withdraw(double withdrawal) {

		//withdrawal *= -1;
		if (canWithdraw (utente.getPortafoglio(), contoBancario, withdrawal)) {
			utente.togliSoldi(withdrawal);
			contoBancario += withdrawal;
		}
		
	}*/
	
	
	//Check if the array is null and show the investments paying attention to the null spaces. example: s[2] != null ?
	public Investment[] lookForReturns(/*LocalDate initialDate*/) {
		System.out.println("LOoking for returns.");
		int size = investments.size();
		System.out.println("Size: " + size);
		Investment[] returns = new Investment[size];
		int cont = 0;
		//System.out.println(returns);

		if (size > 0) {
			System.out.println("Entered if.");
			for (int i = 0; i < size; i++) {
				System.out.println("Entered for");
				if (initialDate.isAfter(investments.get(i).getEndDate())
						|| initialDate.isEqual(investments.get(i).getEndDate())) {
					System.out.println("InitialDate:" + initialDate);
					System.out.println("Start Date:" + investments.get(i).getStartDate());
					System.out.println("End Date:" + investments.get(i).getEndDate());
					returns[cont] = investments.get(i);
					cont++;
					aumentaContoBancario (investments.get(i).getRitorno());
					System.out.println("Contatore: " + cont);
					investments.remove(i);
					i--;//check
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
		//return returns;
		//when I use this method, I need to check if the Array is empty or not. If it's empty there is no investments and for the way other I'll show the investments info.
	}	
	
	public static void main (String[] args) {
		
		/*String [] string = new String [4];
		//string = null;
		string[0] = "hello";
		System.out.println(string[1]);*/
	}
	

}

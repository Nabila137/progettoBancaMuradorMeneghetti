package main.java.Banca;

import java.util.Vector;
import java.time.LocalDate;

public class Bank {

	private String nome;
	private Utente utente;
	private double contoBancario;
	private LocalDate initialDate;
	private Vector<Investment> investments;

	public Bank(String nome, Utente utente) {

		this.nome = nome;
		this.utente = utente;
		investments = new Vector<Investment>(3, 2);

	}

	public String getNome() {
		return nome;
	}

	/*
	 * public int getNumInvestimenti() { return numInvestimenti; }
	 * 
	 * public void setNumInvestimenti(int numInvestimenti) { this.numInvestimenti =
	 * numInvestimenti; }
	 */

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getContoBancario() {
		return contoBancario;
	}

	public void setContoBancario(double soldi) {
		contoBancario += soldi;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public LocalDate getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(LocalDate initialDate) {
		this.initialDate = initialDate;
	}

	public Vector<Investment> getInvestments() {
		return investments;
	}

	public void addInvestimento(Investment x) {
		investments.add(x);
	}

	public void removeInvestment(Investment x) {
		investments.remove(x);
	}

	public void setInvestments(Vector<Investment> investments) {
		this.investments = investments;
	}

	// "initialDate" è la data della registrazione. Così ho un tempo di inizio che
	// verrà memorizzato nel file che mi serve per gestire e riprendere la durata
	// dell'investimento

	public void nextMonth(int months) {

		utente.getPortafoglio().addSoldi(utente.getSalary());

	}

	/*
	 * public boolean canLookForReturns () {
	 * 
	 * }
	 */

	public Investment[] lookForReturns(LocalDate initialDate) {

		int size = investments.size();
		Investment[] returns = new Investment[size];
		System.out.println(returns);

		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if (initialDate.isAfter(investments.get(i).getEndDate())
						|| initialDate.isEqual(investments.get(i).getEndDate())) {
					returns[i] = investments.get(i);

					investments.remove(i);
				}
			}
		} else {
			return null;
		}

		return returns;
	}

	public void deposit(double depositMoney) {

		utente.getPortafoglio().togliSoldi(depositMoney);
		contoBancario += depositMoney;

	}

	public void withdraw(double withdrawal) {

		withdrawal *= -1;
		utente.getPortafoglio().togliSoldi(withdrawal);
		contoBancario += withdrawal;

	}

	/*
	 * public double ricavo (int percentualeSuccesso, int rischio, double
	 * investimento, double guadagno) {
	 * 
	 * double ritorno = -1; double successRate = percentualeSuccesso/100; double
	 * gainLose = investimento * successRate * guadagno; if ((percentualeSuccesso <=
	 * 100) && (percentualeSuccesso >= 0) && (rischio <= 100) && (rischio > 0) &&
	 * (investimento > 0)) { if (percentualeSuccesso >= rischio) { ritorno =
	 * investimento + gainLose; } else { ritorno = investimento - gainLose; } }
	 * 
	 * return ritorno; }
	 */

	/*
	 * public static void main(String[] args) { Investment[] x = lookForReturns
	 * (LocalDate.now(), 5); }
	 */

}

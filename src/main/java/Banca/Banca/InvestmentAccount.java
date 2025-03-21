package main.java.Banca;

//import java.util.Vector;

public class InvestmentAccount extends Account {
	
	
	//private int riskLevel;
	private int numberOfAccounts;
	//private double creationFee = 0;
	//private double monthlyFee = 5;
	//private double annualFee = 30;
	
	public InvestmentAccount (Utente utente, String accountNumber, double creationFee, double monthlyFee, double annualFee) {
		super (utente, accountNumber);
		//this.creationFee = creationFee;
		//this.monthlyFee = monthlyFee;
		//this.annualFee = annualFee;
	}
	
	public InvestmentAccount (Utente utente, String accountNumber) {
		super (utente, accountNumber);
	}
	
	public int getNumPerTipo () {
		return numberOfAccounts;
	}
	
	public void aumentaNumero () {
		numberOfAccounts++;
	}

	/*public double getCreationFee() {
		return creationFee;
	}*/

	/*public void setCreationFee(double creationFee) {
		this.creationFee = creationFee;
	}*/

	/*public double getMonthlyFee() {
		return monthlyFee;
	}*/

	/*public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}*/

	/*public double getAnnualFee() {
		return annualFee;
	}*/

	/*public void setYearlyFee(double yearlyFee) {
		this.yearlyFee = yearlyFee;
	}*/
	
	/*public double expectedReturn (int percentualeGuadagno, int rischio, double investimento, double guadagno) {//mi servirebbe un altro parametro per prendere il percentuale di perdita
		
		double probabilitaGuadagno = (double)percentualeGuadagno/100;
		double probabilitaPerdita = 1 - probabilitaGuadagno;
		double perdita = (double)rischio/100;
		
		double guadagnoTotale = investimento * (1 + guadagno);
		double perditaTotale = investimento * (1 - perdita);
		double ritorno = (probabilitaGuadagno * guadagnoTotale) + (probabilitaPerdita * perditaTotale);
		
		return ritorno;
	}*/
	
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

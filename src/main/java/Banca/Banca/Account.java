package main.java.Banca;

public abstract class Account {
	
	private Bank banca;
	private int numAccounts;
	private String accountNumber;
	private String IBAN;
	private Utente utente;
	private double balance;
	//private double creationFee;
	//private double monthlyFee;
	//private double annualFee;
	
	public Account (Utente utente, String accountNumber) {
		
		this.utente = utente;
		this.accountNumber = accountNumber;
		this.IBAN = banca.getCountryCode() + banca.getChackDigit() + banca.getNationalCheckDigit() + banca.getBankCode() + banca.getBranchCode() + accountNumber;
		
	}
	
	public String getIBAN() {
		return IBAN;
	}

	public int getNumAccounts() {
		return numAccounts;
	}
	
	public abstract int getNumPerTipo ();
	
	public abstract void aumentaNumero ();

	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void aumentaNumAccount () {
		numAccounts++;
	}

	public Utente getUtente() {
		return utente;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double soldi) {
		balance += soldi;
	}

	//public abstract double getCreationFee();

	//public abstract double getMonthlyFee();

	//public abstract double getAnnualFee();
	
	public void deposit(double depositMoney) {
		
		//if (Deposit_Withdraw.canDeposit(utente.getPortafoglio().getSoldi(), balance, depositMoney))
		utente.getPortafoglio().togliSoldi(depositMoney);
		balance += depositMoney;
		
	}
	
	public void withdraw(double withdrawal) {
		
		withdrawal *= -1;
		//if (Deposit_Withdraw.canWithdraw(utente.getPortafoglio().getSoldi(), balance, withdrawal))
		utente.getPortafoglio().togliSoldi(withdrawal);
		balance += withdrawal;
		
	}

}

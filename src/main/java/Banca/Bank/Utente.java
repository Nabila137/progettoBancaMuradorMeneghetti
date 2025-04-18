package main.java.Bank;

public class Utente {
	
	private String nome;
	private String cognome;
	private Account account;
	private double portafoglio = 0;
	private double salary = 100;
	
	public Utente (String nome, String cognome/*, double portafoglio, double salary, Account account*/) {
		this.nome = nome;
		this.cognome = cognome;
		//this.portafoglio = portafoglio;
		//this.salary = salary;
		//this.account = account;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public double getPortafoglio() {
		return portafoglio;
	}

	public void setPortafoglio(double portafoglio) {
		this.portafoglio = portafoglio;
	}
	
	public void aggiungiSoldi (double soldi) {
		portafoglio += soldi;
	}
	
	public void togliSoldi (double soldi) {
		portafoglio -= soldi;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void nextMonth(int months) {

		aggiungiSoldi(getSalary());
		account.setInitialDate(account.getInitialDate().plusMonths(months));
		//here I have to change the initial date 
		//Initial date is the moment you create the account and it will change whenever you click on "nextMonth", in this way you can track the investments without altering the "start date" of the investment.
		//every investment takes the initial date as start date
	}

	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", account=" + account + "]";
	}
	
	
	
	/*private String username;
	private String password;
	private double portafoglio;
	private double salery;
	
	public Utente (String username, String password, double portafoglio, double salery) {
		this.username = username;
		this.password = password;
		this.portafoglio = portafoglio;
		this.salery = salery;
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

	public double getPortafoglio() {
		return portafoglio;
	}

	public void setPortafoglio(double portafoglio) {
		this.portafoglio = portafoglio;
	}

	public double getSalery() {
		return salery;
	}

	public void setSalery(double salery) {
		this.salery = salery;
	}

	@Override
	public String toString() {
		return "Utente [username=" + username + ", password=" + password + ", portafoglio=" + portafoglio + ", salery="
				+ salery + "]";
	}
	*/
	

}

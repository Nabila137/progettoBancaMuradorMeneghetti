package main.java.Banca;

public class Utente {
	
	private String nomeUtente;
	private String password;
	private Portafoglio portafoglio;
	private int cont = 0;
	private double salary;
	
	public Utente (String nomeUtente, String password, Portafoglio portafoglio, double salary) {
		
		this.nomeUtente = nomeUtente;
		this.password = password;
		this.portafoglio = portafoglio;
		this.salary = salary;
		
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public String getPassword() {
		return password;
	}
	
	public Portafoglio getPortafoglio() {
		return portafoglio;
	}

	public int getCont() {
		return cont;
	}

}

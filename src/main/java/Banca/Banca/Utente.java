package main.java.Banca;

public class Utente {
	
	private String nome;
	private String cognome;
	//private String dataDiNascita;
	private String nomeUtente;
	private String password;
	//private String telefono;
	//private String email;
	private String securityQuestion;
	private Account[] accounts = new Account [5];
	private Portafoglio portafoglio;
	private int cont = 0;
	
	public Utente (String nome, String cognome, String nomeUtente, String password, String securityQuestion, Portafoglio portafoglio) {
		
		this.nome = nome;
		this.cognome = cognome;
		this.nomeUtente = nomeUtente;
		this.password = password;
		this.securityQuestion = securityQuestion;
		this.portafoglio = portafoglio;
		
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public String getPassword() {
		return password;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public Account[] getAccounts() {
		return accounts;
	}
	
	public Portafoglio getPortafoglio() {
		return portafoglio;
	}
	
	/*public void setAccount(Account account) {
		this.account = account;
	}*/

	public int getCont() {
		return cont;
	}

	/*public String toStringAccounts() {
		int length = accounts.length;
		String s = "Accounts:\n";
		for (int i = 0; i < length; i++) {
			s += accounts[i].toString();
		}
		return s;
	}*/

	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", nomeUtente=" + nomeUtente + ", password=" + password
				+ ", securityQuestion=" + securityQuestion + ", accounts=" + accounts + "]";
	}

	/*public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}*/

	
	
	

}

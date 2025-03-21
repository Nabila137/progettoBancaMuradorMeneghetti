package main.java.Banca;

public class Portafoglio {
	
	private double soldi;
	
	public Portafoglio (double soldi) {
		this.soldi = soldi;
	}

	public double getSoldi() {
		return soldi;
	}
	
	public void addSoldi (double quantita) {
		soldi +=quantita;
	}
	
	public void togliSoldi (double quantita) {
		soldi -=quantita;
	}

	@Override
	public String toString() {
		return "Portafoglio [soldi=" + soldi + "]";
	}
	
	
}

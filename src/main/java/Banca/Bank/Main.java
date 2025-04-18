package main.java.Bank;

import javax.swing.SwingUtilities;

public class Main {
	
	public static void main(String[] args) {
        
		Utente utente = null;
		//String nomeBanca = "Banca_Menedor";
		Banca banca = new Banca (utente);
		SwingUtilities.invokeLater(() -> {
			LoginFrame frame = new LoginFrame (banca);
			frame.setVisible(true);
		});
		//new Frame(banca).setVisible(true);
		
		
		
    }
}

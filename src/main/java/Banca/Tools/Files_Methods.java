package main.java.Tools;

import main.java.Banca.Utente;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class Files_Methods {
	
	/*public static boolean convertToArray () {
		
	}
	
	public static boolean transferToFile () {
		
	}
	*/
	//mi restituisce il nome del file dedicato all'utente
	public static int isStringPresentInFile (String string, File file, int fieldNumber) {
		
		int posizione = -1;
		int n = 0;
		
		if (file.exists() && file.isFile()) {
			try (Scanner input = new Scanner(file)){
				String riga = input.nextLine();
				n++;
				boolean found = false;
				while(input.hasNextLine() && !found) {
					riga = input.nextLine();
					n++;
					String s[] = riga.split("|");
					if (string.trim().equals(s[fieldNumber-1].trim())) {
						found = true;
						posizione = n;
					}
				}
			} catch (IOException e) {
				return -1;
			}
		}
		
		return posizione;
		
	}
	
	/*public static int isUsernamePresentInVector (String username, Vector <Utente> utenti) {
		
		int size = utenti.size();
		int inferiore = 0;
		int superiore = size-1;
		int centro = -1;
		
		boolean
		
	}*/
	
	/*public static Utente parseUtente (String s) {
		
	}*/
	
	public static void main (String [] args) {
		String fnome="clients.csv";
		//String fnome="C:\\Nabila\\A.s. 2024-25\\Informatica\\Java\\Banca_Menedor\\clients.csv";
		try (FileReader fin = new FileReader(fnome);
			Scanner input = new Scanner(fin);
			FileWriter fout = new FileWriter("output.txt", true);
			PrintWriter output = new PrintWriter(fout)) {	
			
			String riga;
			if (input.hasNextLine()) {
				riga = input.nextLine();
				System.out.println("Line: " + riga);
			} else {
				System.out.println("Errore");
			}
		} catch (FileNotFoundException e) {
			 System.out.println("File not found: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO exception occurred: " + e.getMessage());
		    e.printStackTrace();
     }	
		System.out.println(System.getProperty("user.dir"));
		
	}
	
	//isStringPresentInFile ("Password", )
}

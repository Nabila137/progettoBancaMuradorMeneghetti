package main.java.Tools;

public class CommonMethods {
	
	public static int randomNumberUnsigned (int n) {
		String s = "";
		for (int i=0; i<n; i++) {
			s += (int) (Math.random() * 9);
		}
		//long number = -1;
		int number = Integer.parseInt(s);
		
	return number;
	}

	/*public void throwException() {
	
		throw new IllegalArgumentException("n deve essere minore o uguale a 9.");
	
	}*/

	public static String randomString (int numCarattere) {
		char[] caratteri = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		int length = caratteri.length;
		
		String s = "";
		for (int i=0; i<numCarattere; i++) {
			int j = (int)(Math.random() * length);
			s += caratteri[j];
		}
		return s;
	}

	/*public static void main (String[] args) {
		int n=10;
	//System.out.println("Number: "+ randomNumber(n));
	System.out.println("String: "+ randomString(n));
	}*/

	public static void menuPrincipale () {
	
		System.out.println("BENVENUTO ALLA BANCA MENEDOR");
		System.out.println("1 ---> Deposita");
		System.out.println("2 ---> Preleva");
		System.out.println("3 ---> Investi");
		System.out.println("4 ---> Visualizza conto bancario");
		System.out.println("5 ---> Visualizza conto portafoglio \n");
		//System.out.println();
		System.out.println("6 ---> Passa al mese successivo");
		System.out.println("0 ---> Esci.\n");
		//System.out.println("\n");
		
	}

	/*public static void tempo(int mese, int anno) {

	String Mese = "";

	switch (mese) {

	case 1:
		Mese = "Gennaio";
		break;
	case 2:
		Mese = "Febbario";
		break;
	case 3:
		Mese = "Marzo";
		break;
	case 4:
		Mese = "Aprile";
		break;
	case 5:
		Mese = "Maggio";
		break;
	case 6:
		Mese = "Giugno";
		break;
	case 7:
		Mese = "Luglio";
		break;
	case 8:
		Mese = "Agosto";
		break;
	case 9:
		Mese = "Settembre";
		break;
	case 10:
		Mese = "Ottobre";
		break;
	case 11:
		Mese = "Novembre";
		break;
	case 12:
		Mese = "Dicembre";
		break;
	default:
		break;

	}

	System.out.println(Mese + "/" + anno + "\n");

}*/

	public static void menuDurataInvestimento() {
	
		System.out.println("-----MENU DURATA DELL'INVESTIMENTO-----");
		System.out.println("1. BREVE");
		System.out.println("2. MEDIA");
		System.out.println("3. LUNGA");
	
	}

	public static void menuRiskLevel() {
	
		System.out.println("-----MARGINI DI RISCHIO E GUADAGNO-----");
		System.out.println("1. BASSO");
		System.out.println("2. MEDIO");
		System.out.println("3. ALTO");
		
	}

	
	

}

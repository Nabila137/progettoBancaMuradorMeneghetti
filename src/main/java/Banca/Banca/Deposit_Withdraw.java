package main.java.Banca;

public class Deposit_Withdraw {
	
	//FIRST VERSION
	
		/*public boolean depositaPreleva (double portafoglio, double contoBancario, double money) {
			money(money);
			if ((money <= portafoglio) && (money > 0)) {
				portafoglio += money;
				contoBancario -= money;
				return true;
			}

			return false;
		}
		
		public static double money (double x) {
			
			return x*(-1);
			
		}*/
		
		//SECOND VERSION
		
		/*public boolean deposita (double portafoglio, double contoBancario, double depositMoney) {//!posso togliere quanche parametro?
			
			portafoglio = this.utente.getPortafoglio().getSoldi();
			contoBancario = balance;
			
			if ((depositMoney <= portafoglio) && (depositMoney > 0)) {
				utente.getPortafoglio().togliSoldi(depositMoney);
				balance += depositMoney;
				return true;
			}

			return false;
		}
		
		public boolean preleva (double portafoglio, double contoBancario, double withdrawal) {
			
			//withdrawal *= -1;
			
			return deposita(contoBancario, portafoglio, withdrawal);
		}*/
		
		//ANOTHER VERSION
		
		/*public boolean deposita (double portafoglio, double contoBancario, double depositMoney) {//!posso togliere quanche parametro?
			
			portafoglio = this.utente.getPortafoglio().getSoldi();
			contoBancario = balance;
			if ((depositMoney <= portafoglio) && (depositMoney > 0)) {
				portafoglio -= depositMoney;
				contoBancario += depositMoney;
				return true;
			}

			return false;
		}
		
		public boolean preleva (double portafoglio, double contoBancario, double withdrawal) {
			
			return deposita(contoBancario, portafoglio, withdrawal);
		}*/
		
		//LAST VERSION
		
		public static boolean canDeposit (double portafoglio, double contoBancario, double depositMoney) {//!posso togliere qualche parametro?
			
			//portafoglio = utente.getPortafoglio().getSoldi();
			
			if ((depositMoney <= portafoglio) && (depositMoney > 0))
				return true;

			return false;
		}
		
		public static boolean canWithdraw (double portafoglio, double contoBancario, double withdrawal) {
			
			//contoBancario = balance;
			
			return canDeposit(contoBancario, portafoglio, withdrawal);
		
		}
		
		

}

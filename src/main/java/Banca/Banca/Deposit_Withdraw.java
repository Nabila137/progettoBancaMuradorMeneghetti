package main.java.Banca;

public class Deposit_Withdraw {
	
		//LAST VERSION
		
		public static boolean canDeposit (double portafoglio, double contoBancario, double depositMoney) {
			
			if ((depositMoney <= portafoglio) && (depositMoney > 0))
				return true;

			return false;
		}
		
		public static boolean canWithdraw (double portafoglio, double contoBancario, double withdrawal) {
			
			return canDeposit(contoBancario, portafoglio, withdrawal);
		
		}

}

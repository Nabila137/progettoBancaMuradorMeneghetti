package main.java.Bank;

public class DepositWithdraw {
	
	//Can be used to check whether I can make investment (same code/control)
	public static boolean canDepositWithdraw(double portafoglio, /*double contoBancario,*/ double depositMoney) {

		if ((depositMoney > 0) && (depositMoney <= portafoglio))
			return true;

		return false;
	}
	
	/*public static boolean canMakeInvestment (double contoBancario, double amount) {
		
		if ((amount > 0) && (contoBancario >= amount))
			return true;
		
		return false;
	}*/

	/*public static boolean canWithdraw(/*double portafoglio, double contoBancario, double withdrawal) {

		return canDeposit(contoBancario, /*portafoglio, withdrawal);

	}*/

}

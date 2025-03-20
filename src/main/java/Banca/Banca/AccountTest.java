package Banca;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AccountTest {

	@Test
	void testCanDeposit() {
		double portafoglio = 150.5;
		double contoBancario = 0;
		double depositMoney = 102.5;
		
		boolean risultato = Deposit_Withdraw.canDeposit(portafoglio, contoBancario, depositMoney);
		
		assertTrue(risultato);
	}

}

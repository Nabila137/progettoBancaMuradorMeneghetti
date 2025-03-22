package main.java.Banca;

import java.time.LocalDate;

public class Investment {
    private double amount;
    private int rischio;
    private int durata;
    private LocalDate startDate;
    private LocalDate endDate;
    private double guadagno;
    private double ritorno;
    private int successPercentage;

    public Investment(double amount, int rischio, int durata, LocalDate startDate, double guadagno) {
    	this.successPercentage = randomNumber (1, 100);
        this.amount = amount;
        this.rischio = rischio;
        this.durata = durata;
        this.startDate = startDate;
        this.guadagno = guadagno;
        this.endDate = calculateEndDate(startDate, durata);
        this.ritorno = ricavo(successPercentage, rischio, amount, guadagno);
    }

    private LocalDate calculateEndDate(LocalDate startDate, int durata) {
    	
    	if (durata >= 0) {
    		int days = durata * 30;
        	LocalDate data;
        	try {
        		data = (startDate.plusDays(days));
        		return data;
        	} catch (IllegalArgumentException e) {
        		return null;
        	}
    	}
    	
        /*switch (durata) {
            case "short":
                return startDate.plusMonths(2); // 2 months for short-term
            case "medium":
                return startDate.plusMonths(6); // 6 months for medium-term
            case "long":
                return startDate.plusYears(1);  // 1 year for long-term
            default:
                throw new IllegalArgumentException("Invalid duration: " + duration);
        }*/
    	return null;
    }
    
    public double ricavo (int percentualeSuccesso, int rischio, double investimento, double guadagno) {
		
		double ritorno = -1;
		double successRate = percentualeSuccesso/100;
		double gainLose = investimento * successRate * guadagno; 
		if ((percentualeSuccesso <= 100) && (percentualeSuccesso >= 0) && (rischio <= 100) && (rischio > 0) && (investimento > 0)) {
			if (percentualeSuccesso >= rischio) {
				ritorno = investimento  + gainLose;
			} else {
				ritorno = investimento  - gainLose;			}
		}
		
		return ritorno;
	}
    
    public int randomNumber (int min, int max) {
    	
    	return (int) Math.random() * (max - min) + 1;
    	
    }
    /*private double calculateReturn(double amount, double gainRate) {
        return amount * gainRate;
    }*/

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public int getRischio() {
        return rischio;
    }

    public int getDurata() {
        return durata;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getGuadagno() {
        return guadagno;
    }

    public double getRitorno() {
        return ritorno;
    }

    @Override
    public String toString() {
        return "Investment{" +
                "amount=" + amount +
                ", riskLevel='" + rischio + '\'' +
                ", duration='" + durata + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", gainRate=" + guadagno +
                //", expectedReturn=" + ritorno +
                '}';
    }
}
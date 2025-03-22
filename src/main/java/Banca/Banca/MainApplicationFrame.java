package main.java.Banca;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class MainApplicationFrame extends JFrame {
	private String username;
	private JTextArea historyArea;
	private double portafoglio;
	private double contoBancario;
	private int rischio;
	private int durata;
	private double guadagno;

	public MainApplicationFrame(Bank bank, LocalDate initialDate) {

		this.username = bank.getUtente().getNomeUtente();
		this.portafoglio = bank.getUtente().getPortafoglio().getSoldi();
		this.contoBancario = bank.getContoBancario();

		setTitle("Main Application - " + username);
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new BorderLayout());

		// History Area
		historyArea = new JTextArea();
		historyArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(historyArea);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Buttons Panel
		JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton showPortafoglioButton = new JButton("Show Portafoglio Status");
		JButton showBankButton = new JButton("Show Bank Status");
		JButton nextMonthButton = new JButton("Next Month");
		JButton investButton = new JButton("Invest");

		buttonPanel.add(depositButton);
		buttonPanel.add(withdrawButton);
		buttonPanel.add(showPortafoglioButton);
		buttonPanel.add(showBankButton);
		buttonPanel.add(nextMonthButton);
		buttonPanel.add(investButton);

		panel.add(buttonPanel, BorderLayout.SOUTH);

		// Load history
		loadHistory();

		// Action Listeners
		depositButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");

				// separate method **ME**

				if (amountStr != null && !amountStr.isEmpty()) {
					boolean ok = true;
					double amount = -1;

					try {
						amount = Double.parseDouble(amountStr);
					} catch (NumberFormatException ex) {
						ok = false;
						JOptionPane.showMessageDialog(MainApplicationFrame.this,
								"Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
						addHistory("Entered invalid amount.");
					}

					// separate method **ME**

					if (ok) {
						if (Deposit_Withdraw.canDeposit(portafoglio, contoBancario, amount)) {
							bank.deposit(amount);
							similarCode (bank, amount, "Deposit", "deposited");
							/*portafoglio = bank.getUtente().getPortafoglio().getSoldi();
							contoBancario = bank.getContoBancario();
							String s = "" + contoBancario;
							String s1 = "" + portafoglio;
							if (sostituzioneStringa(username, s, "contoBancario")
									&& sostituzioneStringa(username, s1, "portafoglio")) {
								JOptionPane.showMessageDialog(MainApplicationFrame.this, "Deposit successful!",
										"Success", JOptionPane.INFORMATION_MESSAGE);
								addHistory("Successfully deposited: " + amount);
								//addHistory("Conto Bancario: " + contoBancario);
								//addHistory("Portafoglio: " + portafoglio);
							} else {
								JOptionPane.showMessageDialog(MainApplicationFrame.this, "Error updating balances.",
										"Error", JOptionPane.ERROR_MESSAGE);
								addHistory("Couldn't update balances");
							}*/
						} else {
							JOptionPane.showMessageDialog(MainApplicationFrame.this, "Can't deposit.", "Error",
									JOptionPane.ERROR_MESSAGE);
							addHistory("Failed to deposit.");
						}

					}
				}

			}
		});

		withdrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw:");

				// separate method **ME**

				if (amountStr != null && !amountStr.isEmpty()) {
					boolean ok = true;
					double amount = -1;
					try {
						amount = Double.parseDouble(amountStr);
					} catch (NumberFormatException ex) {
						ok = false;
						JOptionPane.showMessageDialog(MainApplicationFrame.this,
								"Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
						addHistory("Entered invalid amount.");
					}

					// Use HashMap to define the name of the parameters **ME**

					if (ok) {
						if (Deposit_Withdraw.canWithdraw(portafoglio, contoBancario, amount)) {
							bank.withdraw(amount);
							similarCode (bank, amount, "Whithdraw", "withdrawn");
							/*portafoglio = bank.getUtente().getPortafoglio().getSoldi();
							contoBancario = bank.getContoBancario();
							String s = "" + contoBancario;
							String s1 = "" + portafoglio;
							if (sostituzioneStringa(username, s, "contoBancario")
									&& sostituzioneStringa(username, s1, "portafoglio")) {
								JOptionPane.showMessageDialog(MainApplicationFrame.this, "Withdraw successful!",
										"Success", JOptionPane.INFORMATION_MESSAGE);
								addHistory("Successfully withdrawn: " + amount);
								addHistory("Conto Bancario: " + contoBancario);
								addHistory("Portafoglio: " + portafoglio);
							} else {
								JOptionPane.showMessageDialog(MainApplicationFrame.this, "Error updating balances.",
										"Error", JOptionPane.ERROR_MESSAGE);
								addHistory("Couldn't update balances");
							}*/
						} else {
							JOptionPane.showMessageDialog(MainApplicationFrame.this, "Can't withdraw.", "Error",
									JOptionPane.ERROR_MESSAGE);
							addHistory("Failed to withdraw.");
						}

					}
				}
			}
		});

		showPortafoglioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBalance("portafoglio");
				
			}
		});

		showBankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBalance("contoBancario");
			}
		});

		nextMonthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String monthsStr = JOptionPane.showInputDialog("Number of months to skip:");
				if (monthsStr != null && !monthsStr.isEmpty()) {
					boolean ok = true;
					int months = 0;
					try {
						months = Integer.parseInt(monthsStr);
					} catch (NumberFormatException ex) {
						ok = false;
						JOptionPane.showMessageDialog(MainApplicationFrame.this, "Please enter a valid number.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					if (ok) {
						if (months > 0) {
							bank.nextMonth(months);
							
							Investment[] returns = bank.lookForReturns(initialDate);
							int length = returns.length;
							if (returns != null && length > 0) {
								for (int i=0; i< length; i++) {
									double ritorno = returns[i].getRitorno();
									bank.setContoBancario(bank.getContoBancario() + ritorno);
									double soldiBanca = bank.getContoBancario();
									if (soldiBanca < 0) {
										bank.getUtente().getPortafoglio().togliSoldi(soldiBanca * -1);
										bank.setContoBancario (0);
										updateBalance ("contoBancario", bank.getContoBancario());
										/*if ((bank.getUtente().getPortafoglio().getSoldi() + soldiBanca) < 0) {
											bank.setContoBancario(soldiBanca + bank.getUtente().getPortafoglio().getSoldi());
											bank.getUtente().getPortafoglio().setSoldi(0);
										}*/
									}
								}
								int choice = JOptionPane.showConfirmDialog(MainApplicationFrame.this,
										"You got some investment returns. Want to take a look?", "Investment Returns",
										JOptionPane.YES_NO_OPTION);
								if (choice == JOptionPane.YES_OPTION) {
									showInvestmentReturns(returns);
		                        }
								updateInvestments (bank);
							}
							updateBalance ("portafoglio", bank.getUtente().getPortafoglio().getSoldi());
							// Modify LocalDate
						} else {
							JOptionPane.showMessageDialog(MainApplicationFrame.this, "Data must be greater than 0.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}

				// Implement next month functionality
				addHistory("Next month clicked.");
			}
		});

		investButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");
				if (amountStr != null && !amountStr.isEmpty()) {
					double amount;

					try {
						amount = Double.parseDouble(amountStr);
						bank.setContoBancario(bank.getContoBancario() - amount);
						investmentFrame ();
						Investment x = new Investment (amount, rischio, durata, LocalDate.now(), guadagno);
						bank.addInvestimento(x);
						JOptionPane.showMessageDialog(MainApplicationFrame.this, "Investment successful!","Success", JOptionPane.INFORMATION_MESSAGE);
						
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(MainApplicationFrame.this,
								"Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
						addHistory("Entered invalid amount.");
					}
				}
				//addHistory("Invest clicked.");
			}
		});

		add(panel);
	}
	
	private void similarCode (Bank bank, double amount, String x, String y) {
		
		portafoglio = bank.getUtente().getPortafoglio().getSoldi();
		contoBancario = bank.getContoBancario();
		String s = "" + contoBancario;
		String s1 = "" + portafoglio;
		if (sostituzioneStringa(username, s, "contoBancario")
				&& sostituzioneStringa(username, s1, "portafoglio")) {
			JOptionPane.showMessageDialog(MainApplicationFrame.this, x + "successful!",
					"Success", JOptionPane.INFORMATION_MESSAGE);
			addHistory("Successfully" + y + ": " + amount);
			//addHistory("Conto Bancario: " + contoBancario);
			//addHistory("Portafoglio: " + portafoglio);
		} else {
			JOptionPane.showMessageDialog(MainApplicationFrame.this, "Error updating balances.",
					"Error", JOptionPane.ERROR_MESSAGE);
			addHistory("Couldn't update balances");
		}
		
	}
	
	public boolean updateInvestments (Bank bank) {
		//sostituzioneStringa ()
		String nomeFile = bank.getUtente().getNomeUtente() + ".csv";
		File temp = new File("temporaneo.csv");

		try (FileReader file = new FileReader(nomeFile);
				Scanner input = new Scanner(file);
				FileWriter writer = new FileWriter(temp, true);
				PrintWriter printWriter = new PrintWriter(writer);) {

			while (input.hasNextLine()) {
				String riga = input.nextLine();
				if (riga.trim().startsWith("investments")) {
					String s[] = riga.split(",");
					int n = Integer.parseInt(s[1]);
					printWriter.println("investments,"+bank.getInvestments().size());
					for (int i = 0; i < n; i++) {
						printWriter.println(""+bank.getInvestments().get(i).getAmount() + "," + bank.getInvestments().get(i).getRischio() + "," +bank.getInvestments().get(i).getDurata() + ","+bank.getInvestments().get(i).getStartDate()+","+bank.getInvestments().get(i).getGuadagno()+ "," +bank.getInvestments().get(i).getRitorno());
					}
					
				}
				printWriter.println(riga);
			}

			input.close();
			printWriter.close();

			File oldFile = new File(nomeFile);
			if (!oldFile.delete()) {
				JOptionPane.showMessageDialog(this, "Error deleting old file.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (!temp.renameTo(oldFile)) {
				JOptionPane.showMessageDialog(this, "Error renaming file.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error creating user file.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public void investmentFrame() {
		
	    JFrame durationFrame = new JFrame("Duration");
	    durationFrame.setSize(400, 300);
	    durationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    durationFrame.setLocationRelativeTo(this);

	    JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    JLabel titleLabel = new JLabel("Select Duration:", SwingConstants.CENTER);
	    titleLabel.setFont(titleLabel.getFont().deriveFont(16.0f));
	    panel.add(titleLabel);

	    JButton lungoButton = new JButton("Lungo (9 months)");
	    JButton medioButton = new JButton("Medio (5 months)");
	    JButton cortoButton = new JButton("Corto (2 months)");
	    
	    int lungo = 9, medio = 5, corto = 2;

	    panel.add(lungoButton);
	    panel.add(medioButton);
	    panel.add(cortoButton);

	    // Action Listeners for Duration Buttons
	    lungoButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	durata = lungo;
	            durationFrame.dispose();
	            showRiskLevelSelectionFrame(); // Pass the selected duration (12 months)
	        }
	        
	    });

	    medioButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	durata = medio;
	            durationFrame.dispose();
	            showRiskLevelSelectionFrame(); // Pass the selected duration (6 months)
	        }
	    });

	    cortoButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	durata = corto;
	            durationFrame.dispose();
	            showRiskLevelSelectionFrame(); // Pass the selected duration (3 months)
	        }
	    });

	    durationFrame.add(panel);
	    durationFrame.setVisible(true);
	}
	
	private void showRiskLevelSelectionFrame() {
	    JFrame riskFrame = new JFrame("Risk Levels");
	    riskFrame.setSize(400, 300);
	    riskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    riskFrame.setLocationRelativeTo(this);

	    JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    JLabel titleLabel = new JLabel("Select Risk Level:", SwingConstants.CENTER);
	    titleLabel.setFont(titleLabel.getFont().deriveFont(16.0f));
	    panel.add(titleLabel);

	    JButton altoButton = new JButton("Alto (85%) - 10% Gain");
	    JButton medioButton = new JButton("Medio (50%) - 5% Gain");
	    JButton bassoButton = new JButton("Basso (10%) - 2% Gain");

	    panel.add(altoButton);
	    panel.add(medioButton);
	    panel.add(bassoButton);
	    
	    int alto = 85, medium = 50, basso = 10;
	    double high = 1.5, med = 0.7, low = 0.3;

	    // Action Listeners for Risk Level Buttons
	    altoButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	rischio = alto;
	        	guadagno = high;
	            //saveInvestment(durationMonths, "Alto", 0.10); // Save investment with high risk
	            riskFrame.dispose();
	        }
	    });

	    medioButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	rischio = medium;
	        	guadagno = med;
	            //saveInvestment(durationMonths, "Medio", 0.05); // Save investment with medium risk
	            riskFrame.dispose();
	        }
	    });

	    bassoButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	rischio = basso;
	        	guadagno = low;
	            //saveInvestment(durationMonths, "Basso", 0.02); // Save investment with low risk
	            riskFrame.dispose();
	        }
	    });

	    riskFrame.add(panel);
	    riskFrame.setVisible(true);
	}
	
	/*private void aggiornaInvestmetBank (double amount, int rischio, double guadagno) {
		
		
	}*/
	
	/*private void saveInvestment(int durationMonths, String riskLevel, double gainRate) {
	    LocalDate startDate = LocalDate.now(); // Get the current date
	    String fileName = username + ".csv";

	    try (FileWriter writer = new FileWriter(fileName, true);
	         PrintWriter printWriter = new PrintWriter(writer)) {
	        // Save the investment details
	        printWriter.println("Investment");
	        printWriter.println("Duration: " + durationMonths + " months");
	        printWriter.println("Risk Level: " + riskLevel);
	        printWriter.println("Gain Rate: " + gainRate);
	        printWriter.println("Start Date: " + startDate);
	        printWriter.println("----------------------------");

	        JOptionPane.showMessageDialog(this, "Investment saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Error saving investment.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}*/

	private void showInvestmentReturns(Investment[] returns) {
	    JFrame returnsFrame = new JFrame("Investment Returns");
	    returnsFrame.setSize(400, 300);
	    returnsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    returnsFrame.setLocationRelativeTo(this);

	    JPanel panel = new JPanel(new BorderLayout());
	    JTextArea textArea = new JTextArea();
	    textArea.setEditable(false);

	    // Add investment details to the text area
	    StringBuilder sb = new StringBuilder();
	    for (Investment investment : returns) {
	        sb.append("Amount: ").append(investment.getAmount()).append("\n");
	        sb.append("Risk Level: ").append(investment.getRischio()).append("\n");
	        sb.append("Duration: ").append(investment.getDurata()).append("\n");
	        sb.append("Return: ").append(investment.getRitorno()).append("\n");
	        sb.append("----------------------------\n");
	    }
	    textArea.setText(sb.toString());

	    JScrollPane scrollPane = new JScrollPane(textArea);
	    panel.add(scrollPane, BorderLayout.CENTER);

	    returnsFrame.add(panel);
	    returnsFrame.setVisible(true);
	}

	private void loadHistory() {
		try (BufferedReader reader = new BufferedReader(new FileReader(username + ".csv"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("password") /*&& !line.startsWith("contoBancario") && !line.startsWith("portafoglio")
						&& !line.startsWith("investimenti")*/) {
					historyArea.append(line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addHistory(String entry) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + ".csv", true))) {
			writer.write(entry + "\n");
			historyArea.append(entry + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateBalance(String type, double amount) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(username + ".csv"));
			StringBuilder fileContent = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(type)) {
					String[] parts = line.split(",");
					double balance = Double.parseDouble(parts[1]);
					balance += amount;
					line = type + "," + balance;
				}
				fileContent.append(line).append("\n");
			}
			reader.close();

			BufferedWriter writer = new BufferedWriter(new FileWriter(username + ".csv"));
			writer.write(fileContent.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showBalance(String type) {
		try (BufferedReader reader = new BufferedReader(new FileReader(username + ".csv"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(type)) {
					JOptionPane.showMessageDialog(this, type + ": " + line.split(",")[1]);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean sostituzioneStringa(String username, String s, String type) {

		String nomeFile = username + ".csv";
		File temp = new File("temporaneo.csv");

		try (FileReader file = new FileReader(nomeFile);
				Scanner input = new Scanner(file);
				FileWriter writer = new FileWriter(temp, true);
				PrintWriter printWriter = new PrintWriter(writer);) {

			// boolean trovato = false;
			while (input.hasNextLine()) {
				String riga = input.nextLine();
				if (riga.trim().startsWith(type.trim())) {
					riga = type + "," + s;
					// input.nextLine();
					// printWriter.println(s);
					// trovato = true;
				}
				printWriter.println(riga);
			}

			input.close();
			printWriter.close();

			File oldFile = new File(nomeFile);
			if (!oldFile.delete()) {
				JOptionPane.showMessageDialog(this, "Error deleting old file.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (!temp.renameTo(oldFile)) {
				JOptionPane.showMessageDialog(this, "Error renaming file.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error creating user file.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

	public static void main(String[] args) {

		Scanner tastiera = new Scanner(System.in);

		Portafoglio p = new Portafoglio(300);
		Utente utente = new Utente("testUser", "0000", p, 100);
		Bank bank = new Bank("bank", utente);
		LocalDate initialDate = LocalDate.now();
		System.out.println(bank.getContoBancario());
		System.out.println(bank.getUtente().getPortafoglio().getSoldi());

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainApplicationFrame(bank, initialDate).setVisible(true);
			}
		});
		System.out.println(bank.getContoBancario());
		System.out.println(bank.getUtente().getPortafoglio().getSoldi());
	}
}

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

public class MainApplicationFrame extends JFrame {
    private String username;
    private JTextArea historyArea;
    private double portafoglio;
    private double contoBancario;

    public MainApplicationFrame(String username) {
        this.username = username;
        this.portafoglio = 
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
                if (amountStr != null && !amountStr.isEmpty()) {
                	boolean ok = true;
                	double amount = -1;
                    try {
                        amount = Double.parseDouble(amountStr);
                        /*if (amount > 0) {
                            updateBalance("BankBalance", amount);
                            addHistory("Deposited: " + amount);
                        } else {
                            JOptionPane.showMessageDialog(MainApplicationFrame.this, "Amount must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                        }*/
                    } catch (NumberFormatException ex) {
                    	ok = false;
                        JOptionPane.showMessageDialog(MainApplicationFrame.this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if (ok) {
                    	Deposit_Withdraw.canDeposit(ABORT, ALLBITS, amount);
                    }
                }
                
            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = JOptionPane.showInputDialog("Enter amount to withdraw:");
                if (amount != null && !amount.isEmpty()) {
                    updateBalance("BankBalance", -Double.parseDouble(amount));
                    addHistory("Withdrawn: " + amount);
                }
            }
        });

        showPortafoglioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBalance("PortfolioBalance");
            }
        });

        showBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBalance("BankBalance");
            }
        });

        nextMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement next month functionality
                addHistory("Next month clicked.");
            }
        });

        investButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement invest functionality
                addHistory("Invest clicked.");
            }
        });

        add(panel);
    }

    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(username + ".csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("Password") && !line.startsWith("BankBalance") && !line.startsWith("PortfolioBalance") && !line.startsWith("Investments")) {
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
    
    private double cercaPortafoglioEContoBancario () {
    	
    	
    	
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApplicationFrame("testUser").setVisible(true);
            }
        });
    }
}

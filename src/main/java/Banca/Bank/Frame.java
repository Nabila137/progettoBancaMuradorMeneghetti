package main.java.Bank;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class Frame extends JFrame{
	
	private final JFrame frame;
	private JLabel messageLabel;
	private final JTextField usernameField;
	private final JPasswordField passwordField;
	private Banca banca;
    
    public Frame (Banca banca) {
    	
    	this.banca = banca;
    	
		frame = new JFrame("LoginForm");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        
        
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		
		JLabel usernameLabel = new JLabel("Username:");
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(usernameLabel, constraints);
		
		usernameField = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 0;
		panel.add(usernameField, constraints);

		JLabel passwordLabel = new JLabel("Password:");
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(passwordLabel, constraints);

		passwordField = new JPasswordField(20);
		constraints.gridx = 1;
		constraints.gridy = 1;
		panel.add(passwordField, constraints);
		messageLabel = new JLabel();

		JButton loginButton = new JButton("Login");
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(loginButton, constraints);
		
		loginButtonActionListener (loginButton);

		JButton registerButton = new JButton("Register");
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.EAST;
		panel.add(registerButton, constraints);
		
		registerButton.addActionListener(e -> {
		    new RegistrationFrame(banca).setVisible(true);
		});
		
		messageLabel = new JLabel();
		messageLabel.setForeground(Color.RED);
		constraints.gridx = 1;
		constraints.gridy = 5;
		panel.add(messageLabel, constraints);
		
		add(panel);
    }
    
    private void loginButtonActionListener (JButton loginButton) {
    	
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				
				if (validateLogin(username, password)) {
					String fileName = username + ".csv";
					banca = Methods.parseBanca(fileName);
					if (banca != null) {
						JOptionPane.showMessageDialog(frame, "Login successful!");
						
						SwingUtilities.invokeLater(() -> {
							
				            MainFrame frame = new MainFrame(banca);//Opening main frame
				            frame.setVisible(true);
				           
				        });
						
					} else {
						JOptionPane.showMessageDialog(frame, "Incorrect data in file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
					/*
					String dateStr = Files_Methods.getPartOfString(username, "initialDate");
					try {

						initialDate = LocalDate.parse(dateStr);
						bank.setInitialDate(initialDate);
						String s = Files_Methods.getPartOfString(username, "portafoglio");
						String s2 = Files_Methods.getPartOfString(username, "salary");
						String s3 = Files_Methods.getPartOfString(username, "contoBancario");

						try {

							double amount = Double.parseDouble(s);
							double amount2 = Double.parseDouble(s2);
							double amount3 = Double.parseDouble(s2);
							Portafoglio p = new Portafoglio(amount);
							Utente u = new Utente(username, password, p, amount2);
							bank.setUtente(u);
							bank.setContoBancario(amount3);
							bank.setInvestments(investmentsUpdate(bank));

						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(frame, "Invalid field in file.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (DateTimeParseException ex) {
						initialDate = LocalDate.now();
					}
					JOptionPane.showMessageDialog(frame, "Login successful!");
					new MainApplicationFrame(bank, initialDate).setVisible(true);
					// Open main application window
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error",
							JOptionPane.ERROR_MESSAGE);
				}*/
			}
		});
    	
    }
    
    /*public void actionPerformed (ActionEvent e) {
    	
    	
		
    }*/
    
    private boolean validateLogin(String username, String password) {

		String nomeFile = username.trim() + ".csv";
		boolean ok = false;
		try (FileReader file = new FileReader(nomeFile); 
				Scanner input = new Scanner(file)) {
			if (input.hasNextLine()) {
				String line = input.nextLine();
				if (input.hasNextLine()) {
					line = input.nextLine();
					String[] parts = line.split("\\|");
					if (parts[1].trim().equals(password)) {
						return true;
					}
				}
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return ok;

	}
//}
    
   /*private void registerButtonActionListener (JButton loginButton) {
	   
	   registerButton.addActionListener(e -> {
		    new RegistrationFrame(bank).setVisible(true);
		});
    	
		/*loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				
				if (validateRegistration(username, password)) {
					String fileName = username + ".csv";
					banca = Methods.parseBanca(fileName);
					if (banca != null) {
						JOptionPane.showMessageDialog(frame, "Login successful!");
					} else {
						JOptionPane.showMessageDialog(frame, "Incorrect data in file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});*/
    	
    //}
   
   /*private boolean validateRegistration (){
	   
   }*/
    
    /*private JPanel getJPanel() {
        JButton loginButton = new JButton("Login");
        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.WEST;
		//panel.add(loginButton, constraints);

        // Add action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Simple authentication check (read from file?)
                if (username.equals("admin") && password.equals("ilovetpsit")) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton, constraints);
        return buttonPanel;

    }*/
    
    
}

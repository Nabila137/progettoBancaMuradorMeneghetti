package main.java.Bank;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

public class LoginFrame extends JFrame {
	
	private JLabel messageLabel;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private Banca banca;
    
    public LoginFrame (Banca banca) {
    	
    	this.banca = banca;
    	
    	setTitle ("Login Form");
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
		
		registerButtonActionListener (registerButton);
		//this.setVisible(true);
		/*registerButton.addActionListener(e -> {
		    new RegistrationFrame(banca).setVisible(true);
		});*/
		
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
						JOptionPane.showMessageDialog(LoginFrame.this, "Login successful!");
						//We have used "LginFrame.this" to refer an outer class as we are inside an anonymous class.
						SwingUtilities.invokeLater(() -> {
				            MainFrame frame = new MainFrame(banca);//Opening main frame
				            frame.setVisible(true);
				            dispose();
				        });
						
					} else {
						JOptionPane.showMessageDialog(LoginFrame.this, "Incorrect data in file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
    	
    }
    
    //When the registration frame disposes I want the login frame to appear
    private void registerButtonActionListener (JButton registerButton) {
    	
    	registerButton.addActionListener (new ActionListener(){
    		
    		public void actionPerformed(ActionEvent e) {
    			
    			SwingUtilities.invokeLater(() -> {
    				RegistrationFrame frame = new RegistrationFrame (banca);
    				frame.setVisible(true);
    				//LoginFrame.this.setVisible(false);
    			});
    			//LoginFrame.this.setVisible(true);
    			
    		}
    		
    		
    	});
    	
    	//LoginFrame.this.setVisible(true);
    }
    
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

}

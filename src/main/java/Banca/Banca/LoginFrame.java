package main.java.Banca;

//import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import main.java.Tools.Files_Methods;
import java.io.*;
//import javax.swing.border.LineBorder;
//import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Scanner;

public class LoginFrame extends JFrame {
	
	private final JFrame frame;
	private JLabel messageLabel;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
	
	public LoginFrame () {
		
		frame = new JFrame("LoginForm");
		setSize(400, 300);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo (null);

        // Create panel with a matrix layout
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
        
        JButton loginButton = new JButton("LOGIN");
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(loginButton, constraints);
        
        JButton registerButton = new JButton("REGISTER");
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(registerButton, constraints);
        
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(messageLabel, constraints);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    // Open main application window
                } else {
                	JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful!");
                    // Open main application window
                    frame.dispose();
                    new MainApplicationFrame(username).setVisible(true); // Open the main application frame
                }
            }
        });

        add(panel);

    }
	
	private boolean registerUser(String username, String password) {

	    if (username.isEmpty() || password.isEmpty()) {
	        JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    if (isUsernameTaken(username)) {
	        JOptionPane.showMessageDialog(frame, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    try (FileWriter writer = new FileWriter(username + ".csv", true);
	         PrintWriter printWriter = new PrintWriter(writer)) {
	        printWriter.println("Password," + password);
	        printWriter.println("BankBalance,0");
	        printWriter.println("PortfolioBalance,0");
	        printWriter.println("Investments,0");
	        return true;
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(frame, "Error creating user file.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	}

	private boolean isUsernameTaken(String username) {
	    File file = new File(username + ".csv");
	    return file.exists(); // Simply check if the file exists
	}

	private boolean validateLogin(String username, String password) {
		String nomeFile = username.trim() + ".csv";
		boolean ok = false;
		try (FileReader file= new FileReader (nomeFile); 
				Scanner input = new Scanner (file)){
			if (input.hasNextLine()) {
				if (input.nextLine().equals(password.trim())) {
					ok = true;
				}
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {//cosa controlla?
			return false;
		}
        return ok;
    }
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

}

package Banca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Tools.Files_Methods;
import java.io.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Scanner;

public class LoginFrame extends JFrame {
	
	//private JTextField emailField;
	//JCheckbox
	private final JFrame frame;
	private final JFrame frame2;
	private JLabel messageLabel;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField name;
    private final JTextField cognome;
    private final JTextField portafoglio;
    private final JTextField securityQuestion;
    //private final JCheckBox rememberMeCheckBox;
	
	public LoginFrame () {
		
		frame2 = new JFrame ("Registration Form");
		frame = new JFrame("LoginForm");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo (null);
		frame2.setSize(600, 500);
		frame2.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame2.setLocationRelativeTo (null);
        /*frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());*/

        // Create panel with a matrix layout
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
        //JPanel panel = new JPanel(new GridLayout (4, 2, 10, 10));
        //panel.setBorder(BorderFactory.createEmptyBorder (10, 10, 10, 10));
        //panel.setLayout(new GridLayout(3, 2));

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
        
       /* rememberMeCheckBox = new JCheckBox("Remember me");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(rememberMeCheckBox, constraints);*/
        
       /* JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setForeground(Color.BLUE);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(forgotPasswordButton, constraints);*/
        
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
        
        //usernameLabel.setBackground();
        //usernameLabel.setFont(usernameLabel.getFont().deriveFont(20.0f));

        
        
        //messageLabel.setForeground(Color.RED);

        // Add the labels and text fields to the panel
        //JButton loginButton = new JButton("Login");
        /*JButton registerButton = new JButton("Register");
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(messageLabel);*/

        // Create a login button
        //JPanel buttonPanel = getJPanel();

        // Add panels to the frame
        /*frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Display the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);*/
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    //messageLabel.setText("Login successful!");
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    // Open main application window
                } else {
                	JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                    //messageLabel.setText("Invalid username or password.");
                }
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    //messageLabel.setText("Login successful!");
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    // Open main application window
                } else {
                	JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                    //messageLabel.setText("Invalid username or password.");
                }
            }
        });
        
        /*forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("Forgot Password clicked.");
                // Implement forgot password functionality here
            }
        });*/

        add(panel);
        
       // add(panel);
    }

	/*private boolean validateLogin(String username, String password) {
		try (BufferedReader reader = new BufferedReader(new FileReader("clients.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
	}*/
	
	private boolean registerUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("clients.txt", true))) {
            if (validateLogin(username, password)) {
                return false;
            }
            writer.write(username + "," + password + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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


    /*private JPanel getJPanel() {
        JButton loginButton = new JButton("Login");

        // Add action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String fileName = "C:/Nabila/A.s. 2024-25/Informatica/Java/Banca_Menedor/src/Clients_Informations.csv";
                File file = new File (fileName);
                int posizione = Files_Methods.isStringPresentInFile(username, file, 3);
                int posizione2 = Files_Methods.isStringPresentInFile(password, file, 4);
                if ((posizione == -1) || posizione2 == -1) {
                	 JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                	/*usernameField.getCaretColor();
                	TitledBorder titledBorder = new TitledBorder("My Label Border");
                	titledBorder.setBorder(new LineBorder(Color.BLUE, 3));*/
               /* } else {
                	JOptionPane.showMessageDialog(frame, "Login successful!");
                }
                

                // Simple authentication check (read from file?)
                /*if (username.equals("admin") && password.equals("ilovetpsit")) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }*/
        /*    }
        });

        // Create a panel for the button and add it to the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        return buttonPanel;
    }*/
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

}

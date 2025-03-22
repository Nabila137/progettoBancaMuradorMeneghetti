package main.java.Banca;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.Scanner;
import java.time.LocalDate;
import main.java.Tools.*;
import java.time.format.DateTimeParseException;
import java.util.Vector;

public class LoginFrame extends JFrame {

	private final JFrame frame;
	private JLabel messageLabel;
	private final JTextField usernameField;
	private final JPasswordField passwordField;
	private LocalDate initialDate;

	public LoginFrame(Bank bank) {

		initialDate = bank.getInitialDate();
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
				}
			}
		});

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				if (registerUser(username, password)) {
					bank.setInitialDate(initialDate);
					Portafoglio p = new Portafoglio(0);
					Utente utente = new Utente(username, password, p, 100);
					bank.setUtente(utente);
					JOptionPane.showMessageDialog(frame, "Registration successful!");
					frame.dispose();
					new MainApplicationFrame(bank, initialDate).setVisible(true);
				}
			}
		});

		add(panel);

	}

	private boolean validateLogin(String username, String password) {

		String nomeFile = username.trim() + ".csv";
		boolean ok = false;
		try (FileReader file = new FileReader(nomeFile); Scanner input = new Scanner(file)) {
			if (input.hasNextLine()) {
				String line = input.nextLine();
				String[] parts = line.split(",");
				if (parts[1].trim().equals(password)) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return ok;

	}

	private Vector<Investment> investmentsUpdate(Bank bank) {

		Vector<Investment> lista = bank.getInvestments();
		String x = bank.getUtente().getNomeUtente();

		try (FileReader reader = new FileReader(x); Scanner input = new Scanner(reader)) {
			String riga;
			while (input.hasNextInt()) {
				riga = input.nextLine();
				if (riga.startsWith("investimenti")) {
					String[] s = riga.split(",");
					String num = s[1];
					int n = Integer.parseInt(num);
					for (int i = 0; i < n; i++) {
						riga = input.nextLine();
						lista.add(parseInvestment(riga));
					}
				}
			}
		} catch (FileNotFoundException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
		return lista;

	}

	private Investment parseInvestment(String line) {
		String[] parts = line.split(",");

		// int number = Integer.parseInt(parts[0]);
		double amount = Double.parseDouble(parts[0]);
		int rischio = Integer.parseInt(parts[1]);
		int durata = Integer.parseInt(parts[2]);
		LocalDate startDate = LocalDate.parse(parts[3]);
		double guadagno = Double.parseDouble(parts[4]);

		// Create and return the Investment object
		return new Investment(amount, rischio, durata, startDate, guadagno);
	}

	private boolean registerUser(String username, String password) {

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (isUsernameTaken(username)) {
			JOptionPane.showMessageDialog(frame, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try (FileWriter writer = new FileWriter(username + ".csv", true);
				PrintWriter printWriter = new PrintWriter(writer)) {
			printWriter.println("password," + password);
			printWriter.println("portafoglio,0");
			printWriter.println("contoBancario,0");
			printWriter.println("salary,0");
			initialDate = LocalDate.now();
			printWriter.println("initialDate," + initialDate);
			printWriter.println("investimenti,0");
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Error creating user file.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private boolean isUsernameTaken(String username) {
		File file = new File(username + ".csv");
		return file.exists();
	}

	/*
	 * public static void main(String[] args) { SwingUtilities.invokeLater(new
	 * Runnable() { Portafoglio p = new Portafoglio (0); Utente utente = new Utente
	 * ("testUser", "0000", p, 100); Bank bank = new Bank ("bank", utente);
	 * 
	 * @Override public void run() { new LoginFrame(bank).setVisible(true); } }); }
	 */

}

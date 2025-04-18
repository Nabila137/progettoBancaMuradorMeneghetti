package main.java.Bank;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RegistrationFrame extends JFrame {
  // The whole frame should be scrollable
  private JTextField nameField, surnameField, usernameField, walletField, salaryField;
  private JPasswordField passwordField;
  private JButton registerButton, cancelButton;

  public RegistrationFrame() {
    initializeUI();
  }

  private void initializeUI() {
    setTitle("New Account Registration");
    setSize(600, 500);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    mainPanel.setBackground(new Color(240, 242, 245));

    JLabel headerLabel = new JLabel("Create Your Account", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    mainPanel.add(headerLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBackground(Color.WHITE);
    formPanel.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));

    nameField = createFormField("First Name*:", formPanel);
    surnameField = createFormField("Last Name*:", formPanel);
    usernameField = createFormField("Username*:", formPanel);
    passwordField = createPasswordField("Password*:", formPanel);
    walletField = createFormField("Initial Wallet Balance:", formPanel);
    salaryField = createFormField("Monthly Salary:", formPanel);

    JLabel strengthLabel = new JLabel("Password Strength: ");
    strengthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    strengthLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
    formPanel.add(strengthLabel);

    passwordField
        .getDocument()
        .addDocumentListener(
            new DocumentListener() {
              public void changedUpdate(DocumentEvent e) {
                updateStrength();
              }

              public void removeUpdate(DocumentEvent e) {
                updateStrength();
              }

              public void insertUpdate(DocumentEvent e) {
                updateStrength();
              }

              private void updateStrength() {
                String password = new String(passwordField.getPassword());
                String strength = checkPasswordStrength(password);
                strengthLabel.setText("Password Strength: " + strength);
                strengthLabel.setForeground(getStrengthColor(strength));
              }
            });

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
    buttonPanel.setBackground(Color.WHITE);

    registerButton = createModernButton("Register", new Color(76, 175, 80));
    registerButton.addActionListener(e -> registerUser());
    buttonPanel.add(registerButton);

    cancelButton = createModernButton("Cancel", new Color(244, 67, 54));
    cancelButton.addActionListener(e -> dispose());
    buttonPanel.add(cancelButton);

    formPanel.add(buttonPanel);
    mainPanel.add(formPanel, BorderLayout.CENTER);

    add(mainPanel);
  }

  private JTextField createFormField(String labelText, JPanel panel) {
    JPanel fieldPanel = new JPanel(new BorderLayout(5, 5)); // What does it do?
    fieldPanel.setBackground(Color.WHITE);

    JLabel label = new JLabel(labelText);
    label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    JTextField textField = new JTextField();
    textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    textField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));

    fieldPanel.add(label, BorderLayout.NORTH);
    fieldPanel.add(textField, BorderLayout.CENTER);
    panel.add(fieldPanel);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    return textField;
  }

  private JPasswordField createPasswordField(String labelText, JPanel panel) {
    JPanel fieldPanel = new JPanel(new BorderLayout(5, 5));
    fieldPanel.setBackground(Color.WHITE);

    JLabel label = new JLabel(labelText);
    label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    JPasswordField passwordField = new JPasswordField();
    passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    passwordField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));

    JButton toggleButton = new JButton("👁");
    toggleButton.setBorder(BorderFactory.createEmptyBorder());
    toggleButton.setContentAreaFilled(false);
    toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    toggleButton.addActionListener(
        e -> {
          if (passwordField.getEchoChar() == '\u2022') {
            passwordField.setEchoChar((char) 0);
            toggleButton.setText("👁");
          } else {
            passwordField.setEchoChar('\u2022');
            toggleButton.setText("👁");
          }
        });

    JPanel passwordPanel = new JPanel(new BorderLayout());
    passwordPanel.add(passwordField, BorderLayout.CENTER);
    passwordPanel.add(toggleButton, BorderLayout.EAST);

    fieldPanel.add(label, BorderLayout.NORTH);
    fieldPanel.add(passwordPanel, BorderLayout.CENTER);
    panel.add(fieldPanel);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    return passwordField;
  }

  private JButton createModernButton(String text, Color color) {
    JButton button =
        new JButton(text) {
          @Override
          protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
              g.setColor(color.darker());
            } else if (getModel().isRollover()) {
              g.setColor(color.brighter());
            } else {
              g.setColor(color);
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            super.paintComponent(g);
          }

          @Override
          protected void paintBorder(Graphics g) {
            // No border
          }
        };

    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    button.setPreferredSize(new Dimension(120, 40));

    return button;
  }

  private void registerUser() {

    String name = nameField.getText().trim();
    String surname = surnameField.getText().trim();
    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword());
    String walletText = walletField.getText().trim();
    String salaryText = salaryField.getText().trim();

    if (name.isBlank() || surname.isBlank() || username.isBlank() || password.isBlank()) {

      JOptionPane.showMessageDialog(
          this,
          "Please fill in all mandatory fields (marked with *)",
          "Incomplete Information",
          JOptionPane.WARNING_MESSAGE);
      return; // Why does it return?
    }

    if (usernameExists(username)) {
      JOptionPane.showMessageDialog(
          this,
          "Username already exists. Please choose another one.",
          "Username Taken",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    String strength = checkPasswordStrength(password);
    if (strength.equals("Weak")) {
      int confirm =
          JOptionPane.showConfirmDialog(
              this,
              "Your password is weak. Are you sure you want to continue?",
              "Weak Password",
              JOptionPane.YES_NO_OPTION);
      if (confirm != JOptionPane.YES_OPTION) return;
    }

    double walletBalance =
        walletText.isBlank() ? 0.0 : Methods.parseDouble2(walletText); // using ternary operators
    /*double walletBalance;
    if (walletText.isEmpty()) {
        walletBalance = 0.0;
    } else {
        walletBalance = parseDouble(walletText);
    }*/
    double salary = salaryText.isBlank() ? 0.0 : Methods.parseDouble2(salaryText);

    if (walletBalance < 0 || salary < 0) {
      JOptionPane.showMessageDialog(
          this,
          "Please enter valid numbers for wallet balance and salary",
          "Invalid Input",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    LocalDate date = LocalDate.now();
    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    // use the new refined method to create the String ("Methods")
    String s =
        username
            + " | "
            + password
            + " | "
            + name
            + " | "
            + surname
            + " | "
            + walletText
            + " | "
            + salaryText
            + " | "
            + "0.0"
            + " | "
            + date
            + " | "
            + '0'
            + " | "
            + username
            + "History.csv";

    if (Methods.registraUtente(username, s, date, time)) {
      JOptionPane.showMessageDialog(
          this,
          "Registration successful! Your account has been created.",
          "Success",
          JOptionPane.INFORMATION_MESSAGE);
      dispose();
    } else {
      JOptionPane.showMessageDialog(
          this, "Error creating account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private boolean usernameExists(String username) {
    File userFile = new File(username + ".csv");
    return userFile.exists();
  }

  private String checkPasswordStrength(String password) {
    if (password.length() < 8) return "Weak";

    boolean hasUpper = !password.equals(password.toLowerCase());
    boolean hasLower = !password.equals(password.toUpperCase());
    boolean hasDigit = password.matches(".*\\d.*");
    boolean hasSpecial = !password.matches("[A-Za-z0-9]*");

    int strength = 0;
    if (hasUpper) strength++;
    if (hasLower) strength++;
    if (hasDigit) strength++;
    if (hasSpecial) strength++;

    if (password.length() >= 12 && strength >= 3) return "Very Strong";
    if (password.length() >= 10 && strength >= 2) return "Strong";
    if (password.length() >= 8 && strength >= 1) return "Medium";
    return "Weak";
  }

  private Color getStrengthColor(String strength) {
    switch (strength) {
      case "Very Strong":
        return new Color(0, 150, 0);
      case "Strong":
        return new Color(0, 180, 0);
      case "Medium":
        return new Color(255, 165, 0);
      default:
        return Color.RED;
    }
  }
}

package main.java.Bank;

import java.awt.*;
import javax.swing.*;

public class AccountInfoPanel extends JPanel {
  private static final Color BACKGROUND_COLOR = Color.WHITE;
  private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
  private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
  private static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);

  private final Banca bank;
  private JTextField nameField, surnameField, salaryField;
  private JLabel walletLabel;

  public AccountInfoPanel(Banca bank) {
    this.bank = bank;
    setLayout(new BorderLayout());
    setBackground(BACKGROUND_COLOR);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel title = new JLabel("Account Information");
    title.setFont(TITLE_FONT);
    title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    add(title, BorderLayout.NORTH);

    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBackground(BACKGROUND_COLOR);

    formPanel.add(
        createFormField("Name:", nameField = new JTextField(), bank.getUtente().getNome()));
    formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    formPanel.add(
        createFormField(
            "Surname:", surnameField = new JTextField(), bank.getUtente().getCognome()));
    formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    // Wallet (non-editable) --> Why???
    JPanel walletPanel = new JPanel(new BorderLayout(10, 0));
    walletPanel.setBackground(BACKGROUND_COLOR);
    JLabel walletText = new JLabel("Wallet Balance:");
    walletText.setFont(LABEL_FONT);
    walletPanel.add(walletText, BorderLayout.WEST);
    walletLabel = new JLabel(String.format("€%,.2f", bank.getUtente().getPortafoglio()));
    walletLabel.setFont(FIELD_FONT);
    walletPanel.add(walletLabel, BorderLayout.CENTER);
    formPanel.add(walletPanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    formPanel.add(
        createFormField(
            "Monthly Salary:",
            salaryField = new JTextField(),
            String.valueOf(bank.getUtente().getSalary())));

    add(formPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    buttonPanel.setBackground(BACKGROUND_COLOR);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

    JButton cancelButton = createButton("Cancel", new Color(200, 200, 200));
    cancelButton.addActionListener(e -> resetFields());

    JButton confirmButton = createButton("Confirm", new Color(0, 122, 255));
    confirmButton.addActionListener(e -> saveChanges());

    buttonPanel.add(cancelButton);
    buttonPanel.add(confirmButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private JPanel createFormField(String labelText, JTextField field, String initialValue) {
    JPanel panel = new JPanel(new BorderLayout(10, 0));
    panel.setBackground(BACKGROUND_COLOR);

    JLabel label = new JLabel(labelText);
    label.setFont(LABEL_FONT);
    panel.add(label, BorderLayout.WEST);

    field.setFont(FIELD_FONT);
    field.setText(initialValue);
    field.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)));
    panel.add(field, BorderLayout.CENTER);

    return panel;
  }

  private JButton createButton(String text, Color bgColor) {
    JButton button = new JButton(text);
    button.setFont(LABEL_FONT);
    button.setBackground(bgColor);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    return button;
  }

  private void resetFields() {
    nameField.setText(bank.getUtente().getNome());
    surnameField.setText(bank.getUtente().getCognome());
    salaryField.setText(String.valueOf(bank.getUtente().getSalary()));
  }

  private void saveChanges() {
    try {
      // Don't change Name and Surname (doesn't make any sense)
      String newName = nameField.getText().trim();
      String newSurname = surnameField.getText().trim();
      double newSalary = Methods.parseDouble2(salaryField.getText().trim());

      if (newName.isBlank()) {
        newName = bank.getUtente().getNome(); // maybe it can return when something goes wrong
      }
      if (newSurname.isBlank()) {
        newSurname = bank.getUtente().getCognome();
      }
      if (newSalary < 0) {
        newSalary = bank.getUtente().getSalary();
      }

      bank.getUtente().setNome(newName);
      bank.getUtente().setCognome(newSurname);
      bank.getUtente().setSalary(newSalary);

      String username = bank.getUtente().getAccount().getUsername().trim();
      message(Methods.updateFile(username + ".csv", newName, 2, 3), "Name");
      message(Methods.updateFile(username + ".csv", newSurname, 2, 4), "Surname");
      message(Methods.updateFile(username + ".csv", "" + newSalary, 2, 6), "Salary");

      JOptionPane.showMessageDialog(
          this,
          "Account information updated successfully!",
          "Success",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(
          this, "Please enter a valid salary amount", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void message(boolean x, String s) {
    if (!x) {
      JOptionPane.showMessageDialog(
          this, s + " field can't be updated.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}

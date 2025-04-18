package main.java.Bank;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class CredentialsPanel extends JPanel {
  private static final Color BACKGROUND_COLOR = Color.WHITE;
  private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
  private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
  private static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);

  private final Banca bank;
  private JTextField usernameField;
  private JPasswordField oldPasswordField, newPasswordField;

  public CredentialsPanel(Banca bank) {
    this.bank = bank;
    setLayout(new BorderLayout());
    setBackground(BACKGROUND_COLOR);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel title = new JLabel("Change Username & Password");
    title.setFont(TITLE_FONT);
    title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    add(title, BorderLayout.NORTH);

    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBackground(BACKGROUND_COLOR);

    formPanel.add(
        createFormField(
            "Username:",
            usernameField = new JTextField(),
            bank.getUtente().getAccount().getUsername()));
    formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    formPanel.add(
        createPasswordField("Current Password:", oldPasswordField = new JPasswordField()));
    formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    formPanel.add(createPasswordField("New Password:", newPasswordField = new JPasswordField()));

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

  private JPanel createPasswordField(String labelText, JPasswordField field) {
    JPanel panel = new JPanel(new BorderLayout(10, 0));
    panel.setBackground(BACKGROUND_COLOR);

    JLabel label = new JLabel(labelText);
    label.setFont(LABEL_FONT);
    panel.add(label, BorderLayout.WEST);

    field.setFont(FIELD_FONT);
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
    usernameField.setText(bank.getUtente().getAccount().getUsername());
    oldPasswordField.setText("");
    newPasswordField.setText("");
  }

  private void saveChanges() {
    String username = usernameField.getText().trim();
    String oldPassword = new String(oldPasswordField.getPassword());
    String newPassword = new String(newPasswordField.getPassword());

    if (username.isBlank()) {
      JOptionPane.showMessageDialog(
          this, "Username field cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (new File(username + ".csv").exists()) {
      JOptionPane.showMessageDialog(
          this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (!bank.getUtente().getAccount().getPassword().equals(oldPassword)) {
      JOptionPane.showMessageDialog(
          this, "Current password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Validate new password //put the analyser to check the strength
    if (newPassword.isEmpty()) {
      JOptionPane.showMessageDialog(
          this, "New password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String oldUsername = bank.getUtente().getAccount().getUsername();
    message(Methods.updateFile(oldUsername + ".csv", username, 2, 1), "Username");
    message(Methods.updateFile(oldUsername + ".csv", newPassword, 2, 2), "Password");
    if (!Methods.renameFile(oldUsername + ".csv", username + ".csv")) {
      JOptionPane.showMessageDialog(
          this,
          "Can't rename file or the source file doesn't exist.",
          "Error",
          JOptionPane.ERROR_MESSAGE);
    } else {
      if (!Methods.renameFile(oldUsername + "History.csv", username + "History.csv")) {
        JOptionPane.showMessageDialog(
            this,
            "Can't rename history file or the source file doesn't exist.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }

      bank.getUtente().getAccount().setUsername(username);
      bank.getUtente().getAccount().setPassword(newPassword);

      JOptionPane.showMessageDialog(
          this, "Credentials updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

      resetFields();
    }
  }

  private void message(boolean x, String s) {
    if (!x) {
      JOptionPane.showMessageDialog(
          this, s + " field can't be updated.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}

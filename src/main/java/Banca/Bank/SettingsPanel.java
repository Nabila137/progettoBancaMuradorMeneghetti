package main.java.Bank;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.*;

public class SettingsPanel extends JPanel {
  private static final Color BACKGROUND_COLOR = new Color(245, 248, 250);
  private static final Color CARD_COLOR = Color.WHITE;
  private static final Color ACCENT_COLOR = new Color(0, 122, 255);
  private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
  private static final Font OPTION_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 16);

  private final Banca bank;
  private final CardLayout cardLayout;
  private final JPanel cardPanel;

  public SettingsPanel(Banca bank) {
    this.bank = bank;
    setLayout(new BorderLayout());
    setBackground(BACKGROUND_COLOR);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JPanel titlePanel = createTitlePanel();
    add(titlePanel, BorderLayout.NORTH);

    JPanel optionsPanel = createOptionsPanel();
    add(optionsPanel, BorderLayout.WEST);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);
    cardPanel.setBackground(BACKGROUND_COLOR);

    cardPanel.add(new AccountInfoPanel(bank), "accountInfo");
    cardPanel.add(new CredentialsPanel(bank), "credentials");

    add(cardPanel, BorderLayout.CENTER);
  }

  private JPanel createTitlePanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BACKGROUND_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    JLabel title = new JLabel("Settings");
    title.setFont(TITLE_FONT);
    title.setForeground(new Color(60, 60, 60));

    panel.add(title, BorderLayout.WEST);
    return panel;
  }

  private JPanel createOptionsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(BACKGROUND_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 20));
    panel.setPreferredSize(new Dimension(250, 300));

    JPanel accountOption = createOptionPanel("Account Information", "account");
    accountOption.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            cardLayout.show(cardPanel, "accountInfo");
          }
        });

    JPanel credentialsOption = createOptionPanel("Username & Password", "lock");
    credentialsOption.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            cardLayout.show(cardPanel, "credentials");
          }
        });

    panel.add(accountOption);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));
    panel.add(credentialsOption);

    return panel;
  }

  private JPanel createOptionPanel(String text, String iconName) {
    JPanel panel = new JPanel(new BorderLayout(15, 0));
    panel.setBackground(CARD_COLOR);
    panel.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
    panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    JLabel iconLabel = new JLabel(getIconForOption(iconName));
    panel.add(iconLabel, BorderLayout.WEST);

    JLabel textLabel = new JLabel(text);
    textLabel.setFont(OPTION_FONT);
    panel.add(textLabel, BorderLayout.CENTER);

    return panel;
  }

  private Icon getIconForOption(String name) {
    try {
      URL iconUrl = getClass().getResource("/icons/" + name + ".png");
      if (iconUrl != null) {
        return new ImageIcon(iconUrl);
      }
    } catch (Exception e) {
      System.err.println("Could not load icon: " + e.getMessage());
    }

    switch (name) {
      case "account":
        return UIManager.getIcon("OptionPane.informationIcon");
      case "lock":
        return UIManager.getIcon("OptionPane.errorIcon");
      default:
        return UIManager.getIcon("OptionPane.questionIcon");
    }
  }
}

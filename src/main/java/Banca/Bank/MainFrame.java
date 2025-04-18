package main.java.Bank;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {
  private Banca bank;
  private String username;
  private JPanel sidebar;
  private boolean sidebarVisible = false;
  private JPanel mainContentPanel;
  private JTextArea historyArea;
  private JPanel buttonPanel;

  private JLabel walletAmountLabel;
  private JLabel bankAmountLabel;
  private JPanel walletCard;
  private JPanel bankCard;

  public MainFrame(Banca bank) {
    this.bank = bank;
    this.username = bank.getUtente().getAccount().getUsername();

    setTitle("Bank - " + username);
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    initializeUI();
  }

  private void initializeUI() {

    JLayeredPane layeredPane = new JLayeredPane();
    layeredPane.setPreferredSize(new Dimension(800, 600));

    mainContentPanel = new JPanel(new BorderLayout());
    mainContentPanel.setBounds(0, 0, 800, 600);
    mainContentPanel.setBackground(new Color(240, 242, 245));

    JPanel topPanel = createTopPanel();
    topPanel.setBackground(new Color(255, 255, 255));
    mainContentPanel.add(topPanel, BorderLayout.NORTH);

    JPanel centerPanel = createCenterPanel();
    mainContentPanel.add(centerPanel, BorderLayout.CENTER);

    sidebar = createSidebar();
    sidebar.setBounds(-250, 0, 250, 600);
    sidebarVisible = false;

    layeredPane.add(mainContentPanel, JLayeredPane.DEFAULT_LAYER);
    layeredPane.add(sidebar, JLayeredPane.PALETTE_LAYER);

    add(layeredPane);

    addComponentListener(
        new ComponentAdapter() {
          @Override
          public void componentResized(ComponentEvent e) {
            layeredPane.setPreferredSize(getSize());
            mainContentPanel.setBounds(0, 0, getWidth(), getHeight());
            sidebar.setBounds(sidebarVisible ? 0 : -250, 0, 250, getHeight());
          }
        });
  }

  private JPanel createTopPanel() {
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(new Color(255, 255, 255));
    topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    titlePanel.setBackground(new Color(255, 255, 255, 0));

    JLabel logoLabel = new JLabel();
    logoLabel.setIcon(createRoundIcon(new Color(121, 153, 184), 40));

    JLabel titleLabel = new JLabel("Nabila's Bank", SwingConstants.LEFT);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
    titleLabel.setForeground(new Color(33, 33, 33));

    titlePanel.add(logoLabel);
    titlePanel.add(titleLabel);

    JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    profilePanel.setBackground(new Color(255, 255, 255, 0));

    JLabel welcomeLabel = new JLabel("Welcome, " + username);
    welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    welcomeLabel.setForeground(new Color(66, 66, 66));

    JLabel profileIcon = new JLabel();
    profileIcon.setIcon(createRoundIcon2("profileIcon.jpg", 40));
    profileIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    profileIcon.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            toggleSidebar();
          }
        });

    profilePanel.add(welcomeLabel);
    profilePanel.add(profileIcon);

    topPanel.add(titlePanel, BorderLayout.WEST);
    topPanel.add(profilePanel, BorderLayout.EAST);

    return topPanel;
  }

  private JPanel createCenterPanel() {
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    centerPanel.setBackground(new Color(240, 242, 245));

    JPanel balancePanel = createBalanceCards();
    centerPanel.add(balancePanel, BorderLayout.NORTH);

    historyArea = new JTextArea();
    historyArea.setEditable(false);
    historyArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    historyArea.setBackground(Color.WHITE);
    historyArea.setMaximumSize(new Dimension(800, 200));

    JScrollPane scrollPane = new JScrollPane(historyArea);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    centerPanel.add(scrollPane, BorderLayout.CENTER);

    // Buttons panel - wrapped in another panel with padding
    JPanel buttonContainer = new JPanel(new BorderLayout());
    buttonContainer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    buttonContainer.setBackground(new Color(240, 242, 245));
    buttonPanel = createButtonPanel();
    buttonContainer.add(buttonPanel, BorderLayout.CENTER);
    centerPanel.add(buttonContainer, BorderLayout.SOUTH);

    return centerPanel;
  }

  private JPanel createBalanceCards() {
    JPanel balancePanel = new JPanel(new GridLayout(1, 2, 15, 0));
    balancePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

    walletCard =
        createBalanceCard(
            "Wallet Balance", "€" + bank.getUtente().getPortafoglio(), new Color(76, 175, 80));

    bankCard =
        createBalanceCard(
            "Bank Balance",
            "€" + bank.getUtente().getAccount().getContoBancario(),
            new Color(33, 150, 243));

    balancePanel.add(walletCard);
    balancePanel.add(bankCard);

    return balancePanel;
  }

  private JPanel createBalanceCard(String title, String amount, Color color) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    titleLabel.setForeground(new Color(117, 117, 117));

    JLabel amountLabel = new JLabel(amount);
    amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    amountLabel.setForeground(color);

    card.add(titleLabel, BorderLayout.NORTH);
    card.add(amountLabel, BorderLayout.CENTER);

    if (title.equals("Wallet Balance")) {
      this.walletAmountLabel = amountLabel;
      this.walletCard = card;
    } else {
      this.bankAmountLabel = amountLabel;
      this.bankCard = card;
    }

    return card;
  }

  private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
    panel.setBackground(new Color(240, 242, 245));

    JButton depositButton = createModernButton("Deposit", new Color(76, 175, 80));
    depositButton.addActionListener(e -> showDepositDialog());
    panel.add(depositButton);

    JButton withdrawButton = createModernButton("Withdraw", new Color(244, 67, 54));
    withdrawButton.addActionListener(e -> showWithdrawDialog());
    panel.add(withdrawButton);

    JButton investButton = createModernButton("Invest", new Color(0, 150, 136));
    investButton.addActionListener(e -> showInvestmentsDialog());
    panel.add(investButton);

    JButton nextMonthButton = createModernButton("Next Month", new Color(255, 152, 0));
    nextMonthButton.addActionListener(e -> showNextMonthDialog());
    panel.add(nextMonthButton);

    JButton historyButton = createModernButton("History", new Color(156, 39, 176));
    historyButton.addActionListener(e -> showTransactionHistory());
    panel.add(historyButton);

    JButton settingsButton = createModernButton("Investments", new Color(96, 125, 139));
    settingsButton.addActionListener(e -> showInvestmentsPanel());
    panel.add(settingsButton);

    return panel;
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
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
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
    button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    button.setPreferredSize(new Dimension(120, 40));

    return button;
  }

  private JPanel createSidebar() {
    JPanel sidebar = new JPanel();
    sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
    sidebar.setBackground(new Color(55, 71, 79));
    sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

    JPanel profileSection = new JPanel();
    profileSection.setLayout(new BoxLayout(profileSection, BoxLayout.Y_AXIS));
    profileSection.setBackground(new Color(55, 71, 79));
    profileSection.setAlignmentX(Component.LEFT_ALIGNMENT);
    profileSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    JLabel profilePic = new JLabel();
    profilePic.setIcon(createRoundIcon(new Color(176, 190, 197), 80));
    profilePic.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel profileName = new JLabel(username);
    profileName.setFont(new Font("Segoe UI", Font.BOLD, 16));
    profileName.setForeground(Color.BLACK);
    profileName.setAlignmentX(Component.CENTER_ALIGNMENT);
    profileName.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    profileSection.add(profilePic);
    profileSection.add(profileName);
    sidebar.add(profileSection);

    String[] menuItems = {
      "Dashboard", "Profile", "Transactions", "Investments", "Settings", "Help", "Logout"
    };
    Icon[] menuIcons = {
      createIconFromChar('\uE88A'),
      createIconFromChar('\uE7FD'),
      createIconFromChar('\uE8D1'),
      createIconFromChar('\uE1CB'),
      createIconFromChar('\uE8B8'),
      createIconFromChar('\uE887'),
      createIconFromChar('\uE879')
    };

    int i = 0;
    for (String menuItem : menuItems) {
      JButton menuButton = createSidebarButton(menuItem, menuIcons[i]);
      menuButton.addActionListener(e -> handleSidebarAction(menuItem));
      sidebar.add(menuButton);

      if (i < menuItems.length - 1) {
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
      }
      i++;
    }

    sidebar.add(Box.createVerticalGlue());

    return sidebar;
  }

  private JButton createSidebarButton(String text, Icon icon) {
    JButton button = new JButton(text, icon);
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setHorizontalAlignment(SwingConstants.LEFT);
    button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
    button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    button.setBackground(new Color(55, 71, 79));
    button.setForeground(Color.BLACK);
    button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    button.setFocusPainted(false);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    button.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(69, 90, 100));
          }

          @Override
          public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(55, 71, 79));
          }
        });

    return button;
  }

  private void handleSidebarAction(String action) {
    switch (action) {
      case "Dashboard":
        break;
      case "Profile":
        showProfileDialog();
        break;
      case "Transactions":
        showTransactionHistory();
        break;
      case "Investments":
        showInvestmentsPanel();
        break;
      case "Settings":
        showSettingsDialog();
        break;
      case "Help": // cancel it-->Change profile picture. It can give some options (a predefined
                   // list)
        showHelpDialog();
        break;
      case "Logout":
        logout();
        break;
    }
    toggleSidebar();
  }

  private void showInvestmentsPanel() {
    JDialog dialog = new JDialog(this, "Investment Portfolio", true);
    dialog.setSize(900, 500);
    dialog.setLocationRelativeTo(this);

    InvestmentsPanel investmentsPanel = new InvestmentsPanel(bank);
    dialog.add(investmentsPanel);
    dialog.setVisible(true);
  }

  private void toggleSidebar() {
    sidebarVisible = !sidebarVisible;

    if (sidebarVisible) {
      // Create overlay
      JPanel overlay = new JPanel();
      overlay.setOpaque(false);
      overlay.setBackground(new Color(0, 0, 0, 100));
      overlay.setBounds(0, 0, getWidth(), getHeight());
      overlay.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              toggleSidebar();
            }
          });

      ((JLayeredPane) getContentPane().getComponent(0)).add(overlay, JLayeredPane.MODAL_LAYER - 1);
    } else {

      Component[] components =
          ((JLayeredPane) getContentPane().getComponent(0))
              .getComponentsInLayer(JLayeredPane.MODAL_LAYER - 1);
      // Check if the for is needed
      for (Component component : components) {
        ((JLayeredPane) getContentPane().getComponent(0)).setVisible(false);
      }
    }

    new Thread(
            () -> {
              try {
                int startX = sidebarVisible ? -250 : 0;
                int endX = sidebarVisible ? 0 : -250;

                if (sidebarVisible) {
                  sidebar.setVisible(true);
                }

                for (int i = 0; i <= 10; i++) {
                  final int x = startX + (endX - startX) * i / 10;
                  SwingUtilities.invokeLater(
                      () -> {
                        sidebar.setBounds(x, 0, 250, getHeight());
                        repaint();
                      });
                  Thread.sleep(10);
                }

                if (!sidebarVisible) {
                  sidebar.setVisible(false);
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            })
        .start();
  }

  private void showDepositDialog() {

    double amount = showAmountDialog("Deposit Funds", "Deposit");

    if (amount < 0) {
      JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
      if (DepositWithdraw.canDepositWithdraw(bank.getUtente().getPortafoglio(), amount)) {
        bank.depositWithdraw(amount);
        updateDepositWithdraw(amount, "Deposited", "Deposit");
        updateBalances();
      } else {
        JOptionPane.showMessageDialog(
            this,
            "Can't deposit the inserted amount. The amount can't be '0'. If it's not '0' then check"
                + " your wallet balance.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void showWithdrawDialog() {

    double amount = showAmountDialog("Withdraw Funds", "Withdraw");

    if (amount < 0) {
      JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
      if (DepositWithdraw.canDepositWithdraw(
          bank.getUtente().getAccount().getContoBancario(), amount)) {
        double amount1 = bank.withdrawal(amount);
        bank.depositWithdraw(amount1);
        updateDepositWithdraw(amount, "Withdrawn", "Withdraw");
        updateBalances();
      } else {
        JOptionPane.showMessageDialog(
            this,
            "Can't withdraw the inserted amount. The amount can't be '0'. If it's not '0' then"
                + " check your bank balance.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void updateDepositWithdraw(double amount, String action, String title) {

    String s = "" + bank.getUtente().getAccount().getContoBancario();
    String s1 = "" + bank.getUtente().getPortafoglio();

    if (!Methods.updateFile(username + ".csv", s, 2, 7)
        || !Methods.updateFile(username + ".csv", s1, 2, 5)) {
      JOptionPane.showMessageDialog(
          this,
          "File can't be updated. Check the file's existence and the values you have passed in the"
              + " method.",
          "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    addToHistory(action + ": " + amount);
    Methods.appendLine(
        username + "History.csv",
        action
            + ": "
            + amount
            + "___Date: "
            + LocalDate.now()
            + "___Time: "
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    updateBalances();
    JOptionPane.showMessageDialog(
        this, title + " successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  private double showAmountDialog(String title, String action) {
    JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    panel.add(titleLabel);

    JTextField amountField = new JTextField();
    amountField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    panel.add(new JLabel("Amount:"));
    panel.add(amountField);

    int result =
        JOptionPane.showConfirmDialog(
            this, panel, action, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
      try {
        return Double.parseDouble(amountField.getText());
      } catch (NumberFormatException e) {
        return -1;
      }
    }
    return -1;
  }

  private void showNextMonthDialog() {

    JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel titleLabel = new JLabel("Skip months");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    titleLabel.setForeground(new Color(33, 33, 33));
    panel.add(titleLabel);

    JTextField numberField = new JTextField();
    numberField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    numberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    panel.add(new JLabel("Months to skip:"));
    panel.add(numberField);

    int result =
        JOptionPane.showConfirmDialog(
            this, panel, "Next month", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {

      try {
        int number = Integer.parseInt(numberField.getText());

        bank.getUtente().nextMonth(number);
        String str = "" + bank.getUtente().getPortafoglio();

        if (!Methods.updateFile(username + ".csv", str, 2, 5)) {
          JOptionPane.showMessageDialog(
              this, "Protafoglio field can't be updated.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

          if (!Methods.appendLine(
              username + "History.csv", "You have skipped " + number + " months.")) {
            JOptionPane.showMessageDialog(
                this,
                "Can't append information to history file",
                "Error",
                JOptionPane.ERROR_MESSAGE);
          }
          updateBalances();
        }

        str = "" + bank.getUtente().getAccount().getInitialDate();
        if (!Methods.updateFile(username + ".csv", str, 2, 8)) {
          JOptionPane.showMessageDialog(
              this, "Initial date field can't be updated.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        showUpdateInvestmentInformation();

      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
            this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void showUpdateInvestmentInformation() {

    Investment[] investments = bank.getUtente().getAccount().lookForReturns();

    if (investments != null) {
      String info = Methods.investmentInformation(investments);
      if (info != null) {
        JOptionPane.showMessageDialog(
            this, "You have got some returns!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        addToHistory(info);
        boolean bool =
            Methods.appendLine(
                username + "History.csv",
                "Received returns from investments \n Returned___Date: "
                    + LocalDate.now()
                    + "___Time: "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    + "\n"
                    + info);
        showMessage(bool, "History "); // Just put the messages here
        String s = "" + bank.getUtente().getAccount().getInvestments().size();
        // Is it okay to use the same variable "bool"?
        bool = Methods.updateFile(username + ".csv", s, 2, 9);
        showMessage(bool, "");
        String s1 = "" + bank.getUtente().getAccount().getContoBancario();
        Methods.updateFile(username + ".csv", s1, 2, 7);
        if (!Methods.updateInvestments(
            username + ".csv",
            4,
            Methods.allInvestments(bank.getUtente().getAccount().getInvestments()))) {
          JOptionPane.showMessageDialog(
              this, "File can't be updated", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(
              this,
              "File has been updated successfully!",
              "Success",
              JOptionPane.INFORMATION_MESSAGE);
          updateBalances();
        }
      }
    }
  }

  private void showMessage(boolean x, String a) {

    if (!x) {
      JOptionPane.showMessageDialog(
          this, a + "File can't be updated", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void showTransactionHistory() {
    JDialog historyDialog = new JDialog(this, "Transaction History", true);
    historyDialog.setSize(600, 400);
    historyDialog.setLocationRelativeTo(this);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JTextArea historyText = new JTextArea();
    historyText.setEditable(false);
    historyText.setFont(new Font("Segoe UI", Font.PLAIN, 14));

    String s = Methods.getData(username + "History.csv");
    if (s != null && !s.isBlank()) {
      historyText.setText(s);
      JScrollPane scrollPane = new JScrollPane(historyText);
      panel.add(scrollPane, BorderLayout.CENTER);

      historyDialog.add(panel);
      historyDialog.setVisible(true);
    } else {
      JOptionPane.showMessageDialog(
          this, "No history available.", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  // separate it. Put it in another class. The code is too long!!!
  private void showInvestmentsDialog() {

    JDialog investDialog = new JDialog(this, "Make an Investment", true);
    investDialog.setSize(500, 400);
    investDialog.setLocationRelativeTo(this);

    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JPanel amountPanel = new JPanel(new BorderLayout(5, 5));
    JTextField amountField = new JTextField();
    amountField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    amountPanel.add(new JLabel("Investment Amount:"), BorderLayout.NORTH);
    amountPanel.add(amountField, BorderLayout.CENTER);

    JPanel durationPanel = new JPanel(new GridLayout(1, 3, 10, 0));
    durationPanel.setBorder(BorderFactory.createTitledBorder("Durations"));

    int corto = 2;
    int medio = 5;
    int lungo = 9;

    ButtonGroup durationGroup = new ButtonGroup();
    JRadioButton shortTerm = createModernRadioButton("Short __ " + corto + " months");
    JRadioButton mediumTerm = createModernRadioButton("Medium __ " + medio + " months");
    JRadioButton longTerm = createModernRadioButton("Long __ " + lungo + " months");

    durationGroup.add(shortTerm);
    durationGroup.add(mediumTerm);
    durationGroup.add(longTerm);

    durationPanel.add(shortTerm);
    durationPanel.add(mediumTerm);
    durationPanel.add(longTerm);

    JPanel riskPanel = new JPanel(new GridLayout(1, 3, 10, 0));
    riskPanel.setBorder(BorderFactory.createTitledBorder("Risk Levels"));

    int low = 10;
    int medium = 50;
    int high = 85;
    double lowGain = 0.5;
    double mediumGain = 1.3;
    double highGain = 2.0;

    ButtonGroup riskGroup = new ButtonGroup();
    JRadioButton lowRisk =
        createModernRadioButton("Low risk - " + low + "%\n" + (lowGain * 100) + "% Gain");
    JRadioButton mediumRisk =
        createModernRadioButton("Mediuum risk - " + medium + "%\n" + (mediumGain * 100) + "% Gain");
    JRadioButton highRisk =
        createModernRadioButton("High risk - " + high + "%\n" + (highGain * 100) + "% Gain");

    riskGroup.add(lowRisk);
    riskGroup.add(mediumRisk);
    riskGroup.add(highRisk);

    riskPanel.add(lowRisk);
    riskPanel.add(mediumRisk);
    riskPanel.add(highRisk);

    JButton investButton = createModernButton("Make Investment", new Color(0, 150, 136));
    investButton.addActionListener(
        e -> {
          double amount = Methods.parseDouble2(amountField.getText());
          if (amount > 0) {

            int duration = 0;
            if (shortTerm.isSelected()) {
              duration = corto;
            }
            if (mediumTerm.isSelected()) {
              duration = medio;
            }
            if (longTerm.isSelected()) {
              duration = lungo;
            }

            if (duration > 0) {
              int risk = 0;
              double gain = 0;
              if (lowRisk.isSelected()) {
                risk = low;
                gain = lowGain;
              }
              if (mediumRisk.isSelected()) {
                risk = medium;
                gain = mediumGain;
              }
              if (highRisk.isSelected()) {
                risk = high;
                gain = highGain;
              }

              if (risk > 0 && gain > 0) {
                if (DepositWithdraw.canDepositWithdraw(
                    bank.getUtente().getAccount().getContoBancario(), amount)) {
                  ; // .canMakeInvestment(bank.getUtente().getAccount().getContoBancario(), amount))
                    // {
                  Investment x =
                      new Investment(
                          amount,
                          risk,
                          duration,
                          bank.getUtente().getAccount().getInitialDate(),
                          gain);
                  if (Methods.appendLine(username + ".csv", Methods.investmentFormat(x))) {
                    bank.getUtente().getAccount().addInvestimento(x);
                    bank.getUtente().getAccount().diminuisciContoBancario(amount);
                    String s = "" + bank.getUtente().getAccount().getContoBancario();
                    Methods.updateFile(username + ".csv", s, 2, 7);
                    Methods.appendLine(
                        username + "History.csv",
                        "You have made an investment using "
                            + amount
                            + "___Date: "
                            + LocalDate.now()
                            + "___Time: "
                            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                    addToHistory("You have made an investment.");
                    updateBalances();
                    investDialog.dispose();
                  }
                } else {
                  JOptionPane.showMessageDialog(
                      this,
                      "You don't have enough mony in you bank account.",
                      "Error",
                      JOptionPane.ERROR_MESSAGE);
                }
              } else {
                JOptionPane.showMessageDialog(
                    this, "Please select a risk level.", "Error", JOptionPane.ERROR_MESSAGE);
              }

            } else {
              JOptionPane.showMessageDialog(
                  this, "Please select a duration.", "Error", JOptionPane.ERROR_MESSAGE);
            }

          } else {
            JOptionPane.showMessageDialog(
                this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
          }
        });

    JPanel optionsPanel = new JPanel(new GridLayout(2, 1, 10, 15));
    optionsPanel.add(durationPanel);
    optionsPanel.add(riskPanel);

    mainPanel.add(amountPanel, BorderLayout.NORTH);
    mainPanel.add(optionsPanel, BorderLayout.CENTER);
    mainPanel.add(investButton, BorderLayout.SOUTH);

    investDialog.add(mainPanel);
    investDialog.setVisible(true);
  }

  private JRadioButton createModernRadioButton(String text) {
    JRadioButton button =
        new JRadioButton("<html><center>" + text.replace("\n", "<br>") + "</center></html>");
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setVerticalAlignment(SwingConstants.CENTER);
    button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    button.setBackground(Color.WHITE);
    button.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
    return button;
  }

  private void showProfileDialog() {
    JDialog profileDialog = new JDialog(this, "Profile", true);
    profileDialog.setSize(400, 300);
    profileDialog.setLocationRelativeTo(this);

    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel profilePic = new JLabel();
    profilePic.setIcon(createRoundIcon(new Color(25, 118, 210), 80));
    profilePic.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(profilePic, BorderLayout.NORTH);

    JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));

    infoPanel.add(createInfoLabel("Username:", username));
    infoPanel.add(createInfoLabel("Wallet Balance:", "€" + bank.getUtente().getPortafoglio()));
    infoPanel.add(
        createInfoLabel("Bank Balance:", "€" + bank.getUtente().getAccount().getContoBancario()));

    panel.add(infoPanel, BorderLayout.CENTER);

    profileDialog.add(panel);
    profileDialog.setVisible(true);
  }

  private JPanel createInfoLabel(String label, String value) {
    JPanel panel = new JPanel(new BorderLayout());

    JLabel nameLabel = new JLabel(label);
    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    nameLabel.setForeground(new Color(66, 66, 66));

    JLabel valueLabel = new JLabel(value);
    valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    valueLabel.setForeground(new Color(117, 117, 117));

    panel.add(nameLabel, BorderLayout.WEST);
    panel.add(valueLabel, BorderLayout.EAST);

    return panel;
  }

  private void showSettingsDialog() {
    JDialog settingsDialog = new JDialog(this, "Settings", true);
    settingsDialog.setSize(800, 800);
    settingsDialog.setLocationRelativeTo(this);

    JPanel panel = new SettingsPanel(bank);

    settingsDialog.add(panel);
    settingsDialog.setVisible(true);
  }

  private void showHelpDialog() {
    JOptionPane.showMessageDialog(
        this,
        "This application hasn't got anything to help the clients.",
        "Apology",
        JOptionPane.INFORMATION_MESSAGE);
  }

  private void logout() {
    int confirm =
        JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

    if (confirm == JOptionPane.YES_OPTION) {
      this.dispose();

      EventQueue.invokeLater(
          () -> {
            LoginFrame loginFrame = new LoginFrame(bank);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
          });
    }
  }

  private void addToHistory(String entry) {
    historyArea.append(entry + "\n");
  }

  private void updateBalances() {

    walletAmountLabel.setText("€" + bank.getUtente().getPortafoglio());
    bankAmountLabel.setText("€" + bank.getUtente().getAccount().getContoBancario());

    flashUpdate(walletAmountLabel);
    flashUpdate(bankAmountLabel);
  }

  private void flashUpdate(JLabel label) {
    Color original = label.getForeground();
    label.setForeground(Color.WHITE);
    new Timer(300, e -> label.setForeground(original)).start();
  }

  private Icon createRoundIcon(Color color, int diameter) {
    BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = image.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(color);
    g2.fillOval(0, 0, diameter, diameter);
    g2.dispose();
    return new ImageIcon(image);
  }

  private Icon createRoundIcon2(String imagePath, int diameter) {
    try {
      BufferedImage originalImage = ImageIO.read(new File(imagePath));

      BufferedImage scaledImage =
          new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = scaledImage.createGraphics();
      g2d.setRenderingHint(
          RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.drawImage(originalImage, 0, 0, diameter, diameter, null);
      g2d.dispose();

      BufferedImage circle = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
      g2d = circle.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.fillOval(0, 0, diameter, diameter);
      g2d.dispose();

      BufferedImage result = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
      g2d = result.createGraphics();
      g2d.drawImage(scaledImage, 0, 0, null);
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
      g2d.drawImage(circle, 0, 0, null);
      g2d.dispose();

      return new ImageIcon(result);
    } catch (IOException e) {
      e.printStackTrace();
      return createRoundIcon(Color.GRAY, diameter);
    }
  }

  private Icon createIconFromChar(char c) {
    BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = image.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(Color.BLACK);
    g2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
    g2.drawString(String.valueOf(c), 4, 18);
    g2.dispose();
    return new ImageIcon(image);
  }
}

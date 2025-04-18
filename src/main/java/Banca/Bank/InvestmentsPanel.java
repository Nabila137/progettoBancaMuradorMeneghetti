package main.java.Bank;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class InvestmentsPanel extends JPanel {
  private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
  private static final Color HEADER_COLOR = new Color(70, 130, 180);
  private static final Color ROW_COLOR = Color.WHITE;
  private static final Color ALT_ROW_COLOR = new Color(240, 240, 240);
  private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
  private static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);

  public InvestmentsPanel(Banca bank) {
    setLayout(new BorderLayout());
    setBackground(BACKGROUND_COLOR);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Create title panel
    JPanel titlePanel = createTitlePanel();
    add(titlePanel, BorderLayout.NORTH);

    // Create main content panel
    JPanel contentPanel = createContentPanel(bank);
    add(contentPanel, BorderLayout.CENTER);
  }

  private JPanel createTitlePanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BACKGROUND_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

    JLabel title = new JLabel("Investment Portfolio");
    title.setFont(new Font("Segoe UI", Font.BOLD, 18));
    title.setForeground(new Color(60, 60, 60));

    panel.add(title, BorderLayout.WEST);
    return panel;
  }

  private JPanel createContentPanel(Banca bank) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BACKGROUND_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    // Column names
    String[] columns = {"Date", "Amount", "Duration", "Risk", "Gain/Loss", "End Date"};

    // Create table model
    DefaultTableModel model =
        new DefaultTableModel(columns, 0) {
          @Override // What is this? Where is it used?
          public boolean isCellEditable(int row, int column) {
            return false;
          }

          @Override // I have no idea what this is
          public Class<?> getColumnClass(int columnIndex) {
            return String.class;
          }
        };

    // Get investments from bank
    List<Investment> investments = bank.getUtente().getAccount().getInvestments();

    if (investments.isEmpty()) {
      JLabel noDataLabel = new JLabel("No recent investments have been made", JLabel.CENTER);
      noDataLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
      noDataLabel.setForeground(new Color(120, 120, 120));
      panel.add(noDataLabel, BorderLayout.CENTER);
    } else {
      // Use DateTimeFormatter for LocalDate
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

      for (Investment inv : investments) {
        // Safely format LocalDate objects
        String startDate =
            inv.getStartDate() != null ? inv.getStartDate().format(dateFormatter) : "N/A";
        String endDate = inv.getEndDate() != null ? inv.getEndDate().format(dateFormatter) : "N/A";

        // Why use Object[]?
        model.addRow(
            new Object[] {
              startDate,
              formatCurrency(inv.getAmount()),
              inv.getDurata() /*+ " months"*/,
              formatRisk(inv.getRischio()),
              formatGainLoss(inv.getGuadagno()),
              endDate
            });
      }

      JTable table = new JTable(model);
      styleTable(table);

      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBorder(BorderFactory.createEmptyBorder());
      scrollPane.getViewport().setBackground(ROW_COLOR);

      panel.add(scrollPane, BorderLayout.CENTER);
    }

    return panel;
  }

  private void styleTable(JTable table) {
    // Table styling
    table.setRowHeight(30);
    table.setFont(TABLE_FONT);
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 0));
    table.setSelectionBackground(new Color(220, 240, 255));

    // Header styling
    JTableHeader header = table.getTableHeader();
    header.setFont(HEADER_FONT);
    header.setBackground(HEADER_COLOR);
    header.setForeground(Color.BLACK);
    header.setPreferredSize(new Dimension(header.getWidth(), 35));

    // Row striping
    table.setDefaultRenderer(
        Object.class,
        new DefaultTableCellRenderer() {
          @Override
          public Component getTableCellRendererComponent(
              JTable table,
              Object value,
              boolean isSelected,
              boolean hasFocus,
              int row,
              int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
              setBackground(row % 2 == 0 ? ROW_COLOR : ALT_ROW_COLOR);
            }

            // Right-align numeric columns
            if (column == 1 || column == 4) {
              setHorizontalAlignment(SwingConstants.LEFT);
            } else {
              setHorizontalAlignment(SwingConstants.LEFT);
            }

            return this;
          }
        });
  }

  private String formatCurrency(double amount) {
    return String.format("€%,.2f", amount);
  }

  private String formatRisk(int risk) {
    /*if (risk >= 70) return "High (" + risk + "%)";
    if (risk >= 40) return "Medium (" + risk + "%)";
    return "Low (" + risk + "%)";*/
    return risk + "%";
  }

  private String formatGainLoss(double gain) {
    /*if (gain >= 0) {
        return String.format("+$%,.2f", gain);
    } else {
        return String.format("-$%,.2f", Math.abs(gain));
    }*/
    return gain * 100 + "%";
  }
}

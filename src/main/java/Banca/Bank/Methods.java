package main.java.Bank;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Vector;

public class Methods {

  // DO THESE METHODS REALLY NEED TO BE STATIC?? Will it cause issue?

  public static Banca parseBanca(String s) {

    Utente utente = null; // come faccio ad evitare il "null"?
    Account account = null;

    try (FileReader reader = new FileReader(s);
        Scanner input = new Scanner(reader)) {
      String riga;
      if (input.hasNextLine()) {
        riga = input.nextLine(); // ignoring the first line
        // (I don't know if it's the correct way though.)
        if (input.hasNextLine()) {
          riga = input.nextLine();
          try {
            String[] parts = riga.split("\\|");
            String username = parts[0].trim();
            String password = parts[1].trim();
            String nome = parts[2].trim();
            String cognome = parts[3].trim();
            double portafoglio = parseDouble(parts[4].trim());
            double salary = parseDouble(parts[5].trim());
            double contoBancario = parseDouble(parts[6].trim());
            LocalDate initialDate = parseLocalDate(parts[7].trim());
            int numInvestments = parseInt(parts[8].trim());
            utente = new Utente(nome, cognome);
            utente.setPortafoglio(portafoglio);
            utente.setSalary(salary);
            account = new Account(username, password);
            account.setContoBancario(contoBancario);

            account.setInitialDate(initialDate);
            account.setNumInvestimenti(numInvestments);
          } catch (Exception e) {
            return null;
          }
        }
      }

      if (input.hasNextLine()) {
        riga = input.nextLine();
      }
      while (input.hasNextLine()) {
        riga = input.nextLine();
        Investment in = parseInvestment(riga);
        if (in != null) {
          account.addInvestimento(in);
        }
      }
      utente.setAccount(account);
      return new Banca(utente);

    } catch (FileNotFoundException e1) {
      return null;
    } catch (IOException e1) {
      return null;
    }
  }

  public static Investment parseInvestment(String s) {

    try {
      String[] parts = s.split("\\|");
      double amount = parseDouble(parts[0].trim());
      int risk = parseInt(parts[1].trim());
      int duration = parseInt(parts[2].trim());
      LocalDate startDate = parseLocalDate(parts[3].trim());
      double guadagno = parseDouble(parts[4].trim());

      return new Investment(amount, risk, duration, startDate, guadagno);
    } catch (Exception e) {
      return null;
    }
  }

  public static double parseDouble(String s) {
    try {
      double amount = (double) Double.parseDouble(s.trim());
      return amount;
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public static double parseDouble2(String s) {

    try {
      return Double.parseDouble(s);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  public static int parseInt(String s) {
    try {
      int amount = (int) Integer.parseInt(s.trim());
      return amount;
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public static LocalDate parseLocalDate(String s) {

    try {
      LocalDate initialDate = LocalDate.parse(s.trim());
      return initialDate;
    } catch (DateTimeParseException e) {
      return LocalDate.now();
    }
  }

  // Works (It's not efficient. What if the line is empty or there is a \n?). It's not that general
  public static boolean updateFile(String fileName, String replacement, int nLine, int field) {

    boolean ok = false;
    File newFile = new File("new.csv");

    try (FileReader reader = new FileReader(fileName.trim());
        Scanner input = new Scanner(reader);
        FileWriter write = new FileWriter(newFile, true);
        PrintWriter output = new PrintWriter(write)) {

      int contatore = 0;

      if (nLine > 0) {
        while (input.hasNextLine()) {
          contatore++;
          String riga = input.nextLine();
          if (contatore == nLine) {
            riga = updateField(riga, replacement.trim(), field);
            ok = true;
          }
          output.println(riga.trim());
        }

      } else {
        return false;
      }

      // }

    } catch (FileNotFoundException e) {
      return false;
    } catch (IOException e) {
      return false;
    }

    File oldFile = new File(fileName.trim());
    oldFile.delete(); // What if the file can't be deleted?
    newFile.renameTo(oldFile); // What if it can't be renamed?

    if (!ok) return false;

    return true;
  }

  public static String updateField(String s, String part, int field) {

    try {

      String[] parts = s.split(" \\|");
      int length = parts.length;
      if (field > 0 && length >= field) {
        boolean trovato = false;
        String line = "";
        for (int i = 0; i < length - 1; i++) {
          if (i == field - 1) {
            parts[i] = part.trim();
            trovato = true;
          }
          line += parts[i].trim() + "  | ";
        }
        if (!trovato) {
          line += part.trim();
        } else {
          line += parts[length - 1].trim();
        }
        return line;
      } else return null;

    } catch (Exception e) {
      return null;
    }
  }

  public static boolean appendLine(String fileName, String data) {

    boolean ok = false;
    String name = fileName.trim();

    try (FileReader reader = new FileReader(name);
        Scanner input = new Scanner(reader);
        FileWriter write = new FileWriter(new File(name), true);
        PrintWriter output = new PrintWriter(write)) {

      while (!input.hasNextLine() && !input.nextLine().isBlank()) {
        // Is it okay to avoid the lines this way?

      }
      output.println(data.trim());
      ok = true;

    } catch (FileNotFoundException e) {
      return false;
    } catch (IOException e) {
      return false;
    }

    return ok;
  }

  // Posso generalizzarlo. Update File and this one are very similar. Let's think about it later
  // A separate method to update a field which will return a String.
  // A generic method to update that line of the file (it can have "from" and "to" parameter).
  public static boolean updateInvestments(String fileName, int from, String s) {

    File newFile = new File("temp.csv");
    boolean trovato = false;

    try (FileReader reader = new FileReader(fileName.trim());
        Scanner input = new Scanner(reader);
        FileWriter write = new FileWriter(newFile, true);
        PrintWriter output = new PrintWriter(write)) {
      int contatore = 0;

      while (input.hasNextLine() && !trovato) {
        contatore++;
        String riga = input.nextLine();
        if (contatore == from) {
          if (s != null) {
            output.println(s);
          }
          trovato = true;
        } else {
          output.println(riga);
        }
      }

    } catch (FileNotFoundException e) {
      return false;
    } catch (IOException e) {
      return false;
    }

    File oldFile = new File(fileName.trim());
    oldFile.delete();
    newFile.renameTo(oldFile);

    if (!trovato) {
      return false;
    }

    return true;
  }

  public static String allInvestments(Vector<Investment> investments) {

    if (investments != null) {
      int size = investments.size();
      String s = "";

      for (int i = 0; i < size - 1; i++) {
        s += investmentFormat(investments.get(i)) + "";
      }
      if (size > 0) {
        s += investmentFormat(investments.get(size - 1));
      }
      return s;
    }

    return null;
  }

  // Delete it. Write a method that will take a delimiter as parameter and a Vector of String. Then
  // using a "for" which will build the string and return it.Can be used in this method,
  // initialLines, in the RegistrationFrame to get the string from all the fields.
  public static String investmentFormat(Investment investment) {

    String s = "\n";
    s += investment.getAmount() + " | ";
    s += investment.getRischio() + " | ";
    s += investment.getDurata() + " | ";
    s += investment.getStartDate() + " | ";
    s += investment.getGuadagno() + " | ";
    s += investment.getSuccessPercentage();

    return s;
  }

  public static String investmentInformation(Investment[] investments) {

    int length = investments.length;
    String info = "";

    if (length > 0) {
      boolean ok = false;
      for (int i = 0; i < length; i++) {
        if (investments[i] != null) {
          ok = true;
          info += "Investment__" + (i + 1) + "__\n";
          info += investments[i].toString();
          info += "\n\n";
        }
      }
      if (ok) {
        return info;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public static String getData(String fileName) {

    try (FileReader reader = new FileReader(fileName.trim());
        Scanner input = new Scanner(reader); ) {
      String s = "";
      while (input.hasNextLine()) {
        s += input.nextLine() + "\n";
      }
      return s;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      return null;
    }
  }

  public static boolean registraUtente(String username, String data, LocalDate date, String time) {

    String fileUtente = username + ".csv";
    String historyFile = username + "History.csv";
    if ((createFileUtente(fileUtente, data)) && (historyFileInitialLine(historyFile, date, time))) {
      return true;
    }

    return false;
  }

  public static String[] initialLines() {

    String[] lines = new String[2];
    lines[0] =
        "Username | Password | Name | Surname | Portafoglio | Salary | ContoBancario | InitialDate"
            + " | Number of investments | File";
    lines[1] = "Amount | Risk | Duration | Start date | Guadagno | Success Percentage";

    return lines;
  }

  public static boolean createFileUtente(String fileName, String data) {

    try (FileWriter write = new FileWriter(fileName, true);
        PrintWriter print = new PrintWriter(write)) {
      String[] s = initialLines();
      print.println(s[0].trim());
      print.println(data.trim());
      print.println(s[1].trim());
      return true;
    } catch (FileNotFoundException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  public static boolean historyFileInitialLine(String fileName, LocalDate date, String time) {

    try (FileWriter write = new FileWriter(fileName, true);
        PrintWriter print = new PrintWriter(write)) {
      print.println("Your registration date: " + date + "___Time: " + time);
      return true;
    } catch (FileNotFoundException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  public static int randomNumber(int min, int max) {

    return (int) (Math.random() * (max - min)) + 1;
  }

  public static boolean renameFile(String oldPath, String newPath) {
    try {
      Path source = Path.of(oldPath);
      Path target = Path.of(newPath);

      if (!Files.exists(source)) {
        return false;
      }

      Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
      return true;

    } catch (IOException e) {
      return false;
    }
  }
}

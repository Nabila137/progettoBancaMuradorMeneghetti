import java.util.Scanner;

public class BancaMenedor {

  public static void menu() {

    System.out.println("BENVENUTO ALLA BANCA MENEDOR");
    System.out.println(" Inserisci cosa vuoi fare?  ");
    System.out.println();
    System.out.println("1 ---> Deposita");
    System.out.println("2 ---> Preleva");
    System.out.println("3 ---> Investi");
    System.out.println("4 ---> Visualizza conto bancario");
    System.out.println("5 ---> Visualizza conto portafoglio");
    System.out.println();
    System.out.println("6 ---> Passare al mese successivo");
    System.out.println("0 ---> Uscire");
    System.out.println("\n");
  } // menu

  public static void tempo(int mese, int anno) {

    String Mese = "";

    switch (mese) {
      case 1:
        Mese = "Gennaio";
        break;
      case 2:
        Mese = "Febbario";
        break;
      case 3:
        Mese = "Marzo";
        break;
      case 4:
        Mese = "Aprile";
        break;
      case 5:
        Mese = "Maggio";
        break;
      case 6:
        Mese = "Giugno";
        break;
      case 7:
        Mese = "Luglio";
        break;
      case 8:
        Mese = "Agosto";
        break;
      case 9:
        Mese = "Settembre";
        break;
      case 10:
        Mese = "Ottobre";
        break;
      case 11:
        Mese = "Novembre";
        break;
      case 12:
        Mese = "Dicembre";
        break;
      default:
        break;
    } // switch

    System.out.println(Mese + "/" + anno + "\n");
  } // tempo

  public static void menuInvestimentiDurata() {

    System.out.println("Durata investimenti:\n");
    System.out.println("1- Breve ( 2 mesi )");
    System.out.println("2- Media ( 5 mesi )");
    System.out.println("3- Lunga ( 9 mesi )");
    System.out.print("\nInserisci cosa vuoi fare-->");
  } // menuInvestimentiDurata

  public static void menuInvestimentiRischio() {

    System.out.println(
        " Rischio investimenti (bisogna inserire minimo 100$ e un massimo di 2000$ ) : ");
    System.out.println("1- Alto");
    System.out.println("2- Medio");
    System.out.println("3- Basso");
    System.out.println(" Inserisci cosa vuoi fare-->");
  } // menuInvestimentiRischio

  public static int convertitore(String s) {

    int i = -1;
    s.trim();
    try {
      i = (int) Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return i;
    }

    return i;
  } // convertitore

  public static double cifreDopoVirgola(double n) {

    String s = "";
    s += n;
    String s1 = "";
    int posPunto = 0;
    boolean trovato = false;

    for (int i = 0; i < s.length() && !trovato; i++) {
      if (s.charAt(i) == '.') {
        posPunto = i;
        trovato = true;
      }
    }

    for (int i = 0; i < posPunto; i++) {
      s1 += s.charAt(i);
    }

    int maxPuntoCifre = 3;

    for (int i = posPunto; i < s.length() && maxPuntoCifre != 0; i++) {
      s1 += s.charAt(i);
      maxPuntoCifre--;
    }

    return convertitoreDouble(s1);
  } // cifreDopoVirgola

  public static double convertitoreDouble(String s) {

    double i = -1;
    s.trim();
    try {
      i = (double) Double.parseDouble(s);
    } catch (NumberFormatException e) {
      return i;
    }

    return i;
  } // convertitoreDouble

  public static boolean deposita(double contoPortafoglio, double contoBancario, double temp) {

    if (temp > contoPortafoglio) {
      return false;
    }
    if (temp <= 0) {
      return false;
    }

    return true;
  } // deposita

  public static boolean preleva(double contoPortafoglio, double contoBancario, double temp) {

    if (temp > contoBancario) {
      return false;
    }
    if (temp <= 0) {
      return false;
    }

    return true;
  } // preleva

  public static double ricavo(int percentuale, int rischio, double investimento, double guadagno) {

    double ricavo = (double) percentuale / 100;
    double totale;

    if (percentuale >= rischio && percentuale <= 100) {
      totale = investimento + (investimento * ricavo * guadagno);
    } else {

      if (rischio >= 81 && rischio <= 84) {
        totale = investimento - (investimento * ricavo * guadagno);
      } else {
        totale = investimento - (investimento * ricavo);
      }
    }
    return totale;
  } // ricavo

  public static int trovaInvestimenti(int[] durataInvestimenti) {

    for (int i = 0; i < 20; i++) {
      if (durataInvestimenti[i] == 0) {
        return i;
      }
    }
    return -1;
  } // stampaInvestimenti

  public static void main(String[] args) {

    Scanner tastiera = new Scanner(System.in);

    // variabili per lo switch e do-while
    String temp;
    int scelta = 0;
    boolean ok;

    int mese = 1;
    int anno = 2024;

    double contoPortafoglio = 0;
    double contoBancario = 0;

    int numeroInvestimenti = 0;
    double[] Investimenti = new double[20];
    int[] durataInvestimenti = new int[20];

    for (int i = 0; i < 20; i++) {
      Investimenti[i] = -1;
      durataInvestimenti[i] = -1;
    }

    do {

      tempo(mese, anno);
      if (mese == 12) {
        mese = 0;
        anno++;
      }

      menu();

      do {

        ok = true;
        temp = tastiera.nextLine();
        temp = temp.trim();

        if (convertitore(temp) == -1) {
          ok = false;
        } else {
          scelta = convertitore(temp);
        }

        System.out.println("");

      } while (!ok);

      switch (scelta) {
        case 1:
          {
            String deposita = "";
            double tempDeposita = 0;

            System.out.print(
                "Quanti soldi vuoi depositare? ( in un singolo deposito il tetto massimo è di"
                    + " 3000$)");
            deposita = tastiera.nextLine();

            if (convertitoreDouble(deposita) > 0) {

              tempDeposita = convertitoreDouble(deposita);

              tempDeposita = cifreDopoVirgola(tempDeposita);

              if (tempDeposita > 0 && tempDeposita <= 3000) {

                if (deposita(contoPortafoglio, contoBancario, tempDeposita)) {

                  contoPortafoglio -= tempDeposita;
                  contoBancario += tempDeposita;

                  System.out.print("I soldi sono stati depositati.");

                } else {
                  System.out.print("I soldi non sono stati depositati.");
                }

              } else {
                System.out.print("Hai inserito una cifra sbagliata di soldi da depositare.");
              }

            } else {
              System.out.print("Hai inserito una cifra sbagliata di soldi da depositare.");
            }

            System.out.println("\n");
            break;
          } // case 1

        case 2:
          {
            String preleva = "";
            double tempPreleva = 0;

            if (contoBancario > 0) {

              System.out.print(
                  "Quanti soldi vuoi prelevare? ( in un singolo prelievo il tetto massimo è di"
                      + " 3000$)");
              preleva = tastiera.nextLine();

              if (convertitoreDouble(preleva) > 0) {

                tempPreleva = convertitoreDouble(preleva);

                tempPreleva = cifreDopoVirgola(tempPreleva);

                if (tempPreleva > 0 && tempPreleva <= 3000) {

                  if (preleva(contoPortafoglio, contoBancario, tempPreleva)) {

                    contoPortafoglio += tempPreleva;
                    contoBancario -= tempPreleva;

                    System.out.print("I soldi sono stati prelevati.");

                  } else {
                    System.out.print("I soldi non sono stati prelevati.");
                  }

                } else {
                  System.out.print("Hai inserito una cifra sbagliata di soldi da prelevare.");
                }

              } else {
                System.out.print("Hai inserito una cifra sbagliata di soldi da prelevare.");
              }

            } else {
              System.out.println("Il tuo conto e' negativo, non è possibile prelevare soldi!");
            }

            System.out.println("\n");
            break;
          } // case 2

        case 3:
          {
            boolean errore = false;

            if (numeroInvestimenti == 19) {
              errore = true;
            }

            if (contoBancario <= 0) {
              errore = true;
            }

            if (!errore) {
              int rischio = 0;

              // InvestimentoDurata
              int sceltaInvestimentoDurata = 0;
              String tempInvestimentoDurata;
              int durataMesi = 0;

              menuInvestimentiDurata();
              tempInvestimentoDurata = tastiera.nextLine();
              sceltaInvestimentoDurata = convertitore(tempInvestimentoDurata);
              System.out.println();

              switch (sceltaInvestimentoDurata) {
                case 1:
                  {
                    rischio -= 1;
                    durataMesi = 2;
                    System.out.println("\n");
                    break;
                  } // case 1-corto

                case 2:
                  {
                    rischio -= 2;
                    durataMesi = 5;
                    System.out.println("\n");
                    break;
                  } // case 2-medio

                case 3:
                  {
                    rischio -= 4;
                    durataMesi = 9;
                    System.out.println("\n");
                    break;
                  } // case 3-lungo

                default:
                  {
                    errore = true;
                    break;
                  } // default
              } // switchDurata

              if (!errore) {

                // InvestimentoRischio
                String tempInvestimentoRischio;
                int sceltaInvestimentoRischio = 0;

                menuInvestimentiRischio();

                tempInvestimentoRischio = tastiera.nextLine();
                sceltaInvestimentoRischio = convertitore(tempInvestimentoRischio);
                System.out.println();
                int percentuale = (int) (Math.random() * 100) + 1;
                double minimo = 99.99;
                double guadagno = 1;

                switch (sceltaInvestimentoRischio) {
                  case 1:
                    {
                      rischio += 85;
                      guadagno = 2.6;
                      System.out.println("\n");
                      break;
                    } // case 1-alta

                  case 2:
                    {
                      rischio += 46;
                      guadagno = 1.7;
                      System.out.println("\n");
                      break;
                    } // case 2-media

                  case 3:
                    {
                      rischio += 17;
                      guadagno = 1.2;
                      System.out.println("\n");
                      break;
                    } // case 3-bassa

                  default:
                    {
                      errore = true;
                      break;
                    } // default
                } // switchRischio

                if (!errore) {

                  System.out.print("Inserisci quanti soldi vuoi investire:");
                  String tempInvestimento = tastiera.next();
                  double investimento = 0;

                  if (convertitoreDouble(tempInvestimento) > 0
                      && convertitoreDouble(tempInvestimento) <= 3000) {
                    investimento = convertitoreDouble(tempInvestimento);
                  } else {
                    errore = true;
                  }

                  if (investimento <= minimo) {
                    errore = true;
                  }

                  if (investimento > contoBancario) {
                    errore = true;
                  }

                  if (!errore) {

                    contoBancario -= investimento;
                    boolean fatto = false;

                    for (int i = 0; i < 20 && !fatto; i++) {
                      if (durataInvestimenti[i] <= -1) {
                        durataInvestimenti[i] = durataMesi;
                        Investimenti[i] =
                            cifreDopoVirgola(ricavo(percentuale, rischio, investimento, guadagno));
                        fatto = true;
                        System.out.println("L'investimento e' riuscito.");
                      }
                    }

                    System.out.println("\n");
                    numeroInvestimenti++;

                  } else {
                    System.out.println(
                        "E' stato comesso un errore nell'iserimento dei soldi da investire.");
                  }

                } else {
                  System.out.println("E' stato comesso un errore nell'iserimento del rischio.");
                }

              } else {
                System.out.println("E' stato comesso un errore nell'iserimento della durata.");
              }

            } else {
              System.out.println(
                  "Hai raggiunto il numero massimo di investimenti possibili o non hai abbstanza"
                      + " soldi.");
            }

            break;
          } // case 3

        case 4:
          {
            contoBancario = cifreDopoVirgola(contoBancario);
            System.out.println("Il tuo conto bancario e': " + contoBancario + "$.");

            System.out.println("\n");
            break;
          } // case 4

        case 5:
          {
            contoPortafoglio = cifreDopoVirgola(contoPortafoglio);
            System.out.println("Nel tuo portafoglio hai: " + contoPortafoglio + "$.");

            System.out.println("\n");
            break;
          } // case 5

        case 6:
          {
            contoPortafoglio += 100;
            mese++;

            for (int i = 0; i < 20; i++) {
              durataInvestimenti[i]--;
            }

            int investimentiEliminati = 0;

            for (int i = 0; i < numeroInvestimenti; i++) {

              if (trovaInvestimenti(durataInvestimenti) != -1) {

                int n = trovaInvestimenti(durataInvestimenti);

                if (Investimenti[n] >= 0) {
                  System.out.println(
                      "Hai ricevuto : " + Investimenti[n] + "$ da un vecchio investimento.");
                } else {
                  System.out.println(
                      "Hai perso : "
                          + Investimenti[n]
                          + "$ dal tuo conto, a causa di un vecchio investimento.");
                }
                System.out.println("\n");

                contoBancario += Investimenti[n];
                Investimenti[n] = -1;
                durataInvestimenti[n] = -1;
                investimentiEliminati++;
              }
            }

            numeroInvestimenti -= investimentiEliminati;
            System.out.println();
            break;
          } // case 6

        default:
          {
            if (scelta != 0) {
              System.out.println("ERRORE!");
            }

            break;
          } // default
      } // switch

      System.out.println("\n");

    } while (scelta != 0);

    tastiera.close();
  } // main
} // progettoBanca

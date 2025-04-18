package main.java.Bank;

//import java.time.LocalDate;
import java.util.Vector;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class Banca {
	
	Utente utente;
	
	public Banca (/*String nome, */Utente utente) {
		//this.nome = nome;
		//utenti = new Vector <Utente> (5, 5);
		this.utente = utente;
	}

	@Override
	public String toString() {
		return "Banca [utente=" + utente + "]";
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	/*public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}*/

	/*public Vector<Utente> getUtenti() {
		return utenti;
	}

	public void setUtenti(Vector<Utente> utenti) {
		this.utenti = utenti;
	}*/

	
	/*public void depositWithdraw (double money) {
		
		if (DepositWithdraw.canDepositWithdraw (utente.getPortafoglio(), money)) {
			utente.getAccount().aumentaContoBancario(money);
			utente.togliSoldi(money);
		}
		
	}*/
	
	public void depositWithdraw (double money) {
		
		utente.getAccount().aumentaContoBancario(money);
		utente.togliSoldi(money);
		
	}
	
	public double withdrawal (double money) {
		
		return money *= -1;
		
	}
	

}

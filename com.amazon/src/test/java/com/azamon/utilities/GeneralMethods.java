package com.azamon.utilities;

import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;


import com.amazon.enums.messageType;

public class GeneralMethods {
	
	public GeneralMethods() {
		System.setProperty("jansi.passthrough", "true");

	}
	
	public void specialInstractions(messageType msType, String message){

		switch(msType){

		case FATAL :
			AnsiConsole.systemInstall();
			System.out.println(ansi().bold().bgRed().fg(WHITE).a("\n BELOW MANDATORY SETTINGS ARE MISSING - HENCE EXECUTION IS STOPPED \n ====================================").reset());
			System.out.println(ansi().fg(RED).a(message+ "\n").reset());
			AnsiConsole.systemUninstall();
			break;
		case ERROR:
			AnsiConsole.systemInstall();
			System.out.println(ansi().bold().fg(RED).a("\n BELOW SETTINGS ARE INCOMPLETE - PLEASE CORRECT AND RE-START EXECUTION  \n ====================================").reset());
			System.out.println(ansi().fg(RED).a(message+ "\n").reset());
			AnsiConsole.systemUninstall();
			break;

		case WARNING:
			AnsiConsole.systemInstall();
			System.out.println(ansi().bold().fg(YELLOW).a("\n BELOW SETTINGS ARE NOT PROVIDED - HENCE DEFAULT VALUES ARE CONSIDERED  \n ====================================").reset());
			System.out.println(ansi().fg(YELLOW).a(message+ "\n").reset());
			AnsiConsole.systemUninstall();
			break;

		case INFO:
			AnsiConsole.systemInstall();
			System.out.println(ansi().bold().fg(CYAN).a("\n BELOW SETTINGS ARE SUGGESTED FOR FASTER & SMOOTH EXECUTION \n ====================================").reset());
			System.out.println(ansi().fg(CYAN).a(message+ "\n").reset());
			AnsiConsole.systemUninstall();
			break;

		case DEBUG:
			AnsiConsole.systemInstall();
			System.out.println(ansi().fg(BLUE).a(message+ "\n").reset());
			AnsiConsole.systemUninstall();
			break;
		}



	}



}

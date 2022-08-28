package com.jspider.project.jdbc.Multiplayer;

import java.util.Scanner;

public class MultiPlayer1 {
	
	public static void MultiPlayerMenu() {
		SongOperations1 songOperations1 = new SongOperations1();
		Scanner sc = new Scanner(System.in);
		// songOperations.loadingSongs();
		System.out.println("!!  Welcome to Multiplayer !!\n How can I help you Sir/Madam ?\n" + 1 + ". Play Songs  " + 2
				+ ". Add/Remove Songs\n" + 3 + ". Edit Song   " + 4 + ". Exit");
		switch (sc.nextInt()) {
		case 1:
			System.out.println(1 + ". Choose Song         " + 2 + ". All Song\n" + 3 + ". Play Random Song    " + 4
					+ ". Go Back To Menu");

			switch (sc.nextInt()) {
			case 1:
				songOperations1.chooseSongToPlay();

				break;
			case 2:
				songOperations1.playAllSongs();
				break;

			case 3:
				songOperations1.playRandomSong();
				break;
			case 4:
				MultiPlayerMenu();
				break;

			default:
				System.out.println("Invalid Input we will go back to main menu");
				MultiPlayerMenu();
				break;
			}

			break;

		case 2:
			System.out.println(1 + ". Add Song     " + 2 + ". Remove Song");

			switch (sc.nextInt()) {
			case 1:
				songOperations1.addSongs();

				break;
			case 2:
				songOperations1.removeSong();
				break;

			default:
				System.out.println("Invalid Input we will go back to main menu");
				MultiPlayerMenu();
				break;
			}
			break;

		case 3:
			songOperations1.displayAllSongs();
			songOperations1.updateSong();
			break;

		case 4:
			sc.close();
			System.out.println("Thank you");
			break;

		default:
			break;
		}

	}

	public static void main(String[] args) {
			MultiPlayer1.MultiPlayerMenu();
	}

}

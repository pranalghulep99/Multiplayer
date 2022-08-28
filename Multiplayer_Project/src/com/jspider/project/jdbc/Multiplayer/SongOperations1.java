package com.jspider.project.jdbc.Multiplayer;

//package com.jspider.jdbc.multiplayer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class SongOperations1 {

	static Properties properties = new Properties();
	static FileReader fileReader;
	static Connection connection;
	static PreparedStatement preparedStatement;
	static ResultSet resultSet;
	static int resultInt;
	static String filePath = "C:\\Users\\prana\\eclipse-workspace\\Multiplayer_Project\\Resources\\multiplayer_db.properties";
	static String query;
	static int n;
	Scanner sc = new Scanner(System.in);

	public void chooseSongToPlay() {
		connect();
		int count = 0;
		query = "select * from multiplayersongs";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(
						"&&  " + resultSet.getInt(1) + "..." + resultSet.getString(2) + "..." + resultSet.getString(3));
				count++;
			}
			if (count == 0) {
				System.out
						.println("Currently No songs in the multiplayer, " + "Do you want to add? [1. Yes] / [2. No]");
				switch (sc.nextInt()) {
				case 1:
					addSongs();

					break;
				case 2:
					MultiPlayer1.MultiPlayerMenu();
					break;

				default:
					System.out.println("Invalid Entry..!!");
					MultiPlayer1.MultiPlayerMenu();
					break;
				}
			}
			if (count > 0) {
				System.out.print("Enter Song Id to Play : ");
				n = sc.nextInt();
				query = "select * from multiplayersongs where Id =" + n;
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					System.out.println("\nYour Selected >> " + resultSet.getInt(1) + ".." + resultSet.getString(2)
							+ "< from .." + resultSet.getString(3) + " is Playing On..\n");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println("Do You Want to play other songs to stay tuned..?\n[1. Yes] / [2. No]");
			switch (sc.nextInt()) {
			case 1:
				playRandomSong();
				break;
			case 2:
				System.out.println("Okay >> We are Move back to our Main Menu");
				MultiPlayer1.MultiPlayerMenu();
				break;
			default:
				System.out.println("Invalid Input .Lets go Back to Main Session.\n");
				MultiPlayer1.MultiPlayerMenu();
				break;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Connection not closed");
				}
			}
		}

	}

	public void connect() {
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("driverPath"));
			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addSongs() {
		System.out.println("How Many songs you wanna add, Sir/Madam?\n");
		n = sc.nextInt();
		int count = 0;
		for (int i = 1; i <= n; i++) {
			connect();
			query = "Insert into multiplayersongs values(?, ?, ?)";
			try {
				preparedStatement = connection.prepareStatement(query);
//				resultSet = preparedStatement.executeQuery();
				System.out.print("Song Id : ");
				preparedStatement.setInt(1, sc.nextInt());
				System.out.print("Song Name : ");
				preparedStatement.setString(2, sc.next());
				System.out.print("Song Movie : ");
				preparedStatement.setString(3, sc.next());
				resultInt = preparedStatement.executeUpdate();
				count++;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						System.out.println("Connection not closed");
					}
				}
			}
		}
		System.out.println(count + " songs successfully added to playlist\n" + "\nNow > Updated Playlist is ");
		displayAllSongs();
		System.out.println("Do You want to Play One By One? \n[1. Yes] / [2. No]");
		switch (sc.nextInt()) {
		case 1:
			System.out.println("Okay>> Lets Enjoy the Songs \n");
			playAllSongs();
			break;
		case 2:
			System.out.println("We Are Moving back to Main Menu");
			MultiPlayer1.MultiPlayerMenu();
			break;
		default:
			System.out.println("Invalid Input .Lets go Back to Main Session.\n");
			MultiPlayer1.MultiPlayerMenu();
			break;
		}

	}

	public void playAllSongs() {
		connect();
		System.out.println(">> Playing All Songs one by one");
		query = "select * from multiplayersongs";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Now>>.." + resultSet.getInt(1) + "..." + resultSet.getString(2) + "...song from "
						+ resultSet.getString(3) + " Movie is Playing on..");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Connection not closed");
				}
			}
		}
		System.out.println("Do You Wanna Play Again?..[1. Yes] / [2. No]\n");
		switch (sc.nextInt()) {
		case 1:
			playAllSongs();

			break;
		case 2:
			System.out.println("We Are Moving back to Main Menu");
			MultiPlayer1.MultiPlayerMenu();
			break;
		default:
			System.out.println("Invalid Input .Lets go Back to Main Session.\n");
			MultiPlayer1.MultiPlayerMenu();
			break;
		}

	}

	public void playRandomSong() {
		System.out.println("Playing Random Songs...");
		connect();
		query = "select * from multiplayersongs order by rand() limit 10";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Now..R>>" + resultSet.getInt(1) + "..." + resultSet.getString(2)
						+ "...song from Movie.." + resultSet.getString(3) + "..is Playing on");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Connection not closed");
				}
			}
		}
		System.out.println("\nDo you want to continue..??\n [1. Yes] / [2. No]");
		switch (sc.nextInt()) {
		case 1:
			playRandomSong();
			break;
		case 2:
			System.out.println("We Are Moving back to Main Menu\n");
			MultiPlayer1.MultiPlayerMenu();
			break;
		default:
			System.out.println("Invalid Input .Lets go Back to Main Session.\n");
			MultiPlayer1.MultiPlayerMenu();
			break;
		}

	}

	public void removeSong() {
		displayAllSongs();
		System.out.println("Enter song Id to delete");
		n = sc.nextInt();
		query = "Delete from multiplayersongs where Id =" + n;
		try {
			preparedStatement = connection.prepareStatement(query);
			resultInt = preparedStatement.executeUpdate();
			System.out.println(resultInt + " Song of Id no. " + n + " is deleted from the Playlist\n"
					+ "\nNow updated PlayList is..");
			displayAllSongs();
//			while (resultSet.next()) {
//				System.out.println(
//						"&&  " + resultSet.getInt(1) + "..." + resultSet.getString(2) + "..." + resultSet.getString(3));
//			}
			System.out.println("Now We Are Moving back to Main Menu..\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Connection not closed");
				}
			}
		}
		MultiPlayer1.MultiPlayerMenu();
	}

	public void displayAllSongs() {
		connect();
		query = "Select * from multiplayersongs";
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(
						"&&  " + resultSet.getInt(1) + "..." + resultSet.getString(2) + "..." + resultSet.getString(3));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateSong() {
		// displayAllSongs();
		int m;
		String l;
		System.out.println("\nWhat you Want to Update? \n" + "[1. Song Id] / [2. Song Name] / [3. Song Movie]\n");
		switch (sc.nextInt()) {
		case 1:
			displayAllSongs();
			System.out.print("Enter Song Id to Update :  ");
			n = sc.nextInt();
			System.out.print("Enter Updated Id :  ");
			m = sc.nextInt();
			query = "update multiplayersongs set Id =" + m + " where Id = " + n;
			try {
				preparedStatement = connection.prepareStatement(query);
				resultInt = preparedStatement.executeUpdate();
				System.out.println("\nSong Id Updated Successfully\n");
				System.out.println("Here Is Your  Recent Updated Song");
				query = "select * from multiplayersongs where Id =" + m;
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					System.out.println("\nUpdated >>  " + resultSet.getInt(1) + "..." + resultSet.getString(2) + "..."
							+ resultSet.getString(3));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						System.out.println("Connection not closed");
					}
				}
			}
			MultiPlayer1.MultiPlayerMenu();
			break;
		case 2:
			displayAllSongs();
			System.out.print("Enter Song Id to Update :  ");
			n = sc.nextInt();
			System.out.print("Enter Updated Song Name :  ");
			l = sc.next();
			query = "update multiplayersongs set SongName =? where Id = ?";
			try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, l);
				preparedStatement.setInt(2, n);
				resultInt = preparedStatement.executeUpdate();
				System.out.println("\nSong Name Updated Successfully\n");
				System.out.println("Here Is Your  Recent Updated Song");
				query = "select * from multiplayersongs where Id =" + n;
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					System.out.println("\nUpdated >>  " + resultSet.getInt(1) + "..." + resultSet.getString(2) + "..."
							+ resultSet.getString(3));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						System.out.println("Connection not closed");
					}
				}
			}
			MultiPlayer1.MultiPlayerMenu();
			break;
		case 3:
			displayAllSongs();
			System.out.print("Enter Song Id to Update :  ");
			n = sc.nextInt();
			System.out.print("Enter Updated Movie Name :  ");
			l = sc.next();
			query = "update multiplayersongs set MovieName = ? where Id = ?";
			try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, l);
				preparedStatement.setInt(2, n);
				resultInt = preparedStatement.executeUpdate();
				System.out.println("\nSong Name Updated Successfully\n");
				System.out.println("Here Is Your  Recent Updated Song");
				query = "select * from multiplayersongs where Id =" + n;
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					System.out.println("\nUpdated >>  " + resultSet.getInt(1) + "..." + resultSet.getString(2) + "..."
							+ resultSet.getString(3));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						System.out.println("Connection not closed");
					}
				}
			}
			MultiPlayer1.MultiPlayerMenu();
			break;
		default:
			System.out.println("Invalid Input Id\n");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("We Are Moving Back To Main Menu\n");
			MultiPlayer1.MultiPlayerMenu();
			break;
		}

	}

}

package Farkle;

import javax.swing.*;
import java.sql.*;

public class FarkleDB {
    /**
     * Ben Wadsworth
     * 5/15/2019
     * This class handles all database interaction.
     */

    // Declare variables
    private static final String URL = "jdbc:sqlite:planner.sqlite";
    private static final String SAVED_GAME_TABLE = "saved_game";
    private static final String PLAYER_COL = "player";
    private static final String SCORE_COL = "score";
    private static final String TURN_COL = "turn";


    FarkleDB() { createTable(); }

    // Create the table if it does not exist
    private void createTable() {

        final String createTableTemplate = "CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY, %s TEXT, %s BOOLEAN)";

        String createSQL = String.format(createTableTemplate, SAVED_GAME_TABLE, PLAYER_COL, SCORE_COL, TURN_COL);

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement createStatement = conn.prepareStatement(createSQL)) {

            createStatement.execute();
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    // Save the Player data to the database
    public void saveGame(Player[] players){

        dropTable();
        createTable();

        final String savePlayerInfo = "INSERT INTO saved_game VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement saveStatement = conn.prepareStatement(savePlayerInfo)){

            saveStatement.setString(1, SAVED_GAME_TABLE);

            for (Player player : players){
                String name = player.getName();
                int score = player.getScore();
                boolean turn = player.isTurn();

                saveStatement.setString(1, name);
                saveStatement.setInt(2, score);
                saveStatement.setBoolean(3, turn);

                saveStatement.execute();
            }

            System.out.println("The game has been saved.");

        } catch (SQLException ex){
            System.out.println(ex.getErrorCode() + ": " + ex.getMessage());
        }
    }

    // Load the saved game from the database if there is a saved game
    public Player[] loadGame(JLabel[] labels, boolean load) {

        if (!load){
            dropTable();
            createTable();
        }

        final String loadPlayerInfo = "SELECT * FROM %s";

        String loadSQL = String.format(loadPlayerInfo, SAVED_GAME_TABLE);

        Player[] players = new Player[2]; // Create 2 new Player objects
        int playerNum = 0;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement loadStatement = conn.prepareStatement(loadSQL)){

            ResultSet resultSet = loadStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {

                if (load) // IF they want to load but the table is empty shoe message
                    JOptionPane.showMessageDialog(null, "There was no saved game.");

                players[0] = Player.addPlayer("Enter name for Player 1:", true, labels[0]);
                players[1] = Player.addPlayer("Enter name for Player 2:", false, labels[1]);
                return players;
            }
            else
            {
                while (resultSet.next()){
                    String name = resultSet.getString(PLAYER_COL);
                    int score = resultSet.getInt(SCORE_COL);
                    boolean turn = resultSet.getBoolean(TURN_COL);
                    players[playerNum] = new Player(name, score, turn, labels[playerNum]);
                    playerNum++;
                }
                return players;
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred loading the saved game.");
            return players;
        }
    }

    // Drop the table
    public void dropTable() {

        final String dropTable = "DROP TABLE IF EXISTS %s";

        String dropSQL = String.format(dropTable, SAVED_GAME_TABLE);

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement dropStatement = conn.prepareStatement(dropSQL)) {

            dropStatement.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

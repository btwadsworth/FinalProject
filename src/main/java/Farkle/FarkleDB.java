package Farkle;

import javax.swing.*;
import java.sql.*;

public class FarkleDB {

    private static final String URL = "jdbc:sqlite:planner.sqlite";

    private static final String SAVED_GAME = "saved_game";
    private static final String PLAYER = "player";
    private static final String SCORE = "score";
    private static final String TURN = "turn";


    FarkleDB() { createTable(); }

    private void createTable() {

        final String createTableTemplate = "CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY, %s TEXT, %s BOOLEAN)";

        String createSQL = String.format(createTableTemplate, SAVED_GAME, PLAYER, SCORE, TURN);

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement createStatement = conn.prepareStatement(createSQL)) {

            createStatement.execute();
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public void saveGame(Player[] players){

        dropTable();
        createTable();

        final String savePlayerInfo = "INSERT INTO saved_game VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement saveStatement = conn.prepareStatement(savePlayerInfo)){

            saveStatement.setString(1, SAVED_GAME);

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

    public Player[] loadGame(JLabel[] labels, boolean load) {

        if (!load){
            dropTable();
            createTable();
        }

        final String loadPlayerInfo = "SELECT * FROM %s";

        String loadSQL = String.format(loadPlayerInfo, SAVED_GAME);

        Player[] players = new Player[2];
        int playerNum = 0;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement loadStatement = conn.prepareStatement(loadSQL)){

            ResultSet resultSet = loadStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                players[0] = Player.addPlayer("Enter name for Player 1:", true, labels[0]);
                players[1] = Player.addPlayer("Enter name for Player 2:", false, labels[1]);
                return players;
            }
            else
            {
                while (resultSet.next()){
                    String name = resultSet.getString(PLAYER);
                    int score = resultSet.getInt(SCORE);
                    boolean turn = resultSet.getBoolean(TURN);
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

    private void dropTable() {

        final String dropTable = "DROP TABLE IF EXISTS %s";

        String dropSQL = String.format(dropTable, SAVED_GAME);

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement dropStatement = conn.prepareStatement(dropSQL)) {

            dropStatement.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

package com.example.tictactoe.dao;

import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLScoreDAO implements ScoreDAO {
    private static final String TABLE_NAME = "score";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PLAYER_NAME = "player_name";
    private static final String COLUMN_MODE = "mode";
    private static final String COLUMN_DIFFICULTY = "difficulty";
    private static final String COLUMN_WINS = "wins";
    private static final String COLUMN_LOSSES = "losses";
    private static final String COLUMN_TIES = "ties";
    private final DataSource dataSource;

    public MySQLScoreDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Score> getAllScores() throws DAOException {
        String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            List<Score> scores = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(COLUMN_ID);
                String playerName = rs.getString(COLUMN_PLAYER_NAME);
                Mode mode = Mode.fromString(rs.getString(COLUMN_MODE));
                Difficulty difficulty = Difficulty.fromString(rs.getString(COLUMN_DIFFICULTY));
                int wins = rs.getInt(COLUMN_WINS);
                int losses = rs.getInt(COLUMN_LOSSES);
                int ties = rs.getInt(COLUMN_TIES);
                scores.add(new Score(id, playerName, mode, difficulty, wins, losses, ties));
            }
            return scores;
        } catch (SQLException e) {
            throw new DAOException("Error getting all scores", e);
        }
    }

    @Override
    public List<Score> getAllScores(Mode mode, Difficulty difficulty) throws DAOException {
        String query = "SELECT * FROM %s WHERE %s = ? AND %s = ?".formatted(TABLE_NAME, COLUMN_MODE, COLUMN_DIFFICULTY);
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mode.toString());
            stmt.setString(2, difficulty.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                List<Score> scores = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt(COLUMN_ID);
                    String playerName = rs.getString(COLUMN_PLAYER_NAME);
                    int wins = rs.getInt(COLUMN_WINS);
                    int losses = rs.getInt(COLUMN_LOSSES);
                    int ties = rs.getInt(COLUMN_TIES);
                    scores.add(new Score(id, playerName, mode, difficulty, wins, losses, ties));
                }
                return scores;
            }
        } catch (SQLException e) {
            throw new DAOException("Error getting all scores", e);
        }
    }

    @Override
    public Optional<Score> getScore(String playerName, Mode mode, Difficulty difficulty) throws DAOException {
        String query = "SELECT * FROM %s WHERE %s = ? AND %s = ? AND %s = ?".formatted(TABLE_NAME, COLUMN_PLAYER_NAME, COLUMN_MODE, COLUMN_DIFFICULTY);
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerName);
            stmt.setString(2, mode.toString());
            stmt.setString(3, difficulty.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(COLUMN_ID);
                    int wins = rs.getInt(COLUMN_WINS);
                    int losses = rs.getInt(COLUMN_LOSSES);
                    int ties = rs.getInt(COLUMN_TIES);
                    return Optional.of(new Score(id, playerName, mode, difficulty, wins, losses, ties));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error getting score", e);
        }
        return Optional.empty();
    }

    @Override
    public void addScore(String playerName, Mode mode, Difficulty difficulty, int wins, int losses, int ties) throws DAOException {
        String query = "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)".formatted(TABLE_NAME, COLUMN_PLAYER_NAME, COLUMN_MODE, COLUMN_DIFFICULTY, COLUMN_WINS, COLUMN_LOSSES, COLUMN_TIES);
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerName);
            stmt.setString(2, mode.toString());
            stmt.setString(3, difficulty.toString());
            stmt.setInt(4, wins);
            stmt.setInt(5, losses);
            stmt.setInt(6, ties);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding score", e);
        }
    }


    @Override
    public void updateScore(Score score) throws DAOException {
        String query = "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?".formatted(TABLE_NAME, COLUMN_WINS, COLUMN_LOSSES, COLUMN_TIES, COLUMN_ID);
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, score.getWins());
            stmt.setInt(2, score.getLosses());
            stmt.setInt(3, score.getTies());
            stmt.setInt(4, score.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating score", e);
        }
    }

    @Override
    public void deleteScore(int id) throws DAOException {
        String query = "DELETE FROM %s WHERE %s = ?".formatted(TABLE_NAME, COLUMN_ID);
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting score", e);
        }
    }

    @Override
    public void deleteAllScores() throws DAOException {
        String query = "TRUNCATE TABLE " + TABLE_NAME;
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting all scores", e);
        }
    }
}
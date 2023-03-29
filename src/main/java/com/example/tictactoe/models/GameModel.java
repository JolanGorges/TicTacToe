package com.example.tictactoe.models;

import com.example.tictactoe.dao.Score;
import com.example.tictactoe.dao.ScoreDAO;
import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;
import com.example.tictactoe.enums.Player;
import com.example.tictactoe.enums.State;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Optional;

public class GameModel {
    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.ONE_PLAYER);
    private final StringProperty playerOneName = new SimpleStringProperty("Player 1");
    private final StringProperty playerTwoName = new SimpleStringProperty("Player 2");
    private final ObjectProperty<Difficulty> difficulty = new SimpleObjectProperty<>(Difficulty.EASY);
    private final ScoreDAO scoreDAO;
    private TicTacToeGame ticTacToeGame;

    public GameModel(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    public ObjectProperty<Mode> modeProperty() {
        return mode;
    }

    public StringProperty playerOneNameProperty() {
        return playerOneName;
    }

    public StringProperty playerTwoNameProperty() {
        return playerTwoName;
    }

    public ObjectProperty<Difficulty> difficultyProperty() {
        return difficulty;
    }

    public Mode getMode() {
        return mode.get();
    }

    public void setMode(Mode mode) {
        this.mode.set(mode);
    }

    public String getPlayerOneName() {
        return playerOneName.get();
    }

    public String getPlayerTwoName() {
        return playerTwoName.get();
    }

    public Difficulty getDifficulty() {
        return difficulty.get();
    }

    public void newGame() {
        if (mode.get() == Mode.ONE_PLAYER) playerTwoName.set("Computer");
        ticTacToeGame = new TicTacToeGame(mode.get(), difficulty.get());
    }

    public void makeMove(int row, int col) {
        ticTacToeGame.makeMove(row, col);
    }

    public State[][] getBoard() {
        return ticTacToeGame.getBoard();
    }

    public Optional<Player> checkForWinner() {
        return ticTacToeGame.checkForWinner();
    }

    public boolean hasMovesLeft() {
        return ticTacToeGame.hasMovesLeft();
    }

    public void incrementWinsAndLosses(String winner, String loser) {
        incrementWins(winner);
        incrementLosses(loser);
    }

    private void incrementWins(String playerName) {
        try {
            Optional<Score> score = scoreDAO.getScore(playerName, getMode(), getDifficulty());
            if (score.isEmpty()) {
                scoreDAO.addScore(playerName, getMode(), getDifficulty(), 1, 0, 0);
            } else {
                score.get().setWins(score.get().getWins() + 1);
                scoreDAO.updateScore(score.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void incrementLosses(String playerName) {
        try {
            Optional<Score> score = scoreDAO.getScore(playerName, getMode(), getDifficulty());
            if (score.isEmpty()) {
                scoreDAO.addScore(playerName, getMode(), getDifficulty(), 0, 1, 0);
            } else {
                score.get().setLosses(score.get().getLosses() + 1);
                scoreDAO.updateScore(score.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void incrementTies() {
        try {
            String[] playerNames = {getPlayerOneName(), getPlayerTwoName()};
            for (String playerName : playerNames) {
                Optional<Score> score = scoreDAO.getScore(playerName, getMode(), getDifficulty());
                if (score.isEmpty()) {
                    scoreDAO.addScore(playerName, getMode(), getDifficulty(), 0, 0, 1);
                } else {
                    score.get().setTies(score.get().getTies() + 1);
                    scoreDAO.updateScore(score.get());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

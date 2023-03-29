package com.example.tictactoe.dao;

import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;

import java.util.List;
import java.util.Optional;

public interface ScoreDAO {

    List<Score> getAllScores() throws DAOException;

    List<Score> getAllScores(Mode mode, Difficulty difficulty) throws DAOException;

    Optional<Score> getScore(String playerName, Mode mode, Difficulty difficulty) throws DAOException;

    void addScore(String playerName, Mode mode, Difficulty difficulty, int wins, int losses, int ties) throws DAOException;

    void updateScore(Score score) throws DAOException;

    void deleteScore(int id) throws DAOException;

    void deleteAllScores() throws DAOException;
}

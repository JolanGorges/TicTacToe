package com.example.tictactoe.models;

import com.example.tictactoe.dao.DAOException;
import com.example.tictactoe.dao.Score;
import com.example.tictactoe.dao.ScoreDAO;
import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;

import java.util.List;

public class ScoreboardModel {
    private final ScoreDAO scoreDAO;

    public ScoreboardModel(ScoreDAO scoreDAO) {
        this.scoreDAO = scoreDAO;
    }

    public List<Score> getScores() throws DAOException {
        return scoreDAO.getAllScores();
    }

    public List<Score> getScores(Mode mode, Difficulty difficulty) throws DAOException {
        return scoreDAO.getAllScores(mode, difficulty);
    }
}

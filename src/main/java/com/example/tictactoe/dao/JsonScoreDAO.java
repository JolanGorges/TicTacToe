package com.example.tictactoe.dao;

import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonScoreDAO implements ScoreDAO {

    private static final String FILE_NAME = "scores.json";
    private final ObjectMapper mapper = new ObjectMapper();

    private List<Score> readScores() throws DAOException {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                return mapper.readValue(file, new TypeReference<>() {
                });
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new DAOException("Error reading scores from file", e);
        }
    }

    private void writeScores(List<Score> scores) throws DAOException {
        try {
            mapper.writeValue(new File(FILE_NAME), scores);
        } catch (IOException e) {
            throw new DAOException("Error writing scores to file", e);
        }
    }

    @Override
    public List<Score> getAllScores() throws DAOException {
        return readScores();
    }

    @Override
    public List<Score> getAllScores(Mode mode, Difficulty difficulty) throws DAOException {
        List<Score> scores = readScores();
        List<Score> filteredScores = new ArrayList<>();
        for (Score score : scores) {
            if (score.getMode() == mode && score.getDifficulty() == difficulty) {
                filteredScores.add(score);
            }
        }
        return filteredScores;
    }

    @Override
    public Optional<Score> getScore(String playerName, Mode mode, Difficulty difficulty) throws DAOException {
        List<Score> scores = readScores();
        for (Score score : scores) {
            if (score.getPlayerName().equals(playerName) && score.getMode() == mode && score.getDifficulty() == difficulty) {
                return Optional.of(score);
            }
        }
        return Optional.empty();
    }

    @Override
    public void addScore(String playerName, Mode mode, Difficulty difficulty, int wins, int losses, int ties) throws DAOException {
        List<Score> scores = readScores();
        int newId = scores.isEmpty() ? 1 : scores.get(scores.size() - 1).getId() + 1;
        Score newScore = new Score(newId, playerName, mode, difficulty, wins, losses, ties);
        scores.add(newScore);
        writeScores(scores);
    }

    @Override
    public void updateScore(Score score) throws DAOException {
        List<Score> scores = readScores();
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).getId() == score.getId()) {
                scores.set(i, score);
                writeScores(scores);
                return;
            }
        }
        throw new DAOException("Score with ID " + score.getId() + " not found");
    }

    @Override
    public void deleteScore(int id) throws DAOException {
        List<Score> scores = readScores();
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).getId() == id) {
                scores.remove(i);
                writeScores(scores);
                return;
            }
        }
        throw new DAOException("Score with ID " + id + " not found");
    }

    @Override
    public void deleteAllScores() throws DAOException {
        List<Score> scores = new ArrayList<>();
        writeScores(scores);
    }
}
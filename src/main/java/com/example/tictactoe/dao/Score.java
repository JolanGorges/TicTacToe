package com.example.tictactoe.dao;

import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;

public class Score {
    private int id;
    private String playerName;
    private Mode mode;
    private Difficulty difficulty;
    private int wins;
    private int losses;
    private int ties;

    public Score() {
    }

    public Score(int id, String playerName, Mode mode, Difficulty difficulty, int wins, int losses, int ties) {
        this.id = id;
        this.playerName = playerName;
        this.mode = mode;
        this.difficulty = difficulty;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }
}
package com.example.tictactoe.models;

import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;

public class ScoreBoard {
    private int name;
    private int wins;
    private int losses;
    private int ties;
    private Mode mode;
    private Difficulty difficulty;

    public ScoreBoard(int name, int wins, int losses, int ties) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.mode = Mode.TWO_PLAYER;
        this.difficulty = null;
    }

    public ScoreBoard(int name, int wins, int losses, int ties, Difficulty difficulty) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.mode = Mode.ONE_PLAYER;
        this.difficulty = difficulty;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
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
}

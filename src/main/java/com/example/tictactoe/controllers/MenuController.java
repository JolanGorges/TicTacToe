package com.example.tictactoe.controllers;

import com.example.tictactoe.App;
import com.example.tictactoe.dao.ScoreDAO;
import com.example.tictactoe.dao.ScoreDAOFactory;
import com.example.tictactoe.enums.Mode;
import com.example.tictactoe.models.GameModel;
import com.example.tictactoe.models.ScoreboardModel;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    void onePlayer() {
        switchToGameConfig(Mode.ONE_PLAYER);
    }

    @FXML
    void twoPlayer() {
        switchToGameConfig(Mode.TWO_PLAYER);
    }

    @FXML
    void scoreboard() {
        ScoreDAO scoreDAO = ScoreDAOFactory.getScoreDAO();
        ScoreboardModel scoreboardModel = new ScoreboardModel(scoreDAO);
        App.loadView("views/ScoreboardView.fxml", scoreboardModel);
    }

    void switchToGameConfig(Mode mode) {
        ScoreDAO scoreDAO = ScoreDAOFactory.getScoreDAO();
        GameModel gameModel = new GameModel(scoreDAO);
        gameModel.setMode(mode);
        App.loadView("views/GameConfigView.fxml", gameModel);
    }
}

package com.example.tictactoe.controllers;

import com.example.tictactoe.App;
import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;
import com.example.tictactoe.models.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameConfigController {

    private final GameModel gameModel;
    @FXML
    private Label playerOneLabel;
    @FXML
    private ComboBox<Difficulty> difficultyCbo;
    @FXML
    private HBox difficultyHBox;
    @FXML
    private Button playBtn;
    @FXML
    private TextField playerOneTextField;
    @FXML
    private HBox playerTwoHBox;
    @FXML
    private TextField playerTwoTextField;
    @FXML
    private VBox vbox;

    public GameConfigController(GameModel gameModel) {
        this.gameModel = gameModel;

    }

    @FXML
    void initialize() {
        this.gameModel.difficultyProperty().bindBidirectional(difficultyCbo.valueProperty());
        playerOneTextField.textProperty().bindBidirectional(this.gameModel.playerOneNameProperty());
        playerTwoTextField.textProperty().bindBidirectional(this.gameModel.playerTwoNameProperty());
        if (gameModel.getMode() == Mode.ONE_PLAYER) {
            vbox.getChildren().remove(playerTwoHBox);
            playerOneLabel.setText("Your name:");
        } else {
            vbox.getChildren().remove(difficultyHBox);
        }
    }

    @FXML
    void play() {
        gameModel.newGame();
        App.loadView("views/GameboardView.fxml", gameModel);
    }

    @FXML
    void playerOneTextChanged() {
        if (gameModel.getMode() == Mode.ONE_PLAYER) {
            playBtn.setDisable(playerOneTextField.getText().isEmpty());
        } else {
            playBtn.setDisable(playerOneTextField.getText().isEmpty() || playerTwoTextField.getText().isEmpty());
        }
    }

    @FXML
    void playerTwoTextChanged() {
        playBtn.setDisable(playerOneTextField.getText().isEmpty() || playerTwoTextField.getText().isEmpty());
    }
}

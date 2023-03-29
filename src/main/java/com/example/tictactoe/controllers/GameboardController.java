package com.example.tictactoe.controllers;

import com.example.tictactoe.App;
import com.example.tictactoe.enums.Mode;
import com.example.tictactoe.enums.Player;
import com.example.tictactoe.enums.State;
import com.example.tictactoe.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public class GameboardController {

    private final GameModel gameModel;
    @FXML
    private Label difficultyLbl;
    @FXML
    private GridPane gameBoardGp;
    @FXML
    private Label modeLbl;
    @FXML
    private Label resultLbl;
    private Pane[][] gameBoardPanes;

    public GameboardController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void initialize() {
        // Set the mode and difficulty labels
        modeLbl.textProperty().bind(gameModel.modeProperty().asString());
        difficultyLbl.textProperty().bind(gameModel.difficultyProperty().asString());
        // Bind the GridPane width and height to the minimum between the parent width and height to keep the GridPane square
        StackPane parent = (StackPane) gameBoardGp.getParent();
        gameBoardGp.prefHeightProperty().bind(Bindings.min(parent.widthProperty(), parent.heightProperty()));
        gameBoardGp.prefWidthProperty().bind(Bindings.min(parent.widthProperty(), parent.heightProperty()));

        // Create a 2D array of Panes to easily access them when updating the board
        gameBoardPanes = new Pane[3][3];
        for (Node node : gameBoardGp.getChildren()) {
            if (!(node instanceof Pane)) continue;
            int colIndex = GridPane.getColumnIndex(node);
            int rowIndex = GridPane.getRowIndex(node);
            gameBoardPanes[colIndex][rowIndex] = (Pane) node;
        }
    }

    @FXML
    void onGameBoardPaneAction(MouseEvent event) {
        // Get the Pane that was clicked and the column and row indices of the Pane
        Pane pane = (Pane) event.getSource();
        int colIndex = GridPane.getColumnIndex(pane);
        int rowIndex = GridPane.getRowIndex(pane);
        // Try to make a move at the specified column and row indices and update the board
        gameModel.makeMove(colIndex, rowIndex);
        updateBoard();
    }

    void updateBoard() {
        State[][] board = gameModel.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Pane pane = gameBoardPanes[i][j];
                ObservableList<Node> nodes = pane.getChildren();
                if (!nodes.isEmpty()) continue;
                if (board[i][j] == State.X) addX(pane);
                else if (board[i][j] == State.O) addO(pane);
            }
        }
        Optional<Player> winner = gameModel.checkForWinner();
        if (winner.isPresent()) {
            gameBoardGp.setDisable(true);
            if (winner.get() == Player.PLAYER_ONE) {
                if (gameModel.getMode() == Mode.ONE_PLAYER) resultLbl.setText("You win!");
                else resultLbl.setText(gameModel.getPlayerOneName() + " wins!");
                gameModel.incrementWinsAndLosses(gameModel.getPlayerOneName(), gameModel.getPlayerTwoName());
            } else {
                if (gameModel.getMode() == Mode.ONE_PLAYER) resultLbl.setText("You lose!");
                else resultLbl.setText(gameModel.getPlayerTwoName() + " wins!");
                gameModel.incrementWinsAndLosses(gameModel.getPlayerTwoName(), gameModel.getPlayerOneName());
            }
            gameBoardGp.setOpacity(0.3);
            resultLbl.setVisible(true);
        } else if (!gameModel.hasMovesLeft()) {
            gameBoardGp.setOpacity(0.3);
            gameBoardGp.setDisable(true);
            resultLbl.setText("Tie!");
            resultLbl.setVisible(true);
            gameModel.incrementTies();
        }

    }

    void addX(Pane pane) {
        // Add two rectangles to the Pane to create an X
        for (int i = 0; i < 2; i++) {
            Rectangle rect = new Rectangle();
            rect.setFill(Color.BLUE);
            rect.widthProperty().bind(pane.widthProperty().multiply(0.1));
            rect.heightProperty().bind(pane.heightProperty().multiply(0.8));
            rect.xProperty().bind(pane.widthProperty().subtract(rect.widthProperty()).divide(2));
            rect.yProperty().bind(pane.heightProperty().subtract(rect.heightProperty()).divide(2));
            rect.setRotate(i == 0 ? 45 : -45);
            pane.getChildren().add(rect);
        }
    }

    void addO(Pane pane) {
        // Add a circle to the Pane to create an O
        Circle circle = new Circle();
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.RED);
        circle.strokeWidthProperty().bind(pane.widthProperty().multiply(0.1));
        circle.radiusProperty().bind(pane.widthProperty().multiply(0.270));
        circle.centerXProperty().bind(pane.widthProperty().divide(2));
        circle.centerYProperty().bind(pane.heightProperty().divide(2));
        pane.getChildren().add(circle);
    }

    @FXML
    void onRestartBtnAction() {
        gameBoardGp.setOpacity(1);
        // Enable the board and hide the result label
        gameBoardGp.setDisable(false);
        resultLbl.setVisible(false);
        // Clear the board and create a new ticTacToeGame
        gameModel.newGame();
        for (Pane[] panes : gameBoardPanes) {
            for (Pane pane : panes) {
                pane.getChildren().clear();
            }
        }
    }

    @FXML
    void onReturnToMenuBtnAction() {
        App.loadView("views/MenuView.fxml");
    }
}

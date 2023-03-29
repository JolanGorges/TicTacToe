package com.example.tictactoe.controllers;

import com.example.tictactoe.App;
import com.example.tictactoe.dao.DAOException;
import com.example.tictactoe.dao.Score;
import com.example.tictactoe.dao.ScoreDAOFactory;
import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;
import com.example.tictactoe.models.ScoreboardModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScoreboardController {
    private final ObservableList<Score> oneVSOneScores = FXCollections.observableArrayList();
    private final ObservableList<Score> oneVSAIEasyScores = FXCollections.observableArrayList();
    private final ObservableList<Score> oneVSAIMediumScores = FXCollections.observableArrayList();
    private final ObservableList<Score> oneVSAIHardScores = FXCollections.observableArrayList();
    public ScoreboardModel scoreboardModel;
    @FXML
    private TableView<Score> oneVSAIEasyTblView;
    @FXML
    private TableView<Score> oneVSAIHardTblView;
    @FXML
    private TableView<Score> oneVSAIMediumTblView;
    @FXML
    private TableView<Score> oneVSOneTblView;
    @FXML
    private TableColumn<Score, String> oneVSOnePlayerNameColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSOneWinsColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSOneLossesColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSOneTiesColumn;
    @FXML
    private TableColumn<Score, String> oneVSAIEasyPlayerNameColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIEasyWinsColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIEasyLossesColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIEasyTiesColumn;
    @FXML
    private TableColumn<Score, String> oneVSAIMediumPlayerNameColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIMediumWinsColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIMediumLossesColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIMediumTiesColumn;
    @FXML
    private TableColumn<Score, String> oneVSAIHardPlayerNameColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIHardWinsColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIHardLossesColumn;
    @FXML
    private TableColumn<Score, Integer> oneVSAIHardTiesColumn;

    @FXML
    private RadioButton jsonCbx;

    @FXML
    private RadioButton mySQLCbx;

    public ScoreboardController(ScoreboardModel scoreboardModel) {
        this.scoreboardModel = scoreboardModel;
    }

    @FXML
    public void initialize() throws DAOException {
        mySQLCbx.setSelected(ScoreDAOFactory.getUseMySQL());
        jsonCbx.setSelected(!ScoreDAOFactory.getUseMySQL());
        setupTableView(oneVSOneTblView, oneVSOnePlayerNameColumn, oneVSOneWinsColumn, oneVSOneLossesColumn, oneVSOneTiesColumn, Mode.TWO_PLAYER, Difficulty.EASY);
        setupTableView(oneVSAIEasyTblView, oneVSAIEasyPlayerNameColumn, oneVSAIEasyWinsColumn, oneVSAIEasyLossesColumn, oneVSAIEasyTiesColumn, Mode.ONE_PLAYER, Difficulty.EASY);
        setupTableView(oneVSAIMediumTblView, oneVSAIMediumPlayerNameColumn, oneVSAIMediumWinsColumn, oneVSAIMediumLossesColumn, oneVSAIMediumTiesColumn, Mode.ONE_PLAYER, Difficulty.MEDIUM);
        setupTableView(oneVSAIHardTblView, oneVSAIHardPlayerNameColumn, oneVSAIHardWinsColumn, oneVSAIHardLossesColumn, oneVSAIHardTiesColumn, Mode.ONE_PLAYER, Difficulty.HARD);
    }

    private void setupTableView(TableView<Score> tableView, TableColumn<Score, String> playerNameColumn,
                                TableColumn<Score, Integer> winsColumn, TableColumn<Score, Integer> lossesColumn,
                                TableColumn<Score, Integer> tiesColumn, Mode mode, Difficulty difficulty) throws DAOException {

        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));
        tiesColumn.setCellValueFactory(new PropertyValueFactory<>("ties"));

        tableView.setItems(FXCollections.observableArrayList(scoreboardModel.getScores(mode, difficulty)));
    }

    @FXML
    void onCheckboxAction() {
        ScoreDAOFactory.setUseMySQL(mySQLCbx.isSelected());
    }

    @FXML
    void onReturnToMenuBtnAction() {
        App.loadView("views/MenuView.fxml");
    }
}

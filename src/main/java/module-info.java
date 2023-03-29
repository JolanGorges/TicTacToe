module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires com.fasterxml.jackson.databind;
    requires java.prefs;


    opens com.example.tictactoe to javafx.fxml;
    opens com.example.tictactoe.controllers to javafx.fxml;
    opens com.example.tictactoe.models to javafx.fxml;
    opens com.example.tictactoe.dao to com.fasterxml.jackson.databind;
    exports com.example.tictactoe;
    exports com.example.tictactoe.controllers;
    exports com.example.tictactoe.models;
    exports com.example.tictactoe.enums;
    exports com.example.tictactoe.dao;
    opens com.example.tictactoe.enums to javafx.fxml;
}
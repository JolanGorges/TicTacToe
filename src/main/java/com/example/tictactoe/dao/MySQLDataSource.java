package com.example.tictactoe.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLDataSource {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tictactoe";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static HikariDataSource dataSource;

    private MySQLDataSource() {
    }

    public static HikariDataSource getDataSource() throws DAOException {
        if (dataSource == null) initializeDataSource();
        return dataSource;
    }

    public static void initializeDataSource() throws DAOException {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(JDBC_URL);
            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new DAOException("Cannot connect to database", e);
        }
    }

    public static void close() {
        if (dataSource != null)
            dataSource.close();
    }
}
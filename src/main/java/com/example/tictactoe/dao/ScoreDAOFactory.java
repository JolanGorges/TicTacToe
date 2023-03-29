package com.example.tictactoe.dao;

import java.util.prefs.Preferences;

public class ScoreDAOFactory {
    private static boolean useMySQL = false;

    static {
        Preferences prefs = Preferences.userNodeForPackage(ScoreDAOFactory.class);
        boolean retrievedBooleanValue = prefs.getBoolean("savingMethod", false);
    }

    private ScoreDAOFactory() {
    }


    public static boolean getUseMySQL() {
        return useMySQL;
    }

    public static void setUseMySQL(boolean mySQL) {
        useMySQL = mySQL;
        Preferences prefs = Preferences.userNodeForPackage(ScoreDAOFactory.class);
        prefs.putBoolean("savingMethod", mySQL);
    }

    public static ScoreDAO getScoreDAO() {
        if (useMySQL) {
            try {
                // Try to create a data source for the MySQLScoreDAO
                return new MySQLScoreDAO(MySQLDataSource.getDataSource());
            } catch (DAOException e) {
                // If an exception occurs, create a JsonScoreDao instead
                e.printStackTrace();
                return new JsonScoreDAO();
            }
        } else
            return new JsonScoreDAO();
    }
}

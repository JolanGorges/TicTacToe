package com.example.tictactoe.enums;

public enum Difficulty {
    EASY("Easy"), MEDIUM("Medium"), HARD("Hard");

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    public static Difficulty fromString(String text) {
        for (Difficulty b : Difficulty.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

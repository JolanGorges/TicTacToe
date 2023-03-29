package com.example.tictactoe.enums;

public enum Mode {
    ONE_PLAYER("1 VS AI"), TWO_PLAYER("1 VS 1");

    private final String displayName;

    Mode(String displayName) {
        this.displayName = displayName;
    }

    public static Mode fromString(String text) {
        for (Mode b : Mode.values()) {
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

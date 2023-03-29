package com.example.tictactoe.models;

import com.example.tictactoe.enums.Difficulty;
import com.example.tictactoe.enums.Mode;
import com.example.tictactoe.enums.Player;
import com.example.tictactoe.enums.State;

import java.util.Optional;
import java.util.Random;

public class TicTacToeGame {
    private static final int BOARD_SIZE = 3;
    private final State[][] board = new State[BOARD_SIZE][BOARD_SIZE];
    private final Mode mode;
    private final Difficulty difficulty;
    private final Random random = new Random();
    private Player currentPlayer = Player.PLAYER_ONE;

    public TicTacToeGame(Mode mode, Difficulty difficulty) {
        this.mode = mode;
        this.difficulty = difficulty;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = State.BLANK;
            }
        }
    }

    public State[][] getBoard() {
        return board;
    }

    public void makeMove(int x, int y) {
        if (board[x][y] != State.BLANK) return;
        board[x][y] = currentPlayer == Player.PLAYER_ONE ? State.X : State.O;
        currentPlayer = currentPlayer == Player.PLAYER_ONE ? Player.PLAYER_TWO : Player.PLAYER_ONE;
        if (mode == Mode.ONE_PLAYER && currentPlayer == Player.PLAYER_TWO && checkForWinner().isEmpty() && hasMovesLeft())
            aiMakeMove();
    }


    private void aiMakeMove() {
        switch (difficulty) {
            case EASY -> aiMakeMoveEasy();
            case MEDIUM -> aiMakeMoveMedium();
            case HARD -> aiMakeMoveHard();
        }
        currentPlayer = Player.PLAYER_ONE;
    }

    private void aiMakeMoveEasy() {
        int x, y;
        do {
            x = random.nextInt(BOARD_SIZE);
            y = random.nextInt(BOARD_SIZE);
        } while (board[x][y] != State.BLANK);
        board[x][y] = State.O;
    }

    private void aiMakeMoveMedium() {
        // Check if there is a winning move
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == State.BLANK) {
                    board[i][j] = State.O;
                    Optional<Player> winner = checkForWinner();
                    if (winner.isPresent() && winner.get() == Player.PLAYER_TWO) {
                        currentPlayer = Player.PLAYER_ONE;
                        return;
                    }
                    board[i][j] = State.BLANK;
                }
            }
        }

        // Check if there is a blocking move
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == State.BLANK) {
                    board[i][j] = State.X;
                    Optional<Player> winner = checkForWinner();
                    if (winner.isPresent() && winner.get() == Player.PLAYER_ONE) {
                        board[i][j] = State.O;
                        currentPlayer = Player.PLAYER_ONE;
                        return;
                    }
                    board[i][j] = State.BLANK;
                }
            }
        }

        // Otherwise, make a random move
        aiMakeMoveEasy();
    }

    // Returns the best move for the AI player using the Minimax algorithm
    private int[] findBestMove(State[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        // Loop through all possible moves
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == State.BLANK) {
                    // Try the move
                    board[i][j] = State.O;
                    int score = minimax(board, 0, false);
                    board[i][j] = State.BLANK;

                    // Update best move if score is better
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    // Returns the score of the current game state for the AI player
    private int minimax(State[][] board, int depth, boolean isMaximizing) {
        Optional<Player> winner = checkForWinner();
        if (winner.isPresent()) {
            if (winner.get() == Player.PLAYER_ONE) return -10;
            else if (winner.get() == Player.PLAYER_TWO) return 10;
        }
        if (!hasMovesLeft()) return 0;
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        State player = isMaximizing ? State.O : State.X;

        // Loop through all possible moves
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == State.BLANK) {
                    // Try the move
                    board[i][j] = player;
                    int score = minimax(board, depth + 1, !isMaximizing);
                    board[i][j] = State.BLANK;

                    // Update best score if score is better (for maximizing player) or worse (for minimizing player)
                    if (isMaximizing) {
                        bestScore = Math.max(score, bestScore);
                    } else {
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }

        return bestScore;
    }

    // Makes the AI player's move by finding the best move and updating the game board
    private void aiMakeMoveHard() {
        int[] bestMove = findBestMove(board);
        board[bestMove[0]][bestMove[1]] = State.O;
    }

    public boolean hasMovesLeft() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == State.BLANK) return true;
            }
        }
        return false;
    }

    public Optional<Player> checkForWinner() {
        // Check rows and columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == State.X) return Optional.of(Player.PLAYER_ONE);
                else if (board[i][0] == State.O) return Optional.of(Player.PLAYER_TWO);
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == State.X) return Optional.of(Player.PLAYER_ONE);
                else if (board[0][i] == State.O) return Optional.of(Player.PLAYER_TWO);
            }
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == State.X) return Optional.of(Player.PLAYER_ONE);
            else if (board[0][0] == State.O) return Optional.of(Player.PLAYER_TWO);
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == State.X) return Optional.of(Player.PLAYER_ONE);
            else if (board[0][2] == State.O) return Optional.of(Player.PLAYER_TWO);
        }
        return Optional.empty();
    }
}

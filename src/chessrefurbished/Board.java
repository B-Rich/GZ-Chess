/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrefurbished;

/**
 *
 * @author Threadcount
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;

public class Board {

    protected Square[][] chessBoard = new Square[8][8];
    protected Piece[][] chessPieces = new Piece[8][8];
    private boolean flipBoard;
    private Pawn playerPawn;
    private Pawn enemyPawn;
    private Rook playerRook;
    private Rook enemyRook;
    private Knight playerKnight;
    private Knight enemyKnight;
    private Bishop playerBishop;
    private Bishop enemyBishop;
    private Queen playerQueen;
    private Queen enemyQueen;
    private King playerKing;
    private King enemyKing;

    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j] = new Square();
            }
        }
        startGame();
    }

    private void startGame() {
        this.playerPawn = new Pawn("P");
        this.enemyPawn = new Pawn("p");

        this.playerRook = new Rook("R");
        this.enemyRook = new Rook("r");

        this.playerKnight = new Knight("K");
        this.enemyKnight = new Knight("k");

        this.playerBishop = new Bishop("B");
        this.enemyBishop = new Bishop("b");

        this.playerQueen = new Queen("Q");
        this.enemyQueen = new Queen("q");

        this.playerKing = new King("M");
        this.enemyKing = new King("m");

        

        for (int i = 0; i < 8; i++) {
            this.chessPieces[i][1] = this.playerPawn;
            this.chessPieces[i][6] = this.enemyPawn;
            this.chessBoard[i][1].addPiece(this.chessPieces[i][1]);
            this.chessBoard[i][6].addPiece(this.chessPieces[i][6]);
        }
        this.chessPieces[0][0] = this.playerRook;
        this.chessPieces[7][0] = this.playerRook;
        this.chessPieces[0][7] = this.enemyRook;
        this.chessPieces[7][7] = this.enemyRook;

        this.chessBoard[0][0].addPiece(this.chessPieces[0][0]);
        this.chessBoard[7][0].addPiece(this.chessPieces[7][0]);
        this.chessBoard[0][7].addPiece(this.chessPieces[0][7]);
        this.chessBoard[7][7].addPiece(this.chessPieces[7][7]);

        this.chessPieces[1][0] = this.playerKnight;
        this.chessPieces[6][0] = this.playerKnight;
        this.chessPieces[1][7] = this.enemyKnight;
        this.chessPieces[6][7] = this.enemyKnight;

        this.chessBoard[1][0].addPiece(this.chessPieces[1][0]);
        this.chessBoard[6][0].addPiece(this.chessPieces[6][0]);
        this.chessBoard[1][7].addPiece(this.chessPieces[1][7]);
        this.chessBoard[6][7].addPiece(this.chessPieces[6][7]);

        this.chessPieces[2][0] = this.playerBishop;
        this.chessPieces[5][0] = this.playerBishop;
        this.chessPieces[2][7] = this.enemyBishop;
        this.chessPieces[5][7] = this.enemyBishop;

        this.chessBoard[2][0].addPiece(this.chessPieces[2][0]);
        this.chessBoard[5][0].addPiece(this.chessPieces[5][0]);
        this.chessBoard[2][7].addPiece(this.chessPieces[2][7]);
        this.chessBoard[5][7].addPiece(this.chessPieces[5][7]);

        this.chessPieces[3][0] = this.playerQueen;
        this.chessPieces[3][7] = this.enemyQueen;

        this.chessBoard[3][0].addPiece(this.chessPieces[3][0]);
        this.chessBoard[3][7].addPiece(this.chessPieces[3][7]);

        this.chessPieces[4][0] = this.playerKing;
        this.chessPieces[4][7] = this.enemyKing;

        this.chessBoard[4][0].addPiece(this.chessPieces[4][0]);
        this.chessBoard[4][7].addPiece(this.chessPieces[4][7]);

    }


    public String[][] chessBoard() {
        String[][] chess = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                    chess[i][j] = chessBoard[i][j].getPiece();
            }
        }
        return chess;
    }
}


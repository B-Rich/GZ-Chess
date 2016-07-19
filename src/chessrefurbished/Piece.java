/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrefurbished;

import java.util.Stack;

/**
 *
 * @author Threadcount
 */
public abstract class Piece {
    private String pieceName;
    
    public static String cellNumbers[][] = {
        {"1", "2", "3", "4", "5", "6", "7", "8"},
        {"9", "10", "11", "12", "13", "14", "15", "16"},
        {"17", "18", "19", "20", "21", "22", "23", "24"},
        {"25", "26", "27", "28", "29", "30", "31", "32"},
        {"33", "34", "35", "36", "37", "38", "39", "40"},
        {"41", "42", "43", "44", "45", "46", "47", "48"},
        {"49", "50", "51", "52", "53", "54", "55", "56"},
        {"57", "58", "59", "60", "61", "62", "63", "64"}};

    public static String chessBoardPieces[][] = {
        {"r", "n", "b", "q", "k", "b", "n", "r"},
        {"p", "p", "p", "p", "p", "p", "p", "p"},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {"P", "P", "P", "P", "P", "P", "P", "P"},
        {"R", "N", "B", "Q", "K", "B", "N", "R"}};
    

    public static String chessBoardLocations[][] = {
        {"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"},
        {"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"},
        {"a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6"},
        {"a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5"},
        {"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4"},
        {"a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"},
        {"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"},
        {"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"}};
    
    protected static boolean PlayerTurn;
    public static int playerKingPosition = 61;
    public static int enemyKingPosition =  5;
    

    
    public int getWhiteKPos(){ return playerKingPosition; }
    public int getBlackKPos(){ return enemyKingPosition; }
    
    
    public void getKings(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoardPieces[i][j].equals("K")) {
                    playerKingPosition = Integer.parseInt(cellNumbers[i][j]);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoardPieces[i][j].equals("k")) {
                    enemyKingPosition = Integer.parseInt(cellNumbers[i][j]);
                }
            }
        }
    }
    
    public boolean isKingSafe() {
         getKings();
        //Bishop/Queen
        int temp = 1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(" ".equals(chessBoardPieces[playerKingPosition/8+temp*i][playerKingPosition%8+temp*j])) {
                        temp++;
                    }
                    if ("b".equals(chessBoardPieces[playerKingPosition/8+temp*i][playerKingPosition%8+temp*j]) ||
                            "q".equals(chessBoardPieces[playerKingPosition/8+temp*i][playerKingPosition%8+temp*j])) {
                        return false;
                    }
                } catch (Exception excptn) {}
                temp = 1;
            }
        }
        //Rook/Queen
        for (int i=-1; i<=1; i+=2) {
            try {
                while(" ".equals(chessBoardPieces[playerKingPosition/8][playerKingPosition%8+temp*i])) {
                    temp++;
                }
                if ("r".equals(chessBoardPieces[playerKingPosition/8][playerKingPosition%8+temp*i]) ||
                        "q".equals(chessBoardPieces[playerKingPosition/8][playerKingPosition%8+temp*i])) {
                    return false;
                }
            } catch (Exception excptn) {}
            temp=1;
            try {
                while(" ".equals(chessBoardPieces[playerKingPosition/8+temp*i][playerKingPosition%8])) {
                    temp++;
                }
                if ("r".equals(chessBoardPieces[playerKingPosition/8+temp*i][playerKingPosition%8]) ||
                        "q".equals(chessBoardPieces[playerKingPosition/8+temp*i][playerKingPosition%8])) {
                    return false;
                }
            } catch (Exception excptn) {}
            temp=1;
        }
        //Knight
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("n".equals(chessBoardPieces[playerKingPosition/8+i][playerKingPosition%8+j*2])) {
                        return false;
                    }
                } catch (Exception excptn) {}
                try {
                    if ("n".equals(chessBoardPieces[playerKingPosition/8+i*2][playerKingPosition%8+j])) {
                        return false;
                    }
                } catch (Exception excptn) {}
            }
        }
        //Pawn
        if (playerKingPosition>=16) {
            try {
                if ("p".equals(chessBoardPieces[playerKingPosition/8-1][playerKingPosition%8-1])) {
                    return false;
                }
            } catch (Exception excptn) {}
            try {
                if ("p".equals(chessBoardPieces[playerKingPosition/8-1][playerKingPosition%8+1])) {
                    return false;
                }
            } catch (Exception excptn) {}
            //King
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("a".equals(chessBoardPieces[playerKingPosition/8+i][playerKingPosition%8+j])) {
                                return false;
                            }
                        } catch (Exception excptn) {}
                    }
                }
            }
        }
        return true;
    }
    
    
    public Piece(String pieceName){
        this.pieceName = pieceName;
    }
    
    public String toString() {
       return this.pieceName;
    }
    
    public abstract Stack <String> possibleEnemyMoves(String piece, String source);
    
    public abstract Stack <String> possibleMoves(String piece, String source);
    
    public abstract void makeMove(String piece, String src, String dest);
}

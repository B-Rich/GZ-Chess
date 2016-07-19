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
public class Rook extends Piece {
    
    private boolean hasMoved = false;
    
    public Rook(String pieceName) {
        //r for black queen and R for white queen
        super(pieceName);

    }
    public String toString() {
       return super.toString();
    }
    
    
    public Stack <String> possibleEnemyMoves(String rook, String source) {
        if(!rook.equalsIgnoreCase("R")){
            System.out.println("Not a rook");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == rook) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
                    currentI = i;
                    currentJ = j;
                    break;
                }
            }
        }
        String oldPiece, oldPieceLocation;
        int pPos = Integer.parseInt(cellNumbers[currentI][currentJ]);
        int pRow = currentI;
        int pColumn = currentJ;
        int temp = 1;
        if(rook.equals("r")){
        for (int j = -1; j <= 1; j += 2) {
            try {
                while (" ".equals(chessBoardPieces[pRow][pColumn + temp * j])) {
                    oldPiece = chessBoardPieces[pRow][pColumn + temp * j];
                    oldPieceLocation = chessBoardLocations[pRow][pColumn + temp * j];
                    chessBoardPieces[pRow][pColumn] = " ";
                    chessBoardPieces[pRow][pColumn + temp * j] = "r";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn] = "r";
                    chessBoardPieces[pRow][pColumn + temp * j] = oldPiece;
                    temp++;
                }
                if (Character.isUpperCase(chessBoardPieces[pRow][pColumn + temp * j].charAt(0))) {
                    oldPiece = chessBoardPieces[pRow][pColumn + temp * j];
                    oldPieceLocation = chessBoardLocations[pRow][pColumn + temp * j];
                    chessBoardPieces[pRow][pColumn] = " ";
                    chessBoardPieces[pRow][pColumn + temp * j] = "r";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn] = "r";
                    chessBoardPieces[pRow][pColumn + temp * j] = oldPiece;
                }
            } catch (Exception excptn) {
            }
            temp = 1;
            try {
                while (" ".equals(chessBoardPieces[pRow + temp * j][pColumn])) {
                    oldPiece = chessBoardPieces[pRow + temp * j][pColumn];
                    oldPieceLocation = chessBoardLocations[pRow + temp * j][pColumn];
                    chessBoardPieces[pRow][pColumn] = " ";
                    chessBoardPieces[pRow + temp * j][pColumn] = "r";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn] = "r";
                    chessBoardPieces[pRow + temp * j][pColumn] = oldPiece;
                    temp++;
                }
                if (Character.isUpperCase(chessBoardPieces[pRow + temp * j][pColumn].charAt(0))) {
                    oldPiece = chessBoardPieces[pRow + temp * j][pColumn];
                    oldPieceLocation = chessBoardLocations[pRow + temp * j][pColumn];
                    chessBoardPieces[pRow][pColumn] = " ";
                    chessBoardPieces[pRow + temp * j][pColumn] = "r";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);;
                    }
                    chessBoardPieces[pRow][pColumn] = "r";
                    chessBoardPieces[pRow + temp * j][pColumn] = oldPiece;
                }
            } catch (Exception excptn) {
            }
            temp = 1;
        }}
        return moves;
    }
    
    public Stack <String> possibleMoves(String rook, String source) {
        if(!rook.equalsIgnoreCase("R")){
            System.out.println("Not a rook");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == rook) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
                    currentI = i;
                    currentJ = j;
                    break;
                }
            }
        }
        String oldPiece, oldPieceLocation;
        int pPos = Integer.parseInt(cellNumbers[currentI][currentJ]);
        int pRow = currentI;
        int pColumn = currentJ;
        int temp = 1;
        //White Rook
        for (int j = -1; j <= 1; j += 2) {
            try {
                while (" ".equals(chessBoardPieces[pRow][pColumn + temp * j])) {
                    oldPiece = chessBoardPieces[pRow][pColumn + temp * j];
                    oldPieceLocation = chessBoardLocations[pRow][pColumn + temp * j];
                    chessBoardPieces[pRow][pColumn] = " ";
                    chessBoardPieces[pRow][pColumn + temp * j] = "R";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn] = "R";
                    chessBoardPieces[pRow][pColumn + temp * j] = oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(chessBoardPieces[pRow][pColumn + temp * j].charAt(0))) {
                    oldPiece = chessBoardPieces[pRow][pColumn + temp * j];
                    oldPieceLocation = chessBoardLocations[pRow][pColumn + temp * j];
                    chessBoardPieces[pRow][pColumn] = " ";
                    chessBoardPieces[pRow][pColumn + temp * j] = "R";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn] = "R";
                    chessBoardPieces[pRow][pColumn + temp * j] = oldPiece;
                }
            } catch (Exception excptn) {
            }
            temp = 1;
            try {
                while (" ".equals(chessBoardPieces[pRow + temp * j][pColumn])) {
                    oldPiece = chessBoardPieces[pRow + temp * j][pColumn];
                    oldPieceLocation = chessBoardLocations[pRow + temp * j][pColumn];
                    chessBoardPieces[pRow][pColumn] = " ";
                    chessBoardPieces[pRow + temp * j][pColumn] = "R";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn] = "R";
                    chessBoardPieces[pRow + temp * j][pColumn] = oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(chessBoardPieces[pRow + temp * j][pColumn].charAt(0))) {
                    oldPiece = chessBoardPieces[pRow + temp * j][pColumn];
                    oldPieceLocation = chessBoardLocations[pRow + temp * j][pColumn];
                    chessBoardPieces[pRow][pColumn] = " ";
                    chessBoardPieces[pRow + temp * j][pColumn] = "R";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);;
                    }
                    chessBoardPieces[pRow][pColumn] = "R";
                    chessBoardPieces[pRow + temp * j][pColumn] = oldPiece;
                }
            } catch (Exception excptn) {
            }
            temp = 1;
        }
        return moves;
    }
    
    public void makeMove(String rook, String src, String dest){
        if(rook.equalsIgnoreCase("R")){
        int srcI = 0;
        int srcJ = 0;
        int destI = 0;
        int destJ = 0;
        for (int ni = 0; ni < chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessBoardPieces.length; nj++) {
                if ((chessBoardPieces[ni][nj] == rook) && 
                        (chessBoardLocations[ni][nj].equalsIgnoreCase(src))) {
                    srcI = ni;
                    srcJ = nj;
                    break;
                }
            }
        }
        for (int ni = 0; ni < chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessBoardPieces.length; nj++) {
                if ((chessBoardLocations[ni][nj].equalsIgnoreCase(dest))) {
                    destI = ni;
                    destJ = nj;
                    break;
                }
            }
        }
        if(rook.equals("R")){
        if(possibleMoves(rook, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(rook.equals("r")){
        if(possibleEnemyMoves(rook, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}}
    }
}

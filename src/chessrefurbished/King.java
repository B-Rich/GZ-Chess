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
public class King extends Piece {
    
    public King(String pieceName) {
        //q for black queen and Q for white queen
        super(pieceName);

    }
    
    public String toString() {
       return super.toString();
    }
    
    public Stack <String> possibleMoves(String king, String source) {
        if(!king.equalsIgnoreCase("K")){
            System.out.println("Not a king");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == king) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        for (int j=0;j<9;j++) {
            if (j!=4) {
                try {
                    if (Character.isLowerCase(chessBoardPieces[pRow-1+j/3][pColumn-1+j%3].charAt(0)) || " ".equals(chessBoardPieces[pRow-1+j/3][pColumn-1+j%3])) {
                        oldPiece=chessBoardPieces[pRow-1+j/3][pColumn-1+j%3];
                        oldPieceLocation = chessBoardLocations[pRow-1+j/3][pColumn-1+j%3];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow-1+j/3][pColumn-1+j%3]="K";
                        int kingTemp=playerKingPosition;
                        playerKingPosition=pPos+(j/3)*8+j%3-9;
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="K";
                        chessBoardPieces[pRow-1+j/3][pColumn-1+j%3]=oldPiece;
                        playerKingPosition=kingTemp;
                    }
                } catch (Exception excptn) {}
            }
        }
        return moves;
    }
    
    public Stack <String> possibleEnemyMoves(String king, String source) {
        if(!king.equalsIgnoreCase("K")){
            System.out.println("Not a king");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == king) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        if(king.equals("k")){
        for (int j=0;j<9;j++) {
            if (j!=4) {
                try {
                    if (Character.isUpperCase(chessBoardPieces[pRow-1+j/3][pColumn-1+j%3].charAt(0)) || " ".equals(chessBoardPieces[pRow-1+j/3][pColumn-1+j%3])) {
                        oldPiece=chessBoardPieces[pRow-1+j/3][pColumn-1+j%3];
                        oldPieceLocation = chessBoardLocations[pRow-1+j/3][pColumn-1+j%3];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow-1+j/3][pColumn-1+j%3]="k";
                        int kingTemp=enemyKingPosition;
                        enemyKingPosition=pPos+(j/3)*8+j%3-9;
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="k";
                        chessBoardPieces[pRow-1+j/3][pColumn-1+j%3]=oldPiece;
                        enemyKingPosition=kingTemp;
                    }
                } catch (Exception excptn) {}
            }
        }}
        return moves;
    }
    
    public void makeMove(String king, String src, String dest){
        if(king.equalsIgnoreCase("K")){
        int srcI = 0;
        int srcJ = 0;
        int destI = 0;
        int destJ = 0;
        for (int ni = 0; ni < chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessBoardPieces.length; nj++) {
                if ((chessBoardPieces[ni][nj] == king) && 
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
        if(king.equals("K")){
        if(possibleMoves(king, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(king.equals("k")){
        if(possibleEnemyMoves(king, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}}
    }
}

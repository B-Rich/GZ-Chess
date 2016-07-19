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
public class Queen extends Piece {
     public Queen(String pieceName) {
        //q for black queen and Q for white queen
        super(pieceName);

    }
    
    public String toString() {
       return super.toString();
    }
    
    public Stack <String> possibleMoves(String queen, String source) {
        if(!queen.equalsIgnoreCase("Q")){
            System.out.println("Not a Queen");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == queen) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        for (int j=-1; j<=1; j++) {
            for (int k=-1; k<=1; k++) {
                if (j!=0 || k!=0) {
                    try {
                        while(" ".equals(chessBoardPieces[pRow+temp*j][pColumn+temp*k]))
                        {
                            oldPiece=chessBoardPieces[pRow+temp*j][pColumn+temp*k];
                            oldPieceLocation = chessBoardLocations[pRow+temp*j][pColumn+temp*k];
                            chessBoardPieces[pRow][pColumn]=" ";
                            chessBoardPieces[pRow+temp*j][pColumn+temp*k]="Q";
                            if (isKingSafe()) {
                                moves.push(oldPieceLocation);
                            }
                            chessBoardPieces[pRow][pColumn]="Q";
                            chessBoardPieces[pRow+temp*j][pColumn+temp*k]=oldPiece;
                            temp++;
                        }
                        if (Character.isLowerCase(chessBoardPieces[pRow+temp*j][pColumn+temp*k].charAt(0))) {
                            oldPiece=chessBoardPieces[pRow+temp*j][pColumn+temp*k];
                            oldPieceLocation = chessBoardLocations[pRow+temp*j][pColumn+temp*k];
                            chessBoardPieces[pRow][pColumn]=" ";
                            chessBoardPieces[pRow+temp*j][pColumn+temp*k]="Q";
                            if (isKingSafe()) {
                                moves.push(oldPieceLocation);
                            }
                            chessBoardPieces[pRow][pColumn]="Q";
                            chessBoardPieces[pRow+temp*j][pColumn+temp*k]=oldPiece;
                        }
                    } catch (Exception excptn) {}
                    temp=1;
                }
            }
        }
        return moves;
    }
    
    public Stack <String> possibleEnemyMoves(String queen, String source) {
        if(!queen.equalsIgnoreCase("Q")){
            System.out.println("Not a Queen");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == queen) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        if(queen.equals("q")){
        for (int j=-1; j<=1; j++) {
            for (int k=-1; k<=1; k++) {
                if (j!=0 || k!=0) {
                    try {
                        while(" ".equals(chessBoardPieces[pRow+temp*j][pColumn+temp*k]))
                        {
                            oldPiece=chessBoardPieces[pRow+temp*j][pColumn+temp*k];
                            oldPieceLocation = chessBoardLocations[pRow+temp*j][pColumn+temp*k];
                            chessBoardPieces[pRow][pColumn]=" ";
                            chessBoardPieces[pRow+temp*j][pColumn+temp*k]="q";
                            if (isKingSafe()) {
                                moves.push(oldPieceLocation);
                            }
                            chessBoardPieces[pRow][pColumn]="q";
                            chessBoardPieces[pRow+temp*j][pColumn+temp*k]=oldPiece;
                            temp++;
                        }
                        if (Character.isUpperCase(chessBoardPieces[pRow+temp*j][pColumn+temp*k].charAt(0))) {
                            oldPiece=chessBoardPieces[pRow+temp*j][pColumn+temp*k];
                            oldPieceLocation = chessBoardLocations[pRow+temp*j][pColumn+temp*k];
                            chessBoardPieces[pRow][pColumn]=" ";
                            chessBoardPieces[pRow+temp*j][pColumn+temp*k]="q";
                            if (isKingSafe()) {
                                moves.push(oldPieceLocation);
                            }
                            chessBoardPieces[pRow][pColumn]="q";
                            chessBoardPieces[pRow+temp*j][pColumn+temp*k]=oldPiece;
                        }
                    } catch (Exception excptn) {}
                    temp=1;
                }
            }
        }}
        return moves;
    }
    
    public void makeMove(String queen, String src, String dest){
        if(queen.equalsIgnoreCase("Q")){
        int srcI = 0;
        int srcJ = 0;
        int destI = 0;
        int destJ = 0;
        for (int ni = 0; ni < chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessBoardPieces.length; nj++) {
                if ((chessBoardPieces[ni][nj] == queen) && 
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
        if(queen.equals("Q")){
        if(possibleMoves(queen, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(queen.equals("q")){
        if(possibleEnemyMoves(queen, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}}
    }
}

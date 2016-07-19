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
public class Bishop extends Piece{
    public Bishop(String pieceName) {
        //b for black bishop and B for white bishop
        super(pieceName);

    }
    
    public String toString() {
       return super.toString();
    }
    
    public Stack <String> possibleMoves(String bishop, String source) {
        if(!bishop.equals("B")){
            System.out.println("Not a player bishop");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == bishop) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        //White Bishop
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    while(" ".equals(chessBoardPieces[pRow+temp*j][pColumn+temp*k]))
                    {
                        oldPiece=chessBoardPieces[pRow+temp*j][pColumn+temp*k];
                        oldPieceLocation=chessBoardLocations[pRow+temp*j][pColumn+temp*k];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow+temp*j][pColumn+temp*k]="B";
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="B";
                        chessBoardPieces[pRow+temp*j][pColumn+temp*k]=oldPiece;
                        temp++;
                    }
                    if (Character.isLowerCase(chessBoardPieces[pRow+temp*j][pColumn+temp*k].charAt(0))) {
                        oldPiece=chessBoardPieces[pRow+temp*j][pColumn+temp*k];
                        oldPieceLocation=chessBoardLocations[pRow+temp*j][pColumn+temp*k];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow+temp*j][pColumn+temp*k]="B";
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="B";
                        chessBoardPieces[pRow+temp*j][pColumn+temp*k]=oldPiece;
                    }
                } catch (Exception excptn) {}
                temp=1;
            }
        }
        return moves;
    }
    
    public Stack <String> possibleEnemyMoves(String bishop, String source) {
        if(!bishop.equals("b")){
            System.out.println("Not an enemy bishop");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == bishop) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        if(bishop.equals("b")){
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    while(" ".equals(chessBoardPieces[pRow+temp*j][pColumn+temp*k]))
                    {
                        oldPiece=chessBoardPieces[pRow+temp*j][pColumn+temp*k];
                        oldPieceLocation=chessBoardLocations[pRow+temp*j][pColumn+temp*k];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow+temp*j][pColumn+temp*k]="b";
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="b";
                        chessBoardPieces[pRow+temp*j][pColumn+temp*k]=oldPiece;
                        temp++;
                    }
                    if (Character.isUpperCase(chessBoardPieces[pRow+temp*j][pColumn+temp*k].charAt(0))) {
                        oldPiece=chessBoardPieces[pRow+temp*j][pColumn+temp*k];
                        oldPieceLocation=chessBoardLocations[pRow+temp*j][pColumn+temp*k];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow+temp*j][pColumn-temp*k]="b";
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="b";
                        chessBoardPieces[pRow+temp*j][pColumn+temp*k]=oldPiece;
                    }
                } catch (Exception excptn) {}
                temp = 1;
            }
        }}
        return moves;
    }
    
    public void makeMove(String bishop, String src, String dest){
        if(bishop.equalsIgnoreCase("B")){
        int srcI = 0;
        int srcJ = 0;
        int destI = 0;
        int destJ = 0;
        for (int ni = 0; ni < chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessBoardPieces.length; nj++) {
                if ((chessBoardPieces[ni][nj] == bishop) && 
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
        if(bishop.equals("B")){
        if(possibleMoves(bishop, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(bishop.equals("b")){
        if(possibleEnemyMoves(bishop, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}}       
    }
}

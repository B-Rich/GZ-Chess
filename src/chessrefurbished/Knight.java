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
public class Knight extends Piece{
    public Knight(String pieceName) {
        //n for black knight and N for white knight
        super(pieceName);

    }
    
    public String toString() {
       return super.toString();
    }
    
    public Stack <String> possibleEnemyMoves(String knight, String source) {
        if(!knight.equalsIgnoreCase("N")){
            System.out.println("Not a knight");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == knight) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        //White Knights
        if(knight.equals("n")){
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    if (Character.isUpperCase(chessBoardPieces[pRow+j][pColumn-k*2].charAt(0)) || " ".equals(chessBoardPieces[pRow+j][pColumn-k*2])) {
                        oldPiece=chessBoardPieces[pRow+j][pColumn-k*2];
                        oldPieceLocation = chessBoardLocations[pRow+j][pColumn-k*2];
                        chessBoardPieces[pRow][pColumn]=" ";
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="n";
                        chessBoardPieces[pRow+j][pColumn-k*2]=oldPiece;
                    }
                } catch (Exception excptn) {}
                try {
                    if (Character.isUpperCase(chessBoardPieces[pRow+j*2][pColumn-k].charAt(0)) || " ".equals(chessBoardPieces[pRow+j*2][pColumn-k])) {
                        oldPiece=chessBoardPieces[pRow+j*2][pColumn-k];
                        oldPieceLocation = chessBoardLocations[pRow+j*2][pColumn-k];
                        chessBoardPieces[pRow][pColumn]=" ";
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="n";
                        chessBoardPieces[pRow+j*2][pColumn-k]=oldPiece;
                    }
                } catch (Exception excptn) {}
            }
        }
        //return moves;
    }else{}
        return moves;
   }
    
    public Stack <String> possibleMoves(String knight, String source) {
        if(!knight.equalsIgnoreCase("N")){
            System.out.println("Not a knight");
        }
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == knight) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        //White Knights
        if(knight.equalsIgnoreCase("N")){
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    if (Character.isLowerCase(chessBoardPieces[pRow+j][pColumn+k*2].charAt(0)) || " ".equals(chessBoardPieces[pRow+j][pColumn+k*2])) {
                        oldPiece=chessBoardPieces[pRow+j][pColumn+k*2];
                        oldPieceLocation = chessBoardLocations[pRow+j][pColumn+k*2];
                        chessBoardPieces[pRow][pColumn]=" ";
                        if (isKingSafe()) {
                            //list=list+r+c+(r+j)+(c+k*2)+oldPiece;
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="N";
                        chessBoardPieces[pRow+j][pColumn+k*2]=oldPiece;
                    }
                } catch (Exception excptn) {}
                try {
                    if (Character.isLowerCase(chessBoardPieces[pRow+j*2][pColumn+k].charAt(0)) || " ".equals(chessBoardPieces[pRow+j*2][pColumn+k])) {
                        oldPiece=chessBoardPieces[pRow+j*2][pColumn+k];
                        oldPieceLocation = chessBoardLocations[pRow+j*2][pColumn+k];
                        chessBoardPieces[pRow][pColumn]=" ";
                        if (isKingSafe()) {
                            //list=list+r+c+(r+j*2)+(c+k)+oldPiece;
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="N";
                        chessBoardPieces[pRow+j*2][pColumn+k]=oldPiece;
                    }
                } catch (Exception excptn) {}
            }
        }
        //return moves;
    }else{}
        return moves;
   }
    
    public void makeMove(String knight, String src, String dest){
        if(knight.equalsIgnoreCase("N")){
        int srcI = 0;
        int srcJ = 0;
        int destI = 0;
        int destJ = 0;
        for (int ni = 0; ni < chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessBoardPieces.length; nj++) {
                if ((chessBoardPieces[ni][nj] == knight) && 
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
        if(knight.equals("N")){
        if(possibleMoves(knight, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(knight.equals("n")){
        if(possibleEnemyMoves(knight, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        }
    }
}

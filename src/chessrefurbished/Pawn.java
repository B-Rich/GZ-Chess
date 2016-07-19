/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrefurbished;

import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author Threadcount
 */
public class Pawn extends Piece{
    
    private String[] promotionUnits = {"Q","R","B","K"};

    public Pawn(String pieceName) {
        //p for black pawn and P for white pawn
        super(pieceName);
        if(Character.isLowerCase(pieceName.charAt(0))){
            for(int i = 0 ; i < 4 ; i++){
                promotionUnits[i] = promotionUnits[i].toLowerCase();
            }
        }
    }

    public String toString() {
       return super.toString();
    }
    
    public Stack <String> possibleMoves(String pawn, String source) {
        if(!pawn.equals("P")){
            System.out.println("Not a pawn");
        }
        String[] promotionUnits = {"Queen","Rook","Bishop","Knight"};
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        int kR;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == pawn) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        for (int j=-1; j<=1; j+=2) {
            try {//capture
                if (Character.isLowerCase(chessBoardPieces[pRow-1][pColumn+j].charAt(0)) && pPos>=16) {
                    oldPiece = chessBoardPieces[pRow-1][pColumn+j];
                    oldPieceLocation = chessBoardLocations[pRow-1][pColumn+j];
                    chessBoardPieces[pRow][pColumn]=" ";
                    chessBoardPieces[pRow-1][pColumn+j]="P";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn]="P";
                    chessBoardPieces[pRow-1][pColumn+j]=oldPiece;
                }
            } catch (Exception excptn) {}
            
            try {//promotion && capture
                if (Character.isLowerCase(chessBoardPieces[pRow-1][pColumn+j].charAt(0)) && pPos<16) {
                        for (int k = 0; k<4; k++) {
                        oldPiece=chessBoardPieces[pRow-1][pColumn+j];
                        oldPieceLocation = chessBoardLocations[pRow-1][pColumn+j];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow-1][pColumn+j]= promotionUnits[k].substring(0, 1);
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="P";
                        chessBoardPieces[pRow-1][pColumn+j]=oldPiece;
                }}
            } catch (Exception excptn) {}
        }
        try {//move one up
            if (" ".equals(chessBoardPieces[pRow-1][pColumn]) && pPos>=16) {
                oldPiece=chessBoardPieces[pRow-1][pColumn];
                oldPieceLocation = chessBoardLocations[pRow-1][pColumn];
                chessBoardPieces[pRow][pColumn]=" ";
                chessBoardPieces[pRow-1][pColumn]="P";
                if (isKingSafe()) {
                    moves.push(oldPieceLocation);
                }
                chessBoardPieces[pRow][pColumn]="P";
                chessBoardPieces[pRow-1][pColumn]=oldPiece;
            }
        } catch (Exception excptn) {}
        try {//promotion && no capture
            if (" ".equals(chessBoardPieces[pRow-1][pColumn]) && pPos<16) {
                for (int k = 0; k<4; k++) {
                    oldPiece=chessBoardPieces[pRow-1][pColumn];
                    oldPieceLocation = chessBoardLocations[pRow-1][pColumn];
                    chessBoardPieces[pRow][pColumn]=" ";
                    chessBoardPieces[pRow-1][pColumn]=promotionUnits[k].substring(0, 1);
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn]="P";
                    chessBoardPieces[pRow-1][pColumn]=oldPiece;
                }}
            
        } catch (Exception excptn) {}
        try {//move two up
            if (" ".equals(chessBoardPieces[pRow-1][pColumn]) && " ".equals(chessBoardPieces[pRow-2][pColumn]) && pPos>=48) {
                oldPiece=chessBoardPieces[pRow-2][pColumn];
                oldPieceLocation = chessBoardLocations[pRow-2][pColumn];
                chessBoardPieces[pRow][pColumn]=" ";
                chessBoardPieces[pRow-2][pColumn]="P";
                if (isKingSafe()) {
                    moves.push(oldPieceLocation);
                }
                chessBoardPieces[pRow][pColumn]="P";
                chessBoardPieces[pRow-2][pColumn]=oldPiece;
            }
        } catch (Exception excptn) {}
                return moves;
    }
    
    public void makeMove(String pawn, String src, String dest){
        if(pawn.equalsIgnoreCase("P")){
        int srcI = 0;
        int srcJ = 0;
        int destI = 0;
        int destJ = 0;
        for (int ni = 0; ni < chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessBoardPieces.length; nj++) {
                if ((chessBoardPieces[ni][nj] == pawn) && 
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
        if(pawn.equals("P")){
        if(possibleMoves(pawn, src).contains(dest)){
            if(destI == 0){
                String[] promotionUnits = {"Queen","Rook","Bishop","Knight"};
                int k = JOptionPane.showOptionDialog(null, "What unit would you take?", "Choose promotion unit",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, promotionUnits, promotionUnits[0]);
                String[] promUnits = {"Q","R","B","N"};
                chessBoardPieces[destI][destJ] = promUnits[k];
            }else{
                chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            }
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(pawn.equals("p")){
        if(possibleEnemyMoves(pawn, src).contains(dest)){
            if(destI == 7){
                String[] promUnits = {"q","r","b","n"};
                chessBoardPieces[destI][destJ] = promUnits[new Random().nextInt(4)];
            }else{
                chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            }
            chessBoardPieces[srcI][srcJ]= " ";
        }}
    }}
    
    public Stack <String> possibleEnemyMoves(String pawn, String source) {
        if(!pawn.equals("p")){
            System.out.println("Not an enemy pawn");
        }
        String[] promotionUnits = {"q","r","b","n"};
        Stack<String> moves = new Stack();
        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < chessBoardPieces.length; i++) {
            for (int j = 0; j < chessBoardPieces.length; j++) {
                if ((chessBoardPieces[i][j] == pawn) && (chessBoardLocations[i][j].equalsIgnoreCase(source))) {
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
        for (int j=1; j<=-1; j-=2) {
            try {//capture
                if (Character.isUpperCase(chessBoardPieces[pRow+1][pColumn-j].charAt(0)) && 48>=pPos) {
                    oldPiece = chessBoardPieces[pRow+1][pColumn-j];
                    oldPieceLocation = chessBoardLocations[pRow+1][pColumn-j];
                    chessBoardPieces[pRow][pColumn]=" ";
                    chessBoardPieces[pRow+1][pColumn-j]="p";
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn]="p";
                    chessBoardPieces[pRow+1][pColumn-j]=oldPiece;
                }
            } catch (Exception excptn) {}
            
            try {//promotion && capture
                if (Character.isUpperCase(chessBoardPieces[pRow+1][pColumn-j].charAt(0)) && pPos>48) {
                    String[] temp={"q","r","b","n"};
                    for (int k=0; k<4; k++) {
                        oldPiece=chessBoardPieces[pRow+1][pColumn-j];
                        oldPieceLocation = chessBoardLocations[pRow+1][pColumn-j];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow+1][pColumn-j]= temp[k];
                        if (isKingSafe()) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="p";
                        chessBoardPieces[pRow+1][pColumn-j]=oldPiece;
                    }
                }
            } catch (Exception excptn) {}
        }
        try {//move one down
            if (" ".equals(chessBoardPieces[pRow+1][pColumn]) && 48>=pPos) {
                oldPiece=chessBoardPieces[pRow+1][pColumn];
                oldPieceLocation = chessBoardLocations[pRow+1][pColumn];
                chessBoardPieces[pRow][pColumn]=" ";
                chessBoardPieces[pRow+1][pColumn]="p";
                if (isKingSafe()) {
                    moves.push(oldPieceLocation);
                }
                chessBoardPieces[pRow][pColumn]="p";
                chessBoardPieces[pRow+1][pColumn]=oldPiece;
            }
        } catch (Exception excptn) {}
        try {//promotion && no capture
            if (" ".equals(chessBoardPieces[pRow+1][pColumn]) && pPos>48) {
                String[] temp={"q","r","b","n"};
                for (int k=0; k<4; k++) {
                    oldPiece=chessBoardPieces[pRow+1][pColumn];
                    oldPieceLocation = chessBoardLocations[pRow+1][pColumn];
                    chessBoardPieces[pRow][pColumn]=" ";
                    chessBoardPieces[pRow+1][pColumn]=temp[k];
                    if (isKingSafe()) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn]="p";
                    chessBoardPieces[pRow+1][pColumn]=oldPiece;
                }
            }
        } catch (Exception excptn) {}
        try {//move two down
            if (" ".equals(chessBoardPieces[pRow+1][pColumn]) && " ".equals(chessBoardPieces[pRow+2][pColumn]) && 48>=pPos) {
                oldPiece=chessBoardPieces[pRow+2][pColumn];
                oldPieceLocation = chessBoardLocations[pRow+2][pColumn];
                chessBoardPieces[pRow][pColumn]=" ";
                chessBoardPieces[pRow+2][pColumn]="p";
                if (isKingSafe()) {
                    moves.push(oldPieceLocation);
                }
                chessBoardPieces[pRow][pColumn]="p";
                chessBoardPieces[pRow+2][pColumn]=oldPiece;
            }
        } catch (Exception excptn) {}
                return moves;
    }
}

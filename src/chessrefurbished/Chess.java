/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrefurbished;

import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Threadcount
 */
public class Chess {
      
    public String cellNumbers[][] = {
        {"1", "2", "3", "4", "5", "6", "7", "8"},
        {"9", "10", "11", "12", "13", "14", "15", "16"},
        {"17", "18", "19", "20", "21", "22", "23", "24"},
        {"25", "26", "27", "28", "29", "30", "31", "32"},
        {"33", "34", "35", "36", "37", "38", "39", "40"},
        {"41", "42", "43", "44", "45", "46", "47", "48"},
        {"49", "50", "51", "52", "53", "54", "55", "56"},
        {"57", "58", "59", "60", "61", "62", "63", "64"}};

    public String chessBoardPieces[][] = {
        {"r", "n", "b", "q", "k", "b", "n", "r"},
        {"p", "p", "p", "p", "p", "p", "p", "p"},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {"P", "P", "P", "P", "P", "P", "P", "P"},
        {"R", "N", "B", "Q", "K", "B", "N", "R"}};
    

    public String chessBoardLocations[][] = {
        {"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"},
        {"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"},
        {"a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6"},
        {"a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5"},
        {"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4"},
        {"a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"},
        {"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"},
        {"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"}};
    
    private Stack<String[][]> chessGame = new Stack<String[][]>();
    protected static boolean PlayerTurn;
    private static int playerKingPosition = 61;
    private static int enemyKingPosition = 5;
    private static boolean playback = false;
    
    public Chess(){
        this.saveGameState();
    }
    
    public Chess(boolean PlayerTurn){
        this.PlayerTurn = PlayerTurn;
    }
    
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
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                if (chessBoardPieces[m][n].equals("k")) {
                    enemyKingPosition = Integer.parseInt(cellNumbers[m][n]);
                }
            }
        }
    }
    
    public Stack <String> possiblePawnMoves(String pawn, String source) {
        if(!pawn.equalsIgnoreCase("P")){
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
                    //list=list+r+c+(r-2)+c+oldPiece;
                    moves.push(oldPieceLocation);
                }
                chessBoardPieces[pRow][pColumn]="P";
                chessBoardPieces[pRow-2][pColumn]=oldPiece;
            }
        } catch (Exception excptn) {}
                return moves;
    }

    public Stack <String> possibleKnightMoves(String knight, String source) {
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
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="N";
                        chessBoardPieces[pRow+j*2][pColumn+k]=oldPiece;
                    }
                } catch (Exception excptn) {}
            }
        }
    }else{}
        return moves;
   }
    
    public Stack <String> possibleBishopMoves(String bishop, String source) {
        if(!bishop.equalsIgnoreCase("b")){
            System.out.println("Not a bishop");
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
    
    public Stack <String> possibleRookMoves(String rook, String source) {
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
    
    public Stack <String> possibleQueenMoves(String queen, String source) {
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
    
    public Stack <String> possibleKingMoves(String king, String source) {
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
                    System.out.println("I am currentI " + currentI);
                    System.out.println("I am currentJ " + currentJ);
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
        //need to add casting later
        return moves;
    }
    
    public void isPlayback(){
        this.playback = true;
    }
    
    public void flipBoard() {
        String invertBoard;
        for (int i=0;i<32;i++) {
            int r=i/8, c=i%8;
            if (Character.isUpperCase(chessBoardPieces[r][c].charAt(0))) {
                invertBoard=chessBoardPieces[r][c].toLowerCase();
            } else {
                invertBoard=chessBoardPieces[r][c].toUpperCase();
            }
            if (Character.isUpperCase(chessBoardPieces[7-r][7-c].charAt(0))) {
                chessBoardPieces[r][c]=chessBoardPieces[7-r][7-c].toLowerCase();
            } else {
                chessBoardPieces[r][c]=chessBoardPieces[7-r][7-c].toUpperCase();
            }
            chessBoardPieces[7-r][7-c]=invertBoard;
        }
    }
    
     public boolean isKingSafe() {
         //
         if(this.playback == true){
             return true;
         }
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
                        System.out.println((playerKingPosition/8+temp*i) +"\n" + (playerKingPosition%8+temp*j));
                        System.out.println(playerKingPosition + "Can be eaten by a queen or a bishop");
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
                    System.out.println("Can be eaten by a queen or a rook side");
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
                    System.out.println("Can be eaten by a queen or a rook above/below");
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
                        System.out.println("Can be eaten by a knight above/below");
                        return false;
                    }
                } catch (Exception excptn) {}
                try {
                    if ("n".equals(chessBoardPieces[playerKingPosition/8+i*2][playerKingPosition%8+j])) {
                        System.out.println("Can be eaten by a knight side");
                        return false;
                    }
                } catch (Exception excptn) {}
            }
        }
        //Pawn
        if (playerKingPosition>=16) {
            try {
                if ("p".equals(chessBoardPieces[playerKingPosition/8-1][playerKingPosition%8-1])) {
                    System.out.println("Can be eaten by a pawn");
                    return false;
                }
            } catch (Exception excptn) {}
            try {
                if ("p".equals(chessBoardPieces[playerKingPosition/8-1][playerKingPosition%8+1])) {
                    System.out.println("Can be eaten by a pawn");
                    return false;
                }
            } catch (Exception excptn) {}
            //King
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("k".equals(chessBoardPieces[playerKingPosition/8+i][playerKingPosition%8+j])) {
                                System.out.println("Can be eaten by a king");
                                return false;
                            }
                        } catch (Exception excptn) {}
                    }
                }
            }
        }
        return true;
    }
     
     public boolean isEnemyKingSafe() {
         
        //Bishop/Queen
        int temp = 1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(" ".equals(chessBoardPieces[enemyKingPosition/8+temp*i][enemyKingPosition%8+temp*j])) {
                        temp++;
                    }
                    if ("B".equals(chessBoardPieces[enemyKingPosition/8+temp*i][enemyKingPosition%8+temp*j]) ||
                            "Q".equals(chessBoardPieces[enemyKingPosition/8+temp*i][enemyKingPosition%8+temp*j])) {
                        return false;
                    }
                } catch (Exception excptn) {}
                temp = 1;
            }
        }
        //Rook/Queen
        for (int i=-1; i<=1; i+=2) {
            try {
                while(" ".equals(chessBoardPieces[enemyKingPosition/8][enemyKingPosition%8+temp*i])) {
                    temp++;
                }
                if ("R".equals(chessBoardPieces[enemyKingPosition/8][enemyKingPosition%8+temp*i]) ||
                        "Q".equals(chessBoardPieces[enemyKingPosition/8][enemyKingPosition%8+temp*i])) {
                    return false;
                }
            } catch (Exception excptn) {}
            temp=1;
            try {
                while(" ".equals(chessBoardPieces[enemyKingPosition/8+temp*i][enemyKingPosition%8])) {
                    temp++;
                }
                if ("R".equals(chessBoardPieces[enemyKingPosition/8+temp*i][enemyKingPosition%8]) ||
                        "Q".equals(chessBoardPieces[enemyKingPosition/8+temp*i][enemyKingPosition%8])) {
                    return false;
                }
            } catch (Exception excptn) {}
            temp=1;
        }
        //Knight
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("N".equals(chessBoardPieces[enemyKingPosition/8+i][enemyKingPosition%8+j*2])) {
                        return false;
                    }
                } catch (Exception excptn) {}
                try {
                    if ("N".equals(chessBoardPieces[enemyKingPosition/8+i*2][enemyKingPosition%8+j])) {
                        return false;
                    }
                } catch (Exception excptn) {}
            }
        }
        //Pawn
        if (48>=enemyKingPosition) {
            try {
                if ("P".equals(chessBoardPieces[enemyKingPosition/8+1][enemyKingPosition%8-1])) {
                    return false;
                }
            } catch (Exception excptn) {}
            try {
                if ("P".equals(chessBoardPieces[enemyKingPosition/8+1][enemyKingPosition%8+1])) {
                    return false;
                }
            } catch (Exception excptn) {}
            //King
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("K".equals(chessBoardPieces[enemyKingPosition/8+i][enemyKingPosition%8+j])) {
                                return false;
                            }
                        } catch (Exception excptn) {}
                    }
                }
            }
        }
        return true;
    }
  
     
    public void moveThePawn(String pawn, String src, String dest){
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
        if(possiblePawnMoves(pawn, src).contains(dest)){
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
        if(possibleEnemyPawnMoves(pawn, src).contains(dest)){
            if(destI == 7){
                String[] promUnits = {"q","r","b","n"};
                chessBoardPieces[destI][destJ] = promUnits[new Random().nextInt(4)];
            }else{
                chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            }
            chessBoardPieces[srcI][srcJ]= " ";
        }}
    }}
    
    public void moveTheKnight(String knight, String src, String dest){
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
        if(possibleKnightMoves(knight, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(knight.equals("n")){
        if(possibleEnemyKnightMoves(knight, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        }
    }
    
    public void moveTheBishop(String bishop, String src, String dest){
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
        if(possibleBishopMoves(bishop, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(bishop.equals("b")){
        if(possibleEnemyBishopMoves(bishop, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}}       
    }
    
    public void moveTheRook(String rook, String src, String dest){
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
        if(possibleRookMoves(rook, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(rook.equals("r")){
        if(possibleEnemyRookMoves(rook, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}}
    }
    
    public void moveTheQueen(String queen, String src, String dest){
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
        if(possibleQueenMoves(queen, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(queen.equals("q")){
        if(possibleEnemyQueenMoves(queen, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}}
    }
    
    public void moveTheKing(String king, String src, String dest){
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
        if(possibleKingMoves(king, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}
        if(king.equals("k")){
        if(possibleEnemyKingMoves(king, src).contains(dest)){
            chessBoardPieces[destI][destJ] = chessBoardPieces[srcI][srcJ];
            chessBoardPieces[srcI][srcJ]= " ";
        }}}
    }
         
    public void printMoves(Stack <String> possibleMoves){
        Stack<String> temp = new Stack();
            while (!possibleMoves.empty()) {

                String tmp = possibleMoves.pop();
                System.out.println(tmp);
                temp.push(tmp);
            }
            possibleMoves = temp;
    }
     
    public int unitCount(String chessPiece) {
        Stack<String> temp = new Stack();
        for (int row = 0; row < chessBoardPieces.length; ++row) {
            for (int column = 0; column < chessBoardPieces.length; ++column) {
                if (chessBoardPieces[row][column].equals(chessPiece)) {
                    temp.push(chessBoardPieces[row][column]);
                }
            }
        }
        return temp.size();
    }

    public void printChessBoard(String[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                //if(!(a[i][j].equals(" "))){ 
                System.out.print(a[i][j] + " ");
                /*}else{
                 System.out.print("-" + " ");*/
            }
            System.out.println();
        }
        System.out.println();
    }//}
     
    //working
     public Stack <String> possibleEnemyPawnMoves(String pawn, String source) {
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
        for (int j=-1; j<=1; j+=2) {
            try {//capture
                if (Character.isUpperCase(chessBoardPieces[pRow+1][pColumn+j].charAt(0)) && 48>=pPos) {
                    oldPiece = chessBoardPieces[pRow+1][pColumn+j];
                    oldPieceLocation = chessBoardLocations[pRow+1][pColumn+j];
                    chessBoardPieces[pRow][pColumn]=" ";
                    chessBoardPieces[pRow+1][pColumn+j]="p";
                    if (true) {
                        moves.push(oldPieceLocation);
                    }
                    chessBoardPieces[pRow][pColumn]="p";
                    chessBoardPieces[pRow+1][pColumn+j]=oldPiece;
                }
            } catch (Exception excptn) {}
            
            try {//promotion && capture
                if (Character.isUpperCase(chessBoardPieces[pRow+1][pColumn+j].charAt(0)) && pPos>48) {
                    String[] temp={"q","r","b","n"};
                    for (int k=0; k<4; k++) {
                        oldPiece=chessBoardPieces[pRow+1][pColumn+j];
                        oldPieceLocation = chessBoardLocations[pRow+1][pColumn+j];
                        chessBoardPieces[pRow][pColumn]=" ";
                        chessBoardPieces[pRow+1][pColumn+j]= temp[k];
                        if (true) {
                            moves.push(oldPieceLocation);
                        }
                        chessBoardPieces[pRow][pColumn]="p";
                        chessBoardPieces[pRow+1][pColumn+j]=oldPiece;
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
                if (true) {
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
                    if (true) {
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
                if (true) {
                    moves.push(oldPieceLocation);
                }
                chessBoardPieces[pRow][pColumn]="p";
                chessBoardPieces[pRow+2][pColumn]=oldPiece;
            }
        } catch (Exception excptn) {}
                return moves;
    }
     
     //working
     public Stack <String> possibleEnemyKnightMoves(String knight, String source) {
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
                        if (true) {
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
                        if (true) {
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
     
     //working
     public Stack <String> possibleEnemyBishopMoves(String bishop, String source) {
        if(!bishop.equalsIgnoreCase("b")){
            System.out.println("Not a bishop");
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
                        if (true) {
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
                        chessBoardPieces[pRow+temp*j][pColumn+temp*k]="b";
                        if (true) {
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
     
     //working
     public Stack <String> possibleEnemyQueenMoves(String queen, String source) {
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
                            if (true) {
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
                            if (true) {
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
     
     //working, re check
     public Stack <String> possibleEnemyKingMoves(String king, String source) {
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
                        if (true) {
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
    
     //working
     public Stack <String> possibleEnemyRookMoves(String rook, String source) {
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
                    if (true) {
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
                    if (true) {
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
                    if (true) {
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
                    if (true) {
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
  
     public String[][] getBoard(){
         return this.chessBoardPieces;
     }
     
     public static void switchTurn(){
         PlayerTurn = !PlayerTurn;
     }
     
     public void saveGameState(){
         this.chessGame.push(getBoard());
     }
     
     public boolean hasMadeMove(){
        int match = 0;
        String[][] tmp = this.chessGame.peek();
            for(int i = 0 ; i < 8 ; i++){
                for(int j = 0 ; j < 8 ; j++){
                    if(tmp[i][j].equals(this.chessBoardPieces[i][j]))
                        match++;
                }
            }
            if(match == 64){
                return true;
            }
            return false;
    }
     
     public void printGame(){
        Stack<String[][]> temp = new Stack<String[][]>();
            while (!this.chessGame.empty()) {

                String[][] tmp = this.chessGame.pop();
                printChessBoard(tmp);
                temp.push(tmp);
            }
            this.chessGame = temp;
    }
     
     public boolean isCheck(){
         while(!isKingSafe()){
              JOptionPane.showMessageDialog(null, "Check");
              return true;
         }
         while(!isKingSafe() && (possibleKingMoves("K", String.valueOf(playerKingPosition)).size() == 0)){
              JOptionPane.showMessageDialog(null, "Checkmate");
              System.exit(0);
         }
         return false;
     }//}
//}
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessrefurbished;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JFileChooser;

/**
 *
 * @author Threadcount
 */
public class Playback {

    Chess chessPlayback;
    Vector<String[][]> playedMoves = new Vector<String[][]>();

    public Playback() {
        chessPlayback = new Chess();
        chessPlayback.isPlayback();
    }

    //moveData[0]: intial position (if stated), moveData[1]: destination
    public static String[] getMoveData(String pgnMove) {
        String[] moveData = new String[2];
        if (pgnMove.length() == 2) {
            moveData[0] = ""; //intial pos
            moveData[1] = pgnMove; //destination
        }
        if (pgnMove.length() == 3) {
            if (Character.isUpperCase(pgnMove.charAt(0))) {
                moveData[0] = "";
                moveData[1] = pgnMove.substring(1);
            }
            if (Character.isLowerCase(pgnMove.charAt(0))) {
                moveData[0] = pgnMove.substring(0, 1);
                moveData[1] = pgnMove.substring(1);
            }
        }
        if (pgnMove.length() == 4){ 
            if(Character.isUpperCase(pgnMove.charAt(0))) {
                moveData[0] = pgnMove.substring(1, 2);
                moveData[1] = pgnMove.substring(2);;
        } if ((pgnMove.contains("="))) {
                moveData[0] = "";
                moveData[1] = pgnMove.substring(0,2);;
        }} 
        return moveData;
    }

    public static boolean isBetween(int value, int min, int max) {
        return ((value > min) && (value < max));
    }

    public static String[] PGNMoves(String pgn) {

        String pgnMoves[] = pgn.split("\\s+");
        int nums = 0;
        for (int i = 0; i < pgnMoves.length; i++) {
            if ((!pgnMoves[i].contains(".")) && (!pgnMoves[i].contains("0"))
                    && (!pgnMoves[i].contains("/"))) {
                nums++;
            }
        }
        String[] cleanedPGNMoves = new String[nums];
        int j = 0;

        for (int i = 0; i < pgnMoves.length; i++) { 
            if ((!pgnMoves[i].contains(".")) && (!pgnMoves[i].contains("0"))
                    && (!pgnMoves[i].contains("/"))) {
                cleanedPGNMoves[j] = pgnMoves[i];
                j++;
            }
        }
        String cleanerString;
        for (int i = 0; i < cleanedPGNMoves.length; i++) { 
            if (cleanedPGNMoves[i].contains("x")) {
                cleanerString = cleanedPGNMoves[i].replace("x", "");
                cleanedPGNMoves[i] = cleanerString;
            }
            if (cleanedPGNMoves[i].contains("+")) {
                cleanerString = cleanedPGNMoves[i].replace("+", "");
                cleanedPGNMoves[i] = cleanerString;
            }
        }
        return cleanedPGNMoves;
    }

    public String getSource(String piece, String pgnMove) {;
        String pieceDest = getMoveData(pgnMove)[1];
        String piecePos = getMoveData(pgnMove)[0];
        String pieceSrc = "Not Found";
        int srcI = 0, srcJ = 0;
        int sourceI = 0, sourceJ = 0;
        Stack<String> moves = new Stack();
        Stack<String> unitBrothersLocation = new Stack();
        Stack<Integer> foundAtI = new Stack();
        Stack<Integer> foundAtJ = new Stack();
        for (int ni = 0; ni < chessPlayback.chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessPlayback.chessBoardPieces.length; nj++) {
                if ((chessPlayback.chessBoardPieces[ni][nj].equals(piece))
                        && !unitBrothersLocation.contains(chessPlayback.chessBoardPieces[sourceI][sourceJ])) {
                    sourceI = ni;
                    sourceJ = nj;
                    unitBrothersLocation.push(chessPlayback.chessBoardLocations[sourceI][sourceJ]);
                    foundAtI.push(sourceI);
                    foundAtJ.push(sourceJ);
                }
            }
        }
        while (!unitBrothersLocation.isEmpty()) {
            pieceSrc = unitBrothersLocation.pop();
            srcI = foundAtI.pop();
            srcJ = foundAtJ.pop();
            if (piece.equalsIgnoreCase("p")) {

                if (piece.equals("p")) {
                    moves = chessPlayback.possibleEnemyPawnMoves(piece, pieceSrc);
                }
                if (piece.equals("P")) {
                    moves = chessPlayback.possiblePawnMoves(piece, pieceSrc);
                }
                if (moves.contains(pieceDest)) {
                    if (chessPlayback.chessBoardLocations[srcI][srcJ].startsWith(piecePos)
                            || piecePos.equals("")) {
                        pieceSrc = chessPlayback.chessBoardLocations[srcI][srcJ];
                        return pieceSrc;
                    }
                }
            }
            //Bishop
            if (piece.equalsIgnoreCase("b")) {
                if (piece.equals("b")) {
                    moves = chessPlayback.possibleEnemyBishopMoves(piece, pieceSrc);
                }
                if (piece.equals("B")) {
                    moves = chessPlayback.possibleBishopMoves(piece, pieceSrc);
                }
                if (moves.contains(pieceDest)) {
                    if (chessPlayback.chessBoardLocations[srcI][srcJ].startsWith(piecePos)
                            || piecePos.equals("")) {
                        pieceSrc = chessPlayback.chessBoardLocations[srcI][srcJ];
                        return pieceSrc;
                    }
                }
            }

            //Knight
            if (piece.equalsIgnoreCase("n")) {
                if (piece.equals("n")) {
                    moves = chessPlayback.possibleEnemyKnightMoves(piece, pieceSrc);
                }
                if (piece.equals("N")) {
                    moves = chessPlayback.possibleKnightMoves(piece, pieceSrc);
                }
                if (moves.contains(pieceDest)) {
                    if (chessPlayback.chessBoardLocations[srcI][srcJ].startsWith(piecePos)
                            || piecePos.equals("")) {
                        pieceSrc = chessPlayback.chessBoardLocations[srcI][srcJ];
                        return pieceSrc;
                    }
                }
            }

            //Rook
            if (piece.equalsIgnoreCase("r")) {
                if (piece.equals("r")) {
                    moves = chessPlayback.possibleEnemyRookMoves(piece, pieceSrc);
                }
                if (piece.equals("R")) {
                    moves = chessPlayback.possibleRookMoves(piece, pieceSrc);
                }
                if (moves.contains(pieceDest)) {
                    if (chessPlayback.chessBoardLocations[srcI][srcJ].startsWith(piecePos)
                            || piecePos.equals("")) {
                        pieceSrc = chessPlayback.chessBoardLocations[srcI][srcJ];
                        return pieceSrc;
                    }
                }
            }

            //Queen
            if (piece.equalsIgnoreCase("q")) {
                if (piece.equals("q")) {
                    moves = chessPlayback.possibleEnemyQueenMoves(piece, pieceSrc);
                }
                if (piece.equals("Q")) {
                    moves = chessPlayback.possibleQueenMoves(piece, pieceSrc);
                }
                if (moves.contains(pieceDest)) {
                    if (chessPlayback.chessBoardLocations[srcI][srcJ].startsWith(piecePos)
                            || piecePos.equals("")) {
                        pieceSrc = chessPlayback.chessBoardLocations[srcI][srcJ];
                        return pieceSrc;
                    }
                }
            }

            //King
            if (piece.equalsIgnoreCase("k")) {
                if (piece.equals("k")) {
                    moves = chessPlayback.possibleEnemyKingMoves(piece, pieceSrc);
                }
                if (piece.equals("K")) {
                    moves = chessPlayback.possibleKingMoves(piece, pieceSrc);
                }
                if (moves.contains(pieceDest)) {
                    if (chessPlayback.chessBoardLocations[srcI][srcJ].startsWith(piecePos)
                            || piecePos.equals("")) {
                        pieceSrc = chessPlayback.chessBoardLocations[srcI][srcJ];
                        return pieceSrc;
                    }
                }
            }
        }

        return pieceSrc;
    }

    public int[] getPieceLocation(String piece, String dest) {
        int[] temp = new int[2];
        for (int ni = 0; ni < chessPlayback.chessBoardPieces.length; ni++) {
            for (int nj = 0; nj < chessPlayback.chessBoardPieces.length; nj++) {
                try{
                if(Character.isUpperCase(piece.charAt(0))){    
                if (chessPlayback.chessBoardPieces[ni][nj].equals(piece)
                        && (chessPlayback.chessBoardLocations[ni-1][nj].equals(dest)
                        || (chessPlayback.chessBoardLocations[ni-1][nj-1].equals(dest))
                        ||chessPlayback.chessBoardLocations[ni-1][nj+1].equals(dest))) {
                    temp[0] = ni;
                    temp[1] = nj;
                }}
                if(Character.isLowerCase(piece.charAt(0))){    
                if (chessPlayback.chessBoardPieces[ni][nj].equals(piece)
                        && (chessPlayback.chessBoardLocations[ni+1][nj].equals(dest)
                        || (chessPlayback.chessBoardLocations[ni+1][nj-1].equals(dest))
                        ||chessPlayback.chessBoardLocations[ni+1][nj+1].equals(dest))) {
                    temp[0] = ni;
                    temp[1] = nj;
                }}
                }catch(Exception e){}
            }
        }
        return temp;
    }

    public String checkAnnouncer(String str) {
        String checker = str;
        if (str.endsWith("+")) {
            checker = str.replace("+", "");
            System.out.println("Check");
        }
        return checker;
    }

    public void Playback(String[] PGNMoves, double speed) {
        try {
            for (int m = 0; m < PGNMoves.length; m++) {//30; m++) {
                if(m == 0){
                    saveGameState();
                }
                System.out.println("Move " + (m + 1));
                System.out.println("White King at " + chessPlayback.getWhiteKPos());
                System.out.println("Black King at " + chessPlayback.getBlackKPos());
                if (m % 2 == 0) {
                    if (isBetween(PGNMoves[m].length(), 1, 4) && (Character.isLowerCase(PGNMoves[m].charAt(0)))) {
                        chessPlayback.moveThePawn("P", getSource("P", PGNMoves[m]), getMoveData(PGNMoves[m])[1]); //
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("B")) {
                        chessPlayback.moveTheBishop("B", getSource("B", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("R")) {
                        chessPlayback.moveTheRook("R", getSource("R", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("N")) {
                        chessPlayback.moveTheKnight("N", getSource("N", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("K")) {
                        chessPlayback.moveTheKing("K", getSource("K", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("Q")) {
                        chessPlayback.moveTheQueen("Q", getSource("Q", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (PGNMoves[m].equals("O-O")) {
                        chessPlayback.chessBoardPieces[7][4] = " ";
                        chessPlayback.chessBoardPieces[7][6] = "K";
                        chessPlayback.chessBoardPieces[7][7] = " ";
                        chessPlayback.chessBoardPieces[7][5] = "R";
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (PGNMoves[m].equals("O-O-O")) {
                        chessPlayback.chessBoardPieces[7][4] = " ";
                        chessPlayback.chessBoardPieces[7][2] = "K";
                        chessPlayback.chessBoardPieces[7][0] = " ";
                        chessPlayback.chessBoardPieces[7][3] = "R";
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (PGNMoves[m].contains("=")) {
                        chessPlayback.chessBoardPieces[getPieceLocation("P", getMoveData(PGNMoves[m])[1])[0]-1][getPieceLocation("P", getMoveData(PGNMoves[m])[1])[1]] = PGNMoves[m].substring(3);
                        chessPlayback.chessBoardPieces[getPieceLocation("P", getMoveData(PGNMoves[m])[1])[0]][getPieceLocation("P", getMoveData(PGNMoves[m])[1])[1]] = " ";                        
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                }
                //Black pieces
                if (m % 2 == 1) {
                    if (isBetween(PGNMoves[m].length(), 1, 4) && (Character.isLowerCase(PGNMoves[m].charAt(0)))) {
                        chessPlayback.moveThePawn("p", getSource("p", PGNMoves[m]), getMoveData(PGNMoves[m])[1]); //
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("B")) {
                        chessPlayback.moveTheBishop("b", getSource("b", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("R")) {
                        chessPlayback.moveTheRook("r", getSource("r", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("N")) {
                        chessPlayback.moveTheKnight("n", getSource("n", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("K")) {
                        chessPlayback.moveTheKing("k", getSource("k", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (isBetween(PGNMoves[m].length(), 2, 5) && PGNMoves[m].startsWith("Q")) {
                        chessPlayback.moveTheQueen("q", getSource("q", PGNMoves[m]), getMoveData(PGNMoves[m])[1]);
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (PGNMoves[m].equals("O-O")) {
                        chessPlayback.chessBoardPieces[0][4] = " ";
                        chessPlayback.chessBoardPieces[0][6] = "k";
                        chessPlayback.chessBoardPieces[0][7] = " ";
                        chessPlayback.chessBoardPieces[0][5] = "r";
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);

                    }
                    if (PGNMoves[m].equals("O-O-O")) {
                        chessPlayback.chessBoardPieces[0][4] = " ";
                        chessPlayback.chessBoardPieces[0][2] = "k";
                        chessPlayback.chessBoardPieces[0][0] = " ";
                        chessPlayback.chessBoardPieces[0][3] = "r";
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);
                    }
                    if (PGNMoves[m].contains("=")) {                       
                        chessPlayback.chessBoardPieces[getPieceLocation("p", getMoveData(PGNMoves[m])[1])[0]+1][getPieceLocation("p", getMoveData(PGNMoves[m])[1])[1]] = PGNMoves[m].substring(3).toLowerCase();
                        chessPlayback.chessBoardPieces[getPieceLocation("p", getMoveData(PGNMoves[m])[1])[0]][getPieceLocation("p", getMoveData(PGNMoves[m])[1])[1]] = " ";
                        chessPlayback.printChessBoard(chessPlayback.chessBoardPieces);
                        ;
                    }
                    
                }
                chessPlayback.getKings();
                saveGameState();
                Thread.sleep((long) (1000 / speed));

            }
        } catch (Exception e) {
        }
    }
    
    public void playMove(int i){
        this.chessPlayback.chessBoardPieces = this.playedMoves.get(i);
        ChessGUI.updateBoard(this.chessPlayback);
    }
    
    public void saveGameState(){
        String[][] temp = new String[8][8];
        for(int i = 0 ; i < 8 ; i++){
            for(int j = 0 ; j < 8 ; j++){
                temp[i][j] = chessPlayback.chessBoardPieces[i][j];
            }
        }
        playedMoves.add(temp);
    }


    }

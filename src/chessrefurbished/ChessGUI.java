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
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.Stack;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChessGUI extends JPanel {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private static JButton[][] chessBoardSquares = new JButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private final JLabel message = new JLabel(
            "Welcome to Chess");
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 0, KING = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
        ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
    };
    public static final int BLACK = 0, WHITE = 1;
    private JTextArea Score = new JTextArea();
    private JScrollPane Scroll = new JScrollPane(Score);
    private int chosenX;
    private int chosenY;
    private Chess chess;
    private Playback plbck;
    private static int move = 0;
    private static boolean playbackSelected = false;
    private static boolean unitSelected = false;
    private static String humanColor = "";
    private static BufferedImage[] bia = new BufferedImage[12];
    private static boolean playerTurn;

    public ChessGUI() {
        initializeGui();
    }

    public final void initializeGui() {
        // create the images for the chess pieces
        createImages();

        javax.swing.Timer autoplayTimer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plbck.playMove(++move);
            }
        });

        // set up the main GUI
        Scroll.getVerticalScrollBar();
        Score.setEditable(false);
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        Action newGameAction = new AbstractAction(" New Game ") {

            @Override
            public void actionPerformed(ActionEvent e) {
                String[] Color = new String[]{"White", "Black"};
                int reply = JOptionPane.showOptionDialog(null, "What side would you take?", "Choose color",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, Color, Color[0]);
                if (reply == 0) {
                    humanColor = Color[0];
                    playerTurn = true;
                    System.out.println(humanColor);
                }
                if (reply == 1) {
                    humanColor = Color[1];
                    playerTurn = false;
                    System.out.println(humanColor);
                }
                Score.setText("");
                resetGame();
                setupNewGame();
                chess = new Chess();
                if (playerTurn == false) {
                    makeRandomMove(chess);
                    updateBoard(chess);
                    chess.isCheck();
                    playerTurn = true;
                }
            }
        };
        tools.add(newGameAction);
        Action watchGameAction = new AbstractAction(" Watch Game ") {

            public void actionPerformed(ActionEvent e) {
                playbackSelected = true;
                String line = new String();
                String pgn = new String();
                File selectedFile = null;
                JFileChooser browseFile = new JFileChooser();
                FileNameExtensionFilter pgnFilter = new FileNameExtensionFilter("PGN Files", "pgn", "PGN");
                browseFile.setFileFilter(pgnFilter);
                browseFile.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = browseFile.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = browseFile.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                        StringBuilder sb = new StringBuilder();
                        line = br.readLine();

                        while (line != null) {
                            sb.append(line);
                            sb.append(System.lineSeparator());
                            line = br.readLine();
                        }
                        pgn = sb.toString();
                        br.close();
                        resetGame();
                        humanColor = "White";
                        setupNewGame();
                        move = 0;
                        plbck = new Playback();
                        plbck.Playback(plbck.PGNMoves(pgn), 1000);
                    } catch (Exception excptn) {
                    }
                }
            }
        };
        tools.addSeparator();
        tools.add(watchGameAction);
        Action autoplayAction = new AbstractAction(" Play ") {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (playbackSelected == true) {
                        autoplayTimer.start();
                    }
                } catch (ArrayIndexOutOfBoundsException aioob) {
                    JOptionPane.showMessageDialog(null, "Game finished");
                }
            }
        };
        tools.add(autoplayAction);
        Action stopAction = new AbstractAction(" Pause ") {

            public void actionPerformed(ActionEvent e) {
                if (playbackSelected == true) {
                    autoplayTimer.stop();
                }
            }
        };
        tools.add(stopAction);
        Action nextMoveAction = new AbstractAction(" Next Move ") {
            public void actionPerformed(ActionEvent e) {
                if (playbackSelected == true) {
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        autoplayTimer.stop();
                                        if (plbck.playedMoves.size() >= move) {
                                            plbck.playMove(++move);
                                        }
                                    } catch (ArrayIndexOutOfBoundsException aioob) {
                                        JOptionPane.showMessageDialog(null, "Game finished");
                                    }
                                }
                            },
                            0//1000
                    );
                }
            }
        };

        tools.add(nextMoveAction);
        Action undoMoveAction = new AbstractAction(" Undo Move ") {
            public void actionPerformed(ActionEvent e) {
                if (playbackSelected == true) {
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    autoplayTimer.stop();
                                    if (move > 0) {
                                        plbck.playMove(--move);
                                    }
                                    if (move == 0) {
                                        plbck.playMove(0);
                                    }
                                }
                            },
                            0//1000
                    );
                }
            }
        };

        tools.add(undoMoveAction);
        Action speedAction = new AbstractAction(" Speed ") {

            public void actionPerformed(ActionEvent e) {
                double d = 0;
                try {
                    if (playbackSelected == true) {
                        autoplayTimer.stop();
                        String code = JOptionPane.showInputDialog(
                                null,
                                "Enter the desired speed ",
                                "Playback Speed",
                                JOptionPane.INFORMATION_MESSAGE);
                        try{
                        d = Double.parseDouble(code);
                        }catch(NumberFormatException nfe){
                            JOptionPane.showMessageDialog(chessBoard, "Not a valid number, please try again");
                        }
                        if(d > 0){
                        autoplayTimer.setDelay((int) (1000 / d));
                        autoplayTimer.start();
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException aioob) {
                    JOptionPane.showMessageDialog(null, "Game finished");
                }
            }
        };
        //tools.add(new JButton("Restore"));
        tools.add(speedAction);
        tools.addSeparator();
        //tools.add(new JButton("Resign"));
        Action endGameAction = new AbstractAction(" Resign ") {

            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(chessBoard, "Are you sure you want to resign");
                if (reply == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(chessBoard, "You have resigned");
                    System.exit(0);
                }
                if (reply == JOptionPane.NO_OPTION) { //System.exit(0);
                    JOptionPane.showMessageDialog(chessBoard, "Live to fight another day");
                }
            }
        };
        tools.add(endGameAction);
        tools.addSeparator();
        tools.add(message);

        Score.setBounds(50, 50, 684 / 5, 71 * 9);
        Score.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK),
                new EmptyBorder(10, 10, 10, 10)
        ));
        gui.add(Scroll, BorderLayout.WEST);
        gui.add(Score, BorderLayout.WEST);
        gui.add(new JLabel("Moves"), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 9)) {

            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int) d.getWidth(), (int) d.getHeight());
                } else if (c != null
                        && c.getWidth() > d.getWidth()
                        && c.getHeight() > d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w > h ? h : w);
                return new Dimension(s, s);
            }
        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 8),
                new LineBorder(Color.BLACK)
        ));
        // Set the BG to be ochre
        Color ochre = new Color(204, 119, 34);
        chessBoard.setBackground(ochre);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(ochre);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        //Main core
        ActionListener actionListener = new ActionListener() {
            int moveCounter = 0;
            JButton buttonSrc, buttonDest;
            int destY1, destY2;
            int destX1, destX2;
            String chosenPiece;
            String chosenPieceSrc;
            String chosenPieceDest;
            boolean madeMove = false;

            //Making human move
            public void actionPerformed(ActionEvent actionEvent) {
                //chess.isCheck();
                chess.getKings();
                if (madeMove == false) {
                    if (unitSelected == false) {
                        moveCounter = 0;
                        buttonSrc = (JButton) actionEvent.getSource();
                        for (JButton[] dim2 : chessBoardSquares) {
                            for (JButton dim1 : dim2) {
                                if (dim1 == buttonSrc) {
                                    chosenX = (((JButton) dim1).getX() / 71) - 1; //row
                                    chosenY = (((JButton) dim1).getY() / 71) - 1; //column
                                }
                            }
                        }
                        destX1 = (buttonSrc.getX() / 71) - 1;
                        destY1 = (buttonSrc.getY() / 71) - 1;
                        chosenPiece = chess.chessBoardPieces[chosenY][chosenX];
                        chosenPieceSrc = chess.chessBoardLocations[chosenY][chosenX];
                        unitSelected = true;
                        System.out.println(chosenPiece);
                        System.out.println(chosenPieceSrc);
                        System.out.println("Selected " + chosenPiece);

                    } else {
                        buttonDest = (JButton) actionEvent.getSource();
                        String labelDest = buttonDest.getText();
                        for (JButton[] dim22 : chessBoardSquares) {
                            for (JButton dim1 : dim22) {
                                if (dim1 == buttonDest) {
                                    chosenX = (((JButton) dim1).getX() / 71) - 1; //row
                                    chosenY = (((JButton) dim1).getY() / 71) - 1; //column
                                }
                            }
                        }
                        destX2 = (buttonDest.getX() / 71) - 1;
                        destY2 = (buttonDest.getY() / 71) - 1;
                        chosenPieceDest = chess.chessBoardLocations[destY2][destX2];
                        unitSelected = false;
                        System.out.println(chosenPiece);
                        System.out.println(chosenPieceSrc);
                        System.out.println(chosenPieceDest);
                        if (chosenPiece.equals("P")) {
                            Stack<String> moves = chess.possiblePawnMoves(chosenPiece, chosenPieceSrc);
                            if ((moves.contains(chosenPieceDest))) {
                                chess.moveThePawn(chosenPiece, chosenPieceSrc, chosenPieceDest);
                                String temp = Score.getText() + "\n";
                                Score.setText(temp + chosenPieceDest);
                                moveCounter = 1;
                            }
                        }
                        if (chosenPiece.equals("N")) {
                            Stack<String> moves = chess.possibleKnightMoves(chosenPiece, chosenPieceSrc);
                            if ((moves.contains(chosenPieceDest))) {
                                chess.moveTheKnight(chosenPiece, chosenPieceSrc, chosenPieceDest);
                                String temp = Score.getText() + "\n";
                                Score.setText(temp + "N" + chosenPieceDest);
                                moveCounter = 1;
                            }
                        }
                        if (chosenPiece.equals("B")) {
                            Stack<String> moves = chess.possibleBishopMoves(chosenPiece, chosenPieceSrc);
                            if ((moves.contains(chosenPieceDest))) {
                                chess.moveTheBishop(chosenPiece, chosenPieceSrc, chosenPieceDest);
                                String temp = Score.getText() + "\n";
                                Score.setText(temp + "B" + chosenPieceDest);
                                moveCounter = 1;
                            }
                        }
                        if (chosenPiece.equals("R")) {
                            Stack<String> moves = chess.possibleRookMoves(chosenPiece, chosenPieceSrc);
                            if ((moves.contains(chosenPieceDest))) {
                                chess.moveTheRook(chosenPiece, chosenPieceSrc, chosenPieceDest);
                                String temp = Score.getText() + "\n";
                                Score.setText(temp + "R" + chosenPieceDest);
                                moveCounter = 1;
                            }
                        }
                        if (chosenPiece.equals("Q")) {
                            Stack<String> moves = chess.possibleQueenMoves(chosenPiece, chosenPieceSrc);
                            if ((moves.contains(chosenPieceDest))) {
                                chess.moveTheQueen(chosenPiece, chosenPieceSrc, chosenPieceDest);
                                String temp = Score.getText() + "\n";
                                Score.setText(temp + "Q" + chosenPieceDest);
                                moveCounter = 1;
                            }
                        }
                        if (chosenPiece.equals("K")) {
                            Stack<String> moves = chess.possibleKingMoves(chosenPiece, chosenPieceSrc);
                            if ((moves.contains(chosenPieceDest))) {
                                chess.moveTheKing(chosenPiece, chosenPieceSrc, chosenPieceDest);
                                String temp = Score.getText() + "\n";
                                Score.setText(temp + "K" + chosenPieceDest);
                                moveCounter = 1;
                            }
                        }
                        if (moveCounter == 1) {//unitSelected == false){;
                            updateBoard(chess);
                            chess.isCheck();
                            madeMove = !madeMove;
                        }
                        System.out.println("Moved " + chosenPiece);
                        chess.printChessBoard(chess.chessBoardPieces);
                    }
                    //
                }
                if (chess.unitCount("k") == 0) {
                    JOptionPane.showMessageDialog(null, "Player has won");
                }
                if (chess.unitCount("K") == 0) {
                    JOptionPane.showMessageDialog(null, "Computer has won");
                }
                if (madeMove == true) {
                    //playerTurn = true;
                    chess.isCheck();
                    makeRandomMove(chess);
                    chess.isCheck();
                    updateBoard(chess);
                    if (chess.unitCount("k") == 0) {
                        JOptionPane.showMessageDialog(null, "Player has won");
                    }
                    if (chess.unitCount("K") == 0) {
                        JOptionPane.showMessageDialog(null, "Computer has won");
                    }
                    chess.printChessBoard(chess.chessBoardPieces);
                    System.out.println("I made a random move");
                    madeMove = !madeMove;

                }
            }
        };

        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0;
                ii < chessBoardSquares.length;
                ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.addActionListener(actionListener);
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.GRAY);
                }
                chessBoardSquares[jj][ii] = b;
            }
        }

        chessBoard.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX());
                System.out.println(e.getY());
            }
        }
        );

        /*
         * fill the chess board
         */
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9 - (ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }

    public final JComponent getGui() {
        return gui;
    }

    private final void createImages() {
        BufferedImage bi;
        try {
            bi = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/ChessPieces.png"));
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(
                            jj * 64, ii * 64, 64, 64);
                }
            }
            bia[0] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/Blackp.png"));
            bia[1] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/WhiteP.png"));
            bia[2] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/Blackb.png"));
            bia[3] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/WhiteB.png"));
            bia[4] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/Blackr.png"));
            bia[5] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/WhiteR.png"));
            bia[6] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/Blackq.png"));
            bia[7] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/WhiteQ.png"));
            bia[8] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/Blackm.png"));
            bia[9] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/WhiteM.png"));
            bia[10] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/Blackk.png"));
            bia[11] = ImageIO.read(getClass().getResourceAsStream("/ChssPcs/WhiteK.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private final void setupNewGame() {
        message.setText("Make your move!");
        // set up the black pieces
        if (humanColor.equalsIgnoreCase("White")) {
            for (int ii = 0; ii < STARTING_ROW.length; ii++) {
                chessBoardSquares[ii][0].setIcon(new ImageIcon(
                        chessPieceImages[BLACK][STARTING_ROW[ii]]));
            }
            for (int ii = 0; ii < STARTING_ROW.length; ii++) {
                chessBoardSquares[ii][1].setIcon(new ImageIcon(
                        chessPieceImages[BLACK][PAWN]));
            }
            // set up the white pieces
            for (int ii = 0; ii < STARTING_ROW.length; ii++) {
                chessBoardSquares[ii][6].setIcon(new ImageIcon(
                        chessPieceImages[WHITE][PAWN]));
            }
            for (int ii = 0; ii < STARTING_ROW.length; ii++) {
                chessBoardSquares[ii][7].setIcon(new ImageIcon(
                        chessPieceImages[WHITE][STARTING_ROW[ii]]));
            }
        } else {
            for (int ii = 0; ii < STARTING_ROW.length; ii++) {
                chessBoardSquares[ii][0].setIcon(new ImageIcon(
                        chessPieceImages[WHITE][STARTING_ROW[ii]]));
            }
            for (int ii = 0; ii < STARTING_ROW.length; ii++) {
                chessBoardSquares[ii][1].setIcon(new ImageIcon(
                        chessPieceImages[WHITE][PAWN]));
            }
            // set up the white pieces
            for (int ii = 0; ii < STARTING_ROW.length; ii++) {
                chessBoardSquares[ii][6].setIcon(new ImageIcon(
                        chessPieceImages[BLACK][PAWN]));
            }
            for (int ii = 0; ii < STARTING_ROW.length; ii++) {
                chessBoardSquares[ii][7].setIcon(new ImageIcon(
                        chessPieceImages[BLACK][STARTING_ROW[ii]]));
            }
        }
    }

    public static void updateBoard(Chess chess) {
        BufferedImage bi;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!humanColor.equalsIgnoreCase("White")) {
                    try {
                        if (chess.chessBoardPieces[i][j].equals("P")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[0]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("p")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[1]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("B")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[2]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("b")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[3]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("R")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[4]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("r")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[5]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("Q")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[6]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("q")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[7]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("K")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[8]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("k")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[9]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("N")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[10]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("n")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[11]));
                        }
                        if (chess.chessBoardPieces[i][j].equals(" ")) {
                            chessBoardSquares[j][i].setIcon(null);
                        }
                    } catch (Exception excptn) {
                    }
                } else {
                    try {
                        if (chess.chessBoardPieces[i][j].equals("P")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[1]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("p")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[0]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("B")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[3]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("b")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[2]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("R")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[5]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("r")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[4]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("Q")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[7]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("q")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[6]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("K")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[9]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("k")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[8]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("N")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[11]));
                        }
                        if (chess.chessBoardPieces[i][j].equals("n")) {
                            chessBoardSquares[j][i].setIcon(new ImageIcon(bia[10]));
                        }
                        if (chess.chessBoardPieces[i][j].equals(" ")) {
                            chessBoardSquares[j][i].setIcon(null);
                        }
                    } catch (Exception excptn) {
                    }
                }
            }
        }
    }

    private void checkStatus() {
        chess.isCheck();
    }

    private final void resetGame() {
        for (int w = 0; w < 8; w++) {
            for (int k = 0; k < 8; k++) {
                chessBoardSquares[w][k].setIcon(null);
            }
        }
    }

    private void makeRandomMove(Chess chess) {
        Random rand = new Random();
        int randI = rand.nextInt(8);
        int randJ = rand.nextInt(8);
        boolean unitFound = false;
        String chosenPiece = chess.chessBoardPieces[randI][randJ];
        String chosenPieceSrc = "";
        String chosenPieceDest = "";
        try {
            while (unitFound == false) {
                randI = rand.nextInt(8);
                randJ = rand.nextInt(8);
                chosenPiece = chess.chessBoardPieces[randI][randJ];
                chosenPieceSrc = chess.chessBoardLocations[randI][randJ];
                if (chosenPiece.equals("p")
                        && !chess.possibleEnemyPawnMoves(chosenPiece, chosenPieceSrc).isEmpty()) {
                    unitFound = true;
                }
                if (chosenPiece.equals("b")
                        && !chess.possibleEnemyBishopMoves(chosenPiece, chosenPieceSrc).isEmpty()) {
                    unitFound = true;
                }
                if (chosenPiece.equals("r")
                        && !chess.possibleEnemyRookMoves(chosenPiece, chosenPieceSrc).isEmpty()) {
                    unitFound = true;
                }
                if (chosenPiece.equals("q")
                        && !chess.possibleEnemyQueenMoves(chosenPiece, chosenPieceSrc).isEmpty()) {
                    unitFound = true;
                }
                if (chosenPiece.equals("n")
                        && !chess.possibleEnemyKnightMoves(chosenPiece, chosenPieceSrc).isEmpty()) {
                    unitFound = true;
                }
                if (chosenPiece.equals("k")
                        && !chess.possibleEnemyKingMoves(chosenPiece, chosenPieceSrc).isEmpty()) {
                    unitFound = true;
                }
            }
            if (chosenPiece.equals("p")) {
                Stack<String> moves = chess.possibleEnemyPawnMoves(chosenPiece, chosenPieceSrc);
                chosenPieceDest = moves.pop();
                chess.moveThePawn(chosenPiece, chosenPieceSrc, chosenPieceDest);
                String temp = Score.getText() + "\n";
                Score.setText(temp + chosenPieceDest);
            }
            if (chosenPiece.equals("b")) {
                Stack<String> moves = chess.possibleEnemyBishopMoves(chosenPiece, chosenPieceSrc);
                chosenPieceDest = moves.pop();
                chess.moveTheBishop(chosenPiece, chosenPieceSrc, chosenPieceDest);
                String temp = Score.getText() + "\n";
                Score.setText(temp + "b" + chosenPieceDest);
            }
            if (chosenPiece.equals("n")) {
                Stack<String> moves = chess.possibleEnemyKnightMoves(chosenPiece, chosenPieceSrc);
                chosenPieceDest = moves.pop();
                chess.moveTheKnight(chosenPiece, chosenPieceSrc, chosenPieceDest);
                String temp = Score.getText() + "\n";
                Score.setText(temp + "n" + chosenPieceDest);
            }
            if (chosenPiece.equals("r")) {
                Stack<String> moves = chess.possibleEnemyRookMoves(chosenPiece, chosenPieceSrc);
                chosenPieceDest = moves.pop();
                chess.moveTheRook(chosenPiece, chosenPieceSrc, chosenPieceDest);
                String temp = Score.getText() + "\n";
                Score.setText(temp + "r" + chosenPieceDest);
                //}
            }
            if (chosenPiece.equals("q")) {
                Stack<String> moves = chess.possibleEnemyQueenMoves(chosenPiece, chosenPieceSrc);
                chosenPieceDest = moves.pop();
                chess.moveTheQueen(chosenPiece, chosenPieceSrc, chosenPieceDest);
                String temp = Score.getText() + "\n";
                Score.setText(temp + "q" + chosenPieceDest);
                //}
            }
            if (chosenPiece.equals("k")) {
                Stack<String> moves = chess.possibleEnemyKingMoves(chosenPiece, chosenPieceSrc);
                chosenPieceDest = moves.pop();
                chess.moveTheKing(chosenPiece, chosenPieceSrc, chosenPieceDest);
                String temp = Score.getText() + "\n";
                Score.setText(temp + "k" + chosenPieceDest);
                //}
            }
            chess.saveGameState();
        } catch (Exception excptn) {
        }
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            public void run() {
                ChessGUI cg = new ChessGUI();
                JFrame f = new JFrame("GZ-Chess");
                f.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(
                        "ChssPcs/Chess.png")));
                f.add(cg.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.pack();
                f.setResizable(false);
                f.setSize(1000, 731);
                f.setVisible(true);
                System.out.println(f.getSize());
            }
        };

        SwingUtilities.invokeLater(r);
    }
}

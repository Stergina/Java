import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.util.ArrayList;

/* Graphical User Interface menu */
public class Main extends JPanel implements MouseListener {
    /* Variable Declaration */
    private static int Black = 1;
    private static int White = 2;
    static int playingperson;
    private int[][] pieces;
    private int scorewhite, scoreblack, pawn;
    public static int level, firstPlay;
    static int noGames = 0;
    private Rectangle[][] boxes;
    private char[][] board, tempboard;
    char myPawn, compawn;
    State previousState, newState;
    Game newGame;
    static JFrame window;
    public boolean run = true;
    public static boolean noMoreMoves = false;
    boolean wrongMove = false;

    /* An ArrayList to store every state of the board */
    public ArrayList < State > gamesList;

    public void paint(Graphics windowsgraphics) {
        windowsgraphics.setColor(Color.green); //Background color of the board
        windowsgraphics.fillRect(0, 0, 800, 800); //Fills the frame
        windowsgraphics.setColor(Color.black); //Grid
        Graphics2D windowsgraphics2 = (Graphics2D) windowsgraphics;
        windowsgraphics.setFont(new Font("Arial", Font.BOLD, 48));
        scorewhite = 0;
        scoreblack = 0;

        //8x8 Board creation with black lines drawn
        for (int r = 0; r < boxes.length; r++) {
            for (int c = 0; c < boxes.length; c++) {
                windowsgraphics2.draw(boxes[r][c]);
            }
        }

        //Disc creation
        for (int r = 0; r < pieces.length; r++)
            for (int c = 0; c < pieces.length; c++) //x8 times as the length of the reversi board
                if (pieces[r][c] > 0) {
                    if (pieces[r][c] == Black) {
                        windowsgraphics.setColor(Color.black);
                        scoreblack++;
                    } else if (pieces[r][c] == White) {
                        windowsgraphics.setColor(Color.white);
                        scorewhite++;
                    }
                    Rectangle box = boxes[r][c];
                    windowsgraphics.fillOval(box.x + 7, box.y + 7, box.width - 15, box.height - 15); //adding pieces in boxes oval shaped
                }

        //Status, score and coordinates
        windowsgraphics.setColor(Color.black);
        windowsgraphics.setFont(new Font("Arial", Font.BOLD, 30));
        windowsgraphics.drawString((playingperson == Black ? "Black" : "White") + "'s turn.", 20, 30); //Users turn then use a black disc else use white
        windowsgraphics.drawString("White : " + scorewhite + "  VS.  " + "Black : " + scoreblack, 250, 30); //Draws the score box
        windowsgraphics.setColor(Color.darkGray);
        windowsgraphics.setFont(new Font("Arial", Font.BOLD, 30));
        windowsgraphics.drawString("A       B      C      D      E      F      G      H", 120, 73); //Draws coordinates
        windowsgraphics.drawString("A       B      C      D      E      F      G      H", 120, 665); //Draws coordinates
        windowsgraphics.drawString("1", 80, 120);
        windowsgraphics.drawString("1", 665, 120);
        windowsgraphics.drawString("2", 80, 190);
        windowsgraphics.drawString("2", 665, 190);
        windowsgraphics.drawString("3", 80, 260);
        windowsgraphics.drawString("3", 665, 260);
        windowsgraphics.drawString("4", 80, 330);
        windowsgraphics.drawString("4", 665, 330);
        windowsgraphics.drawString("5", 80, 400);
        windowsgraphics.drawString("5", 665, 400);
        windowsgraphics.drawString("6", 80, 470);
        windowsgraphics.drawString("6", 665, 470);
        windowsgraphics.drawString("7", 80, 540);
        windowsgraphics.drawString("7", 665, 540);
        windowsgraphics.drawString("8", 80, 610);
        windowsgraphics.drawString("8", 665, 610);

    }

    /* Setting the depth level */
    public void setLevel(int level) {
        this.level = level;
    }

    /*The messages function asks the player if he wants to play first and the level of difficulty */
    public static void messages() {
        int orderPlay = JOptionPane.showConfirmDialog(null, "Would you like to play first?", "Choose turns",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (orderPlay == JOptionPane.YES_OPTION) {
            firstPlay = 0;
            JOptionPane.showMessageDialog(window, "You have the black discs!");
            System.out.println("You have the black discs!");
            System.out.println();
        } else if (orderPlay == JOptionPane.NO_OPTION) {
            firstPlay = 1;
            JOptionPane.showMessageDialog(window, "You have the white discs!");
            System.out.println("You have the white discs!");
            System.out.println();
        }

        if (firstPlay == 0) {
            playingperson = Black;
        } else {
            playingperson = White;
        }

        String[] choices = new String[] {
            "Easy",
            "Medium",
            "Hard"
        };
        String str = (String) JOptionPane.showInputDialog(
            null,
            "Please choose the desired level of difficulty:",
            "Difficulty",
            JOptionPane.PLAIN_MESSAGE,
            null,
            choices,
            choices[0]);
        int level1;
        if (str.equals("Easy")) {
            level1 = 1;
            level = level1;
        } else if (str.equals("Medium")) {
            level1 = 2;
            level = level1;
        } else if (str.equals("Hard")) {
            level1 = 3;
            level = level1;
        }
    }

    /* Main */
    public static void Main(String[] args) {
        messages();
        window = new JFrame("The Game Of Reversi(Othello)");
        window.setSize(770, 770);
        window.getContentPane().add(new Main());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    /* Main constructor */
    public Main() {
        addMouseListener(this);
        boxes = new Rectangle[8][8];
        pieces = new int[8][8];
        board = new char[8][8];
        tempboard = new char[8][8];
        for (int r = 0; r < boxes.length; r++)
            for (int c = 0; c < boxes.length; c++) {
                boxes[r][c] = new Rectangle(100 + c * 70, 76 + r * 70, 70, 70); //Boxes with of this width and height
            }
        pieces[3][3] = 1; //White
        pieces[3][4] = 2; //Black
        pieces[4][3] = 2;
        pieces[4][4] = 1;
        gamesList = new ArrayList < State > ();
        newGame = new Game();
        State initialstate = newGame.getBoard();
        if (level > 1)
            newGame.setLevel(level);
        tempboard = initialstate.getReversi();
        initialstate.printBoard();
        System.out.println();
        gamesList.add(initialstate);
        if (firstPlay == 1 && noGames == 0) {
            char tempPawn = 'X';
            State white = gamesList.get(gamesList.size() - 1);
            noGames++;
            State s1 = newGame.playerMove(white, 2, 4, tempPawn);
            board = s1.getReversi();
            pieces = getPieces(board);
            s1.printBoard();
            gamesList.add(white);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            window.repaint();
        }
    }

    public void mousePressed(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        String player;
        char oppositePawn;
        if (firstPlay == 0) {
            playingperson = Black;
        } else {
            playingperson = White;
        }
        previousState = gamesList.get(gamesList.size() - 1);
        State tempState = new State(previousState.getReversi());
        String mssg = " ";
        for (int r = 0; r < boxes.length; r++)
            for (int c = 0; c < boxes.length; c++)
                if (boxes[r][c].contains(evt.getPoint())) {
                    pawn = playingperson;
                    if (pawn == Black) {
                        myPawn = 'X';
                        oppositePawn = 'O';
                    } else {
                        myPawn = 'O';
                        oppositePawn = 'X';
                    }
                    if (pieces[r][c] == 0) {
                        newState = newGame.playerMove(previousState, r, c, myPawn);
                        if (newState == null) {
                            wrongMove = true;
                            if (playingperson == 1) {
                                player = "Black";
                            } else {
                                player = "White";
                            }
                            JLabel wrongMove = new JLabel("Non eligible move for " + player + " discs." + " Try again!", JLabel.CENTER);
                            wrongMove.setAlignmentX(0);
                            wrongMove.setAlignmentY(0);
                            JFrame window2 = new JFrame("Non eligible move!");
                            window2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            window2.setSize(300, 300);
                            window2.add(wrongMove);
                            window2.setLocation(300, 300);
                            window2.setVisible(true);
                        }
                        if (newState != null) {
                            if (checkFinal(newState)) {
                                int comScore = newState.computerScore(myPawn);
                                int playerScore = newState.computerScore(myPawn);
                                JLabel over = new JLabel("Game is over!" + " Black: " + playerScore + ", White: " + comScore);
                                over.setAlignmentX(60);
                                over.setAlignmentY(60);
                                JFrame window5 = new JFrame("Game over!");
                                window5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                window5.setSize(250, 250);
                                window5.add(over);
                                window5.setLocation(300, 300);
                                window5.setVisible(true);
                            }
                            System.out.println();
                            newState.printBoard();
                            gamesList.add(newState);
                            System.out.println();
                            board = newState.getReversi();
                            tempboard = board;
                            wrongMove = false;
                            noMoreMoves = newState.no_more_moves(board, myPawn);
                        } else {
                            newState = tempState;
                            wrongMove = true;
                            System.out.println();
                            newState.printBoard();
                            System.out.println();
                            System.out.println();
                            newState.printBoard();
                            gamesList.add(tempState);
                            board = newState.getReversi();
                        }
                        pieces = getPieces(board);
                        if (playingperson == 1)
                            mssg = "Black";
                        if (noMoreMoves) {
                            newState = newGame.oppositeMove(newState, oppositePawn); //calls the rival
                            JLabel nomove = new JLabel("No more moves for " + mssg + " discs!", JLabel.CENTER);
                            nomove.setAlignmentX(60);
                            nomove.setAlignmentY(60);
                            JFrame window2 = new JFrame("No more moves!");
                            window2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            window2.setSize(300, 300);
                            window2.add(nomove);
                            window2.setLocation(300, 300);
                            window2.setVisible(true);
                            if (newState != null)
                                gamesList.add(newState);
                        }
                    } else {
                        wrongMove = true;
                        JLabel wrongMove1 = new JLabel("Move on occupied cell. Try again!", JLabel.CENTER);
                        wrongMove1.setAlignmentX(0);
                        wrongMove1.setAlignmentY(0);
                        JFrame window2 = new JFrame("Non eligible move!");
                        window2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        window2.setSize(300, 300);
                        window2.add(wrongMove1);
                        window2.setLocation(300, 300);
                        window2.setVisible(true);
                    }
                    if (firstPlay == 1)
                        playingperson = White;
                    window.repaint();
                    return;
                }
    }

    /* The mouseReleased function performs players' mouse clicks */
    public void mouseReleased(MouseEvent evt) {
        char opposite;
        if (firstPlay == 0) {
            playingperson = White;
        } else {
            playingperson = Black;
        }
        pawn = playingperson;
        if (wrongMove == false) {
            System.out.println();
            System.out.println("Computer is playing!");
            System.out.println();
            if (pawn == White) {
                opposite = 'O';
            } else {
                opposite = 'X';
            }
            State state;
            state = gamesList.get(gamesList.size() - 1);
            state.printBoard();
            System.out.println(" ");
            System.out.println(" ");
            newState = newGame.oppositeMove(state, opposite);
            if (newState != null) {
                gamesList.add(newState);
                board = newState.getReversi();
                newState.printBoard();
                board = newState.getReversi();
                pieces = getPieces(board);
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (checkFinal(newState)) {
                    int comScore = newState.computerScore(opposite);
                    int playerScore = newState.computerScore(myPawn);
                    if (playerScore > comScore) {
                        JOptionPane.showMessageDialog(window, "You win!");
                    } else {
                        JOptionPane.showMessageDialog(window, "You lose!");
                    }
                }
                if (firstPlay == 1) {
                    playingperson = White;
                } else {
                    playingperson = Black;
                }
                window.repaint();
            }
            System.out.println();
            System.out.println("Computer is playing!");
            System.out.println();
        }
    }

    /* Terminates the mouse events */
    public void mouseClicked(MouseEvent evt) {}
    public void mouseEntered(MouseEvent evt) {}
    public void mouseExited(MouseEvent evt) {}

    /* The getPieces function prints the discs */
    public int[][] getPieces(char[][] board) {
        int[][] pieces = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'X') {
                    pieces[i][j] = 1;
                } else if (board[i][j] == 'O') {
                    pieces[i][j] = 2;
                } else {
                    pieces[i][j] = 0;
                }
            }
        }
        return pieces;
    }

    /* The checkFinal function checks if the game has reached to the final state */
    public boolean checkFinal(State aState) {
        int dim;
        boolean isFinal = true;
        dim = aState.getReversi().length;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (aState.getReversi()[i][j] == ' ')
                    isFinal = false;
            }
        }
        return isFinal;
    }
}

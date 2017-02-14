//Import packages
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * TicTacToe game representation with GUI
 * 
 * @author Gurveer Dhindsa
 * @version 2016-12-08
 */
public class TicTacToeFrame
{

    public static final String PLAYER_X = "X"; // player using "X"
    public static final String PLAYER_O = "O"; // player using "O"
    public static final String EMPTY = " ";    // empty cell
    public static final String TIE = "T";      // game ended in a tie
    private String player;                     // current player (PLAYER_X or PLAYER_O)
    private String winner;                     // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress
    private int numFreeSquares;                // number of squares still free
    JButton[][] buttons = new JButton[3][3];   //3x3 grid
    JFrame frame;                              // game frame
    JLabel label;                              // status of the game
    JMenuBar menuBar;                          //option bar at top of game

    /**
     * Constructor for objects of class TicTacToeFrame
     */
    public TicTacToeFrame()
    {
        setGame();
        clearBoard();
    }

    /**
     * Main method
     */
    public static void main(String[]args)
    {
        TicTacToeFrame start_game = new TicTacToeFrame();
    }

    /**
     * Initializes GUI components and adds respective components to ActionListener
     */
    private void setGame()
    {
        //Initialize frame, container, panels, label
        frame  = new JFrame("TicTacToe");
        frame.setSize(325,425);
        Container contentpane = frame.getContentPane();
        JPanel game = new JPanel(new GridLayout(3,3));
        JPanel menu = new JPanel(new FlowLayout());
        JPanel main = new JPanel(new BorderLayout());
        label = new JLabel("Turn: Player X");

        //Add components to respective panels
        menu.add(label);
        main.add(menu, BorderLayout.SOUTH);
        main.add(game);

        //Add panel to container
        contentpane.add(main);

        //Add buttons
        for(int row = 0; row<3; row++)
        {
            for(int col=0; col<3; col++)
            {
                buttons[row][col] = new JButton("");
                buttons[row][col].addActionListener(new ButtonHandler());
                game.add(buttons[row][col]);
            }
        }

        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu gameItem = new JMenu("GAME MENU");
        menuBar.add(gameItem);
        JMenuItem new_game = new JMenuItem("NEW");
        gameItem.add(new_game);
        JMenuItem quit_game = new JMenuItem("QUIT");
        gameItem.add(quit_game);

        new_game.addActionListener(new NewGameListener());
        quit_game.addActionListener(new QuitGameListener());

        frame.setVisible(true);
    }

    /**
     * Sets everything up for a new game.  Marks all squares in the Tic Tac Toe board as empty,
     * and indicates no winner yet, 9 free squares and the current player is player X.
     */
    private void clearBoard()
    {
        for(int i=0; i<3; i++)
        {
            for(int j =0; j<3; j++)
            {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        winner = EMPTY;
        numFreeSquares = 9;
        player = PLAYER_X;
        label.setText("Turn: Player " + player);
    }

    /**
     * Returns true if filling the given square gives us a winner, and false
     * otherwise.
     *
     * @param int row of square just set
     * @param int col of square just set
     * 
     * @return true if we have a winner, false otherwise
     */
    public boolean haveWinner(int row, int col)
    {
        if(numFreeSquares >4) 
        {
            return false;
        }

        if(buttons[row][0].getText().equals(buttons[row][1].getText()) && 
        buttons[row][0].getText().equals(buttons[row][2].getText()) ) return true;

        if(buttons[0][col].getText().equals(buttons[1][col].getText()) &&
        buttons[0][col].getText().equals(buttons[2][col].getText()))return true;

        if(row == col)
        {
            if(buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText())) return true; 
        }
        if(row == 2-col)
        {
            if(buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText()) ) 
                return true;
        }

        return false;
    }

    /**
     * ButtonHandler has reference to buttons on board and updates game as each player takes turns
     */
    public class ButtonHandler implements ActionListener
    {
        public ButtonHandler()
        {
        }

        public void actionPerformed(ActionEvent e)
        {
            if((e.getSource() == buttons[0][0])&& (winner==EMPTY))
            {
                gamePlay(e.getSource(),0,0);
            }
            else if((e.getSource() == buttons[0][1]) &&( winner ==EMPTY)  && (buttons[0][1].isSelected()!=true))
            {
                gamePlay(e.getSource(),0,1);
            }
            else if((e.getSource() == buttons[0][2]) && (winner ==EMPTY ) && (buttons[0][2].isSelected()!=true))
            {
                gamePlay(e.getSource(),0,2);
            }
            else if((e.getSource() == buttons[1][0]) && (winner ==EMPTY)  &&( buttons[1][0].isSelected()!=true))
            {
                gamePlay(e.getSource(),1,0);
            }
            else if((e.getSource() == buttons[1][1]) && (winner ==EMPTY ) && (buttons[1][1].isSelected()!=true))
            {
                gamePlay(e.getSource(),1,1);
            }
            else if((e.getSource() == buttons[1][2])&&( winner ==EMPTY)  && (buttons[1][2].isSelected()!=true))
            {
                gamePlay(e.getSource(),1,2);
            }
            else if((e.getSource() == buttons[2][0]) &&( winner ==EMPTY)  && (buttons[2][0].isSelected()!=true))
            {
                gamePlay(e.getSource(),2,0);
            }
            else if((e.getSource() == buttons[2][1] )&& (winner ==EMPTY ) && (buttons[2][1].isSelected()!=true))
            {
                gamePlay(e.getSource(),2,1);
            }
            else if((e.getSource() == buttons[2][2]) && (winner ==EMPTY)  && (buttons[2][2].isSelected()!=true))
            {
                gamePlay(e.getSource(),2,2);
            } 
        }

        public void gamePlay(Object source,int row, int column)
        {
            buttons[row][column].setText(player);
            buttons[row][column].setEnabled(false);
            numFreeSquares--;
            if(haveWinner(row,column))
            {
                winner = player;
                label.setText("GAME OVER!  Player " + player + " wins!");
            }
            else if(numFreeSquares == 0)
            {
                winner =TIE;
                label.setText("GAME OVER!  Tie game");
            }
            if(player == PLAYER_X && winner== EMPTY)
            {
                player =PLAYER_O;
                label.setText("Turn: Player " + player);
            }
            else if(player == PLAYER_O && winner == EMPTY)
            {
                player =PLAYER_X;
                label.setText("Turn: Player " + player);
            }
        }
    }

    /**
    * QuitGameListener has reference to the 'QUIT' JMenuItem if a player decides to quit.
    */
    public class QuitGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            frame.setVisible(false);
            System.exit(0);
        }
    }

    /**
    * NewGameListener has reference to the 'NEW' JMenuItem if a player decides to create a new game.
    */
    public class NewGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            clearBoard();
        }
    }
}
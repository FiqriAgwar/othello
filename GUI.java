import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;

public class GUI extends JFrame implements ActionListener {
    private JPanel panel;
    private JPanel menuPanel;
    private JButton[][] board;
    private Integer[][] boardgame;
    private Othello game;
    private DingdongOthello ai;

    public GUI() {
        game = new Othello(0);
        this.ai = new DingdongOthello(game, "White", 5);

        this.boardgame = new Integer[8][];
        for(int i=0;i<8;i++){
            this.boardgame[i] = new Integer[8];
            }
        this.setBoardGame(game.getGameBoard());
        initInterface();
        this.setVisible(true);
    }

    public GUI(Othello game){
        this.game = game;
        this.ai = new DingdongOthello(game, "White", 10);

        this.boardgame = new Integer[8][];
        for(int i=0;i<8;i++){
            this.boardgame[i] = new Integer[8];
            }
        this.setBoardGame(game.getGameBoard());
        initInterface();
        this.setVisible(true);
    }

    public void initInterface() {
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(8,8,5,5));
        panel.setLayout(new GridLayout(8,8,5,5));

        this.board = new JButton[8][8];
        //Othello temp_game = new Othello(0);
        for (int i=0;i<this.boardgame.length;i++) { //from index 1 - 8
            for (int j=0;j<this.boardgame[i].length;j++) { //from index 1-8

                if (this.boardgame[i][j] == 0) {
                    this.board[i][j] = new JButton();
                    board[i][j].setActionCommand(game.convertInt(i+1)+Integer.toString(j+1));
                    
                    panel.add(this.board[i][j]);
                    //board[i][j].setBorder(new LineBorder(Color.BLACK));
                }
                else if (this.boardgame[i][j] == 1) {
                    this.board[i][j] = new JButton(new ImageIcon("img/black.png"));
                    board[i][j].setActionCommand(game.convertInt(i+1)+Integer.toString(j+1));
                    panel.add(this.board[i][j]);
                    //board[i][j].setBorder(new LineBorder(Color.BLACK));
                }
                else if (this.boardgame[i][j] == 2) {
                    this.board[i][j] = new JButton(new ImageIcon("img/white.png"));
                    board[i][j].setActionCommand(game.convertInt(i+1)+Integer.toString(j+1));
                    panel.add(this.board[i][j]);
                    //board[i][j].setBorder(new LineBorder(Color.BLACK));
                }
                else {
                    this.board[i][j] = new JButton(new ImageIcon("img/slc.png"));
                    board[i][j].setActionCommand(game.convertInt(i+1)+Integer.toString(j+1));
                    panel.add(this.board[i][j]);
                    
                }
                System.out.print(game.convertInt(i+1)+Integer.toString(j+1)+" ");
                board[i][j].setBackground(Color.GREEN);
                board[i][j].addActionListener(this);

            }
            System.out.println();
            panel.setVisible(true);
        }
        add(panel);

        setTitle("Othello Game");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setBoardGame(int[][] gameboard) {
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                
                this.boardgame[i][j] = Integer.valueOf(gameboard[i+1][j+1]);   
            }   
        }
       
    }

    //
    public void actionPerformed(ActionEvent e) {
        
        for(int i=0;i<boardgame.length;i++){
            for (int j=0;j<boardgame[i].length;j++) {
                
                if(board[i][j] == e.getSource()) {
                    if (this.boardgame[i][j] == 9) {
                        
                        //clearBoard();
                        String playerAction = this.board[i][j].getActionCommand();
                        System.out.println(playerAction);
                        game.progress(playerAction);

                        ai.playerMoveInput(playerAction);
                        game.progress(ai.nextBestMove());

                        game.printBoard();
                        boardUpdate();
                    }
                }
            }
        }
    }

    public void boardUpdate(){
        this.setBoardGame(game.getGameBoard());
        /*
        if ((game.turn=="Black" && game.blackValidMoves == 0)){
            //pop up skip
            game.countSkip++;
            game.turn = "White";
        }
        if ((game.turn=="White" && game.whiteValidMoves == 0)){
            game.countSkip++;
            game.turn = "Black";
        }
        if ((game.turn=="Black" && game.blackValidMoves == 0)){
            //pop up skip
            game.countSkip++;
            game.turn = "White";
        }
        */
        for (int i=0;i<this.boardgame.length;i++){
            for (int j=0;j<this.boardgame.length;j++) {

                if (this.boardgame[i][j] == 0) {
                    this.board[i][j].setIcon(new ImageIcon(""));
                    
                }
                else if (this.boardgame[i][j] == 1) {
                    this.board[i][j].setIcon(new ImageIcon("img/black.png"));
                    
                    //board[i][j].setBorder(new LineBorder(Color.BLACK));
                }
                else if (this.boardgame[i][j] == 2) {
                    this.board[i][j].setIcon(new ImageIcon("img/white.png"));
                    //board[i][j].setBorder(new LineBorder(Color.BLACK));
                }
                else {
                    this.board[i][j].setIcon(new ImageIcon("img/slc.png"));
                    
                }
                board[i][j].setBackground(Color.GREEN);
                board[i][j].addActionListener(this);
            }
        }
        if (game.endGame) {
            //game.stopGame();
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                GUI test = new GUI();
            }
        });
    }

}
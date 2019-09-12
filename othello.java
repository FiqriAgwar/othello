import java.util.Scanner;

public class othello{
    private int[][] gameboard;
    private String turn;
    private boolean endGame;

    private int whiteValidMoves;
    private int blackValidMoves;

    private int currentPeg;

    public othello(int type){
        gameboard = new int[10][10];
        
        // 0 -> Empty
        // 1 -> Black
        // 2 -> White
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                gameboard[i][j] = 0;
            }
        }

        turn = "Black";
        blackValidMoves = 0;
        whiteValidMoves = 0;
        currentPeg = 4;
        endGame = false;
        initBoard(type);
    }

    private void initBoard(int type)
    {
        if (type == 0) {
            //diagonal type
            gameboard[4][4] = 1;
            gameboard[5][5] = 1;
            gameboard[4][5] = 2;
            gameboard[5][4] = 2;
        }
        else {
            //straight type
            gameboard[4][4] = 1;
            gameboard[5][4] = 1;
            gameboard[4][5] = 2;
            gameboard[5][5] = 2;
        }

        setValidMove();
    }

    private void printBoard()
    {
        System.out.println("0 | A B C D E F G H");
        for (int i=1; i<=8; i++)
        {
            System.out.print(i + " | ");
            for (int j=1; j<=8; j++)
            {
                if (gameboard[i][j] == 1){
                    System.out.print("B ");
                }
                else if (gameboard[i][j] == 2){
                    System.out.print("W ");
                }
                else if (gameboard[i][j] == 9){
                    System.out.print("? ");
                }
                else{
                    System.out.print("_ ");
                }
            }

            System.out.println();
        }
    }

    private void add(int axis, int ordinat)
    {
        if (turn == "Black"){
            gameboard[ordinat][axis] = 1;
        }
        else {
            gameboard[ordinat][axis] = 2;
        }
        currentPeg++;
    }

    private int convertAxis(char axis){
        switch(axis){
            case 'A' : return 1;
            case 'B' : return 2;
            case 'C' : return 3;
            case 'D' : return 4;
            case 'E' : return 5;
            case 'F' : return 6;
            case 'G' : return 7;
            case 'H' : return 8;
            default : return 0;
        }
    }

    private int convertOrdinat(char ordinat){
        switch(ordinat){
            case '1' : return 1;
            case '2' : return 2;
            case '3' : return 3;
            case '4' : return 4;
            case '5' : return 5;
            case '6' : return 6;
            case '7' : return 7;
            case '8' : return 8;
            default : return 0;
        }
    }

    private boolean validMove(int axis, int ordinat)
    {
        
        boolean valid = false;
        int playerTurn = 1;
        int opponentTurn = 2;

        if (turn == "White"){
            playerTurn = 2;
            opponentTurn = 1;
        }

        for (int y = -1; y<=1; y++)
        {
            for (int x = -1; x<=1; x++)
            {
                int cekX = axis + x;
                int cekY = ordinat + y;

                if(x != 0 || y != 0){
                    if (gameboard[cekY][cekX] == opponentTurn){
                        cekX += x;
                        cekY += y;

                        while (gameboard[cekY][cekX] == opponentTurn){
                            cekX += x;
                            cekY += y;
                        }

                        if (gameboard[cekY][cekX] == playerTurn){
                            valid = true;
                        }
                    }
                }
            }
        }

        return valid;
    }

    private void flip(int axis, int ordinat){
        //System.out.println("masuk flip " + axis + " " + ordinat);
        int playerTurn = 1;
        int opponentTurn = 2;

        if (turn == "White"){
            playerTurn = 2;
            opponentTurn = 1;
        }

            for (int y = -1; y<=1; y++)
            {
                for (int x = -1; x<=1; x++)
                {
                    int cekX = axis + x;
                    int cekY = ordinat + y;

                    if (x != 0 || y != 0){
                        //System.out.println(cekX + " " + cekY + " " + gameboard[cekY][cekX] + " " + axis + " " + ordinat);
                        if (gameboard[cekY][cekX] == opponentTurn){
                            cekX += x;
                            cekY += y;

                            while (gameboard[cekY][cekX] == opponentTurn){
                                cekX += x;
                                cekY += y;
                            }

                            if (gameboard[cekY][cekX] == playerTurn){
                                cekX -= x;
                                cekY -= y;

                                while (gameboard[cekY][cekX] != playerTurn){
                                    gameboard[cekY][cekX] = playerTurn;
                                    cekY -= y;
                                    cekX -= x;
                                }
                            }
                        }
                    }
                }
            }
    }

    private void setValidMove()
    {
        if (turn == "White") {
            whiteValidMoves = 0;
        }
        else {
            blackValidMoves = 0;
        }

        //System.out.println(whiteValidMoves + " " + blackValidMoves);

        for (int i=1; i<=8; i++)
        {
            for (int j=1; j<=8; j++)
            {
                if (gameboard[i][j] == 9) {
                    gameboard[i][j] = 0;
                }

                //System.out.println(i + " " + j + " " + validMove(j, i));
                
                if (validMove(j, i)) {
                    
                        gameboard[i][j] = 9;
    
                        if (turn == "White") {
                            whiteValidMoves++;
                        }
                        else {
                            blackValidMoves++;
                        }
                    
                }

                //System.out.print(gameboard[i][j] + " ");
            }
            //System.out.println();
        }

        //System.out.println(whiteValidMoves + " " + blackValidMoves);
    }

    private boolean checkInput(String coord)
    {
        return (coord.charAt(0) == 'A' || coord.charAt(0) == 'B' || coord.charAt(0) == 'C' || coord.charAt(0) == 'D' || coord.charAt(0) == 'E' || coord.charAt(0) == 'F' || coord.charAt(0) == 'G' || coord.charAt(0) == 'H') && (coord.charAt(1) == '1' || coord.charAt(1) == '2' || coord.charAt(1) == '3' || coord.charAt(1) == '4' || coord.charAt(1) == '5' || coord.charAt(1) == '6' || coord.charAt(1) == '7' || coord.charAt(1) == '8');
    }

    private void progress(String coordinate){
        int axis = convertAxis(coordinate.charAt(0));
        int ordinat = convertOrdinat(coordinate.charAt(1));

        add(axis, ordinat);
        flip(axis, ordinat);
        
        if (turn == "White") {
            turn = "Black";
        }
        else {
            turn = "White";
        }
        
        setValidMove();

        if (currentPeg == 64){
            stopGame();
        }

        if (turn == "White") {
            if (blackValidMoves == 0) {
                turn = "White";
                
                setValidMove();

                if (whiteValidMoves == 0){
                    stopGame();
                }
            }
            else {
                turn = "Black";
            }
        }
        else {
            if (whiteValidMoves == 0) {
                turn = "Black";

                setValidMove();

                if (blackValidMoves == 0){
                    stopGame();
                }
            }
            else {
                turn = "White";
            }
        }
    }

    private void stopGame()
    {
        endGame = true;
        
        int whiteAmount = 0;
        int blackAmount = 0;

        for (int i=1; i<=8; i++)
        {
            for (int j=1; j<=8; j++)
            {
                if(gameboard[i][j] == 1){
                    blackAmount++;
                }
                else if (gameboard[i][j] == 2){
                    whiteAmount++;
                }
            }
        }
        
        if(whiteAmount > blackAmount) {
            System.out.println("White Wins!!");
        }
        else if(blackAmount > whiteAmount) {
            System.out.println("Black Wins!!");
        }
        else {
            System.out.println("Draw!");
        }
    }
    
    private String takeCoordinate(){
        Scanner input = new Scanner(System.in);

        System.out.print(turn + " player, it's your turn! Input your next move (e.g : A6) = ");
        String coordinate = input.nextLine();

        while(!checkInput(coordinate))
        {
            System.out.print(turn + " player, it's your turn! Input your next move (e.g : A6) = ");
            coordinate = input.nextLine();
        }

        input.close();

        return coordinate;
    }

    public static void main(String args[])
    {
        System.out.println("Start the game!");

        othello game = new othello(0);
        game.printBoard();
        
        while (!game.endGame)
        {
            //System.out.println("in while");
            String coordinate = game.takeCoordinate();

            game.progress(coordinate);
            
            game.printBoard();
        }
    }
}

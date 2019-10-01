import java.util.Scanner;

public class Othello{
    public int[][] gameboard;
    public String turn;
    public boolean endGame;

    public int whiteValidMoves;
    public int blackValidMoves;
    public int countSkip;

    public int currentPeg;

    public Othello(int type){
        this.gameboard = new int[10][10];
        
        // 0 -> Empty
        // 1 -> Black
        // 2 -> White
        for (int i=0; i<10; i++)
        {
            for (int j=0; j<10; j++)
            {
                this.gameboard[i][j] = 0;
            }
        }

        turn = "Black";
        blackValidMoves = 0;
        whiteValidMoves = 0;
        currentPeg = 4;
        endGame = false;
        countSkip = 0;
        initBoard(type);
        
    }

    public Othello(Othello currentBoard){
        this.gameboard = new int[10][10];

        for (int i = 0; i < 10; i++) {
            this.gameboard[i] = currentBoard.gameboard[i].clone();
        }

        this.turn = currentBoard.turn;
        this.endGame = currentBoard.endGame;
    
        this.whiteValidMoves = currentBoard.whiteValidMoves;
        this.blackValidMoves = currentBoard.blackValidMoves;
    
        this.currentPeg = currentBoard.currentPeg;
    }
    //get
    public int[][] getGameBoard(){
        return this.gameboard;
    }
    public String getTurn(){
        return this.turn;
    }
    //set
    public void setTurn(String turn){
        this.turn = turn;
    }
    public void setGameBoard(int[][] gameboard){
        this.gameboard = gameboard;
    }

    public void initBoard(int type)
    {
        if (type == 0) {
            //diagonal type
            this.gameboard[4][4] = 1;
            this.gameboard[5][5] = 1;
            this.gameboard[4][5] = 2;
            this.gameboard[5][4] = 2;
        }
        else {
            //straight type
            this.gameboard[4][4] = 1;
            this.gameboard[5][4] = 1;
            this.gameboard[4][5] = 2;
            this.gameboard[5][5] = 2;
        }

        setValidMove("Black");
    }

    public void printBoard()
    {
        System.out.println("0 | 1 2 3 4 5 6 7 8");
        for (int i=1; i<=8; i++)
        {
            System.out.print(convertInt(i) + " | ");
            for (int j=1; j<=8; j++)
            {
                if (this.gameboard[i][j] == 1){
                    System.out.print("B ");
                }
                else if (this.gameboard[i][j] == 2){
                    System.out.print("W ");
                }
                else if (this.gameboard[i][j] == 9){
                    System.out.print("? ");
                }
                else{
                    System.out.print("_ ");
                }
            }

            System.out.println();
        }
    }

    public void add(int axis, int ordinat)
    {
        if (turn == "Black"){
            this.gameboard[ordinat][axis] = 1;
        }
        else {
            this.gameboard[ordinat][axis] = 2;
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

    public char convertInt(int coord){
        switch(coord){
            case 1 : return 'A';
            case 2 : return 'B';
            case 3 : return 'C';
            case 4 : return 'D';
            case 5 : return 'E';
            case 6 : return 'F';
            case 7 : return 'G';
            case 8 : return 'H';
            default : return 'X';
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

    public boolean validMove(int axis, int ordinat)
    {
        if(this.gameboard[ordinat][axis] == 1 || this.gameboard[ordinat][axis] == 2) return false;
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
                    if (this.gameboard[cekY][cekX] == opponentTurn){
                        cekX += x;
                        cekY += y;

                        while (this.gameboard[cekY][cekX] == opponentTurn){
                            cekX += x;
                            cekY += y;
                        }

                        if (this.gameboard[cekY][cekX] == playerTurn){
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

        if (this.turn == "White"){
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
                        //System.out.println(cekX + " " + cekY + " " + this.gameboard[cekY][cekX] + " " + axis + " " + ordinat);
                        if (this.gameboard[cekY][cekX] == opponentTurn){
                            cekX += x;
                            cekY += y;

                            while (this.gameboard[cekY][cekX] == opponentTurn){
                                cekX += x;
                                cekY += y;
                            }

                            if (this.gameboard[cekY][cekX] == playerTurn){
                                cekX -= x;
                                cekY -= y;

                                while (this.gameboard[cekY][cekX] != playerTurn){
                                    this.gameboard[cekY][cekX] = playerTurn;
                                    cekY -= y;
                                    cekX -= x;
                                }
                            }
                        }
                    }
                }
            }
    }

    private void setValidMove(String turn)
    {
        if(turn == "White"){
            whiteValidMoves = 0;
        }
        else{
            blackValidMoves = 0;
        }

        System.out.println(whiteValidMoves + " " + blackValidMoves);

        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                if(this.gameboard[i][j] == 9){
                    this.gameboard[i][j] = 0;
                }

                if(validMove(j, i)){
                    System.out.println(i + " " + j + " valid");
                    this.gameboard[i][j] = 9;

                    if(turn == "White"){
                        this.whiteValidMoves++;
                        this.countSkip=0;
                    }
                    else{
                        this.blackValidMoves++;
                        this.countSkip=0;
                    }
                }
                

            }
        }
    }

    private boolean checkInput(String coord)
    {
        return (coord.charAt(0) == 'A' || coord.charAt(0) == 'B' || coord.charAt(0) == 'C' || coord.charAt(0) == 'D' || coord.charAt(0) == 'E' || coord.charAt(0) == 'F' || coord.charAt(0) == 'G' || coord.charAt(0) == 'H') && (coord.charAt(1) == '1' || coord.charAt(1) == '2' || coord.charAt(1) == '3' || coord.charAt(1) == '4' || coord.charAt(1) == '5' || coord.charAt(1) == '6' || coord.charAt(1) == '7' || coord.charAt(1) == '8');
    }

    public void progress(String coordinate){
        int ordinat = convertAxis(coordinate.charAt(0));
        int axis = convertOrdinat(coordinate.charAt(1));

        add(axis, ordinat);
        flip(axis, ordinat);
        
        if (this.turn == "White") {
            this.turn = "Black";
        }
        else {
            this.turn = "White";
        }
        
        setValidMove(this.turn); //next turn valid moves

        if (currentPeg == 64
            || (this.turn == "White" && this.whiteValidMoves == 0)
            || (this.turn == "Black" && this.blackValidMoves == 0)){
            stopGame();
        }
        else if ((this.turn == "White" && this.whiteValidMoves == 0)
                  || (this.turn == "Black" && this.blackValidMoves == 0)){
            
            if (this.countSkip==2){
                stopGame();
            }
        }

        
    }

    public String stopGame()
    {
        endGame = true;
        
        int whiteAmount = 0;
        int blackAmount = 0;

        for (int i=1; i<=8; i++)
        {
            for (int j=1; j<=8; j++)
            {
                if(this.gameboard[i][j] == 1){
                    blackAmount++;
                }
                else if (this.gameboard[i][j] == 2){
                    whiteAmount++;
                }
            }
        }
        
        if(whiteAmount > blackAmount) {
            System.out.println("White Wins!!");
            return ("White Wins!!");
        }
        else if(blackAmount > whiteAmount) {
            System.out.println("Black Wins!!");
            return ("Black Wins!!");
        }
        else {
            System.out.println("Draw!");
            return ("Draw!");
        }
    }
    
    private String takeCoordinate(Scanner input){

        System.out.println(turn + " player, it's your turn! Input your next move (e.g : A6) = ");
        String coordinate = input.nextLine();
        System.out.println(convertAxis(coordinate.charAt(0)) + " " + convertOrdinat(coordinate.charAt(1)) + " " + gameboard[4][3]);

        System.out.println(checkInput(coordinate) + " " + this.gameboard[convertAxis(coordinate.charAt(0))][convertOrdinat(coordinate.charAt(1))]);

        while(!checkInput(coordinate) || this.gameboard[convertAxis(coordinate.charAt(0))][convertOrdinat(coordinate.charAt(1))] != 9)
        {
            System.out.println("Inputan tidak valid");
            System.out.println(turn + " player, it's your turn! Input your next move (e.g : A6) = ");
            coordinate = input.nextLine();
        }



        return coordinate;
    }

    public static void main(String args[])
    {
        System.out.println("Start the game!");
        Scanner input = new Scanner(System.in);

        Othello game = new Othello(0);
        game.printBoard();
        
        while (!game.endGame)
        {
            //System.out.println("in while");
            String coordinate = game.takeCoordinate(input);

            game.progress(coordinate);
            
            game.printBoard();
        }
        input.close();
    }
}

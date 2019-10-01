public class DingdongOthello {
    final int WEIGHT_MULTIPLIER = 50;
    public final int[][] weight =
    {
        {  4, -2,  2,  1,  1,  2, -2,  4},
        { -2, -4, -1, -1, -1, -1, -4, -2},
        {  2, -1,  2,  0,  0,  2, -1,  2},
        {  1, -1,  0,  0,  0,  0, -1,  1},
        {  1, -1,  0,  0,  0,  0, -1,  1},
        {  2, -1,  2,  0,  0,  2, -1,  2},
        { -2, -4, -1, -1, -1, -1, -4, -2},
        {  4, -2,  2,  1,  1,  2, -2,  4}
    };
    public Othello currentGame;
    public int searchDepth = 10;
    public String aiColor = "White";

    public DingdongOthello(String aiColor, int searchDepth) {
        this.currentGame = new Othello(0);
        this.searchDepth = searchDepth;
        this.aiColor = aiColor;
    }

    public DingdongOthello(Othello gameState, String aiColor, int searchDepth) {
        this.currentGame = new Othello(gameState);
        this.searchDepth = searchDepth;
        this.aiColor = aiColor;

        // clear available move marker
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                if(this.currentGame.gameboard[i][j] == 9){
                    this.currentGame.gameboard[i][j] = 0;
                }
            }
        }
    }

    public void playerMoveInput(String move) {
        int ordinat = this.currentGame.convertAxis(move.charAt(0));
        int axis = this.currentGame.convertOrdinat(move.charAt(1));

        this.doMove(this.currentGame, axis, ordinat);
    }

    public String nextBestMove() {
        String move = "";
        int value = Integer.MIN_VALUE;
        Integer alpha = Integer.MIN_VALUE;
        Integer beta = Integer.MAX_VALUE;
        
        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                if (this.currentGame.validMove(j, i)) {
                    Othello nextPossibleState = new Othello(this.currentGame);
                    this.doMove(nextPossibleState, j, i);

                    int alphabetaResult =
                        alphabeta(nextPossibleState, this.searchDepth-1, alpha, beta, false)
                        + (this.getWeight(j, i) * WEIGHT_MULTIPLIER);

                    if (alphabetaResult > value) {
                        value = alphabetaResult;
                        move = String.valueOf(nextPossibleState.convertInt(i)) + Integer.toString(j);
                    }

                    alpha = Math.max(alpha, value);

                    if (alpha >= beta) {
                        break;
                    }
                }
            }
        }

        playerMoveInput(move);
        return move;
    }

    public int alphabeta(Othello gameState, int depth, Integer alpha, Integer beta, boolean isMaximizing) {
        if (depth == 0) {
            int colorCheck;
            if (this.aiColor.equals("Black")) {
                colorCheck = 1;
            } else {
                colorCheck = 2;
            }

            int result = 0;
            for(int i=1;i<=8;i++){
                for(int j=1;j<=8;j++){
                    if (gameState.getGameBoard()[i][j] == colorCheck) {
                        result++;
                    }
                }
            }

            return result;
        }

        int value;

        if (isMaximizing) {
            value = Integer.MIN_VALUE;

            for(int i=1;i<=8;i++){
                for(int j=1;j<=8;j++){
                    if (this.currentGame.validMove(j, i)) {
                        Othello nextPossibleState = new Othello(this.currentGame);
                        this.doMove(nextPossibleState, j, i);
    
                        int alphabetaResult =
                            alphabeta(nextPossibleState, depth-1, alpha, beta, false)
                            + this.getWeight(j, i);
    
                        value = Math.max(value, alphabetaResult);
                        alpha = Math.max(alpha, value);
    
                        if (alpha >= beta) {
                            break;
                        }
                    }
                }
            }
        } else {
            value = Integer.MAX_VALUE;

            for(int i=1;i<=8;i++){
                for(int j=1;j<=8;j++){
                    if (this.currentGame.validMove(j, i)) {
                        Othello nextPossibleState = new Othello(this.currentGame);
                        this.doMove(nextPossibleState, j, i);
    
                        int alphabetaResult =
                            alphabeta(nextPossibleState, depth-1, alpha, beta, true)
                            + this.getWeight(j, i);
    
                        value = Math.min(value, alphabetaResult);
                        beta = Math.min(beta, value);
    
                        if (alpha >= beta) {
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public int getWeight(int axis, int ordinat) {
        return weight[ordinat-1][axis-1];
    }

    /*
    public void nextBestMove(String nextMove, Integer moveScore) {
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
    }*/

    private void doMove(Othello game, int axis, int ordinat) {
        game.add(axis, ordinat);
        game.flip(axis, ordinat);

        this.changeTurn(game);
    }

    private void changeTurn(Othello game) {
        if (game.turn == "White") {
            game.turn = "Black";
        }
        else {
            game.turn = "White";
        }
    }
    
    public static void main(String[] args) {
        
    }
}
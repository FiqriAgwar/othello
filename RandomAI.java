import java.util.Random;

public class RandomAI{
    public String color = "White";
    public Othello currentGame;
    public int validMoves[][]; 
    public int indexValidMoves;
    private Random randomizer;

    public RandomAI(String color){
        this.currentGame = new Othello(0);
        this.color = color;
        this.validMoves = new int[65][2];
        this.indexValidMoves = 0;
        this.randomizer = new Random();
    }

    public RandomAI(Othello gameState, String color){
        this.currentGame = new Othello(gameState);
        this.color = color;

        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                if(this.currentGame.gameboard[i][j] == 9){
                    this.currentGame.gameboard[i][j] = 0;
                }
            }
        }
    }

    public String randomizeMove(){
        indexValidMoves = 0;

        for(int i=1;i<=8;i++){
            for(int j=1;j<=8;j++){
                if(this.currentGame.validMove(j,i)){
                    indexValidMoves++;
                    validMoves[indexValidMoves][0] = i;
                    validMoves[indexValidMoves][1] = j;
                }
            }
        }

        int index = randomizer.nextInt(indexValidMoves) + 1;
        String move = String.valueOf(this.currentGame.convertInt(validMoves[index][0]) + Integer.toString(validMoves[index][0]));

        playerMoveInput(move);
        return move;
    }

    public void playerMoveInput(String move) {
        int ordinat = this.currentGame.convertAxis(move.charAt(0));
        int axis = this.currentGame.convertOrdinat(move.charAt(1));

        this.doMove(this.currentGame, axis, ordinat);
    }

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
}

import java.util.Scanner;

class Test {
    public static void main(String[] args) {
        
        System.out.println("Start the game!");
        Scanner input = new Scanner(System.in);

        Othello game = new Othello(0);

        DingdongOthello ai = new DingdongOthello(game, "White", 5);
        RandomAI randAI = new RandomAI(game, "Black");

        boolean playerTurn = true;
        
        while (!game.endGame)
        {
            game.printBoard();

            String turn = game.getTurn();

            String coordinate;
            if (playerTurn) {
                //coordinate = game.takeCoordinate(input);
                coordinate = randAI.randomizeMove();

                ai.playerMoveInput(coordinate);
            } else {
                //coordinate = game.takeCoordinate(input);
                coordinate = ai.nextBestMove();
                
                randAI.playerMoveInput(coordinate);
                //ai2.playerMoveInput(coordinate);
            }

            if (coordinate != null) {
                game.progress(coordinate);
            }

            if (!turn.equals(game.getTurn())) {
                playerTurn = !playerTurn;
            }
        }

        game.printBoard();

        input.close();
    }
}
import java.util.Scanner;

class Test {
    public static void main(String[] args) {
        
        System.out.println("Start the game!");
        Scanner input = new Scanner(System.in);

        Othello game = new Othello(0);

        DingdongOthello ai1 = new DingdongOthello(game, "White", 5);
        //DingdongOthello ai2 = new DingdongOthello(game, "Black", 5);

        boolean playerTurn = true;
        
        while (!game.endGame)
        {
            game.printBoard();

            String turn = game.getTurn();

            String coordinate;
            if (playerTurn) {
                //System.out.println("in while");
                coordinate = game.takeCoordinate(input);
                //coordinate = ai2.nextBestMove();

                ai1.playerMoveInput(coordinate);
            } else {
                //coordinate = game.takeCoordinate(input);
                coordinate = ai1.nextBestMove();

                //ai2.playerMoveInput(coordinate);
            }

            game.progress(coordinate);

            if (!turn.equals(game.getTurn())) {
                playerTurn = !playerTurn;
            }
        }

        game.printBoard();

        input.close();
    }
}
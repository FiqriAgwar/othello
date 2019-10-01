import java.util.Scanner;

public class Game{
    public static void main(String args[])
    {
        System.out.println("Start the game!");
        Scanner input = new Scanner(System.in);

        Othello game = new Othello(0);
        RandomAI myRandomAI = new RandomAI();
        
        game.printBoard();
        
        while (!game.endGame)
        {
            //System.out.println("in while");
            String coordinate = game.takeCoordinate(input);

            game = game.progress(coordinate);
            
            game.printBoard();

            game = myRandomAI.randomizeMove(game);

            game.printBoard();

        }
        input.close();
    }
}
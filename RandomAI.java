import java.util.Random;

public class RandomAI{
    public Othello randomizeMove(Othello game){
        Othello nextboard = new Othello(game);
        
        Random rand = new Random();

        int index = rand.nextInt(nextboard.nValidMoves) + 1;
        
        String coordinate = Character.toString(nextboard.convertInt(nextboard.validMoves[index].x)) + Integer.toString(nextboard.validMoves[index].y);
        System.out.println("RandomAI takes move on " + coordinate);
        nextboard = nextboard.progress(coordinate);
        
        return nextboard;
    }
}
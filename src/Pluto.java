import java.util.HashSet;
import java.util.Random;

public class Pluto extends Player {
    Random rand;
    public Pluto(byte player) {
        super(player);
        this.team = "Pluto";
    }

    @Override
    public Move move(OthelloState state, HashSet<Move> legalMoves) throws InterruptedException {

        Thread.sleep(500);
        return (Move) legalMoves.toArray()[rand.nextInt(legalMoves.size())];
    }
}

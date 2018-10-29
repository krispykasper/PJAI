import java.util.HashSet;

public class Pluto extends Player {

    public Pluto(byte player) {
        super(player);
    }

    @Override
    public Move move(OthelloState state, HashSet<Move> legalMoves) throws InterruptedException {
        return null;
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class MiniMaxPlayer extends Player {

    private static final int LIMIT = 5;

    public MiniMaxPlayer(byte player) {
        super(player);
        this.team = "42";
    }

    @Override
    public Move move(OthelloState state, HashSet<Move> legalMoves) throws InterruptedException {

//        Thread.sleep(500);

        double max = Double.MAX_VALUE * -1;



        Move maxMove = null;


        for (Move move : legalMoves) {


            System.out.println(move.toString());


            double tempMax = minValue(OthelloGame.transition(state, move), Double.MIN_VALUE, Double.MAX_VALUE, 1);


            if (max < tempMax) {


                max = tempMax;


                maxMove = move;


            }


        }


        System.out.println("score === " + OthelloGame.computeScore(OthelloGame.transition(state, maxMove).getBoard(), player));


        return maxMove;


    }


    public double minValue(OthelloState state, double alpha, double beta, int depth) {



        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());


        if (legalMoves.size() == 0 || depth > LIMIT) {

            return OthelloGame.computeScore(state.getBoard(), this.player);

        }


        double min = Double.MAX_VALUE;




        List<Double> orderValue = new ArrayList<>();
        Move[] orderedLegalMove = new Move[legalMoves.size()];


        for(Move move: legalMoves){
            OthelloState newState = OthelloGame.transition(state, move);
            orderValue.add((double)OthelloGame.computeScore(newState.getBoard(), player));
        }
        Collections.sort(orderValue, Collections.reverseOrder());

        for(int i = 0; i < orderValue.size(); i++){

            for(Move move: legalMoves){
                OthelloState newState = OthelloGame.transition(state, move);
                if(OthelloGame.computeScore(newState.getBoard(), player) == orderValue.get(i)){
                    orderedLegalMove[i] = move;
                    break;
                }
            }
        }

        for (Move move : orderedLegalMove) {


            OthelloState newState = OthelloGame.transition(state, move);


            min = Math.min(min, maxValue(newState, alpha, beta, depth + 1));


            if (min >= beta) return min;


            alpha = Math.max(alpha, min);

        }


        return min;


    }


    public double maxValue(OthelloState state, double alpha, double beta, int depth) {




        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());


        if (legalMoves.size() == 0 || depth > LIMIT) {



            return OthelloGame.computeScore(state.getBoard(), this.player);


        }


        double max = Double.MAX_VALUE * -1;

        List<Double> orderValue = new ArrayList<>();
        Move[] orderedLegalMove = new Move[legalMoves.size()];


        for(Move move: legalMoves){
            OthelloState newState = OthelloGame.transition(state, move);
            orderValue.add((double)OthelloGame.computeScore(newState.getBoard(), player));
        }
        Collections.sort(orderValue, Collections.reverseOrder());

        for(int i = 0; i < orderValue.size(); i++){

            for(Move move: legalMoves){
                OthelloState newState = OthelloGame.transition(state, move);
                if(OthelloGame.computeScore(newState.getBoard(), player) == orderValue.get(i)){
                    orderedLegalMove[i] = move;
                    break;
                }
            }
        }


        for (Move move : orderedLegalMove) {


            OthelloState newState = OthelloGame.transition(state, move);


            max = Math.max(max, minValue(newState, alpha, beta, depth + 1));


            if (max <= alpha) return max;


            beta = Math.min(beta, max);


        }


        return max;


    }

}
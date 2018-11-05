import java.util.HashSet;
import java.util.Random;

public class Pluto extends Player {


    private static final int LIMIT = 5;
    Random rand = new Random();



    public Pluto(byte player) {

        super(player);

        this.team = "Pluto";

    }



    @Override

    public Move move(OthelloState state, HashSet<Move> legalMoves) throws InterruptedException {
//        Thread.sleep(500);
        double max = Double.MIN_VALUE;

        Move maxMove = null;

        for (Move move : legalMoves) {

            System.out.println(move.toString());

            double tempMax = minValue(OthelloGame.transition(state, move), Double.MIN_VALUE, Double.MAX_VALUE, 1);

            if (max < tempMax) {

                max = tempMax;

                maxMove = move;

            }

        }

        return maxMove;

    }



    public double minValue(OthelloState state, double alpha, double beta, int depth) {

        // System.out.println(depth);

        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());

        if (legalMoves.size() == 0 || depth > LIMIT) {

//            return OthelloGame.computeScore(state.getBoard(), this.player);
            return evalution(state.getBoard(), this.player);
        }

        double min = Double.MAX_VALUE;

        for (Move move : legalMoves) {

            OthelloState newState = OthelloGame.transition(state, move);

            min = Math.min(min, maxValue(newState, alpha, beta, depth + 1));

            if(min >= beta) return min;

            alpha = Math.max(alpha, min);
        }

        return min;

    }



    public double maxValue(OthelloState state, double alpha, double beta, int depth) {

        // System.out.println(depth);

        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());

        if (legalMoves.size() == 0 || depth > LIMIT) {

//            return OthelloGame.computeScore(state.getBoard(), this.player);
            return evalution(state.getBoard(), this.player);

        }

        double max = Double.MIN_VALUE;

        for (Move move : legalMoves) {

            OthelloState newState = OthelloGame.transition(state, move);

            max = Math.max(max, minValue(newState, alpha, beta, depth + 1));

            if(max <= alpha) return max;

            beta = Math.min(beta, max);

        }

        return max;

    }

    public double evalution(byte[][] board,byte player) {
        int score = 0, discNum = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != 0){
                    discNum++;
                }
            }
        }

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){

                if(discNum < 16){
                    if(board[i][j] == player && i >= 2 && i <= 5 && j >= 2 && j <= 5){
                        score += 5;
                    }
                }else if(discNum < 32){
                    if(board[i][j] == player && (i==1 || i==6)){
                        if(j==1 || j==6){
                            score +=4;
                        }
                        else if(j!=2 || j!=5){
                            score +=3;
                        }
                        else{
                            score++;
                        }
                    }
                    else if(board[i][j] == player && (j==1 || j==6)){
                        if(i==1 || i==6){
                            score +=4;
                        }
                        else if(i!=2 || i!=5){
                            score +=3;
                        }
                        else{
                            score++;
                        }
                    }

                }else if(discNum < 48){
                    if(board[i][j] == player && ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 0) || (i == 7 && j == 7))){
                        score += 4;
                    }else if(board[i][j] == player) {
                        score ++;
                    }
                }else {
                    if(board[i][j] == player){
                        score++;
                    }
                }

            }
        }


        return score;
    }

}

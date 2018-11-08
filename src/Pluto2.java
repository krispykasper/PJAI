import java.util.*;


public class Pluto2 extends Player {


    private static final int LIMIT = 5;

    Random rand = new Random();


    public Pluto2(byte player) {


        super(player);


        this.team = "Pluto";


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


        System.out.println("score === " + evalution(OthelloGame.transition(state, maxMove).getBoard(), player));


        return maxMove;


    }


    public double minValue(OthelloState state, double alpha, double beta, int depth) {



        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());


        if (legalMoves.size() == 0 || depth > LIMIT) {

            return evalution(state.getBoard(), this.player);

        }


        double min = Double.MAX_VALUE;




        List<Double> orderValue = new ArrayList<>();
        Move[] orderedLegalMove = new Move[legalMoves.size()];


        for(Move move: legalMoves){
            OthelloState newState = OthelloGame.transition(state, move);
            orderValue.add(evalution(newState.getBoard(), player));
        }
        Collections.sort(orderValue, Collections.reverseOrder());

        for(int i = 0; i < orderValue.size(); i++){

            for(Move move: legalMoves){
                OthelloState newState = OthelloGame.transition(state, move);
                if(evalution(newState.getBoard(), player) == orderValue.get(i)){
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



            return evalution(state.getBoard(), this.player);


        }


        double max = Double.MAX_VALUE * -1;

        List<Double> orderValue = new ArrayList<>();
        Move[] orderedLegalMove = new Move[legalMoves.size()];


        for(Move move: legalMoves){
            OthelloState newState = OthelloGame.transition(state, move);
            orderValue.add(evalution(newState.getBoard(), player));
        }
        Collections.sort(orderValue, Collections.reverseOrder());

        for(int i = 0; i < orderValue.size(); i++){

            for(Move move: legalMoves){
                OthelloState newState = OthelloGame.transition(state, move);
                if(evalution(newState.getBoard(), player) == orderValue.get(i)){
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


    public double evalution(byte[][] board, byte player) {

        int score = 0, discNum = 0;

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[0].length; j++) {

                if (board[i][j] != 0) {

                    discNum++;

                }

            }

        }




        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if(board[i][j] == player && ((i == 1 && j == 1) || (i == 6 && j == 1) || (i == 1 && j == 6) || (i == 6 && j == 6) || (i == 0 && j == 1)|| (i == 1 && j == 0)
                        || (i == 0 && j == 6) || (i == 6 && j == 0) || (i == 1 && j == 7) || (i == 7 && j == 1) || (i == 6 && j == 7) || (i == 7 && j == 6))){
                    score -= 100;
                }

                if (discNum < 16) {
                    if (board[i][j] == player && i >= 2 && i <= 5 && j >= 2 && j <= 5) {
                        score += 10;
                    }

                } else if (discNum < 42) {

                    if (board[i][j] == player && ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 0) || (i == 7 && j == 7))) {
                        score += 100;
                    }
                    if (board[i][j] == player && ((i == 0 && j == 2) || (i == 0 && j == 5) || (i == 7 && j == 2) || (i == 7 && j == 5))) {
                        score += 10;
                    }
                    if (board[i][j] == player && ((i == 2 && j == 0) || (i == 2 && j == 7) || (i == 5 && j == 0) || (i == 5 && j == 5))) {
                        score += 10;
                    }
                } else if (discNum < 60) {

                    if (board[i][j] == player && ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 0) || (i == 7 && j == 7))) {
                        score += 200;
                    }
                    if (board[i][j] == player && ((i == 0 && j == 2) || (i == 0 && j == 5) || (i == 7 && j == 2) || (i == 7 && j == 5))) {
                        score += 10;
                    }
                    if (board[i][j] == player && ((i == 2 && j == 0) || (i == 2 && j == 7) || (i == 5 && j == 0) || (i == 5 && j == 5))) {
                        score += 10;
                    }

                    if (board[i][j] == player){
                        score++;
                    }


                }
            }
        }

        return score;

    }



}
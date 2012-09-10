package tadbr;

import java.util.ArrayList;

/**
 * AlphaBeta pruning algorithm.
 * 
 * @author jecnua
 */
public class AlphaBeta {
	
    private int profAlphaBeta; /**< Prof of the alphabeta algorithm. */

    /** Create a new instance of AlphaBeta */
    public AlphaBeta(){
    	this.profAlphaBeta = 0;
    }
    
    /**
     * Return to me the best move that can be done.
     * 
     * @param chessboard Actual chessboard
     * @param color TiAro color
     * @return The best move or NULL if no move is possible
     */
    public Move chooseMove (Chessboard chessboard, boolean color) {
    	
        //Starting chessboard
        ChessboardValue V = new ChessboardValue(chessboard,null,null);
        
        //alpha
        ChessboardValue min = new ChessboardValue(chessboard,null,null);
        min.setValue(Integer.MIN_VALUE);
        
        //beta
        ChessboardValue max = new ChessboardValue(chessboard,null,null);
        max.setValue(Integer.MAX_VALUE);
        
        //AlphaBeta pruning
        ChessboardValue choice = alphaBetaAlg(V, min, max, color, 0);
        return choice.getBestMove();
    }

    /**
     * 
     * @param chessValue
     * @param alpha
     * @param beta
     * @param color
     * @param counter
     * @return 
     */
    private ChessboardValue alphaBetaAlg(ChessboardValue chessValue, ChessboardValue alpha, ChessboardValue beta, boolean color, int counter) {
        
    	//If node == leaf then return heuristic value of chessboard
        if (counter >= getProfAlphaBeta()) {
            return (chessValue);
        }
        else {
            counter++;
        }
        
        //Generate every son for this node
        //(all possible moves for this color on this chessboard)
        ArrayList<Move> allPossibleMove = chessValue.getActualChessboard().generateAllPossibleMoves(color);
    	
        for (Move thisMove : allPossibleMove) {
            //New ChessboardValue that have my son (move) and my path
            ChessboardValue thisSon = new ChessboardValue(chessValue.getActualChessboard(), thisMove, chessValue.getMoves());
            if (thisSon.isLastMoveValid()) {
            	if (color) //alpha = al massimo tra beta e la ricorsione su mio figlio
            		alpha = alpha.VSmax(alphaBetaAlg(thisSon, alpha, beta, !(color), counter));
            	else //beta = al minimo tra beta e la ricorsione su mio figlio
            		beta = beta.VSmin(alphaBetaAlg(thisSon, alpha, beta, !(color), counter));
            }
		}
        
        if (color) {
            if (beta.getValue() <= alpha.getValue()) {
                return beta;
            }
            else {
                return alpha;
            }
        }
        else {
            //ritorno il minimo
            if (beta.getValue() <= alpha.getValue()) {
                return alpha;
            }
            else {
                return beta;
            }
        }
    }
    
    //GETTER AND SETTER
    
    public void setProfAlphaBeta(int profAlphaBeta) {
            this.profAlphaBeta = profAlphaBeta;
    }

    public int getProfAlphaBeta() {
            return profAlphaBeta;
    }

}

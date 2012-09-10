package tadbr;

/**
 * 
 * @author jecnua
 */
abstract public class Algorithm {
    
    protected int profundity;
    
    public Algorithm(int aProf){
        profundity = aProf;
    }
    
    /**
     * Return to me the best move that can be done.
     * @param chessboard Actual chessboard
     * @param color TiAro color
     * @return The best move or NULL if no move is possible
     */
    abstract public Move chooseMove(Chessboard chessboard, boolean color);

    /**
     * @return the profundity
     */
    public int getProfundity() {
        return profundity;
    }

    /**
     * @param profundity the profundity to set
     */
    public void setProfundity(int profundity) {
        this.profundity = profundity;
    }
    
}
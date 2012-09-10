package tadbr;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a chessboard.
 * 
 * @author jecnua
 */
public class Chessboard implements Cloneable {
    
	//Number of possible moves. Used to calculate mobility.
    private int nWhiteMoves;
    private int nBlackMoves;
    
    private Piece[][] chessboard;
    
    private List<Piece> blacks;
    private List<Piece> whites;
    
    
    /** Creates a new instance of Chessboard */
    public Chessboard() {
        
        nWhiteMoves = 0;
        nBlackMoves = 0;
        
        setWhites(new ArrayList<Piece>());
        setBlacks(new ArrayList<Piece>());
    
        chessboard = new Piece[8][8];
        
        //Black
    	chessboard[7][0] = new Rook(25);
        chessboard[7][1] = new Knight(17);
        chessboard[7][2] = new Bishop(21);
        
        chessboard[7][3] = new Queen(29);
        chessboard[7][4] = new King(31);
        
        chessboard[7][5] = new Bishop(23);
        chessboard[7][6] = new Knight(19);
        chessboard[7][7] = new Rook(27);    

        chessboard[6][0] = new Pawn(1);
        chessboard[6][1] = new Pawn(3);
        chessboard[6][2] = new Pawn(5);
        chessboard[6][3] = new Pawn(7);
        chessboard[6][4] = new Pawn(9);
        chessboard[6][5] = new Pawn(11);
        chessboard[6][6] = new Pawn(13);
        chessboard[6][7] = new Pawn(15);

        //White
        chessboard[0][0] = new Rook(26);
        chessboard[0][1] = new Knight(18);
        chessboard[0][2] = new Bishop(22);
        
        chessboard[0][3] = new Queen(30);
        chessboard[0][4] = new King(32);
        
        chessboard[0][5] = new Bishop(24);
        chessboard[0][6] = new Knight(20);
        chessboard[0][7] = new Rook(28);  

        chessboard[1][0] = new Pawn(2);
        chessboard[1][1] = new Pawn(4);
        chessboard[1][2] = new Pawn(6);
        chessboard[1][3] = new Pawn(8);
        chessboard[1][4] = new Pawn(10);
        chessboard[1][5] = new Pawn(12);
        chessboard[1][6] = new Pawn(14);
        chessboard[1][7] = new Pawn(16);

        
        //TODO: Not used for now
		for (int i1=0;i1<8;i1++) {
		    for (int j=0;j<8;j++) {
		        if (chessboard[j][i1]!=null) {
		        	if (chessboard[j][i1].isColour()) {
                                getWhites().add((Piece)chessboard[j][i1]);
                            }
		        	else {
                                getBlacks().add((Piece)chessboard[j][i1]);
                            }
		        }
		    }
		}
    }
    
    
    /** Creates a new instance of Chessboard (used by clone) */
    private Chessboard(int nWhite, int nBlack) {
        
        nWhiteMoves = nWhite;
        nBlackMoves = nBlack;
        
        setWhites(new ArrayList<Piece>());
        setBlacks(new ArrayList<Piece>());
    
        chessboard = new Piece[8][8];
    }
    
    
    /**
     * Apply a Move to the chessboard.
     * 
     * @param thisMove Move to apply.
     * @return false in case of errors.
     */
    public boolean doMove(Move thisMove) {
        
        int fromX, fromY, toX, toY;
        ArrayList<Move> moves;
        Piece piece;
        
        fromX = thisMove.getStartX();
        fromY = thisMove.getStartY();
        toX = thisMove.getEndX();
        toY = thisMove.getEndY();
        
        //Get piece
        piece = getPieceMuov(fromX, fromY);
        
        /* Remove the inDanger flag if a piece is no more in Danger */
        
        //Generate all possible moves for this piece
        moves = piece.generateMovesForThisPiece(this);
        //For each move find if it eats something
        for (Move move : moves) {
			Piece enemyPiece = (Piece)chessboard[move.getEndY()][move.getEndX()];
        	if ((enemyPiece != null) && (enemyPiece.isColour() != piece.isColour())){
        		 enemyPiece.noMoreInDanger(); //I will no longer set it in danger
        	}
		}
        
        /* Apply changes */
        
        piece.setPosition(toX,toY);
        
        if (piece.isColour() && chessboard[toY][toX] != null)
        	getBlacks().remove(chessboard[toY][toX]);
        if (!(piece.isColour()) && chessboard[toY][toX] != null)
        	getWhites().remove(chessboard[toY][toX]);
        
        chessboard[toY][toX] = piece;
        chessboard[fromY][fromX] = null;
        
        //I moved
        piece.setMoved(true);
        
        /* Find if now there are pieces in danger */
        
        //Generate all possible moves for this piece
        moves = piece.generateMovesForThisPiece(this);
        //For each move find if it eats something
        for (Move move : moves) {
			Piece enemyPiece = (Piece)chessboard[move.getEndY()][move.getEndX()];
        	if ((enemyPiece != null) && (enemyPiece.isColour() != piece.isColour())){
        		 enemyPiece.inDanger();
        	}
		}
        
        /* TODO: Change my status if i am no more in danger */
        /* TODO: Make tiaro understand promotion */
        /* TODO: Make tiaro understand cattura en passant */
        /* TODO: Make tiaro understand if the king is in peril */
        
        return (true);
    }
    
    
    /**
     * Return the Piece in the x/y position
     * 
     * @param x The x position
     * @param y The y position
     * @return The Piece at the position
     */
    public Piece getPieceMuov(int x, int y) {
    	
        try {
            return this.chessboard[y][x];
        }
        catch(ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    /**
     * Return the Piece in the x/y position
     * 
     * @param x The x position
     * @param y The y position
     * @return The Piece at the position
     */
    public Piece getPiece(int x, int y) {
        try {
        	return (Piece)chessboard[y][x];
        }
        catch(ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    /**
     * Return an array of all possible moves for a
     * color.
     * 
     * @param color The color of interest
     * @return An array of all possible moves
     */
    public ArrayList<Move> generateAllPossibleMoves (boolean color) {
        
    	ArrayList<Move> moves = new ArrayList<Move>();    	
    	List<Piece> temp = new ArrayList<Piece>();
    	
    	if (color)
    		temp = getWhites();
    	else
    		temp = getBlacks();

        for (Piece thisPiece : temp) {
            ArrayList<Move> movesForThisPiece = thisPiece.generateMovesForThisPiece(this);
            if (!(movesForThisPiece.isEmpty())) {
                for (Move move : movesForThisPiece) {
                	moves.add(move);
				}
            }
		}
        
    	if (color)
    		setnWhiteMoves(moves.size());
    	else
    		setnBlackMoves(moves.size());   
	    
        return moves;
    }
    
    /**
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() {
    	
		Chessboard chessboardClone = new Chessboard(this.getnWhiteMoves(), this.getnBlackMoves());
		
		for (int x=0; x<8; x++) {
		    for (int y=0; y<8; y++) {
		        if (this.chessboard[y][x] != null) {
		        	Piece pieceClone = (Piece)chessboard[y][x].clone();
		            chessboardClone.chessboard[y][x] = pieceClone;
		            if (pieceClone.isColour())
		            	chessboardClone.getWhites().add((Piece)pieceClone);
		            else
		            	chessboardClone.getBlacks().add((Piece)pieceClone);
		        }
		        else
		            chessboardClone.chessboard[y][x] = null;
		    }
		}
		return chessboardClone;
	}

    /**
     * Return the starting position (string) of a piece
     * on the chessboard from it's ID.
     * 
     * @param ID ID of the piece
     * @return The string identifying the position of the piece
     */
    public static String startingPosition (int ID) {
    	
        String position;
        
        switch (ID) {
        
        	//Blacks
            case 1:		return "a7";
            case 3:		return "b7";
            case 5:		return "c7";
            case 7:		return "d7";
            case 9:		return "e7";
            case 11:	return "f7";
            case 13:	return "g7";
            case 15:	return "h7";
            case 25:	return "a8";
            case 17:	return "b8";
            case 21:	return "c8";
            case 29:	return "d8";
            case 31:	return "e8";
            case 23:	return "f8";
            case 19:	return "g8";
            case 27:	return "h8";
            
            //Whites TODO
            //pedone
            case 2:
            {
                position="a2";
                break;
            }
            //pedone
            case 4:
            {
                position="b2";
                break;
            }
            //pedone
            case 6:
            {
                position="c2";
                break;
            }
            //pedone
            case 8:
            {
                position="d2";
                break;
            }
            //pedone
            case 10:
            {
                position="e2";
                break;
            }
            //pedone
            case 12:
            {
                position="f2";
                break;
            }
            //pedone
            case 14:
            {
                position="g2";
                break;
            }
            //pedone
            case 16:
            {
                position="h2";
                break;
            }
            //torre sx
            case 26:
            {
                position="a1";
                break;
            }
            //cavallo sx
            case 18:
            {
                position="b1";
                break;
            }
            //alfiere sx
            case 22:
            {
                position="c1";
                break;
            }
            //regina
            case 30:
            {
                position="d1";
                break;
            }
            //re
            case 32:
            {
                position="e1";
                break;
            }
            //alfiere dx
            case 24:
            {
                position="f1";
                break;
            }
            //cavallo dx
            case 20:
            {
                position="g1";
                break;
            }
            //torre dx
            case 28:
            {
                position="h1";
                break;
            }
            
            default:
            	return null;
        }
        return position;
    }
    
    
    //--- GETTER AND SETTER

	void setnWhiteMoves(int nWhiteMoves) {
		this.nWhiteMoves = nWhiteMoves;
	}

	int getnWhiteMoves() {
		return nWhiteMoves;
	}

	void setnBlackMoves(int nBlackMoves) {
		this.nBlackMoves = nBlackMoves;
	}

	int getnBlackMoves() {
		return nBlackMoves;
	}


	private void setBlacks(List<Piece> blacks) {
		this.blacks = blacks;
	}


	public List<Piece> getBlacks() {
		return blacks;
	}


	private void setWhites(List<Piece> whites) {
		this.whites = whites;
	}


	public List<Piece> getWhites() {
		return whites;
	}
    
}

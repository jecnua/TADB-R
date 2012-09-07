package tadbr;

/**
 * Main class of TiAroDiBruttoRevenge IA
 * @author jecnua
 */
public class TADBR {
    
    private static boolean mycolor;			/**< TiAroDiBrutto color */
    private static boolean playerColor;			/**< EnemyPlayer color */
    private static Chessboard globalChessboard;         /**< Real chessboard */
    private static AlphaBeta alg;                       /**< AlphaBeta alg class */
    
    private static boolean black = false;
    private static boolean white = true;
    
    /**
     * "Main" function
     */
    public static void main (String[] args) {
    	
        String inputCommunication = new String();
        globalChessboard = new Chessboard();
        alg = new AlphaBeta();
        
        //Alg
    	if (args.length > 0){
        	alg.setProfAlphaBeta(Helper.returnProf(args));
        }
        else {
    		alg.setProfAlphaBeta(3);
        }
        
    	//Color
        setColour(black);       //TODO: Only implemented "black player" for now
        setPlayerColor(white);
        
        //Main loop
        while (true) {
        	
            //TODO: if is move lui->io, If my move io->lui
        	
            /** User turn **/
        	
            //waiting for xboard
            inputCommunication = Helper.readFromInput();
            
            if (inputCommunication.equalsIgnoreCase("XBoard")) {
                //communication protocol
            	String output = "feature done=0 ping=0 usermove=1 time=0 draw=0 reuse=0 "
                    + "analyze=0 myname=\"TiAroDiBruttoRevenge\" variants=\"normal\" colors=0 "
                    + "sigint=0 sigterm=0 done=1";
                System.out.println(output);
            }
            
            if ((inputCommunication.charAt(0)=='u') && (inputCommunication.charAt(1)=='s')) {
            	
                /*
                String inCaseOfPromotion = null;
                try {
                	inCaseOfPromotion = comunicazioneIn.substring(14);
                }
                catch (StringIndexOutOfBoundsException e) {
                	inCaseOfPromotion = null;
                }
                */

            	//TODO: Use charAt??
                int startX = Helper.getXfromString(inputCommunication.substring(9,11));
                int startY = Helper.getYfromString(inputCommunication.substring(9,11));
                int endX = Helper.getXfromString(inputCommunication.substring(11,13));
                int endY = Helper.getYfromString(inputCommunication.substring(11,13));
                
                Move playerMove = new Move(startX,startY,endX,endY,isPlayerColor());
                
                /* TODO: Flag
                playerMove = Move.MossaPromozione (playerMove, globalChessboard);
                playerMove = Move.MossaCatturabile (playerMove);
                playerMove = Move.MossaCatturante (playerMove, globalChessboard);
                playerMove = Move.MossaArrocco (playerMove, globalChessboard);
                */
                
                //TODO: Make him hunderstand arrocco
                
                //For now let's assume that it doesn't do illegal move
                if (playerMove.isValid()) {
                	globalChessboard.doMove(playerMove);
                }
                else {
                    System.err.println("ERROR: This move is not valid");
                    System.out.println("resign");
                    System.exit(0);
                }
                	
                
                /*
                if (globalChessboard.doMove(playerMove) == 0) {
                    System.err.println("ERROR: Illegal move.");
                    continue;
                }
                */
                /* TODO
                if (inCaseOfPromotion != null)
                	miaScacchiera = Libreria.FaiPromozione(SuaMossa,miaScacchiera, inCaseOfPromotion);
                */
                
                /** MyTurn **/
               
                Move myMove = alg.chooseMove(globalChessboard,isColour());
                if (myMove == null) {
                    System.err.println("ERROR: No possible move.");
                    System.out.println("resign");
                    System.exit(0);
                }
                else {
                    globalChessboard.doMove(myMove); //TODO: Check return value?
                    System.out.println(myMove.moveOutputString());
                }
            }
        }
    }
    
    //GETTER AND SETTER

    public static void setColour(boolean colour) {
	TADBR.mycolor = colour;
    }

    public static boolean isColour() {
        return mycolor;
    }

    public static void setPlayerColor(boolean playerColor) {
        TADBR.playerColor = playerColor;
    }

    public static boolean isPlayerColor() {
        return playerColor;
    }
    
 }

package tadbr;

/**
 * Main class of TiAroDiBruttoRevenge IA
 * @author jecnua
 */
public class TADBR {
    
    private boolean myColor;			/**< TiAroDiBrutto color */
    private boolean enemyColor;			/**< EnemyPlayer color */
    private Chessboard globalChessboard;        /**< Real chessboard */
    private AlphaBeta algorithm;                /**< AlphaBeta alg class */
    private String[] commandLine;
    
    private boolean black = false;
    private boolean white = true;
    
    /**
     * "Main" function
     */
    public static void main (String[] args) {
    	TADBR aTADBRInstance = new TADBR();
        aTADBRInstance.commandLine = args;
        aTADBRInstance.play();
    }
    
    
    public void play(){
        
        setGlobalChessboard(new Chessboard());
        AlphaBeta anAlgorithm = new AlphaBeta();
        setAlgorithm(anAlgorithm);
        
        //Set Algorithm profoundity
    	if (commandLine.length > 0){
        	anAlgorithm.setProfAlphaBeta(Helper.returnProf(commandLine));
        }
        else {
    		anAlgorithm.setProfAlphaBeta(3);
        }
        
        //TODO: Only implemented "black player" for now
        setEnemyColor(white);       
        setMyColor(black);
        
        //Main loop
        while (true) {
        	
            //TODO: if is move lui->io, If my move io->lui
        	
            /** User turn **/
            
            //waiting for xboard
            String inputCommunication = Helper.readFromInput();
            
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
                
                Move playerMove = new Move(startX,startY,endX,endY,isEnemyColor());
                
                /* TODO: Flag
                playerMove = Move.MossaPromozione (playerMove, globalChessboard);
                playerMove = Move.MossaCatturabile (playerMove);
                playerMove = Move.MossaCatturante (playerMove, globalChessboard);
                playerMove = Move.MossaArrocco (playerMove, globalChessboard);
                */
                
                //TODO: Make him hunderstand arrocco
                
                //For now let's assume that it doesn't do illegal move
                if (playerMove.isValid()) {
                	getGlobalChessboard().doMove(playerMove);
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
               
                Move myMove = getAlgorithm().chooseMove(getGlobalChessboard(),isMyColor());
                if (myMove == null) {
                    System.err.println("ERROR: No possible move.");
                    System.out.println("resign");
                    System.exit(0);
                }
                else {
                    getGlobalChessboard().doMove(myMove); //TODO: Check return value?
                    System.out.println(myMove.moveOutputString());
                }
            }
        }
    }
    
    /**
     * @return the myColor
     */
    public boolean isMyColor() {
        return myColor;
    }

    /**
     * @param myColor the myColor to set
     */
    public void setMyColor(boolean myColor) {
        this.myColor = myColor;
    }

    /**
     * @return the enemyColor
     */
    public boolean isEnemyColor() {
        return enemyColor;
    }

    /**
     * @param enemyColor the enemyColor to set
     */
    public void setEnemyColor(boolean enemyColor) {
        this.enemyColor = enemyColor;
    }

    /**
     * @return the globalChessboard
     */
    public Chessboard getGlobalChessboard() {
        return globalChessboard;
    }

    /**
     * @param globalChessboard the globalChessboard to set
     */
    public void setGlobalChessboard(Chessboard globalChessboard) {
        this.globalChessboard = globalChessboard;
    }

    /**
     * @return the algorithm
     */
    public AlphaBeta getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm the algorithm to set
     */
    public void setAlgorithm(AlphaBeta algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * @return the black
     */
    public boolean isBlack() {
        return black;
    }

    /**
     * @param black the black to set
     */
    public void setBlack(boolean black) {
        this.black = black;
    }

    /**
     * @return the white
     */
    public boolean isWhite() {
        return white;
    }

    /**
     * @param white the white to set
     */
    public void setWhite(boolean white) {
        this.white = white;
    }
    
 }

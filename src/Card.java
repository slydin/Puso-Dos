/**
 * The Card class acts as a GUI class as well as a storage for each individual card's 
 * rank and suit. 
 * 
 * Name, Date, Modifications: 
 * John Doe, 10/10/10, Added some methods (specify) and added corrections (where?).
 * Jeric Derama, 8/03/2012, Completed UI functionality with Card components to be used in poker based card games. 
 * Jeric Derama, 02/01/2014, Removed GUI features and updated features to be text-based. 
 * Jeric Derama, 02/07/2014, Implemented Comparable<Card> class and compareTo() method.
 * Jeric Derama, 02/10/2014, Added resetStates() method to reduce amount of calls done in GameApp.java
 * @author Jeric Derama
 * @version 10 Feb 2014
 */
public class Card implements Comparable<Card>{ 
    private Integer suit;    
    private Integer rank;      
    private String fullSuit;
    private boolean pair;
    private boolean triple;
    private boolean straight;
    private boolean flush;
    private boolean full;
    private boolean four;
    private boolean straightFlush;
 
    /**
     * Card constructor for playing purposes
     * @param card The number assigned to the card
     */
    public Card(int rank,int suit) {	
    	// Sets the card's rank
    	this.rank = rank % 13;
        // Sets the card's numerical suit
    	this.suit = suit;
    	// Set up hand possible variables
    	this.pair = false;
    	this.triple = false;
    	this.straight = false;
    	this.flush = false;
        this.full = false;
        this.four = false;
        this.straightFlush = false;
    	
    	if(this.rank == 0)
    		this.rank = 13;
    	else if(this.rank == 1)
        	this.rank = 14;
        else if(this.rank == 2)
        	this.rank = 15;
        

    	fullSuit = null;
    	if(this.suit == 4)
    		this.fullSuit = "D";
    	else if(this.suit == 3)
    		this.fullSuit = "H";
    	else if(this.suit == 2)
    		this.fullSuit = "S";
    	else if(this.suit == 1)
    		this.fullSuit = "C";
    }
    
    /**
     * Getter method for this card's rank
     * @return the rank
     */
    public Integer getRank(){
    	return this.rank;
    }
    
    /**
     * Getter method for this card's numerical suit
     * @return the numerical suit
     */
    public Integer getSuit(){
    	return this.suit;
    } 
    
    /**
     * Getter method for this card's suit.
     * @return the suit in string form.
     */
    public String getFullSuit(){
    	return this.fullSuit;
    }
    
    /**
     * Method that resets the statuses of this card.
     */
    public void resetStates(){
    	this.pair = false;
    	this.triple = false;
    	this.straight = false;
    	this.flush = false;
    	this.full = false;
    	this.four = false;
    	this.straightFlush = false;
    }
    
    /**
     * Method that changes the card's state
     */
    public void changeDouble(){
        this.pair = !this.pair;
    }

    /**
     * Method that determines the card's state
     * @return true if it the state applies
     */
    public boolean isDouble()
    {
    	return this.pair;
    }
    
    /**
     * Method that changes the card's state
     */
    public void changeTriple(){
        this.triple = !this.triple;
    }

    /**
     * Method that determines the card's state
     * @return true if it the state applies
     */
    public boolean isTriple(){
    	return this.triple;
    }
    
    /**
     * Method that changes the card's state
     */
    public void changeStraight(){
        this.straight = !this.straight;
    }

    /**
     * Method that determines the card's state
     * @return true if it the state applies
     */
    public boolean isStraight(){
    	return this.straight;
    }
    
    /**
     * Method that changes the card's state
     */
    public void changeFlush(){
        this.flush = !this.flush;
    }

    /**
     * Method that determines the card's state
     * @return true if it the state applies
     */
    public boolean isFlush(){
    	return this.flush;
    }
    
    /**
     * Method that changes the card's state
     */
    public void changeFull(){
        this.full = !this.full;
    }

    /**
     * Method that determines the card's state
     * @return true if it the state applies
     */
    public boolean isFull(){
    	return this.full;
    }
    
    /**
     * Method that changes the card's state
     */
    public void change4OfAKind(){
        this.four = !this.four;
    }

    /**
     * Method that determines the card's state
     * @return true if it the state applies
     */
    public boolean is4OfAKind(){
    	return this.four;
    }
    
    /**
     * Method that changes the card's state
     */
    public void changeStraightFlush(){
        this.straightFlush = !this.straightFlush;
    }
    
    /**
     * Method that determines the card's state
     * @return true if it the state applies
     */
    public boolean isStraightFlush(){
    	return this.straightFlush;
    }
    
    @Override
    /**
     * Method Override of the toString() method for Objects
     */
    public String toString(){
    	return this.rank + " " + this.getFullSuit();
    }

	@Override
	/**
	 * Method that allows for the Card to be checked as a comparable object
	 */
	public int compareTo(Card o) {
        return o.getRank() - this.getRank();
	}
}

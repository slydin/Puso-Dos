/**
 * The Card class acts as a GUI class as well as a storage for each individual card's 
 * rank and suit. 
 * 
 * Name, Date, Modifications: 
 * John Doe, 10/10/10, Added some methods (specify) and added corrections (where?).
 * Jeric Derama, 8/03/2012, Completed UI functionality with Card components to be used in poker based card games. 
 * Jeric Derama, 02/01/2014, Removed GUI features and updated features to be text-based. 
 * Jeric Derama, 02/07/2014, Implemented Comparable<Card> class and compareTo() method.
 * @author Jeric Derama
 * @version 1 Feb 2014
 */
@SuppressWarnings("serial")
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
    		this.fullSuit = "Diamonds";
    	else if(this.suit == 3)
    		this.fullSuit = "Hearts";
    	else if(this.suit == 2)
    		this.fullSuit = "Spades";
    	else if(this.suit == 1)
    		this.fullSuit = "Clubs";
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
     * Getter method for this card's suit *useless method*
     * @return the suit
     */
    public String getFullSuit(){
    	return this.fullSuit;
    }
    
    /**
     * Method that changes the card's state
     */
    public void changeDouble(){
    	if(!this.pair)
    		this.pair = true;
    	else
    		this.pair = false;
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
    	if(!this.triple)
    		this.triple = true;
    	else
    		this.triple = false;
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
    	if(!this.straight)
    		this.straight = true;
    	else
    		this.straight = false;
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
    	if(!this.flush)
    		this.flush = true;
    	else
    		this.flush = false;
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
    	if(!this.full)
    		this.full = true;
    	else
    		this.full = false;
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
    	if(!this.four)
    		this.four = true;
    	else
    		this.four = false;
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
    	if(!this.straightFlush)
    		this.straightFlush = true;
    	else
    		this.straightFlush = false;
    }
    
    /**
     * Method that determines the card's state
     * @return true if it the state applies
     */
    public boolean isStraightFlush(){
    	return this.straightFlush;
    }
    
    @Override
    public String toString(){
    	return "" + this.rank + " of " + this.getFullSuit();
    }

	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		if(o.getRank() > this.getRank())
			return 1;
		else if(o.getRank() == this.getRank())
			return 0;
		else
			return -1;
	}
}

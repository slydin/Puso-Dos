import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The Player class acts as a GUI class and as a storage class for each card each player holds.
 * 
 * Name, Date, Modifications: 
 * John Doe, 10/10/10, Added some methods (specify) and added corrections (where?).
 * Jeric Derama, 7/18/2012, Made the Player class into a JPanel that will become a pop-up
 * 							If the player is a real player they will return true in the 
 * 							newly implemented method.
 * Lucas Kushner, 7/30/2012 Modified GUI code to fix player table interaction
 * @author Jeric
 * @version 24 June 2012
 */
@SuppressWarnings("serial")
public class Player extends JPanel
{
	private ArrayList<Card> hand;
	private static int number = 1;
	private int playerNum;
	boolean player;
	/**
	 * Constructor for a player
	 * @param a hand
	 */
	public Player(ArrayList<Card> given, boolean nonAI)
	{
		this.player = nonAI;
		this.playerNum = number;
		number++;
		this.hand = given;
	}
	
	/**
	 * Getter method for this player's hand
	 * @return this player's hand
	 */
	public ArrayList<Card> getHand(){
		return this.hand;
	}
	
	/**
	 * Setter method for this player's hand
	 * @param newHand to be given
	 */
	public void setHand(ArrayList<Card> newHand){
		this.hand = new ArrayList<Card>(newHand);
	}
	
	/** 
	 * Getter method to check if the player is an AI or an actual player
	 * @return true if it is an player
	 */
	public boolean isPlayer(){
		//TODO: whatever is supposed to actually happen here
		return this.player;
	}
	
	/**
	 * Getter method to get Player's number
	 * @return the player's number
	 */
	public int getNumber(){
		return this.playerNum;
	}
	
	
	
	
	
}
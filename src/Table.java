import java.awt.*;
import java.util.*;

/**
 * The Table class acts as both a gui frame and a storage for the put down by the 
 * players. 
 * 
 * Name, Date, Modifications: 
 * John Doe, 10/10/10, Added some methods (specify) and added corrections (where?).
 * Jeric Derama, 7/11/2012, Added some swing functionality and changed Table to extend JFrame. 
 * 							Updated the constructor, changed canPlay, and removed the play method and 
 * 							implemented it within canPlay. 
 * 
 * Jeric Derama, 7/17/2012, Re-implemented the play method and changed the Table constructor. Started on GUI.
 * Jeric Derama, 7/25/2012, Implemented some of the GUI code and set the layout to show the hand being played. 
 * 							Updated the table constructor which now requires the list of players.
 * Jeric Derama, 8/03/2012, Updated the canPlay method to hand all pass situations. Add allPass method. Updated play method. Removed
 * 							most of the GUI implementations - moved to the GameApp.
 * Jeric Derama, 8/09/2012, Added some GUI fixes in the play method. Still need to implement JMenu. 
 * Jeric Derama, 2/01/2014, Removed GUI features and updated methods to become text-based.
 * @author Jeric Derama
 * @version 1 Feb 2014
 */
@SuppressWarnings("serial")
public class Table{
	private ArrayList<Card> played;
	private Rank ranker;
	private boolean check;

	/**
	 * Constructor for the table where the cards are placed
	 * @param Cards being put down. 
	 */
	public Table(){
		this.ranker = new Rank();
		this.check = true;
	}
		
	/**
	 * Method that returns the played hand's size
	 */
	public int playedSize(){
		if(this.played == null)
			return 0;
		
		return this.played.size();
	}
	
	public String played(){
		if(this.played == null)
			return "A hand has not been played yet.";
		else{
			String cards = "";
			for(Card c: this.played)
				cards = c.toString() + ", ";
			return cards;
		}	

	}
	/**
	 * Checks to see if a player can play their hand by comparing to what has been
	 * currently put down. If they can play it is put down on the table and the current hand 
	 * changes. 
	 * @param hand that the current player wants to put down
	 * @return true or false whether the player could play
	 */
	public boolean canPlay(ArrayList<Card> hand){	
		// Handles game start hands. First hand must contain a 3 of clubs
		// this.check is changed in the play method when a 3 of clubs has been played
		if(this.check){
			for(Card c: hand)
				if(this.played == null  && c.getRank() == 3 && c.getFullSuit() == "Clubs")
						return true;
			if(this.played == null)
				return false;
		}

		// When null any hand can be played
		if(this.played == null)
			return true;
		
		// Make sure the hand being played matches the hand that has been played
		if(hand.size() != this.played.size())
			return false;
		// Compare the current hand the one being played
		if(this.ranker.rank(this.played) < ranker.rank(hand))
			return true;
		else if(this.ranker.rank(this.played) == this.ranker.rank(hand))
			if(this.ranker.sameRankGreaterSuit(hand,this.played))
				return true;
	
		return false;
	}
	
	/**
	 * Method that simulates when all players except for the last player to play have passed
	 */
	public void allPass(){
		this.played = null;
	}
	
	/**
	 * Method that sets a hand to be the current hand played on the table
	 * @param hand to be played
	 */
	public void play(ArrayList<Card> hand, Player p){
		// First check if the first hand has been played yet
		for(Card c: hand)
			if(c.getRank() == 3 && c.getSuit() == 1)
				this.check = false;

		System.out.println("This is player: "  + p.getNumber());
		System.out.println("This is the hand to be played: " + hand.toString());
		this.played = hand;
		System.out.println("This is the player's hand: " + p.getHand().toString());
		System.out.println("This is the size of this.played: " + this.played.size());
		System.out.println("This is the size of the player's hand: " + p.getHand().size());
		for(Card c: this.played){
			System.out.println("This is the index of c: " + p.getHand().indexOf(c));
			p.getHand().remove(p.getHand().indexOf(c));
		}
		
		String beingPlayed = "";
		for(Card c: this.played)
			beingPlayed = beingPlayed + " " + c.getRank() + " of " + c.getFullSuit();
		
		beingPlayed = beingPlayed + " has/have been played";
		System.out.println(beingPlayed);
		System.out.println("Player: " + p.getNumber() + " has played");
	}
	
}

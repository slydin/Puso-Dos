import java.util.ArrayList;
import java.util.Collections;

/**
 * The Rank class acts as a system where hands can be ranked and compared to decide whether 
 * or not they can be played
 * 
 * Name, Date, Modifications: 
 * John Doe, 10/10/10, Added some methods (specify) and added corrections (where?).
 * Jeric Derama, 8/03/2012, Changed the methods away from being static.
 * Jeric Derama, 02/01/2014, Updated rank() method for doubles and triples.
 * Jeric Derama, 02/07/2014, Updated isStraight() method.
 * Jeric Derama, 05/29/2014, Fixed isFullHouse() method.
 * @author Jeric
 * @version 7 Feb 2014
 */
public class Rank 
{	
	/**
	 * Method that determines rank of given hand
	 * @param playing the hand being played
	 * @return a rank between 1-5 for hands and their normal ranks for singles, doubles, and triples,
	 * 		   if 0 is returned it cannot be played
	 */
	public int rank(ArrayList<Card> playing)
	{
		int rank = 0;
		// If ranking against singles, doubles, and triples just use the card's rank. 
		if(playing.size() == 1){
			rank = playing.get(0).getRank();
		}
		else if(playing.size() == 2){
			if(playing.get(0).getRank() != playing.get(1).getRank())
				return -1;
			else
				return playing.get(0).getRank();
		}
		else if(playing.size() == 3){
			if(playing.get(0).getRank() == playing.get(1).getRank())
				if(playing.get(0).getRank() == playing.get(2).getRank())
					return playing.get(0).getRank();
				else
					return -1;
			else
				return -1;
		}
		// Calls on private methods to determine hand's rank
		if(playing.size() == 5)
		{
			
			if(this.isStraightFlush(playing))
				rank = 5;
			else if(this.isStraight(playing))
				rank = 1;
			else if(this.isFlush(playing))
				rank = 2;
			else if(this.isFullHouse(playing))
				rank = 3;
			else if(this.isFourOfAKind(playing))
				rank = 4;
			return rank;
		}
		
		return rank;
	}
	
	
	/**
	 * Private methods used by the method rank to determine the
	 * hands rank
	 * @param hand to be checked
	 * @return true if the hand is of this type
	 */
	@SuppressWarnings("unchecked")
	private boolean isStraight(ArrayList<Card> hand){
		ArrayList<Card> copy = (ArrayList<Card>) hand.clone();
		// First check if the hand size is 5
		if(!(copy.size() == 5))
			return false;
		
		Collections.sort(copy);
		Card c = copy.get(0);
		for(int i = 1; i < copy.size(); i++){
			Card d = copy.get(i);
			if(c.getRank() + i != d.getRank())
				return false;
		}
		return true;
	}
	/**
	 * Private methods used by the method rank to determine the
	 * hands rank
	 * @param hand to be checked
	 * @return true if the hand is of this type
	 */
	private boolean isFlush(ArrayList<Card> hand){
		boolean check = true;
		
		// First check if the hand size is 5
		if(!(hand.size() == 5))
			return false;
		
		int suit = hand.get(0).getSuit();
		
		// Check if each card in the hand has the same suit
		for(Card c: hand)
		{
			if(!(suit == c.getSuit()))
				check = false;
		}
		
		return check;
	}
	/**
	 * Private methods used by the method rank to determine the
	 * hands rank
	 * @param hand to be checked
	 * @return true if the hand is of this type
	 */
	private boolean isFullHouse(ArrayList<Card> hand){
		boolean check = false;
		// First check if the hand size is 5
		if(!(hand.size() == 5))
			return false;
		
		// Ranks and counters to keep track of the triple and the double
		int rank1 = hand.get(0).getRank();
		int rank2 = 0;
		int counter1 = 0;
		int counter2 = 0;
		
		// Go through each card
		for(Card c: hand){
			// If we don't get a match the second rank will
			// be assigned to that card
			if(rank1 == c.getRank())
				counter1++;
			else 
				rank2 = c.getRank();
			
			if(rank2 == c.getRank())
				counter2++;
		}
		
		// Checks the two possibilities
		if(counter1 == 3 && counter2 == 2)
			check = true;
		else if(counter1 == 2 && counter2 == 3)
			check = true;
		
		return check;
	}
	/**
	 * Private methods used by the method rank to determine the
	 * hands rank
	 * @param hand to be checked
	 * @return true if the hand is of this type
	 */
	private boolean isFourOfAKind(ArrayList<Card> hand){
		boolean check = false;
		// First check if the hand size is 5
		if(!(hand.size() == 5))
			return false;
		
		int rank = 0;
		int counter = 0;
		// Go through each card
		for(Card c: hand)
		{	
			// Check for every card
			rank = c.getRank();
			for(Card d: hand)
			{
				if(rank == d.getRank())
					counter++;
			}
			if(counter == 4)
				check = true;
		}
		return check;
	}
	/**
	 * Private methods used by the method rank to determine the
	 * hands rank
	 * @param hand to be checked
	 * @return true if the hand is of this type
	 */
	private boolean isStraightFlush(ArrayList<Card> hand){
		if(!(hand.size() == 5))
			return false;
		if(this.isStraight(hand) && this.isFlush(hand))
			return true;
		
		return false;
	}
	
	/**
	 * Method used when two cards have the same rank and checks whether 
	 * the first hand is stronger than the second one. 
	 * @param one the first hand
	 * @param two the second hand
	 * @return true if the first hand is stronger than the second
	 */
	public boolean sameRankGreaterSuit(ArrayList<Card> one, ArrayList<Card> two){
		boolean check = false;
		int rank = this.rank(one);
		// Calls on the private methods to check if the first hand
		// is stronger than the second.
		if(one.size() == 1)
			if(one.get(0).getSuit() > two.get(0).getSuit())
				return true;
			else 
				return false;
		if(one.size() == 2){
			int oneSuit = 0;
			int twoSuit = 0;
			for(Card c: one)
				if(oneSuit < c.getSuit())
					oneSuit = c.getSuit();
			for(Card c: two)
				if(twoSuit < c.getSuit())
					twoSuit = c.getSuit();
				
		}
		if(rank == 1)
			check = straight(one,two);
		else if(rank == 2)
			check = flush(one,two);
		else if(rank == 3) 
			check = fullHouse(one,two);
		else if(rank == 4)
			check = fourOfAKind(one,two);
		else if(rank == 5)
			check = straightFlush(one,two);
		
		return check;
	}
	
	/**
	 * Private method used by sameRank method to establish which hand
	 * is stronger when both are straight.
	 * @param one the first hand
	 * @param two the second hand
	 * @return true if the first hand is stronger than the second
	 */
	private boolean straight(ArrayList<Card> one, ArrayList<Card> two){
		// Find the strongest card in each hand and match the two
		int max1 = 0;
		int max2 = 0;
		boolean check = false;
		// Find max for the first hand
		for(Card c: one)
			if(max1 < c.getRank())
					max1 = c.getRank();
				
				
				// Find max for the second hand
		for(Card c: one)
			if(max2 < c.getRank())
					max2 = c.getRank();
				
				
				// If they are equal check the suit
		if(max1 > max2)
			check = true;
		else if(max1 == max2)
			if(one.get(0).getSuit() > two.get(0).getSuit())
				check = true;;
				
				
		return check;
	}

	/**
	 * Private method used by sameRank method to establish which hand
	 * is stronger when both are flushes.
	 * @param one the first hand
	 * @param two the second hand
	 * @return true if the first hand is stronger than the second
	 */
	private boolean flush(ArrayList<Card> one, ArrayList<Card> two){
		return this.straight(one,two);
	}

	/**
	 * Private method used by sameRank method to establish which hand
	 * is stronger when both are full houses.
	 * @param one the first hand
	 * @param two the second hand
	 * @return true if the first hand is stronger than the second
	 */
	private boolean fullHouse(ArrayList<Card> one, ArrayList<Card> two){
		// Find the value of the triples in each hand and compare them
		int triple1 = 0;
		int triple2 = 0;
		
		int counter = 0;
		// First hand's loop to find the triple 
		for(Card c: one)
		{
			int temp = c.getRank();
			for(Card d: one)
				if(temp == d.getRank())
					counter++;
			// When it is 2 we have found a triple
			if(counter == 2)
				triple1 = temp;
			
			counter = 0;
		}
		
		// Second hand's loop to find the triple
		for(Card c: two)
		{
			int temp = c.getRank();
			for(Card d: two)
				if(temp == d.getRank())
					counter++;
			// When it is 2 we have found a triple
			if(counter == 2)
				triple2 = temp;
			
			counter = 0;
		}
		
		// Compare the triple values
		if(triple1 > triple2)
			return true;
		
		return false;
	}

	/**
	 * Private method used by sameRank method to establish which hand
	 * is stronger when both are four of a kind.
	 * @param one the first hand
	 * @param two the second hand
	 * @return true if the first hand is stronger than the second
	 */
	private boolean fourOfAKind(ArrayList<Card> one, ArrayList<Card> two){
		// Find the value of the triples in each hand and compare them
				int quad1 = 0;
				int quad2 = 0;
				
				int counter = 0;
				// First hand's loop to find the triple 
				for(Card c: one)
				{
					int temp = c.getRank();
					for(Card d: one)
						if(temp == d.getRank())
							counter++;
					// When it is 3 we have found a triple
					if(counter == 3)
						quad1 = temp;
					
					counter = 0;
				}
				
				// Second hand's loop to find the triple
				for(Card c: two)
				{
					int temp = c.getRank();
					for(Card d: two)
						if(temp == d.getRank())
							counter++;
					// When it is 3 we have found a triple
					if(counter == 3)
						quad2 = temp;
					
					counter = 0;
				}
				
				// Compare the triple values
				if(quad1 > quad2)
					return true;
				
				return false;
	}

	/**
	 * Private method used by sameRank method to establish which hand
	 * is stronger when both are straight flushes.
	 * @param one the first hand
	 * @param two the second hand
	 * @return true if the first hand is stronger than the second
	 */
	private boolean straightFlush(ArrayList<Card> one, ArrayList<Card> two){
		return this.straight(one,two);
	}
}

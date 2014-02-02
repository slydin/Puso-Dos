import java.util.*;


/**
 * The GameApp class initiates the game between the players and plays the game. 
 * (Currently aimed toward AI vs. AI and building upon Player vs. AI and PVP)
 * 
 * Name, Date, Modifications: 
 * John Doe, 10/10/10, Added some methods (specify) and added corrections (where?).
 * Jeric Derama, 8/03/2012, Added GUI implementations via mouse listeners and moved class table swing over to GameApp. 
 * 							Updates on play, startGame, and pass methods. Implemented user step method for nonAI players.
 * Jeric Derama, 8/04/2012, Updated and added AI methods to play hands. Need to fix findStraight and findStraightFlush methods. 
 * Jeric Derama, 2/01/2014, Removed GUI features and updated methods to become text-based. Updated playAI method to handle 
 * 							first player to play scenarios (Has Three of Clubs).
 * @author Jeric
 * @version 1 Feb 2014
 */
public class GameApp{
	private ArrayList<Card> deck, toBePlayed;
	private ArrayList<Player> players;
	private Table t;
	private Random r;
	private int numPlayers, starter, passCounter;
	private boolean yourTurn, straight, flush, full, four, straightFlush;    
    private Scanner scan;
	
	/**
	 * Constructor for the GameApp that runs the simulation
	 * @param numberOfPlayers determines the number of players to determine
	 * the number of AIs.
	 */
	public GameApp(int numberOfPlayers){
		this.numPlayers = numberOfPlayers;
		this.deck = new ArrayList<Card>();
		this.players = new ArrayList<Player>();
		this.passCounter = 0;
		this.yourTurn = false;
		this.t = new Table();
		this.initialize();
	}
	
	/**
	 * Method that initializes the game
	 */
	public void initialize(){
		scan = new Scanner(System.in);
		// Creates a deck full of cards
		for(int j = 0; j < 52; j++){
			Card c = new Card(j%13,j/13+1);
			this.deck.add(c);
		}	
		// Shuffles the deck for a certain amount
		for(int i = 0; i < 52; i++)
			this.deck = this.shuffle(this.deck);
		// Deal the cards to the players 
		this.deal(this.deck);
		
		// Start the game
		this.startGame(this.players);
		if(!this.players.get(this.starter).isPlayer())
			this.play();
		else {
			this.yourTurn = true;
			this.step(this.players.get(this.starter));
		}
	}
	
	/**
	 * When a winner is reached it ends the game. 
	 * @param winner who wins the game
	 */
	public void win(Player winner){
		System.out.println("Player: " + winner.getNumber() + " has won!");
	}
	
	/**
	 * Getter method for the deck
	 * @return the deck
	 */
	public ArrayList<Card> getDeck(){
		return this.deck;
	}
	
	/**
	 * Getter method for the game table
	 * @return the table
	 */
	public Table getTable(){
		return this.t;
	}
	
	/**
	 * Method that shuffles the deck
	 * @param deck to be shuffled 
	 * @return the shuffled deck
	 */
	public ArrayList<Card> shuffle(ArrayList<Card> deck){	
		// The shuffled deck
		ArrayList<Card> shuffled = new ArrayList<Card>();
		// Making a random generator
		Random randGen = new Random();
		// To handle the changing deck size
		int deckSize = deck.size();
		// Loop to go through each card in the deck
		for(int i = 0; i < deckSize; i++){
			// Number that keeps track at which card will be pulled out
			int random = randGen.nextInt(deck.size());
			// Shuffling*
			shuffled.add(deck.get(random));
			// Keeps track at which cards have been shuffled or not
			deck.remove(random);
		}
		// When done shuffling return that that has been shuffled
		return shuffled;
	}
	
	/**
	 * Deals the deck out to the players
	 * @param party to be dealt with
	 */
	public void deal(ArrayList<Card> cards){
		// Start the counter at 12, starting it at the back instead of having it
		// at 0 helps prevent any Array bound issues
		int counter = 12;
		// Counter for amount of players
		int playerCount = this.numPlayers;
		// Initialize an array list to be utilized to be the hand for each player
		ArrayList<Card> hand = new ArrayList<Card>();
		// For loop to go through the given deck known as cards to be distributed to each player
		for(int j = 51; j >= 0; j--){
			// Boolean checker to check how many players there are 
			Boolean check = false;
			// Add said card to a given hand
			hand.add(cards.get(j));
			// Remove the card from the deck
			cards.remove(j);
			// Decrease the counter
			counter--;
			// When the counter reaches 0 we go to the next player
			if(counter < 0){
				// Construct a player and add it to the array
				// But first check if there are players
				if(playerCount != 0)
					check = true;
				Player p = new Player(hand,check);
				this.players.add(p);
				// Reinitialize the ArrayList for the next player
				hand = new ArrayList<Card>();
				// Restart the counter
				counter = 12;
				if(check == true)
					playerCount--;
			}
		}
		for(Player p: this.players)
			if(!p.isPlayer())
				this.makeHands(p);
		
	}
	
	
	/**
	 * Method that finds all the possible hands that a player may have in hand
	 * @param p the player to be checked
	 */
	public void makeHands(Player p){
		this.findDoubles(p);
		this.findTriples(p);
	//	this.findStraights(p);
		this.findFlushes(p);
		this.findFullHouses(p);
		this.find4OfAKinds(p);
	//	this.findStraightFlushes(p);
	}

	/**
	 * Method that runs the simulation for both the Non-AI player and the AI
	 */
	public void play(){
		// Starter puts his hand down containing the 3 of clubs
		// Now the game begins with the next person on the list
		this.r = new Random();
		boolean check = true;
		// Keeps playing until we have a winner
		while(check){
			// First check if the player before the current player has already won
			Player before = null;
			if(this.starter - 1 < 0)
				before = this.players.get(3); 
			else
				before = this.players.get(this.starter - 1);
			// Then we let the current player start
			Player p = this.players.get(this.starter);
			// If a player's hand reaches 0 that player has won the game.
			if(before.getHand().size() == 0){
				// Call the win method
				this.win(before);
				check = false;
				return;
			}
			if(p.isPlayer()){
				this.yourTurn = true;
				check = false;
				this.step(p);
			}
			else
				this.playAI(this.t.playedSize(), p);
		}
	}

	/**
	 * Method that allows for a player (the user) to interact in the game
	 * @param p the player that of which is the user
	 */
	public void step(Player p){
		if(this.yourTurn){
			// Initialize the hand
			boolean check = true;
			while(check){
				this.toBePlayed = new ArrayList<Card>();
				if(!(this.t.playedSize() == 0)){
					System.out.println("This is your hand: " );
					System.out.println(p.getHand().toString());
					System.out.println("Would you like to pass this turn? (y/n)");
					String answer = scan.next();
					if(answer.equals("y")){
						this.pass();
						this.play();
						break;
					}
					System.out.println("The last hand played: " + this.t.played());
					System.out.println("You have to play a hand with: " + this.t.playedSize() + " card(s)");
					System.out.println("This is your hand: ");
					String playerHand = "";
					for(int i = 0; i < p.getHand().size(); i++){
						Card c = p.getHand().get(i);
						playerHand = playerHand + c.toString() + " (" + i + "), ";
					}
					System.out.println(playerHand);
					for(int i = 0; i < t.playedSize(); i++){
						System.out.println("Choose a card to be played by inputing the index starting from 0: ");
						this.toBePlayed.add(p.getHand().get(scan.nextInt()));
					}
					// Check if the hand can be played
					if(this.t.canPlay(toBePlayed)){
						this.t.play(toBePlayed, p);
						this.passCounter = 0;
						this.endTurn();
						this.play();
						check = false;
					}
					else
						System.out.println("This hand cannot be played.");
				}
				else{
					System.out.println("The last hand played: " + this.t.played());
					System.out.println("This is your hand: ");
					System.out.println(p.getHand().toString());
					int input = 1;;
					if(p.getHand().size() > 4){
						System.out.println("How many cards would you like to play: (1/2/3/5)");
						input = scan.nextInt();
					}
					else if(p.getHand().size() > 2){
						System.out.println("How many cards would you like to play: (1/2/3)");
						input = scan.nextInt();
					}
					else if(p.getHand().size() > 1){
						System.out.println("How many cards would you like to play: (1/2)");
						input = scan.nextInt();
					}
						

					if(input == 1 || input == 2 || input == 3 || input == 5){
						String playerHand = "";
						for(int i = 0; i < p.getHand().size(); i++){
							Card c = p.getHand().get(i);
							playerHand = playerHand + c.toString() + " (" + i + "), ";
						}
						System.out.println(playerHand);
							for(int i = 0; i < input; i++){
								System.out.println("Choose a card to be played by inputing the index of the card starting from 0: ");
								toBePlayed.add(p.getHand().get(scan.nextInt()));
							}
							if(this.t.canPlay(toBePlayed)){
								this.t.play(toBePlayed, p);
								this.passCounter = 0;
								this.endTurn();
								this.play();
								check = false;
							}
							else
								System.out.println("This hand is invalid. Please try again.");
					}
					else
						System.out.println("You can only have a hand size of 1, 2, 3, or 5. Please try again.");	
				}
			}
		}
	
	}

	/**
	 * Method that helps the AI determine what to play
	 * @param handSize to play appropriate hands
	 */
	public void playAI(Integer handSize, Player p){
		boolean check = false;
		// Check if the computer has a three of clubs, if so they must start.
		boolean ThreeOfClubs = false;
		for(int i = 0; i < p.getHand().size(); i++){
			Card c = p.getHand().get(i);
			if(c.getRank() == 3 && c.getSuit() == 1)
				ThreeOfClubs = true;
		}
		// Computer has limited amount of tries before passing.
		int tries = 0;
		while(!check){
			if(tries == 5 && !ThreeOfClubs){
				this.pass();
				System.out.println("Player " + p.getNumber() + " has passed.");
				return;
			}
			else if(tries == 5 && ThreeOfClubs){
				ArrayList<Card> hand = new ArrayList<Card>();
				this.t.play(hand, p);
				this.resetHand(p);
				this.endTurn();
				this.passCounter = 0;
				return;
			}
			// If the current hand is null let the AI play at random
			if(handSize == 0){
				int ran = 0;
				if(this.hasDoubles(p))
					ran = this.r.nextInt(1);
				if(this.hasTriples(p))
					ran = this.r.nextInt(2);
				if(this.hasHand(p))
					ran = this.r.nextInt(3);
				
				if(ran == 0)
					check = this.playSingle(p);
				else if(ran == 1)
					check = this.playDouble(p);
				else if(ran == 2)
					check = this.playTriple(p);
				else if(ran == 3)
					check = this.playHand(p);
					
			}
			else if(handSize == 1)
				check = this.playSingle(p);
			else if(handSize == 2)
				check = this.playDouble(p);
			else if(handSize == 3)
				check = this.playTriple(p);
			else if(this.hasHand(p))
				check = this.playHand(p);

			tries++;
		}
		this.resetHand(p);
	}

	public void startGame(ArrayList<Player> party){
		// Check the players for the 3 of clubs
		// Counter to check who started first
		int finder = 0;
		// Second variable for starter
		this.starter = 0;
		
		for(Player p: party){	
			// Create a counter
			int counter = 0;
			// Check each players hand
			for(Card c: p.getHand()){
				// Finding the 3 of clubs
				if(c.getRank() == 3 && c.getSuit() == 1){
					this.starter = finder;
					return;
				}
				// Increasing the counter so we can remove the card when put
				// on the table
					counter++;
			}
			// Increase the player counter to the next player;
			finder++;
		}
	
	}

	/**
	 * When a certain player cannot play they pass their turn to the next person
	 * @param who's turn it is
	 */
	public int pass(){
		// passCounter is used for instances when 3 people have passed. The next player can place any hand they want therein after
		this.passCounter++;
		if(this.passCounter == 3)
			this.t.allPass();
		// Makes sure that this.starter goes around to each player
		if(this.starter == 3){
			this.starter = 0;
			return this.starter;
		}
		this.starter++;
		
		return this.starter;
	}

	/**
	 * Same implementation as pass method but does not include the pass counter
	 * @return
	 */
	public int endTurn(){
		// Makes sure that this.starter goes around to each player
		if(this.starter == 3){
			this.starter = 0;
			return this.starter;
		}
		this.starter++;
		return this.starter;
	}

	/**
	 * Checker method to see if two or more players can still play
	 * 
	 * This method is for later use if game still continues after someone already wins.
	 * @param Players in the game
	 * @return true or false whether or not the game can continue
	 */
	public boolean handCheck(ArrayList<Player> stillIn){
		boolean check = false;
		int counter = 0;
		// Checks to see if 2 or more players still have a hand
		for(Player p: stillIn){
			if(p.getHand().size() >= 1)
				counter++;
		}
		// If 2 or more players still have a hand continue the game
		if(counter >= 2)
			check = true;
		return check;
	}

//	public void makePlayerPlayThreeOfClubs(Player p, int locationOfThreeOfClubs){
//		Card threeOfClubs = p.getHand().get(locationOfThreeOfClubs);
//		ArrayList<Card> hand = new ArrayList<Card>();;
//		hand.add(threeOfClubs);
//		ArrayList<String> possibilities = new ArrayList<String>();
//		if(threeOfClubs.isDouble())
//			possibilities.add("double");
//		if(threeOfClubs.isTriple())
//			possibilities.add("triple");
//		if(threeOfClubs.isStraight())
//			possibilities.add("straight");
//		if(threeOfClubs.isFlush())
//			possibilities.add("flush");
//		if(threeOfClubs.is4OfAKind())
//			possibilities.add("four");
//		if(threeOfClubs.isStraightFlush())
//			possibilities.add("sflush");
//		Random r = new Random();
//		int ran = r.nextInt(possibilities.size());
//		switch(possibilities.get(ran)){
//			
//		}
//	}
	/**
	 * Method used by make hands to find given state
	 * @param p the player 
	 */
	private void findDoubles( Player p){
		ArrayList<Card> hand = p.getHand();
		// Initialize a counter
		int counter = 0;
		// Look for extras
		for(Card c: hand){
			for(Card d: hand){
				// If the ranks match add to the temporary hand
				// and remove it from the original hand
				if(c.getRank() == d.getRank() && c.getSuit() != d.getSuit())
					counter++;			
			}
			// if the counter has exceeded the given number
			// add it to temp as well and remove from the original hand
			if(counter >= 1){
				for(Card d: hand)
					if(c.getRank() == d.getRank() && c.getSuit() != d.getSuit() && !d.isDouble())
						d.changeDouble();
				
				if(!c.isDouble())
					c.changeDouble();
			}
			// Reset the counter
			counter = 0;
		}
		// Set the player's hand
		p.setHand(hand);
	}

	/**
	 * Method used by make hands to find given state
	 * @param p the player 
	 */
	private void findTriples(Player p){
		ArrayList<Card> hand = p.getHand();
		// Initialize a counter
		int counter = 0;
		// Look for extras
		for(Card c: hand){
			for(Card d: hand){
				// If the ranks match add to the temporary hand
				// and remove it from the original hand
				if(c.getRank() == d.getRank() && c.getSuit() != d.getSuit())
					counter++;			
			}
			// if the counter has exceeded the given number
			// add it to temp as well and remove from the original hand
			if(counter >= 2){
				for(Card d: hand)
					if(c.getRank() == d.getRank() && c.getSuit() != d.getSuit() && !d.isTriple())
						d.changeTriple();
				
				if(!c.isTriple())
					c.changeTriple();
			}
			// Reset the counter
			counter = 0;
		}
		// Set the player's hand
		p.setHand(hand);
		
	}

	/**
	 * Method used by make hands to find given state
	 * @param p the player 
	 */
	private void findStraights(Player p){
		ArrayList<Card> hand = p.getHand();
		// Initialize a temporary hand
		ArrayList<Card> temp = new ArrayList<Card>();
		ArrayList<Card> extra = new ArrayList<Card>();
		// Initialize straight hand counter
		int straightCounter = 1;
		boolean check1 = false;
		boolean check2 = false;
		boolean check3 = false;
		boolean check4 = false;
		
		// Go for each card
		for(Card c: hand){
			// Reset the counter
			straightCounter = 0;
			
			for(Card d: hand){
				if(c.getRank() + 1 == d.getRank()){
					extra.add(d);
					check1 = true;
				}
				if(c.getRank() + 2 == d.getRank() && check1){
					extra.add(d);
					check2 = true;
				}
				if(c.getRank() + 3 == d.getRank() && check2){
					extra.add(d);
					check3 = true;
				}
				if(c.getRank() + 4 == d.getRank() && check3){
					extra.add(d);
					check4 = true;
				}
				if(straightCounter == hand.size() - 1 || extra.size() == 4){	
					if(check4){						
						extra.add(c);
						for(Card e: extra){
							if(!e.isStraight())
								e.changeStraight();
						}
					}
					else
						extra = new ArrayList<Card>();
				}
				straightCounter++;
			}
			if(temp.size() == 0)
				temp = extra;
			else{
				boolean check = false;
				int size = temp.size();
				for(Card d: extra){
					for(int i = 0; i < size; i++){
						Card e = temp.get(i);
						if(d.getRank() == e.getRank() && d.getSuit() == e.getSuit())
							check = true;
					}
					if(!check)
						temp.add(d);
					check = true;
				}
			}

		}
		// Initialize another temporary hand
		ArrayList<Card> temp2 = new ArrayList<Card>();
		// Initialize a boolean
		boolean check = false;
		// Check for cards not added to temp 
		for(Card c: hand){	
			for(Card d: temp)
				if(c.getRank() == d.getRank() && c.getSuit() == d.getSuit())
					check = true;
			// If check is false the card is added to temp2
			if(!check)
				temp2.add(c);
		}			
		// Add everything in temp2 to temp
		temp.addAll(temp2);
		p.setHand(temp);
		
	}

	/**
	 * Method used by make hands to find given state
	 * @param p the player 
	 */
	private void findFlushes(Player p){
		ArrayList<Card> hand = p.getHand();
		int counter = 0;
		// Loop through each card
		for(Card c: hand){
			for(Card d: hand){
				// Make sure the suits match
				if(c.getSuit() == d.getSuit()){
					// Add to the counter if the card has not yet been changed
					if(!d.isFlush())
						counter++;
				}
			}
			// Prevent changing the states of cards whose suit has less than 
			// 5 in the player's hand
			if(counter >= 4){
				for(Card d: hand){
					if(c.getSuit() == d.getSuit())
						if(!d.isFlush())
							d.changeFlush();
				}
			}
			// Reset the counter
			counter = 0;
		}
		// Set the player's hand
		p.setHand(hand);
	}

	/**
	 * Method used by make hands to find given state
	 * @param p the player 
	 */
	private void findFullHouses(Player p){
		ArrayList<Card> hand = p.getHand();
		// First check if it is even possible for player to have a Full House
		Card possible = null; 
		boolean check1 = false;
		boolean check2 = false;
		
		// Checks for triples and doubles
		for(Card c: hand){
			if(c.isTriple())
				check1 = true;
			if(c.isDouble())
				check2 = true;
		}
		
		// Go through the hand if there are triples and doubles present
		if(check1 && check2){
			for(Card c: hand){
				// Use cards that have triples
				if(c.isTriple())
					possible = c;
				// If possible isn't null we can go through the hand
				if(possible != null){
					for(Card d: hand){
						// Go for the cards that are triples with possible
						if(possible.getRank() == d.getRank() && possible.getSuit() != d.getSuit()){
								if(!possible.isFull())
									possible.changeFull();
								if(!d.isFull())
									d.changeFull();
							}
						// Get the doubles
						if(possible.getRank() != d.getRank() && d.isDouble())
							if(!d.isFull())
								d.changeFull();
					}
				}
			}
		}
		// Set the player's hand to the current one
		p.setHand(hand);
			
	}
	
	/**
	 * Method used by make hands to find given state
	 * @param p the player 
	 */
	private void find4OfAKinds(Player p){
		ArrayList<Card> hand = p.getHand();
		// Initialize a counter
		int counter = 0;
		// Look for extras
		for(Card c: hand){
			for(Card d: hand){
				// If the ranks match add to the temporary hand
				// and remove it from the original hand
				if(c.getRank() == d.getRank() && c.getSuit() != d.getSuit())
					counter++;			
			}
			// if the counter has exceeded the given number
			// add it to temp as well and remove from the original hand
			if(counter >= 3){
				for(Card d: hand)
					if(c.getRank() == d.getRank() && c.getSuit() != d.getSuit() && !d.is4OfAKind())
						d.change4OfAKind();
				
				if(!c.is4OfAKind())
					c.change4OfAKind();
			}
			// Reset the counter
			counter = 0;
		}
		// Set the player's hand
		p.setHand(hand);
	}
	
	/**
	 * Method used by make hands to find given state
	 * @param p the player 
	 */
	private void findStraightFlushes(Player p){
		ArrayList<Card> hand = p.getHand();
		// Initialize a temporary hand
		ArrayList<Card> temp = new ArrayList<Card>();
		ArrayList<Card> extra = new ArrayList<Card>();
		// Initialize straight hand counter
		int straightCounter = 1;
		boolean check1 = false;
		boolean check2 = false;
		boolean check3 = false;
		boolean check4 = false;
		
		// Go for each card
		for(Card c: hand){
			// Reset the counter
			straightCounter = 0;
			
			for(Card d: hand){
				if(c.getRank() + 1 == d.getRank() && c.getSuit() == d.getSuit()){
					extra.add(d);
					check1 = true;
				}
				if(c.getRank() + 2 == d.getRank() && check1 && c.getSuit() == d.getSuit()){
					extra.add(d);
					check2 = true;
				}
				if(c.getRank() + 3 == d.getRank() && check2 && c.getSuit() == d.getSuit()){
					extra.add(d);
					check3 = true;
				}
				if(c.getRank() + 4 == d.getRank() && check3 && c.getSuit() == d.getSuit()){
					extra.add(d);
					check4 = true;
				}
				if(straightCounter == hand.size() - 1 || extra.size() == 4){	
					if(check4){						
						extra.add(c);
						for(Card e: extra){
							if(!e.isStraightFlush())
								e.changeStraightFlush();
						}
					}
					else
						extra = new ArrayList<Card>();
				}
				straightCounter++;
			}
			if(temp.size() == 0)
				temp = extra;
			else{
				boolean check = false;
				int size = temp.size();
				for(Card d: extra){
					for(int i = 0; i < size; i++){
						Card e = temp.get(i);
						if(d.getRank() == e.getRank() && d.getSuit() == e.getSuit())
							check = true;
					}
					if(!check)
						temp.add(d);
					check = true;
				}
			}

		}
		// Initialize another temporary hand
		ArrayList<Card> temp2 = new ArrayList<Card>();
		// Initialize a boolean
		boolean check = false;
		// Check for cards not added to temp 
		for(Card c: hand){	
			for(Card d: temp)
				if(c.getRank() == d.getRank() && c.getSuit() == d.getSuit())
					check = true;
			// If check is false the card is added to temp2
			if(!check)
				temp2.add(c);
		}			
		// Add everything in temp2 to temp
		temp.addAll(temp2);
		p.setHand(temp);
	}
	
	/**
	 * Method that resets the state of each card in a player's hand
	 * @param p the player to reset
	 */
	private void resetHand(Player p){
		// Reset the states for each card
		for(Card c: p.getHand()){
			if(c.isDouble())
				c.changeDouble();
			if(c.isTriple())
				c.changeTriple();
			if(c.isStraight())
				c.changeStraight();
			if(c.isFlush())
				c.changeFlush();
			if(c.isFull())
				c.changeFull();
			if(c.is4OfAKind())
				c.change4OfAKind();
			if(c.isStraightFlush())
				c.changeStraightFlush();
		}
		
		// Set the new states for each card
		this.makeHands(p);
	}
	
	/**
	 * Method that looks for doubles in a given player's hand
	 * @param p the player
	 * @return true if their hand contains doubles
	 */
	private boolean hasDoubles(Player p){
		// Checks hand for doubles
		boolean check = false;
		for(Card c: p.getHand())
			if(c.isDouble())
				check = true;
		return check;
	}
	
	/**
	 * Method that looks for triples in a given player's hand
	 * @param p the player
	 * @return true if their hand contains triples
	 */
	private boolean hasTriples(Player p){
		// Checks hand for triples
		boolean check = false;
		for(Card c: p.getHand())
			if(c.isTriple())
				check = true;
		return check;
	}
	
	/**
	 * Method that looks for five card hands in a given player's hand
	 * @param p the player
	 * @return true if their hand contains 5 card hands
	 */
	private boolean hasHand(Player p){
		// Checks hands for 5 card hands
		this.straight = false;
	    this.flush = false;
	    this.full = false;
	    this.four = false;
	    this.straightFlush = false;
		boolean check = false;
		for(Card c: p.getHand()){
				if(c.isStraight()){
						check = true;
						this.straight = true;
					}
				if(c.isFlush()){
						check = true;
						this.flush = true;
					}
				if(c.isFull()){
						check = true;
						this.full = true;
					}
				if(c.is4OfAKind() && p.getHand().size() >= 5){
						check = true;
						this.four = true;
					}
				if(c.isStraightFlush()){
						check = true;
						this.straightFlush = true;
					}
			}
				
		return check;
	}
	
	/**
	 * Method that would play a random single for an AI
	 * @param p the player
	 */
	private boolean playSingle(Player p){
		//Create an ArrayList of hands the are the possibilities to be played each turn by for each player
		ArrayList<ArrayList<Card>> possible = new ArrayList<ArrayList<Card>>();
		// Go through a loop (temporary) for each card and initialize each card as a hand if it is possible to play
		for(Card c: p.getHand()){
			// Initialize a hand to be added to the possibilities to be played
			ArrayList<Card> hands = new ArrayList<Card>();
			hands.add(c);
			// Check if the hand can be played and add it to the possibilities if it can
			if(this.t.canPlay(hands))
				possible.add(hands);
		}
		// Use a random generator to choose a hand out of the possibilities
		int ranNum = 0;
		if(possible.size() > 1)
			ranNum = this.r.nextInt(possible.size());

		if(ranNum >= 0  && possible.size() >= 1){
				// Use the random number and play that hand
				this.t.play(possible.get(ranNum),p);
				// Reset the counter
				this.passCounter = 0;
				// End the turn
				this.starter = this.endTurn();
				return true;
			}
//		else{
//				System.out.println("Player: "+ p.getNumber() + " has passed");
//				this.starter = this.pass();
//			}
		return false;
	}
	
	/**
	 * Method that would play a double for the AI
	 * @param p the player
	 */
	private boolean playDouble(Player p){
		//Create an ArrayList of hands the are the possibilities to be played each turn by for each player
		ArrayList<ArrayList<Card>> possible = new ArrayList<ArrayList<Card>>();
		// Go through a loop (temporary) for each card and initialize each card as a hand if it is possible to play
		for(Card c: p.getHand()){
			// Initialize a hand to be added to the possibilities to be played
			ArrayList<Card> hands = new ArrayList<Card>();
			for(Card d: p.getHand()){
				if(c.getRank() == d.getRank() && c.getSuit() != d.getSuit() && c.isDouble() && d.isDouble()){
					hands.add(c);
					hands.add(d);
					// Check if the hand can be played and add it to the possibilities if it can
					if(this.t.canPlay(hands))
						possible.add(hands);
				}
			}
		}
		// Use a random generator to choose a hand out of the possibilities
		int ranNum = 0;
		if(possible.size() > 1)
			ranNum = this.r.nextInt(possible.size());

		if(ranNum >= 0  && possible.size() >= 1){
				// Use the rand	 wom number and play that hand
				this.t.play(possible.get(ranNum),p);
				// Reset the counter
				this.passCounter = 0;
				// End the turn
				this.starter = this.endTurn();
				return true;
			}
//		else {
//				System.out.println("Player: "+ p.getNumber() + " has passed");
//				this.starter = this.pass();
//			}
		return false;
	}
	
	/**
	 * Method that would play a triple for the AI
	 * @param p the player
	 */
	private boolean playTriple(Player p){
		//Create an ArrayList of hands the are the possibilities to be played each turn by for each player
		ArrayList<ArrayList<Card>> possible = new ArrayList<ArrayList<Card>>();
		// Go through a loop (temporary) for each card and initialize each card as a hand if it is possible to play
		for(Card c: p.getHand()){
			// Initialize a hand to be added to the possibilities to be played
			ArrayList<Card> hands = new ArrayList<Card>();
			for(Card d: p.getHand()){
				if(c.getRank() == d.getRank() && c.getSuit() != d.getSuit() && c.isTriple() && d.isTriple()){
					hands.add(c);
					hands.add(d);
					// Check if the hand can be played and add it to the possibilities if it can
					if(this.t.canPlay(hands)){
						possible.add(hands);
						hands = new ArrayList<Card>();
					}
				}
			}
		}
		// Use a random generator to choose a hand out of the possibilities
		int ranNum = 0;
		if(possible.size() > 1)
			ranNum = this.r.nextInt(possible.size());

		if(ranNum >= 0  && possible.size() >= 1){
				// Use the random number and play that hand
				this.t.play(possible.get(ranNum),p);
				// Reset the counter
				this.passCounter = 0;
				// End the turn
				this.starter = this.endTurn();
				return true;
			}
//		else {
//				System.out.println("Player: "+ p.getNumber() + " has passed");
//				this.starter = this.pass();
//			}
		return false;
	}
	
	/** 
	 * Method that would play a 5 card hand for the AI
	 * @param p the player
	 */
	private boolean playHand(Player p)
	{
		//Create an ArrayList of hands the are the possibilities to be played each turn by for each player
		ArrayList<ArrayList<Card>> possible = new ArrayList<ArrayList<Card>>();
		int counter = 1;
		// If the player is the first to play and contains a straight
		// If the player contain's a straight
		if(this.straight){
			// Go through each card
			for(Card c: p.getHand()){
				if(c.isStraight()){
					// Initialize a hand to be added to the possibilities to be played
					ArrayList<Card> hands = new ArrayList<Card>();
					hands.add(c);
//					for(Card d: p.getHand()){
//						// Increase the counter 
//						if(c.getRank() + counter == d.getRank() && d.isStraight()){
//							counter++;
//							hands.add(d);
//						}
//					}
					int k = 0;
					while(counter < 5){
						Card d = p.getHand().get(k);
						if(c.getRank() + counter == d.getRank() && d.isStraight()){
							counter++;
							hands.add(d);
						}
						if(k + 1 == p.getHand().size())
							k = 0;
						else
							k++;
					}
					
					if(counter == 5)
						possible.add(hands);
					else if(counter > 5){
						// Correct hand size
						for(int i = hands.size(); i > 4; i--)
							hands.remove(i-1);
						possible.add(hands);
					}
					// Reset counter at 1
					counter = 1;
						
				}
			}
		}
		// Set the counter to 0
		counter = 0;
		// If the player contains a flush
		if(this.flush){
			// Go through each card
			for(Card c: p.getHand()){
				if(c.isFlush()){
					// Initialize a hand to be added to the possibilities to be played
					ArrayList<Card> hands = new ArrayList<Card>();
					hands.add(c);
					for(Card d: p.getHand()){
						// Look for matching suits
						if(c.getSuit() == d.getSuit() && d.isFlush()){
							counter++;
							hands.add(d);
						}
					}
					
					if(counter == 4)
						possible.add(hands);
					else if(counter > 4){
						// Correct the hand size
						for(int i = hands.size(); i > 5; i--)
							hands.remove(i-1);
						possible.add(hands);
					}
					// Reset the counter
					counter = 0;
				}
			}
		}
		
		// Add another counter to count for the Doubles for Full Houses
		int counter1 = 0;
		// If the player contains full houses
		if(this.full){
			// Go through each card
			for(Card c: p.getHand()){
				
				if(c.isFull() && c.isTriple()){
					// Initialize a hand to be added to the possibilities to be played
					ArrayList<Card> hands = new ArrayList<Card>();
					hands.add(c);
					// Going for triples
					for(Card d: p.getHand()){
						if(c.getRank() == d.getRank() && d.isFull() && d.isTriple() && counter < 3){
							counter++;
							hands.add(d);
						}
					}
					// Going for doubles
					Boolean doubleNotFound = true;
					Card e = null;
					for(Card d: p.getHand())
					{
						if(c.getRank() != d.getRank() && d.isFull() && d.isDouble() && counter < 2 && doubleNotFound){
							counter1++;
							hands.add(d);
							e = d;
							doubleNotFound = false;
						}
						else if(e.getRank() == d.getRank() && e.getSuit() != d.getSuit())
							hands.add(d);
						
					}
					
					if(counter == 3 && counter1 == 2 )
						possible.add(hands);
						
					// Reset the counters
					counter = 0;
					counter1 = 0;
				}
			}
		}
		
		counter = 0;
		// If the player contains a four of a kind
		if(this.four){
			// Go through each card
			for(Card c: p.getHand()){
				
				if(c.is4OfAKind()){
					// Initialize a hand to be added to the possibilities to be played
					ArrayList<Card> hands = new ArrayList<Card>();
					hands.add(c);
					// Find the four of a kind
					for(Card d: p.getHand()){
						if(c.getRank() == d.getRank() && d.is4OfAKind()){
							counter++;
							hands.add(d);
						}
					}
					
					// Find an extra card to make it a hand
					for(Card d: p.getHand()){
						if(c.getRank() != d.getRank() && !d.is4OfAKind() && counter < 4){
								hands.add(d);
								counter++;
							}
							
					}
					
					if(counter == 4)
						possible.add(hands);
					// Reset the counter
					counter = 0;
				}
			}
		}
		
		// Set the counter similar to the Straight
		counter = 1;
		// If the player contains a straight flush
		if(this.straightFlush){
			// Go through each card
			for(Card c: p.getHand()){
				// Similar to the straight
				if(c.isStraightFlush()){
					// Initialize a hand to be added to the possibilities to be played
					ArrayList<Card> hands = new ArrayList<Card>();
					hands.add(c);
//					for(Card d: p.getHand()){
//						if(c.getRank() + counter == d.getRank() && d.isStraightFlush()){
//							counter++;
//							hands.add(d);
//						}
//					}
					
					int k = 0;
					while(counter < 5){
						Card d = p.getHand().get(k);
						if(c.getRank() + counter == d.getRank() && d.isStraightFlush()){
							counter++;
							hands.add(d);
						}
						if(k + 1 == p.getHand().size())
							k = 0;
						else
							k++;
					}
					
					if(counter == 5)
						possible.add(hands);
					else if(counter > 5){
						// Correct the hand size
						for(int i = hands.size(); i > 4; i--)
							hands.remove(i-1);
						possible.add(hands);
					}
					counter = 1;
						
				}
			}
		}
		
		// Use a random generator to choose a hand out of the possibilities
		int ranNum = 0;
		if(possible.size() > 1)
			ranNum = this.r.nextInt(possible.size());

		if(ranNum >= 0  && possible.size() >= 1){
				// Use the random number and play that hand
				this.t.play(possible.get(ranNum),p);
				// Reset the counter
				this.passCounter = 0;
				// End the turn
				this.starter = this.endTurn();
				return true;
			}
//		else {
//				System.out.println("Player: "+ p.getNumber() + " has passed");
//				this.starter = this.pass();
//			}
		return false;
	}
}


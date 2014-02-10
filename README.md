Puso-Dos
========
Formally known as Big 2 in English, Puso-Dos is a card game played with a maximum of 4 players utilizing a normal 52 card deck. The program is made to always have any combination of AI and Non-AI players  adding up to four players. The winner of the game is the first player to run out of cards. Cards are ranked by both their number and suit. 

By suit (Highest to Lowest):
Diamonds, Hearts, Spades, and Clubs

By Number (Highest to Lowest):
2,King,Queen,Jack,10,9,8,7,6,5,4, and 3

The first player to go is the player with the lowest card in the entire deck, the owner of the 3 of Clubs. That player has 3 routes to take. They may play a single where they just play the 3 of Clubs, they may play a double where they play two 3's, they may play a triple where they play three 3's, or they may play a hand. The person after the first player must follow in kind. Whatever the player before you plays you must play the in the same style but with a set of cards with higher rank. 
In the case of singles and doubles we have to look at both the number on the card and the suit. 

For example, if the first player plays 3 of Clubs and 3 of Diamonds and the next player has a 3 of Spades and 3 of Hearts that player must play a different hand or pass because the 3 of Clubs and 3 of Diamonds is greater in rank than the 3 of Spades and 3 of Hearts since we only have to look at the card with the highest rank between the two pairs, in this case 3 of Diamonds is greater than 3 of Hearts thus the first player's hand would be greater than the hand that the next player has. Thus, the next player (after first) can play a better hand (e.g. a pair of 4's) or pass. 

Passing is option given to players. If like in the previous example the first player plays a pair of 3's, a 3 of Clubs and a 3 of Diamonds, everyone else must play a pair or pass. If everyone passes the first player is able to play any hand.  Essentially if you the player play a set of cards that makes everyone pass you may play any valid set of cards from your hand. 

Valid Hands
Singles
Doubles
Triples
5 Card Hands (similar to poker) [lowest to highest by rank]*
Straight
Flush
Full House
Four of a Kind
Straight Flush

*Hands have their own rank as well.

 
The program's AI is ran randomly. In other words, when it is their turn the playAI() method calls private methods to find the possible hands available to the current AI and randomly chooses between those options. 

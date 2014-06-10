import java.util.Scanner;


/**
 * This main class plays the Pusoy Dos Game (AI vs. AI [under construction] , 
 * Player vs. AI [under construction], and PVP [under construction]).
 * 
 * Name, Date, Modifications: 
 * John Doe, 10/10/10, Added some methods (specify) and added corrections (where?).
 * 
 * @author Jeric
 * @version 24 June 2012
 */
public class GameMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Please insert the amount of players: ");
		int numOfPlayers = scan.nextInt();
		GameApp pusoy = new GameApp(numOfPlayers);
		pusoy.initialize();
		
	}
}

package halmaSquareSkeleton;

import ch.aplu.jgamegrid.*;

import java.util.*;
import java.awt.*;

//------------------------------------------------------------------------------
public class Halma extends GameGrid implements GGMouseListener {
	
	private int nbPlayers = 2;
	private boolean jumpModeOn = false; 
	// needed if stone can still move after a jump
	private HalmaStone movingHS;

	//random player starts:
	private int currentPlayer = (int) (Math.random() * nbPlayers);	
	private HalmaPlayer[] players = new HalmaPlayer[nbPlayers];

	public Halma() {
		super(16, 16, 25, Color.BLACK, null, false);
		this.setBgColor(Color.WHITE);
		initializePlayers();
		setUpBoard();
		addMouseListener(this, GGMouse.lClick | GGMouse.rClick);
		setTitle(players[currentPlayer] + " starts!"); 
		show();
	}

	public boolean mouseEvent(GGMouse mouse) {
		Location clickLoc = toLocationInGrid(mouse.getX(), mouse.getY());

		// rightclick ends move of a player (replaced next-button with that)
		if (mouse.getEvent() == GGMouse.rClick) {
			movingHS.putDown();
			movingHS = null;
			nextPlayersTurn();
			refresh();
			return true;
		}
		
		HalmaStone stone = getHalmaStoneOfCurrentPlayerAt(clickLoc);
		// if clicked on a stone:
		if (stone != null && !jumpModeOn) {
			if (stone == movingHS) {
				stone.putDown();
				movingHS = null;
			} else {
				if (movingHS == null) {
					setTitle("Rightclick to end turn");
					stone.pickUp();
					movingHS = stone;
				} else {
					stone.pickUp();
					movingHS.putDown();
					movingHS = stone;
				}
			}
			refresh();
			return true;
		}
		// if clicked on empty, possible Location with a picked up Stone:
		if (movingHS != null && isUnoccupiedLocation(clickLoc)) {
			// if move is possible
			if (isValidMove(movingHS, clickLoc)) {
				jumpModeOn = true;
				movingHS.setLocation(clickLoc);
				checkGameOver();
			}
		}
		refresh();
		return true;
	}

	/**
	 * Returns true if a move is valid, meaning:
	 * - the clicked position is situated orthogonally to the picked up Stone
	 * - moving one cell (so no interjacent locations) 
	 *   is only allowed if we're not in jump mode
	 * - a jump is only valid if there are stones on the overjumped locations 
	 */
	private boolean isValidMove(HalmaStone hs, Location loc) {
		Location hsLoc = hs.getLocation();
		if (hsLoc.x != loc.x && hsLoc.y != loc.y) //jumping diagonally is not allowed
			return false;
		ArrayList<Location> allLocsBetween = getInterjacent(hsLoc, loc);
		// if there are no stones between, but the stone has already jumped
		if (allLocsBetween.isEmpty())
			return !jumpModeOn;
		else {
			for (Location interLoc: allLocsBetween) {
				if (getOneActorAt(interLoc) == null)
					return false;
			}
		}
		return true;
	}

	public HalmaStone getHalmaStoneOfCurrentPlayerAt(Location location) {
		HalmaStone hs = (HalmaStone) getOneActorAt(location);
		if (hs != null && hs.getOwningPlayer() == players[currentPlayer])
			return hs;
		else return null;
	}

	private void nextPlayersTurn() {
		jumpModeOn = false;
		currentPlayer = (currentPlayer + 1) % nbPlayers;
		setTitle(players[currentPlayer] + " plays!");
	}

	/**
	 * Creates Players with their specific start & end locations.
	 * Is especially easy on a square shaped board, because one players
	 * startingLocations are the others endLocations
	 */
	private void initializePlayers() {
		ArrayList<Location>[] startLocations = new ArrayList[nbPlayers];
		
		for (int i = 0; i < nbPlayers; i++)
			startLocations[i] = new ArrayList<Location>();
		
		/* TODO: Initialize startLocations[i] and endLocations[i]
		 * with the according locations (See screenshot).
		 * 
		 * Hint1: One players startLocations are the others endLocations.
		 * Hint2: Use nested for-loops.
		 * Hint3: You can experiment with:
		 * startLocations[0].add(new Location(0,0));
		 */
		
		players[0] = new HalmaPlayer(this, HalmaColor.Blue,
				startLocations[0], startLocations[1]);
		players[1] = new HalmaPlayer(this, HalmaColor.Red,
				startLocations[1], startLocations[0]);
	}
	
	/**
	 * Initializes each players stones at their specific
	 * starting locations.
	 */
	private void setUpBoard() {
		for (HalmaPlayer p: players)
			p.initializeStones();
	}

	private boolean isUnoccupiedLocation(Location loc) {
		return getOneActorAt(loc) == null;
	}

	/**
	 *  Get all Locations between loc1 and loc2 (without loc1 and loc2)
	 *  
	 *  This method only works properly if loc1 and loc2 have the same
	 *  x or y coordinate, meaning they're orthogonally situated.
	 */
	private ArrayList<Location> getInterjacent(Location loc1, Location loc2) {
		ArrayList<Location> interjacentLocs = new ArrayList<Location>();
		
		/*
		 * TODO: put all locations between loc1 and loc2 into interjacentLocs.
		 * You can assume, that loc1 and loc2 have either the same x or y coordinate.
		 * Hint: You might want to look through the javadoc for useful helper methods
		 * http://www.aplu.ch/classdoc/jgamegrid/index.html
		 */
		
		return interjacentLocs;
	}

	/**
	 * Checks if the current player has won the game. 
	 * Stops the game if so.
	 */
	private void checkGameOver() {
		//this could be a task, too!
		if(players[currentPlayer].isWinner()) {
			 addActor(new Actor("sprites/you_win.gif"), new Location(10,11));
		     setTitle(players[currentPlayer] + " wins!!!");
		     reset();
		}
	}

	public void reset() {
		delay(5000);
		removeAllActors();
		jumpModeOn = false;
		setTitle("Game reset!");
		setUpBoard();
	}

	public static void main(String[] args) {
		new Halma();
	}
}
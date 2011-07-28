package halmaSquareSkeleton;

import java.awt.Color;
import java.util.ArrayList;

import ch.aplu.jgamegrid.Location;

public class HalmaPlayer {
	private HalmaColor color;
	private Halma halmaBoard;
	private ArrayList<Location> startLocations;
	private ArrayList<Location> endLocations;
	
	public HalmaPlayer(Halma halmaBoard, HalmaColor color, 
			ArrayList<Location> startLocations, ArrayList<Location> endLocations) {
		this.halmaBoard = halmaBoard;
		this.color = color;
		this.startLocations = startLocations;
		this.endLocations = endLocations;
	}
	
	/**
	 * Puts a stone of the players own color on every Location
	 * of startLocations. Also fills these cells with lightGray.
	 */
	public void initializeStones() {
		/* 
		 * TODO: add a stone to every location contained in
		 * startLocations. Also fill the corresponding cell with a color.
		 * 
		 * Hint: You can experiment with:
		 * halmaBoard.addActor(new HalmaStone(this), new Location(0,0)); 
		 */
		halmaBoard.getBg().drawGridLines(Color.black);
	}

	/**
	 * Player wins if every Location of endLocation is 
	 * occupied by a stone of his color.
	 * @return true if the current Player wins
	 */
	public boolean isWinner() {
		/* 
		 * TODO: Check, if every stone of this player is on a
		 * endLocation. 
		 * Hint: Read the method description above, it may be easier to
		 * check it that way...
		 */
		return true;
	}
	
	public String toString() {
		return color.name();
	}
	
	public HalmaColor getColor() {
		return color;
	}
}

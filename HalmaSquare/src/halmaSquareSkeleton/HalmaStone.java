package halmaSquareSkeleton;

import ch.aplu.jgamegrid.Actor;

/**
 * Every stone on the board is an object of this class. A stone
 * can be in two states: either pickedUp or not pickedUp. This is
 * represented with a boolean.
 * 
 * Instead of assertions, that could be verified by rising an exception.
 * But this is not part of this task.
 */
class HalmaStone extends Actor {
	public HalmaPlayer player;
	private HalmaColor hc;
	private boolean pickedUp;

	public HalmaStone(HalmaPlayer player) {
		super("sprites/halmaStone.png", 6);
		show(player.getColor().ordinal() * 2);
		this.player = player;
	}

	public HalmaPlayer getOwningPlayer() {
		return player;
	}

	/**
	 * Same as pickUp()
	 */
	public void putDown() {
		assert pickedUp;
		pickedUp = false;
		this.showPreviousSprite();
	}

	/**
	 * Funny color changes could occur here, if a 
	 * already picked up stone gets picked up again! 
	 * Make sure this is ONLY called on NOT pickedUp stones!
	 */
	public void pickUp() {
		assert !pickedUp;
		pickedUp = true;
		this.showNextSprite();
	}

	public boolean isPickedUp() {
		return pickedUp;
	}
	public HalmaColor getColor() {
		return hc;
	}
}
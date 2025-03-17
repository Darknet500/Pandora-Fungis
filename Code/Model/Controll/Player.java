package Controll;

/**
 * 
 */
public abstract class Player {

    /**
     *
     */
    private int score;

    /**
     * Default constructor
     */
    public Player() {
    }

    /**
     * 
     */
    public abstract void chooseAction();

    /**
     * @param howMuch
     */
    public void increaseScore(int howMuch) {
        // TODO implement here
    }

}
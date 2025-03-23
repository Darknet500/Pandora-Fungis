package Controll;

/**
 * The player class is base of the Bug and Shroomer classes. It takes count of the player's score
 */
public abstract class Player {

    /**
     * Player's score
     */
    private int score;

    /**
     * Default constructor
     */
    public Player() {
    }

    /** Increases the score of the player
     * @param howMuch by how many points the score will be increased
     */
    public void increaseScore(int howMuch) {
        score += howMuch;
    }

}
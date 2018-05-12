public class Player {
    
    final private String name;                  //Holds name of player.
    final private int NUM_SCORES;               //Holds the number of score fields. 
    final private boolean[] scored;             //Records whether a score has been used or not this game.
    
    /*
     * Constructor that takes a name and creates a player.
     */
 
    public Player(String name) {
        
        //Initializes variables.
        this.name = name;
        NUM_SCORES = 13;
        scored = new boolean[NUM_SCORES];
        
        //Sets all of the scored fields to false.
        for(int i = 0; i < NUM_SCORES; i++) {
            scored[i] = false;
        }
    }
    
    /*
     * sets the scored field for a particular score to true.
     */
 
    public void score(int score) {
        scored[score] = true;
    }
    
    /*
     * Returns whether a field has been scored.
     */
    
    public boolean getScored(int score) {
        return scored[score];
    }
    
    /*
     * Returns the name of the player.
     */
    
    public String getName() {
        return name;
    }

}

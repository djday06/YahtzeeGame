
import java.io.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class YahtzeeGame {
    
    private Player[] players;                   //Array of players.
    private Player turn;                        //Player who's turn it is.
    private int turnNumRolls;                   //Number of times the dice have been rolled this turn.
    private int tripleNum;                      //Varible to hold a number that there is three of a kind of.
    private final int[] dice;                   //Array to hold the dice rolls.
    private int round;                          //A count of the current round in the game.
    private final int NUM_DICE = 5;             //Number of dice rolled.
    
    public YahtzeeGame() {
        
        turnNumRolls = 0;
        round = 0;
        tripleNum = 0;
        dice = new int[NUM_DICE];
       
        setUpPlayers();
        
        turn = players[0];
    }
    
    /*
     * Method to get the number of rolls for this turn.
     */
 
    public int getTurnNumRolls() {
        return turnNumRolls;
    }
    
    /*
     * Return player names.
     */
    
    public String[] getNames() {
        
        //Creates sting array to hold player names.
        String[] names = new String[players.length];
        
        //Fills the array with player names.
        for(int i = 0; i < players.length; i++) {
            names[i] = players[i].getName();
        }
        
        return names;
    }
    
    /*
     * Method to set up the players for the game.
     */
    
    private void setUpPlayers() {
        
        //Initializes the players array and creates a name vairable.
        players = new Player[2];
        String name;
        
        //Prompts user for name of player and creates the player.
        name = JOptionPane.showInputDialog(null,
               "Enter Player 1's name.");
        players[0] = new Player(name);
        
        //Prompts user for name of player and creates the player.
        name = JOptionPane.showInputDialog(null,
               "Enter Player 2's name.");
        players[1] = new Player(name);
    }
    
    /*
     * Method to swap from one player to another.
     */
    
    public boolean swapTurn() {
        //Resets number of rolls at the beginning of turn.
        turnNumRolls = 0;
        
        //Varaible to return if the game is over.
        boolean gameOver = false;
        
        //Swaps active player and increases round count after player 2.
        if (turn.equals(players[0])) {
            turn = players[1];
        }
        else {
            turn = players[0];
            round++;
        }
        
        if (round < 13)
            JOptionPane.showMessageDialog(null, "It is " + turn.getName() + "'s turn.");
        else {
            JOptionPane.showMessageDialog(null, "Good game.");
            gameOver = true;
        }
        
        return gameOver;
    }
    
    /*
     * Method to roll the dice.
     * @param roll:  boolean array to check which dice to reroll.
     * @return:      returns the dice rolled.
     */
    
    public int[] rollDice(boolean[] roll) {
        
        //Checks if it is the first roll. If so rolls all the dice.
        if (turnNumRolls == 0) {
            for (int i = 0; i < NUM_DICE; i++) 
                dice[i] = new Random().nextInt(6) + 1;
        }
        //Else only rolls the dice that you want rerolled.
        else {
            for (int i = 0; i < NUM_DICE; i++) {
                if (roll[i]) {
                    dice[i] = new Random().nextInt(6) + 1;
                }
            }
        }
        
        turnNumRolls++;
        
        return dice;
    }
    
    
    
    
    /*
     * Method that returns if a specific score has been used.
     */
    
    public boolean getScored(int score) {
        return turn.getScored(score);
    }
    
    /*
     * Method to return a value based on which player is active.
     */
 
    public int getPlayerTurn() {
        if(turn.equals(players[0]))
            return 0;
        else
            return 16;
    }
    
    /*
     * Method checks for three of a kind and returns what number is three of kind.
     */
    
    public boolean tokCheck() {
        
        boolean pair = false;
        boolean triple = false;
        
        for (int i = 0; i < dice.length; i++) {
            for ( int j = i + 1; j < dice.length; j++) {
                if (dice [i] == dice[j] && pair) {
                    triple = true;
                    tripleNum = dice[i];
                }
                if (dice[i] == dice[j]) {
                    pair = true;
                }
            }
        }
        return triple; 
    }
    
    /*
     * Method to check for four of a kind.
     */
    
    public boolean fokCheck() {
        
        int count;
        boolean quad = false;
        
        for (int i = 0; i < dice.length; i++) {
            count = 0;
            for ( int j = 0; j < dice.length; j++) {
                if (dice[i] == dice[j]){
                    count++;
                    if (count >= 4) {
                        quad = true;
                    }
                }
            }
        }
        return quad; 
    }
    
    /*
     * Method to check for a full house.
     */
    
    public boolean fhCheck() {
        boolean fullHouse = false;
        int temp = 0;
        
        if (tokCheck()) {
            for (int i = 0; i < dice.length; i++) {
                if (temp == dice[i])
                    fullHouse = true;
                if (dice[i] != tripleNum)
                    temp = dice[i];
            }
        }
        return fullHouse;
    }
    
    /*
     * Method to check for a small straight.
     */
    
    public boolean ssCheck() {
        
        //Variables to see if a digit is present.
        boolean[] digits = new boolean[6];
        
        //Variable if a straight is present.
        boolean straight = false;
        
        //Sets values of boolean variables.
        for(int i = 0; i < dice.length; i++) {
            if (dice[i] == 1)
                digits[0] = true;
            if (dice[i] == 2)
                digits[1] = true;
            if (dice[i] == 3)
                digits[2] = true;
            if (dice[i] == 4)
                digits[3] = true;
            if (dice[i] == 5)
                digits[4] = true;
            if (dice[i] == 6)
                digits[5] = true;
        }
        
        //Checks for straight.
        if (digits[0] && digits[1] && digits[2] && digits[3])
            straight = true;
        if (digits[1] && digits[2] && digits[3] && digits[4])
            straight = true;
        if (digits[2] && digits[3] && digits[4] && digits[5])
            straight = true;
        
        return straight;
    }
    
    /*
     * Method to check for a large straight.
     */
    
    public boolean lsCheck() {
        
        //Variables to see if a digit is present.
        boolean[] digits = new boolean[6];
        
        //Variable if a straight is present.
        boolean straight = false;
        
        //Sets values of boolean variables.
        for(int i = 0; i < dice.length; i++) {
            if (dice[i] == 1)
                digits[0] = true;
            if (dice[i] == 2)
                digits[1] = true;
            if (dice[i] == 3)
                digits[2] = true;
            if (dice[i] == 4)
                digits[3] = true;
            if (dice[i] == 5)
                digits[4] = true;
            if (dice[i] == 6)
                digits[5] = true;
        }
        
        //Checks for straight.
        if (digits[0] && digits[1] && digits[2] && digits[3] && digits[4])
            straight = true;
        if (digits[1] && digits[2] && digits[3] && digits[4] && digits[5])
            straight = true;
        
        return straight;
    }
    
    /*
     * Method to check for a yahtzee.
     */
    
    public boolean yahtzeeCheck() {
        
        //Variable to hold flag for yahtzee.
        boolean yahtzee = true;
        
        //Temp variable to check for yahtzee.
        int temp = dice[0];
        
        //Loop through and check for yahtzee.
        for (int i = 0; i < dice.length; i++) {
            if (temp != dice[i])
                yahtzee = false;
        }
        
        return yahtzee;
    }
    
    /*
     * Method checks for ones and adds the total up.
     */
    
    public int scoreAces(int field) {
        
        int score = 0;
        
        for(int i = 0; i < NUM_DICE; i++) {
            if (dice[i] == 1)
                score += dice[i];
        }
        
        turn.score(field);
        
        return score;
    }
    
    /*
     * Method checks for twos and adds the total up.
     */
    
    public int scoreTwos(int field) {
        
        int score = 0;
        
        for(int i = 0; i < NUM_DICE; i++) {
            if (dice[i] == 2)
                score += dice[i];
        }
        
        turn.score(field);
        
        return score;
    }
    
    /*
     * Method checks for threes and adds the total up.
     */
    
    public int scoreThrees(int field) {
        
        int score = 0;
        
        for(int i = 0; i < NUM_DICE; i++) {
            if (dice[i] == 3)
                score += dice[i];
        }
        
        turn.score(field);
        
        return score;
    }
    
    /*
     * Method checks for fours and adds the total up.
     */
    
    public int scoreFours(int field) {
        
        int score = 0;
        
        for(int i = 0; i < NUM_DICE; i++) {
            if (dice[i] == 4)
                score += dice[i];
        }
        
        turn.score(field);
        
        return score;
    }
    
    /*
     * Method checks for fives and adds the total up.
     */
    
    public int scoreFives(int field) {
        
        int score = 0;
        
        for(int i = 0; i < NUM_DICE; i++) {
            if (dice[i] == 5)
                score += dice[i];
        }
        
        turn.score(field);
        
        return score;
    }
    
    /*
     * Method checks for sixes and adds the total up.
     */
    
    public int scoreSixes(int field) {
        
        int score = 0;
        
        for(int i = 0; i < NUM_DICE; i++) {
            if (dice[i] == 6)
                score += dice[i];
        }
        
        turn.score(field);
        
        return score;
    }
    
    /*
     * Method add scores of all dice.
     */
    
    public int scoreAllDice(int field) {
        
        int score = 0;
        
        for(int i = 0; i < NUM_DICE; i++) {
            score += dice[i];
        }
        
        turn.score(field - 1);
        
        return score;
    }
    
    /*
     * Method to set full house score.
     */
    
    public int scoreFullHouse(int field) {
        int score = 25;
        
        turn.score(field - 1);
        return score;
    }
    
    /*
     * Method to set small straight score.
     */
    
    public int scoreSmallStraight(int field) {
        int score = 30;
        
        turn.score(field - 1);
        return score;
    }
    
    /*
     * Method to set large straight score.
     */
    
    public int scoreLargeStraight(int field) {
        int score = 40;
        
        turn.score(field - 1);
        return score;
    }
    
    /*
     * Method to set yahtzee score.
     */
    
    public int scoreYahtzee(int field) {
        int score = 50;
        
        turn.score(field - 1);
        return score;
    }
    
    public void endGame(int player1, int player2) throws IOException {
        
        //File Name.
        String fileName = "highscore.txt";
        
        //Temp varible to hold high score.
        int temp = 0;
        int temp2 = 0;
        
        //Checks for higer score and sets to temp.
        if(player1 > player2) 
            temp = player1;
        else 
            temp = player2;
        
            //Variable to hold high score coming in.
            String highScore;
                    
            highScore = readHighScore();
            
            //Converts the string to an integer.
            temp2 = Integer.parseInt(highScore);
        
        
        //Checks if the saved high score is lower than high score for game.
        //If so writes new high score.
        if (temp > temp2) {
            
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.println(temp);
            
            writer.close();
        }
    }
    
    public String readHighScore() {
        
        String fileName = "highscore.txt";
    
        //Variable to hold high score coming in.
            String highScore = null;
            
        try {
            
                    
            //FileReader to read file.
            FileReader fr = new FileReader(fileName);
            
            //BufferedREader to read individual lines.
            BufferedReader br = new BufferedReader(fr);
            
            //Reads the one line in file and saves it to highScore.
            highScore = br.readLine();
            
            //Closes the file reader.
            br.close();
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No file found");
        }
        catch (Exception ae) {
            JOptionPane.showMessageDialog(null, "No file found");
        }
        
        return highScore;
    }
}

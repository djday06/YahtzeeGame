
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class YahtzeeGameDemo extends JFrame{
    
    Container pane;                                                     //Gets the Pane from main window to add content.
    ArrayList<JLabel> labels;                                           //List to set up labels.
    ArrayList<JTextField> textFields;                                   //List to set up text fields.
    ArrayList<JTextField> dieFields;                                    //List to set up die fiels.
    ArrayList<JButton> scoreButtons;                                    //List to set up buttons.
    JButton roll;                                                       //Button to roll dice.
    ArrayList<JCheckBox> reroll;                                         //Checkboxes for rerolls.
    GridBagConstraints c;                                               //Constraints to set up arrangement of window.
    Insets inset;                                                       //Insets object to set up padding of each element.
    private boolean upperScore;                                         //Flag for whether upper score has been given the bonus.
    private int topPadding, leftPadding, bottomPadding, rightPadding;   //Padding variables so that a seperate method can set up Insets.
    private final int ROWS = 16;                                        //Number of rows in window.
    private final int NUM_DICE = 5;                                     //Number of dice used.
    private final YahtzeeGame game;                                     //Variable to hold the game object.
    private boolean gameOver;                                           //Flag for if a game is over.
    
    /*
     * Constructor Method.
     */
    public YahtzeeGameDemo() {
        
        //Initializes main objects of program.
        pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        
        //Start game up.
        game = new YahtzeeGame();
        upperScore = false;
        gameOver = false;
        
        //Set intial values for Layout.
        resetLayout();
        
        //Sets up window options.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        
        //Call methods add elements to main pane.
        addTopLabels(pane);
        addScoreLabels(pane);      
        addTextFields(pane);
        addScoreButtons(pane);
        addDiceLabels(pane);
        addDiceFields(pane);
        addRerollBoxes(pane);
        
        
        //Finalize set up for main window.
        setVisible(true);
        pack();
        setTitle("Yahtzee Game");
    }
    
    /*
     * Method to add top labels.
    */
    
    private void addTopLabels(Container pane) {
        
        //ArrayList for setting up Labels.
        labels = new ArrayList<>();
        
        //Creates array and gets the names of the players.
        String[] names = game.getNames();
        
        //Create the Labels.
        labels.add(new JLabel("Score:"));
        labels.add(new JLabel(names[0] + ":"));
        labels.add(new JLabel(names[1] + ":"));
        labels.add(new JLabel("High Score:"));
        labels.add(new JLabel(game.readHighScore()));
        
        //Add Labels to Panel.
        rightPadding = 20;
        setInsets();
        pane.add(labels.get(0), c);
        
        leftPadding = 30;
        setInsets();
        c.gridx = 1;
        pane.add(labels.get(1), c);
        
        c.gridx = 2;
        pane.add(labels.get(2), c);
        
        leftPadding = 60;
        rightPadding = 5;
        setInsets();
        c.gridx = 4;
        pane.add(labels.get(3), c);
        
        /*
        TODO: Set up file reader and read high score.
        */
        leftPadding = 5;
        setInsets();
        c.gridx = 5;
        pane.add(labels.get(4), c);
        
        //Reset Layout settings.
        resetLayout();
    }
    
    /*
     * Method to add score labels.
     */
    
    private void addScoreLabels(Container pane) {
        
        //ArrayList for setting up Labels.
        labels = new ArrayList<>();
        
        //Create Labels.
        labels.add(new JLabel("Aces Score:"));
        labels.add(new JLabel("Twos Score:"));
        labels.add(new JLabel("Threes Score:"));
        labels.add(new JLabel("Fours Score:"));
        labels.add(new JLabel("Fives Score:"));
        labels.add(new JLabel("Sixes Score:"));
        labels.add(new JLabel("Upper Score:"));
        labels.add(new JLabel("Three of a Kind:"));
        labels.add(new JLabel("Four of a Kind:"));
        labels.add(new JLabel("Full House:"));
        labels.add(new JLabel("Sm. Straight:"));
        labels.add(new JLabel("Lg. Straight:"));
        labels.add(new JLabel("Yahtzee:"));
        labels.add(new JLabel("Chance:"));
        labels.add(new JLabel("Lower Score:"));
        labels.add(new JLabel("Total Score:"));
        
        //Add Labels to panel.
        rightPadding = 20;
        setInsets();
        for (int i = 0; i < ROWS; i++) {
            c.gridy = (i + 1);
            pane.add(labels.get(i), c);
        }
        
        
        resetLayout();
    }
    
    /*
     * Method to add text fields.
     */
    
    private void addTextFields(Container pane) {
        
        //ArrayList for setting up text fields.
        textFields = new ArrayList<>();
        JTextField field;
        
        //Creating adding the labels to the main window.
        leftPadding = 30;
        rightPadding = 20;
        setInsets();
        c.gridx = 1;
        
        // Created a local variable i to run loops since I wanted all the 
        // text fields to be in one array but on two colums.
        int i = 0;
        for( ; i < ROWS; i++) {
            field = new JTextField("000");
            c.gridy = (i + 1);
            field.setEditable(false);
            textFields.add(field);
            pane.add(textFields.get(i), c);
        }
        
        c.gridx = 2;
        for( ; i < (ROWS * 2); i++) {
            c.gridy = (i + 1 - ROWS);
            field = new JTextField("000");
            field.setEditable(false);
            textFields.add(field);
            pane.add(textFields.get(i), c);
        }
        
    }
    
    /*
     * Method to add score butons.
     */
    
    private void addScoreButtons(Container pane) {
        
        //Setting up ArrayList for buttons.
        scoreButtons = new ArrayList<>();
        JButton button;
        
        //Creating buttons and adding them to the array.
        button = new JButton("Score Aces");
        button.addActionListener(new ScoreAcesListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Twos");
        button.addActionListener(new ScoreTwosListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Threes");
        button.addActionListener(new ScoreThreesListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Fours");
        button.addActionListener(new ScoreFoursListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Fives");
        button.addActionListener(new ScoreFivesListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Sixes");
        button.addActionListener(new ScoreSixesListener());
        scoreButtons.add(button);
        
        button = new JButton("Score ToK");
        button.addActionListener(new ScoreToKListener());
        scoreButtons.add(button);
        
        button = new JButton("Score FoK");
        button.addActionListener(new ScoreFoKListener());
        scoreButtons.add(button);
        
        button = new JButton("Score FH");
        button.addActionListener(new ScoreFHListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Sm. St.");
        button.addActionListener(new ScoreSSListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Lg. St.");
        button.addActionListener(new ScoreLSListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Yahtzee");
        button.addActionListener(new ScoreYahtzeeListener());
        scoreButtons.add(button);
        
        button = new JButton("Score Chance");
        button.addActionListener(new ScoreChanceListener());
        scoreButtons.add(button);
        
        //Adding buttons to main window.
        c.gridx = 3;
        for(int i = 0; i < 6; i++) {
            c.gridy = (i + 1);
            pane.add(scoreButtons.get(i), c);
        }
        
        for(int i = 6; i < scoreButtons.size(); i++) {
            c.gridy = (i + 2);
            pane.add(scoreButtons.get(i), c);
        }
        
        resetLayout();
        
    }
    
    /*
     * Method to add dice labels.
     */
    
    private void addDiceLabels(Container pane) {
        
        //ArrayList for setting up labels.
        labels = new ArrayList<>();
        
        //Creating labels.
        labels.add(new JLabel("Die 1:"));
        labels.add(new JLabel("Die 2:"));
        labels.add(new JLabel("Die 3:"));
        labels.add(new JLabel("Die 4:"));
        labels.add(new JLabel("Die 5:"));
        
        //Adding Die Labels to main window pane.
        c.gridx = 4;
        for(int i = 0; i < labels.size(); i++) {
            c.gridy = (i + 6);
            pane.add(labels.get(i), c);
        }
        
        resetLayout();
    }
    
    /*
     * Method to add dice fields.
     */
    
    private void addDiceFields(Container pane) {
        
        JTextField field;
        
        //ArrayList for die fields.
        dieFields = new ArrayList<>();
        
        //Setting up roll button.
        roll = new JButton("Roll");
        roll.addActionListener(new RollDiceListener());
        
        //Creating die fields.
        for(int i = 0; i < NUM_DICE; i++) {
            field = new JTextField("0");
            dieFields.add(field);
        }
        
        //Adding die fields to main window pane.
        c.gridx = 5;
        for(int i = 0; i < NUM_DICE; i++) {
            c.gridy = (i + 6);
            pane.add(dieFields.get(i), c);
        }
        
        c.gridy += 1;
        pane.add(roll, c);
        
        resetLayout();
    }
    
    /*
     * Method to add reroll checkboxes.
     */
    
    private void addRerollBoxes(Container pane) {
        
        //Label for rerolls.
        JLabel diceReroll = new JLabel("Reroll Dice?");
        
        //Generic Checkbox for adding to array.
        JCheckBox check;
        
        //Creating list for checkboxes.
        reroll = new ArrayList<>();
        
        //Adding checkboxes to array.
        for(int i = 0; i < NUM_DICE; i++) {
            check = new JCheckBox();
            reroll.add(check);
        }
        
        //Adding Label to main window pane.
        c.gridx = 6;
        c.gridy = 5;
        pane.add(diceReroll, c);
        
        //Adding checkboxes to main window pane.
        for(int i = 0; i < NUM_DICE; i++) {
            c.gridy = (i + 6);
            pane.add(reroll.get(i), c);
        }
        
        resetLayout();
    }
    
    /*
     * Method to set Insets for each object.
     */
    
    private void setInsets() {
        inset = new Insets(topPadding, leftPadding, bottomPadding, rightPadding);
        
        c.insets = inset;
    }
    
    /*
     * Method to reset the layout at end of each method call.
     */    
    private void resetLayout() {
        topPadding = 5;
        leftPadding = 5;
        bottomPadding = 5;
        rightPadding = 5;
        c.gridx = 0;
        c.gridy = 0;
        
        setInsets();
    }
    
    /*
     * Method to clear all check boxes.
     */
    
    private void resetCheckBoxes() {
        
        //Loops through check boxes.
        for (int i = 0; i < reroll.size(); i++) {
            reroll.get(i).setSelected(false);
        }
        
        resetDice();
        
        if(gameOver) {
            try {
                game.endGame(getTotalScore(0), getTotalScore(16));
            } catch (IOException ex) {
                Logger.getLogger(YahtzeeGameDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /*
     * Method to reset all dice.
     */
    
    private void resetDice() {
        
        //Loops through check boxes.
        for (int i = 0; i < dieFields.size(); i++) {
            dieFields.get(i).setText("0");
        }
    }
    
    /*
     * Method to add up upper score boxes.
     */
    
    private void addUpperScore() {
        
        //Variable to hold score total
        int score = 0;
        
        //Variable to hold text from field.
        String fieldScore;
        
        //Totals upper score.
        for(int i = 0; i < 6; i++) {
                fieldScore = textFields.get(i + game.getPlayerTurn()).getText();
                score += Integer.parseInt(fieldScore);
        }
        
        //Checks if upper score bonus has been added. If it hasn't checks to see
        //if upper score should get bonus and if so adds bonus and changes flag.
        if (!upperScore) {
            if (score >= 63) {
                score += 35;
                upperScore = true;
            }
        }
        
        //Sets upper score box.
        textFields.get(6 + game.getPlayerTurn()).setText(String.valueOf(score));
    }
    
    /*
     * Method to add up lower score boxes.
     */
    
    private void addLowerScore() {
        //Variable to hold score total
        int score = 0;
        
        //Variable to hold text from field.
        String fieldScore;
        
        //Totals upper score.
        for(int i = 7; i < 14; i++) {
                fieldScore = textFields.get(i + game.getPlayerTurn()).getText();
                score += Integer.parseInt(fieldScore);
        }
        
        //Sets lower score box.
        textFields.get(14 + game.getPlayerTurn()).setText(String.valueOf(score));
    }
    
    private void addTotalScore(int player) {
        //Variable to hold score total
        int score = 0;
        
        //Variable to hold text from field.
        String fieldScore;
        
        //Gets the upper score and adds it to score.
        fieldScore = textFields.get(6 + player).getText();
        score += Integer.parseInt(fieldScore);
        
        //Gets the lower score and adds it to score.
        fieldScore = textFields.get(14 + game.getPlayerTurn()).getText();
        score += Integer.parseInt(fieldScore);
        
        //Sets total score box.
        textFields.get(15 + game.getPlayerTurn()).setText(String.valueOf(score));
    }
    
    private int getTotalScore(int player) {
        //Variable to hold score total
        int score = 0;
        
        //Variable to hold text from field.
        String fieldScore;
        
        //Gets the upper score and adds it to score.
        fieldScore = textFields.get(6 + player).getText();
        score += Integer.parseInt(fieldScore);
        
        //Gets the lower score and adds it to score.
        fieldScore = textFields.get(14 + game.getPlayerTurn()).getText();
        score += Integer.parseInt(fieldScore);
        
        //Returns total score.
        return score;
    }
    
    /*
     * ActionListners for all buttons.
    */
    
    private class RollDiceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            //Create array to hold check values.
            boolean[] roll = new boolean[NUM_DICE];
            
            //Assign array values.
            for (int i = 0; i < NUM_DICE; i++) {
                roll[i] = reroll.get(i).isSelected();
            }
            
            //Checks if the player has already rolled twice.
            if (game.getTurnNumRolls() < 3) {
                //Call the dice rolling function.
                int[] dice = game.rollDice(roll);

                //Write the dice values to text fields.
                for (int i = 0; i < NUM_DICE; i++) {
                    dieFields.get(i).setText(Integer.toString(dice[i]));
                }
            }
            //Tells player they already rolled twice and must make a score selection.
            else {
                JOptionPane.showMessageDialog(null, 
                "You have already rolled thrice choose a field to score.");
            }
        }
        
    }
    
    private class ScoreAcesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            //Value of field that should hold the score.
            int field = 0;
            
            //Checks if a field is scored. if not scores the field then swaps the turn
            //and resets the check boxes.
            if (!game.getScored(field)) {
                textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreAces(field)));
            
                addUpperScore();
                addTotalScore(game.getPlayerTurn());
                gameOver = game.swapTurn();
                resetCheckBoxes();
            }
            //Message displayed if field is already scored.
            else {
                JOptionPane.showMessageDialog(null, 
                "You have already scored this field. Please choose a new score.");
            }   
        }
    }
    
    private class ScoreTwosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            //Value of field that should hold the score.
            int field = 1;
            
            //Checks if a field is scored. if not scores the field then swaps the turn
            //and resets the check boxes.
            if (!game.getScored(field)) {
                textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreTwos(field)));
            
                addUpperScore();
                addTotalScore(game.getPlayerTurn());
                gameOver = game.swapTurn();
                resetCheckBoxes();
            }
            //Message displayed if field is already scored.
            else {
                JOptionPane.showMessageDialog(null, 
                "You have already scored this field. Please choose a new score.");
            }   
        }
    }
    
    private class ScoreThreesListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 2;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field)) {
                    textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreThrees(field)));

                    addUpperScore();
                    addTotalScore(game.getPlayerTurn());
                   gameOver =  game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }

    private class ScoreFoursListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 3;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field)) {
                    textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreFours(field)));

                    addUpperScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }

    private class ScoreFivesListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 4;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field)) {
                    textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreFives(field)));

                    addUpperScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }

    private class ScoreSixesListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 5;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field)) {
                    textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreSixes(field)));

                    addUpperScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }
    
    private class ScoreToKListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 7;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field-1)) {
                    
                    if(game.tokCheck())
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreAllDice(field)));
                    else
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(0));

                    addLowerScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }
    
    private class ScoreFoKListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 8;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field-1)) {
                    
                    if(game.fokCheck())
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreAllDice(field)));
                    else
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(0));

                    addLowerScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }
    
    private class ScoreFHListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 9;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field-1)) {
                    
                    if(game.fhCheck())
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreFullHouse(field)));
                    else
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(0));

                    addLowerScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }
    
    private class ScoreSSListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 10;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field-1)) {
                    
                    if(game.ssCheck())
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreSmallStraight(field)));
                    else
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(0));

                    addLowerScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }
    
    private class ScoreLSListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 11;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field-1)) {
                    
                    if(game.lsCheck())
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreLargeStraight(field)));
                    else
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(0));

                    addLowerScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }
    
    private class ScoreYahtzeeListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 12;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field-1)) {
                    
                    if(game.yahtzeeCheck())
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreYahtzee(field)));
                    else
                        textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(0));

                    addLowerScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }
    
    private class ScoreChanceListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //Value of field that should hold the score.
                int field = 13;

                //Checks if a field is scored. if not scores the field then swaps the turn
                //and resets the check boxes.
                if (!game.getScored(field-1)) {
                
                    textFields.get(field + game.getPlayerTurn()).setText(String.valueOf(game.scoreAllDice(field)));
                   
                    addLowerScore();
                    addTotalScore(game.getPlayerTurn());
                    gameOver = game.swapTurn();
                    resetCheckBoxes();
                }
                //Message displayed if field is already scored.
                else {
                    JOptionPane.showMessageDialog(null, 
                    "You have already scored this field. Please choose a new score.");
                }   
            }
        }

    public static void main(String[] args) {
        new YahtzeeGameDemo();
        
    }

}

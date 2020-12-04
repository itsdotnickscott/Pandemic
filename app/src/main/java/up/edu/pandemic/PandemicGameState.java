package up.edu.pandemic;

import up.edu.GameFramework.infoMessage.GameState;
import androidx.annotation.NonNull;
import java.util.Random;

/** PandemicState
 * This is the game state for Pandemic which contains all the information that is required to
 * play the game.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
 */
public class PandemicGameState extends GameState {
    // game rule variables
    public static final int HAND_LIMIT = 7;
    public static final int NUM_ACTIONS = 4;
    public static final int MAX_STATIONS = 6;
    public static final int MAX_OUTBREAKS = 8;
    public static final int STARTING_RATE = 2;
    public static final int REQUIRED_CARDS_CURE = 5;
    public static final int NUM_EPIDEMICS = 5;
    public static final int NUM_DRAW_CARDS = 2;

    // game condition variables
    public static final int PLAY = 0;
    public static final int WIN = 1;
    public static final int LOSE = 2;

    // game action variables
    public static final int DRIVE_FERRY = 0;
    public static final int DIRECT_FLIGHT = 1;
    public static final int CHARTER_FLIGHT = 2;
    public static final int SHUTTLE_FLIGHT = 3;
    public static final int TREAT = 4;
    public static final int BUILD = 5;
    public static final int SHARE = 6;
    public static final int CURE = 7;
    public static final int PASS = 8;
    public static final int END_TURN = 9;
    public static final int NUM_TYPE_OF_ACTIONS = 10;

    // instance variables
    private Disease[] diseases;
    private int outbreaks;
    private int infRate;
    private int stationsLeft;

    private int numPlayers;
    private int currPlayer;

    private int actionsLeft;
    private boolean needToDiscard;
    private int drawCardsLeft;

    private City[][] playerHands;
    private City[] currCity;
    private Board cities;

    private Deck infectionDeck;
    private Deck playerDeck;
    private int epiLeft;
    private int gameCondition;

    private String infoBar = "";
    private String infoBarInfected = "";
    private String infoBarOutbroke = "";
    private String infoBarEpidemic = "";
    private boolean epidemicPulled;

    /** PandemicState()
     * The constructor class for the main Pandemic state. Sets all instance variables to start.
     * @param num Number of players.
     */
    public PandemicGameState(int num) {
        // create the four diseases
        this.diseases = new Disease[Disease.NUM_DISEASES];
        for(int i = 0; i < Disease.NUM_DISEASES; i++) {
            this.diseases[i] = new Disease(i);
        }

        // starting game parameters
        this.outbreaks = 0;
        this.infRate = STARTING_RATE;
        this.stationsLeft = MAX_STATIONS;
        this.epiLeft = NUM_EPIDEMICS;

        // choose starting player at random
        Random rng = new Random();
        this.numPlayers = num;
        this.currPlayer = rng.nextInt(numPlayers);
        this.actionsLeft = NUM_ACTIONS;
        this.needToDiscard = false;
        this.drawCardsLeft = NUM_DRAW_CARDS;

        this.cities = new Board();

        // make the decks, initialize so they contain the same set of cities
        this.infectionDeck = new Deck(this.cities.getAllCities());
        this.playerDeck = new Deck(this.cities.getAllCities());

        // initialize player hands, the max cards are hand limit + 1 in the case of getting an
        // eighth card, then the player will be prompted to discard before getting any more cards
        this.playerHands = new City[this.numPlayers][HAND_LIMIT + 1];

        this.currCity = new City[this.numPlayers];
        // the players start at the CDC in Atlanta
        for(int i = 0; i < this.numPlayers; i++) {
            this.currCity[i] = this.cities.getCity("Atlanta");
        }

        // build CDC
        this.cities.getCity("Atlanta").buildStation();
        this.stationsLeft--;

        // determine how many cards are dealt out at the start
        int deal;
        switch(this.numPlayers) {
            case 2: deal = 4; break;
            case 3: deal = 3; break;
            default:
            case 4: deal = 2; break;
        }

        // this NULL city represents an "empty" slot in a player's hand
        City empty = new City(City.NULL);

        // deal out the starting hands
        for(int i = 0; i < this.numPlayers; i++) {
            for(int j = 0; j < deal; j++) {
                this.playerHands[i][j] = this.playerDeck.draw();
            }
            for(int j = deal; j < HAND_LIMIT + 1; j++) {
                this.playerHands[i][j] = empty;
            }
        }

        // infect starting cities
        for(int i = 3; i > 0; i--) {
            for(int j = 0; j < 3; j++) {
                City infect = this.infectionDeck.draw();
                for(int k = i; k > 0; k--) {
                    infect.infectCity(this.diseases);
                }
            }
        }

        this.playerDeck.insertEpidemics(this.numPlayers);

        this.gameCondition = PLAY;
        this.epidemicPulled = false;
    } // PandemicState()

    /** PandemicState()
     * This is a deep copy constructor which will be used to send to human players.
     * @param orig The original game state that will be copied.
     */
    public PandemicGameState(PandemicGameState orig) {
        this.numPlayers = orig.numPlayers;

        this.diseases = new Disease[Disease.NUM_DISEASES];
        for(int i = 0; i < Disease.NUM_DISEASES; i++) {
            this.diseases[i] = orig.diseases[i];
        }

        this.outbreaks = orig.outbreaks;
        this.infRate = orig.infRate;
        this.stationsLeft = orig.stationsLeft;
        this.epiLeft = orig.epiLeft;

        this.currPlayer = orig.currPlayer;
        this.actionsLeft = orig.actionsLeft;
        this.drawCardsLeft = orig.drawCardsLeft;
        this.needToDiscard = orig.needToDiscard;

        this.infectionDeck = new Deck(orig.infectionDeck);
        this.playerDeck = new Deck(orig.playerDeck);
        this.cities = new Board(orig.cities);

        this.playerHands = new City[this.numPlayers][HAND_LIMIT + 1];
        for(int i = 0; i < this.numPlayers; i++) {
            for(int j = 0; j < HAND_LIMIT + 1; j++) {
                this.playerHands[i][j] = new City(orig.playerHands[i][j]);
            }
        }

        this.currCity = new City[this.numPlayers];
        for(int i = 0; i < this.numPlayers; i++) {
            this.currCity[i] = new City(orig.currCity[i]);
        }

        this.gameCondition = orig.gameCondition;
        this.infoBar = orig.infoBar;
        this.infoBarInfected = orig.infoBarInfected;
        this.infoBarEpidemic = orig.infoBarEpidemic;
        this.infoBarOutbroke = orig.infoBarOutbroke;
        this.epidemicPulled = orig.epidemicPulled;
    } // PandemicState()

    /*
     External Citation
     Date: 20 October 2020.
     Problem: Didn't know how to implement prompting the user to discard.
     Resource: Andrew Nuxoll.
     Solution: After asking Professor Nuxoll, he recommended we create a "needToDiscard" variable.
     */

    /** needToDiscard()
     * This method checks to see if the current player has too many cards. If so, the only action
     * the player can do is discard a card.
     * @return Whether the current player needs to discard.
     */
    public boolean needToDiscard() {
        // count the number of empty slots in the current player's hand
        int numNull = 0;
        for(int i = 0; i < HAND_LIMIT + 1; i++) {
            if(this.playerHands[this.currPlayer][i].getName().equals("NULL")) {
                numNull++;
            }
        }

        // if there are no empty slots, there are eight cards, so the player needs to discard
        if(numNull == 0) {
            this.needToDiscard = true;
        }
        else {
            this.needToDiscard = false;
        }
        return this.needToDiscard;
    } // needToDiscard()

    /** driveFerry()
     * This method performs the Drive/Ferry action, which moves a player to an adjacent connected
     * city.
     * @param player The player that performed this action.
     * @param newCity The city the player is trying to go to.
     * @return Whether the action was valid.
     */
    public boolean driveFerry(int player, City newCity) {
        if(!this.checkDoableActions(player)[DRIVE_FERRY]) {
            return false;
        }

        for(int i = 0; i < this.currCity[player].getConnections().length; i++){
            // check if the current city is adjacent to the new city
            if(this.currCity[player].getConnections()[i].getName().equals(newCity.getName())) {
                this.currCity[player] = this.cities.getCity(newCity.getName());
                this.actionsLeft--;
                this.infoBar = "PLAYER " + (this.currPlayer + 1) + " MOVED TO "
                        + this.currCity[player].getName().toUpperCase();
                return true;
            }
        }
        return false;
    } // driveFerry()

    /** directFlight()
     * This method performs the Direct Flight action, which moves a player to the city of a card
     * they discarded.
     * @param player The player that performed this action.
     * @param newCity The city the player discarded in order to go to it.
     * @return Whether the action was valid.
     */
    public boolean directFlight(int player, City newCity) {
        if(!this.checkDoableActions(player)[DIRECT_FLIGHT]) {
            return false;
        }

        // makes sure the player has the card, then discard it and move the player
        if(this.hasCard(player, newCity)) {
            this.currCity[player] = this.cities.getCity(newCity.getName());
            this.discard(player, newCity, true);
            this.infoBar = "PLAYER " + (this.currPlayer + 1) + " MOVED TO "
                    + this.currCity[player].getName().toUpperCase();
            this.actionsLeft--;
            return true;
        }
        return false;
    } // directFlight()

    /** charterFlight()
     * This method performs the Charter Flight action, which moves a player to any city on the board
     * if they discard the card of the city they are in.
     * @param player The player that performed this action.
     * @param newCity The city the player is trying to go to.
     * @return Whether the action was valid.
     */
    public boolean charterFlight(int player, City newCity) {
        if(!this.checkDoableActions(player)[CHARTER_FLIGHT]) {
            return false;
        }

        this.discard(player, this.currCity[player], true);
        this.currCity[player] = this.cities.getCity(newCity.getName());
        this.infoBar = "PLAYER " + (this.currPlayer + 1) + " MOVED TO "
                + this.currCity[player].getName().toUpperCase();
        this.actionsLeft--;
        return true;
    } // charterFlight()

    /** shuttleFlight()
     * This method performs the Shuttle Flight action, which moves a player between two research
     * stations.
     * @param player The player that performed the action.
     * @param newCity The city the player is trying to go to.
     * @return Whether the action was valid.
     */
    public boolean shuttleFlight(int player, City newCity) {
        if (!this.checkDoableActions(player)[SHUTTLE_FLIGHT]) {
            return false;
        }

        /*
         External Citation
         Date: 17 November 2020.
         Problem: Shuttle Flight was not working in-game.
         Resource: Jacob Noble
         Solution: After project review, Jacob identified the bug in his code report.
         */

        //check if both locations have a research station, if so, move them
        if (currCity[player].hasStation() && this.cities.getCity(newCity.getName()).hasStation()) {
            this.currCity[player] = this.cities.getCity(newCity.getName());
            this.infoBar = "PLAYER " + (this.currPlayer + 1) + " MOVED TO "
                    + this.currCity[player].getName().toUpperCase();
            this.actionsLeft--;
            return true;
        }
        return false;
    } // shuttleFlight()

    /** treat()
     * This method performs the "treat disease" action, which removes a disease cube from the
     * location of the current player.
     * @param player The player that performed this action.
     * @return Whether the action was valid.
     */
    public boolean treat(int player) {
        if (!this.checkDoableActions(player)[TREAT]) {
            return false;
        }

        // which city player is in
        City curr = this.currCity[player];
        int col = curr.getColor();

        // if treating the disease was successful, remove a cube
        // if the disease is already cured, remove ALL cubes
        if(diseases[curr.getColor()].getState() == Disease.CURED) {
            while (curr.treatDisease()) {
                this.diseases[col].removeCube();
            }
            this.actionsLeft--;
            return true;
        }

        if(curr.treatDisease()) {
            this.diseases[col].removeCube();
            this.infoBar = "PLAYER " + (this.currPlayer + 1) + " TREATED "
                    + this.currCity[player].getName().toUpperCase();
            this.actionsLeft--;
            this.checkIfEradicated();
            return true;
        }
        return false;
    } // treat()

    /** buildStation()
     * This method performs the Build Research Station action, which builds a research station if
     * the player discards the city that they are currently in.
     * @param player The player that performed the action.
     * @return Whether the action was valid.
     */
    public boolean buildStation(int player) {
        if (!this.checkDoableActions(player)[BUILD]) {
            return false;
        }

        // gets the city the player is in
        City curr = this.currCity[player];
        // checks to see if ... a) there are stations left, and b) the player has that card
        if(this.stationsLeft > 0 && this.hasCard(player, curr)) {
            // attempts to build a station, then discard card
            if(curr.buildStation()) {
                this.discard(player, this.currCity[player], true);
                this.stationsLeft--;
                this.infoBar = "PLAYER " + (this.currPlayer + 1) + " BUILT IN "
                        + this.currCity[player].getName().toUpperCase();
                this.actionsLeft--;
                return true;
            }
        }
        return false;
    } // buildStation()

    /** share()
     * This method performs the Share Knowledge action, which either gives or takes a city card of
     * two players that are in the same city.
     * @param player The player that performed the action.
     * @return Whether the action was valid.
     */
    public boolean share(int player) {
        if(!this.checkDoableActions(player)[SHARE]) {
            return false;
        }

        for(int i = 0; i < this.numPlayers; i++) {
            // make sure we are not looking at the same player
            if(i != player) {
                for(int j = 0; j < HAND_LIMIT + 1; j++) {
                    // check to see if that player has that city card
                    if(playerHands[i][j] == this.currCity[this.currPlayer]) {
                        // then insert that card into current player's hand
                        return swapCards(this.currCity[this.currPlayer], i, player, j);
                    }
                    // check to see if current player has that city card
                    else if(playerHands[player][j] == this.currCity[this.currPlayer]) {
                        // then insert that card into other player's hand
                        return swapCards(this.currCity[this.currPlayer], player, i, j);
                    }
                }
            }
        }
        return false;
    } // share()

    /** cure()
     * This method performs the Cure Disease action, which discards five cards of the same color
     * in order to cure the disease (required to win).
     * @param player The player that performed the action.
     * @return Whether the action was valid.
     */
    public boolean cure(int player) {
        if(!this.checkDoableActions(player)[CURE]) {
            return false;
        }

        if(this.countFiveCards() != -1) {
            this.discardToCure(this.countFiveCards());
            return true;
        }
        return false;
    } // cure()

    /** discardToCure()
     * This is a helper method which is called when a player cures a disease. It removes five cards
     * from their hand of the same color.
     * @param color The disease that is being cured.
     */
    public void discardToCure(int color) {
        int count = 0;
        for(int i = 0; i < playerHands[this.currPlayer].length; i++) {
            if(playerHands[this.currPlayer][i].getColor() == color && count != REQUIRED_CARDS_CURE) {
                this.discard(this.currPlayer, playerHands[this.currPlayer][i], true);
                count++;
            }
        }
        this.diseases[color].cure();
        this.infoBar = "PLAYER " + (this.currPlayer + 1) + " CURED "
                + this.diseases[color].getName() + " DISEASE";
        this.actionsLeft--;
        this.gameWon();
    } // discardToCure()

    /** forgoAction()
     * This method removes an action from the current player, if they choose to do so.
     * @param player The player that performed the action.
     * @return Whether the action was valid.
     */
    public boolean forgoAction(int player) {
        if(!this.checkDoableActions(player)[PASS]) {
            return false;
        }

        this.infoBar = "PLAYER " + (this.currPlayer + 1) + " FORGOED AN ACTION";
        this.actionsLeft--;
        return true;

    } // forgoAction()

    /** endTurn()
     * This method performs the End Turn action, which will carry out the rest of the player's turn,
     * including drawing two city cards and drawing infection cards.
     * @param player The player that performed the action.
     * @return Whether the action was valid.
     */
    public boolean endTurn(int player) {
        if(!this.checkDoableActions(player)[END_TURN]) {
            return false;
        }

        /*
         External Citation
         Date: 20 October 2020.
         Problem: Had trouble getting the player to both draw and discard in the same action if
         necessary.
         Resource: Andrew Nuxoll.
         Solution: We video chatted with Professor Nuxoll who helped create a "drawCardsLeft"
         variable.
         */

        if(this.actionsLeft == 0) {
            int origOutbreak = this.outbreaks;

            while(this.drawCardsLeft != 0) {
                if(!drawCard()) {
                    return false;
                }
                this.drawCardsLeft--;
            }
            // if it gets to here, the player has successfully drawn two cards.
            if(this.needToDiscard()) {
                return false;
            }

            if(!this.epidemicPulled) {
                this.infoBarEpidemic = "";
            }
            this.drawCardsLeft = NUM_DRAW_CARDS;
            this.drawInfectionCards();
            this.infoBarOutbroke = this.outbreaks - origOutbreak + " OUTBREAKS OCCURRED";

            // continue to next player
            this.currPlayer = ++this.currPlayer % this.numPlayers;
            this.actionsLeft = NUM_ACTIONS;
            this.epidemicPulled = false;
            return true;
        }
        else {
            return false;
        }
    } // endTurn()

    /** discard()
     * This method removes a certain card from a player's hand, usually to perform actions or to
     * satisfy the hand limit.
     * @param player The player who is discarding a card.
     * @param disCity The city card they are trying to discard.
     * @param override Is true if it is called from an action that requires a card to be discarded.
     * @return Whether the action was valid.
     */
    public boolean discard(int player, City disCity, boolean override){
        if(!override && !this.needToDiscard()) {
            return false;
        }

        // this NULL city represents an "empty" slot in a player's hand
        City empty = new City(City.NULL);

        if(hasCard(player, disCity)) {
            // finds the index of the card to discard and make it "empty"
            for(int i = 0; i < playerHands[player].length; i++) {
                if(playerHands[player][i].getName().equals(disCity.getName())) {
                    playerHands[player][i] = empty;
                    return true;
                }
            }
        }

        return false;
    } // discard()

    /** discard()
     * This method is called when a player makes an action. It will call the other discard method.
     * @param player The player who is discarding a card.
     * @param cityName The city name they are trying to discard.
     * @return Whether the action was valid.
     */
    public boolean discard(int player, String cityName) {
        for(int i = 0; i < playerHands[player].length; i++) {
            if(playerHands[player][i].getName().equals(cityName)) {
                return discard(player, playerHands[player][i], false);
            }
        }
        return false;
    } // discard()

    /** swapCards()
     * This is a helper method which transfers cards, and appropriately replaces it with an "empty"
     * city card in the original player's hand.
     * @param location The city card that is wanting to be swapped.
     * @param origPlayer The player in which the city card is coming from.
     * @param newPlayer The player in which the city card is going to.
     * @param origIdx The index of the city card is in for the original player.
     * @return Whether the action was valid.
     */
    public boolean swapCards(City location, int origPlayer, int newPlayer, int origIdx) {
        // this NULL city represents an "empty" slot in a player's hand
        City empty = new City(City.NULL);

        for(int i = 0; i < this.playerHands[newPlayer].length; i++) {
            // if we find an empty slot in the player's hand, place the card in
            if(this.playerHands[newPlayer][i].getName().equals("NULL")) {
                this.playerHands[newPlayer][i] = location;
                // and remove the card from the original player's hand with an "empty" city card
                this.playerHands[origPlayer][origIdx] = empty;
                this.infoBar = "PLAYER " + (this.currPlayer + 1) + " SHARED "
                        + this.currCity[this.currPlayer].getName().toUpperCase();
                this.actionsLeft--;
                return true;
            }
        }
        return false;
    } // swapCards()

    /** hasCard()
     * This is a helper method which checks to see if a player has a certain city card.
     * @param player The player whose hand we are checking.
     * @param card The city card we are looking for.
     * @return Whether the player has that city card.
     */
    public boolean hasCard(int player, City card) {
        // check if the player has the city card in their hand
        for(int i = 0; i < this.playerHands[player].length; i++) {
            if(this.playerHands[player][i].getName().equals(card.getName()) &&
                    !this.playerHands[player][i].getName().equals("NULL")) {
                return true;
            }
        }
        return false;
    } // hasCard()

    /** drawCard()
     * This method draws a city card for the player and adds it into their hand.
     * @return Whether drawing a card was successful.
     */
    public boolean drawCard() {
        if(this.needToDiscard()) {
            return false;
        }

        for(int i = 0; i < HAND_LIMIT + 1; i++) {
            if(this.playerHands[this.currPlayer][i].getName().equals("NULL")) {
                City draw = this.playerDeck.draw();
                if(draw.getName().equals("Epidemic")) {
                    this.epidemic();
                }
                else {
                    this.playerHands[this.currPlayer][i] = draw;
                }
                return true;
            }
        }
        return false;
    } // drawCard()

    /** epidemic()
     * This method is called when an Epidemic card is pulled instead of a regular city card.
     * Epidemics increase the difficulty of the game significantly.
     */
    public void epidemic() {
        this.epidemicPulled = true;

        // step 1) increase: adjust the infection rate
        switch(this.epiLeft) {
            case 5: case 4: break;
            case 3: case 2: this.infRate = 3; break;
            case 1: this.infRate = 4; break;
        }
        this.epiLeft--;

        // step 2) infect: draw bottom card, infect that city at max
        City epidemic = this.infectionDeck.drawBottomCard();
        this.infoBarEpidemic = "EPIDEMIC PULLED - " + epidemic.getName() + " INFECTED AT MAX";
        if(diseases[epidemic.getColor()].getState() != Disease.ERADICATED) {
            for(int i = 0; i < City.MAX_CUBES; i++) {
                int numOutbroke = epidemic.infectCity(this.diseases);
                if(numOutbroke > 0) {
                    this.outbreaks += numOutbroke;
                    i = City.MAX_CUBES;
                }
            }
        }

        // step 3) intensify: shuffle all previously drawn cards
        this.infectionDeck.shuffleEpidemic();
        this.gameLost();
    } // epidemic()

    /** drawInfectionCards()
     * This method draws infection cards equal to the current infection rate, then places a disease
     * cube there. An outbreak occurs if the city already has three cubes.
     */
    public void drawInfectionCards() {
        this.infoBarInfected = "";
        for(int i = 0; i < this.infRate; i++) {
            City infect = this.infectionDeck.draw();
            this.infoBarInfected += infect.getName();
            if(this.infRate == 2 & i == 0) {
                this.infoBarInfected += " AND ";
            }
            else if(i < this.infRate - 2) {
                this.infoBarInfected += ", ";
            }
            else if(i < this.infRate - 1){
                this.infoBarInfected += ", AND ";
            }

            if (diseases[infect.getColor()].getState() != Disease.ERADICATED) {
                int numOutbroke = infect.infectCity(this.diseases);
                this.outbreaks += numOutbroke;
                this.cities.resetHasOutbroke();
            }
        }
        this.infoBarInfected += " WERE INFECTED";

        this.gameLost();
    } // drawInfectionCards()

    /** gameLost()
     * This method checks to see if the game is lost yet.
     */
    public void gameLost() {
        // LOSE CONDITION #1: no more disease cubes to place
        for(int i = 0; i < Disease.NUM_DISEASES; i++) {
            if(this.diseases[i].getCubesLeft() < 0) {
                this.gameCondition = LOSE;
            }
        }
        // LOSE CONDITION #2: eight outbreaks
        if(this.outbreaks >= MAX_OUTBREAKS) {
            this.gameCondition = LOSE;
        }
        // LOSE CONDITION #3: can't draw player cards
        else if(this.playerDeck.getCurrPos() == this.playerDeck.getDeckSize()) {
            this.gameCondition = LOSE;
        }
    } // gameLost()

    /** gameWon()
     * This method checks to see if the game is won yet.
     */
    public void gameWon() {
        for(int i = 0; i < Disease.NUM_DISEASES; i++) {
            if(this.diseases[i].getState() == Disease.UNCURED) {
                return;
            }
        }
        this.gameCondition = WIN;
    } // gameWon()

    /** countFiveCards()
     * This is a helper method that checks to see if there are five cards of the same color in the
     * player's hand.
     * @return The color of the disease they have five cards of. -1 if no color met the requirement.
     */
    public int countFiveCards() {
        // counters for cards
        int blue = 0;
        int yellow = 0;
        int black = 0;
        int red = 0;
        // go through the player hand and determine which disease can be cured
        for(int i = 0; i < this.playerHands[this.currPlayer].length; i++) {
            if(!this.playerHands[this.currPlayer][i].getName().equals("NULL")) {
                switch(this.playerHands[this.currPlayer][i].getColor()) {
                    // count cards
                    case Disease.BLUE:
                        blue++;
                        if(blue == 5) {
                            return Disease.BLUE;
                        }
                        break;
                    case Disease.YELLOW:
                        yellow++;
                        if(yellow == 5) {
                            return Disease.YELLOW;
                        }
                        break;
                    case Disease.BLACK:
                        black++;
                        if(black == 5) {
                             return Disease.BLACK;
                        }
                        break;
                    case Disease.RED:
                        red++;
                        if(red == 5) {
                            return Disease.RED;
                        }
                        break;
                }
            }
        }

        // if it gets here, not enough cards were found of one color
        return -1;
    } // countFiveCards()

    /** checkIfEradicated()
     * This methods checks all diseases to eradicate if they have been cured and treated in
     * all cities.
     */
    public void checkIfEradicated() {
        for(int i = 0; i < Disease.NUM_DISEASES; i++) {
            if (diseases[i].getCubesLeft() == Disease.NUM_CUBES &&
                    diseases[i].getState() == Disease.CURED) {
                diseases[i].eradicate();
            }
        }
    } // checkIfEradicated()

    /*
     External Citation
     Date: 20 October 2020.
     Problem: Didn't know how to implement a unit test handling illegal moves.
     Resource: Andrew Nuxoll.
     Solution: After chatting with Professor Nuxoll, he recommended creating a method that
     identified which moves were illegal.
     */

    /** checkDoableActions()
     * This method checks to see what actions can be performed; not necessarily if they are valid.
     * @param player The player that is performing the action.
     * @return An array containing all actions and whether they are doable.
     */
    public boolean[] checkDoableActions(int player) {
        // declaring a boolean array automatically sets values to false
        boolean[] canDo = new boolean[NUM_TYPE_OF_ACTIONS];

        // make sure the player is the current player
        if(this.currPlayer == player) {
            // count the number of cards in the player's hand
            int numCards = 0;
            // also check to see if the player has a card that they are currently in
            boolean currCard = false;
            for(int i = 0; i < this.playerHands[this.currPlayer].length; i++) {
                if(!this.playerHands[this.currPlayer][i].getName().equals("NULL")) {
                    numCards++;
                    if(this.playerHands[this.currPlayer][i] == this.currCity[this.currPlayer]) {
                        currCard = true;
                    }
                }
            }
            if(!this.needToDiscard()) {
                if(this.actionsLeft != 0) {
                    // if there are actions left, a player can always use the drive/ferry action
                    canDo[DRIVE_FERRY] = true;
                    // and they can always forgo actions
                    canDo[PASS] = true;

                    // checks to make sure the player has at least one card
                    if(numCards != 0) {
                        canDo[DIRECT_FLIGHT] = true;
                    }

                    // if the player has the card of the city they're in
                    if(currCard) {
                        canDo[CHARTER_FLIGHT] = true;

                        // and if there isn't a research station in the city they're in
                        if(!this.currCity[this.currPlayer].hasStation()) {
                            canDo[BUILD] = true;
                        }
                    }

                    // and check to see if there is at least two people in one city
                    for(int i = 0; i < this.numPlayers; i++) {
                        // make sure we are not looking at the same player
                        if(i != this.currPlayer) {
                            // check to see if another player is in the same city
                            if(currCity[i] == currCity[player]) {
                                // check if current player has the city card
                                if(currCard) {
                                    canDo[SHARE] = true;
                                }
                                else {
                                    for(int j = 0; j < HAND_LIMIT + 1; j++) {
                                        // check to see if that player has that city card
                                        if (playerHands[i][j].getName().equals
                                                (this.currCity[this.currPlayer].getName())) {
                                            canDo[SHARE] = true;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // checks to see if there's a research station
                    if(this.currCity[this.currPlayer].hasStation()) {
                        // and if there are at least two research stations
                        if(this.stationsLeft < MAX_STATIONS - 1) {
                            canDo[SHUTTLE_FLIGHT] = true;
                        }

                        // and if the current player has five cards of the same color and that it
                        // isn't cured yet
                        if(this.countFiveCards() != -1 &&
                                this.diseases[this.countFiveCards()].getState() == Disease.UNCURED) {
                            canDo[CURE] = true;
                        }
                    }

                    // checks to see if there is at least one cube at the city
                    if(this.currCity[this.currPlayer].getCubes() > 0) {
                        canDo[TREAT] = true;
                    }
                }
                // if there are no actions left, player must end their turn
                else {
                    canDo[END_TURN] = true;
                }
            }
        }

        return canDo;
    } // checkDoableActions()

    // setter
    public void setInfoBar(String info) {
        this.infoBar = info;
    }

    // ---------------------- GETTER METHODS ---------------------- //
    public Disease[] getDiseases() {
        return diseases;
    }

    public int getOutbreaks() {
        return outbreaks;
    }

    public int getInfRate() {
        return infRate;
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }

    public int getEpiLeft() {
        return epiLeft;
    }

    public int getGameCondition() {
        return gameCondition;
    }

    public Board getCities() {
        return cities;
    }

    public City[] getCurrCity() {
        return currCity;
    }

    public int getActionsLeft() {
        return actionsLeft;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public City[][] getPlayerHand() {
        return playerHands;
    }

    public String getInfoBar() {
        return infoBar;
    }

    public String getInfoBarInfected() {
        return infoBarInfected;
    }

    public String getInfoBarOutbroke() {
        return infoBarOutbroke;
    }

    public String getInfoBarEpidemic() {
        return infoBarEpidemic;
    }

    /** toString()
     * This method converts all the information of the Pandemic Game State and puts into a String.
     * @return The String with all of the information.
     */
    @NonNull
    @Override
    public String toString() {
        String gameState = "PANDEMIC GAME STATE\n";

        gameState += "\t-DISEASES-\n";
        for(int i = 0; i < Disease.NUM_DISEASES; i++) {
            gameState += "Color: " + this.diseases[i].getColor() + "\n";
            gameState += "Cubes Left: " + this.diseases[i].getCubesLeft() + "\n";
            gameState += "State: ";
            switch(diseases[i].getState()) {
                case 0: gameState += "Uncured\n"; break;
                case 1: gameState += "Cured\n"; break;
                case 2: gameState += "Eradicated\n"; break;
            }
        }
        gameState += "________________\n";

        gameState += "\t-CURRENT VARIABLES-\n";
        gameState += "Outbreaks: " + this.outbreaks + "\n";
        gameState += "Infection Rate: " + this.infRate + "\n";
        gameState += "Research Stations Left: " + this.stationsLeft + "\n";
        gameState += "Number of Players: " + this.numPlayers + "\n";
        gameState += "Current Player: " + this.currPlayer + "\n";
        gameState += "Need to Discard: " + this.needToDiscard + "\n";
        gameState += "Actions Left: " + this.actionsLeft + "\n";
        gameState += "Epidemics Left: " + this.epiLeft + "\n";
        gameState += "Player Hands: \n";
        for(int i = 0; i < this.playerHands.length; i++){
            gameState += "Player " + i + ": ";
            for(int j = 0; j < this.playerHands[i].length; j++){
                gameState += " " + this.playerHands[i][j].getName();
            }
            gameState += "\n";
        }
        gameState += "Player Locations: \n";
        for(int i = 0; i < this.currCity.length; i++){
            gameState += "Player " + i + " Location: " + this.currCity[i].getName() + "\n";
        }
        gameState += "Infection Deck: \n";
        for(int i = 0; i < this.infectionDeck.getDeckSize(); i++){
            gameState += this.infectionDeck.getCityAtIndex(i).getName() + "\n";
        }
        gameState += "Infection Deck Index Location: " + this.infectionDeck.getCurrPos() + "\n";
        gameState += "Player Deck/City Information: \n";
        for(int i = 0; i < this.playerDeck.getDeckSize(); i++){
            gameState += "City Name: " + this.playerDeck.getCityAtIndex(i).getName() + ":\n";
            gameState += "\t Has Research Station: " +
                    this.playerDeck.getCityAtIndex(i).hasStation() + "\n";
            gameState += "\t Color: " + this.playerDeck.getCityAtIndex(i).getColor() + "\n";
            gameState += "\t Location/Hit Box: \n";
            for(int j = 0; j < 2; j++){
                for(int k = 0; k < 2; k++){
                    gameState += "\t" + this.playerDeck.getCityAtIndex(i).getLocation()[j][k] + "\n";
                }
            }
            gameState += "\t Number of Cubes: " + this.playerDeck.getCityAtIndex(i).getCubes() + "\n";
            gameState += "\t Connections: \n";
            for(int j = 0; j < this.playerDeck.getCityAtIndex(i).getConnections().length; j++){
                gameState += "\t" +
                        this.playerDeck.getCityAtIndex(i).getConnections()[j].getName() + "\n";
            }
        }
        gameState += "Player Deck Index Location: " + playerDeck.getCurrPos() + "\n";
        gameState += "________________\n";
        gameState += "--------------------------------------------\n";
        return gameState;
    } // toString()
}
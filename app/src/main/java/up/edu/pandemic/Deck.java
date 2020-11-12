package up.edu.pandemic;

import java.util.Random;

/** Deck
 * This is a Deck object which contains an array of City objects, which will be used as City Cards
 * for the players in this case.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 20 October 2020.
 */
public class Deck {
    // instance variables
    private City[] deck;
    private int currPos;

    /** Deck()
     * This constructor initializes the deck, and then shuffles it.
     * @param cards The city cards to put into the deck.
     */
    public Deck(City[] cards) {
        Board temp = new Board();
        this.deck = temp.getAllCities();

        this.shuffle(this.deck);

        for(int i = 0; i < cards.length; i++) {
            for(int j = 0; j < this.deck.length; j++) {
                if(cards[i].getName().equals(this.deck[j].getName())) {
                    this.deck[j] = cards[i];
                }
            }
        }

        // currPos starts at -1 so when it first deals, it goes to index 0
        this.currPos = -1;
    } // Deck()

    /** Deck()
     * This is a deep copy constructor which creates a deck of copies of the City Cards.
     * @param orig The Deck object we are making a deep copy of.
     */
    public Deck(Deck orig) {
        this.deck = new City[orig.deck.length];
        for(int i = 0; i < orig.deck.length; i++) {
            this.deck[i] = new City(orig.deck[i]);
        }

        this.currPos = orig.currPos;
    } // Deck()

    /** draw()
     * Draws the next card in the deck.
     * @return The card drawn.
     */
    public City draw() {
        this.currPos++;
        if(currPos == this.deck.length - 1) {
            return null;
        }
        return deck[currPos];
    } // draw()

    /** insertEpidemics()
     * This is a helper method which inserts the epidemic cards after the initial cards have been
     * drawn.
     * @param numPlayers The number of players.
     */
    public void insertEpidemics(int numPlayers) {
        City epidemic = new City(City.EPIDEMIC);
        // temporary decks for a 2 and 4 player game
        City[] temp1 = new City[9];
        City[] temp2 = new City[9];
        City[] temp3 = new City[9];
        City[] temp4 = new City[9];
        // temporary deck for a 3 player game
        City[] temp5;
        if(numPlayers == 3) {
            temp5 = new City[8];
        }
        else {
            temp5 = new City[9];
        }

        //initialize the first mini stack
        for(int j = 0; j < 8; j++){
            temp1[j] = this.deck[j];
        }
        temp1[8] = epidemic; //set the last space equal to an epidemic
        temp1 = shuffle(temp1);

        //stack 2
        for(int j = 8; j < 16; j++){
            temp2[j - 8] = this.deck[j];
        }
        temp2[8] = epidemic;
        temp2 = shuffle(temp2);

        //stack 3
        for(int j = 16; j < 24; j++){
            temp3[j - 16] = this.deck[j];
        }
        temp3[8] = epidemic;
        temp3 = shuffle(temp3);

        //stack 4
        for(int j = 24; j < 32; j++){
            temp4[j - 24] = this.deck[j];
        }
        temp4[8] = epidemic;
        temp4 = shuffle(temp4);

        //stack 5
        if(numPlayers == 3) {
            for(int j = 32; j < 39; j++){
                temp5[j - 32] = this.deck[j];
            }
            temp5[7] = epidemic;
        }
        else {
            for (int j = 32; j < 40; j++) {
                temp5[j - 32] = this.deck[j];
            }
            temp5[8] = epidemic;
        }
        temp5 = shuffle(temp5);

        //put the newly shuffled decks into the final player deck
        for(int i = 0; i < this.deck.length; i++){
            if(i < 9) {
                this.deck[i] = temp1[i];
            }
            else if(i < 18) {
                this.deck[i] = temp2[i - 9];
            }
            else if(i < 27) {
                this.deck[i] = temp3[i - 18];
            }
            else if(i < 36) {
                this.deck[i] = temp4[i - 27];
            }
            else if(i < 45) {
                if(!(i == 44 && numPlayers == 3)) {
                    this.deck[i] = temp5[i - 36];
                }
            }
        }
    } // insertEpidemics()

    /** shuffle()
     * This is a helper method which shuffles an array.
     * @param cards an array of City objects.
     * @return City[] returns the shuffled array.
     */
    public City[] shuffle(City[] cards){
        int shuffle = 1000;
        Random rng = new Random();
        // randomize the cards by swapping two cards
        for (int i = 0; i < shuffle; i++) {
            int card1 = rng.nextInt(cards.length);
            int card2 = rng.nextInt(cards.length);
            City temp = cards[card1];
            cards[card1] = cards[card2];
            cards[card2] = temp;
        }
        return cards;
    } // shuffle()

    /** drawBottomCard()
     * This method is called when an epidemic is pulled, and we need to pull the card on the bottom
     * of the deck.
     * @return The bottom card.
     */
    public City drawBottomCard() {
        return this.deck[Board.NUM_CITIES - 1];
    } // drawBottomCard()

    /** shuffleEpidemic()
     * This method brings the bottom card up to the discard pile, shifting every card down, and then
     * shuffles only the discard pile.
     */
    public void shuffleEpidemic() {
        // shift all cards up
        City temp = this.deck[Board.NUM_CITIES - 1];
        for(int i = Board.NUM_CITIES - 1; i > this.currPos + 1; i--) {
            this.deck[i] = this.deck[i - 1];
        }
        // in order to put the new card onto the discard pile
        this.deck[this.currPos + 1] = temp;

        // now we shuffle only the discard pile, shuffle is arbitrary
        int shuffle = 500;
        Random rng = new Random();
        for(int i = 0; i < shuffle; i++) {
            int card1 = rng.nextInt(this.currPos + 2);
            int card2 = rng.nextInt(this.currPos + 2);

            City swap = this.deck[card1];
            this.deck[card1] = this.deck[card2];
            this.deck[card2] = swap;
        }

        this.currPos = -1;
    } // shuffleEpidemic()

    /** getCurrPos()
     * @return The current position of the deck.
     */
    public int getCurrPos() {
        return this.currPos;
    } // getCurrPos()

    /** getDeckSize()
     * @return The size of the deck.
     */
    public int getDeckSize() {
        int count = 0;
        for(int i = 0; i < this.deck.length; i++) {
            if(!this.deck[i].getName().equals("NULL")) {
                count++;
            }
        }

        return count;
    } // getDeckSize()

    public int getCardsLeft() {
        int count = 0;
        for(int i = this.currPos + 1; i < this.deck.length; i++) {
            if(!this.deck[i].getName().equals("NULL")) {
                count++;
            }
        }

        return count;
    }

    /** getCityAtIndex()
     * @param idx The index of the deck.
     * @return The city at that given index.
     */
    public City getCityAtIndex(int idx) {
        return this.deck[idx];
    } // getCityAtIndex()
}

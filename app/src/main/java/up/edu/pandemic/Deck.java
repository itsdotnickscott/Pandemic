package up.edu.pandemic;

import java.util.Random;

/** Deck
 * This is a Deck object which contains an array of City objects, which will be used as City Cards
 * for the players in this case.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
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
        // determine deck size - add one to round up
        int pileSize = (this.getCardsLeft() + 1) / PandemicGameState.NUM_EPIDEMICS;

        City epidemic = new City(City.EPIDEMIC);

        // initialize the starting mini stacks
        City[][] temp = new City[PandemicGameState.NUM_EPIDEMICS][];
        for(int i = 0; i < PandemicGameState.NUM_EPIDEMICS; i++) {
            if(numPlayers == 3 && i == PandemicGameState.NUM_EPIDEMICS - 1) {
                temp[i] = new City[pileSize];
            }
            else {
                temp[i] = new City[pileSize + 1];
            }
        }

        // create the mini stacks
        for(int i = 0; i < PandemicGameState.NUM_EPIDEMICS; i++) {
            if(numPlayers == 3 && i == PandemicGameState.NUM_EPIDEMICS - 1) {
                pileSize--;
            }
            for(int j = 0; j < pileSize; j++) {
                // insert city into this temporary mini stack
                temp[i][j] = this.deck[this.currPos + 1 + (i * pileSize) + j];
            }
            temp[i][pileSize] = epidemic; // last spot is an epidemic card
            temp[i] = shuffle(temp[i]);
        }

        // reset pile size to include newly created epidemic cards
        pileSize = ((this.getCardsLeft() + 1) / PandemicGameState.NUM_EPIDEMICS) + 1;

        // reset deck variables (making a brand new player deck)
        this.deck = null;
        this.deck = new City[Board.NUM_CITIES];
        this.currPos = 0;

        for(int i = 0; i < PandemicGameState.NUM_EPIDEMICS; i++) {
            if(numPlayers == 3 && i == PandemicGameState.NUM_EPIDEMICS - 1) {
                pileSize--;
            }
            for(int j = 0; j < pileSize; j++) {
                this.deck[this.currPos] = temp[i][j];
                this.currPos++;
            }
        }

        // put "null cards" into the rest of the deck
        City empty = new City(City.NULL);

        for(int i = this.currPos; i < Board.NUM_CITIES; i++) {
            this.deck[i] = empty;
        }

        // reset current position so player can draw from the beginning
        this.currPos = -1;
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
        return this.deck[this.getDeckSize() - 1];
    } // drawBottomCard()

    /** shuffleEpidemic()
     * This method brings the bottom card up to the discard pile, shifting every card down, and then
     * shuffles only the discard pile.
     */
    public void shuffleEpidemic() {
        // shift all cards not drawn up
        City temp = this.deck[this.getDeckSize() - 1];
        for(int i = this.getDeckSize() - 1; i > this.currPos + 1; i--) {
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

    /** getCardsLeft()
     * @return The number of cards left to be drawn.
     */
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

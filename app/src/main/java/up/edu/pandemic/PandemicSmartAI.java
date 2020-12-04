package up.edu.pandemic;

import android.util.Log;
import java.util.ArrayList;
import up.edu.GameFramework.GameComputerPlayer;
import up.edu.GameFramework.infoMessage.GameInfo;

/** PandemicSmartAI
 * This is a computer player for Pandemic, which makes decisions based on the context of the game
 * state.
 * @author Nick Scott, Sarah Strong, Emily Vo.
 * @version 24 November 2020.
 */

public class PandemicSmartAI extends GameComputerPlayer {
    PandemicGameState state = null;

    public static final int HIGH_PRIORITY = 3;
    public static final int MID_PRIORITY = 2;
    public static final int LOW_PRIORITY = 1;

    public static final int MAX_DEPTH_CURE = 12;
    public static final int MAX_DEPTH_BUILD = 3;
    public static final int MAX_DEPTH_HIGH_PRIORITY = 6;
    public static final int MAX_DEPTH_MID_PRIORITY = 3;
    public static final int MAX_DEPTH_LOW_PRIORITY = 1;

    /** PandemicSmartAI()
     * This is the constructor for the Smart AI.
     * @param name The player's name.
     */
    public PandemicSmartAI(String name) {
        super(name);
    } // PandemicSmartAI()

    /** receiveInfo()
     * This method receives the current game state and makes decisions on what to do for the turn.
     * @param info The current game info.
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof PandemicGameState){

            /*
             External Citation
             Date: 24 November 2020.
             Problem: Smart AI got stuck after completing one action.
             Resource: Andrew Nuxoll.
             Solution: Nuxoll identified the issue as our code not checking the player's turn first.
             */

            if (((PandemicGameState) info).getCurrPlayer() != this.playerNum) {
                return;
            }

            state = (PandemicGameState) info;

            /* --------------------------- unit test ----------------------------
            targetCity = ((PandemicGameState) info).getCities().getCity("Istanbul");
            path = findPath(targetCity);
            for(int i = 0; i < path.length; i++) {
                Log.i("test", "" + i + ": " + path[i].getName());
            } ------------------------------------------------------------------- */

            // if cpu needs to discard
            if(this.state.needToDiscard()) {
                int[] ranks = this.rankCards();
                int highestPriority = -1;
                String cityName = "";
                for(int i = 0; i < PandemicGameState.HAND_LIMIT + 1; i++) {
                    if(ranks[i] > highestPriority) {
                        cityName = this.state.getPlayerHand()[this.playerNum][i].getName();
                    }
                }
                DiscardAction action = new DiscardAction(this, cityName);
                this.game.sendAction(action);
            }

            // if cpu has no more actions, end turn
            if(this.state.getActionsLeft() == 0) {
                EndTurnAction action = new EndTurnAction(this);
                this.game.sendAction(action);
            }

            this.sleep(1.2);

            // actions will be performed by priority
            if(!this.tryToCure()) {
                if(!this.tryToTreat(HIGH_PRIORITY, MAX_DEPTH_HIGH_PRIORITY)) {
                    if(!this.tryToBuild()) {
                        if(!this.tryToTreat(MID_PRIORITY, MAX_DEPTH_MID_PRIORITY)) {
                            if(!this.tryToTreat(LOW_PRIORITY, MAX_DEPTH_LOW_PRIORITY)) {
                                ForgoAction action = new ForgoAction(this);
                                this.game.sendAction(action);
                            }
                        }
                    }
                }
            }
        }
    } // receiveInfo()

    /** tryToCure()
     * If possible, the computer player will try to cure a disease.
     * @return Whether the computer attemped an action.
     */
    public boolean tryToCure() {
        ArrayList<City> stations = new ArrayList<City>();

        if (this.state.countFiveCards() == -1) {
            return false;
        }

        // find all cities with research stations
        for (int i = 0; i < Board.NUM_CITIES; i++) {
            if (this.state.getCities().getAllCities()[i].hasStation()) {
                stations.add(this.state.getCities().getAllCities()[i]);
            }
        }

        City[] path = determineMainPath(stations, MAX_DEPTH_CURE);
        if (path == null) {
            return false;
        }

        // perform action
        if(this.state.getCurrCity()[this.playerNum].getName().equals(path[path.length - 1].getName())) {
            CureAction action = new CureAction(this);
            this.game.sendAction(action);
        }

        else {
            DriveFerryAction action = new DriveFerryAction(this);
            action.setEndCity(path[1]);
            this.game.sendAction(action);
        }
        return true;
    } // tryToCure()

    /** tryToTreat()
     * If possible, the computer player will try to treat a disease.
     * @return Whether the computer attemped an action.
     */
    public boolean tryToTreat(int priority, int depth) {
        ArrayList<City> cities = this.getPriorityCities(priority);
        City[] path = determineMainPath(cities, depth);
        if (path == null) {
            return false;
        }

        // treat if at city
        if (this.state.getCurrCity()[this.playerNum].getName().equals(path[path.length - 1].getName())) {
            TreatAction action = new TreatAction(this);
            this.game.sendAction(action);
        }

        // move
        else {
            DriveFerryAction action = new DriveFerryAction(this);
            action.setEndCity(path[1]);
            this.game.sendAction(action);
        }
        return true;
    } // tryToTreat()

    /** tryToBuild()
     * If possible, the computer player will try to build a research station.
     * @return Whether the computer attemped an action.
     */
    public boolean tryToBuild() {
        ArrayList<City> cards = new ArrayList<City>();

        for (int i = 0; i < PandemicGameState.HAND_LIMIT + 1; i++) {
            cards.add(this.state.getPlayerHand()[this.playerNum][i]);
        }

        City[] path = this.determineMainPath(cards, MAX_DEPTH_BUILD);
        if (path == null) {
            return false;
        }

        // build if at city
        if (this.state.getCurrCity()[this.playerNum].getName().equals(path[path.length - 1].getName())) {
            BuildStationAction action = new BuildStationAction(this);
            this.game.sendAction(action);
        }

        // move
        else {
            DriveFerryAction action = new DriveFerryAction(this);
            action.setEndCity(path[1]);
            this.game.sendAction(action);
        }

        return true;
    }

    /** rankCards()
     * This method ranks all of the cpu's cards in order of what to discard.
     * @return An array of the player ranks of the cpu's cards.
     */
    public int[] rankCards() {
        int[] ranks = new int[PandemicGameState.HAND_LIMIT + 1];
        int[] numColors = new int[Disease.NUM_DISEASES];

        // count how many cards of each color the cpu has
        for(int i = 0; i < PandemicGameState.HAND_LIMIT + 1; i++) {
            for(int j = 0; j < Disease.NUM_DISEASES; j++) {
                if(this.state.getPlayerHand()[this.playerNum][i].getColor() == j) {
                    numColors[j]++;
                }
            }
        }

        for(int i = 0; i < PandemicGameState.HAND_LIMIT + 1; i++) {
            // if the card is null, don't discard it because you can't discard a null card
            if(this.state.getPlayerHand()[this.playerNum][i].getName().equals("NULL")) {
                ranks[i] = -1;
                continue;
            }

            // if the disease is already cured, prioritize this discard.
            if(this.state.getDiseases()[this.state.getPlayerHand()[this.playerNum][i].getColor()].
                    getState() != Disease.UNCURED) {
                ranks[i]++;
            }

            // if there aren't many matching colors in the cpu's hand, prioritize this discard.
            if(numColors[this.state.getPlayerHand()[this.playerNum][i].getColor()] < 3) {
                ranks[i]++;
            }
        }

        return ranks;
    } // rankCards()

    /** determineMainPath()
     * This is a helper method that is given an array of target cities and finds the best and
     * easiest path.
     * @param cities An array of target cities.
     * @param depth The largest amount of distance to look for.
     * @return The best path.
     */
    public City[] determineMainPath(ArrayList<City> cities, int depth) {
        // find all paths to cities (in range)
        City[][] paths = new City[cities.size()][];
        for(int i = 0; i < cities.size(); i++) {
            paths[i] = findPath(cities.get(i), depth);
        }

        // find shortest path
        return findShortestPath(paths);
    }

    /*
     External Citation
     Date: 24 November 2020.
     Problem: Had trouble finding a path to a target city.
     Resource: Andrew Nuxoll.
     Solution: We met up with Professor Nuxoll to craft the following two methods.
     */

    /** findPath()
     * A helper method which finds a random shortest path to the target city.
     * @param targetCity The city that would be the final destination.
     * @return The array of cities on the way to the target city.
     */
    public City[] findPath(City targetCity, int maxDepth) {
        // create an array of cities that represent a path, and add the starting city
        ArrayList<City> path = new ArrayList<City>();
        path.add(this.state.getCurrCity()[this.playerNum]);

        ArrayList<City> result = null;
        // look for a path that meets the given requirements
        for(int i = 2; i <= maxDepth; i++) {
            result = findPathHelper(path, targetCity, 0, i);
            // a path was found, let's break out of this loop
            if(result != null) {
                break;
            }
        }

        // if no path was found
        if(result == null) {
            return null;
        }

        // convert the array list into an array of cities
        City[] newPath = new City[result.size()];
        for(int i = 0; i < newPath.length; i++) {
            newPath[i] = result.get(i);
        }
        return newPath;
    }

    /** findPathHelper()
     * A helper method that recursively calls itself searching for a path to a given target city,
     * until a path is found.
     * @param startPath The path that contains all of the cities we've visited thus far.
     * @param targetCity The city we are trying to get to.
     * @param currDepth The current distance of the path.
     * @param maxDepth The maximum distance to look for of this path.
     * @return An array of cities that represents the current path.
     */
    public ArrayList<City> findPathHelper(ArrayList<City> startPath, City targetCity, int currDepth,
                                          int maxDepth) {
        // if continuing forward would be greater than the maximum distance provided, exit
        if(currDepth > maxDepth) {
            return null;
        }
        currDepth++;

        ArrayList<City> returnVal = null;
        // find the latest city we have recently visited
        City start = startPath.get(startPath.size() - 1);

        // base case: if the latest city IS the target city, the path is complete
        if(start.getName().equals(targetCity.getName())) {
            return startPath;
        }

        // case: if a city connected to the latest city is the target city, add that to the path
        for(int i = 0; i < start.getConnections().length; i++) {
            City possibleNext = start.getConnections()[i];
            if(possibleNext.getName().equals(targetCity.getName())) {
                startPath.add(possibleNext);
                return startPath;
            }
        }

        // recursive case: if the connected city has not been visited yet, add that city to the path
        // and recursively call this method in order to continue searching through this path
        for(int i = 0; i < start.getConnections().length; i++) {
            City possibleNext = start.getConnections()[i];
            // if we have already visited this city
            if(startPath.contains(possibleNext)) {
               continue;
            }

            // create a deep copy of the array list
            ArrayList<City> clone = new ArrayList<City>(startPath);
            // and add the connected city
            clone.add(possibleNext);
            // recursive all
            ArrayList<City> result = findPathHelper(clone, targetCity, currDepth, maxDepth);

            // check to see if the new result is better than any other path we have found before
            if(result != null) {
                if (returnVal == null || returnVal.size() > result.size()) {
                    returnVal = result;
                }
            }
        }

        return returnVal;
    } // findPathHelper()

    /** findShortestPath()
     * Given an array of paths, this method will determine the shortest path.
     * @param paths An array of paths.
     * @return The path that is the shortest distance from the given array.
     */
    public City[] findShortestPath(City[][] paths) {
        if(paths == null) {
            return null;
        }

        City[] shortest = null;
        int size = MAX_DEPTH_CURE + 1; // no path will be larger than this
        for(int i = 0; i < paths.length; i++) {
            if(paths[i] != null) {
                // if current path is shorter than previously seen, make it the new shortest path
                if(paths[i].length < size) {
                    shortest = paths[i];
                    size = paths[i].length;
                }
            }
        }

        return shortest;
    } // findShortestPath()

    /** getPriorityCities()
     * Find all of the current cities on the board that are of a certain priority.
     * @param priority The priority level of the city in terms of how badly it needs to be treated.
     * @return An array of all of the cities of that priority level.
     */
    public ArrayList<City> getPriorityCities(int priority) {
        ArrayList<City> cities = new ArrayList<City>();

        for(int i = 0; i < Board.NUM_CITIES; i++) {
            if(this.state.getCities().getAllCities()[i].getCubes() == priority) {
                cities.add(this.state.getCities().getAllCities()[i]);
            }
        }

        return cities;
    } // getPriorityCities()
}
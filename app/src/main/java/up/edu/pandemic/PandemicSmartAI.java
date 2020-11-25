package up.edu.pandemic;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import up.edu.GameFramework.GameComputerPlayer;
import up.edu.GameFramework.infoMessage.GameInfo;

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

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PandemicSmartAI(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof PandemicGameState){
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
                        if(!this.tryToTreat(LOW_PRIORITY, MAX_DEPTH_LOW_PRIORITY)) {
                            ForgoAction action = new ForgoAction(this);
                            this.game.sendAction(action);
                        }
                    }
                }
            }
        }
    }

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
    }

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
    }

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

    public int[] rankCards() {
        int[] ranks = new int[PandemicGameState.HAND_LIMIT + 1];
        int[] numColors = new int[Disease.NUM_DISEASES];

        for(int i = 0; i < PandemicGameState.HAND_LIMIT + 1; i++) {
            for(int j = 0; j < Disease.NUM_DISEASES; j++) {
                if(this.state.getPlayerHand()[this.playerNum][i].getColor() == j) {
                    numColors[j]++;
                }
            }
        }

        for(int i = 0; i < PandemicGameState.HAND_LIMIT + 1; i++) {
            if(this.state.getPlayerHand()[this.playerNum][i].getName().equals("NULL")) {
                ranks[i] = -1;
                continue;
            }

            if(this.state.getDiseases()[this.state.getPlayerHand()[this.playerNum][i].getColor()].getState() != Disease.UNCURED) {
                ranks[i]++;
            }

            if(numColors[this.state.getPlayerHand()[this.playerNum][i].getColor()] < 3) {
                ranks[i]++;
            }
        }

        return ranks;
    }

    public City[] determineMainPath(ArrayList<City> cities, int depth) {
        // find all paths to cities (in range)
        City[][] paths = new City[cities.size()][];
        for(int i = 0; i < cities.size(); i++) {
            paths[i] = findPath(cities.get(i), depth);
        }

        // find shortest path
        return findShortestPath(paths);
    }

    /**
     External Citation
     Date: 24 November 2020
     Problem: Had trouble finding a path to a target city.
     Resource: Andrew Nuxoll
     Solution: We met up with Professor Nuxoll to craft the following two methods.
     */

    /** findPath()
     * A helper method which finds a random shortest path to the target city
     * @param targetCity the city that would be the final destination
     * @return the array of cities on the way to the target city
     */
    public City[] findPath(City targetCity, int maxDepth) {
        ArrayList<City> path = new ArrayList<City>();
        path.add(this.state.getCurrCity()[this.playerNum]);

        ArrayList<City> result = null;
        for(int i = 2; i <= maxDepth; i++) {
            result = findPathHelper(path, targetCity, 0, i);
            if(result != null) {
                break;
            }
        }
        if(result == null) {
            return null;
        }

        City[] newPath = new City[result.size()];
        for(int i = 0; i < newPath.length; i++) {
            newPath[i] = result.get(i);
        }
        return newPath;
    }


    public ArrayList<City> findPathHelper(ArrayList<City> startPath, City targetCity, int currDepth,
                                          int maxDepth) {
        if(currDepth == maxDepth) {
            return null;
        }
        currDepth++;

        ArrayList<City> returnVal = null;
        City start = startPath.get(startPath.size() - 1);

        if(start.getName().equals(targetCity.getName())) {
            return startPath;
        }

        for(int i = 0; i < start.getConnections().length; i++) {
            City possibleNext = start.getConnections()[i];
            if(possibleNext.getName().equals(targetCity.getName())) {
                startPath.add(possibleNext);
                return startPath;
            }
        }

        for(int i = 0; i < start.getConnections().length; i++) {
            City possibleNext = start.getConnections()[i];
            if(startPath.contains(possibleNext)) {
               continue;
            }

            ArrayList<City> clone = new ArrayList<City>(startPath);
            clone.add(possibleNext);
            ArrayList<City> result = findPathHelper(clone, targetCity, currDepth, maxDepth);

            if(result != null) {
                if (returnVal == null || returnVal.size() > result.size()) {
                    returnVal = result;
                }
            }
        }

        return returnVal;
    }

    public City[] findShortestPath(City[][] paths) {
        if(paths == null) {
            return null;
        }

        City[] shortest = null;
        int size = MAX_DEPTH_CURE + 1; // no path will be larger than this
        for(int i = 0; i < paths.length; i++) {
            if(paths[i] != null) {
                if (paths[i].length < size) {
                    shortest = paths[i];
                    size = paths[i].length;
                }
            }
        }

        return shortest;
    }

    public ArrayList<City> getPriorityCities(int priority) {
        ArrayList<City> cities = new ArrayList<City>();

        for(int i = 0; i < Board.NUM_CITIES; i++) {
            if(this.state.getCities().getAllCities()[i].getCubes() == priority) {
                cities.add(this.state.getCities().getAllCities()[i]);
            }
        }

        return cities;
    }
}
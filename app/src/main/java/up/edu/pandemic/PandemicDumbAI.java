package up.edu.pandemic;

import java.util.Random;

import up.edu.GameFramework.GameComputerPlayer;
import up.edu.GameFramework.infoMessage.GameInfo;

/** PandemicDumbAI
 * This is a computer player for Pandemic, which makes decisions randomly.
 * @author Nick Scott, Sarah Strong, Emily Vo.
 * @version 24 November 2020.
 */

public class PandemicDumbAI extends GameComputerPlayer {
    /** PandemicDumbAI
     * This is the constructor for the computer player.
     * @param name The player's name.
     */
    public PandemicDumbAI(String name) {
        super(name);
    } // PandemicDumbAI()

    /** receiveInfo()
     * This method receives the current game state and makes decisions on what to do for the turn.
     * @param info The current game info.
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof PandemicGameState) {
            if (((PandemicGameState) info).getCurrPlayer() != this.playerNum) {
                return;
            }

            Random rng = new Random();
            // wait one second in between actions
            this.sleep(1.2);

            // if the cpu needs to discard
            if (((PandemicGameState) info).needToDiscard()) {
                int choice = rng.nextInt(PandemicGameState.HAND_LIMIT + 1);
                if (!((PandemicGameState) info).getPlayerHand()[this.playerNum][choice].getName().
                        equals("NULL")) {
                    DiscardAction action = new DiscardAction(this,
                            ((PandemicGameState) info).getPlayerHand()[this.playerNum][choice].
                                    getName());
                    this.game.sendAction(action);
                }
            }

            // if there are no more actions to do
            if(((PandemicGameState) info).getActionsLeft() == 0) {
                EndTurnAction action = new EndTurnAction(this);
                this.game.sendAction(action);
            }

            else {
                // find the doable actions the cpu can do
                int[] doableActions = new int[PandemicGameState.NUM_TYPE_OF_ACTIONS - 1];
                int numChoices = 0;
                for (int i = 0; i < PandemicGameState.NUM_TYPE_OF_ACTIONS - 1; i++) {
                    if (((PandemicGameState) info).checkDoableActions(this.playerNum)[i]) {
                        doableActions[numChoices] = i;
                        numChoices++;
                    }
                }

                // and choose from the list of doable actions
                int choice = -1;
                if(numChoices > 0) {
                    choice = doableActions[rng.nextInt(numChoices)];
                }

                if (choice == PandemicGameState.DRIVE_FERRY) {
                    // randomly pick a connected city
                    int connect = rng.nextInt(((PandemicGameState) info).
                            getCurrCity()[this.playerNum].getConnections().length);

                    DriveFerryAction action = new DriveFerryAction(this);
                    action.setEndCity(((PandemicGameState) info).getCurrCity()[this.playerNum].
                            getConnections()[connect]);
                    this.game.sendAction(action);
                }

                else if (choice == PandemicGameState.DIRECT_FLIGHT) {
                    // pick a random card number
                    int card = rng.nextInt(PandemicGameState.HAND_LIMIT + 1);

                    DirectFlightAction action = new DirectFlightAction(this);
                    action.setEndCity(((PandemicGameState) info).getPlayerHand()[this.playerNum][card]);
                    this.game.sendAction(action);
                }

                else if (choice == PandemicGameState.CHARTER_FLIGHT) {
                    // pick a random city
                    int city = rng.nextInt(Board.NUM_CITIES);

                    CharterFlightAction action = new CharterFlightAction(this);
                    action.setEndCity(((PandemicGameState) info).getCities().getAllCities()[city]);
                    this.game.sendAction(action);
                }

                else if (choice == PandemicGameState.SHUTTLE_FLIGHT) {
                    // find all research stations on the board
                    City[] stations = new City[PandemicGameState.MAX_STATIONS];
                    int idx = 0;
                    for (int i = 0; i < Board.NUM_CITIES; i++) {
                        if (((PandemicGameState) info).getCities().getAllCities()[i].hasStation()) {
                            stations[idx] = ((PandemicGameState) info).getCities().getAllCities()[i];
                            idx++;
                        }
                    }
                    // pick a random city
                    int city = rng.nextInt(idx);

                    ShuttleFlightAction action = new ShuttleFlightAction(this);
                    action.setEndCity(stations[city]);
                    this.game.sendAction(action);
                }

                else if (choice == PandemicGameState.TREAT) {
                    TreatAction action = new TreatAction(this);
                    this.game.sendAction(action);
                }

                else if (choice == PandemicGameState.BUILD) {
                    BuildStationAction action = new BuildStationAction(this);
                    this.game.sendAction(action);
                }

                else if (choice == PandemicGameState.SHARE) {
                    ShareAction action = new ShareAction(this);
                    this.game.sendAction(action);
                }

                else if (choice == PandemicGameState.CURE) {
                    CureAction action = new CureAction(this);
                    this.game.sendAction(action);
                }

                else if (choice == PandemicGameState.PASS) {
                    ForgoAction action = new ForgoAction(this);
                    this.game.sendAction(action);
                }
            }
        }
    } // receiveInfo()
}

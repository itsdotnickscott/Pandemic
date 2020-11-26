package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

/** ShuttleFlightAction()
 * This is an action that represents a Shuttle Flight action.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
 */

public class ShuttleFlightAction extends GameAction {
    private City endCity;

    /** BuildStationAction()
     * This is the constructor for a Shuttle Flight action.
     * @param player The player who created the action.
     */
    public ShuttleFlightAction(GamePlayer player) {
        super(player);
    } // ShuttleFlightAction()

    // getters and setters
    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }

    public City getEndCity() {
        return endCity;
    }
}

package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

/** CharterFlightAction()
 * This is an action that represents a Charter Flight action.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
 */

public class CharterFlightAction extends GameAction {
    // instance variables
    private City endCity;

    /** CharterFlightAction()
     * This is the constructor for the a Charter Flight action.
     * @param player The player who created the action.
     */
    public CharterFlightAction(GamePlayer player) {
        super(player);
    }

    // setters and getters

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }

    public City getEndCity() {
        return endCity;
    }
}

package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

/** DirectFlightAction()
 * This is an action that represents a Direct Flight action.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
 */

public class DirectFlightAction extends GameAction {
    private City endCity;

    /** DirectFlightAction()
     * This is the constructor for a Direct Flight action.
     * @param player The player who created the action.
     */
    public DirectFlightAction(GamePlayer player) {
        super(player);
    } // DirectFlightAction()

    // setters and getters

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }

    public City getEndCity() {
        return endCity;
    }
}

package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

/** DriveFerryAction()
 * This is an action that represents a Drive/Ferry action.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
 */

public class DriveFerryAction extends GameAction {
    // instance variables
    private City endCity;

    /** DriveFerryAction()
     * This is the constructor for a Drive/Ferry action.
     * @param player The player who created the action.
     */
    public DriveFerryAction(GamePlayer player) {
        super(player);
    } // DriveFerryAction()

    // setters and getters

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }

    public City getEndCity() {
        return endCity;
    }
}

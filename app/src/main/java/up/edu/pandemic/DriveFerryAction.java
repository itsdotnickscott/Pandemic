package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

public class DriveFerryAction extends GameAction {
    // instance variables
    private City endCity;

    /** DriveFerryAction()
     * The constructor class for the Drive/Ferry action. Sets the game player.
     * @param player The player who created the action.
     */
    public DriveFerryAction(GamePlayer player) {
        super(player);
    } // DriveFerryAction()

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }
}

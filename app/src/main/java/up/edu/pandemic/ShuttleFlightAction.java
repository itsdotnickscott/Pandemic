package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

public class ShuttleFlightAction extends GameAction {
    private City endCity;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ShuttleFlightAction(GamePlayer player) {
        super(player);
    }

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }
}

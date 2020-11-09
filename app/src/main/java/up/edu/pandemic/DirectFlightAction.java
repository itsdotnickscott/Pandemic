package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

public class DirectFlightAction extends GameAction {
    private City endCity;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public DirectFlightAction(GamePlayer player) {
        super(player);
    }

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }
}

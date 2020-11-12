package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

public class DiscardAction extends GameAction {
    // instance variables
    private String cityName;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public DiscardAction(GamePlayer player, String cityName) {
        super(player);
        this.cityName = cityName;
    }

    public String getCityName() {
        return this.cityName;
    }
}

package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

public class BuildStationAction extends GameAction {
    // instance variables

    private int player;
    private City city;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public BuildStationAction(GamePlayer player) {
        super(player);
    }
}

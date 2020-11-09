package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

public class DiscardAction extends GameAction {
    // instance variables
    private int cardIdx;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public DiscardAction(GamePlayer player, int idx) {
        super(player);
        this.cardIdx = idx;
    }
}

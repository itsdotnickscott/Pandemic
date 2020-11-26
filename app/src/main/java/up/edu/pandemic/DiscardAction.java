package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

/** DiscardAction()
 * This is an action that represents a Discard Card action.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
 */

public class DiscardAction extends GameAction {
    // instance variables
    private String cityName;

    /** DiscardAction()
     * This is the constructor for a Discard Card action.
     * @param player The player who created the action.
     * @param cityName The card the player is trying to discard.
     */
    public DiscardAction(GamePlayer player, String cityName) {
        super(player);
        this.cityName = cityName;
    } // DiscardAction()

    // getter
    public String getCityName() {
        return this.cityName;
    }
}

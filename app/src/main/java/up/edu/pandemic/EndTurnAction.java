package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.actionMessage.GameAction;

/*
 External Citation
 Date: 20 October 2020.
 Problem: Didn't know how to handle "end of turn" actions.
 Resource: Andrew Nuxoll.
 Solution: After asking, Professor Nuxoll that we make "end turn" an action itself.
 */

/** EndTurnAction
 * This is an action that represents an End Turn action.
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
 */

public class EndTurnAction extends GameAction {
    /** EndTurnAction()
     * This is the constructor for an End Turn action.
     * @param player The player who created the action.
     */
    public EndTurnAction(GamePlayer player) {
        super(player);
    } // EndTurnAction()
}

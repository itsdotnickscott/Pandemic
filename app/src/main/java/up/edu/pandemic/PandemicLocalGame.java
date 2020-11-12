package up.edu.pandemic;

import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.LocalGame;
import up.edu.GameFramework.actionMessage.GameAction;

public class PandemicLocalGame extends LocalGame {
    private PandemicGameState gameState;

    public PandemicLocalGame(int num) {
        super();
        this.gameState = new PandemicGameState(num);
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        PandemicGameState copy = new PandemicGameState(this.gameState);
        p.sendInfo(copy);
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return this.gameState.getCurrPlayer() == playerIdx;
    }

    @Override
    protected String checkIfGameOver() {
        if(this.gameState.getGameCondition() == PandemicGameState.LOSE) {
            return "Game Over. You could not save the world.";
        }
        else if(this.gameState.getGameCondition() == PandemicGameState.WIN) {
            return "Game Over. You saved the world.";
        }
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        // based on the game action made, this calls the appropriate method in PandemicGameState
        if (action instanceof DriveFerryAction){
            return gameState.driveFerry(gameState.getCurrPlayer(), ((DriveFerryAction)action).getEndCity());
        } // drive/ferry
        else if (action instanceof ShuttleFlightAction){
            return gameState.shuttleFlight(gameState.getCurrPlayer(), ((ShuttleFlightAction)action).getEndCity());
        } // shuttle flight
        else if (action instanceof DirectFlightAction){
            return gameState.directFlight(gameState.getCurrPlayer(), ((DirectFlightAction)action).getEndCity());
        } // direct flight
        else if (action instanceof CharterFlightAction){
            return gameState.charterFlight(gameState.getCurrPlayer(), ((CharterFlightAction) action).getEndCity());
        } // charter flight
        else if (action instanceof ShareAction){
            return gameState.share(gameState.getCurrPlayer());
        } // share
        else if (action instanceof TreatAction){
            return gameState.treat(gameState.getCurrPlayer());
        } // treat
        else if (action instanceof BuildStationAction){
            return gameState.buildStation(gameState.getCurrPlayer());
        } //build
        else if (action instanceof CureAction){
            return gameState.cure(gameState.getCurrPlayer());
        } // cure
        else if (action instanceof ForgoAction){
            return gameState.forgoAction(gameState.getCurrPlayer());
        } // forgo action
        else if (action instanceof DiscardAction){
            return gameState.discard(gameState.getCurrPlayer(), ((DiscardAction) action).getCityName());
        } // discard
        else if (action instanceof EndTurnAction){
            return gameState.endTurn(gameState.getCurrPlayer());
        } // end turn

        return false;
    }
}

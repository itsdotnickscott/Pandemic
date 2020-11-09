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
        return false;
    }
}

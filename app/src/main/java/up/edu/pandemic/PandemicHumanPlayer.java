package up.edu.pandemic;

import android.view.View;

import up.edu.GameFramework.GameHumanPlayer;
import up.edu.GameFramework.GameMainActivity;
import up.edu.GameFramework.infoMessage.GameInfo;

public class PandemicHumanPlayer extends GameHumanPlayer implements View.OnClickListener {

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public PandemicHumanPlayer(String name) {
        super(name);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

    }
}

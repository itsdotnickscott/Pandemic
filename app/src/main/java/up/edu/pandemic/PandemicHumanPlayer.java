package up.edu.pandemic;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import up.edu.GameFramework.GameHumanPlayer;
import up.edu.GameFramework.GameMainActivity;
import up.edu.GameFramework.infoMessage.GameInfo;

public class PandemicHumanPlayer extends GameHumanPlayer implements View.OnClickListener,
View.OnTouchListener {
    // instance variables
    private Button driveButton = null;
    private Button directButton = null;
    private Button charterButton = null;
    private Button shuttleButton = null;
    private Button treatButton = null;
    private Button buildButton = null;
    private Button shareButton = null;
    private Button cureButton = null;
    private Button passButton = null;
    private Button endTurnButton = null;

    private Button card1 = null;
    private Button card2 = null;
    private Button card3 = null;
    private Button card4 = null;
    private Button card5 = null;
    private Button card6 = null;
    private Button card7 = null;
    private Button card8 = null;

    private TextView blueLeft = null;
    private TextView redLeft = null;
    private TextView blackLeft = null;
    private TextView yellowLeft = null;
    private TextView infoBar = null;
    private TextView infRate = null;
    private TextView turnsLeft = null;
    private TextView epiLeft = null;
    private TextView outbreakCounter = null;
    private TextView uncuredDiseases = null;
    private TextView curedDiseases = null;
    private TextView eradicatedDiseases = null;
    private SurfaceView mapView = null;

    private GameMainActivity myActivity;
    private Board cities;

    private DriveFerryAction driveFerry = null;
    private DirectFlightAction directFlight = null;
    private ShuttleFlightAction shuttleFlight = null;
    private CharterFlightAction charterFlight = null;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public PandemicHumanPlayer(String name) {
        super(name);
        this.cities = new Board();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.drivebutton) {
            this.charterFlight = null;
            this.directFlight = null;
            this.shuttleFlight = null;
            this.driveFerry = new DriveFerryAction(this);
        }
        else if(view.getId() == R.id.directbutton) {
            this.charterFlight = null;
            this.driveFerry = null;
            this.shuttleFlight = null;
            this.directFlight = new DirectFlightAction(this);
        }
        else if(view.getId() == R.id.charterbutton) {
            this.directFlight = null;
            this.driveFerry = null;
            this.shuttleFlight = null;
            this.charterFlight = new CharterFlightAction(this);
        }
        else if(view.getId() == R.id.shuttlebutton) {
            this.directFlight = null;
            this.driveFerry = null;
            this.charterFlight = null;
            this.shuttleFlight = new ShuttleFlightAction(this);
        }
        else if(view.getId() == R.id.treatbutton) {
            TreatAction action = new TreatAction(this);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.sharebutton) {
            ShareAction action = new ShareAction(this);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.buildbutton) {
            BuildStationAction action = new BuildStationAction(this);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.curebutton) {
            CureAction action = new CureAction(this);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.passbutton) {
            ForgoAction action = new ForgoAction(this);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.endturnbutton) {
            EndTurnAction action = new EndTurnAction(this);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card1) {
            DiscardAction action = new DiscardAction(this, 0);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card2) {
            DiscardAction action = new DiscardAction(this, 1);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card3) {
            DiscardAction action = new DiscardAction(this, 2);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card4) {
            DiscardAction action = new DiscardAction(this, 3);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card5) {
            DiscardAction action = new DiscardAction(this, 4);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card6) {
            DiscardAction action = new DiscardAction(this, 5);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card7) {
            DiscardAction action = new DiscardAction(this, 6);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card8) {
            DiscardAction action = new DiscardAction(this, 7);
            this.game.sendAction(action);
        }
    }

    @Override
    public View getTopView() {
        return this.myActivity.findViewById(R.id.mainlayout);
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if(info instanceof PandemicGameState) {
            blueLeft.setText("" + ((PandemicGameState) info).getDiseases()[Disease.BLUE].getCubesLeft());
            redLeft.setText("" + ((PandemicGameState) info).getDiseases()[Disease.RED].getCubesLeft());
            blackLeft.setText("" + ((PandemicGameState) info).getDiseases()[Disease.BLACK].getCubesLeft());
            yellowLeft.setText("" + ((PandemicGameState) info).getDiseases()[Disease.YELLOW].getCubesLeft());

            infRate.setText("Infection Rate: " + ((PandemicGameState) info).getInfRate());
            turnsLeft.setText("Turns Left: " + ((PandemicGameState) info).getPlayerDeck().getCardsLeft() / 2);
            epiLeft.setText("Epidemics Left: " + ((PandemicGameState) info).getEpiLeft());

            outbreakCounter.setText("Outbreaks: " + ((PandemicGameState) info).getOutbreaks());
            String curedString = "";
            String uncuredString = "";
            String eradicatedString = "";

            for(int i = 0; i < Disease.NUM_DISEASES; i++) {
                switch (((PandemicGameState) info).getDiseases()[i].getState()) {
                    case Disease.CURED:
                        curedString = curedString + "\n"+((PandemicGameState) info).getDiseases()[i].getName();
                        break;
                    case Disease.UNCURED:
                        uncuredString = uncuredString+"\n"+((PandemicGameState) info).getDiseases()[i].getName();
                        break;
                    case Disease.ERADICATED:
                        eradicatedString = eradicatedString+"\n"+((PandemicGameState) info).getDiseases()[i].getName();
                        break;
                }
            }

            uncuredDiseases.setText("UNCURED: \n" + uncuredString);
            curedDiseases.setText("CURED: \n" + curedString);
            eradicatedDiseases.setText("ERADICATED: \n" + eradicatedString);

        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        this.myActivity = activity;

        activity.setContentView(R.layout.pandemic_human_player);

        this.driveButton = activity.findViewById(R.id.drivebutton);
        this.directButton = activity.findViewById(R.id.directbutton);
        this.charterButton = activity.findViewById(R.id.charterbutton);
        this.shuttleButton = activity.findViewById(R.id.shuttlebutton);
        this.treatButton = activity.findViewById(R.id.treatbutton);
        this.buildButton = activity.findViewById(R.id.buildbutton);
        this.shareButton = activity.findViewById(R.id.sharebutton);
        this.cureButton = activity.findViewById(R.id.curebutton);
        this.passButton = activity.findViewById(R.id.passbutton);
        this.endTurnButton = activity.findViewById(R.id.endturnbutton);

        this.card1 = activity.findViewById(R.id.card1);
        this.card2 = activity.findViewById(R.id.card2);
        this.card3 = activity.findViewById(R.id.card3);
        this.card4 = activity.findViewById(R.id.card4);
        this.card5 = activity.findViewById(R.id.card5);
        this.card6 = activity.findViewById(R.id.card6);
        this.card7 = activity.findViewById(R.id.card7);
        this.card8 = activity.findViewById(R.id.card8);

        this.blueLeft = activity.findViewById(R.id.blueleft);
        this.redLeft = activity.findViewById(R.id.redleft);
        this.blackLeft = activity.findViewById(R.id.blackleft);
        this.yellowLeft = activity.findViewById(R.id.yellowleft);
        this.infoBar = activity.findViewById(R.id.infobar);
        this.infRate = activity.findViewById(R.id.infrate);
        this.turnsLeft = activity.findViewById(R.id.turnsleft);
        this.epiLeft = activity.findViewById(R.id.epileft);
        this.outbreakCounter = activity.findViewById(R.id.outbreakcounter);
        this.uncuredDiseases = activity.findViewById(R.id.uncureddiseases);
        this.curedDiseases = activity.findViewById(R.id.cureddiseases);
        this.eradicatedDiseases = activity.findViewById(R.id.eradicateddiseases);
        this.mapView = activity.findViewById(R.id.mapview);

        this.driveButton.setOnClickListener(this);
        this.directButton.setOnClickListener(this);
        this.charterButton.setOnClickListener(this);
        this.shuttleButton.setOnClickListener(this);
        this.treatButton.setOnClickListener(this);
        this.buildButton.setOnClickListener(this);
        this.shareButton.setOnClickListener(this);
        this.cureButton.setOnClickListener(this);
        this.passButton.setOnClickListener(this);
        this.endTurnButton.setOnClickListener(this);

        this.card1.setOnClickListener(this);
        this.card2.setOnClickListener(this);
        this.card3.setOnClickListener(this);
        this.card4.setOnClickListener(this);
        this.card5.setOnClickListener(this);
        this.card6.setOnClickListener(this);
        this.card7.setOnClickListener(this);
        this.card8.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(this.driveFerry != null) {
            this.driveFerry.setEndCity(this.determineCity(x, y));
            this.game.sendAction(this.driveFerry);
            this.driveFerry = null;
            return true;
        }
        else if(this.directFlight != null) {
            this.directFlight.setEndCity(this.determineCity(x, y));
            this.game.sendAction(this.directFlight);
            this.directFlight = null;
            return true;
        }
        else if(this.charterFlight != null) {
            this.charterFlight.setEndCity(this.determineCity(x, y));
            this.game.sendAction(this.charterFlight);
            this.charterFlight = null;
            return true;
        }
        else if(this.shuttleFlight != null) {
            this.shuttleFlight.setEndCity(this.determineCity(x, y));
            this.game.sendAction(this.shuttleFlight);
            this.shuttleFlight = null;
            return true;
        }

        return false;
    }

    public City determineCity(float x, float y) {
        for(int i = 0; i < this.cities.getAllCities().length; i++) {
            if(x >= this.cities.getAllCities()[i].getLocation()[0][0] &&
               y >= this.cities.getAllCities()[i].getLocation()[0][1] &&
               x <= this.cities.getAllCities()[i].getLocation()[1][0] &&
               y <= this.cities.getAllCities()[i].getLocation()[1][1]) {
                return this.cities.getAllCities()[i];
            }
        }

        return null;
    }
}

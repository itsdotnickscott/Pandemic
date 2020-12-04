package up.edu.pandemic;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import up.edu.GameFramework.GameHumanPlayer;
import up.edu.GameFramework.GameMainActivity;
import up.edu.GameFramework.infoMessage.GameInfo;

/** PandemicHumanPlayer()
 * This class represents the human player for Pandemic. All of the GUI elements the player interacts
 * with are shown here.
 * @author Nick Scott, Sarah Strong, Emily Vo.
 * @version 24 November 2020.
 */

public class PandemicHumanPlayer extends GameHumanPlayer implements View.OnClickListener,
View.OnTouchListener {
    // instance variables
    public static final int PINK = 0;
    public static final int ORANGE = 1;
    public static final int BLUE = 2;
    public static final int GREEN = 3;

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

    private PandemicGUIView guiView = null;
    private TextView playerCards = null;

    private GameMainActivity myActivity;
    private Board cities;

    private DriveFerryAction driveFerry = null;
    private DirectFlightAction directFlight = null;
    private ShuttleFlightAction shuttleFlight = null;
    private CharterFlightAction charterFlight = null;

    /** PandemicHumanPlayer()
     * This is the human player constructor.
     * @param name the name of the player
     */
    public PandemicHumanPlayer(String name) {
        super(name);
        this.cities = new Board();
    } // PandemicHumanPlayer()

    /** onClick()
     * This method is called whenever a button is pressed by the player, which corresponds to an
     * action.
     * @param view The view on which the button is pressed.
     */
    @Override
    public void onClick(View view) {
        // reset incomplete actions
        this.driveFerry = null;
        this.charterFlight = null;
        this.directFlight = null;
        this.shuttleFlight = null;

        /*
         External Citation
         Date: 1 November 2020.
         Problem: Had trouble getting the Drive/Ferry action to handle two different button presses.
         Resource: Andrew Nuxoll.
         Solution: Professor Nuxoll recommended we create a "partially complete" action, which
         creates an action, stores the incomplete action, and completes it later.
         */

        // send an action based off of the button pressed
        if(view.getId() == R.id.drivebutton) {
            this.driveFerry = new DriveFerryAction(this);
        }
        else if(view.getId() == R.id.directbutton) {
            this.directFlight = new DirectFlightAction(this);
        }
        else if(view.getId() == R.id.charterbutton) {
            this.charterFlight = new CharterFlightAction(this);
        }
        else if(view.getId() == R.id.shuttlebutton) {
            this.shuttleFlight = new ShuttleFlightAction(this);
        }
        else if(view.getId() == R.id.treatbutton) {
            TreatAction action = new TreatAction(this);
            this.game.sendAction(action);
            this.guiView.invalidate();
        }
        else if(view.getId() == R.id.sharebutton) {
            ShareAction action = new ShareAction(this);
            this.game.sendAction(action);
            this.guiView.invalidate();
        }
        else if(view.getId() == R.id.buildbutton) {
            BuildStationAction action = new BuildStationAction(this);
            this.game.sendAction(action);
            this.guiView.invalidate();
        }
        else if(view.getId() == R.id.curebutton) {
            CureAction action = new CureAction(this);
            this.game.sendAction(action);
            this.guiView.invalidate();
        }
        else if(view.getId() == R.id.passbutton) {
            ForgoAction action = new ForgoAction(this);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.endturnbutton) {
            EndTurnAction action = new EndTurnAction(this);
            this.game.sendAction(action);
        }
        else if(view.getId() == R.id.card1 || view.getId() == R.id.card2 ||
                view.getId() == R.id.card3 || view.getId() == R.id.card4 ||
                view.getId() == R.id.card5 || view.getId() == R.id.card6 ||
                view.getId() == R.id.card7 || view.getId() == R.id.card8) {
            Button button = (Button) view;
            DiscardAction action = new DiscardAction(this, button.getText().toString());
            this.game.sendAction(action);
        }
    } // onClick()

    /** getTopView()
     * @return The main LinearLayout that contains all of the GUI.
     */
    @Override
    public View getTopView() {
        return this.myActivity.findViewById(R.id.mainlayout);
    } // getTopView()

    /** receiveInfo()
     * This method receives the current game state and displays that information to the player.
     * @param info The current game info.
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if(info instanceof PandemicGameState) {
            //update the info bar based off of how many actions are left.
            if(((PandemicGameState) info).getActionsLeft() == 4) {
                ((PandemicGameState) info).setInfoBar("PLAYER " +
                        (((PandemicGameState) info).getCurrPlayer() + 1) +
                        "'S TURN");
            }
            else if(((PandemicGameState) info).getActionsLeft() == 0 &&
                    ((PandemicGameState) info).getCurrPlayer() == this.playerNum) {
                ((PandemicGameState) info).setInfoBar("PLAYER " +
                        (((PandemicGameState) info).getCurrPlayer() + 1) +
                        " - PLEASE END TURN");
            }

            // update the info bar if the player needs to discard
            if(((PandemicGameState) info).needToDiscard()) {
                ((PandemicGameState) info).setInfoBar("PLAYER " +
                        (((PandemicGameState) info).getCurrPlayer() + 1) +
                        " - PLEASE DISCARD A CARD");
            }

            // update all of the current player cards
            String allCards = "";

            for(int i = 0; i < ((PandemicGameState) info).getNumPlayers(); i++) {
                allCards += "PLAYER " + (i + 1) + "\n";
                for (int j = 0; j < (PandemicGameState.HAND_LIMIT + 1); j++) {
                    if(!((PandemicGameState) info).getPlayerHand()[i][j].getName().equals("NULL")) {
                        allCards += ((PandemicGameState) info).getPlayerHand()[i][j].getName() + "\n";
                    }
                }
                allCards += "\n";
            }

            this.playerCards.setText(allCards);

            // update hand
            this.updateCard(0, info, this.card1);
            this.updateCard(1, info, this.card2);
            this.updateCard(2, info, this.card3);
            this.updateCard(3, info, this.card4);
            this.updateCard(4, info, this.card5);
            this.updateCard(5, info, this.card6);
            this.updateCard(6, info, this.card7);
            this.updateCard(7, info, this.card8);

            // update variables for the map view
            this.guiView.setState(((PandemicGameState) info));
            this.guiView.invalidate();
        }
    } // receiveInfo()

    /** updateCard()
     * This method is given a card and will update its text and color based on the city it
     * represents.
     * @param num The index of the card.
     * @param info The current game info.
     * @param card The button of which to edit.
     */
    public void updateCard(int num, GameInfo info, Button card) {
        card.setVisibility(View.VISIBLE);
        if(((PandemicGameState) info).getPlayerHand()[((PandemicGameState) info).
                getCurrPlayer()][num].getName().equals("NULL")) {
            card.setVisibility(View.GONE);
            return;
        }
        card.setText(((PandemicGameState) info).getPlayerHand()[((PandemicGameState) info).
                getCurrPlayer()][num].getName());

        switch(((PandemicGameState) info).getPlayerHand()[((PandemicGameState) info).
                getCurrPlayer()][num].getColor()){
            case Disease.BLUE:
                card.setBackgroundColor(Color.BLUE);
                break;
            case Disease.BLACK:
                card.setBackgroundColor(Color.GRAY);
                break;
            case Disease.RED:
                card.setBackgroundColor(Color.RED);
                break;
            case Disease.YELLOW:
                card.setBackgroundColor(Color.YELLOW);
                break;
        }
    } // updateCard()

    /** setAsGui()
     * This method sets all of the instance variables to the various GUI elements.
     * @param activity The activity which contains all of these GUI elements.
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        this.myActivity = activity;

        activity.setContentView(R.layout.pandemic_human_player);

        // GUI elements
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

        this.guiView = activity.findViewById(R.id.guiview);
        this.playerCards = activity.findViewById(R.id.allcards);

        // set up listeners
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

        this.guiView.setOnTouchListener(this);
    } // setAsGui()

    /** onTouch()
     * This method is called whenever the user touches on the PandemicGUIView. If it corresponds to
     * a city, it makes an action based on it.
     * @param v The view that was touched.
     * @param event Information about the touch.
     * @return Whether the touch did anything in particular.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // get the coordinate of the touch
        float x = event.getX();
        float y = event.getY();

        // if touch does not correspond to a city
        if(this.determineCity(x, y) == null) {
            return false;
        }

        // unit test
        // infoBar.setText(this.determineCity(x, y).getName().toUpperCase() + " CHOSEN");

        // check if a move action was initialized. if so, complete that action.
        if(this.driveFerry != null) {
            this.driveFerry.setEndCity(this.determineCity(x, y));
            this.game.sendAction(this.driveFerry);
            this.driveFerry = null;
            this.guiView.invalidate();
            return true;
        }
        else if(this.directFlight != null) {
            this.directFlight.setEndCity(this.determineCity(x, y));
            this.game.sendAction(this.directFlight);
            this.directFlight = null;
            this.guiView.invalidate();
            return true;
        }
        else if(this.charterFlight != null) {
            this.charterFlight.setEndCity(this.determineCity(x, y));
            this.game.sendAction(this.charterFlight);
            this.charterFlight = null;
            this.guiView.invalidate();
            return true;
        }
        else if(this.shuttleFlight != null) {
            this.shuttleFlight.setEndCity(this.determineCity(x, y));
            this.game.sendAction(this.shuttleFlight);
            this.shuttleFlight = null;
            this.guiView.invalidate();
            return true;
        }

        return false;
    } // onTouch()

    /** determineCity()
     * This method is called whenever the PandemicGUIView is touched. It determines if the user
     * touched within a hitbox of a city.
     * @param x The x-coordinate of the touch.
     * @param y The y-coordinate of the touch.
     * @return The city, if any, that was touched.
     */
    public City determineCity(float x, float y) {
        // go through each city and see if the coordinate falls within the city's parameters
        for(int i = 0; i < this.cities.getAllCities().length; i++) {
            if(x >= this.cities.getAllCities()[i].getLocation()[0][0] &&
               y >= this.cities.getAllCities()[i].getLocation()[0][1] &&
               x <= this.cities.getAllCities()[i].getLocation()[1][0] &&
               y <= this.cities.getAllCities()[i].getLocation()[1][1]) {
                return this.cities.getAllCities()[i];
            }
        }

        return null;
    } // determineCity()
}

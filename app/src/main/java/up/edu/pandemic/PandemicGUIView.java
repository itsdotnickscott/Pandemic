package up.edu.pandemic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/** PandemicGUIView
 * This object is the main graphic representation used to display information for the human player.
 * @author Nick Scott, Sarah Strong, Emily Vo.
 * @version 24 November 2020.
 */

public class PandemicGUIView extends SurfaceView {
    // instance variables
    private Bitmap cityNames;
    private Bitmap gui;

    private Bitmap black1;
    private Bitmap black2;
    private Bitmap black3;
    private Bitmap blue1;
    private Bitmap blue2;
    private Bitmap blue3;
    private Bitmap red1;
    private Bitmap red2;
    private Bitmap red3;
    private Bitmap yellow1;
    private Bitmap yellow2;
    private Bitmap yellow3;

    private Bitmap bluePawn;
    private Bitmap pinkPawn;
    private Bitmap greenPawn;
    private Bitmap orangePawn;

    private Bitmap researchStation;

    private Bitmap uncuredBlue;
    private Bitmap curedBlue;
    private Bitmap eradBlue;
    private Bitmap uncuredYellow;
    private Bitmap curedYellow;
    private Bitmap eradYellow;
    private Bitmap uncuredBlack;
    private Bitmap curedBlack;
    private Bitmap eradBlack;
    private Bitmap uncuredRed;
    private Bitmap curedRed;
    private Bitmap eradRed;

    private Bitmap outbreak0;
    private Bitmap outbreak1;
    private Bitmap outbreak2;
    private Bitmap outbreak3;
    private Bitmap outbreak4;
    private Bitmap outbreak5;
    private Bitmap outbreak6;
    private Bitmap outbreak7;

    private Bitmap bigPink;
    private Bitmap bigOrange;
    private Bitmap bigBlue;
    private Bitmap bigGreen;

    private Bitmap actions0;
    private Bitmap actions1;
    private Bitmap actions2;
    private Bitmap actions3;
    private Bitmap actions4;

    private Paint textBold;

    private PandemicGameState state;

    /** PandemicGUIView()
     * This method initializes all of the Bitmaps that will be used to print onto the screen.
     * @param context The context of the resources.
     * @param attributes The attributes of the resources.
     */
    public PandemicGUIView(Context context, AttributeSet attributes) {
        super(context, attributes);
        setWillNotDraw(false);

        // load image files
        gui = BitmapFactory.decodeResource(getResources(), R.drawable.pandemicgui);
        cityNames = BitmapFactory.decodeResource(getResources(), R.drawable.citynames);

        black1 = BitmapFactory.decodeResource(getResources(), R.drawable.black1);
        black2 = BitmapFactory.decodeResource(getResources(), R.drawable.black2);
        black3 = BitmapFactory.decodeResource(getResources(), R.drawable.black3);
        blue1 = BitmapFactory.decodeResource(getResources(), R.drawable.blue1);
        blue2 = BitmapFactory.decodeResource(getResources(), R.drawable.blue2);
        blue3 = BitmapFactory.decodeResource(getResources(), R.drawable.blue3);
        red1 = BitmapFactory.decodeResource(getResources(), R.drawable.red1);
        red2 = BitmapFactory.decodeResource(getResources(), R.drawable.red2);
        red3 = BitmapFactory.decodeResource(getResources(), R.drawable.red3);
        yellow1 = BitmapFactory.decodeResource(getResources(), R.drawable.yellow1);
        yellow2 = BitmapFactory.decodeResource(getResources(), R.drawable.yellow2);
        yellow3 = BitmapFactory.decodeResource(getResources(), R.drawable.yellow3);

        pinkPawn = BitmapFactory.decodeResource(getResources(), R.drawable.pinkpawn);
        orangePawn = BitmapFactory.decodeResource(getResources(), R.drawable.orangepawn);
        bluePawn = BitmapFactory.decodeResource(getResources(), R.drawable.bluepawn);
        greenPawn = BitmapFactory.decodeResource(getResources(), R.drawable.greenpawn);

        researchStation = BitmapFactory.decodeResource(getResources(), R.drawable.station);

        uncuredBlue = BitmapFactory.decodeResource(getResources(), R.drawable.uncuredblue);
        curedBlue = BitmapFactory.decodeResource(getResources(), R.drawable.curedblue);
        eradBlue = BitmapFactory.decodeResource(getResources(), R.drawable.eradicatedblue);
        uncuredYellow = BitmapFactory.decodeResource(getResources(), R.drawable.uncuredyellow);
        curedYellow = BitmapFactory.decodeResource(getResources(), R.drawable.curedyellow);
        eradYellow = BitmapFactory.decodeResource(getResources(), R.drawable.eradicatedyellow);
        uncuredBlack = BitmapFactory.decodeResource(getResources(), R.drawable.uncuredblack);
        curedBlack = BitmapFactory.decodeResource(getResources(), R.drawable.curedblack);
        eradBlack = BitmapFactory.decodeResource(getResources(), R.drawable.eradicatedblack);
        uncuredRed = BitmapFactory.decodeResource(getResources(), R.drawable.uncuredred);
        curedRed = BitmapFactory.decodeResource(getResources(), R.drawable.curedred);
        eradRed = BitmapFactory.decodeResource(getResources(), R.drawable.eradicatedred);

        outbreak0 = BitmapFactory.decodeResource(getResources(), R.drawable.outbreak0);
        outbreak1 = BitmapFactory.decodeResource(getResources(), R.drawable.outbreak1);
        outbreak2 = BitmapFactory.decodeResource(getResources(), R.drawable.outbreak2);
        outbreak3 = BitmapFactory.decodeResource(getResources(), R.drawable.outbreak3);
        outbreak4 = BitmapFactory.decodeResource(getResources(), R.drawable.outbreak4);
        outbreak5 = BitmapFactory.decodeResource(getResources(), R.drawable.outbreak5);
        outbreak6 = BitmapFactory.decodeResource(getResources(), R.drawable.outbreak6);
        outbreak7 = BitmapFactory.decodeResource(getResources(), R.drawable.outbreak7);

        bigPink = BitmapFactory.decodeResource(getResources(), R.drawable.bigpink);
        bigOrange = BitmapFactory.decodeResource(getResources(), R.drawable.bigorange);
        bigBlue = BitmapFactory.decodeResource(getResources(), R.drawable.bigblue);
        bigGreen = BitmapFactory.decodeResource(getResources(), R.drawable.biggreen);

        actions0 = BitmapFactory.decodeResource(getResources(), R.drawable.actions0);
        actions1 = BitmapFactory.decodeResource(getResources(), R.drawable.actions1);
        actions2 = BitmapFactory.decodeResource(getResources(), R.drawable.actions2);
        actions3 = BitmapFactory.decodeResource(getResources(), R.drawable.actions3);
        actions4 = BitmapFactory.decodeResource(getResources(), R.drawable.actions4);

        textBold = new Paint();
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "font/lgsb.ttf");
        textBold.setTypeface(bold);
    } // PandemicGUIView()

    /** onDraw()
     * This method is called whenever information has to be printed onto the screen for the player.
     * @param canvas The canvas of which to draw on.
     */
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.gui, 0.0f, 0.0f, null);

        if(this.state != null) {
            this.updateCures(canvas);
            this.updateOutbreaks(canvas);
            this.updateCurrPlayer(canvas);
            this.updateCities(canvas);
            this.updateInfo(canvas);
            this.updateActions(canvas);
            this.updateInfoBar(canvas);
        }

        canvas.drawBitmap(this.cityNames, 0.0f, 0.0f, null);
    } // onDraw()

    /** updateInfoBar()
     * This method updates the info bars at the top and the bottom screen, which show actions and
     * what cities have been infected.
     * @param canvas The canvas of which to draw on.
     */
    public void updateInfoBar(Canvas canvas) {
        textBold.setColor(Color.WHITE);
        textBold.setTextSize(45.0f);

        float startLeft = 530.0f;
        float startHeight = 65.0f;
        canvas.drawText(this.state.getInfoBar(), startLeft, startHeight, this.textBold);

        textBold.setTextSize(32.0f);

        startLeft = 750.0f;
        startHeight = 868.5f;
        float addBy = 24.0f;
        canvas.drawText(this.state.getInfoBarEpidemic(), startLeft, startHeight, this.textBold);
        canvas.drawText(this.state.getInfoBarInfected(), startLeft, startHeight + addBy,
                this.textBold);
        canvas.drawText(this.state.getInfoBarOutbroke(), startLeft, startHeight + addBy * 2,
                this.textBold);
    } // updateInfoBar()

    /** updateActions()
     * This method updates the actions left for the player.
     * @param canvas The canvas of which to draw on.
     */
    public void updateActions(Canvas canvas) {
        float startLeft = 177.75f;
        float startHeight = 712.0f;

        switch(state.getActionsLeft()) {
            case 0: canvas.drawBitmap(this.actions0, startLeft, startHeight, null); break;
            case 1: canvas.drawBitmap(this.actions1, startLeft, startHeight, null); break;
            case 2: canvas.drawBitmap(this.actions2, startLeft, startHeight, null); break;
            case 3: canvas.drawBitmap(this.actions3, startLeft, startHeight, null); break;
            case 4: canvas.drawBitmap(this.actions4, startLeft, startHeight, null); break;
        }
    } // updateActions()

    /** updateInfo()
     * This method updates the number of cubes left, turns left, epidemics left, and infection rate.
     * @param canvas The canvas of which to draw on.
     */
    public void updateInfo(Canvas canvas) {
        float startLeft = 200.0f;
        float centerCoord = 75.0f;
        float addBy = 71.0f;

        // update each disease's cubes left
        textBold.setColor(Color.WHITE);
        textBold.setTextSize(56.0f);
        for(int i = 0; i < Disease.NUM_DISEASES; i++) {
            canvas.drawText("" + this.state.getDiseases()[i].getCubesLeft(),
                    startLeft + (addBy * i), centerCoord, this.textBold);
        }

        startLeft = 1342.0f;
        centerCoord = 80.0f;
        addBy = 155.0f;

        // infection rate and epidemics left
        textBold.setColor(Color.BLACK);
        canvas.drawText("" + this.state.getInfRate(),
                startLeft, centerCoord, this.textBold);
        canvas.drawText("" + this.state.getEpiLeft(),
                startLeft + (addBy), centerCoord, this.textBold);

        startLeft = 1643.0f;

        // turns left, which is player cards left divided by 2
        canvas.drawText("" + (this.state.getPlayerDeck().getCardsLeft() / 2),
                startLeft, centerCoord, this.textBold);
    } // updateInfo()

    /** updateCities()
     * This method updates everything on the map including research stations, player pawns, and
     * disease cubes.
     * @param canvas The canvas of which to draw on.
     */
    public void updateCities(Canvas canvas) {
        for(int i = 0; i < Board.NUM_CITIES; i++) {
            // get the top left corner of the hitbox of the city
            float x = this.state.getCities().getAllCities()[i].getLocation()[0][0];
            float y = this.state.getCities().getAllCities()[i].getLocation()[0][1];

            // if there is a research station
            if(this.state.getCities().getAllCities()[i].hasStation()) {
                canvas.drawBitmap(this.researchStation, x, y, null);
            }

            // print cubes based on how many are there
            switch(this.state.getCities().getAllCities()[i].getColor()) {
                case Disease.BLUE:
                    switch (this.state.getCities().getAllCities()[i].getCubes()) {
                        case 1:
                            canvas.drawBitmap(this.blue1, x, y, null);
                            break;
                        case 2:
                            canvas.drawBitmap(this.blue2, x, y, null);
                            break;
                        case 3:
                            canvas.drawBitmap(this.blue3, x, y, null);
                            break;
                    }
                    break;
                case Disease.YELLOW:
                    switch(this.state.getCities().getAllCities()[i].getCubes()) {
                        case 1:
                            canvas.drawBitmap(this.yellow1, x, y, null);
                            break;
                        case 2:
                            canvas.drawBitmap(this.yellow2, x, y, null);
                            break;
                        case 3:
                            canvas.drawBitmap(this.yellow3, x, y, null);
                            break;
                    }
                    break;
                case Disease.BLACK:
                    switch(this.state.getCities().getAllCities()[i].getCubes()) {
                        case 1:
                            canvas.drawBitmap(this.black1, x, y, null);
                            break;
                        case 2:
                            canvas.drawBitmap(this.black2, x, y, null);
                            break;
                        case 3:
                            canvas.drawBitmap(this.black3, x, y, null);
                            break;
                    }
                    break;
                case Disease.RED:
                    switch(this.state.getCities().getAllCities()[i].getCubes()) {
                        case 1:
                            canvas.drawBitmap(this.red1, x, y, null);
                            break;
                        case 2:
                            canvas.drawBitmap(this.red2, x, y, null);
                            break;
                        case 3:
                            canvas.drawBitmap(this.red3, x, y, null);
                            break;
                    }
                    break;
            }

            // print player pawns, if anyone is at that city
            for(int j = 0; j < this.state.getCurrCity().length; j++) {
                if(this.state.getCurrCity()[j].getName().equals(this.state.getCities().
                        getAllCities()[i].getName())) {
                    switch(j) {
                        case PandemicHumanPlayer.PINK:
                            canvas.drawBitmap(this.pinkPawn, x, y, null);
                            break;
                        case PandemicHumanPlayer.ORANGE:
                            canvas.drawBitmap(this.orangePawn, x, y, null);
                            break;
                        case PandemicHumanPlayer.BLUE:
                            canvas.drawBitmap(this.bluePawn, x, y, null);
                            break;
                        case PandemicHumanPlayer.GREEN:
                            canvas.drawBitmap(this.greenPawn, x, y, null);
                            break;
                    }
                }
            }
        }
    } // updateCities()

    /** updateCurrPlayer()
     * This method updates the current player on the GUI.
     * @param canvas The canvas of which to draw on.
     */
    public void updateCurrPlayer(Canvas canvas) {
        float centerCoord = 160.0f;
        float height = 825.0f;

        switch(this.state.getCurrPlayer()) {
            case PandemicHumanPlayer.PINK: canvas.drawBitmap(this.bigPink, centerCoord, height,
                    null); break;
            case PandemicHumanPlayer.ORANGE: canvas.drawBitmap(this.bigOrange, centerCoord, height,
                    null); break;
            case PandemicHumanPlayer.BLUE: canvas.drawBitmap(this.bigBlue, centerCoord, height,
                    null); break;
            case PandemicHumanPlayer.GREEN: canvas.drawBitmap(this.bigGreen, centerCoord, height,
                    null); break;
        }
    } // updateCurrPlayer()

    /** updateOutbreaks()
     * This method updates the number of outbreaks that have already occurred.
     * @param canvas The canvas of which to draw on.
     */
    public void updateOutbreaks(Canvas canvas) {
        switch(this.state.getOutbreaks()) {
            case 0: canvas.drawBitmap(this.outbreak0, 0.0f, 0.0f, null); break;
            case 1: canvas.drawBitmap(this.outbreak1, 0.0f, 0.0f, null); break;
            case 2: canvas.drawBitmap(this.outbreak2, 0.0f, 0.0f, null); break;
            case 3: canvas.drawBitmap(this.outbreak3, 0.0f, 0.0f, null); break;
            case 4: canvas.drawBitmap(this.outbreak4, 0.0f, 0.0f, null); break;
            case 5: canvas.drawBitmap(this.outbreak5, 0.0f, 0.0f, null); break;
            case 6: canvas.drawBitmap(this.outbreak6, 0.0f, 0.0f, null); break;
            case 7: canvas.drawBitmap(this.outbreak7, 0.0f, 0.0f, null); break;
        }
    } // updateOutbreaks()

    /** updateCures()
     * This method updates the states of each of the diseases.
     * @param canvas The canvas of which to draw on.
     */
    public void updateCures(Canvas canvas) {
        float centerCoord = 49.0f;
        float startHeight = 530.0f;
        float addBy = 80.0f;

        // based on each disease, update whether it is cured, uncured, or eradicated.
        for (int i = 0; i < Disease.NUM_DISEASES; i++) {
            switch (i) {
                case Disease.BLUE:
                    switch (this.state.getDiseases()[i].getState()) {
                        case Disease.CURED:
                            canvas.drawBitmap(curedBlue, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                        case Disease.UNCURED:
                            canvas.drawBitmap(uncuredBlue, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                        case Disease.ERADICATED:
                            canvas.drawBitmap(eradBlue, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                    }
                    break;
                case Disease.YELLOW:
                    switch (this.state.getDiseases()[i].getState()) {
                        case Disease.CURED:
                            canvas.drawBitmap(curedYellow, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                        case Disease.UNCURED:
                            canvas.drawBitmap(uncuredYellow, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                        case Disease.ERADICATED:
                            canvas.drawBitmap(eradYellow, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                    }
                    break;
                case Disease.BLACK:
                    switch (this.state.getDiseases()[i].getState()) {
                        case Disease.CURED:
                            canvas.drawBitmap(curedBlack, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                        case Disease.UNCURED:
                            canvas.drawBitmap(uncuredBlack, centerCoord,startHeight +
                                    (addBy * i), null);
                            break;
                        case Disease.ERADICATED:
                            canvas.drawBitmap(eradBlack, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                    }
                    break;
                case Disease.RED:
                    switch (this.state.getDiseases()[i].getState()) {
                        case Disease.CURED:
                            canvas.drawBitmap(curedRed, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                        case Disease.UNCURED:
                            canvas.drawBitmap(uncuredRed, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                        case Disease.ERADICATED:
                            canvas.drawBitmap(eradRed, centerCoord, startHeight +
                                    (addBy * i), null);
                            break;
                    }
                    break;
            }
        }
    } // updateCures()

    /*
     External Citation
     Date: 9 November 2020.
     Problem: Had trouble getting the map to update based on the state.
     Resource: Andrew Nuxoll.
     Solution: Nuxoll recommended we pass in a reference into PandemicMapView (now PandemicGUIView)
     variable.
     */

    // setter
    public void setState(PandemicGameState ref) {
        this.state = ref;
    }
}
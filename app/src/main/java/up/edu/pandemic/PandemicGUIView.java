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

    private PandemicGameState state;
    private Paint textNormal;
    private Paint textBold;

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

        textNormal = new Paint();
        textNormal.setColor(Color.WHITE);
        textNormal.setTextSize(72.0f);
        textBold = new Paint();
        textBold.setTextSize(56.0f);

        Typeface normal = Typeface.createFromAsset(context.getAssets(), "font/lgs.ttf");
        textNormal.setTypeface(normal);
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "font/lgsb.ttf");
        textBold.setTypeface(bold);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(this.gui, 0.0f, 0.0f, null);

        if(this.state != null) {
            this.updateCures(canvas);
            this.updateOutbreaks(canvas);
            this.updateCurrPlayer(canvas);
            this.updateCities(canvas);
            this.updateInfo(canvas);
        }

        canvas.drawBitmap(this.cityNames, 0.0f, 0.0f, null);
    }

    public void updateInfo(Canvas canvas) {
        float startLeft = 200.0f;
        float centerCoord = 75.0f;
        float addBy = 71.0f;

        textBold.setColor(Color.WHITE);
        for(int i = 0; i < Disease.NUM_DISEASES; i++) {
            canvas.drawText("" + this.state.getDiseases()[i].getCubesLeft(),
                    startLeft + (addBy * i), centerCoord, this.textBold);
        }

        startLeft = 1342.0f;
        centerCoord = 80.0f;
        addBy = 155.0f;

        textBold.setColor(Color.BLACK);
        canvas.drawText("" + this.state.getInfRate(),
                startLeft, centerCoord, this.textBold);
        canvas.drawText("" + this.state.getEpiLeft(),
                startLeft + (addBy), centerCoord, this.textBold);

        startLeft = 1643.0f;

        canvas.drawText("" + (this.state.getPlayerDeck().getCardsLeft() / 2),
                startLeft, centerCoord, this.textBold);
    }

    public void updateCities(Canvas canvas) {
        for(int i = 0; i < Board.NUM_CITIES; i++) {
            float x = this.state.getCities().getAllCities()[i].getLocation()[0][0];
            float y = this.state.getCities().getAllCities()[i].getLocation()[0][1];

            if(this.state.getCities().getAllCities()[i].hasStation()) {
                canvas.drawBitmap(this.researchStation, x, y, null);
            }

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

            for(int j = 0; j < this.state.getCurrCity().length; j++) {
                if(this.state.getCurrCity()[j].getName().equals(this.state.getCities().getAllCities()[i].getName())) {
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
    }

    public void updateCurrPlayer(Canvas canvas) {
        float centerCoord = 160.0f;
        float height = 825.0f;

        switch(this.state.getCurrPlayer()) {
            case PandemicHumanPlayer.PINK: canvas.drawBitmap(this.bigPink, centerCoord, height, null); break;
            case PandemicHumanPlayer.ORANGE: canvas.drawBitmap(this.bigOrange, centerCoord, height, null); break;
            case PandemicHumanPlayer.BLUE: canvas.drawBitmap(this.bigBlue, centerCoord, height, null); break;
            case PandemicHumanPlayer.GREEN: canvas.drawBitmap(this.bigGreen, centerCoord, height, null); break;
        }
    }

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
    }

    public void updateCures(Canvas canvas) {
        float centerCoord = 49.0f;
        float startHeight = 530.0f;
        float addBy = 80.0f;

        for (int i = 0; i < Disease.NUM_DISEASES; i++) {
            switch (i) {
                case Disease.BLUE:
                    switch (this.state.getDiseases()[i].getState()) {
                        case Disease.CURED:
                            canvas.drawBitmap(curedBlue, centerCoord, startHeight + (addBy * i), null);
                            break;
                        case Disease.UNCURED:
                            canvas.drawBitmap(uncuredBlue, centerCoord, startHeight + (addBy * i), null);
                            break;
                        case Disease.ERADICATED:
                            canvas.drawBitmap(eradBlue, centerCoord, startHeight + (addBy * i), null);
                            break;
                    }
                    break;
                case Disease.YELLOW:
                    switch (this.state.getDiseases()[i].getState()) {
                        case Disease.CURED:
                            canvas.drawBitmap(curedYellow, centerCoord, startHeight + (addBy * i), null);
                            break;
                        case Disease.UNCURED:
                            canvas.drawBitmap(uncuredYellow, centerCoord, startHeight + (addBy * i), null);
                            break;
                        case Disease.ERADICATED:
                            canvas.drawBitmap(eradYellow, centerCoord, startHeight + (addBy * i), null);
                            break;
                    }
                case Disease.BLACK:
                    switch (this.state.getDiseases()[i].getState()) {
                        case Disease.CURED:
                            canvas.drawBitmap(curedBlack, centerCoord, startHeight + (addBy * i), null);
                            break;
                        case Disease.UNCURED:
                            canvas.drawBitmap(uncuredBlack, centerCoord,startHeight + (addBy * i), null);
                            break;
                        case Disease.ERADICATED:
                            canvas.drawBitmap(eradBlack, centerCoord, startHeight + (addBy * i), null);
                            break;
                    }
                case Disease.RED:
                    switch (this.state.getDiseases()[i].getState()) {
                        case Disease.CURED:
                            canvas.drawBitmap(curedRed, centerCoord, startHeight + (addBy * i), null);
                            break;
                        case Disease.UNCURED:
                            canvas.drawBitmap(uncuredRed, centerCoord, startHeight + (addBy * i), null);
                            break;
                        case Disease.ERADICATED:
                            canvas.drawBitmap(eradRed, centerCoord, startHeight + (addBy * i), null);
                            break;
                    }
            }
        }
    }

    public void setState(PandemicGameState ref) {
        this.state = ref;
    }
}
package up.edu.pandemic;

import android.content.Context;
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

    private PandemicGameState state;

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
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(this.gui, 0.0f, 0.0f, null);

        if(this.state != null) {
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

        canvas.drawBitmap(this.cityNames, 0.0f, 0.0f, null);
    }

    public void setState(PandemicGameState ref) {
        this.state = ref;
    }
}
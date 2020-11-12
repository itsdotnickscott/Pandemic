package up.edu.pandemic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PandemicMapView extends SurfaceView {
    // instance variables
    private Bitmap map;

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

    private Bitmap pawn1b;
    private Bitmap pawn1g;
    private Bitmap pawn1p;
    private Bitmap pawn1w;
    private Bitmap pawn2bg;
    private Bitmap pawn2bp;
    private Bitmap pawn2bw;
    private Bitmap pawn2wg;
    private Bitmap pawn2wp;
    private Bitmap pawn2gp;
    private Bitmap pawn3bgp;
    private Bitmap pawn3bwg;
    private Bitmap pawn3bwp;
    private Bitmap pawn3wgp;
    private Bitmap pawns4;

    private Bitmap researchStation;

    private Board cities;
    private City[] currCity;

    public PandemicMapView(Context context, AttributeSet attributes) {
        super(context, attributes);
        setWillNotDraw(false);

        // load image files
        map = BitmapFactory.decodeResource(getResources(), R.drawable.pandemicmap);

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

        pawn1b = BitmapFactory.decodeResource(getResources(), R.drawable.pawn1b);
        pawn1g = BitmapFactory.decodeResource(getResources(), R.drawable.pawn1g);
        pawn1p = BitmapFactory.decodeResource(getResources(), R.drawable.pawn1p);
        pawn1w = BitmapFactory.decodeResource(getResources(), R.drawable.pawn1w);
        pawn2bg = BitmapFactory.decodeResource(getResources(), R.drawable.pawn2bg);
        pawn2bp = BitmapFactory.decodeResource(getResources(), R.drawable.pawn2bp);
        pawn2bw = BitmapFactory.decodeResource(getResources(), R.drawable.pawn2bw);
        pawn2wg = BitmapFactory.decodeResource(getResources(), R.drawable.pawn2wg);
        pawn2wp = BitmapFactory.decodeResource(getResources(), R.drawable.pawn2wp);
        pawn2gp = BitmapFactory.decodeResource(getResources(), R.drawable.pawn2gp);
        pawn3bgp = BitmapFactory.decodeResource(getResources(), R.drawable.pawn3bgp);
        pawn3bwg = BitmapFactory.decodeResource(getResources(), R.drawable.pawn3bwg);
        pawn3bwp = BitmapFactory.decodeResource(getResources(), R.drawable.pawn3bwp);
        pawn3wgp = BitmapFactory.decodeResource(getResources(), R.drawable.pawn3wgp);
        pawns4 = BitmapFactory.decodeResource(getResources(), R.drawable.pawns4);

        researchStation = BitmapFactory.decodeResource(getResources(), R.drawable.researchstation);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(this.map, 0.0f, 0.0f, null);

        if(this.cities != null && this.currCity != null) {
            for(int i = 0; i < Board.NUM_CITIES; i++) {
                float x = this.cities.getAllCities()[i].getLocation()[0][0];
                float y = this.cities.getAllCities()[i].getLocation()[0][1];

                if(this.cities.getAllCities()[i].hasStation()) {
                    canvas.drawBitmap(this.researchStation, x, y, null);
                }

                switch(this.cities.getAllCities()[i].getColor()) {
                    case Disease.BLUE:
                        switch(this.cities.getAllCities()[i].getCubes()) {
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
                        switch(this.cities.getAllCities()[i].getCubes()) {
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
                        switch(this.cities.getAllCities()[i].getCubes()) {
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
                        switch(this.cities.getAllCities()[i].getCubes()) {
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

                boolean threePlayers = false;
                boolean fourPlayers = false;

                switch(this.currCity.length) {
                    case 3: threePlayers = true; break;
                    case 4: fourPlayers = true; break;
                }

                if(this.currCity[PandemicHumanPlayer.GREEN].getName().equals(this.cities.getAllCities()[i].getName())) {
                    if(this.currCity[PandemicHumanPlayer.PINK].getName().equals(this.cities.getAllCities()[i].getName())) {
                        if((threePlayers || fourPlayers ) &&
                                this.currCity[PandemicHumanPlayer.BLACK].getName().equals(this.cities.getAllCities()[i].getName())) {
                            if(fourPlayers &&
                                    this.currCity[PandemicHumanPlayer.WHITE].getName().equals(this.cities.getAllCities()[i].getName())) {
                                // CASE: All 4
                                canvas.drawBitmap(this.pawns4, x, y, null);
                            }
                            // CASE: Green, Pink, Black
                            canvas.drawBitmap(this.pawn3bgp, x, y, null);
                        }
                        else if(fourPlayers &&
                                this.currCity[PandemicHumanPlayer.WHITE].getName().equals(this.cities.getAllCities()[i].getName())) {
                            // CASE: Green, Pink, White
                            canvas.drawBitmap(this.pawn3wgp, x, y, null);
                        }
                        // CASE: Green, Pink
                        canvas.drawBitmap(this.pawn2gp, x, y, null);
                    }
                    else if((threePlayers || fourPlayers) &&
                            this.currCity[PandemicHumanPlayer.BLACK].getName().equals(this.cities.getAllCities()[i].getName())) {
                        if(fourPlayers &&
                                this.currCity[PandemicHumanPlayer.WHITE].getName().equals(this.cities.getAllCities()[i].getName())) {
                            // CASE: Green, Black, White
                            canvas.drawBitmap(this.pawn3bwg, x, y, null);
                        }
                        // CASE: Green, Black
                        canvas.drawBitmap(this.pawn2bg, x, y, null);
                    }
                    else if(fourPlayers &&
                            this.currCity[PandemicHumanPlayer.WHITE].getName().equals(this.cities.getAllCities()[i].getName())) {
                        // CASE: Green, White
                        canvas.drawBitmap(this.pawn2wg, x, y, null);
                    }
                    // CASE: Green
                    canvas.drawBitmap(this.pawn1g, x, y, null);
                }
                else if(this.currCity[PandemicHumanPlayer.PINK].getName().equals(this.cities.getAllCities()[i].getName())) {
                    if((threePlayers || fourPlayers) &&
                            this.currCity[PandemicHumanPlayer.BLACK].getName().equals(this.cities.getAllCities()[i].getName())) {
                        if(fourPlayers &&
                                this.currCity[PandemicHumanPlayer.WHITE].getName().equals(this.cities.getAllCities()[i].getName())) {
                            // CASE: Pink, Black, White
                            canvas.drawBitmap(this.pawn3bwp, x, y, null);
                        }
                        // CASE: Pink, Black
                        canvas.drawBitmap(this.pawn2bp, x, y, null);
                    }
                    else if(fourPlayers &&
                            this.currCity[PandemicHumanPlayer.WHITE].getName().equals(this.cities.getAllCities()[i].getName())) {
                        // CASE: Pink, White
                        canvas.drawBitmap(this.pawn2wp, x, y, null);
                    }
                    // CASE: Pink
                    canvas.drawBitmap(this.pawn1p, x, y, null);
                }
                else if((threePlayers || fourPlayers) &&
                        this.currCity[PandemicHumanPlayer.BLACK].getName().equals(this.cities.getAllCities()[i].getName())) {
                    if(fourPlayers &&
                            this.currCity[PandemicHumanPlayer.WHITE].getName().equals(this.cities.getAllCities()[i].getName())) {
                        // CASE: Black, White
                        canvas.drawBitmap(this.pawn2bw, x, y, null);
                    }
                    // CASE: Black
                    canvas.drawBitmap(this.pawn1b, x, y, null);
                }
                else if(fourPlayers &&
                        this.currCity[PandemicHumanPlayer.WHITE].getName().equals(this.cities.getAllCities()[i].getName())) {
                    // CASE: White
                    canvas.drawBitmap(this.pawn1w, x, y, null);
                }
            }
        }
    }

    public void setCities(Board board) {
        this.cities = board;
    }

    public void setCurrCity(City[] curr) {
        this.currCity = curr;
    }
}
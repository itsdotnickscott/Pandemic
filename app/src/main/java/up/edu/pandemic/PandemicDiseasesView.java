package up.edu.pandemic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class PandemicDiseasesView extends SurfaceView {
    public static final float CENTER_COORD = 50.0f;
    public static final float START_HEIGHT = 50.0f;
    public static final float ADD_BY = 85.0f;

    private Bitmap background;
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

    private Disease[] diseases;

    public PandemicDiseasesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.cures);
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
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0.0f, 0.0f, null);

        if(this.diseases != null) {
            for (int i = 0; i < Disease.NUM_DISEASES; i++) {
                switch (i) {
                    case Disease.BLUE:
                        switch (this.diseases[i].getState()) {
                            case Disease.CURED:
                                canvas.drawBitmap(curedBlue, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                            case Disease.UNCURED:
                                canvas.drawBitmap(uncuredBlue, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                            case Disease.ERADICATED:
                                canvas.drawBitmap(eradBlue, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                        }
                        break;
                    case Disease.YELLOW:
                        switch (this.diseases[i].getState()) {
                            case Disease.CURED:
                                canvas.drawBitmap(curedYellow, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                            case Disease.UNCURED:
                                canvas.drawBitmap(uncuredYellow, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                            case Disease.ERADICATED:
                                canvas.drawBitmap(eradYellow, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                        }
                    case Disease.BLACK:
                        switch (this.diseases[i].getState()) {
                            case Disease.CURED:
                                canvas.drawBitmap(curedBlack, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                            case Disease.UNCURED:
                                canvas.drawBitmap(uncuredBlack, CENTER_COORD,START_HEIGHT + (ADD_BY * i), null);
                                break;
                            case Disease.ERADICATED:
                                canvas.drawBitmap(eradBlack, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                        }
                    case Disease.RED:
                        switch (this.diseases[i].getState()) {
                            case Disease.CURED:
                                canvas.drawBitmap(curedRed, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                            case Disease.UNCURED:
                                canvas.drawBitmap(uncuredRed, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                            case Disease.ERADICATED:
                                canvas.drawBitmap(eradRed, CENTER_COORD, START_HEIGHT + (ADD_BY * i), null);
                                break;
                        }
                }
            }
        }
    }

    public void setDiseases(Disease[] update) {
        this.diseases = update;
    }
}

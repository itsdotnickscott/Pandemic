package up.edu.pandemic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PandemicMapView extends SurfaceView {
    private Bitmap map = null;

    public PandemicMapView(Context context, AttributeSet attributes) {
        super(context, attributes);
        setWillNotDraw(false);
        //load map
        map = BitmapFactory.decodeResource(getResources(), R.drawable.pandemicmap);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(this.map, 0.0f, 0.0f, null);
    }
}

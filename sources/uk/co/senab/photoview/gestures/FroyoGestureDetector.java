package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

@TargetApi(8)
/* loaded from: classes5.dex */
public class FroyoGestureDetector extends EclairGestureDetector {

    /* renamed from: f, reason: collision with root package name */
    protected final ScaleGestureDetector f26868f;

    public FroyoGestureDetector(Context context) {
        super(context);
        this.f26868f = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() { // from class: uk.co.senab.photoview.gestures.FroyoGestureDetector.1
            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                float scaleFactor = scaleGestureDetector.getScaleFactor();
                if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                    return false;
                }
                FroyoGestureDetector.this.f26863a.onScale(scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }
        });
    }

    @Override // uk.co.senab.photoview.gestures.CupcakeGestureDetector, uk.co.senab.photoview.gestures.GestureDetector
    public boolean isScaling() {
        return this.f26868f.isInProgress();
    }

    @Override // uk.co.senab.photoview.gestures.EclairGestureDetector, uk.co.senab.photoview.gestures.CupcakeGestureDetector, uk.co.senab.photoview.gestures.GestureDetector
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.f26868f.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }
}

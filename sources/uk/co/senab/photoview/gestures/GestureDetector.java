package uk.co.senab.photoview.gestures;

import android.view.MotionEvent;

/* loaded from: classes5.dex */
public interface GestureDetector {
    boolean isDragging();

    boolean isScaling();

    boolean onTouchEvent(MotionEvent motionEvent);

    void setOnGestureListener(OnGestureListener onGestureListener);
}

package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import uk.co.senab.photoview.Compat;

@TargetApi(5)
/* loaded from: classes5.dex */
public class EclairGestureDetector extends CupcakeGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId;
    private int mActivePointerIndex;

    public EclairGestureDetector(Context context) {
        super(context);
        this.mActivePointerId = -1;
        this.mActivePointerIndex = 0;
    }

    @Override // uk.co.senab.photoview.gestures.CupcakeGestureDetector
    float a(MotionEvent motionEvent) {
        try {
            return motionEvent.getX(this.mActivePointerIndex);
        } catch (Exception unused) {
            return motionEvent.getX();
        }
    }

    @Override // uk.co.senab.photoview.gestures.CupcakeGestureDetector
    float b(MotionEvent motionEvent) {
        try {
            return motionEvent.getY(this.mActivePointerIndex);
        } catch (Exception unused) {
            return motionEvent.getY();
        }
    }

    @Override // uk.co.senab.photoview.gestures.CupcakeGestureDetector, uk.co.senab.photoview.gestures.GestureDetector
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action == 1 || action == 3) {
                this.mActivePointerId = -1;
            } else if (action == 6) {
                int pointerIndex = Compat.getPointerIndex(motionEvent.getAction());
                if (motionEvent.getPointerId(pointerIndex) == this.mActivePointerId) {
                    int i2 = pointerIndex != 0 ? 0 : 1;
                    this.mActivePointerId = motionEvent.getPointerId(i2);
                    this.f26864b = motionEvent.getX(i2);
                    this.f26865c = motionEvent.getY(i2);
                }
            }
        } else {
            this.mActivePointerId = motionEvent.getPointerId(0);
        }
        int i3 = this.mActivePointerId;
        this.mActivePointerIndex = motionEvent.findPointerIndex(i3 != -1 ? i3 : 0);
        return super.onTouchEvent(motionEvent);
    }
}

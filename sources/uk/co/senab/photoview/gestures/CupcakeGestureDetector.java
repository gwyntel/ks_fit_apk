package uk.co.senab.photoview.gestures;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import uk.co.senab.photoview.log.LogManager;

/* loaded from: classes5.dex */
public class CupcakeGestureDetector implements GestureDetector {
    private static final String LOG_TAG = "CupcakeGestureDetector";

    /* renamed from: a, reason: collision with root package name */
    protected OnGestureListener f26863a;

    /* renamed from: b, reason: collision with root package name */
    float f26864b;

    /* renamed from: c, reason: collision with root package name */
    float f26865c;

    /* renamed from: d, reason: collision with root package name */
    final float f26866d;

    /* renamed from: e, reason: collision with root package name */
    final float f26867e;
    private boolean mIsDragging;
    private VelocityTracker mVelocityTracker;

    public CupcakeGestureDetector(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.f26867e = viewConfiguration.getScaledMinimumFlingVelocity();
        this.f26866d = viewConfiguration.getScaledTouchSlop();
    }

    float a(MotionEvent motionEvent) {
        return motionEvent.getX();
    }

    float b(MotionEvent motionEvent) {
        return motionEvent.getY();
    }

    @Override // uk.co.senab.photoview.gestures.GestureDetector
    public boolean isDragging() {
        return this.mIsDragging;
    }

    @Override // uk.co.senab.photoview.gestures.GestureDetector
    public boolean isScaling() {
        return false;
    }

    @Override // uk.co.senab.photoview.gestures.GestureDetector
    public boolean onTouchEvent(MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        int action = motionEvent.getAction();
        if (action == 0) {
            VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
            this.mVelocityTracker = velocityTrackerObtain;
            if (velocityTrackerObtain != null) {
                velocityTrackerObtain.addMovement(motionEvent);
            } else {
                LogManager.getLogger().i(LOG_TAG, "Velocity tracker is null");
            }
            this.f26864b = a(motionEvent);
            this.f26865c = b(motionEvent);
            this.mIsDragging = false;
        } else if (action == 1) {
            if (this.mIsDragging && this.mVelocityTracker != null) {
                this.f26864b = a(motionEvent);
                this.f26865c = b(motionEvent);
                this.mVelocityTracker.addMovement(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = this.mVelocityTracker.getXVelocity();
                float yVelocity = this.mVelocityTracker.getYVelocity();
                if (Math.max(Math.abs(xVelocity), Math.abs(yVelocity)) >= this.f26867e) {
                    this.f26863a.onFling(this.f26864b, this.f26865c, -xVelocity, -yVelocity);
                }
            }
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
                this.mVelocityTracker = null;
            }
        } else if (action == 2) {
            float fA = a(motionEvent);
            float fB = b(motionEvent);
            float f2 = fA - this.f26864b;
            float f3 = fB - this.f26865c;
            if (!this.mIsDragging) {
                this.mIsDragging = Math.sqrt((double) ((f2 * f2) + (f3 * f3))) >= ((double) this.f26866d);
            }
            if (this.mIsDragging) {
                this.f26863a.onDrag(f2, f3);
                this.f26864b = fA;
                this.f26865c = fB;
                VelocityTracker velocityTracker3 = this.mVelocityTracker;
                if (velocityTracker3 != null) {
                    velocityTracker3.addMovement(motionEvent);
                }
            }
        } else if (action == 3 && (velocityTracker = this.mVelocityTracker) != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        return true;
    }

    @Override // uk.co.senab.photoview.gestures.GestureDetector
    public void setOnGestureListener(OnGestureListener onGestureListener) {
        this.f26863a = onGestureListener;
    }
}

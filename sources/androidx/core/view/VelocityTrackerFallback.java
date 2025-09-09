package androidx.core.view;

import android.view.MotionEvent;

/* loaded from: classes.dex */
class VelocityTrackerFallback {
    private static final long ASSUME_POINTER_STOPPED_MS = 40;
    private static final int HISTORY_SIZE = 20;
    private static final long RANGE_MS = 100;
    private final float[] mMovements = new float[20];
    private final long[] mEventTimes = new long[20];
    private float mLastComputedVelocity = 0.0f;
    private int mDataPointsBufferSize = 0;
    private int mDataPointsBufferLastUsedIndex = 0;

    VelocityTrackerFallback() {
    }

    private void clear() {
        this.mDataPointsBufferSize = 0;
        this.mLastComputedVelocity = 0.0f;
    }

    private float getCurrentVelocity() {
        long[] jArr;
        long j2;
        int i2 = this.mDataPointsBufferSize;
        if (i2 < 2) {
            return 0.0f;
        }
        int i3 = this.mDataPointsBufferLastUsedIndex;
        int i4 = ((i3 + 20) - (i2 - 1)) % 20;
        long j3 = this.mEventTimes[i3];
        while (true) {
            jArr = this.mEventTimes;
            j2 = jArr[i4];
            if (j3 - j2 <= RANGE_MS) {
                break;
            }
            this.mDataPointsBufferSize--;
            i4 = (i4 + 1) % 20;
        }
        int i5 = this.mDataPointsBufferSize;
        if (i5 < 2) {
            return 0.0f;
        }
        if (i5 == 2) {
            int i6 = (i4 + 1) % 20;
            if (j2 == jArr[i6]) {
                return 0.0f;
            }
            return this.mMovements[i6] / (r2 - j2);
        }
        float fAbs = 0.0f;
        int i7 = 0;
        for (int i8 = 0; i8 < this.mDataPointsBufferSize - 1; i8++) {
            int i9 = i8 + i4;
            long[] jArr2 = this.mEventTimes;
            long j4 = jArr2[i9 % 20];
            int i10 = (i9 + 1) % 20;
            if (jArr2[i10] != j4) {
                i7++;
                float fKineticEnergyToVelocity = kineticEnergyToVelocity(fAbs);
                float f2 = this.mMovements[i10] / (this.mEventTimes[i10] - j4);
                fAbs += (f2 - fKineticEnergyToVelocity) * Math.abs(f2);
                if (i7 == 1) {
                    fAbs *= 0.5f;
                }
            }
        }
        return kineticEnergyToVelocity(fAbs);
    }

    private static float kineticEnergyToVelocity(float f2) {
        return (f2 < 0.0f ? -1.0f : 1.0f) * ((float) Math.sqrt(Math.abs(f2) * 2.0f));
    }

    void a(MotionEvent motionEvent) {
        long eventTime = motionEvent.getEventTime();
        if (this.mDataPointsBufferSize != 0 && eventTime - this.mEventTimes[this.mDataPointsBufferLastUsedIndex] > ASSUME_POINTER_STOPPED_MS) {
            clear();
        }
        int i2 = (this.mDataPointsBufferLastUsedIndex + 1) % 20;
        this.mDataPointsBufferLastUsedIndex = i2;
        int i3 = this.mDataPointsBufferSize;
        if (i3 != 20) {
            this.mDataPointsBufferSize = i3 + 1;
        }
        this.mMovements[i2] = motionEvent.getAxisValue(26);
        this.mEventTimes[this.mDataPointsBufferLastUsedIndex] = eventTime;
    }

    void b(int i2, float f2) {
        float currentVelocity = getCurrentVelocity() * i2;
        this.mLastComputedVelocity = currentVelocity;
        if (currentVelocity < (-Math.abs(f2))) {
            this.mLastComputedVelocity = -Math.abs(f2);
        } else if (this.mLastComputedVelocity > Math.abs(f2)) {
            this.mLastComputedVelocity = Math.abs(f2);
        }
    }

    float c(int i2) {
        if (i2 != 26) {
            return 0.0f;
        }
        return this.mLastComputedVelocity;
    }
}

package com.facebook.appevents.codeless;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/* loaded from: classes3.dex */
class ViewIndexingTrigger implements SensorEventListener {
    private static final double SHAKE_THRESHOLD_GRAVITY = 2.299999952316284d;
    private OnShakeListener mListener;

    public interface OnShakeListener {
        void onShake();
    }

    ViewIndexingTrigger() {
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i2) {
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.mListener != null) {
            float[] fArr = sensorEvent.values;
            double d2 = fArr[0] / 9.80665f;
            double d3 = fArr[1] / 9.80665f;
            double d4 = fArr[2] / 9.80665f;
            if (Math.sqrt((d2 * d2) + (d3 * d3) + (d4 * d4)) > SHAKE_THRESHOLD_GRAVITY) {
                this.mListener.onShake();
            }
        }
    }

    public void setOnShakeListener(OnShakeListener onShakeListener) {
        this.mListener = onShakeListener;
    }
}

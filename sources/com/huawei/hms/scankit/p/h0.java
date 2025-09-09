package com.huawei.hms.scankit.p;

import android.hardware.Camera;
import android.util.Log;

/* loaded from: classes4.dex */
public class h0 {

    /* renamed from: a, reason: collision with root package name */
    private Camera f17314a;

    public synchronized void a(Camera camera) {
        this.f17314a = camera;
    }

    public synchronized g0 a() {
        return new g0(this.f17314a.getParameters().getMaxExposureCompensation(), this.f17314a.getParameters().getMinExposureCompensation(), this.f17314a.getParameters().getExposureCompensation(), this.f17314a.getParameters().getExposureCompensationStep());
    }

    public synchronized void a(int i2) {
        Camera camera = this.f17314a;
        if (camera == null) {
            return;
        }
        try {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setExposureCompensation(i2);
            Log.i("WWYYEHG", "setExpuseModeA: " + parameters.getAutoExposureLock());
            this.f17314a.setParameters(parameters);
            Log.i("WWYYEHG", "setExpuseModeB: " + parameters.getAutoExposureLock());
        } catch (RuntimeException unused) {
            Log.w("CameraManager", "CameraExposureManager::setCompensation failed");
        }
    }
}

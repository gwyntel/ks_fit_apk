package com.huawei.hms.scankit.p;

import android.hardware.Camera;
import android.util.Log;

/* loaded from: classes4.dex */
public class n0 {

    /* renamed from: a, reason: collision with root package name */
    private Camera f17562a;

    public synchronized void a(Camera camera) {
        this.f17562a = camera;
    }

    public synchronized boolean b() {
        Camera camera = this.f17562a;
        if (camera == null) {
            return false;
        }
        return camera.getParameters().isZoomSupported();
    }

    public synchronized m0 a() {
        return new m0(this.f17562a.getParameters().getMaxZoom(), this.f17562a.getParameters().getZoom(), this.f17562a.getParameters().getZoomRatios());
    }

    public synchronized void a(int i2) {
        Camera camera = this.f17562a;
        if (camera == null) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        parameters.setZoom(i2);
        try {
            this.f17562a.setParameters(parameters);
        } catch (RuntimeException e2) {
            Log.e("CameraManager", "CameraZoomManager::setCameraZoomIndex failed: " + e2.getMessage());
        }
    }
}

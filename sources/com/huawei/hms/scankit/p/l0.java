package com.huawei.hms.scankit.p;

import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import com.huawei.hms.scankit.p.k0;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class l0 {

    /* renamed from: a, reason: collision with root package name */
    private Camera f17487a;

    public synchronized void a(Camera camera) {
        this.f17487a = camera;
    }

    public synchronized k0 a() {
        int maxNumMeteringAreas;
        RuntimeException e2;
        Rect rect;
        try {
            maxNumMeteringAreas = this.f17487a.getParameters().getMaxNumMeteringAreas();
        } catch (RuntimeException e3) {
            maxNumMeteringAreas = 0;
            e2 = e3;
        }
        try {
            rect = this.f17487a.getParameters().getMeteringAreas().get(0).rect;
        } catch (RuntimeException e4) {
            e2 = e4;
            Log.w("CameraManager", "CameraMeteringManager::getCameraMeteringData failed: " + e2.getMessage());
            rect = null;
            return new k0(maxNumMeteringAreas, rect);
        }
        return new k0(maxNumMeteringAreas, rect);
    }

    public synchronized void a(List<k0.a> list) {
        try {
            Camera camera = this.f17487a;
            if (camera == null) {
                return;
            }
            Camera.Parameters parameters = camera.getParameters();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < list.size(); i2++) {
                arrayList.add(new Camera.Area(list.get(i2).f17464a, list.get(i2).f17465b));
            }
            parameters.setMeteringAreas(arrayList);
            try {
                this.f17487a.setParameters(parameters);
            } catch (RuntimeException e2) {
                Log.w("CameraManager", "CameraMeteringManager::setCameraMeteringArea failed: " + e2.getMessage());
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}

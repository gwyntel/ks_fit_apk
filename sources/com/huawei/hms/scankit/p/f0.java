package com.huawei.hms.scankit.p;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: classes4.dex */
public class f0 {

    /* renamed from: a, reason: collision with root package name */
    private e0 f17229a;

    /* renamed from: b, reason: collision with root package name */
    private Point f17230b;

    /* renamed from: c, reason: collision with root package name */
    private Point f17231c;

    private void b(Camera.Parameters parameters) {
        if (parameters.isZoomSupported()) {
            parameters.setZoom(1);
        } else {
            Log.w("CameraManager", "initCameraParameters::setDefaultZoom not support zoom");
        }
    }

    private void c(Camera.Parameters parameters) {
        String str;
        String[] strArr = {"continuous-picture", "continuous-video", "auto"};
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes == null) {
            Log.w("CameraManager", "setFocusMode failed, use default");
            return;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= 3) {
                str = null;
                break;
            }
            str = strArr[i2];
            if (supportedFocusModes.contains(str)) {
                break;
            } else {
                i2++;
            }
        }
        if (str != null) {
            Log.i("CameraManager", "setFocusMode: " + str);
            parameters.setFocusMode(str);
        }
    }

    void a(Camera camera, e0 e0Var) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (camera == null || e0Var == null) {
            throw new IllegalArgumentException("initCameraParameters param is invalid");
        }
        Camera.Parameters parameters = camera.getParameters();
        this.f17229a = e0Var;
        this.f17230b = a(parameters, e0Var.a(), false);
        Log.d("CameraManager", "initCameraParameters previewCameraSize: " + this.f17230b.toString());
        if (e0Var.c() == 0) {
            this.f17231c = a(parameters, e0Var.a(), true);
            Log.d("CameraManager", "initCameraParameters pictureCameraSize: " + this.f17231c.toString());
        }
        a(camera, this.f17230b, this.f17231c);
    }

    Point a() {
        return this.f17230b;
    }

    private void a(Camera camera, Point point, Point point2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.f17229a == null) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(point.x, point.y);
        if (this.f17229a.c() == 0) {
            parameters.setPictureSize(point2.x, point2.y);
        }
        if (this.f17229a.b() != 1) {
            a(parameters);
        }
        c(parameters);
        b(parameters);
        if (this.f17229a.e()) {
            parameters.setRecordingHint(true);
        }
        if (Build.VERSION.SDK_INT >= 30) {
            a(parameters, true);
        }
        camera.setParameters(parameters);
    }

    public static void a(Camera.Parameters parameters, boolean z2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Method method = Camera.Parameters.class.getMethod("setScanOptEnable", Boolean.TYPE);
            if (method != null) {
                method.invoke(parameters, Boolean.valueOf(z2));
                Log.i("CameraManager", "setScanOptEnable isOpt " + z2);
            }
        } catch (IllegalAccessException unused) {
            Log.e("CameraManager", "setScanOptEnable reflection IllegalAccessException");
        } catch (NoSuchMethodException unused2) {
            Log.e("CameraManager", "setScanOptEnable reflection NoSuchMethodException");
        } catch (InvocationTargetException unused3) {
            Log.e("CameraManager", "setScanOptEnable reflection InvocationTargetException");
        } catch (Exception unused4) {
            Log.e("CameraManager", "setScanOptEnable reflection Exception");
        }
    }

    private Point a(Camera.Parameters parameters, Point point, boolean z2) {
        List<Camera.Size> supportedPictureSizes;
        if (!z2) {
            supportedPictureSizes = parameters.getSupportedPreviewSizes();
        } else {
            supportedPictureSizes = parameters.getSupportedPictureSizes();
        }
        if (supportedPictureSizes != null && !supportedPictureSizes.isEmpty()) {
            return a(supportedPictureSizes, point);
        }
        Log.e("CameraManager", "CameraConfigImpl::findCameraResolution camera not support");
        return new Point(0, 0);
    }

    private Point a(List<Camera.Size> list, Point point) {
        double d2 = point.x / point.y;
        int i2 = 0;
        double dAbs = Double.MAX_VALUE;
        int i3 = 0;
        for (Camera.Size size : list) {
            int i4 = size.width;
            int i5 = size.height;
            if (i4 == point.x && i5 == point.y) {
                return new Point(i4, i5);
            }
            if (i4 * i5 >= 153600.0d) {
                double d3 = (i4 / i5) - d2;
                if (Math.abs(d3) < dAbs) {
                    dAbs = Math.abs(d3);
                    i3 = i5;
                    i2 = i4;
                }
            }
        }
        return new Point(i2, i3);
    }

    private void a(Camera.Parameters parameters) {
        e0 e0Var = this.f17229a;
        if (e0Var == null) {
            return;
        }
        String strF = e0Var.f();
        if (!strF.equals("off") && !strF.equals("torch")) {
            strF = "off";
        }
        parameters.setFlashMode(strF);
    }
}

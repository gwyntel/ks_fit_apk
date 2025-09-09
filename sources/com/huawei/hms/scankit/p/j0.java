package com.huawei.hms.scankit.p;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.TextureView;
import com.huawei.hms.scankit.p.k0;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes4.dex */
public class j0 {

    /* renamed from: a, reason: collision with root package name */
    private e0 f17401a;

    /* renamed from: b, reason: collision with root package name */
    private d f17402b;

    /* renamed from: c, reason: collision with root package name */
    private b f17403c;

    /* renamed from: d, reason: collision with root package name */
    private Camera.PreviewCallback f17404d;

    /* renamed from: e, reason: collision with root package name */
    private WeakReference<Context> f17405e;

    /* renamed from: f, reason: collision with root package name */
    private i0 f17406f;

    /* renamed from: g, reason: collision with root package name */
    private h0 f17407g;

    /* renamed from: h, reason: collision with root package name */
    private n0 f17408h;

    /* renamed from: i, reason: collision with root package name */
    private l0 f17409i;

    /* renamed from: j, reason: collision with root package name */
    private Camera f17410j;

    /* renamed from: k, reason: collision with root package name */
    private f0 f17411k;

    /* renamed from: l, reason: collision with root package name */
    private String f17412l;

    /* renamed from: m, reason: collision with root package name */
    private c f17413m = c.CAMERA_CLOSED;

    /* renamed from: n, reason: collision with root package name */
    private int f17414n = -1;

    public interface b {
        void a(Point point);
    }

    public enum c {
        CAMERA_CLOSED(1),
        CAMERA_OPENED(2),
        CAMERA_INITIALED(3),
        PREVIEW_STARTED(4),
        PREVIEW_STOPPED(5);


        /* renamed from: a, reason: collision with root package name */
        private final int f17421a;

        c(int i2) {
            this.f17421a = i2;
        }

        public int a() {
            return this.f17421a;
        }
    }

    public interface d {
        void a();

        void b();

        void c();
    }

    public interface e {
        void a(byte[] bArr);
    }

    private static class f implements Camera.PreviewCallback {
        private f() {
        }

        @Override // android.hardware.Camera.PreviewCallback
        public void onPreviewFrame(byte[] bArr, Camera camera) {
        }
    }

    public j0(Context context, e0 e0Var) {
        if (context == null || e0Var == null) {
            throw new IllegalArgumentException("CameraManager constructor param invalid");
        }
        this.f17405e = new WeakReference<>(context);
        this.f17401a = e0Var;
        this.f17412l = e0Var.f();
        this.f17411k = new f0();
        this.f17407g = new h0();
        this.f17408h = new n0();
        this.f17409i = new l0();
    }

    public synchronized void a(e eVar) {
        if (eVar == null) {
            throw new IllegalArgumentException("CameraManager::setFrameCallback param invalid");
        }
        this.f17404d = new f6(eVar);
    }

    public synchronized g0 b() {
        if (this.f17410j == null || this.f17413m.a() == c.CAMERA_CLOSED.a()) {
            return null;
        }
        try {
            return this.f17407g.a();
        } catch (Exception unused) {
            Log.e("CameraManager", "CameraManager::getCameraExposureData failed");
            return null;
        }
    }

    public synchronized void c(int i2) {
        if (this.f17410j != null && this.f17413m.a() != c.CAMERA_CLOSED.a()) {
            this.f17407g.a(i2);
        }
    }

    public synchronized void d(int i2) {
        if (this.f17410j != null && this.f17413m.a() != c.CAMERA_CLOSED.a()) {
            this.f17408h.a(i2);
        }
    }

    public synchronized Point e() {
        return this.f17411k.a();
    }

    public synchronized c f() {
        return this.f17413m;
    }

    public synchronized m0 g() {
        if (this.f17410j != null && this.f17413m.a() != c.CAMERA_CLOSED.a()) {
            return this.f17408h.a();
        }
        return null;
    }

    public synchronized String h() {
        return this.f17412l;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean i() {
        /*
            r2 = this;
            monitor-enter(r2)
            android.hardware.Camera r0 = r2.f17410j     // Catch: java.lang.Throwable -> L15
            if (r0 == 0) goto L17
            com.huawei.hms.scankit.p.j0$c r0 = r2.f17413m     // Catch: java.lang.Throwable -> L15
            int r0 = r0.a()     // Catch: java.lang.Throwable -> L15
            com.huawei.hms.scankit.p.j0$c r1 = com.huawei.hms.scankit.p.j0.c.CAMERA_OPENED     // Catch: java.lang.Throwable -> L15
            int r1 = r1.a()     // Catch: java.lang.Throwable -> L15
            if (r0 < r1) goto L17
            r0 = 1
            goto L18
        L15:
            r0 = move-exception
            goto L1a
        L17:
            r0 = 0
        L18:
            monitor-exit(r2)
            return r0
        L1a:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L15
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.j0.i():boolean");
    }

    public synchronized boolean j() {
        return this.f17408h.b();
    }

    public synchronized void k() {
        this.f17403c = null;
    }

    public synchronized void l() {
        try {
            try {
                if (this.f17413m.a() == c.PREVIEW_STARTED.a()) {
                    a();
                    q();
                    this.f17413m = c.PREVIEW_STOPPED;
                }
                if (h().equals("torch")) {
                    a("off");
                }
                if (this.f17413m.a() >= c.CAMERA_OPENED.a()) {
                    this.f17413m = c.CAMERA_CLOSED;
                    Camera camera = this.f17410j;
                    if (camera != null) {
                        camera.setPreviewCallback(null);
                        this.f17410j.stopPreview();
                        this.f17410j.release();
                        this.f17410j = null;
                    }
                    d dVar = this.f17402b;
                    if (dVar != null) {
                        dVar.c();
                    }
                }
            } catch (RuntimeException unused) {
                Log.e("CameraManager", "CameraManager::onPause failed");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void m() {
        c cVar = this.f17413m;
        if (cVar == c.CAMERA_CLOSED || cVar == c.PREVIEW_STOPPED) {
            int iA = a(this.f17401a.b());
            Log.i("CameraManager", "onResume: " + iA);
            try {
                this.f17410j = Camera.open(iA);
            } catch (RuntimeException e2) {
                Log.e("CameraManager", "CameraManager::Camera open failed, " + e2.getMessage());
            }
            if (this.f17410j == null) {
                Log.e("CameraManager", "CameraManager::initCamera failed");
                d dVar = this.f17402b;
                if (dVar != null) {
                    dVar.b();
                }
            } else {
                d dVar2 = this.f17402b;
                if (dVar2 != null) {
                    dVar2.a();
                }
                this.f17413m = c.CAMERA_OPENED;
            }
        }
    }

    public synchronized void n() {
        Camera camera;
        if (this.f17413m.a() < c.CAMERA_OPENED.a()) {
            return;
        }
        if (this.f17401a.c() != 0 && (camera = this.f17410j) != null) {
            camera.setPreviewCallback(new f());
        }
    }

    public synchronized void o() {
        try {
            if (this.f17401a.c() == 1) {
                Log.d("CameraManager", "CameraManager::requestPreviewFrame PREVIEW_ONE_SHOT");
                if (this.f17413m == c.PREVIEW_STOPPED) {
                    return;
                }
                Camera camera = this.f17410j;
                if (camera != null) {
                    camera.setOneShotPreviewCallback(this.f17404d);
                }
            } else if (this.f17401a.c() == 0) {
                Log.d("CameraManager", "CameraManager::requestPreviewFrame PICTURE_MODE");
                if (this.f17413m == c.PREVIEW_STOPPED) {
                    p();
                }
            } else if (this.f17401a.c() == 2) {
                Log.d("CameraManager", "CameraManager::requestPreviewFrame PREVIEW_MULTI_SHOT");
                if (this.f17413m == c.PREVIEW_STOPPED) {
                    return;
                }
                Camera camera2 = this.f17410j;
                if (camera2 != null) {
                    camera2.setPreviewCallback(this.f17404d);
                }
            } else {
                Log.w("CameraManager", "CameraManager::requestPreviewFrame unknown mode");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void p() {
        try {
        } catch (RuntimeException unused) {
            Log.w("CameraManager", "stopPreview error");
        }
        if (this.f17413m.a() < c.CAMERA_INITIALED.a()) {
            Log.w("CameraManager", "CameraManager::startPreview camera is not initialed yet");
            return;
        }
        Camera camera = this.f17410j;
        if (camera != null) {
            camera.startPreview();
            this.f17413m = c.PREVIEW_STARTED;
        }
    }

    public synchronized void q() {
        try {
        } catch (RuntimeException unused) {
            Log.w("CameraManager", "stopPreview error");
        }
        if (this.f17413m.a() < c.PREVIEW_STARTED.a()) {
            Log.w("CameraManager", "CameraManager::startPreview camera is not startPreview yet");
            return;
        }
        Camera camera = this.f17410j;
        if (camera != null) {
            camera.setPreviewCallback(null);
            this.f17410j.stopPreview();
            this.f17413m = c.PREVIEW_STOPPED;
        }
    }

    public synchronized void a(d dVar) {
        if (dVar != null) {
            this.f17402b = dVar;
        } else {
            throw new IllegalArgumentException("CameraManager::setCameraStatusListener param invalid");
        }
    }

    public synchronized k0 c() {
        if (this.f17410j != null && this.f17413m.a() != c.CAMERA_CLOSED.a()) {
            return this.f17409i.a();
        }
        return null;
    }

    public synchronized int d() {
        return this.f17401a.d();
    }

    public synchronized void b(int i2) {
        if (this.f17401a != null && this.f17410j != null && this.f17413m.a() >= c.CAMERA_OPENED.a()) {
            this.f17401a.a(i2);
            try {
                try {
                    this.f17410j.setDisplayOrientation(i2);
                } catch (RuntimeException unused) {
                    Log.e("CameraManager", "setDisplayOrientation RuntimeException");
                }
            } catch (Exception unused2) {
                Log.e("CameraManager", "setDisplayOrientation Exception");
            }
        }
    }

    public synchronized void a(TextureView textureView) throws IOException {
        try {
            if (textureView != null) {
                if (this.f17413m.a() != c.CAMERA_OPENED.a()) {
                    Log.w("CameraManager", "CameraManager::initCamera camera is not opened yet");
                    m();
                }
                this.f17407g.a(this.f17410j);
                this.f17408h.a(this.f17410j);
                this.f17409i.a(this.f17410j);
                Camera camera = this.f17410j;
                if (camera != null) {
                    camera.setPreviewTexture(textureView.getSurfaceTexture());
                }
                this.f17411k.a(this.f17410j, this.f17401a);
                Camera camera2 = this.f17410j;
                if (camera2 != null) {
                    camera2.setDisplayOrientation(this.f17401a.d());
                }
                b bVar = this.f17403c;
                if (bVar != null) {
                    bVar.a(this.f17411k.a());
                }
                this.f17413m = c.CAMERA_INITIALED;
            } else {
                throw new IllegalArgumentException("CameraManager::initCamera SurfaceHolder is null");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void a(String str) {
        try {
        } catch (RuntimeException unused) {
            Log.w("CameraManager", "CameraManager::setTorchStatus error");
        }
        if (this.f17410j != null && this.f17413m.a() != c.CAMERA_CLOSED.a()) {
            if ("off".equals(str) || "torch".equals(str)) {
                Camera.Parameters parameters = this.f17410j.getParameters();
                parameters.setFlashMode(str);
                this.f17410j.setParameters(parameters);
                this.f17412l = str;
            }
        }
    }

    public synchronized void a(Rect rect, boolean z2) {
        try {
            if (this.f17410j == null) {
                return;
            }
            if (this.f17406f == null) {
                this.f17406f = new i0(this.f17410j);
            }
            this.f17406f.a(rect, this.f17411k.a().x, this.f17411k.a().y, z2, this.f17401a.b() == 1);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void a() {
        i0 i0Var = this.f17406f;
        if (i0Var != null) {
            i0Var.e();
            this.f17406f = null;
        }
    }

    public synchronized void a(List<k0.a> list) {
        if (this.f17410j != null && this.f17413m.a() != c.CAMERA_CLOSED.a()) {
            this.f17409i.a(list);
        }
    }

    private int a(int i2) {
        if (i2 != 0 && i2 != 1) {
            return 0;
        }
        try {
            int numberOfCameras = Camera.getNumberOfCameras();
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i3 = 0; i3 < numberOfCameras; i3++) {
                Camera.getCameraInfo(i3, cameraInfo);
                if (cameraInfo.facing == i2) {
                    Log.i("CameraManager", "findCameraId: " + i3);
                    return i3;
                }
            }
        } catch (RuntimeException unused) {
            Log.e("CameraManager", "getCameraInfo RuntimeException");
        } catch (Exception unused2) {
            Log.e("CameraManager", "getCameraInfo Exception");
        }
        return 0;
    }
}

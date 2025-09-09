package com.huawei.hms.scankit.p;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes4.dex */
public class i0 implements Camera.AutoFocusCallback {

    /* renamed from: i, reason: collision with root package name */
    private static final Set<String> f17348i;

    /* renamed from: a, reason: collision with root package name */
    private final boolean f17349a;

    /* renamed from: b, reason: collision with root package name */
    private Camera f17350b;

    /* renamed from: c, reason: collision with root package name */
    private AsyncTask<?, ?, ?> f17351c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f17352d = false;

    /* renamed from: e, reason: collision with root package name */
    private boolean f17353e = false;

    /* renamed from: f, reason: collision with root package name */
    private int f17354f = -1;

    /* renamed from: g, reason: collision with root package name */
    private int f17355g = 2;

    /* renamed from: h, reason: collision with root package name */
    private String f17356h = null;

    private static class a extends AsyncTask<Object, Object, Object> {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<i0> f17357a;

        a(i0 i0Var) {
            this.f17357a = new WeakReference<>(i0Var);
        }

        @Override // android.os.AsyncTask
        protected Object doInBackground(Object... objArr) throws InterruptedException {
            i0 i0Var = this.f17357a.get();
            if (i0Var == null) {
                return null;
            }
            i0Var.d();
            try {
                Thread.sleep(Math.max(i0Var.c(), 0));
            } catch (InterruptedException unused) {
                Log.e("CameraManager", "CameraFocusManager::doInBackground InterruptedException");
            }
            return null;
        }
    }

    static {
        HashSet hashSet = new HashSet();
        f17348i = hashSet;
        hashSet.add("auto");
        hashSet.add("macro");
    }

    i0(Camera camera) {
        String focusMode;
        this.f17350b = camera;
        try {
            focusMode = camera.getParameters().getFocusMode();
        } catch (RuntimeException e2) {
            Log.e("CameraManager", "Unexpected exception while getFocusMode" + e2.getMessage());
            focusMode = "auto";
        }
        boolean zContains = f17348i.contains(focusMode);
        this.f17349a = zContains;
        Log.i("CameraManager", "CameraFocusManager useAutoFocus： " + zContains);
    }

    private synchronized void b() {
        try {
            AsyncTask<?, ?, ?> asyncTask = this.f17351c;
            if (asyncTask != null) {
                if (asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
                    this.f17351c.cancel(true);
                }
                this.f17351c = null;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized int c() {
        return this.f17354f;
    }

    public synchronized void d() {
        if (this.f17349a) {
            this.f17351c = null;
            if (!this.f17352d && !this.f17353e) {
                try {
                    this.f17350b.autoFocus(this);
                    this.f17353e = true;
                } catch (RuntimeException e2) {
                    Log.w("CameraManager", "Unexpected exception while focusing" + e2.getMessage());
                    a();
                }
            }
        }
    }

    synchronized void e() {
        this.f17352d = true;
        if (this.f17349a) {
            b();
            try {
                Camera camera = this.f17350b;
                if (camera != null) {
                    camera.cancelAutoFocus();
                }
            } catch (RuntimeException e2) {
                Log.w("CameraManager", "Unexpected exception while cancelling focusing" + e2.getMessage());
            }
        }
    }

    @Override // android.hardware.Camera.AutoFocusCallback
    public synchronized void onAutoFocus(boolean z2, Camera camera) {
        this.f17353e = false;
        a();
        if (camera != null) {
            try {
                camera.cancelAutoFocus();
            } catch (RuntimeException e2) {
                Log.i("CameraManager", "Unexpected exception while cancelling focusing" + e2.getMessage());
            }
            try {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFocusMode(this.f17356h);
                camera.setParameters(parameters);
            } catch (RuntimeException e3) {
                Log.i("CameraManager", "CameraFocusManager::setCameraFocusArea failed: " + e3.getMessage());
            }
        }
    }

    private synchronized void a() {
        if (!this.f17352d && this.f17351c == null) {
            a aVar = new a(this);
            try {
                aVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                this.f17351c = aVar;
            } catch (RejectedExecutionException e2) {
                Log.w("CameraManager", "CameraFocusManager::autoFocusAgainLater RejectedExecutionException: " + e2.getMessage());
            }
        }
    }

    public void a(Rect rect, int i2, int i3, boolean z2, boolean z3) {
        int i4 = this.f17355g;
        if (i4 < 1) {
            return;
        }
        this.f17355g = i4 - 1;
        Rect rectA = a(rect.centerX(), rect.centerY(), 1.0f, i3, i2, false, z2 ? 90 : 0);
        if (rectA == null) {
            this.f17355g--;
            return;
        }
        Camera camera = this.f17350b;
        if (camera != null) {
            try {
                camera.cancelAutoFocus();
                Camera.Parameters parameters = this.f17350b.getParameters();
                if (parameters.getMaxNumFocusAreas() <= 0) {
                    Log.i("CameraManager", "focus areas not supported");
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new Camera.Area(rectA, 1000));
                    parameters.setFocusAreas(arrayList);
                }
                if (parameters.getMaxNumMeteringAreas() <= 0) {
                    Log.i("CameraManager", "metering areas not supported");
                } else {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(new Camera.Area(rectA, 1000));
                    parameters.setMeteringAreas(arrayList2);
                }
                this.f17356h = parameters.getFocusMode();
                parameters.setFocusMode("macro");
                this.f17350b.setParameters(parameters);
                this.f17350b.autoFocus(this);
            } catch (RuntimeException e2) {
                Log.i("CameraManager", "CameraFocusManager::setCameraFocusArea failed: " + e2.getMessage());
                this.f17355g = this.f17355g + 1;
            }
        }
    }

    private static Rect a(float f2, float f3, float f4, int i2, int i3, boolean z2, int i4) {
        int i5 = (int) (((f2 / i2) * 2000.0f) - 1000.0f);
        int i6 = (int) (((f3 / i3) * 2000.0f) - 1000.0f);
        RectF rectF = new RectF(a(i5 - 150), a(i6 - 150), a(i5 + 150), a(i6 + 150));
        Matrix matrix = new Matrix();
        try {
            a(matrix, z2, i4);
            matrix.mapRect(rectF);
            return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
        } catch (RuntimeException e2) {
            Log.i("CameraManager", "CameraFocusManager::prepareMatrix failed: " + e2.getMessage());
            return null;
        }
    }

    public static void a(Matrix matrix, boolean z2, int i2) {
        if (matrix == null) {
            return;
        }
        Matrix matrix2 = new Matrix();
        try {
            matrix.reset();
            matrix2.setScale(z2 ? -1.0f : 1.0f, 1.0f);
            matrix2.postRotate(i2);
            matrix2.invert(matrix);
        } catch (RuntimeException e2) {
            Log.i("CameraManager", "CameraFocusManager::prepareMatrix failed: " + e2.getMessage());
        }
    }

    private static int a(int i2) {
        if (i2 > 1000) {
            return 1000;
        }
        return Math.max(i2, -1000);
    }
}

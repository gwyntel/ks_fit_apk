package com.huawei.hms.scankit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import androidx.constraintlayout.motion.widget.Key;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.hmsscankit.api.IOnErrorCallback;
import com.huawei.hms.hmsscankit.api.IOnResultCallback;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import com.huawei.hms.scankit.p.e0;
import com.huawei.hms.scankit.p.e5;
import com.huawei.hms.scankit.p.f5;
import com.huawei.hms.scankit.p.j0;
import com.huawei.hms.scankit.p.k0;
import com.huawei.hms.scankit.p.l1;
import com.huawei.hms.scankit.p.m0;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.v3;
import com.huawei.hms.scankit.p.w3;
import com.huawei.hms.scankit.p.w7;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.yc.utesdk.ble.close.KeyType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes4.dex */
public class b {
    public static final String H = "b";
    public static volatile v3 I;
    public static volatile w3 J;
    private IOnErrorCallback D;

    /* renamed from: a, reason: collision with root package name */
    private final Rect f16813a;

    /* renamed from: b, reason: collision with root package name */
    private final int f16814b;

    /* renamed from: c, reason: collision with root package name */
    private final boolean f16815c;

    /* renamed from: d, reason: collision with root package name */
    private Context f16816d;

    /* renamed from: e, reason: collision with root package name */
    private com.huawei.hms.scankit.a f16817e;

    /* renamed from: f, reason: collision with root package name */
    private f5 f16818f;

    /* renamed from: g, reason: collision with root package name */
    private j0 f16819g;

    /* renamed from: h, reason: collision with root package name */
    private ViewfinderView f16820h;

    /* renamed from: i, reason: collision with root package name */
    public TextureView f16821i;

    /* renamed from: j, reason: collision with root package name */
    private TextureView.SurfaceTextureListener f16822j;

    /* renamed from: k, reason: collision with root package name */
    private Collection<BarcodeFormat> f16823k;

    /* renamed from: l, reason: collision with root package name */
    private Map<l1, ?> f16824l;

    /* renamed from: m, reason: collision with root package name */
    private String f16825m;

    /* renamed from: o, reason: collision with root package name */
    private String f16827o;

    /* renamed from: q, reason: collision with root package name */
    private float f16829q;

    /* renamed from: u, reason: collision with root package name */
    private boolean f16833u;

    /* renamed from: v, reason: collision with root package name */
    private boolean f16834v;

    /* renamed from: w, reason: collision with root package name */
    private boolean f16835w;

    /* renamed from: x, reason: collision with root package name */
    private IObjectWrapper f16836x;

    /* renamed from: y, reason: collision with root package name */
    private e5 f16837y;

    /* renamed from: z, reason: collision with root package name */
    private IOnResultCallback f16838z;

    /* renamed from: p, reason: collision with root package name */
    private boolean f16828p = true;

    /* renamed from: r, reason: collision with root package name */
    private boolean f16830r = true;

    /* renamed from: s, reason: collision with root package name */
    private boolean f16831s = false;

    /* renamed from: t, reason: collision with root package name */
    private boolean f16832t = true;
    private boolean A = false;
    private boolean B = false;
    private boolean E = true;
    private boolean F = false;
    private boolean G = false;

    /* renamed from: n, reason: collision with root package name */
    private boolean f16826n = false;
    private boolean C = false;

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            b.this.f16819g.m();
        }
    }

    /* renamed from: com.huawei.hms.scankit.b$b, reason: collision with other inner class name */
    class C0137b implements j0.d {
        C0137b() {
        }

        @Override // com.huawei.hms.scankit.p.j0.d
        public void a() {
        }

        @Override // com.huawei.hms.scankit.p.j0.d
        public void b() {
            if (b.this.D != null) {
                try {
                    b.this.D.onError(-1000);
                } catch (RemoteException unused) {
                    o4.b(b.H, "RemoteException");
                }
            }
        }

        @Override // com.huawei.hms.scankit.p.j0.d
        public void c() {
        }
    }

    class c implements TextureView.SurfaceTextureListener {
        c() {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
            b.this.B = false;
            if (surfaceTexture == null) {
                o4.b(b.H, "*** WARNING *** surfaceCreated() gave us a null surface!");
            }
            if (b.this.f16826n) {
                return;
            }
            b.this.f16826n = true;
            if (b.this.f16816d.checkPermission("android.permission.CAMERA", Process.myPid(), Process.myUid()) == 0) {
                b bVar = b.this;
                bVar.a(bVar.f16821i);
            } else {
                if (b.this.G || !(b.this.f16816d instanceof Activity)) {
                    return;
                }
                b.this.B = true;
                ((Activity) b.this.f16816d).requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            b.this.f16826n = false;
            return true;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    }

    class d implements f5 {
        d() {
        }

        @Override // com.huawei.hms.scankit.p.f5
        public void a(HmsScan[] hmsScanArr, Bitmap bitmap, float f2) {
            b.this.a(hmsScanArr, bitmap);
        }
    }

    public b(Context context, TextureView textureView, ViewfinderView viewfinderView, Rect rect, int i2, IObjectWrapper iObjectWrapper, boolean z2, String str, boolean z3) {
        this.f16816d = context;
        this.f16820h = viewfinderView;
        this.f16836x = iObjectWrapper;
        this.f16821i = textureView;
        this.f16813a = rect;
        this.f16814b = i2;
        this.f16815c = z2;
        this.f16827o = str;
        this.f16834v = z3;
    }

    private void j() throws InterruptedException {
        com.huawei.hms.scankit.a aVar = this.f16817e;
        if (aVar != null) {
            aVar.e();
            this.f16817e = null;
        }
        this.f16819g.l();
    }

    public void f() {
        TextureView textureView;
        TextureView textureView2;
        this.C = false;
        try {
            I = new v3((Bundle) ObjectWrapper.unwrap(this.f16836x), this.f16827o);
            I.h();
        } catch (RuntimeException unused) {
            o4.b(H, "RuntimeException");
        } catch (Exception unused2) {
            o4.b(H, "Exception");
        }
        if (!this.A && !this.f16826n && (textureView2 = this.f16821i) != null) {
            textureView2.setSurfaceTextureListener(this.f16822j);
            if (this.f16826n) {
                a(this.f16821i);
            } else {
                this.f16821i.setSurfaceTextureListener(this.f16822j);
            }
        }
        if (this.B && this.f16816d.checkPermission("android.permission.CAMERA", Process.myPid(), Process.myUid()) == 0 && (textureView = this.f16821i) != null) {
            this.B = false;
            a(textureView);
        }
    }

    public void g() {
        this.C = false;
        TextureView textureView = this.f16821i;
        if (textureView != null) {
            textureView.setSurfaceTextureListener(this.f16822j);
            this.A = true;
            if (this.f16826n) {
                a(this.f16821i);
            } else {
                this.f16821i.setSurfaceTextureListener(this.f16822j);
            }
        }
    }

    public void h() throws InterruptedException {
        this.C = true;
        if (I != null) {
            I.i();
        }
        I = null;
        if (this.A) {
            j();
        }
    }

    public void i() {
        try {
            j0 j0Var = this.f16819g;
            if (j0Var != null) {
                j0Var.d(1);
            }
        } catch (RuntimeException unused) {
            o4.b(H, "RuntimeException in reset zoomValue");
        } catch (Exception unused2) {
            o4.b(H, "Exception in reset zoomValue");
        }
    }

    public void c() {
        this.C = false;
        try {
            J = new w3((Bundle) ObjectWrapper.unwrap(this.f16836x), this.f16827o);
            J.a("single");
        } catch (RuntimeException unused) {
            o4.b(H, "RuntimeException");
        } catch (Exception unused2) {
            o4.b(H, "Exception");
        }
        if (this.f16816d.getPackageManager() != null && !this.f16816d.getPackageManager().hasSystemFeature("android.hardware.camera")) {
            Log.e("scankit", "has no camera");
            return;
        }
        e0 e0VarA = a(this.f16816d);
        Log.i(H, "onCreate: CameraManageOncreate");
        this.f16819g = new j0(this.f16816d, e0VarA);
        new Thread(new a()).start();
        this.f16819g.a(new C0137b());
        this.f16822j = new c();
        this.f16818f = new d();
    }

    public void d() {
        this.C = true;
        this.f16821i.setSurfaceTextureListener(null);
        this.f16819g.k();
        J.f17921l.b();
        J = null;
    }

    public void e() throws InterruptedException {
        this.C = true;
        if (this.A) {
            return;
        }
        j();
    }

    public boolean b(MotionEvent motionEvent) {
        j0 j0Var = this.f16819g;
        if (j0Var == null || !this.f16828p || j0Var.f().a() < j0.c.CAMERA_OPENED.a() || motionEvent.getPointerCount() <= 1) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 2) {
            float fA = a(motionEvent);
            float f2 = this.f16829q;
            if (fA > f2 + 6.0f) {
                a(true, this.f16819g);
            } else if (fA < f2 - 6.0f) {
                a(false, this.f16819g);
            } else {
                o4.d("CaptureHelper", "MotionEvent.ACTION_MOVE no handleZoom");
            }
            this.f16829q = fA;
        } else if (action == 5) {
            this.f16829q = a(motionEvent);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(TextureView textureView) {
        if (textureView != null && textureView.getSurfaceTexture() != null) {
            try {
                this.f16819g.a(textureView);
                this.f16819g.a(Collections.singletonList(new k0.a(new Rect(-150, -150, 150, 150), 1000)));
                try {
                    this.f16819g.n();
                } catch (Exception unused) {
                    o4.b(H, "initCamera() get exception");
                }
                if (this.f16817e == null) {
                    com.huawei.hms.scankit.a aVar = new com.huawei.hms.scankit.a(this.f16816d, this.f16820h, this.f16818f, this.f16823k, this.f16824l, this.f16825m, this.f16819g, this.f16813a, this.f16814b, this.f16834v, this.E);
                    this.f16817e = aVar;
                    aVar.c(this.f16833u);
                    this.f16817e.a(this.f16835w);
                    this.f16817e.b(this.f16830r);
                    this.f16817e.a(this.f16837y);
                    return;
                }
                return;
            } catch (Exception e2) {
                if (I != null) {
                    I.c(XiaomiOAuthConstants.ERROR_LOGIN_FAILED);
                }
                o4.a(H, "initCamera IOException", e2);
                return;
            }
        }
        o4.e(H, "initCamera() no surface view");
    }

    public void b(boolean z2) {
        this.E = z2;
    }

    public boolean b() {
        return this.F;
    }

    public void c(boolean z2) {
        this.G = z2;
    }

    private void a(boolean z2, j0 j0Var) {
        try {
            m0 m0VarG = j0Var.g();
            if (j0Var.j()) {
                int iC = m0VarG.c();
                int iB = m0VarG.b();
                if (z2 && iB < iC) {
                    iB++;
                } else if (iB > 0) {
                    iB--;
                } else {
                    o4.d(H, "handleZoom  zoom not change");
                }
                j0Var.d(iB);
                return;
            }
            o4.d(H, "zoom not supported");
        } catch (RuntimeException unused) {
            Log.e(H, "handleZoom: RuntimeException");
        }
    }

    private float a(MotionEvent motionEvent) {
        float x2 = motionEvent.getX(0) - motionEvent.getX(1);
        float y2 = motionEvent.getY(0) - motionEvent.getY(1);
        double dSqrt = Math.sqrt((x2 * x2) + (y2 * y2));
        if (Double.isInfinite(dSqrt) || Double.isNaN(dSqrt)) {
            return 0.0f;
        }
        return BigDecimal.valueOf(dSqrt).floatValue();
    }

    public void a(HmsScan[] hmsScanArr, Bitmap bitmap) {
        o4.a("scan-time", "decode time:" + System.currentTimeMillis());
        try {
            String str = H;
            o4.d(str, "result onResult");
            if (this.f16837y.a()) {
                o4.d(str, "result intercepted");
                return;
            }
            if (I != null) {
                I.a(hmsScanArr);
            }
            if (!this.f16815c) {
                hmsScanArr = w7.a(hmsScanArr);
            }
            if (this.f16837y != null) {
                if (this.f16820h != null && hmsScanArr.length > 0 && hmsScanArr[0] != null) {
                    o4.d(str, "result draw result point");
                    if (this.f16816d instanceof Activity) {
                        this.f16820h.a(hmsScanArr[0].getBorderRect(), w7.c((Activity) this.f16816d), this.f16819g.e());
                    }
                    this.C = false;
                }
                this.f16837y.a(hmsScanArr);
            }
            if (this.f16838z != null) {
                try {
                    o4.d(str, "result callback end: pauseStatus" + this.C);
                    if (this.C) {
                        return;
                    }
                    if (this.f16835w && hmsScanArr != null && hmsScanArr.length > 0 && hmsScanArr[0] != null) {
                        Context context = this.f16816d;
                        if (context instanceof Activity) {
                            hmsScanArr[0].originalBitmap = w7.a(bitmap, ((Activity) context).getWindowManager().getDefaultDisplay().getRotation());
                        }
                    }
                    this.f16838z.onResult(hmsScanArr);
                } catch (RemoteException e2) {
                    if (I != null) {
                        I.c(XiaomiOAuthConstants.ERROR_AUTH_FAILED);
                    }
                    o4.e("CaptureHelper", "onResult  RemoteException  e:" + e2);
                }
            }
        } catch (RuntimeException e3) {
            Log.e(H, "onResult:RuntimeException " + e3);
        } catch (Exception e4) {
            Log.e(H, "onResult:Exception: " + e4);
        }
    }

    public b a(boolean z2) {
        this.f16835w = z2;
        com.huawei.hms.scankit.a aVar = this.f16817e;
        if (aVar != null) {
            aVar.a(z2);
        }
        return this;
    }

    public b a(e5 e5Var) {
        this.f16837y = e5Var;
        return this;
    }

    public void a(IOnResultCallback iOnResultCallback) {
        this.f16838z = iOnResultCallback;
    }

    public j0 a() {
        return this.f16819g;
    }

    public void a(IOnErrorCallback iOnErrorCallback) {
        this.D = iOnErrorCallback;
    }

    private e0 a(Context context) {
        e0 e0VarA;
        Activity activity = (Activity) context;
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Log.i(H, "initCameraConfig:false" + Key.ROTATION + rotation);
        if (rotation == 0) {
            e0VarA = new e0.b().a(new Point(1920, 1080)).a(1).b(90).b(false).a(true).a();
        } else if (rotation == 1) {
            e0VarA = new e0.b().a(new Point(1920, 1080)).a(1).b(0).b(false).a(true).a();
        } else if (rotation == 2) {
            e0VarA = new e0.b().a(new Point(1920, 1080)).a(1).b(KeyType.QUERY_BRIGHT_SCREEN_PARAM).b(false).a(true).a();
        } else if (rotation != 3) {
            e0VarA = new e0.b().a(new Point(1920, 1080)).a(1).b(90).b(false).a(true).a();
        } else {
            e0VarA = new e0.b().a(new Point(1920, 1080)).a(1).b(180).b(false).a(true).a();
        }
        if (w7.f(context) || w7.b(activity) || w7.e(context)) {
            e0VarA.a(new Point(1080, 1080));
            this.F = true;
        }
        if ("ceres-c3".equals(Build.DEVICE)) {
            e0VarA = new e0.b().a(new Point(1080, 1920)).a(1).b(false).a(true).a();
        }
        boolean zB = w7.b();
        boolean zE = w7.e();
        if ((!w7.e(context) || zB) && (!w7.b(activity) || zE)) {
            return e0VarA;
        }
        e0 e0VarA2 = new e0.b().a(new Point(1080, 1080)).a(1).b(90).b(false).a(true).a();
        this.F = true;
        return e0VarA2;
    }
}

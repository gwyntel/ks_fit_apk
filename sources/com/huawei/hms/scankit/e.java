package com.huawei.hms.scankit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.huawei.hms.feature.DynamicModuleInitializer;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.hmsscankit.DetailRect;
import com.huawei.hms.hmsscankit.api.IOnErrorCallback;
import com.huawei.hms.hmsscankit.api.IOnLightCallback;
import com.huawei.hms.hmsscankit.api.IOnResultCallback;
import com.huawei.hms.hmsscankit.api.IRemoteViewDelegate;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.scankit.p.e5;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.r3;
import com.huawei.hms.scankit.p.r6;
import com.huawei.hms.scankit.p.w3;
import com.huawei.hms.scankit.p.w7;
import com.huawei.hms.scankit.util.OpencvJNI;
import com.umeng.analytics.pro.bc;
import com.yc.utesdk.ble.close.KeyType;
import io.flutter.plugin.platform.PlatformPlugin;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class e extends IRemoteViewDelegate.Stub implements e5, SensorEventListener {

    /* renamed from: y, reason: collision with root package name */
    private static final String f16899y = "e";

    /* renamed from: z, reason: collision with root package name */
    protected static boolean f16900z = false;

    /* renamed from: b, reason: collision with root package name */
    protected int f16902b;

    /* renamed from: c, reason: collision with root package name */
    protected Context f16903c;

    /* renamed from: d, reason: collision with root package name */
    protected ProviderRemoteView f16904d;

    /* renamed from: e, reason: collision with root package name */
    protected TextureView f16905e;

    /* renamed from: f, reason: collision with root package name */
    protected com.huawei.hms.scankit.b f16906f;

    /* renamed from: g, reason: collision with root package name */
    protected IOnResultCallback f16907g;

    /* renamed from: h, reason: collision with root package name */
    protected SensorManager f16908h;

    /* renamed from: i, reason: collision with root package name */
    protected View.OnClickListener f16909i;

    /* renamed from: l, reason: collision with root package name */
    protected Boolean f16912l;

    /* renamed from: m, reason: collision with root package name */
    protected AlertDialog f16913m;

    /* renamed from: n, reason: collision with root package name */
    protected Rect f16914n;

    /* renamed from: o, reason: collision with root package name */
    private IObjectWrapper f16915o;

    /* renamed from: p, reason: collision with root package name */
    protected boolean f16916p;

    /* renamed from: q, reason: collision with root package name */
    private OrientationEventListener f16917q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f16918r;

    /* renamed from: s, reason: collision with root package name */
    protected boolean f16919s;

    /* renamed from: v, reason: collision with root package name */
    protected IOnLightCallback f16922v;

    /* renamed from: w, reason: collision with root package name */
    protected LinearLayout f16923w;

    /* renamed from: a, reason: collision with root package name */
    private volatile w3 f16901a = null;

    /* renamed from: j, reason: collision with root package name */
    protected boolean f16910j = false;

    /* renamed from: k, reason: collision with root package name */
    protected final Float f16911k = Float.valueOf(40.0f);

    /* renamed from: t, reason: collision with root package name */
    protected boolean f16920t = true;

    /* renamed from: u, reason: collision with root package name */
    private Point f16921u = null;

    /* renamed from: x, reason: collision with root package name */
    boolean f16924x = false;

    class a implements View.OnTouchListener {
        a() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            e.this.f16906f.b(motionEvent);
            return true;
        }
    }

    class b extends OrientationEventListener {
        b(Context context) {
            super(context);
        }

        @Override // android.view.OrientationEventListener
        public void onOrientationChanged(int i2) {
            int rotation = ((Activity) e.this.f16903c).getWindowManager().getDefaultDisplay().getRotation();
            boolean zB = w7.b();
            boolean zE = w7.e();
            if (w7.e(e.this.f16903c) && !zB) {
                e.this.a(90);
                return;
            }
            if (w7.b((Activity) e.this.f16903c) && !zE) {
                e.this.a(90);
                return;
            }
            if (rotation == 0) {
                e.this.a(90);
                return;
            }
            if (rotation == 1) {
                e.this.a(0);
            } else if (rotation == 2) {
                e.this.a(KeyType.QUERY_BRIGHT_SCREEN_PARAM);
            } else {
                if (rotation != 3) {
                    return;
                }
                e.this.a(180);
            }
        }
    }

    class c implements View.OnClickListener {
        c() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            e.this.f16913m.dismiss();
        }
    }

    public e(Context context, int i2, Object obj, IObjectWrapper iObjectWrapper, boolean z2, boolean z3, boolean z4) {
        this.f16902b = 0;
        this.f16916p = false;
        this.f16903c = context;
        this.f16902b = i2;
        this.f16915o = iObjectWrapper;
        if (obj instanceof Rect) {
            this.f16914n = (Rect) obj;
        } else {
            this.f16914n = null;
        }
        this.f16916p = z2;
        this.f16918r = z3;
        this.f16919s = z4;
    }

    protected void b(Point point, boolean z2) {
        int i2;
        int i3;
        float f2;
        float f3;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.f16905e.getLayoutParams();
        float f4 = point.x;
        float f5 = point.y;
        boolean zB = w7.b();
        o4.d(f16899y, "initSurfaceViewSize: isPortraitScreen: " + w7.c((Activity) this.f16903c) + " inMultiWindow: " + w7.f(this.f16903c) + " isInMultiWindowFreeform: " + w7.b((Activity) this.f16903c) + " isPadOrFold: " + w7.j(this.f16903c) + " isFoldStateExpand: " + w7.h(this.f16903c) + " isPad: " + w7.i(this.f16903c) + " inMagicWindow: " + w7.e(this.f16903c) + " ignore: " + zB + " screen: " + point.toString() + " width: " + layoutParams.width + " height: " + layoutParams.height + " inMagicWindow " + w7.e(this.f16903c) + " ignore " + zB + " isInit " + z2 + " isSpecialExpectSize " + this.f16906f.b());
        if (w7.c((Activity) this.f16903c) || (w7.e(this.f16903c) && !(w7.e(this.f16903c) && zB))) {
            f16900z = false;
            boolean zEquals = "ceres-c3".equals(Build.DEVICE);
            int i4 = PlatformPlugin.DEFAULT_SYSTEM_UI;
            if (zEquals) {
                i2 = 1280;
                i3 = 1280;
            } else {
                i2 = 1080;
                i3 = 1920;
            }
            if (z2 && (w7.f(this.f16903c) || w7.b((Activity) this.f16903c) || w7.e(this.f16903c))) {
                i2 = 1280;
                i3 = 1280;
            }
            if (this.f16906f.b()) {
                i3 = 1280;
            } else {
                i4 = i2;
            }
            float f6 = i4;
            float f7 = f4 / f6;
            float f8 = i3;
            float f9 = f5 / f8;
            if (f7 > f9) {
                layoutParams.width = -1;
                layoutParams.height = (int) (f8 * f7);
                layoutParams.gravity = 17;
            } else {
                layoutParams.height = -1;
                layoutParams.width = (int) (f6 * f9);
                layoutParams.gravity = 17;
            }
        } else {
            f16900z = true;
            float f10 = 1280.0f;
            if (z2 && (w7.f(this.f16903c) || w7.b((Activity) this.f16903c) || w7.e(this.f16903c))) {
                f2 = 1280.0f;
                f3 = 1280.0f;
            } else {
                f2 = 1920.0f;
                f3 = 1080.0f;
            }
            if (this.f16906f.b()) {
                f3 = 1280.0f;
            } else {
                f10 = f2;
            }
            float f11 = f4 / f10;
            float f12 = f5 / f3;
            if (f11 > f12) {
                layoutParams.width = -1;
                layoutParams.height = (int) (f3 * f11);
                layoutParams.gravity = 17;
            } else {
                layoutParams.height = -1;
                layoutParams.width = (int) (f10 * f12);
                layoutParams.gravity = 17;
            }
        }
        this.f16905e.setLayoutParams(layoutParams);
    }

    protected void c() {
        Object systemService = this.f16903c.getSystemService(bc.ac);
        if (systemService instanceof SensorManager) {
            SensorManager sensorManager = (SensorManager) systemService;
            this.f16908h = sensorManager;
            Iterator<Sensor> it = sensorManager.getSensorList(-1).iterator();
            while (it.hasNext()) {
                if (5 == it.next().getType()) {
                    this.f16910j = true;
                    return;
                }
            }
        }
    }

    protected ProviderRemoteView d() {
        return new ProviderRemoteView(DynamicModuleInitializer.getContext() == null ? this.f16903c : DynamicModuleInitializer.getContext(), true);
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public HmsScan[] decodeWithBitmap(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) throws RemoteException {
        boolean z2;
        boolean z3;
        boolean z4;
        if (!r3.A) {
            OpencvJNI.init();
        }
        Bundle bundle = (iObjectWrapper2 == null || !(ObjectWrapper.unwrap(iObjectWrapper2) instanceof Bundle)) ? new Bundle() : (Bundle) ObjectWrapper.unwrap(iObjectWrapper2);
        if (iObjectWrapper2 == null || !(ObjectWrapper.unwrap(iObjectWrapper2) instanceof Bundle)) {
            z2 = false;
            z3 = false;
            z4 = true;
        } else {
            z2 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.USE_APK, false);
            z3 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.SUPPORT_ROLLBACK, false);
            z4 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getBoolean(DetailRect.PARSE_RESULT, true);
        }
        r3.f17719f = z4;
        if (z3 && !r3.f17714a && z2) {
            return new HmsScan[]{r6.b()};
        }
        if (this.f16901a == null) {
            try {
                this.f16901a = new w3(bundle, DetailRect.PHOTO_MODE);
            } catch (RuntimeException unused) {
                o4.b(f16899y, "RuntimeException");
            } catch (Exception unused2) {
                o4.b(f16899y, "Exception");
            }
        }
        return a(iObjectWrapper, iObjectWrapper2);
    }

    protected void e() {
        ProviderRemoteView providerRemoteViewD = d();
        this.f16904d = providerRemoteViewD;
        this.f16905e = (TextureView) providerRemoteViewD.findViewById(R.id.surfaceView);
        com.huawei.hms.scankit.b bVar = new com.huawei.hms.scankit.b(this.f16903c, this.f16905e, null, this.f16914n, this.f16902b, this.f16915o, this.f16916p, "CustomizedView", true);
        this.f16906f = bVar;
        bVar.b(this.f16919s);
        c();
        a((Point) null, true);
    }

    protected void f() {
        try {
            com.huawei.hms.scankit.b bVar = this.f16906f;
            if (bVar == null || bVar.a() == null) {
                return;
            }
            this.f16906f.a().a("off");
        } catch (RuntimeException unused) {
            o4.b(f16899y, "offFlashRuntimeException");
        } catch (Exception unused2) {
            o4.b(f16899y, "offFlashException");
        }
    }

    public void g() {
        try {
            com.huawei.hms.scankit.b bVar = this.f16906f;
            if (bVar == null || bVar.a() == null) {
                return;
            }
            this.f16906f.a().a("torch");
        } catch (RuntimeException unused) {
            o4.b(f16899y, "openFlashRuntimeException");
        } catch (Exception unused2) {
            o4.b(f16899y, "openFlashException");
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public boolean getLightStatus() throws RemoteException {
        return b();
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public IObjectWrapper getView() {
        return ObjectWrapper.wrap(this.f16904d);
    }

    protected void h() {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(this.f16903c).create();
        this.f16913m = alertDialogCreate;
        alertDialogCreate.show();
        View viewInflate = LayoutInflater.from(DynamicModuleInitializer.getContext() == null ? this.f16903c : DynamicModuleInitializer.getContext()).inflate(R.layout.scankit_dialog_layout, (ViewGroup) null);
        Window window = this.f16913m.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.y = 60;
        window.setAttributes(attributes);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setContentView(viewInflate);
        window.setGravity(80);
        viewInflate.findViewById(R.id.dialog_sure_btn).setOnClickListener(new c());
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i2) {
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onCreate(Bundle bundle) {
        Context context = this.f16903c;
        if ((context instanceof Activity) && ((Activity) context).getWindow() != null) {
            ((Activity) this.f16903c).getWindow().setFlags(16777216, 16777216);
        }
        Context context2 = this.f16903c;
        if (context2 != null && context2.getPackageManager() != null) {
            this.f16920t = this.f16903c.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
            o4.d("Scankit", "initlight hasFlash " + this.f16920t);
        }
        e();
        this.f16906f.a(this);
        this.f16904d.setOnTouchListener(new a());
        IOnResultCallback iOnResultCallback = this.f16907g;
        if (iOnResultCallback != null) {
            this.f16906f.a(iOnResultCallback);
        }
        this.f16906f.a(this.f16918r);
        this.f16906f.c();
        if (Build.VERSION.SDK_INT >= 24) {
            b bVar = new b(this.f16903c);
            this.f16917q = bVar;
            if (bVar.canDetectOrientation()) {
                this.f16917q.enable();
            } else {
                this.f16917q.disable();
            }
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onDestroy() {
        try {
            this.f16906f.d();
            OrientationEventListener orientationEventListener = this.f16917q;
            if (orientationEventListener != null && orientationEventListener.canDetectOrientation()) {
                this.f16917q.disable();
            }
            if (this.f16903c != null) {
                this.f16903c = null;
            }
            AlertDialog alertDialog = this.f16913m;
            if (alertDialog == null || !alertDialog.isShowing()) {
                return;
            }
            this.f16913m.dismiss();
            this.f16913m = null;
        } catch (RuntimeException unused) {
            o4.b(f16899y, "onDestroyRuntimeException");
        } catch (Exception unused2) {
            o4.b(f16899y, "onDestroyException");
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onPause() {
        try {
            this.f16906f.e();
            this.f16908h.unregisterListener(this);
        } catch (RuntimeException unused) {
            o4.b(f16899y, "onPauseRuntimeException");
        } catch (Exception unused2) {
            o4.b(f16899y, "onPauseException");
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onResume() {
        try {
            this.f16906f.f();
            SensorManager sensorManager = this.f16908h;
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(5), 2);
        } catch (RuntimeException unused) {
            o4.b(f16899y, "onResumeRuntimeException");
        } catch (Exception unused2) {
            o4.b(f16899y, "onResumeException");
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.f16910j && sensorEvent.sensor.getType() == 5 && this.f16920t) {
            boolean z2 = sensorEvent.values[0] > this.f16911k.floatValue();
            this.f16912l = Boolean.valueOf(z2);
            if (z2) {
                if (sensorEvent.values[0] > 600.0f) {
                    if (this.f16923w != null && !b()) {
                        this.f16923w.setVisibility(8);
                    }
                    IOnLightCallback iOnLightCallback = this.f16922v;
                    if (iOnLightCallback != null) {
                        try {
                            iOnLightCallback.onVisibleChanged(false);
                            return;
                        } catch (RemoteException unused) {
                            o4.e(f16899y, "onSensorChanged RemoteException");
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            if (w7.a(this.f16903c)) {
                if (this.f16923w != null) {
                    o4.d("Scankit", "initlight onSensorChanged open");
                    this.f16923w.setVisibility(0);
                }
                IOnLightCallback iOnLightCallback2 = this.f16922v;
                if (iOnLightCallback2 != null) {
                    try {
                        iOnLightCallback2.onVisibleChanged(true);
                    } catch (RemoteException unused2) {
                        o4.e(f16899y, "onSensorChanged RemoteException");
                    }
                }
            }
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onStart() {
        try {
            this.f16906f.g();
        } catch (RuntimeException unused) {
            o4.b(f16899y, "onStartRuntimeException");
        } catch (Exception unused2) {
            o4.b(f16899y, "onStartException");
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void onStop() {
        try {
            this.f16906f.h();
        } catch (RuntimeException unused) {
            o4.b(f16899y, "onStopRuntimeException");
        } catch (Exception unused2) {
            o4.b(f16899y, "onStopException");
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void pauseContinuouslyScan() throws RemoteException {
        com.huawei.hms.scankit.b bVar = this.f16906f;
        if (bVar != null) {
            bVar.i();
        }
        this.f16924x = true;
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void resumeContinuouslyScan() throws RemoteException {
        this.f16924x = false;
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void setOnClickListener(IObjectWrapper iObjectWrapper) {
        if (iObjectWrapper != null) {
            this.f16909i = (View.OnClickListener) ObjectWrapper.unwrap(iObjectWrapper);
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void setOnErrorCallback(IOnErrorCallback iOnErrorCallback) throws RemoteException {
        com.huawei.hms.scankit.b bVar = this.f16906f;
        if (bVar != null) {
            bVar.a(iOnErrorCallback);
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void setOnLightVisbleCallBack(IOnLightCallback iOnLightCallback) throws RemoteException {
        this.f16922v = iOnLightCallback;
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void setOnResultCallback(IOnResultCallback iOnResultCallback) {
        this.f16907g = iOnResultCallback;
        com.huawei.hms.scankit.b bVar = this.f16906f;
        if (bVar != null) {
            bVar.a(iOnResultCallback);
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void turnOffLight() throws RemoteException {
        f();
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteViewDelegate
    public void turnOnLight() throws RemoteException {
        g();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2) {
        com.huawei.hms.scankit.b bVar = this.f16906f;
        if (bVar == null || bVar.a() == null) {
            return;
        }
        try {
            Point pointA = a(this.f16903c);
            if (i2 != this.f16906f.a().d()) {
                this.f16906f.a().b(i2);
            }
            if (this.f16906f.a().i()) {
                Point point = this.f16921u;
                if (point == null || point.x != pointA.x) {
                    a(pointA, false);
                }
            }
        } catch (NullPointerException unused) {
            o4.e(f16899y, "adjustCameraOrientation: nullpoint");
        } catch (Exception unused2) {
            o4.e(f16899y, "adjustCameraOrientation: Exception");
        }
    }

    private static Point a(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (!w7.f(context) && !w7.e(context)) {
            defaultDisplay.getRealSize(point);
        } else {
            Log.i(f16899y, "initSurfaceView: is in MultiWindowMode");
            defaultDisplay.getSize(point);
        }
        return point;
    }

    protected void a(Point point, boolean z2) {
        try {
            if (this.f16903c.getSystemService("window") != null) {
                if (point == null) {
                    point = a(this.f16903c);
                }
                this.f16921u = point;
                b(point, z2);
            }
        } catch (NullPointerException unused) {
            o4.e(f16899y, "initSurfaceView: nullpoint");
        } catch (Exception unused2) {
            o4.e(f16899y, "initSurfaceView: Exception");
        }
    }

    @Override // com.huawei.hms.scankit.p.e5
    public boolean a(HmsScan[] hmsScanArr) {
        AlertDialog alertDialog;
        if (hmsScanArr == null || hmsScanArr.length <= 0 || (alertDialog = this.f16913m) == null || !alertDialog.isShowing()) {
            return false;
        }
        this.f16913m.dismiss();
        return false;
    }

    @Override // com.huawei.hms.scankit.p.e5
    public boolean a() {
        return this.f16924x;
    }

    private HmsScan[] a(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) {
        boolean z2;
        int iB;
        if (iObjectWrapper == null) {
            o4.b("ScankitRemoteS", "bitmap is null");
            return new HmsScan[0];
        }
        if (iObjectWrapper2 == null || !(ObjectWrapper.unwrap(iObjectWrapper2) instanceof Bundle)) {
            z2 = false;
            iB = 0;
        } else {
            iB = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getInt(DetailRect.FORMAT_FLAG);
            int i2 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper2)).getInt(DetailRect.TYPE_TRANS, 0);
            DetailRect.HMSSCAN_SDK_VALUE = i2;
            z2 = i2 >= 2;
            if (z2) {
                iB = w7.b(iB);
            }
        }
        HmsScan[] hmsScanArrB = r6.a().b((Bitmap) ObjectWrapper.unwrap(iObjectWrapper), iB, true, this.f16901a);
        if (!z2) {
            hmsScanArrB = w7.a(hmsScanArrB);
        }
        if (hmsScanArrB.length == 0) {
            h();
        } else {
            HmsScan hmsScan = hmsScanArrB[0];
            if (hmsScan != null && TextUtils.isEmpty(hmsScan.originalValue)) {
                h();
            }
        }
        return hmsScanArrB;
    }

    protected boolean b() {
        try {
            return this.f16906f.a().h().equals("torch");
        } catch (RuntimeException unused) {
            o4.b(f16899y, "getFlashStatusRuntimeException");
            return false;
        } catch (Exception unused2) {
            o4.b(f16899y, "getFlashStatusException");
            return false;
        }
    }
}

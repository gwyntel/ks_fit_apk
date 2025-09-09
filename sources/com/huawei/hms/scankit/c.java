package com.huawei.hms.scankit;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.huawei.hms.hmsscankit.api.IRemoteFrameDecoderDelegate;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.scankit.p.g0;
import com.huawei.hms.scankit.p.j0;
import com.huawei.hms.scankit.p.k0;
import com.huawei.hms.scankit.p.l1;
import com.huawei.hms.scankit.p.m0;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.s6;
import com.huawei.hms.scankit.p.w3;
import com.huawei.hms.scankit.p.y6;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
final class c extends Handler {

    /* renamed from: j, reason: collision with root package name */
    private static a f16843j;

    /* renamed from: k, reason: collision with root package name */
    private static long f16844k;

    /* renamed from: a, reason: collision with root package name */
    private final Context f16845a;

    /* renamed from: b, reason: collision with root package name */
    private final j0 f16846b;

    /* renamed from: c, reason: collision with root package name */
    private final com.huawei.hms.scankit.a f16847c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f16848d = true;

    /* renamed from: e, reason: collision with root package name */
    private int f16849e = 50;

    /* renamed from: f, reason: collision with root package name */
    private Rect f16850f;

    /* renamed from: g, reason: collision with root package name */
    private int f16851g;

    /* renamed from: h, reason: collision with root package name */
    private IRemoteFrameDecoderDelegate f16852h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f16853i;

    private static class a extends AsyncTask<Object, Object, Object> {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<c> f16854a;

        /* renamed from: e, reason: collision with root package name */
        private List<k0.a> f16858e;

        /* renamed from: f, reason: collision with root package name */
        private List<k0.a> f16859f;

        /* renamed from: b, reason: collision with root package name */
        private boolean f16855b = true;

        /* renamed from: c, reason: collision with root package name */
        private boolean f16856c = false;

        /* renamed from: d, reason: collision with root package name */
        private int f16857d = 0;

        /* renamed from: g, reason: collision with root package name */
        private int f16860g = 0;

        /* renamed from: h, reason: collision with root package name */
        private int f16861h = 0;

        public a(c cVar) {
            this.f16854a = new WeakReference<>(cVar);
        }

        public void b(int i2) {
            this.f16861h = i2;
            c cVar = this.f16854a.get();
            if (cVar != null) {
                try {
                    cVar.a(this.f16861h, this.f16859f);
                    o4.d("DecodeHandler", "ScanCode handle global value" + this.f16861h);
                } catch (RuntimeException e2) {
                    o4.b("DecodeHandler", "RuntimeException: " + e2.getMessage());
                } catch (Exception unused) {
                    o4.b("DecodeHandler", "Exception");
                }
            }
        }

        @Override // android.os.AsyncTask
        protected Object doInBackground(Object... objArr) throws InterruptedException {
            Log.i("ScankitDecode", "doInBackground: ");
            if (c.f16843j.isCancelled()) {
                return null;
            }
            while (!this.f16856c) {
                if (this.f16855b) {
                    try {
                        Thread.sleep(400L);
                    } catch (InterruptedException unused) {
                        o4.d("ScankitDecode", "doInBackground  get InterruptedException  error!!!");
                    }
                    this.f16855b = false;
                } else {
                    c cVar = this.f16854a.get();
                    int i2 = this.f16860g;
                    if (i2 == 0) {
                        this.f16855b = true;
                    } else if (cVar != null) {
                        try {
                            cVar.a(this.f16857d / i2, this.f16858e);
                            o4.d("DecodeHandler", "ScanCode handle auto value" + (this.f16857d / this.f16860g));
                            a();
                            this.f16855b = true;
                        } catch (RuntimeException e2) {
                            o4.b("DecodeHandler", "RuntimeException: " + e2.getMessage());
                        } catch (Exception unused2) {
                            o4.b("DecodeHandler", "Exception");
                        }
                    }
                }
            }
            return null;
        }

        public void a(int i2) {
            this.f16857d += i2;
            this.f16860g++;
        }

        public void a(List<Rect> list, int i2, int i3, boolean z2) {
            if (list == null) {
                o4.a("ScankitDecode", "areas is null");
                return;
            }
            if (list.size() == 0) {
                this.f16859f = Collections.singletonList(new k0.a(new Rect(-100, -100, 100, 100), 1000));
                return;
            }
            this.f16859f = new ArrayList();
            for (Rect rect : list) {
                int iCenterX = ((rect.centerX() * 2000) / i2) - 1000;
                int iCenterY = ((rect.centerY() * 2000) / i3) - 1000;
                int iWidth = ((rect.width() * 2000) / i2) / 2;
                int iHeight = ((rect.height() * 2000) / i3) / 2;
                this.f16859f.add(new k0.a(new Rect(iCenterX - (iWidth / 2), iCenterY - (iHeight / 2), iCenterX + iWidth, iCenterY + iHeight), 1000 / list.size()));
            }
            list.clear();
        }

        public void b(List<Rect> list, int i2, int i3, boolean z2) {
            if (list == null) {
                o4.a("ScankitDecode", "areas is null");
                return;
            }
            if (list.size() == 0) {
                this.f16858e = Collections.singletonList(new k0.a(new Rect(-100, -100, 100, 100), 1000));
                return;
            }
            this.f16858e = new ArrayList();
            if (z2) {
                int i4 = (i3 > i2 ? i3 - i2 : i2 - i3) >> 1;
                for (Rect rect : list) {
                    int iCenterY = (((rect.centerY() + i4) * 2000) / i2) - 1000;
                    int iCenterX = ((rect.centerX() * 2000) / i3) - 1000;
                    int iHeight = ((rect.height() * 2000) / i2) / 2;
                    int iWidth = ((rect.width() * 2000) / i3) / 2;
                    this.f16858e.add(new k0.a(new Rect(iCenterY - (iHeight / 2), iCenterX - (iWidth / 2), iCenterY + iHeight, iCenterX + iWidth), 1000 / list.size()));
                }
                return;
            }
            for (Rect rect2 : list) {
                int iCenterX2 = ((rect2.centerX() * 2000) / i2) - 1000;
                int iCenterY2 = ((rect2.centerY() * 2000) / i3) - 1000;
                int iWidth2 = ((rect2.width() * 2000) / i2) / 2;
                int iHeight2 = ((rect2.height() * 2000) / i3) / 2;
                this.f16858e.add(new k0.a(new Rect(iCenterX2 - (iWidth2 / 2), iCenterY2 - (iHeight2 / 2), iCenterX2 + iWidth2, iCenterY2 + iHeight2), 1000 / list.size()));
            }
            list.clear();
        }

        private void a() {
            this.f16857d = 0;
            this.f16860g = 0;
        }
    }

    c(Context context, j0 j0Var, com.huawei.hms.scankit.a aVar, Map<l1, Object> map, Rect rect, boolean z2) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        this.f16853i = false;
        this.f16845a = context;
        this.f16846b = j0Var;
        this.f16847c = aVar;
        this.f16850f = rect;
        if (f16843j == null) {
            a aVar2 = new a(this);
            f16843j = aVar2;
            aVar2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
        }
        this.f16851g = 0;
        this.f16853i = z2;
        a(context);
    }

    private boolean d() {
        Context context = this.f16845a;
        if (context == null) {
            return true;
        }
        Object systemService = context.getSystemService("window");
        if (!(systemService instanceof WindowManager)) {
            o4.d("ScankitDecode", "isScreenPortrait  getSystemService  WINDOW_SERVICE  error!!!");
            return true;
        }
        Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x < point.y;
    }

    public boolean b(float f2) {
        boolean z2;
        com.huawei.hms.scankit.a aVar = this.f16847c;
        if (aVar != null && aVar.a()) {
            return false;
        }
        try {
            m0 m0VarG = this.f16846b.g();
            if (m0VarG == null) {
                o4.d("ScankitDecode", "Zoom not supported,data is null");
                return false;
            }
            int iC = m0VarG.c();
            int iB = m0VarG.b();
            float fIntValue = ((r1.get(iB).intValue() * 1.0f) / 100.0f) * f2;
            if (((int) (fIntValue * 100.0f)) > m0VarG.a().get(iC).intValue()) {
                fIntValue = (iC * 1.0f) / 100.0f;
            }
            if (!this.f16846b.j()) {
                o4.d("ScankitDecode", "Zoom not supported");
                return false;
            }
            int iA = a(fIntValue);
            if (iA > iB) {
                this.f16846b.d(iA);
                z2 = true;
            } else {
                this.f16846b.d(iB);
                z2 = false;
            }
            this.f16846b.a(Collections.singletonList(new k0.a(new Rect(-150, -150, 150, 150), 1000)));
            return z2;
        } catch (RuntimeException unused) {
            o4.b("ScankitDecode", "Zoom not supported,RuntimeException happen");
            return false;
        } catch (Exception unused2) {
            o4.b("ScankitDecode", "Zoom not supported,Exception happen");
            return false;
        }
    }

    public float c() {
        if (b() == null) {
            return 1.0f;
        }
        return Math.round(r0.get(r0.size() - 1).intValue() / 100.0f);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        if (message == null || !this.f16848d) {
            return;
        }
        int i2 = message.what;
        if (i2 == R.id.scankit_decode) {
            int i3 = this.f16851g;
            if (i3 <= 1) {
                this.f16851g = i3 + 1;
                this.f16847c.sendEmptyMessage(R.id.scankit_decode_failed);
                return;
            } else {
                Object obj = message.obj;
                if (obj instanceof byte[]) {
                    a((byte[]) obj, d());
                    return;
                }
                return;
            }
        }
        if (i2 != R.id.scankit_quit) {
            o4.d("ScankitDecode", "handleMessage  message.what:" + message.what);
            return;
        }
        this.f16848d = false;
        a aVar = f16843j;
        if (aVar != null) {
            aVar.f16856c = true;
            f16843j.cancel(true);
        }
        Looper.myLooper().quit();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0049 A[Catch: RemoteException -> 0x0061, TryCatch #0 {RemoteException -> 0x0061, blocks: (B:14:0x0045, B:16:0x0049, B:18:0x0056, B:20:0x005a), top: B:25:0x0045 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0056 A[Catch: RemoteException -> 0x0061, TryCatch #0 {RemoteException -> 0x0061, blocks: (B:14:0x0045, B:16:0x0049, B:18:0x0056, B:20:0x005a), top: B:25:0x0045 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(android.content.Context r4) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.ClassNotFoundException {
        /*
            r3 = this;
            java.lang.String r0 = "ScankitDecode"
            boolean r1 = r3.f16853i     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            if (r1 == 0) goto L12
            java.lang.String r4 = "use local decoder"
            android.util.Log.d(r0, r4)     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            java.lang.Class<com.huawei.hms.scankit.DecoderCreator> r4 = com.huawei.hms.scankit.DecoderCreator.class
            java.lang.Object r4 = r4.newInstance()     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            goto L45
        L12:
            java.lang.String r1 = "use remote decoder"
            android.util.Log.d(r0, r1)     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            android.content.Context r4 = com.huawei.hms.hmsscankit.g.e(r4)     // Catch: java.lang.Throwable -> L1b java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
        L1b:
            java.lang.ClassLoader r1 = r4.getClassLoader()     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            java.lang.String r2 = "com.huawei.hms.scankit.DecoderCreator"
            java.lang.Class r1 = r1.loadClass(r2)     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            java.lang.ClassLoader r4 = r4.getClassLoader()     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            java.lang.String r2 = "com.huawei.hms.scankit.aiscan.common.BarcodeFormat"
            r4.loadClass(r2)     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            java.lang.Object r4 = r1.newInstance()     // Catch: java.lang.InstantiationException -> L33 java.lang.ClassNotFoundException -> L39 java.lang.IllegalAccessException -> L3f
            goto L45
        L33:
            java.lang.String r4 = "InstantiationException"
            com.huawei.hms.scankit.p.o4.a(r0, r4)
            goto L44
        L39:
            java.lang.String r4 = "ClassNotFoundException"
            com.huawei.hms.scankit.p.o4.a(r0, r4)
            goto L44
        L3f:
            java.lang.String r4 = "IllegalAccessException"
            com.huawei.hms.scankit.p.o4.a(r0, r4)
        L44:
            r4 = 0
        L45:
            boolean r1 = r4 instanceof android.os.IBinder     // Catch: android.os.RemoteException -> L61
            if (r1 == 0) goto L56
            android.os.IBinder r4 = (android.os.IBinder) r4     // Catch: android.os.RemoteException -> L61
            com.huawei.hms.hmsscankit.api.IRemoteDecoderCreator r4 = com.huawei.hms.hmsscankit.api.IRemoteDecoderCreator.Stub.asInterface(r4)     // Catch: android.os.RemoteException -> L61
            com.huawei.hms.hmsscankit.api.IRemoteFrameDecoderDelegate r4 = r4.newRemoteFrameDecoderDelegate()     // Catch: android.os.RemoteException -> L61
            r3.f16852h = r4     // Catch: android.os.RemoteException -> L61
            return
        L56:
            com.huawei.hms.hmsscankit.api.IRemoteFrameDecoderDelegate r4 = r3.f16852h     // Catch: android.os.RemoteException -> L61
            if (r4 != 0) goto L66
            com.huawei.hms.scankit.p.h4 r4 = com.huawei.hms.scankit.p.h4.a()     // Catch: android.os.RemoteException -> L61
            r3.f16852h = r4     // Catch: android.os.RemoteException -> L61
            goto L66
        L61:
            java.lang.String r4 = "RemoteException"
            com.huawei.hms.scankit.p.o4.a(r0, r4)
        L66:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.c.a(android.content.Context):void");
    }

    public List<Integer> b() {
        return this.f16846b.g().a();
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(byte[] r19, boolean r20) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.ClassNotFoundException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 452
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.c.a(byte[], boolean):void");
    }

    private boolean a(float f2, s6[] s6VarArr, w3.c cVar) {
        if (!b(f2)) {
            return false;
        }
        Message messageObtain = Message.obtain();
        messageObtain.what = R.id.scankit_decode_succeeded;
        HmsScan[] hmsScanArrA = y6.a(s6VarArr);
        messageObtain.obj = hmsScanArrA;
        if (b.J != null) {
            b.J.a(hmsScanArrA, cVar);
        }
        this.f16847c.sendMessage(messageObtain);
        return true;
    }

    private void a(s6[] s6VarArr, byte[] bArr, int i2, int i3, w3.c cVar) throws IOException {
        if (this.f16847c != null) {
            Message messageObtain = Message.obtain(this.f16847c, R.id.scankit_decode_succeeded, y6.a(s6VarArr));
            Log.d("ScankitDecode", "scankit decode succeed msg SCAN_MODE: FULLSDK VERSION_CODE: 21200301 VERSION_NAME: 2.12.0.301");
            try {
                if (b.I != null) {
                    b.I.a(s6VarArr[0].e(), s6VarArr[0].b(), s6VarArr[0].m());
                }
            } catch (Exception unused) {
                Log.d("ScankitDecode", "ha is null");
            }
            if (this.f16847c.c()) {
                Bundle bundle = new Bundle();
                a(bArr, i2, i3, bundle);
                messageObtain.setData(bundle);
            }
            messageObtain.sendToTarget();
        }
    }

    private static void a(byte[] bArr, int i2, int i3, Bundle bundle) throws IOException {
        if (bArr == null || bArr.length == 0) {
            return;
        }
        YuvImage yuvImage = new YuvImage(bArr, 17, i2, i3, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, i2, i3), 100, byteArrayOutputStream);
        bundle.putByteArray("barcode_bitmap", byteArrayOutputStream.toByteArray());
        bundle.putFloat("barcode_scaled_factor", 1.0f);
        try {
            byteArrayOutputStream.close();
        } catch (IOException unused) {
            Log.e("ScankitDecode", "RemoteException");
        }
    }

    public void a(Rect rect, boolean z2) {
        com.huawei.hms.scankit.a aVar = this.f16847c;
        if (aVar == null || !aVar.a()) {
            this.f16846b.a(rect, z2);
        }
    }

    public void a(int i2, List<k0.a> list) {
        g0 g0VarB = this.f16846b.b();
        int iB = g0VarB.b();
        int iC = g0VarB.c();
        int iA = g0VarB.a();
        if (i2 == 0) {
            return;
        }
        int i3 = iA + i2;
        if (i3 <= iB) {
            iB = i3 < iC ? iC : i3;
        }
        this.f16846b.c(iB);
        k0 k0VarC = this.f16846b.c();
        Rect rectB = k0VarC.b();
        if (k0VarC.a() > 0) {
            if (k0VarC.a() == 1) {
                int iCenterX = rectB.centerX();
                int iCenterY = rectB.centerY();
                if (Math.sqrt(((iCenterX - list.get(0).f17464a.centerX()) * (iCenterX - list.get(0).f17464a.centerX())) + (iCenterY - list.get(0).f17464a.centerY()) + (iCenterY - list.get(0).f17464a.centerY())) > this.f16849e) {
                    list.set(0, new k0.a(list.get(0).f17464a, 1000));
                    this.f16846b.a(list.subList(0, 1));
                    return;
                }
                return;
            }
            this.f16846b.a(list);
        }
    }

    public int a(float f2) {
        List<Integer> listB = b();
        if (listB == null) {
            return -3;
        }
        if (listB.size() <= 0) {
            return -4;
        }
        if (Math.abs(f2 - 1.0f) < 1.0E-6f) {
            return 0;
        }
        if (f2 == c()) {
            return listB.size() - 1;
        }
        for (int i2 = 1; i2 < listB.size(); i2++) {
            float f3 = 100.0f * f2;
            if (listB.get(i2).intValue() >= f3 && listB.get(i2 - 1).intValue() <= f3) {
                return i2;
            }
        }
        return -1;
    }
}

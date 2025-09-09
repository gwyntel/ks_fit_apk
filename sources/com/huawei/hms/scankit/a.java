package com.huawei.hms.scankit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import com.huawei.hms.scankit.p.e5;
import com.huawei.hms.scankit.p.f5;
import com.huawei.hms.scankit.p.i8;
import com.huawei.hms.scankit.p.j0;
import com.huawei.hms.scankit.p.l1;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.u6;
import com.huawei.hms.scankit.p.v5;
import com.huawei.hms.scankit.p.v6;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes4.dex */
public class a extends Handler implements v6 {

    /* renamed from: a, reason: collision with root package name */
    private final f5 f16779a;

    /* renamed from: b, reason: collision with root package name */
    private d f16780b;

    /* renamed from: c, reason: collision with root package name */
    private final int f16781c;

    /* renamed from: d, reason: collision with root package name */
    private EnumC0136a f16782d;

    /* renamed from: e, reason: collision with root package name */
    private Context f16783e;

    /* renamed from: f, reason: collision with root package name */
    private final j0 f16784f;

    /* renamed from: g, reason: collision with root package name */
    private final ViewfinderView f16785g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f16786h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f16787i;

    /* renamed from: j, reason: collision with root package name */
    private boolean f16788j;

    /* renamed from: k, reason: collision with root package name */
    private e5 f16789k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f16790l;

    /* renamed from: com.huawei.hms.scankit.a$a, reason: collision with other inner class name */
    private enum EnumC0136a {
        PREVIEW,
        SUCCESS,
        DONE
    }

    a(Context context, ViewfinderView viewfinderView, f5 f5Var, Collection<BarcodeFormat> collection, Map<l1, ?> map, String str, j0 j0Var, Rect rect, int i2, boolean z2, boolean z3) {
        this.f16785g = viewfinderView;
        this.f16779a = f5Var;
        this.f16781c = i2;
        this.f16783e = context;
        d dVar = new d(context, j0Var, this, collection, map, str, this);
        this.f16780b = dVar;
        dVar.a(rect);
        this.f16780b.a(z3);
        this.f16780b.start();
        this.f16790l = z2;
        j0Var.a(new j(this.f16780b));
        this.f16782d = EnumC0136a.SUCCESS;
        this.f16784f = j0Var;
        j0Var.p();
        o4.a("scan-time", "start preview time:" + System.currentTimeMillis());
        f();
        v5.a(null);
    }

    public void a(e5 e5Var) {
        this.f16789k = e5Var;
    }

    public int b() {
        return this.f16781c;
    }

    public void c(boolean z2) {
        this.f16786h = z2;
    }

    public boolean d() {
        return this.f16788j;
    }

    public void e() throws InterruptedException {
        this.f16782d = EnumC0136a.DONE;
        this.f16784f.q();
        Message.obtain(this.f16780b.a(), R.id.scankit_quit).sendToTarget();
        try {
            this.f16780b.b();
            this.f16780b.join(50L);
        } catch (InterruptedException unused) {
            o4.e("CaptureHandler", "quitSynchronously   wait interrupt");
        }
        this.f16780b = null;
        removeMessages(R.id.scankit_decode_succeeded);
        removeMessages(R.id.scankit_decode_failed);
    }

    public void f() {
        if (this.f16782d == EnumC0136a.SUCCESS) {
            this.f16782d = EnumC0136a.PREVIEW;
            this.f16784f.o();
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        HmsScan hmsScan;
        int i2 = message.what;
        if (i2 == R.id.scankit_restart_preview) {
            f();
            return;
        }
        if (i2 != R.id.scankit_decode_succeeded) {
            if (i2 == R.id.scankit_decode_failed) {
                this.f16782d = EnumC0136a.PREVIEW;
                this.f16784f.o();
                return;
            }
            return;
        }
        this.f16782d = EnumC0136a.SUCCESS;
        Object obj = message.obj;
        if (obj instanceof HmsScan[]) {
            HmsScan[] hmsScanArr = (HmsScan[]) obj;
            if (hmsScanArr.length <= 0 || (hmsScan = hmsScanArr[0]) == null || TextUtils.isEmpty(hmsScan.originalValue)) {
                o4.d("CaptureHandler", "retrieve  HmsScan lenth is 0");
            } else {
                o4.d("CaptureHandler", "scan successful");
                Bitmap bitmapDecodeByteArray = null;
                float f2 = 0.0f;
                if (this.f16787i) {
                    o4.d("CaptureHandler", "scan successful & return bitmap");
                    Bundle data = message.getData();
                    if (data != null) {
                        byte[] byteArray = data.getByteArray("barcode_bitmap");
                        f2 = data.getFloat("barcode_scaled_factor", 0.0f);
                        if (byteArray != null && byteArray.length > 0) {
                            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        }
                    }
                    this.f16779a.a(hmsScanArr, bitmapDecodeByteArray, f2);
                } else {
                    this.f16779a.a(hmsScanArr, null, 0.0f);
                }
                if (!this.f16790l) {
                    return;
                }
            }
            f();
        }
    }

    private u6 b(u6 u6Var) {
        float fB;
        float fC;
        int iMax;
        Point pointB = i8.b(this.f16783e);
        Point pointE = this.f16784f.e();
        int i2 = pointB.x;
        int i3 = pointB.y;
        if (i2 < i3) {
            fB = (u6Var.b() * ((i2 * 1.0f) / pointE.y)) - (Math.max(pointB.x, pointE.y) / 2.0f);
            fC = u6Var.c() * ((i3 * 1.0f) / pointE.x);
            iMax = Math.min(pointB.y, pointE.x);
        } else {
            fB = (u6Var.b() * ((i2 * 1.0f) / pointE.x)) - (Math.min(pointB.y, pointE.y) / 2.0f);
            fC = u6Var.c() * ((i3 * 1.0f) / pointE.y);
            iMax = Math.max(pointB.x, pointE.x);
        }
        return new u6(fB, fC - (iMax / 2.0f));
    }

    public boolean a() {
        e5 e5Var = this.f16789k;
        if (e5Var != null) {
            return e5Var.a();
        }
        return false;
    }

    public boolean c() {
        return this.f16787i;
    }

    @Override // com.huawei.hms.scankit.p.v6
    public void a(u6 u6Var) {
        if (this.f16785g != null) {
            this.f16785g.a(b(u6Var));
        }
    }

    public void a(boolean z2) {
        this.f16787i = z2;
    }

    public void b(boolean z2) {
        this.f16788j = z2;
    }
}

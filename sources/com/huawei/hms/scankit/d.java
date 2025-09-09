package com.huawei.hms.scankit;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import com.huawei.hms.scankit.p.j0;
import com.huawei.hms.scankit.p.k1;
import com.huawei.hms.scankit.p.l1;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.v6;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
final class d extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private Context f16862a;

    /* renamed from: b, reason: collision with root package name */
    private final j0 f16863b;

    /* renamed from: c, reason: collision with root package name */
    private final Map<l1, Object> f16864c;

    /* renamed from: d, reason: collision with root package name */
    private Handler f16865d;

    /* renamed from: e, reason: collision with root package name */
    private a f16866e;

    /* renamed from: g, reason: collision with root package name */
    private Rect f16868g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f16869h = true;

    /* renamed from: f, reason: collision with root package name */
    private final CountDownLatch f16867f = new CountDownLatch(1);

    d(Context context, j0 j0Var, a aVar, Collection<BarcodeFormat> collection, Map<l1, ?> map, String str, v6 v6Var) {
        this.f16862a = context;
        this.f16866e = aVar;
        this.f16863b = j0Var;
        EnumMap enumMap = new EnumMap(l1.class);
        this.f16864c = enumMap;
        if (map != null) {
            enumMap.putAll(map);
        }
        if (collection == null || collection.isEmpty()) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            collection = EnumSet.noneOf(BarcodeFormat.class);
            if (defaultSharedPreferences.getBoolean("preferences_decode_1D_product", true)) {
                collection.addAll(k1.f17466a);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_1D_industrial", true)) {
                collection.addAll(k1.f17468c);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_QR", true)) {
                collection.addAll(k1.f17469d);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_Data_Matrix", true)) {
                collection.addAll(k1.f17471f);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_Aztec", false)) {
                collection.addAll(k1.f17470e);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_PDF417", false)) {
                collection.addAll(k1.f17472g);
            }
        }
        enumMap.put((EnumMap) l1.f17489c, (l1) collection);
        if (str != null) {
            enumMap.put((EnumMap) l1.f17494h, (l1) str);
        }
        enumMap.put((EnumMap) l1.f17496j, (l1) v6Var);
        o4.d("DecodeThread", "Hints: " + enumMap);
    }

    public void a(Rect rect) {
        this.f16868g = rect;
    }

    public void b() {
        this.f16862a = null;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Looper.prepare();
        this.f16865d = new c(this.f16862a, this.f16863b, this.f16866e, this.f16864c, this.f16868g, this.f16869h);
        this.f16867f.countDown();
        Looper.loop();
    }

    Handler a() throws InterruptedException {
        try {
            this.f16867f.await();
        } catch (InterruptedException unused) {
            o4.b("exception", "InterruptedException");
        }
        return this.f16865d;
    }

    public void a(boolean z2) {
        this.f16869h = z2;
    }
}

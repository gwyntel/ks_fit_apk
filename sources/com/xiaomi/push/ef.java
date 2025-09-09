package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import com.taobao.accs.common.Constants;
import com.xiaomi.push.ak;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ef {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ef f23647a;

    /* renamed from: a, reason: collision with other field name */
    private Context f312a;

    /* renamed from: a, reason: collision with other field name */
    private final ConcurrentLinkedQueue<b> f313a;

    class a extends b {
        a() {
            super();
        }

        @Override // com.xiaomi.push.ef.b, com.xiaomi.push.ak.b
        public void b() {
            ef.this.b();
        }
    }

    class b extends ak.b {

        /* renamed from: a, reason: collision with root package name */
        long f23649a = System.currentTimeMillis();

        b() {
        }

        public boolean a() {
            return true;
        }

        @Override // com.xiaomi.push.ak.b
        public void b() {
        }

        /* renamed from: b, reason: collision with other method in class */
        final boolean m307b() {
            return System.currentTimeMillis() - this.f23649a > 172800000;
        }
    }

    private ef(Context context) {
        ConcurrentLinkedQueue<b> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        this.f313a = concurrentLinkedQueue;
        this.f312a = context;
        concurrentLinkedQueue.add(new a());
        b(0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (z.b() || z.m810a()) {
            return;
        }
        try {
            File file = new File(this.f312a.getExternalFilesDir(null) + "/.logcache");
            if (file.exists() && file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    file2.delete();
                }
            }
        } catch (NullPointerException unused) {
        }
    }

    private void c() {
        while (!this.f313a.isEmpty()) {
            b bVarPeek = this.f313a.peek();
            if (bVarPeek != null) {
                if (!bVarPeek.m307b() && this.f313a.size() <= 6) {
                    return;
                }
                com.xiaomi.channel.commonutils.logger.b.c("remove Expired task");
                this.f313a.remove(bVarPeek);
            }
        }
    }

    public static ef a(Context context) {
        if (f23647a == null) {
            synchronized (ef.class) {
                try {
                    if (f23647a == null) {
                        f23647a = new ef(context);
                    }
                } finally {
                }
            }
        }
        f23647a.f312a = context;
        return f23647a;
    }

    private void b(long j2) {
        if (this.f313a.isEmpty()) {
            return;
        }
        ie.a(new eh(this), j2);
    }

    class c extends b {

        /* renamed from: a, reason: collision with root package name */
        int f23651a;

        /* renamed from: a, reason: collision with other field name */
        File f315a;

        /* renamed from: a, reason: collision with other field name */
        String f316a;

        /* renamed from: a, reason: collision with other field name */
        boolean f317a;

        /* renamed from: b, reason: collision with root package name */
        String f23652b;

        /* renamed from: b, reason: collision with other field name */
        boolean f318b;

        c(String str, String str2, File file, boolean z2) {
            super();
            this.f316a = str;
            this.f23652b = str2;
            this.f315a = file;
            this.f318b = z2;
        }

        private boolean c() throws JSONException {
            int i2;
            int i3 = 0;
            SharedPreferences sharedPreferences = ef.this.f312a.getSharedPreferences("log.timestamp", 0);
            String string = sharedPreferences.getString("log.requst", "");
            long jCurrentTimeMillis = System.currentTimeMillis();
            try {
                JSONObject jSONObject = new JSONObject(string);
                jCurrentTimeMillis = jSONObject.getLong("time");
                i2 = jSONObject.getInt(Constants.KEY_TIMES);
            } catch (JSONException unused) {
                i2 = 0;
            }
            if (System.currentTimeMillis() - jCurrentTimeMillis >= 86400000) {
                jCurrentTimeMillis = System.currentTimeMillis();
            } else {
                if (i2 > 10) {
                    return false;
                }
                i3 = i2;
            }
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("time", jCurrentTimeMillis);
                jSONObject2.put(Constants.KEY_TIMES, i3 + 1);
                sharedPreferences.edit().putString("log.requst", jSONObject2.toString()).commit();
            } catch (JSONException e2) {
                com.xiaomi.channel.commonutils.logger.b.c("JSONException on put " + e2.getMessage());
            }
            return true;
        }

        @Override // com.xiaomi.push.ef.b
        public boolean a() {
            return bg.e(ef.this.f312a) || (this.f318b && bg.b(ef.this.f312a));
        }

        @Override // com.xiaomi.push.ef.b, com.xiaomi.push.ak.b
        public void b() {
            try {
                if (c()) {
                    HashMap map = new HashMap();
                    map.put("uid", com.xiaomi.push.service.bw.m767a());
                    map.put("token", this.f23652b);
                    map.put(com.alipay.sdk.m.k.b.f9362k, bg.m204a(ef.this.f312a));
                    bg.a(this.f316a, map, this.f315a, "file");
                }
                this.f317a = true;
            } catch (IOException unused) {
            }
        }

        @Override // com.xiaomi.push.ak.b
        /* renamed from: c, reason: collision with other method in class */
        public void mo308c() {
            if (!this.f317a) {
                int i2 = this.f23651a + 1;
                this.f23651a = i2;
                if (i2 < 3) {
                    ef.this.f313a.add(this);
                }
            }
            if (this.f317a || this.f23651a >= 3) {
                this.f315a.delete();
            }
            ef.this.a((1 << this.f23651a) * 1000);
        }
    }

    public void a(String str, String str2, Date date, Date date2, int i2, boolean z2) {
        this.f313a.add(new eg(this, i2, date, date2, str, str2, z2));
        b(0L);
    }

    public void a() {
        c();
        a(0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j2) {
        b bVarPeek = this.f313a.peek();
        if (bVarPeek == null || !bVarPeek.a()) {
            return;
        }
        b(j2);
    }
}

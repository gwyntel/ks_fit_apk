package com.xiaomi.push.service;

import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.push.gb;
import com.xiaomi.push.gc;
import com.xiaomi.push.service.XMPushService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class bq {

    /* renamed from: a, reason: collision with root package name */
    private final ConcurrentHashMap<String, c> f24555a = new ConcurrentHashMap<>();

    public static class a extends XMPushService.j {
        public a() {
            super(17);
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        /* renamed from: a */
        public void mo433a() {
            bq.a().m766a();
        }

        @Override // com.xiaomi.push.service.XMPushService.j
        public String a() {
            return "RecordTimeManager clear";
        }
    }

    private static class b {

        /* renamed from: a, reason: collision with root package name */
        private static final bq f24556a = new bq();
    }

    private static class c {

        /* renamed from: a, reason: collision with root package name */
        long f24557a;

        /* renamed from: b, reason: collision with root package name */
        long f24558b;

        /* renamed from: c, reason: collision with root package name */
        long f24559c;

        /* renamed from: d, reason: collision with root package name */
        long f24560d;

        private c() {
        }

        public long a() {
            long j2 = this.f24559c;
            long j3 = this.f24558b;
            if (j2 > j3) {
                return j2 - j3;
            }
            return 0L;
        }

        public long b() {
            long j2 = this.f24560d;
            long j3 = this.f24559c;
            if (j2 > j3) {
                return j2 - j3;
            }
            return 0L;
        }
    }

    public static bq a() {
        return b.f24556a;
    }

    public void b(String str, long j2) {
        c cVarRemove = this.f24555a.remove(str);
        if (cVarRemove != null) {
            cVarRemove.f24560d = j2;
            a(str, cVarRemove);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m766a() {
        if (this.f24555a.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<String, c>> it = this.f24555a.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, c> next = it.next();
            if (next == null || next.getValue() == null) {
                it.remove();
            } else {
                c value = next.getValue();
                if (Math.abs(SystemClock.elapsedRealtime() - value.f24558b) > 10000) {
                    a(next.getKey(), value);
                    it.remove();
                }
            }
        }
    }

    public void a(String str, long j2, long j3) {
        c cVar = new c();
        cVar.f24557a = j3;
        cVar.f24558b = j2;
        this.f24555a.put(str, cVar);
    }

    public void a(String str, long j2) {
        c cVar = this.f24555a.get(str);
        if (cVar != null) {
            cVar.f24559c = j2;
        }
    }

    private void a(String str, c cVar) {
        if (TextUtils.isEmpty(str) || cVar == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("xmsfVC", Long.valueOf(cVar.f24557a));
        map.put("packetId", str);
        map.put("pTime", Long.valueOf(cVar.a()));
        map.put("bTime", Long.valueOf(cVar.b()));
        gc.a().a(new gb("msg_process_time", map));
    }
}

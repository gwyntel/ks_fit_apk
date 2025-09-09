package com.alibaba.sdk.android.httpdns.probe;

import com.alibaba.sdk.android.httpdns.i;
import com.alibaba.sdk.android.httpdns.probe.IPProbeService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class e implements IPProbeService {

    /* renamed from: a, reason: collision with other field name */
    private AtomicLong f35a = new AtomicLong(0);

    /* renamed from: c, reason: collision with root package name */
    private ConcurrentHashMap<String, Long> f8897c = new ConcurrentHashMap<>();

    /* renamed from: a, reason: collision with root package name */
    private b f8895a = null;

    /* renamed from: b, reason: collision with root package name */
    private f f8896b = new f() { // from class: com.alibaba.sdk.android.httpdns.probe.e.1
        @Override // com.alibaba.sdk.android.httpdns.probe.f
        public void a(long j2, c cVar) {
            if (cVar != null) {
                try {
                    if (!e.this.f8897c.containsKey(cVar.getHostName()) || ((Long) e.this.f8897c.get(cVar.getHostName())).longValue() != j2) {
                        i.d("corresponding tasknumber not exists, drop the result");
                    } else if (cVar.getIps() != null && cVar.j() != null && cVar.k() != null && cVar.getHostName() != null) {
                        i.e("defultId:" + cVar.j() + ", selectedIp:" + cVar.k() + ", promote:" + (cVar.c() - cVar.d()));
                        e.this.a(cVar.getHostName(), cVar.j(), cVar.k(), cVar.c(), cVar.d(), cVar.getIps().length);
                        e.this.f8895a.a(cVar.getHostName(), cVar.getIps());
                        e.this.f8897c.remove(cVar.getHostName());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    };

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public IPProbeService.a getProbeStatus(String str) {
        return this.f8897c.containsKey(str) ? IPProbeService.a.PROBING : IPProbeService.a.NO_PROBING;
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public void launchIPProbeTask(String str, int i2, String[] strArr) {
        if (!com.alibaba.sdk.android.httpdns.a.a.a().f()) {
            i.f("ip probe is forbidden");
        } else {
            if (getProbeStatus(str) != IPProbeService.a.NO_PROBING) {
                i.f("already launch the same task, drop the task");
                return;
            }
            long jAddAndGet = this.f35a.addAndGet(1L);
            this.f8897c.put(str, Long.valueOf(jAddAndGet));
            com.alibaba.sdk.android.httpdns.c.a().execute(new a(jAddAndGet, str, strArr, i2, this.f8896b));
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public void setIPListUpdateCallback(b bVar) {
        this.f8895a = bVar;
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public boolean stopIPProbeTask(String str) {
        if (!this.f8897c.containsKey(str)) {
            return false;
        }
        i.d("stop ip probe task for host:" + str);
        this.f8897c.remove(str);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, String str3, long j2, long j3, int i2) {
        com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
        if (bVarA != null) {
            bVarA.a(str, str2, str3, j2, j3, i2);
        }
    }
}

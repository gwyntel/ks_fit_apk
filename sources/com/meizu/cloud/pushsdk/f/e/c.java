package com.meizu.cloud.pushsdk.f.e;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public class c implements d {

    /* renamed from: a, reason: collision with root package name */
    private final int f19618a;

    /* renamed from: b, reason: collision with root package name */
    private final AtomicLong f19619b = new AtomicLong(0);

    /* renamed from: c, reason: collision with root package name */
    private final Map<Long, byte[]> f19620c = new ConcurrentHashMap();

    /* renamed from: d, reason: collision with root package name */
    private final List<Long> f19621d = new CopyOnWriteArrayList();

    public c(int i2) {
        this.f19618a = i2;
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public void a(com.meizu.cloud.pushsdk.f.b.a aVar) throws IOException {
        b(aVar);
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public long b() {
        return this.f19621d.size();
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public com.meizu.cloud.pushsdk.f.c.c c() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList();
        int iB = (int) b();
        int i2 = this.f19618a;
        if (iB > i2) {
            iB = i2;
        }
        for (int i3 = 0; i3 < iB; i3++) {
            Long l2 = this.f19621d.get(i3);
            if (l2 != null) {
                com.meizu.cloud.pushsdk.f.b.c cVar = new com.meizu.cloud.pushsdk.f.b.c();
                cVar.a(a.a(this.f19620c.get(l2)));
                com.meizu.cloud.pushsdk.f.g.c.c("MemoryStore", " current key " + l2 + " payload " + cVar, new Object[0]);
                linkedList.add(l2);
                arrayList.add(cVar);
            }
        }
        return new com.meizu.cloud.pushsdk.f.c.c(arrayList, linkedList);
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public boolean a() {
        return true;
    }

    public long b(com.meizu.cloud.pushsdk.f.b.a aVar) throws IOException {
        byte[] bArrA = a.a((Map<String, String>) aVar.a());
        long andIncrement = this.f19619b.getAndIncrement();
        this.f19621d.add(Long.valueOf(andIncrement));
        this.f19620c.put(Long.valueOf(andIncrement), bArrA);
        return andIncrement;
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public boolean a(long j2) {
        return this.f19621d.remove(Long.valueOf(j2)) && this.f19620c.remove(Long.valueOf(j2)) != null;
    }
}

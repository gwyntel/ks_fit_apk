package com.xiaomi.push;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
class df extends cz {

    /* renamed from: a, reason: collision with root package name */
    cz f23575a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ dd f274a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ cz f23576b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    df(dd ddVar, String str, cz czVar) {
        super(str);
        this.f274a = ddVar;
        this.f23576b = czVar;
        this.f23575a = czVar;
        ((cz) this).f260b = ((cz) this).f260b;
        if (czVar != null) {
            this.f23563f = czVar.f23563f;
        }
    }

    @Override // com.xiaomi.push.cz
    public synchronized ArrayList<String> a(boolean z2) {
        ArrayList<String> arrayList;
        try {
            arrayList = new ArrayList<>();
            cz czVar = this.f23575a;
            if (czVar != null) {
                arrayList.addAll(czVar.a(true));
            }
            Map<String, cz> map = dd.f23571b;
            synchronized (map) {
                try {
                    cz czVar2 = map.get(((cz) this).f260b);
                    if (czVar2 != null) {
                        Iterator<String> it = czVar2.a(true).iterator();
                        while (it.hasNext()) {
                            String next = it.next();
                            if (arrayList.indexOf(next) == -1) {
                                arrayList.add(next);
                            }
                        }
                        arrayList.remove(((cz) this).f260b);
                        arrayList.add(((cz) this).f260b);
                    }
                } finally {
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    @Override // com.xiaomi.push.cz
    public boolean b() {
        return false;
    }

    @Override // com.xiaomi.push.cz
    public synchronized void a(String str, cy cyVar) {
        cz czVar = this.f23575a;
        if (czVar != null) {
            czVar.a(str, cyVar);
        }
    }
}

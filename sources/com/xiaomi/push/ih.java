package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.ak;
import java.util.ArrayList;

/* loaded from: classes4.dex */
class ih extends ak.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23984a;

    ih(Context context) {
        this.f23984a = context;
    }

    @Override // com.xiaomi.push.ak.b
    public void b() {
        ArrayList arrayList;
        synchronized (ig.f570a) {
            arrayList = new ArrayList(ig.f572a);
            ig.f572a.clear();
        }
        ig.b(this.f23984a, arrayList);
    }
}

package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public final class zadc {
    public static final Status zaa = new Status(8, "The connection to Google Play services was lost");

    /* renamed from: a, reason: collision with root package name */
    final Set f12766a = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zadb zac = new zadb(this);

    final void a(BasePendingResult basePendingResult) {
        this.f12766a.add(basePendingResult);
        basePendingResult.zan(this.zac);
    }

    public final void zab() {
        for (BasePendingResult basePendingResult : (BasePendingResult[]) this.f12766a.toArray(new BasePendingResult[0])) {
            basePendingResult.zan(null);
            if (basePendingResult.zam()) {
                this.f12766a.remove(basePendingResult);
            }
        }
    }
}

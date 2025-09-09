package com.alibaba.sdk.android.beacon;

import com.alibaba.sdk.android.beacon.Beacon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
final class b {

    /* renamed from: a, reason: collision with root package name */
    private final List<Beacon.Config> f8788a = new ArrayList();

    b(Beacon beacon) {
    }

    List<Beacon.Config> a() {
        return Collections.unmodifiableList(this.f8788a);
    }
}

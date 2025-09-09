package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class zzay {

    /* renamed from: a, reason: collision with root package name */
    final List f13178a = new ArrayList();

    protected zzay() {
    }

    final zzaq a(String str) {
        if (!this.f13178a.contains(zzg.zza(str))) {
            throw new IllegalArgumentException("Command not supported");
        }
        throw new UnsupportedOperationException("Command not implemented: " + str);
    }

    public abstract zzaq zza(String str, zzh zzhVar, List<zzaq> list);
}

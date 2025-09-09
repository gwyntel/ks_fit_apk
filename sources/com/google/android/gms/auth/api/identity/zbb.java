package com.google.android.gms.auth.api.identity;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public final class zbb {
    private String zba;

    private zbb() {
    }

    public static final zbb zbc(zbc zbcVar) {
        String strZbb = zbcVar.zbb();
        zbb zbbVar = new zbb();
        if (strZbb != null) {
            zbbVar.zba = Preconditions.checkNotEmpty(strZbb);
        }
        return zbbVar;
    }

    public final zbb zba(@NonNull String str) {
        this.zba = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zbc zbb() {
        return new zbc(this.zba);
    }

    /* synthetic */ zbb(zba zbaVar) {
    }
}

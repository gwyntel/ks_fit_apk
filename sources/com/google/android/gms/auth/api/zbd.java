package com.google.android.gms.auth.api;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;

@Deprecated
/* loaded from: classes3.dex */
public final class zbd implements Api.ApiOptions.Optional {
    public static final zbd zba = new zbd(new zbc());
    private final String zbb = null;
    private final boolean zbc;

    @Nullable
    private final String zbd;

    public zbd(zbc zbcVar) {
        this.zbc = zbcVar.f12660a.booleanValue();
        this.zbd = zbcVar.f12661b;
    }

    static /* bridge */ /* synthetic */ String a(zbd zbdVar) {
        String str = zbdVar.zbb;
        return null;
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zbd)) {
            return false;
        }
        zbd zbdVar = (zbd) obj;
        String str = zbdVar.zbb;
        return Objects.equal(null, null) && this.zbc == zbdVar.zbc && Objects.equal(this.zbd, zbdVar.zbd);
    }

    public final int hashCode() {
        return Objects.hashCode(null, Boolean.valueOf(this.zbc), this.zbd);
    }

    public final Bundle zba() {
        Bundle bundle = new Bundle();
        bundle.putString("consumer_package", null);
        bundle.putBoolean("force_save_dialog", this.zbc);
        bundle.putString("log_session_id", this.zbd);
        return bundle;
    }
}

package com.google.android.gms.common.internal;

import android.os.Bundle;
import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
public final class zzg extends zza {

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ BaseGmsClient f12855c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @BinderThread
    public zzg(BaseGmsClient baseGmsClient, @Nullable int i2, Bundle bundle) {
        super(baseGmsClient, i2, null);
        this.f12855c = baseGmsClient;
    }

    @Override // com.google.android.gms.common.internal.zza
    protected final void c(ConnectionResult connectionResult) {
        if (this.f12855c.b() && BaseGmsClient.A(this.f12855c)) {
            BaseGmsClient.w(this.f12855c, 16);
        } else {
            this.f12855c.f12810c.onReportServiceBinding(connectionResult);
            this.f12855c.j(connectionResult);
        }
    }

    @Override // com.google.android.gms.common.internal.zza
    protected final boolean d() {
        this.f12855c.f12810c.onReportServiceBinding(ConnectionResult.RESULT_SUCCESS);
        return true;
    }
}

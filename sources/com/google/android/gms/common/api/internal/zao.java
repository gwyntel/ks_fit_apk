package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import android.app.PendingIntent;
import androidx.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zao implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zap f12771a;
    private final zam zab;

    zao(zap zapVar, zam zamVar) {
        this.f12771a = zapVar;
        this.zab = zamVar;
    }

    @Override // java.lang.Runnable
    @MainThread
    public final void run() {
        if (this.f12771a.f12772b) {
            ConnectionResult connectionResultB = this.zab.b();
            if (connectionResultB.hasResolution()) {
                zap zapVar = this.f12771a;
                zapVar.f12695a.startActivityForResult(GoogleApiActivity.zaa(zapVar.getActivity(), (PendingIntent) Preconditions.checkNotNull(connectionResultB.getResolution()), this.zab.a(), false), 1);
                return;
            }
            zap zapVar2 = this.f12771a;
            if (zapVar2.f12774d.getErrorResolutionIntent(zapVar2.getActivity(), connectionResultB.getErrorCode(), null) != null) {
                zap zapVar3 = this.f12771a;
                zapVar3.f12774d.zag(zapVar3.getActivity(), zapVar3.f12695a, connectionResultB.getErrorCode(), 2, this.f12771a);
                return;
            }
            if (connectionResultB.getErrorCode() != 18) {
                this.f12771a.zaa(connectionResultB, this.zab.a());
                return;
            }
            zap zapVar4 = this.f12771a;
            Dialog dialogZab = zapVar4.f12774d.zab(zapVar4.getActivity(), zapVar4);
            zap zapVar5 = this.f12771a;
            zapVar5.f12774d.zac(zapVar5.getActivity().getApplicationContext(), new zan(this, dialogZab));
        }
    }
}

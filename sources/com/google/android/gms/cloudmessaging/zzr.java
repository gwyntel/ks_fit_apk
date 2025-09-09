package com.google.android.gms.cloudmessaging;

import android.os.Bundle;

/* loaded from: classes3.dex */
final class zzr extends zzp<Bundle> {
    zzr(int i2, int i3, Bundle bundle) {
        super(i2, 1, bundle);
    }

    @Override // com.google.android.gms.cloudmessaging.zzp
    final void a(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("data");
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        d(bundle2);
    }

    @Override // com.google.android.gms.cloudmessaging.zzp
    final boolean b() {
        return false;
    }
}

package com.google.android.gms.cloudmessaging;

import android.os.Bundle;
import com.taobao.accs.utl.BaseMonitor;

/* loaded from: classes3.dex */
final class zzo extends zzp<Void> {
    zzo(int i2, int i3, Bundle bundle) {
        super(i2, 2, bundle);
    }

    @Override // com.google.android.gms.cloudmessaging.zzp
    final void a(Bundle bundle) {
        if (bundle.getBoolean(BaseMonitor.COUNT_ACK, false)) {
            d(null);
        } else {
            c(new zzq(4, "Invalid response to one way request", null));
        }
    }

    @Override // com.google.android.gms.cloudmessaging.zzp
    final boolean b() {
        return true;
    }
}

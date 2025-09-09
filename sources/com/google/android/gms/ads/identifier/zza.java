package com.google.android.gms.ads.identifier;

import android.net.Uri;
import java.util.Map;

/* loaded from: classes3.dex */
final class zza extends Thread {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Map f12603a;

    zza(AdvertisingIdClient advertisingIdClient, Map map) {
        this.f12603a = map;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Map map = this.f12603a;
        Uri.Builder builderBuildUpon = Uri.parse("https://pagead2.googlesyndication.com/pagead/gen_204?id=gmob-apps").buildUpon();
        for (String str : map.keySet()) {
            builderBuildUpon.appendQueryParameter(str, (String) map.get(str));
        }
        zzc.zza(builderBuildUpon.build().toString());
    }
}

package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.firebase.messaging.Constants;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
public final class zzu {
    private final zzhc zza;

    public zzu(zzhc zzhcVar) {
        this.zza = zzhcVar;
    }

    private final boolean zzc() {
        return this.zza.zzn().zzs.zza() > 0;
    }

    private final boolean zzd() {
        return zzc() && this.zza.zzb().currentTimeMillis() - this.zza.zzn().zzs.zza() > this.zza.zzf().zzc(null, zzbi.zzas);
    }

    final void a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.zza.zzl().zzt();
        if (zzc()) {
            if (zzd()) {
                this.zza.zzn().zzr.zza(null);
                Bundle bundle = new Bundle();
                bundle.putString("source", "(not set)");
                bundle.putString("medium", "(not set)");
                bundle.putString("_cis", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK);
                bundle.putLong("_cc", 1L);
                this.zza.zzp().r("auto", "_cmpx", bundle);
            } else {
                String strZza = this.zza.zzn().zzr.zza();
                if (TextUtils.isEmpty(strZza)) {
                    this.zza.zzj().zzm().zza("Cache still valid but referrer not found");
                } else {
                    long jZza = ((this.zza.zzn().zzs.zza() / 3600000) - 1) * 3600000;
                    Uri uri = Uri.parse(strZza);
                    Bundle bundle2 = new Bundle();
                    Pair pair = new Pair(uri.getPath(), bundle2);
                    for (String str : uri.getQueryParameterNames()) {
                        bundle2.putString(str, uri.getQueryParameter(str));
                    }
                    ((Bundle) pair.second).putLong("_cc", jZza);
                    Object obj = pair.first;
                    this.zza.zzp().r(obj == null ? PushConstants.EXTRA_APPLICATION_PENDING_INTENT : (String) obj, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, (Bundle) pair.second);
                }
                this.zza.zzn().zzr.zza(null);
            }
            this.zza.zzn().zzs.zza(0L);
        }
    }

    final void b(String str, Bundle bundle) {
        String string;
        this.zza.zzl().zzt();
        if (this.zza.zzac()) {
            return;
        }
        if (bundle == null || bundle.isEmpty()) {
            string = null;
        } else {
            if (str == null || str.isEmpty()) {
                str = "auto";
            }
            Uri.Builder builder = new Uri.Builder();
            builder.path(str);
            for (String str2 : bundle.keySet()) {
                builder.appendQueryParameter(str2, bundle.getString(str2));
            }
            string = builder.build().toString();
        }
        if (TextUtils.isEmpty(string)) {
            return;
        }
        this.zza.zzn().zzr.zza(string);
        this.zza.zzn().zzs.zza(this.zza.zzb().currentTimeMillis());
    }

    final void c() {
        if (zzc() && zzd()) {
            this.zza.zzn().zzr.zza(null);
        }
    }
}

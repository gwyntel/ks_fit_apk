package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.m.u.i;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class zzaz {

    /* renamed from: a, reason: collision with root package name */
    final String f13260a;

    /* renamed from: b, reason: collision with root package name */
    final String f13261b;

    /* renamed from: c, reason: collision with root package name */
    final long f13262c;

    /* renamed from: d, reason: collision with root package name */
    final long f13263d;

    /* renamed from: e, reason: collision with root package name */
    final zzbb f13264e;
    private final String zzf;

    zzaz(zzhc zzhcVar, String str, String str2, String str3, long j2, long j3, Bundle bundle) {
        zzbb zzbbVar;
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        this.f13260a = str2;
        this.f13261b = str3;
        this.zzf = TextUtils.isEmpty(str) ? null : str;
        this.f13262c = j2;
        this.f13263d = j3;
        if (j3 != 0 && j3 > j2) {
            zzhcVar.zzj().zzu().zza("Event created with reverse previous/current timestamps. appId", zzfs.d(str2));
        }
        if (bundle == null || bundle.isEmpty()) {
            zzbbVar = new zzbb(new Bundle());
        } else {
            Bundle bundle2 = new Bundle(bundle);
            Iterator<String> it = bundle2.keySet().iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next == null) {
                    zzhcVar.zzj().zzg().zza("Param name can't be null");
                    it.remove();
                } else {
                    Object objD = zzhcVar.zzt().D(next, bundle2.get(next));
                    if (objD == null) {
                        zzhcVar.zzj().zzu().zza("Param value can't be null", zzhcVar.zzk().d(next));
                        it.remove();
                    } else {
                        zzhcVar.zzt().k(bundle2, next, objD);
                    }
                }
            }
            zzbbVar = new zzbb(bundle2);
        }
        this.f13264e = zzbbVar;
    }

    final zzaz a(zzhc zzhcVar, long j2) {
        return new zzaz(zzhcVar, this.zzf, this.f13260a, this.f13261b, this.f13262c, j2, this.f13264e);
    }

    public final String toString() {
        return "Event{appId='" + this.f13260a + "', name='" + this.f13261b + "', params=" + String.valueOf(this.f13264e) + i.f9804d;
    }

    private zzaz(zzhc zzhcVar, String str, String str2, String str3, long j2, long j3, zzbb zzbbVar) {
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        Preconditions.checkNotNull(zzbbVar);
        this.f13260a = str2;
        this.f13261b = str3;
        this.zzf = TextUtils.isEmpty(str) ? null : str;
        this.f13262c = j2;
        this.f13263d = j3;
        if (j3 != 0 && j3 > j2) {
            zzhcVar.zzj().zzu().zza("Event created with reverse previous/current timestamps. appId, name", zzfs.d(str2), zzfs.d(str3));
        }
        this.f13264e = zzbbVar;
    }
}

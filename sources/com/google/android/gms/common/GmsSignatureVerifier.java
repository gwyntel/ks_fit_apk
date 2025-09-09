package com.google.android.gms.common;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.internal.common.zzag;
import com.google.errorprone.annotations.RestrictedInheritance;
import java.util.HashMap;

@ShowFirstParty
@KeepForSdk
@RestrictedInheritance(allowedOnPath = ".*javatests/com/google/android/gmscore/integ/client/common/robolectric/.*", explanation = "Sub classing of GMS Core's APIs are restricted to testing fakes.", link = "go/gmscore-restrictedinheritance")
/* loaded from: classes3.dex */
public class GmsSignatureVerifier {
    private static final zzab zza;
    private static final zzab zzb;
    private static final HashMap zzc;

    static {
        zzz zzzVar = new zzz();
        zzzVar.d("com.google.android.gms");
        zzzVar.a(204200000L);
        zzl zzlVar = zzn.f12906d;
        zzzVar.c(zzag.zzn(zzlVar.g(), zzn.f12904b.g()));
        zzl zzlVar2 = zzn.f12905c;
        zzzVar.b(zzag.zzn(zzlVar2.g(), zzn.f12903a.g()));
        zza = zzzVar.e();
        zzz zzzVar2 = new zzz();
        zzzVar2.d("com.android.vending");
        zzzVar2.a(82240000L);
        zzzVar2.c(zzag.zzm(zzlVar.g()));
        zzzVar2.b(zzag.zzm(zzlVar2.g()));
        zzb = zzzVar2.e();
        zzc = new HashMap();
    }
}

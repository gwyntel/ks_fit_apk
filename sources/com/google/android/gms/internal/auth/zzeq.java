package com.google.android.gms.internal.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzeq {
    private static final zzeq zzb = new zzeq(true);

    /* renamed from: a, reason: collision with root package name */
    final zzgv f13014a = new zzgl(16);
    private boolean zzc;
    private boolean zzd;

    private zzeq() {
    }

    public static zzeq zza() {
        throw null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static final void zzd(com.google.android.gms.internal.auth.zzep r4, java.lang.Object r5) {
        /*
            com.google.android.gms.internal.auth.zzho r0 = r4.zzb()
            byte[] r1 = com.google.android.gms.internal.auth.zzfa.zzd
            r5.getClass()
            com.google.android.gms.internal.auth.zzho r1 = com.google.android.gms.internal.auth.zzho.zza
            com.google.android.gms.internal.auth.zzhp r1 = com.google.android.gms.internal.auth.zzhp.INT
            com.google.android.gms.internal.auth.zzhp r0 = r0.zza()
            int r0 = r0.ordinal()
            switch(r0) {
                case 0: goto L43;
                case 1: goto L40;
                case 2: goto L3d;
                case 3: goto L3a;
                case 4: goto L37;
                case 5: goto L34;
                case 6: goto L2b;
                case 7: goto L22;
                case 8: goto L19;
                default: goto L18;
            }
        L18:
            goto L48
        L19:
            boolean r0 = r5 instanceof com.google.android.gms.internal.auth.zzfx
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof com.google.android.gms.internal.auth.zzfc
            if (r0 == 0) goto L48
            goto L47
        L22:
            boolean r0 = r5 instanceof java.lang.Integer
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof com.google.android.gms.internal.auth.zzex
            if (r0 == 0) goto L48
            goto L47
        L2b:
            boolean r0 = r5 instanceof com.google.android.gms.internal.auth.zzef
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof byte[]
            if (r0 == 0) goto L48
            goto L47
        L34:
            boolean r0 = r5 instanceof java.lang.String
            goto L45
        L37:
            boolean r0 = r5 instanceof java.lang.Boolean
            goto L45
        L3a:
            boolean r0 = r5 instanceof java.lang.Double
            goto L45
        L3d:
            boolean r0 = r5 instanceof java.lang.Float
            goto L45
        L40:
            boolean r0 = r5 instanceof java.lang.Long
            goto L45
        L43:
            boolean r0 = r5 instanceof java.lang.Integer
        L45:
            if (r0 == 0) goto L48
        L47:
            return
        L48:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            int r1 = r4.zza()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            com.google.android.gms.internal.auth.zzho r4 = r4.zzb()
            com.google.android.gms.internal.auth.zzhp r4 = r4.zza()
            java.lang.Class r5 = r5.getClass()
            java.lang.String r5 = r5.getName()
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            r2[r3] = r1
            r1 = 1
            r2[r1] = r4
            r4 = 2
            r2[r4] = r5
            java.lang.String r4 = "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n"
            java.lang.String r4 = java.lang.String.format(r4, r2)
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzeq.zzd(com.google.android.gms.internal.auth.zzep, java.lang.Object):void");
    }

    public final /* bridge */ /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzeq zzeqVar = new zzeq();
        for (int i2 = 0; i2 < this.f13014a.zzb(); i2++) {
            Map.Entry entryZzg = this.f13014a.zzg(i2);
            zzeqVar.zzc((zzep) entryZzg.getKey(), entryZzg.getValue());
        }
        for (Map.Entry entry : this.f13014a.zzc()) {
            zzeqVar.zzc((zzep) entry.getKey(), entry.getValue());
        }
        zzeqVar.zzd = this.zzd;
        return zzeqVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzeq) {
            return this.f13014a.equals(((zzeq) obj).f13014a);
        }
        return false;
    }

    public final int hashCode() {
        return this.f13014a.hashCode();
    }

    public final void zzb() {
        if (this.zzc) {
            return;
        }
        for (int i2 = 0; i2 < this.f13014a.zzb(); i2++) {
            Map.Entry entryZzg = this.f13014a.zzg(i2);
            if (entryZzg.getValue() instanceof zzev) {
                ((zzev) entryZzg.getValue()).h();
            }
        }
        this.f13014a.zza();
        this.zzc = true;
    }

    public final void zzc(zzep zzepVar, Object obj) {
        if (!zzepVar.zzc()) {
            zzd(zzepVar, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                zzd(zzepVar, arrayList.get(i2));
            }
            obj = arrayList;
        }
        if (obj instanceof zzfc) {
            this.zzd = true;
        }
        this.f13014a.put(zzepVar, obj);
    }

    private zzeq(boolean z2) {
        zzb();
        zzb();
    }
}

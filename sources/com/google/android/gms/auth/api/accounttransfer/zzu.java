package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.internal.auth.zzbz;
import com.umeng.ccg.a;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SafeParcelable.Class(creator = "AuthenticatorAnnotatedDataCreator")
/* loaded from: classes3.dex */
public final class zzu extends zzbz {
    public static final Parcelable.Creator<zzu> CREATOR = new zzv();
    private static final HashMap zzc;

    /* renamed from: a, reason: collision with root package name */
    final Set f12636a;

    /* renamed from: b, reason: collision with root package name */
    final int f12637b;

    @SafeParcelable.Field(getter = "getInfo", id = 2)
    private zzw zzd;

    @SafeParcelable.Field(getter = "getSignature", id = 3)
    private String zze;

    @SafeParcelable.Field(getter = "getPackageName", id = 4)
    private String zzf;

    @SafeParcelable.Field(getter = "getId", id = 5)
    private String zzg;

    static {
        HashMap map = new HashMap();
        zzc = map;
        map.put("authenticatorInfo", FastJsonResponse.Field.forConcreteType("authenticatorInfo", 2, zzw.class));
        map.put(a.f22022x, FastJsonResponse.Field.forString(a.f22022x, 3));
        map.put("package", FastJsonResponse.Field.forString("package", 4));
    }

    public zzu() {
        this.f12636a = new HashSet(3);
        this.f12637b = 1;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final Object a(FastJsonResponse.Field field) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 1) {
            return Integer.valueOf(this.f12637b);
        }
        if (safeParcelableFieldId == 2) {
            return this.zzd;
        }
        if (safeParcelableFieldId == 3) {
            return this.zze;
        }
        if (safeParcelableFieldId == 4) {
            return this.zzf;
        }
        throw new IllegalStateException("Unknown SafeParcelable id=" + field.getSafeParcelableFieldId());
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void addConcreteTypeInternal(FastJsonResponse.Field field, String str, FastJsonResponse fastJsonResponse) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId != 2) {
            throw new IllegalArgumentException(String.format("Field with id=%d is not a known custom type. Found %s", Integer.valueOf(safeParcelableFieldId), fastJsonResponse.getClass().getCanonicalName()));
        }
        this.zzd = (zzw) fastJsonResponse;
        this.f12636a.add(Integer.valueOf(safeParcelableFieldId));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final boolean b(FastJsonResponse.Field field) {
        return this.f12636a.contains(Integer.valueOf(field.getSafeParcelableFieldId()));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final void g(FastJsonResponse.Field field, String str, String str2) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 3) {
            this.zze = str2;
        } else {
            if (safeParcelableFieldId != 4) {
                throw new IllegalArgumentException(String.format("Field with id=%d is not known to be a string.", Integer.valueOf(safeParcelableFieldId)));
            }
            this.zzf = str2;
        }
        this.f12636a.add(Integer.valueOf(safeParcelableFieldId));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final /* synthetic */ Map getFieldMappings() {
        return zzc;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        Set set = this.f12636a;
        if (set.contains(1)) {
            SafeParcelWriter.writeInt(parcel, 1, this.f12637b);
        }
        if (set.contains(2)) {
            SafeParcelWriter.writeParcelable(parcel, 2, this.zzd, i2, true);
        }
        if (set.contains(3)) {
            SafeParcelWriter.writeString(parcel, 3, this.zze, true);
        }
        if (set.contains(4)) {
            SafeParcelWriter.writeString(parcel, 4, this.zzf, true);
        }
        if (set.contains(5)) {
            SafeParcelWriter.writeString(parcel, 5, this.zzg, true);
        }
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    zzu(Set set, int i2, zzw zzwVar, String str, String str2, String str3) {
        this.f12636a = set;
        this.f12637b = i2;
        this.zzd = zzwVar;
        this.zze = str;
        this.zzf = str2;
        this.zzg = str3;
    }
}

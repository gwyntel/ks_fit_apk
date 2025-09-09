package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.internal.auth.zzbz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SafeParcelable.Class(creator = "AccountTransferMsgCreator")
/* loaded from: classes3.dex */
public final class zzo extends zzbz {
    public static final Parcelable.Creator<zzo> CREATOR = new zzp();
    private static final HashMap zzc;

    /* renamed from: a, reason: collision with root package name */
    final Set f12633a;

    /* renamed from: b, reason: collision with root package name */
    final int f12634b;

    @SafeParcelable.Field(getter = "getAuthenticatorDatas", id = 2)
    private ArrayList zzd;

    @SafeParcelable.Field(getter = "getRequestType", id = 3)
    private int zze;

    @SafeParcelable.Field(getter = "getProgress", id = 4)
    private zzs zzf;

    static {
        HashMap map = new HashMap();
        zzc = map;
        map.put("authenticatorData", FastJsonResponse.Field.forConcreteTypeArray("authenticatorData", 2, zzu.class));
        map.put("progress", FastJsonResponse.Field.forConcreteType("progress", 4, zzs.class));
    }

    public zzo() {
        this.f12633a = new HashSet(1);
        this.f12634b = 1;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final Object a(FastJsonResponse.Field field) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 1) {
            return Integer.valueOf(this.f12634b);
        }
        if (safeParcelableFieldId == 2) {
            return this.zzd;
        }
        if (safeParcelableFieldId == 4) {
            return this.zzf;
        }
        throw new IllegalStateException("Unknown SafeParcelable id=" + field.getSafeParcelableFieldId());
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void addConcreteTypeArrayInternal(FastJsonResponse.Field field, String str, ArrayList arrayList) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId != 2) {
            throw new IllegalArgumentException(String.format("Field with id=%d is not a known ConcreteTypeArray type. Found %s", Integer.valueOf(safeParcelableFieldId), arrayList.getClass().getCanonicalName()));
        }
        this.zzd = arrayList;
        this.f12633a.add(Integer.valueOf(safeParcelableFieldId));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void addConcreteTypeInternal(FastJsonResponse.Field field, String str, FastJsonResponse fastJsonResponse) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId != 4) {
            throw new IllegalArgumentException(String.format("Field with id=%d is not a known custom type. Found %s", Integer.valueOf(safeParcelableFieldId), fastJsonResponse.getClass().getCanonicalName()));
        }
        this.zzf = (zzs) fastJsonResponse;
        this.f12633a.add(Integer.valueOf(safeParcelableFieldId));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected final boolean b(FastJsonResponse.Field field) {
        return this.f12633a.contains(Integer.valueOf(field.getSafeParcelableFieldId()));
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final /* synthetic */ Map getFieldMappings() {
        return zzc;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        Set set = this.f12633a;
        if (set.contains(1)) {
            SafeParcelWriter.writeInt(parcel, 1, this.f12634b);
        }
        if (set.contains(2)) {
            SafeParcelWriter.writeTypedList(parcel, 2, this.zzd, true);
        }
        if (set.contains(3)) {
            SafeParcelWriter.writeInt(parcel, 3, this.zze);
        }
        if (set.contains(4)) {
            SafeParcelWriter.writeParcelable(parcel, 4, this.zzf, i2, true);
        }
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    zzo(Set set, int i2, ArrayList arrayList, int i3, zzs zzsVar) {
        this.f12633a = set;
        this.f12634b = i2;
        this.zzd = arrayList;
        this.zze = i3;
        this.zzf = zzsVar;
    }
}

package com.google.android.gms.auth.api.proxy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ShowFirstParty
@KeepForSdkWithMembers
@SafeParcelable.Class(creator = "ProxyResponseCreator")
/* loaded from: classes3.dex */
public class ProxyResponse extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<ProxyResponse> CREATOR = new zzb();
    public static final int STATUS_CODE_NO_CONNECTION = -1;

    /* renamed from: a, reason: collision with root package name */
    final int f12642a;

    /* renamed from: b, reason: collision with root package name */
    final Bundle f12643b;

    @NonNull
    @SafeParcelable.Field(id = 5)
    public final byte[] body;

    @SafeParcelable.Field(id = 1)
    public final int googlePlayServicesStatusCode;

    @NonNull
    @SafeParcelable.Field(id = 2)
    public final PendingIntent recoveryAction;

    @SafeParcelable.Field(id = 3)
    public final int statusCode;

    ProxyResponse(int i2, int i3, PendingIntent pendingIntent, int i4, Bundle bundle, byte[] bArr) {
        this.f12642a = i2;
        this.googlePlayServicesStatusCode = i3;
        this.statusCode = i4;
        this.f12643b = bundle;
        this.body = bArr;
        this.recoveryAction = pendingIntent;
    }

    @NonNull
    public static ProxyResponse createErrorProxyResponse(int i2, @NonNull PendingIntent pendingIntent, int i3, @NonNull Map<String, String> map, @NonNull byte[] bArr) {
        return new ProxyResponse(1, i2, pendingIntent, i3, zza(map), bArr);
    }

    private static Bundle zza(Map map) {
        Bundle bundle = new Bundle();
        if (map == null) {
            return bundle;
        }
        for (Map.Entry entry : map.entrySet()) {
            bundle.putString((String) entry.getKey(), (String) entry.getValue());
        }
        return bundle;
    }

    @NonNull
    public Map<String, String> getHeaders() {
        if (this.f12643b == null) {
            return Collections.emptyMap();
        }
        HashMap map = new HashMap();
        for (String str : this.f12643b.keySet()) {
            map.put(str, this.f12643b.getString(str));
        }
        return map;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.googlePlayServicesStatusCode);
        SafeParcelWriter.writeParcelable(parcel, 2, this.recoveryAction, i2, false);
        SafeParcelWriter.writeInt(parcel, 3, this.statusCode);
        SafeParcelWriter.writeBundle(parcel, 4, this.f12643b, false);
        SafeParcelWriter.writeByteArray(parcel, 5, this.body, false);
        SafeParcelWriter.writeInt(parcel, 1000, this.f12642a);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public ProxyResponse(int i2, @NonNull PendingIntent pendingIntent, int i3, @NonNull Bundle bundle, @NonNull byte[] bArr) {
        this(1, i2, pendingIntent, i3, bundle, bArr);
    }

    public ProxyResponse(int i2, @NonNull Map<String, String> map, @NonNull byte[] bArr) {
        this(1, 0, null, i2, zza(map), bArr);
    }
}

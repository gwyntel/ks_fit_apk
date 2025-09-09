package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
@SafeParcelable.Class(creator = "GetServiceRequestCreator")
@SafeParcelable.Reserved({9})
/* loaded from: classes3.dex */
public class GetServiceRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zzn();

    /* renamed from: n, reason: collision with root package name */
    static final Scope[] f12813n = new Scope[0];

    /* renamed from: o, reason: collision with root package name */
    static final Feature[] f12814o = new Feature[0];

    /* renamed from: a, reason: collision with root package name */
    final int f12815a;

    /* renamed from: b, reason: collision with root package name */
    final int f12816b;

    /* renamed from: c, reason: collision with root package name */
    final int f12817c;

    /* renamed from: d, reason: collision with root package name */
    String f12818d;

    /* renamed from: e, reason: collision with root package name */
    IBinder f12819e;

    /* renamed from: f, reason: collision with root package name */
    Scope[] f12820f;

    /* renamed from: g, reason: collision with root package name */
    Bundle f12821g;

    /* renamed from: h, reason: collision with root package name */
    Account f12822h;

    /* renamed from: i, reason: collision with root package name */
    Feature[] f12823i;

    /* renamed from: j, reason: collision with root package name */
    Feature[] f12824j;

    /* renamed from: k, reason: collision with root package name */
    final boolean f12825k;

    /* renamed from: l, reason: collision with root package name */
    final int f12826l;

    /* renamed from: m, reason: collision with root package name */
    boolean f12827m;

    @Nullable
    @SafeParcelable.Field(getter = "getAttributionTag", id = 15)
    private final String zzp;

    GetServiceRequest(int i2, int i3, int i4, String str, IBinder iBinder, Scope[] scopeArr, Bundle bundle, Account account, Feature[] featureArr, Feature[] featureArr2, boolean z2, int i5, boolean z3, String str2) {
        scopeArr = scopeArr == null ? f12813n : scopeArr;
        bundle = bundle == null ? new Bundle() : bundle;
        featureArr = featureArr == null ? f12814o : featureArr;
        featureArr2 = featureArr2 == null ? f12814o : featureArr2;
        this.f12815a = i2;
        this.f12816b = i3;
        this.f12817c = i4;
        if ("com.google.android.gms".equals(str)) {
            this.f12818d = "com.google.android.gms";
        } else {
            this.f12818d = str;
        }
        if (i2 < 2) {
            this.f12822h = iBinder != null ? AccountAccessor.getAccountBinderSafe(IAccountAccessor.Stub.asInterface(iBinder)) : null;
        } else {
            this.f12819e = iBinder;
            this.f12822h = account;
        }
        this.f12820f = scopeArr;
        this.f12821g = bundle;
        this.f12823i = featureArr;
        this.f12824j = featureArr2;
        this.f12825k = z2;
        this.f12826l = i5;
        this.f12827m = z3;
        this.zzp = str2;
    }

    @NonNull
    @KeepForSdk
    public Bundle getExtraArgs() {
        return this.f12821g;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i2) {
        zzn.a(this, parcel, i2);
    }

    @Nullable
    public final String zza() {
        return this.zzp;
    }
}

package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ResolveAccountResponseCreator")
/* loaded from: classes3.dex */
public final class zav extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zav> CREATOR = new zaw();

    /* renamed from: a, reason: collision with root package name */
    final int f12846a;

    /* renamed from: b, reason: collision with root package name */
    final IBinder f12847b;

    @SafeParcelable.Field(getter = "getConnectionResult", id = 3)
    private final ConnectionResult zac;

    @SafeParcelable.Field(getter = "getSaveDefaultAccount", id = 4)
    private final boolean zad;

    @SafeParcelable.Field(getter = "isFromCrossClientAuth", id = 5)
    private final boolean zae;

    zav(int i2, IBinder iBinder, ConnectionResult connectionResult, boolean z2, boolean z3) {
        this.f12846a = i2;
        this.f12847b = iBinder;
        this.zac = connectionResult;
        this.zad = z2;
        this.zae = z3;
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zav)) {
            return false;
        }
        zav zavVar = (zav) obj;
        return this.zac.equals(zavVar.zac) && Objects.equal(zab(), zavVar.zab());
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f12846a);
        SafeParcelWriter.writeIBinder(parcel, 2, this.f12847b, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zac, i2, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zad);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zae);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final ConnectionResult zaa() {
        return this.zac;
    }

    @Nullable
    public final IAccountAccessor zab() {
        IBinder iBinder = this.f12847b;
        if (iBinder == null) {
            return null;
        }
        return IAccountAccessor.Stub.asInterface(iBinder);
    }

    public final boolean zac() {
        return this.zad;
    }

    public final boolean zad() {
        return this.zae;
    }
}

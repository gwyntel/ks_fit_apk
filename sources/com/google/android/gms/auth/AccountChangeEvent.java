package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alipay.sdk.m.u.i;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "AccountChangeEventCreator")
/* loaded from: classes3.dex */
public class AccountChangeEvent extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<AccountChangeEvent> CREATOR = new zza();

    /* renamed from: a, reason: collision with root package name */
    final int f12606a;

    /* renamed from: b, reason: collision with root package name */
    final long f12607b;

    /* renamed from: c, reason: collision with root package name */
    final String f12608c;

    /* renamed from: d, reason: collision with root package name */
    final int f12609d;

    /* renamed from: e, reason: collision with root package name */
    final int f12610e;

    /* renamed from: f, reason: collision with root package name */
    final String f12611f;

    AccountChangeEvent(int i2, long j2, String str, int i3, int i4, String str2) {
        this.f12606a = i2;
        this.f12607b = j2;
        this.f12608c = (String) Preconditions.checkNotNull(str);
        this.f12609d = i3;
        this.f12610e = i4;
        this.f12611f = str2;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AccountChangeEvent)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        AccountChangeEvent accountChangeEvent = (AccountChangeEvent) obj;
        return this.f12606a == accountChangeEvent.f12606a && this.f12607b == accountChangeEvent.f12607b && Objects.equal(this.f12608c, accountChangeEvent.f12608c) && this.f12609d == accountChangeEvent.f12609d && this.f12610e == accountChangeEvent.f12610e && Objects.equal(this.f12611f, accountChangeEvent.f12611f);
    }

    @NonNull
    public String getAccountName() {
        return this.f12608c;
    }

    @NonNull
    public String getChangeData() {
        return this.f12611f;
    }

    public int getChangeType() {
        return this.f12609d;
    }

    public int getEventIndex() {
        return this.f12610e;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.f12606a), Long.valueOf(this.f12607b), this.f12608c, Integer.valueOf(this.f12609d), Integer.valueOf(this.f12610e), this.f12611f);
    }

    @NonNull
    public String toString() {
        int i2 = this.f12609d;
        String str = i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? "UNKNOWN" : "RENAMED_TO" : "RENAMED_FROM" : "REMOVED" : "ADDED";
        return "AccountChangeEvent {accountName = " + this.f12608c + ", changeType = " + str + ", changeData = " + this.f12611f + ", eventIndex = " + this.f12610e + i.f9804d;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f12606a);
        SafeParcelWriter.writeLong(parcel, 2, this.f12607b);
        SafeParcelWriter.writeString(parcel, 3, this.f12608c, false);
        SafeParcelWriter.writeInt(parcel, 4, this.f12609d);
        SafeParcelWriter.writeInt(parcel, 5, this.f12610e);
        SafeParcelWriter.writeString(parcel, 6, this.f12611f, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public AccountChangeEvent(long j2, @NonNull String str, int i2, int i3, @NonNull String str2) {
        this.f12606a = 1;
        this.f12607b = j2;
        this.f12608c = (String) Preconditions.checkNotNull(str);
        this.f12609d = i2;
        this.f12610e = i3;
        this.f12611f = str2;
    }
}

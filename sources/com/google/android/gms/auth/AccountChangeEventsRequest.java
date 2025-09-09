package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "AccountChangeEventsRequestCreator")
/* loaded from: classes3.dex */
public class AccountChangeEventsRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<AccountChangeEventsRequest> CREATOR = new zzb();

    /* renamed from: a, reason: collision with root package name */
    final int f12612a;

    /* renamed from: b, reason: collision with root package name */
    int f12613b;

    /* renamed from: c, reason: collision with root package name */
    String f12614c;

    /* renamed from: d, reason: collision with root package name */
    Account f12615d;

    public AccountChangeEventsRequest() {
        this.f12612a = 1;
    }

    @NonNull
    public Account getAccount() {
        return this.f12615d;
    }

    @NonNull
    @Deprecated
    public String getAccountName() {
        return this.f12614c;
    }

    public int getEventIndex() {
        return this.f12613b;
    }

    @NonNull
    public AccountChangeEventsRequest setAccount(@NonNull Account account) {
        this.f12615d = account;
        return this;
    }

    @NonNull
    @Deprecated
    public AccountChangeEventsRequest setAccountName(@NonNull String str) {
        this.f12614c = str;
        return this;
    }

    @NonNull
    public AccountChangeEventsRequest setEventIndex(int i2) {
        this.f12613b = i2;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f12612a);
        SafeParcelWriter.writeInt(parcel, 2, this.f12613b);
        SafeParcelWriter.writeString(parcel, 3, this.f12614c, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.f12615d, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    AccountChangeEventsRequest(int i2, int i3, String str, Account account) {
        this.f12612a = i2;
        this.f12613b = i3;
        this.f12614c = str;
        if (account != null || TextUtils.isEmpty(str)) {
            this.f12615d = account;
        } else {
            this.f12615d = new Account(str, "com.google");
        }
    }
}

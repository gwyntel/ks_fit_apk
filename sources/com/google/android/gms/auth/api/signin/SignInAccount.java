package com.google.android.gms.auth.api.signin;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "SignInAccountCreator")
@SafeParcelable.Reserved({1})
/* loaded from: classes3.dex */
public class SignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final Parcelable.Creator<SignInAccount> CREATOR = new zbc();

    /* renamed from: a, reason: collision with root package name */
    final String f12648a;

    /* renamed from: b, reason: collision with root package name */
    final String f12649b;

    @SafeParcelable.Field(getter = "getGoogleSignInAccount", id = 7)
    private final GoogleSignInAccount zbc;

    SignInAccount(String str, GoogleSignInAccount googleSignInAccount, String str2) {
        this.zbc = googleSignInAccount;
        this.f12648a = Preconditions.checkNotEmpty(str, "8.3 and 8.4 SDKs require non-null email");
        this.f12649b = Preconditions.checkNotEmpty(str2, "8.3 and 8.4 SDKs require non-null userId");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i2) {
        String str = this.f12648a;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 4, str, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zbc, i2, false);
        SafeParcelWriter.writeString(parcel, 8, this.f12649b, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Nullable
    public final GoogleSignInAccount zba() {
        return this.zbc;
    }
}

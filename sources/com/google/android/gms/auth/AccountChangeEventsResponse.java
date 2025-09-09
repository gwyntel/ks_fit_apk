package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

@SafeParcelable.Class(creator = "AccountChangeEventsResponseCreator")
/* loaded from: classes3.dex */
public class AccountChangeEventsResponse extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<AccountChangeEventsResponse> CREATOR = new zzc();

    /* renamed from: a, reason: collision with root package name */
    final int f12616a;

    /* renamed from: b, reason: collision with root package name */
    final List f12617b;

    AccountChangeEventsResponse(int i2, List list) {
        this.f12616a = i2;
        this.f12617b = (List) Preconditions.checkNotNull(list);
    }

    @NonNull
    public List<AccountChangeEvent> getEvents() {
        return this.f12617b;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f12616a);
        SafeParcelWriter.writeTypedList(parcel, 2, this.f12617b, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public AccountChangeEventsResponse(@NonNull List<AccountChangeEvent> list) {
        this.f12616a = 1;
        this.f12617b = (List) Preconditions.checkNotNull(list);
    }
}

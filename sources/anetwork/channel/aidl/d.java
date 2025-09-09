package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
final class d implements Parcelable.Creator<ParcelableHeader> {
    d() {
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public ParcelableHeader createFromParcel(Parcel parcel) {
        return ParcelableHeader.a(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public ParcelableHeader[] newArray(int i2) {
        return new ParcelableHeader[i2];
    }
}

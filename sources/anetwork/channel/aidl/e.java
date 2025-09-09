package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
final class e implements Parcelable.Creator<ParcelableRequest> {
    e() {
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public ParcelableRequest createFromParcel(Parcel parcel) {
        return ParcelableRequest.readFromParcel(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public ParcelableRequest[] newArray(int i2) {
        return new ParcelableRequest[i2];
    }
}

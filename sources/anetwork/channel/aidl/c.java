package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
final class c implements Parcelable.Creator<NetworkResponse> {
    c() {
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public NetworkResponse createFromParcel(Parcel parcel) {
        return NetworkResponse.readFromParcel(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public NetworkResponse[] newArray(int i2) {
        return new NetworkResponse[i2];
    }
}

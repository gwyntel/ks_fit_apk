package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
final class b implements Parcelable.Creator<DefaultProgressEvent> {
    b() {
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public DefaultProgressEvent createFromParcel(Parcel parcel) {
        return DefaultProgressEvent.readFromParcel(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public DefaultProgressEvent[] newArray(int i2) {
        return new DefaultProgressEvent[i2];
    }
}

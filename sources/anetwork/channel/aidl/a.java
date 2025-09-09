package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
final class a implements Parcelable.Creator<DefaultFinishEvent> {
    a() {
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public DefaultFinishEvent createFromParcel(Parcel parcel) {
        return DefaultFinishEvent.a(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public DefaultFinishEvent[] newArray(int i2) {
        return new DefaultFinishEvent[i2];
    }
}

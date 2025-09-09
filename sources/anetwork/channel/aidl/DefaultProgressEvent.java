package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import anetwork.channel.NetworkEvent;

/* loaded from: classes2.dex */
public class DefaultProgressEvent implements Parcelable, NetworkEvent.ProgressEvent {
    public static final Parcelable.Creator<DefaultProgressEvent> CREATOR = new b();

    /* renamed from: a, reason: collision with root package name */
    int f7103a;

    /* renamed from: b, reason: collision with root package name */
    int f7104b;

    /* renamed from: c, reason: collision with root package name */
    int f7105c;

    /* renamed from: d, reason: collision with root package name */
    Object f7106d;

    /* renamed from: e, reason: collision with root package name */
    byte[] f7107e;

    public DefaultProgressEvent() {
    }

    public static DefaultProgressEvent readFromParcel(Parcel parcel) {
        DefaultProgressEvent defaultProgressEvent = new DefaultProgressEvent();
        try {
            defaultProgressEvent.f7103a = parcel.readInt();
            defaultProgressEvent.f7104b = parcel.readInt();
            defaultProgressEvent.f7105c = parcel.readInt();
            int i2 = parcel.readInt();
            if (i2 > 0) {
                byte[] bArr = new byte[i2];
                parcel.readByteArray(bArr);
                defaultProgressEvent.f7107e = bArr;
            }
        } catch (Exception unused) {
        }
        return defaultProgressEvent;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // anetwork.channel.NetworkEvent.ProgressEvent
    public byte[] getBytedata() {
        return this.f7107e;
    }

    public Object getContext() {
        return this.f7106d;
    }

    @Override // anetwork.channel.NetworkEvent.ProgressEvent
    public String getDesc() {
        return "";
    }

    @Override // anetwork.channel.NetworkEvent.ProgressEvent
    public int getIndex() {
        return this.f7103a;
    }

    @Override // anetwork.channel.NetworkEvent.ProgressEvent
    public int getSize() {
        return this.f7104b;
    }

    @Override // anetwork.channel.NetworkEvent.ProgressEvent
    public int getTotal() {
        return this.f7105c;
    }

    public void setContext(Object obj) {
        this.f7106d = obj;
    }

    public String toString() {
        return "DefaultProgressEvent [index=" + this.f7103a + ", size=" + this.f7104b + ", total=" + this.f7105c + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f7103a);
        parcel.writeInt(this.f7104b);
        parcel.writeInt(this.f7105c);
        byte[] bArr = this.f7107e;
        parcel.writeInt(bArr != null ? bArr.length : 0);
        parcel.writeByteArray(this.f7107e);
    }

    public DefaultProgressEvent(int i2, int i3, int i4, byte[] bArr) {
        this.f7103a = i2;
        this.f7104b = i3;
        this.f7105c = i4;
        this.f7107e = bArr;
    }
}

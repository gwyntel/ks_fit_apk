package anetwork.channel.entity;

import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anetwork.channel.aidl.DefaultProgressEvent;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.adapter.ParcelableInputStreamImpl;

/* loaded from: classes2.dex */
class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f7221a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ByteArray f7222b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f7223c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ ParcelableNetworkListener f7224d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ c f7225e;

    e(c cVar, int i2, ByteArray byteArray, int i3, ParcelableNetworkListener parcelableNetworkListener) {
        this.f7225e = cVar;
        this.f7221a = i2;
        this.f7222b = byteArray;
        this.f7223c = i3;
        this.f7224d = parcelableNetworkListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.f7225e.f7215d) {
                try {
                    if (this.f7225e.f7214c == null) {
                        this.f7225e.f7214c = new ParcelableInputStreamImpl();
                        this.f7225e.f7214c.init(this.f7225e.f7216e, this.f7223c);
                        this.f7225e.f7214c.write(this.f7222b);
                        this.f7224d.onInputStreamGet(this.f7225e.f7214c);
                    } else {
                        this.f7225e.f7214c.write(this.f7222b);
                    }
                } catch (Exception unused) {
                    if (this.f7225e.f7214c == null) {
                    } else {
                        this.f7225e.f7214c.close();
                    }
                }
            } else {
                this.f7224d.onDataReceived(new DefaultProgressEvent(this.f7221a, this.f7222b.getDataLength(), this.f7223c, this.f7222b.getBuffer()));
            }
        } catch (RemoteException unused2) {
        }
    }
}

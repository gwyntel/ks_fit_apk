package anetwork.channel.entity;

import android.os.RemoteException;
import anetwork.channel.aidl.ParcelableHeader;
import anetwork.channel.aidl.ParcelableNetworkListener;
import java.util.Map;

/* loaded from: classes2.dex */
class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ParcelableNetworkListener f7217a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ int f7218b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ Map f7219c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ c f7220d;

    d(c cVar, ParcelableNetworkListener parcelableNetworkListener, int i2, Map map) {
        this.f7220d = cVar;
        this.f7217a = parcelableNetworkListener;
        this.f7218b = i2;
        this.f7219c = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f7217a.onResponseCode(this.f7218b, new ParcelableHeader(this.f7218b, this.f7219c));
        } catch (RemoteException unused) {
        }
    }
}

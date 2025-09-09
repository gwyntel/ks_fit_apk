package anetwork.channel.entity;

import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.adapter.ParcelableInputStreamImpl;
import anetwork.channel.interceptor.Callback;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class c implements Callback {

    /* renamed from: a, reason: collision with root package name */
    private ParcelableNetworkListener f7212a;

    /* renamed from: b, reason: collision with root package name */
    private String f7213b;

    /* renamed from: c, reason: collision with root package name */
    private ParcelableInputStreamImpl f7214c = null;

    /* renamed from: d, reason: collision with root package name */
    private boolean f7215d;

    /* renamed from: e, reason: collision with root package name */
    private g f7216e;

    public c(ParcelableNetworkListener parcelableNetworkListener, g gVar) {
        this.f7215d = false;
        this.f7212a = parcelableNetworkListener;
        this.f7216e = gVar;
        if (parcelableNetworkListener != null) {
            try {
                if ((parcelableNetworkListener.getListenerState() & 8) != 0) {
                    this.f7215d = true;
                }
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // anetwork.channel.interceptor.Callback
    public void onDataReceiveSize(int i2, int i3, ByteArray byteArray) {
        ParcelableNetworkListener parcelableNetworkListener = this.f7212a;
        if (parcelableNetworkListener != null) {
            a(new e(this, i2, byteArray, i3, parcelableNetworkListener));
        }
    }

    @Override // anetwork.channel.interceptor.Callback
    public void onFinish(DefaultFinishEvent defaultFinishEvent) {
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.Repeater", "[onFinish] ", this.f7213b, new Object[0]);
        }
        ParcelableNetworkListener parcelableNetworkListener = this.f7212a;
        if (parcelableNetworkListener != null) {
            f fVar = new f(this, defaultFinishEvent, parcelableNetworkListener);
            RequestStatistic requestStatistic = defaultFinishEvent.rs;
            if (requestStatistic != null) {
                requestStatistic.rspCbDispatch = System.currentTimeMillis();
            }
            a(fVar);
        }
        this.f7212a = null;
    }

    @Override // anetwork.channel.interceptor.Callback
    public void onResponseCode(int i2, Map<String, List<String>> map) {
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.Repeater", "[onResponseCode]", this.f7213b, new Object[0]);
        }
        ParcelableNetworkListener parcelableNetworkListener = this.f7212a;
        if (parcelableNetworkListener != null) {
            a(new d(this, parcelableNetworkListener, i2, map));
        }
    }

    private void a(Runnable runnable) {
        if (this.f7216e.c()) {
            runnable.run();
        } else {
            String str = this.f7213b;
            a.a(str != null ? str.hashCode() : hashCode(), runnable);
        }
    }

    public void a(String str) {
        this.f7213b = str;
    }
}

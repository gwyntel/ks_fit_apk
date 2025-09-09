package anetwork.channel.aidl.adapter;

/* loaded from: classes2.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ byte f7126a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Object f7127b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ ParcelableNetworkListenerWrapper f7128c;

    c(ParcelableNetworkListenerWrapper parcelableNetworkListenerWrapper, byte b2, Object obj) {
        this.f7128c = parcelableNetworkListenerWrapper;
        this.f7126a = b2;
        this.f7127b = obj;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7128c.dispatchCallback(this.f7126a, this.f7127b);
    }
}

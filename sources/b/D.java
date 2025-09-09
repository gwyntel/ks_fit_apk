package b;

import b.K;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;

/* loaded from: classes2.dex */
public class D implements K.a.InterfaceC0014a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ExtendedBluetoothDevice f7305a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ K f7306b;

    public D(K k2, ExtendedBluetoothDevice extendedBluetoothDevice) {
        this.f7306b = k2;
        this.f7305a = extendedBluetoothDevice;
    }

    @Override // b.K.a.InterfaceC0014a
    public void a(Object obj) {
        a.a.a.a.b.m.a.a(K.f7315a, "connect to device: " + this.f7305a.getAddress() + ", all connection size: " + this.f7306b.f7339y.size());
        this.f7306b.c((ExtendedBluetoothDevice) obj);
    }
}

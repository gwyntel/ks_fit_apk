package b;

import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class C implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ K f7304a;

    public C(K k2) {
        this.f7304a = k2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f7304a.b();
        Iterator it = this.f7304a.f7340z.iterator();
        while (it.hasNext()) {
            this.f7304a.a((ExtendedBluetoothDevice) it.next());
        }
        this.f7304a.f7340z.clear();
    }
}

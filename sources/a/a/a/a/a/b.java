package a.a.a.a.a;

import androidx.annotation.RequiresApi;
import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;

/* loaded from: classes.dex */
public class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ byte[] f1157a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BleAdvertiseCallback f1158b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ g f1159c;

    public b(g gVar, byte[] bArr, BleAdvertiseCallback bleAdvertiseCallback) {
        this.f1159c = gVar;
        this.f1157a = bArr;
        this.f1158b = bleAdvertiseCallback;
    }

    @Override // java.lang.Runnable
    @RequiresApi(api = 21)
    public void run() {
        this.f1159c.f1173h.b();
        this.f1159c.a(this.f1157a, (BleAdvertiseCallback<Boolean>) this.f1158b);
    }
}

package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* loaded from: classes.dex */
public class la implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1572a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ String f1573b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f1574c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ int f1575d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ int f1576e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ int f1577f;

    /* renamed from: g, reason: collision with root package name */
    public final /* synthetic */ int f1578g;

    /* renamed from: h, reason: collision with root package name */
    public final /* synthetic */ IActionListener f1579h;

    /* renamed from: i, reason: collision with root package name */
    public final /* synthetic */ MeshService.b f1580i;

    public la(MeshService.b bVar, int i2, String str, String str2, int i3, int i4, int i5, int i6, IActionListener iActionListener) {
        this.f1580i = bVar;
        this.f1572a = i2;
        this.f1573b = str;
        this.f1574c = str2;
        this.f1575d = i3;
        this.f1576e = i4;
        this.f1577f = i5;
        this.f1578g = i6;
        this.f1579h = iActionListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        int i2 = this.f1572a;
        if (i2 > 0) {
            this.f1580i.a(this.f1573b, this.f1574c, this.f1575d, this.f1576e, this.f1577f, this.f1578g, this.f1579h, i2 - 1);
        } else {
            Utils.notifyFailed(this.f1579h, -31, "Timeout after 2 seconds of operation");
        }
    }
}

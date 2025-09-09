package b;

import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* renamed from: b.h, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0333h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ProvisionedMeshNode f7479a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f7480b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ IActionListener f7481c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ boolean f7482d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ String f7483e;

    /* renamed from: f, reason: collision with root package name */
    public final /* synthetic */ byte[] f7484f;

    /* renamed from: g, reason: collision with root package name */
    public final /* synthetic */ boolean f7485g;

    /* renamed from: h, reason: collision with root package name */
    public final /* synthetic */ int f7486h;

    /* renamed from: i, reason: collision with root package name */
    public final /* synthetic */ int f7487i;

    /* renamed from: j, reason: collision with root package name */
    public final /* synthetic */ C0337l f7488j;

    public RunnableC0333h(C0337l c0337l, ProvisionedMeshNode provisionedMeshNode, byte[] bArr, IActionListener iActionListener, boolean z2, String str, byte[] bArr2, boolean z3, int i2, int i3) {
        this.f7488j = c0337l;
        this.f7479a = provisionedMeshNode;
        this.f7480b = bArr;
        this.f7481c = iActionListener;
        this.f7482d = z2;
        this.f7483e = str;
        this.f7484f = bArr2;
        this.f7485g = z3;
        this.f7486h = i2;
        this.f7487i = i3;
    }

    @Override // java.lang.Runnable
    public void run() {
        C0337l c0337l = this.f7488j;
        byte[] unicastAddress = this.f7479a.getUnicastAddress();
        byte[] bArr = this.f7480b;
        c0337l.b(unicastAddress, 13871105, new byte[]{bArr[1], bArr[2]}, this.f7481c);
        this.f7488j.b(this.f7479a, this.f7482d, this.f7483e, this.f7484f, this.f7485g, this.f7486h, this.f7487i, this.f7480b, this.f7481c);
    }
}

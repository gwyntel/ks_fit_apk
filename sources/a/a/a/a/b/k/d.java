package a.a.a.a.b.k;

import a.a.a.a.b.i.J;
import a.a.a.a.b.i.c.g;
import aisble.callback.DataReceivedCallback;
import android.content.Context;
import androidx.annotation.RequiresApi;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.utils.SecureUtils;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static final String f1548a = "" + d.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public Context f1549b;

    /* renamed from: c, reason: collision with root package name */
    public FastProvisionTransportCallback f1550c;

    /* renamed from: d, reason: collision with root package name */
    public BaseMeshNode f1551d;

    /* renamed from: e, reason: collision with root package name */
    public a.a.a.a.a.a.a.b.a f1552e;

    /* renamed from: f, reason: collision with root package name */
    public List<a.a.a.a.a.a.a.a.a> f1553f;

    /* renamed from: g, reason: collision with root package name */
    public g f1554g;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f1555h;

    /* renamed from: i, reason: collision with root package name */
    public J f1556i;

    /* renamed from: j, reason: collision with root package name */
    public final DataReceivedCallback f1557j = new a(this);

    public d(Context context, byte[] bArr, FastProvisionTransportCallback fastProvisionTransportCallback, J j2) {
        this.f1556i = null;
        this.f1549b = context;
        this.f1555h = bArr;
        this.f1550c = fastProvisionTransportCallback;
        g gVar = new g();
        this.f1554g = gVar;
        gVar.init(this.f1549b);
        this.f1553f = new ArrayList();
        this.f1556i = j2;
        a.a.a.a.a.g.c().a(this.f1549b);
    }

    public final void b() {
        a.a.a.a.a.a.a.a.a aVar = this.f1553f.get(0);
        a.a.a.a.b.m.a.c(f1548a, "checkControlBufferAndSend, expect " + aVar.e() + ", current " + this.f1553f.size());
        Collections.sort(this.f1553f, new c(this));
        if (aVar.e() == this.f1553f.size()) {
            Iterator<a.a.a.a.a.a.a.a.a> it = this.f1553f.iterator();
            int length = 0;
            while (it.hasNext()) {
                byte[] bArrB = it.next().b();
                if (bArrB != null) {
                    length += bArrB.length;
                }
            }
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length + 1);
            byteBufferAllocate.put((byte) 0);
            Iterator<a.a.a.a.a.a.a.a.a> it2 = this.f1553f.iterator();
            while (it2.hasNext()) {
                byte[] bArrB2 = it2.next().b();
                if (bArrB2 != null) {
                    byteBufferAllocate.put(bArrB2);
                }
            }
            this.f1550c.onReceiveFastProvisionData(this.f1551d, byteBufferAllocate.array());
        }
    }

    public final byte c() {
        a.a.a.a.b.m.a.c(f1548a, "networkKey=" + ConvertUtils.bytes2HexString(this.f1555h));
        return SecureUtils.calculateK2(this.f1555h, SecureUtils.K2_MASTER_INPUT).getNid();
    }

    public void a(BaseMeshNode baseMeshNode, byte[] bArr) {
        byte[] bArr2;
        a.a.a.a.b.m.a.c(f1548a, "before split package " + ConvertUtils.bytes2HexString(bArr));
        this.f1551d = baseMeshNode;
        if (bArr.length >= 1) {
            int length = bArr.length - 1;
            bArr2 = new byte[length];
            System.arraycopy(bArr, 1, bArr2, 0, length);
        } else {
            int length2 = bArr.length;
            bArr2 = new byte[length2];
            System.arraycopy(bArr, 0, bArr2, 0, length2);
        }
        this.f1552e = new a.a.a.a.a.a.a.b.a(bArr2, c(), new b(this, baseMeshNode, bArr));
        a.a.a.a.a.g.c().b(this.f1549b);
        a.a.a.a.a.g.c().a(this.f1552e);
    }

    public void a(Context context) {
        a.a.a.a.b.m.a.c(f1548a, "startScanDeviceAdvertise execute");
        g gVar = this.f1554g;
        if (gVar != null) {
            gVar.a(this.f1557j);
        }
    }

    public final synchronized void a(byte[] bArr) {
        String str = f1548a;
        a.a.a.a.b.m.a.c(str, "assembleControlResp " + ConvertUtils.bytes2HexString(bArr));
        if (this.f1552e == null) {
            a.a.a.a.b.m.a.b(str, "There is no controlMsg");
            return;
        }
        a.a.a.a.a.a.a.a.a aVarA = a.a.a.a.a.a.a.a.a.a(bArr);
        if (aVarA == null) {
            a.a.a.a.b.m.a.b(str, "failed to parse " + ConvertUtils.bytes2HexString(bArr));
            return;
        }
        byte bC = c();
        if (aVarA.d() != bC) {
            a.a.a.a.b.m.a.b(str, "network id not equal, abandon. Expect " + ((int) bC) + ", receive " + ((int) this.f1552e.d()));
            return;
        }
        if (this.f1553f.isEmpty()) {
            this.f1553f.add(aVarA);
            b();
        } else {
            a.a.a.a.a.a.a.a.a aVar = this.f1553f.get(0);
            if (aVar.c() != aVarA.c()) {
                a.a.a.a.b.m.a.b(str, "clear old cache ...");
                this.f1553f.clear();
                this.f1553f.add(aVarA);
                b();
            } else {
                if (aVar.e() != aVarA.e()) {
                    a.a.a.a.b.m.a.b(str, "total package number illegal, expect " + aVar.e() + ", receive " + aVarA.e());
                    return;
                }
                Iterator<a.a.a.a.a.a.a.a.a> it = this.f1553f.iterator();
                while (it.hasNext()) {
                    if (it.next().a() == aVarA.a()) {
                        a.a.a.a.b.m.a.c(f1548a, "index duplicate");
                        return;
                    }
                }
                this.f1553f.add(aVarA);
                b();
            }
        }
    }
}

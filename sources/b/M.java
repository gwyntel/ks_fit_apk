package b;

import android.text.TextUtils;
import b.K;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class M implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ K.b f7362a;

    public M(K.b bVar) {
        this.f7362a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        SIGMeshBizRequest sIGMeshBizRequest;
        K.d dVar;
        LinkedList linkedList = new LinkedList();
        for (Map.Entry entry : K.this.f7320f.entrySet()) {
            if (((K.d) entry.getValue()).d()) {
                linkedList.add(entry.getValue());
            }
        }
        this.f7362a.a(linkedList.size() * 4);
        if (linkedList.size() == 1) {
            a.a.a.a.b.a.K.f1245c = 400;
        } else {
            a.a.a.a.b.a.K.f1245c = 200;
        }
        this.f7362a.f1249g = 0;
        K.b bVar = this.f7362a;
        bVar.f1248f = Math.min(a.a.a.a.b.a.K.f1244b, K.this.C.size());
        if (this.f7362a.f1248f == 0) {
            a.a.a.a.b.m.a.d(this.f7362a.f7350j, "interrupting the dispatch biz-request process, there may be no proxy connections currently available");
            this.f7362a.f1250h = false;
            while (!K.this.C.isEmpty()) {
                Utils.notifyFailed(((SIGMeshBizRequest) K.this.C.poll()).m(), -23, "Unreachable");
            }
            return;
        }
        int size = 0;
        for (int i2 = 0; i2 < this.f7362a.f1248f && K.this.C.size() > 0; i2++) {
            synchronized (K.this.C) {
                sIGMeshBizRequest = (SIGMeshBizRequest) K.this.C.poll();
            }
            if (sIGMeshBizRequest != null) {
                String strK = sIGMeshBizRequest.k();
                if (!TextUtils.isEmpty(strK) && (dVar = (K.d) K.this.f7320f.get(strK.toUpperCase())) != null && dVar.f7352a.getWriteReadType() == BleMeshManager.WriteReadType.WRITE) {
                    dVar.a(BleMeshManager.WriteReadType.WRITE_AND_READ);
                    sIGMeshBizRequest.a(new L(this, strK, dVar));
                }
                K.d dVar2 = (K.d) linkedList.get(size);
                sIGMeshBizRequest.a(dVar2.f7353b);
                size = (size + 1) % linkedList.size();
                if (i2 != 0) {
                    try {
                        Thread.sleep(a.a.a.a.b.a.K.f1245c);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                a.a.a.a.b.m.a.a(this.f7362a.f7350j, String.format(Locale.US, "Execute request(to %s) via proxy node: %s", Utils.bytes2HexString(sIGMeshBizRequest.j()), dVar2.f7355d.getAddress()));
                this.f7362a.a(sIGMeshBizRequest);
            }
        }
    }
}

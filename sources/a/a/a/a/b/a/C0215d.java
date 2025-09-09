package a.a.a.a.b.a;

import b.C0337l;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import java.util.Locale;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.MeshParserUtils;

/* renamed from: a.a.a.a.b.a.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0215d {

    /* renamed from: a, reason: collision with root package name */
    public C0337l f1263a;

    public C0215d(C0337l c0337l) {
        this.f1263a = c0337l;
    }

    public void a(SIGMeshBizRequest sIGMeshBizRequest) {
        boolean zA = true;
        if (sIGMeshBizRequest == null) {
            a.a.a.a.b.m.a.b("DefaultExecutionDispatcher", "Execute null request");
            return;
        }
        a.a.a.a.b.m.a.a("DefaultExecutionDispatcher", String.format(Locale.US, "Execute request(to %s)", Utils.bytes2HexString(sIGMeshBizRequest.j())));
        SIGMeshBizRequest.Type typeL = sIGMeshBizRequest.l();
        IActionListener iActionListenerM = sIGMeshBizRequest.m();
        byte[] bArrJ = sIGMeshBizRequest.j();
        SIGMeshBizRequest.InteractionModel interactionModel = typeL.getInteractionModel();
        if (AddressUtils.isValidGroupAddress(bArrJ)) {
            a.a.a.a.b.m.a.c("DefaultExecutionDispatcher", "Dst address is group address, fire and forget");
            interactionModel = SIGMeshBizRequest.InteractionModel.FIRE_AND_FORGET;
        }
        C0337l c0337lI = this.f1263a;
        if (sIGMeshBizRequest.i() != null) {
            c0337lI = sIGMeshBizRequest.i();
        }
        if (c0337lI == null) {
            return;
        }
        int i2 = C0214c.f1262a[interactionModel.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            I iG = sIGMeshBizRequest.g();
            sIGMeshBizRequest.o();
            c0337lI.a(sIGMeshBizRequest.f8736f, sIGMeshBizRequest.d() != null ? MeshParserUtils.bytesToHex(sIGMeshBizRequest.d(), false) : null, bArrJ, sIGMeshBizRequest.f(), sIGMeshBizRequest.e(), sIGMeshBizRequest.c(), new C0213b(this, sIGMeshBizRequest, iG, iActionListenerM));
            return;
        }
        if (sIGMeshBizRequest.d() != null) {
            c0337lI.a(sIGMeshBizRequest.f8736f, typeL.isAccess(), MeshParserUtils.bytesToHex(sIGMeshBizRequest.d(), false), bArrJ, false, -1, typeL.getOpcode(), sIGMeshBizRequest.c());
        } else {
            zA = c0337lI.a(sIGMeshBizRequest.f8736f, typeL.isAccess(), bArrJ, typeL.getOpcode(), sIGMeshBizRequest.c());
        }
        if (zA) {
            Utils.notifySuccess((IActionListener<Boolean>) iActionListenerM, Boolean.TRUE);
        } else {
            Utils.notifyFailed(iActionListenerM, -21, "Parameters appKeys are not passed");
        }
        try {
            Thread.sleep(200L);
            c(sIGMeshBizRequest);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public void b(SIGMeshBizRequest sIGMeshBizRequest) {
        Utils.notifyFailed(sIGMeshBizRequest.m(), -13, "Timeout! the device is not reply");
    }

    public void c(SIGMeshBizRequest sIGMeshBizRequest) {
    }
}

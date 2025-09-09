package a.a.a.a.b;

import android.os.Handler;
import android.os.Looper;
import b.u;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.android.multiendinonebridge.IoTMultiendInOneBridge;
import com.alibaba.android.multiendinonebridge.SigMeshNetworkParameters;
import datasource.bean.SigmeshIotDev;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import meshprovisioner.utils.MeshParserUtils;

/* renamed from: a.a.a.a.b.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0226d {

    /* renamed from: a, reason: collision with root package name */
    public Handler f1332a;

    /* renamed from: a.a.a.a.b.d$a */
    private static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final C0226d f1333a = new C0226d(null);
    }

    public /* synthetic */ C0226d(C0225c c0225c) {
        this();
    }

    public static C0226d a() {
        return a.f1333a;
    }

    public void b() {
        a.a.a.a.b.m.a.c("BleMeshHeartReportManager", "updateMeshNetworkParameters");
        ConcurrentHashMap<String, SigmeshIotDev> sigmeshIotDevMap = MeshDeviceInfoManager.getInstance().getSigmeshIotDevMap();
        if (sigmeshIotDevMap == null || sigmeshIotDevMap.size() <= 0) {
            return;
        }
        SIGMeshBizRequest.NetworkParameter networkParameter = SIGMeshBizRequest.NetworkParameter.getNetworkParameter(sigmeshIotDevMap.size());
        u.a aVarD = G.a().d().d();
        SigMeshNetworkParameters sigMeshNetworkParameters = new SigMeshNetworkParameters();
        int i2 = 0;
        sigMeshNetworkParameters.ct_enable = networkParameter.cr_enable == 1;
        sigMeshNetworkParameters.send_ttl = (byte) 3;
        sigMeshNetworkParameters.group_delay_min = (byte) networkParameter.group_delay_min;
        sigMeshNetworkParameters.group_delay_max = (byte) networkParameter.group_delay_max;
        sigMeshNetworkParameters.boot_interval = (byte) networkParameter.boot_interval;
        sigMeshNetworkParameters.adv_duration = (byte) networkParameter.adv_duration;
        sigMeshNetworkParameters.per_interval = (byte) networkParameter.per_interval;
        IoTMultiendInOneBridge.getInstance().updateSigMeshNetworkParameter(MeshParserUtils.bytesToHex(aVarD.d(), false), sigMeshNetworkParameters);
        LinkedList linkedList = new LinkedList();
        while (true) {
            int i3 = 2;
            if (i2 >= 2) {
                a(linkedList);
                return;
            }
            int i4 = i2 == 0 ? 5 : 3;
            if (i2 == 0) {
                i3 = 5;
            }
            networkParameter.setSend_ttl(i4);
            linkedList.add(SIGMeshBizRequestGenerator.a(networkParameter.getParameter(), i3, new C0225c(this)));
            i2++;
        }
    }

    public C0226d() {
        this.f1332a = new Handler(Looper.myLooper());
    }

    public void a(List<SIGMeshBizRequest> list) {
        b.K kE;
        a.a.a.a.b.m.a.c("BleMeshHeartReportManager", "offerBizRequest");
        if (list == null || list.size() == 0) {
            return;
        }
        u.a aVarH = list.get(0).h();
        if (aVarH != null && (kE = aVarH.e()) != null) {
            kE.a(list);
            return;
        }
        Iterator<SIGMeshBizRequest> it = list.iterator();
        while (it.hasNext()) {
            Utils.notifyFailed(it.next().m(), -30, "Internal error");
        }
    }
}

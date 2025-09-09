package anet.channel.util;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.NetTypeStat;
import anet.channel.status.NetworkStatusHelper;

/* loaded from: classes2.dex */
class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ d f7086a;

    e(d dVar) {
        this.f7086a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        f fVarK;
        try {
            if (this.f7086a.f7084a.equals(c.b(NetworkStatusHelper.getStatus()))) {
                ALog.e("awcn.Inet64Util", "startIpStackDetect double check", null, new Object[0]);
                int iJ = c.j();
                d dVar = this.f7086a;
                if (dVar.f7085b.ipStackType != iJ) {
                    c.f7083e.put(dVar.f7084a, Integer.valueOf(iJ));
                    NetTypeStat netTypeStat = this.f7086a.f7085b;
                    netTypeStat.lastIpStackType = netTypeStat.ipStackType;
                    netTypeStat.ipStackType = iJ;
                }
                if ((iJ == 2 || iJ == 3) && (fVarK = c.k()) != null) {
                    c.f7082d.put(this.f7086a.f7084a, fVarK);
                    this.f7086a.f7085b.nat64Prefix = fVarK.toString();
                }
                if (GlobalAppRuntimeInfo.isTargetProcess()) {
                    AppMonitor.getInstance().commitStat(this.f7086a.f7085b);
                }
            }
        } catch (Exception unused) {
        }
    }
}

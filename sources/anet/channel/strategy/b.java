package anet.channel.strategy;

import anet.channel.util.ALog;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.taobao.accs.common.Constants;
import java.net.InetAddress;
import java.util.Collections;
import java.util.LinkedList;

/* loaded from: classes2.dex */
class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f6983a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Object f6984b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ a f6985c;

    b(a aVar, String str, Object obj) {
        this.f6985c = aVar;
        this.f6983a = str;
        this.f6984b = obj;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                String hostAddress = InetAddress.getByName(this.f6983a).getHostAddress();
                LinkedList linkedList = new LinkedList();
                ConnProtocol connProtocol = StrategyTemplate.getInstance().getConnProtocol(this.f6983a);
                if (connProtocol != null) {
                    linkedList.add(IPConnStrategy.a(hostAddress, !this.f6985c.a(connProtocol) ? 80 : Constants.PORT, connProtocol, 0, 0, 1, 45000));
                }
                linkedList.add(IPConnStrategy.a(hostAddress, 80, ConnProtocol.HTTP, 0, 0, 0, 0));
                linkedList.add(IPConnStrategy.a(hostAddress, Constants.PORT, ConnProtocol.HTTPS, 0, 0, 0, 0));
                this.f6985c.f6981a.put(this.f6983a, linkedList);
                if (ALog.isPrintLog(1)) {
                    ALog.d("awcn.LocalDnsStrategyTable", "resolve ip by local dns", null, "host", this.f6983a, "ip", hostAddress, AlinkConstants.KEY_LIST, linkedList);
                }
                synchronized (this.f6985c.f6982b) {
                    this.f6985c.f6982b.remove(this.f6983a);
                }
                synchronized (this.f6984b) {
                    this.f6984b.notifyAll();
                }
            } catch (Exception unused) {
                if (ALog.isPrintLog(1)) {
                    ALog.d("awcn.LocalDnsStrategyTable", "resolve ip by local dns failed", null, "host", this.f6983a);
                }
                this.f6985c.f6981a.put(this.f6983a, Collections.EMPTY_LIST);
                synchronized (this.f6985c.f6982b) {
                    this.f6985c.f6982b.remove(this.f6983a);
                    synchronized (this.f6984b) {
                        this.f6984b.notifyAll();
                    }
                }
            }
        } catch (Throwable th) {
            synchronized (this.f6985c.f6982b) {
                this.f6985c.f6982b.remove(this.f6983a);
                synchronized (this.f6984b) {
                    this.f6984b.notifyAll();
                    throw th;
                }
            }
        }
    }
}

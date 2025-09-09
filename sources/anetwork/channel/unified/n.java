package anetwork.channel.unified;

import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anetwork.channel.aidl.DefaultFinishEvent;

/* loaded from: classes2.dex */
class n implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ k f7299a;

    n(k kVar) {
        this.f7299a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f7299a.f7292a.f7289d.compareAndSet(false, true)) {
            RequestStatistic requestStatistic = this.f7299a.f7292a.f7286a.f7230b;
            if (requestStatistic.isDone.compareAndSet(false, true)) {
                requestStatistic.statusCode = -202;
                requestStatistic.msg = ErrorConstant.getErrMsg(-202);
                requestStatistic.rspEnd = System.currentTimeMillis();
                ALog.e("anet.UnifiedRequestTask", "task time out", this.f7299a.f7292a.f7288c, "rs", requestStatistic);
                AppMonitor.getInstance().commitStat(new ExceptionStatistic(-202, null, requestStatistic, null));
            }
            this.f7299a.f7292a.b();
            this.f7299a.f7292a.f7287b.onFinish(new DefaultFinishEvent(-202, (String) null, this.f7299a.f7292a.f7286a.a()));
        }
    }
}

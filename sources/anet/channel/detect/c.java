package anet.channel.detect;

import android.text.TextUtils;
import android.util.Pair;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ RequestStatistic f6723a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ExceptionDetector f6724b;

    c(ExceptionDetector exceptionDetector, RequestStatistic requestStatistic) {
        this.f6724b = exceptionDetector;
        this.f6723a = requestStatistic;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            RequestStatistic requestStatistic = this.f6723a;
            if (requestStatistic == null) {
                return;
            }
            if (!TextUtils.isEmpty(requestStatistic.ip) && this.f6723a.ret == 0) {
                if ("guide-acs.m.taobao.com".equalsIgnoreCase(this.f6723a.host)) {
                    this.f6724b.f6708b = this.f6723a.ip;
                } else if ("msgacs.m.taobao.com".equalsIgnoreCase(this.f6723a.host)) {
                    this.f6724b.f6709c = this.f6723a.ip;
                } else if ("gw.alicdn.com".equalsIgnoreCase(this.f6723a.host)) {
                    this.f6724b.f6710d = this.f6723a.ip;
                }
            }
            if (!TextUtils.isEmpty(this.f6723a.url)) {
                this.f6724b.f6711e.add(Pair.create(this.f6723a.url, Integer.valueOf(this.f6723a.statusCode)));
            }
            if (this.f6724b.c()) {
                this.f6724b.b();
            }
        } catch (Throwable th) {
            ALog.e("anet.ExceptionDetector", "network detect fail.", null, th, new Object[0]);
        }
    }
}

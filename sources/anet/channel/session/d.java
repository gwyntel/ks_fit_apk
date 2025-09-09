package anet.channel.session;

import android.content.Context;
import anet.channel.AwcnConfig;
import anet.channel.RequestCb;
import anet.channel.Session;
import anet.channel.entity.ConnType;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.strategy.IConnStrategy;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.Utils;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes2.dex */
public class d extends Session {

    /* renamed from: w, reason: collision with root package name */
    private SSLSocketFactory f6912w;

    public d(Context context, anet.channel.entity.a aVar) {
        super(context, aVar);
        if (this.f6627k == null) {
            String str = this.f6619c;
            this.f6626j = (str == null || !str.startsWith("https")) ? ConnType.HTTP : ConnType.HTTPS;
        } else if (AwcnConfig.isHttpsSniEnable() && this.f6626j.equals(ConnType.HTTPS)) {
            this.f6912w = new anet.channel.util.j(this.f6620d);
        }
    }

    @Override // anet.channel.Session
    public void close() {
        notifyStatus(6, null);
    }

    @Override // anet.channel.Session
    public void connect() {
        try {
            IConnStrategy iConnStrategy = this.f6627k;
            if (iConnStrategy != null && iConnStrategy.getIpSource() == 1) {
                notifyStatus(4, new anet.channel.entity.b(1));
                return;
            }
            Request.Builder redirectEnable = new Request.Builder().setUrl(this.f6619c).setSeq(this.f6632p).setConnectTimeout((int) (this.f6634r * Utils.getNetworkTimeFactor())).setReadTimeout((int) (this.f6635s * Utils.getNetworkTimeFactor())).setRedirectEnable(false);
            SSLSocketFactory sSLSocketFactory = this.f6912w;
            if (sSLSocketFactory != null) {
                redirectEnable.setSslSocketFactory(sSLSocketFactory);
            }
            if (this.f6629m) {
                redirectEnable.addHeader("Host", this.f6621e);
            }
            if (anet.channel.util.c.a() && anet.channel.strategy.utils.c.a(this.f6621e)) {
                try {
                    this.f6622f = anet.channel.util.c.a(this.f6621e);
                } catch (Exception unused) {
                }
            }
            ALog.i("awcn.HttpSession", "HttpSession connect", null, "host", this.f6619c, "ip", this.f6622f, "port", Integer.valueOf(this.f6623g));
            Request requestBuild = redirectEnable.build();
            requestBuild.setDnsOptimize(this.f6622f, this.f6623g);
            ThreadPoolExecutorFactory.submitPriorityTask(new e(this, requestBuild), ThreadPoolExecutorFactory.Priority.LOW);
        } catch (Throwable th) {
            ALog.e("awcn.HttpSession", "HTTP connect fail.", null, th, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // anet.channel.Session
    public Runnable getRecvTimeOutRunnable() {
        return null;
    }

    @Override // anet.channel.Session
    public boolean isAvailable() {
        return this.f6630n == 4;
    }

    @Override // anet.channel.Session
    public Cancelable request(Request request, RequestCb requestCb) {
        anet.channel.request.b bVar = anet.channel.request.b.NULL;
        Request.Builder builderNewBuilder = null;
        RequestStatistic requestStatistic = request != null ? request.f6850a : new RequestStatistic(this.f6620d, null);
        requestStatistic.setConnType(this.f6626j);
        if (requestStatistic.start == 0) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            requestStatistic.reqStart = jCurrentTimeMillis;
            requestStatistic.start = jCurrentTimeMillis;
        }
        if (request == null || requestCb == null) {
            if (requestCb != null) {
                requestCb.onFinish(-102, ErrorConstant.getErrMsg(-102), requestStatistic);
            }
            return bVar;
        }
        try {
            if (request.getSslSocketFactory() == null && this.f6912w != null) {
                builderNewBuilder = request.newBuilder().setSslSocketFactory(this.f6912w);
            }
            if (this.f6629m) {
                if (builderNewBuilder == null) {
                    builderNewBuilder = request.newBuilder();
                }
                builderNewBuilder.addHeader("Host", this.f6621e);
            }
            if (builderNewBuilder != null) {
                request = builderNewBuilder.build();
            }
            if (this.f6622f == null) {
                String strHost = request.getHttpUrl().host();
                if (anet.channel.util.c.a() && anet.channel.strategy.utils.c.a(strHost)) {
                    try {
                        this.f6622f = anet.channel.util.c.a(strHost);
                    } catch (Exception unused) {
                    }
                }
            }
            request.setDnsOptimize(this.f6622f, this.f6623g);
            request.setUrlScheme(this.f6626j.isSSL());
            IConnStrategy iConnStrategy = this.f6627k;
            if (iConnStrategy != null) {
                request.f6850a.setIpInfo(iConnStrategy.getIpSource(), this.f6627k.getIpType());
            } else {
                request.f6850a.setIpInfo(1, 1);
            }
            request.f6850a.unit = this.f6628l;
            return new anet.channel.request.b(ThreadPoolExecutorFactory.submitPriorityTask(new f(this, request, requestCb, requestStatistic), anet.channel.util.h.a(request)), request.getSeq());
        } catch (Throwable th) {
            requestCb.onFinish(-101, ErrorConstant.formatMsg(-101, th.toString()), requestStatistic);
            return bVar;
        }
    }

    @Override // anet.channel.Session
    public void close(boolean z2) {
        this.f6636t = false;
        close();
    }
}

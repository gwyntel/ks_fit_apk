package anetwork.channel.unified;

import android.os.Looper;
import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.request.Request;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.cache.CacheManager;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.interceptor.Callback;
import anetwork.channel.interceptor.Interceptor;
import anetwork.channel.interceptor.InterceptorManager;
import anetwork.channel.util.RequestConstant;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
class k {

    /* renamed from: a, reason: collision with root package name */
    protected j f7292a;

    class a implements Interceptor.Chain {

        /* renamed from: b, reason: collision with root package name */
        private int f7294b;

        /* renamed from: c, reason: collision with root package name */
        private Request f7295c;

        /* renamed from: d, reason: collision with root package name */
        private Callback f7296d;

        a(int i2, Request request, Callback callback) {
            this.f7294b = i2;
            this.f7295c = request;
            this.f7296d = callback;
        }

        @Override // anetwork.channel.interceptor.Interceptor.Chain
        public Callback callback() {
            return this.f7296d;
        }

        @Override // anetwork.channel.interceptor.Interceptor.Chain
        public Future proceed(Request request, Callback callback) {
            if (k.this.f7292a.f7289d.get()) {
                ALog.i("anet.UnifiedRequestTask", "request canneled or timeout in processing interceptor", request.getSeq(), new Object[0]);
                return null;
            }
            if (this.f7294b < InterceptorManager.getSize()) {
                return InterceptorManager.getInterceptor(this.f7294b).intercept(k.this.new a(this.f7294b + 1, request, callback));
            }
            k.this.f7292a.f7286a.a(request);
            k.this.f7292a.f7287b = callback;
            Cache cache = NetworkConfigCenter.isHttpCacheEnable() ? CacheManager.getCache(k.this.f7292a.f7286a.g(), k.this.f7292a.f7286a.h()) : null;
            j jVar = k.this.f7292a;
            jVar.f7290e = cache != null ? new anetwork.channel.unified.a(jVar, cache) : new e(jVar, null, null);
            k.this.f7292a.f7290e.run();
            k.this.c();
            return null;
        }

        @Override // anetwork.channel.interceptor.Interceptor.Chain
        public Request request() {
            return this.f7295c;
        }
    }

    public k(anetwork.channel.entity.g gVar, anetwork.channel.entity.c cVar) {
        cVar.a(gVar.f7233e);
        this.f7292a = new j(gVar, cVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.f7292a.f7291f = ThreadPoolExecutorFactory.submitScheduledTask(new n(this), this.f7292a.f7286a.b(), TimeUnit.MILLISECONDS);
    }

    void b() {
        if (this.f7292a.f7289d.compareAndSet(false, true)) {
            ALog.e("anet.UnifiedRequestTask", "task cancelled", this.f7292a.f7288c, "URL", this.f7292a.f7286a.f().simpleUrlString());
            RequestStatistic requestStatistic = this.f7292a.f7286a.f7230b;
            if (requestStatistic.isDone.compareAndSet(false, true)) {
                requestStatistic.ret = 2;
                requestStatistic.statusCode = -204;
                requestStatistic.msg = ErrorConstant.getErrMsg(-204);
                requestStatistic.rspEnd = System.currentTimeMillis();
                AppMonitor.getInstance().commitStat(new ExceptionStatistic(-204, null, requestStatistic, null));
                if (requestStatistic.recDataSize > OSSConstants.MIN_PART_SIZE_LIMIT) {
                    anet.channel.monitor.b.a().a(requestStatistic.sendStart, requestStatistic.rspEnd, requestStatistic.recDataSize);
                }
            }
            this.f7292a.b();
            this.f7292a.a();
            this.f7292a.f7287b.onFinish(new DefaultFinishEvent(-204, (String) null, this.f7292a.f7286a.a()));
        }
    }

    public Future a() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.f7292a.f7286a.f7230b.reqServiceTransmissionEnd = jCurrentTimeMillis;
        this.f7292a.f7286a.f7230b.start = jCurrentTimeMillis;
        anetwork.channel.entity.g gVar = this.f7292a.f7286a;
        gVar.f7230b.isReqSync = gVar.c();
        this.f7292a.f7286a.f7230b.isReqMain = Looper.myLooper() == Looper.getMainLooper();
        try {
            anetwork.channel.entity.g gVar2 = this.f7292a.f7286a;
            gVar2.f7230b.netReqStart = Long.valueOf(gVar2.a(RequestConstant.KEY_REQ_START)).longValue();
        } catch (Exception unused) {
        }
        String strA = this.f7292a.f7286a.a(RequestConstant.KEY_TRACE_ID);
        if (!TextUtils.isEmpty(strA)) {
            this.f7292a.f7286a.f7230b.traceId = strA;
        }
        String strA2 = this.f7292a.f7286a.a(RequestConstant.KEY_REQ_PROCESS);
        anetwork.channel.entity.g gVar3 = this.f7292a.f7286a;
        RequestStatistic requestStatistic = gVar3.f7230b;
        requestStatistic.process = strA2;
        requestStatistic.pTraceId = gVar3.a(RequestConstant.KEY_PARENT_TRACE_ID);
        j jVar = this.f7292a;
        ALog.e("anet.UnifiedRequestTask", "[traceId:" + strA + "]start", jVar.f7288c, "bizId", jVar.f7286a.a().getBizId(), "processFrom", strA2, "url", this.f7292a.f7286a.g());
        if (!NetworkConfigCenter.isUrlInDegradeList(this.f7292a.f7286a.f())) {
            ThreadPoolExecutorFactory.submitPriorityTask(new m(this), ThreadPoolExecutorFactory.Priority.HIGH);
            return new d(this);
        }
        b bVar = new b(this.f7292a);
        this.f7292a.f7290e = bVar;
        bVar.f7244a = new anet.channel.request.b(ThreadPoolExecutorFactory.submitBackupTask(new l(this)), this.f7292a.f7286a.a().getSeq());
        c();
        return new d(this);
    }
}

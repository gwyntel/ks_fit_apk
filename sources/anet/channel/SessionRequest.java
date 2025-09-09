package anet.channel;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.entity.ConnType;
import anet.channel.entity.EventType;
import anet.channel.session.TnetSpdySession;
import anet.channel.statist.AlarmObject;
import anet.channel.statist.SessionConnStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.taobao.accs.common.Constants;
import com.umeng.analytics.pro.bc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
class SessionRequest {

    /* renamed from: a, reason: collision with root package name */
    SessionCenter f6655a;

    /* renamed from: b, reason: collision with root package name */
    e f6656b;

    /* renamed from: c, reason: collision with root package name */
    SessionInfo f6657c;

    /* renamed from: e, reason: collision with root package name */
    volatile Session f6659e;

    /* renamed from: i, reason: collision with root package name */
    private String f6663i;

    /* renamed from: j, reason: collision with root package name */
    private String f6664j;

    /* renamed from: k, reason: collision with root package name */
    private volatile Future f6665k;

    /* renamed from: d, reason: collision with root package name */
    volatile boolean f6658d = false;

    /* renamed from: f, reason: collision with root package name */
    volatile boolean f6660f = false;

    /* renamed from: g, reason: collision with root package name */
    HashMap<SessionGetCallback, c> f6661g = new HashMap<>();

    /* renamed from: h, reason: collision with root package name */
    SessionConnStat f6662h = null;

    /* renamed from: l, reason: collision with root package name */
    private Object f6666l = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    interface IConnCb {
        void onDisConnect(Session session, long j2, int i2);

        void onFailed(Session session, long j2, int i2, int i3);

        void onSuccess(Session session, long j2);
    }

    class a implements IConnCb {

        /* renamed from: a, reason: collision with root package name */
        boolean f6667a = false;

        /* renamed from: c, reason: collision with root package name */
        private Context f6669c;

        /* renamed from: d, reason: collision with root package name */
        private List<anet.channel.entity.a> f6670d;

        /* renamed from: e, reason: collision with root package name */
        private anet.channel.entity.a f6671e;

        a(Context context, List<anet.channel.entity.a> list, anet.channel.entity.a aVar) {
            this.f6669c = context;
            this.f6670d = list;
            this.f6671e = aVar;
        }

        @Override // anet.channel.SessionRequest.IConnCb
        public void onDisConnect(Session session, long j2, int i2) {
            SessionInfo sessionInfo;
            boolean zIsAppBackground = GlobalAppRuntimeInfo.isAppBackground();
            ALog.d("awcn.SessionRequest", "Connect Disconnect", this.f6671e.h(), "session", session, "host", SessionRequest.this.a(), "appIsBg", Boolean.valueOf(zIsAppBackground), "isHandleFinish", Boolean.valueOf(this.f6667a));
            SessionRequest sessionRequest = SessionRequest.this;
            sessionRequest.f6656b.b(sessionRequest, session);
            if (this.f6667a) {
                return;
            }
            this.f6667a = true;
            if (session.f6636t) {
                if (zIsAppBackground && ((sessionInfo = SessionRequest.this.f6657c) == null || !sessionInfo.isAccs || AwcnConfig.isAccsSessionCreateForbiddenInBg())) {
                    ALog.e("awcn.SessionRequest", "[onDisConnect]app background, don't Recreate", this.f6671e.h(), "session", session);
                    return;
                }
                if (!NetworkStatusHelper.isConnected()) {
                    ALog.e("awcn.SessionRequest", "[onDisConnect]no network, don't Recreate", this.f6671e.h(), "session", session);
                    return;
                }
                try {
                    ALog.d("awcn.SessionRequest", "session disconnected, try to recreate session", this.f6671e.h(), new Object[0]);
                    SessionInfo sessionInfo2 = SessionRequest.this.f6657c;
                    ThreadPoolExecutorFactory.submitScheduledTask(new i(this, session), (long) (Math.random() * ((sessionInfo2 == null || !sessionInfo2.isAccs) ? 10000 : AwcnConfig.getAccsReconnectionDelayPeriod())), TimeUnit.MILLISECONDS);
                } catch (Exception unused) {
                }
            }
        }

        @Override // anet.channel.SessionRequest.IConnCb
        public void onFailed(Session session, long j2, int i2, int i3) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.SessionRequest", "Connect failed", this.f6671e.h(), "session", session, "host", SessionRequest.this.a(), "isHandleFinish", Boolean.valueOf(this.f6667a));
            }
            if (SessionRequest.this.f6660f) {
                SessionRequest.this.f6660f = false;
                return;
            }
            if (this.f6667a) {
                return;
            }
            this.f6667a = true;
            SessionRequest sessionRequest = SessionRequest.this;
            sessionRequest.f6656b.b(sessionRequest, session);
            if (!session.f6637u || !NetworkStatusHelper.isConnected() || this.f6670d.isEmpty()) {
                SessionRequest.this.c();
                SessionRequest.this.a(session, i2, i3);
                synchronized (SessionRequest.this.f6661g) {
                    try {
                        for (Map.Entry<SessionGetCallback, c> entry : SessionRequest.this.f6661g.entrySet()) {
                            c value = entry.getValue();
                            if (value.f6675b.compareAndSet(false, true)) {
                                ThreadPoolExecutorFactory.removeScheduleTask(value);
                                entry.getKey().onSessionGetFail();
                            }
                        }
                        SessionRequest.this.f6661g.clear();
                    } finally {
                    }
                }
                return;
            }
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.SessionRequest", "use next connInfo to create session", this.f6671e.h(), "host", SessionRequest.this.a());
            }
            anet.channel.entity.a aVar = this.f6671e;
            if (aVar.f6770b == aVar.f6771c && (i3 == -2003 || i3 == -2410)) {
                ListIterator<anet.channel.entity.a> listIterator = this.f6670d.listIterator();
                while (listIterator.hasNext()) {
                    if (session.getIp().equals(listIterator.next().f6769a.getIp())) {
                        listIterator.remove();
                    }
                }
            }
            if (anet.channel.strategy.utils.c.b(session.getIp())) {
                ListIterator<anet.channel.entity.a> listIterator2 = this.f6670d.listIterator();
                while (listIterator2.hasNext()) {
                    if (anet.channel.strategy.utils.c.b(listIterator2.next().f6769a.getIp())) {
                        listIterator2.remove();
                    }
                }
            }
            if (!this.f6670d.isEmpty()) {
                anet.channel.entity.a aVarRemove = this.f6670d.remove(0);
                SessionRequest sessionRequest2 = SessionRequest.this;
                Context context = this.f6669c;
                sessionRequest2.a(context, aVarRemove, sessionRequest2.new a(context, this.f6670d, aVarRemove), aVarRemove.h());
                return;
            }
            SessionRequest.this.c();
            SessionRequest.this.a(session, i2, i3);
            synchronized (SessionRequest.this.f6661g) {
                try {
                    for (Map.Entry<SessionGetCallback, c> entry2 : SessionRequest.this.f6661g.entrySet()) {
                        c value2 = entry2.getValue();
                        if (value2.f6675b.compareAndSet(false, true)) {
                            ThreadPoolExecutorFactory.removeScheduleTask(value2);
                            entry2.getKey().onSessionGetFail();
                        }
                    }
                    SessionRequest.this.f6661g.clear();
                } finally {
                }
            }
        }

        @Override // anet.channel.SessionRequest.IConnCb
        public void onSuccess(Session session, long j2) {
            ALog.d("awcn.SessionRequest", "Connect Success", this.f6671e.h(), "session", session, "host", SessionRequest.this.a());
            try {
                if (SessionRequest.this.f6660f) {
                    SessionRequest.this.f6660f = false;
                    session.close(false);
                    return;
                }
                SessionRequest sessionRequest = SessionRequest.this;
                sessionRequest.f6656b.a(sessionRequest, session);
                SessionRequest.this.a(session);
                synchronized (SessionRequest.this.f6661g) {
                    try {
                        for (Map.Entry<SessionGetCallback, c> entry : SessionRequest.this.f6661g.entrySet()) {
                            c value = entry.getValue();
                            if (value.f6675b.compareAndSet(false, true)) {
                                ThreadPoolExecutorFactory.removeScheduleTask(value);
                                entry.getKey().onSessionGetSuccess(session);
                            }
                        }
                        SessionRequest.this.f6661g.clear();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e2) {
                ALog.e("awcn.SessionRequest", "[onSuccess]:", this.f6671e.h(), e2, new Object[0]);
            } finally {
                SessionRequest.this.c();
            }
        }
    }

    private class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        String f6672a;

        b(String str) {
            this.f6672a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SessionRequest.this.f6658d) {
                ALog.e("awcn.SessionRequest", "Connecting timeout!!! reset status!", this.f6672a, new Object[0]);
                SessionConnStat sessionConnStat = SessionRequest.this.f6662h;
                sessionConnStat.ret = 2;
                sessionConnStat.totalTime = System.currentTimeMillis() - SessionRequest.this.f6662h.start;
                if (SessionRequest.this.f6659e != null) {
                    SessionRequest.this.f6659e.f6637u = false;
                    SessionRequest.this.f6659e.close();
                    SessionRequest sessionRequest = SessionRequest.this;
                    sessionRequest.f6662h.syncValueFromSession(sessionRequest.f6659e);
                }
                AppMonitor.getInstance().commitStat(SessionRequest.this.f6662h);
                SessionRequest.this.a(false);
            }
        }
    }

    protected class c implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        SessionGetCallback f6674a;

        /* renamed from: b, reason: collision with root package name */
        AtomicBoolean f6675b = new AtomicBoolean(false);

        protected c(SessionGetCallback sessionGetCallback) {
            this.f6674a = null;
            this.f6674a = sessionGetCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.f6675b.compareAndSet(false, true)) {
                ALog.e("awcn.SessionRequest", "get session timeout", null, new Object[0]);
                synchronized (SessionRequest.this.f6661g) {
                    SessionRequest.this.f6661g.remove(this.f6674a);
                }
                this.f6674a.onSessionGetFail();
            }
        }
    }

    SessionRequest(String str, SessionCenter sessionCenter) {
        this.f6663i = str;
        String strSubstring = str.substring(str.indexOf(HttpConstant.SCHEME_SPLIT) + 3);
        this.f6664j = strSubstring;
        this.f6655a = sessionCenter;
        this.f6657c = sessionCenter.f6650g.b(strSubstring);
        this.f6656b = sessionCenter.f6648e;
    }

    protected synchronized void b(Context context, int i2, String str, SessionGetCallback sessionGetCallback, long j2) {
        Session sessionA = this.f6656b.a(this, i2);
        if (sessionA != null) {
            ALog.d("awcn.SessionRequest", "Available Session exist!!!", str, new Object[0]);
            sessionGetCallback.onSessionGetSuccess(sessionA);
            return;
        }
        String strA = TextUtils.isEmpty(str) ? anet.channel.util.i.a(null) : str;
        ALog.d("awcn.SessionRequest", "SessionRequest start", strA, "host", this.f6663i, "type", Integer.valueOf(i2));
        if (this.f6658d) {
            ALog.d("awcn.SessionRequest", "session connecting", strA, "host", a());
            if (b() == i2) {
                c cVar = new c(sessionGetCallback);
                synchronized (this.f6661g) {
                    this.f6661g.put(sessionGetCallback, cVar);
                }
                ThreadPoolExecutorFactory.submitScheduledTask(cVar, j2, TimeUnit.MILLISECONDS);
            } else {
                sessionGetCallback.onSessionGetFail();
            }
            return;
        }
        a(true);
        this.f6665k = ThreadPoolExecutorFactory.submitScheduledTask(new b(strA), 45L, TimeUnit.SECONDS);
        SessionConnStat sessionConnStat = new SessionConnStat();
        this.f6662h = sessionConnStat;
        sessionConnStat.start = System.currentTimeMillis();
        if (!NetworkStatusHelper.isConnected()) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.SessionRequest", "network is not available, can't create session", strA, "isConnected", Boolean.valueOf(NetworkStatusHelper.isConnected()));
            }
            c();
            throw new RuntimeException("no network");
        }
        List<IConnStrategy> listA = a(i2, strA);
        if (listA.isEmpty()) {
            ALog.i("awcn.SessionRequest", "no avalible strategy, can't create session", strA, "host", this.f6663i, "type", Integer.valueOf(i2));
            c();
            throw new NoAvailStrategyException("no avalible strategy");
        }
        List<anet.channel.entity.a> listA2 = a(listA, strA);
        try {
            anet.channel.entity.a aVarRemove = listA2.remove(0);
            a(context, aVarRemove, new a(context, listA2, aVarRemove), aVarRemove.h());
            c cVar2 = new c(sessionGetCallback);
            synchronized (this.f6661g) {
                this.f6661g.put(sessionGetCallback, cVar2);
            }
            ThreadPoolExecutorFactory.submitScheduledTask(cVar2, j2, TimeUnit.MILLISECONDS);
        } catch (Throwable unused) {
            c();
        }
        return;
    }

    void c() {
        a(false);
        synchronized (this.f6666l) {
            this.f6666l.notifyAll();
        }
    }

    protected String a() {
        return this.f6663i;
    }

    void a(boolean z2) {
        this.f6658d = z2;
        if (z2) {
            return;
        }
        if (this.f6665k != null) {
            this.f6665k.cancel(true);
            this.f6665k = null;
        }
        this.f6659e = null;
    }

    private void c(Session session, int i2, String str) {
        SessionInfo sessionInfo = this.f6657c;
        if (sessionInfo == null || !sessionInfo.isAccs) {
            return;
        }
        ALog.e("awcn.SessionRequest", "sendConnectInfoToAccsByCallBack", null, new Object[0]);
        Intent intent = new Intent(Constants.ACTION_ACCS_CONNECT_INFO);
        intent.putExtra("command", 103);
        intent.putExtra("host", session.getHost());
        intent.putExtra(Constants.KEY_CENTER_HOST, true);
        boolean zIsAvailable = session.isAvailable();
        if (!zIsAvailable) {
            intent.putExtra("errorCode", i2);
            intent.putExtra(Constants.KEY_ERROR_DETAIL, str);
        }
        intent.putExtra(Constants.KEY_CONNECT_AVAILABLE, zIsAvailable);
        intent.putExtra(Constants.KEY_TYPE_INAPP, true);
        this.f6655a.f6651h.notifyListener(intent);
    }

    protected synchronized void a(Context context, int i2, String str, SessionGetCallback sessionGetCallback, long j2) {
        Session sessionA = this.f6656b.a(this, i2);
        if (sessionA != null) {
            ALog.d("awcn.SessionRequest", "Available Session exist!!!", str, new Object[0]);
            if (sessionGetCallback != null) {
                sessionGetCallback.onSessionGetSuccess(sessionA);
            }
            return;
        }
        String strA = TextUtils.isEmpty(str) ? anet.channel.util.i.a(null) : str;
        ALog.d("awcn.SessionRequest", "SessionRequest start", strA, "host", this.f6663i, "type", Integer.valueOf(i2));
        if (this.f6658d) {
            ALog.d("awcn.SessionRequest", "session connecting", strA, "host", a());
            if (sessionGetCallback != null) {
                if (b() == i2) {
                    c cVar = new c(sessionGetCallback);
                    synchronized (this.f6661g) {
                        this.f6661g.put(sessionGetCallback, cVar);
                    }
                    ThreadPoolExecutorFactory.submitScheduledTask(cVar, j2, TimeUnit.MILLISECONDS);
                } else {
                    sessionGetCallback.onSessionGetFail();
                }
            }
            return;
        }
        a(true);
        this.f6665k = ThreadPoolExecutorFactory.submitScheduledTask(new b(strA), 45L, TimeUnit.SECONDS);
        SessionConnStat sessionConnStat = new SessionConnStat();
        this.f6662h = sessionConnStat;
        sessionConnStat.start = System.currentTimeMillis();
        if (!NetworkStatusHelper.isConnected()) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.SessionRequest", "network is not available, can't create session", strA, "isConnected", Boolean.valueOf(NetworkStatusHelper.isConnected()));
            }
            c();
            throw new RuntimeException("no network");
        }
        List<IConnStrategy> listA = a(i2, strA);
        if (!listA.isEmpty()) {
            List<anet.channel.entity.a> listA2 = a(listA, strA);
            try {
                anet.channel.entity.a aVarRemove = listA2.remove(0);
                a(context, aVarRemove, new a(context, listA2, aVarRemove), aVarRemove.h());
                if (sessionGetCallback != null) {
                    c cVar2 = new c(sessionGetCallback);
                    synchronized (this.f6661g) {
                        this.f6661g.put(sessionGetCallback, cVar2);
                    }
                    ThreadPoolExecutorFactory.submitScheduledTask(cVar2, j2, TimeUnit.MILLISECONDS);
                }
            } catch (Throwable unused) {
                c();
            }
            return;
        }
        ALog.i("awcn.SessionRequest", "no avalible strategy, can't create session", strA, "host", this.f6663i, "type", Integer.valueOf(i2));
        c();
        throw new NoAvailStrategyException("no avalible strategy");
    }

    protected void b(boolean z2) {
        ALog.d("awcn.SessionRequest", "closeSessions", this.f6655a.f6646c, "host", this.f6663i, "autoCreate", Boolean.valueOf(z2));
        if (!z2 && this.f6659e != null) {
            this.f6659e.f6637u = false;
            this.f6659e.close(false);
        }
        List<Session> listA = this.f6656b.a(this);
        if (listA != null) {
            for (Session session : listA) {
                if (session != null) {
                    session.close(z2);
                }
            }
        }
    }

    void a(Session session) {
        AlarmObject alarmObject = new AlarmObject();
        alarmObject.module = "networkPrefer";
        alarmObject.modulePoint = bc.by;
        alarmObject.arg = this.f6663i;
        alarmObject.isSuccess = true;
        AppMonitor.getInstance().commitAlarm(alarmObject);
        this.f6662h.syncValueFromSession(session);
        SessionConnStat sessionConnStat = this.f6662h;
        sessionConnStat.ret = 1;
        sessionConnStat.totalTime = System.currentTimeMillis() - this.f6662h.start;
        AppMonitor.getInstance().commitStat(this.f6662h);
    }

    protected int b() {
        Session session = this.f6659e;
        if (session != null) {
            return session.f6626j.getType();
        }
        return -1;
    }

    private void b(Session session, int i2, String str) {
        SessionInfo sessionInfo;
        Context context = GlobalAppRuntimeInfo.getContext();
        if (context == null || (sessionInfo = this.f6657c) == null || !sessionInfo.isAccs) {
            return;
        }
        ALog.e("awcn.SessionRequest", "sendConnectInfoToAccsByService", null, new Object[0]);
        try {
            Intent intent = new Intent(Constants.ACTION_RECEIVE);
            intent.setPackage(context.getPackageName());
            intent.setClassName(context, com.taobao.accs.utl.j.msgService);
            intent.putExtra("command", 103);
            intent.putExtra("host", session.getHost());
            intent.putExtra(Constants.KEY_CENTER_HOST, true);
            boolean zIsAvailable = session.isAvailable();
            if (!zIsAvailable) {
                intent.putExtra("errorCode", i2);
                intent.putExtra(Constants.KEY_ERROR_DETAIL, str);
            }
            intent.putExtra(Constants.KEY_CONNECT_AVAILABLE, zIsAvailable);
            intent.putExtra(Constants.KEY_TYPE_INAPP, true);
            if (Build.VERSION.SDK_INT >= 26) {
                context.bindService(intent, new h(this, intent, context), 1);
            } else {
                context.startService(intent);
            }
        } catch (Throwable th) {
            ALog.e("awcn.SessionRequest", "sendConnectInfoToAccsByService", null, th, new Object[0]);
        }
    }

    void a(Session session, int i2, int i3) {
        if (256 != i2 || i3 == -2613 || i3 == -2601) {
            return;
        }
        AlarmObject alarmObject = new AlarmObject();
        alarmObject.module = "networkPrefer";
        alarmObject.modulePoint = bc.by;
        alarmObject.arg = this.f6663i;
        alarmObject.errorCode = String.valueOf(i3);
        alarmObject.isSuccess = false;
        AppMonitor.getInstance().commitAlarm(alarmObject);
        SessionConnStat sessionConnStat = this.f6662h;
        sessionConnStat.ret = 0;
        sessionConnStat.appendErrorTrace(i3);
        this.f6662h.errorCode = String.valueOf(i3);
        this.f6662h.totalTime = System.currentTimeMillis() - this.f6662h.start;
        this.f6662h.syncValueFromSession(session);
        AppMonitor.getInstance().commitStat(this.f6662h);
    }

    private List<IConnStrategy> a(int i2, String str) {
        HttpUrl httpUrl;
        List<IConnStrategy> connStrategyListByHost = Collections.EMPTY_LIST;
        try {
            httpUrl = HttpUrl.parse(a());
        } catch (Throwable th) {
            ALog.e("awcn.SessionRequest", "", str, th, new Object[0]);
        }
        if (httpUrl == null) {
            return connStrategyListByHost;
        }
        connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(httpUrl.host());
        if (!connStrategyListByHost.isEmpty()) {
            boolean zEqualsIgnoreCase = "https".equalsIgnoreCase(httpUrl.scheme());
            boolean zB = anet.channel.util.c.b();
            ListIterator<IConnStrategy> listIterator = connStrategyListByHost.listIterator();
            while (listIterator.hasNext()) {
                IConnStrategy next = listIterator.next();
                ConnType connTypeValueOf = ConnType.valueOf(next.getProtocol());
                if (connTypeValueOf != null) {
                    if (connTypeValueOf.isSSL() == zEqualsIgnoreCase && (i2 == anet.channel.entity.c.f6779c || connTypeValueOf.getType() == i2)) {
                        if (zB && anet.channel.strategy.utils.c.b(next.getIp())) {
                            listIterator.remove();
                        }
                    }
                    listIterator.remove();
                }
            }
        }
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.SessionRequest", "[getAvailStrategy]", str, "strategies", connStrategyListByHost);
        }
        return connStrategyListByHost;
    }

    private List<anet.channel.entity.a> a(List<IConnStrategy> list, String str) {
        if (list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            IConnStrategy iConnStrategy = list.get(i3);
            int retryTimes = iConnStrategy.getRetryTimes();
            for (int i4 = 0; i4 <= retryTimes; i4++) {
                i2++;
                anet.channel.entity.a aVar = new anet.channel.entity.a(a(), str + OpenAccountUIConstants.UNDER_LINE + i2, iConnStrategy);
                aVar.f6770b = i4;
                aVar.f6771c = retryTimes;
                arrayList.add(aVar);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, anet.channel.entity.a aVar, IConnCb iConnCb, String str) {
        ConnType connTypeC = aVar.c();
        if (context != null && !connTypeC.isHttpType()) {
            TnetSpdySession tnetSpdySession = new TnetSpdySession(context, aVar);
            tnetSpdySession.initConfig(this.f6655a.f6647d);
            tnetSpdySession.initSessionInfo(this.f6657c);
            tnetSpdySession.setTnetPublicKey(this.f6655a.f6650g.c(this.f6664j));
            this.f6659e = tnetSpdySession;
        } else {
            this.f6659e = new anet.channel.session.d(context, aVar);
        }
        ALog.i("awcn.SessionRequest", "create connection...", str, "Host", a(), "Type", aVar.c(), "IP", aVar.a(), "Port", Integer.valueOf(aVar.b()), "heartbeat", Integer.valueOf(aVar.g()), "session", this.f6659e);
        a(this.f6659e, iConnCb, System.currentTimeMillis());
        this.f6659e.connect();
        SessionConnStat sessionConnStat = this.f6662h;
        sessionConnStat.retryTimes++;
        sessionConnStat.startConnect = System.currentTimeMillis();
        SessionConnStat sessionConnStat2 = this.f6662h;
        if (sessionConnStat2.retryTimes == 0) {
            sessionConnStat2.putExtra("firstIp", aVar.a());
        }
    }

    private void a(Session session, IConnCb iConnCb, long j2) {
        if (iConnCb == null) {
            return;
        }
        session.registerEventcb(EventType.ALL, new f(this, iConnCb, j2));
        session.registerEventcb(1792, new g(this, session));
    }

    protected void a(String str) {
        ALog.d("awcn.SessionRequest", "reCreateSession", str, "host", this.f6663i);
        b(true);
    }

    protected void a(long j2) throws InterruptedException, TimeoutException {
        ALog.d("awcn.SessionRequest", "[await]", null, "timeoutMs", Long.valueOf(j2));
        if (j2 <= 0) {
            return;
        }
        synchronized (this.f6666l) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis() + j2;
                while (this.f6658d) {
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (jCurrentTimeMillis2 >= jCurrentTimeMillis) {
                        break;
                    } else {
                        this.f6666l.wait(jCurrentTimeMillis - jCurrentTimeMillis2);
                    }
                }
                if (this.f6658d) {
                    throw new TimeoutException();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    void a(Session session, int i2, String str) {
        if (AwcnConfig.isSendConnectInfoByService()) {
            b(session, i2, str);
        }
        c(session, i2, str);
    }
}

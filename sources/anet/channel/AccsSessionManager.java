package anet.channel;

import android.content.Intent;
import android.text.TextUtils;
import anet.channel.entity.ConnType;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.StrategyCenter;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.StringUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes2.dex */
class AccsSessionManager {

    /* renamed from: c, reason: collision with root package name */
    private static CopyOnWriteArraySet<ISessionListener> f6566c = new CopyOnWriteArraySet<>();

    /* renamed from: a, reason: collision with root package name */
    SessionCenter f6567a;

    /* renamed from: b, reason: collision with root package name */
    Set<String> f6568b = Collections.EMPTY_SET;

    AccsSessionManager(SessionCenter sessionCenter) {
        this.f6567a = null;
        this.f6567a = sessionCenter;
    }

    private boolean b() {
        return !(GlobalAppRuntimeInfo.isAppBackground() && AwcnConfig.isAccsSessionCreateForbiddenInBg()) && NetworkStatusHelper.isConnected();
    }

    public synchronized void checkAndStartSession() {
        try {
            Collection<SessionInfo> collectionA = this.f6567a.f6650g.a();
            Set<String> treeSet = Collections.EMPTY_SET;
            if (!collectionA.isEmpty()) {
                treeSet = new TreeSet<>();
            }
            for (SessionInfo sessionInfo : collectionA) {
                if (sessionInfo.isKeepAlive) {
                    treeSet.add(StringUtils.concatString(StrategyCenter.getInstance().getSchemeByHost(sessionInfo.host, sessionInfo.isAccs ? "https" : "http"), HttpConstant.SCHEME_SPLIT, sessionInfo.host));
                }
            }
            for (String str : this.f6568b) {
                if (!treeSet.contains(str)) {
                    a(str);
                }
            }
            if (b()) {
                for (String str2 : treeSet) {
                    try {
                        this.f6567a.get(str2, ConnType.TypeLevel.SPDY, 0L);
                    } catch (Exception unused) {
                        ALog.e("start session failed", null, "host", str2);
                    }
                }
                this.f6568b = treeSet;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void forceCloseSession(boolean z2) {
        try {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.AccsSessionManager", "forceCloseSession", this.f6567a.f6646c, "reCreate", Boolean.valueOf(z2));
            }
            Iterator<String> it = this.f6568b.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
            if (z2) {
                checkAndStartSession();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void notifyListener(Intent intent) {
        ThreadPoolExecutorFactory.submitScheduledTask(new a(this, intent));
    }

    public void registerListener(ISessionListener iSessionListener) {
        if (iSessionListener != null) {
            f6566c.add(iSessionListener);
        }
    }

    public void unregisterListener(ISessionListener iSessionListener) {
        f6566c.remove(iSessionListener);
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ALog.d("awcn.AccsSessionManager", "closeSessions", this.f6567a.f6646c, "host", str);
        this.f6567a.a(str).b(false);
    }
}

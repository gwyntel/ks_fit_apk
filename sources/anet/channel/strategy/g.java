package anet.channel.strategy;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.strategy.c;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.strategy.dispatch.DispatchEvent;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anet.channel.strategy.l;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import anet.channel.util.StringUtils;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONObject;

/* loaded from: classes2.dex */
class g implements IStrategyInstance, HttpDispatcher.IDispatchEventListener {

    /* renamed from: a, reason: collision with root package name */
    boolean f7009a = false;

    /* renamed from: b, reason: collision with root package name */
    StrategyInfoHolder f7010b = null;

    /* renamed from: c, reason: collision with root package name */
    long f7011c = 0;

    /* renamed from: d, reason: collision with root package name */
    CopyOnWriteArraySet<IStrategyListener> f7012d = new CopyOnWriteArraySet<>();

    /* renamed from: e, reason: collision with root package name */
    private IStrategyFilter f7013e = new h(this);

    g() {
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public void forceRefreshStrategy(String str) {
        if (a() || TextUtils.isEmpty(str)) {
            return;
        }
        ALog.i("awcn.StrategyCenter", "force refresh strategy", null, "host", str);
        this.f7010b.d().a(str, true);
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public String getCNameByHost(String str) {
        if (a() || TextUtils.isEmpty(str)) {
            return null;
        }
        return this.f7010b.d().getCnameByHost(str);
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public String getClientIp() {
        return a() ? "" : this.f7010b.d().f6975b;
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public List<IConnStrategy> getConnStrategyListByHost(String str) {
        return getConnStrategyListByHost(str, this.f7013e);
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public String getFormalizeUrl(String str) {
        HttpUrl httpUrl = HttpUrl.parse(str);
        if (httpUrl == null) {
            ALog.e("awcn.StrategyCenter", "url is invalid.", null, "URL", str);
            return null;
        }
        String strUrlString = httpUrl.urlString();
        try {
            String schemeByHost = getSchemeByHost(httpUrl.host(), httpUrl.scheme());
            if (!schemeByHost.equalsIgnoreCase(httpUrl.scheme())) {
                strUrlString = StringUtils.concatString(schemeByHost, ":", str.substring(str.indexOf("//")));
            }
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.StrategyCenter", "", null, "raw", StringUtils.simplifyString(str, 128), "ret", StringUtils.simplifyString(strUrlString, 128));
            }
        } catch (Exception e2) {
            ALog.e("awcn.StrategyCenter", "getFormalizeUrl failed", null, e2, "raw", str);
        }
        return strUrlString;
    }

    @Override // anet.channel.strategy.IStrategyInstance
    @Deprecated
    public String getSchemeByHost(String str) {
        return getSchemeByHost(str, null);
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public String getUnitByHost(String str) {
        if (a()) {
            return null;
        }
        return this.f7010b.f6964b.b(str);
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public synchronized void initialize(Context context) {
        if (this.f7009a || context == null) {
            return;
        }
        try {
            ALog.i("awcn.StrategyCenter", "StrategyCenter initialize started.", null, new Object[0]);
            AmdcRuntimeInfo.setContext(context);
            m.a(context);
            HttpDispatcher.getInstance().addListener(this);
            this.f7010b = StrategyInfoHolder.a();
            this.f7009a = true;
            ALog.i("awcn.StrategyCenter", "StrategyCenter initialize finished.", null, new Object[0]);
        } catch (Exception e2) {
            ALog.e("awcn.StrategyCenter", "StrategyCenter initialize failed.", null, e2, new Object[0]);
        }
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public void notifyConnEvent(String str, IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if (a() || iConnStrategy == null || !(iConnStrategy instanceof IPConnStrategy)) {
            return;
        }
        IPConnStrategy iPConnStrategy = (IPConnStrategy) iConnStrategy;
        if (iPConnStrategy.f6950b == 1) {
            this.f7010b.f6965c.a(str, iConnStrategy, connEvent);
        } else if (iPConnStrategy.f6950b == 0) {
            this.f7010b.d().a(str, iConnStrategy, connEvent);
        }
    }

    @Override // anet.channel.strategy.dispatch.HttpDispatcher.IDispatchEventListener
    public void onEvent(DispatchEvent dispatchEvent) {
        if (dispatchEvent.eventType != 1 || this.f7010b == null) {
            return;
        }
        ALog.d("awcn.StrategyCenter", "receive amdc event", null, new Object[0]);
        l.d dVarA = l.a((JSONObject) dispatchEvent.extraObject);
        if (dVarA == null) {
            return;
        }
        this.f7010b.a(dVarA);
        saveData();
        Iterator<IStrategyListener> it = this.f7012d.iterator();
        while (it.hasNext()) {
            try {
                it.next().onStrategyUpdated(dVarA);
            } catch (Exception e2) {
                ALog.e("awcn.StrategyCenter", "onStrategyUpdated failed", null, e2, new Object[0]);
            }
        }
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public void registerListener(IStrategyListener iStrategyListener) {
        ALog.e("awcn.StrategyCenter", "registerListener", null, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, this.f7012d);
        if (iStrategyListener != null) {
            this.f7012d.add(iStrategyListener);
        }
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public synchronized void saveData() {
        ALog.i("awcn.StrategyCenter", "saveData", null, new Object[0]);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.f7011c > 30000) {
            this.f7011c = jCurrentTimeMillis;
            anet.channel.strategy.utils.a.a(new i(this), 500L);
        }
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public synchronized void switchEnv() {
        m.a();
        HttpDispatcher.getInstance().switchENV();
        StrategyInfoHolder strategyInfoHolder = this.f7010b;
        if (strategyInfoHolder != null) {
            strategyInfoHolder.b();
            this.f7010b = StrategyInfoHolder.a();
        }
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public void unregisterListener(IStrategyListener iStrategyListener) {
        ALog.e("awcn.StrategyCenter", "unregisterListener", null, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, this.f7012d);
        this.f7012d.remove(iStrategyListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        if (this.f7010b != null) {
            return false;
        }
        ALog.w("StrategyCenter not initialized", null, "isInitialized", Boolean.valueOf(this.f7009a));
        return true;
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public List<IConnStrategy> getConnStrategyListByHost(String str, IStrategyFilter iStrategyFilter) {
        if (TextUtils.isEmpty(str) || a()) {
            return Collections.EMPTY_LIST;
        }
        String cnameByHost = this.f7010b.d().getCnameByHost(str);
        if (!TextUtils.isEmpty(cnameByHost)) {
            str = cnameByHost;
        }
        List listQueryByHost = this.f7010b.d().queryByHost(str);
        if (listQueryByHost.isEmpty()) {
            listQueryByHost = this.f7010b.f6965c.a(str);
        }
        if (listQueryByHost.isEmpty() || iStrategyFilter == null) {
            ALog.d("getConnStrategyListByHost", null, "host", str, "result", listQueryByHost);
            return listQueryByHost;
        }
        boolean z2 = !AwcnConfig.isIpv6Enable() || (AwcnConfig.isIpv6BlackListEnable() && this.f7010b.d().a(str, AwcnConfig.getIpv6BlackListTtl()));
        ListIterator<IConnStrategy> listIterator = listQueryByHost.listIterator();
        while (listIterator.hasNext()) {
            IConnStrategy next = listIterator.next();
            if (!iStrategyFilter.accept(next)) {
                listIterator.remove();
            } else if (z2 && anet.channel.strategy.utils.c.b(next.getIp())) {
                listIterator.remove();
            }
        }
        if (ALog.isPrintLog(1)) {
            ALog.d("getConnStrategyListByHost", null, "host", str, "result", listQueryByHost);
        }
        return listQueryByHost;
    }

    @Override // anet.channel.strategy.IStrategyInstance
    public String getSchemeByHost(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (a()) {
            return str2;
        }
        String strA = this.f7010b.f6964b.a(str);
        if (strA != null || TextUtils.isEmpty(str2)) {
            str2 = strA;
        }
        if (str2 == null && (str2 = c.a.f6988a.a(str)) == null) {
            str2 = "http";
        }
        ALog.d("awcn.StrategyCenter", "getSchemeByHost", null, "host", str, "scheme", str2);
        return str2;
    }
}

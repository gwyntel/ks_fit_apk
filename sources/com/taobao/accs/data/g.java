package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.media3.datasource.cache.CacheDataSink;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.IAppReceiverV1;
import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.vivo.push.PushClientConstants;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.android.agoo.common.Config;

/* loaded from: classes4.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static Set<String> f20156a;

    /* renamed from: b, reason: collision with root package name */
    private static volatile g f20157b;

    public static g a() {
        if (f20157b == null) {
            synchronized (g.class) {
                try {
                    if (f20157b == null) {
                        f20157b = new g();
                    }
                } finally {
                }
            }
        }
        return f20157b;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void b(android.content.Context r47, android.content.Intent r48) {
        /*
            Method dump skipped, instructions count: 916
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.g.b(android.content.Context, android.content.Intent):void");
    }

    public static void a(Context context, Intent intent) {
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_DATA_ID);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_SERVICE_ID);
            if (ALog.isPrintLog(ALog.Level.D) || "accs-impaas".equals(stringExtra2)) {
                ALog.e("MsgDistribute", "distribMessage", Constants.KEY_DATA_ID, stringExtra);
            }
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(new h(context, intent));
        } catch (Throwable th) {
            ALog.e("MsgDistribute", "distribMessage", th, new Object[0]);
            UTMini.getInstance().commitEvent(66001, "MsgToBuss8", "distribMessage" + th.toString(), 221);
        }
    }

    protected boolean a(int i2, String str) {
        if (i2 != 100 && !GlobalClientInfo.AGOO_SERVICE_ID.equals(str)) {
            long jE = UtilityImpl.e();
            if (jE != -1 && jE <= CacheDataSink.DEFAULT_FRAGMENT_SIZE) {
                com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", "space low");
                ALog.e("MsgDistribute", "user space low, don't distribute", "size", Long.valueOf(jE), Constants.KEY_SERVICE_ID, str);
                return true;
            }
        }
        return false;
    }

    protected boolean a(Context context, String str, String str2, Intent intent, IAppReceiver iAppReceiver) {
        boolean z2 = true;
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            String service = GlobalClientInfo.getInstance(context).getService(intent.getStringExtra(Constants.KEY_CONFIG_TAG), str);
            if (TextUtils.isEmpty(service) && iAppReceiver != null) {
                service = iAppReceiver.getService(str);
            }
            if (TextUtils.isEmpty(service)) {
                service = GlobalClientInfo.getInstance(context).getService(str);
            }
            if (!TextUtils.isEmpty(service) || UtilityImpl.isMainProcess(context)) {
                z2 = false;
            } else {
                if ("accs".equals(str)) {
                    ALog.e("MsgDistribute", "start MsgDistributeService", Constants.KEY_DATA_ID, str2);
                } else {
                    ALog.i("MsgDistribute", "start MsgDistributeService", Constants.KEY_DATA_ID, str2);
                }
                intent.setClassName(intent.getPackage(), b());
                com.taobao.accs.a.a.a(context, intent);
            }
            return z2;
        } catch (Throwable th) {
            ALog.e("MsgDistribute", "handleMsgInChannelProcess", th, new Object[0]);
            return false;
        }
    }

    private void a(Context context, Intent intent, String str, String str2, int i2, String str3, String str4, String str5, IAppReceiver iAppReceiver, int i3) {
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.d("MsgDistribute", "handleControlMsg", Constants.KEY_CONFIG_TAG, str, Constants.KEY_DATA_ID, str5, Constants.KEY_SERVICE_ID, str4, "command", Integer.valueOf(i2), "errorCode", Integer.valueOf(i3), "appReceiver", iAppReceiver == null ? null : iAppReceiver.getClass().getName());
        }
        if (iAppReceiver != null) {
            if (i2 != 1) {
                if (i2 == 2) {
                    if (i3 == 200) {
                        UtilityImpl.disableService(context);
                    }
                    iAppReceiver.onUnbindApp(i3);
                } else if (i2 == 3) {
                    iAppReceiver.onBindUser(str3, i3);
                } else if (i2 == 4) {
                    iAppReceiver.onUnbindUser(i3);
                } else if (i2 != 100) {
                    if (i2 == 101 && TextUtils.isEmpty(str4)) {
                        ALog.d("MsgDistribute", "handleControlMsg serviceId isEmpty", new Object[0]);
                        byte[] byteArrayExtra = intent.getByteArrayExtra("data");
                        if (byteArrayExtra != null) {
                            iAppReceiver.onData(str3, str5, byteArrayExtra);
                        }
                    }
                } else if (TextUtils.isEmpty(str4)) {
                    iAppReceiver.onSendData(str5, i3);
                }
            } else if (iAppReceiver instanceof IAppReceiverV1) {
                ((IAppReceiverV1) iAppReceiver).onBindApp(i3, null);
            } else {
                iAppReceiver.onBindApp(i3);
            }
        }
        if (i2 == 1 && GlobalClientInfo.f20066b != null && str2 != null && str2.equals(Config.a(context))) {
            ALog.d("MsgDistribute", "handleControlMsg agoo receiver onBindApp", new Object[0]);
            GlobalClientInfo.f20066b.onBindApp(i3, null);
            return;
        }
        if (iAppReceiver != null || i2 == 100 || i2 == 104 || i2 == 103) {
            return;
        }
        com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str4, "1", "appReceiver null return");
        UTMini.getInstance().commitEvent(66001, "MsgToBuss7", "commandId=" + i2, "serviceId=" + str4 + " errorCode=" + i3 + " dataId=" + str5, 221);
    }

    protected String b() {
        return com.taobao.accs.utl.j.msgService;
    }

    protected void a(Context context, IAppReceiver iAppReceiver, Intent intent, String str, String str2, int i2, int i3) {
        ALog.Level level = ALog.Level.D;
        if (ALog.isPrintLog(level) || "accs-impaas".equals(str)) {
            ALog.e("MsgDistribute", "handleBusinessMsg start", Constants.KEY_DATA_ID, str2, Constants.KEY_SERVICE_ID, str, "command", Integer.valueOf(i2));
        }
        String service = GlobalClientInfo.getInstance(context).getService(intent.getStringExtra(Constants.KEY_CONFIG_TAG), str);
        if (TextUtils.isEmpty(service) && iAppReceiver != null) {
            service = iAppReceiver.getService(str);
        }
        if (TextUtils.isEmpty(service)) {
            service = GlobalClientInfo.getInstance(context).getService(str);
        }
        if (!TextUtils.isEmpty(service)) {
            if (ALog.isPrintLog(level) || "accs-impaas".equals(str)) {
                ALog.e("MsgDistribute", "handleBusinessMsg to start service", PushClientConstants.TAG_CLASS_NAME, service);
            }
            NetPerformanceMonitor netPerformanceMonitorA = com.taobao.accs.utl.a.a(intent);
            if (netPerformanceMonitorA != null) {
                netPerformanceMonitorA.start_service = System.currentTimeMillis();
            }
            intent.setClassName(context, service);
            com.taobao.accs.a.a.a(context, intent);
        } else {
            AccsDataListener listener = GlobalClientInfo.getInstance(context).getListener(str);
            if (listener != null) {
                if (ALog.isPrintLog(level) || "accs-impaas".equals(str)) {
                    ALog.e("MsgDistribute", "handleBusinessMsg getListener not null", new Object[0]);
                }
                com.taobao.accs.utl.a.a(context, intent, listener);
            } else {
                ALog.e("MsgDistribute", "handleBusinessMsg getListener also null", new Object[0]);
                com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", "service is null");
            }
        }
        UTMini.getInstance().commitEvent(66001, "MsgToBuss", "commandId=" + i2, "serviceId=" + str + " errorCode=" + i3 + " dataId=" + str2, 221);
        StringBuilder sb = new StringBuilder();
        sb.append("2commandId=");
        sb.append(i2);
        sb.append("serviceId=");
        sb.append(str);
        com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_TO_BUSS, sb.toString(), 0.0d);
    }

    protected void a(Context context, Map<String, IAppReceiver> map, Intent intent, int i2, int i3) {
        TaoBaseService.ConnectInfo connectInfo;
        ALog.e("MsgDistribute", "handBroadCastMsg", "command", Integer.valueOf(i2));
        HashMap map2 = new HashMap();
        if (map != null) {
            for (Map.Entry<String, IAppReceiver> entry : map.entrySet()) {
                Map<String, String> allService = GlobalClientInfo.getInstance(context).getAllService(entry.getKey());
                if (allService == null) {
                    allService = entry.getValue().getAllServices();
                }
                if (allService != null) {
                    map2.putAll(allService);
                }
            }
        }
        if (i2 != 103) {
            if (i2 == 104) {
                for (String str : map2.keySet()) {
                    String service = (String) map2.get(str);
                    if (TextUtils.isEmpty(service)) {
                        service = GlobalClientInfo.getInstance(context).getService(str);
                    }
                    if (!TextUtils.isEmpty(service)) {
                        intent.setClassName(context, service);
                        com.taobao.accs.a.a.a(context, intent);
                    }
                }
                return;
            }
            ALog.w("MsgDistribute", "handBroadCastMsg not handled command", new Object[0]);
            return;
        }
        for (String str2 : map2.keySet()) {
            if ("accs".equals(str2) || "windvane".equals(str2) || "motu-remote".equals(str2)) {
                String service2 = (String) map2.get(str2);
                if (TextUtils.isEmpty(service2)) {
                    service2 = GlobalClientInfo.getInstance(context).getService(str2);
                }
                if (!TextUtils.isEmpty(service2)) {
                    intent.setClassName(context, service2);
                    com.taobao.accs.a.a.a(context, intent);
                }
            }
        }
        boolean booleanExtra = intent.getBooleanExtra(Constants.KEY_CONNECT_AVAILABLE, false);
        String stringExtra = intent.getStringExtra("host");
        String stringExtra2 = intent.getStringExtra(Constants.KEY_ERROR_DETAIL);
        boolean booleanExtra2 = intent.getBooleanExtra(Constants.KEY_TYPE_INAPP, false);
        boolean booleanExtra3 = intent.getBooleanExtra(Constants.KEY_CENTER_HOST, false);
        if (TextUtils.isEmpty(stringExtra)) {
            connectInfo = null;
        } else {
            if (booleanExtra) {
                connectInfo = new TaoBaseService.ConnectInfo(stringExtra, booleanExtra2, booleanExtra3);
            } else {
                connectInfo = new TaoBaseService.ConnectInfo(stringExtra, booleanExtra2, booleanExtra3, i3, stringExtra2);
            }
            connectInfo.connected = booleanExtra;
        }
        if (connectInfo != null) {
            ALog.d("MsgDistribute", "handBroadCastMsg ACTION_CONNECT_INFO", connectInfo);
            Intent intent2 = new Intent(Constants.ACTION_CONNECT_INFO);
            intent2.setPackage(context.getPackageName());
            intent2.putExtra(Constants.KEY_CONNECT_INFO, connectInfo);
            context.sendBroadcast(intent2);
            return;
        }
        ALog.e("MsgDistribute", "handBroadCastMsg connect info null, host empty", new Object[0]);
    }

    private boolean a(Context context, Intent intent, String str, String str2) {
        boolean z2;
        boolean booleanExtra = intent.getBooleanExtra("routingAck", false);
        boolean booleanExtra2 = intent.getBooleanExtra("routingMsg", false);
        if (booleanExtra) {
            ALog.e("MsgDistribute", "recieve routiong ack", Constants.KEY_DATA_ID, str, Constants.KEY_SERVICE_ID, str2);
            Set<String> set = f20156a;
            if (set != null) {
                set.remove(str);
            }
            com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_MSG_ROUTING_RATE, "");
            z2 = true;
        } else {
            z2 = false;
        }
        if (booleanExtra2) {
            try {
                String stringExtra = intent.getStringExtra("packageName");
                ALog.e("MsgDistribute", "send routiong ack", Constants.KEY_DATA_ID, str, "to pkg", stringExtra, Constants.KEY_SERVICE_ID, str2);
                Intent intent2 = new Intent(Constants.ACTION_COMMAND);
                intent2.putExtra("command", 106);
                intent2.setClassName(stringExtra, com.taobao.accs.utl.j.channelService);
                intent2.putExtra("routingAck", true);
                intent2.putExtra("packageName", stringExtra);
                intent2.putExtra(Constants.KEY_DATA_ID, str);
                com.taobao.accs.a.a.a(context, intent2);
            } catch (Throwable th) {
                ALog.e("MsgDistribute", "send routing ack", th, Constants.KEY_SERVICE_ID, str2);
            }
        }
        return z2;
    }

    private boolean a(Context context, Intent intent, String str, String str2, String str3) {
        AccsClientConfig configByTag = !TextUtils.isEmpty(str3) ? AccsClientConfig.getConfigByTag(str3) : null;
        if (context.getPackageName().equals(intent.getPackage()) || !(configByTag == null || configByTag.isPullUpEnable())) {
            return false;
        }
        try {
            ALog.e("MsgDistribute", "start MsgDistributeService", "receive pkg", context.getPackageName(), "target pkg", intent.getPackage(), Constants.KEY_SERVICE_ID, str2);
            intent.setClassName(intent.getPackage(), com.taobao.accs.utl.j.msgService);
            intent.putExtra("routingMsg", true);
            intent.putExtra("packageName", context.getPackageName());
            com.taobao.accs.a.a.a(context, intent);
            if (f20156a == null) {
                f20156a = new HashSet();
            }
            f20156a.add(str);
        } catch (Throwable th) {
            th = th;
        }
        try {
            ThreadPoolExecutorFactory.schedule(new i(this, str, str2, intent), 10L, TimeUnit.SECONDS);
        } catch (Throwable th2) {
            th = th2;
            com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_MSG_ROUTING_RATE, "", "exception", th.toString());
            ALog.e("MsgDistribute", "routing msg error, try election", th, Constants.KEY_SERVICE_ID, str2, Constants.KEY_DATA_ID, str);
            return true;
        }
        return true;
    }
}

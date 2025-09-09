package com.taobao.accs.utl;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;
import com.vivo.push.PushClientConstants;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.accs.AgooService;

/* loaded from: classes4.dex */
public class a {
    public static final String TAG = "a";

    /* renamed from: a, reason: collision with root package name */
    private static Handler f20351a = new Handler(Looper.getMainLooper());

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r30v1 */
    /* JADX WARN: Type inference failed for: r30v13 */
    /* JADX WARN: Type inference failed for: r30v14 */
    /* JADX WARN: Type inference failed for: r30v2 */
    /* JADX WARN: Type inference failed for: r30v9 */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.lang.Object[]] */
    public static int a(Context context, Intent intent, AccsDataListener accsDataListener) {
        String str;
        String str2;
        int i2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        TaoBaseService.ExtraInfo extraInfoC;
        if (accsDataListener == null || context == null) {
            ALog.e(TAG, "onReceiveData listener or context null", new Object[0]);
            return 2;
        }
        if (intent == null) {
            return 2;
        }
        try {
            try {
                int intExtra = intent.getIntExtra("command", -1);
                int intExtra2 = intent.getIntExtra("errorCode", 0);
                String stringExtra = intent.getStringExtra(Constants.KEY_USER_ID);
                String stringExtra2 = intent.getStringExtra(Constants.KEY_DATA_ID);
                String stringExtra3 = intent.getStringExtra(Constants.KEY_SERVICE_ID);
                String str8 = "accs-impaas";
                if (ALog.isPrintLog(ALog.Level.I) || "accs-impaas".equals(stringExtra3)) {
                    i2 = intExtra2;
                    String str9 = TAG;
                    str3 = "1";
                    try {
                        ?? r8 = {Constants.KEY_DATA_ID, stringExtra2, Constants.KEY_SERVICE_ID, stringExtra3, "command", Integer.valueOf(intExtra), PushClientConstants.TAG_CLASS_NAME, accsDataListener.getClass().getName()};
                        ALog.e(str9, "onReceiveData", r8);
                        str5 = r8;
                    } catch (Exception e2) {
                        e = e2;
                        str4 = "onReceiveData";
                        str2 = str3;
                        str = str4;
                        k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", str2, "callback error" + e.toString());
                        ALog.e(TAG, str, e, new Object[0]);
                        return 2;
                    }
                } else {
                    i2 = intExtra2;
                    str3 = "1";
                    str5 = "1";
                }
                try {
                    if (intExtra <= 0) {
                        ALog.w(TAG, "onReceiveData command not handled", new Object[0]);
                        return 2;
                    }
                    UTMini.getInstance().commitEvent(66001, "MsgToBuss5", "commandId=" + intExtra, "serviceId=" + stringExtra3 + " dataId=" + stringExtra2, 221);
                    String str10 = "onReceiveData";
                    try {
                        k.a("accs", BaseMonitor.COUNT_POINT_TO_BUSS, "3commandId=" + intExtra + "serviceId=" + stringExtra3, 0.0d);
                        if (intExtra == 5) {
                            a(stringExtra3, new b(accsDataListener, stringExtra3, i2, intent));
                            return 2;
                        }
                        if (intExtra == 6) {
                            a(stringExtra3, new c(accsDataListener, stringExtra3, i2, intent));
                            return 2;
                        }
                        try {
                            if (intExtra == 100) {
                                int i3 = i2;
                                NetPerformanceMonitor netPerformanceMonitorA = a(intent);
                                if (netPerformanceMonitorA != null) {
                                    str6 = stringExtra2;
                                    long jCurrentTimeMillis = System.currentTimeMillis();
                                    netPerformanceMonitorA.service_recv = jCurrentTimeMillis;
                                    if (netPerformanceMonitorA.start_service == 0) {
                                        netPerformanceMonitorA.start_service = jCurrentTimeMillis;
                                    }
                                } else {
                                    str6 = stringExtra2;
                                }
                                if (TextUtils.equals("res", intent.getStringExtra(Constants.KEY_SEND_TYPE))) {
                                    a(stringExtra3, new e(netPerformanceMonitorA, stringExtra3, str6, accsDataListener, i3, intent.getByteArrayExtra("data"), intent));
                                    return 2;
                                }
                                a(stringExtra3, new f(netPerformanceMonitorA, stringExtra3, str6, accsDataListener, i3, intent));
                                return 2;
                            }
                            if (intExtra != 101) {
                                if (intExtra != 103) {
                                    if (intExtra != 104) {
                                        ALog.w(TAG, "onReceiveData command not handled", new Object[0]);
                                        return 2;
                                    }
                                    boolean booleanExtra = intent.getBooleanExtra(Constants.KEY_ANTI_BRUSH_RET, false);
                                    ALog.e(TAG, "onReceiveData anti brush result:" + booleanExtra, new Object[0]);
                                    a(stringExtra3, new g(accsDataListener, booleanExtra));
                                    return 2;
                                }
                                boolean booleanExtra2 = intent.getBooleanExtra(Constants.KEY_CONNECT_AVAILABLE, false);
                                String stringExtra4 = intent.getStringExtra("host");
                                String stringExtra5 = intent.getStringExtra(Constants.KEY_ERROR_DETAIL);
                                boolean booleanExtra3 = intent.getBooleanExtra(Constants.KEY_TYPE_INAPP, false);
                                boolean booleanExtra4 = intent.getBooleanExtra(Constants.KEY_CENTER_HOST, false);
                                if (TextUtils.isEmpty(stringExtra4)) {
                                    return 2;
                                }
                                if (booleanExtra2) {
                                    a(stringExtra3, new h(accsDataListener, stringExtra4, booleanExtra3, booleanExtra4));
                                    return 2;
                                }
                                a(stringExtra3, new i(accsDataListener, stringExtra4, booleanExtra3, booleanExtra4, i2, stringExtra5));
                                return 2;
                            }
                            try {
                                byte[] byteArrayExtra = intent.getByteArrayExtra("data");
                                boolean booleanExtra5 = intent.getBooleanExtra(Constants.KEY_NEED_BUSINESS_ACK, false);
                                if (byteArrayExtra == null) {
                                    str8 = str10;
                                    try {
                                        ALog.e(TAG, "onReceiveData COMMAND_RECEIVE_DATA msg null", new Object[0]);
                                        k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, stringExtra3, str3, "COMMAND_RECEIVE_DATA msg null");
                                        return 2;
                                    } catch (Exception e3) {
                                        e = e3;
                                        str10 = str3;
                                        str = str8;
                                        str2 = str10;
                                        k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", str2, "callback error" + e.toString());
                                        ALog.e(TAG, str, e, new Object[0]);
                                        return 2;
                                    }
                                }
                                try {
                                    if (ALog.isPrintLog(ALog.Level.D) || "accs-impaas".equals(stringExtra3)) {
                                        ALog.e(TAG, "onReceiveData COMMAND_RECEIVE_DATA onData dataId:" + stringExtra2 + " serviceId:" + stringExtra3, new Object[0]);
                                    }
                                    extraInfoC = c(intent);
                                    if (booleanExtra5) {
                                        ALog.i(TAG, "onReceiveData try to send biz ack dataId " + stringExtra2, new Object[0]);
                                        a(context, intent, stringExtra2, extraInfoC.oriExtHeader);
                                    }
                                    try {
                                        intent.getExtras().setClassLoader(NetPerformanceMonitor.class.getClassLoader());
                                        NetPerformanceMonitor netPerformanceMonitor = (NetPerformanceMonitor) intent.getExtras().getSerializable(Constants.KEY_MONIROT);
                                        if (netPerformanceMonitor != null) {
                                            netPerformanceMonitor.onToAccsTime();
                                            if (!(context instanceof AgooService)) {
                                                AppMonitor.getInstance().commitStat(netPerformanceMonitor);
                                            }
                                        }
                                    } catch (Exception e4) {
                                        ALog.e(TAG, "get NetPerformanceMonitor Error:", e4, new Object[0]);
                                    }
                                    str7 = str10;
                                } catch (Exception e5) {
                                    e = e5;
                                    str7 = str10;
                                }
                                try {
                                    k.a("accs", BaseMonitor.COUNT_POINT_TO_BUSS_SUCCESS, "1commandId=101serviceId=" + stringExtra3, 0.0d);
                                    a(stringExtra3, new d(stringExtra3, stringExtra2, intExtra, accsDataListener, stringExtra, byteArrayExtra, extraInfoC));
                                    return 2;
                                } catch (Exception e6) {
                                    e = e6;
                                    str4 = str7;
                                    str2 = str3;
                                    str = str4;
                                    k.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", str2, "callback error" + e.toString());
                                    ALog.e(TAG, str, e, new Object[0]);
                                    return 2;
                                }
                            } catch (Exception e7) {
                                e = e7;
                                str8 = str10;
                            }
                        } catch (Exception e8) {
                            e = e8;
                        }
                    } catch (Exception e9) {
                        e = e9;
                        str4 = str10;
                    }
                } catch (Exception e10) {
                    e = e10;
                    str2 = str5;
                    str = context;
                }
            } catch (Exception e11) {
                e = e11;
                str = "onReceiveData";
                str2 = "1";
            }
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TaoBaseService.ExtraInfo c(Intent intent) {
        TaoBaseService.ExtraInfo extraInfo = new TaoBaseService.ExtraInfo();
        try {
            HashMap map = (HashMap) intent.getSerializableExtra(TaoBaseService.ExtraInfo.EXT_HEADER);
            Map<TaoBaseService.ExtHeaderType, String> mapA = a(map);
            String stringExtra = intent.getStringExtra("packageName");
            String stringExtra2 = intent.getStringExtra("host");
            extraInfo.connType = intent.getIntExtra(Constants.KEY_CONN_TYPE, 0);
            extraInfo.extHeader = mapA;
            extraInfo.oriExtHeader = map;
            extraInfo.fromPackage = stringExtra;
            extraInfo.fromHost = stringExtra2;
        } catch (Throwable th) {
            ALog.e(TAG, "getExtraInfo", th, new Object[0]);
        }
        return extraInfo;
    }

    private static void a(String str, Runnable runnable) {
        if ("accs-impaas".equals(str) && t.e()) {
            ThreadPoolExecutorFactory.executeCallback(runnable);
        } else {
            f20351a.post(runnable);
        }
    }

    public static NetPerformanceMonitor a(Intent intent) {
        try {
            intent.getExtras().setClassLoader(NetPerformanceMonitor.class.getClassLoader());
            return (NetPerformanceMonitor) intent.getExtras().getSerializable(Constants.KEY_MONIROT);
        } catch (Exception e2) {
            ALog.e(TAG, "get NetPerformanceMonitor Error:", e2, new Object[0]);
            return null;
        }
    }

    private static Map<TaoBaseService.ExtHeaderType, String> a(Map<Integer, String> map) {
        HashMap map2;
        HashMap map3 = null;
        if (map == null) {
            return null;
        }
        try {
            map2 = new HashMap();
        } catch (Exception e2) {
            e = e2;
        }
        try {
            for (TaoBaseService.ExtHeaderType extHeaderType : TaoBaseService.ExtHeaderType.values()) {
                String str = map.get(Integer.valueOf(extHeaderType.ordinal()));
                if (!TextUtils.isEmpty(str)) {
                    map2.put(extHeaderType, str);
                }
            }
            return map2;
        } catch (Exception e3) {
            e = e3;
            map3 = map2;
            ALog.e(TAG, "getExtHeader", e, new Object[0]);
            return map3;
        }
    }

    private static void a(Context context, Intent intent, String str, Map<Integer, String> map) {
        try {
            ALog.i(TAG, "sendBusinessAck", Constants.KEY_DATA_ID, str);
            if (intent != null) {
                String stringExtra = intent.getStringExtra("host");
                String stringExtra2 = intent.getStringExtra("source");
                String stringExtra3 = intent.getStringExtra("target");
                String stringExtra4 = intent.getStringExtra("appKey");
                String stringExtra5 = intent.getStringExtra(Constants.KEY_CONFIG_TAG);
                short shortExtra = intent.getShortExtra(Constants.KEY_FLAGS, (short) 0);
                com.taobao.accs.b accsInstance = ACCSManager.getAccsInstance(context, stringExtra4, stringExtra5);
                if (accsInstance == null) {
                    k.a("accs", BaseMonitor.COUNT_BUSINESS_ACK_FAIL, "no acsmgr", 0.0d);
                } else {
                    accsInstance.a(stringExtra3, stringExtra2, str, shortExtra, stringExtra, map);
                    k.a("accs", BaseMonitor.COUNT_BUSINESS_ACK_SUCC, "", 0.0d);
                }
            }
        } catch (Throwable th) {
            ALog.e(TAG, "sendBusinessAck", th, new Object[0]);
            k.a("accs", BaseMonitor.COUNT_BUSINESS_ACK_FAIL, th.toString(), 0.0d);
        }
    }
}

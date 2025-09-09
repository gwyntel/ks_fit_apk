package org.android.agoo.accs;

import android.text.TextUtils;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.j;
import com.taobao.accs.utl.k;
import java.nio.charset.Charset;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.CallBack;
import org.android.agoo.common.Config;
import org.android.agoo.control.AgooFactory;

/* loaded from: classes5.dex */
public class AgooService extends TaoBaseService {

    /* renamed from: a, reason: collision with root package name */
    public static CallBack f26502a;

    /* renamed from: b, reason: collision with root package name */
    public static CallBack f26503b;

    /* renamed from: c, reason: collision with root package name */
    private AgooFactory f26504c;

    private String a(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                stringBuffer.append(stackTraceElement.toString());
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onBind(String str, int i2, TaoBaseService.ExtraInfo extraInfo) {
        if (ALog.isPrintLog(ALog.Level.E)) {
            ALog.e("AgooService", "into--[onBind]:serviceId:" + str + ",errorCode=" + i2, new Object[0]);
        }
        if (f26502a != null && GlobalClientInfo.AGOO_SERVICE_ID.equals(str)) {
            if (i2 == 200) {
                f26502a.onSuccess();
            } else {
                f26502a.onFailure(String.valueOf(i2), "bind Agoo service fail");
            }
        }
        f26502a = null;
    }

    @Override // com.taobao.accs.base.TaoBaseService, android.app.Service
    public void onCreate() {
        super.onCreate();
        ALog.d("AgooService", "into--[onCreate]", new Object[0]);
        AgooFactory agooFactory = new AgooFactory();
        this.f26504c = agooFactory;
        agooFactory.init(getApplicationContext(), null, null);
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onData(String str, String str2, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.i("AgooService", "into--[onData]:serviceId:" + str + ",dataId=" + str3, new Object[0]);
            StringBuilder sb = new StringBuilder();
            sb.append("push data:");
            sb.append(new String(bArr, Charset.forName("UTF-8")));
            ALog.d("AgooService", sb.toString(), new Object[0]);
        }
        UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.agooService", j.b(getApplicationContext()), str3);
        k.a("accs", BaseMonitor.COUNT_AGOO_TOTAL_ARRIVE, "total_arrive", 0.0d);
        try {
            this.f26504c.saveMsg(bArr);
            this.f26504c.msgRecevie(bArr, "accs", extraInfo);
        } catch (Throwable th) {
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.agooService", "onDataError", th);
            ALog.e("AgooService", "into--[onData,dealMessage]:error:" + th, new Object[0]);
        }
    }

    @Override // com.taobao.accs.base.TaoBaseService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onResponse(String str, String str2, int i2, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.i("AgooService", "onResponse,dataId=" + str2 + ",errorCode=" + i2 + ",data=" + bArr + ",serviceId=" + str, new Object[0]);
        }
        String str3 = null;
        if (bArr != null) {
            try {
                if (bArr.length > 0) {
                    str3 = new String(bArr, "utf-8");
                }
            } catch (Throwable th) {
                ALog.e("AgooService", "onResponse get data error,e=" + th, new Object[0]);
            }
        }
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.d("AgooService", "onResponse,message=" + str3, new Object[0]);
        }
        if (i2 == 200 && TextUtils.equals(str, AgooConstants.AGOO_SERVICE_AGOOACK)) {
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.e("AgooService", "request is success", Constants.KEY_DATA_ID, str2);
            }
            this.f26504c.updateMsg(bArr, true);
        } else {
            if (i2 != 200 && TextUtils.equals(str, AgooConstants.AGOO_SERVICE_AGOOACK)) {
                if (ALog.isPrintLog(ALog.Level.E)) {
                    ALog.e("AgooService", "request is error", Constants.KEY_DATA_ID, str2, "errorid", Integer.valueOf(i2));
                }
                Config.a(getApplicationContext(), 1);
                k.a("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, String.valueOf(i2), 0.0d);
                return;
            }
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.e("AgooService", "business request is error,message=" + str3, new Object[0]);
            }
        }
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onSendData(String str, String str2, int i2, TaoBaseService.ExtraInfo extraInfo) {
        try {
            ALog.Level level = ALog.Level.I;
            if (ALog.isPrintLog(level)) {
                ALog.i("AgooService", "onSendData,dataId=" + str2 + ",errorCode=" + i2 + ",serviceId=" + str, new Object[0]);
            }
            if (i2 != 200) {
                if (TextUtils.equals(AgooConstants.AGOO_SERVICE_AGOOACK, str)) {
                    Config.a(getApplicationContext(), 1);
                    k.a("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, String.valueOf(i2), 0.0d);
                }
                if (ALog.isPrintLog(level)) {
                    ALog.i("AgooService", "onSendData error,dataId=" + str2 + ",serviceId=" + str, new Object[0]);
                    ALog.e("AgooService", "into--[parseError]", new Object[0]);
                }
                UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.agooService", j.b(getApplicationContext()), "errorCode", str2 + ",serviceId=" + str + ",errorCode=" + i2);
                return;
            }
            if (TextUtils.equals(AgooConstants.AGOO_SERVICE_AGOOACK, str)) {
                k.a("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, "8/9", 0.0d);
            }
            if (!TextUtils.isEmpty(str) && TextUtils.equals(str, AgooConstants.AGOO_SERVICE_AGOOACK) && Long.parseLong(str2) > 300000000 && Long.parseLong(str2) < 600000000) {
                if (ALog.isPrintLog(level)) {
                    ALog.i("AgooService", "onSendData,AckData=" + str2 + ",serviceId=" + str, new Object[0]);
                    return;
                }
                return;
            }
            if (TextUtils.isEmpty(str) || !TextUtils.equals(str, AgooConstants.AGOO_SERVICE_AGOOACK) || Long.parseLong(str2) <= 600000000 || !ALog.isPrintLog(level)) {
                return;
            }
            ALog.i("AgooService", "onSendData,reportData=" + str2 + ",serviceId=" + str, new Object[0]);
        } catch (Throwable th) {
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.e("AgooService", "onSendData exception,e=" + th.getMessage() + ",e.getStackMsg=" + a(th), new Object[0]);
            }
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.agooService", j.b(getApplicationContext()), "onSendDataException", a(th));
        }
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onUnbind(String str, int i2, TaoBaseService.ExtraInfo extraInfo) {
        if (ALog.isPrintLog(ALog.Level.E)) {
            ALog.e("AgooService", "into--[onUnbind]:serviceId:" + str + ",errorCode=" + i2, new Object[0]);
        }
        if (f26503b != null && GlobalClientInfo.AGOO_SERVICE_ID.equals(str)) {
            if (i2 == 200) {
                f26503b.onSuccess();
            } else {
                f26503b.onFailure(String.valueOf(i2), "unbind Agoo service fail");
            }
        }
        f26503b = null;
    }
}

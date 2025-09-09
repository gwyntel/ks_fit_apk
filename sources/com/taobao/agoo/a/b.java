package com.taobao.agoo.a;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.l;
import com.taobao.accs.utl.p;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.IRegister;
import com.taobao.agoo.TaobaoRegister;
import com.taobao.agoo.a.a.d;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.Config;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b implements AccsDataListener {

    /* renamed from: b, reason: collision with root package name */
    public static a f20447b;

    /* renamed from: a, reason: collision with root package name */
    public Map<String, ICallback> f20448a = new HashMap();

    public b(Context context) {
        if (f20447b == null) {
            f20447b = new a(context.getApplicationContext());
        }
    }

    private void a(JSONObject jSONObject, ICallback iCallback) throws JSONException {
        String strA = p.a(jSONObject, com.taobao.agoo.a.a.a.JSON_PUSH_USER_TOKEN, null);
        if (TextUtils.isEmpty(strA)) {
            if (iCallback != null) {
                iCallback.onFailure("", "agoo server error-pushtoken null");
            }
        } else {
            Config.c(GlobalClientInfo.getContext(), strA);
            if (iCallback != null) {
                iCallback.onSuccess();
                f20447b.c(iCallback.extra);
            }
        }
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onAntiBrush(boolean z2, TaoBaseService.ExtraInfo extraInfo) {
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onBind(String str, int i2, TaoBaseService.ExtraInfo extraInfo) {
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onConnected(TaoBaseService.ConnectInfo connectInfo) {
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onData(String str, String str2, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onDisconnected(TaoBaseService.ConnectInfo connectInfo) {
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onResponse(String str, String str2, int i2, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
        boolean zEquals;
        try {
            if ("AgooDeviceCmd".equals(str)) {
                ICallback iCallback = this.f20448a.get(str2);
                if (i2 == 200) {
                    String str3 = new String(bArr, "utf-8");
                    ALog.i("RequestListener", "RequestListener onResponse", Constants.KEY_DATA_ID, str2, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, iCallback, "json", str3);
                    JSONObject jSONObject = new JSONObject(str3);
                    String strA = p.a(jSONObject, com.taobao.agoo.a.a.b.JSON_ERRORCODE, null);
                    String strA2 = p.a(jSONObject, com.taobao.agoo.a.a.b.JSON_CMD, null);
                    if (!"success".equals(strA)) {
                        if (iCallback != null) {
                            iCallback.onFailure(String.valueOf(strA), "agoo server error");
                        }
                        if (zEquals) {
                            return;
                        } else {
                            return;
                        }
                    }
                    if ("register".equals(strA2)) {
                        String strA3 = p.a(jSONObject, "deviceId", null);
                        if (!TextUtils.isEmpty(strA3)) {
                            TaobaoRegister.setIsRegisterSuccess(true);
                            l.a().b();
                            Config.b(GlobalClientInfo.getContext(), strA3);
                            f20447b.a(GlobalClientInfo.getContext().getPackageName());
                            if (iCallback instanceof IRegister) {
                                UtilityImpl.c(Config.PREFERENCES, GlobalClientInfo.getContext());
                                ((IRegister) iCallback).onSuccess(strA3);
                            }
                        } else if (iCallback != null) {
                            iCallback.onFailure("", "agoo server error deviceid null");
                        }
                        if ("AgooDeviceCmd".equals(str)) {
                            this.f20448a.remove(str2);
                            return;
                        }
                        return;
                    }
                    if (com.taobao.agoo.a.a.a.JSON_CMD_SETALIAS.equals(strA2)) {
                        a(jSONObject, iCallback);
                        if ("AgooDeviceCmd".equals(str)) {
                            this.f20448a.remove(str2);
                            return;
                        }
                        return;
                    }
                    if (com.taobao.agoo.a.a.a.JSON_CMD_REMOVEALIAS.equals(strA2)) {
                        Config.c(GlobalClientInfo.getContext(), null);
                        if (iCallback != null) {
                            iCallback.onSuccess();
                        }
                        f20447b.a();
                        if ("AgooDeviceCmd".equals(str)) {
                            this.f20448a.remove(str2);
                            return;
                        }
                        return;
                    }
                    if ((d.JSON_CMD_ENABLEPUSH.equals(strA2) || d.JSON_CMD_DISABLEPUSH.equals(strA2)) && iCallback != null) {
                        iCallback.onSuccess();
                    }
                } else if (iCallback != null) {
                    iCallback.onFailure(String.valueOf(i2), "accs channel error");
                }
            }
            if (!"AgooDeviceCmd".equals(str)) {
                return;
            }
        } catch (Throwable th) {
            try {
                ALog.e("RequestListener", "onResponse", th, new Object[0]);
                if (!"AgooDeviceCmd".equals(str)) {
                    return;
                }
            } finally {
                if ("AgooDeviceCmd".equals(str)) {
                    this.f20448a.remove(str2);
                }
            }
        }
        this.f20448a.remove(str2);
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onSendData(String str, String str2, int i2, TaoBaseService.ExtraInfo extraInfo) {
    }

    @Override // com.taobao.accs.base.AccsDataListener
    public void onUnbind(String str, int i2, TaoBaseService.ExtraInfo extraInfo) {
    }
}

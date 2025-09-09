package com.aliyun.alink.linksdk.tmp.device.c;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.config.ProvisionReceiver;
import com.aliyun.alink.linksdk.tmp.data.auth.ProvisionAuthData;
import com.aliyun.alink.linksdk.tmp.data.auth.SetupData;
import com.aliyun.alink.linksdk.tmp.data.config.LocalConfigData;
import com.aliyun.alink.linksdk.tmp.device.payload.setup.SetupRequestPayload;
import com.aliyun.alink.linksdk.tmp.listener.IProvisionListener;
import com.aliyun.alink.linksdk.tmp.listener.IProvisionResponser;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f11381a = "Xtau@iot";

    /* renamed from: b, reason: collision with root package name */
    public static final String f11382b = "Yx3DdsyetbSezlvc";

    /* renamed from: c, reason: collision with root package name */
    private static final String f11383c = "[Tmp]ConfigMgr";

    /* renamed from: d, reason: collision with root package name */
    private LocalConfigData f11384d;

    /* renamed from: com.aliyun.alink.linksdk.tmp.device.c.a$a, reason: collision with other inner class name */
    public static class C0081a {

        /* renamed from: a, reason: collision with root package name */
        private static a f11387a = new a();
    }

    public static a a() {
        return C0081a.f11387a;
    }

    public void b() {
        ProvisionReceiver.getInstance().addListener(new IProvisionListener() { // from class: com.aliyun.alink.linksdk.tmp.device.c.a.1
            @Override // com.aliyun.alink.linksdk.tmp.listener.IProvisionListener
            public void onMsg(String str, String str2, Object obj, IProvisionResponser iProvisionResponser) {
                if (TextUtils.isEmpty(str2) || !str2.endsWith(TmpConstant.PATH_SETUP)) {
                    return;
                }
                SetupRequestPayload setupRequestPayload = (SetupRequestPayload) GsonUtils.fromJson(String.valueOf(obj), new TypeToken<SetupRequestPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.c.a.1.1
                }.getType());
                if (a.this.a(setupRequestPayload.getParams())) {
                    if (setupRequestPayload.getParams().configType.equalsIgnoreCase("ServerAuthInfo")) {
                        for (int i2 = 0; i2 < setupRequestPayload.getParams().configValue.size(); i2++) {
                            com.aliyun.alink.linksdk.tmp.connect.a.b(setupRequestPayload.getParams().configValue.get(i2).authCode, setupRequestPayload.getParams().configValue.get(i2).authSecret);
                            com.aliyun.alink.linksdk.tmp.connect.a.b("Xtau@iot");
                        }
                    }
                    iProvisionResponser.onComplete(str, null, null);
                }
            }
        });
    }

    public LocalConfigData c() {
        return this.f11384d;
    }

    private a() {
    }

    public void a(LocalConfigData localConfigData) {
        this.f11384d = localConfigData;
    }

    public boolean a(SetupData setupData) throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
        List<ProvisionAuthData> list;
        int i2 = 0;
        if (setupData == null || (list = setupData.configValue) == null || list.isEmpty() || TextUtils.isEmpty(setupData.configType)) {
            return false;
        }
        if (setupData.configType.equalsIgnoreCase(TmpConstant.CONFIG_TYPE_CLIENT)) {
            while (i2 < setupData.configValue.size()) {
                ProvisionAuthData provisionAuthData = setupData.configValue.get(i2);
                TmpStorage.getInstance().saveAccessInfo(provisionAuthData.getId(), provisionAuthData.accessKey, provisionAuthData.accessToken, true, "local");
                i2++;
            }
            return true;
        }
        if (!setupData.configType.equalsIgnoreCase("ServerAuthInfo")) {
            return true;
        }
        while (i2 < setupData.configValue.size()) {
            ProvisionAuthData provisionAuthData2 = setupData.configValue.get(i2);
            TmpStorage.getInstance().saveServerEnptInfo(provisionAuthData2.getId(), provisionAuthData2.authCode, provisionAuthData2.authSecret, "local");
            i2++;
        }
        return true;
    }
}

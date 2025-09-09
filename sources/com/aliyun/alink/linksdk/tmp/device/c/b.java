package com.aliyun.alink.linksdk.tmp.device.c;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.data.config.LocalConfigData;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.ResHelper;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11388a = "[Tmp]LocalConfigurator";

    /* renamed from: b, reason: collision with root package name */
    private static final String f11389b = "local_config";

    /* renamed from: c, reason: collision with root package name */
    private static final String f11390c = "tmp_config";

    public static void a(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LocalConfigData.DynamicConfig dynamicConfig;
        File externalFilesDir;
        File file = null;
        if (TextUtils.isEmpty(str) && (externalFilesDir = TmpSdk.getContext().getExternalFilesDir(null)) != null) {
            file = new File(new File(externalFilesDir.getAbsolutePath(), f11390c).getAbsolutePath(), f11389b);
        }
        if (file == null && !TextUtils.isEmpty(str)) {
            file = new File(str);
        }
        if (file == null || !file.exists()) {
            ALog.w(f11388a, "LocalConfigurator config not exist");
            return;
        }
        ALog.d(f11388a, "LocalConfigurator start path:" + file.getAbsolutePath());
        LocalConfigData localConfigData = (LocalConfigData) GsonUtils.fromJson(ResHelper.getFileStr(file), new TypeToken<LocalConfigData>() { // from class: com.aliyun.alink.linksdk.tmp.device.c.b.1
        }.getType());
        a.a().a(localConfigData);
        if (localConfigData == null || (dynamicConfig = localConfigData.configReceiver) == null || dynamicConfig.autoRun) {
            ALog.i(f11388a, "configReceiver end");
        } else {
            ALog.i(f11388a, "configReceiver not autorun");
        }
    }
}

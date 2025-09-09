package com.umeng.vt.diff;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback;
import com.umeng.vt.diff.util.ClassLoadUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class Channel {
    private static final String UM_VISUAL_IMPRINT = "utm-visual";

    private void download() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method;
        try {
            Class<?> clsFindClass = ClassLoadUtil.findClass("com.umeng.vt.vismode.config.ConfigTools");
            if (clsFindClass == null || (method = clsFindClass.getMethod("download", null)) == null) {
                return;
            }
            method.invoke(clsFindClass.getMethod("getInstance", null).invoke(null, null), null);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void storeConfig(Map<String, String> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method;
        try {
            Class<?> clsFindClass = ClassLoadUtil.findClass("com.umeng.vt.vismode.config.ConfigTools");
            if (clsFindClass == null || (method = clsFindClass.getMethod("storeConfig", Map.class)) == null) {
                return;
            }
            method.invoke(clsFindClass.getMethod("getInstance", null).invoke(null, null), map);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void init(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (context == null) {
            return;
        }
        Context applicationContext = context.getApplicationContext();
        loadConfig(applicationContext);
        registerListener(applicationContext);
    }

    public void loadConfig(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(context, UM_VISUAL_IMPRINT, null);
        if (TextUtils.isEmpty(strImprintProperty)) {
            return;
        }
        String str = new String(Base64.decode(strImprintProperty, 0));
        HashMap map = new HashMap();
        map.put("data-track", str);
        storeConfig(map);
    }

    public void registerListener(final Context context) {
        ImprintHandler.getImprintService(context).registImprintCallback(UM_VISUAL_IMPRINT, new UMImprintChangeCallback() { // from class: com.umeng.vt.diff.Channel.1
            @Override // com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback
            public void onImprintValueChanged(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (Channel.UM_VISUAL_IMPRINT.equals(str)) {
                    Channel.this.loadConfig(context);
                }
            }
        });
    }
}

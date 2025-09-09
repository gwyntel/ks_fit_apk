package com.alibaba.sdk.android.openaccount.webview;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class BridgeInterfaceContainer {
    private static final String LOGIN_BRIDGE = "loginBridge";
    private Map<String, Object> nameToObj = new HashMap();

    public final void addHavanaBridgeObject(Object obj, String str) {
        if (TextUtils.equals(str, "loginBridge")) {
            return;
        }
        this.nameToObj.put(str, obj);
    }

    public Object getBridgeObj(String str) {
        return this.nameToObj.get(str);
    }
}

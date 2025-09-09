package com.aliyun.iot.aep.sdk.framework.sdk;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class SDKConfigure {
    public final JSONArray classFiles;
    public final String description;
    public final String doc;
    public final boolean initializedInUIThread;
    public final String name;
    public final boolean needIoTToken;
    public final JSONObject opts;
    public final String process;
    public final ArrayList<SDKConfigure> submodules;
    public final String version;

    public SDKConfigure(String str, String str2, String str3, String str4, JSONArray jSONArray, boolean z2, JSONObject jSONObject, String str5, ArrayList<SDKConfigure> arrayList) {
        this.name = str;
        this.version = str2;
        this.description = str3;
        this.doc = str4;
        this.classFiles = jSONArray;
        this.needIoTToken = z2;
        this.opts = jSONObject;
        this.process = str5;
        this.submodules = arrayList;
        this.initializedInUIThread = false;
    }

    public String toString() {
        ArrayList<SDKConfigure> arrayList = this.submodules;
        String string = TmpConstant.GROUP_ROLE_UNKNOWN;
        String string2 = arrayList == null ? TmpConstant.GROUP_ROLE_UNKNOWN : arrayList.toString();
        JSONArray jSONArray = this.classFiles;
        if (jSONArray != null) {
            string = jSONArray.toString();
        }
        return "SDKConfigure{name='" + this.name + "', version='" + this.version + "', description='" + this.description + "', doc='" + this.doc + "', initializedInUIThread=" + this.initializedInUIThread + ", needIoTToken=" + this.needIoTToken + ", process='" + this.process + "', classFiles='" + string + "', submodules='" + string2 + "'}";
    }

    public SDKConfigure(String str, String str2, String str3, String str4, JSONArray jSONArray, boolean z2, boolean z3, JSONObject jSONObject, String str5, ArrayList<SDKConfigure> arrayList) {
        this.name = str;
        this.version = str2;
        this.description = str3;
        this.doc = str4;
        this.classFiles = jSONArray;
        this.initializedInUIThread = z2;
        this.needIoTToken = z3;
        this.opts = jSONObject;
        this.process = str5;
        this.submodules = arrayList;
    }
}

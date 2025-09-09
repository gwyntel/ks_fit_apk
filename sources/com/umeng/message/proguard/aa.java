package com.umeng.message.proguard;

import com.umeng.message.api.UPushAliasCallback;
import org.json.JSONObject;

/* loaded from: classes4.dex */
abstract class aa {
    aa() {
    }

    abstract void a() throws Exception;

    abstract void a(String str) throws Exception;

    abstract void a(String str, int i2) throws Exception;

    abstract void a(String str, int i2, String str2) throws Exception;

    abstract void a(String str, String str2, int i2) throws Exception;

    abstract void a(String str, String str2, JSONObject jSONObject, UPushAliasCallback uPushAliasCallback) throws Exception;

    abstract void b(String str, String str2, JSONObject jSONObject, UPushAliasCallback uPushAliasCallback) throws Exception;

    abstract void c(String str, String str2, JSONObject jSONObject, UPushAliasCallback uPushAliasCallback) throws Exception;
}

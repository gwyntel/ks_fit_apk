package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class a implements f {

    /* renamed from: a, reason: collision with root package name */
    public static final String f10775a = "[AlcsLPBS]DefaultICAStorage";

    /* renamed from: b, reason: collision with root package name */
    protected static final String f10776b = "asKey_pre_";

    /* renamed from: c, reason: collision with root package name */
    protected static final String f10777c = "asToken_pre_";

    /* renamed from: f, reason: collision with root package name */
    private static final String f10778f = "DefaultICAStoragePerf";

    /* renamed from: d, reason: collision with root package name */
    protected SharedPreferences f10779d;

    /* renamed from: e, reason: collision with root package name */
    protected SharedPreferences.Editor f10780e;

    /* renamed from: g, reason: collision with root package name */
    private Context f10781g;

    public a(Context context) {
        this.f10781g = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(f10778f, 0);
        this.f10779d = sharedPreferences;
        this.f10780e = sharedPreferences.edit();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.f
    public void a(String str, String str2, String str3) {
        this.f10780e.putString(f10776b + str, str2);
        this.f10780e.putString(f10777c + str, str3);
        this.f10780e.apply();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.f
    public ICAAuthParams a(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string = this.f10779d.getString(f10776b + str, null);
        String string2 = this.f10779d.getString(f10777c + str, null);
        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
            ICAAuthParams iCAAuthParams = new ICAAuthParams();
            iCAAuthParams.accessKey = string;
            iCAAuthParams.accessToken = string2;
            return iCAAuthParams;
        }
        ALog.e(f10775a, "getAccessInfo empty id:" + str);
        return null;
    }
}

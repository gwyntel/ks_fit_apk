package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b;

import android.util.Base64;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthPairs;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.alcs.lpbs.utils.RandomUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.utils.SecureUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f10782a = "2";

    /* renamed from: b, reason: collision with root package name */
    public static final String f10783b = "0";

    /* renamed from: c, reason: collision with root package name */
    public static final String f10784c = "1";

    /* renamed from: d, reason: collision with root package name */
    public static final String f10785d = "Xtau@iot";

    /* renamed from: e, reason: collision with root package name */
    public static final String f10786e = "Yx3DdsyetbSezlvc";

    /* renamed from: f, reason: collision with root package name */
    public static final String f10787f = "1";

    /* renamed from: g, reason: collision with root package name */
    public static final String f10788g = "001";

    /* renamed from: h, reason: collision with root package name */
    private static final String f10789h = "[AlcsLPBS]ICAAuthInfoCreater";

    /* renamed from: i, reason: collision with root package name */
    private static final int f10790i = 8;

    /* renamed from: j, reason: collision with root package name */
    private static final int f10791j = 16;

    public static ICAAuthPairs a(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String randomString = RandomUtils.getRandomString(8);
        String randomString2 = RandomUtils.getRandomString(16);
        ICAAuthParams iCAAuthParamsA = a(randomString, randomString2, str);
        ICAAuthPairs iCAAuthPairs = new ICAAuthPairs(iCAAuthParamsA.accessKey, iCAAuthParamsA.accessToken, randomString, randomString2);
        ALog.d(f10789h, "authCode:" + randomString + " secret:" + randomString2 + " ak:" + iCAAuthParamsA.accessKey + " at:" + iCAAuthParamsA.accessToken);
        return iCAAuthPairs;
    }

    public static ICAAuthParams a(String str, String str2, String str3) {
        ICAAuthParams iCAAuthParams = new ICAAuthParams();
        String str4 = str + "1" + str3 + "001";
        iCAAuthParams.accessKey = str4;
        iCAAuthParams.accessToken = Base64.encodeToString(SecureUtils.getHMacSha1Str(str4, str2), 2);
        return iCAAuthParams;
    }
}

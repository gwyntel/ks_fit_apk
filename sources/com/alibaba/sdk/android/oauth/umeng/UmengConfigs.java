package com.alibaba.sdk.android.oauth.umeng;

import com.alibaba.sdk.android.oauth.OauthInfoConfig;
import com.google.android.gms.common.Scopes;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class UmengConfigs {
    public static Map<SHARE_MEDIA, OauthInfoConfig> plateform2Config;
    public static Map<Integer, SHARE_MEDIA> plateform2Media = new HashMap();

    static {
        HashMap map = new HashMap();
        plateform2Config = map;
        map.put(SHARE_MEDIA.QQ, new OauthInfoConfig("appId", Scopes.OPEN_ID, "access_token"));
        plateform2Config.put(SHARE_MEDIA.SINA, new OauthInfoConfig("appkey", "uid", "access_token", "access_key"));
        plateform2Config.put(SHARE_MEDIA.WEIXIN, new OauthInfoConfig("appId", Scopes.OPEN_ID, "access_token"));
        plateform2Media.put(4, SHARE_MEDIA.QQ);
        plateform2Media.put(3, SHARE_MEDIA.SINA);
        plateform2Media.put(2, SHARE_MEDIA.WEIXIN);
    }
}

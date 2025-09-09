package com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;

/* loaded from: classes2.dex */
public class a {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str).getString("id");
    }
}

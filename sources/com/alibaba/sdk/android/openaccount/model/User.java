package com.alibaba.sdk.android.openaccount.model;

import java.util.Map;

/* loaded from: classes2.dex */
public class User {
    public String avatarUrl;
    public String displayName;
    public String email;
    public boolean hasPassword;
    public String id;
    public String loginId;
    public String mobile;
    public String mobileLocationCode;
    public String nick;
    public String openId;
    public Map<String, Object> otherInfo;

    public String toString() {
        return "User [id=" + this.id + ", nick=" + this.nick + "]";
    }
}

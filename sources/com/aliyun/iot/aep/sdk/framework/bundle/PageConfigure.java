package com.aliyun.iot.aep.sdk.framework.bundle;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class PageConfigure {
    public String name;
    public ArrayList<NavigationConfigure> navigationConfigures;
    public boolean needLogin;
    public String version;

    public static final class NavigationConfigure {
        public String navigationCode;
        public String navigationIntentAction;
        public String navigationIntentCategory;
        public String navigationIntentUrl;
    }
}

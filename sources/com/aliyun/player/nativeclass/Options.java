package com.aliyun.player.nativeclass;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class Options {
    private static final int FLAG_APPEND = 1;
    private static final int FLAG_REPLACE = 2;
    private Map<String, String> mOptions = new HashMap();

    public String get(String str) {
        return this.mOptions.get(str);
    }

    public void reset() {
        this.mOptions.clear();
    }

    public boolean set(String str, String str2, int i2) {
        Map<String, String> map;
        if (!this.mOptions.containsKey(str)) {
            map = this.mOptions;
        } else if (i2 == 1) {
            map = this.mOptions;
            str2 = this.mOptions.get(str) + str2;
        } else {
            if (i2 != 2) {
                return false;
            }
            map = this.mOptions;
        }
        map.put(str, str2);
        return true;
    }
}

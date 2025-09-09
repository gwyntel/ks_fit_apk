package com.aliyun.alink.linksdk.tmp.device.deviceshadow;

import android.util.LruCache;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class MemoryLruHelper {
    public static final int MAX_LRU_SIZE = 5242880;
    public static final String TAG = "[Tmp]MemoryLruHelper";
    public Map<String, Boolean> isNeedRefreshPropertyMap;
    protected LruCache<String, String> mMemoryLruCahce = new LruCache<>(MAX_LRU_SIZE);

    public void deleteValue(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "deleteValue key:" + str);
        try {
            this.mMemoryLruCahce.remove(str);
        } catch (Exception e2) {
            ALog.e(TAG, "deleteValue error:" + e2.toString());
        }
    }

    public String getString(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str2;
        try {
            str2 = this.mMemoryLruCahce.get(str);
        } catch (Exception e2) {
            ALog.e(TAG, "mDiskLruCache get error:" + e2.toString());
            str2 = null;
        }
        ALog.d(TAG, "getString key:" + str + " result:" + str2);
        return str2;
    }

    public Boolean isRefreshProperty(String str) {
        Map<String, Boolean> map = this.isNeedRefreshPropertyMap;
        return (map == null || !map.containsKey(str)) ? Boolean.FALSE : this.isNeedRefreshPropertyMap.get(str);
    }

    public boolean saveValue(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "saveValue key:" + str + " data:" + str2);
        try {
            this.mMemoryLruCahce.put(str, str2);
            return true;
        } catch (Exception e2) {
            ALog.e(TAG, "mDiskLruCache saveValue error: " + e2.toString());
            return false;
        }
    }

    public void setRefreshProperty(String str, boolean z2) {
        if (this.isNeedRefreshPropertyMap == null) {
            this.isNeedRefreshPropertyMap = new HashMap();
        }
        this.isNeedRefreshPropertyMap.put(str, Boolean.valueOf(z2));
    }
}

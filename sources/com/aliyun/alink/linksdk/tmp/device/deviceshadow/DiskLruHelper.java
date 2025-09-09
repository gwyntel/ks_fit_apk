package com.aliyun.alink.linksdk.tmp.device.deviceshadow;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.disklrucache.DiskLruCache;
import com.aliyun.alink.linksdk.tmp.utils.DiskUtil;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class DiskLruHelper {
    public static final int DEFAULT_MAXSIZE = 33554432;
    public static final int DEFAULT_VALUECOUNT = 1;
    private static final String TAG = "[Tmp]DiskLruHelper";
    private String mCacheFile;
    private int mCacheSize;
    private DiskLruCache mDiskLruCache;
    private int mVersion;

    public DiskLruHelper(String str, int i2) {
        this(str, i2, DEFAULT_MAXSIZE);
    }

    public void clearCache() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "clearCache mDiskLruCache:" + this.mDiskLruCache);
        DiskLruCache diskLruCache = this.mDiskLruCache;
        if (diskLruCache == null) {
            return;
        }
        try {
            diskLruCache.delete();
        } catch (Exception e2) {
            ALog.e(TAG, "clearCache error:" + e2.toString());
        }
        createDiskLruCache();
    }

    protected void createDiskLruCache() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            if (TmpSdk.getContext() != null) {
                this.mDiskLruCache = DiskLruCache.open(DiskUtil.getDiskCacheDirWithAppend(TmpSdk.getContext(), this.mCacheFile), this.mVersion, 1, this.mCacheSize);
            }
        } catch (Exception e2) {
            ALog.e(TAG, "DiskLruCache.open error:" + e2.toString());
        }
    }

    public void deleteValue(String str) {
        DiskLruCache diskLruCache;
        ALog.d(TAG, "deleteValue key:" + str + " mDiskLruCache:" + this.mDiskLruCache);
        if (TextUtils.isEmpty(str) || (diskLruCache = this.mDiskLruCache) == null) {
            return;
        }
        try {
            diskLruCache.remove(str);
        } catch (Exception e2) {
            ALog.e(TAG, "deleteValue error:" + e2.toString());
        }
    }

    public String getString(String str) {
        String fully = null;
        if (TextUtils.isEmpty(str)) {
            ALog.d(TAG, "getString key:" + str + " mDiskLruCache:" + this.mDiskLruCache + " result:" + ((String) null));
            return null;
        }
        DiskLruCache diskLruCache = this.mDiskLruCache;
        if (diskLruCache == null) {
            ALog.d(TAG, "getString key:" + str + " mDiskLruCache:" + this.mDiskLruCache + " result:" + ((String) null));
            return null;
        }
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(str);
            if (snapshot != null && snapshot.getInputStream(0) != null) {
                fully = DiskUtil.readFully(snapshot.getInputStream(0));
            }
        } catch (Exception e2) {
            ALog.e(TAG, "mDiskLruCache get error:" + e2.toString());
        }
        ALog.d(TAG, "getString key:" + str + " mDiskLruCache:" + this.mDiskLruCache + " result:" + fully);
        return fully;
    }

    public boolean saveValue(String str, String str2) {
        DiskLruCache diskLruCache;
        ALog.d(TAG, "saveValue key:" + str + " data:" + str2 + " mDiskLruCache:" + this.mDiskLruCache);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || (diskLruCache = this.mDiskLruCache) == null) {
            return false;
        }
        try {
            DiskLruCache.Editor editorEdit = diskLruCache.edit(str);
            editorEdit.set(0, str2);
            editorEdit.commit();
            return true;
        } catch (Exception e2) {
            ALog.e(TAG, "mDiskLruCache saveValue error: " + e2.toString());
            return false;
        }
    }

    public DiskLruHelper(String str, int i2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mCacheFile = str;
        this.mVersion = i2;
        this.mCacheSize = i3;
        createDiskLruCache();
    }
}

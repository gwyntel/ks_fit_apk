package com.linkkit.tools.utils;

import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class ClassExistHelper {
    private ConcurrentHashMap<String, Boolean> classExistHashMap;

    static class SingletonHolder {
        private static final ClassExistHelper INSTANCE = new ClassExistHelper();

        private SingletonHolder() {
        }
    }

    public static ClassExistHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public synchronized boolean hasClass(String str) {
        try {
            ConcurrentHashMap<String, Boolean> concurrentHashMap = this.classExistHashMap;
            if (concurrentHashMap == null) {
                return false;
            }
            if (concurrentHashMap.containsKey(str)) {
                return this.classExistHashMap.get(str).booleanValue();
            }
            boolean zHasClass = ReflectUtils.hasClass(str);
            if (zHasClass) {
                this.classExistHashMap.put(str, Boolean.TRUE);
            } else {
                this.classExistHashMap.put(str, Boolean.FALSE);
            }
            return zHasClass;
        } catch (Throwable th) {
            throw th;
        }
    }

    private ClassExistHelper() {
        this.classExistHashMap = null;
        this.classExistHashMap = new ConcurrentHashMap<>();
    }
}

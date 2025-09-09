package com.aliyun.alink.linksdk.tools.ut;

import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class AUserTrack {
    public static final String UTKEY_DEVICE_IOTID = "deviceIotId";
    public static final String UTKEY_END_TIME = "endTime";
    public static final String UTKEY_ERROR_CODE = "errorCode";
    public static final String UTKEY_EVENT_NAME = "eventName";
    public static final String UTKEY_MODULE = "module";
    public static final String UTKEY_PAGE_NAME = "pageName";
    public static final String UTKEY_PERFORMANCEID = "performanceId";
    public static final String UTKEY_START_TIME = "startTime";
    public static final String UTKEY_SUB_ERROR_CODE = "subErrorCode";

    /* renamed from: a, reason: collision with root package name */
    protected static IUserTrackDispatch f11469a;

    public static void record(String str, Map<String, String> map) {
        IUserTrackDispatch iUserTrackDispatch = f11469a;
        if (iUserTrackDispatch != null) {
            iUserTrackDispatch.record(str, map);
        }
    }

    public static void setDispatch(IUserTrackDispatch iUserTrackDispatch) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("AUserTrack", "setDispatch dispatch:" + iUserTrackDispatch);
        f11469a = iUserTrackDispatch;
    }

    public static void record(String str, long j2, long j3) {
        if (f11469a != null) {
            HashMap map = new HashMap();
            map.put("startTime", String.valueOf(j2));
            map.put(UTKEY_END_TIME, String.valueOf(j3));
            record(str, map);
        }
    }

    public static void record(String str, long j2, long j3, String str2) {
        if (f11469a != null) {
            HashMap map = new HashMap();
            map.put("startTime", String.valueOf(j2));
            map.put(UTKEY_END_TIME, String.valueOf(j3));
            map.put("errorCode", str2);
            record(str, map);
        }
    }

    public static void record(String str, String str2, long j2, long j3) {
        if (f11469a != null) {
            HashMap map = new HashMap();
            map.put("startTime", String.valueOf(j2));
            map.put(UTKEY_END_TIME, String.valueOf(j3));
            map.put(UTKEY_PAGE_NAME, str2);
            record(str, map);
        }
    }
}

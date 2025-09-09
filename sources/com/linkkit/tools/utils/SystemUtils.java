package com.linkkit.tools.utils;

import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.SimpleDateFormat;

/* loaded from: classes4.dex */
public class SystemUtils {
    public static String getCurrentTime(long j2) {
        try {
            return new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss).format(Long.valueOf(j2));
        } catch (Exception e2) {
            e2.printStackTrace();
            return String.valueOf(j2);
        }
    }
}

package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.os.Process;
import android.text.TextUtils;
import java.util.List;

/* loaded from: classes4.dex */
public final class az {
    public static ActivityManager.ProcessErrorStateInfo a(ActivityManager activityManager, long j2) throws InterruptedException {
        if (activityManager == null) {
            al.c("get anr state, ActivityManager is null", new Object[0]);
            return null;
        }
        al.c("get anr state, timeout:%d", Long.valueOf(j2));
        long j3 = j2 / 500;
        int i2 = 0;
        while (true) {
            ActivityManager.ProcessErrorStateInfo processErrorStateInfoA = a(activityManager.getProcessesInErrorState());
            if (processErrorStateInfoA == null) {
                al.c("found proc state is null", new Object[0]);
            } else {
                int i3 = processErrorStateInfoA.condition;
                if (i3 == 2) {
                    al.c("found proc state is anr! proc:%s", processErrorStateInfoA.processName);
                    return processErrorStateInfoA;
                }
                if (i3 == 1) {
                    al.c("found proc state is crashed!", new Object[0]);
                    return null;
                }
            }
            int i4 = i2 + 1;
            if (i2 >= j3) {
                return a("Find process anr, but unable to get anr message.");
            }
            al.c("try the %s times:", Integer.valueOf(i4));
            ap.b(500L);
            i2 = i4;
        }
    }

    private static ActivityManager.ProcessErrorStateInfo a(List<ActivityManager.ProcessErrorStateInfo> list) {
        if (list != null && !list.isEmpty()) {
            int iMyPid = Process.myPid();
            for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : list) {
                if (processErrorStateInfo.pid == iMyPid) {
                    if (TextUtils.isEmpty(processErrorStateInfo.longMsg)) {
                        return null;
                    }
                    al.c("found current proc in the error state", new Object[0]);
                    return processErrorStateInfo;
                }
            }
            al.c("current proc not in the error state", new Object[0]);
            return null;
        }
        al.c("error state info list is null", new Object[0]);
        return null;
    }

    private static ActivityManager.ProcessErrorStateInfo a(String str) {
        ActivityManager.ProcessErrorStateInfo processErrorStateInfo = new ActivityManager.ProcessErrorStateInfo();
        processErrorStateInfo.pid = Process.myPid();
        processErrorStateInfo.processName = z.a(Process.myPid());
        processErrorStateInfo.shortMsg = str;
        return processErrorStateInfo;
    }
}

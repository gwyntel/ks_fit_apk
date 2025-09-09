package anet.channel.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.monitor.NetworkSpeed;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.status.NetworkStatusHelper;
import com.aliyun.alink.linksdk.alcs.coap.resources.LinkFormat;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.ta.utdid2.device.UTDevice;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class Utils {
    private static final String TAG = "awcn.Utils";
    public static Context context;

    public static Context getAppContext() {
        Context context2 = context;
        if (context2 != null) {
            return context2;
        }
        synchronized (Utils.class) {
            Context context3 = context;
            if (context3 != null) {
                return context3;
            }
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Object objInvoke = cls.getMethod("currentActivityThread", null).invoke(cls, null);
                context = (Context) objInvoke.getClass().getMethod("getApplication", null).invoke(objInvoke, null);
            } catch (Exception e2) {
                ALog.w(TAG, "getAppContext", null, e2, new Object[0]);
            }
            return context;
        }
    }

    public static String getDeviceId(Context context2) {
        return UTDevice.getUtdid(context2);
    }

    public static String getMainProcessName(Context context2) {
        if (context2 == null) {
            return "";
        }
        try {
            return context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0).applicationInfo.processName;
        } catch (PackageManager.NameNotFoundException unused) {
            return "";
        }
    }

    public static float getNetworkTimeFactor() {
        NetworkStatusHelper.NetworkStatus status = NetworkStatusHelper.getStatus();
        float f2 = (status == NetworkStatusHelper.NetworkStatus.G4 || status == NetworkStatusHelper.NetworkStatus.WIFI) ? 0.8f : 1.0f;
        return anet.channel.monitor.b.a().b() == NetworkSpeed.Fast.getCode() ? f2 * 0.75f : f2;
    }

    public static String getProcessName(Context context2, int i2) {
        String str = "";
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context2.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
            if (runningAppProcesses == null || runningAppProcesses.size() <= 0) {
                AppMonitor.getInstance().commitStat(new ExceptionStatistic(-108, ErrorConstant.formatMsg(-108, "BuildVersion=" + String.valueOf(Build.VERSION.SDK_INT)), LinkFormat.RESOURCE_TYPE));
            } else {
                Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                while (true) {
                    if (it.hasNext()) {
                        ActivityManager.RunningAppProcessInfo next = it.next();
                        if (next.pid == i2) {
                            str = next.processName;
                            break;
                        }
                    }
                }
            }
            break;
        } catch (Exception e2) {
            AppMonitor.getInstance().commitStat(new ExceptionStatistic(-108, e2.toString(), LinkFormat.RESOURCE_TYPE));
        }
        return TextUtils.isEmpty(str) ? getProcessNameNew(i2) : str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0087, code lost:
    
        r10 = r3[8];
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x008b, code lost:
    
        r7.close();
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0092, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0093, code lost:
    
        anet.channel.util.ALog.e(anet.channel.util.Utils.TAG, "getProcessNameNew ", null, r0, new java.lang.Object[0]);
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00dd A[Catch: IOException -> 0x00d9, TRY_LEAVE, TryCatch #0 {IOException -> 0x00d9, blocks: (B:48:0x00d5, B:52:0x00dd), top: B:56:0x00d5 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00d5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getProcessNameNew(int r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.Utils.getProcessNameNew(int):java.lang.String");
    }

    public static String getStackMsg(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString() + "\n");
                }
            }
        } catch (Exception e2) {
            ALog.e(TAG, "getStackMsg", null, e2, new Object[0]);
        }
        return stringBuffer.toString();
    }

    public static Object invokeStaticMethodThrowException(String str, String str2, Class<?>[] clsArr, Object... objArr) throws Exception {
        if (str == null || str2 == null) {
            return null;
        }
        Class<?> cls = Class.forName(str);
        Method declaredMethod = clsArr != null ? cls.getDeclaredMethod(str2, clsArr) : cls.getDeclaredMethod(str2, null);
        if (declaredMethod == null) {
            return null;
        }
        declaredMethod.setAccessible(true);
        return objArr != null ? declaredMethod.invoke(cls, objArr) : declaredMethod.invoke(cls, null);
    }
}

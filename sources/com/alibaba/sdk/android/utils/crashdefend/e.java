package com.alibaba.sdk.android.utils.crashdefend;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
class e {
    static void a(Context context, a aVar, List<c> list) {
        if (context == null) {
            return;
        }
        synchronized (list) {
            FileOutputStream fileOutputStreamOpenFileOutput = null;
            try {
                try {
                    JSONObject jSONObject = new JSONObject();
                    if (aVar != null) {
                        jSONObject.put("startSerialNumber", aVar.f8945a);
                    }
                    try {
                        JSONArray jSONArray = new JSONArray();
                        for (c cVar : list) {
                            if (cVar != null) {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("sdkId", cVar.f52a);
                                jSONObject2.put("sdkVersion", cVar.f54b);
                                jSONObject2.put("crashLimit", cVar.f8951a);
                                jSONObject2.put("crashCount", cVar.crashCount);
                                jSONObject2.put(HiAnalyticsConstant.HaKey.BI_KEY_WAITTIME, cVar.f8952b);
                                jSONObject2.put("registerSerialNumber", cVar.f53b);
                                jSONObject2.put("startSerialNumber", cVar.f50a);
                                jSONObject2.put("restoreCount", cVar.f8953c);
                                jSONArray.put(jSONObject2);
                            }
                        }
                        jSONObject.put("sdkList", jSONArray);
                    } catch (JSONException e2) {
                        Log.w("CrashUtils", "save sdk json fail:", e2);
                    }
                    String string = jSONObject.toString();
                    fileOutputStreamOpenFileOutput = m53a(context) ? context.openFileOutput("com_alibaba_aliyun_crash_defend_sdk_info", 0) : context.openFileOutput("com_alibaba_aliyun_crash_defend_sdk_info_" + a(context), 0);
                    fileOutputStreamOpenFileOutput.write(string.getBytes());
                } catch (IOException e3) {
                    Log.w("CrashUtils", "save sdk io fail:", e3);
                    if (fileOutputStreamOpenFileOutput != null) {
                    }
                } catch (Exception e4) {
                    Log.w("CrashUtils", "save sdk exception:", e4);
                    if (fileOutputStreamOpenFileOutput != null) {
                    }
                }
                try {
                    fileOutputStreamOpenFileOutput.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        }
    }

    private static String b(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null) {
            return "";
        }
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    private static String c(Context context) throws NoSuchMethodException, SecurityException {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, context.getClassLoader()).getDeclaredMethod("currentProcessName", null);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke(null, null);
        } catch (Exception e2) {
            Log.d("CrashUtils", "getProcessNameByActivityThread error: " + e2);
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    static boolean m54a(Context context, a aVar, List<c> list) {
        byte[] bArr;
        if (context == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        synchronized (list) {
            FileInputStream fileInputStreamOpenFileInput = null;
            try {
                try {
                    fileInputStreamOpenFileInput = m53a(context) ? context.openFileInput("com_alibaba_aliyun_crash_defend_sdk_info") : context.openFileInput("com_alibaba_aliyun_crash_defend_sdk_info_" + a(context));
                    bArr = new byte[512];
                } catch (FileNotFoundException e2) {
                    Log.d("CrashUtils", "load sdk file fail:" + e2.getMessage());
                    if (fileInputStreamOpenFileInput != null) {
                        break;
                    }
                } catch (IOException e3) {
                    Log.w("CrashUtils", "load sdk io fail:", e3);
                    if (fileInputStreamOpenFileInput != null) {
                    }
                } catch (Exception e4) {
                    Log.w("CrashUtils", "load sdk exception:", e4);
                    if (fileInputStreamOpenFileInput != null) {
                    }
                }
                while (true) {
                    int i2 = fileInputStreamOpenFileInput.read(bArr);
                    if (i2 != -1) {
                        sb.append(new String(bArr, 0, i2));
                    }
                    try {
                        break;
                    } catch (IOException unused) {
                    }
                }
                fileInputStreamOpenFileInput.close();
                if (sb.length() == 0) {
                    return false;
                }
                try {
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    aVar.f8945a = jSONObject.optLong("startSerialNumber", 1L);
                    JSONArray jSONArray = jSONObject.getJSONArray("sdkList");
                    for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i3);
                        if (jSONObject2 != null) {
                            c cVar = new c();
                            cVar.f52a = jSONObject2.optString("sdkId", "");
                            cVar.f54b = jSONObject2.optString("sdkVersion", "");
                            cVar.f8951a = jSONObject2.optInt("crashLimit", -1);
                            cVar.crashCount = jSONObject2.optInt("crashCount", 0);
                            cVar.f8952b = jSONObject2.optInt(HiAnalyticsConstant.HaKey.BI_KEY_WAITTIME, 0);
                            cVar.f53b = jSONObject2.optLong("registerSerialNumber", 0L);
                            cVar.f50a = jSONObject2.optLong("startSerialNumber", 0L);
                            cVar.f8953c = jSONObject2.optInt("restoreCount", 0);
                            if (!TextUtils.isEmpty(cVar.f52a)) {
                                list.add(cVar);
                            }
                        }
                    }
                } catch (JSONException e5) {
                    Log.w("CrashUtils", "load sdk json fail:", e5);
                } catch (Exception e6) {
                    Log.w("CrashUtils", "load sdk exception:", e6);
                }
                return true;
            } catch (Throwable th) {
                if (fileInputStreamOpenFileInput != null) {
                    try {
                        fileInputStreamOpenFileInput.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m53a(Context context) {
        return context.getPackageName().equalsIgnoreCase(a(context));
    }

    private static String a(Context context) throws Throwable {
        if (Build.VERSION.SDK_INT >= 28) {
            return Application.getProcessName();
        }
        String strC = c(context);
        if (!TextUtils.isEmpty(strC)) {
            return strC;
        }
        String strA = a();
        return !TextUtils.isEmpty(strA) ? strA : b(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0078 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a() throws java.lang.Throwable {
        /*
            int r0 = android.os.Process.myPid()
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            r3.<init>()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            java.lang.String r4 = "/proc/"
            r3.append(r4)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            r3.append(r0)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            java.lang.String r0 = "/cmdline"
            r3.append(r0)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            boolean r0 = r2.exists()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            if (r0 == 0) goto L48
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            java.io.FileReader r3 = new java.io.FileReader     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            r0.<init>(r3)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L45
            java.lang.String r2 = r0.readLine()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            java.lang.String r1 = r2.trim()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3e
            r6 = r1
            r1 = r0
            r0 = r6
            goto L49
        L3c:
            r1 = move-exception
            goto L76
        L3e:
            r2 = move-exception
            goto L55
        L40:
            r0 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L76
        L45:
            r2 = move-exception
            r0 = r1
            goto L55
        L48:
            r0 = r1
        L49:
            if (r1 == 0) goto L53
            r1.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r1 = move-exception
            r1.printStackTrace()
        L53:
            r1 = r0
            goto L75
        L55:
            java.lang.String r3 = "CrashUtils"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3c
            r4.<init>()     // Catch: java.lang.Throwable -> L3c
            java.lang.String r5 = "getProcessNameByPid error: "
            r4.append(r5)     // Catch: java.lang.Throwable -> L3c
            r4.append(r2)     // Catch: java.lang.Throwable -> L3c
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L3c
            android.util.Log.d(r3, r2)     // Catch: java.lang.Throwable -> L3c
            if (r0 == 0) goto L75
            r0.close()     // Catch: java.io.IOException -> L71
            goto L75
        L71:
            r0 = move-exception
            r0.printStackTrace()
        L75:
            return r1
        L76:
            if (r0 == 0) goto L80
            r0.close()     // Catch: java.io.IOException -> L7c
            goto L80
        L7c:
            r0 = move-exception
            r0.printStackTrace()
        L80:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.utils.crashdefend.e.a():java.lang.String");
    }
}

package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.coap.resources.LinkFormat;
import com.huawei.hms.framework.common.ContainerUtils;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class be {

    /* renamed from: a, reason: collision with root package name */
    private static List<File> f20947a = new ArrayList();

    protected static String a(String str) {
        if (str == null) {
            return "";
        }
        String[] strArrSplit = str.split("\n");
        if (strArrSplit == null || strArrSplit.length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArrSplit) {
            if (!str2.contains("java.lang.Thread.getStackTrace(")) {
                sb.append(str2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static String b(String str, String str2) throws IOException {
        BufferedReader bufferedReaderB = ap.b(str, "reg_record.txt");
        if (bufferedReaderB == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = bufferedReaderB.readLine();
            if (line != null && line.startsWith(str2)) {
                int i2 = 18;
                int i3 = 0;
                int length = 0;
                while (true) {
                    String line2 = bufferedReaderB.readLine();
                    if (line2 == null) {
                        break;
                    }
                    if (i3 % 4 == 0) {
                        if (i3 > 0) {
                            sb.append("\n");
                        }
                        sb.append("  ");
                    } else {
                        if (line2.length() > 16) {
                            i2 = 28;
                        }
                        sb.append("                ".substring(0, i2 - length));
                    }
                    length = line2.length();
                    sb.append(line2);
                    i3++;
                }
                sb.append("\n");
                return sb.toString();
            }
            try {
                bufferedReaderB.close();
            } catch (Exception e2) {
                al.a(e2);
            }
            return null;
        } catch (Throwable th) {
            try {
                al.a(th);
                try {
                    bufferedReaderB.close();
                } catch (Exception e3) {
                    al.a(e3);
                }
                return null;
            } finally {
                try {
                    bufferedReaderB.close();
                } catch (Exception e4) {
                    al.a(e4);
                }
            }
        }
    }

    private static String c(String str, String str2) throws IOException {
        BufferedReader bufferedReaderB = ap.b(str, "map_record.txt");
        if (bufferedReaderB == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = bufferedReaderB.readLine();
            if (line != null && line.startsWith(str2)) {
                while (true) {
                    String line2 = bufferedReaderB.readLine();
                    if (line2 == null) {
                        break;
                    }
                    sb.append("  ");
                    sb.append(line2);
                    sb.append("\n");
                }
                return sb.toString();
            }
            try {
                bufferedReaderB.close();
            } catch (Exception e2) {
                al.a(e2);
            }
            return null;
        } catch (Throwable th) {
            try {
                al.a(th);
                try {
                    bufferedReaderB.close();
                } catch (Exception e3) {
                    al.a(e3);
                }
                return null;
            } finally {
                try {
                    bufferedReaderB.close();
                } catch (Exception e4) {
                    al.a(e4);
                }
            }
        }
    }

    private static Map<String, Integer> d(String str) {
        if (str == null) {
            return null;
        }
        try {
            HashMap map = new HashMap();
            for (String str2 : str.split(",")) {
                String[] strArrSplit = str2.split(":");
                if (strArrSplit.length != 2) {
                    al.e("error format at %s", str2);
                    return null;
                }
                map.put(strArrSplit[0], Integer.valueOf(Integer.parseInt(strArrSplit[1])));
            }
            return map;
        } catch (Exception e2) {
            al.e("error format intStateStr %s", str);
            e2.printStackTrace();
            return null;
        }
    }

    private static <KeyT, ValueT> ValueT a(Map<KeyT, ValueT> map, KeyT keyt, ValueT valuet) {
        ValueT valuet2;
        try {
            valuet2 = map.get(keyt);
        } catch (Exception e2) {
            al.a(e2);
        }
        return valuet2 != null ? valuet2 : valuet;
    }

    private static String a(BufferedInputStream bufferedInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(1024);
            while (true) {
                try {
                    int i2 = bufferedInputStream.read();
                    if (i2 == -1) {
                        break;
                    }
                    if (i2 == 0) {
                        String str = new String(byteArrayOutputStream.toByteArray(), "UTf-8");
                        byteArrayOutputStream.close();
                        return str;
                    }
                    byteArrayOutputStream.write(i2);
                } catch (Throwable th) {
                    th = th;
                    try {
                        al.a(th);
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        return null;
                    } catch (Throwable th2) {
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        throw th2;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [boolean] */
    public static CrashDetailBean a(Context context, String str, NativeExceptionHandler nativeExceptionHandler) {
        BufferedInputStream bufferedInputStream;
        String str2;
        String strA;
        BufferedInputStream bufferedInputStream2 = null;
        if (context != null && str != null && nativeExceptionHandler != null) {
            File file = new File(str, "rqd_record.eup");
            if (file.exists()) {
                ?? CanRead = file.canRead();
                try {
                    if (CanRead != 0) {
                        try {
                            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                        } catch (IOException e2) {
                            e = e2;
                            bufferedInputStream = null;
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedInputStream2 != null) {
                                try {
                                    bufferedInputStream2.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                        try {
                            String strA2 = a(bufferedInputStream);
                            if (strA2 != null && strA2.equals("NATIVE_RQD_REPORT")) {
                                HashMap map = new HashMap();
                                loop0: while (true) {
                                    str2 = null;
                                    while (true) {
                                        strA = a(bufferedInputStream);
                                        if (strA == null) {
                                            break loop0;
                                        }
                                        if (str2 == null) {
                                            str2 = strA;
                                        }
                                    }
                                    map.put(str2, strA);
                                }
                                if (str2 != null) {
                                    al.e("record not pair! drop! %s", str2);
                                    try {
                                        bufferedInputStream.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                    return null;
                                }
                                CrashDetailBean crashDetailBeanA = a(context, map, nativeExceptionHandler);
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                                return crashDetailBeanA;
                            }
                            al.e("record read fail! %s", strA2);
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                            return null;
                        } catch (IOException e7) {
                            e = e7;
                            e.printStackTrace();
                            if (bufferedInputStream != null) {
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e8) {
                                    e8.printStackTrace();
                                }
                            }
                            return null;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream2 = CanRead;
                }
            }
            return null;
        }
        al.e("get eup record file args error", new Object[0]);
        return null;
    }

    public static void c(String str) {
        File[] fileArrListFiles;
        if (str == null) {
            return;
        }
        try {
            File file = new File(str);
            if (file.canRead() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.canRead() && file2.canWrite() && file2.length() == 0) {
                        file2.delete();
                        al.c("Delete empty record file %s", file2.getAbsoluteFile());
                    }
                }
            }
        } catch (Throwable th) {
            al.a(th);
        }
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str, "backup_record.txt");
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    private static long b(Map<String, String> map) {
        String str = map.get("launchTime");
        if (str == null) {
            return -1L;
        }
        al.c("[Native record info] launchTime: %s", str);
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e2) {
            if (al.a(e2)) {
                return -1L;
            }
            e2.printStackTrace();
            return -1L;
        }
    }

    public static String a(String str, String str2) throws IOException {
        if (str == null || str2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String strB = b(str, str2);
        if (strB != null && !strB.isEmpty()) {
            sb.append("Register infos:\n");
            sb.append(strB);
        }
        String strC = c(str, str2);
        if (strC != null && !strC.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append("System SO infos:\n");
            sb.append(strC);
        }
        return sb.toString();
    }

    public static void a(boolean z2, String str) {
        if (str != null) {
            f20947a.add(new File(str, "rqd_record.eup"));
            f20947a.add(new File(str, "reg_record.txt"));
            f20947a.add(new File(str, "map_record.txt"));
            f20947a.add(new File(str, "backup_record.txt"));
            if (z2) {
                c(str);
            }
        }
        List<File> list = f20947a;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (File file : f20947a) {
            if (file.exists() && file.canWrite()) {
                file.delete();
                al.c("Delete record file %s", file.getAbsoluteFile());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.lang.String] */
    public static String a(String str, int i2, String str2, boolean z2) throws IOException {
        BufferedReader bufferedReader = null;
        if (str != null && i2 > 0) {
            File file = new File(str);
            if (file.exists() && file.canRead()) {
                al.a("Read system log from native record file(length: %s bytes): %s", Long.valueOf(file.length()), file.getAbsolutePath());
                f20947a.add(file);
                al.c("Add this record file to list for cleaning lastly.", new Object[0]);
                if (str2 == null) {
                    return ap.a(new File(str), i2, z2);
                }
                String sb = new StringBuilder();
                try {
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                        while (true) {
                            try {
                                String line = bufferedReader2.readLine();
                                if (line == null) {
                                    break;
                                }
                                if (Pattern.compile(str2 + "[ ]*:").matcher(line).find()) {
                                    sb.append(line);
                                    sb.append("\n");
                                }
                                if (i2 > 0 && sb.length() > i2) {
                                    if (z2) {
                                        sb.delete(i2, sb.length());
                                        break;
                                    }
                                    sb.delete(0, sb.length() - i2);
                                }
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                try {
                                    al.a(th);
                                    sb.append("\n[error:" + th.toString() + "]");
                                    String string = sb.toString();
                                    if (bufferedReader == null) {
                                        return string;
                                    }
                                    bufferedReader.close();
                                    sb = string;
                                    return sb;
                                } catch (Throwable th2) {
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (Exception e2) {
                                            al.a(e2);
                                        }
                                    }
                                    throw th2;
                                }
                            }
                        }
                        String string2 = sb.toString();
                        bufferedReader2.close();
                        sb = string2;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                    return sb;
                } catch (Exception e3) {
                    al.a(e3);
                    return sb;
                }
            }
        }
        return null;
    }

    private static Map<String, String> a(Map<String, String> map) {
        String str = map.get("key-value");
        if (str == null) {
            return null;
        }
        HashMap map2 = new HashMap();
        for (String str2 : str.split("\n")) {
            String[] strArrSplit = str2.split(ContainerUtils.KEY_VALUE_DELIMITER);
            if (strArrSplit.length == 2) {
                map2.put(strArrSplit[0], strArrSplit[1]);
            }
        }
        return map2;
    }

    private static CrashDetailBean a(Context context, Map<String, String> map, NativeExceptionHandler nativeExceptionHandler) {
        String str;
        String str2;
        boolean zEqualsIgnoreCase;
        if (aa.a(context) == null) {
            al.e("abnormal com info not created", new Object[0]);
        } else {
            String str3 = map.get("intStateStr");
            if (str3 != null && str3.trim().length() > 0) {
                Map<String, Integer> mapD = d(map.get("intStateStr"));
                if (mapD == null) {
                    al.e("parse intSateMap fail", Integer.valueOf(map.size()));
                    return null;
                }
                try {
                    mapD.get("sino").intValue();
                    mapD.get("sud").intValue();
                    String str4 = map.get("soVersion");
                    if (!TextUtils.isEmpty(str4)) {
                        String str5 = (String) a(map, "codeMsg", "unknown");
                        String str6 = (String) a(map, "signalName", "unknown");
                        map.get("errnoMsg");
                        String str7 = (String) a(map, "stack", "unknown");
                        String str8 = map.get("jstack");
                        if (str8 != null) {
                            str7 = str7 + "java:\n" + str8;
                        }
                        Integer num = mapD.get("sico");
                        if (num == null || num.intValue() <= 0) {
                            str = str5;
                            str2 = str6;
                        } else {
                            str2 = str6 + "(" + str5 + ")";
                            str = "KERNEL";
                        }
                        String str9 = map.get("nativeLog");
                        byte[] bArrA = (str9 == null || str9.isEmpty()) ? null : ap.a(str9, "BuglyNativeLog.txt");
                        String str10 = (String) a(map, "sendingProcess", "unknown");
                        Integer num2 = mapD.get("spd");
                        if (num2 != null) {
                            str10 = str10 + "(" + num2 + ")";
                        }
                        String str11 = str10;
                        String str12 = (String) a(map, "threadName", "unknown");
                        Integer num3 = mapD.get("et");
                        if (num3 != null) {
                            str12 = str12 + "(" + num3 + ")";
                        }
                        String str13 = str12;
                        String str14 = (String) a(map, "processName", "unknown");
                        Integer num4 = mapD.get(LinkFormat.END_POINT);
                        if (num4 != null) {
                            str14 = str14 + "(" + num4 + ")";
                        }
                        CrashDetailBean crashDetailBeanPackageCrashDatas = nativeExceptionHandler.packageCrashDatas(str14, str13, (mapD.get("ets").intValue() * 1000) + (mapD.get("etms").intValue() / 1000), str2, (String) a(map, "errorAddr", "unknown"), a(str7), str, str11, (String) a(map, "tombPath", "unknown"), map.get("sysLogPath"), map.get("jniLogPath"), str4, bArrA, a(map), false, false);
                        if (crashDetailBeanPackageCrashDatas != null) {
                            String str15 = (String) a(map, "userId", crashDetailBeanPackageCrashDatas.f20632m);
                            crashDetailBeanPackageCrashDatas.f20632m = str15;
                            al.c("[Native record info] userId: %s", str15);
                            String str16 = (String) a(map, "sysLog", crashDetailBeanPackageCrashDatas.f20642w);
                            crashDetailBeanPackageCrashDatas.f20642w = str16;
                            String str17 = (String) a(map, "appVersion", str16);
                            crashDetailBeanPackageCrashDatas.f20625f = str17;
                            al.c("[Native record info] appVersion: %s", str17);
                            String str18 = map.get("isAppForeground");
                            if (str18 != null) {
                                al.c("[Native record info] isAppForeground: %s", str18);
                                zEqualsIgnoreCase = str18.equalsIgnoreCase("true");
                            } else {
                                zEqualsIgnoreCase = false;
                            }
                            crashDetailBeanPackageCrashDatas.R = zEqualsIgnoreCase;
                            crashDetailBeanPackageCrashDatas.Q = b(map);
                            crashDetailBeanPackageCrashDatas.f20645z = null;
                            crashDetailBeanPackageCrashDatas.f20630k = true;
                        }
                        return crashDetailBeanPackageCrashDatas;
                    }
                    al.e("error format at version", new Object[0]);
                    return null;
                } catch (Throwable th) {
                    al.e("error format", new Object[0]);
                    th.printStackTrace();
                    return null;
                }
            }
            al.e("no intStateStr", new Object[0]);
        }
        return null;
    }
}

package com.tencent.bugly.crashreport.crash.anr;

import com.huawei.hms.framework.common.ContainerUtils;
import com.tencent.bugly.proguard.al;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class TraceFileHelper {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public long f20651a;

        /* renamed from: b, reason: collision with root package name */
        public String f20652b;

        /* renamed from: c, reason: collision with root package name */
        public long f20653c;

        /* renamed from: d, reason: collision with root package name */
        public Map<String, String[]> f20654d;
    }

    public interface b {
        boolean a(long j2);

        boolean a(long j2, long j3, String str);

        boolean a(String str, int i2, String str2, String str3);
    }

    private static Object[] a(BufferedReader bufferedReader, Pattern... patternArr) throws IOException {
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return null;
            }
            for (Pattern pattern : patternArr) {
                if (pattern.matcher(line).matches()) {
                    return new Object[]{pattern, line};
                }
            }
        }
    }

    private static String b(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null || line.trim().length() <= 0) {
                break;
            }
            stringBuffer.append(line + "\n");
        }
        return stringBuffer.toString();
    }

    public static a readFirstDumpInfo(String str, final boolean z2) throws Throwable {
        if (str == null) {
            al.e("path:%s", str);
            return null;
        }
        final a aVar = new a();
        readTraceFile(str, new b() { // from class: com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.2
            @Override // com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.b
            public final boolean a(String str2, int i2, String str3, String str4) {
                al.c("new thread %s", str2);
                a aVar2 = aVar;
                if (aVar2.f20654d == null) {
                    aVar2.f20654d = new HashMap();
                }
                aVar.f20654d.put(str2, new String[]{str3, str4, String.valueOf(i2)});
                return true;
            }

            @Override // com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.b
            public final boolean a(long j2, long j3, String str2) {
                al.c("new process %s", str2);
                a aVar2 = aVar;
                aVar2.f20651a = j2;
                aVar2.f20652b = str2;
                aVar2.f20653c = j3;
                return z2;
            }

            @Override // com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.b
            public final boolean a(long j2) {
                al.c("process end %d", Long.valueOf(j2));
                return false;
            }
        });
        if (aVar.f20651a > 0 && aVar.f20653c > 0 && aVar.f20652b != null) {
            return aVar;
        }
        al.e("first dump error %s", aVar.f20651a + " " + aVar.f20653c + " " + aVar.f20652b);
        return null;
    }

    public static a readTargetDumpInfo(final String str, String str2, final boolean z2) throws Throwable {
        if (str != null && str2 != null) {
            final a aVar = new a();
            readTraceFile(str2, new b() { // from class: com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.1
                @Override // com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.b
                public final boolean a(String str3, int i2, String str4, String str5) {
                    al.c("new thread %s", str3);
                    a aVar2 = aVar;
                    if (aVar2.f20651a > 0 && aVar2.f20653c > 0 && aVar2.f20652b != null) {
                        if (aVar2.f20654d == null) {
                            aVar2.f20654d = new HashMap();
                        }
                        aVar.f20654d.put(str3, new String[]{str4, str5, String.valueOf(i2)});
                    }
                    return true;
                }

                @Override // com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.b
                public final boolean a(long j2, long j3, String str3) {
                    al.c("new process %s", str3);
                    if (!str3.equals(str)) {
                        return true;
                    }
                    a aVar2 = aVar;
                    aVar2.f20651a = j2;
                    aVar2.f20652b = str3;
                    aVar2.f20653c = j3;
                    return z2;
                }

                @Override // com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.b
                public final boolean a(long j2) {
                    al.c("process end %d", Long.valueOf(j2));
                    a aVar2 = aVar;
                    return aVar2.f20651a <= 0 || aVar2.f20653c <= 0 || aVar2.f20652b == null;
                }
            });
            if (aVar.f20651a > 0 && aVar.f20653c > 0 && aVar.f20652b != null) {
                return aVar;
            }
        }
        return null;
    }

    public static void readTraceFile(String str, b bVar) throws Throwable {
        Throwable th;
        int i2 = 0;
        if (str == null || bVar == null) {
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            return;
        }
        file.lastModified();
        file.length();
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                try {
                    Pattern patternCompile = Pattern.compile("-{5}\\spid\\s\\d+\\sat\\s\\d+-\\d+-\\d+\\s\\d{2}:\\d{2}:\\d{2}\\s-{5}");
                    Pattern patternCompile2 = Pattern.compile("-{5}\\send\\s\\d+\\s-{5}");
                    Pattern patternCompile3 = Pattern.compile("Cmd\\sline:\\s(\\S+)");
                    Pattern patternCompile4 = Pattern.compile("\".+\"\\s(daemon\\s){0,1}prio=\\d+\\stid=\\d+\\s.*");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss, Locale.US);
                    while (true) {
                        Pattern[] patternArr = new Pattern[1];
                        patternArr[i2] = patternCompile;
                        Object[] objArrA = a(bufferedReader2, patternArr);
                        if (objArrA == null) {
                            try {
                                bufferedReader2.close();
                                return;
                            } catch (IOException e2) {
                                if (al.a(e2)) {
                                    return;
                                }
                                e2.printStackTrace();
                                return;
                            }
                        }
                        Pattern[] patternArr2 = new Pattern[1];
                        patternArr2[i2] = patternCompile3;
                        Object[] objArrA2 = a(bufferedReader2, patternArr2);
                        if (objArrA2 == null) {
                            al.d("Failed to find process name.", new Object[i2]);
                            try {
                                bufferedReader2.close();
                                return;
                            } catch (IOException e3) {
                                if (al.a(e3)) {
                                    return;
                                }
                                e3.printStackTrace();
                                return;
                            }
                        }
                        String[] strArrSplit = objArrA[1].toString().split("\\s");
                        long j2 = Long.parseLong(strArrSplit[2]);
                        long time = simpleDateFormat.parse(strArrSplit[4] + " " + strArrSplit[5]).getTime();
                        Matcher matcher = patternCompile3.matcher(objArrA2[1].toString());
                        matcher.find();
                        matcher.group(1);
                        SimpleDateFormat simpleDateFormat2 = simpleDateFormat;
                        if (!bVar.a(j2, time, matcher.group(1))) {
                            try {
                                bufferedReader2.close();
                                return;
                            } catch (IOException e4) {
                                if (al.a(e4)) {
                                    return;
                                }
                                e4.printStackTrace();
                                return;
                            }
                        }
                        while (true) {
                            Object[] objArrA3 = a(bufferedReader2, patternCompile4, patternCompile2);
                            if (objArrA3 == null) {
                                break;
                            }
                            if (objArrA3[0] == patternCompile4) {
                                String string = objArrA3[1].toString();
                                Matcher matcher2 = Pattern.compile("\".+\"").matcher(string);
                                matcher2.find();
                                String strGroup = matcher2.group();
                                String strSubstring = strGroup.substring(1, strGroup.length() - 1);
                                string.contains("NATIVE");
                                Matcher matcher3 = Pattern.compile("tid=\\d+").matcher(string);
                                matcher3.find();
                                String strGroup2 = matcher3.group();
                                bVar.a(strSubstring, Integer.parseInt(strGroup2.substring(strGroup2.indexOf(ContainerUtils.KEY_VALUE_DELIMITER) + 1)), a(bufferedReader2), b(bufferedReader2));
                            } else if (!bVar.a(Long.parseLong(objArrA3[1].toString().split("\\s")[2]))) {
                                try {
                                    bufferedReader2.close();
                                    return;
                                } catch (IOException e5) {
                                    if (al.a(e5)) {
                                        return;
                                    }
                                    e5.printStackTrace();
                                    return;
                                }
                            }
                        }
                        simpleDateFormat = simpleDateFormat2;
                        i2 = 0;
                    }
                } catch (Exception e6) {
                    e = e6;
                    bufferedReader = bufferedReader2;
                    if (!al.a(e)) {
                        e.printStackTrace();
                    }
                    al.d("trace open fail:%s : %s", e.getClass().getName(), e.getMessage());
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e7) {
                            if (al.a(e7)) {
                                return;
                            }
                            e7.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader == null) {
                        throw th;
                    }
                    try {
                        bufferedReader.close();
                        throw th;
                    } catch (IOException e8) {
                        if (al.a(e8)) {
                            throw th;
                        }
                        e8.printStackTrace();
                        throw th;
                    }
                }
            } catch (Exception e9) {
                e = e9;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static String a(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < 3; i2++) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return null;
            }
            stringBuffer.append(line + "\n");
        }
        return stringBuffer.toString();
    }
}

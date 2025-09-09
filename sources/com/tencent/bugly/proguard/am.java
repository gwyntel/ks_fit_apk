package com.tencent.bugly.proguard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class am {
    public static boolean a(File file, String str, long j2, boolean z2) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, z2));
            boolean zA = a(bufferedWriter, str.toCharArray(), str.length(), file.length(), j2);
            bufferedWriter.close();
            return zA;
        } catch (Throwable th) {
            al.a(th);
            return false;
        }
    }

    private static List<File> b(String str, final String str2, final String str3, long j2) {
        ArrayList arrayList = new ArrayList();
        if (str2 == null || str3 == null) {
            al.d("prefix %s and/or postfix %s is null.", str2, str3);
            return arrayList;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return arrayList;
        }
        try {
            File[] fileArrListFiles = file.listFiles(new FilenameFilter() { // from class: com.tencent.bugly.proguard.am.1
                @Override // java.io.FilenameFilter
                public final boolean accept(File file2, String str4) {
                    return str4 != null && str4.startsWith(str2) && str4.endsWith(str3);
                }
            });
            if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                return a(fileArrListFiles, str2, str3, jCurrentTimeMillis - j2);
            }
            return arrayList;
        } catch (Throwable th) {
            al.a(th);
            return arrayList;
        }
    }

    private static boolean a(Writer writer, char[] cArr, int i2, long j2, long j3) throws IOException {
        if (j2 >= j3) {
            return false;
        }
        try {
            if ((i2 * 2) + j2 <= j3) {
                writer.write(cArr, 0, i2);
            } else {
                writer.write(cArr, 0, (int) ((j3 - j2) / 2));
            }
            writer.flush();
            return true;
        } catch (IOException e2) {
            al.a(e2);
            return false;
        }
    }

    public static void a(String str, String str2, String str3, long j2) {
        try {
            int i2 = 0;
            for (File file : b(str, str2, str3, j2)) {
                al.c("File %s is to be deleted.", file.getName());
                if (file.delete()) {
                    i2++;
                }
            }
            al.c("Number of overdue trace files that has deleted: ".concat(String.valueOf(i2)), new Object[0]);
        } catch (Throwable th) {
            al.a(th);
        }
    }

    private static List<File> a(File[] fileArr, String str, String str2, long j2) {
        ArrayList arrayList = new ArrayList();
        for (File file : fileArr) {
            long jA = a(file.getName(), str, str2);
            if (jA >= 0 && 0 <= jA && jA <= j2) {
                arrayList.add(file);
            }
        }
        return arrayList;
    }

    public static long a(String str, String str2, String str3) {
        if (str == null) {
            al.d("File name is null.", new Object[0]);
            return -1L;
        }
        try {
            if (str.startsWith(str2) && str.endsWith(str3)) {
                return Long.parseLong(str.substring(str2.length(), str.indexOf(str3)));
            }
        } catch (Throwable th) {
            al.a(th);
        }
        return -1L;
    }

    public static boolean a(String str, String str2, int i2) {
        boolean z2 = true;
        al.c("rqdp{  sv sd start} %s", str);
        if (str2 != null && str2.trim().length() > 0) {
            File file = new File(str);
            try {
                if (!file.exists()) {
                    if (file.getParentFile() != null) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                long j2 = i2;
                if (file.length() >= j2) {
                    z2 = false;
                }
                return a(file, str2, j2, z2);
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }
}

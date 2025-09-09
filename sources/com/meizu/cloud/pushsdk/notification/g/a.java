package com.meizu.cloud.pushsdk.notification.g;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: com.meizu.cloud.pushsdk.notification.g.a$a, reason: collision with other inner class name */
    class C0165a implements FileFilter {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f19757a;

        C0165a(String str) {
            this.f19757a = str;
        }

        @Override // java.io.FileFilter
        public boolean accept(File file) {
            try {
                return Long.valueOf(this.f19757a).longValue() > Long.valueOf(file.getName().split(Constants.ACCEPT_TIME_SEPARATOR_SERVER)[1]).longValue();
            } catch (Exception e2) {
                DebugLogger.e("FileUtil", "filters file error " + e2.getMessage());
                return true;
            }
        }
    }

    public static void a(String str, String str2) throws IOException {
        try {
            new File(str2).mkdirs();
            for (String str3 : new File(str).list()) {
                String str4 = File.separator;
                File file = str.endsWith(str4) ? new File(str + str3) : new File(str + str4 + str3);
                if (file.isFile()) {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    FileOutputStream fileOutputStream = new FileOutputStream(str2 + "/" + file.getName());
                    byte[] bArr = new byte[5120];
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i2);
                        }
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    fileInputStream.close();
                } else if (file.isDirectory()) {
                    a(str + "/" + str3, str2 + "/" + str3);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean b(String str) {
        if (!TextUtils.isEmpty(str) && !str.contains("../")) {
            File file = new File(str);
            if (file.isFile() && file.exists()) {
                return file.delete();
            }
        }
        return false;
    }

    public static String c(String str) throws IOException {
        BufferedReader bufferedReader;
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            bufferedReader = null;
        }
        if (bufferedReader == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                try {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        sb.append(line);
                    }
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            try {
                break;
            } catch (IOException e32) {
            }
        }
        return sb.toString();
    }

    public static boolean a(String str) {
        if (!TextUtils.isEmpty(str) && !str.contains("../")) {
            String str2 = File.separator;
            if (!str.endsWith(str2)) {
                str = str + str2;
            }
            File file = new File(str);
            if (file.exists() && file.isDirectory()) {
                boolean zA = true;
                for (File file2 : file.listFiles()) {
                    boolean zIsFile = file2.isFile();
                    String absolutePath = file2.getAbsolutePath();
                    if (zIsFile) {
                        zA = b(absolutePath);
                        if (!zA) {
                            break;
                        }
                    } else {
                        zA = a(absolutePath);
                        if (!zA) {
                            break;
                        }
                    }
                }
                if (zA) {
                    return file.delete();
                }
                return false;
            }
        }
        return false;
    }

    public static File[] b(String str, String str2) {
        File file = new File(str);
        return file.isDirectory() ? file.listFiles(new C0165a(str2)) : new File[0];
    }

    public static void c(String str, String str2) throws Throwable {
        IOException e2;
        BufferedWriter bufferedWriter = null;
        try {
            try {
                try {
                    File file = new File(str);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file));
                    try {
                        bufferedWriter2.write(str2.toCharArray());
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                    } catch (IOException e3) {
                        e2 = e3;
                        bufferedWriter = bufferedWriter2;
                        e2.printStackTrace();
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                    } catch (Throwable th) {
                        bufferedWriter = bufferedWriter2;
                        th = th;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e2 = e5;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e6) {
            e6.printStackTrace();
        }
    }
}

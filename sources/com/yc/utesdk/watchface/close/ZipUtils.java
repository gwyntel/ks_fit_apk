package com.yc.utesdk.watchface.close;

import com.yc.utesdk.log.LogUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* loaded from: classes4.dex */
public class ZipUtils {
    public static List<File> GetFileList(String str, boolean z2, boolean z3) throws IOException {
        File file;
        ArrayList arrayList = new ArrayList();
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                zipInputStream.close();
                return arrayList;
            }
            String name = nextEntry.getName();
            if (nextEntry.isDirectory()) {
                file = new File(name.substring(0, name.length() - 1));
                if (z2) {
                    arrayList.add(file);
                }
            } else {
                File file2 = new File(name);
                if (z3) {
                    file = file2;
                    arrayList.add(file);
                }
            }
        }
    }

    public static void UnZipFolder(String str, String str2) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                zipInputStream.close();
                return;
            }
            String name = nextEntry.getName();
            if (!new File(str2, name).getCanonicalPath().startsWith(str2)) {
                LogUtils.e("SecurityException");
            } else if (nextEntry.isDirectory()) {
                new File(str2 + File.separator + name.substring(0, name.length() - 1)).mkdirs();
            } else {
                File file = new File(str2 + File.separator + name);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = zipInputStream.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, i2);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
            }
        }
    }

    public static InputStream UpZip(String str, String str2) {
        ZipFile zipFile = new ZipFile(str);
        return zipFile.getInputStream(zipFile.getEntry(str2));
    }

    public static boolean fileIsExists(String str) {
        try {
            return new File(str).exists();
        } catch (Exception unused) {
            return false;
        }
    }

    public static void UnZipFolder(String str, String str2, String str3) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                zipInputStream.close();
                return;
            }
            if (nextEntry.isDirectory()) {
                str3 = str3.substring(0, str3.length() - 1);
                new File(str2 + File.separator + str3).mkdirs();
            } else {
                File file = new File(str2 + File.separator + str3);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = zipInputStream.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, i2);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
            }
        }
    }
}

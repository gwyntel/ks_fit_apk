package com.xiaomi.accountsdk.diagnosis.e;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes4.dex */
public class f {
    public static void a(File[] fileArr, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        for (File file2 : fileArr) {
            if (file2.exists() && file2.isFile()) {
                FileInputStream fileInputStream = new FileInputStream(file2);
                zipOutputStream.putNextEntry(new ZipEntry(file2.getName()));
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = fileInputStream.read(bArr);
                    if (i2 < 0) {
                        break;
                    } else {
                        zipOutputStream.write(bArr, 0, i2);
                    }
                }
                fileInputStream.close();
            }
        }
        zipOutputStream.close();
        fileOutputStream.close();
    }
}

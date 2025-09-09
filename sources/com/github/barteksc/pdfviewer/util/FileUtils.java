package com.github.barteksc.pdfviewer.util;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class FileUtils {
    private FileUtils() {
    }

    public static void copy(InputStream inputStream, File file) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = inputStream.read(bArr);
                    if (i2 == -1) {
                        try {
                            inputStream.close();
                            return;
                        } finally {
                            fileOutputStream2.close();
                        }
                    }
                    fileOutputStream2.write(bArr, 0, i2);
                }
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } finally {
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static File fileFromAsset(Context context, String str) throws Throwable {
        File file = new File(context.getCacheDir(), str + "-pdfview.pdf");
        if (str.contains("/")) {
            file.getParentFile().mkdirs();
        }
        copy(context.getAssets().open(str), file);
        return file;
    }
}

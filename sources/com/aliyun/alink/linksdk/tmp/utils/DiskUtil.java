package com.aliyun.alink.linksdk.tmp.utils;

import android.content.Context;
import android.os.Environment;
import com.aliyun.alink.linksdk.tools.ALog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class DiskUtil {
    private static final String TAG = "[Tmp]DiskUtil";

    public static String getDiskCacheDir(Context context) {
        return ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) ? context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
    }

    public static File getDiskCacheDirWithAppend(Context context, String str) {
        return new File(getDiskCacheDir(context) + File.separator + str);
    }

    public static String readFully(Reader reader) throws IOException {
        try {
            StringWriter stringWriter = new StringWriter();
            char[] cArr = new char[1024];
            while (true) {
                int i2 = reader.read(cArr);
                if (i2 == -1) {
                    String string = stringWriter.toString();
                    reader.close();
                    return string;
                }
                stringWriter.write(cArr, 0, i2);
            }
        } catch (Throwable th) {
            reader.close();
            throw th;
        }
    }

    public static String readFully(InputStream inputStream) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = inputStream.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
                String string = byteArrayOutputStream.toString("UTF-8");
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    ALog.e(TAG, "readFully close:" + e2.toString());
                }
                return string;
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (Exception e3) {
                    ALog.e(TAG, "readFully close:" + e3.toString());
                }
                throw th;
            }
        } catch (Exception e4) {
            ALog.e(TAG, "readFully InputStream:" + e4.toString());
            try {
                inputStream.close();
                return null;
            } catch (Exception e5) {
                ALog.e(TAG, "readFully close:" + e5.toString());
                return null;
            }
        }
    }
}

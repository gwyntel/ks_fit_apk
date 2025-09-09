package com.xiaomi.infra.galaxy.fds.android.util;

import android.webkit.MimeTypeMap;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.android.model.FDSObject;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class Util {
    private static final int BUFFER_SIZE = 4096;
    private static final ThreadLocal<SimpleDateFormat> DATE_FOPMAT = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.infra.galaxy.fds.android.util.Util.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat;
        }
    };

    public static void downloadObjectToFile(FDSObject fDSObject, File file, boolean z2) throws Throwable {
        File parentFile = file.getParentFile();
        if (!z2 && parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        byte[] bArr = new byte[4096];
        InputStream objectContent = fDSObject.getObjectContent();
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file, z2));
                while (true) {
                    try {
                        int i2 = objectContent.read(bArr, 0, 4096);
                        if (i2 == -1) {
                            try {
                                objectContent.close();
                                bufferedOutputStream2.close();
                                return;
                            } catch (IOException unused) {
                                return;
                            }
                        }
                        bufferedOutputStream2.write(bArr, 0, i2);
                    } catch (IOException e2) {
                        e = e2;
                        bufferedOutputStream = bufferedOutputStream2;
                        throw new GalaxyFDSClientException("Unable to store object[" + fDSObject.getBucketName() + "/" + fDSObject.getObjectName() + "] content to disk:" + e.getMessage(), e);
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream = bufferedOutputStream2;
                        try {
                            objectContent.close();
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                        } catch (IOException unused2) {
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    public static String formatDateString(Date date) {
        return DATE_FOPMAT.get().format(date);
    }

    public static String getMimeType(File file) {
        int i2;
        String name = file.getName();
        int iLastIndexOf = name.lastIndexOf(".");
        if (iLastIndexOf > 0 && (i2 = iLastIndexOf + 1) < name.length()) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(i2));
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension;
            }
        }
        return Consts.APPLICATION_OCTET_STREAM;
    }

    public static String getStackTrace(Exception exc) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exc.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static Date parseDate(String str) throws ParseException {
        return DATE_FOPMAT.get().parse(str);
    }
}

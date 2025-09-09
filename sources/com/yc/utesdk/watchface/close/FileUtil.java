package com.yc.utesdk.watchface.close;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class FileUtil {
    private String SDPATH;

    public FileUtil() {
        this.SDPATH = "";
    }

    public File createDir(String str) {
        File file = new File(this.SDPATH + str);
        file.mkdir();
        return file;
    }

    public File createSDFile(String str) throws IOException {
        File file = new File(this.SDPATH + str);
        file.createNewFile();
        return file;
    }

    public void deleteFile(String str) {
        File file = new File(str);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public String getSDPATH() {
        return this.SDPATH;
    }

    public boolean isFileExist(String str) {
        return new File(this.SDPATH + str).exists();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.io.FileOutputStream, java.io.OutputStream] */
    public File write2SDFromInput(String str, String str2, InputStream inputStream) throws Throwable {
        ?? fileOutputStream;
        File fileCreateSDFile;
        byte[] bArr = null;
         = 0;
        ?? r02 = 0;
        bArr = null;
        try {
            try {
                try {
                    createDir(str);
                    fileCreateSDFile = createSDFile(((String) str) + str2);
                    try {
                        fileOutputStream = new FileOutputStream(fileCreateSDFile);
                        try {
                            byte[] bArr2 = new byte[4096];
                            while (inputStream.read(bArr2) != -1) {
                                fileOutputStream.write(bArr2);
                                fileOutputStream.flush();
                            }
                            fileOutputStream.close();
                            bArr = bArr2;
                            str = fileCreateSDFile;
                        } catch (IOException e2) {
                            e = e2;
                            r02 = fileOutputStream;
                            e.printStackTrace();
                            r02.close();
                            bArr = r02;
                            str = fileCreateSDFile;
                            return str;
                        } catch (Throwable th) {
                            th = th;
                            try {
                                fileOutputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                            throw th;
                        }
                    } catch (IOException e4) {
                        e = e4;
                    }
                } catch (IOException e5) {
                    e = e5;
                    fileCreateSDFile = null;
                }
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            return str;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = bArr;
        }
    }

    public FileUtil(String str) {
        this.SDPATH = str;
    }
}

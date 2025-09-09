package com.alibaba.ailabs.tg.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.tg.storage.IOUtils;
import com.alibaba.ailabs.tg.utils.constant.RegexConstants;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class FileUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");

    public interface OnReplaceListener {
        boolean onReplace();
    }

    public static String checkFileInDirWithMd5(String str, String str2) {
        File[] fileArrListFiles;
        if (!TextUtils.isEmpty(str2) && str != null && (fileArrListFiles = new File(str).listFiles()) != null && fileArrListFiles.length != 0) {
            for (File file : fileArrListFiles) {
                if (file != null && file.exists() && file.isFile() && str2.equalsIgnoreCase(EncryptUtils.md5(file))) {
                    return file.getAbsolutePath();
                }
            }
        }
        return null;
    }

    public static boolean copyDir(String str, String str2, OnReplaceListener onReplaceListener) {
        return copyDir(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean copyFile(String str, String str2, OnReplaceListener onReplaceListener) {
        return copyFile(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    private static boolean copyOrMoveDir(File file, File file2, OnReplaceListener onReplaceListener, boolean z2) {
        if (file == null || file2 == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file.getPath());
        String str = File.separator;
        sb.append(str);
        String string = sb.toString();
        String str2 = file2.getPath() + str;
        if (str2.contains(string) || !file.exists() || !file.isDirectory()) {
            return false;
        }
        if (file2.exists()) {
            if (!onReplaceListener.onReplace()) {
                return true;
            }
            if (!deleteAllInDir(file2)) {
                return false;
            }
        }
        if (!createOrExistsDir(file2)) {
            return false;
        }
        for (File file3 : file.listFiles()) {
            File file4 = new File(str2 + file3.getName());
            if (file3.isFile()) {
                if (!copyOrMoveFile(file3, file4, onReplaceListener, z2)) {
                    return false;
                }
            } else if (file3.isDirectory() && !copyOrMoveDir(file3, file4, onReplaceListener, z2)) {
                return false;
            }
        }
        return !z2 || deleteDir(file);
    }

    private static boolean copyOrMoveFile(File file, File file2, OnReplaceListener onReplaceListener, boolean z2) {
        if (file != null && file2 != null && !file.equals(file2) && file.exists() && file.isFile()) {
            if (file2.exists()) {
                if (!onReplaceListener.onReplace()) {
                    return true;
                }
                if (!file2.delete()) {
                    return false;
                }
            }
            if (!createOrExistsDir(file2.getParentFile())) {
                return false;
            }
            try {
                if (!IOUtils.writeFileFromInputStream(file2, new FileInputStream(file), false)) {
                    return false;
                }
                if (z2) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                }
                return true;
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    public static boolean createFileByDeleteOldFile(String str) {
        return createFileByDeleteOldFile(getFileByPath(str));
    }

    public static boolean createOrExistsDir(String str) {
        return createOrExistsDir(getFileByPath(str));
    }

    public static boolean createOrExistsFile(String str) {
        return createOrExistsFile(getFileByPath(str));
    }

    public static boolean deleteAllInDir(String str) {
        return deleteAllInDir(getFileByPath(str));
    }

    public static boolean deleteDir(String str) {
        return deleteDir(getFileByPath(str));
    }

    public static boolean deleteFile(String str) {
        return deleteFile(getFileByPath(str));
    }

    public static boolean deleteFilesInDir(String str) {
        return deleteFilesInDir(getFileByPath(str));
    }

    public static boolean deleteFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return deleteFilesInDirWithFilter(getFileByPath(str), fileFilter);
    }

    public static long getDirLength(String str) {
        return getDirLength(getFileByPath(str));
    }

    public static String getDirName(File file) {
        if (file == null) {
            return null;
        }
        return getDirName(file.getAbsolutePath());
    }

    public static String getDirSize(String str) {
        return getDirSize(getFileByPath(str));
    }

    public static String getExternalPath(Context context, @NonNull String str) {
        File externalFilesDir;
        if (context == null || (externalFilesDir = context.getExternalFilesDir(str)) == null || !externalFilesDir.isDirectory()) {
            return null;
        }
        return externalFilesDir.getAbsolutePath();
    }

    public static File getFileByPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return new File(str);
    }

    public static String getFileCharsetSimple(String str) {
        return getFileCharsetSimple(getFileByPath(str));
    }

    public static String getFileExtension(File file) {
        if (file == null) {
            return null;
        }
        return getFileExtension(file.getPath());
    }

    public static long getFileLastModified(String str) {
        return getFileLastModified(getFileByPath(str));
    }

    public static long getFileLength(String str) throws IOException {
        if (str.matches(RegexConstants.REGEX_URL)) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    return httpURLConnection.getContentLength();
                }
                return -1L;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return getFileLength(getFileByPath(str));
    }

    public static int getFileLines(String str) {
        return getFileLines(getFileByPath(str));
    }

    public static byte[] getFileMD5(String str) {
        return EncryptUtils.encryptMD5File(getFileByPath(str));
    }

    public static String getFileMD5ToString(String str) {
        return getFileMD5ToString(TextUtils.isEmpty(str) ? null : new File(str));
    }

    public static String getFileName(File file) {
        if (file == null) {
            return null;
        }
        return getFileName(file.getAbsolutePath());
    }

    public static String getFileNameNoExtension(File file) {
        if (file == null) {
            return null;
        }
        return getFileNameNoExtension(file.getPath());
    }

    public static String getFileSize(String str) throws IOException {
        long fileLength = getFileLength(str);
        return fileLength == -1 ? "" : ConvertUtils.byte2FitMemorySize(fileLength);
    }

    public static String getPath(Context context, @NonNull String str) {
        if (context == null) {
            return null;
        }
        File file = new File(context.getFilesDir(), str);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static boolean isDir(String str) {
        return isDir(getFileByPath(str));
    }

    public static boolean isFile(String str) {
        return isFile(getFileByPath(str));
    }

    public static boolean isFileExists(String str) {
        return isFileExists(getFileByPath(str));
    }

    public static List<File> listFilesInDir(String str) {
        return listFilesInDir(str, false);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, false);
    }

    public static boolean moveDir(String str, String str2, OnReplaceListener onReplaceListener) {
        return moveDir(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean moveFile(String str, String str2, OnReplaceListener onReplaceListener) {
        return moveFile(getFileByPath(str), getFileByPath(str2), onReplaceListener);
    }

    public static boolean rename(String str, String str2) {
        return rename(getFileByPath(str), str2);
    }

    public static void saveBitmap(File file, Bitmap bitmap, Bitmap.CompressFormat compressFormat) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    bitmap.compress(compressFormat, 100, fileOutputStream2);
                    fileOutputStream2.flush();
                    IOUtils.closeQuietly(fileOutputStream2);
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    IOUtils.closeQuietly(fileOutputStream);
                } catch (IOException e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    IOUtils.closeQuietly(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    IOUtils.closeQuietly(fileOutputStream);
                    throw th;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
            } catch (IOException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean copyDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveDir(file, file2, onReplaceListener, false);
    }

    public static boolean copyFile(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveFile(file, file2, onReplaceListener, false);
    }

    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) {
            return false;
        }
        if ((file.exists() && !file.delete()) || !createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAllInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: com.alibaba.ailabs.tg.utils.FileUtils.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        });
    }

    public static boolean deleteDir(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (file2.isFile()) {
                    if (!file2.delete()) {
                        return false;
                    }
                } else if (file2.isDirectory() && !deleteDir(file2)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || (file.isFile() && file.delete()));
    }

    public static boolean deleteFilesInDir(File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() { // from class: com.alibaba.ailabs.tg.utils.FileUtils.2
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return file2.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(File file, FileFilter fileFilter) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (fileFilter.accept(file2)) {
                    if (file2.isFile()) {
                        if (!file2.delete()) {
                            return false;
                        }
                    } else if (file2.isDirectory() && !deleteDir(file2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static long getDirLength(File file) {
        if (!isDir(file)) {
            return -1L;
        }
        File[] fileArrListFiles = file.listFiles();
        long dirLength = 0;
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                dirLength += file2.isDirectory() ? getDirLength(file2) : file2.length();
            }
        }
        return dirLength;
    }

    public static String getDirName(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(File.separator);
        return iLastIndexOf == -1 ? "" : str.substring(0, iLastIndexOf + 1);
    }

    public static String getDirSize(File file) {
        long dirLength = getDirLength(file);
        return dirLength == -1 ? "" : ConvertUtils.byte2FitMemorySize(dirLength);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0042 A[RETURN] */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getFileCharsetSimple(java.io.File r3) throws java.lang.Throwable {
        /*
            r0 = 0
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L22
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L22
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L22
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L20 java.io.IOException -> L22
            int r3 = r1.read()     // Catch: java.lang.Throwable -> L1a java.io.IOException -> L1d
            int r3 = r3 << 8
            int r0 = r1.read()     // Catch: java.lang.Throwable -> L1a java.io.IOException -> L1d
            int r3 = r3 + r0
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r1)
            goto L2a
        L1a:
            r3 = move-exception
            r0 = r1
            goto L45
        L1d:
            r3 = move-exception
            r0 = r1
            goto L23
        L20:
            r3 = move-exception
            goto L45
        L22:
            r3 = move-exception
        L23:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L20
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r0)
            r3 = 0
        L2a:
            r0 = 61371(0xefbb, float:8.5999E-41)
            if (r3 == r0) goto L42
            r0 = 65279(0xfeff, float:9.1475E-41)
            if (r3 == r0) goto L3f
            r0 = 65534(0xfffe, float:9.1833E-41)
            if (r3 == r0) goto L3c
            java.lang.String r3 = "GBK"
            return r3
        L3c:
            java.lang.String r3 = "Unicode"
            return r3
        L3f:
            java.lang.String r3 = "UTF-16BE"
            return r3
        L42:
            java.lang.String r3 = "UTF-8"
            return r3
        L45:
            com.alibaba.ailabs.tg.storage.IOUtils.closeQuietly(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.tg.utils.FileUtils.getFileCharsetSimple(java.io.File):java.lang.String");
    }

    public static String getFileExtension(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return (iLastIndexOf == -1 || str.lastIndexOf(File.separator) >= iLastIndexOf) ? "" : str.substring(iLastIndexOf + 1);
    }

    public static long getFileLastModified(File file) {
        if (file == null) {
            return -1L;
        }
        return file.lastModified();
    }

    public static int getFileLines(File file) throws Throwable {
        int i2 = 1;
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
                try {
                    byte[] bArr = new byte[1024];
                    if (!LINE_SEP.endsWith("\n")) {
                        while (true) {
                            int i3 = bufferedInputStream2.read(bArr, 0, 1024);
                            if (i3 == -1) {
                                break;
                            }
                            for (int i4 = 0; i4 < i3; i4++) {
                                if (bArr[i4] == 13) {
                                    i2++;
                                }
                            }
                        }
                    } else {
                        while (true) {
                            int i5 = bufferedInputStream2.read(bArr, 0, 1024);
                            if (i5 == -1) {
                                break;
                            }
                            for (int i6 = 0; i6 < i5; i6++) {
                                if (bArr[i6] == 10) {
                                    i2++;
                                }
                            }
                        }
                    }
                    IOUtils.closeQuietly(bufferedInputStream2);
                } catch (IOException e2) {
                    e = e2;
                    bufferedInputStream = bufferedInputStream2;
                    e.printStackTrace();
                    IOUtils.closeQuietly(bufferedInputStream);
                    return i2;
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    IOUtils.closeQuietly(bufferedInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        }
        return i2;
    }

    public static String getFileName(String str) {
        int iLastIndexOf;
        return (TextUtils.isEmpty(str) || (iLastIndexOf = str.lastIndexOf(File.separator)) == -1) ? str : str.substring(iLastIndexOf + 1);
    }

    public static String getFileNameNoExtension(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iLastIndexOf2 = str.lastIndexOf(File.separator);
        return iLastIndexOf2 == -1 ? iLastIndexOf == -1 ? str : str.substring(0, iLastIndexOf) : (iLastIndexOf == -1 || iLastIndexOf2 > iLastIndexOf) ? str.substring(iLastIndexOf2 + 1) : str.substring(iLastIndexOf2 + 1, iLastIndexOf);
    }

    public static boolean isDir(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    public static List<File> listFilesInDir(File file) {
        return listFilesInDir(file, false);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter) {
        return listFilesInDirWithFilter(file, fileFilter, false);
    }

    public static boolean moveDir(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveDir(file, file2, onReplaceListener, true);
    }

    public static boolean moveFile(File file, File file2, OnReplaceListener onReplaceListener) {
        return copyOrMoveFile(file, file2, onReplaceListener, true);
    }

    public static boolean rename(File file, String str) {
        if (file == null || !file.exists() || TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.equals(file.getName())) {
            return true;
        }
        File file2 = new File(file.getParent() + File.separator + str);
        return !file2.exists() && file.renameTo(file2);
    }

    public static String getFileMD5ToString(File file) {
        return ConvertUtils.bytes2HexString(EncryptUtils.encryptMD5File(file));
    }

    public static String getFileSize(File file) {
        long fileLength = getFileLength(file);
        return fileLength == -1 ? "" : ConvertUtils.byte2FitMemorySize(fileLength);
    }

    public static List<File> listFilesInDir(String str, boolean z2) {
        return listFilesInDir(getFileByPath(str), z2);
    }

    public static List<File> listFilesInDirWithFilter(String str, FileFilter fileFilter, boolean z2) {
        return listFilesInDirWithFilter(getFileByPath(str), fileFilter, z2);
    }

    public static List<File> listFilesInDir(File file, boolean z2) {
        return listFilesInDirWithFilter(file, new FileFilter() { // from class: com.alibaba.ailabs.tg.utils.FileUtils.3
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return true;
            }
        }, z2);
    }

    public static List<File> listFilesInDirWithFilter(File file, FileFilter fileFilter, boolean z2) {
        if (!isDir(file)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (fileFilter.accept(file2)) {
                    arrayList.add(file2);
                }
                if (z2 && file2.isDirectory()) {
                    arrayList.addAll(listFilesInDirWithFilter(file2, fileFilter, true));
                }
            }
        }
        return arrayList;
    }

    public static long getFileLength(File file) {
        if (isFile(file)) {
            return file.length();
        }
        return -1L;
    }
}

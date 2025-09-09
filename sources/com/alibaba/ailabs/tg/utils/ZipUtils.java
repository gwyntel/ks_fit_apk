package com.alibaba.ailabs.tg.utils;

import android.text.TextUtils;
import com.alibaba.ailabs.tg.storage.IOUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* loaded from: classes2.dex */
public final class ZipUtils {
    private static final int BUFFER_LEN = 8192;

    private ZipUtils() {
    }

    public static List<String> getComments(String str) throws IOException {
        return getComments(FileUtils.getFileByPath(str));
    }

    public static List<String> getFilesPath(String str) throws IOException {
        return getFilesPath(FileUtils.getFileByPath(str));
    }

    private static boolean unzipChildFile(File file, List<File> list, ZipFile zipFile, ZipEntry zipEntry, String str) throws Throwable {
        BufferedInputStream bufferedInputStream;
        Throwable th;
        BufferedOutputStream bufferedOutputStream;
        File file2 = new File(file + File.separator + str);
        list.add(file2);
        if (zipEntry.isDirectory()) {
            if (!FileUtils.createOrExistsDir(file2)) {
                return false;
            }
        } else {
            if (!FileUtils.createOrExistsFile(file2)) {
                return false;
            }
            try {
                bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                try {
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                    try {
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int i2 = bufferedInputStream.read(bArr);
                            if (i2 == -1) {
                                break;
                            }
                            bufferedOutputStream.write(bArr, 0, i2);
                        }
                        IOUtils.closeQuietly(bufferedInputStream, bufferedOutputStream);
                    } catch (Throwable th2) {
                        th = th2;
                        IOUtils.closeQuietly(bufferedInputStream, bufferedOutputStream);
                        throw th;
                    }
                } catch (Throwable th3) {
                    bufferedOutputStream = null;
                    th = th3;
                }
            } catch (Throwable th4) {
                bufferedInputStream = null;
                th = th4;
                bufferedOutputStream = null;
            }
        }
        return true;
    }

    public static List<File> unzipFile(String str, String str2) throws IOException {
        return unzipFileByKeyword(str, str2, (String) null);
    }

    public static List<File> unzipFileByKeyword(String str, String str2, String str3) throws IOException {
        return unzipFileByKeyword(FileUtils.getFileByPath(str), FileUtils.getFileByPath(str2), str3);
    }

    public static boolean zipFile(String str, String str2) throws IOException {
        return zipFile(FileUtils.getFileByPath(str), FileUtils.getFileByPath(str2), (String) null);
    }

    public static boolean zipFiles(Collection<String> collection, String str) throws IOException {
        return zipFiles(collection, str, (String) null);
    }

    public static List<String> getComments(File file) throws IOException {
        if (file == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Enumeration<? extends ZipEntry> enumerationEntries = new ZipFile(file).entries();
        while (enumerationEntries.hasMoreElements()) {
            arrayList.add(enumerationEntries.nextElement().getComment());
        }
        return arrayList;
    }

    public static List<String> getFilesPath(File file) throws IOException {
        if (file == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Enumeration<? extends ZipEntry> enumerationEntries = new ZipFile(file).entries();
        while (enumerationEntries.hasMoreElements()) {
            arrayList.add(enumerationEntries.nextElement().getName());
        }
        return arrayList;
    }

    public static List<File> unzipFile(File file, File file2) throws IOException {
        return unzipFileByKeyword(file, file2, (String) null);
    }

    public static List<File> unzipFileByKeyword(File file, File file2, String str) throws IOException {
        if (file == null || file2 == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
        if (!TextUtils.isEmpty(str)) {
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                String name = zipEntryNextElement.getName();
                if (name.contains(str) && !unzipChildFile(file2, arrayList, zipFile, zipEntryNextElement, name)) {
                    break;
                }
            }
        } else {
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement2 = enumerationEntries.nextElement();
                if (!unzipChildFile(file2, arrayList, zipFile, zipEntryNextElement2, zipEntryNextElement2.getName())) {
                    return arrayList;
                }
            }
        }
        return arrayList;
    }

    public static boolean zipFile(String str, String str2, String str3) throws IOException {
        return zipFile(FileUtils.getFileByPath(str), FileUtils.getFileByPath(str2), str3);
    }

    public static boolean zipFiles(Collection<String> collection, String str, String str2) throws Throwable {
        if (collection == null || str == null) {
            return false;
        }
        ZipOutputStream zipOutputStream = null;
        try {
            ZipOutputStream zipOutputStream2 = new ZipOutputStream(new FileOutputStream(str));
            try {
                Iterator<String> it = collection.iterator();
                while (it.hasNext()) {
                    File fileByPath = FileUtils.getFileByPath(it.next());
                    if (fileByPath != null && !zipFile(fileByPath, "", zipOutputStream2, str2)) {
                        zipOutputStream2.finish();
                        IOUtils.closeQuietly(zipOutputStream2);
                        return false;
                    }
                }
                zipOutputStream2.finish();
                IOUtils.closeQuietly(zipOutputStream2);
                return true;
            } catch (Throwable th) {
                th = th;
                zipOutputStream = zipOutputStream2;
                if (zipOutputStream != null) {
                    zipOutputStream.finish();
                    IOUtils.closeQuietly(zipOutputStream);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean zipFile(File file, File file2) throws IOException {
        return zipFile(file, file2, (String) null);
    }

    public static boolean zipFile(File file, File file2, String str) throws Throwable {
        ZipOutputStream zipOutputStream;
        if (file == null || file2 == null) {
            return false;
        }
        ZipOutputStream zipOutputStream2 = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(file2));
        } catch (Throwable th) {
            th = th;
        }
        try {
            boolean zZipFile = zipFile(file, "", zipOutputStream, str);
            IOUtils.closeQuietly(zipOutputStream);
            return zZipFile;
        } catch (Throwable th2) {
            th = th2;
            zipOutputStream2 = zipOutputStream;
            if (zipOutputStream2 != null) {
                IOUtils.closeQuietly(zipOutputStream2);
            }
            throw th;
        }
    }

    private static boolean zipFile(File file, String str, ZipOutputStream zipOutputStream, String str2) throws Throwable {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(TextUtils.isEmpty(str) ? "" : File.separator);
        sb.append(file.getName());
        String string = sb.toString();
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                for (File file2 : fileArrListFiles) {
                    if (!zipFile(file2, string, zipOutputStream, str2)) {
                        return false;
                    }
                }
                return true;
            }
            ZipEntry zipEntry = new ZipEntry(string + org.apache.commons.io.IOUtils.DIR_SEPARATOR_UNIX);
            zipEntry.setComment(str2);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.closeEntry();
            return true;
        }
        BufferedInputStream bufferedInputStream = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
            try {
                ZipEntry zipEntry2 = new ZipEntry(string);
                zipEntry2.setComment(str2);
                zipOutputStream.putNextEntry(zipEntry2);
                byte[] bArr = new byte[8192];
                while (true) {
                    int i2 = bufferedInputStream2.read(bArr, 0, 8192);
                    if (i2 != -1) {
                        zipOutputStream.write(bArr, 0, i2);
                    } else {
                        zipOutputStream.closeEntry();
                        IOUtils.closeQuietly(bufferedInputStream2);
                        return true;
                    }
                }
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                IOUtils.closeQuietly(bufferedInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean zipFiles(Collection<File> collection, File file) throws IOException {
        return zipFiles(collection, file, (String) null);
    }

    public static boolean zipFiles(Collection<File> collection, File file, String str) throws Throwable {
        ZipOutputStream zipOutputStream;
        if (collection == null || file == null) {
            return false;
        }
        ZipOutputStream zipOutputStream2 = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
        } catch (Throwable th) {
            th = th;
        }
        try {
            Iterator<File> it = collection.iterator();
            while (it.hasNext()) {
                if (!zipFile(it.next(), "", zipOutputStream, str)) {
                    zipOutputStream.finish();
                    IOUtils.closeQuietly(zipOutputStream);
                    return false;
                }
            }
            zipOutputStream.finish();
            IOUtils.closeQuietly(zipOutputStream);
            return true;
        } catch (Throwable th2) {
            th = th2;
            zipOutputStream2 = zipOutputStream;
            if (zipOutputStream2 != null) {
                zipOutputStream2.finish();
                IOUtils.closeQuietly(zipOutputStream2);
            }
            throw th;
        }
    }
}

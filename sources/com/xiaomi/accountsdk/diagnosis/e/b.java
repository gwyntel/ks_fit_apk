package com.xiaomi.accountsdk.diagnosis.e;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes4.dex */
public class b {
    public static void a(File file) throws IOException {
        IOException e2 = null;
        for (File file2 : f(file)) {
            try {
                e(file2);
            } catch (IOException e3) {
                e2 = e3;
            }
        }
        if (e2 != null) {
            throw e2;
        }
    }

    public static boolean b(File file) {
        if (file != null) {
            return c(file);
        }
        throw new NullPointerException("File must not be null");
    }

    public static boolean c(File file) {
        return a(file.getParentFile(), file.getName());
    }

    public static void d(File file) throws IOException {
        if (file.exists()) {
            if (!b(file)) {
                a(file);
            }
            if (file.delete()) {
                return;
            }
            throw new IOException("Unable to delete directory " + file + ".");
        }
    }

    public static void e(File file) throws IOException {
        if (file.isDirectory()) {
            d(file);
            return;
        }
        boolean zExists = file.exists();
        if (file.delete()) {
            return;
        }
        if (zExists) {
            throw new IOException("Unable to delete file: " + file);
        }
        throw new FileNotFoundException("File does not exist: " + file);
    }

    private static File[] f(File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            return fileArrListFiles;
        }
        throw new IOException("Failed to list contents of " + file);
    }

    public static boolean a(File file, String str) {
        File file2 = new File(file.getCanonicalPath(), str);
        return !file2.getAbsolutePath().equals(file2.getCanonicalPath());
    }
}

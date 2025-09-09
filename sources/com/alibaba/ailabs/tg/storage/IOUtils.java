package com.alibaba.ailabs.tg.storage;

import android.database.Cursor;
import com.alibaba.ailabs.tg.utils.FileUtils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* loaded from: classes2.dex */
public class IOUtils {
    private static int sBufferSize = 8192;

    private IOUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

    public static Object deserialization(String str) throws Throwable {
        ObjectInputStream objectInputStream;
        ObjectInputStream objectInputStream2 = null;
        Object object = null;
        try {
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(str));
            } catch (Exception e2) {
                e = e2;
                objectInputStream = null;
            } catch (Throwable th) {
                th = th;
                closeQuietly(objectInputStream2);
                throw th;
            }
            try {
                object = objectInputStream.readObject();
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                closeQuietly(objectInputStream);
                return object;
            }
            closeQuietly(objectInputStream);
            return object;
        } catch (Throwable th2) {
            th = th2;
            objectInputStream2 = objectInputStream;
            closeQuietly(objectInputStream2);
            throw th;
        }
    }

    public static byte[] readFile2Bytes(File file) throws Throwable {
        FileInputStream fileInputStream;
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        if (!FileUtils.isFileExists(file)) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (IOException e2) {
                e = e2;
                byteArrayOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
                closeQuietly(fileInputStream, byteArrayOutputStream);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            byteArrayOutputStream = null;
            fileInputStream = null;
        } catch (Throwable th3) {
            fileInputStream = null;
            th = th3;
            byteArrayOutputStream = null;
        }
        try {
            try {
                byte[] bArr = new byte[sBufferSize];
                while (true) {
                    int i2 = fileInputStream.read(bArr, 0, sBufferSize);
                    if (i2 == -1) {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        closeQuietly(fileInputStream, byteArrayOutputStream);
                        return byteArray;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
            } catch (Throwable th4) {
                th = th4;
                closeQuietly(fileInputStream, byteArrayOutputStream);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            closeQuietly(fileInputStream, byteArrayOutputStream);
            return null;
        }
    }

    public static void serialization(String str, Object obj) throws Throwable {
        ObjectOutputStream objectOutputStream = null;
        try {
            try {
                ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(str));
                try {
                    objectOutputStream2.writeObject(obj);
                    closeQuietly(objectOutputStream2);
                } catch (Exception e2) {
                    e = e2;
                    objectOutputStream = objectOutputStream2;
                    e.printStackTrace();
                    closeQuietly(objectOutputStream);
                } catch (Throwable th) {
                    th = th;
                    objectOutputStream = objectOutputStream2;
                    closeQuietly(objectOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static boolean writeFileFromBytes(File file, byte[] bArr, boolean z2) throws Throwable {
        if (bArr == null || !FileUtils.createOrExistsFile(file)) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file, z2));
                try {
                    bufferedOutputStream2.write(bArr);
                    closeQuietly(bufferedOutputStream2);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    closeQuietly(bufferedOutputStream);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    closeQuietly(bufferedOutputStream);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean writeFileFromInputStream(File file, InputStream inputStream, boolean z2) throws Throwable {
        if (!FileUtils.createOrExistsFile(file) || inputStream == null) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file, z2));
                try {
                    byte[] bArr = new byte[sBufferSize];
                    while (true) {
                        int i2 = inputStream.read(bArr, 0, sBufferSize);
                        if (i2 == -1) {
                            closeQuietly(inputStream, bufferedOutputStream2);
                            return true;
                        }
                        bufferedOutputStream2.write(bArr, 0, i2);
                    }
                } catch (IOException e2) {
                    e = e2;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    closeQuietly(inputStream, bufferedOutputStream);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    closeQuietly(inputStream, bufferedOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable unused) {
            }
        }
    }

    public static void closeQuietly(Closeable... closeableArr) {
        if (closeableArr == null) {
            return;
        }
        for (Closeable closeable : closeableArr) {
            closeQuietly(closeable);
        }
    }
}

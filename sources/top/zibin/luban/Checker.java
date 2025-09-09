package top.zibin.luban;

import android.graphics.BitmapFactory;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* loaded from: classes5.dex */
public enum Checker {
    SINGLE;

    private static final String JPG = ".jpg";
    private static final String TAG = "Luban";
    private final byte[] JPEG_SIGNATURE = {-1, -40, -1};

    Checker() {
    }

    public static boolean isContent(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("content://");
    }

    private int pack(byte[] bArr, int i2, int i3, boolean z2) {
        int i4;
        if (z2) {
            i2 += i3 - 1;
            i4 = -1;
        } else {
            i4 = 1;
        }
        int i5 = 0;
        while (true) {
            int i6 = i3 - 1;
            if (i3 <= 0) {
                return i5;
            }
            i5 = (bArr[i2] & 255) | (i5 << 8);
            i2 += i4;
            i3 = i6;
        }
    }

    private byte[] toByteArray(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            try {
                try {
                    int i2 = inputStream.read(bArr, 0, 4096);
                    if (i2 != -1) {
                        byteArrayOutputStream.write(bArr, 0, i2);
                    } else {
                        try {
                            break;
                        } catch (IOException unused) {
                        }
                    }
                } catch (Exception unused2) {
                    byte[] bArr2 = new byte[0];
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException unused3) {
                    }
                    return bArr2;
                }
            } finally {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused4) {
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    String extSuffix(InputStreamProvider inputStreamProvider) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStreamProvider.open(), null, options);
            return options.outMimeType.replace("image/", ".");
        } catch (Exception unused) {
            return ".jpg";
        }
    }

    int getOrientation(InputStream inputStream) {
        return getOrientation(toByteArray(inputStream));
    }

    boolean isJPG(InputStream inputStream) {
        return isJPG(toByteArray(inputStream));
    }

    boolean needCompress(int i2, String str) {
        if (i2 <= 0) {
            return true;
        }
        File file = new File(str);
        return file.exists() && file.length() > (((long) i2) << 10);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x005c, code lost:
    
        android.util.Log.e(top.zibin.luban.Checker.TAG, "Invalid length");
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0061, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0062, code lost:
    
        r9 = 0;
        r1 = r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getOrientation(byte[] r12) {
        /*
            Method dump skipped, instructions count: 216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: top.zibin.luban.Checker.getOrientation(byte[]):int");
    }

    private boolean isJPG(byte[] bArr) {
        if (bArr == null || bArr.length < 3) {
            return false;
        }
        return Arrays.equals(this.JPEG_SIGNATURE, new byte[]{bArr[0], bArr[1], bArr[2]});
    }
}

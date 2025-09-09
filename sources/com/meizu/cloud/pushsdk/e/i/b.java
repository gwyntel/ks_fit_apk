package com.meizu.cloud.pushsdk.e.i;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.NetworkOnMainThreadException;
import android.widget.ImageView;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.meizu.cloud.pushsdk.e.b.c;
import com.meizu.cloud.pushsdk.e.d.k;
import com.meizu.cloud.pushsdk.e.h.g;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/* loaded from: classes4.dex */
public class b {
    public static int a(int i2, int i3, int i4, int i5) {
        double dMin = Math.min(i2 / i4, i3 / i5);
        float f2 = 1.0f;
        while (true) {
            float f3 = 2.0f * f2;
            if (f3 > dMin) {
                return (int) f2;
            }
            f2 = f3;
        }
    }

    public static com.meizu.cloud.pushsdk.e.c.a b(com.meizu.cloud.pushsdk.e.c.a aVar) {
        aVar.a(0);
        aVar.b("parseError");
        aVar.a(aVar.getMessage());
        return aVar;
    }

    private static int a(int i2, int i3, int i4, int i5, ImageView.ScaleType scaleType) {
        if (i2 == 0 && i3 == 0) {
            return i4;
        }
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            return i2 == 0 ? i4 : i2;
        }
        if (i2 == 0) {
            return (int) (i4 * (i3 / i5));
        }
        if (i3 == 0) {
            return i2;
        }
        double d2 = i5 / i4;
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            double d3 = i3;
            return ((double) i2) * d2 < d3 ? (int) (d3 / d2) : i2;
        }
        double d4 = i3;
        return ((double) i2) * d2 > d4 ? (int) (d4 / d2) : i2;
    }

    public static c<Bitmap> a(k kVar, int i2, int i3, Bitmap.Config config, ImageView.ScaleType scaleType) {
        Bitmap bitmapCreateScaledBitmap;
        byte[] bArrB = new byte[0];
        try {
            bArrB = g.a(kVar.a().f()).b();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (i2 != 0 || i3 != 0) {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArrB, 0, bArrB.length, options);
            int i4 = options.outWidth;
            int i5 = options.outHeight;
            int iA = a(i2, i3, i4, i5, scaleType);
            int iA2 = a(i3, i2, i5, i4, scaleType);
            options.inJustDecodeBounds = false;
            options.inSampleSize = a(i4, i5, iA, iA2);
            Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrB, 0, bArrB.length, options);
            if (a(bitmapDecodeByteArray, iA, iA2)) {
                bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeByteArray, iA, iA2, true);
                bitmapDecodeByteArray.recycle();
            } else {
                bitmapCreateScaledBitmap = bitmapDecodeByteArray;
            }
        } else {
            options.inPreferredConfig = config;
            bitmapCreateScaledBitmap = BitmapFactory.decodeByteArray(bArrB, 0, bArrB.length, options);
        }
        return bitmapCreateScaledBitmap == null ? c.a(b(new com.meizu.cloud.pushsdk.e.c.a(kVar))) : c.a(bitmapCreateScaledBitmap);
    }

    public static com.meizu.cloud.pushsdk.e.c.a a(com.meizu.cloud.pushsdk.e.c.a aVar) {
        aVar.b("connectionError");
        aVar.a(0);
        aVar.a(aVar.getMessage());
        return aVar;
    }

    public static com.meizu.cloud.pushsdk.e.c.a a(com.meizu.cloud.pushsdk.e.c.a aVar, com.meizu.cloud.pushsdk.e.b.b bVar, int i2) {
        com.meizu.cloud.pushsdk.e.c.a aVarA = bVar.a(aVar);
        aVarA.a(i2);
        aVarA.b("responseFromServerError");
        return aVarA;
    }

    public static com.meizu.cloud.pushsdk.e.c.a a(Exception exc) {
        com.meizu.cloud.pushsdk.e.c.a aVar = new com.meizu.cloud.pushsdk.e.c.a(exc);
        aVar.b(exc instanceof NetworkOnMainThreadException ? "networkOnMainThreadError" : "connectionError");
        aVar.a(0);
        return aVar;
    }

    public static String a(String str) {
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(str);
        return contentTypeFor == null ? OSSConstants.DEFAULT_OBJECT_CONTENT_TYPE : contentTypeFor;
    }

    public static void a(k kVar, String str, String str2) throws Throwable {
        InputStream inputStreamE;
        byte[] bArr = new byte[2048];
        FileOutputStream fileOutputStream = null;
        try {
            inputStreamE = kVar.a().e();
            try {
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(file, str2));
                while (true) {
                    try {
                        int i2 = inputStreamE.read(bArr);
                        if (i2 == -1) {
                            break;
                        } else {
                            fileOutputStream2.write(bArr, 0, i2);
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (inputStreamE != null) {
                            try {
                                inputStreamE.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (fileOutputStream == null) {
                            throw th;
                        }
                        try {
                            fileOutputStream.close();
                            throw th;
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            throw th;
                        }
                    }
                }
                fileOutputStream2.flush();
                try {
                    inputStreamE.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                try {
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStreamE = null;
        }
    }

    private static boolean a(Bitmap bitmap, int i2, int i3) {
        if (bitmap == null) {
            return false;
        }
        return bitmap.getWidth() > i2 || bitmap.getHeight() > i3;
    }
}

package com.huawei.hms.scankit.p;

import android.graphics.Bitmap;
import android.util.Log;
import com.huawei.hms.feature.DynamicModuleInitializer;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public class m1 {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f17520a = true;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f17521b = false;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f17522c = false;

    /* renamed from: d, reason: collision with root package name */
    private static int f17523d;

    /* renamed from: e, reason: collision with root package name */
    private static LinkedList<i2> f17524e = new LinkedList<>();

    /* renamed from: f, reason: collision with root package name */
    private static LinkedList<c6> f17525f = new LinkedList<>();

    /* renamed from: g, reason: collision with root package name */
    private static LinkedList<c6> f17526g = new LinkedList<>();

    /* renamed from: h, reason: collision with root package name */
    private static boolean f17527h;

    /* renamed from: i, reason: collision with root package name */
    private static boolean f17528i;

    /* renamed from: j, reason: collision with root package name */
    private static long f17529j;

    /* renamed from: k, reason: collision with root package name */
    private static boolean f17530k;

    static {
        f17527h = !r3.f17714a || r3.f17716c;
        f17528i = false;
        f17530k = false;
        if (DynamicModuleInitializer.getContext() == null) {
            Log.e("ScankitDecode", "static initializer: context null");
            return;
        }
        Log.i("ScankitDecode", "static initializer: InitModuleBegin");
        y4.c(DynamicModuleInitializer.getContext(), "detect.ms");
        y4.a(DynamicModuleInitializer.getContext(), "angle.ms");
        y4.b(DynamicModuleInitializer.getContext(), "corner.ms");
        Log.i("ScankitDecode", "static initializer: InitModuleEnd");
    }

    public static s6 a(List<BarcodeFormat> list, n1 n1Var) {
        if (list.size() > 0) {
            return n1Var.e(list, null);
        }
        return null;
    }

    public static s6[] b(Bitmap bitmap, x6 x6Var) {
        byte[] bArrB = null;
        try {
            x6Var.f17990a = bitmap.getWidth();
            x6Var.f17991b = bitmap.getHeight();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmap.getByteCount());
            bitmap.copyPixelsToBuffer(byteBufferAllocate);
            byteBufferAllocate.flip();
            bArrB = new m6(x6Var.f17990a, x6Var.f17991b, byteBufferAllocate).b();
            byteBufferAllocate.clear();
        } catch (IllegalArgumentException unused) {
            o4.b("exception", "IllegalArgumentException");
        } catch (Exception unused2) {
            o4.b("exception", "Exception");
        } catch (OutOfMemoryError unused3) {
            o4.b("exception", "OutOfMemoryError");
        } catch (UnsatisfiedLinkError unused4) {
            o4.b("exception", "UnsatisfiedLinkError");
        } catch (UnsupportedOperationException unused5) {
            o4.b("exception", "UnsupportedArgumentException");
        }
        return c(bArrB, x6Var);
    }

    public static s6[] c(byte[] bArr, x6 x6Var) {
        s6[] s6VarArr = new s6[0];
        try {
            return a(bArr, x6Var, true);
        } catch (IllegalArgumentException unused) {
            o4.b("exception", "IllegalArgumentException");
            return s6VarArr;
        } catch (UnsatisfiedLinkError unused2) {
            o4.b("exception", "UnsatisfiedLinkError");
            return s6VarArr;
        } catch (UnsupportedOperationException unused3) {
            o4.b("exception", "UnsupportedArgumentException");
            return s6VarArr;
        } catch (Exception unused4) {
            o4.b("exception", "Exception");
            return s6VarArr;
        } catch (OutOfMemoryError unused5) {
            o4.b("exception", "OutOfMemoryError");
            return s6VarArr;
        }
    }

    public static s6[] a(Bitmap bitmap, x6 x6Var) {
        o4.b("scankit mul", "start decodeMultiCode pre");
        byte[] bArrB = null;
        try {
            x6Var.f17990a = bitmap.getWidth();
            x6Var.f17991b = bitmap.getHeight();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmap.getByteCount());
            bitmap.copyPixelsToBuffer(byteBufferAllocate);
            byteBufferAllocate.flip();
            bArrB = new m6(x6Var.f17990a, x6Var.f17991b, byteBufferAllocate).b();
            byteBufferAllocate.clear();
        } catch (IllegalArgumentException unused) {
            o4.b("exception", "IllegalArgumentException");
        } catch (UnsupportedOperationException unused2) {
            o4.b("exception", "UnsupportedArgumentException");
        } catch (Exception unused3) {
            o4.b("exception", "Exception");
        } catch (OutOfMemoryError unused4) {
            o4.b("exception", "OutOfMemoryError");
        } catch (UnsatisfiedLinkError unused5) {
            o4.b("exception", "UnsatisfiedLinkError");
        }
        o4.b("scankit mul", "end decodeMultiCode pre");
        return b(bArrB, x6Var);
    }

    public static s6[] b(p4 p4Var, x6 x6Var) {
        s6 s6VarA;
        float fMax;
        s6 s6Var;
        boolean z2;
        boolean zA;
        int i2;
        o4.d("ScankitDecode", "scankit mode:FULLSDK21200301 VERSION_NAME: 2.12.0.301");
        r3.a(x6Var);
        List<i2> arrayList = new ArrayList<>();
        if (x6Var.f17990a >= 30 && x6Var.f17991b >= 30 && p4Var != null) {
            List<List<BarcodeFormat>> listA = n3.a(x6Var.f17992c);
            List<BarcodeFormat> list = listA.get(0);
            List<BarcodeFormat> list2 = listA.get(1);
            List<BarcodeFormat> list3 = listA.get(2);
            List<BarcodeFormat> list4 = listA.get(3);
            List<BarcodeFormat> list5 = listA.get(4);
            n1 n1Var = new n1(p4Var);
            o4.d("Scankit", "Start decoding the full image");
            s6 s6Var2 = null;
            if (!f17520a || f17522c) {
                s6VarA = null;
            } else {
                s6VarA = a(list, n1Var);
                f17528i = false;
                f17529j = System.currentTimeMillis();
            }
            if (a(s6VarA)) {
                o4.d("Scankit", "detection start.");
                arrayList = n1Var.a(0, r3.f17730q);
                o4.d("Scankit", "detection results size: " + arrayList.size());
                if (arrayList.size() > 0) {
                    o4.d("Scankit", "Start decoding  with detection");
                    s6VarA = b(arrayList, n1Var, listA);
                    f17528i = true;
                } else {
                    o4.d("Scankit", "Start decoding  without detection");
                    if (r3.f17716c || !r3.f17714a || r3.f17715b) {
                        if (a(s6VarA) && list3.size() > 0) {
                            s6VarA = n1Var.d(list3, null);
                        }
                        if (a(s6VarA) && list2.size() > 0 && f17527h) {
                            s6VarA = n1Var.a(list2, (i2) null);
                        }
                        if (a(s6VarA) && list5.size() > 0) {
                            s6VarA = n1Var.b(list5, (i2) null);
                        }
                        if (a(s6VarA) && list4.size() > 0) {
                            s6VarA = n1Var.b(list4, (i2) null);
                        }
                    }
                }
            }
            o4.d("Scankit", "Decoding completed");
            boolean z3 = (f17520a || !f17521b || f17522c) ? false : true;
            if (x6Var.f17994e && a(s6VarA) && z3) {
                s6VarA = a(list, n1Var);
                f17521b = false;
            }
            if (r3.f17716c) {
                fMax = 1.0f;
                s6Var = null;
                z2 = false;
                zA = false;
            } else {
                boolean zB = n1Var.b();
                int i3 = r3.f17724k - 1;
                if (i3 <= 0) {
                    i3 = 0;
                }
                r3.f17724k = i3;
                if (arrayList.size() > 0) {
                    zB = zB || n1Var.b(arrayList);
                }
                if (zB && n1Var.c(n1Var.a()) < 20.0f) {
                    zB = false;
                }
                if (n1Var.e() > 0.0f) {
                    fMax = Math.max(1.0f, n1Var.e());
                } else {
                    fMax = Math.max(1.0f, Math.max(n1Var.c(), n1Var.d()));
                }
                s6 s6VarA2 = n1.a(arrayList, n1Var);
                s6 s6VarA3 = n1.a(n1Var);
                zA = arrayList.size() > 0 ? n1.a(arrayList, zB) : false;
                z2 = zB;
                s6Var = s6VarA2;
                s6Var2 = s6VarA3;
            }
            if (s6Var2 != null && s6Var2.h() == -2) {
                f17523d++;
                i2 = 0;
            } else {
                i2 = 0;
                f17523d = 0;
            }
            o4.d("Scankit", "end zoom and expose cal");
            if (s6VarA != null && s6VarA.k() != null) {
                o4.d("ScankitDecode", "ScanCode successful");
                f17523d = i2;
                s6VarA.b(f17529j);
                s6VarA.a(System.currentTimeMillis());
                s6VarA.a(f17528i);
                s6[] s6VarArr = new s6[1];
                s6VarArr[i2] = s6VarA;
                return s6VarArr;
            }
            if (z2) {
                o4.d("ScankitDecode", "ScanCode need zoom");
                s6 s6Var3 = new s6(fMax);
                s6Var3.c(true);
                f17523d = i2;
                s6[] s6VarArr2 = new s6[1];
                s6VarArr2[i2] = s6Var3;
                return s6VarArr2;
            }
            if (arrayList.size() > 0 && s6Var != null) {
                o4.d("ScankitDecode", "ScanCode need exposure");
                f17523d = i2;
                s6[] s6VarArr3 = new s6[1];
                s6VarArr3[i2] = s6Var;
                return s6VarArr3;
            }
            if (s6Var2 != null && f17523d == 3) {
                s6Var2.b(true);
                s6Var2.a(-1);
                o4.d("ScankitDecode", "ScanCode need globalexposure");
                f17523d = 0;
                return new s6[]{s6Var2};
            }
            if (zA) {
                s6 s6Var4 = new s6(1.0f, true);
                float[] fArr = r3.f17738y;
                float f2 = fArr[0];
                float f3 = fArr[1];
                s6Var4.a(new i2(false, f2, f3, fArr[2] - f2, fArr[3] - f3, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
                return new s6[]{s6Var4};
            }
            o4.d("ScankitDecode", "ScanCode null");
            return new s6[0];
        }
        throw new IllegalArgumentException("widthOrHeight is Illeagle");
    }

    public static s6[] a(ByteBuffer byteBuffer, x6 x6Var) {
        return b(byteBuffer.array(), x6Var);
    }

    public static s6[] a(p4 p4Var, x6 x6Var) {
        s6 s6VarA;
        boolean zP;
        List arrayList = new ArrayList();
        r3.a(x6Var);
        r3.a(1);
        if (x6Var.f17990a >= 30 && x6Var.f17991b >= 30 && p4Var != null) {
            List<List<BarcodeFormat>> listA = n3.a(x6Var.f17992c);
            List<BarcodeFormat> list = listA.get(0);
            List<BarcodeFormat> list2 = listA.get(1);
            List<BarcodeFormat> list3 = listA.get(2);
            List<BarcodeFormat> list4 = listA.get(3);
            n1 n1Var = new n1(p4Var);
            o4.b("scankit mul", "start detectCodes");
            List<i2> listA2 = n1Var.a(1, r3.f17730q);
            o4.b("scankit mul", "end detectCodes");
            if (listA2.size() > 0) {
                arrayList = a(listA2, n1Var, listA);
            } else if ((r3.f17716c || !r3.f17714a) && (s6VarA = a(n1Var, list, list2, list3, list4)) != null && s6VarA.k() != null) {
                arrayList.add(s6VarA);
            }
            List<s6> listA3 = v7.a(arrayList);
            if (r3.f17720g && listA3.size() > 0 && listA3.get(0).k() != null) {
                return (s6[]) listA3.toArray(new s6[0]);
            }
            float fMax = 1.0f;
            if (r3.f17716c || !r3.f17720g) {
                zP = false;
            } else {
                if (listA3.size() > 0) {
                    zP = listA3.get(0).p();
                    fMax = Math.max(1.0f, listA3.get(0).l());
                } else {
                    zP = false;
                }
                int i2 = r3.f17724k - 1;
                if (i2 <= 0) {
                    i2 = 0;
                }
                r3.f17724k = i2;
                if (listA2.size() > 0) {
                    zP = zP || n1Var.c(listA2);
                }
                if (zP && n1Var.c(n1Var.a()) < 20.0f) {
                    zP = false;
                }
                if (zP) {
                    fMax = Math.max(fMax, n1Var.e());
                }
            }
            if (r3.f17720g && zP) {
                s6 s6Var = new s6(fMax);
                s6Var.c(true);
                listA3.clear();
                listA3.add(s6Var);
                return (s6[]) listA3.toArray(new s6[0]);
            }
            if (listA3.size() > 0) {
                return (s6[]) listA3.toArray(new s6[0]);
            }
            return new s6[0];
        }
        throw new IllegalArgumentException("width or Height is Illeagle");
    }

    private static s6 a(n1 n1Var, List<BarcodeFormat> list, List<BarcodeFormat> list2, List<BarcodeFormat> list3, List<BarcodeFormat> list4) {
        s6 s6VarF = list.size() > 0 ? n1Var.f(list, null) : null;
        if (a(s6VarF) && list3.size() > 0) {
            s6VarF = n1Var.d(list3, null);
        }
        if (a(s6VarF) && list2.size() > 0 && f17527h) {
            s6VarF = n1Var.a(list2, (i2) null);
        }
        return (!a(s6VarF) || list4.size() <= 0) ? s6VarF : n1Var.b(list4, (i2) null);
    }

    public static List<s6> a(List<i2> list, n1 n1Var, List<List<BarcodeFormat>> list2) {
        s6 s6VarG;
        boolean z2;
        float fMax;
        boolean z3 = false;
        List<BarcodeFormat> list3 = list2.get(0);
        boolean z4 = true;
        List<BarcodeFormat> list4 = list2.get(1);
        List<BarcodeFormat> list5 = list2.get(2);
        List<BarcodeFormat> list6 = list2.get(3);
        List<BarcodeFormat> list7 = list2.get(4);
        List<BarcodeFormat> list8 = list2.get(5);
        List<BarcodeFormat> list9 = list2.get(6);
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < list.size()) {
            n1Var.f17571i.a();
            i2 i2Var = list.get(i2);
            boolean z5 = i2Var.g() == 5.0f ? z4 : z3;
            boolean z6 = i2Var.g() == 1.0f ? z4 : false;
            boolean z7 = i2Var.g() == 3.0f ? z4 : false;
            boolean z8 = i2Var.g() == 2.0f ? z4 : false;
            boolean z9 = i2Var.g() == 4.0f ? z4 : false;
            boolean z10 = i2Var.g() == 6.0f ? z4 : false;
            boolean z11 = i2Var.g() == 7.0f ? z4 : false;
            if (r3.f17715b) {
                z5 = i2Var.g() == 1.0f ? z4 : false;
                z6 = i2Var.g() == 2.0f ? z4 : false;
                z7 = i2Var.g() == 2.0f ? z4 : false;
                z9 = i2Var.g() == 1.0f ? z4 : false;
                z8 = i2Var.g() == 2.0f ? z4 : false;
            }
            o4.d("scankit mul", "start cropAndRotate");
            n1Var.b(i2Var);
            o4.d("scankit mul", "end cropAndRotate");
            o4.d("scankit mul", "start decode");
            s6 s6VarE = (a((s6) null) && list3.size() > 0 && z6) ? n1Var.e(list3, i2Var) : null;
            if (a(s6VarE) && list6.size() > 0 && z7) {
                s6VarE = n1Var.b(list6, i2Var);
            }
            if (a(s6VarE) && list5.size() > 0 && z9) {
                s6VarE = n1Var.d(list5, i2Var);
            }
            if (a(s6VarE) && list7.size() > 0 && z8) {
                s6VarE = n1Var.b(list7, i2Var);
            }
            if (a(s6VarE) && list4.size() > 0 && z5) {
                s6VarE = n1Var.a(list4, i2Var);
            }
            if (a(s6VarE) && list8.size() > 0 && z11) {
                s6VarE = n1Var.c(list8, i2Var);
            }
            s6 s6Var = (!a(s6VarE) || list9.size() <= 0 || !z10 || (((double) i2Var.h()) <= 0.6d && !r3.f17716c) || (s6VarE = n1Var.h(list9, i2Var)) == null || n1.b(n1Var.a(), i2Var)) ? s6VarE : null;
            if (r3.f17720g && s6Var != null && n1Var.b()) {
                if (n1Var.e() > 0.0f) {
                    fMax = Math.max(1.0f, n1Var.e());
                } else {
                    fMax = Math.max(1.0f, Math.max(n1Var.c(), n1Var.d()));
                }
                s6 s6Var2 = new s6(fMax);
                z2 = true;
                s6Var2.c(true);
                arrayList.add(s6Var2);
            } else {
                z2 = true;
                if (s6Var != null && s6Var.k() != null) {
                    arrayList.add(s6Var);
                }
            }
            i2++;
            z4 = z2;
            z3 = false;
        }
        if (arrayList.size() == 0 && list3.size() > 0 && !r3.f17720g && (s6VarG = n1Var.g(list3, null)) != null && s6VarG.k() != null) {
            arrayList.add(s6VarG);
        }
        o4.d("scankit mul", "end decode");
        return arrayList;
    }

    public static s6 b(List<i2> list, n1 n1Var, List<List<BarcodeFormat>> list2) {
        List<BarcodeFormat> list3;
        boolean z2 = false;
        List<BarcodeFormat> list4 = list2.get(0);
        List<BarcodeFormat> list5 = list2.get(1);
        List<BarcodeFormat> list6 = list2.get(2);
        List<BarcodeFormat> list7 = list2.get(3);
        List<BarcodeFormat> list8 = list2.get(4);
        List<BarcodeFormat> list9 = list2.get(5);
        List<BarcodeFormat> list10 = list2.get(6);
        int i2 = 0;
        s6 s6VarC = null;
        while (i2 < list.size()) {
            if (i2 > 0) {
                n1Var.f17571i.a();
            }
            i2 i2Var = list.get(i2);
            boolean z3 = i2Var.g() == 5.0f ? true : z2;
            boolean z4 = i2Var.g() == 1.0f ? true : z2;
            boolean z5 = i2Var.g() == 2.0f ? true : z2;
            boolean z6 = i2Var.g() == 3.0f ? true : z2;
            boolean z7 = i2Var.g() == 4.0f ? true : z2;
            boolean z8 = i2Var.g() == 6.0f ? true : z2;
            boolean z9 = i2Var.g() == 7.0f ? true : z2;
            if (r3.f17715b) {
                z3 = i2Var.g() == 1.0f ? true : z2;
                z4 = i2Var.g() == 2.0f ? true : z2;
                z5 = i2Var.g() == 2.0f ? true : z2;
                z6 = i2Var.g() == 2.0f ? true : z2;
                z7 = i2Var.g() == 1.0f ? true : z2;
            }
            n1Var.b(i2Var);
            if (a(s6VarC) && list4.size() > 0 && z4) {
                f17521b = true;
                s6VarC = n1Var.e(list4, i2Var);
            }
            if (a(s6VarC) && list7.size() > 0 && z6) {
                s6VarC = n1Var.b(list7, i2Var);
            }
            if (a(s6VarC) && list8.size() > 0 && z5) {
                s6VarC = n1Var.b(list8, i2Var);
            }
            if (a(s6VarC) && list6.size() > 0 && z7) {
                s6VarC = n1Var.d(list6, i2Var);
            }
            if (a(s6VarC) && list5.size() > 0 && z3) {
                s6VarC = n1Var.a(list5, i2Var);
            }
            if (a(s6VarC) && list9.size() > 0 && z9) {
                s6VarC = n1Var.c(list9, i2Var);
            }
            if (a(s6VarC) && list10.size() > 0 && z8) {
                list3 = list4;
                if (i2Var.h() > 0.6d || r3.f17716c) {
                    s6 s6VarH = n1Var.h(list10, i2Var);
                    s6VarC = (s6VarH == null || n1.b(n1Var.a(), i2Var)) ? s6VarH : null;
                }
            } else {
                list3 = list4;
            }
            if (s6VarC != null && s6VarC.k() != null) {
                break;
            }
            i2++;
            list4 = list3;
            z2 = false;
        }
        return s6VarC;
    }

    private static p4 a(byte[] bArr, x6 x6Var) {
        int i2 = x6Var.f17990a;
        int i3 = x6Var.f17991b;
        if (x6Var.f17993d) {
            int i4 = i2 * i3;
            byte[] bArr2 = new byte[i4];
            float f2 = 0.0f;
            for (int i5 = 0; i5 < i3; i5++) {
                for (int i6 = 0; i6 < i2; i6++) {
                    f2 += r9 & 255;
                    bArr2[(((i6 * i3) + i3) - i5) - 1] = bArr[(i5 * i2) + i6];
                }
            }
            float f3 = f2 / i4;
            if (f3 < 25.0f) {
                r3.f17718e = true;
            } else if (f3 > 215.0f) {
                r3.f17717d = true;
            }
            x6Var.f17990a = i3;
            x6Var.f17991b = i2;
            return new e6(bArr2, i3, i2, 0, 0, i3, i2, false);
        }
        return new e6(bArr, i2, i3, 0, 0, i2, i3, false);
    }

    public static void a(boolean z2) {
        r3.f17714a = z2;
    }

    private static void a() {
        f17522c = false;
        f17524e = new LinkedList<>();
        f17525f = new LinkedList<>();
        f17526g = new LinkedList<>();
        r3.f17717d = false;
        r3.f17718e = false;
    }

    public static s6[] b(byte[] bArr, x6 x6Var) {
        s6[] s6VarArrA;
        o4.b("scankit mul", "start decodeMultiCode");
        s6[] s6VarArr = new s6[0];
        try {
            s6VarArrA = a(bArr, x6Var, false);
        } catch (IllegalArgumentException unused) {
            o4.b("exception", "IllegalArgumentException");
        } catch (OutOfMemoryError unused2) {
            o4.b("exception", "OutOfMemoryError");
        } catch (UnsupportedOperationException unused3) {
            o4.b("exception", "UnsupportedArgumentException");
        } catch (Exception unused4) {
            o4.b("exception", "Exception");
        } catch (UnsatisfiedLinkError unused5) {
            o4.b("exception", "UnsatisfiedLinkError");
        }
        if (r3.f17720g && s6VarArrA.length > 0 && s6VarArrA[0].p()) {
            return s6VarArrA;
        }
        int length = s6VarArrA.length;
        int[] iArr = new int[length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < s6VarArrA.length) {
            int i4 = i2 + 1;
            for (int i5 = i4; i5 < s6VarArrA.length; i5++) {
                if (v7.a(s6VarArrA[i2].j(), s6VarArrA[i5].j()) > 0.7d) {
                    iArr[i5] = 1;
                    i3++;
                }
            }
            i2 = i4;
        }
        int length2 = s6VarArrA.length - i3;
        s6VarArr = new s6[length2];
        for (int i6 = 0; i6 < length2; i6++) {
            int i7 = i6;
            while (i7 < length) {
                if (iArr[i7] != 1) {
                    break;
                }
                i7++;
            }
            s6VarArr[i6] = s6VarArrA[i7];
        }
        o4.b("scankit mul", "end decodeMultiCode");
        return s6VarArr;
    }

    public static s6[] a(byte[] bArr, x6 x6Var, boolean z2) {
        int i2;
        int i3;
        o4.d("scankit mul", "start pre");
        LinkedList linkedList = new LinkedList();
        a();
        int iMin = Math.min(x6Var.f17990a, x6Var.f17991b);
        float f2 = iMin;
        float fMax = Math.max(x6Var.f17990a, x6Var.f17991b) / f2;
        int i4 = (int) (f2 * 1.78f);
        p4 p4VarA = a(bArr, x6Var);
        o4.d("Scankit", "init " + f17530k);
        if ((r3.f17717d || r3.f17718e) && f17530k) {
            return new s6[0];
        }
        f17530k = true;
        x6 x6Var2 = new x6(x6Var);
        o4.d("scankit mul", "end pre");
        if (iMin > 500 && x6Var.f17990a >= x6Var.f17991b && x6Var.f17994e && fMax > 3.0f) {
            f17522c = true;
            x6Var2.f17990a = i4;
            int i5 = x6Var.f17990a - 1;
            while (i5 >= 0) {
                i5 -= i4;
                int i6 = i5 >= 0 ? i5 : 0;
                x6Var2.f17997h = i6;
                x6Var2.f17998i = 0;
                a(p4VarA, i6, 0, x6Var2);
            }
            Collections.sort(f17524e);
            s6 s6VarA = a(p4VarA, x6Var2, linkedList, z2, true, i4);
            if (s6VarA != null) {
                return new s6[]{s6VarA};
            }
            f17524e = new LinkedList<>();
            Collections.sort(f17525f);
            HashSet hashSet = new HashSet();
            Iterator<c6> it = f17525f.iterator();
            while (it.hasNext()) {
                c6 next = it.next();
                if (hashSet.add(Integer.valueOf(next.f17073b)) && (i3 = next.f17073b) >= i4 && i3 <= (x6Var.f17990a - 1) - i4) {
                    x6Var2.f17990a = i4;
                    x6Var2.f17999j = true;
                    int i7 = i3 - (i4 / 2);
                    x6Var2.f17997h = i7;
                    x6Var2.f17998i = 0;
                    a(p4VarA, i7, 0, x6Var2);
                }
            }
            Collections.sort(f17524e);
            s6 s6VarA2 = a(p4VarA, x6Var2, linkedList, z2, true, i4);
            if (s6VarA2 != null) {
                return new s6[]{s6VarA2};
            }
        } else if (iMin > 500 && x6Var.f17994e && fMax > 3.0f) {
            f17522c = true;
            x6Var2.f17991b = i4;
            int i8 = x6Var.f17991b - 1;
            while (i8 >= 0) {
                i8 -= i4;
                int i9 = i8 >= 0 ? i8 : 0;
                x6Var2.f17997h = 0;
                x6Var2.f17998i = i9;
                a(p4VarA, 0, i9, x6Var2);
            }
            Collections.sort(f17524e);
            s6 s6VarA3 = a(p4VarA, x6Var, linkedList, z2, false, i4);
            if (s6VarA3 != null) {
                return new s6[]{s6VarA3};
            }
            f17524e = new LinkedList<>();
            Collections.sort(f17526g);
            HashSet hashSet2 = new HashSet();
            Iterator<c6> it2 = f17526g.iterator();
            while (it2.hasNext()) {
                c6 next2 = it2.next();
                if (hashSet2.add(Integer.valueOf(next2.f17073b)) && (i2 = next2.f17073b) >= i4 && i2 <= (x6Var.f17991b - 1) - i4) {
                    int i10 = i2 - (i4 / 2);
                    x6Var2.f17991b = i4;
                    x6Var2.f17999j = true;
                    x6Var2.f17997h = 0;
                    x6Var2.f17998i = i10;
                    a(p4VarA, 0, i10, x6Var2);
                }
            }
            Collections.sort(f17524e);
            s6 s6VarA4 = a(p4VarA, x6Var, linkedList, z2, false, i4);
            if (s6VarA4 != null) {
                return new s6[]{s6VarA4};
            }
        } else {
            f17522c = false;
            if (z2) {
                return b(p4VarA, x6Var);
            }
            return a(p4VarA, x6Var);
        }
        s6[] s6VarArr = new s6[linkedList.size()];
        linkedList.toArray(s6VarArr);
        return s6VarArr;
    }

    private static void a(p4 p4Var, int i2, int i3, x6 x6Var) {
        r3.a(x6Var);
        byte[] bArrB = p4Var.a(i2, i3, x6Var.f17990a, x6Var.f17991b).b();
        int i4 = x6Var.f17990a;
        int i5 = x6Var.f17991b;
        List<i2> listA = new n1(new e6(bArrB, i4, i5, 0, 0, i4, i5, false)).a(0, r3.f17730q);
        if (!x6Var.f17999j) {
            a(listA, x6Var);
        }
        for (i2 i2Var : listA) {
            i2Var.a(x6Var.f17997h, x6Var.f17998i);
            f17524e.offer(i2Var);
        }
    }

    private static s6 a(p4 p4Var, x6 x6Var, LinkedList<s6> linkedList, boolean z2, boolean z3, int i2) {
        n1 n1Var = new n1(p4Var);
        List<List<BarcodeFormat>> listA = n3.a(x6Var.f17992c);
        if (z2) {
            s6 s6VarB = b(f17524e, n1Var, listA);
            if (s6VarB == null || s6VarB.k() == null) {
                return null;
            }
            return s6VarB;
        }
        Iterator<s6> it = a(f17524e, n1Var, listA).iterator();
        while (it.hasNext()) {
            linkedList.offer(it.next());
        }
        return null;
    }

    private static void a(List<i2> list, x6 x6Var) {
        for (i2 i2Var : list) {
            if (i2Var.d() < x6Var.f17990a * 0.1f) {
                f17525f.offer(new c6(i2Var, x6Var.f17997h));
            } else {
                float fD = i2Var.d() + i2Var.f();
                int i2 = x6Var.f17990a;
                if (fD > i2 * 0.9f) {
                    f17525f.offer(new c6(i2Var, x6Var.f17997h + i2));
                }
            }
            if (i2Var.e() < x6Var.f17991b * 0.1f) {
                f17526g.offer(new c6(i2Var, x6Var.f17998i));
            } else {
                float fE = i2Var.e() + i2Var.c();
                int i3 = x6Var.f17991b;
                if (fE > i3 * 0.9f) {
                    f17526g.offer(new c6(i2Var, x6Var.f17998i + i3));
                }
            }
        }
    }

    private static boolean a(s6 s6Var) {
        return s6Var == null || s6Var.k() == null;
    }
}

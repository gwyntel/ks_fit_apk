package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class dt {
    public static int a(jy jyVar, in inVar) {
        int iA;
        int iA2;
        switch (du.f23610a[inVar.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return fn.a(inVar.a());
            case 11:
                iA = fn.a(inVar.a());
                if (jyVar != null) {
                    try {
                        if (jyVar instanceof je) {
                            String str = ((je) jyVar).f706d;
                            if (!TextUtils.isEmpty(str) && fn.a(fn.m403a(str)) != -1) {
                                iA2 = fn.a(fn.m403a(str));
                                return iA2;
                            }
                        } else if (jyVar instanceof jm) {
                            String str2 = ((jm) jyVar).f765d;
                            if (!TextUtils.isEmpty(str2)) {
                                if (fn.a(fn.m403a(str2)) != -1) {
                                    iA = fn.a(fn.m403a(str2));
                                }
                                if (ix.UploadTinyData.equals(fn.m403a(str2))) {
                                    return -1;
                                }
                            }
                        }
                    } catch (Exception unused) {
                        com.xiaomi.channel.commonutils.logger.b.d("PERF_ERROR : parse Notification type error");
                        return iA;
                    }
                }
                return iA;
            case 12:
                iA = fn.a(inVar.a());
                if (jyVar != null) {
                    try {
                        if (jyVar instanceof ji) {
                            String strB = ((ji) jyVar).b();
                            if (!TextUtils.isEmpty(strB) && fy.a(strB) != -1) {
                                iA = fy.a(strB);
                            }
                        } else if (jyVar instanceof jh) {
                            String strA = ((jh) jyVar).a();
                            if (!TextUtils.isEmpty(strA) && fy.a(strA) != -1) {
                                iA2 = fy.a(strA);
                                return iA2;
                            }
                        }
                    } catch (Exception unused2) {
                        com.xiaomi.channel.commonutils.logger.b.d("PERF_ERROR : parse Command type error");
                    }
                }
                return iA;
            default:
                return -1;
        }
    }

    public static int a(Context context, int i2) {
        int iA = ig.a(context);
        if (-1 == iA) {
            return -1;
        }
        return (i2 * (iA == 0 ? 13 : 11)) / 10;
    }

    public static int a(in inVar) {
        return fn.a(inVar.a());
    }

    public static void a(String str, Context context, byte[] bArr) {
        if (context == null || bArr == null || bArr.length <= 0) {
            return;
        }
        jj jjVar = new jj();
        try {
            jx.a(jjVar, bArr);
            a(str, context, jjVar, bArr.length);
        } catch (kd unused) {
            com.xiaomi.channel.commonutils.logger.b.m91a("fail to convert bytes to container");
        }
    }

    public static void a(String str, Context context, jj jjVar, int i2) {
        in inVarA;
        if (context == null || jjVar == null || (inVarA = jjVar.a()) == null) {
            return;
        }
        int iA = a(inVarA);
        if (i2 <= 0) {
            byte[] bArrA = jx.a(jjVar);
            i2 = bArrA != null ? bArrA.length : 0;
        }
        a(str, context, iA, i2);
    }

    public static void a(String str, Context context, int i2, int i3) {
        if (i2 <= 0 || i3 <= 0) {
            return;
        }
        int iA = a(context, i3);
        if (i2 != fn.a(ix.UploadTinyData)) {
            fo.a(context.getApplicationContext()).a(str, i2, 1L, iA);
        }
    }

    public static void a(String str, Context context, jy jyVar, in inVar, int i2) {
        a(str, context, a(jyVar, inVar), i2);
    }
}

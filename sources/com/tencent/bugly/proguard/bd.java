package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import com.huawei.hms.framework.common.ContainerUtils;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class bd implements NativeExceptionHandler {

    /* renamed from: a, reason: collision with root package name */
    private final Context f20943a;

    /* renamed from: b, reason: collision with root package name */
    private final as f20944b;

    /* renamed from: c, reason: collision with root package name */
    private final aa f20945c;

    /* renamed from: d, reason: collision with root package name */
    private final ac f20946d;

    public bd(Context context, aa aaVar, as asVar, ac acVar) {
        this.f20943a = context;
        this.f20944b = asVar;
        this.f20945c = aaVar;
        this.f20946d = acVar;
    }

    private static Map<String, String> a(String[] strArr) {
        HashMap map = new HashMap(strArr == null ? 1 : strArr.length);
        if (strArr != null) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str = strArr[i2];
                if (str != null) {
                    al.a("Extra message[%d]: %s", Integer.valueOf(i2), str);
                    String[] strArrSplit = str.split(ContainerUtils.KEY_VALUE_DELIMITER);
                    if (strArrSplit.length == 2) {
                        map.put(strArrSplit[0], strArrSplit[1]);
                    } else {
                        al.d("bad extraMsg %s", str);
                    }
                }
            }
        } else {
            al.c("not found extraMsg", new Object[0]);
        }
        return map;
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final boolean getAndUpdateAnrState() {
        if (ay.a() == null) {
            return false;
        }
        ay ayVarA = ay.a();
        if (ayVarA.f20905a.get()) {
            al.c("anr is processing, return", new Object[0]);
            return false;
        }
        ActivityManager activityManager = ayVarA.f20906b;
        if (z.a(activityManager) || az.a(activityManager, 0L) == null) {
            al.c("proc is not in anr, wait next check", new Object[0]);
            return false;
        }
        if (ayVarA.a(System.currentTimeMillis())) {
            return false;
        }
        return ayVarA.a(true);
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final void handleNativeException(int i2, int i3, long j2, long j3, String str, String str2, String str3, String str4, int i4, String str5, int i5, int i6, int i7, String str6, String str7) {
        al.a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i2, i3, j2, j3, str, str2, str3, str4, i4, str5, i5, i6, i7, str6, str7, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0180 A[PHI: r4
      0x0180: PHI (r4v10 java.lang.String) = (r4v9 java.lang.String), (r4v17 java.lang.String) binds: [B:40:0x0156, B:44:0x0166] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01ab A[Catch: all -> 0x01b4, TryCatch #1 {all -> 0x01b4, blocks: (B:49:0x01a5, B:51:0x01ab, B:55:0x01b9), top: B:77:0x01a5 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01b9 A[Catch: all -> 0x01b4, TRY_LEAVE, TryCatch #1 {all -> 0x01b4, blocks: (B:49:0x01a5, B:51:0x01ab, B:55:0x01b9), top: B:77:0x01a5 }] */
    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleNativeException2(int r28, int r29, long r30, long r32, java.lang.String r34, java.lang.String r35, java.lang.String r36, java.lang.String r37, int r38, java.lang.String r39, int r40, int r41, int r42, java.lang.String r43, java.lang.String r44, java.lang.String[] r45) {
        /*
            Method dump skipped, instructions count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.bd.handleNativeException2(int, int, long, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, int, int, int, java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final CrashDetailBean packageCrashDatas(String str, String str2, long j2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, byte[] bArr, Map<String, String> map, boolean z2, boolean z3) throws IOException {
        int i2;
        String str12;
        int iIndexOf;
        boolean zI = at.a().i();
        if (zI) {
            al.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.f20621b = 1;
        crashDetailBean.f20624e = this.f20945c.g();
        aa aaVar = this.f20945c;
        crashDetailBean.f20625f = aaVar.f20690o;
        crashDetailBean.f20626g = aaVar.q();
        crashDetailBean.f20632m = this.f20945c.f();
        crashDetailBean.f20633n = str3;
        crashDetailBean.f20634o = zI ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        crashDetailBean.f20635p = str4;
        String str13 = str5 != null ? str5 : "";
        crashDetailBean.f20636q = str13;
        crashDetailBean.f20637r = j2;
        crashDetailBean.f20640u = ap.c(str13.getBytes());
        crashDetailBean.A = str;
        crashDetailBean.B = str2;
        crashDetailBean.L = this.f20945c.s();
        crashDetailBean.f20627h = this.f20945c.p();
        crashDetailBean.f20628i = this.f20945c.A();
        crashDetailBean.f20641v = str8;
        String dumpFilePath = NativeCrashHandler.getInstance() != null ? NativeCrashHandler.getDumpFilePath() : null;
        String strA = be.a(dumpFilePath, str8);
        if (!ap.b(strA)) {
            crashDetailBean.Z = strA;
        }
        crashDetailBean.aa = be.b(dumpFilePath);
        crashDetailBean.f20642w = be.a(str9, at.f20842f, at.f20847k, at.f20852p);
        crashDetailBean.f20643x = be.a(str10, at.f20842f, null, true);
        crashDetailBean.N = str7;
        crashDetailBean.O = str6;
        crashDetailBean.P = str11;
        crashDetailBean.F = this.f20945c.k();
        crashDetailBean.G = this.f20945c.j();
        crashDetailBean.H = this.f20945c.l();
        crashDetailBean.I = ab.b(this.f20943a);
        crashDetailBean.J = ab.g();
        crashDetailBean.K = ab.h();
        if (z2) {
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.f20644y = ao.a();
            aa aaVar2 = this.f20945c;
            crashDetailBean.Q = aaVar2.f20676a;
            crashDetailBean.R = aaVar2.a();
            crashDetailBean.f20645z = ap.a(this.f20945c.Q, at.f20844h);
            int iIndexOf2 = crashDetailBean.f20636q.indexOf("java:\n");
            if (iIndexOf2 > 0 && (i2 = iIndexOf2 + 6) < crashDetailBean.f20636q.length()) {
                String str14 = crashDetailBean.f20636q;
                String strSubstring = str14.substring(i2, str14.length() - 1);
                if (strSubstring.length() > 0 && crashDetailBean.f20645z.containsKey(crashDetailBean.B) && (iIndexOf = (str12 = crashDetailBean.f20645z.get(crashDetailBean.B)).indexOf(strSubstring)) > 0) {
                    String strSubstring2 = str12.substring(iIndexOf);
                    crashDetailBean.f20645z.put(crashDetailBean.B, strSubstring2);
                    crashDetailBean.f20636q = crashDetailBean.f20636q.substring(0, i2);
                    crashDetailBean.f20636q += strSubstring2;
                }
            }
            if (str == null) {
                crashDetailBean.A = this.f20945c.f20679d;
            }
            crashDetailBean.U = this.f20945c.z();
            aa aaVar3 = this.f20945c;
            crashDetailBean.V = aaVar3.f20699x;
            crashDetailBean.W = aaVar3.t();
            crashDetailBean.X = this.f20945c.y();
        } else {
            crashDetailBean.C = -1L;
            crashDetailBean.D = -1L;
            crashDetailBean.E = -1L;
            if (crashDetailBean.f20642w == null) {
                crashDetailBean.f20642w = "This crash occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.Q = -1L;
            crashDetailBean.U = -1;
            crashDetailBean.V = -1;
            crashDetailBean.W = map;
            crashDetailBean.X = this.f20945c.y();
            crashDetailBean.f20645z = null;
            if (str == null) {
                crashDetailBean.A = "unknown(record)";
            }
            if (bArr != null) {
                crashDetailBean.f20644y = bArr;
            }
        }
        return crashDetailBean;
    }
}

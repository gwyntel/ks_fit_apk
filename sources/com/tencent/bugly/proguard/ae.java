package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.device.panel.data.InvokeServiceData;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ae {
    public static bu a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        bu buVar = new bu();
        buVar.f21058a = userInfoBean.f20579e;
        buVar.f21062e = userInfoBean.f20584j;
        buVar.f21061d = userInfoBean.f20577c;
        buVar.f21060c = userInfoBean.f20578d;
        buVar.f21065h = userInfoBean.f20589o == 1;
        int i2 = userInfoBean.f20576b;
        if (i2 == 1) {
            buVar.f21059b = (byte) 1;
        } else if (i2 == 2) {
            buVar.f21059b = (byte) 4;
        } else if (i2 == 3) {
            buVar.f21059b = (byte) 2;
        } else if (i2 == 4) {
            buVar.f21059b = (byte) 3;
        } else if (i2 == 8) {
            buVar.f21059b = (byte) 8;
        } else {
            if (i2 < 10 || i2 >= 20) {
                al.e("unknown uinfo type %d ", Integer.valueOf(i2));
                return null;
            }
            buVar.f21059b = (byte) i2;
        }
        HashMap map = new HashMap();
        buVar.f21063f = map;
        if (userInfoBean.f20590p >= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(userInfoBean.f20590p);
            map.put("C01", sb.toString());
        }
        if (userInfoBean.f20591q >= 0) {
            Map<String, String> map2 = buVar.f21063f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(userInfoBean.f20591q);
            map2.put("C02", sb2.toString());
        }
        Map<String, String> map3 = userInfoBean.f20592r;
        if (map3 != null && map3.size() > 0) {
            for (Map.Entry<String, String> entry : userInfoBean.f20592r.entrySet()) {
                buVar.f21063f.put("C03_" + entry.getKey(), entry.getValue());
            }
        }
        Map<String, String> map4 = userInfoBean.f20593s;
        if (map4 != null && map4.size() > 0) {
            for (Map.Entry<String, String> entry2 : userInfoBean.f20593s.entrySet()) {
                buVar.f21063f.put("C04_" + entry2.getKey(), entry2.getValue());
            }
        }
        Map<String, String> map5 = buVar.f21063f;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(!userInfoBean.f20586l);
        map5.put("A36", sb3.toString());
        Map<String, String> map6 = buVar.f21063f;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(userInfoBean.f20581g);
        map6.put("F02", sb4.toString());
        Map<String, String> map7 = buVar.f21063f;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(userInfoBean.f20582h);
        map7.put("F03", sb5.toString());
        buVar.f21063f.put("F04", userInfoBean.f20584j);
        Map<String, String> map8 = buVar.f21063f;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(userInfoBean.f20583i);
        map8.put("F05", sb6.toString());
        buVar.f21063f.put("F06", userInfoBean.f20587m);
        Map<String, String> map9 = buVar.f21063f;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(userInfoBean.f20585k);
        map9.put("F10", sb7.toString());
        al.c("summary type %d vm:%d", Byte.valueOf(buVar.f21059b), Integer.valueOf(buVar.f21063f.size()));
        return buVar;
    }

    public static <T extends m> T a(byte[] bArr, Class<T> cls) {
        if (bArr != null && bArr.length > 0) {
            try {
                T tNewInstance = cls.newInstance();
                k kVar = new k(bArr);
                kVar.a("utf-8");
                tNewInstance.a(kVar);
                return tNewInstance;
            } catch (Throwable th) {
                if (!al.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static bq a(Context context, int i2, byte[] bArr) {
        String str;
        aa aaVarB = aa.b();
        StrategyBean strategyBeanC = ac.a().c();
        if (aaVarB != null && strategyBeanC != null) {
            try {
                bq bqVar = new bq();
                synchronized (aaVarB) {
                    try {
                        bqVar.f21006a = aaVarB.f20677b;
                        bqVar.f21007b = aaVarB.e();
                        bqVar.f21008c = aaVarB.f20678c;
                        bqVar.f21009d = aaVarB.f20690o;
                        bqVar.f21010e = aaVarB.f20694s;
                        bqVar.f21011f = aaVarB.f20683h;
                        bqVar.f21012g = i2;
                        if (bArr == null) {
                            bArr = "".getBytes();
                        }
                        bqVar.f21013h = bArr;
                        bqVar.f21014i = aaVarB.h();
                        bqVar.f21015j = aaVarB.f20686k;
                        bqVar.f21016k = new HashMap();
                        bqVar.f21017l = aaVarB.d();
                        bqVar.f21018m = strategyBeanC.f20611o;
                        bqVar.f21020o = aaVarB.g();
                        bqVar.f21021p = ab.c(context);
                        bqVar.f21022q = System.currentTimeMillis();
                        bqVar.f21024s = aaVarB.i();
                        bqVar.f21027v = aaVarB.g();
                        bqVar.f21028w = bqVar.f21021p;
                        bqVar.f21019n = "com.tencent.bugly";
                        bqVar.f21016k.put("A26", aaVarB.s());
                        Map<String, String> map = bqVar.f21016k;
                        StringBuilder sb = new StringBuilder();
                        sb.append(aa.C());
                        map.put("A62", sb.toString());
                        Map<String, String> map2 = bqVar.f21016k;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(aa.D());
                        map2.put("A63", sb2.toString());
                        Map<String, String> map3 = bqVar.f21016k;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(aaVarB.J);
                        map3.put("F11", sb3.toString());
                        Map<String, String> map4 = bqVar.f21016k;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(aaVarB.I);
                        map4.put("F12", sb4.toString());
                        bqVar.f21016k.put("D3", aaVarB.f20692q);
                        List<o> list = p.f21111b;
                        if (list != null) {
                            for (o oVar : list) {
                                String str2 = oVar.versionKey;
                                if (str2 != null && (str = oVar.version) != null) {
                                    bqVar.f21016k.put(str2, str);
                                }
                            }
                        }
                        bqVar.f21016k.put("G15", ap.d("G15", ""));
                        bqVar.f21016k.put("G10", ap.d("G10", ""));
                        bqVar.f21016k.put("D4", ap.d("D4", "0"));
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                Map<String, String> mapX = aaVarB.x();
                if (mapX != null) {
                    for (Map.Entry<String, String> entry : mapX.entrySet()) {
                        if (!TextUtils.isEmpty(entry.getValue())) {
                            bqVar.f21016k.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                return bqVar;
            } catch (Throwable th2) {
                if (!al.b(th2)) {
                    th2.printStackTrace();
                }
                return null;
            }
        }
        al.e("Can not create request pkg for parameters is invalid.", new Object[0]);
        return null;
    }

    public static byte[] a(Object obj) {
        try {
            e eVar = new e();
            eVar.b();
            eVar.a("utf-8");
            eVar.c();
            eVar.b("RqdServer");
            eVar.c(InvokeServiceData.CALL_TYPE_SYNC);
            eVar.a("detail", (String) obj);
            return eVar.a();
        } catch (Throwable th) {
            if (al.b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static br a(byte[] bArr) {
        if (bArr != null) {
            try {
                e eVar = new e();
                eVar.b();
                eVar.a("utf-8");
                eVar.a(bArr);
                Object objB = eVar.b("detail", new br());
                if (br.class.isInstance(objB)) {
                    return (br) br.class.cast(objB);
                }
                return null;
            } catch (Throwable th) {
                if (!al.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] a(m mVar) {
        try {
            l lVar = new l();
            lVar.a("utf-8");
            mVar.a(lVar);
            byte[] bArr = new byte[lVar.f21106a.position()];
            System.arraycopy(lVar.f21106a.array(), 0, bArr, 0, lVar.f21106a.position());
            return bArr;
        } catch (Throwable th) {
            if (al.b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}

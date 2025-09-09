package com.xiaomi.push;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.ReportProgressUtil;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class dd {

    /* renamed from: a, reason: collision with root package name */
    protected static Context f23570a;

    /* renamed from: a, reason: collision with other field name */
    private static a f263a;

    /* renamed from: a, reason: collision with other field name */
    private static dd f264a;

    /* renamed from: c, reason: collision with root package name */
    private static String f23572c;

    /* renamed from: d, reason: collision with root package name */
    private static String f23573d;

    /* renamed from: a, reason: collision with other field name */
    private long f266a;

    /* renamed from: a, reason: collision with other field name */
    private dc f267a;

    /* renamed from: a, reason: collision with other field name */
    protected b f268a;

    /* renamed from: a, reason: collision with other field name */
    private String f269a;

    /* renamed from: a, reason: collision with other field name */
    protected final Map<String, da> f270a;

    /* renamed from: b, reason: collision with other field name */
    private final long f271b;

    /* renamed from: b, reason: collision with other field name */
    private String f272b;

    /* renamed from: c, reason: collision with other field name */
    private long f273c;

    /* renamed from: b, reason: collision with root package name */
    protected static final Map<String, cz> f23571b = new HashMap();

    /* renamed from: a, reason: collision with other field name */
    protected static boolean f265a = false;

    public interface a {
        dd a(Context context, dc dcVar, b bVar, String str);
    }

    public interface b {
        String a(String str);
    }

    protected dd(Context context, dc dcVar, b bVar, String str) {
        this(context, dcVar, bVar, str, null, null);
    }

    public static synchronized dd a() {
        dd ddVar;
        ddVar = f264a;
        if (ddVar == null) {
            throw new IllegalStateException("the host manager is not initialized yet.");
        }
        return ddVar;
    }

    private String f() {
        return "host_fallbacks";
    }

    private String g() throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = f23570a.getPackageManager().getPackageInfo(f23570a.getPackageName(), 16384);
            return packageInfo != null ? packageInfo.versionName : "0";
        } catch (Exception unused) {
            return "0";
        }
    }

    public cz b(String str) {
        return a(str, true);
    }

    protected cz c(String str) {
        da daVar;
        cz czVarA;
        synchronized (this.f270a) {
            m274a();
            daVar = this.f270a.get(str);
        }
        if (daVar == null || (czVarA = daVar.a()) == null) {
            return null;
        }
        return czVarA;
    }

    public cz d(String str) {
        cz czVar;
        Map<String, cz> map = f23571b;
        synchronized (map) {
            czVar = map.get(str);
        }
        return czVar;
    }

    protected cz e(String str) {
        if (System.currentTimeMillis() - this.f273c <= this.f266a * 60000) {
            return null;
        }
        this.f273c = System.currentTimeMillis();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str);
        cz czVar = a(arrayList).get(0);
        if (czVar != null) {
            this.f266a = 0L;
            return czVar;
        }
        long j2 = this.f266a;
        if (j2 >= 15) {
            return null;
        }
        this.f266a = j2 + 1;
        return null;
    }

    protected dd(Context context, dc dcVar, b bVar, String str, String str2, String str3) {
        this.f270a = new HashMap();
        this.f269a = "0";
        this.f266a = 0L;
        this.f271b = 15L;
        this.f273c = 0L;
        this.f272b = "isp_prov_city_country_ip";
        this.f268a = bVar;
        if (dcVar == null) {
            this.f267a = new de(this);
        } else {
            this.f267a = dcVar;
        }
        this.f269a = str;
        f23572c = str2 == null ? context.getPackageName() : str2;
        f23573d = str3 == null ? g() : str3;
    }

    protected String b() {
        return "resolver.msg.xiaomi.net";
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m275b() throws JSONException {
        ArrayList<String> arrayList;
        synchronized (this.f270a) {
            try {
                m274a();
                arrayList = new ArrayList<>(this.f270a.keySet());
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    da daVar = this.f270a.get(arrayList.get(size));
                    if (daVar != null && daVar.a() != null) {
                        arrayList.remove(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        ArrayList<cz> arrayListA = a(arrayList);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (arrayListA.get(i2) != null) {
                a(arrayList.get(i2), arrayListA.get(i2));
            }
        }
    }

    protected String d() {
        BufferedInputStream bufferedInputStream;
        FileInputStream fileInputStream;
        File file;
        try {
            file = new File(f23570a.getFilesDir(), f());
        } catch (Throwable th) {
            th = th;
            bufferedInputStream = null;
            fileInputStream = null;
        }
        if (file.isFile()) {
            fileInputStream = new FileInputStream(file);
            try {
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                try {
                    String str = new String(h.a(m269a(), x.a((InputStream) bufferedInputStream)), StandardCharsets.UTF_8);
                    com.xiaomi.channel.commonutils.logger.b.b("load host fallbacks = " + str);
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        com.xiaomi.channel.commonutils.logger.b.m91a("load host exception " + th.getMessage());
                        return null;
                    } finally {
                        x.a((Closeable) bufferedInputStream);
                        x.a((Closeable) fileInputStream);
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream = null;
            }
        } else {
            x.a((Closeable) null);
            x.a((Closeable) null);
            return null;
        }
    }

    public static synchronized void a(a aVar) {
        f263a = aVar;
        f264a = null;
    }

    public String c() {
        StringBuilder sb = new StringBuilder();
        synchronized (this.f270a) {
            try {
                for (Map.Entry<String, da> entry : this.f270a.entrySet()) {
                    sb.append(entry.getKey());
                    sb.append(":\n");
                    sb.append(entry.getValue().toString());
                    sb.append("\n");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sb.toString();
    }

    public static synchronized void a(Context context, dc dcVar, b bVar, String str, String str2, String str3) {
        try {
            Context applicationContext = context.getApplicationContext();
            f23570a = applicationContext;
            if (applicationContext == null) {
                f23570a = context;
            }
            if (f264a == null) {
                a aVar = f263a;
                if (aVar == null) {
                    f264a = new dd(context, dcVar, bVar, str, str2, str3);
                } else {
                    f264a = aVar.a(context, dcVar, bVar, str);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    protected String e() {
        if ("com.xiaomi.xmsf".equals(f23572c)) {
            return f23572c;
        }
        return f23572c + ":pushservice";
    }

    /* renamed from: e, reason: collision with other method in class */
    public void m279e() {
        String next;
        synchronized (this.f270a) {
            try {
                Iterator<da> it = this.f270a.values().iterator();
                while (it.hasNext()) {
                    it.next().a(true);
                }
                while (true) {
                    Iterator<String> it2 = this.f270a.keySet().iterator();
                    while (it2.hasNext()) {
                        next = it2.next();
                        if (this.f270a.get(next).m264a().isEmpty()) {
                            break;
                        }
                    }
                    this.f270a.remove(next);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    static String m268a() {
        if (f23570a == null) {
            return "unknown";
        }
        try {
            bj bjVarM201a = bg.m201a();
            if (bjVarM201a == null) {
                return "unknown";
            }
            if (bjVarM201a.a() == 1) {
                return "WIFI-UNKNOWN";
            }
            return bjVarM201a.m211a() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + bjVarM201a.m213b();
        } catch (Throwable unused) {
            return "unknown";
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    protected void m276b(String str) {
        synchronized (this.f270a) {
            try {
                this.f270a.clear();
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optInt("ver") == 2) {
                    JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
                    if (jSONArrayOptJSONArray != null) {
                        for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                            da daVarA = new da().a(jSONArrayOptJSONArray.getJSONObject(i2));
                            this.f270a.put(daVarA.m263a(), daVarA);
                        }
                    }
                    JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("reserved");
                    if (jSONArrayOptJSONArray2 != null) {
                        for (int i3 = 0; i3 < jSONArrayOptJSONArray2.length(); i3++) {
                            JSONObject jSONObject2 = jSONArrayOptJSONArray2.getJSONObject(i3);
                            String strOptString = jSONObject2.optString("host");
                            if (!TextUtils.isEmpty(strOptString)) {
                                try {
                                    cz czVarA = new cz(strOptString).a(jSONObject2);
                                    f23571b.put(czVarA.f260b, czVarA);
                                    com.xiaomi.channel.commonutils.logger.b.m91a("load local reserved host for " + czVarA.f260b);
                                } catch (JSONException unused) {
                                    com.xiaomi.channel.commonutils.logger.b.m91a("parse reserved host fail.");
                                }
                            }
                        }
                    }
                } else {
                    throw new JSONException("Bad version");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m277c() {
        FileOutputStream fileOutputStreamOpenFileOutput;
        BufferedOutputStream bufferedOutputStream;
        Throwable th;
        Exception e2;
        synchronized (this.f270a) {
            BufferedOutputStream bufferedOutputStream2 = null;
            try {
                String string = m271a().toString();
                com.xiaomi.channel.commonutils.logger.b.b("persist host fallbacks = " + string);
                if (TextUtils.isEmpty(string)) {
                    fileOutputStreamOpenFileOutput = null;
                } else {
                    fileOutputStreamOpenFileOutput = f23570a.openFileOutput(f(), 0);
                    try {
                        bufferedOutputStream = new BufferedOutputStream(fileOutputStreamOpenFileOutput);
                    } catch (Exception e3) {
                        e = e3;
                        bufferedOutputStream = null;
                        e2 = e;
                        com.xiaomi.channel.commonutils.logger.b.m91a("persist bucket failure: " + e2.getMessage());
                        x.a(bufferedOutputStream);
                        x.a(fileOutputStreamOpenFileOutput);
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream = null;
                        th = th;
                        x.a(bufferedOutputStream);
                        x.a(fileOutputStreamOpenFileOutput);
                        throw th;
                    }
                    try {
                        try {
                            bufferedOutputStream.write(h.b(m269a(), string.getBytes(StandardCharsets.UTF_8)));
                            bufferedOutputStream.flush();
                            bufferedOutputStream2 = bufferedOutputStream;
                        } catch (Throwable th3) {
                            th = th3;
                            x.a(bufferedOutputStream);
                            x.a(fileOutputStreamOpenFileOutput);
                            throw th;
                        }
                    } catch (Exception e4) {
                        e2 = e4;
                        com.xiaomi.channel.commonutils.logger.b.m91a("persist bucket failure: " + e2.getMessage());
                        x.a(bufferedOutputStream);
                        x.a(fileOutputStreamOpenFileOutput);
                    }
                }
                x.a(bufferedOutputStream2);
            } catch (Exception e5) {
                e = e5;
                fileOutputStreamOpenFileOutput = null;
                bufferedOutputStream = null;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStreamOpenFileOutput = null;
                bufferedOutputStream = null;
            }
            x.a(fileOutputStreamOpenFileOutput);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public cz m270a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return a(new URL(str).getHost(), true);
        }
        throw new IllegalArgumentException("the url is empty");
    }

    /* renamed from: d, reason: collision with other method in class */
    public void m278d() {
        String strE = e();
        try {
            File file = new File(f23570a.getFilesDir(), strE);
            if (file.exists()) {
                boolean zDelete = file.delete();
                StringBuilder sb = new StringBuilder();
                sb.append("Delete old host fallbacks file ");
                sb.append(strE);
                sb.append(zDelete ? " successful." : " failed.");
                com.xiaomi.channel.commonutils.logger.b.m91a(sb.toString());
            } else {
                com.xiaomi.channel.commonutils.logger.b.b("Old host fallbacks file " + strE + " does not exist.");
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Delete old host fallbacks file " + strE + " error: " + e2.getMessage());
        }
    }

    public cz a(String str, boolean z2) {
        cz czVarE;
        com.xiaomi.channel.commonutils.logger.b.b("HostManager", "-->getFallbacksByHost(): host=", str, ", fetchRemoteIfNeed=", Boolean.valueOf(z2));
        if (!TextUtils.isEmpty(str)) {
            if (!this.f267a.a(str)) {
                return null;
            }
            cz czVarC = c(str);
            return (czVarC == null || !czVarC.b()) ? (z2 && bg.b(f23570a) && (czVarE = e(str)) != null) ? czVarE : new df(this, str, czVarC) : czVarC;
        }
        throw new IllegalArgumentException("the host is empty");
    }

    private ArrayList<cz> a(ArrayList<String> arrayList) throws JSONException {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        m279e();
        synchronized (this.f270a) {
            try {
                m274a();
                for (String str : this.f270a.keySet()) {
                    if (!arrayList.contains(str)) {
                        arrayList.add(str);
                    }
                }
            } finally {
            }
        }
        Map<String, cz> map = f23571b;
        synchronized (map) {
            try {
                for (Object obj : map.values().toArray()) {
                    cz czVar = (cz) obj;
                    if (!czVar.b()) {
                        f23571b.remove(czVar.f260b);
                    }
                }
            } finally {
            }
        }
        if (!arrayList.contains(b())) {
            arrayList.add(b());
        }
        ArrayList<cz> arrayList2 = new ArrayList<>(arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(null);
        }
        try {
            String str2 = bg.e(f23570a) ? "wifi" : "wap";
            String strA = a(arrayList, str2, this.f269a, true);
            if (!TextUtils.isEmpty(strA)) {
                JSONObject jSONObject3 = new JSONObject(strA);
                com.xiaomi.channel.commonutils.logger.b.b(strA);
                if (ReportProgressUtil.CODE_OK.equalsIgnoreCase(jSONObject3.getString(ExifInterface.LATITUDE_SOUTH))) {
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("R");
                    String string = jSONObject4.getString("province");
                    String string2 = jSONObject4.getString("city");
                    String string3 = jSONObject4.getString("isp");
                    String string4 = jSONObject4.getString("ip");
                    String string5 = jSONObject4.getString("country");
                    JSONObject jSONObject5 = jSONObject4.getJSONObject(str2);
                    com.xiaomi.channel.commonutils.logger.b.c("get bucket: net=" + string3 + ", hosts=" + jSONObject5.toString());
                    int i3 = 0;
                    while (i3 < arrayList.size()) {
                        String str3 = arrayList.get(i3);
                        JSONArray jSONArrayOptJSONArray = jSONObject5.optJSONArray(str3);
                        if (jSONArrayOptJSONArray == null) {
                            com.xiaomi.channel.commonutils.logger.b.m91a("no bucket found for " + str3);
                            jSONObject = jSONObject5;
                        } else {
                            cz czVar2 = new cz(str3);
                            int i4 = 0;
                            while (i4 < jSONArrayOptJSONArray.length()) {
                                String string6 = jSONArrayOptJSONArray.getString(i4);
                                if (TextUtils.isEmpty(string6)) {
                                    jSONObject2 = jSONObject5;
                                } else {
                                    jSONObject2 = jSONObject5;
                                    czVar2.a(new di(string6, jSONArrayOptJSONArray.length() - i4));
                                }
                                i4++;
                                jSONObject5 = jSONObject2;
                            }
                            jSONObject = jSONObject5;
                            arrayList2.set(i3, czVar2);
                            czVar2.f23564g = string5;
                            czVar2.f23560c = string;
                            czVar2.f23562e = string3;
                            czVar2.f23563f = string4;
                            czVar2.f23561d = string2;
                            if (jSONObject4.has("stat-percent")) {
                                czVar2.a(jSONObject4.getDouble("stat-percent"));
                            }
                            if (jSONObject4.has("stat-domain")) {
                                czVar2.b(jSONObject4.getString("stat-domain"));
                            }
                            if (jSONObject4.has(RemoteMessageConst.TTL)) {
                                czVar2.a(jSONObject4.getInt(RemoteMessageConst.TTL) * 1000);
                            }
                            m273a(czVar2.a());
                        }
                        i3++;
                        jSONObject5 = jSONObject;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject4.optJSONObject("reserved");
                    if (jSONObjectOptJSONObject != null) {
                        long j2 = jSONObject4.has("reserved-ttl") ? jSONObject4.getInt("reserved-ttl") * 1000 : 604800000L;
                        Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray(next);
                            if (jSONArrayOptJSONArray2 == null) {
                                com.xiaomi.channel.commonutils.logger.b.m91a("no bucket found for " + next);
                            } else {
                                cz czVar3 = new cz(next);
                                czVar3.a(j2);
                                for (int i5 = 0; i5 < jSONArrayOptJSONArray2.length(); i5++) {
                                    String string7 = jSONArrayOptJSONArray2.getString(i5);
                                    if (!TextUtils.isEmpty(string7)) {
                                        czVar3.a(new di(string7, jSONArrayOptJSONArray2.length() - i5));
                                    }
                                }
                                Map<String, cz> map2 = f23571b;
                                synchronized (map2) {
                                    try {
                                        if (this.f267a.a(next)) {
                                            map2.put(next, czVar3);
                                        }
                                    } finally {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("failed to get bucket " + e2.getMessage());
        }
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            cz czVar4 = arrayList2.get(i6);
            if (czVar4 != null) {
                a(arrayList.get(i6), czVar4);
            }
        }
        m277c();
        return arrayList2;
    }

    protected String a(ArrayList<String> arrayList, String str, String str2, boolean z2) throws IOException {
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<bf> arrayList3 = new ArrayList();
        arrayList3.add(new bd("type", str));
        if (str.equals("wap")) {
            arrayList3.add(new bd("conpt", a(bg.m204a(f23570a))));
        }
        if (z2) {
            arrayList3.add(new bd("reserved", "1"));
        }
        arrayList3.add(new bd(DeviceCommonConstants.KEY_DEVICE_ID, str2));
        arrayList3.add(new bd(AlinkConstants.KEY_LIST, bp.a(arrayList, ",")));
        arrayList3.add(new bd("countrycode", com.xiaomi.push.service.a.a(f23570a).b()));
        arrayList3.add(new bd("push_sdk_vc", String.valueOf(BuildConfig.VERSION_CODE)));
        String strB = b();
        cz czVarC = c(strB);
        String str3 = String.format(Locale.US, "https://%1$s/gslb/?ver=5.0", strB);
        if (czVarC == null) {
            arrayList2.add(str3);
            Map<String, cz> map = f23571b;
            synchronized (map) {
                try {
                    cz czVar = map.get(strB);
                    if (czVar != null) {
                        Iterator<String> it = czVar.a(true).iterator();
                        while (it.hasNext()) {
                            arrayList2.add(String.format(Locale.US, "https://%1$s/gslb/?ver=5.0", it.next()));
                        }
                    }
                } finally {
                }
            }
        } else {
            arrayList2 = czVarC.a(str3);
        }
        Iterator<String> it2 = arrayList2.iterator();
        IOException e2 = null;
        while (it2.hasNext()) {
            Uri.Builder builderBuildUpon = Uri.parse(it2.next()).buildUpon();
            for (bf bfVar : arrayList3) {
                builderBuildUpon.appendQueryParameter(bfVar.a(), bfVar.b());
            }
            try {
                b bVar = this.f268a;
                if (bVar == null) {
                    return bg.a(f23570a, new URL(builderBuildUpon.toString()));
                }
                return bVar.a(builderBuildUpon.toString());
            } catch (IOException e3) {
                e2 = e3;
            }
        }
        if (e2 == null) {
            return null;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("network exception: " + e2.getMessage());
        throw e2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m272a() {
        synchronized (this.f270a) {
            this.f270a.clear();
        }
    }

    public void a(String str, cz czVar) {
        if (!TextUtils.isEmpty(str) && czVar != null) {
            if (this.f267a.a(str)) {
                synchronized (this.f270a) {
                    try {
                        m274a();
                        if (this.f270a.containsKey(str)) {
                            this.f270a.get(str).a(czVar);
                        } else {
                            da daVar = new da(str);
                            daVar.a(czVar);
                            this.f270a.put(str, daVar);
                        }
                    } finally {
                    }
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("the argument is invalid " + str + ", " + czVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    protected boolean m274a() {
        synchronized (this.f270a) {
            try {
                if (f265a) {
                    return true;
                }
                f265a = true;
                this.f270a.clear();
                try {
                    String strD = d();
                    if (!TextUtils.isEmpty(strD)) {
                        m276b(strD);
                        com.xiaomi.channel.commonutils.logger.b.b("loading the new hosts succeed");
                        return true;
                    }
                } catch (Throwable th) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("load bucket failure: " + th.getMessage());
                }
                return false;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public static void a(String str, String str2) {
        Map<String, cz> map = f23571b;
        cz czVar = map.get(str);
        synchronized (map) {
            try {
                if (czVar == null) {
                    cz czVar2 = new cz(str);
                    czVar2.a(604800000L);
                    czVar2.m261a(str2);
                    map.put(str, czVar2);
                } else {
                    czVar.m261a(str2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private byte[] m269a() {
        return bo.m219a(f23570a.getPackageName() + "_key_salt");
    }

    static String a(String str) throws UnsupportedEncodingException {
        try {
            int length = str.length();
            byte[] bytes = str.getBytes("UTF-8");
            for (int i2 = 0; i2 < bytes.length; i2++) {
                byte b2 = bytes[i2];
                int i3 = b2 & 240;
                if (i3 != 240) {
                    bytes[i2] = (byte) (((b2 & 15) ^ ((byte) (((b2 >> 4) + length) & 15))) | i3);
                }
            }
            return new String(bytes);
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m273a(String str) {
        this.f272b = str;
    }

    /* renamed from: a, reason: collision with other method in class */
    protected JSONObject m271a() {
        JSONObject jSONObject;
        synchronized (this.f270a) {
            try {
                jSONObject = new JSONObject();
                jSONObject.put("ver", 2);
                JSONArray jSONArray = new JSONArray();
                Iterator<da> it = this.f270a.values().iterator();
                while (it.hasNext()) {
                    jSONArray.put(it.next().m265a());
                }
                jSONObject.put("data", jSONArray);
                JSONArray jSONArray2 = new JSONArray();
                Iterator<cz> it2 = f23571b.values().iterator();
                while (it2.hasNext()) {
                    jSONArray2.put(it2.next().m260a());
                }
                jSONObject.put("reserved", jSONArray2);
            } catch (Throwable th) {
                throw th;
            }
        }
        return jSONObject;
    }
}

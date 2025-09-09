package com.xiaomi.push.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.ft;
import com.xiaomi.push.hp;
import com.xiaomi.push.hq;
import com.xiaomi.push.hr;
import com.xiaomi.push.hu;
import com.xiaomi.push.id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;

/* loaded from: classes4.dex */
public class ServiceClient {

    /* renamed from: a, reason: collision with other field name */
    private static ServiceClient f941a;

    /* renamed from: a, reason: collision with other field name */
    private static String f942a;

    /* renamed from: a, reason: collision with other field name */
    private Context f943a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f946a;

    /* renamed from: b, reason: collision with other field name */
    private Messenger f947b;

    /* renamed from: b, reason: collision with root package name */
    private static String f24415b = id.a(5) + Constants.ACCEPT_TIME_SEPARATOR_SERVER;

    /* renamed from: a, reason: collision with root package name */
    private static long f24414a = 0;

    /* renamed from: a, reason: collision with other field name */
    private Messenger f944a = null;

    /* renamed from: a, reason: collision with other field name */
    private List<Message> f945a = new ArrayList();

    /* renamed from: b, reason: collision with other field name */
    private boolean f948b = false;

    private ServiceClient(Context context) {
        this.f946a = false;
        this.f943a = context.getApplicationContext();
        if (m693a()) {
            com.xiaomi.channel.commonutils.logger.b.c("use miui push service");
            this.f946a = true;
        }
    }

    private void b() {
        this.f943a.getPackageManager().setComponentEnabledSetting(new ComponentName(this.f943a, (Class<?>) XMPushService.class), 1, 1);
    }

    public static ServiceClient getInstance(Context context) {
        if (f941a == null) {
            f941a = new ServiceClient(context);
        }
        return f941a;
    }

    public static String getSession() {
        return f942a;
    }

    public static void setSession(String str) {
        f942a = str;
    }

    public boolean batchSendMessage(hr[] hrVarArr, boolean z2) {
        if (!com.xiaomi.push.bg.b(this.f943a)) {
            return false;
        }
        Intent intentA = a();
        int length = hrVarArr.length;
        Bundle[] bundleArr = new Bundle[length];
        for (int i2 = 0; i2 < hrVarArr.length; i2++) {
            String strA = ft.a();
            if (!TextUtils.isEmpty(strA)) {
                hp hpVar = new hp("pf", null, null, null);
                hp hpVar2 = new hp("sent", null, null, null);
                hpVar2.m483a(strA);
                hpVar.a(hpVar2);
                hrVarArr[i2].a(hpVar);
            }
            com.xiaomi.channel.commonutils.logger.b.c("SEND:" + hrVarArr[i2].mo485a());
            bundleArr[i2] = hrVarArr[i2].a();
        }
        if (length <= 0) {
            return false;
        }
        intentA.setAction(bj.f24523g);
        intentA.putExtra(bj.J, f942a);
        intentA.putExtra("ext_packets", bundleArr);
        intentA.putExtra("ext_encrypt", z2);
        return startServiceSafely(intentA);
    }

    public void checkAlive() {
        Intent intentA = a();
        intentA.setAction("com.xiaomi.push.check_alive");
        startServiceSafely(intentA);
    }

    public boolean closeChannel() {
        Intent intentA = a();
        intentA.setAction(bj.f24525i);
        return startServiceSafely(intentA);
    }

    @Deprecated
    public boolean forceReconnection(String str, String str2, String str3, String str4, String str5, boolean z2, List<NameValuePair> list, List<NameValuePair> list2) {
        return forceReconnection(str, str2, str3, str4, str5, z2, a(list), a(list2));
    }

    public boolean isMiuiPushServiceEnabled() {
        return this.f946a;
    }

    public boolean notifyMessage(Bundle bundle, String str, String str2) {
        if (bundle == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Failed to notify message: bundle|userId|chid may be empty");
            return false;
        }
        Intent intentA = a();
        intentA.setAction(bj.f24531o);
        intentA.putExtras(bundle);
        com.xiaomi.channel.commonutils.logger.b.e("notify: chid=" + str2 + " bundle:" + bundle);
        return startServiceSafely(intentA);
    }

    @Deprecated
    public int openChannel(String str, String str2, String str3, String str4, String str5, boolean z2, List<NameValuePair> list, List<NameValuePair> list2) {
        return openChannel(str, str2, str3, str4, str5, a(list), a(list2), z2);
    }

    @Deprecated
    public void resetConnection(String str, String str2, String str3, String str4, String str5, boolean z2, List<NameValuePair> list, List<NameValuePair> list2) {
        resetConnection(str, str2, str3, str4, str5, z2, a(list), a(list2));
    }

    public boolean sendIQ(hq hqVar) {
        if (!com.xiaomi.push.bg.b(this.f943a)) {
            return false;
        }
        Intent intentA = a();
        Bundle bundleA = hqVar.a();
        if (bundleA == null) {
            return false;
        }
        com.xiaomi.channel.commonutils.logger.b.c("SEND:" + hqVar.mo485a());
        intentA.setAction(bj.f24522f);
        intentA.putExtra(bj.J, f942a);
        intentA.putExtra("ext_packet", bundleA);
        return startServiceSafely(intentA);
    }

    public boolean sendMessage(hr hrVar, boolean z2) {
        if (!com.xiaomi.push.bg.b(this.f943a)) {
            return false;
        }
        Intent intentA = a();
        String strA = ft.a();
        if (!TextUtils.isEmpty(strA)) {
            hp hpVar = new hp("pf", null, null, null);
            hp hpVar2 = new hp("sent", null, null, null);
            hpVar2.m483a(strA);
            hpVar.a(hpVar2);
            hrVar.a(hpVar);
        }
        Bundle bundleA = hrVar.a();
        if (bundleA == null) {
            return false;
        }
        com.xiaomi.channel.commonutils.logger.b.c("SEND:" + hrVar.mo485a());
        intentA.setAction(bj.f24521e);
        intentA.putExtra(bj.J, f942a);
        intentA.putExtra("ext_packet", bundleA);
        intentA.putExtra("ext_encrypt", z2);
        return startServiceSafely(intentA);
    }

    public boolean sendPresence(hu huVar) {
        if (!com.xiaomi.push.bg.b(this.f943a)) {
            return false;
        }
        Intent intentA = a();
        Bundle bundleA = huVar.a();
        if (bundleA == null) {
            return false;
        }
        com.xiaomi.channel.commonutils.logger.b.c("SEND:" + huVar.mo485a());
        intentA.setAction(bj.f24524h);
        intentA.putExtra(bj.J, f942a);
        intentA.putExtra("ext_packet", bundleA);
        return startServiceSafely(intentA);
    }

    public void setMessenger(Messenger messenger) {
        this.f944a = messenger;
    }

    public boolean startServiceSafely(Intent intent) {
        try {
            if (com.xiaomi.push.j.m549a() || Build.VERSION.SDK_INT < 26) {
                this.f943a.startService(intent);
                return true;
            }
            m692a(intent);
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    @Deprecated
    public void updateChannelInfo(String str, List<NameValuePair> list, List<NameValuePair> list2) {
        updateChannelInfo(str, a(list), a(list2));
    }

    public boolean forceReconnection(String str, String str2, String str3, String str4, String str5, boolean z2, Map<String, String> map, Map<String, String> map2) {
        Intent intentA = a();
        intentA.setAction(bj.f24526j);
        a(intentA, str, str2, str3, str4, str5, z2, map, map2);
        return startServiceSafely(intentA);
    }

    public int openChannel(String str, String str2, String str3, String str4, String str5, Map<String, String> map, Map<String, String> map2, boolean z2) {
        Intent intentA = a();
        intentA.setAction(bj.f24520d);
        a(intentA, str, str2, str3, str4, str5, z2, map, map2);
        startServiceSafely(intentA);
        return 0;
    }

    public void resetConnection(String str, String str2, String str3, String str4, String str5, boolean z2, Map<String, String> map, Map<String, String> map2) {
        Intent intentA = a();
        intentA.setAction(bj.f24527k);
        a(intentA, str, str2, str3, str4, str5, z2, map, map2);
        startServiceSafely(intentA);
    }

    public void updateChannelInfo(String str, Map<String, String> map, Map<String, String> map2) {
        Intent intentA = a();
        intentA.setAction(bj.f24528l);
        if (map != null) {
            String strA = a(map);
            if (!TextUtils.isEmpty(strA)) {
                intentA.putExtra(bj.D, strA);
            }
        }
        if (map2 != null) {
            String strA2 = a(map2);
            if (!TextUtils.isEmpty(strA2)) {
                intentA.putExtra(bj.E, strA2);
            }
        }
        intentA.putExtra(bj.f24538v, str);
        startServiceSafely(intentA);
    }

    public boolean closeChannel(String str) {
        Intent intentA = a();
        intentA.setAction(bj.f24525i);
        intentA.putExtra(bj.f24538v, str);
        return startServiceSafely(intentA);
    }

    private Map<String, String> a(List<NameValuePair> list) {
        HashMap map = new HashMap();
        if (list != null && list.size() > 0) {
            for (NameValuePair nameValuePair : list) {
                if (nameValuePair != null) {
                    map.put(nameValuePair.getName(), nameValuePair.getValue());
                }
            }
        }
        return map;
    }

    public boolean closeChannel(String str, String str2) {
        Intent intentA = a();
        intentA.setAction(bj.f24525i);
        intentA.putExtra(bj.f24538v, str);
        intentA.putExtra(bj.f24535s, str2);
        return startServiceSafely(intentA);
    }

    private void a(Intent intent, String str, String str2, String str3, String str4, String str5, boolean z2, Map<String, String> map, Map<String, String> map2) {
        intent.putExtra(bj.f24535s, str);
        intent.putExtra(bj.f24538v, str2);
        intent.putExtra(bj.f24542z, str3);
        intent.putExtra(bj.B, str5);
        intent.putExtra(bj.A, str4);
        intent.putExtra(bj.C, z2);
        intent.putExtra(bj.J, f942a);
        intent.putExtra(bj.N, this.f944a);
        if (map != null && map.size() > 0) {
            String strA = a(map);
            if (!TextUtils.isEmpty(strA)) {
                intent.putExtra(bj.D, strA);
            }
        }
        if (map2 == null || map2.size() <= 0) {
            return;
        }
        String strA2 = a(map2);
        if (TextUtils.isEmpty(strA2)) {
            return;
        }
        intent.putExtra(bj.E, strA2);
    }

    public boolean sendMessage(byte[] bArr, String str, String str2) {
        String strSubstring;
        if (com.xiaomi.push.bg.b(this.f943a) && bArr != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            Intent intentA = a();
            intentA.setAction(bj.f24521e);
            intentA.putExtra(bj.J, f942a);
            intentA.putExtra("ext_raw_packet", bArr);
            int iIndexOf = str.indexOf("@");
            String strSubstring2 = null;
            String strSubstring3 = iIndexOf != -1 ? str.substring(0, iIndexOf) : null;
            int iLastIndexOf = str.lastIndexOf("/");
            if (iLastIndexOf != -1) {
                strSubstring2 = str.substring(iIndexOf + 1, iLastIndexOf);
                strSubstring = str.substring(iLastIndexOf + 1);
            } else {
                strSubstring = null;
            }
            intentA.putExtra(bj.f24535s, strSubstring3);
            intentA.putExtra(bj.f24536t, strSubstring2);
            intentA.putExtra(bj.f24537u, strSubstring);
            StringBuilder sb = new StringBuilder();
            sb.append(f24415b);
            long j2 = f24414a;
            f24414a = 1 + j2;
            sb.append(j2);
            String string = sb.toString();
            intentA.putExtra("ext_pkt_id", string);
            intentA.putExtra("ext_chid", str2);
            com.xiaomi.channel.commonutils.logger.b.e("SEND: chid=" + str2 + ", packetId=" + string);
            return startServiceSafely(intentA);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("Failed to send message: message|userId|chid may be empty, or the network is unavailable.");
        return false;
    }

    private String a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        int i2 = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            if (i2 < map.size()) {
                sb.append(",");
            }
            i2++;
        }
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m693a() throws PackageManager.NameNotFoundException {
        if (com.xiaomi.push.aa.f162a) {
            return false;
        }
        try {
            PackageInfo packageInfo = this.f943a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 104;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private String m689a() {
        try {
            if (this.f943a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 106) {
                return "com.xiaomi.push.service.XMPushService";
            }
            return "com.xiaomi.xmsf.push.service.XMPushService";
        } catch (Exception unused) {
            return "com.xiaomi.xmsf.push.service.XMPushService";
        }
    }

    private Intent a() {
        if (isMiuiPushServiceEnabled()) {
            Intent intent = new Intent();
            intent.setPackage("com.xiaomi.xmsf");
            intent.setClassName("com.xiaomi.xmsf", m689a());
            intent.putExtra(bj.F, this.f943a.getPackageName());
            m691a();
            return intent;
        }
        Intent intent2 = new Intent(this.f943a, (Class<?>) XMPushService.class);
        intent2.putExtra(bj.F, this.f943a.getPackageName());
        b();
        return intent2;
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m691a() {
        this.f943a.getPackageManager().setComponentEnabledSetting(new ComponentName(this.f943a, (Class<?>) XMPushService.class), 2, 1);
    }

    /* renamed from: a, reason: collision with other method in class */
    private synchronized void m692a(Intent intent) {
        try {
            if (this.f948b) {
                Message messageA = a(intent);
                if (this.f945a.size() >= 50) {
                    this.f945a.remove(0);
                }
                this.f945a.add(messageA);
                return;
            }
            if (this.f947b == null) {
                this.f943a.bindService(intent, new bv(this), 1);
                this.f948b = true;
                this.f945a.clear();
                this.f945a.add(a(intent));
            } else {
                try {
                    this.f947b.send(a(intent));
                } catch (RemoteException unused) {
                    this.f947b = null;
                    this.f948b = false;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private Message a(Intent intent) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 17;
        messageObtain.obj = intent;
        return messageObtain;
    }
}

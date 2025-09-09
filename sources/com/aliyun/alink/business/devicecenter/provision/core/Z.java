package com.aliyun.alink.business.devicecenter.provision.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConfigParams;
import com.aliyun.alink.business.devicecenter.config.phoneap.AlinkAESHelper;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.utils.AlinkWifiSolutionUtils;
import com.aliyun.alink.business.devicecenter.utils.DictionaryEncryptionUtils;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class Z {

    /* renamed from: a, reason: collision with root package name */
    public static String f10553a = "AlinkP2PConfigStrategy";

    /* renamed from: k, reason: collision with root package name */
    public Context f10563k;

    /* renamed from: b, reason: collision with root package name */
    public String f10554b = null;

    /* renamed from: c, reason: collision with root package name */
    public String f10555c = null;

    /* renamed from: d, reason: collision with root package name */
    public String f10556d = null;

    /* renamed from: e, reason: collision with root package name */
    public BroadcastReceiver f10557e = null;

    /* renamed from: f, reason: collision with root package name */
    public WifiP2pManager f10558f = null;

    /* renamed from: g, reason: collision with root package name */
    public WifiP2pManager.Channel f10559g = null;

    /* renamed from: h, reason: collision with root package name */
    public AtomicBoolean f10560h = new AtomicBoolean(false);

    /* renamed from: i, reason: collision with root package name */
    public int f10561i = 0;

    /* renamed from: j, reason: collision with root package name */
    public String f10562j = null;

    /* renamed from: l, reason: collision with root package name */
    public DCAlibabaConfigParams f10564l = null;

    public Z(Context context) {
        this.f10563k = context;
    }

    public final void h() {
        ALog.d(f10553a, "unRegisterP2PReceiver(),call");
        try {
            if (this.f10557e != null) {
                ALog.d(f10553a, "unRegisterP2PReceiver(),exe");
                this.f10563k.unregisterReceiver(this.f10557e);
                this.f10557e = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w(f10553a, "unRegisterP2PReceiver(),error" + e2);
        }
    }

    public final void i() {
        ALog.d(f10553a, "p2p unSupportMethod()");
    }

    public void a(DCAlibabaConfigParams dCAlibabaConfigParams) throws IllegalAccessException, IllegalArgumentException, UnsupportedEncodingException, InvocationTargetException {
        if (dCAlibabaConfigParams == null) {
            ALog.d(f10553a, "provision params null.");
            return;
        }
        this.f10564l = dCAlibabaConfigParams;
        this.f10562j = dCAlibabaConfigParams.productEncryptKey;
        if (this.f10560h.compareAndSet(false, true)) {
            b();
            c();
        }
    }

    public final void b() {
        if (this.f10558f == null) {
            WifiP2pManager wifiP2pManager = (WifiP2pManager) this.f10563k.getSystemService("wifip2p");
            this.f10558f = wifiP2pManager;
            Context context = this.f10563k;
            this.f10559g = wifiP2pManager.initialize(context, context.getMainLooper(), null);
        }
    }

    public final void c() throws IllegalAccessException, IllegalArgumentException, UnsupportedEncodingException, InvocationTargetException {
        DCAlibabaConfigParams dCAlibabaConfigParams = this.f10564l;
        String strA = a(dCAlibabaConfigParams.ssid, dCAlibabaConfigParams.password, true);
        if (TextUtils.isEmpty(strA)) {
            ALog.w(f10553a, "startProvosion(),data is empty");
            i();
        } else {
            e();
            this.f10556d = strA;
            PerformanceLog.trace(f10553a, AlinkConstants.KEY_BROADCAST, PerformanceLog.getJsonObject("type", "p2p"));
            a(strA);
        }
    }

    public final void d() {
        ALog.d(f10553a, "registerP2PReceiver(),call,originName=" + this.f10554b);
        if (this.f10557e == null) {
            ALog.d(f10553a, "registerP2PReceiver(),exe");
            this.f10557e = new V(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
            Context context = this.f10563k;
            if (context != null) {
                context.registerReceiver(this.f10557e, intentFilter);
            }
        }
    }

    public final void e() {
        d();
    }

    public final void f() {
        ALog.d(f10553a, "stopExposeData()");
        try {
            WifiP2pManager wifiP2pManager = this.f10558f;
            if (wifiP2pManager != null) {
                wifiP2pManager.stopPeerDiscovery(this.f10559g, new Y(this));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w(f10553a, "stopExposeData(),error" + e2);
        }
    }

    public void g() {
        ALog.d(f10553a, "stopProvosion(),call");
        try {
            this.f10554b = "wifi";
            if (this.f10560h.compareAndSet(true, false)) {
                a(this.f10554b);
                f();
            }
            this.f10561i = 0;
            h();
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w(f10553a, "stop error," + e2);
        }
    }

    public final String a(String str, String str2, boolean z2) throws UnsupportedEncodingException {
        byte[] encode;
        try {
            byte[] bytes = str.getBytes("UTF-8");
            if (!TextUtils.isEmpty(str2) && z2) {
                byte[] bArrEncrypt128CFB = AlinkAESHelper.encrypt128CFB(str2, this.f10562j);
                ALog.d(f10553a, "packetDataForP2P(), passwd encrypted data = ");
                AlinkWifiSolutionUtils.printByteArray(bArrEncrypt128CFB);
                encode = AlinkWifiSolutionUtils.eightBitsToSevenBits(bArrEncrypt128CFB);
                ALog.d(f10553a, "packetDataForP2P(), passwd encrypted 8->7 data = ");
                AlinkWifiSolutionUtils.printByteArray(encode);
            } else {
                encode = DictionaryEncryptionUtils.getEncode(str2.getBytes("UTF-8"));
            }
            int length = bytes.length + 3 + encode.length + a(encode);
            if (length > 32) {
                String str3 = f10553a;
                StringBuilder sb = new StringBuilder();
                sb.append("packetDataForP2P(), too long, length = ");
                sb.append(length);
                ALog.d(str3, sb.toString());
                return null;
            }
            int length2 = bytes.length + 3 + encode.length;
            byte[] bArr = new byte[length2];
            byte length3 = (byte) bytes.length;
            if (z2) {
                length3 = (byte) (length3 | 32);
            }
            bArr[0] = length3;
            byte[] encode2 = DictionaryEncryptionUtils.getEncode(bytes);
            int i2 = 0;
            int i3 = 1;
            while (i2 < encode2.length) {
                bArr[i3] = encode2[i2];
                i2++;
                i3++;
            }
            int i4 = 0;
            while (i4 < encode.length) {
                bArr[i3] = encode[i4];
                i4++;
                i3++;
            }
            short s2 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                s2 = (short) (s2 + (bArr[i5] & 255));
            }
            bArr[i3] = (byte) ((s2 >> 6) & 63);
            bArr[i3 + 1] = (byte) (s2 & 63);
            if ((bArr[i3] & 255) == 0) {
                bArr[i3] = 1;
            }
            int i6 = i3 + 1;
            if ((bArr[i6] & 255) == 0) {
                bArr[i6] = 1;
            }
            String str4 = f10553a;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("packetDataForP2P(), deviceNameHexString = ");
            sb2.append(AlinkWifiSolutionUtils.bytesToHexString(bArr));
            ALog.i(str4, sb2.toString());
            AlinkWifiSolutionUtils.printByteArray(bArr);
            String str5 = new String(bArr, "UTF-8");
            String str6 = f10553a;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("packetDataForP2P(), deviceName = ");
            sb3.append(str5);
            ALog.d(str6, sb3.toString());
            this.f10561i = length2;
            byte[] bytes2 = str5.getBytes("UTF-8");
            if (this.f10561i != (bytes2 != null ? bytes2.length : 0)) {
                ALog.d(f10553a, "packetDataForP2P(),UTF8 断层.");
            }
            return str5;
        } catch (Exception e2) {
            ALog.w(f10553a, "packetDataForP2P(),error.");
            e2.printStackTrace();
            return null;
        }
    }

    public final int a(byte[] bArr) {
        if (bArr.length == 0) {
            return 0;
        }
        int i2 = 0;
        for (byte b2 : bArr) {
            if ((b2 & 255) == 0) {
                i2++;
            }
        }
        ALog.d(f10553a, "count0InByte,count=" + i2);
        return i2;
    }

    public final void a(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            String str2 = f10553a;
            StringBuilder sb = new StringBuilder();
            sb.append("changeDeviceName(),name");
            sb.append(str);
            ALog.d(str2, sb.toString());
            this.f10558f.getClass().getMethod("setDeviceName", WifiP2pManager.Channel.class, String.class, WifiP2pManager.ActionListener.class).invoke(this.f10558f, this.f10559g, str, new W(this, str));
        } catch (NoSuchMethodException unused) {
            ALog.w(f10553a, "p2p unSupportMethod changeDeviceName() NoSuchMethodException.");
            i();
        } catch (Exception e2) {
            ALog.w(f10553a, "p2p unSupportMethod changeDeviceName() catch error." + e2);
            e2.printStackTrace();
        }
    }

    public final void a() {
        ALog.d(f10553a, "exposeData()");
        this.f10558f.discoverPeers(this.f10559g, new X(this));
    }
}

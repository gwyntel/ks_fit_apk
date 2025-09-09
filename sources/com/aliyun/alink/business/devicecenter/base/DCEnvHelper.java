package com.aliyun.alink.business.devicecenter.base;

import com.alibaba.ailabs.iot.aisbase.env.AppEnv;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.ReflectUtils;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class DCEnvHelper {

    /* renamed from: a, reason: collision with root package name */
    public static AtomicBoolean f10156a = new AtomicBoolean(true);

    /* renamed from: b, reason: collision with root package name */
    public static AtomicBoolean f10157b = new AtomicBoolean(false);

    /* renamed from: c, reason: collision with root package name */
    public static AtomicBoolean f10158c = new AtomicBoolean(false);

    /* renamed from: d, reason: collision with root package name */
    public static AtomicBoolean f10159d = new AtomicBoolean(false);

    /* renamed from: e, reason: collision with root package name */
    public static AtomicBoolean f10160e = new AtomicBoolean(false);

    /* renamed from: f, reason: collision with root package name */
    public static AtomicBoolean f10161f = new AtomicBoolean(false);

    /* renamed from: g, reason: collision with root package name */
    public static AtomicBoolean f10162g = new AtomicBoolean(false);

    /* renamed from: h, reason: collision with root package name */
    public static AtomicBoolean f10163h = new AtomicBoolean(false);

    /* renamed from: i, reason: collision with root package name */
    public static AtomicBoolean f10164i = new AtomicBoolean(false);

    /* renamed from: j, reason: collision with root package name */
    public static AtomicBoolean f10165j = new AtomicBoolean(false);

    /* renamed from: k, reason: collision with root package name */
    public static AtomicBoolean f10166k = new AtomicBoolean(false);

    public static boolean getHasLogExtraSDK() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10165j.get();
    }

    public static boolean getHasUTAbility() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10166k.get();
    }

    public static boolean hasApiClient() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10159d.get();
    }

    public static boolean hasApiClientBiz() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10160e.get();
    }

    public static boolean hasBreeze() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10158c.get();
    }

    public static boolean hasCoapAbilitiAB() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10164i.get();
    }

    public static boolean hasMeshScanAbility() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10161f.get();
    }

    public static boolean hasTGBleScanAbilityAB() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10162g.get();
    }

    public static void initEnv() {
        try {
            if (ReflectUtils.hasClass("com.aliyun.iot.breeze.biz.BreezeHelper")) {
                f10158c.set(true);
            }
            if (ReflectUtils.hasClass("com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient")) {
                f10159d.set(true);
            }
            if (ReflectUtils.hasClass("com.aliyun.alink.apiclient.biz.ApiClientBiz")) {
                f10160e.set(true);
            }
            if (ReflectUtils.hasClass("com.alibaba.ailabs.iot.mesh.TgScanManager")) {
                f10161f.set(true);
            }
            if (ReflectUtils.hasClass("com.alibaba.ailabs.iot.bluetoothlesdk.GenieBLEDeviceManager") && (ReflectUtils.hasClass("com.alibaba.ailabs.tg.mtop.MtopCommonHelper") || ReflectUtils.hasClass("com.taobao.api.TaobaoClient"))) {
                ALog.d("DCEnvHelper", "has genie and (top or mtop)");
                f10162g.set(true);
            }
            if (ReflectUtils.hasClass("com.alibaba.ailabs.tg.mtop.MtopCommonHelper") && ReflectUtils.hasClass("mtopsdk.mtop.domain.IMTOPDataObject") && ReflectUtils.hasClass(AppEnv.MTOP_CLASS_NAME)) {
                f10163h.set(true);
            }
            if (ReflectUtils.hasClass("com.aliyun.alink.linksdk.alcs.coap.AlcsCoAP")) {
                f10164i.set(true);
            }
            if (ReflectUtils.hasClass("com.aliyun.alink.linksdk.logextra.upload.Log2Cloud")) {
                f10165j.set(true);
            }
            if (ReflectUtils.hasClass("com.ut.mini.UTAnalytics") && ReflectUtils.hasClass("com.ut.mini.internal.UTOriginalCustomHitBuilder")) {
                f10166k.set(true);
            }
            f10157b.set(true);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean isILopEnv() {
        if (!f10157b.get()) {
            initEnv();
        }
        return hasApiClient() && !isTgEnv();
    }

    public static boolean isTgEnv() {
        if (!f10157b.get()) {
            initEnv();
        }
        return f10156a.get() && hasTGBleScanAbilityAB() && f10163h.get();
    }

    public static void setUseTgEnv(boolean z2) {
        f10156a.set(z2);
    }
}

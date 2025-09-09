package com.aliyun.aio_stat;

import com.aliyun.aio.keep.CalledByNative;

/* loaded from: classes2.dex */
public class ParamGetter {

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f9934a;

        static {
            int[] iArr = new int[b.values().length];
            f9934a = iArr;
            try {
                iArr[b.cpu_usage.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public enum b {
        event_id,
        stm,
        stat_log_id,
        stat_log_seq,
        stat_log_priority,
        session_id,
        terminal_type,
        device_model,
        os_name,
        os_version,
        uuid,
        application_id,
        application_name,
        application_version,
        cpu_processor,
        cpu_info,
        gpu_info,
        opengl_version,
        device_brand,
        device_manufacturer,
        device_feature,
        cpu_usage,
        mem_usage,
        mem_total,
        electric_usage,
        network_type,
        android_sdk_version,
        install_uuid,
        stat_log_ver,
        time_zone
    }

    @CalledByNative
    public static String getParamValue(int i2) {
        return a.f9934a[b.values()[i2].ordinal()] != 1 ? "" : String.valueOf(com.aliyun.aio_stat.a.d().e());
    }
}

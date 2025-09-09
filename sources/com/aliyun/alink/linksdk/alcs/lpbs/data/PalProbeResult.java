package com.aliyun.alink.linksdk.alcs.lpbs.data;

import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;

/* loaded from: classes2.dex */
public class PalProbeResult {
    public static final int PAL_PROBE_ERROR_RSPERROR = 2;
    public static final int PAL_PROBE_ERROR_TIMEOUT = 1;
    public static final int PAL_PROBE_OK = 0;
    public String probePluginId;
    public int probeResult;

    public PalProbeResult(int i2) {
        this(i2, AlcsPalConst.DEFAULT_PLUGIN_ID);
    }

    public PalProbeResult(int i2, String str) {
        this.probeResult = i2;
        this.probePluginId = str;
    }
}

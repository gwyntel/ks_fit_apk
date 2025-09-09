package com.aliyun.alink.linksdk.tmp.data.discovery;

import com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsDiscoveryConfig;
import com.aliyun.alink.linksdk.tmp.data.cloud.CloudLcaRequestParams;
import java.util.List;

/* loaded from: classes2.dex */
public class DiscoveryConfig {
    public CloudLcaRequestParams cloudLcaRequestParams;
    public DiscoveryParams discoveryParams;

    public static class DiscoveryParams {
        public static final String BREEZE_PLUGIN_ID = "com.aliyun.iot.breeze.lpbs";
        public static final String ICA_PLUGIN_ID = "iot_ica";
        public DiscoveryStrategy discoveryStrategy;
        public List<String> lpbsPluginIdList;
    }

    public enum DiscoveryStrategy {
        LOW_LATENCY(1),
        LOW_ENERGY(2);

        protected int value;

        DiscoveryStrategy(int i2) {
            this.value = i2;
        }

        public int value() {
            return this.value;
        }
    }

    public AlcsDiscoveryConfig translateToALCSDiscoveryConfig() {
        if (this.discoveryParams == null) {
            return null;
        }
        AlcsDiscoveryConfig alcsDiscoveryConfig = new AlcsDiscoveryConfig();
        DiscoveryParams discoveryParams = this.discoveryParams;
        alcsDiscoveryConfig.mPluginIdList = discoveryParams.lpbsPluginIdList;
        if (discoveryParams.discoveryStrategy == null) {
            discoveryParams.discoveryStrategy = DiscoveryStrategy.LOW_ENERGY;
        }
        alcsDiscoveryConfig.mDiscoveryStrategy = discoveryParams.discoveryStrategy.value();
        return alcsDiscoveryConfig;
    }
}

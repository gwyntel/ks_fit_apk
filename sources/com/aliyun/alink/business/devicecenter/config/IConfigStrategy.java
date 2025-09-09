package com.aliyun.alink.business.devicecenter.config;

import com.aliyun.alink.business.devicecenter.config.model.DCConfigParams;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IConfigStrategy {
    void continueConfig(Map map);

    void doExtraPrepareWork(IConfigExtraCallback iConfigExtraCallback);

    String getProvisionType();

    boolean hasExtraPrepareWork();

    boolean isSupport();

    boolean needWiFiSsidPwd();

    void preConfig(IConfigCallback iConfigCallback, DCConfigParams dCConfigParams);

    void startConfig(IConfigCallback iConfigCallback, DCConfigParams dCConfigParams) throws Exception;

    void stopConfig();
}

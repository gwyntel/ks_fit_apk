package com.aliyun.alink.business.devicecenter.plugin;

import com.aliyun.alink.business.devicecenter.api.add.LinkType;
import com.aliyun.alink.business.devicecenter.config.IConfigStrategy;

/* loaded from: classes2.dex */
public interface IProvisionPlugin {
    void registerProvisionStrategy(LinkType linkType, Class<? extends IConfigStrategy> cls);

    void unregisterProvisionStrategy(LinkType linkType);
}

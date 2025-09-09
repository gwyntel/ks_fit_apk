package com.aliyun.alink.business.devicecenter.discover.annotation;

import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface DeviceDiscovery {
    DiscoveryType[] discoveryType();
}

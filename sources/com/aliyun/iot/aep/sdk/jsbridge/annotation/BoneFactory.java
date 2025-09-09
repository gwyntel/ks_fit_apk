package com.aliyun.iot.aep.sdk.jsbridge.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface BoneFactory {
    String name();

    String sdkName();

    String sdkVersion() default "0.0.1";
}

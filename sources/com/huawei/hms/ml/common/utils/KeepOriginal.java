package com.huawei.hms.ml.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@KeepOriginal
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes4.dex */
public @interface KeepOriginal {
}

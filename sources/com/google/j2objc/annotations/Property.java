package com.google.j2objc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes3.dex */
public @interface Property {

    @Target({ElementType.METHOD})
    public @interface Suppress {
        String reason() default "";
    }

    String value() default "";
}

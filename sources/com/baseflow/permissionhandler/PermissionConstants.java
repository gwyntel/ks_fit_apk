package com.baseflow.permissionhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* loaded from: classes3.dex */
final class PermissionConstants {

    @Retention(RetentionPolicy.SOURCE)
    @interface PermissionGroup {
    }

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    @interface PermissionStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface ServiceStatus {
    }
}

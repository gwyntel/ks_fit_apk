package com.heytap.mcssdk.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class McsEventConstant {

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventId {
        public static final String EVENT_ID_PUSH_DELETE_BY_FOLD = "push_delete_by_fold";
        public static final String EVENT_ID_PUSH_NO_SHOW_BY_FOLD = "push_no_show_by_fold";
        public static final String EVENT_ID_PUSH_REGISTER = "push_register";
        public static final String EVENT_ID_PUSH_TRANSMIT = "push_transmit";
    }
}

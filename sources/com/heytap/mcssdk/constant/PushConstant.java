package com.heytap.mcssdk.constant;

/* loaded from: classes3.dex */
public class PushConstant {

    public interface Notification {
        public static final String DEFAULT_NOTIFICATION_CAN_DELETE_GROUP_KEY = "mcs.enable.";
        public static final int DEFAULT_NOTIFICATION_GROUP_ID = 4096;
        public static final String DEFAULT_NOTIFICATION_NOT_DELETE_GROUP_KEY = "mcs.";
    }

    public interface NotificationSort {
        public static final String CLIENT_STATISTIC_DATA = "clientStatisticData";
        public static final String IS_MCS = "isMcs";
        public static final int KEEP_NUMBER = 3;
        public static final String SORT_ARRAY = "SORT_ARRAY";
    }

    public interface Version {
        public static final int SDK_INT_20 = 20;
        public static final int SDK_INT_23 = 23;
        public static final int SDK_INT_24 = 24;
        public static final int SDK_INT_26 = 26;
        public static final int SDK_INT_30 = 30;
    }
}

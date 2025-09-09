package com.xiaomi.push.service;

/* loaded from: classes4.dex */
public abstract class bj {
    public static String A = "ext_auth_method";
    public static String B = "ext_security";
    public static String C = "ext_kick";
    public static String D = "ext_client_attr";
    public static String E = "ext_cloud_attr";
    public static String F = "ext_pkg_name";
    public static String G = "ext_notify_id";
    public static String H = "ext_clicked_button";
    public static String I = "ext_notify_type";
    public static String J = "ext_session";
    public static String K = "sig";
    public static String L = "ext_notify_title";
    public static String M = "ext_notify_description";
    public static String N = "ext_messenger";
    public static String O = "title";
    public static String P = "description";
    public static String Q = "notifyId";
    public static String R = "dump";

    /* renamed from: a, reason: collision with root package name */
    public static String f24517a = "1";

    /* renamed from: b, reason: collision with root package name */
    public static String f24518b = "2";

    /* renamed from: c, reason: collision with root package name */
    public static String f24519c = "3";

    /* renamed from: d, reason: collision with root package name */
    public static String f24520d = "com.xiaomi.push.OPEN_CHANNEL";

    /* renamed from: e, reason: collision with root package name */
    public static String f24521e = "com.xiaomi.push.SEND_MESSAGE";

    /* renamed from: f, reason: collision with root package name */
    public static String f24522f = "com.xiaomi.push.SEND_IQ";

    /* renamed from: g, reason: collision with root package name */
    public static String f24523g = "com.xiaomi.push.BATCH_SEND_MESSAGE";

    /* renamed from: h, reason: collision with root package name */
    public static String f24524h = "com.xiaomi.push.SEND_PRES";

    /* renamed from: i, reason: collision with root package name */
    public static String f24525i = "com.xiaomi.push.CLOSE_CHANNEL";

    /* renamed from: j, reason: collision with root package name */
    public static String f24526j = "com.xiaomi.push.FORCE_RECONN";

    /* renamed from: k, reason: collision with root package name */
    public static String f24527k = "com.xiaomi.push.RESET_CONN";

    /* renamed from: l, reason: collision with root package name */
    public static String f24528l = "com.xiaomi.push.UPDATE_CHANNEL_INFO";

    /* renamed from: m, reason: collision with root package name */
    public static String f24529m = "com.xiaomi.push.SEND_STATS";

    /* renamed from: n, reason: collision with root package name */
    public static String f24530n = "com.xiaomi.push.HANDLE_FCM_MSG";

    /* renamed from: o, reason: collision with root package name */
    public static String f24531o = "com.xiaomi.push.APP_NOTIFY_MSG";

    /* renamed from: p, reason: collision with root package name */
    public static String f24532p = "com.xiaomi.push.CHANGE_HOST";

    /* renamed from: q, reason: collision with root package name */
    public static String f24533q = "com.xiaomi.push.PING_TIMER";

    /* renamed from: r, reason: collision with root package name */
    public static String f24534r = "com.xiaomi.push.APP_CHANNEL_SWITCH";

    /* renamed from: s, reason: collision with root package name */
    public static String f24535s = "ext_user_id";

    /* renamed from: t, reason: collision with root package name */
    public static String f24536t = "ext_user_server";

    /* renamed from: u, reason: collision with root package name */
    public static String f24537u = "ext_user_res";

    /* renamed from: v, reason: collision with root package name */
    public static String f24538v = "ext_chid";

    /* renamed from: w, reason: collision with root package name */
    public static String f24539w = "ext_receive_time";

    /* renamed from: x, reason: collision with root package name */
    public static String f24540x = "ext_broadcast_time";

    /* renamed from: y, reason: collision with root package name */
    public static String f24541y = "ext_sid";

    /* renamed from: z, reason: collision with root package name */
    public static String f24542z = "ext_token";

    public static String a(int i2) {
        switch (i2) {
            case 0:
                return "ERROR_OK";
            case 1:
                return "ERROR_SERVICE_NOT_INSTALLED";
            case 2:
                return "ERROR_NETWORK_NOT_AVAILABLE";
            case 3:
                return "ERROR_NETWORK_FAILED";
            case 4:
                return "ERROR_ACCESS_DENIED";
            case 5:
                return "ERROR_AUTH_FAILED";
            case 6:
                return "ERROR_MULTI_LOGIN";
            case 7:
                return "ERROR_SERVER_ERROR";
            case 8:
                return "ERROR_RECEIVE_TIMEOUT";
            case 9:
                return "ERROR_READ_ERROR";
            case 10:
                return "ERROR_SEND_ERROR";
            case 11:
                return "ERROR_RESET";
            case 12:
                return "ERROR_NO_CLIENT";
            case 13:
                return "ERROR_SERVER_STREAM";
            case 14:
                return "ERROR_THREAD_BLOCK";
            case 15:
                return "ERROR_SERVICE_DESTROY";
            case 16:
                return "ERROR_SESSION_CHANGED";
            case 17:
                return "ERROR_READ_TIMEOUT";
            case 18:
                return "ERROR_CONNECTIING_TIMEOUT";
            case 19:
                return "ERROR_USER_BLOCKED";
            case 20:
                return "ERROR_REDIRECT";
            case 21:
                return "ERROR_BIND_TIMEOUT";
            case 22:
                return "ERROR_PING_TIMEOUT";
            default:
                return String.valueOf(i2);
        }
    }
}

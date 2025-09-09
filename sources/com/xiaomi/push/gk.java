package com.xiaomi.push;

import java.net.UnknownHostException;

/* loaded from: classes4.dex */
final class gk {

    static class a {

        /* renamed from: a, reason: collision with root package name */
        ge f23831a;

        /* renamed from: a, reason: collision with other field name */
        String f471a;

        a() {
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static void m434a(Exception exc) {
        exc.getClass();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v7, types: [java.lang.Throwable] */
    static a b(Exception exc) {
        Throwable cause;
        m434a(exc);
        boolean z2 = exc instanceof hm;
        Exception excA = exc;
        if (z2) {
            hm hmVar = (hm) exc;
            excA = exc;
            if (hmVar.a() != null) {
                excA = hmVar.a();
            }
        }
        a aVar = new a();
        String message = excA.getMessage();
        if (excA.getCause() != null) {
            message = excA.getCause().getMessage();
        }
        int iA = hd.a(excA);
        String str = excA.getClass().getSimpleName() + ":" + message;
        if (iA != 0) {
            ge geVarA = ge.a(ge.CONN_SUCCESS.a() + iA);
            aVar.f23831a = geVarA;
            if (geVarA == ge.CONN_BOSH_ERR && (cause = excA.getCause()) != null && (cause instanceof UnknownHostException)) {
                aVar.f23831a = ge.CONN_BOSH_UNKNOWNHOST;
            }
        } else {
            aVar.f23831a = ge.CONN_XMPP_ERR;
        }
        ge geVar = aVar.f23831a;
        if (geVar == ge.CONN_TCP_ERR_OTHER || geVar == ge.CONN_XMPP_ERR || geVar == ge.CONN_BOSH_ERR) {
            aVar.f471a = str;
        }
        return aVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.lang.Throwable] */
    static a c(Exception exc) {
        m434a(exc);
        boolean z2 = exc instanceof hm;
        Exception excA = exc;
        if (z2) {
            hm hmVar = (hm) exc;
            excA = exc;
            if (hmVar.a() != null) {
                excA = hmVar.a();
            }
        }
        a aVar = new a();
        String message = excA.getMessage();
        if (excA.getCause() != null) {
            message = excA.getCause().getMessage();
        }
        int iA = hd.a(excA);
        String str = excA.getClass().getSimpleName() + ":" + message;
        if (iA == 105) {
            aVar.f23831a = ge.BIND_TCP_READ_TIMEOUT;
        } else if (iA == 199) {
            aVar.f23831a = ge.BIND_TCP_ERR;
        } else if (iA == 499) {
            aVar.f23831a = ge.BIND_BOSH_ERR;
            if (message.startsWith("Terminal binding condition encountered: item-not-found")) {
                aVar.f23831a = ge.BIND_BOSH_ITEM_NOT_FOUND;
            }
        } else if (iA == 109) {
            aVar.f23831a = ge.BIND_TCP_CONNRESET;
        } else if (iA != 110) {
            aVar.f23831a = ge.BIND_XMPP_ERR;
        } else {
            aVar.f23831a = ge.BIND_TCP_BROKEN_PIPE;
        }
        ge geVar = aVar.f23831a;
        if (geVar == ge.BIND_TCP_ERR || geVar == ge.BIND_XMPP_ERR || geVar == ge.BIND_BOSH_ERR) {
            aVar.f471a = str;
        }
        return aVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.lang.Throwable] */
    static a d(Exception exc) {
        m434a(exc);
        boolean z2 = exc instanceof hm;
        Exception excA = exc;
        if (z2) {
            hm hmVar = (hm) exc;
            excA = exc;
            if (hmVar.a() != null) {
                excA = hmVar.a();
            }
        }
        a aVar = new a();
        String message = excA.getMessage();
        int iA = hd.a(excA);
        String str = excA.getClass().getSimpleName() + ":" + message;
        if (iA == 105) {
            aVar.f23831a = ge.CHANNEL_TCP_READTIMEOUT;
        } else if (iA == 199) {
            aVar.f23831a = ge.CHANNEL_TCP_ERR;
        } else if (iA == 499) {
            aVar.f23831a = ge.CHANNEL_BOSH_EXCEPTION;
            if (message.startsWith("Terminal binding condition encountered: item-not-found")) {
                aVar.f23831a = ge.CHANNEL_BOSH_ITEMNOTFIND;
            }
        } else if (iA == 109) {
            aVar.f23831a = ge.CHANNEL_TCP_CONNRESET;
        } else if (iA != 110) {
            aVar.f23831a = ge.CHANNEL_XMPPEXCEPTION;
        } else {
            aVar.f23831a = ge.CHANNEL_TCP_BROKEN_PIPE;
        }
        ge geVar = aVar.f23831a;
        if (geVar == ge.CHANNEL_TCP_ERR || geVar == ge.CHANNEL_XMPPEXCEPTION || geVar == ge.CHANNEL_BOSH_EXCEPTION) {
            aVar.f471a = str;
        }
        return aVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.lang.Throwable] */
    static a a(Exception exc) {
        m434a(exc);
        boolean z2 = exc instanceof hm;
        Exception excA = exc;
        if (z2) {
            hm hmVar = (hm) exc;
            excA = exc;
            if (hmVar.a() != null) {
                excA = hmVar.a();
            }
        }
        a aVar = new a();
        String message = excA.getMessage();
        if (excA.getCause() != null) {
            message = excA.getCause().getMessage();
        }
        String str = excA.getClass().getSimpleName() + ":" + message;
        int iA = hd.a(excA);
        if (iA != 0) {
            aVar.f23831a = ge.a(ge.GSLB_REQUEST_SUCCESS.a() + iA);
        }
        if (aVar.f23831a == null) {
            aVar.f23831a = ge.GSLB_TCP_ERR_OTHER;
        }
        if (aVar.f23831a == ge.GSLB_TCP_ERR_OTHER) {
            aVar.f471a = str;
        }
        return aVar;
    }
}

package io.flutter.plugins.webviewflutter;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class u1 {
    public static MessageCodec a() {
        return new StandardMessageCodec();
    }

    public static /* synthetic */ void b(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Number number2 = (Number) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.create(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()));
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void c(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setDomStorageEnabled(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void d(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setDisplayZoomControls(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void e(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setBuiltInZoomControls(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void f(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setAllowFileAccess(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void g(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Number number2 = (Number) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setTextZoom(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()));
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void h(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        Number number = (Number) ((ArrayList) obj).get(0);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        arrayList.add(0, webSettingsHostApi.getUserAgentString(lValueOf));
        reply.reply(arrayList);
    }

    public static /* synthetic */ void i(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setJavaScriptCanOpenWindowsAutomatically(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void j(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setSupportMultipleWindows(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void k(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setJavaScriptEnabled(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void l(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        String str = (String) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setUserAgentString(lValueOf, str);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void m(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setMediaPlaybackRequiresUserGesture(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void n(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setSupportZoom(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void o(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setLoadWithOverviewMode(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static /* synthetic */ void p(GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi, Object obj, BasicMessageChannel.Reply reply) {
        Long lValueOf;
        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList arrayList2 = (ArrayList) obj;
        Number number = (Number) arrayList2.get(0);
        Boolean bool = (Boolean) arrayList2.get(1);
        if (number == null) {
            lValueOf = null;
        } else {
            try {
                lValueOf = Long.valueOf(number.longValue());
            } catch (Throwable th) {
                arrayList = GeneratedAndroidWebView.wrapError(th);
            }
        }
        webSettingsHostApi.setUseWideViewPort(lValueOf, bool);
        arrayList.add(0, null);
        reply.reply(arrayList);
    }

    public static void q(BinaryMessenger binaryMessenger, final GeneratedAndroidWebView.WebSettingsHostApi webSettingsHostApi) {
        BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.create", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.f1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.b(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setDomStorageEnabled", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.q1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.c(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel2.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setJavaScriptCanOpenWindowsAutomatically", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.r1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.i(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel3.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setSupportMultipleWindows", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.s1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.j(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel4.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel5 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setJavaScriptEnabled", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel5.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.t1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.k(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel5.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel6 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setUserAgentString", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel6.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.g1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.l(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel6.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel7 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setMediaPlaybackRequiresUserGesture", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel7.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.h1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.m(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel7.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel8 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setSupportZoom", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel8.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.i1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.n(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel8.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel9 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setLoadWithOverviewMode", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel9.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.j1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.o(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel9.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel10 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setUseWideViewPort", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel10.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.k1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.p(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel10.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel11 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setDisplayZoomControls", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel11.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.l1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.d(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel11.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel12 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setBuiltInZoomControls", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel12.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.m1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.e(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel12.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel13 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setAllowFileAccess", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel13.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.n1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.f(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel13.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel14 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.setTextZoom", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel14.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.o1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.g(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel14.setMessageHandler(null);
        }
        BasicMessageChannel basicMessageChannel15 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebSettingsHostApi.getUserAgentString", a());
        if (webSettingsHostApi != null) {
            basicMessageChannel15.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.p1
                @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                    u1.h(webSettingsHostApi, obj, reply);
                }
            });
        } else {
            basicMessageChannel15.setMessageHandler(null);
        }
    }
}

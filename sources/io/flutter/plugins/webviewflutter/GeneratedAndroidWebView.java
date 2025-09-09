package io.flutter.plugins.webviewflutter;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class GeneratedAndroidWebView {

    public static final class ConsoleMessage {

        @NonNull
        private ConsoleMessageLevel level;

        @NonNull
        private Long lineNumber;

        @NonNull
        private String message;

        @NonNull
        private String sourceId;

        public static final class Builder {

            @Nullable
            private ConsoleMessageLevel level;

            @Nullable
            private Long lineNumber;

            @Nullable
            private String message;

            @Nullable
            private String sourceId;

            @NonNull
            public ConsoleMessage build() {
                ConsoleMessage consoleMessage = new ConsoleMessage();
                consoleMessage.setLineNumber(this.lineNumber);
                consoleMessage.setMessage(this.message);
                consoleMessage.setLevel(this.level);
                consoleMessage.setSourceId(this.sourceId);
                return consoleMessage;
            }

            @NonNull
            public Builder setLevel(@NonNull ConsoleMessageLevel consoleMessageLevel) {
                this.level = consoleMessageLevel;
                return this;
            }

            @NonNull
            public Builder setLineNumber(@NonNull Long l2) {
                this.lineNumber = l2;
                return this;
            }

            @NonNull
            public Builder setMessage(@NonNull String str) {
                this.message = str;
                return this;
            }

            @NonNull
            public Builder setSourceId(@NonNull String str) {
                this.sourceId = str;
                return this;
            }
        }

        ConsoleMessage() {
        }

        @NonNull
        static ConsoleMessage fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            ConsoleMessage consoleMessage = new ConsoleMessage();
            Object obj = arrayList.get(0);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            consoleMessage.setLineNumber(lValueOf);
            consoleMessage.setMessage((String) arrayList.get(1));
            consoleMessage.setLevel(ConsoleMessageLevel.values()[((Integer) arrayList.get(2)).intValue()]);
            consoleMessage.setSourceId((String) arrayList.get(3));
            return consoleMessage;
        }

        @NonNull
        public ConsoleMessageLevel getLevel() {
            return this.level;
        }

        @NonNull
        public Long getLineNumber() {
            return this.lineNumber;
        }

        @NonNull
        public String getMessage() {
            return this.message;
        }

        @NonNull
        public String getSourceId() {
            return this.sourceId;
        }

        public void setLevel(@NonNull ConsoleMessageLevel consoleMessageLevel) {
            if (consoleMessageLevel == null) {
                throw new IllegalStateException("Nonnull field \"level\" is null.");
            }
            this.level = consoleMessageLevel;
        }

        public void setLineNumber(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"lineNumber\" is null.");
            }
            this.lineNumber = l2;
        }

        public void setMessage(@NonNull String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"message\" is null.");
            }
            this.message = str;
        }

        public void setSourceId(@NonNull String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"sourceId\" is null.");
            }
            this.sourceId = str;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(4);
            arrayList.add(this.lineNumber);
            arrayList.add(this.message);
            ConsoleMessageLevel consoleMessageLevel = this.level;
            arrayList.add(consoleMessageLevel == null ? null : Integer.valueOf(consoleMessageLevel.index));
            arrayList.add(this.sourceId);
            return arrayList;
        }
    }

    public enum ConsoleMessageLevel {
        DEBUG(0),
        ERROR(1),
        LOG(2),
        TIP(3),
        WARNING(4),
        UNKNOWN(5);

        final int index;

        ConsoleMessageLevel(int i2) {
            this.index = i2;
        }
    }

    public interface CookieManagerHostApi {

        /* renamed from: io.flutter.plugins.webviewflutter.GeneratedAndroidWebView$CookieManagerHostApi$-CC, reason: invalid class name */
        public abstract /* synthetic */ class CC {
            public static MessageCodec a() {
                return new StandardMessageCodec();
            }

            public static /* synthetic */ void b(CookieManagerHostApi cookieManagerHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                cookieManagerHostApi.attachInstance(lValueOf);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void c(CookieManagerHostApi cookieManagerHostApi, Object obj, BasicMessageChannel.Reply reply) {
                Long lValueOf;
                ArrayList<Object> arrayList = new ArrayList<>();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                String str = (String) arrayList2.get(1);
                String str2 = (String) arrayList2.get(2);
                if (number == null) {
                    lValueOf = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(number.longValue());
                    } catch (Throwable th) {
                        arrayList = GeneratedAndroidWebView.wrapError(th);
                    }
                }
                cookieManagerHostApi.setCookie(lValueOf, str, str2);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void d(CookieManagerHostApi cookieManagerHostApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                Number number = (Number) ((ArrayList) obj).get(0);
                cookieManagerHostApi.removeAllCookies(number == null ? null : Long.valueOf(number.longValue()), new Result<Boolean>() { // from class: io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.CookieManagerHostApi.1
                    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.Result
                    public void error(Throwable th) {
                        reply.reply(GeneratedAndroidWebView.wrapError(th));
                    }

                    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.Result
                    public void success(Boolean bool) {
                        arrayList.add(0, bool);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void e(CookieManagerHostApi cookieManagerHostApi, Object obj, BasicMessageChannel.Reply reply) {
                Long lValueOf;
                ArrayList<Object> arrayList = new ArrayList<>();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                Number number2 = (Number) arrayList2.get(1);
                Boolean bool = (Boolean) arrayList2.get(2);
                if (number == null) {
                    lValueOf = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(number.longValue());
                    } catch (Throwable th) {
                        arrayList = GeneratedAndroidWebView.wrapError(th);
                    }
                }
                cookieManagerHostApi.setAcceptThirdPartyCookies(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()), bool);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static void f(BinaryMessenger binaryMessenger, final CookieManagerHostApi cookieManagerHostApi) {
                BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.CookieManagerHostApi.attachInstance", a());
                if (cookieManagerHostApi != null) {
                    basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.d
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.CookieManagerHostApi.CC.b(cookieManagerHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.CookieManagerHostApi.setCookie", a());
                if (cookieManagerHostApi != null) {
                    basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.e
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.CookieManagerHostApi.CC.c(cookieManagerHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel2.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.CookieManagerHostApi.removeAllCookies", a());
                if (cookieManagerHostApi != null) {
                    basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.f
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.CookieManagerHostApi.CC.d(cookieManagerHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel3.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.CookieManagerHostApi.setAcceptThirdPartyCookies", a());
                if (cookieManagerHostApi != null) {
                    basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.g
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.CookieManagerHostApi.CC.e(cookieManagerHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel4.setMessageHandler(null);
                }
            }
        }

        void attachInstance(@NonNull Long l2);

        void removeAllCookies(@NonNull Long l2, @NonNull Result<Boolean> result);

        void setAcceptThirdPartyCookies(@NonNull Long l2, @NonNull Long l3, @NonNull Boolean bool);

        void setCookie(@NonNull Long l2, @NonNull String str, @NonNull String str2);
    }

    public static class CustomViewCallbackFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public CustomViewCallbackFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void create(@NonNull Long l2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.CustomViewCallbackFlutterApi.create", getCodec()).send(new ArrayList(Collections.singletonList(l2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.h
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface CustomViewCallbackHostApi {
        void onCustomViewHidden(@NonNull Long l2);
    }

    public static class DownloadListenerFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public DownloadListenerFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void onDownloadStart(@NonNull Long l2, @NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4, @NonNull Long l3, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.DownloadListenerFlutterApi.onDownloadStart", getCodec()).send(new ArrayList(Arrays.asList(l2, str, str2, str3, str4, l3)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.k
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface DownloadListenerHostApi {
        void create(@NonNull Long l2);
    }

    public enum FileChooserMode {
        OPEN(0),
        OPEN_MULTIPLE(1),
        SAVE(2);

        final int index;

        FileChooserMode(int i2) {
            this.index = i2;
        }
    }

    public static class FileChooserParamsFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public FileChooserParamsFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void create(@NonNull Long l2, @NonNull Boolean bool, @NonNull List<String> list, @NonNull FileChooserMode fileChooserMode, @Nullable String str, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.FileChooserParamsFlutterApi.create", getCodec()).send(new ArrayList(Arrays.asList(l2, bool, list, Integer.valueOf(fileChooserMode.index), str)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.n
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface FlutterAssetManagerHostApi {
        @NonNull
        String getAssetFilePathByName(@NonNull String str);

        @NonNull
        List<String> list(@NonNull String str);
    }

    public static class FlutterError extends RuntimeException {
        public final String code;
        public final Object details;

        public FlutterError(@NonNull String str, @Nullable String str2, @Nullable Object obj) {
            super(str2);
            this.code = str;
            this.details = obj;
        }
    }

    public static class GeolocationPermissionsCallbackFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public GeolocationPermissionsCallbackFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void create(@NonNull Long l2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.GeolocationPermissionsCallbackFlutterApi.create", getCodec()).send(new ArrayList(Collections.singletonList(l2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.s
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface GeolocationPermissionsCallbackHostApi {
        void invoke(@NonNull Long l2, @NonNull String str, @NonNull Boolean bool, @NonNull Boolean bool2);
    }

    public static class HttpAuthHandlerFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public HttpAuthHandlerFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void create(@NonNull Long l2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.HttpAuthHandlerFlutterApi.create", getCodec()).send(new ArrayList(Collections.singletonList(l2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.v
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface HttpAuthHandlerHostApi {
        void cancel(@NonNull Long l2);

        void proceed(@NonNull Long l2, @NonNull String str, @NonNull String str2);

        @NonNull
        Boolean useHttpAuthUsernamePassword(@NonNull Long l2);
    }

    public interface InstanceManagerHostApi {
        void clear();
    }

    public static class JavaObjectFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public JavaObjectFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void dispose(@NonNull Long l2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.JavaObjectFlutterApi.dispose", getCodec()).send(new ArrayList(Collections.singletonList(l2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.c0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface JavaObjectHostApi {
        void dispose(@NonNull Long l2);
    }

    public static class JavaScriptChannelFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public JavaScriptChannelFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void postMessage(@NonNull Long l2, @NonNull String str, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.JavaScriptChannelFlutterApi.postMessage", getCodec()).send(new ArrayList(Arrays.asList(l2, str)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.f0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface JavaScriptChannelHostApi {
        void create(@NonNull Long l2, @NonNull String str);
    }

    public static class PermissionRequestFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public PermissionRequestFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void create(@NonNull Long l2, @NonNull List<String> list, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.PermissionRequestFlutterApi.create", getCodec()).send(new ArrayList(Arrays.asList(l2, list)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.i0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface PermissionRequestHostApi {
        void deny(@NonNull Long l2);

        void grant(@NonNull Long l2, @NonNull List<String> list);
    }

    public interface Result<T> {
        void error(@NonNull Throwable th);

        void success(T t2);
    }

    public static class ViewFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public ViewFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void create(@NonNull Long l2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.ViewFlutterApi.create", getCodec()).send(new ArrayList(Collections.singletonList(l2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.m0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public static class WebChromeClientFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public WebChromeClientFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return WebChromeClientFlutterApiCodec.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onJsConfirm$9(Reply reply, Object obj) {
            reply.reply((Boolean) obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onJsPrompt$10(Reply reply, Object obj) {
            reply.reply((String) obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onShowFileChooser$1(Reply reply, Object obj) {
            reply.reply((List) obj);
        }

        public void onConsoleMessage(@NonNull Long l2, @NonNull ConsoleMessage consoleMessage, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onConsoleMessage", getCodec()).send(new ArrayList(Arrays.asList(l2, consoleMessage)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.r0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onGeolocationPermissionsHidePrompt(@NonNull Long l2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onGeolocationPermissionsHidePrompt", getCodec()).send(new ArrayList(Collections.singletonList(l2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.x0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onGeolocationPermissionsShowPrompt(@NonNull Long l2, @NonNull Long l3, @NonNull String str, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onGeolocationPermissionsShowPrompt", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, str)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.n0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onHideCustomView(@NonNull Long l2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onHideCustomView", getCodec()).send(new ArrayList(Collections.singletonList(l2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.p0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onJsAlert(@NonNull Long l2, @NonNull String str, @NonNull String str2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onJsAlert", getCodec()).send(new ArrayList(Arrays.asList(l2, str, str2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.q0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onJsConfirm(@NonNull Long l2, @NonNull String str, @NonNull String str2, @NonNull final Reply<Boolean> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onJsConfirm", getCodec()).send(new ArrayList(Arrays.asList(l2, str, str2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.w0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    GeneratedAndroidWebView.WebChromeClientFlutterApi.lambda$onJsConfirm$9(reply, obj);
                }
            });
        }

        public void onJsPrompt(@NonNull Long l2, @NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull final Reply<String> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onJsPrompt", getCodec()).send(new ArrayList(Arrays.asList(l2, str, str2, str3)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.t0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    GeneratedAndroidWebView.WebChromeClientFlutterApi.lambda$onJsPrompt$10(reply, obj);
                }
            });
        }

        public void onPermissionRequest(@NonNull Long l2, @NonNull Long l3, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onPermissionRequest", getCodec()).send(new ArrayList(Arrays.asList(l2, l3)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.s0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onProgressChanged(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onProgressChanged", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, l4)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.v0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onShowCustomView(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onShowCustomView", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, l4)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.u0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onShowFileChooser(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4, @NonNull final Reply<List<String>> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebChromeClientFlutterApi.onShowFileChooser", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, l4)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.o0
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    GeneratedAndroidWebView.WebChromeClientFlutterApi.lambda$onShowFileChooser$1(reply, obj);
                }
            });
        }
    }

    private static class WebChromeClientFlutterApiCodec extends StandardMessageCodec {
        public static final WebChromeClientFlutterApiCodec INSTANCE = new WebChromeClientFlutterApiCodec();

        private WebChromeClientFlutterApiCodec() {
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected Object readValueOfType(byte b2, @NonNull ByteBuffer byteBuffer) {
            return b2 != Byte.MIN_VALUE ? super.readValueOfType(b2, byteBuffer) : ConsoleMessage.fromList((ArrayList) readValue(byteBuffer));
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected void writeValue(@NonNull ByteArrayOutputStream byteArrayOutputStream, Object obj) {
            if (!(obj instanceof ConsoleMessage)) {
                super.writeValue(byteArrayOutputStream, obj);
            } else {
                byteArrayOutputStream.write(128);
                writeValue(byteArrayOutputStream, ((ConsoleMessage) obj).toList());
            }
        }
    }

    public interface WebChromeClientHostApi {
        void create(@NonNull Long l2);

        void setSynchronousReturnValueForOnConsoleMessage(@NonNull Long l2, @NonNull Boolean bool);

        void setSynchronousReturnValueForOnJsAlert(@NonNull Long l2, @NonNull Boolean bool);

        void setSynchronousReturnValueForOnJsConfirm(@NonNull Long l2, @NonNull Boolean bool);

        void setSynchronousReturnValueForOnJsPrompt(@NonNull Long l2, @NonNull Boolean bool);

        void setSynchronousReturnValueForOnShowFileChooser(@NonNull Long l2, @NonNull Boolean bool);
    }

    public static final class WebResourceErrorData {

        @NonNull
        private String description;

        @NonNull
        private Long errorCode;

        public static final class Builder {

            @Nullable
            private String description;

            @Nullable
            private Long errorCode;

            @NonNull
            public WebResourceErrorData build() {
                WebResourceErrorData webResourceErrorData = new WebResourceErrorData();
                webResourceErrorData.setErrorCode(this.errorCode);
                webResourceErrorData.setDescription(this.description);
                return webResourceErrorData;
            }

            @NonNull
            public Builder setDescription(@NonNull String str) {
                this.description = str;
                return this;
            }

            @NonNull
            public Builder setErrorCode(@NonNull Long l2) {
                this.errorCode = l2;
                return this;
            }
        }

        WebResourceErrorData() {
        }

        @NonNull
        static WebResourceErrorData fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            WebResourceErrorData webResourceErrorData = new WebResourceErrorData();
            Object obj = arrayList.get(0);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            webResourceErrorData.setErrorCode(lValueOf);
            webResourceErrorData.setDescription((String) arrayList.get(1));
            return webResourceErrorData;
        }

        @NonNull
        public String getDescription() {
            return this.description;
        }

        @NonNull
        public Long getErrorCode() {
            return this.errorCode;
        }

        public void setDescription(@NonNull String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"description\" is null.");
            }
            this.description = str;
        }

        public void setErrorCode(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"errorCode\" is null.");
            }
            this.errorCode = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(2);
            arrayList.add(this.errorCode);
            arrayList.add(this.description);
            return arrayList;
        }
    }

    public static final class WebResourceRequestData {

        @NonNull
        private Boolean hasGesture;

        @NonNull
        private Boolean isForMainFrame;

        @Nullable
        private Boolean isRedirect;

        @NonNull
        private String method;

        @NonNull
        private Map<String, String> requestHeaders;

        @NonNull
        private String url;

        public static final class Builder {

            @Nullable
            private Boolean hasGesture;

            @Nullable
            private Boolean isForMainFrame;

            @Nullable
            private Boolean isRedirect;

            @Nullable
            private String method;

            @Nullable
            private Map<String, String> requestHeaders;

            @Nullable
            private String url;

            @NonNull
            public WebResourceRequestData build() {
                WebResourceRequestData webResourceRequestData = new WebResourceRequestData();
                webResourceRequestData.setUrl(this.url);
                webResourceRequestData.setIsForMainFrame(this.isForMainFrame);
                webResourceRequestData.setIsRedirect(this.isRedirect);
                webResourceRequestData.setHasGesture(this.hasGesture);
                webResourceRequestData.setMethod(this.method);
                webResourceRequestData.setRequestHeaders(this.requestHeaders);
                return webResourceRequestData;
            }

            @NonNull
            public Builder setHasGesture(@NonNull Boolean bool) {
                this.hasGesture = bool;
                return this;
            }

            @NonNull
            public Builder setIsForMainFrame(@NonNull Boolean bool) {
                this.isForMainFrame = bool;
                return this;
            }

            @NonNull
            public Builder setIsRedirect(@Nullable Boolean bool) {
                this.isRedirect = bool;
                return this;
            }

            @NonNull
            public Builder setMethod(@NonNull String str) {
                this.method = str;
                return this;
            }

            @NonNull
            public Builder setRequestHeaders(@NonNull Map<String, String> map) {
                this.requestHeaders = map;
                return this;
            }

            @NonNull
            public Builder setUrl(@NonNull String str) {
                this.url = str;
                return this;
            }
        }

        WebResourceRequestData() {
        }

        @NonNull
        static WebResourceRequestData fromList(@NonNull ArrayList<Object> arrayList) {
            WebResourceRequestData webResourceRequestData = new WebResourceRequestData();
            webResourceRequestData.setUrl((String) arrayList.get(0));
            webResourceRequestData.setIsForMainFrame((Boolean) arrayList.get(1));
            webResourceRequestData.setIsRedirect((Boolean) arrayList.get(2));
            webResourceRequestData.setHasGesture((Boolean) arrayList.get(3));
            webResourceRequestData.setMethod((String) arrayList.get(4));
            webResourceRequestData.setRequestHeaders((Map) arrayList.get(5));
            return webResourceRequestData;
        }

        @NonNull
        public Boolean getHasGesture() {
            return this.hasGesture;
        }

        @NonNull
        public Boolean getIsForMainFrame() {
            return this.isForMainFrame;
        }

        @Nullable
        public Boolean getIsRedirect() {
            return this.isRedirect;
        }

        @NonNull
        public String getMethod() {
            return this.method;
        }

        @NonNull
        public Map<String, String> getRequestHeaders() {
            return this.requestHeaders;
        }

        @NonNull
        public String getUrl() {
            return this.url;
        }

        public void setHasGesture(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"hasGesture\" is null.");
            }
            this.hasGesture = bool;
        }

        public void setIsForMainFrame(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"isForMainFrame\" is null.");
            }
            this.isForMainFrame = bool;
        }

        public void setIsRedirect(@Nullable Boolean bool) {
            this.isRedirect = bool;
        }

        public void setMethod(@NonNull String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"method\" is null.");
            }
            this.method = str;
        }

        public void setRequestHeaders(@NonNull Map<String, String> map) {
            if (map == null) {
                throw new IllegalStateException("Nonnull field \"requestHeaders\" is null.");
            }
            this.requestHeaders = map;
        }

        public void setUrl(@NonNull String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"url\" is null.");
            }
            this.url = str;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(6);
            arrayList.add(this.url);
            arrayList.add(this.isForMainFrame);
            arrayList.add(this.isRedirect);
            arrayList.add(this.hasGesture);
            arrayList.add(this.method);
            arrayList.add(this.requestHeaders);
            return arrayList;
        }
    }

    public static final class WebResourceResponseData {

        @NonNull
        private Long statusCode;

        public static final class Builder {

            @Nullable
            private Long statusCode;

            @NonNull
            public WebResourceResponseData build() {
                WebResourceResponseData webResourceResponseData = new WebResourceResponseData();
                webResourceResponseData.setStatusCode(this.statusCode);
                return webResourceResponseData;
            }

            @NonNull
            public Builder setStatusCode(@NonNull Long l2) {
                this.statusCode = l2;
                return this;
            }
        }

        WebResourceResponseData() {
        }

        @NonNull
        static WebResourceResponseData fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            WebResourceResponseData webResourceResponseData = new WebResourceResponseData();
            Object obj = arrayList.get(0);
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            webResourceResponseData.setStatusCode(lValueOf);
            return webResourceResponseData;
        }

        @NonNull
        public Long getStatusCode() {
            return this.statusCode;
        }

        public void setStatusCode(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"statusCode\" is null.");
            }
            this.statusCode = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(1);
            arrayList.add(this.statusCode);
            return arrayList;
        }
    }

    public interface WebSettingsHostApi {
        void create(@NonNull Long l2, @NonNull Long l3);

        @NonNull
        String getUserAgentString(@NonNull Long l2);

        void setAllowFileAccess(@NonNull Long l2, @NonNull Boolean bool);

        void setBuiltInZoomControls(@NonNull Long l2, @NonNull Boolean bool);

        void setDisplayZoomControls(@NonNull Long l2, @NonNull Boolean bool);

        void setDomStorageEnabled(@NonNull Long l2, @NonNull Boolean bool);

        void setJavaScriptCanOpenWindowsAutomatically(@NonNull Long l2, @NonNull Boolean bool);

        void setJavaScriptEnabled(@NonNull Long l2, @NonNull Boolean bool);

        void setLoadWithOverviewMode(@NonNull Long l2, @NonNull Boolean bool);

        void setMediaPlaybackRequiresUserGesture(@NonNull Long l2, @NonNull Boolean bool);

        void setSupportMultipleWindows(@NonNull Long l2, @NonNull Boolean bool);

        void setSupportZoom(@NonNull Long l2, @NonNull Boolean bool);

        void setTextZoom(@NonNull Long l2, @NonNull Long l3);

        void setUseWideViewPort(@NonNull Long l2, @NonNull Boolean bool);

        void setUserAgentString(@NonNull Long l2, @Nullable String str);
    }

    public interface WebStorageHostApi {
        void create(@NonNull Long l2);

        void deleteAllData(@NonNull Long l2);
    }

    public static class WebViewClientFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public WebViewClientFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return WebViewClientFlutterApiCodec.INSTANCE;
        }

        public void doUpdateVisitedHistory(@NonNull Long l2, @NonNull Long l3, @NonNull String str, @NonNull Boolean bool, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.doUpdateVisitedHistory", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, str, bool)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.d2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onPageFinished(@NonNull Long l2, @NonNull Long l3, @NonNull String str, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.onPageFinished", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, str)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.e2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onPageStarted(@NonNull Long l2, @NonNull Long l3, @NonNull String str, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.onPageStarted", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, str)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.a2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onReceivedError(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4, @NonNull String str, @NonNull String str2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.onReceivedError", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, l4, str, str2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.z1
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onReceivedHttpAuthRequest(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4, @NonNull String str, @NonNull String str2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.onReceivedHttpAuthRequest", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, l4, str, str2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.f2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onReceivedHttpError(@NonNull Long l2, @NonNull Long l3, @NonNull WebResourceRequestData webResourceRequestData, @NonNull WebResourceResponseData webResourceResponseData, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.onReceivedHttpError", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, webResourceRequestData, webResourceResponseData)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.g2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onReceivedRequestError(@NonNull Long l2, @NonNull Long l3, @NonNull WebResourceRequestData webResourceRequestData, @NonNull WebResourceErrorData webResourceErrorData, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.onReceivedRequestError", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, webResourceRequestData, webResourceErrorData)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.c2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void requestLoading(@NonNull Long l2, @NonNull Long l3, @NonNull WebResourceRequestData webResourceRequestData, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.requestLoading", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, webResourceRequestData)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.b2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void urlLoading(@NonNull Long l2, @NonNull Long l3, @NonNull String str, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewClientFlutterApi.urlLoading", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, str)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.y1
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    private static class WebViewClientFlutterApiCodec extends StandardMessageCodec {
        public static final WebViewClientFlutterApiCodec INSTANCE = new WebViewClientFlutterApiCodec();

        private WebViewClientFlutterApiCodec() {
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected Object readValueOfType(byte b2, @NonNull ByteBuffer byteBuffer) {
            switch (b2) {
                case Byte.MIN_VALUE:
                    return WebResourceErrorData.fromList((ArrayList) readValue(byteBuffer));
                case -127:
                    return WebResourceRequestData.fromList((ArrayList) readValue(byteBuffer));
                case -126:
                    return WebResourceResponseData.fromList((ArrayList) readValue(byteBuffer));
                default:
                    return super.readValueOfType(b2, byteBuffer);
            }
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected void writeValue(@NonNull ByteArrayOutputStream byteArrayOutputStream, Object obj) {
            if (obj instanceof WebResourceErrorData) {
                byteArrayOutputStream.write(128);
                writeValue(byteArrayOutputStream, ((WebResourceErrorData) obj).toList());
            } else if (obj instanceof WebResourceRequestData) {
                byteArrayOutputStream.write(129);
                writeValue(byteArrayOutputStream, ((WebResourceRequestData) obj).toList());
            } else if (!(obj instanceof WebResourceResponseData)) {
                super.writeValue(byteArrayOutputStream, obj);
            } else {
                byteArrayOutputStream.write(130);
                writeValue(byteArrayOutputStream, ((WebResourceResponseData) obj).toList());
            }
        }
    }

    public interface WebViewClientHostApi {
        void create(@NonNull Long l2);

        void setSynchronousReturnValueForShouldOverrideUrlLoading(@NonNull Long l2, @NonNull Boolean bool);
    }

    public static class WebViewFlutterApi {

        @NonNull
        private final BinaryMessenger binaryMessenger;

        public interface Reply<T> {
            void reply(T t2);
        }

        public WebViewFlutterApi(@NonNull BinaryMessenger binaryMessenger) {
            this.binaryMessenger = binaryMessenger;
        }

        @NonNull
        static MessageCodec<Object> getCodec() {
            return new StandardMessageCodec();
        }

        public void create(@NonNull Long l2, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewFlutterApi.create", getCodec()).send(new ArrayList(Collections.singletonList(l2)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.l2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }

        public void onScrollChanged(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4, @NonNull Long l5, @NonNull Long l6, @NonNull final Reply<Void> reply) {
            new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewFlutterApi.onScrollChanged", getCodec()).send(new ArrayList(Arrays.asList(l2, l3, l4, l5, l6)), new BasicMessageChannel.Reply() { // from class: io.flutter.plugins.webviewflutter.k2
                @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
                public final void reply(Object obj) {
                    reply.reply(null);
                }
            });
        }
    }

    public interface WebViewHostApi {

        /* renamed from: io.flutter.plugins.webviewflutter.GeneratedAndroidWebView$WebViewHostApi$-CC, reason: invalid class name */
        public abstract /* synthetic */ class CC {
            public static /* synthetic */ void A(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.goForward(lValueOf);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static void B(BinaryMessenger binaryMessenger, final WebViewHostApi webViewHostApi) {
                BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.create", a());
                if (webViewHostApi != null) {
                    basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.m2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.b(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.loadData", a());
                if (webViewHostApi != null) {
                    basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.o2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.c(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel2.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.loadDataWithBaseUrl", a());
                if (webViewHostApi != null) {
                    basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.v2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.n(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel3.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.loadUrl", a());
                if (webViewHostApi != null) {
                    basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.w2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.u(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel4.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel5 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.postUrl", a());
                if (webViewHostApi != null) {
                    basicMessageChannel5.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.y2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.v(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel5.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel6 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.getUrl", a());
                if (webViewHostApi != null) {
                    basicMessageChannel6.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.z2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.w(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel6.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel7 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.canGoBack", a());
                if (webViewHostApi != null) {
                    basicMessageChannel7.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.a3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.x(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel7.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel8 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.canGoForward", a());
                if (webViewHostApi != null) {
                    basicMessageChannel8.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.b3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.y(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel8.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel9 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.goBack", a());
                if (webViewHostApi != null) {
                    basicMessageChannel9.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.c3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.z(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel9.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel10 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.goForward", a());
                if (webViewHostApi != null) {
                    basicMessageChannel10.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.d3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.A(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel10.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel11 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.reload", a());
                if (webViewHostApi != null) {
                    basicMessageChannel11.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.x2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.d(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel11.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel12 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.clearCache", a());
                if (webViewHostApi != null) {
                    basicMessageChannel12.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.e3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.e(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel12.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel13 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.evaluateJavascript", a());
                if (webViewHostApi != null) {
                    basicMessageChannel13.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.f3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.f(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel13.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel14 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.getTitle", a());
                if (webViewHostApi != null) {
                    basicMessageChannel14.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.g3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.g(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel14.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel15 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.scrollTo", a());
                if (webViewHostApi != null) {
                    basicMessageChannel15.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.h3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.h(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel15.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel16 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.scrollBy", a());
                if (webViewHostApi != null) {
                    basicMessageChannel16.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.i3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.i(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel16.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel17 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.getScrollX", a());
                if (webViewHostApi != null) {
                    basicMessageChannel17.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.j3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.j(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel17.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel18 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.getScrollY", a());
                if (webViewHostApi != null) {
                    basicMessageChannel18.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.k3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.k(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel18.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel19 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.getScrollPosition", a());
                if (webViewHostApi != null) {
                    basicMessageChannel19.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.l3
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.l(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel19.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel20 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.setWebContentsDebuggingEnabled", a());
                if (webViewHostApi != null) {
                    basicMessageChannel20.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.n2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.m(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel20.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel21 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.setWebViewClient", a());
                if (webViewHostApi != null) {
                    basicMessageChannel21.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.p2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.o(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel21.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel22 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.addJavaScriptChannel", a());
                if (webViewHostApi != null) {
                    basicMessageChannel22.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.q2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.p(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel22.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel23 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.removeJavaScriptChannel", a());
                if (webViewHostApi != null) {
                    basicMessageChannel23.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.r2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.q(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel23.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel24 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.setDownloadListener", a());
                if (webViewHostApi != null) {
                    basicMessageChannel24.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.s2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.r(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel24.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel25 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.setWebChromeClient", a());
                if (webViewHostApi != null) {
                    basicMessageChannel25.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.t2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.s(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel25.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel26 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.WebViewHostApi.setBackgroundColor", a());
                if (webViewHostApi != null) {
                    basicMessageChannel26.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.webviewflutter.u2
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            GeneratedAndroidWebView.WebViewHostApi.CC.t(webViewHostApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel26.setMessageHandler(null);
                }
            }

            public static MessageCodec a() {
                return WebViewHostApiCodec.INSTANCE;
            }

            public static /* synthetic */ void b(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.create(lValueOf);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void c(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
                Long lValueOf;
                ArrayList<Object> arrayList = new ArrayList<>();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                String str = (String) arrayList2.get(1);
                String str2 = (String) arrayList2.get(2);
                String str3 = (String) arrayList2.get(3);
                if (number == null) {
                    lValueOf = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(number.longValue());
                    } catch (Throwable th) {
                        arrayList = GeneratedAndroidWebView.wrapError(th);
                    }
                }
                webViewHostApi.loadData(lValueOf, str, str2, str3);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void d(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.reload(lValueOf);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void e(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.clearCache(lValueOf, bool);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void f(WebViewHostApi webViewHostApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                webViewHostApi.evaluateJavascript(number == null ? null : Long.valueOf(number.longValue()), (String) arrayList2.get(1), new Result<String>() { // from class: io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewHostApi.1
                    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.Result
                    public void error(Throwable th) {
                        reply.reply(GeneratedAndroidWebView.wrapError(th));
                    }

                    @Override // io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.Result
                    public void success(String str) {
                        arrayList.add(0, str);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void g(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                arrayList.add(0, webViewHostApi.getTitle(lValueOf));
                reply.reply(arrayList);
            }

            public static /* synthetic */ void h(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
                Long lValueOf;
                ArrayList<Object> arrayList = new ArrayList<>();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                Number number2 = (Number) arrayList2.get(1);
                Number number3 = (Number) arrayList2.get(2);
                if (number == null) {
                    lValueOf = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(number.longValue());
                    } catch (Throwable th) {
                        arrayList = GeneratedAndroidWebView.wrapError(th);
                    }
                }
                webViewHostApi.scrollTo(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()), number3 == null ? null : Long.valueOf(number3.longValue()));
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void i(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
                Long lValueOf;
                ArrayList<Object> arrayList = new ArrayList<>();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                Number number2 = (Number) arrayList2.get(1);
                Number number3 = (Number) arrayList2.get(2);
                if (number == null) {
                    lValueOf = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(number.longValue());
                    } catch (Throwable th) {
                        arrayList = GeneratedAndroidWebView.wrapError(th);
                    }
                }
                webViewHostApi.scrollBy(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()), number3 == null ? null : Long.valueOf(number3.longValue()));
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void j(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                arrayList.add(0, webViewHostApi.getScrollX(lValueOf));
                reply.reply(arrayList);
            }

            public static /* synthetic */ void k(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                arrayList.add(0, webViewHostApi.getScrollY(lValueOf));
                reply.reply(arrayList);
            }

            public static /* synthetic */ void l(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                arrayList.add(0, webViewHostApi.getScrollPosition(lValueOf));
                reply.reply(arrayList);
            }

            public static /* synthetic */ void m(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
                ArrayList<Object> arrayList = new ArrayList<>();
                try {
                    webViewHostApi.setWebContentsDebuggingEnabled((Boolean) ((ArrayList) obj).get(0));
                    arrayList.add(0, null);
                } catch (Throwable th) {
                    arrayList = GeneratedAndroidWebView.wrapError(th);
                }
                reply.reply(arrayList);
            }

            public static /* synthetic */ void n(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
                Long lValueOf;
                ArrayList<Object> arrayList = new ArrayList<>();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                String str = (String) arrayList2.get(1);
                String str2 = (String) arrayList2.get(2);
                String str3 = (String) arrayList2.get(3);
                String str4 = (String) arrayList2.get(4);
                String str5 = (String) arrayList2.get(5);
                if (number == null) {
                    lValueOf = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(number.longValue());
                    } catch (Throwable th) {
                        arrayList = GeneratedAndroidWebView.wrapError(th);
                    }
                }
                webViewHostApi.loadDataWithBaseUrl(lValueOf, str, str2, str3, str4, str5);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void o(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.setWebViewClient(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()));
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void p(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.addJavaScriptChannel(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()));
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void q(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.removeJavaScriptChannel(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()));
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void r(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.setDownloadListener(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()));
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void s(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.setWebChromeClient(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()));
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void t(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.setBackgroundColor(lValueOf, number2 == null ? null : Long.valueOf(number2.longValue()));
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void u(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
                Long lValueOf;
                ArrayList<Object> arrayList = new ArrayList<>();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                String str = (String) arrayList2.get(1);
                Map<String, String> map = (Map) arrayList2.get(2);
                if (number == null) {
                    lValueOf = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(number.longValue());
                    } catch (Throwable th) {
                        arrayList = GeneratedAndroidWebView.wrapError(th);
                    }
                }
                webViewHostApi.loadUrl(lValueOf, str, map);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void v(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
                Long lValueOf;
                ArrayList<Object> arrayList = new ArrayList<>();
                ArrayList arrayList2 = (ArrayList) obj;
                Number number = (Number) arrayList2.get(0);
                String str = (String) arrayList2.get(1);
                byte[] bArr = (byte[]) arrayList2.get(2);
                if (number == null) {
                    lValueOf = null;
                } else {
                    try {
                        lValueOf = Long.valueOf(number.longValue());
                    } catch (Throwable th) {
                        arrayList = GeneratedAndroidWebView.wrapError(th);
                    }
                }
                webViewHostApi.postUrl(lValueOf, str, bArr);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }

            public static /* synthetic */ void w(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                arrayList.add(0, webViewHostApi.getUrl(lValueOf));
                reply.reply(arrayList);
            }

            public static /* synthetic */ void x(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                arrayList.add(0, webViewHostApi.canGoBack(lValueOf));
                reply.reply(arrayList);
            }

            public static /* synthetic */ void y(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                arrayList.add(0, webViewHostApi.canGoForward(lValueOf));
                reply.reply(arrayList);
            }

            public static /* synthetic */ void z(WebViewHostApi webViewHostApi, Object obj, BasicMessageChannel.Reply reply) {
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
                webViewHostApi.goBack(lValueOf);
                arrayList.add(0, null);
                reply.reply(arrayList);
            }
        }

        void addJavaScriptChannel(@NonNull Long l2, @NonNull Long l3);

        @NonNull
        Boolean canGoBack(@NonNull Long l2);

        @NonNull
        Boolean canGoForward(@NonNull Long l2);

        void clearCache(@NonNull Long l2, @NonNull Boolean bool);

        void create(@NonNull Long l2);

        void evaluateJavascript(@NonNull Long l2, @NonNull String str, @NonNull Result<String> result);

        @NonNull
        WebViewPoint getScrollPosition(@NonNull Long l2);

        @NonNull
        Long getScrollX(@NonNull Long l2);

        @NonNull
        Long getScrollY(@NonNull Long l2);

        @Nullable
        String getTitle(@NonNull Long l2);

        @Nullable
        String getUrl(@NonNull Long l2);

        void goBack(@NonNull Long l2);

        void goForward(@NonNull Long l2);

        void loadData(@NonNull Long l2, @NonNull String str, @Nullable String str2, @Nullable String str3);

        void loadDataWithBaseUrl(@NonNull Long l2, @Nullable String str, @NonNull String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5);

        void loadUrl(@NonNull Long l2, @NonNull String str, @NonNull Map<String, String> map);

        void postUrl(@NonNull Long l2, @NonNull String str, @NonNull byte[] bArr);

        void reload(@NonNull Long l2);

        void removeJavaScriptChannel(@NonNull Long l2, @NonNull Long l3);

        void scrollBy(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4);

        void scrollTo(@NonNull Long l2, @NonNull Long l3, @NonNull Long l4);

        void setBackgroundColor(@NonNull Long l2, @NonNull Long l3);

        void setDownloadListener(@NonNull Long l2, @Nullable Long l3);

        void setWebChromeClient(@NonNull Long l2, @Nullable Long l3);

        void setWebContentsDebuggingEnabled(@NonNull Boolean bool);

        void setWebViewClient(@NonNull Long l2, @NonNull Long l3);
    }

    private static class WebViewHostApiCodec extends StandardMessageCodec {
        public static final WebViewHostApiCodec INSTANCE = new WebViewHostApiCodec();

        private WebViewHostApiCodec() {
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected Object readValueOfType(byte b2, @NonNull ByteBuffer byteBuffer) {
            return b2 != Byte.MIN_VALUE ? super.readValueOfType(b2, byteBuffer) : WebViewPoint.fromList((ArrayList) readValue(byteBuffer));
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected void writeValue(@NonNull ByteArrayOutputStream byteArrayOutputStream, Object obj) {
            if (!(obj instanceof WebViewPoint)) {
                super.writeValue(byteArrayOutputStream, obj);
            } else {
                byteArrayOutputStream.write(128);
                writeValue(byteArrayOutputStream, ((WebViewPoint) obj).toList());
            }
        }
    }

    public static final class WebViewPoint {

        /* renamed from: x, reason: collision with root package name */
        @NonNull
        private Long f25190x;

        /* renamed from: y, reason: collision with root package name */
        @NonNull
        private Long f25191y;

        public static final class Builder {

            /* renamed from: x, reason: collision with root package name */
            @Nullable
            private Long f25192x;

            /* renamed from: y, reason: collision with root package name */
            @Nullable
            private Long f25193y;

            @NonNull
            public WebViewPoint build() {
                WebViewPoint webViewPoint = new WebViewPoint();
                webViewPoint.setX(this.f25192x);
                webViewPoint.setY(this.f25193y);
                return webViewPoint;
            }

            @NonNull
            public Builder setX(@NonNull Long l2) {
                this.f25192x = l2;
                return this;
            }

            @NonNull
            public Builder setY(@NonNull Long l2) {
                this.f25193y = l2;
                return this;
            }
        }

        WebViewPoint() {
        }

        @NonNull
        static WebViewPoint fromList(@NonNull ArrayList<Object> arrayList) {
            Long lValueOf;
            WebViewPoint webViewPoint = new WebViewPoint();
            Object obj = arrayList.get(0);
            Long lValueOf2 = null;
            if (obj == null) {
                lValueOf = null;
            } else {
                lValueOf = Long.valueOf(obj instanceof Integer ? ((Integer) obj).intValue() : ((Long) obj).longValue());
            }
            webViewPoint.setX(lValueOf);
            Object obj2 = arrayList.get(1);
            if (obj2 != null) {
                lValueOf2 = Long.valueOf(obj2 instanceof Integer ? ((Integer) obj2).intValue() : ((Long) obj2).longValue());
            }
            webViewPoint.setY(lValueOf2);
            return webViewPoint;
        }

        @NonNull
        public Long getX() {
            return this.f25190x;
        }

        @NonNull
        public Long getY() {
            return this.f25191y;
        }

        public void setX(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"x\" is null.");
            }
            this.f25190x = l2;
        }

        public void setY(@NonNull Long l2) {
            if (l2 == null) {
                throw new IllegalStateException("Nonnull field \"y\" is null.");
            }
            this.f25191y = l2;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(2);
            arrayList.add(this.f25190x);
            arrayList.add(this.f25191y);
            return arrayList;
        }
    }

    @NonNull
    protected static ArrayList<Object> wrapError(@NonNull Throwable th) {
        ArrayList<Object> arrayList = new ArrayList<>(3);
        if (th instanceof FlutterError) {
            FlutterError flutterError = (FlutterError) th;
            arrayList.add(flutterError.code);
            arrayList.add(flutterError.getMessage());
            arrayList.add(flutterError.details);
        } else {
            arrayList.add(th.toString());
            arrayList.add(th.getClass().getSimpleName());
            arrayList.add("Cause: " + th.getCause() + ", Stacktrace: " + Log.getStackTraceString(th));
        }
        return arrayList;
    }
}

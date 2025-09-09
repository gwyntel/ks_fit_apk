package io.flutter.plugins.urllauncher;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes4.dex */
public class Messages {

    public static final class BrowserOptions {

        @NonNull
        private Boolean showTitle;

        public static final class Builder {

            @Nullable
            private Boolean showTitle;

            @NonNull
            public BrowserOptions build() {
                BrowserOptions browserOptions = new BrowserOptions();
                browserOptions.setShowTitle(this.showTitle);
                return browserOptions;
            }

            @NonNull
            public Builder setShowTitle(@NonNull Boolean bool) {
                this.showTitle = bool;
                return this;
            }
        }

        BrowserOptions() {
        }

        @NonNull
        static BrowserOptions fromList(@NonNull ArrayList<Object> arrayList) {
            BrowserOptions browserOptions = new BrowserOptions();
            browserOptions.setShowTitle((Boolean) arrayList.get(0));
            return browserOptions;
        }

        @NonNull
        public Boolean getShowTitle() {
            return this.showTitle;
        }

        public void setShowTitle(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"showTitle\" is null.");
            }
            this.showTitle = bool;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(1);
            arrayList.add(this.showTitle);
            return arrayList;
        }
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

    public interface UrlLauncherApi {
        @NonNull
        Boolean canLaunchUrl(@NonNull String str);

        void closeWebView();

        @NonNull
        Boolean launchUrl(@NonNull String str, @NonNull Map<String, String> map);

        @NonNull
        Boolean openUrlInApp(@NonNull String str, @NonNull Boolean bool, @NonNull WebViewOptions webViewOptions, @NonNull BrowserOptions browserOptions);

        @NonNull
        Boolean supportsCustomTabs();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class UrlLauncherApiCodec extends StandardMessageCodec {
        public static final UrlLauncherApiCodec INSTANCE = new UrlLauncherApiCodec();

        private UrlLauncherApiCodec() {
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected Object readValueOfType(byte b2, @NonNull ByteBuffer byteBuffer) {
            return b2 != Byte.MIN_VALUE ? b2 != -127 ? super.readValueOfType(b2, byteBuffer) : WebViewOptions.fromList((ArrayList) readValue(byteBuffer)) : BrowserOptions.fromList((ArrayList) readValue(byteBuffer));
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected void writeValue(@NonNull ByteArrayOutputStream byteArrayOutputStream, Object obj) {
            if (obj instanceof BrowserOptions) {
                byteArrayOutputStream.write(128);
                writeValue(byteArrayOutputStream, ((BrowserOptions) obj).toList());
            } else if (!(obj instanceof WebViewOptions)) {
                super.writeValue(byteArrayOutputStream, obj);
            } else {
                byteArrayOutputStream.write(129);
                writeValue(byteArrayOutputStream, ((WebViewOptions) obj).toList());
            }
        }
    }

    public static final class WebViewOptions {

        @NonNull
        private Boolean enableDomStorage;

        @NonNull
        private Boolean enableJavaScript;

        @NonNull
        private Map<String, String> headers;

        public static final class Builder {

            @Nullable
            private Boolean enableDomStorage;

            @Nullable
            private Boolean enableJavaScript;

            @Nullable
            private Map<String, String> headers;

            @NonNull
            public WebViewOptions build() {
                WebViewOptions webViewOptions = new WebViewOptions();
                webViewOptions.setEnableJavaScript(this.enableJavaScript);
                webViewOptions.setEnableDomStorage(this.enableDomStorage);
                webViewOptions.setHeaders(this.headers);
                return webViewOptions;
            }

            @NonNull
            public Builder setEnableDomStorage(@NonNull Boolean bool) {
                this.enableDomStorage = bool;
                return this;
            }

            @NonNull
            public Builder setEnableJavaScript(@NonNull Boolean bool) {
                this.enableJavaScript = bool;
                return this;
            }

            @NonNull
            public Builder setHeaders(@NonNull Map<String, String> map) {
                this.headers = map;
                return this;
            }
        }

        WebViewOptions() {
        }

        @NonNull
        static WebViewOptions fromList(@NonNull ArrayList<Object> arrayList) {
            WebViewOptions webViewOptions = new WebViewOptions();
            webViewOptions.setEnableJavaScript((Boolean) arrayList.get(0));
            webViewOptions.setEnableDomStorage((Boolean) arrayList.get(1));
            webViewOptions.setHeaders((Map) arrayList.get(2));
            return webViewOptions;
        }

        @NonNull
        public Boolean getEnableDomStorage() {
            return this.enableDomStorage;
        }

        @NonNull
        public Boolean getEnableJavaScript() {
            return this.enableJavaScript;
        }

        @NonNull
        public Map<String, String> getHeaders() {
            return this.headers;
        }

        public void setEnableDomStorage(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"enableDomStorage\" is null.");
            }
            this.enableDomStorage = bool;
        }

        public void setEnableJavaScript(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"enableJavaScript\" is null.");
            }
            this.enableJavaScript = bool;
        }

        public void setHeaders(@NonNull Map<String, String> map) {
            if (map == null) {
                throw new IllegalStateException("Nonnull field \"headers\" is null.");
            }
            this.headers = map;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(3);
            arrayList.add(this.enableJavaScript);
            arrayList.add(this.enableDomStorage);
            arrayList.add(this.headers);
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

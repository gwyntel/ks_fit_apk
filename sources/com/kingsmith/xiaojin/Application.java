package com.kingsmith.xiaojin;

import android.util.Log;
import com.umeng.analytics.pro.bc;
import com.umeng.commonsdk.UMConfigure;
import io.flutter.app.FlutterApplication;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lcom/kingsmith/xiaojin/Application;", "Lio/flutter/app/FlutterApplication;", "()V", "onCreate", "", "Companion", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class Application extends FlutterApplication {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static Application INSTANCE;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/kingsmith/xiaojin/Application$Companion;", "", "()V", "INSTANCE", "Lcom/kingsmith/xiaojin/Application;", "getINSTANCE", "()Lcom/kingsmith/xiaojin/Application;", "setINSTANCE", "(Lcom/kingsmith/xiaojin/Application;)V", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Application getINSTANCE() {
            Application application = Application.INSTANCE;
            if (application != null) {
                return application;
            }
            Intrinsics.throwUninitializedPropertyAccessException("INSTANCE");
            return null;
        }

        public final void setINSTANCE(@NotNull Application application) {
            Intrinsics.checkNotNullParameter(application, "<set-?>");
            Application.INSTANCE = application;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // io.flutter.app.FlutterApplication, android.app.Application
    public void onCreate() {
        super.onCreate();
        UMConfigure.setLogEnabled(true);
        UMConfigure.preInit(this, "5d242c520cafb266b0000545", "Umeng");
        Log.e("UMLog", "Application onCreate call UMConfigure.preInit");
        INSTANCE.setINSTANCE(this);
        Log.d("", "初始化 Application");
        String language = Locale.getDefault().getLanguage();
        Sp sp = Sp.INSTANCE;
        Intrinsics.checkNotNull(language);
        sp.put(bc.N, language);
    }
}

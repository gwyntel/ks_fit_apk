package dev.fluttercommunity.plus.share;

import android.content.Context;
import android.content.Intent;
import com.taobao.agoo.a.a.b;
import com.umeng.analytics.pro.f;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0000\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u000e\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0015\u001a\u00020\u0011R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Ldev/fluttercommunity/plus/share/ShareSuccessManager;", "Lio/flutter/plugin/common/PluginRegistry$ActivityResultListener;", f.X, "Landroid/content/Context;", "(Landroid/content/Context;)V", "callback", "Lio/flutter/plugin/common/MethodChannel$Result;", "isCalledBack", "Ljava/util/concurrent/atomic/AtomicBoolean;", "onActivityResult", "", "requestCode", "", b.JSON_ERRORCODE, "data", "Landroid/content/Intent;", "returnResult", "", "result", "", "setCallback", "unavailable", "Companion", "share_plus_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class ShareSuccessManager implements PluginRegistry.ActivityResultListener {
    public static final int ACTIVITY_CODE = 22643;

    @NotNull
    public static final String RESULT_UNAVAILABLE = "dev.fluttercommunity.plus/share/unavailable";

    @Nullable
    private MethodChannel.Result callback;

    @NotNull
    private final Context context;

    @NotNull
    private AtomicBoolean isCalledBack;

    public ShareSuccessManager(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.isCalledBack = new AtomicBoolean(true);
    }

    private final void returnResult(String result) {
        MethodChannel.Result result2;
        if (!this.isCalledBack.compareAndSet(false, true) || (result2 = this.callback) == null) {
            return;
        }
        Intrinsics.checkNotNull(result2);
        result2.success(result);
        this.callback = null;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != 22643) {
            return false;
        }
        returnResult(SharePlusPendingIntent.INSTANCE.getResult());
        return true;
    }

    public final boolean setCallback(@NotNull MethodChannel.Result callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (!this.isCalledBack.compareAndSet(true, false)) {
            callback.error("Share callback error", "prior share-sheet did not call back, did you await it? Maybe use non-result variant", null);
            return false;
        }
        SharePlusPendingIntent.INSTANCE.setResult("");
        this.isCalledBack.set(false);
        this.callback = callback;
        return true;
    }

    public final void unavailable() {
        returnResult(RESULT_UNAVAILABLE);
    }
}

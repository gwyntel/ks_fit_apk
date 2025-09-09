package dev.fluttercommunity.plus.share;

import androidx.core.app.NotificationCompat;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Ldev/fluttercommunity/plus/share/MethodCallHandler;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "share", "Ldev/fluttercommunity/plus/share/Share;", "manager", "Ldev/fluttercommunity/plus/share/ShareSuccessManager;", "(Ldev/fluttercommunity/plus/share/Share;Ldev/fluttercommunity/plus/share/ShareSuccessManager;)V", "expectMapArguments", "", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "onMethodCall", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "share_plus_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nMethodCallHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MethodCallHandler.kt\ndev/fluttercommunity/plus/share/MethodCallHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,85:1\n1#2:86\n*E\n"})
/* loaded from: classes4.dex */
public final class MethodCallHandler implements MethodChannel.MethodCallHandler {

    @NotNull
    private final ShareSuccessManager manager;

    @NotNull
    private final Share share;

    public MethodCallHandler(@NotNull Share share, @NotNull ShareSuccessManager manager) {
        Intrinsics.checkNotNullParameter(share, "share");
        Intrinsics.checkNotNullParameter(manager, "manager");
        this.share = share;
        this.manager = manager;
    }

    private final void expectMapArguments(MethodCall call) throws IllegalArgumentException {
        if (!(call.arguments instanceof Map)) {
            throw new IllegalArgumentException("Map arguments expected".toString());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0063, code lost:
    
        if (r1.equals("shareFilesWithResult") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006d, code lost:
    
        if (r1.equals("shareWithResult") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0071, code lost:
    
        expectMapArguments(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0074, code lost:
    
        if (r0 == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007c, code lost:
    
        if (r12.manager.setCallback(r14) != false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007f, code lost:
    
        r1 = r12.share;
        r2 = r13.argument("text");
        kotlin.jvm.internal.Intrinsics.checkNotNull(r2, "null cannot be cast to non-null type kotlin.String");
        r1.share((java.lang.String) r2, (java.lang.String) r13.argument("subject"), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0093, code lost:
    
        if (r0 != false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0095, code lost:
    
        if (r0 == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0097, code lost:
    
        r14.success(dev.fluttercommunity.plus.share.ShareSuccessManager.RESULT_UNAVAILABLE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009b, code lost:
    
        r14.success(null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a5, code lost:
    
        if (r1.equals("shareFiles") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a8, code lost:
    
        expectMapArguments(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ab, code lost:
    
        if (r0 == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b3, code lost:
    
        if (r12.manager.setCallback(r14) != false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b6, code lost:
    
        r1 = r12.share;
        r2 = r13.argument("paths");
        kotlin.jvm.internal.Intrinsics.checkNotNull(r2);
        r1.shareFiles((java.util.List) r2, (java.util.List) r13.argument("mimeTypes"), (java.lang.String) r13.argument("text"), (java.lang.String) r13.argument("subject"), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e0, code lost:
    
        if (r0 != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e2, code lost:
    
        if (r0 == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e4, code lost:
    
        r14.success(dev.fluttercommunity.plus.share.ShareSuccessManager.RESULT_UNAVAILABLE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00e8, code lost:
    
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ea, code lost:
    
        r14.success(null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ee, code lost:
    
        r14.error("Share failed", r13.getMessage(), null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0035, code lost:
    
        if (r1.equals("share") == false) goto L49;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x00ee -> B:51:0x00fb). Please report as a decompilation issue!!! */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMethodCall(@org.jetbrains.annotations.NotNull io.flutter.plugin.common.MethodCall r13, @org.jetbrains.annotations.NotNull io.flutter.plugin.common.MethodChannel.Result r14) throws java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: dev.fluttercommunity.plus.share.MethodCallHandler.onMethodCall(io.flutter.plugin.common.MethodCall, io.flutter.plugin.common.MethodChannel$Result):void");
    }
}

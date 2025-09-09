package com.fluttercandies.image_editor.core;

import android.os.Handler;
import android.os.Looper;
import com.fluttercandies.image_editor.core.ResultHandler;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\u0001J&\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0001R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/fluttercandies/image_editor/core/ResultHandler;", "", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "(Lio/flutter/plugin/common/MethodChannel$Result;)V", "notImplemented", "", "reply", Languages.ANY, "replyError", "code", "", "message", "obj", "Companion", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ResultHandler {

    @NotNull
    private static final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    private MethodChannel.Result result;

    public ResultHandler(@Nullable MethodChannel.Result result) {
        this.result = result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notImplemented$lambda$2(ResultHandler this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MethodChannel.Result result = this$0.result;
        if (result != null) {
            result.notImplemented();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reply$lambda$0(MethodChannel.Result result, Object obj) {
        if (result != null) {
            result.success(obj);
        }
    }

    public static /* synthetic */ void replyError$default(ResultHandler resultHandler, String str, String str2, Object obj, int i2, Object obj2) {
        if ((i2 & 2) != 0) {
            str2 = null;
        }
        if ((i2 & 4) != 0) {
            obj = null;
        }
        resultHandler.replyError(str, str2, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void replyError$lambda$1(MethodChannel.Result result, String code, String str, Object obj) {
        Intrinsics.checkNotNullParameter(code, "$code");
        if (result != null) {
            result.error(code, str, obj);
        }
    }

    public final void notImplemented() {
        handler.post(new Runnable() { // from class: x.c
            @Override // java.lang.Runnable
            public final void run() {
                ResultHandler.notImplemented$lambda$2(this.f26889a);
            }
        });
    }

    public final void reply(@Nullable final Object any) {
        final MethodChannel.Result result = this.result;
        this.result = null;
        handler.post(new Runnable() { // from class: x.b
            @Override // java.lang.Runnable
            public final void run() {
                ResultHandler.reply$lambda$0(result, any);
            }
        });
    }

    public final void replyError(@NotNull final String code, @Nullable final String message, @Nullable final Object obj) {
        Intrinsics.checkNotNullParameter(code, "code");
        final MethodChannel.Result result = this.result;
        this.result = null;
        handler.post(new Runnable() { // from class: x.a
            @Override // java.lang.Runnable
            public final void run() {
                ResultHandler.replyError$lambda$1(result, code, message, obj);
            }
        });
    }
}

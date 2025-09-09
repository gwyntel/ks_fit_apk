package xyz.luan.audioplayers;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
/* synthetic */ class AudioplayersPlugin$onAttachedToEngine$2$1 extends FunctionReferenceImpl implements Function2<MethodCall, MethodChannel.Result, Unit> {
    AudioplayersPlugin$onAttachedToEngine$2$1(Object obj) {
        super(2, obj, AudioplayersPlugin.class, "globalMethodHandler", "globalMethodHandler(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo1invoke(MethodCall methodCall, MethodChannel.Result result) {
        invoke2(methodCall, result);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull MethodCall p02, @NotNull MethodChannel.Result p1) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        ((AudioplayersPlugin) this.receiver).globalMethodHandler(p02, p1);
    }
}

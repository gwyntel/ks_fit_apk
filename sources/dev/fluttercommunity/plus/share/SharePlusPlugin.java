package dev.fluttercommunity.plus.share;

import android.content.Context;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00142\u00020\u00012\u00020\u0002:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000bH\u0016J\b\u0010\u0011\u001a\u00020\u000bH\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000fH\u0016J\u0010\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Ldev/fluttercommunity/plus/share/SharePlusPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "()V", "manager", "Ldev/fluttercommunity/plus/share/ShareSuccessManager;", "methodChannel", "Lio/flutter/plugin/common/MethodChannel;", "share", "Ldev/fluttercommunity/plus/share/Share;", "onAttachedToActivity", "", "binding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "onAttachedToEngine", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "onReattachedToActivityForConfigChanges", "Companion", "share_plus_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class SharePlusPlugin implements FlutterPlugin, ActivityAware {

    @NotNull
    private static final String CHANNEL = "dev.fluttercommunity.plus/share";
    private ShareSuccessManager manager;
    private MethodChannel methodChannel;
    private Share share;

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        ShareSuccessManager shareSuccessManager = this.manager;
        Share share = null;
        if (shareSuccessManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("manager");
            shareSuccessManager = null;
        }
        binding.addActivityResultListener(shareSuccessManager);
        Share share2 = this.share;
        if (share2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("share");
        } else {
            share = share2;
        }
        share.setActivity(binding.getActivity());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.methodChannel = new MethodChannel(binding.getBinaryMessenger(), CHANNEL);
        Context applicationContext = binding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.manager = new ShareSuccessManager(applicationContext);
        Context applicationContext2 = binding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
        ShareSuccessManager shareSuccessManager = this.manager;
        MethodChannel methodChannel = null;
        if (shareSuccessManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("manager");
            shareSuccessManager = null;
        }
        Share share = new Share(applicationContext2, null, shareSuccessManager);
        this.share = share;
        ShareSuccessManager shareSuccessManager2 = this.manager;
        if (shareSuccessManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("manager");
            shareSuccessManager2 = null;
        }
        MethodCallHandler methodCallHandler = new MethodCallHandler(share, shareSuccessManager2);
        MethodChannel methodChannel2 = this.methodChannel;
        if (methodChannel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("methodChannel");
        } else {
            methodChannel = methodChannel2;
        }
        methodChannel.setMethodCallHandler(methodCallHandler);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        Share share = this.share;
        if (share == null) {
            Intrinsics.throwUninitializedPropertyAccessException("share");
            share = null;
        }
        share.setActivity(null);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        MethodChannel methodChannel = this.methodChannel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("methodChannel");
            methodChannel = null;
        }
        methodChannel.setMethodCallHandler(null);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NotNull ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        onAttachedToActivity(binding);
    }
}

package com.fluttercandies.image_editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.fluttercandies.image_editor.common.font.FontUtils;
import com.fluttercandies.image_editor.core.BitmapWrapper;
import com.fluttercandies.image_editor.core.ImageHandler;
import com.fluttercandies.image_editor.core.ImageMerger;
import com.fluttercandies.image_editor.core.ResultHandler;
import com.fluttercandies.image_editor.error.BitmapDecodeException;
import com.fluttercandies.image_editor.option.FlipOption;
import com.fluttercandies.image_editor.option.FormatOption;
import com.fluttercandies.image_editor.option.MergeOption;
import com.fluttercandies.image_editor.option.Option;
import com.fluttercandies.image_editor.util.ConvertUtils;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 -2\u00020\u00012\u00020\u0002:\u0001-B\u0005¢\u0006\u0002\u0010\u0003J4\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\u0002J \u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\rH\u0002J\u0012\u0010\u0016\u001a\u00020\u00072\b\b\u0001\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\f\u0010#\u001a\u00020\u001e*\u00020\u0013H\u0002J\f\u0010$\u001a\u00020\u000b*\u00020\u0013H\u0002J\u000e\u0010%\u001a\u0004\u0018\u00010&*\u00020\u0013H\u0002J\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(*\u00020\u00132\u0006\u0010*\u001a\u00020\u001eH\u0002J\u000e\u0010+\u001a\u0004\u0018\u00010\u0011*\u00020\u0013H\u0002J\u000e\u0010,\u001a\u0004\u0018\u00010\u0011*\u00020\u0013H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/fluttercandies/image_editor/ImageEditorPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "()V", "applicationContext", "Landroid/content/Context;", "handle", "", "imageHandler", "Lcom/fluttercandies/image_editor/core/ImageHandler;", "formatOption", "Lcom/fluttercandies/image_editor/option/FormatOption;", "outputMemory", "", "resultHandler", "Lcom/fluttercandies/image_editor/core/ResultHandler;", "targetPath", "", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "handleMerge", "memory", "onAttachedToEngine", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "onMethodCall", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "wrapperBitmapWrapper", "Lcom/fluttercandies/image_editor/core/BitmapWrapper;", "bitmap", "Landroid/graphics/Bitmap;", "exifInterface", "Landroidx/exifinterface/media/ExifInterface;", "getBitmap", "getFormatOption", "getMemory", "", "getOptions", "", "Lcom/fluttercandies/image_editor/option/Option;", "bitmapWrapper", "getSrc", "getTarget", "Companion", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nImageEditorPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ImageEditorPlugin.kt\ncom/fluttercandies/image_editor/ImageEditorPlugin\n+ 2 ImageEditorPlugin.kt\ncom/fluttercandies/image_editor/ImageEditorPlugin$Companion\n*L\n1#1,221:1\n37#2,4:222\n*S KotlinDebug\n*F\n+ 1 ImageEditorPlugin.kt\ncom/fluttercandies/image_editor/ImageEditorPlugin\n*L\n55#1:222,4\n*E\n"})
/* loaded from: classes3.dex */
public final class ImageEditorPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String channelName = "com.fluttercandies/image_editor";

    @NotNull
    private static final ExecutorService threadPool;

    @Nullable
    private Context applicationContext;

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\t\u001a\u00020\n2\u000e\b\u0004\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\fH\u0086\bø\u0001\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\r"}, d2 = {"Lcom/fluttercandies/image_editor/ImageEditorPlugin$Companion;", "", "()V", "channelName", "", "threadPool", "Ljava/util/concurrent/ExecutorService;", "getThreadPool", "()Ljava/util/concurrent/ExecutorService;", "runOnBackground", "", "block", "Lkotlin/Function0;", "image_editor_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ExecutorService getThreadPool() {
            return ImageEditorPlugin.threadPool;
        }

        public final void runOnBackground(@NotNull final Function0<Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            getThreadPool().execute(new Runnable() { // from class: com.fluttercandies.image_editor.ImageEditorPlugin$Companion$runOnBackground$1
                @Override // java.lang.Runnable
                public final void run() {
                    block.invoke();
                }
            });
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        ExecutorService executorServiceNewCachedThreadPool = Executors.newCachedThreadPool();
        Intrinsics.checkNotNullExpressionValue(executorServiceNewCachedThreadPool, "newCachedThreadPool(...)");
        threadPool = executorServiceNewCachedThreadPool;
    }

    private final BitmapWrapper getBitmap(MethodCall methodCall) {
        String src = getSrc(methodCall);
        if (src != null) {
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(src);
            ExifInterface exifInterface = new ExifInterface(src);
            Intrinsics.checkNotNull(bitmapDecodeFile);
            return wrapperBitmapWrapper(bitmapDecodeFile, exifInterface);
        }
        byte[] memory = getMemory(methodCall);
        if (memory == null) {
            throw new BitmapDecodeException();
        }
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(memory, 0, memory.length);
        ExifInterface exifInterface2 = new ExifInterface(new ByteArrayInputStream(memory));
        Intrinsics.checkNotNull(bitmapDecodeByteArray);
        return wrapperBitmapWrapper(bitmapDecodeByteArray, exifInterface2);
    }

    private final FormatOption getFormatOption(MethodCall methodCall) {
        return ConvertUtils.INSTANCE.getFormatOption(methodCall);
    }

    private final byte[] getMemory(MethodCall methodCall) {
        return (byte[]) methodCall.argument("image");
    }

    private final List<Option> getOptions(MethodCall methodCall, BitmapWrapper bitmapWrapper) {
        Object objArgument = methodCall.argument(Constant.METHOD_OPTIONS);
        Intrinsics.checkNotNull(objArgument);
        return ConvertUtils.INSTANCE.convertMapOption((List) objArgument, bitmapWrapper);
    }

    private final String getSrc(MethodCall methodCall) {
        return (String) methodCall.argument("src");
    }

    private final String getTarget(MethodCall methodCall) {
        return (String) methodCall.argument("target");
    }

    private final void handle(ImageHandler imageHandler, FormatOption formatOption, boolean outputMemory, ResultHandler resultHandler, String targetPath) {
        if (outputMemory) {
            resultHandler.reply(imageHandler.outputByteArray(formatOption));
        } else if (targetPath == null) {
            resultHandler.reply(null);
        } else {
            imageHandler.outputToFile(targetPath, formatOption);
            resultHandler.reply(targetPath);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleMerge(MethodCall call, ResultHandler resultHandler, boolean memory) {
        Object objArgument = call.argument("option");
        Intrinsics.checkNotNull(objArgument, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        MergeOption mergeOption = new MergeOption((Map) objArgument);
        byte[] bArrProcess = new ImageMerger(mergeOption).process();
        if (bArrProcess == null) {
            ResultHandler.replyError$default(resultHandler, "Cannot merge image.", null, null, 6, null);
            return;
        }
        if (memory) {
            resultHandler.reply(bArrProcess);
            return;
        }
        String str = mergeOption.getFormatOption().getFormat() == 1 ? "jpg" : "png";
        Context context = this.applicationContext;
        Intrinsics.checkNotNull(context);
        File file = new File(context.getCacheDir(), System.currentTimeMillis() + "." + str);
        FilesKt.writeBytes(file, bArrProcess);
        resultHandler.reply(file.getPath());
    }

    private final BitmapWrapper wrapperBitmapWrapper(Bitmap bitmap, ExifInterface exifInterface) {
        int i2 = 0;
        FlipOption flipOption = new FlipOption(false, false, 2, null);
        switch (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)) {
            case 2:
                flipOption = new FlipOption(true, false, 2, null);
                break;
            case 3:
                i2 = 180;
                break;
            case 4:
                flipOption = new FlipOption(false, true, 1, null);
                break;
            case 5:
                flipOption = new FlipOption(true, false, 2, null);
            case 6:
                i2 = 90;
                break;
            case 7:
                flipOption = new FlipOption(true, false, 2, null);
            case 8:
                i2 = 270;
                break;
        }
        return new BitmapWrapper(bitmap, i2, flipOption);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.applicationContext = binding.getApplicationContext();
        new MethodChannel(binding.getBinaryMessenger(), channelName).setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.applicationContext = null;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NotNull final MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        final ResultHandler resultHandler = new ResultHandler(result);
        INSTANCE.getThreadPool().execute(new Runnable() { // from class: com.fluttercandies.image_editor.ImageEditorPlugin$onMethodCall$$inlined$runOnBackground$1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
            java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
             */
            @Override // java.lang.Runnable
            public final void run() {
                File cacheDir;
                try {
                    String str = call.method;
                    if (str != null) {
                        switch (str.hashCode()) {
                            case -2032648323:
                                if (!str.equals("memoryToMemory")) {
                                    break;
                                } else {
                                    this.handle(call, resultHandler, true);
                                    return;
                                }
                            case -1708153454:
                                if (!str.equals("registerFont")) {
                                    break;
                                } else {
                                    Object objArgument = call.argument("path");
                                    Intrinsics.checkNotNull(objArgument);
                                    resultHandler.reply(FontUtils.registerFont((String) objArgument));
                                    return;
                                }
                            case -563320815:
                                if (!str.equals("getCachePath")) {
                                    break;
                                } else {
                                    ResultHandler resultHandler2 = resultHandler;
                                    Context context = this.applicationContext;
                                    resultHandler2.reply((context == null || (cacheDir = context.getCacheDir()) == null) ? null : cacheDir.getAbsolutePath());
                                    return;
                                }
                            case 215369967:
                                if (!str.equals("mergeToFile")) {
                                    break;
                                } else {
                                    this.handleMerge(call, resultHandler, false);
                                    return;
                                }
                            case 712763128:
                                if (!str.equals("memoryToFile")) {
                                    break;
                                } else {
                                    this.handle(call, resultHandler, false);
                                    return;
                                }
                            case 1008861108:
                                if (!str.equals("mergeToMemory")) {
                                    break;
                                } else {
                                    this.handleMerge(call, resultHandler, true);
                                    return;
                                }
                            case 1064226040:
                                if (!str.equals("fileToMemory")) {
                                    break;
                                } else {
                                    this.handle(call, resultHandler, true);
                                    return;
                                }
                            case 1824364339:
                                if (!str.equals("fileToFile")) {
                                    break;
                                } else {
                                    this.handle(call, resultHandler, false);
                                    return;
                                }
                        }
                    }
                    resultHandler.notImplemented();
                } catch (BitmapDecodeException unused) {
                    ResultHandler.replyError$default(resultHandler, "Decode bitmap error.", null, null, 6, null);
                } catch (Exception e2) {
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    try {
                        e2.printStackTrace(printWriter);
                        ResultHandler resultHandler3 = resultHandler;
                        String string = stringWriter.getBuffer().toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        resultHandler3.replyError(string, "", null);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(printWriter, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(printWriter, th);
                            throw th2;
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handle(MethodCall call, ResultHandler resultHandler, boolean outputMemory) {
        BitmapWrapper bitmap = getBitmap(call);
        ImageHandler imageHandler = new ImageHandler(bitmap.getBitmap());
        imageHandler.handle(getOptions(call, bitmap));
        handle(imageHandler, getFormatOption(call), outputMemory, resultHandler, getTarget(call));
    }
}

package com.kineapps.flutterarchive;

import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.tekartik.sqflite.Constant;
import com.xiaomi.infra.galaxy.fds.Common;
import com.yc.utesdk.ble.close.KeyType;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\u0018\u0000 =2\u00020\u00012\u00020\u0002:\u0002=>B\u0005¢\u0006\u0002\u0010\u0003JN\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\tH\u0082@¢\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u0018H\u0002J\u0018\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0007H\u0016J\u0010\u0010 \u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0007H\u0016J\u0018\u0010!\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J&\u0010\u0012\u001a\u00020&2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0082@¢\u0006\u0002\u0010+J8\u0010,\u001a\u00020\u00182\u0006\u0010-\u001a\u00020\u000f2\b\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\tH\u0082@¢\u0006\u0002\u00101J>\u00102\u001a\u00020\u00182\u0006\u00103\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u00104\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\tH\u0082@¢\u0006\u0002\u00105J\u001c\u00106\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u000208072\u0006\u00109\u001a\u00020(H\u0002J.\u0010:\u001a\u00020\u00182\u0006\u00103\u001a\u00020\u000f2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u000f0<2\u0006\u0010-\u001a\u00020\u000f2\u0006\u00104\u001a\u00020\u0011H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lcom/kineapps/flutterarchive/FlutterArchivePlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "()V", "methodChannel", "Lio/flutter/plugin/common/MethodChannel;", "pluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "addFilesInDirectoryToZip", "", "zipOutputStream", "Ljava/util/zip/ZipOutputStream;", "rootDirectory", "Ljava/io/File;", "directoryPath", "", "recurseSubDirs", "", "reportProgress", "jobId", "totalFilesCount", "totalHandledFilesCount", "(Ljava/util/zip/ZipOutputStream;Ljava/io/File;Ljava/lang/String;ZZIIILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "doOnAttachedToEngine", "", "messenger", "Lio/flutter/plugin/common/BinaryMessenger;", "doOnDetachedFromEngine", "getFilesCount", "dir", "onAttachedToEngine", "binding", "onDetachedFromEngine", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "Lcom/kineapps/flutterarchive/ZipFileOperation;", "zipEntry", "Ljava/util/zip/ZipEntry;", "progress", "", "(ILjava/util/zip/ZipEntry;DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unzip", "zipFilePath", "zipFileCharSet", "Ljava/nio/charset/Charset;", "destinationDirPath", "(Ljava/lang/String;Ljava/nio/charset/Charset;Ljava/lang/String;ZILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "zip", "sourceDirPath", "includeBaseDirectory", "(Ljava/lang/String;Ljava/lang/String;ZZZILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "zipEntryToMap", "", "", "ze", "zipFiles", "relativeFilePaths", "", "Companion", "ZipFileEx", "flutter_archive_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class FlutterArchivePlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

    @NotNull
    private static final String LOG_TAG = "FlutterArchivePlugin";

    @Nullable
    private MethodChannel methodChannel;

    @Nullable
    private FlutterPlugin.FlutterPluginBinding pluginBinding;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lcom/kineapps/flutterarchive/FlutterArchivePlugin$ZipFileEx;", "Ljava/util/zip/ZipFile;", "Ljava/io/Closeable;", "name", "", "(Ljava/lang/String;)V", "flutter_archive_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ZipFileEx extends ZipFile implements Closeable {
        public ZipFileEx(@Nullable String str) {
            super(str);
        }
    }

    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin", f = "FlutterArchivePlugin.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, l = {254, KeyType.SEND_MESSAGE_REPLY_DATA_COMMAND, KeyType.SET_BRIGHT_SCREEN_PARAM, KeyType.SET_AI_AGENT_TYPE}, m = "addFilesInDirectoryToZip", n = {"this", "zipOutputStream", "rootDirectory", "directoryPath", "files", "handledFilesCount", "path", "entry", "recurseSubDirs", "reportProgress", "jobId", "totalFilesCount", "this", "zipOutputStream", "rootDirectory", "directoryPath", "files", "handledFilesCount", "path", "recurseSubDirs", "reportProgress", "jobId", "totalFilesCount", "this", "zipOutputStream", "rootDirectory", "directoryPath", "files", "handledFilesCount", "recurseSubDirs", "reportProgress", "jobId", "totalFilesCount", "this", "zipOutputStream", "rootDirectory", "directoryPath", "files", "handledFilesCount", "recurseSubDirs", "reportProgress", "jobId", "totalFilesCount"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "Z$0", "Z$1", "I$0", "I$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "Z$0", "Z$1", "I$0", "I$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "Z$0", "Z$1", "I$0", "I$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "Z$0", "Z$1", "I$0", "I$1"})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$addFilesInDirectoryToZip$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        boolean Z$0;
        boolean Z$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlutterArchivePlugin.this.addFilesInDirectoryToZip(null, null, null, false, false, 0, 0, 0, this);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$addFilesInDirectoryToZip$2", f = "FlutterArchivePlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$addFilesInDirectoryToZip$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ZipEntry $entry;
        final /* synthetic */ ZipOutputStream $zipOutputStream;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(ZipOutputStream zipOutputStream, ZipEntry zipEntry, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$zipOutputStream = zipOutputStream;
            this.$entry = zipEntry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass2(this.$zipOutputStream, this.$entry, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$zipOutputStream.putNextEntry(this.$entry);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$addFilesInDirectoryToZip$3", f = "FlutterArchivePlugin.kt", i = {0, 0}, l = {294}, m = "invokeSuspend", n = {"fileInputStream", "entry"}, s = {"L$2", "L$3"})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$addFilesInDirectoryToZip$3, reason: invalid class name */
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Object>, Object> {
        final /* synthetic */ File $f;
        final /* synthetic */ Ref.IntRef $handledFilesCount;
        final /* synthetic */ int $jobId;
        final /* synthetic */ String $relativePath;
        final /* synthetic */ boolean $reportProgress;
        final /* synthetic */ int $totalFilesCount;
        final /* synthetic */ ZipOutputStream $zipOutputStream;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        final /* synthetic */ FlutterArchivePlugin this$0;

        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$addFilesInDirectoryToZip$3$WhenMappings */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ZipFileOperation.values().length];
                try {
                    iArr[ZipFileOperation.INCLUDE_ITEM.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ZipFileOperation.CANCEL.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(File file, String str, boolean z2, Ref.IntRef intRef, int i2, FlutterArchivePlugin flutterArchivePlugin, int i3, ZipOutputStream zipOutputStream, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$f = file;
            this.$relativePath = str;
            this.$reportProgress = z2;
            this.$handledFilesCount = intRef;
            this.$totalFilesCount = i2;
            this.this$0 = flutterArchivePlugin;
            this.$jobId = i3;
            this.$zipOutputStream = zipOutputStream;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass3(this.$f, this.$relativePath, this.$reportProgress, this.$handledFilesCount, this.$totalFilesCount, this.this$0, this.$jobId, this.$zipOutputStream, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        /* renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo1invoke(CoroutineScope coroutineScope, Continuation<? super Object> continuation) {
            return invoke2(coroutineScope, (Continuation<Object>) continuation);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:21:0x00ab  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00ba A[Catch: all -> 0x0027, TRY_LEAVE, TryCatch #0 {all -> 0x0027, blocks: (B:6:0x0020, B:19:0x008a, B:23:0x00ae, B:25:0x00b2, B:26:0x00b9, B:27:0x00ba), top: B:38:0x0020 }] */
        /* JADX WARN: Type inference failed for: r3v9, types: [java.io.Closeable] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r17) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 228
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kineapps.flutterarchive.FlutterArchivePlugin.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<Object> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$1", f = "FlutterArchivePlugin.kt", i = {}, l = {105}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$1, reason: invalid class name and case insensitive filesystem */
    static final class C04661 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ MethodCall $call;
        final /* synthetic */ MethodChannel.Result $result;
        int label;
        final /* synthetic */ FlutterArchivePlugin this$0;

        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$1$1", f = "FlutterArchivePlugin.kt", i = {}, l = {106}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$1$1, reason: invalid class name and collision with other inner class name */
        static final class C01391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ boolean $includeBaseDirectory;
            final /* synthetic */ Integer $jobId;
            final /* synthetic */ boolean $recurseSubDirs;
            final /* synthetic */ Boolean $reportProgress;
            final /* synthetic */ String $sourceDir;
            final /* synthetic */ String $zipFile;
            int label;
            final /* synthetic */ FlutterArchivePlugin this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01391(FlutterArchivePlugin flutterArchivePlugin, String str, String str2, boolean z2, boolean z3, Boolean bool, Integer num, Continuation<? super C01391> continuation) {
                super(2, continuation);
                this.this$0 = flutterArchivePlugin;
                this.$sourceDir = str;
                this.$zipFile = str2;
                this.$recurseSubDirs = z2;
                this.$includeBaseDirectory = z3;
                this.$reportProgress = bool;
                this.$jobId = num;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new C01391(this.this$0, this.$sourceDir, this.$zipFile, this.$recurseSubDirs, this.$includeBaseDirectory, this.$reportProgress, this.$jobId, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i2 = this.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlutterArchivePlugin flutterArchivePlugin = this.this$0;
                    String str = this.$sourceDir;
                    Intrinsics.checkNotNull(str);
                    String str2 = this.$zipFile;
                    Intrinsics.checkNotNull(str2);
                    boolean z2 = this.$recurseSubDirs;
                    boolean z3 = this.$includeBaseDirectory;
                    boolean zAreEqual = Intrinsics.areEqual(this.$reportProgress, Boxing.boxBoolean(true));
                    Integer num = this.$jobId;
                    Intrinsics.checkNotNull(num);
                    int iIntValue = num.intValue();
                    this.label = 1;
                    if (flutterArchivePlugin.zip(str, str2, z2, z3, zAreEqual, iIntValue, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
            public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
                return ((C01391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04661(MethodCall methodCall, MethodChannel.Result result, FlutterArchivePlugin flutterArchivePlugin, Continuation<? super C04661> continuation) {
            super(2, continuation);
            this.$call = methodCall;
            this.$result = result;
            this.this$0 = flutterArchivePlugin;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04661(this.$call, this.$result, this.this$0, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    String str = (String) this.$call.argument("sourceDir");
                    String str2 = (String) this.$call.argument("zipFile");
                    boolean zAreEqual = Intrinsics.areEqual(this.$call.argument("recurseSubDirs"), Boxing.boxBoolean(true));
                    boolean zAreEqual2 = Intrinsics.areEqual(this.$call.argument("includeBaseDirectory"), Boxing.boxBoolean(true));
                    Boolean bool = (Boolean) this.$call.argument("reportProgress");
                    Integer num = (Integer) this.$call.argument("jobId");
                    CoroutineDispatcher io2 = Dispatchers.getIO();
                    C01391 c01391 = new C01391(this.this$0, str, str2, zAreEqual, zAreEqual2, bool, num, null);
                    this.label = 1;
                    if (BuildersKt.withContext(io2, c01391, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$result.success(Boxing.boxBoolean(true));
            } catch (Exception e2) {
                e2.printStackTrace();
                this.$result.error("zip_error", e2.getLocalizedMessage(), e2.toString());
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04661) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$2", f = "FlutterArchivePlugin.kt", i = {}, l = {131}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$2, reason: invalid class name and case insensitive filesystem */
    static final class C04672 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ MethodCall $call;
        final /* synthetic */ MethodChannel.Result $result;
        int label;
        final /* synthetic */ FlutterArchivePlugin this$0;

        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$2$1", f = "FlutterArchivePlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$2$1, reason: invalid class name */
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ List<String> $files;
            final /* synthetic */ boolean $includeBaseDirectory;
            final /* synthetic */ String $sourceDir;
            final /* synthetic */ String $zipFile;
            int label;
            final /* synthetic */ FlutterArchivePlugin this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(FlutterArchivePlugin flutterArchivePlugin, String str, List<String> list, String str2, boolean z2, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = flutterArchivePlugin;
                this.$sourceDir = str;
                this.$files = list;
                this.$zipFile = str2;
                this.$includeBaseDirectory = z2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, this.$sourceDir, this.$files, this.$zipFile, this.$includeBaseDirectory, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                FlutterArchivePlugin flutterArchivePlugin = this.this$0;
                String str = this.$sourceDir;
                Intrinsics.checkNotNull(str);
                List<String> list = this.$files;
                Intrinsics.checkNotNull(list);
                String str2 = this.$zipFile;
                Intrinsics.checkNotNull(str2);
                flutterArchivePlugin.zipFiles(str, list, str2, this.$includeBaseDirectory);
                return Unit.INSTANCE;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
            public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04672(MethodCall methodCall, MethodChannel.Result result, FlutterArchivePlugin flutterArchivePlugin, Continuation<? super C04672> continuation) {
            super(2, continuation);
            this.$call = methodCall;
            this.$result = result;
            this.this$0 = flutterArchivePlugin;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04672(this.$call, this.$result, this.this$0, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    String str = (String) this.$call.argument("sourceDir");
                    List list = (List) this.$call.argument("files");
                    String str2 = (String) this.$call.argument("zipFile");
                    boolean zAreEqual = Intrinsics.areEqual(this.$call.argument("includeBaseDirectory"), Boxing.boxBoolean(true));
                    CoroutineDispatcher io2 = Dispatchers.getIO();
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, str, list, str2, zAreEqual, null);
                    this.label = 1;
                    if (BuildersKt.withContext(io2, anonymousClass1, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                this.$result.success(Boxing.boxBoolean(true));
            } catch (Exception e2) {
                e2.printStackTrace();
                this.$result.error("zip_error", e2.getLocalizedMessage(), e2.toString());
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04672) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$3", f = "FlutterArchivePlugin.kt", i = {}, l = {153}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nFlutterArchivePlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlutterArchivePlugin.kt\ncom/kineapps/flutterarchive/FlutterArchivePlugin$onMethodCall$3\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,524:1\n1#2:525\n*E\n"})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$3, reason: invalid class name and case insensitive filesystem */
    static final class C04683 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ MethodCall $call;
        final /* synthetic */ MethodChannel.Result $result;
        int label;
        final /* synthetic */ FlutterArchivePlugin this$0;

        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$3$1", f = "FlutterArchivePlugin.kt", i = {}, l = {154}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$onMethodCall$3$1, reason: invalid class name */
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Charset $charset;
            final /* synthetic */ String $destinationDir;
            final /* synthetic */ Integer $jobId;
            final /* synthetic */ Boolean $reportProgress;
            final /* synthetic */ String $zipFile;
            int label;
            final /* synthetic */ FlutterArchivePlugin this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(FlutterArchivePlugin flutterArchivePlugin, String str, Charset charset, String str2, Boolean bool, Integer num, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = flutterArchivePlugin;
                this.$zipFile = str;
                this.$charset = charset;
                this.$destinationDir = str2;
                this.$reportProgress = bool;
                this.$jobId = num;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, this.$zipFile, this.$charset, this.$destinationDir, this.$reportProgress, this.$jobId, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i2 = this.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlutterArchivePlugin flutterArchivePlugin = this.this$0;
                    String str = this.$zipFile;
                    Intrinsics.checkNotNull(str);
                    Charset charset = this.$charset;
                    String str2 = this.$destinationDir;
                    Intrinsics.checkNotNull(str2);
                    boolean zAreEqual = Intrinsics.areEqual(this.$reportProgress, Boxing.boxBoolean(true));
                    Integer num = this.$jobId;
                    Intrinsics.checkNotNull(num);
                    int iIntValue = num.intValue();
                    this.label = 1;
                    if (flutterArchivePlugin.unzip(str, charset, str2, zAreEqual, iIntValue, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
            public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04683(MethodCall methodCall, MethodChannel.Result result, FlutterArchivePlugin flutterArchivePlugin, Continuation<? super C04683> continuation) {
            super(2, continuation);
            this.$call = methodCall;
            this.$result = result;
            this.this$0 = flutterArchivePlugin;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04683(this.$call, this.$result, this.this$0, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    String str = (String) this.$call.argument("zipFile");
                    String str2 = (String) this.$call.argument("zipFileCharset");
                    String str3 = (String) this.$call.argument("destinationDir");
                    Boolean bool = (Boolean) this.$call.argument("reportProgress");
                    Integer num = (Integer) this.$call.argument("jobId");
                    Charset charsetForName = str2 != null ? Charset.forName(str2) : null;
                    Log.d(FlutterArchivePlugin.LOG_TAG, "onMethodCall / unzip...");
                    CoroutineDispatcher io2 = Dispatchers.getIO();
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, str, charsetForName, str3, bool, num, null);
                    this.label = 1;
                    if (BuildersKt.withContext(io2, anonymousClass1, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                Log.d(FlutterArchivePlugin.LOG_TAG, "...onMethodCall / unzip");
                this.$result.success(Boxing.boxBoolean(true));
            } catch (Exception e2) {
                e2.printStackTrace();
                this.$result.error("unzip_error", e2.getLocalizedMessage(), e2.toString());
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04683) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$reportProgress$2", f = "FlutterArchivePlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$reportProgress$2, reason: invalid class name and case insensitive filesystem */
    static final class C04692 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CompletableDeferred<ZipFileOperation> $deferred;
        final /* synthetic */ Map<String, Object> $map;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04692(Map<String, Object> map, CompletableDeferred<ZipFileOperation> completableDeferred, Continuation<? super C04692> continuation) {
            super(2, continuation);
            this.$map = map;
            this.$deferred = completableDeferred;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return FlutterArchivePlugin.this.new C04692(this.$map, this.$deferred, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            MethodChannel methodChannel = FlutterArchivePlugin.this.methodChannel;
            if (methodChannel != null) {
                Map<String, Object> map = this.$map;
                final CompletableDeferred<ZipFileOperation> completableDeferred = this.$deferred;
                methodChannel.invokeMethod("progress", map, new MethodChannel.Result() { // from class: com.kineapps.flutterarchive.FlutterArchivePlugin.reportProgress.2.1
                    @Override // io.flutter.plugin.common.MethodChannel.Result
                    public void error(@NotNull String code, @Nullable String msg, @Nullable Object details) {
                        Intrinsics.checkNotNullParameter(code, "code");
                        Log.e(FlutterArchivePlugin.LOG_TAG, "invokeMethod - error: " + msg);
                        completableDeferred.complete(ZipFileOperation.INCLUDE_ITEM);
                    }

                    @Override // io.flutter.plugin.common.MethodChannel.Result
                    public void notImplemented() {
                        Log.e(FlutterArchivePlugin.LOG_TAG, "invokeMethod - notImplemented");
                        completableDeferred.complete(ZipFileOperation.INCLUDE_ITEM);
                    }

                    @Override // io.flutter.plugin.common.MethodChannel.Result
                    public void success(@Nullable Object result) {
                        Log.i(FlutterArchivePlugin.LOG_TAG, "invokeMethod - success: " + result);
                        if (Intrinsics.areEqual(result, Constant.PARAM_CANCEL)) {
                            completableDeferred.complete(ZipFileOperation.CANCEL);
                        } else if (Intrinsics.areEqual(result, "skipItem")) {
                            completableDeferred.complete(ZipFileOperation.SKIP_ITEM);
                        } else {
                            completableDeferred.complete(ZipFileOperation.INCLUDE_ITEM);
                        }
                    }
                });
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04692) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin", f = "FlutterArchivePlugin.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, l = {Common.HTTP_STATUS_CONFLICT, 431}, m = "unzip", n = {"this", "destinationDirPath", "destinationDir", "zipFile", "ze", "outputFile", "reportProgress", "jobId", "entriesCount", "currentEntryIndex", "this", "destinationDirPath", "destinationDir", "zipFile", "reportProgress", "jobId", "entriesCount", "currentEntryIndex"}, s = {"L$0", "L$1", "L$2", "L$3", "L$5", "L$7", "Z$0", "I$0", "D$0", "D$1", "L$0", "L$1", "L$2", "L$3", "Z$0", "I$0", "D$0", "D$1"})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$unzip$1, reason: invalid class name and case insensitive filesystem */
    static final class C04701 extends ContinuationImpl {
        double D$0;
        double D$1;
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        C04701(Continuation<? super C04701> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlutterArchivePlugin.this.unzip(null, null, null, false, 0, this);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$zip$2", f = "FlutterArchivePlugin.kt", i = {}, l = {199}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.kineapps.flutterarchive.FlutterArchivePlugin$zip$2, reason: invalid class name and case insensitive filesystem */
    static final class C04712 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Integer>, Object> {
        final /* synthetic */ int $jobId;
        final /* synthetic */ boolean $recurseSubDirs;
        final /* synthetic */ boolean $reportProgress;
        final /* synthetic */ File $rootDirectory;
        final /* synthetic */ String $sourceDirPath;
        final /* synthetic */ int $totalFileCount;
        final /* synthetic */ String $zipFilePath;
        Object L$0;
        int label;
        final /* synthetic */ FlutterArchivePlugin this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04712(String str, FlutterArchivePlugin flutterArchivePlugin, File file, String str2, boolean z2, boolean z3, int i2, int i3, Continuation<? super C04712> continuation) {
            super(2, continuation);
            this.$zipFilePath = str;
            this.this$0 = flutterArchivePlugin;
            this.$rootDirectory = file;
            this.$sourceDirPath = str2;
            this.$recurseSubDirs = z2;
            this.$reportProgress = z3;
            this.$jobId = i2;
            this.$totalFileCount = i3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04712(this.$zipFilePath, this.this$0, this.$rootDirectory, this.$sourceDirPath, this.$recurseSubDirs, this.$reportProgress, this.$jobId, this.$totalFileCount, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Closeable closeable;
            Throwable th;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(this.$zipFilePath)));
                FlutterArchivePlugin flutterArchivePlugin = this.this$0;
                File file = this.$rootDirectory;
                String str = this.$sourceDirPath;
                boolean z2 = this.$recurseSubDirs;
                boolean z3 = this.$reportProgress;
                int i3 = this.$jobId;
                int i4 = this.$totalFileCount;
                try {
                    Intrinsics.checkNotNull(file);
                    this.L$0 = zipOutputStream;
                    this.label = 1;
                    Object objAddFilesInDirectoryToZip = flutterArchivePlugin.addFilesInDirectoryToZip(zipOutputStream, file, str, z2, z3, i3, i4, 0, this);
                    if (objAddFilesInDirectoryToZip == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    closeable = zipOutputStream;
                    obj = objAddFilesInDirectoryToZip;
                } catch (Throwable th2) {
                    closeable = zipOutputStream;
                    th = th2;
                    throw th;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                closeable = (Closeable) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        throw th;
                    } catch (Throwable th4) {
                        CloseableKt.closeFinally(closeable, th);
                        throw th4;
                    }
                }
            }
            Integer numBoxInt = Boxing.boxInt(((Number) obj).intValue());
            CloseableKt.closeFinally(closeable, null);
            return numBoxInt;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Integer> continuation) {
            return ((C04712) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x02e0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0322 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x0190 -> B:58:0x03d3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x0258 -> B:58:0x03d3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x03b3 -> B:57:0x03c4). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object addFilesInDirectoryToZip(java.util.zip.ZipOutputStream r34, java.io.File r35, java.lang.String r36, boolean r37, boolean r38, int r39, int r40, int r41, kotlin.coroutines.Continuation<? super java.lang.Integer> r42) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 999
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kineapps.flutterarchive.FlutterArchivePlugin.addFilesInDirectoryToZip(java.util.zip.ZipOutputStream, java.io.File, java.lang.String, boolean, boolean, int, int, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final void doOnAttachedToEngine(BinaryMessenger messenger) {
        Log.d(LOG_TAG, "doOnAttachedToEngine - IN");
        MethodChannel methodChannel = new MethodChannel(messenger, "flutter_archive");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        Log.d(LOG_TAG, "doOnAttachedToEngine - OUT");
    }

    private final void doOnDetachedFromEngine() {
        Log.d(LOG_TAG, "doOnDetachedFromEngine - IN");
        if (this.pluginBinding == null) {
            Log.w(LOG_TAG, "doOnDetachedFromEngine - already detached");
        }
        this.pluginBinding = null;
        MethodChannel methodChannel = this.methodChannel;
        if (methodChannel != null) {
            methodChannel.setMethodCallHandler(null);
        }
        this.methodChannel = null;
        Log.d(LOG_TAG, "doOnDetachedFromEngine - OUT");
    }

    private final int getFilesCount(File dir, boolean recurseSubDirs) {
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles == null) {
            return 0;
        }
        int filesCount = 0;
        for (File file : fileArrListFiles) {
            if (recurseSubDirs && file.isDirectory()) {
                Intrinsics.checkNotNull(file);
                filesCount += getFilesCount(file, recurseSubDirs);
            } else {
                filesCount++;
            }
        }
        return filesCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object reportProgress(int i2, ZipEntry zipEntry, double d2, Continuation<? super ZipFileOperation> continuation) {
        Map mutableMap = MapsKt.toMutableMap(zipEntryToMap(zipEntry));
        mutableMap.put("jobId", Boxing.boxInt(i2));
        mutableMap.put("progress", Boxing.boxDouble(d2));
        CompletableDeferred completableDeferredCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getMain()), null, null, new C04692(mutableMap, completableDeferredCompletableDeferred$default, null), 3, null);
        return completableDeferredCompletableDeferred$default.await(continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0148 A[Catch: all -> 0x038d, TRY_LEAVE, TryCatch #6 {all -> 0x038d, blocks: (B:30:0x0142, B:32:0x0148), top: B:105:0x0142 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x024e A[Catch: all -> 0x0069, TRY_LEAVE, TryCatch #4 {all -> 0x0069, blocks: (B:13:0x0050, B:48:0x0228, B:51:0x024e, B:86:0x0396, B:20:0x00a2, B:28:0x0123), top: B:101:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02a6 A[Catch: all -> 0x02c9, TRY_ENTER, TryCatch #3 {all -> 0x02c9, blocks: (B:62:0x029c, B:65:0x02a6, B:68:0x02ce, B:70:0x02d4, B:72:0x02da, B:74:0x02f8), top: B:99:0x029c }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x02ce A[Catch: all -> 0x02c9, TryCatch #3 {all -> 0x02c9, blocks: (B:62:0x029c, B:65:0x02a6, B:68:0x02ce, B:70:0x02d4, B:72:0x02da, B:74:0x02f8), top: B:99:0x029c }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0392  */
    /* JADX WARN: Type inference failed for: r13v1, types: [java.lang.Object, java.util.zip.ZipFile] */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x0242 -> B:105:0x0142). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:65:0x02a6 -> B:105:0x0142). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:78:0x0346 -> B:79:0x0351). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object unzip(java.lang.String r28, java.nio.charset.Charset r29, java.lang.String r30, boolean r31, int r32, kotlin.coroutines.Continuation<? super kotlin.Unit> r33) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 934
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kineapps.flutterarchive.FlutterArchivePlugin.unzip(java.lang.String, java.nio.charset.Charset, java.lang.String, boolean, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object zip(String str, String str2, boolean z2, boolean z3, boolean z4, int i2, Continuation<? super Unit> continuation) throws Throwable {
        int filesCount;
        Log.i("zip", "sourceDirPath: " + str + ", zipFilePath: " + str2 + ", recurseSubDirs: " + z2 + ", includeBaseDirectory: " + z3);
        File parentFile = z3 ? new File(str).getParentFile() : new File(str);
        if (z4) {
            Intrinsics.checkNotNull(parentFile);
            filesCount = getFilesCount(parentFile, z2);
        } else {
            filesCount = 0;
        }
        Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new C04712(str2, this, parentFile, str, z2, z4, i2, filesCount, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    private final Map<String, Object> zipEntryToMap(ZipEntry ze) {
        return MapsKt.mapOf(TuplesKt.to("name", ze.getName()), TuplesKt.to("isDirectory", Boolean.valueOf(ze.isDirectory())), TuplesKt.to("comment", ze.getComment()), TuplesKt.to("modificationDate", Long.valueOf(ze.getTime())), TuplesKt.to("uncompressedSize", Long.valueOf(ze.getSize())), TuplesKt.to("compressedSize", Long.valueOf(ze.getCompressedSize())), TuplesKt.to("crc", Long.valueOf(ze.getCrc())), TuplesKt.to("compressionMethod", ze.getMethod() == 8 ? "deflated" : "none"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zipFiles(String sourceDirPath, List<String> relativeFilePaths, String zipFilePath, boolean includeBaseDirectory) throws IOException {
        Log.i("zip", "sourceDirPath: " + sourceDirPath + ", zipFilePath: " + zipFilePath + ", includeBaseDirectory: " + includeBaseDirectory);
        String strJoinToString$default = CollectionsKt.joinToString$default(relativeFilePaths, ",", null, null, 0, null, null, 62, null);
        StringBuilder sb = new StringBuilder();
        sb.append("Files: ");
        sb.append(strJoinToString$default);
        Log.i("zip", sb.toString());
        File parentFile = includeBaseDirectory ? new File(sourceDirPath).getParentFile() : new File(sourceDirPath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath)));
        try {
            for (String str : relativeFilePaths) {
                Intrinsics.checkNotNull(parentFile);
                File fileResolve = FilesKt.resolve(parentFile, str);
                String path = FilesKt.relativeTo(fileResolve, parentFile).getPath();
                Log.i("zip", "Adding file: " + path);
                FileInputStream fileInputStream = new FileInputStream(fileResolve);
                try {
                    ZipEntry zipEntry = new ZipEntry(path);
                    zipEntry.setTime(fileResolve.lastModified());
                    zipEntry.setSize(fileResolve.length());
                    zipOutputStream.putNextEntry(zipEntry);
                    ByteStreamsKt.copyTo$default(fileInputStream, zipOutputStream, 0, 2, null);
                    CloseableKt.closeFinally(fileInputStream, null);
                } finally {
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipOutputStream, null);
        } finally {
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Log.d(LOG_TAG, "onAttachedToEngine - IN");
        if (this.pluginBinding != null) {
            Log.w(LOG_TAG, "onAttachedToEngine - already attached");
        }
        this.pluginBinding = binding;
        BinaryMessenger binaryMessenger = binding != null ? binding.getBinaryMessenger() : null;
        Intrinsics.checkNotNull(binaryMessenger);
        doOnAttachedToEngine(binaryMessenger);
        Log.d(LOG_TAG, "onAttachedToEngine - OUT");
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Log.d(LOG_TAG, "onDetachedFromEngine");
        doOnDetachedFromEngine();
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
        String str = call.method;
        if (str != null) {
            int iHashCode = str.hashCode();
            if (iHashCode != -626402228) {
                if (iHashCode != -152551466) {
                    if (iHashCode == 111449576 && str.equals("unzip")) {
                        BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new C04683(call, result, this, null), 3, null);
                        return;
                    }
                } else if (str.equals("zipFiles")) {
                    BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new C04672(call, result, this, null), 3, null);
                    return;
                }
            } else if (str.equals("zipDirectory")) {
                BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new C04661(call, result, this, null), 3, null);
                return;
            }
        }
        result.notImplemented();
    }
}

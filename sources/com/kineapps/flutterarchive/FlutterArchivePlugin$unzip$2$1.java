package com.kineapps.flutterarchive;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.kineapps.flutterarchive.FlutterArchivePlugin$unzip$2$1", f = "FlutterArchivePlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
@SourceDebugExtension({"SMAP\nFlutterArchivePlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlutterArchivePlugin.kt\ncom/kineapps/flutterarchive/FlutterArchivePlugin$unzip$2$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,524:1\n1#2:525\n*E\n"})
/* loaded from: classes4.dex */
final class FlutterArchivePlugin$unzip$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Long>, Object> {
    final /* synthetic */ File $outputFile;
    final /* synthetic */ ZipEntry $ze;
    final /* synthetic */ ZipFile $zipFile;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FlutterArchivePlugin$unzip$2$1(ZipFile zipFile, ZipEntry zipEntry, File file, Continuation<? super FlutterArchivePlugin$unzip$2$1> continuation) {
        super(2, continuation);
        this.$zipFile = zipFile;
        this.$ze = zipEntry;
        this.$outputFile = file;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        return new FlutterArchivePlugin$unzip$2$1(this.$zipFile, this.$ze, this.$outputFile, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        InputStream inputStream = this.$zipFile.getInputStream(this.$ze);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.$outputFile);
            try {
                Intrinsics.checkNotNull(inputStream);
                long jCopyTo$default = ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                CloseableKt.closeFinally(fileOutputStream, null);
                Long lBoxLong = Boxing.boxLong(jCopyTo$default);
                CloseableKt.closeFinally(inputStream, null);
                return lBoxLong;
            } finally {
            }
        } finally {
        }
    }

    @Override // kotlin.jvm.functions.Function2
    @Nullable
    /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Long> continuation) {
        return ((FlutterArchivePlugin$unzip$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}

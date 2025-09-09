package xyz.luan.audioplayers.player;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.source.UrlSource;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "xyz.luan.audioplayers.player.SoundPoolPlayer$urlSource$1$1", f = "SoundPoolPlayer.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes5.dex */
final class SoundPoolPlayer$urlSource$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ SoundPoolPlayer $soundPoolPlayer;
    final /* synthetic */ long $start;
    final /* synthetic */ UrlSource $value;
    int label;
    final /* synthetic */ SoundPoolPlayer this$0;

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "xyz.luan.audioplayers.player.SoundPoolPlayer$urlSource$1$1$1", f = "SoundPoolPlayer.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: xyz.luan.audioplayers.player.SoundPoolPlayer$urlSource$1$1$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $actualUrl;
        final /* synthetic */ SoundPoolPlayer $soundPoolPlayer;
        final /* synthetic */ long $start;
        final /* synthetic */ UrlSource $value;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SoundPoolPlayer this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(SoundPoolPlayer soundPoolPlayer, String str, SoundPoolPlayer soundPoolPlayer2, UrlSource urlSource, long j2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = soundPoolPlayer;
            this.$actualUrl = str;
            this.$soundPoolPlayer = soundPoolPlayer2;
            this.$value = urlSource;
            this.$start = j2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$actualUrl, this.$soundPoolPlayer, this.$value, this.$start, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            this.this$0.getWrappedPlayer().handleLog("Now loading " + this.$actualUrl);
            int iLoad = this.this$0.getSoundPool().load(this.$actualUrl, 1);
            this.this$0.soundPoolWrapper.getSoundIdToPlayer().put(Boxing.boxInt(iLoad), this.$soundPoolPlayer);
            this.this$0.setSoundId(Boxing.boxInt(iLoad));
            this.this$0.getWrappedPlayer().handleLog("time to call load() for " + this.$value + ": " + (System.currentTimeMillis() - this.$start) + " player=" + coroutineScope);
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
    SoundPoolPlayer$urlSource$1$1(UrlSource urlSource, SoundPoolPlayer soundPoolPlayer, SoundPoolPlayer soundPoolPlayer2, long j2, Continuation<? super SoundPoolPlayer$urlSource$1$1> continuation) {
        super(2, continuation);
        this.$value = urlSource;
        this.this$0 = soundPoolPlayer;
        this.$soundPoolPlayer = soundPoolPlayer2;
        this.$start = j2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        return new SoundPoolPlayer$urlSource$1$1(this.$value, this.this$0, this.$soundPoolPlayer, this.$start, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BuildersKt__Builders_commonKt.launch$default(this.this$0.mainScope, Dispatchers.getMain(), null, new AnonymousClass1(this.this$0, this.$value.getAudioPathForSoundPool(), this.$soundPoolPlayer, this.$value, this.$start, null), 2, null);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    @Nullable
    /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
        return ((SoundPoolPlayer$urlSource$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}

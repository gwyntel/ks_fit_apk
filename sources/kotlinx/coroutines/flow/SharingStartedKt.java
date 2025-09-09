package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.time.Duration;
import kotlinx.coroutines.flow.SharingStarted;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a+\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0006\u0010\u0007\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b\u0019¨\u0006\b"}, d2 = {"WhileSubscribed", "Lkotlinx/coroutines/flow/SharingStarted;", "Lkotlinx/coroutines/flow/SharingStarted$Companion;", "stopTimeout", "Lkotlin/time/Duration;", "replayExpiration", "WhileSubscribed-5qebJ5I", "(Lkotlinx/coroutines/flow/SharingStarted$Companion;JJ)Lkotlinx/coroutines/flow/SharingStarted;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class SharingStartedKt {
    @NotNull
    /* renamed from: WhileSubscribed-5qebJ5I, reason: not valid java name */
    public static final SharingStarted m2367WhileSubscribed5qebJ5I(@NotNull SharingStarted.Companion companion, long j2, long j3) {
        return new StartedWhileSubscribed(Duration.m2169getInWholeMillisecondsimpl(j2), Duration.m2169getInWholeMillisecondsimpl(j3));
    }

    /* renamed from: WhileSubscribed-5qebJ5I$default, reason: not valid java name */
    public static /* synthetic */ SharingStarted m2368WhileSubscribed5qebJ5I$default(SharingStarted.Companion companion, long j2, long j3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            j2 = Duration.INSTANCE.m2254getZEROUwyO8pc();
        }
        if ((i2 & 2) != 0) {
            j3 = Duration.INSTANCE.m2252getINFINITEUwyO8pc();
        }
        return m2367WhileSubscribed5qebJ5I(companion, j2, j3);
    }
}

package kotlinx.coroutines.debug.internal;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.Volatile;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: private */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class DebugProbesImpl$Installations$kotlinx$VolatileWrapper {

    @NotNull
    private static final AtomicIntegerFieldUpdater installations$FU = AtomicIntegerFieldUpdater.newUpdater(DebugProbesImpl$Installations$kotlinx$VolatileWrapper.class, "installations");

    @Volatile
    private volatile int installations;

    private DebugProbesImpl$Installations$kotlinx$VolatileWrapper() {
    }

    public /* synthetic */ DebugProbesImpl$Installations$kotlinx$VolatileWrapper(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}

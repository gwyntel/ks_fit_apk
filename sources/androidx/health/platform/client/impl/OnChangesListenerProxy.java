package androidx.health.platform.client.impl;

import androidx.health.platform.client.changes.ChangesEvent;
import androidx.health.platform.client.proto.ChangeProto;
import androidx.health.platform.client.service.IOnChangesListener;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Landroidx/health/platform/client/impl/OnChangesListenerProxy;", "Landroidx/health/platform/client/service/IOnChangesListener$Stub;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lkotlin/Function1;", "Landroidx/health/platform/client/proto/ChangeProto$ChangesEvent;", "", "(Lkotlin/jvm/functions/Function1;)V", "onChanges", "changesEvent", "Landroidx/health/platform/client/changes/ChangesEvent;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class OnChangesListenerProxy extends IOnChangesListener.Stub {

    @NotNull
    private final Function1<ChangeProto.ChangesEvent, Unit> listener;

    /* JADX WARN: Multi-variable type inference failed */
    public OnChangesListenerProxy(@NotNull Function1<? super ChangeProto.ChangesEvent, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
    }

    @Override // androidx.health.platform.client.service.IOnChangesListener
    public void onChanges(@NotNull ChangesEvent changesEvent) {
        Intrinsics.checkNotNullParameter(changesEvent, "changesEvent");
        this.listener.invoke(changesEvent.getProto());
    }
}

package androidx.webkit.internal;

import androidx.annotation.NonNull;
import androidx.webkit.ScriptHandler;
import java.lang.reflect.InvocationHandler;
import org.chromium.support_lib_boundary.ScriptHandlerBoundaryInterface;
import org.chromium.support_lib_boundary.util.BoundaryInterfaceReflectionUtil;

/* loaded from: classes2.dex */
public class ScriptHandlerImpl implements ScriptHandler {
    private final ScriptHandlerBoundaryInterface mBoundaryInterface;

    private ScriptHandlerImpl(@NonNull ScriptHandlerBoundaryInterface scriptHandlerBoundaryInterface) {
        this.mBoundaryInterface = scriptHandlerBoundaryInterface;
    }

    @NonNull
    public static ScriptHandlerImpl toScriptHandler(@NonNull InvocationHandler invocationHandler) {
        return new ScriptHandlerImpl((ScriptHandlerBoundaryInterface) BoundaryInterfaceReflectionUtil.castToSuppLibClass(ScriptHandlerBoundaryInterface.class, invocationHandler));
    }

    @Override // androidx.webkit.ScriptHandler
    public void remove() {
        this.mBoundaryInterface.remove();
    }
}

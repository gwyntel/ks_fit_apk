package org.mozilla.javascript;

import org.mozilla.javascript.PolicySecurityController;

/* loaded from: classes5.dex */
public class RhinoSecurityManager extends SecurityManager {
    protected Class<?> getCurrentScriptClass() {
        for (Class<?> cls : getClassContext()) {
            if ((cls != InterpretedFunction.class && NativeFunction.class.isAssignableFrom(cls)) || PolicySecurityController.SecureCaller.class.isAssignableFrom(cls)) {
                return cls;
            }
        }
        return null;
    }
}

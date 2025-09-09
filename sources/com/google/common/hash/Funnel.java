package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;

@DoNotMock("Implement with a lambda")
@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public interface Funnel<T> extends Serializable {
    void funnel(@ParametricNullness T t2, PrimitiveSink primitiveSink);
}

package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.RestrictedApi;

@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
interface PatternCompiler {
    @RestrictedApi(allowedOnPath = ".*/com/google/common/base/.*", explanation = "PatternCompiler is an implementation detail of com.google.common.base")
    CommonPattern compile(String str);

    @RestrictedApi(allowedOnPath = ".*/com/google/common/base/.*", explanation = "PatternCompiler is an implementation detail of com.google.common.base")
    boolean isPcreLike();
}

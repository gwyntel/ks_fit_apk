package com.google.firebase.encoders;

import androidx.annotation.NonNull;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public interface Encoder<TValue, TContext> {
    void encode(@NonNull TValue tvalue, @NonNull TContext tcontext) throws IOException;
}

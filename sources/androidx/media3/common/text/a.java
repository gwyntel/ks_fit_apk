package androidx.media3.common.text;

import android.os.Bundle;
import com.google.common.base.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class a implements Function {
    @Override // com.google.common.base.Function
    public final Object apply(Object obj) {
        return Cue.fromBundle((Bundle) obj);
    }
}

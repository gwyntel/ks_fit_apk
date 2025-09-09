package androidx.media3.common;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;

@UnstableApi
/* loaded from: classes.dex */
public class Label {
    private static final String FIELD_LANGUAGE_INDEX = Util.intToStringMaxRadix(0);
    private static final String FIELD_VALUE_INDEX = Util.intToStringMaxRadix(1);

    @Nullable
    public final String language;
    public final String value;

    public Label(@Nullable String str, String str2) {
        this.language = Util.normalizeLanguageCode(str);
        this.value = str2;
    }

    public static Label fromBundle(Bundle bundle) {
        return new Label(bundle.getString(FIELD_LANGUAGE_INDEX), (String) Assertions.checkNotNull(bundle.getString(FIELD_VALUE_INDEX)));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Label label = (Label) obj;
        return Util.areEqual(this.language, label.language) && Util.areEqual(this.value, label.value);
    }

    public int hashCode() {
        int iHashCode = this.value.hashCode() * 31;
        String str = this.language;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        String str = this.language;
        if (str != null) {
            bundle.putString(FIELD_LANGUAGE_INDEX, str);
        }
        bundle.putString(FIELD_VALUE_INDEX, this.value);
        return bundle;
    }
}

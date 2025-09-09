package androidx.webkit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.internal.AnalyticsEvents;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import org.mozilla.javascript.typedarrays.NativeArrayBuffer;

/* loaded from: classes2.dex */
public class WebMessageCompat {
    public static final int TYPE_ARRAY_BUFFER = 1;
    public static final int TYPE_STRING = 0;

    @Nullable
    private final byte[] mArrayBuffer;

    @Nullable
    private final WebMessagePortCompat[] mPorts;

    @Nullable
    private final String mString;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Type {
    }

    public WebMessageCompat(@Nullable String str) {
        this(str, (WebMessagePortCompat[]) null);
    }

    private void checkType(int i2) {
        if (i2 == this.mType) {
            return;
        }
        throw new IllegalStateException("Wrong data accessor type detected. " + typeToString(this.mType) + " expected, but got " + typeToString(i2));
    }

    @NonNull
    private String typeToString(int i2) {
        return i2 != 0 ? i2 != 1 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : NativeArrayBuffer.CLASS_NAME : "String";
    }

    @NonNull
    public byte[] getArrayBuffer() {
        checkType(1);
        Objects.requireNonNull(this.mArrayBuffer);
        return this.mArrayBuffer;
    }

    @Nullable
    public String getData() {
        checkType(0);
        return this.mString;
    }

    @Nullable
    public WebMessagePortCompat[] getPorts() {
        return this.mPorts;
    }

    public int getType() {
        return this.mType;
    }

    public WebMessageCompat(@Nullable String str, @Nullable WebMessagePortCompat[] webMessagePortCompatArr) {
        this.mString = str;
        this.mArrayBuffer = null;
        this.mPorts = webMessagePortCompatArr;
        this.mType = 0;
    }

    public WebMessageCompat(@NonNull byte[] bArr) {
        this(bArr, (WebMessagePortCompat[]) null);
    }

    public WebMessageCompat(@NonNull byte[] bArr, @Nullable WebMessagePortCompat[] webMessagePortCompatArr) {
        Objects.requireNonNull(bArr);
        this.mArrayBuffer = bArr;
        this.mString = null;
        this.mPorts = webMessagePortCompatArr;
        this.mType = 1;
    }
}

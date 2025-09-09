package androidx.webkit.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;
import org.chromium.support_lib_boundary.WebMessagePayloadBoundaryInterface;

/* loaded from: classes2.dex */
public class WebMessagePayloadAdapter implements WebMessagePayloadBoundaryInterface {

    @Nullable
    private final byte[] mArrayBuffer;

    @Nullable
    private final String mString;
    private final int mType;

    public WebMessagePayloadAdapter(@Nullable String str) {
        this.mType = 0;
        this.mString = str;
        this.mArrayBuffer = null;
    }

    private void checkType(int i2) {
        if (this.mType == i2) {
            return;
        }
        throw new IllegalStateException("Expected " + i2 + ", but type is " + this.mType);
    }

    @Override // org.chromium.support_lib_boundary.WebMessagePayloadBoundaryInterface
    @NonNull
    public byte[] getAsArrayBuffer() {
        checkType(1);
        byte[] bArr = this.mArrayBuffer;
        Objects.requireNonNull(bArr);
        return bArr;
    }

    @Override // org.chromium.support_lib_boundary.WebMessagePayloadBoundaryInterface
    @Nullable
    public String getAsString() {
        checkType(0);
        return this.mString;
    }

    @Override // org.chromium.support_lib_boundary.FeatureFlagHolderBoundaryInterface
    @NonNull
    public String[] getSupportedFeatures() {
        return new String[0];
    }

    @Override // org.chromium.support_lib_boundary.WebMessagePayloadBoundaryInterface
    public int getType() {
        return this.mType;
    }

    public WebMessagePayloadAdapter(@NonNull byte[] bArr) {
        this.mType = 1;
        this.mString = null;
        this.mArrayBuffer = bArr;
    }
}

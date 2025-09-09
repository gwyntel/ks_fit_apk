package androidx.media3.extractor.text;

import android.os.Bundle;
import android.os.Parcel;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.BundleCollectionUtil;
import androidx.media3.common.util.UnstableApi;
import com.umeng.analytics.pro.bc;
import java.util.ArrayList;

@UnstableApi
/* loaded from: classes2.dex */
public final class CueDecoder {
    public CuesWithTiming decode(long j2, byte[] bArr) {
        return decode(j2, bArr, 0, bArr.length);
    }

    public CuesWithTiming decode(long j2, byte[] bArr, int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, i2, i3);
        parcelObtain.setDataPosition(0);
        Bundle bundle = parcelObtain.readBundle(Bundle.class.getClassLoader());
        parcelObtain.recycle();
        return new CuesWithTiming(BundleCollectionUtil.fromBundleList(new androidx.media3.common.text.a(), (ArrayList) Assertions.checkNotNull(bundle.getParcelableArrayList(bc.aL))), j2, bundle.getLong("d"));
    }
}

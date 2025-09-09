package androidx.media;

import androidx.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcel;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class AudioAttributesImplBaseParcelizer {
    public static AudioAttributesImplBase read(VersionedParcel versionedParcel) {
        AudioAttributesImplBase audioAttributesImplBase = new AudioAttributesImplBase();
        audioAttributesImplBase.f4626a = versionedParcel.readInt(audioAttributesImplBase.f4626a, 1);
        audioAttributesImplBase.f4627b = versionedParcel.readInt(audioAttributesImplBase.f4627b, 2);
        audioAttributesImplBase.f4628c = versionedParcel.readInt(audioAttributesImplBase.f4628c, 3);
        audioAttributesImplBase.f4629d = versionedParcel.readInt(audioAttributesImplBase.f4629d, 4);
        return audioAttributesImplBase;
    }

    public static void write(AudioAttributesImplBase audioAttributesImplBase, VersionedParcel versionedParcel) {
        versionedParcel.setSerializationFlags(false, false);
        versionedParcel.writeInt(audioAttributesImplBase.f4626a, 1);
        versionedParcel.writeInt(audioAttributesImplBase.f4627b, 2);
        versionedParcel.writeInt(audioAttributesImplBase.f4628c, 3);
        versionedParcel.writeInt(audioAttributesImplBase.f4629d, 4);
    }
}

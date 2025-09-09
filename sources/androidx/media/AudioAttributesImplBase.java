package androidx.media;

import android.os.Bundle;
import androidx.annotation.NonNull;
import java.util.Arrays;

/* loaded from: classes.dex */
class AudioAttributesImplBase implements AudioAttributesImpl {

    /* renamed from: a, reason: collision with root package name */
    int f4626a;

    /* renamed from: b, reason: collision with root package name */
    int f4627b;

    /* renamed from: c, reason: collision with root package name */
    int f4628c;

    /* renamed from: d, reason: collision with root package name */
    int f4629d;

    AudioAttributesImplBase() {
        this.f4626a = 0;
        this.f4627b = 0;
        this.f4628c = 0;
        this.f4629d = -1;
    }

    public static AudioAttributesImpl fromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return new AudioAttributesImplBase(bundle.getInt("androidx.media.audio_attrs.CONTENT_TYPE", 0), bundle.getInt("androidx.media.audio_attrs.FLAGS", 0), bundle.getInt("androidx.media.audio_attrs.USAGE", 0), bundle.getInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", -1));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AudioAttributesImplBase)) {
            return false;
        }
        AudioAttributesImplBase audioAttributesImplBase = (AudioAttributesImplBase) obj;
        return this.f4627b == audioAttributesImplBase.getContentType() && this.f4628c == audioAttributesImplBase.getFlags() && this.f4626a == audioAttributesImplBase.getUsage() && this.f4629d == audioAttributesImplBase.f4629d;
    }

    @Override // androidx.media.AudioAttributesImpl
    public Object getAudioAttributes() {
        return null;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getContentType() {
        return this.f4627b;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getFlags() {
        int i2 = this.f4628c;
        int legacyStreamType = getLegacyStreamType();
        if (legacyStreamType == 6) {
            i2 |= 4;
        } else if (legacyStreamType == 7) {
            i2 |= 1;
        }
        return i2 & 273;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getLegacyStreamType() {
        int i2 = this.f4629d;
        return i2 != -1 ? i2 : AudioAttributesCompat.a(false, this.f4628c, this.f4626a);
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getRawLegacyStreamType() {
        return this.f4629d;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getUsage() {
        return this.f4626a;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getVolumeControlStream() {
        return AudioAttributesCompat.a(true, this.f4628c, this.f4626a);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.f4627b), Integer.valueOf(this.f4628c), Integer.valueOf(this.f4626a), Integer.valueOf(this.f4629d)});
    }

    @Override // androidx.media.AudioAttributesImpl
    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("androidx.media.audio_attrs.USAGE", this.f4626a);
        bundle.putInt("androidx.media.audio_attrs.CONTENT_TYPE", this.f4627b);
        bundle.putInt("androidx.media.audio_attrs.FLAGS", this.f4628c);
        int i2 = this.f4629d;
        if (i2 != -1) {
            bundle.putInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", i2);
        }
        return bundle;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (this.f4629d != -1) {
            sb.append(" stream=");
            sb.append(this.f4629d);
            sb.append(" derived");
        }
        sb.append(" usage=");
        sb.append(AudioAttributesCompat.c(this.f4626a));
        sb.append(" content=");
        sb.append(this.f4627b);
        sb.append(" flags=0x");
        sb.append(Integer.toHexString(this.f4628c).toUpperCase());
        return sb.toString();
    }

    AudioAttributesImplBase(int i2, int i3, int i4, int i5) {
        this.f4627b = i2;
        this.f4628c = i3;
        this.f4626a = i4;
        this.f4629d = i5;
    }
}

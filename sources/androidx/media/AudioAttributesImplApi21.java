package androidx.media;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(21)
/* loaded from: classes.dex */
class AudioAttributesImplApi21 implements AudioAttributesImpl {
    private static final String TAG = "AudioAttributesCompat21";

    /* renamed from: c, reason: collision with root package name */
    static Method f4623c;

    /* renamed from: a, reason: collision with root package name */
    AudioAttributes f4624a;

    /* renamed from: b, reason: collision with root package name */
    int f4625b;

    AudioAttributesImplApi21() {
        this.f4625b = -1;
    }

    static Method a() {
        try {
            if (f4623c == null) {
                f4623c = AudioAttributes.class.getMethod("toLegacyStreamType", AudioAttributes.class);
            }
            return f4623c;
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static AudioAttributesImpl fromBundle(Bundle bundle) {
        AudioAttributes audioAttributes;
        if (bundle == null || (audioAttributes = (AudioAttributes) bundle.getParcelable("androidx.media.audio_attrs.FRAMEWORKS")) == null) {
            return null;
        }
        return new AudioAttributesImplApi21(audioAttributes, bundle.getInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", -1));
    }

    public boolean equals(Object obj) {
        if (obj instanceof AudioAttributesImplApi21) {
            return this.f4624a.equals(((AudioAttributesImplApi21) obj).f4624a);
        }
        return false;
    }

    @Override // androidx.media.AudioAttributesImpl
    public Object getAudioAttributes() {
        return this.f4624a;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getContentType() {
        return this.f4624a.getContentType();
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getFlags() {
        return this.f4624a.getFlags();
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getLegacyStreamType() {
        int i2 = this.f4625b;
        if (i2 != -1) {
            return i2;
        }
        Method methodA = a();
        if (methodA == null) {
            Log.w(TAG, "No AudioAttributes#toLegacyStreamType() on API: " + Build.VERSION.SDK_INT);
            return -1;
        }
        try {
            return ((Integer) methodA.invoke(null, this.f4624a)).intValue();
        } catch (IllegalAccessException | InvocationTargetException e2) {
            Log.w(TAG, "getLegacyStreamType() failed on API: " + Build.VERSION.SDK_INT, e2);
            return -1;
        }
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getRawLegacyStreamType() {
        return this.f4625b;
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getUsage() {
        return this.f4624a.getUsage();
    }

    @Override // androidx.media.AudioAttributesImpl
    public int getVolumeControlStream() {
        return Build.VERSION.SDK_INT >= 26 ? this.f4624a.getVolumeControlStream() : AudioAttributesCompat.a(true, getFlags(), getUsage());
    }

    public int hashCode() {
        return this.f4624a.hashCode();
    }

    @Override // androidx.media.AudioAttributesImpl
    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("androidx.media.audio_attrs.FRAMEWORKS", this.f4624a);
        int i2 = this.f4625b;
        if (i2 != -1) {
            bundle.putInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", i2);
        }
        return bundle;
    }

    public String toString() {
        return "AudioAttributesCompat: audioattributes=" + this.f4624a;
    }

    AudioAttributesImplApi21(AudioAttributes audioAttributes) {
        this(audioAttributes, -1);
    }

    AudioAttributesImplApi21(AudioAttributes audioAttributes, int i2) {
        this.f4624a = audioAttributes;
        this.f4625b = i2;
    }
}

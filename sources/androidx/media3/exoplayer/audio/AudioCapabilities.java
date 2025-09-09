package androidx.media3.exoplayer.audio;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.Uri;
import android.provider.Settings;
import android.util.Pair;
import android.util.SparseArray;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.extractor.OpusUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Ints;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.android.agoo.xiaomi.MiPushRegistar;

@UnstableApi
/* loaded from: classes.dex */
public final class AudioCapabilities {
    private static final String EXTERNAL_SURROUND_SOUND_KEY = "external_surround_sound_enabled";
    private static final String FORCE_EXTERNAL_SURROUND_SOUND_KEY = "use_external_surround_sound_flag";
    private final SparseArray<AudioProfile> encodingToAudioProfile;
    private final int maxChannelCount;
    public static final AudioCapabilities DEFAULT_AUDIO_CAPABILITIES = new AudioCapabilities(ImmutableList.of(AudioProfile.DEFAULT_AUDIO_PROFILE));

    @SuppressLint({"InlinedApi"})
    private static final ImmutableList<Integer> EXTERNAL_SURROUND_SOUND_ENCODINGS = ImmutableList.of(2, 5, 6);

    /* renamed from: a, reason: collision with root package name */
    static final ImmutableMap f5099a = new ImmutableMap.Builder().put(5, 6).put(17, 6).put(7, 6).put(30, 10).put(18, 6).put(6, 8).put(8, 8).put(14, 8).buildOrThrow();

    @RequiresApi(23)
    private static final class Api23 {
        private Api23() {
        }

        @DoNotInline
        private static ImmutableSet<Integer> getAllBluetoothDeviceTypes() {
            ImmutableSet.Builder builderAdd = new ImmutableSet.Builder().add((Object[]) new Integer[]{8, 7});
            int i2 = Util.SDK_INT;
            if (i2 >= 31) {
                builderAdd.add((Object[]) new Integer[]{26, 27});
            }
            if (i2 >= 33) {
                builderAdd.add((ImmutableSet.Builder) 30);
            }
            return builderAdd.build();
        }

        @DoNotInline
        public static boolean isBluetoothConnected(AudioManager audioManager, @Nullable AudioDeviceInfoApi23 audioDeviceInfoApi23) {
            AudioDeviceInfo[] devices = audioDeviceInfoApi23 == null ? ((AudioManager) Assertions.checkNotNull(audioManager)).getDevices(2) : new AudioDeviceInfo[]{audioDeviceInfoApi23.audioDeviceInfo};
            ImmutableSet<Integer> allBluetoothDeviceTypes = getAllBluetoothDeviceTypes();
            for (AudioDeviceInfo audioDeviceInfo : devices) {
                if (allBluetoothDeviceTypes.contains(Integer.valueOf(audioDeviceInfo.getType()))) {
                    return true;
                }
            }
            return false;
        }
    }

    @RequiresApi(29)
    private static final class Api29 {
        private Api29() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @DoNotInline
        public static ImmutableList<Integer> getDirectPlaybackSupportedEncodings(AudioAttributes audioAttributes) {
            ImmutableList.Builder builder = ImmutableList.builder();
            UnmodifiableIterator it = AudioCapabilities.f5099a.keySet().iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                int iIntValue = num.intValue();
                if (Util.SDK_INT >= Util.getApiLevelThatAudioFormatIntroducedAudioEncoding(iIntValue) && AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setChannelMask(12).setEncoding(iIntValue).setSampleRate(OpusUtil.SAMPLE_RATE).build(), audioAttributes.getAudioAttributesV21().audioAttributes)) {
                    builder.add((ImmutableList.Builder) num);
                }
            }
            builder.add((ImmutableList.Builder) 2);
            return builder.build();
        }

        @DoNotInline
        public static int getMaxSupportedChannelCountForPassthrough(int i2, int i3, AudioAttributes audioAttributes) {
            for (int i4 = 10; i4 > 0; i4--) {
                int audioTrackChannelConfig = Util.getAudioTrackChannelConfig(i4);
                if (audioTrackChannelConfig != 0 && AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setEncoding(i2).setSampleRate(i3).setChannelMask(audioTrackChannelConfig).build(), audioAttributes.getAudioAttributesV21().audioAttributes)) {
                    return i4;
                }
            }
            return 0;
        }
    }

    @RequiresApi(33)
    private static final class Api33 {
        private Api33() {
        }

        @DoNotInline
        public static AudioCapabilities getCapabilitiesInternalForDirectPlayback(AudioManager audioManager, AudioAttributes audioAttributes) {
            return new AudioCapabilities(AudioCapabilities.getAudioProfiles(audioManager.getDirectProfilesForAttributes(audioAttributes.getAudioAttributesV21().audioAttributes)));
        }

        @Nullable
        @DoNotInline
        public static AudioDeviceInfoApi23 getDefaultRoutedDeviceForAttributes(AudioManager audioManager, AudioAttributes audioAttributes) {
            try {
                List audioDevicesForAttributes = ((AudioManager) Assertions.checkNotNull(audioManager)).getAudioDevicesForAttributes(audioAttributes.getAudioAttributesV21().audioAttributes);
                if (audioDevicesForAttributes.isEmpty()) {
                    return null;
                }
                return new AudioDeviceInfoApi23((AudioDeviceInfo) audioDevicesForAttributes.get(0));
            } catch (RuntimeException unused) {
                return null;
            }
        }
    }

    static AudioCapabilities b(Context context, Intent intent, AudioAttributes audioAttributes, AudioDeviceInfoApi23 audioDeviceInfoApi23) {
        AudioManager audioManager = (AudioManager) Assertions.checkNotNull(context.getSystemService("audio"));
        if (audioDeviceInfoApi23 == null) {
            audioDeviceInfoApi23 = Util.SDK_INT >= 33 ? Api33.getDefaultRoutedDeviceForAttributes(audioManager, audioAttributes) : null;
        }
        int i2 = Util.SDK_INT;
        if (i2 >= 33 && (Util.isTv(context) || Util.isAutomotive(context))) {
            return Api33.getCapabilitiesInternalForDirectPlayback(audioManager, audioAttributes);
        }
        if (i2 >= 23 && Api23.isBluetoothConnected(audioManager, audioDeviceInfoApi23)) {
            return DEFAULT_AUDIO_CAPABILITIES;
        }
        ImmutableSet.Builder builder = new ImmutableSet.Builder();
        builder.add((ImmutableSet.Builder) 2);
        if (i2 >= 29 && (Util.isTv(context) || Util.isAutomotive(context))) {
            builder.addAll((Iterable) Api29.getDirectPlaybackSupportedEncodings(audioAttributes));
            return new AudioCapabilities(getAudioProfiles(Ints.toArray(builder.build()), 10));
        }
        ContentResolver contentResolver = context.getContentResolver();
        boolean z2 = Settings.Global.getInt(contentResolver, FORCE_EXTERNAL_SURROUND_SOUND_KEY, 0) == 1;
        if ((z2 || deviceMaySetExternalSurroundSoundGlobalSetting()) && Settings.Global.getInt(contentResolver, EXTERNAL_SURROUND_SOUND_KEY, 0) == 1) {
            builder.addAll((Iterable) EXTERNAL_SURROUND_SOUND_ENCODINGS);
        }
        if (intent == null || z2 || intent.getIntExtra("android.media.extra.AUDIO_PLUG_STATE", 0) != 1) {
            return new AudioCapabilities(getAudioProfiles(Ints.toArray(builder.build()), 10));
        }
        int[] intArrayExtra = intent.getIntArrayExtra("android.media.extra.ENCODINGS");
        if (intArrayExtra != null) {
            builder.addAll((Iterable) Ints.asList(intArrayExtra));
        }
        return new AudioCapabilities(getAudioProfiles(Ints.toArray(builder.build()), intent.getIntExtra("android.media.extra.MAX_CHANNEL_COUNT", 10)));
    }

    static AudioCapabilities c(Context context, AudioAttributes audioAttributes, AudioDeviceInfoApi23 audioDeviceInfoApi23) {
        return b(context, context.registerReceiver(null, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG")), audioAttributes, audioDeviceInfoApi23);
    }

    static Uri d() {
        if (deviceMaySetExternalSurroundSoundGlobalSetting()) {
            return Settings.Global.getUriFor(EXTERNAL_SURROUND_SOUND_KEY);
        }
        return null;
    }

    private static boolean deviceMaySetExternalSurroundSoundGlobalSetting() {
        String str = Util.MANUFACTURER;
        return "Amazon".equals(str) || MiPushRegistar.XIAOMI.equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(33)
    @SuppressLint({"WrongConstant"})
    public static ImmutableList<AudioProfile> getAudioProfiles(List<android.media.AudioProfile> list) {
        HashMap map = new HashMap();
        map.put(2, new HashSet(Ints.asList(12)));
        for (int i2 = 0; i2 < list.size(); i2++) {
            android.media.AudioProfile audioProfileA = a.a(list.get(i2));
            if (audioProfileA.getEncapsulationType() != 1) {
                int format = audioProfileA.getFormat();
                if (Util.isEncodingLinearPcm(format) || f5099a.containsKey(Integer.valueOf(format))) {
                    if (map.containsKey(Integer.valueOf(format))) {
                        ((Set) Assertions.checkNotNull((Set) map.get(Integer.valueOf(format)))).addAll(Ints.asList(audioProfileA.getChannelMasks()));
                    } else {
                        map.put(Integer.valueOf(format), new HashSet(Ints.asList(audioProfileA.getChannelMasks())));
                    }
                }
            }
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Map.Entry entry : map.entrySet()) {
            builder.add((ImmutableList.Builder) new AudioProfile(((Integer) entry.getKey()).intValue(), (Set<Integer>) entry.getValue()));
        }
        return builder.build();
    }

    @Deprecated
    public static AudioCapabilities getCapabilities(Context context) {
        return getCapabilities(context, AudioAttributes.DEFAULT, null);
    }

    private static int getChannelConfigForPassthrough(int i2) {
        int i3 = Util.SDK_INT;
        if (i3 <= 28) {
            if (i2 == 7) {
                i2 = 8;
            } else if (i2 == 3 || i2 == 4 || i2 == 5) {
                i2 = 6;
            }
        }
        if (i3 <= 26 && "fugu".equals(Util.DEVICE) && i2 == 1) {
            i2 = 2;
        }
        return Util.getAudioTrackChannelConfig(i2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioCapabilities)) {
            return false;
        }
        AudioCapabilities audioCapabilities = (AudioCapabilities) obj;
        return Util.contentEquals(this.encodingToAudioProfile, audioCapabilities.encodingToAudioProfile) && this.maxChannelCount == audioCapabilities.maxChannelCount;
    }

    @Nullable
    @Deprecated
    public Pair<Integer, Integer> getEncodingAndChannelConfigForPassthrough(Format format) {
        return getEncodingAndChannelConfigForPassthrough(format, AudioAttributes.DEFAULT);
    }

    public int getMaxChannelCount() {
        return this.maxChannelCount;
    }

    public int hashCode() {
        return this.maxChannelCount + (Util.contentHashCode(this.encodingToAudioProfile) * 31);
    }

    @Deprecated
    public boolean isPassthroughPlaybackSupported(Format format) {
        return isPassthroughPlaybackSupported(format, AudioAttributes.DEFAULT);
    }

    public boolean supportsEncoding(int i2) {
        return Util.contains(this.encodingToAudioProfile, i2);
    }

    public String toString() {
        return "AudioCapabilities[maxChannelCount=" + this.maxChannelCount + ", audioProfiles=" + this.encodingToAudioProfile + "]";
    }

    @Deprecated
    public AudioCapabilities(@Nullable int[] iArr, int i2) {
        this(getAudioProfiles(iArr, i2));
    }

    public static AudioCapabilities getCapabilities(Context context, AudioAttributes audioAttributes, @Nullable AudioDeviceInfo audioDeviceInfo) {
        return c(context, audioAttributes, (Util.SDK_INT < 23 || audioDeviceInfo == null) ? null : new AudioDeviceInfoApi23(audioDeviceInfo));
    }

    @Nullable
    public Pair<Integer, Integer> getEncodingAndChannelConfigForPassthrough(Format format, AudioAttributes audioAttributes) {
        int encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs);
        if (!f5099a.containsKey(Integer.valueOf(encoding))) {
            return null;
        }
        if (encoding == 18 && !supportsEncoding(18)) {
            encoding = 6;
        } else if ((encoding == 8 && !supportsEncoding(8)) || (encoding == 30 && !supportsEncoding(30))) {
            encoding = 7;
        }
        if (!supportsEncoding(encoding)) {
            return null;
        }
        AudioProfile audioProfile = (AudioProfile) Assertions.checkNotNull(this.encodingToAudioProfile.get(encoding));
        int maxSupportedChannelCountForPassthrough = format.channelCount;
        if (maxSupportedChannelCountForPassthrough == -1 || encoding == 18) {
            int i2 = format.sampleRate;
            if (i2 == -1) {
                i2 = OpusUtil.SAMPLE_RATE;
            }
            maxSupportedChannelCountForPassthrough = audioProfile.getMaxSupportedChannelCountForPassthrough(i2, audioAttributes);
        } else if (!format.sampleMimeType.equals(MimeTypes.AUDIO_DTS_X) || Util.SDK_INT >= 33) {
            if (!audioProfile.supportsChannelCount(maxSupportedChannelCountForPassthrough)) {
                return null;
            }
        } else if (maxSupportedChannelCountForPassthrough > 10) {
            return null;
        }
        int channelConfigForPassthrough = getChannelConfigForPassthrough(maxSupportedChannelCountForPassthrough);
        if (channelConfigForPassthrough == 0) {
            return null;
        }
        return Pair.create(Integer.valueOf(encoding), Integer.valueOf(channelConfigForPassthrough));
    }

    public boolean isPassthroughPlaybackSupported(Format format, AudioAttributes audioAttributes) {
        return getEncodingAndChannelConfigForPassthrough(format, audioAttributes) != null;
    }

    private AudioCapabilities(List<AudioProfile> list) {
        this.encodingToAudioProfile = new SparseArray<>();
        for (int i2 = 0; i2 < list.size(); i2++) {
            AudioProfile audioProfile = list.get(i2);
            this.encodingToAudioProfile.put(audioProfile.encoding, audioProfile);
        }
        int iMax = 0;
        for (int i3 = 0; i3 < this.encodingToAudioProfile.size(); i3++) {
            iMax = Math.max(iMax, this.encodingToAudioProfile.valueAt(i3).maxChannelCount);
        }
        this.maxChannelCount = iMax;
    }

    private static final class AudioProfile {
        public static final AudioProfile DEFAULT_AUDIO_PROFILE;

        @Nullable
        private final ImmutableSet<Integer> channelMasks;
        public final int encoding;
        public final int maxChannelCount;

        static {
            DEFAULT_AUDIO_PROFILE = Util.SDK_INT >= 33 ? new AudioProfile(2, getAllChannelMasksForMaxChannelCount(10)) : new AudioProfile(2, 10);
        }

        @RequiresApi(33)
        public AudioProfile(int i2, Set<Integer> set) {
            this.encoding = i2;
            ImmutableSet<Integer> immutableSetCopyOf = ImmutableSet.copyOf((Collection) set);
            this.channelMasks = immutableSetCopyOf;
            UnmodifiableIterator<Integer> it = immutableSetCopyOf.iterator();
            int iMax = 0;
            while (it.hasNext()) {
                iMax = Math.max(iMax, Integer.bitCount(it.next().intValue()));
            }
            this.maxChannelCount = iMax;
        }

        private static ImmutableSet<Integer> getAllChannelMasksForMaxChannelCount(int i2) {
            ImmutableSet.Builder builder = new ImmutableSet.Builder();
            for (int i3 = 1; i3 <= i2; i3++) {
                builder.add((ImmutableSet.Builder) Integer.valueOf(Util.getAudioTrackChannelConfig(i3)));
            }
            return builder.build();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AudioProfile)) {
                return false;
            }
            AudioProfile audioProfile = (AudioProfile) obj;
            return this.encoding == audioProfile.encoding && this.maxChannelCount == audioProfile.maxChannelCount && Util.areEqual(this.channelMasks, audioProfile.channelMasks);
        }

        public int getMaxSupportedChannelCountForPassthrough(int i2, AudioAttributes audioAttributes) {
            return this.channelMasks != null ? this.maxChannelCount : Util.SDK_INT >= 29 ? Api29.getMaxSupportedChannelCountForPassthrough(this.encoding, i2, audioAttributes) : ((Integer) Assertions.checkNotNull((Integer) AudioCapabilities.f5099a.getOrDefault(Integer.valueOf(this.encoding), 0))).intValue();
        }

        public int hashCode() {
            int i2 = ((this.encoding * 31) + this.maxChannelCount) * 31;
            ImmutableSet<Integer> immutableSet = this.channelMasks;
            return i2 + (immutableSet == null ? 0 : immutableSet.hashCode());
        }

        public boolean supportsChannelCount(int i2) {
            if (this.channelMasks == null) {
                return i2 <= this.maxChannelCount;
            }
            int audioTrackChannelConfig = Util.getAudioTrackChannelConfig(i2);
            if (audioTrackChannelConfig == 0) {
                return false;
            }
            return this.channelMasks.contains(Integer.valueOf(audioTrackChannelConfig));
        }

        public String toString() {
            return "AudioProfile[format=" + this.encoding + ", maxChannelCount=" + this.maxChannelCount + ", channelMasks=" + this.channelMasks + "]";
        }

        public AudioProfile(int i2, int i3) {
            this.encoding = i2;
            this.maxChannelCount = i3;
            this.channelMasks = null;
        }
    }

    private static ImmutableList<AudioProfile> getAudioProfiles(@Nullable int[] iArr, int i2) {
        ImmutableList.Builder builder = ImmutableList.builder();
        if (iArr == null) {
            iArr = new int[0];
        }
        for (int i3 : iArr) {
            builder.add((ImmutableList.Builder) new AudioProfile(i3, i2));
        }
        return builder.build();
    }
}

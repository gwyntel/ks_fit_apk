package androidx.media3.extractor;

import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.common.FileTypes;
import androidx.media3.common.Format;
import androidx.media3.common.util.TimestampAdjuster;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.amr.AmrExtractor;
import androidx.media3.extractor.avi.AviExtractor;
import androidx.media3.extractor.avif.AvifExtractor;
import androidx.media3.extractor.bmp.BmpExtractor;
import androidx.media3.extractor.flac.FlacExtractor;
import androidx.media3.extractor.flv.FlvExtractor;
import androidx.media3.extractor.heif.HeifExtractor;
import androidx.media3.extractor.jpeg.JpegExtractor;
import androidx.media3.extractor.mkv.MatroskaExtractor;
import androidx.media3.extractor.mp3.Mp3Extractor;
import androidx.media3.extractor.mp4.FragmentedMp4Extractor;
import androidx.media3.extractor.mp4.Mp4Extractor;
import androidx.media3.extractor.ogg.OggExtractor;
import androidx.media3.extractor.png.PngExtractor;
import androidx.media3.extractor.text.DefaultSubtitleParserFactory;
import androidx.media3.extractor.text.SubtitleParser;
import androidx.media3.extractor.text.SubtitleTranscodingExtractor;
import androidx.media3.extractor.ts.Ac3Extractor;
import androidx.media3.extractor.ts.Ac4Extractor;
import androidx.media3.extractor.ts.AdtsExtractor;
import androidx.media3.extractor.ts.DefaultTsPayloadReaderFactory;
import androidx.media3.extractor.ts.PsExtractor;
import androidx.media3.extractor.ts.TsExtractor;
import androidx.media3.extractor.wav.WavExtractor;
import androidx.media3.extractor.webp.WebpExtractor;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@UnstableApi
/* loaded from: classes2.dex */
public final class DefaultExtractorsFactory implements ExtractorsFactory {
    private static final int[] DEFAULT_EXTRACTOR_ORDER = {5, 4, 12, 8, 3, 10, 9, 11, 6, 2, 0, 1, 7, 16, 15, 14, 17, 18, 19, 20, 21};
    private static final ExtensionLoader FLAC_EXTENSION_LOADER = new ExtensionLoader(new ExtensionLoader.ConstructorSupplier() { // from class: androidx.media3.extractor.b
        @Override // androidx.media3.extractor.DefaultExtractorsFactory.ExtensionLoader.ConstructorSupplier
        public final Constructor getConstructor() {
            return DefaultExtractorsFactory.getFlacExtractorConstructor();
        }
    });
    private static final ExtensionLoader MIDI_EXTENSION_LOADER = new ExtensionLoader(new ExtensionLoader.ConstructorSupplier() { // from class: androidx.media3.extractor.c
        @Override // androidx.media3.extractor.DefaultExtractorsFactory.ExtensionLoader.ConstructorSupplier
        public final Constructor getConstructor() {
            return DefaultExtractorsFactory.getMidiExtractorConstructor();
        }
    });
    private int adtsFlags;
    private int amrFlags;
    private boolean constantBitrateSeekingAlwaysEnabled;
    private boolean constantBitrateSeekingEnabled;
    private int flacFlags;
    private int fragmentedMp4Flags;
    private int jpegFlags;
    private int matroskaFlags;
    private int mp3Flags;
    private int mp4Flags;
    private int tsFlags;

    @Nullable
    private ImmutableList<Format> tsSubtitleFormats;
    private int tsMode = 1;
    private int tsTimestampSearchBytes = TsExtractor.DEFAULT_TIMESTAMP_SEARCH_BYTES;
    private SubtitleParser.Factory subtitleParserFactory = new DefaultSubtitleParserFactory();
    private boolean textTrackTranscodingEnabled = true;

    /* JADX INFO: Access modifiers changed from: private */
    static final class ExtensionLoader {
        private final ConstructorSupplier constructorSupplier;
        private final AtomicBoolean extensionLoaded = new AtomicBoolean(false);

        @Nullable
        @GuardedBy("extensionLoaded")
        private Constructor<? extends Extractor> extractorConstructor;

        public interface ConstructorSupplier {
            @Nullable
            Constructor<? extends Extractor> getConstructor() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException;
        }

        public ExtensionLoader(ConstructorSupplier constructorSupplier) {
            this.constructorSupplier = constructorSupplier;
        }

        @Nullable
        private Constructor<? extends Extractor> maybeLoadExtractorConstructor() {
            synchronized (this.extensionLoaded) {
                if (this.extensionLoaded.get()) {
                    return this.extractorConstructor;
                }
                try {
                    return this.constructorSupplier.getConstructor();
                } catch (ClassNotFoundException unused) {
                    this.extensionLoaded.set(true);
                    return this.extractorConstructor;
                } catch (Exception e2) {
                    throw new RuntimeException("Error instantiating extension", e2);
                }
            }
        }

        @Nullable
        public Extractor getExtractor(Object... objArr) {
            Constructor<? extends Extractor> constructorMaybeLoadExtractorConstructor = maybeLoadExtractorConstructor();
            if (constructorMaybeLoadExtractorConstructor == null) {
                return null;
            }
            try {
                return constructorMaybeLoadExtractorConstructor.newInstance(objArr);
            } catch (Exception e2) {
                throw new IllegalStateException("Unexpected error creating extractor", e2);
            }
        }
    }

    private void addExtractorsForFileType(int i2, List<Extractor> list) {
        switch (i2) {
            case 0:
                list.add(new Ac3Extractor());
                break;
            case 1:
                list.add(new Ac4Extractor());
                break;
            case 2:
                list.add(new AdtsExtractor(this.adtsFlags | (this.constantBitrateSeekingEnabled ? 1 : 0) | (this.constantBitrateSeekingAlwaysEnabled ? 2 : 0)));
                break;
            case 3:
                list.add(new AmrExtractor(this.amrFlags | (this.constantBitrateSeekingEnabled ? 1 : 0) | (this.constantBitrateSeekingAlwaysEnabled ? 2 : 0)));
                break;
            case 4:
                Extractor extractor = FLAC_EXTENSION_LOADER.getExtractor(Integer.valueOf(this.flacFlags));
                if (extractor == null) {
                    list.add(new FlacExtractor(this.flacFlags));
                    break;
                } else {
                    list.add(extractor);
                    break;
                }
            case 5:
                list.add(new FlvExtractor());
                break;
            case 6:
                list.add(new MatroskaExtractor(this.subtitleParserFactory, (this.textTrackTranscodingEnabled ? 0 : 2) | this.matroskaFlags));
                break;
            case 7:
                list.add(new Mp3Extractor(this.mp3Flags | (this.constantBitrateSeekingEnabled ? 1 : 0) | (this.constantBitrateSeekingAlwaysEnabled ? 2 : 0)));
                break;
            case 8:
                list.add(new FragmentedMp4Extractor(this.subtitleParserFactory, this.fragmentedMp4Flags | (this.textTrackTranscodingEnabled ? 0 : 32)));
                list.add(new Mp4Extractor(this.subtitleParserFactory, this.mp4Flags | (this.textTrackTranscodingEnabled ? 0 : 16)));
                break;
            case 9:
                list.add(new OggExtractor());
                break;
            case 10:
                list.add(new PsExtractor());
                break;
            case 11:
                if (this.tsSubtitleFormats == null) {
                    this.tsSubtitleFormats = ImmutableList.of();
                }
                list.add(new TsExtractor(this.tsMode, !this.textTrackTranscodingEnabled ? 1 : 0, this.subtitleParserFactory, new TimestampAdjuster(0L), new DefaultTsPayloadReaderFactory(this.tsFlags, this.tsSubtitleFormats), this.tsTimestampSearchBytes));
                break;
            case 12:
                list.add(new WavExtractor());
                break;
            case 14:
                list.add(new JpegExtractor(this.jpegFlags));
                break;
            case 15:
                Extractor extractor2 = MIDI_EXTENSION_LOADER.getExtractor(new Object[0]);
                if (extractor2 != null) {
                    list.add(extractor2);
                    break;
                }
                break;
            case 16:
                list.add(new AviExtractor(1 ^ (this.textTrackTranscodingEnabled ? 1 : 0), this.subtitleParserFactory));
                break;
            case 17:
                list.add(new PngExtractor());
                break;
            case 18:
                list.add(new WebpExtractor());
                break;
            case 19:
                list.add(new BmpExtractor());
                break;
            case 20:
                int i3 = this.mp4Flags;
                if ((i3 & 2) == 0 && (i3 & 4) == 0) {
                    list.add(new HeifExtractor());
                    break;
                }
                break;
            case 21:
                list.add(new AvifExtractor());
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static Constructor<? extends Extractor> getFlacExtractorConstructor() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        if (Boolean.TRUE.equals(Class.forName("androidx.media3.decoder.flac.FlacLibrary").getMethod("isAvailable", null).invoke(null, null))) {
            return Class.forName("androidx.media3.decoder.flac.FlacExtractor").asSubclass(Extractor.class).getConstructor(Integer.TYPE);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Constructor<? extends Extractor> getMidiExtractorConstructor() throws NoSuchMethodException, ClassNotFoundException {
        return Class.forName("androidx.media3.decoder.midi.MidiExtractor").asSubclass(Extractor.class).getConstructor(null);
    }

    @Override // androidx.media3.extractor.ExtractorsFactory
    public synchronized Extractor[] createExtractors() {
        return createExtractors(Uri.EMPTY, new HashMap());
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setAdtsExtractorFlags(int i2) {
        this.adtsFlags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setAmrExtractorFlags(int i2) {
        this.amrFlags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setConstantBitrateSeekingAlwaysEnabled(boolean z2) {
        this.constantBitrateSeekingAlwaysEnabled = z2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setConstantBitrateSeekingEnabled(boolean z2) {
        this.constantBitrateSeekingEnabled = z2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setFlacExtractorFlags(int i2) {
        this.flacFlags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setFragmentedMp4ExtractorFlags(int i2) {
        this.fragmentedMp4Flags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setJpegExtractorFlags(int i2) {
        this.jpegFlags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setMatroskaExtractorFlags(int i2) {
        this.matroskaFlags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setMp3ExtractorFlags(int i2) {
        this.mp3Flags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setMp4ExtractorFlags(int i2) {
        this.mp4Flags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    @Deprecated
    public synchronized DefaultExtractorsFactory setTextTrackTranscodingEnabled(boolean z2) {
        return experimentalSetTextTrackTranscodingEnabled(z2);
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setTsExtractorFlags(int i2) {
        this.tsFlags = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setTsExtractorMode(int i2) {
        this.tsMode = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setTsExtractorTimestampSearchBytes(int i2) {
        this.tsTimestampSearchBytes = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setTsSubtitleFormats(List<Format> list) {
        this.tsSubtitleFormats = ImmutableList.copyOf((Collection) list);
        return this;
    }

    @Override // androidx.media3.extractor.ExtractorsFactory
    public synchronized Extractor[] createExtractors(Uri uri, Map<String, List<String>> map) {
        Extractor[] extractorArr;
        try {
            int[] iArr = DEFAULT_EXTRACTOR_ORDER;
            ArrayList arrayList = new ArrayList(iArr.length);
            int iInferFileTypeFromResponseHeaders = FileTypes.inferFileTypeFromResponseHeaders(map);
            if (iInferFileTypeFromResponseHeaders != -1) {
                addExtractorsForFileType(iInferFileTypeFromResponseHeaders, arrayList);
            }
            int iInferFileTypeFromUri = FileTypes.inferFileTypeFromUri(uri);
            if (iInferFileTypeFromUri != -1 && iInferFileTypeFromUri != iInferFileTypeFromResponseHeaders) {
                addExtractorsForFileType(iInferFileTypeFromUri, arrayList);
            }
            for (int i2 : iArr) {
                if (i2 != iInferFileTypeFromResponseHeaders && i2 != iInferFileTypeFromUri) {
                    addExtractorsForFileType(i2, arrayList);
                }
            }
            extractorArr = new Extractor[arrayList.size()];
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                Extractor subtitleTranscodingExtractor = arrayList.get(i3);
                if (this.textTrackTranscodingEnabled && !(subtitleTranscodingExtractor.getUnderlyingImplementation() instanceof FragmentedMp4Extractor) && !(subtitleTranscodingExtractor.getUnderlyingImplementation() instanceof Mp4Extractor) && !(subtitleTranscodingExtractor.getUnderlyingImplementation() instanceof TsExtractor) && !(subtitleTranscodingExtractor.getUnderlyingImplementation() instanceof AviExtractor) && !(subtitleTranscodingExtractor.getUnderlyingImplementation() instanceof MatroskaExtractor)) {
                    subtitleTranscodingExtractor = new SubtitleTranscodingExtractor(subtitleTranscodingExtractor, this.subtitleParserFactory);
                }
                extractorArr[i3] = subtitleTranscodingExtractor;
            }
        } catch (Throwable th) {
            throw th;
        }
        return extractorArr;
    }

    @Override // androidx.media3.extractor.ExtractorsFactory
    @Deprecated
    public synchronized DefaultExtractorsFactory experimentalSetTextTrackTranscodingEnabled(boolean z2) {
        this.textTrackTranscodingEnabled = z2;
        return this;
    }

    @Override // androidx.media3.extractor.ExtractorsFactory
    @CanIgnoreReturnValue
    public synchronized DefaultExtractorsFactory setSubtitleParserFactory(SubtitleParser.Factory factory) {
        this.subtitleParserFactory = factory;
        return this;
    }
}

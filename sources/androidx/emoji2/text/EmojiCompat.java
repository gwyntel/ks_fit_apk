package androidx.emoji2.text;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.annotation.AnyThread;
import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.GuardedBy;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.DefaultEmojiCompatConfig;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@AnyThread
/* loaded from: classes.dex */
public class EmojiCompat {
    public static final String EDITOR_INFO_METAVERSION_KEY = "android.support.text.emoji.emojiCompat_metadataVersion";
    public static final String EDITOR_INFO_REPLACE_ALL_KEY = "android.support.text.emoji.emojiCompat_replaceAll";
    public static final int EMOJI_FALLBACK = 2;
    public static final int EMOJI_SUPPORTED = 1;
    public static final int EMOJI_UNSUPPORTED = 0;
    public static final int LOAD_STATE_DEFAULT = 3;
    public static final int LOAD_STATE_FAILED = 2;
    public static final int LOAD_STATE_LOADING = 0;
    public static final int LOAD_STATE_SUCCEEDED = 1;
    public static final int LOAD_STRATEGY_DEFAULT = 0;
    public static final int LOAD_STRATEGY_MANUAL = 1;
    private static final String NOT_INITIALIZED_ERROR_TEXT = "EmojiCompat is not initialized.\n\nYou must initialize EmojiCompat prior to referencing the EmojiCompat instance.\n\nThe most likely cause of this error is disabling the EmojiCompatInitializer\neither explicitly in AndroidManifest.xml, or by including\nandroidx.emoji2:emoji2-bundled.\n\nAutomatic initialization is typically performed by EmojiCompatInitializer. If\nyou are not expecting to initialize EmojiCompat manually in your application,\nplease check to ensure it has not been removed from your APK's manifest. You can\ndo this in Android Studio using Build > Analyze APK.\n\nIn the APK Analyzer, ensure that the startup entry for\nEmojiCompatInitializer and InitializationProvider is present in\n AndroidManifest.xml. If it is missing or contains tools:node=\"remove\", and you\nintend to use automatic configuration, verify:\n\n  1. Your application does not include emoji2-bundled\n  2. All modules do not contain an exclusion manifest rule for\n     EmojiCompatInitializer or InitializationProvider. For more information\n     about manifest exclusions see the documentation for the androidx startup\n     library.\n\nIf you intend to use emoji2-bundled, please call EmojiCompat.init. You can\nlearn more in the documentation for BundledEmojiCompatConfig.\n\nIf you intended to perform manual configuration, it is recommended that you call\nEmojiCompat.init immediately on application startup.\n\nIf you still cannot resolve this issue, please open a bug with your specific\nconfiguration to help improve error message.";
    public static final int REPLACE_STRATEGY_ALL = 1;
    public static final int REPLACE_STRATEGY_DEFAULT = 0;
    public static final int REPLACE_STRATEGY_NON_EXISTENT = 2;

    @GuardedBy("CONFIG_LOCK")
    private static volatile boolean sHasDoneDefaultConfigLookup;

    @Nullable
    @GuardedBy("INSTANCE_LOCK")
    private static volatile EmojiCompat sInstance;

    /* renamed from: a, reason: collision with root package name */
    final MetadataRepoLoader f4010a;

    /* renamed from: b, reason: collision with root package name */
    final boolean f4011b;

    /* renamed from: c, reason: collision with root package name */
    final boolean f4012c;

    /* renamed from: d, reason: collision with root package name */
    final int[] f4013d;
    private final int mEmojiSpanIndicatorColor;
    private final boolean mEmojiSpanIndicatorEnabled;
    private final GlyphChecker mGlyphChecker;

    @NonNull
    private final CompatInternal mHelper;

    @NonNull
    @GuardedBy("mInitLock")
    private final Set<InitCallback> mInitCallbacks;

    @NonNull
    private final ReadWriteLock mInitLock = new ReentrantReadWriteLock();

    @GuardedBy("mInitLock")
    private volatile int mLoadState = 3;

    @NonNull
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private final int mMetadataLoadStrategy;
    private static final Object INSTANCE_LOCK = new Object();
    private static final Object CONFIG_LOCK = new Object();

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface CodepointSequenceMatchResult {
    }

    private static class CompatInternal {

        /* renamed from: a, reason: collision with root package name */
        final EmojiCompat f4014a;

        CompatInternal(EmojiCompat emojiCompat) {
            this.f4014a = emojiCompat;
        }

        String a() {
            return "";
        }

        boolean b(CharSequence charSequence) {
            return false;
        }

        boolean c(CharSequence charSequence, int i2) {
            return false;
        }

        void d() {
            this.f4014a.c();
        }

        CharSequence e(CharSequence charSequence, int i2, int i3, int i4, boolean z2) {
            return charSequence;
        }

        void f(EditorInfo editorInfo) {
        }

        public int getEmojiMatch(CharSequence charSequence, int i2) {
            return 0;
        }
    }

    @RequiresApi(19)
    private static final class CompatInternal19 extends CompatInternal {
        private volatile MetadataRepo mMetadataRepo;
        private volatile EmojiProcessor mProcessor;

        CompatInternal19(EmojiCompat emojiCompat) {
            super(emojiCompat);
        }

        @Override // androidx.emoji2.text.EmojiCompat.CompatInternal
        String a() {
            String strSourceSha = this.mMetadataRepo.getMetadataList().sourceSha();
            return strSourceSha == null ? "" : strSourceSha;
        }

        @Override // androidx.emoji2.text.EmojiCompat.CompatInternal
        boolean b(CharSequence charSequence) {
            return this.mProcessor.a(charSequence) == 1;
        }

        @Override // androidx.emoji2.text.EmojiCompat.CompatInternal
        boolean c(CharSequence charSequence, int i2) {
            return this.mProcessor.b(charSequence, i2) == 1;
        }

        @Override // androidx.emoji2.text.EmojiCompat.CompatInternal
        void d() {
            try {
                this.f4014a.f4010a.load(new MetadataRepoLoaderCallback() { // from class: androidx.emoji2.text.EmojiCompat.CompatInternal19.1
                    @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
                    public void onFailed(@Nullable Throwable th) {
                        CompatInternal19.this.f4014a.b(th);
                    }

                    @Override // androidx.emoji2.text.EmojiCompat.MetadataRepoLoaderCallback
                    public void onLoaded(@NonNull MetadataRepo metadataRepo) {
                        CompatInternal19.this.g(metadataRepo);
                    }
                });
            } catch (Throwable th) {
                this.f4014a.b(th);
            }
        }

        @Override // androidx.emoji2.text.EmojiCompat.CompatInternal
        CharSequence e(CharSequence charSequence, int i2, int i3, int i4, boolean z2) {
            return this.mProcessor.e(charSequence, i2, i3, i4, z2);
        }

        @Override // androidx.emoji2.text.EmojiCompat.CompatInternal
        void f(EditorInfo editorInfo) {
            editorInfo.extras.putInt(EmojiCompat.EDITOR_INFO_METAVERSION_KEY, this.mMetadataRepo.a());
            editorInfo.extras.putBoolean(EmojiCompat.EDITOR_INFO_REPLACE_ALL_KEY, this.f4014a.f4011b);
        }

        void g(MetadataRepo metadataRepo) {
            if (metadataRepo == null) {
                this.f4014a.b(new IllegalArgumentException("metadataRepo cannot be null"));
                return;
            }
            this.mMetadataRepo = metadataRepo;
            MetadataRepo metadataRepo2 = this.mMetadataRepo;
            SpanFactory spanFactory = new SpanFactory();
            GlyphChecker glyphChecker = this.f4014a.mGlyphChecker;
            EmojiCompat emojiCompat = this.f4014a;
            this.mProcessor = new EmojiProcessor(metadataRepo2, spanFactory, glyphChecker, emojiCompat.f4012c, emojiCompat.f4013d);
            this.f4014a.c();
        }

        @Override // androidx.emoji2.text.EmojiCompat.CompatInternal
        public int getEmojiMatch(CharSequence charSequence, int i2) {
            return this.mProcessor.b(charSequence, i2);
        }
    }

    public static abstract class Config {

        /* renamed from: a, reason: collision with root package name */
        final MetadataRepoLoader f4016a;

        /* renamed from: b, reason: collision with root package name */
        boolean f4017b;

        /* renamed from: c, reason: collision with root package name */
        boolean f4018c;

        /* renamed from: d, reason: collision with root package name */
        int[] f4019d;

        /* renamed from: e, reason: collision with root package name */
        Set f4020e;

        /* renamed from: f, reason: collision with root package name */
        boolean f4021f;

        /* renamed from: g, reason: collision with root package name */
        int f4022g = -16711936;

        /* renamed from: h, reason: collision with root package name */
        int f4023h = 0;

        /* renamed from: i, reason: collision with root package name */
        GlyphChecker f4024i = new DefaultGlyphChecker();

        protected Config(MetadataRepoLoader metadataRepoLoader) {
            Preconditions.checkNotNull(metadataRepoLoader, "metadataLoader cannot be null.");
            this.f4016a = metadataRepoLoader;
        }

        protected final MetadataRepoLoader a() {
            return this.f4016a;
        }

        @NonNull
        public Config registerInitCallback(@NonNull InitCallback initCallback) {
            Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
            if (this.f4020e == null) {
                this.f4020e = new ArraySet();
            }
            this.f4020e.add(initCallback);
            return this;
        }

        @NonNull
        public Config setEmojiSpanIndicatorColor(@ColorInt int i2) {
            this.f4022g = i2;
            return this;
        }

        @NonNull
        public Config setEmojiSpanIndicatorEnabled(boolean z2) {
            this.f4021f = z2;
            return this;
        }

        @NonNull
        public Config setGlyphChecker(@NonNull GlyphChecker glyphChecker) {
            Preconditions.checkNotNull(glyphChecker, "GlyphChecker cannot be null");
            this.f4024i = glyphChecker;
            return this;
        }

        @NonNull
        public Config setMetadataLoadStrategy(int i2) {
            this.f4023h = i2;
            return this;
        }

        @NonNull
        public Config setReplaceAll(boolean z2) {
            this.f4017b = z2;
            return this;
        }

        @NonNull
        public Config setUseEmojiAsDefaultStyle(boolean z2) {
            return setUseEmojiAsDefaultStyle(z2, null);
        }

        @NonNull
        public Config unregisterInitCallback(@NonNull InitCallback initCallback) {
            Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
            Set set = this.f4020e;
            if (set != null) {
                set.remove(initCallback);
            }
            return this;
        }

        @NonNull
        public Config setUseEmojiAsDefaultStyle(boolean z2, @Nullable List<Integer> list) {
            this.f4018c = z2;
            if (!z2 || list == null) {
                this.f4019d = null;
            } else {
                this.f4019d = new int[list.size()];
                Iterator<Integer> it = list.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    this.f4019d[i2] = it.next().intValue();
                    i2++;
                }
                Arrays.sort(this.f4019d);
            }
            return this;
        }
    }

    public interface GlyphChecker {
        boolean hasGlyph(@NonNull CharSequence charSequence, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4);
    }

    public static abstract class InitCallback {
        public void onFailed(@Nullable Throwable th) {
        }

        public void onInitialized() {
        }
    }

    private static class ListenerDispatcher implements Runnable {
        private final List<InitCallback> mInitCallbacks;
        private final int mLoadState;
        private final Throwable mThrowable;

        ListenerDispatcher(InitCallback initCallback, int i2) {
            this(Arrays.asList((InitCallback) Preconditions.checkNotNull(initCallback, "initCallback cannot be null")), i2, null);
        }

        @Override // java.lang.Runnable
        public void run() {
            int size = this.mInitCallbacks.size();
            int i2 = 0;
            if (this.mLoadState != 1) {
                while (i2 < size) {
                    this.mInitCallbacks.get(i2).onFailed(this.mThrowable);
                    i2++;
                }
            } else {
                while (i2 < size) {
                    this.mInitCallbacks.get(i2).onInitialized();
                    i2++;
                }
            }
        }

        ListenerDispatcher(Collection collection, int i2) {
            this(collection, i2, null);
        }

        ListenerDispatcher(Collection collection, int i2, Throwable th) {
            Preconditions.checkNotNull(collection, "initCallbacks cannot be null");
            this.mInitCallbacks = new ArrayList(collection);
            this.mLoadState = i2;
            this.mThrowable = th;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface LoadStrategy {
    }

    public interface MetadataRepoLoader {
        void load(@NonNull MetadataRepoLoaderCallback metadataRepoLoaderCallback);
    }

    public static abstract class MetadataRepoLoaderCallback {
        public abstract void onFailed(@Nullable Throwable th);

        public abstract void onLoaded(@NonNull MetadataRepo metadataRepo);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ReplaceStrategy {
    }

    @RequiresApi(19)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    static class SpanFactory {
        SpanFactory() {
        }

        EmojiSpan a(EmojiMetadata emojiMetadata) {
            return new TypefaceEmojiSpan(emojiMetadata);
        }
    }

    private EmojiCompat(@NonNull Config config) {
        this.f4011b = config.f4017b;
        this.f4012c = config.f4018c;
        this.f4013d = config.f4019d;
        this.mEmojiSpanIndicatorEnabled = config.f4021f;
        this.mEmojiSpanIndicatorColor = config.f4022g;
        this.f4010a = config.f4016a;
        this.mMetadataLoadStrategy = config.f4023h;
        this.mGlyphChecker = config.f4024i;
        ArraySet arraySet = new ArraySet();
        this.mInitCallbacks = arraySet;
        Set set = config.f4020e;
        if (set != null && !set.isEmpty()) {
            arraySet.addAll(config.f4020e);
        }
        this.mHelper = new CompatInternal19(this);
        loadMetadata();
    }

    @NonNull
    public static EmojiCompat get() {
        EmojiCompat emojiCompat;
        synchronized (INSTANCE_LOCK) {
            emojiCompat = sInstance;
            Preconditions.checkState(emojiCompat != null, NOT_INITIALIZED_ERROR_TEXT);
        }
        return emojiCompat;
    }

    public static boolean handleDeleteSurroundingText(@NonNull InputConnection inputConnection, @NonNull Editable editable, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, boolean z2) {
        return EmojiProcessor.c(inputConnection, editable, i2, i3, z2);
    }

    public static boolean handleOnKeyDown(@NonNull Editable editable, int i2, @NonNull KeyEvent keyEvent) {
        return EmojiProcessor.d(editable, i2, keyEvent);
    }

    @Nullable
    public static EmojiCompat init(@NonNull Context context) {
        return init(context, null);
    }

    public static boolean isConfigured() {
        return sInstance != null;
    }

    private boolean isInitialized() {
        return getLoadState() == 1;
    }

    private void loadMetadata() {
        this.mInitLock.writeLock().lock();
        try {
            if (this.mMetadataLoadStrategy == 0) {
                this.mLoadState = 0;
            }
            this.mInitLock.writeLock().unlock();
            if (getLoadState() == 0) {
                this.mHelper.d();
            }
        } catch (Throwable th) {
            this.mInitLock.writeLock().unlock();
            throw th;
        }
    }

    @NonNull
    public static EmojiCompat reset(@NonNull Config config) {
        EmojiCompat emojiCompat;
        synchronized (INSTANCE_LOCK) {
            emojiCompat = new EmojiCompat(config);
            sInstance = emojiCompat;
        }
        return emojiCompat;
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    public static void skipDefaultConfigurationLookup(boolean z2) {
        synchronized (CONFIG_LOCK) {
            sHasDoneDefaultConfigLookup = z2;
        }
    }

    void b(Throwable th) {
        ArrayList arrayList = new ArrayList();
        this.mInitLock.writeLock().lock();
        try {
            this.mLoadState = 2;
            arrayList.addAll(this.mInitCallbacks);
            this.mInitCallbacks.clear();
            this.mInitLock.writeLock().unlock();
            this.mMainHandler.post(new ListenerDispatcher(arrayList, this.mLoadState, th));
        } catch (Throwable th2) {
            this.mInitLock.writeLock().unlock();
            throw th2;
        }
    }

    void c() {
        ArrayList arrayList = new ArrayList();
        this.mInitLock.writeLock().lock();
        try {
            this.mLoadState = 1;
            arrayList.addAll(this.mInitCallbacks);
            this.mInitCallbacks.clear();
            this.mInitLock.writeLock().unlock();
            this.mMainHandler.post(new ListenerDispatcher(arrayList, this.mLoadState));
        } catch (Throwable th) {
            this.mInitLock.writeLock().unlock();
            throw th;
        }
    }

    @NonNull
    public String getAssetSignature() {
        Preconditions.checkState(isInitialized(), "Not initialized yet");
        return this.mHelper.a();
    }

    public int getEmojiMatch(@NonNull CharSequence charSequence, @IntRange(from = 0) int i2) {
        Preconditions.checkState(isInitialized(), "Not initialized yet");
        Preconditions.checkNotNull(charSequence, "sequence cannot be null");
        return this.mHelper.getEmojiMatch(charSequence, i2);
    }

    @ColorInt
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getEmojiSpanIndicatorColor() {
        return this.mEmojiSpanIndicatorColor;
    }

    public int getLoadState() {
        this.mInitLock.readLock().lock();
        try {
            return this.mLoadState;
        } finally {
            this.mInitLock.readLock().unlock();
        }
    }

    @Deprecated
    public boolean hasEmojiGlyph(@NonNull CharSequence charSequence) {
        Preconditions.checkState(isInitialized(), "Not initialized yet");
        Preconditions.checkNotNull(charSequence, "sequence cannot be null");
        return this.mHelper.b(charSequence);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isEmojiSpanIndicatorEnabled() {
        return this.mEmojiSpanIndicatorEnabled;
    }

    public void load() {
        Preconditions.checkState(this.mMetadataLoadStrategy == 1, "Set metadataLoadStrategy to LOAD_STRATEGY_MANUAL to execute manual loading");
        if (isInitialized()) {
            return;
        }
        this.mInitLock.writeLock().lock();
        try {
            if (this.mLoadState == 0) {
                return;
            }
            this.mLoadState = 0;
            this.mInitLock.writeLock().unlock();
            this.mHelper.d();
        } finally {
            this.mInitLock.writeLock().unlock();
        }
    }

    @Nullable
    @CheckResult
    public CharSequence process(@Nullable CharSequence charSequence) {
        return process(charSequence, 0, charSequence == null ? 0 : charSequence.length());
    }

    public void registerInitCallback(@NonNull InitCallback initCallback) {
        Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
        this.mInitLock.writeLock().lock();
        try {
            if (this.mLoadState == 1 || this.mLoadState == 2) {
                this.mMainHandler.post(new ListenerDispatcher(initCallback, this.mLoadState));
            } else {
                this.mInitCallbacks.add(initCallback);
            }
            this.mInitLock.writeLock().unlock();
        } catch (Throwable th) {
            this.mInitLock.writeLock().unlock();
            throw th;
        }
    }

    public void unregisterInitCallback(@NonNull InitCallback initCallback) {
        Preconditions.checkNotNull(initCallback, "initCallback cannot be null");
        this.mInitLock.writeLock().lock();
        try {
            this.mInitCallbacks.remove(initCallback);
        } finally {
            this.mInitLock.writeLock().unlock();
        }
    }

    public void updateEditorInfo(@NonNull EditorInfo editorInfo) {
        if (!isInitialized() || editorInfo == null) {
            return;
        }
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        this.mHelper.f(editorInfo);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static EmojiCompat init(@NonNull Context context, @Nullable DefaultEmojiCompatConfig.DefaultEmojiCompatConfigFactory defaultEmojiCompatConfigFactory) {
        EmojiCompat emojiCompat;
        if (sHasDoneDefaultConfigLookup) {
            return sInstance;
        }
        if (defaultEmojiCompatConfigFactory == null) {
            defaultEmojiCompatConfigFactory = new DefaultEmojiCompatConfig.DefaultEmojiCompatConfigFactory(null);
        }
        Config configCreate = defaultEmojiCompatConfigFactory.create(context);
        synchronized (CONFIG_LOCK) {
            try {
                if (!sHasDoneDefaultConfigLookup) {
                    if (configCreate != null) {
                        init(configCreate);
                    }
                    sHasDoneDefaultConfigLookup = true;
                }
                emojiCompat = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return emojiCompat;
    }

    @Nullable
    @CheckResult
    public CharSequence process(@Nullable CharSequence charSequence, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        return process(charSequence, i2, i3, Integer.MAX_VALUE);
    }

    @Deprecated
    public boolean hasEmojiGlyph(@NonNull CharSequence charSequence, @IntRange(from = 0) int i2) {
        Preconditions.checkState(isInitialized(), "Not initialized yet");
        Preconditions.checkNotNull(charSequence, "sequence cannot be null");
        return this.mHelper.c(charSequence, i2);
    }

    @Nullable
    @CheckResult
    public CharSequence process(@Nullable CharSequence charSequence, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4) {
        return process(charSequence, i2, i3, i4, 0);
    }

    @Nullable
    @CheckResult
    public CharSequence process(@Nullable CharSequence charSequence, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4, int i5) {
        boolean z2;
        Preconditions.checkState(isInitialized(), "Not initialized yet");
        Preconditions.checkArgumentNonnegative(i2, "start cannot be negative");
        Preconditions.checkArgumentNonnegative(i3, "end cannot be negative");
        Preconditions.checkArgumentNonnegative(i4, "maxEmojiCount cannot be negative");
        Preconditions.checkArgument(i2 <= i3, "start should be <= than end");
        if (charSequence == null) {
            return null;
        }
        Preconditions.checkArgument(i2 <= charSequence.length(), "start should be < than charSequence length");
        Preconditions.checkArgument(i3 <= charSequence.length(), "end should be < than charSequence length");
        if (charSequence.length() == 0 || i2 == i3) {
            return charSequence;
        }
        if (i5 != 1) {
            z2 = i5 != 2 ? this.f4011b : false;
        } else {
            z2 = true;
        }
        return this.mHelper.e(charSequence, i2, i3, i4, z2);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.TESTS})
    public static EmojiCompat reset(@Nullable EmojiCompat emojiCompat) {
        EmojiCompat emojiCompat2;
        synchronized (INSTANCE_LOCK) {
            sInstance = emojiCompat;
            emojiCompat2 = sInstance;
        }
        return emojiCompat2;
    }

    @NonNull
    public static EmojiCompat init(@NonNull Config config) {
        EmojiCompat emojiCompat = sInstance;
        if (emojiCompat == null) {
            synchronized (INSTANCE_LOCK) {
                try {
                    emojiCompat = sInstance;
                    if (emojiCompat == null) {
                        emojiCompat = new EmojiCompat(config);
                        sInstance = emojiCompat;
                    }
                } finally {
                }
            }
        }
        return emojiCompat;
    }
}

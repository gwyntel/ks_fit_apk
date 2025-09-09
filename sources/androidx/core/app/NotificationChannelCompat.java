package androidx.core.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

/* loaded from: classes.dex */
public class NotificationChannelCompat {
    public static final String DEFAULT_CHANNEL_ID = "miscellaneous";
    private static final int DEFAULT_LIGHT_COLOR = 0;
    private static final boolean DEFAULT_SHOW_BADGE = true;

    /* renamed from: a, reason: collision with root package name */
    final String f3414a;

    /* renamed from: b, reason: collision with root package name */
    CharSequence f3415b;

    /* renamed from: c, reason: collision with root package name */
    int f3416c;

    /* renamed from: d, reason: collision with root package name */
    String f3417d;

    /* renamed from: e, reason: collision with root package name */
    String f3418e;

    /* renamed from: f, reason: collision with root package name */
    boolean f3419f;

    /* renamed from: g, reason: collision with root package name */
    Uri f3420g;

    /* renamed from: h, reason: collision with root package name */
    AudioAttributes f3421h;

    /* renamed from: i, reason: collision with root package name */
    boolean f3422i;

    /* renamed from: j, reason: collision with root package name */
    int f3423j;

    /* renamed from: k, reason: collision with root package name */
    boolean f3424k;

    /* renamed from: l, reason: collision with root package name */
    long[] f3425l;

    /* renamed from: m, reason: collision with root package name */
    String f3426m;
    private boolean mBypassDnd;
    private boolean mCanBubble;
    private boolean mImportantConversation;
    private int mLockscreenVisibility;

    /* renamed from: n, reason: collision with root package name */
    String f3427n;

    @RequiresApi(26)
    static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        static boolean a(NotificationChannel notificationChannel) {
            return notificationChannel.canBypassDnd();
        }

        @DoNotInline
        static boolean b(NotificationChannel notificationChannel) {
            return notificationChannel.canShowBadge();
        }

        @DoNotInline
        static NotificationChannel c(String str, CharSequence charSequence, int i2) {
            return new NotificationChannel(str, charSequence, i2);
        }

        @DoNotInline
        static void d(NotificationChannel notificationChannel, boolean z2) {
            notificationChannel.enableLights(z2);
        }

        @DoNotInline
        static void e(NotificationChannel notificationChannel, boolean z2) {
            notificationChannel.enableVibration(z2);
        }

        @DoNotInline
        static AudioAttributes f(NotificationChannel notificationChannel) {
            return notificationChannel.getAudioAttributes();
        }

        @DoNotInline
        static String g(NotificationChannel notificationChannel) {
            return notificationChannel.getDescription();
        }

        @DoNotInline
        static String h(NotificationChannel notificationChannel) {
            return notificationChannel.getGroup();
        }

        @DoNotInline
        static String i(NotificationChannel notificationChannel) {
            return notificationChannel.getId();
        }

        @DoNotInline
        static int j(NotificationChannel notificationChannel) {
            return notificationChannel.getImportance();
        }

        @DoNotInline
        static int k(NotificationChannel notificationChannel) {
            return notificationChannel.getLightColor();
        }

        @DoNotInline
        static int l(NotificationChannel notificationChannel) {
            return notificationChannel.getLockscreenVisibility();
        }

        @DoNotInline
        static CharSequence m(NotificationChannel notificationChannel) {
            return notificationChannel.getName();
        }

        @DoNotInline
        static Uri n(NotificationChannel notificationChannel) {
            return notificationChannel.getSound();
        }

        @DoNotInline
        static long[] o(NotificationChannel notificationChannel) {
            return notificationChannel.getVibrationPattern();
        }

        @DoNotInline
        static void p(NotificationChannel notificationChannel, String str) {
            notificationChannel.setDescription(str);
        }

        @DoNotInline
        static void q(NotificationChannel notificationChannel, String str) {
            notificationChannel.setGroup(str);
        }

        @DoNotInline
        static void r(NotificationChannel notificationChannel, int i2) {
            notificationChannel.setLightColor(i2);
        }

        @DoNotInline
        static void s(NotificationChannel notificationChannel, boolean z2) {
            notificationChannel.setShowBadge(z2);
        }

        @DoNotInline
        static void t(NotificationChannel notificationChannel, Uri uri, AudioAttributes audioAttributes) {
            notificationChannel.setSound(uri, audioAttributes);
        }

        @DoNotInline
        static void u(NotificationChannel notificationChannel, long[] jArr) {
            notificationChannel.setVibrationPattern(jArr);
        }

        @DoNotInline
        static boolean v(NotificationChannel notificationChannel) {
            return notificationChannel.shouldShowLights();
        }

        @DoNotInline
        static boolean w(NotificationChannel notificationChannel) {
            return notificationChannel.shouldVibrate();
        }
    }

    @RequiresApi(29)
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static boolean a(NotificationChannel notificationChannel) {
            return notificationChannel.canBubble();
        }
    }

    @RequiresApi(30)
    static class Api30Impl {
        private Api30Impl() {
        }

        @DoNotInline
        static String a(NotificationChannel notificationChannel) {
            return notificationChannel.getConversationId();
        }

        @DoNotInline
        static String b(NotificationChannel notificationChannel) {
            return notificationChannel.getParentChannelId();
        }

        @DoNotInline
        static boolean c(NotificationChannel notificationChannel) {
            return notificationChannel.isImportantConversation();
        }

        @DoNotInline
        static void d(NotificationChannel notificationChannel, String str, String str2) {
            notificationChannel.setConversationId(str, str2);
        }
    }

    public static class Builder {
        private final NotificationChannelCompat mChannel;

        public Builder(@NonNull String str, int i2) {
            this.mChannel = new NotificationChannelCompat(str, i2);
        }

        @NonNull
        public NotificationChannelCompat build() {
            return this.mChannel;
        }

        @NonNull
        public Builder setConversationId(@NonNull String str, @NonNull String str2) {
            if (Build.VERSION.SDK_INT >= 30) {
                NotificationChannelCompat notificationChannelCompat = this.mChannel;
                notificationChannelCompat.f3426m = str;
                notificationChannelCompat.f3427n = str2;
            }
            return this;
        }

        @NonNull
        public Builder setDescription(@Nullable String str) {
            this.mChannel.f3417d = str;
            return this;
        }

        @NonNull
        public Builder setGroup(@Nullable String str) {
            this.mChannel.f3418e = str;
            return this;
        }

        @NonNull
        public Builder setImportance(int i2) {
            this.mChannel.f3416c = i2;
            return this;
        }

        @NonNull
        public Builder setLightColor(int i2) {
            this.mChannel.f3423j = i2;
            return this;
        }

        @NonNull
        public Builder setLightsEnabled(boolean z2) {
            this.mChannel.f3422i = z2;
            return this;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.mChannel.f3415b = charSequence;
            return this;
        }

        @NonNull
        public Builder setShowBadge(boolean z2) {
            this.mChannel.f3419f = z2;
            return this;
        }

        @NonNull
        public Builder setSound(@Nullable Uri uri, @Nullable AudioAttributes audioAttributes) {
            NotificationChannelCompat notificationChannelCompat = this.mChannel;
            notificationChannelCompat.f3420g = uri;
            notificationChannelCompat.f3421h = audioAttributes;
            return this;
        }

        @NonNull
        public Builder setVibrationEnabled(boolean z2) {
            this.mChannel.f3424k = z2;
            return this;
        }

        @NonNull
        public Builder setVibrationPattern(@Nullable long[] jArr) {
            NotificationChannelCompat notificationChannelCompat = this.mChannel;
            notificationChannelCompat.f3424k = jArr != null && jArr.length > 0;
            notificationChannelCompat.f3425l = jArr;
            return this;
        }
    }

    NotificationChannelCompat(String str, int i2) {
        this.f3419f = true;
        this.f3420g = Settings.System.DEFAULT_NOTIFICATION_URI;
        this.f3423j = 0;
        this.f3414a = (String) Preconditions.checkNotNull(str);
        this.f3416c = i2;
        this.f3421h = Notification.AUDIO_ATTRIBUTES_DEFAULT;
    }

    NotificationChannel a() {
        String str;
        String str2;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 26) {
            return null;
        }
        NotificationChannel notificationChannelC = Api26Impl.c(this.f3414a, this.f3415b, this.f3416c);
        Api26Impl.p(notificationChannelC, this.f3417d);
        Api26Impl.q(notificationChannelC, this.f3418e);
        Api26Impl.s(notificationChannelC, this.f3419f);
        Api26Impl.t(notificationChannelC, this.f3420g, this.f3421h);
        Api26Impl.d(notificationChannelC, this.f3422i);
        Api26Impl.r(notificationChannelC, this.f3423j);
        Api26Impl.u(notificationChannelC, this.f3425l);
        Api26Impl.e(notificationChannelC, this.f3424k);
        if (i2 >= 30 && (str = this.f3426m) != null && (str2 = this.f3427n) != null) {
            Api30Impl.d(notificationChannelC, str, str2);
        }
        return notificationChannelC;
    }

    public boolean canBubble() {
        return this.mCanBubble;
    }

    public boolean canBypassDnd() {
        return this.mBypassDnd;
    }

    public boolean canShowBadge() {
        return this.f3419f;
    }

    @Nullable
    public AudioAttributes getAudioAttributes() {
        return this.f3421h;
    }

    @Nullable
    public String getConversationId() {
        return this.f3427n;
    }

    @Nullable
    public String getDescription() {
        return this.f3417d;
    }

    @Nullable
    public String getGroup() {
        return this.f3418e;
    }

    @NonNull
    public String getId() {
        return this.f3414a;
    }

    public int getImportance() {
        return this.f3416c;
    }

    public int getLightColor() {
        return this.f3423j;
    }

    public int getLockscreenVisibility() {
        return this.mLockscreenVisibility;
    }

    @Nullable
    public CharSequence getName() {
        return this.f3415b;
    }

    @Nullable
    public String getParentChannelId() {
        return this.f3426m;
    }

    @Nullable
    public Uri getSound() {
        return this.f3420g;
    }

    @Nullable
    public long[] getVibrationPattern() {
        return this.f3425l;
    }

    public boolean isImportantConversation() {
        return this.mImportantConversation;
    }

    public boolean shouldShowLights() {
        return this.f3422i;
    }

    public boolean shouldVibrate() {
        return this.f3424k;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this.f3414a, this.f3416c).setName(this.f3415b).setDescription(this.f3417d).setGroup(this.f3418e).setShowBadge(this.f3419f).setSound(this.f3420g, this.f3421h).setLightsEnabled(this.f3422i).setLightColor(this.f3423j).setVibrationEnabled(this.f3424k).setVibrationPattern(this.f3425l).setConversationId(this.f3426m, this.f3427n);
    }

    NotificationChannelCompat(NotificationChannel notificationChannel) {
        this(Api26Impl.i(notificationChannel), Api26Impl.j(notificationChannel));
        this.f3415b = Api26Impl.m(notificationChannel);
        this.f3417d = Api26Impl.g(notificationChannel);
        this.f3418e = Api26Impl.h(notificationChannel);
        this.f3419f = Api26Impl.b(notificationChannel);
        this.f3420g = Api26Impl.n(notificationChannel);
        this.f3421h = Api26Impl.f(notificationChannel);
        this.f3422i = Api26Impl.v(notificationChannel);
        this.f3423j = Api26Impl.k(notificationChannel);
        this.f3424k = Api26Impl.w(notificationChannel);
        this.f3425l = Api26Impl.o(notificationChannel);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 30) {
            this.f3426m = Api30Impl.b(notificationChannel);
            this.f3427n = Api30Impl.a(notificationChannel);
        }
        this.mBypassDnd = Api26Impl.a(notificationChannel);
        this.mLockscreenVisibility = Api26Impl.l(notificationChannel);
        if (i2 >= 29) {
            this.mCanBubble = Api29Impl.a(notificationChannel);
        }
        if (i2 >= 30) {
            this.mImportantConversation = Api30Impl.c(notificationChannel);
        }
    }
}

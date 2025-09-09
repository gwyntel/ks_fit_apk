package androidx.core.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class NotificationChannelGroupCompat {

    /* renamed from: a, reason: collision with root package name */
    final String f3428a;

    /* renamed from: b, reason: collision with root package name */
    CharSequence f3429b;

    /* renamed from: c, reason: collision with root package name */
    String f3430c;
    private boolean mBlocked;
    private List<NotificationChannelCompat> mChannels;

    @RequiresApi(26)
    static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        static NotificationChannelGroup a(String str, CharSequence charSequence) {
            return new NotificationChannelGroup(str, charSequence);
        }

        @DoNotInline
        static List<NotificationChannel> b(NotificationChannelGroup notificationChannelGroup) {
            return notificationChannelGroup.getChannels();
        }

        @DoNotInline
        static String c(NotificationChannel notificationChannel) {
            return notificationChannel.getGroup();
        }

        @DoNotInline
        static String d(NotificationChannelGroup notificationChannelGroup) {
            return notificationChannelGroup.getId();
        }

        @DoNotInline
        static CharSequence e(NotificationChannelGroup notificationChannelGroup) {
            return notificationChannelGroup.getName();
        }
    }

    @RequiresApi(28)
    static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static String a(NotificationChannelGroup notificationChannelGroup) {
            return notificationChannelGroup.getDescription();
        }

        @DoNotInline
        static boolean b(NotificationChannelGroup notificationChannelGroup) {
            return notificationChannelGroup.isBlocked();
        }

        @DoNotInline
        static void c(NotificationChannelGroup notificationChannelGroup, String str) {
            notificationChannelGroup.setDescription(str);
        }
    }

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        final NotificationChannelGroupCompat f3431a;

        public Builder(@NonNull String str) {
            this.f3431a = new NotificationChannelGroupCompat(str);
        }

        @NonNull
        public NotificationChannelGroupCompat build() {
            return this.f3431a;
        }

        @NonNull
        public Builder setDescription(@Nullable String str) {
            this.f3431a.f3430c = str;
            return this;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.f3431a.f3429b = charSequence;
            return this;
        }
    }

    NotificationChannelGroupCompat(String str) {
        this.mChannels = Collections.emptyList();
        this.f3428a = (String) Preconditions.checkNotNull(str);
    }

    @RequiresApi(26)
    private List<NotificationChannelCompat> getChannelsCompat(List<NotificationChannel> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<NotificationChannel> it = list.iterator();
        while (it.hasNext()) {
            NotificationChannel notificationChannelA = i.a(it.next());
            if (this.f3428a.equals(Api26Impl.c(notificationChannelA))) {
                arrayList.add(new NotificationChannelCompat(notificationChannelA));
            }
        }
        return arrayList;
    }

    NotificationChannelGroup a() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 26) {
            return null;
        }
        NotificationChannelGroup notificationChannelGroupA = Api26Impl.a(this.f3428a, this.f3429b);
        if (i2 >= 28) {
            Api28Impl.c(notificationChannelGroupA, this.f3430c);
        }
        return notificationChannelGroupA;
    }

    @NonNull
    public List<NotificationChannelCompat> getChannels() {
        return this.mChannels;
    }

    @Nullable
    public String getDescription() {
        return this.f3430c;
    }

    @NonNull
    public String getId() {
        return this.f3428a;
    }

    @Nullable
    public CharSequence getName() {
        return this.f3429b;
    }

    public boolean isBlocked() {
        return this.mBlocked;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this.f3428a).setName(this.f3429b).setDescription(this.f3430c);
    }

    NotificationChannelGroupCompat(NotificationChannelGroup notificationChannelGroup) {
        this(notificationChannelGroup, Collections.emptyList());
    }

    NotificationChannelGroupCompat(NotificationChannelGroup notificationChannelGroup, List list) {
        this(Api26Impl.d(notificationChannelGroup));
        this.f3429b = Api26Impl.e(notificationChannelGroup);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 28) {
            this.f3430c = Api28Impl.a(notificationChannelGroup);
        }
        if (i2 >= 28) {
            this.mBlocked = Api28Impl.b(notificationChannelGroup);
            this.mChannels = getChannelsCompat(Api26Impl.b(notificationChannelGroup));
        } else {
            this.mChannels = getChannelsCompat(list);
        }
    }
}

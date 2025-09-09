package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.LocusId;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArraySet;
import androidx.core.app.NotificationCompat;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private RemoteViews mBigContentView;
    private final Notification.Builder mBuilder;
    private final NotificationCompat.Builder mBuilderCompat;
    private RemoteViews mContentView;
    private final Context mContext;
    private int mGroupAlertBehavior;
    private RemoteViews mHeadsUpContentView;
    private final List<Bundle> mActionExtrasList = new ArrayList();
    private final Bundle mExtras = new Bundle();

    @RequiresApi(20)
    static class Api20Impl {
        private Api20Impl() {
        }

        @DoNotInline
        static Notification.Builder a(Notification.Builder builder, Notification.Action action) {
            return builder.addAction(action);
        }

        @DoNotInline
        static Notification.Action.Builder b(Notification.Action.Builder builder, Bundle bundle) {
            return builder.addExtras(bundle);
        }

        @DoNotInline
        static Notification.Action.Builder c(Notification.Action.Builder builder, android.app.RemoteInput remoteInput) {
            return builder.addRemoteInput(remoteInput);
        }

        @DoNotInline
        static Notification.Action d(Notification.Action.Builder builder) {
            return builder.build();
        }

        @DoNotInline
        static Notification.Action.Builder e(int i2, CharSequence charSequence, PendingIntent pendingIntent) {
            return new Notification.Action.Builder(i2, charSequence, pendingIntent);
        }

        @DoNotInline
        static String f(Notification notification) {
            return notification.getGroup();
        }

        @DoNotInline
        static Notification.Builder g(Notification.Builder builder, String str) {
            return builder.setGroup(str);
        }

        @DoNotInline
        static Notification.Builder h(Notification.Builder builder, boolean z2) {
            return builder.setGroupSummary(z2);
        }

        @DoNotInline
        static Notification.Builder i(Notification.Builder builder, boolean z2) {
            return builder.setLocalOnly(z2);
        }

        @DoNotInline
        static Notification.Builder j(Notification.Builder builder, String str) {
            return builder.setSortKey(str);
        }
    }

    @RequiresApi(21)
    static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        static Notification.Builder a(Notification.Builder builder, String str) {
            return builder.addPerson(str);
        }

        @DoNotInline
        static Notification.Builder b(Notification.Builder builder, String str) {
            return builder.setCategory(str);
        }

        @DoNotInline
        static Notification.Builder c(Notification.Builder builder, int i2) {
            return builder.setColor(i2);
        }

        @DoNotInline
        static Notification.Builder d(Notification.Builder builder, Notification notification) {
            return builder.setPublicVersion(notification);
        }

        @DoNotInline
        static Notification.Builder e(Notification.Builder builder, Uri uri, Object obj) {
            return builder.setSound(uri, (AudioAttributes) obj);
        }

        @DoNotInline
        static Notification.Builder f(Notification.Builder builder, int i2) {
            return builder.setVisibility(i2);
        }
    }

    @RequiresApi(23)
    static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        static Notification.Action.Builder a(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
            return new Notification.Action.Builder(icon, charSequence, pendingIntent);
        }

        @DoNotInline
        static Notification.Builder b(Notification.Builder builder, Icon icon) {
            return builder.setLargeIcon(icon);
        }

        @DoNotInline
        static Notification.Builder c(Notification.Builder builder, Object obj) {
            return builder.setSmallIcon((Icon) obj);
        }
    }

    @RequiresApi(24)
    static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        static Notification.Action.Builder a(Notification.Action.Builder builder, boolean z2) {
            return builder.setAllowGeneratedReplies(z2);
        }

        @DoNotInline
        static Notification.Builder b(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomBigContentView(remoteViews);
        }

        @DoNotInline
        static Notification.Builder c(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomContentView(remoteViews);
        }

        @DoNotInline
        static Notification.Builder d(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomHeadsUpContentView(remoteViews);
        }

        @DoNotInline
        static Notification.Builder e(Notification.Builder builder, CharSequence[] charSequenceArr) {
            return builder.setRemoteInputHistory(charSequenceArr);
        }
    }

    @RequiresApi(26)
    static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        static Notification.Builder a(Context context, String str) {
            return new Notification.Builder(context, str);
        }

        @DoNotInline
        static Notification.Builder b(Notification.Builder builder, int i2) {
            return builder.setBadgeIconType(i2);
        }

        @DoNotInline
        static Notification.Builder c(Notification.Builder builder, boolean z2) {
            return builder.setColorized(z2);
        }

        @DoNotInline
        static Notification.Builder d(Notification.Builder builder, int i2) {
            return builder.setGroupAlertBehavior(i2);
        }

        @DoNotInline
        static Notification.Builder e(Notification.Builder builder, CharSequence charSequence) {
            return builder.setSettingsText(charSequence);
        }

        @DoNotInline
        static Notification.Builder f(Notification.Builder builder, String str) {
            return builder.setShortcutId(str);
        }

        @DoNotInline
        static Notification.Builder g(Notification.Builder builder, long j2) {
            return builder.setTimeoutAfter(j2);
        }
    }

    @RequiresApi(28)
    static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static Notification.Builder a(Notification.Builder builder, android.app.Person person) {
            return builder.addPerson(person);
        }

        @DoNotInline
        static Notification.Action.Builder b(Notification.Action.Builder builder, int i2) {
            return builder.setSemanticAction(i2);
        }
    }

    @RequiresApi(29)
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static Notification.Builder a(Notification.Builder builder, boolean z2) {
            return builder.setAllowSystemGeneratedContextualActions(z2);
        }

        @DoNotInline
        static Notification.Builder b(Notification.Builder builder, Notification.BubbleMetadata bubbleMetadata) {
            return builder.setBubbleMetadata(bubbleMetadata);
        }

        @DoNotInline
        static Notification.Action.Builder c(Notification.Action.Builder builder, boolean z2) {
            return builder.setContextual(z2);
        }

        @DoNotInline
        static Notification.Builder d(Notification.Builder builder, Object obj) {
            return builder.setLocusId((LocusId) obj);
        }
    }

    @RequiresApi(31)
    static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        static Notification.Action.Builder a(Notification.Action.Builder builder, boolean z2) {
            return builder.setAuthenticationRequired(z2);
        }

        @DoNotInline
        static Notification.Builder b(Notification.Builder builder, int i2) {
            return builder.setForegroundServiceBehavior(i2);
        }
    }

    NotificationCompatBuilder(NotificationCompat.Builder builder) {
        int i2;
        this.mBuilderCompat = builder;
        Context context = builder.mContext;
        this.mContext = context;
        if (Build.VERSION.SDK_INT >= 26) {
            this.mBuilder = Api26Impl.a(context, builder.I);
        } else {
            this.mBuilder = new Notification.Builder(builder.mContext);
        }
        Notification notification = builder.R;
        this.mBuilder.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, builder.f3439f).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(builder.f3435b).setContentText(builder.f3436c).setContentInfo(builder.f3441h).setContentIntent(builder.f3437d).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(builder.f3438e, (notification.flags & 128) != 0).setNumber(builder.f3442i).setProgress(builder.f3451r, builder.f3452s, builder.f3453t);
        Notification.Builder builder2 = this.mBuilder;
        IconCompat iconCompat = builder.f3440g;
        Api23Impl.b(builder2, iconCompat == null ? null : iconCompat.toIcon(context));
        this.mBuilder.setSubText(builder.f3448o).setUsesChronometer(builder.f3445l).setPriority(builder.f3443j);
        NotificationCompat.Style style = builder.f3447n;
        if (style instanceof NotificationCompat.CallStyle) {
            Iterator<NotificationCompat.Action> it = ((NotificationCompat.CallStyle) style).getActionsListWithSystemActions().iterator();
            while (it.hasNext()) {
                addAction(it.next());
            }
        } else {
            Iterator<NotificationCompat.Action> it2 = builder.mActions.iterator();
            while (it2.hasNext()) {
                addAction(it2.next());
            }
        }
        Bundle bundle = builder.B;
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        int i3 = Build.VERSION.SDK_INT;
        this.mContentView = builder.F;
        this.mBigContentView = builder.G;
        this.mBuilder.setShowWhen(builder.f3444k);
        Api20Impl.i(this.mBuilder, builder.f3457x);
        Api20Impl.g(this.mBuilder, builder.f3454u);
        Api20Impl.j(this.mBuilder, builder.f3456w);
        Api20Impl.h(this.mBuilder, builder.f3455v);
        this.mGroupAlertBehavior = builder.N;
        Api21Impl.b(this.mBuilder, builder.A);
        Api21Impl.c(this.mBuilder, builder.C);
        Api21Impl.f(this.mBuilder, builder.D);
        Api21Impl.d(this.mBuilder, builder.E);
        Api21Impl.e(this.mBuilder, notification.sound, notification.audioAttributes);
        List listCombineLists = i3 < 28 ? combineLists(getPeople(builder.mPersonList), builder.mPeople) : builder.mPeople;
        if (listCombineLists != null && !listCombineLists.isEmpty()) {
            Iterator it3 = listCombineLists.iterator();
            while (it3.hasNext()) {
                Api21Impl.a(this.mBuilder, (String) it3.next());
            }
        }
        this.mHeadsUpContentView = builder.H;
        if (builder.f3434a.size() > 0) {
            Bundle bundle2 = builder.getExtras().getBundle("android.car.EXTENSIONS");
            bundle2 = bundle2 == null ? new Bundle() : bundle2;
            Bundle bundle3 = new Bundle(bundle2);
            Bundle bundle4 = new Bundle();
            for (int i4 = 0; i4 < builder.f3434a.size(); i4++) {
                bundle4.putBundle(Integer.toString(i4), NotificationCompatJellybean.b((NotificationCompat.Action) builder.f3434a.get(i4)));
            }
            bundle2.putBundle("invisible_actions", bundle4);
            bundle3.putBundle("invisible_actions", bundle4);
            builder.getExtras().putBundle("android.car.EXTENSIONS", bundle2);
            this.mExtras.putBundle("android.car.EXTENSIONS", bundle3);
        }
        int i5 = Build.VERSION.SDK_INT;
        Object obj = builder.T;
        if (obj != null) {
            Api23Impl.c(this.mBuilder, obj);
        }
        if (i5 >= 24) {
            this.mBuilder.setExtras(builder.B);
            Api24Impl.e(this.mBuilder, builder.f3450q);
            RemoteViews remoteViews = builder.F;
            if (remoteViews != null) {
                Api24Impl.c(this.mBuilder, remoteViews);
            }
            RemoteViews remoteViews2 = builder.G;
            if (remoteViews2 != null) {
                Api24Impl.b(this.mBuilder, remoteViews2);
            }
            RemoteViews remoteViews3 = builder.H;
            if (remoteViews3 != null) {
                Api24Impl.d(this.mBuilder, remoteViews3);
            }
        }
        if (i5 >= 26) {
            Api26Impl.b(this.mBuilder, builder.J);
            Api26Impl.e(this.mBuilder, builder.f3449p);
            Api26Impl.f(this.mBuilder, builder.K);
            Api26Impl.g(this.mBuilder, builder.M);
            Api26Impl.d(this.mBuilder, builder.N);
            if (builder.f3459z) {
                Api26Impl.c(this.mBuilder, builder.f3458y);
            }
            if (!TextUtils.isEmpty(builder.I)) {
                this.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
            }
        }
        if (i5 >= 28) {
            Iterator<Person> it4 = builder.mPersonList.iterator();
            while (it4.hasNext()) {
                Api28Impl.a(this.mBuilder, it4.next().toAndroidPerson());
            }
        }
        int i6 = Build.VERSION.SDK_INT;
        if (i6 >= 29) {
            Api29Impl.a(this.mBuilder, builder.P);
            Api29Impl.b(this.mBuilder, NotificationCompat.BubbleMetadata.toPlatform(builder.Q));
            LocusIdCompat locusIdCompat = builder.L;
            if (locusIdCompat != null) {
                Api29Impl.d(this.mBuilder, locusIdCompat.toLocusId());
            }
        }
        if (i6 >= 31 && (i2 = builder.O) != 0) {
            Api31Impl.b(this.mBuilder, i2);
        }
        if (builder.S) {
            if (this.mBuilderCompat.f3455v) {
                this.mGroupAlertBehavior = 2;
            } else {
                this.mGroupAlertBehavior = 1;
            }
            this.mBuilder.setVibrate(null);
            this.mBuilder.setSound(null);
            int i7 = notification.defaults & (-4);
            notification.defaults = i7;
            this.mBuilder.setDefaults(i7);
            if (i6 >= 26) {
                if (TextUtils.isEmpty(this.mBuilderCompat.f3454u)) {
                    Api20Impl.g(this.mBuilder, NotificationCompat.GROUP_KEY_SILENT);
                }
                Api26Impl.d(this.mBuilder, this.mGroupAlertBehavior);
            }
        }
    }

    private void addAction(NotificationCompat.Action action) {
        IconCompat iconCompat = action.getIconCompat();
        Notification.Action.Builder builderA = Api23Impl.a(iconCompat != null ? iconCompat.toIcon() : null, action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            for (android.app.RemoteInput remoteInput : RemoteInput.b(action.getRemoteInputs())) {
                Api20Impl.c(builderA, remoteInput);
            }
        }
        Bundle bundle = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 24) {
            Api24Impl.a(builderA, action.getAllowGeneratedReplies());
        }
        bundle.putInt("android.support.action.semanticAction", action.getSemanticAction());
        if (i2 >= 28) {
            Api28Impl.b(builderA, action.getSemanticAction());
        }
        if (i2 >= 29) {
            Api29Impl.c(builderA, action.isContextual());
        }
        if (i2 >= 31) {
            Api31Impl.a(builderA, action.isAuthenticationRequired());
        }
        bundle.putBoolean("android.support.action.showsUserInterface", action.getShowsUserInterface());
        Api20Impl.b(builderA, bundle);
        Api20Impl.a(this.mBuilder, Api20Impl.d(builderA));
    }

    @Nullable
    private static List<String> combineLists(@Nullable List<String> list, @Nullable List<String> list2) {
        if (list == null) {
            return list2;
        }
        if (list2 == null) {
            return list;
        }
        ArraySet arraySet = new ArraySet(list.size() + list2.size());
        arraySet.addAll(list);
        arraySet.addAll(list2);
        return new ArrayList(arraySet);
    }

    @Nullable
    private static List<String> getPeople(@Nullable List<Person> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Person> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resolveToLegacyUri());
        }
        return arrayList;
    }

    private void removeSoundAndVibration(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -4;
    }

    protected Notification a() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            return this.mBuilder.build();
        }
        if (i2 >= 24) {
            Notification notificationBuild = this.mBuilder.build();
            if (this.mGroupAlertBehavior != 0) {
                if (Api20Impl.f(notificationBuild) != null && (notificationBuild.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                    removeSoundAndVibration(notificationBuild);
                }
                if (Api20Impl.f(notificationBuild) != null && (notificationBuild.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notificationBuild);
                }
            }
            return notificationBuild;
        }
        this.mBuilder.setExtras(this.mExtras);
        Notification notificationBuild2 = this.mBuilder.build();
        RemoteViews remoteViews = this.mContentView;
        if (remoteViews != null) {
            notificationBuild2.contentView = remoteViews;
        }
        RemoteViews remoteViews2 = this.mBigContentView;
        if (remoteViews2 != null) {
            notificationBuild2.bigContentView = remoteViews2;
        }
        RemoteViews remoteViews3 = this.mHeadsUpContentView;
        if (remoteViews3 != null) {
            notificationBuild2.headsUpContentView = remoteViews3;
        }
        if (this.mGroupAlertBehavior != 0) {
            if (Api20Impl.f(notificationBuild2) != null && (notificationBuild2.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                removeSoundAndVibration(notificationBuild2);
            }
            if (Api20Impl.f(notificationBuild2) != null && (notificationBuild2.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                removeSoundAndVibration(notificationBuild2);
            }
        }
        return notificationBuild2;
    }

    Context b() {
        return this.mContext;
    }

    public Notification build() {
        Bundle extras;
        RemoteViews remoteViewsMakeHeadsUpContentView;
        RemoteViews remoteViewsMakeBigContentView;
        NotificationCompat.Style style = this.mBuilderCompat.f3447n;
        if (style != null) {
            style.apply(this);
        }
        RemoteViews remoteViewsMakeContentView = style != null ? style.makeContentView(this) : null;
        Notification notificationA = a();
        if (remoteViewsMakeContentView != null) {
            notificationA.contentView = remoteViewsMakeContentView;
        } else {
            RemoteViews remoteViews = this.mBuilderCompat.F;
            if (remoteViews != null) {
                notificationA.contentView = remoteViews;
            }
        }
        if (style != null && (remoteViewsMakeBigContentView = style.makeBigContentView(this)) != null) {
            notificationA.bigContentView = remoteViewsMakeBigContentView;
        }
        if (style != null && (remoteViewsMakeHeadsUpContentView = this.mBuilderCompat.f3447n.makeHeadsUpContentView(this)) != null) {
            notificationA.headsUpContentView = remoteViewsMakeHeadsUpContentView;
        }
        if (style != null && (extras = NotificationCompat.getExtras(notificationA)) != null) {
            style.addCompatExtras(extras);
        }
        return notificationA;
    }

    @Override // androidx.core.app.NotificationBuilderWithBuilderAccessor
    public Notification.Builder getBuilder() {
        return this.mBuilder;
    }
}

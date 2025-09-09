package androidx.media.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.res.Resources;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.RemoteViews;
import androidx.annotation.RestrictTo;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;
import androidx.media.R;
import o.a;

/* loaded from: classes.dex */
public class NotificationCompat {

    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
        private void setBackgroundColor(RemoteViews remoteViews) {
            remoteViews.setInt(R.id.status_bar_latest_event_content, "setBackgroundColor", this.f3460a.getColor() != 0 ? this.f3460a.getColor() : this.f3460a.mContext.getResources().getColor(R.color.notification_material_background_media_default_color));
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle, androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(h(a.a()));
            } else {
                super.apply(notificationBuilderWithBuilderAccessor);
            }
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle
        int k(int i2) {
            return i2 <= 3 ? R.layout.notification_template_big_media_narrow_custom : R.layout.notification_template_big_media_custom;
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle
        int l() {
            return this.f3460a.getContentView() != null ? R.layout.notification_template_media_custom : super.l();
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle, androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) throws Resources.NotFoundException {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews bigContentView = this.f3460a.getBigContentView() != null ? this.f3460a.getBigContentView() : this.f3460a.getContentView();
            if (bigContentView == null) {
                return null;
            }
            RemoteViews remoteViewsI = i();
            buildIntoRemoteViews(remoteViewsI, bigContentView);
            setBackgroundColor(remoteViewsI);
            return remoteViewsI;
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle, androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) throws Resources.NotFoundException {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            boolean z2 = this.f3460a.getContentView() != null;
            if (!z2 && this.f3460a.getBigContentView() == null) {
                return null;
            }
            RemoteViews remoteViewsJ = j();
            if (z2) {
                buildIntoRemoteViews(remoteViewsJ, this.f3460a.getContentView());
            }
            setBackgroundColor(remoteViewsJ);
            return remoteViewsJ;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) throws Resources.NotFoundException {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUpContentView = this.f3460a.getHeadsUpContentView() != null ? this.f3460a.getHeadsUpContentView() : this.f3460a.getContentView();
            if (headsUpContentView == null) {
                return null;
            }
            RemoteViews remoteViewsI = i();
            buildIntoRemoteViews(remoteViewsI, headsUpContentView);
            setBackgroundColor(remoteViewsI);
            return remoteViewsI;
        }
    }

    private NotificationCompat() {
    }

    public static class MediaStyle extends NotificationCompat.Style {
        private static final int MAX_MEDIA_BUTTONS = 5;
        private static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;

        /* renamed from: e, reason: collision with root package name */
        int[] f4738e = null;

        /* renamed from: f, reason: collision with root package name */
        MediaSessionCompat.Token f4739f;

        /* renamed from: g, reason: collision with root package name */
        boolean f4740g;

        /* renamed from: h, reason: collision with root package name */
        PendingIntent f4741h;

        public MediaStyle() {
        }

        private RemoteViews generateMediaActionButton(NotificationCompat.Action action) {
            boolean z2 = action.getActionIntent() == null;
            RemoteViews remoteViews = new RemoteViews(this.f3460a.mContext.getPackageName(), R.layout.notification_media_action);
            remoteViews.setImageViewResource(R.id.action0, action.getIcon());
            if (!z2) {
                remoteViews.setOnClickPendingIntent(R.id.action0, action.getActionIntent());
            }
            remoteViews.setContentDescription(R.id.action0, action.getTitle());
            return remoteViews;
        }

        public static MediaSessionCompat.Token getMediaSession(Notification notification) {
            Parcelable parcelable;
            Bundle extras = androidx.core.app.NotificationCompat.getExtras(notification);
            if (extras == null || (parcelable = extras.getParcelable(androidx.core.app.NotificationCompat.EXTRA_MEDIA_SESSION)) == null) {
                return null;
            }
            return MediaSessionCompat.Token.fromToken(parcelable);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            notificationBuilderWithBuilderAccessor.getBuilder().setStyle(h(new Notification.MediaStyle()));
        }

        Notification.MediaStyle h(Notification.MediaStyle mediaStyle) {
            int[] iArr = this.f4738e;
            if (iArr != null) {
                mediaStyle.setShowActionsInCompactView(iArr);
            }
            MediaSessionCompat.Token token = this.f4739f;
            if (token != null) {
                mediaStyle.setMediaSession((MediaSession.Token) token.getToken());
            }
            return mediaStyle;
        }

        RemoteViews i() throws Resources.NotFoundException {
            int iMin = Math.min(this.f3460a.mActions.size(), 5);
            RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(false, k(iMin), false);
            remoteViewsApplyStandardTemplate.removeAllViews(R.id.media_actions);
            if (iMin > 0) {
                for (int i2 = 0; i2 < iMin; i2++) {
                    remoteViewsApplyStandardTemplate.addView(R.id.media_actions, generateMediaActionButton(this.f3460a.mActions.get(i2)));
                }
            }
            if (this.f4740g) {
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 0);
                remoteViewsApplyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", this.f3460a.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
                remoteViewsApplyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, this.f4741h);
            } else {
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 8);
            }
            return remoteViewsApplyStandardTemplate;
        }

        RemoteViews j() throws Resources.NotFoundException {
            RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(false, l(), true);
            int size = this.f3460a.mActions.size();
            int[] iArr = this.f4738e;
            int iMin = iArr == null ? 0 : Math.min(iArr.length, 3);
            remoteViewsApplyStandardTemplate.removeAllViews(R.id.media_actions);
            if (iMin > 0) {
                for (int i2 = 0; i2 < iMin; i2++) {
                    if (i2 >= size) {
                        throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", Integer.valueOf(i2), Integer.valueOf(size - 1)));
                    }
                    remoteViewsApplyStandardTemplate.addView(R.id.media_actions, generateMediaActionButton(this.f3460a.mActions.get(this.f4738e[i2])));
                }
            }
            if (this.f4740g) {
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.end_padder, 8);
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 0);
                remoteViewsApplyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, this.f4741h);
                remoteViewsApplyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", this.f3460a.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
            } else {
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.end_padder, 0);
                remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 8);
            }
            return remoteViewsApplyStandardTemplate;
        }

        int k(int i2) {
            return i2 <= 3 ? R.layout.notification_template_big_media_narrow : R.layout.notification_template_big_media;
        }

        int l() {
            return R.layout.notification_template_media;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        public MediaStyle setCancelButtonIntent(PendingIntent pendingIntent) {
            this.f4741h = pendingIntent;
            return this;
        }

        public MediaStyle setMediaSession(MediaSessionCompat.Token token) {
            this.f4739f = token;
            return this;
        }

        public MediaStyle setShowActionsInCompactView(int... iArr) {
            this.f4738e = iArr;
            return this;
        }

        public MediaStyle setShowCancelButton(boolean z2) {
            return this;
        }

        public MediaStyle(NotificationCompat.Builder builder) {
            setBuilder(builder);
        }
    }
}

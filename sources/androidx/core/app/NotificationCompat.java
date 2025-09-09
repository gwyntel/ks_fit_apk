package androidx.core.app;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.LocusId;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.R;
import androidx.core.app.Person;
import androidx.core.content.ContextCompat;
import androidx.core.content.LocusIdCompat;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.ViewCompat;
import com.facebook.share.internal.ShareConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_LOCATION_SHARING = "location_sharing";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_MISSED_CALL = "missed_call";
    public static final String CATEGORY_NAVIGATION = "navigation";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_STOPWATCH = "stopwatch";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    public static final String CATEGORY_WORKOUT = "workout";

    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_ANSWER_COLOR = "android.answerColor";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_ANSWER_INTENT = "android.answerIntent";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_BIG_TEXT = "android.bigText";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CALL_IS_VIDEO = "android.callIsVideo";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CALL_PERSON = "android.callPerson";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CALL_PERSON_COMPAT = "android.callPersonCompat";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CALL_TYPE = "android.callType";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHANNEL_GROUP_ID = "android.intent.extra.CHANNEL_GROUP_ID";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHANNEL_ID = "android.intent.extra.CHANNEL_ID";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHRONOMETER_COUNT_DOWN = "android.chronometerCountDown";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_COLORIZED = "android.colorized";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_COMPAT_TEMPLATE = "androidx.core.app.extra.COMPAT_TEMPLATE";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_DECLINE_COLOR = "android.declineColor";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_DECLINE_INTENT = "android.declineIntent";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_HANG_UP_INTENT = "android.hangUpIntent";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_HIDDEN_CONVERSATION_TITLE = "android.hiddenConversationTitle";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_HISTORIC_MESSAGES = "android.messages.historic";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_INFO_TEXT = "android.infoText";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_IS_GROUP_CONVERSATION = "android.isGroupConversation";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MESSAGES = "android.messages";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MESSAGING_STYLE_USER = "android.messagingStyleUser";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_NOTIFICATION_ID = "android.intent.extra.NOTIFICATION_ID";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_NOTIFICATION_TAG = "android.intent.extra.NOTIFICATION_TAG";

    @SuppressLint({"ActionValue"})
    @Deprecated
    public static final String EXTRA_PEOPLE = "android.people";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PEOPLE_LIST = "android.people.list";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PICTURE = "android.picture";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PICTURE_CONTENT_DESCRIPTION = "android.pictureContentDescription";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PICTURE_ICON = "android.pictureIcon";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS = "android.progress";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED = "android.showBigPictureWhenCollapsed";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SMALL_ICON = "android.icon";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SUB_TEXT = "android.subText";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEMPLATE = "android.template";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEXT = "android.text";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEXT_LINES = "android.textLines";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TITLE = "android.title";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TITLE_BIG = "android.title.big";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_VERIFICATION_ICON = "android.verificationIcon";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_VERIFICATION_ICON_COMPAT = "android.verificationIconCompat";

    @SuppressLint({"ActionValue"})
    public static final String EXTRA_VERIFICATION_TEXT = "android.verificationText";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_BUBBLE = 4096;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;

    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int FOREGROUND_SERVICE_DEFAULT = 0;
    public static final int FOREGROUND_SERVICE_DEFERRED = 2;
    public static final int FOREGROUND_SERVICE_IMMEDIATE = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final String GROUP_KEY_SILENT = "silent";

    @SuppressLint({"ActionValue"})
    public static final String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int MAX_ACTION_BUTTONS = 3;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    private static final String TAG = "NotifCompat";
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    public static class Action {
        public static final int SEMANTIC_ACTION_ARCHIVE = 5;
        public static final int SEMANTIC_ACTION_CALL = 10;
        public static final int SEMANTIC_ACTION_DELETE = 4;
        public static final int SEMANTIC_ACTION_MARK_AS_READ = 2;
        public static final int SEMANTIC_ACTION_MARK_AS_UNREAD = 3;
        public static final int SEMANTIC_ACTION_MUTE = 6;
        public static final int SEMANTIC_ACTION_NONE = 0;
        public static final int SEMANTIC_ACTION_REPLY = 1;
        public static final int SEMANTIC_ACTION_THUMBS_DOWN = 9;
        public static final int SEMANTIC_ACTION_THUMBS_UP = 8;
        public static final int SEMANTIC_ACTION_UNMUTE = 7;

        /* renamed from: a, reason: collision with root package name */
        final Bundle f3432a;

        @Nullable
        public PendingIntent actionIntent;

        /* renamed from: b, reason: collision with root package name */
        boolean f3433b;

        @Deprecated
        public int icon;
        private boolean mAllowGeneratedReplies;
        private boolean mAuthenticationRequired;
        private final RemoteInput[] mDataOnlyRemoteInputs;

        @Nullable
        private IconCompat mIcon;
        private final boolean mIsContextual;
        private final RemoteInput[] mRemoteInputs;
        private final int mSemanticAction;
        public CharSequence title;

        public static final class Builder {
            private boolean mAllowGeneratedReplies;
            private boolean mAuthenticationRequired;
            private final Bundle mExtras;
            private final IconCompat mIcon;
            private final PendingIntent mIntent;
            private boolean mIsContextual;
            private ArrayList<RemoteInput> mRemoteInputs;
            private int mSemanticAction;
            private boolean mShowsUserInterface;
            private final CharSequence mTitle;

            @RequiresApi(20)
            static class Api20Impl {
                private Api20Impl() {
                }

                @DoNotInline
                static Bundle a(Notification.Action action) {
                    return action.getExtras();
                }

                @DoNotInline
                static android.app.RemoteInput[] b(Notification.Action action) {
                    return action.getRemoteInputs();
                }
            }

            @RequiresApi(23)
            static class Api23Impl {
                private Api23Impl() {
                }

                @DoNotInline
                static Icon a(Notification.Action action) {
                    return action.getIcon();
                }
            }

            @RequiresApi(24)
            static class Api24Impl {
                private Api24Impl() {
                }

                @DoNotInline
                static boolean a(Notification.Action action) {
                    return action.getAllowGeneratedReplies();
                }
            }

            @RequiresApi(28)
            static class Api28Impl {
                private Api28Impl() {
                }

                @DoNotInline
                static int a(Notification.Action action) {
                    return action.getSemanticAction();
                }
            }

            @RequiresApi(29)
            static class Api29Impl {
                private Api29Impl() {
                }

                @DoNotInline
                static boolean a(Notification.Action action) {
                    return action.isContextual();
                }
            }

            @RequiresApi(31)
            static class Api31Impl {
                private Api31Impl() {
                }

                @DoNotInline
                static boolean a(Notification.Action action) {
                    return action.isAuthenticationRequired();
                }
            }

            public Builder(@Nullable IconCompat iconCompat, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
                this(iconCompat, charSequence, pendingIntent, new Bundle(), null, true, 0, true, false, false);
            }

            private void checkContextualActionNullFields() {
                if (this.mIsContextual && this.mIntent == null) {
                    throw new NullPointerException("Contextual Actions must contain a valid PendingIntent");
                }
            }

            @NonNull
            @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
            public static Builder fromAndroidAction(@NonNull Notification.Action action) {
                Builder builder = Api23Impl.a(action) != null ? new Builder(IconCompat.createFromIconOrNullIfZeroResId(Api23Impl.a(action)), action.title, action.actionIntent) : new Builder(action.icon, action.title, action.actionIntent);
                android.app.RemoteInput[] remoteInputArrB = Api20Impl.b(action);
                if (remoteInputArrB != null && remoteInputArrB.length != 0) {
                    for (android.app.RemoteInput remoteInput : remoteInputArrB) {
                        builder.addRemoteInput(RemoteInput.c(remoteInput));
                    }
                }
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 24) {
                    builder.mAllowGeneratedReplies = Api24Impl.a(action);
                }
                if (i2 >= 28) {
                    builder.setSemanticAction(Api28Impl.a(action));
                }
                if (i2 >= 29) {
                    builder.setContextual(Api29Impl.a(action));
                }
                if (i2 >= 31) {
                    builder.setAuthenticationRequired(Api31Impl.a(action));
                }
                builder.addExtras(Api20Impl.a(action));
                return builder;
            }

            @NonNull
            public Builder addExtras(@Nullable Bundle bundle) {
                if (bundle != null) {
                    this.mExtras.putAll(bundle);
                }
                return this;
            }

            @NonNull
            public Builder addRemoteInput(@Nullable RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList<>();
                }
                if (remoteInput != null) {
                    this.mRemoteInputs.add(remoteInput);
                }
                return this;
            }

            @NonNull
            public Action build() {
                checkContextualActionNullFields();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList<RemoteInput> arrayList3 = this.mRemoteInputs;
                if (arrayList3 != null) {
                    Iterator<RemoteInput> it = arrayList3.iterator();
                    while (it.hasNext()) {
                        RemoteInput next = it.next();
                        if (next.isDataOnly()) {
                            arrayList.add(next);
                        } else {
                            arrayList2.add(next);
                        }
                    }
                }
                return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrayList2.isEmpty() ? null : (RemoteInput[]) arrayList2.toArray(new RemoteInput[arrayList2.size()]), arrayList.isEmpty() ? null : (RemoteInput[]) arrayList.toArray(new RemoteInput[arrayList.size()]), this.mAllowGeneratedReplies, this.mSemanticAction, this.mShowsUserInterface, this.mIsContextual, this.mAuthenticationRequired);
            }

            @NonNull
            public Builder extend(@NonNull Extender extender) {
                extender.extend(this);
                return this;
            }

            @NonNull
            public Bundle getExtras() {
                return this.mExtras;
            }

            @NonNull
            public Builder setAllowGeneratedReplies(boolean z2) {
                this.mAllowGeneratedReplies = z2;
                return this;
            }

            @NonNull
            public Builder setAuthenticationRequired(boolean z2) {
                this.mAuthenticationRequired = z2;
                return this;
            }

            @NonNull
            public Builder setContextual(boolean z2) {
                this.mIsContextual = z2;
                return this;
            }

            @NonNull
            public Builder setSemanticAction(int i2) {
                this.mSemanticAction = i2;
                return this;
            }

            @NonNull
            public Builder setShowsUserInterface(boolean z2) {
                this.mShowsUserInterface = z2;
                return this;
            }

            public Builder(int i2, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
                this(i2 != 0 ? IconCompat.createWithResource(null, "", i2) : null, charSequence, pendingIntent, new Bundle(), null, true, 0, true, false, false);
            }

            public Builder(@NonNull Action action) {
                this(action.getIconCompat(), action.title, action.actionIntent, new Bundle(action.f3432a), action.getRemoteInputs(), action.getAllowGeneratedReplies(), action.getSemanticAction(), action.f3433b, action.isContextual(), action.isAuthenticationRequired());
            }

            private Builder(@Nullable IconCompat iconCompat, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent, @NonNull Bundle bundle, @Nullable RemoteInput[] remoteInputArr, boolean z2, int i2, boolean z3, boolean z4, boolean z5) {
                this.mAllowGeneratedReplies = true;
                this.mShowsUserInterface = true;
                this.mIcon = iconCompat;
                this.mTitle = Builder.a(charSequence);
                this.mIntent = pendingIntent;
                this.mExtras = bundle;
                this.mRemoteInputs = remoteInputArr == null ? null : new ArrayList<>(Arrays.asList(remoteInputArr));
                this.mAllowGeneratedReplies = z2;
                this.mSemanticAction = i2;
                this.mShowsUserInterface = z3;
                this.mIsContextual = z4;
                this.mAuthenticationRequired = z5;
            }
        }

        public interface Extender {
            @NonNull
            Builder extend(@NonNull Builder builder);
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SemanticAction {
        }

        public static final class WearableExtender implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags;
            private CharSequence mInProgressLabel;

            public WearableExtender() {
                this.mFlags = 1;
            }

            private void setFlag(int i2, boolean z2) {
                if (z2) {
                    this.mFlags = i2 | this.mFlags;
                } else {
                    this.mFlags = (~i2) & this.mFlags;
                }
            }

            @Override // androidx.core.app.NotificationCompat.Action.Extender
            @NonNull
            public Builder extend(@NonNull Builder builder) {
                Bundle bundle = new Bundle();
                int i2 = this.mFlags;
                if (i2 != 1) {
                    bundle.putInt("flags", i2);
                }
                CharSequence charSequence = this.mInProgressLabel;
                if (charSequence != null) {
                    bundle.putCharSequence(KEY_IN_PROGRESS_LABEL, charSequence);
                }
                CharSequence charSequence2 = this.mConfirmLabel;
                if (charSequence2 != null) {
                    bundle.putCharSequence(KEY_CONFIRM_LABEL, charSequence2);
                }
                CharSequence charSequence3 = this.mCancelLabel;
                if (charSequence3 != null) {
                    bundle.putCharSequence(KEY_CANCEL_LABEL, charSequence3);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
                return builder;
            }

            @Nullable
            @Deprecated
            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            @Nullable
            @Deprecated
            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            public boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            @Nullable
            @Deprecated
            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            @NonNull
            public WearableExtender setAvailableOffline(boolean z2) {
                setFlag(1, z2);
                return this;
            }

            @NonNull
            @Deprecated
            public WearableExtender setCancelLabel(@Nullable CharSequence charSequence) {
                this.mCancelLabel = charSequence;
                return this;
            }

            @NonNull
            @Deprecated
            public WearableExtender setConfirmLabel(@Nullable CharSequence charSequence) {
                this.mConfirmLabel = charSequence;
                return this;
            }

            @NonNull
            public WearableExtender setHintDisplayActionInline(boolean z2) {
                setFlag(4, z2);
                return this;
            }

            @NonNull
            public WearableExtender setHintLaunchesActivity(boolean z2) {
                setFlag(2, z2);
                return this;
            }

            @NonNull
            @Deprecated
            public WearableExtender setInProgressLabel(@Nullable CharSequence charSequence) {
                this.mInProgressLabel = charSequence;
                return this;
            }

            @NonNull
            /* renamed from: clone, reason: merged with bridge method [inline-methods] */
            public WearableExtender m6clone() {
                WearableExtender wearableExtender = new WearableExtender();
                wearableExtender.mFlags = this.mFlags;
                wearableExtender.mInProgressLabel = this.mInProgressLabel;
                wearableExtender.mConfirmLabel = this.mConfirmLabel;
                wearableExtender.mCancelLabel = this.mCancelLabel;
                return wearableExtender;
            }

            public WearableExtender(@NonNull Action action) {
                this.mFlags = 1;
                Bundle bundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (bundle != null) {
                    this.mFlags = bundle.getInt("flags", 1);
                    this.mInProgressLabel = bundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = bundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = bundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }
        }

        public Action(int i2, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
            this(i2 != 0 ? IconCompat.createWithResource(null, "", i2) : null, charSequence, pendingIntent);
        }

        @Nullable
        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        @Nullable
        public RemoteInput[] getDataOnlyRemoteInputs() {
            return this.mDataOnlyRemoteInputs;
        }

        @NonNull
        public Bundle getExtras() {
            return this.f3432a;
        }

        @Deprecated
        public int getIcon() {
            return this.icon;
        }

        @Nullable
        public IconCompat getIconCompat() {
            int i2;
            if (this.mIcon == null && (i2 = this.icon) != 0) {
                this.mIcon = IconCompat.createWithResource(null, "", i2);
            }
            return this.mIcon;
        }

        @Nullable
        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public int getSemanticAction() {
            return this.mSemanticAction;
        }

        public boolean getShowsUserInterface() {
            return this.f3433b;
        }

        @Nullable
        public CharSequence getTitle() {
            return this.title;
        }

        public boolean isAuthenticationRequired() {
            return this.mAuthenticationRequired;
        }

        public boolean isContextual() {
            return this.mIsContextual;
        }

        public Action(@Nullable IconCompat iconCompat, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
            this(iconCompat, charSequence, pendingIntent, new Bundle(), (RemoteInput[]) null, (RemoteInput[]) null, true, 0, true, false, false);
        }

        Action(int i2, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z2, int i3, boolean z3, boolean z4, boolean z5) {
            this(i2 != 0 ? IconCompat.createWithResource(null, "", i2) : null, charSequence, pendingIntent, bundle, remoteInputArr, remoteInputArr2, z2, i3, z3, z4, z5);
        }

        Action(IconCompat iconCompat, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z2, int i2, boolean z3, boolean z4, boolean z5) {
            this.f3433b = true;
            this.mIcon = iconCompat;
            if (iconCompat != null && iconCompat.getType() == 2) {
                this.icon = iconCompat.getResId();
            }
            this.title = Builder.a(charSequence);
            this.actionIntent = pendingIntent;
            this.f3432a = bundle == null ? new Bundle() : bundle;
            this.mRemoteInputs = remoteInputArr;
            this.mDataOnlyRemoteInputs = remoteInputArr2;
            this.mAllowGeneratedReplies = z2;
            this.mSemanticAction = i2;
            this.f3433b = z3;
            this.mIsContextual = z4;
            this.mAuthenticationRequired = z5;
        }
    }

    @RequiresApi(20)
    static class Api20Impl {
        private Api20Impl() {
        }

        @DoNotInline
        static boolean a(android.app.RemoteInput remoteInput) {
            return remoteInput.getAllowFreeFormInput();
        }

        @DoNotInline
        static CharSequence[] b(android.app.RemoteInput remoteInput) {
            return remoteInput.getChoices();
        }

        @DoNotInline
        static Bundle c(Notification.Action action) {
            return action.getExtras();
        }

        @DoNotInline
        static Bundle d(android.app.RemoteInput remoteInput) {
            return remoteInput.getExtras();
        }

        @DoNotInline
        static String e(Notification notification) {
            return notification.getGroup();
        }

        @DoNotInline
        static CharSequence f(android.app.RemoteInput remoteInput) {
            return remoteInput.getLabel();
        }

        @DoNotInline
        static android.app.RemoteInput[] g(Notification.Action action) {
            return action.getRemoteInputs();
        }

        @DoNotInline
        static String h(android.app.RemoteInput remoteInput) {
            return remoteInput.getResultKey();
        }

        @DoNotInline
        static String i(Notification notification) {
            return notification.getSortKey();
        }
    }

    @RequiresApi(23)
    static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        static Icon a(Notification.Action action) {
            return action.getIcon();
        }
    }

    @RequiresApi(24)
    static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        static boolean a(Notification.Action action) {
            return action.getAllowGeneratedReplies();
        }
    }

    @RequiresApi(26)
    static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        static int a(Notification notification) {
            return notification.getBadgeIconType();
        }

        @DoNotInline
        static String b(Notification notification) {
            return notification.getChannelId();
        }

        @DoNotInline
        static int c(Notification notification) {
            return notification.getGroupAlertBehavior();
        }

        @DoNotInline
        static CharSequence d(Notification notification) {
            return notification.getSettingsText();
        }

        @DoNotInline
        static String e(Notification notification) {
            return notification.getShortcutId();
        }

        @DoNotInline
        static long f(Notification notification) {
            return notification.getTimeoutAfter();
        }
    }

    @RequiresApi(28)
    static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static int a(Notification.Action action) {
            return action.getSemanticAction();
        }
    }

    @RequiresApi(29)
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static boolean a(Notification notification) {
            return notification.getAllowSystemGeneratedContextualActions();
        }

        @DoNotInline
        static Notification.BubbleMetadata b(Notification notification) {
            return notification.getBubbleMetadata();
        }

        @DoNotInline
        static int c(android.app.RemoteInput remoteInput) {
            return remoteInput.getEditChoicesBeforeSending();
        }

        @DoNotInline
        static LocusId d(Notification notification) {
            return notification.getLocusId();
        }

        @DoNotInline
        static boolean e(Notification.Action action) {
            return action.isContextual();
        }
    }

    @RequiresApi(31)
    static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        static boolean a(Notification.Action action) {
            return action.isAuthenticationRequired();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface BadgeIconType {
    }

    public static class BigPictureStyle extends Style {
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$BigPictureStyle";
        private IconCompat mBigLargeIcon;
        private boolean mBigLargeIconSet;
        private CharSequence mPictureContentDescription;
        private IconCompat mPictureIcon;
        private boolean mShowBigPictureWhenCollapsed;

        @RequiresApi(23)
        private static class Api23Impl {
            private Api23Impl() {
            }

            @RequiresApi(23)
            static void a(Notification.BigPictureStyle bigPictureStyle, Icon icon) {
                bigPictureStyle.bigLargeIcon(icon);
            }
        }

        @RequiresApi(31)
        private static class Api31Impl {
            private Api31Impl() {
            }

            @RequiresApi(31)
            static void a(Notification.BigPictureStyle bigPictureStyle, Icon icon) {
                bigPictureStyle.bigPicture(icon);
            }

            @RequiresApi(31)
            static void b(Notification.BigPictureStyle bigPictureStyle, CharSequence charSequence) {
                bigPictureStyle.setContentDescription(charSequence);
            }

            @RequiresApi(31)
            static void c(Notification.BigPictureStyle bigPictureStyle, boolean z2) {
                bigPictureStyle.showBigPictureWhenCollapsed(z2);
            }
        }

        public BigPictureStyle() {
        }

        @Nullable
        private static IconCompat asIconCompat(@Nullable Parcelable parcelable) {
            if (parcelable == null) {
                return null;
            }
            if (parcelable instanceof Icon) {
                return IconCompat.createFromIcon((Icon) parcelable);
            }
            if (parcelable instanceof Bitmap) {
                return IconCompat.createWithBitmap((Bitmap) parcelable);
            }
            return null;
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static IconCompat getPictureIcon(@Nullable Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            Parcelable parcelable = bundle.getParcelable(NotificationCompat.EXTRA_PICTURE);
            return parcelable != null ? asIconCompat(parcelable) : asIconCompat(bundle.getParcelable(NotificationCompat.EXTRA_PICTURE_ICON));
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void a(Bundle bundle) {
            super.a(bundle);
            bundle.remove(NotificationCompat.EXTRA_LARGE_ICON_BIG);
            bundle.remove(NotificationCompat.EXTRA_PICTURE);
            bundle.remove(NotificationCompat.EXTRA_PICTURE_ICON);
            bundle.remove(NotificationCompat.EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            Notification.BigPictureStyle bigContentTitle = new Notification.BigPictureStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.f3461b);
            IconCompat iconCompat = this.mPictureIcon;
            if (iconCompat != null) {
                if (Build.VERSION.SDK_INT >= 31) {
                    Api31Impl.a(bigContentTitle, this.mPictureIcon.toIcon(notificationBuilderWithBuilderAccessor instanceof NotificationCompatBuilder ? ((NotificationCompatBuilder) notificationBuilderWithBuilderAccessor).b() : null));
                } else if (iconCompat.getType() == 1) {
                    bigContentTitle = bigContentTitle.bigPicture(this.mPictureIcon.getBitmap());
                }
            }
            if (this.mBigLargeIconSet) {
                if (this.mBigLargeIcon == null) {
                    bigContentTitle.bigLargeIcon((Bitmap) null);
                } else {
                    Api23Impl.a(bigContentTitle, this.mBigLargeIcon.toIcon(notificationBuilderWithBuilderAccessor instanceof NotificationCompatBuilder ? ((NotificationCompatBuilder) notificationBuilderWithBuilderAccessor).b() : null));
                }
            }
            if (this.f3463d) {
                bigContentTitle.setSummaryText(this.f3462c);
            }
            if (Build.VERSION.SDK_INT >= 31) {
                Api31Impl.c(bigContentTitle, this.mShowBigPictureWhenCollapsed);
                Api31Impl.b(bigContentTitle, this.mPictureContentDescription);
            }
        }

        @NonNull
        public BigPictureStyle bigLargeIcon(@Nullable Bitmap bitmap) {
            this.mBigLargeIcon = bitmap == null ? null : IconCompat.createWithBitmap(bitmap);
            this.mBigLargeIconSet = true;
            return this;
        }

        @NonNull
        public BigPictureStyle bigPicture(@Nullable Bitmap bitmap) {
            this.mPictureIcon = bitmap == null ? null : IconCompat.createWithBitmap(bitmap);
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected String f() {
            return TEMPLATE_CLASS_NAME;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void g(Bundle bundle) {
            super.g(bundle);
            if (bundle.containsKey(NotificationCompat.EXTRA_LARGE_ICON_BIG)) {
                this.mBigLargeIcon = asIconCompat(bundle.getParcelable(NotificationCompat.EXTRA_LARGE_ICON_BIG));
                this.mBigLargeIconSet = true;
            }
            this.mPictureIcon = getPictureIcon(bundle);
            this.mShowBigPictureWhenCollapsed = bundle.getBoolean(NotificationCompat.EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED);
        }

        @NonNull
        public BigPictureStyle setBigContentTitle(@Nullable CharSequence charSequence) {
            this.f3461b = Builder.a(charSequence);
            return this;
        }

        @NonNull
        @RequiresApi(31)
        public BigPictureStyle setContentDescription(@Nullable CharSequence charSequence) {
            this.mPictureContentDescription = charSequence;
            return this;
        }

        @NonNull
        public BigPictureStyle setSummaryText(@Nullable CharSequence charSequence) {
            this.f3462c = Builder.a(charSequence);
            this.f3463d = true;
            return this;
        }

        @NonNull
        @RequiresApi(31)
        public BigPictureStyle showBigPictureWhenCollapsed(boolean z2) {
            this.mShowBigPictureWhenCollapsed = z2;
            return this;
        }

        public BigPictureStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }

        @NonNull
        @RequiresApi(31)
        public BigPictureStyle bigPicture(@Nullable Icon icon) {
            this.mPictureIcon = IconCompat.createFromIcon(icon);
            return this;
        }

        @NonNull
        @RequiresApi(23)
        public BigPictureStyle bigLargeIcon(@Nullable Icon icon) {
            this.mBigLargeIcon = icon == null ? null : IconCompat.createFromIcon(icon);
            this.mBigLargeIconSet = true;
            return this;
        }
    }

    public static class BigTextStyle extends Style {
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$BigTextStyle";
        private CharSequence mBigText;

        public BigTextStyle() {
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void a(Bundle bundle) {
            super.a(bundle);
            bundle.remove(NotificationCompat.EXTRA_BIG_TEXT);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void addCompatExtras(@NonNull Bundle bundle) {
            super.addCompatExtras(bundle);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            Notification.BigTextStyle bigTextStyleBigText = new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.f3461b).bigText(this.mBigText);
            if (this.f3463d) {
                bigTextStyleBigText.setSummaryText(this.f3462c);
            }
        }

        @NonNull
        public BigTextStyle bigText(@Nullable CharSequence charSequence) {
            this.mBigText = Builder.a(charSequence);
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected String f() {
            return TEMPLATE_CLASS_NAME;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void g(Bundle bundle) {
            super.g(bundle);
            this.mBigText = bundle.getCharSequence(NotificationCompat.EXTRA_BIG_TEXT);
        }

        @NonNull
        public BigTextStyle setBigContentTitle(@Nullable CharSequence charSequence) {
            this.f3461b = Builder.a(charSequence);
            return this;
        }

        @NonNull
        public BigTextStyle setSummaryText(@Nullable CharSequence charSequence) {
            this.f3462c = Builder.a(charSequence);
            this.f3463d = true;
            return this;
        }

        public BigTextStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }
    }

    public static final class BubbleMetadata {
        private static final int FLAG_AUTO_EXPAND_BUBBLE = 1;
        private static final int FLAG_SUPPRESS_NOTIFICATION = 2;
        private PendingIntent mDeleteIntent;
        private int mDesiredHeight;

        @DimenRes
        private int mDesiredHeightResId;
        private int mFlags;
        private IconCompat mIcon;
        private PendingIntent mPendingIntent;
        private String mShortcutId;

        @RequiresApi(29)
        private static class Api29Impl {
            private Api29Impl() {
            }

            @Nullable
            @RequiresApi(29)
            static BubbleMetadata a(@Nullable Notification.BubbleMetadata bubbleMetadata) {
                if (bubbleMetadata == null || bubbleMetadata.getIntent() == null) {
                    return null;
                }
                Builder suppressNotification = new Builder(bubbleMetadata.getIntent(), IconCompat.createFromIcon(bubbleMetadata.getIcon())).setAutoExpandBubble(bubbleMetadata.getAutoExpandBubble()).setDeleteIntent(bubbleMetadata.getDeleteIntent()).setSuppressNotification(bubbleMetadata.isNotificationSuppressed());
                if (bubbleMetadata.getDesiredHeight() != 0) {
                    suppressNotification.setDesiredHeight(bubbleMetadata.getDesiredHeight());
                }
                if (bubbleMetadata.getDesiredHeightResId() != 0) {
                    suppressNotification.setDesiredHeightResId(bubbleMetadata.getDesiredHeightResId());
                }
                return suppressNotification.build();
            }

            @Nullable
            @RequiresApi(29)
            static Notification.BubbleMetadata b(@Nullable BubbleMetadata bubbleMetadata) {
                if (bubbleMetadata == null || bubbleMetadata.getIntent() == null) {
                    return null;
                }
                Notification.BubbleMetadata.Builder suppressNotification = new Notification.BubbleMetadata.Builder().setIcon(bubbleMetadata.getIcon().toIcon()).setIntent(bubbleMetadata.getIntent()).setDeleteIntent(bubbleMetadata.getDeleteIntent()).setAutoExpandBubble(bubbleMetadata.getAutoExpandBubble()).setSuppressNotification(bubbleMetadata.isNotificationSuppressed());
                if (bubbleMetadata.getDesiredHeight() != 0) {
                    suppressNotification.setDesiredHeight(bubbleMetadata.getDesiredHeight());
                }
                if (bubbleMetadata.getDesiredHeightResId() != 0) {
                    suppressNotification.setDesiredHeightResId(bubbleMetadata.getDesiredHeightResId());
                }
                return suppressNotification.build();
            }
        }

        @RequiresApi(30)
        private static class Api30Impl {
            private Api30Impl() {
            }

            @Nullable
            @RequiresApi(30)
            static BubbleMetadata a(@Nullable Notification.BubbleMetadata bubbleMetadata) {
                if (bubbleMetadata == null) {
                    return null;
                }
                Builder builder = bubbleMetadata.getShortcutId() != null ? new Builder(bubbleMetadata.getShortcutId()) : new Builder(bubbleMetadata.getIntent(), IconCompat.createFromIcon(bubbleMetadata.getIcon()));
                builder.setAutoExpandBubble(bubbleMetadata.getAutoExpandBubble()).setDeleteIntent(bubbleMetadata.getDeleteIntent()).setSuppressNotification(bubbleMetadata.isNotificationSuppressed());
                if (bubbleMetadata.getDesiredHeight() != 0) {
                    builder.setDesiredHeight(bubbleMetadata.getDesiredHeight());
                }
                if (bubbleMetadata.getDesiredHeightResId() != 0) {
                    builder.setDesiredHeightResId(bubbleMetadata.getDesiredHeightResId());
                }
                return builder.build();
            }

            @Nullable
            @RequiresApi(30)
            static Notification.BubbleMetadata b(@Nullable BubbleMetadata bubbleMetadata) {
                if (bubbleMetadata == null) {
                    return null;
                }
                Notification.BubbleMetadata.Builder builder = bubbleMetadata.getShortcutId() != null ? new Notification.BubbleMetadata.Builder(bubbleMetadata.getShortcutId()) : new Notification.BubbleMetadata.Builder(bubbleMetadata.getIntent(), bubbleMetadata.getIcon().toIcon());
                builder.setDeleteIntent(bubbleMetadata.getDeleteIntent()).setAutoExpandBubble(bubbleMetadata.getAutoExpandBubble()).setSuppressNotification(bubbleMetadata.isNotificationSuppressed());
                if (bubbleMetadata.getDesiredHeight() != 0) {
                    builder.setDesiredHeight(bubbleMetadata.getDesiredHeight());
                }
                if (bubbleMetadata.getDesiredHeightResId() != 0) {
                    builder.setDesiredHeightResId(bubbleMetadata.getDesiredHeightResId());
                }
                return builder.build();
            }
        }

        public static final class Builder {
            private PendingIntent mDeleteIntent;
            private int mDesiredHeight;

            @DimenRes
            private int mDesiredHeightResId;
            private int mFlags;
            private IconCompat mIcon;
            private PendingIntent mPendingIntent;
            private String mShortcutId;

            @Deprecated
            public Builder() {
            }

            @NonNull
            private Builder setFlag(int i2, boolean z2) {
                if (z2) {
                    this.mFlags = i2 | this.mFlags;
                } else {
                    this.mFlags = (~i2) & this.mFlags;
                }
                return this;
            }

            @NonNull
            public BubbleMetadata build() {
                String str = this.mShortcutId;
                if (str == null && this.mPendingIntent == null) {
                    throw new NullPointerException("Must supply pending intent or shortcut to bubble");
                }
                if (str == null && this.mIcon == null) {
                    throw new NullPointerException("Must supply an icon or shortcut for the bubble");
                }
                BubbleMetadata bubbleMetadata = new BubbleMetadata(this.mPendingIntent, this.mDeleteIntent, this.mIcon, this.mDesiredHeight, this.mDesiredHeightResId, this.mFlags, str);
                bubbleMetadata.setFlags(this.mFlags);
                return bubbleMetadata;
            }

            @NonNull
            public Builder setAutoExpandBubble(boolean z2) {
                setFlag(1, z2);
                return this;
            }

            @NonNull
            public Builder setDeleteIntent(@Nullable PendingIntent pendingIntent) {
                this.mDeleteIntent = pendingIntent;
                return this;
            }

            @NonNull
            public Builder setDesiredHeight(@Dimension(unit = 0) int i2) {
                this.mDesiredHeight = Math.max(i2, 0);
                this.mDesiredHeightResId = 0;
                return this;
            }

            @NonNull
            public Builder setDesiredHeightResId(@DimenRes int i2) {
                this.mDesiredHeightResId = i2;
                this.mDesiredHeight = 0;
                return this;
            }

            @NonNull
            public Builder setIcon(@NonNull IconCompat iconCompat) {
                if (this.mShortcutId != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set an Icon. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                }
                if (iconCompat == null) {
                    throw new NullPointerException("Bubbles require non-null icon");
                }
                this.mIcon = iconCompat;
                return this;
            }

            @NonNull
            public Builder setIntent(@NonNull PendingIntent pendingIntent) {
                if (this.mShortcutId != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set a PendingIntent. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                }
                if (pendingIntent == null) {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                }
                this.mPendingIntent = pendingIntent;
                return this;
            }

            @NonNull
            public Builder setSuppressNotification(boolean z2) {
                setFlag(2, z2);
                return this;
            }

            @RequiresApi(30)
            public Builder(@NonNull String str) {
                if (TextUtils.isEmpty(str)) {
                    throw new NullPointerException("Bubble requires a non-null shortcut id");
                }
                this.mShortcutId = str;
            }

            public Builder(@NonNull PendingIntent pendingIntent, @NonNull IconCompat iconCompat) {
                if (pendingIntent == null) {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                }
                if (iconCompat != null) {
                    this.mPendingIntent = pendingIntent;
                    this.mIcon = iconCompat;
                    return;
                }
                throw new NullPointerException("Bubbles require non-null icon");
            }
        }

        @Nullable
        public static BubbleMetadata fromPlatform(@Nullable Notification.BubbleMetadata bubbleMetadata) {
            if (bubbleMetadata == null) {
                return null;
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 30) {
                return Api30Impl.a(bubbleMetadata);
            }
            if (i2 == 29) {
                return Api29Impl.a(bubbleMetadata);
            }
            return null;
        }

        @Nullable
        public static Notification.BubbleMetadata toPlatform(@Nullable BubbleMetadata bubbleMetadata) {
            if (bubbleMetadata == null) {
                return null;
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 30) {
                return Api30Impl.b(bubbleMetadata);
            }
            if (i2 == 29) {
                return Api29Impl.b(bubbleMetadata);
            }
            return null;
        }

        public boolean getAutoExpandBubble() {
            return (this.mFlags & 1) != 0;
        }

        @Nullable
        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        @Dimension(unit = 0)
        public int getDesiredHeight() {
            return this.mDesiredHeight;
        }

        @DimenRes
        public int getDesiredHeightResId() {
            return this.mDesiredHeightResId;
        }

        @Nullable
        @SuppressLint({"InvalidNullConversion"})
        public IconCompat getIcon() {
            return this.mIcon;
        }

        @Nullable
        @SuppressLint({"InvalidNullConversion"})
        public PendingIntent getIntent() {
            return this.mPendingIntent;
        }

        @Nullable
        public String getShortcutId() {
            return this.mShortcutId;
        }

        public boolean isNotificationSuppressed() {
            return (this.mFlags & 2) != 0;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void setFlags(int i2) {
            this.mFlags = i2;
        }

        private BubbleMetadata(@Nullable PendingIntent pendingIntent, @Nullable PendingIntent pendingIntent2, @Nullable IconCompat iconCompat, int i2, @DimenRes int i3, int i4, @Nullable String str) {
            this.mPendingIntent = pendingIntent;
            this.mIcon = iconCompat;
            this.mDesiredHeight = i2;
            this.mDesiredHeightResId = i3;
            this.mDeleteIntent = pendingIntent2;
            this.mFlags = i4;
            this.mShortcutId = str;
        }
    }

    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        String A;
        Bundle B;
        int C;
        int D;
        Notification E;
        RemoteViews F;
        RemoteViews G;
        RemoteViews H;
        String I;
        int J;
        String K;
        LocusIdCompat L;
        long M;
        int N;
        int O;
        boolean P;
        BubbleMetadata Q;
        Notification R;
        boolean S;
        Object T;

        /* renamed from: a, reason: collision with root package name */
        ArrayList f3434a;

        /* renamed from: b, reason: collision with root package name */
        CharSequence f3435b;

        /* renamed from: c, reason: collision with root package name */
        CharSequence f3436c;

        /* renamed from: d, reason: collision with root package name */
        PendingIntent f3437d;

        /* renamed from: e, reason: collision with root package name */
        PendingIntent f3438e;

        /* renamed from: f, reason: collision with root package name */
        RemoteViews f3439f;

        /* renamed from: g, reason: collision with root package name */
        IconCompat f3440g;

        /* renamed from: h, reason: collision with root package name */
        CharSequence f3441h;

        /* renamed from: i, reason: collision with root package name */
        int f3442i;

        /* renamed from: j, reason: collision with root package name */
        int f3443j;

        /* renamed from: k, reason: collision with root package name */
        boolean f3444k;

        /* renamed from: l, reason: collision with root package name */
        boolean f3445l;

        /* renamed from: m, reason: collision with root package name */
        boolean f3446m;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public ArrayList<Action> mActions;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Context mContext;

        @Deprecated
        public ArrayList<String> mPeople;

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public ArrayList<Person> mPersonList;

        /* renamed from: n, reason: collision with root package name */
        Style f3447n;

        /* renamed from: o, reason: collision with root package name */
        CharSequence f3448o;

        /* renamed from: p, reason: collision with root package name */
        CharSequence f3449p;

        /* renamed from: q, reason: collision with root package name */
        CharSequence[] f3450q;

        /* renamed from: r, reason: collision with root package name */
        int f3451r;

        /* renamed from: s, reason: collision with root package name */
        int f3452s;

        /* renamed from: t, reason: collision with root package name */
        boolean f3453t;

        /* renamed from: u, reason: collision with root package name */
        String f3454u;

        /* renamed from: v, reason: collision with root package name */
        boolean f3455v;

        /* renamed from: w, reason: collision with root package name */
        String f3456w;

        /* renamed from: x, reason: collision with root package name */
        boolean f3457x;

        /* renamed from: y, reason: collision with root package name */
        boolean f3458y;

        /* renamed from: z, reason: collision with root package name */
        boolean f3459z;

        @RequiresApi(21)
        static class Api21Impl {
            private Api21Impl() {
            }

            @DoNotInline
            static AudioAttributes a(AudioAttributes.Builder builder) {
                return builder.build();
            }

            @DoNotInline
            static AudioAttributes.Builder b() {
                return new AudioAttributes.Builder();
            }

            @DoNotInline
            static AudioAttributes.Builder c(AudioAttributes.Builder builder, int i2) {
                return builder.setContentType(i2);
            }

            @DoNotInline
            static AudioAttributes.Builder d(AudioAttributes.Builder builder, int i2) {
                return builder.setLegacyStreamType(i2);
            }

            @DoNotInline
            static AudioAttributes.Builder e(AudioAttributes.Builder builder, int i2) {
                return builder.setUsage(i2);
            }
        }

        @RequiresApi(23)
        static class Api23Impl {
            private Api23Impl() {
            }

            @DoNotInline
            static Icon a(Notification notification) {
                return notification.getLargeIcon();
            }

            @DoNotInline
            static Icon b(Notification notification) {
                return notification.getSmallIcon();
            }
        }

        @RequiresApi(24)
        static class Api24Impl {
            private Api24Impl() {
            }

            @DoNotInline
            static RemoteViews a(Notification.Builder builder) {
                return builder.createHeadsUpContentView();
            }

            @DoNotInline
            static RemoteViews b(Notification.Builder builder) {
                return builder.createContentView();
            }

            @DoNotInline
            static RemoteViews c(Notification.Builder builder) {
                return builder.createHeadsUpContentView();
            }

            @DoNotInline
            static Notification.Builder d(Context context, Notification notification) {
                return Notification.Builder.recoverBuilder(context, notification);
            }
        }

        public Builder(@NonNull Context context, @NonNull Notification notification) {
            ArrayList parcelableArrayList;
            this(context, NotificationCompat.getChannelId(notification));
            Bundle bundle = notification.extras;
            Style styleExtractStyleFromNotification = Style.extractStyleFromNotification(notification);
            setContentTitle(NotificationCompat.getContentTitle(notification)).setContentText(NotificationCompat.getContentText(notification)).setContentInfo(NotificationCompat.getContentInfo(notification)).setSubText(NotificationCompat.getSubText(notification)).setSettingsText(NotificationCompat.getSettingsText(notification)).setStyle(styleExtractStyleFromNotification).setGroup(NotificationCompat.getGroup(notification)).setGroupSummary(NotificationCompat.isGroupSummary(notification)).setLocusId(NotificationCompat.getLocusId(notification)).setWhen(notification.when).setShowWhen(NotificationCompat.getShowWhen(notification)).setUsesChronometer(NotificationCompat.getUsesChronometer(notification)).setAutoCancel(NotificationCompat.getAutoCancel(notification)).setOnlyAlertOnce(NotificationCompat.getOnlyAlertOnce(notification)).setOngoing(NotificationCompat.getOngoing(notification)).setLocalOnly(NotificationCompat.getLocalOnly(notification)).setLargeIcon(notification.largeIcon).setBadgeIconType(NotificationCompat.getBadgeIconType(notification)).setCategory(NotificationCompat.getCategory(notification)).setBubbleMetadata(NotificationCompat.getBubbleMetadata(notification)).setNumber(notification.number).setTicker(notification.tickerText).setContentIntent(notification.contentIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(notification.fullScreenIntent, NotificationCompat.b(notification)).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setDefaults(notification.defaults).setPriority(notification.priority).setColor(NotificationCompat.getColor(notification)).setVisibility(NotificationCompat.getVisibility(notification)).setPublicVersion(NotificationCompat.getPublicVersion(notification)).setSortKey(NotificationCompat.getSortKey(notification)).setTimeoutAfter(NotificationCompat.getTimeoutAfter(notification)).setShortcutId(NotificationCompat.getShortcutId(notification)).setProgress(bundle.getInt(NotificationCompat.EXTRA_PROGRESS_MAX), bundle.getInt(NotificationCompat.EXTRA_PROGRESS), bundle.getBoolean(NotificationCompat.EXTRA_PROGRESS_INDETERMINATE)).setAllowSystemGeneratedContextualActions(NotificationCompat.getAllowSystemGeneratedContextualActions(notification)).setSmallIcon(notification.icon, notification.iconLevel).addExtras(getExtrasWithoutDuplicateData(notification, styleExtractStyleFromNotification));
            this.T = Api23Impl.b(notification);
            Icon iconA = Api23Impl.a(notification);
            if (iconA != null) {
                this.f3440g = IconCompat.createFromIcon(iconA);
            }
            Notification.Action[] actionArr = notification.actions;
            if (actionArr != null && actionArr.length != 0) {
                for (Notification.Action action : actionArr) {
                    addAction(Action.Builder.fromAndroidAction(action).build());
                }
            }
            List<Action> invisibleActions = NotificationCompat.getInvisibleActions(notification);
            if (!invisibleActions.isEmpty()) {
                Iterator<Action> it = invisibleActions.iterator();
                while (it.hasNext()) {
                    addInvisibleAction(it.next());
                }
            }
            String[] stringArray = notification.extras.getStringArray(NotificationCompat.EXTRA_PEOPLE);
            if (stringArray != null && stringArray.length != 0) {
                for (String str : stringArray) {
                    addPerson(str);
                }
            }
            if (Build.VERSION.SDK_INT >= 28 && (parcelableArrayList = notification.extras.getParcelableArrayList(NotificationCompat.EXTRA_PEOPLE_LIST)) != null && !parcelableArrayList.isEmpty()) {
                Iterator it2 = parcelableArrayList.iterator();
                while (it2.hasNext()) {
                    addPerson(Person.fromAndroidPerson(j.a(it2.next())));
                }
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 24 && bundle.containsKey(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN)) {
                setChronometerCountDown(bundle.getBoolean(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN));
            }
            if (i2 < 26 || !bundle.containsKey(NotificationCompat.EXTRA_COLORIZED)) {
                return;
            }
            setColorized(bundle.getBoolean(NotificationCompat.EXTRA_COLORIZED));
        }

        protected static CharSequence a(CharSequence charSequence) {
            return (charSequence != null && charSequence.length() > MAX_CHARSEQUENCE_LENGTH) ? charSequence.subSequence(0, MAX_CHARSEQUENCE_LENGTH) : charSequence;
        }

        @Nullable
        private static Bundle getExtrasWithoutDuplicateData(@NonNull Notification notification, @Nullable Style style) {
            if (notification.extras == null) {
                return null;
            }
            Bundle bundle = new Bundle(notification.extras);
            bundle.remove(NotificationCompat.EXTRA_TITLE);
            bundle.remove(NotificationCompat.EXTRA_TEXT);
            bundle.remove(NotificationCompat.EXTRA_INFO_TEXT);
            bundle.remove(NotificationCompat.EXTRA_SUB_TEXT);
            bundle.remove(NotificationCompat.EXTRA_CHANNEL_ID);
            bundle.remove(NotificationCompat.EXTRA_CHANNEL_GROUP_ID);
            bundle.remove(NotificationCompat.EXTRA_SHOW_WHEN);
            bundle.remove(NotificationCompat.EXTRA_PROGRESS);
            bundle.remove(NotificationCompat.EXTRA_PROGRESS_MAX);
            bundle.remove(NotificationCompat.EXTRA_PROGRESS_INDETERMINATE);
            bundle.remove(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN);
            bundle.remove(NotificationCompat.EXTRA_COLORIZED);
            bundle.remove(NotificationCompat.EXTRA_PEOPLE_LIST);
            bundle.remove(NotificationCompat.EXTRA_PEOPLE);
            bundle.remove(NotificationCompatExtras.EXTRA_SORT_KEY);
            bundle.remove(NotificationCompatExtras.EXTRA_GROUP_KEY);
            bundle.remove(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
            bundle.remove(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
            bundle.remove(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
            Bundle bundle2 = bundle.getBundle("android.car.EXTENSIONS");
            if (bundle2 != null) {
                Bundle bundle3 = new Bundle(bundle2);
                bundle3.remove("invisible_actions");
                bundle.putBundle("android.car.EXTENSIONS", bundle3);
            }
            if (style != null) {
                style.a(bundle);
            }
            return bundle;
        }

        private void setFlag(int i2, boolean z2) {
            if (z2) {
                Notification notification = this.R;
                notification.flags = i2 | notification.flags;
            } else {
                Notification notification2 = this.R;
                notification2.flags = (~i2) & notification2.flags;
            }
        }

        private boolean useExistingRemoteView() {
            Style style = this.f3447n;
            return style == null || !style.displayCustomViewInline();
        }

        @NonNull
        public Builder addAction(int i2, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
            this.mActions.add(new Action(i2, charSequence, pendingIntent));
            return this;
        }

        @NonNull
        public Builder addExtras(@Nullable Bundle bundle) {
            if (bundle != null) {
                Bundle bundle2 = this.B;
                if (bundle2 == null) {
                    this.B = new Bundle(bundle);
                } else {
                    bundle2.putAll(bundle);
                }
            }
            return this;
        }

        @NonNull
        @RequiresApi(21)
        public Builder addInvisibleAction(int i2, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
            this.f3434a.add(new Action(i2, charSequence, pendingIntent));
            return this;
        }

        @NonNull
        @Deprecated
        public Builder addPerson(@Nullable String str) {
            if (str != null && !str.isEmpty()) {
                this.mPeople.add(str);
            }
            return this;
        }

        @NonNull
        public Notification build() {
            return new NotificationCompatBuilder(this).build();
        }

        @NonNull
        public Builder clearActions() {
            this.mActions.clear();
            return this;
        }

        @NonNull
        public Builder clearInvisibleActions() {
            this.f3434a.clear();
            Bundle bundle = this.B.getBundle("android.car.EXTENSIONS");
            if (bundle != null) {
                Bundle bundle2 = new Bundle(bundle);
                bundle2.remove("invisible_actions");
                this.B.putBundle("android.car.EXTENSIONS", bundle2);
            }
            return this;
        }

        @NonNull
        public Builder clearPeople() {
            this.mPersonList.clear();
            this.mPeople.clear();
            return this;
        }

        @Nullable
        @SuppressLint({"BuilderSetStyle"})
        public RemoteViews createBigContentView() {
            RemoteViews remoteViewsMakeBigContentView;
            if (this.G != null && useExistingRemoteView()) {
                return this.G;
            }
            NotificationCompatBuilder notificationCompatBuilder = new NotificationCompatBuilder(this);
            Style style = this.f3447n;
            if (style != null && (remoteViewsMakeBigContentView = style.makeBigContentView(notificationCompatBuilder)) != null) {
                return remoteViewsMakeBigContentView;
            }
            Notification notificationBuild = notificationCompatBuilder.build();
            return Build.VERSION.SDK_INT >= 24 ? Api24Impl.a(Api24Impl.d(this.mContext, notificationBuild)) : notificationBuild.bigContentView;
        }

        @Nullable
        @SuppressLint({"BuilderSetStyle"})
        public RemoteViews createContentView() {
            RemoteViews remoteViewsMakeContentView;
            if (this.F != null && useExistingRemoteView()) {
                return this.F;
            }
            NotificationCompatBuilder notificationCompatBuilder = new NotificationCompatBuilder(this);
            Style style = this.f3447n;
            if (style != null && (remoteViewsMakeContentView = style.makeContentView(notificationCompatBuilder)) != null) {
                return remoteViewsMakeContentView;
            }
            Notification notificationBuild = notificationCompatBuilder.build();
            return Build.VERSION.SDK_INT >= 24 ? Api24Impl.b(Api24Impl.d(this.mContext, notificationBuild)) : notificationBuild.contentView;
        }

        @Nullable
        @SuppressLint({"BuilderSetStyle"})
        public RemoteViews createHeadsUpContentView() {
            RemoteViews remoteViewsMakeHeadsUpContentView;
            int i2 = Build.VERSION.SDK_INT;
            if (this.H != null && useExistingRemoteView()) {
                return this.H;
            }
            NotificationCompatBuilder notificationCompatBuilder = new NotificationCompatBuilder(this);
            Style style = this.f3447n;
            if (style != null && (remoteViewsMakeHeadsUpContentView = style.makeHeadsUpContentView(notificationCompatBuilder)) != null) {
                return remoteViewsMakeHeadsUpContentView;
            }
            Notification notificationBuild = notificationCompatBuilder.build();
            return i2 >= 24 ? Api24Impl.c(Api24Impl.d(this.mContext, notificationBuild)) : notificationBuild.headsUpContentView;
        }

        @NonNull
        public Builder extend(@NonNull Extender extender) {
            extender.extend(this);
            return this;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getBigContentView() {
            return this.G;
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public BubbleMetadata getBubbleMetadata() {
            return this.Q;
        }

        @ColorInt
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public int getColor() {
            return this.C;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getContentView() {
            return this.F;
        }

        @NonNull
        public Bundle getExtras() {
            if (this.B == null) {
                this.B = new Bundle();
            }
            return this.B;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public int getForegroundServiceBehavior() {
            return this.O;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getHeadsUpContentView() {
            return this.H;
        }

        @NonNull
        @Deprecated
        public Notification getNotification() {
            return build();
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public int getPriority() {
            return this.f3443j;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public long getWhenIfShowing() {
            if (this.f3444k) {
                return this.R.when;
            }
            return 0L;
        }

        @NonNull
        public Builder setAllowSystemGeneratedContextualActions(boolean z2) {
            this.P = z2;
            return this;
        }

        @NonNull
        public Builder setAutoCancel(boolean z2) {
            setFlag(16, z2);
            return this;
        }

        @NonNull
        public Builder setBadgeIconType(int i2) {
            this.J = i2;
            return this;
        }

        @NonNull
        public Builder setBubbleMetadata(@Nullable BubbleMetadata bubbleMetadata) {
            this.Q = bubbleMetadata;
            return this;
        }

        @NonNull
        public Builder setCategory(@Nullable String str) {
            this.A = str;
            return this;
        }

        @NonNull
        public Builder setChannelId(@NonNull String str) {
            this.I = str;
            return this;
        }

        @NonNull
        @RequiresApi(24)
        public Builder setChronometerCountDown(boolean z2) {
            this.f3446m = z2;
            getExtras().putBoolean(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN, z2);
            return this;
        }

        @NonNull
        public Builder setColor(@ColorInt int i2) {
            this.C = i2;
            return this;
        }

        @NonNull
        public Builder setColorized(boolean z2) {
            this.f3458y = z2;
            this.f3459z = true;
            return this;
        }

        @NonNull
        public Builder setContent(@Nullable RemoteViews remoteViews) {
            this.R.contentView = remoteViews;
            return this;
        }

        @NonNull
        public Builder setContentInfo(@Nullable CharSequence charSequence) {
            this.f3441h = a(charSequence);
            return this;
        }

        @NonNull
        public Builder setContentIntent(@Nullable PendingIntent pendingIntent) {
            this.f3437d = pendingIntent;
            return this;
        }

        @NonNull
        public Builder setContentText(@Nullable CharSequence charSequence) {
            this.f3436c = a(charSequence);
            return this;
        }

        @NonNull
        public Builder setContentTitle(@Nullable CharSequence charSequence) {
            this.f3435b = a(charSequence);
            return this;
        }

        @NonNull
        public Builder setCustomBigContentView(@Nullable RemoteViews remoteViews) {
            this.G = remoteViews;
            return this;
        }

        @NonNull
        public Builder setCustomContentView(@Nullable RemoteViews remoteViews) {
            this.F = remoteViews;
            return this;
        }

        @NonNull
        public Builder setCustomHeadsUpContentView(@Nullable RemoteViews remoteViews) {
            this.H = remoteViews;
            return this;
        }

        @NonNull
        public Builder setDefaults(int i2) {
            Notification notification = this.R;
            notification.defaults = i2;
            if ((i2 & 4) != 0) {
                notification.flags |= 1;
            }
            return this;
        }

        @NonNull
        public Builder setDeleteIntent(@Nullable PendingIntent pendingIntent) {
            this.R.deleteIntent = pendingIntent;
            return this;
        }

        @NonNull
        public Builder setExtras(@Nullable Bundle bundle) {
            this.B = bundle;
            return this;
        }

        @NonNull
        public Builder setForegroundServiceBehavior(int i2) {
            this.O = i2;
            return this;
        }

        @NonNull
        public Builder setFullScreenIntent(@Nullable PendingIntent pendingIntent, boolean z2) {
            this.f3438e = pendingIntent;
            setFlag(128, z2);
            return this;
        }

        @NonNull
        public Builder setGroup(@Nullable String str) {
            this.f3454u = str;
            return this;
        }

        @NonNull
        public Builder setGroupAlertBehavior(int i2) {
            this.N = i2;
            return this;
        }

        @NonNull
        public Builder setGroupSummary(boolean z2) {
            this.f3455v = z2;
            return this;
        }

        @NonNull
        public Builder setLargeIcon(@Nullable Bitmap bitmap) {
            this.f3440g = bitmap == null ? null : IconCompat.createWithBitmap(NotificationCompat.reduceLargeIconSize(this.mContext, bitmap));
            return this;
        }

        @NonNull
        public Builder setLights(@ColorInt int i2, int i3, int i4) {
            Notification notification = this.R;
            notification.ledARGB = i2;
            notification.ledOnMS = i3;
            notification.ledOffMS = i4;
            notification.flags = ((i3 == 0 || i4 == 0) ? 0 : 1) | (notification.flags & (-2));
            return this;
        }

        @NonNull
        public Builder setLocalOnly(boolean z2) {
            this.f3457x = z2;
            return this;
        }

        @NonNull
        public Builder setLocusId(@Nullable LocusIdCompat locusIdCompat) {
            this.L = locusIdCompat;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setNotificationSilent() {
            this.S = true;
            return this;
        }

        @NonNull
        public Builder setNumber(int i2) {
            this.f3442i = i2;
            return this;
        }

        @NonNull
        public Builder setOngoing(boolean z2) {
            setFlag(2, z2);
            return this;
        }

        @NonNull
        public Builder setOnlyAlertOnce(boolean z2) {
            setFlag(8, z2);
            return this;
        }

        @NonNull
        public Builder setPriority(int i2) {
            this.f3443j = i2;
            return this;
        }

        @NonNull
        public Builder setProgress(int i2, int i3, boolean z2) {
            this.f3451r = i2;
            this.f3452s = i3;
            this.f3453t = z2;
            return this;
        }

        @NonNull
        public Builder setPublicVersion(@Nullable Notification notification) {
            this.E = notification;
            return this;
        }

        @NonNull
        public Builder setRemoteInputHistory(@Nullable CharSequence[] charSequenceArr) {
            this.f3450q = charSequenceArr;
            return this;
        }

        @NonNull
        public Builder setSettingsText(@Nullable CharSequence charSequence) {
            this.f3449p = a(charSequence);
            return this;
        }

        @NonNull
        public Builder setShortcutId(@Nullable String str) {
            this.K = str;
            return this;
        }

        @NonNull
        public Builder setShortcutInfo(@Nullable ShortcutInfoCompat shortcutInfoCompat) {
            if (shortcutInfoCompat == null) {
                return this;
            }
            this.K = shortcutInfoCompat.getId();
            if (this.L == null) {
                if (shortcutInfoCompat.getLocusId() != null) {
                    this.L = shortcutInfoCompat.getLocusId();
                } else if (shortcutInfoCompat.getId() != null) {
                    this.L = new LocusIdCompat(shortcutInfoCompat.getId());
                }
            }
            if (this.f3435b == null) {
                setContentTitle(shortcutInfoCompat.getShortLabel());
            }
            return this;
        }

        @NonNull
        public Builder setShowWhen(boolean z2) {
            this.f3444k = z2;
            return this;
        }

        @NonNull
        public Builder setSilent(boolean z2) {
            this.S = z2;
            return this;
        }

        @NonNull
        @RequiresApi(23)
        public Builder setSmallIcon(@NonNull IconCompat iconCompat) {
            this.T = iconCompat.toIcon(this.mContext);
            return this;
        }

        @NonNull
        public Builder setSortKey(@Nullable String str) {
            this.f3456w = str;
            return this;
        }

        @NonNull
        public Builder setSound(@Nullable Uri uri) {
            Notification notification = this.R;
            notification.sound = uri;
            notification.audioStreamType = -1;
            AudioAttributes.Builder builderE = Api21Impl.e(Api21Impl.c(Api21Impl.b(), 4), 5);
            this.R.audioAttributes = Api21Impl.a(builderE);
            return this;
        }

        @NonNull
        public Builder setStyle(@Nullable Style style) {
            if (this.f3447n != style) {
                this.f3447n = style;
                if (style != null) {
                    style.setBuilder(this);
                }
            }
            return this;
        }

        @NonNull
        public Builder setSubText(@Nullable CharSequence charSequence) {
            this.f3448o = a(charSequence);
            return this;
        }

        @NonNull
        public Builder setTicker(@Nullable CharSequence charSequence) {
            this.R.tickerText = a(charSequence);
            return this;
        }

        @NonNull
        public Builder setTimeoutAfter(long j2) {
            this.M = j2;
            return this;
        }

        @NonNull
        public Builder setUsesChronometer(boolean z2) {
            this.f3445l = z2;
            return this;
        }

        @NonNull
        public Builder setVibrate(@Nullable long[] jArr) {
            this.R.vibrate = jArr;
            return this;
        }

        @NonNull
        public Builder setVisibility(int i2) {
            this.D = i2;
            return this;
        }

        @NonNull
        public Builder setWhen(long j2) {
            this.R.when = j2;
            return this;
        }

        @NonNull
        public Builder addAction(@Nullable Action action) {
            if (action != null) {
                this.mActions.add(action);
            }
            return this;
        }

        @NonNull
        @RequiresApi(21)
        public Builder addInvisibleAction(@Nullable Action action) {
            if (action != null) {
                this.f3434a.add(action);
            }
            return this;
        }

        @NonNull
        public Builder setSmallIcon(int i2) {
            this.R.icon = i2;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setTicker(@Nullable CharSequence charSequence, @Nullable RemoteViews remoteViews) {
            this.R.tickerText = a(charSequence);
            this.f3439f = remoteViews;
            return this;
        }

        @NonNull
        public Builder addPerson(@Nullable Person person) {
            if (person != null) {
                this.mPersonList.add(person);
            }
            return this;
        }

        @NonNull
        public Builder setSmallIcon(int i2, int i3) {
            Notification notification = this.R;
            notification.icon = i2;
            notification.iconLevel = i3;
            return this;
        }

        @NonNull
        @RequiresApi(23)
        public Builder setLargeIcon(@Nullable Icon icon) {
            this.f3440g = icon == null ? null : IconCompat.createFromIcon(icon);
            return this;
        }

        @NonNull
        public Builder setSound(@Nullable Uri uri, int i2) {
            Notification notification = this.R;
            notification.sound = uri;
            notification.audioStreamType = i2;
            AudioAttributes.Builder builderD = Api21Impl.d(Api21Impl.c(Api21Impl.b(), 4), i2);
            this.R.audioAttributes = Api21Impl.a(builderD);
            return this;
        }

        public Builder(@NonNull Context context, @NonNull String str) {
            this.mActions = new ArrayList<>();
            this.mPersonList = new ArrayList<>();
            this.f3434a = new ArrayList();
            this.f3444k = true;
            this.f3457x = false;
            this.C = 0;
            this.D = 0;
            this.J = 0;
            this.N = 0;
            this.O = 0;
            Notification notification = new Notification();
            this.R = notification;
            this.mContext = context;
            this.I = str;
            notification.when = System.currentTimeMillis();
            this.R.audioStreamType = -1;
            this.f3443j = 0;
            this.mPeople = new ArrayList<>();
            this.P = true;
        }

        @Deprecated
        public Builder(@NonNull Context context) {
            this(context, (String) null);
        }
    }

    public static class CallStyle extends Style {
        public static final int CALL_TYPE_INCOMING = 1;
        public static final int CALL_TYPE_ONGOING = 2;
        public static final int CALL_TYPE_SCREENING = 3;
        public static final int CALL_TYPE_UNKNOWN = 0;
        private static final String KEY_ACTION_PRIORITY = "key_action_priority";
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$CallStyle";
        private Integer mAnswerButtonColor;
        private PendingIntent mAnswerIntent;
        private int mCallType;
        private Integer mDeclineButtonColor;
        private PendingIntent mDeclineIntent;
        private PendingIntent mHangUpIntent;
        private boolean mIsVideo;
        private Person mPerson;
        private IconCompat mVerificationIcon;
        private CharSequence mVerificationText;

        @RequiresApi(20)
        static class Api20Impl {
            private Api20Impl() {
            }

            @DoNotInline
            static Notification.Action.Builder a(Notification.Action.Builder builder, Bundle bundle) {
                return builder.addExtras(bundle);
            }

            @DoNotInline
            static Notification.Action.Builder b(Notification.Action.Builder builder, android.app.RemoteInput remoteInput) {
                return builder.addRemoteInput(remoteInput);
            }

            @DoNotInline
            static Notification.Action c(Notification.Action.Builder builder) {
                return builder.build();
            }

            @DoNotInline
            static Notification.Action.Builder d(int i2, CharSequence charSequence, PendingIntent pendingIntent) {
                return new Notification.Action.Builder(i2, charSequence, pendingIntent);
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
        }

        @RequiresApi(23)
        static class Api23Impl {
            private Api23Impl() {
            }

            @DoNotInline
            static Parcelable a(Icon icon) {
                return icon;
            }

            @DoNotInline
            static Notification.Action.Builder b(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
                return new Notification.Action.Builder(icon, charSequence, pendingIntent);
            }

            @DoNotInline
            static void c(Notification.Builder builder, Icon icon) {
                builder.setLargeIcon(icon);
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
            static Parcelable b(android.app.Person person) {
                return person;
            }
        }

        @RequiresApi(31)
        static class Api31Impl {
            private Api31Impl() {
            }

            @DoNotInline
            static Notification.CallStyle a(@NonNull android.app.Person person, @NonNull PendingIntent pendingIntent, @NonNull PendingIntent pendingIntent2) {
                return Notification.CallStyle.forIncomingCall(person, pendingIntent, pendingIntent2);
            }

            @DoNotInline
            static Notification.CallStyle b(@NonNull android.app.Person person, @NonNull PendingIntent pendingIntent) {
                return Notification.CallStyle.forOngoingCall(person, pendingIntent);
            }

            @DoNotInline
            static Notification.CallStyle c(@NonNull android.app.Person person, @NonNull PendingIntent pendingIntent, @NonNull PendingIntent pendingIntent2) {
                return Notification.CallStyle.forScreeningCall(person, pendingIntent, pendingIntent2);
            }

            @DoNotInline
            static Notification.CallStyle d(Notification.CallStyle callStyle, @ColorInt int i2) {
                return callStyle.setAnswerButtonColorHint(i2);
            }

            @DoNotInline
            static Notification.Action.Builder e(Notification.Action.Builder builder, boolean z2) {
                return builder.setAuthenticationRequired(z2);
            }

            @DoNotInline
            static Notification.CallStyle f(Notification.CallStyle callStyle, @ColorInt int i2) {
                return callStyle.setDeclineButtonColorHint(i2);
            }

            @DoNotInline
            static Notification.CallStyle g(Notification.CallStyle callStyle, boolean z2) {
                return callStyle.setIsVideo(z2);
            }

            @DoNotInline
            static Notification.CallStyle h(Notification.CallStyle callStyle, @Nullable Icon icon) {
                return callStyle.setVerificationIcon(icon);
            }

            @DoNotInline
            static Notification.CallStyle i(Notification.CallStyle callStyle, @Nullable CharSequence charSequence) {
                return callStyle.setVerificationText(charSequence);
            }
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public @interface CallType {
        }

        public CallStyle() {
        }

        @NonNull
        public static CallStyle forIncomingCall(@NonNull Person person, @NonNull PendingIntent pendingIntent, @NonNull PendingIntent pendingIntent2) {
            Objects.requireNonNull(pendingIntent, "declineIntent is required");
            Objects.requireNonNull(pendingIntent2, "answerIntent is required");
            return new CallStyle(1, person, null, pendingIntent, pendingIntent2);
        }

        @NonNull
        public static CallStyle forOngoingCall(@NonNull Person person, @NonNull PendingIntent pendingIntent) {
            Objects.requireNonNull(pendingIntent, "hangUpIntent is required");
            return new CallStyle(2, person, pendingIntent, null, null);
        }

        @NonNull
        public static CallStyle forScreeningCall(@NonNull Person person, @NonNull PendingIntent pendingIntent, @NonNull PendingIntent pendingIntent2) {
            Objects.requireNonNull(pendingIntent, "hangUpIntent is required");
            Objects.requireNonNull(pendingIntent2, "answerIntent is required");
            return new CallStyle(3, person, pendingIntent, null, pendingIntent2);
        }

        @Nullable
        private String getDefaultText() {
            int i2 = this.mCallType;
            if (i2 == 1) {
                return this.f3460a.mContext.getResources().getString(R.string.call_notification_incoming_text);
            }
            if (i2 == 2) {
                return this.f3460a.mContext.getResources().getString(R.string.call_notification_ongoing_text);
            }
            if (i2 != 3) {
                return null;
            }
            return this.f3460a.mContext.getResources().getString(R.string.call_notification_screening_text);
        }

        private boolean isActionAddedByCallStyle(Action action) {
            return action != null && action.getExtras().getBoolean(KEY_ACTION_PRIORITY);
        }

        @NonNull
        @RequiresApi(20)
        private Action makeAction(int i2, int i3, Integer num, int i4, PendingIntent pendingIntent) {
            if (num == null) {
                num = Integer.valueOf(ContextCompat.getColor(this.f3460a.mContext, i4));
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append((CharSequence) this.f3460a.mContext.getResources().getString(i3));
            spannableStringBuilder.setSpan(new ForegroundColorSpan(num.intValue()), 0, spannableStringBuilder.length(), 18);
            Action actionBuild = new Action.Builder(IconCompat.createWithResource(this.f3460a.mContext, i2), spannableStringBuilder, pendingIntent).build();
            actionBuild.getExtras().putBoolean(KEY_ACTION_PRIORITY, true);
            return actionBuild;
        }

        @Nullable
        @RequiresApi(20)
        private Action makeAnswerAction() {
            int i2 = R.drawable.ic_call_answer_video;
            int i3 = R.drawable.ic_call_answer;
            PendingIntent pendingIntent = this.mAnswerIntent;
            if (pendingIntent == null) {
                return null;
            }
            boolean z2 = this.mIsVideo;
            return makeAction(z2 ? i2 : i3, z2 ? R.string.call_notification_answer_video_action : R.string.call_notification_answer_action, this.mAnswerButtonColor, R.color.call_notification_answer_color, pendingIntent);
        }

        @NonNull
        @RequiresApi(20)
        private Action makeNegativeAction() {
            int i2 = R.drawable.ic_call_decline;
            PendingIntent pendingIntent = this.mDeclineIntent;
            return pendingIntent == null ? makeAction(i2, R.string.call_notification_hang_up_action, this.mDeclineButtonColor, R.color.call_notification_decline_color, this.mHangUpIntent) : makeAction(i2, R.string.call_notification_decline_action, this.mDeclineButtonColor, R.color.call_notification_decline_color, pendingIntent);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void addCompatExtras(@NonNull Bundle bundle) {
            super.addCompatExtras(bundle);
            bundle.putInt(NotificationCompat.EXTRA_CALL_TYPE, this.mCallType);
            bundle.putBoolean(NotificationCompat.EXTRA_CALL_IS_VIDEO, this.mIsVideo);
            Person person = this.mPerson;
            if (person != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    bundle.putParcelable(NotificationCompat.EXTRA_CALL_PERSON, Api28Impl.b(person.toAndroidPerson()));
                } else {
                    bundle.putParcelable(NotificationCompat.EXTRA_CALL_PERSON_COMPAT, person.toBundle());
                }
            }
            IconCompat iconCompat = this.mVerificationIcon;
            if (iconCompat != null) {
                bundle.putParcelable(NotificationCompat.EXTRA_VERIFICATION_ICON, Api23Impl.a(iconCompat.toIcon(this.f3460a.mContext)));
            }
            bundle.putCharSequence(NotificationCompat.EXTRA_VERIFICATION_TEXT, this.mVerificationText);
            bundle.putParcelable(NotificationCompat.EXTRA_ANSWER_INTENT, this.mAnswerIntent);
            bundle.putParcelable(NotificationCompat.EXTRA_DECLINE_INTENT, this.mDeclineIntent);
            bundle.putParcelable(NotificationCompat.EXTRA_HANG_UP_INTENT, this.mHangUpIntent);
            Integer num = this.mAnswerButtonColor;
            if (num != null) {
                bundle.putInt(NotificationCompat.EXTRA_ANSWER_COLOR, num.intValue());
            }
            Integer num2 = this.mDeclineButtonColor;
            if (num2 != null) {
                bundle.putInt(NotificationCompat.EXTRA_DECLINE_COLOR, num2.intValue());
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            int i2 = Build.VERSION.SDK_INT;
            CharSequence defaultText = null;
            callStyleA = null;
            Notification.CallStyle callStyleA = null;
            defaultText = null;
            if (i2 < 31) {
                Notification.Builder builder = notificationBuilderWithBuilderAccessor.getBuilder();
                Person person = this.mPerson;
                builder.setContentTitle(person != null ? person.getName() : null);
                Bundle bundle = this.f3460a.B;
                if (bundle != null && bundle.containsKey(NotificationCompat.EXTRA_TEXT)) {
                    defaultText = this.f3460a.B.getCharSequence(NotificationCompat.EXTRA_TEXT);
                }
                if (defaultText == null) {
                    defaultText = getDefaultText();
                }
                builder.setContentText(defaultText);
                Person person2 = this.mPerson;
                if (person2 != null) {
                    if (person2.getIcon() != null) {
                        Api23Impl.c(builder, this.mPerson.getIcon().toIcon(this.f3460a.mContext));
                    }
                    if (i2 >= 28) {
                        Api28Impl.a(builder, this.mPerson.toAndroidPerson());
                    } else {
                        Api21Impl.a(builder, this.mPerson.getUri());
                    }
                }
                Api21Impl.b(builder, NotificationCompat.CATEGORY_CALL);
                return;
            }
            int i3 = this.mCallType;
            if (i3 == 1) {
                callStyleA = Api31Impl.a(this.mPerson.toAndroidPerson(), this.mDeclineIntent, this.mAnswerIntent);
            } else if (i3 == 2) {
                callStyleA = Api31Impl.b(this.mPerson.toAndroidPerson(), this.mHangUpIntent);
            } else if (i3 == 3) {
                callStyleA = Api31Impl.c(this.mPerson.toAndroidPerson(), this.mHangUpIntent, this.mAnswerIntent);
            } else if (Log.isLoggable(NotificationCompat.TAG, 3)) {
                Log.d(NotificationCompat.TAG, "Unrecognized call type in CallStyle: " + String.valueOf(this.mCallType));
            }
            if (callStyleA != null) {
                callStyleA.setBuilder(notificationBuilderWithBuilderAccessor.getBuilder());
                Integer num = this.mAnswerButtonColor;
                if (num != null) {
                    Api31Impl.d(callStyleA, num.intValue());
                }
                Integer num2 = this.mDeclineButtonColor;
                if (num2 != null) {
                    Api31Impl.f(callStyleA, num2.intValue());
                }
                Api31Impl.i(callStyleA, this.mVerificationText);
                IconCompat iconCompat = this.mVerificationIcon;
                if (iconCompat != null) {
                    Api31Impl.h(callStyleA, iconCompat.toIcon(this.f3460a.mContext));
                }
                Api31Impl.g(callStyleA, this.mIsVideo);
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected String f() {
            return TEMPLATE_CLASS_NAME;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void g(Bundle bundle) {
            super.g(bundle);
            this.mCallType = bundle.getInt(NotificationCompat.EXTRA_CALL_TYPE);
            this.mIsVideo = bundle.getBoolean(NotificationCompat.EXTRA_CALL_IS_VIDEO);
            if (Build.VERSION.SDK_INT >= 28 && bundle.containsKey(NotificationCompat.EXTRA_CALL_PERSON)) {
                this.mPerson = Person.fromAndroidPerson(j.a(bundle.getParcelable(NotificationCompat.EXTRA_CALL_PERSON)));
            } else if (bundle.containsKey(NotificationCompat.EXTRA_CALL_PERSON_COMPAT)) {
                this.mPerson = Person.fromBundle(bundle.getBundle(NotificationCompat.EXTRA_CALL_PERSON_COMPAT));
            }
            if (bundle.containsKey(NotificationCompat.EXTRA_VERIFICATION_ICON)) {
                this.mVerificationIcon = IconCompat.createFromIcon((Icon) bundle.getParcelable(NotificationCompat.EXTRA_VERIFICATION_ICON));
            } else if (bundle.containsKey(NotificationCompat.EXTRA_VERIFICATION_ICON_COMPAT)) {
                this.mVerificationIcon = IconCompat.createFromBundle(bundle.getBundle(NotificationCompat.EXTRA_VERIFICATION_ICON_COMPAT));
            }
            this.mVerificationText = bundle.getCharSequence(NotificationCompat.EXTRA_VERIFICATION_TEXT);
            this.mAnswerIntent = (PendingIntent) bundle.getParcelable(NotificationCompat.EXTRA_ANSWER_INTENT);
            this.mDeclineIntent = (PendingIntent) bundle.getParcelable(NotificationCompat.EXTRA_DECLINE_INTENT);
            this.mHangUpIntent = (PendingIntent) bundle.getParcelable(NotificationCompat.EXTRA_HANG_UP_INTENT);
            this.mAnswerButtonColor = bundle.containsKey(NotificationCompat.EXTRA_ANSWER_COLOR) ? Integer.valueOf(bundle.getInt(NotificationCompat.EXTRA_ANSWER_COLOR)) : null;
            this.mDeclineButtonColor = bundle.containsKey(NotificationCompat.EXTRA_DECLINE_COLOR) ? Integer.valueOf(bundle.getInt(NotificationCompat.EXTRA_DECLINE_COLOR)) : null;
        }

        @NonNull
        @RequiresApi(20)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public ArrayList<Action> getActionsListWithSystemActions() {
            Action actionMakeNegativeAction = makeNegativeAction();
            Action actionMakeAnswerAction = makeAnswerAction();
            ArrayList<Action> arrayList = new ArrayList<>(3);
            arrayList.add(actionMakeNegativeAction);
            ArrayList<Action> arrayList2 = this.f3460a.mActions;
            int i2 = 2;
            if (arrayList2 != null) {
                for (Action action : arrayList2) {
                    if (action.isContextual()) {
                        arrayList.add(action);
                    } else if (!isActionAddedByCallStyle(action) && i2 > 1) {
                        arrayList.add(action);
                        i2--;
                    }
                    if (actionMakeAnswerAction != null && i2 == 1) {
                        arrayList.add(actionMakeAnswerAction);
                        i2--;
                    }
                }
            }
            if (actionMakeAnswerAction != null && i2 >= 1) {
                arrayList.add(actionMakeAnswerAction);
            }
            return arrayList;
        }

        @NonNull
        public CallStyle setAnswerButtonColorHint(@ColorInt int i2) {
            this.mAnswerButtonColor = Integer.valueOf(i2);
            return this;
        }

        @NonNull
        public CallStyle setDeclineButtonColorHint(@ColorInt int i2) {
            this.mDeclineButtonColor = Integer.valueOf(i2);
            return this;
        }

        @NonNull
        public CallStyle setIsVideo(boolean z2) {
            this.mIsVideo = z2;
            return this;
        }

        @NonNull
        @RequiresApi(23)
        public CallStyle setVerificationIcon(@Nullable Icon icon) {
            this.mVerificationIcon = icon == null ? null : IconCompat.createFromIcon(icon);
            return this;
        }

        @NonNull
        public CallStyle setVerificationText(@Nullable CharSequence charSequence) {
            this.mVerificationText = charSequence;
            return this;
        }

        public CallStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }

        @NonNull
        public CallStyle setVerificationIcon(@Nullable Bitmap bitmap) {
            this.mVerificationIcon = IconCompat.createWithBitmap(bitmap);
            return this;
        }

        private CallStyle(int i2, @NonNull Person person, @Nullable PendingIntent pendingIntent, @Nullable PendingIntent pendingIntent2, @Nullable PendingIntent pendingIntent3) {
            if (person != null && !TextUtils.isEmpty(person.getName())) {
                this.mCallType = i2;
                this.mPerson = person;
                this.mAnswerIntent = pendingIntent3;
                this.mDeclineIntent = pendingIntent2;
                this.mHangUpIntent = pendingIntent;
                return;
            }
            throw new IllegalArgumentException("person must have a non-empty a name");
        }
    }

    public static class DecoratedCustomViewStyle extends Style {
        private static final int MAX_ACTION_BUTTONS = 3;
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle";

        @RequiresApi(24)
        static class Api24Impl {
            private Api24Impl() {
            }

            @DoNotInline
            static Notification.Style a() {
                return new Notification.DecoratedCustomViewStyle();
            }
        }

        private RemoteViews createRemoteViews(RemoteViews remoteViews, boolean z2) throws Resources.NotFoundException {
            int iMin;
            int i2 = 0;
            RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(true, R.layout.notification_template_custom_big, false);
            remoteViewsApplyStandardTemplate.removeAllViews(R.id.actions);
            List<Action> nonContextualActions = getNonContextualActions(this.f3460a.mActions);
            if (!z2 || nonContextualActions == null || (iMin = Math.min(nonContextualActions.size(), 3)) <= 0) {
                i2 = 8;
            } else {
                for (int i3 = 0; i3 < iMin; i3++) {
                    remoteViewsApplyStandardTemplate.addView(R.id.actions, generateActionButton(nonContextualActions.get(i3)));
                }
            }
            remoteViewsApplyStandardTemplate.setViewVisibility(R.id.actions, i2);
            remoteViewsApplyStandardTemplate.setViewVisibility(R.id.action_divider, i2);
            buildIntoRemoteViews(remoteViewsApplyStandardTemplate, remoteViews);
            return remoteViewsApplyStandardTemplate;
        }

        private RemoteViews generateActionButton(Action action) {
            boolean z2 = action.actionIntent == null;
            RemoteViews remoteViews = new RemoteViews(this.f3460a.mContext.getPackageName(), z2 ? R.layout.notification_action_tombstone : R.layout.notification_action);
            IconCompat iconCompat = action.getIconCompat();
            if (iconCompat != null) {
                remoteViews.setImageViewBitmap(R.id.action_image, e(iconCompat, R.color.notification_action_color_filter));
            }
            remoteViews.setTextViewText(R.id.action_text, action.title);
            if (!z2) {
                remoteViews.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
            }
            remoteViews.setContentDescription(R.id.action_container, action.title);
            return remoteViews;
        }

        private static List<Action> getNonContextualActions(List<Action> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Action action : list) {
                if (!action.isContextual()) {
                    arrayList.add(action);
                }
            }
            return arrayList;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(Api24Impl.a());
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected String f() {
            return TEMPLATE_CLASS_NAME;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews bigContentView = this.f3460a.getBigContentView();
            if (bigContentView == null) {
                bigContentView = this.f3460a.getContentView();
            }
            if (bigContentView == null) {
                return null;
            }
            return createRemoteViews(bigContentView, true);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT < 24 && this.f3460a.getContentView() != null) {
                return createRemoteViews(this.f3460a.getContentView(), false);
            }
            return null;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUpContentView = this.f3460a.getHeadsUpContentView();
            RemoteViews contentView = headsUpContentView != null ? headsUpContentView : this.f3460a.getContentView();
            if (headsUpContentView == null) {
                return null;
            }
            return createRemoteViews(contentView, true);
        }
    }

    public interface Extender {
        @NonNull
        Builder extend(@NonNull Builder builder);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface GroupAlertBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface NotificationVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface ServiceNotificationBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface StreamType {
    }

    public static abstract class Style {

        /* renamed from: a, reason: collision with root package name */
        protected Builder f3460a;

        /* renamed from: b, reason: collision with root package name */
        CharSequence f3461b;

        /* renamed from: c, reason: collision with root package name */
        CharSequence f3462c;

        /* renamed from: d, reason: collision with root package name */
        boolean f3463d = false;

        @RequiresApi(24)
        static class Api24Impl {
            private Api24Impl() {
            }

            @DoNotInline
            static void a(RemoteViews remoteViews, int i2, boolean z2) {
                remoteViews.setChronometerCountDown(i2, z2);
            }
        }

        static Style b(String str) {
            if (str == null) {
                return null;
            }
            switch (str) {
                case "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle":
                    return new DecoratedCustomViewStyle();
                case "androidx.core.app.NotificationCompat$BigPictureStyle":
                    return new BigPictureStyle();
                case "androidx.core.app.NotificationCompat$CallStyle":
                    return new CallStyle();
                case "androidx.core.app.NotificationCompat$InboxStyle":
                    return new InboxStyle();
                case "androidx.core.app.NotificationCompat$BigTextStyle":
                    return new BigTextStyle();
                case "androidx.core.app.NotificationCompat$MessagingStyle":
                    return new MessagingStyle();
                default:
                    return null;
            }
        }

        static Style c(Bundle bundle) {
            Style styleB = b(bundle.getString(NotificationCompat.EXTRA_COMPAT_TEMPLATE));
            return styleB != null ? styleB : (bundle.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME) || bundle.containsKey(NotificationCompat.EXTRA_MESSAGING_STYLE_USER)) ? new MessagingStyle() : (bundle.containsKey(NotificationCompat.EXTRA_PICTURE) || bundle.containsKey(NotificationCompat.EXTRA_PICTURE_ICON)) ? new BigPictureStyle() : bundle.containsKey(NotificationCompat.EXTRA_BIG_TEXT) ? new BigTextStyle() : bundle.containsKey(NotificationCompat.EXTRA_TEXT_LINES) ? new InboxStyle() : bundle.containsKey(NotificationCompat.EXTRA_CALL_TYPE) ? new CallStyle() : constructCompatStyleByPlatformName(bundle.getString(NotificationCompat.EXTRA_TEMPLATE));
        }

        private int calculateTopPadding() throws Resources.NotFoundException {
            Resources resources = this.f3460a.mContext.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_top_pad);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
            float fConstrain = (constrain(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round(((1.0f - fConstrain) * dimensionPixelSize) + (fConstrain * dimensionPixelSize2));
        }

        private static float constrain(float f2, float f3, float f4) {
            return f2 < f3 ? f3 : f2 > f4 ? f4 : f2;
        }

        @Nullable
        private static Style constructCompatStyleByPlatformName(@Nullable String str) {
            if (str == null) {
                return null;
            }
            if (str.equals(Notification.BigPictureStyle.class.getName())) {
                return new BigPictureStyle();
            }
            if (str.equals(Notification.BigTextStyle.class.getName())) {
                return new BigTextStyle();
            }
            if (str.equals(Notification.InboxStyle.class.getName())) {
                return new InboxStyle();
            }
            if (Build.VERSION.SDK_INT >= 24) {
                if (str.equals(m.a().getName())) {
                    return new MessagingStyle();
                }
                if (str.equals(n.a().getName())) {
                    return new DecoratedCustomViewStyle();
                }
            }
            return null;
        }

        private Bitmap createIconWithBackground(int i2, int i3, int i4, int i5) {
            int i6 = R.drawable.notification_icon_background;
            if (i5 == 0) {
                i5 = 0;
            }
            Bitmap bitmapCreateColoredBitmap = createColoredBitmap(i6, i5, i3);
            Canvas canvas = new Canvas(bitmapCreateColoredBitmap);
            Drawable drawableMutate = this.f3460a.mContext.getResources().getDrawable(i2).mutate();
            drawableMutate.setFilterBitmap(true);
            int i7 = (i3 - i4) / 2;
            int i8 = i4 + i7;
            drawableMutate.setBounds(i7, i7, i8, i8);
            drawableMutate.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
            drawableMutate.draw(canvas);
            return bitmapCreateColoredBitmap;
        }

        static Style d(Bundle bundle) {
            Style styleC = c(bundle);
            if (styleC == null) {
                return null;
            }
            try {
                styleC.g(bundle);
                return styleC;
            } catch (ClassCastException unused) {
                return null;
            }
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static Style extractStyleFromNotification(@NonNull Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            if (extras == null) {
                return null;
            }
            return d(extras);
        }

        private void hideNormalContent(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(R.id.title, 8);
            remoteViews.setViewVisibility(R.id.text2, 8);
            remoteViews.setViewVisibility(R.id.text, 8);
        }

        protected void a(Bundle bundle) {
            bundle.remove(NotificationCompat.EXTRA_SUMMARY_TEXT);
            bundle.remove(NotificationCompat.EXTRA_TITLE_BIG);
            bundle.remove(NotificationCompat.EXTRA_COMPAT_TEMPLATE);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void addCompatExtras(@NonNull Bundle bundle) {
            if (this.f3463d) {
                bundle.putCharSequence(NotificationCompat.EXTRA_SUMMARY_TEXT, this.f3462c);
            }
            CharSequence charSequence = this.f3461b;
            if (charSequence != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_TITLE_BIG, charSequence);
            }
            String strF = f();
            if (strF != null) {
                bundle.putString(NotificationCompat.EXTRA_COMPAT_TEMPLATE, strF);
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x010a  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0149  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x0195  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x019a  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x019c  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x01a5  */
        @androidx.annotation.NonNull
        @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.widget.RemoteViews applyStandardTemplate(boolean r13, int r14, boolean r15) throws android.content.res.Resources.NotFoundException {
            /*
                Method dump skipped, instructions count: 426
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat.Style.applyStandardTemplate(boolean, int, boolean):android.widget.RemoteViews");
        }

        @Nullable
        public Notification build() {
            Builder builder = this.f3460a;
            if (builder != null) {
                return builder.build();
            }
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void buildIntoRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2) throws Resources.NotFoundException {
            hideNormalContent(remoteViews);
            remoteViews.removeAllViews(R.id.notification_main_column);
            remoteViews.addView(R.id.notification_main_column, remoteViews2.clone());
            remoteViews.setViewVisibility(R.id.notification_main_column, 0);
            remoteViews.setViewPadding(R.id.notification_main_column_container, 0, calculateTopPadding(), 0, 0);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Bitmap createColoredBitmap(int i2, int i3) {
            return createColoredBitmap(i2, i3, 0);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public boolean displayCustomViewInline() {
            return false;
        }

        Bitmap e(IconCompat iconCompat, int i2) {
            return createColoredBitmap(iconCompat, i2, 0);
        }

        protected String f() {
            return null;
        }

        protected void g(Bundle bundle) {
            if (bundle.containsKey(NotificationCompat.EXTRA_SUMMARY_TEXT)) {
                this.f3462c = bundle.getCharSequence(NotificationCompat.EXTRA_SUMMARY_TEXT);
                this.f3463d = true;
            }
            this.f3461b = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE_BIG);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        public void setBuilder(@Nullable Builder builder) {
            if (this.f3460a != builder) {
                this.f3460a = builder;
                if (builder != null) {
                    builder.setStyle(this);
                }
            }
        }

        private Bitmap createColoredBitmap(int i2, int i3, int i4) {
            return createColoredBitmap(IconCompat.createWithResource(this.f3460a.mContext, i2), i3, i4);
        }

        private Bitmap createColoredBitmap(@NonNull IconCompat iconCompat, int i2, int i3) {
            Drawable drawableLoadDrawable = iconCompat.loadDrawable(this.f3460a.mContext);
            int intrinsicWidth = i3 == 0 ? drawableLoadDrawable.getIntrinsicWidth() : i3;
            if (i3 == 0) {
                i3 = drawableLoadDrawable.getIntrinsicHeight();
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, i3, Bitmap.Config.ARGB_8888);
            drawableLoadDrawable.setBounds(0, 0, intrinsicWidth, i3);
            if (i2 != 0) {
                drawableLoadDrawable.mutate().setColorFilter(new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_IN));
            }
            drawableLoadDrawable.draw(new Canvas(bitmapCreateBitmap));
            return bitmapCreateBitmap;
        }
    }

    public static final class WearableExtender implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";

        @Deprecated
        public static final int SCREEN_TIMEOUT_LONG = -1;

        @Deprecated
        public static final int SCREEN_TIMEOUT_SHORT = 0;

        @Deprecated
        public static final int SIZE_DEFAULT = 0;

        @Deprecated
        public static final int SIZE_FULL_SCREEN = 5;

        @Deprecated
        public static final int SIZE_LARGE = 4;

        @Deprecated
        public static final int SIZE_MEDIUM = 3;

        @Deprecated
        public static final int SIZE_SMALL = 2;

        @Deprecated
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions;
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex;
        private int mContentIcon;
        private int mContentIconGravity;
        private int mCustomContentHeight;
        private int mCustomSizePreset;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags;
        private int mGravity;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages;

        @RequiresApi(20)
        static class Api20Impl {
            private Api20Impl() {
            }

            @DoNotInline
            static Notification.Action.Builder a(Notification.Action.Builder builder, Bundle bundle) {
                return builder.addExtras(bundle);
            }

            @DoNotInline
            static Notification.Action.Builder b(Notification.Action.Builder builder, android.app.RemoteInput remoteInput) {
                return builder.addRemoteInput(remoteInput);
            }

            @DoNotInline
            static Notification.Action c(Notification.Action.Builder builder) {
                return builder.build();
            }

            @DoNotInline
            static Notification.Action.Builder d(int i2, CharSequence charSequence, PendingIntent pendingIntent) {
                return new Notification.Action.Builder(i2, charSequence, pendingIntent);
            }

            @DoNotInline
            public static Action getActionCompatFromAction(ArrayList<Parcelable> arrayList, int i2) {
                return NotificationCompat.a((Notification.Action) arrayList.get(i2));
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
        }

        @RequiresApi(24)
        static class Api24Impl {
            private Api24Impl() {
            }

            @DoNotInline
            static Notification.Action.Builder a(Notification.Action.Builder builder, boolean z2) {
                return builder.setAllowGeneratedReplies(z2);
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
        }

        public WearableExtender() {
            this.mActions = new ArrayList<>();
            this.mFlags = 1;
            this.mPages = new ArrayList<>();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
        }

        @RequiresApi(20)
        private static Notification.Action getActionFromActionCompat(Action action) {
            int i2 = Build.VERSION.SDK_INT;
            IconCompat iconCompat = action.getIconCompat();
            Notification.Action.Builder builderA = Api23Impl.a(iconCompat == null ? null : iconCompat.toIcon(), action.getTitle(), action.getActionIntent());
            Bundle bundle = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
            bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
            if (i2 >= 24) {
                Api24Impl.a(builderA, action.getAllowGeneratedReplies());
            }
            if (i2 >= 31) {
                Api31Impl.a(builderA, action.isAuthenticationRequired());
            }
            Api20Impl.a(builderA, bundle);
            RemoteInput[] remoteInputs = action.getRemoteInputs();
            if (remoteInputs != null) {
                for (android.app.RemoteInput remoteInput : RemoteInput.b(remoteInputs)) {
                    Api20Impl.b(builderA, remoteInput);
                }
            }
            return Api20Impl.c(builderA);
        }

        private void setFlag(int i2, boolean z2) {
            if (z2) {
                this.mFlags = i2 | this.mFlags;
            } else {
                this.mFlags = (~i2) & this.mFlags;
            }
        }

        @NonNull
        public WearableExtender addAction(@NonNull Action action) {
            this.mActions.add(action);
            return this;
        }

        @NonNull
        public WearableExtender addActions(@NonNull List<Action> list) {
            this.mActions.addAll(list);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender addPage(@NonNull Notification notification) {
            this.mPages.add(notification);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender addPages(@NonNull List<Notification> list) {
            this.mPages.addAll(list);
            return this;
        }

        @NonNull
        public WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Extender
        @NonNull
        public Builder extend(@NonNull Builder builder) {
            Bundle bundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>(this.mActions.size());
                Iterator<Action> it = this.mActions.iterator();
                while (it.hasNext()) {
                    arrayList.add(getActionFromActionCompat(it.next()));
                }
                bundle.putParcelableArrayList(KEY_ACTIONS, arrayList);
            }
            int i2 = this.mFlags;
            if (i2 != 1) {
                bundle.putInt("flags", i2);
            }
            PendingIntent pendingIntent = this.mDisplayIntent;
            if (pendingIntent != null) {
                bundle.putParcelable(KEY_DISPLAY_INTENT, pendingIntent);
            }
            if (!this.mPages.isEmpty()) {
                ArrayList<Notification> arrayList2 = this.mPages;
                bundle.putParcelableArray("pages", (Parcelable[]) arrayList2.toArray(new Notification[arrayList2.size()]));
            }
            Bitmap bitmap = this.mBackground;
            if (bitmap != null) {
                bundle.putParcelable(KEY_BACKGROUND, bitmap);
            }
            int i3 = this.mContentIcon;
            if (i3 != 0) {
                bundle.putInt(KEY_CONTENT_ICON, i3);
            }
            int i4 = this.mContentIconGravity;
            if (i4 != 8388613) {
                bundle.putInt(KEY_CONTENT_ICON_GRAVITY, i4);
            }
            int i5 = this.mContentActionIndex;
            if (i5 != -1) {
                bundle.putInt(KEY_CONTENT_ACTION_INDEX, i5);
            }
            int i6 = this.mCustomSizePreset;
            if (i6 != 0) {
                bundle.putInt(KEY_CUSTOM_SIZE_PRESET, i6);
            }
            int i7 = this.mCustomContentHeight;
            if (i7 != 0) {
                bundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, i7);
            }
            int i8 = this.mGravity;
            if (i8 != 80) {
                bundle.putInt(KEY_GRAVITY, i8);
            }
            int i9 = this.mHintScreenTimeout;
            if (i9 != 0) {
                bundle.putInt(KEY_HINT_SCREEN_TIMEOUT, i9);
            }
            String str = this.mDismissalId;
            if (str != null) {
                bundle.putString(KEY_DISMISSAL_ID, str);
            }
            String str2 = this.mBridgeTag;
            if (str2 != null) {
                bundle.putString(KEY_BRIDGE_TAG, str2);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return builder;
        }

        @NonNull
        public List<Action> getActions() {
            return this.mActions;
        }

        @Nullable
        @Deprecated
        public Bitmap getBackground() {
            return this.mBackground;
        }

        @Nullable
        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        @Deprecated
        public int getContentIcon() {
            return this.mContentIcon;
        }

        @Deprecated
        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        @Deprecated
        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        @Deprecated
        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        @Nullable
        public String getDismissalId() {
            return this.mDismissalId;
        }

        @Nullable
        @Deprecated
        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        @Deprecated
        public int getGravity() {
            return this.mGravity;
        }

        @Deprecated
        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        @Deprecated
        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        @Deprecated
        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        @Deprecated
        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        @Deprecated
        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        @NonNull
        @Deprecated
        public List<Notification> getPages() {
            return this.mPages;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setBackground(@Nullable Bitmap bitmap) {
            this.mBackground = bitmap;
            return this;
        }

        @NonNull
        public WearableExtender setBridgeTag(@Nullable String str) {
            this.mBridgeTag = str;
            return this;
        }

        @NonNull
        public WearableExtender setContentAction(int i2) {
            this.mContentActionIndex = i2;
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setContentIcon(int i2) {
            this.mContentIcon = i2;
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setContentIconGravity(int i2) {
            this.mContentIconGravity = i2;
            return this;
        }

        @NonNull
        public WearableExtender setContentIntentAvailableOffline(boolean z2) {
            setFlag(1, z2);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setCustomContentHeight(int i2) {
            this.mCustomContentHeight = i2;
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setCustomSizePreset(int i2) {
            this.mCustomSizePreset = i2;
            return this;
        }

        @NonNull
        public WearableExtender setDismissalId(@Nullable String str) {
            this.mDismissalId = str;
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setDisplayIntent(@Nullable PendingIntent pendingIntent) {
            this.mDisplayIntent = pendingIntent;
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setGravity(int i2) {
            this.mGravity = i2;
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintAmbientBigPicture(boolean z2) {
            setFlag(32, z2);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintAvoidBackgroundClipping(boolean z2) {
            setFlag(16, z2);
            return this;
        }

        @NonNull
        public WearableExtender setHintContentIntentLaunchesActivity(boolean z2) {
            setFlag(64, z2);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintHideIcon(boolean z2) {
            setFlag(2, z2);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintScreenTimeout(int i2) {
            this.mHintScreenTimeout = i2;
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintShowBackgroundOnly(boolean z2) {
            setFlag(4, z2);
            return this;
        }

        @NonNull
        public WearableExtender setStartScrollBottom(boolean z2) {
            setFlag(8, z2);
            return this;
        }

        @NonNull
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public WearableExtender m7clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.mActions = new ArrayList<>(this.mActions);
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mDisplayIntent = this.mDisplayIntent;
            wearableExtender.mPages = new ArrayList<>(this.mPages);
            wearableExtender.mBackground = this.mBackground;
            wearableExtender.mContentIcon = this.mContentIcon;
            wearableExtender.mContentIconGravity = this.mContentIconGravity;
            wearableExtender.mContentActionIndex = this.mContentActionIndex;
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset;
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight;
            wearableExtender.mGravity = this.mGravity;
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
            wearableExtender.mDismissalId = this.mDismissalId;
            wearableExtender.mBridgeTag = this.mBridgeTag;
            return wearableExtender;
        }

        public WearableExtender(@NonNull Notification notification) {
            this.mActions = new ArrayList<>();
            this.mFlags = 1;
            this.mPages = new ArrayList<>();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
            Bundle extras = NotificationCompat.getExtras(notification);
            Bundle bundle = extras != null ? extras.getBundle(EXTRA_WEARABLE_EXTENSIONS) : null;
            if (bundle != null) {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList(KEY_ACTIONS);
                if (parcelableArrayList != null) {
                    int size = parcelableArrayList.size();
                    Action[] actionArr = new Action[size];
                    for (int i2 = 0; i2 < size; i2++) {
                        actionArr[i2] = Api20Impl.getActionCompatFromAction(parcelableArrayList, i2);
                    }
                    Collections.addAll(this.mActions, actionArr);
                }
                this.mFlags = bundle.getInt("flags", 1);
                this.mDisplayIntent = (PendingIntent) bundle.getParcelable(KEY_DISPLAY_INTENT);
                Notification[] notificationArrC = NotificationCompat.c(bundle, "pages");
                if (notificationArrC != null) {
                    Collections.addAll(this.mPages, notificationArrC);
                }
                this.mBackground = (Bitmap) bundle.getParcelable(KEY_BACKGROUND);
                this.mContentIcon = bundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = bundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = bundle.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = bundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = bundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = bundle.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = bundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = bundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = bundle.getString(KEY_BRIDGE_TAG);
            }
        }
    }

    @Deprecated
    public NotificationCompat() {
    }

    static Action a(Notification.Action action) {
        RemoteInput[] remoteInputArr;
        int i2;
        android.app.RemoteInput[] remoteInputArrG = Api20Impl.g(action);
        if (remoteInputArrG == null) {
            remoteInputArr = null;
        } else {
            RemoteInput[] remoteInputArr2 = new RemoteInput[remoteInputArrG.length];
            for (int i3 = 0; i3 < remoteInputArrG.length; i3++) {
                android.app.RemoteInput remoteInput = remoteInputArrG[i3];
                remoteInputArr2[i3] = new RemoteInput(Api20Impl.h(remoteInput), Api20Impl.f(remoteInput), Api20Impl.b(remoteInput), Api20Impl.a(remoteInput), Build.VERSION.SDK_INT >= 29 ? Api29Impl.c(remoteInput) : 0, Api20Impl.d(remoteInput), null);
            }
            remoteInputArr = remoteInputArr2;
        }
        int i4 = Build.VERSION.SDK_INT;
        boolean z2 = i4 >= 24 ? Api20Impl.c(action).getBoolean("android.support.allowGeneratedReplies") || Api24Impl.a(action) : Api20Impl.c(action).getBoolean("android.support.allowGeneratedReplies");
        boolean z3 = Api20Impl.c(action).getBoolean("android.support.action.showsUserInterface", true);
        int iA = i4 >= 28 ? Api28Impl.a(action) : Api20Impl.c(action).getInt("android.support.action.semanticAction", 0);
        boolean zE = i4 >= 29 ? Api29Impl.e(action) : false;
        boolean zA = i4 >= 31 ? Api31Impl.a(action) : false;
        if (Api23Impl.a(action) != null || (i2 = action.icon) == 0) {
            return new Action(Api23Impl.a(action) != null ? IconCompat.createFromIconOrNullIfZeroResId(Api23Impl.a(action)) : null, action.title, action.actionIntent, Api20Impl.c(action), remoteInputArr, (RemoteInput[]) null, z2, iA, z3, zE, zA);
        }
        return new Action(i2, action.title, action.actionIntent, Api20Impl.c(action), remoteInputArr, (RemoteInput[]) null, z2, iA, z3, zE, zA);
    }

    static boolean b(Notification notification) {
        return (notification.flags & 128) != 0;
    }

    static Notification[] c(Bundle bundle, String str) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(str);
        if ((parcelableArray instanceof Notification[]) || parcelableArray == null) {
            return (Notification[]) parcelableArray;
        }
        Notification[] notificationArr = new Notification[parcelableArray.length];
        for (int i2 = 0; i2 < parcelableArray.length; i2++) {
            notificationArr[i2] = (Notification) parcelableArray[i2];
        }
        bundle.putParcelableArray(str, notificationArr);
        return notificationArr;
    }

    @Nullable
    public static Action getAction(@NonNull Notification notification, int i2) {
        return a(notification.actions[i2]);
    }

    public static int getActionCount(@NonNull Notification notification) {
        Notification.Action[] actionArr = notification.actions;
        if (actionArr != null) {
            return actionArr.length;
        }
        return 0;
    }

    public static boolean getAllowSystemGeneratedContextualActions(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.a(notification);
        }
        return false;
    }

    public static boolean getAutoCancel(@NonNull Notification notification) {
        return (notification.flags & 16) != 0;
    }

    public static int getBadgeIconType(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.a(notification);
        }
        return 0;
    }

    @Nullable
    public static BubbleMetadata getBubbleMetadata(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 29) {
            return BubbleMetadata.fromPlatform(Api29Impl.b(notification));
        }
        return null;
    }

    @Nullable
    public static String getCategory(@NonNull Notification notification) {
        return notification.category;
    }

    @Nullable
    public static String getChannelId(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.b(notification);
        }
        return null;
    }

    public static int getColor(@NonNull Notification notification) {
        return notification.color;
    }

    @Nullable
    public static CharSequence getContentInfo(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_INFO_TEXT);
    }

    @Nullable
    public static CharSequence getContentText(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_TEXT);
    }

    @Nullable
    public static CharSequence getContentTitle(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_TITLE);
    }

    @Nullable
    public static Bundle getExtras(@NonNull Notification notification) {
        return notification.extras;
    }

    @Nullable
    public static String getGroup(@NonNull Notification notification) {
        return Api20Impl.e(notification);
    }

    public static int getGroupAlertBehavior(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.c(notification);
        }
        return 0;
    }

    @NonNull
    @RequiresApi(21)
    public static List<Action> getInvisibleActions(@NonNull Notification notification) {
        Bundle bundle;
        ArrayList arrayList = new ArrayList();
        Bundle bundle2 = notification.extras.getBundle("android.car.EXTENSIONS");
        if (bundle2 != null && (bundle = bundle2.getBundle("invisible_actions")) != null) {
            for (int i2 = 0; i2 < bundle.size(); i2++) {
                arrayList.add(NotificationCompatJellybean.a(bundle.getBundle(Integer.toString(i2))));
            }
        }
        return arrayList;
    }

    public static boolean getLocalOnly(@NonNull Notification notification) {
        return (notification.flags & 256) != 0;
    }

    @Nullable
    public static LocusIdCompat getLocusId(@NonNull Notification notification) {
        LocusId locusIdD;
        if (Build.VERSION.SDK_INT < 29 || (locusIdD = Api29Impl.d(notification)) == null) {
            return null;
        }
        return LocusIdCompat.toLocusIdCompat(locusIdD);
    }

    public static boolean getOngoing(@NonNull Notification notification) {
        return (notification.flags & 2) != 0;
    }

    public static boolean getOnlyAlertOnce(@NonNull Notification notification) {
        return (notification.flags & 8) != 0;
    }

    @NonNull
    public static List<Person> getPeople(@NonNull Notification notification) {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 28) {
            ArrayList parcelableArrayList = notification.extras.getParcelableArrayList(EXTRA_PEOPLE_LIST);
            if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    arrayList.add(Person.fromAndroidPerson(j.a(it.next())));
                }
            }
        } else {
            String[] stringArray = notification.extras.getStringArray(EXTRA_PEOPLE);
            if (stringArray != null && stringArray.length != 0) {
                for (String str : stringArray) {
                    arrayList.add(new Person.Builder().setUri(str).build());
                }
            }
        }
        return arrayList;
    }

    @Nullable
    public static Notification getPublicVersion(@NonNull Notification notification) {
        return notification.publicVersion;
    }

    @Nullable
    public static CharSequence getSettingsText(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.d(notification);
        }
        return null;
    }

    @Nullable
    public static String getShortcutId(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.e(notification);
        }
        return null;
    }

    public static boolean getShowWhen(@NonNull Notification notification) {
        return notification.extras.getBoolean(EXTRA_SHOW_WHEN);
    }

    @Nullable
    public static String getSortKey(@NonNull Notification notification) {
        return Api20Impl.i(notification);
    }

    @Nullable
    public static CharSequence getSubText(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_SUB_TEXT);
    }

    public static long getTimeoutAfter(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.f(notification);
        }
        return 0L;
    }

    public static boolean getUsesChronometer(@NonNull Notification notification) {
        return notification.extras.getBoolean(EXTRA_SHOW_CHRONOMETER);
    }

    public static int getVisibility(@NonNull Notification notification) {
        return notification.visibility;
    }

    public static boolean isGroupSummary(@NonNull Notification notification) {
        return (notification.flags & 512) != 0;
    }

    @Nullable
    public static Bitmap reduceLargeIconSize(@NonNull Context context, @Nullable Bitmap bitmap) throws Resources.NotFoundException {
        if (bitmap == null || Build.VERSION.SDK_INT >= 27) {
            return bitmap;
        }
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.compat_notification_large_icon_max_width);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.compat_notification_large_icon_max_height);
        if (bitmap.getWidth() <= dimensionPixelSize && bitmap.getHeight() <= dimensionPixelSize2) {
            return bitmap;
        }
        double dMin = Math.min(dimensionPixelSize / Math.max(1, bitmap.getWidth()), dimensionPixelSize2 / Math.max(1, bitmap.getHeight()));
        return Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(bitmap.getWidth() * dMin), (int) Math.ceil(bitmap.getHeight() * dMin), true);
    }

    public static final class CarExtender implements Extender {
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String KEY_AUTHOR = "author";
        private static final String KEY_MESSAGES = "messages";
        private static final String KEY_ON_READ = "on_read";
        private static final String KEY_ON_REPLY = "on_reply";
        private static final String KEY_PARTICIPANTS = "participants";
        private static final String KEY_REMOTE_INPUT = "remote_input";
        private static final String KEY_TEXT = "text";
        private static final String KEY_TIMESTAMP = "timestamp";
        private int mColor;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        @RequiresApi(20)
        static class Api20Impl {
            private Api20Impl() {
            }

            @DoNotInline
            static RemoteInput.Builder a(RemoteInput.Builder builder, Bundle bundle) {
                return builder.addExtras(bundle);
            }

            @DoNotInline
            static android.app.RemoteInput b(RemoteInput.Builder builder) {
                return builder.build();
            }

            @DoNotInline
            static Parcelable c(android.app.RemoteInput remoteInput) {
                return remoteInput;
            }

            @DoNotInline
            static RemoteInput.Builder d(String str) {
                return new RemoteInput.Builder(str);
            }

            @DoNotInline
            static boolean e(android.app.RemoteInput remoteInput) {
                return remoteInput.getAllowFreeFormInput();
            }

            @DoNotInline
            static CharSequence[] f(android.app.RemoteInput remoteInput) {
                return remoteInput.getChoices();
            }

            @DoNotInline
            static Bundle g(android.app.RemoteInput remoteInput) {
                return remoteInput.getExtras();
            }

            @DoNotInline
            static CharSequence h(android.app.RemoteInput remoteInput) {
                return remoteInput.getLabel();
            }

            @DoNotInline
            static String i(android.app.RemoteInput remoteInput) {
                return remoteInput.getResultKey();
            }

            @DoNotInline
            static RemoteInput.Builder j(RemoteInput.Builder builder, boolean z2) {
                return builder.setAllowFreeFormInput(z2);
            }

            @DoNotInline
            static RemoteInput.Builder k(RemoteInput.Builder builder, CharSequence[] charSequenceArr) {
                return builder.setChoices(charSequenceArr);
            }

            @DoNotInline
            static RemoteInput.Builder l(RemoteInput.Builder builder, CharSequence charSequence) {
                return builder.setLabel(charSequence);
            }
        }

        @RequiresApi(29)
        static class Api29Impl {
            private Api29Impl() {
            }

            @DoNotInline
            static int a(android.app.RemoteInput remoteInput) {
                return remoteInput.getEditChoicesBeforeSending();
            }
        }

        @Deprecated
        public static class UnreadConversation {
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            public static class Builder {
                private long mLatestTimestamp;
                private final List<String> mMessages = new ArrayList();
                private final String mParticipant;
                private PendingIntent mReadPendingIntent;
                private RemoteInput mRemoteInput;
                private PendingIntent mReplyPendingIntent;

                public Builder(@NonNull String str) {
                    this.mParticipant = str;
                }

                @NonNull
                public Builder addMessage(@Nullable String str) {
                    if (str != null) {
                        this.mMessages.add(str);
                    }
                    return this;
                }

                @NonNull
                public UnreadConversation build() {
                    List<String> list = this.mMessages;
                    return new UnreadConversation((String[]) list.toArray(new String[list.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, new String[]{this.mParticipant}, this.mLatestTimestamp);
                }

                @NonNull
                public Builder setLatestTimestamp(long j2) {
                    this.mLatestTimestamp = j2;
                    return this;
                }

                @NonNull
                public Builder setReadPendingIntent(@Nullable PendingIntent pendingIntent) {
                    this.mReadPendingIntent = pendingIntent;
                    return this;
                }

                @NonNull
                public Builder setReplyAction(@Nullable PendingIntent pendingIntent, @Nullable RemoteInput remoteInput) {
                    this.mRemoteInput = remoteInput;
                    this.mReplyPendingIntent = pendingIntent;
                    return this;
                }
            }

            UnreadConversation(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j2) {
                this.mMessages = strArr;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = pendingIntent2;
                this.mReplyPendingIntent = pendingIntent;
                this.mParticipants = strArr2;
                this.mLatestTimestamp = j2;
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }

            @Nullable
            public String[] getMessages() {
                return this.mMessages;
            }

            @Nullable
            public String getParticipant() {
                String[] strArr = this.mParticipants;
                if (strArr.length > 0) {
                    return strArr[0];
                }
                return null;
            }

            @Nullable
            public String[] getParticipants() {
                return this.mParticipants;
            }

            @Nullable
            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            @Nullable
            public RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            @Nullable
            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }
        }

        public CarExtender() {
            this.mColor = 0;
        }

        @RequiresApi(21)
        private static Bundle getBundleForUnreadConversation(@NonNull UnreadConversation unreadConversation) {
            Bundle bundle = new Bundle();
            String str = (unreadConversation.getParticipants() == null || unreadConversation.getParticipants().length <= 1) ? null : unreadConversation.getParticipants()[0];
            int length = unreadConversation.getMessages().length;
            Parcelable[] parcelableArr = new Parcelable[length];
            for (int i2 = 0; i2 < length; i2++) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("text", unreadConversation.getMessages()[i2]);
                bundle2.putString(KEY_AUTHOR, str);
                parcelableArr[i2] = bundle2;
            }
            bundle.putParcelableArray(KEY_MESSAGES, parcelableArr);
            RemoteInput remoteInput = unreadConversation.getRemoteInput();
            if (remoteInput != null) {
                RemoteInput.Builder builderD = Api20Impl.d(remoteInput.getResultKey());
                Api20Impl.l(builderD, remoteInput.getLabel());
                Api20Impl.k(builderD, remoteInput.getChoices());
                Api20Impl.j(builderD, remoteInput.getAllowFreeFormInput());
                Api20Impl.a(builderD, remoteInput.getExtras());
                bundle.putParcelable(KEY_REMOTE_INPUT, Api20Impl.c(Api20Impl.b(builderD)));
            }
            bundle.putParcelable(KEY_ON_REPLY, unreadConversation.getReplyPendingIntent());
            bundle.putParcelable(KEY_ON_READ, unreadConversation.getReadPendingIntent());
            bundle.putStringArray(KEY_PARTICIPANTS, unreadConversation.getParticipants());
            bundle.putLong("timestamp", unreadConversation.getLatestTimestamp());
            return bundle;
        }

        @RequiresApi(21)
        private static UnreadConversation getUnreadConversationFromBundle(@Nullable Bundle bundle) {
            String[] strArr;
            if (bundle == null) {
                return null;
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray(KEY_MESSAGES);
            if (parcelableArray != null) {
                int length = parcelableArray.length;
                String[] strArr2 = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    Parcelable parcelable = parcelableArray[i2];
                    if (parcelable instanceof Bundle) {
                        String string = ((Bundle) parcelable).getString("text");
                        strArr2[i2] = string;
                        if (string != null) {
                        }
                    }
                    return null;
                }
                strArr = strArr2;
            } else {
                strArr = null;
            }
            PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable(KEY_ON_READ);
            PendingIntent pendingIntent2 = (PendingIntent) bundle.getParcelable(KEY_ON_REPLY);
            android.app.RemoteInput remoteInput = (android.app.RemoteInput) bundle.getParcelable(KEY_REMOTE_INPUT);
            String[] stringArray = bundle.getStringArray(KEY_PARTICIPANTS);
            if (stringArray == null || stringArray.length != 1) {
                return null;
            }
            return new UnreadConversation(strArr, remoteInput != null ? new RemoteInput(Api20Impl.i(remoteInput), Api20Impl.h(remoteInput), Api20Impl.f(remoteInput), Api20Impl.e(remoteInput), Build.VERSION.SDK_INT >= 29 ? Api29Impl.a(remoteInput) : 0, Api20Impl.g(remoteInput), null) : null, pendingIntent2, pendingIntent, stringArray, bundle.getLong("timestamp"));
        }

        @Override // androidx.core.app.NotificationCompat.Extender
        @NonNull
        public Builder extend(@NonNull Builder builder) {
            Bundle bundle = new Bundle();
            Bitmap bitmap = this.mLargeIcon;
            if (bitmap != null) {
                bundle.putParcelable(EXTRA_LARGE_ICON, bitmap);
            }
            int i2 = this.mColor;
            if (i2 != 0) {
                bundle.putInt(EXTRA_COLOR, i2);
            }
            UnreadConversation unreadConversation = this.mUnreadConversation;
            if (unreadConversation != null) {
                bundle.putBundle(EXTRA_CONVERSATION, getBundleForUnreadConversation(unreadConversation));
            }
            builder.getExtras().putBundle("android.car.EXTENSIONS", bundle);
            return builder;
        }

        @ColorInt
        public int getColor() {
            return this.mColor;
        }

        @Nullable
        public Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        @Nullable
        @Deprecated
        public UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }

        @NonNull
        public CarExtender setColor(@ColorInt int i2) {
            this.mColor = i2;
            return this;
        }

        @NonNull
        public CarExtender setLargeIcon(@Nullable Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        @NonNull
        @Deprecated
        public CarExtender setUnreadConversation(@Nullable UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        public CarExtender(@NonNull Notification notification) {
            this.mColor = 0;
            Bundle bundle = NotificationCompat.getExtras(notification) == null ? null : NotificationCompat.getExtras(notification).getBundle("android.car.EXTENSIONS");
            if (bundle != null) {
                this.mLargeIcon = (Bitmap) bundle.getParcelable(EXTRA_LARGE_ICON);
                this.mColor = bundle.getInt(EXTRA_COLOR, 0);
                this.mUnreadConversation = getUnreadConversationFromBundle(bundle.getBundle(EXTRA_CONVERSATION));
            }
        }
    }

    public static class InboxStyle extends Style {
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$InboxStyle";
        private ArrayList<CharSequence> mTexts = new ArrayList<>();

        public InboxStyle() {
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void a(Bundle bundle) {
            super.a(bundle);
            bundle.remove(NotificationCompat.EXTRA_TEXT_LINES);
        }

        @NonNull
        public InboxStyle addLine(@Nullable CharSequence charSequence) {
            if (charSequence != null) {
                this.mTexts.add(Builder.a(charSequence));
            }
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            Notification.InboxStyle bigContentTitle = new Notification.InboxStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.f3461b);
            if (this.f3463d) {
                bigContentTitle.setSummaryText(this.f3462c);
            }
            Iterator<CharSequence> it = this.mTexts.iterator();
            while (it.hasNext()) {
                bigContentTitle.addLine(it.next());
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected String f() {
            return TEMPLATE_CLASS_NAME;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void g(Bundle bundle) {
            super.g(bundle);
            this.mTexts.clear();
            if (bundle.containsKey(NotificationCompat.EXTRA_TEXT_LINES)) {
                Collections.addAll(this.mTexts, bundle.getCharSequenceArray(NotificationCompat.EXTRA_TEXT_LINES));
            }
        }

        @NonNull
        public InboxStyle setBigContentTitle(@Nullable CharSequence charSequence) {
            this.f3461b = Builder.a(charSequence);
            return this;
        }

        @NonNull
        public InboxStyle setSummaryText(@Nullable CharSequence charSequence) {
            this.f3462c = Builder.a(charSequence);
            this.f3463d = true;
            return this;
        }

        public InboxStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }
    }

    public static final class TvExtender implements Extender {

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        private static final String EXTRA_FLAGS = "flags";
        private static final int FLAG_AVAILABLE_ON_TV = 1;
        private static final String TAG = "TvExtender";
        private String mChannelId;
        private PendingIntent mContentIntent;
        private PendingIntent mDeleteIntent;
        private int mFlags;
        private boolean mSuppressShowOverApps;

        public TvExtender() {
            this.mFlags = 1;
        }

        @Override // androidx.core.app.NotificationCompat.Extender
        @NonNull
        public Builder extend(@NonNull Builder builder) {
            if (Build.VERSION.SDK_INT < 26) {
                return builder;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("flags", this.mFlags);
            bundle.putString("channel_id", this.mChannelId);
            bundle.putBoolean("suppressShowOverApps", this.mSuppressShowOverApps);
            PendingIntent pendingIntent = this.mContentIntent;
            if (pendingIntent != null) {
                bundle.putParcelable("content_intent", pendingIntent);
            }
            PendingIntent pendingIntent2 = this.mDeleteIntent;
            if (pendingIntent2 != null) {
                bundle.putParcelable("delete_intent", pendingIntent2);
            }
            builder.getExtras().putBundle("android.tv.EXTENSIONS", bundle);
            return builder;
        }

        @Nullable
        public String getChannelId() {
            return this.mChannelId;
        }

        @Nullable
        public PendingIntent getContentIntent() {
            return this.mContentIntent;
        }

        @Nullable
        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public boolean isAvailableOnTv() {
            return (this.mFlags & 1) != 0;
        }

        public boolean isSuppressShowOverApps() {
            return this.mSuppressShowOverApps;
        }

        @NonNull
        public TvExtender setChannelId(@Nullable String str) {
            this.mChannelId = str;
            return this;
        }

        @NonNull
        public TvExtender setContentIntent(@Nullable PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent;
            return this;
        }

        @NonNull
        public TvExtender setDeleteIntent(@Nullable PendingIntent pendingIntent) {
            this.mDeleteIntent = pendingIntent;
            return this;
        }

        @NonNull
        public TvExtender setSuppressShowOverApps(boolean z2) {
            this.mSuppressShowOverApps = z2;
            return this;
        }

        public TvExtender(@NonNull Notification notification) {
            if (Build.VERSION.SDK_INT < 26) {
                return;
            }
            Bundle bundle = notification.extras;
            Bundle bundle2 = bundle == null ? null : bundle.getBundle("android.tv.EXTENSIONS");
            if (bundle2 != null) {
                this.mFlags = bundle2.getInt("flags");
                this.mChannelId = bundle2.getString("channel_id");
                this.mSuppressShowOverApps = bundle2.getBoolean("suppressShowOverApps");
                this.mContentIntent = (PendingIntent) bundle2.getParcelable("content_intent");
                this.mDeleteIntent = (PendingIntent) bundle2.getParcelable("delete_intent");
            }
        }
    }

    public static class MessagingStyle extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        private static final String TEMPLATE_CLASS_NAME = "androidx.core.app.NotificationCompat$MessagingStyle";

        @Nullable
        private CharSequence mConversationTitle;

        @Nullable
        private Boolean mIsGroupConversation;
        private Person mUser;
        private final List<Message> mMessages = new ArrayList();
        private final List<Message> mHistoricMessages = new ArrayList();

        @RequiresApi(24)
        static class Api24Impl {
            private Api24Impl() {
            }

            @DoNotInline
            static Notification.MessagingStyle a(Notification.MessagingStyle messagingStyle, Notification.MessagingStyle.Message message) {
                return messagingStyle.addMessage(message);
            }

            @DoNotInline
            static Notification.MessagingStyle b(CharSequence charSequence) {
                return new Notification.MessagingStyle(charSequence);
            }

            @DoNotInline
            static Notification.MessagingStyle c(Notification.MessagingStyle messagingStyle, CharSequence charSequence) {
                return messagingStyle.setConversationTitle(charSequence);
            }
        }

        @RequiresApi(26)
        static class Api26Impl {
            private Api26Impl() {
            }

            @DoNotInline
            static Notification.MessagingStyle a(Notification.MessagingStyle messagingStyle, Notification.MessagingStyle.Message message) {
                return messagingStyle.addHistoricMessage(message);
            }
        }

        @RequiresApi(28)
        static class Api28Impl {
            private Api28Impl() {
            }

            @DoNotInline
            static Notification.MessagingStyle a(android.app.Person person) {
                return new Notification.MessagingStyle(person);
            }

            @DoNotInline
            static Notification.MessagingStyle b(Notification.MessagingStyle messagingStyle, boolean z2) {
                return messagingStyle.setGroupConversation(z2);
            }
        }

        MessagingStyle() {
        }

        @Nullable
        public static MessagingStyle extractMessagingStyleFromNotification(@NonNull Notification notification) {
            Style styleExtractStyleFromNotification = Style.extractStyleFromNotification(notification);
            if (styleExtractStyleFromNotification instanceof MessagingStyle) {
                return (MessagingStyle) styleExtractStyleFromNotification;
            }
            return null;
        }

        @Nullable
        private Message findLatestIncomingMessage() {
            for (int size = this.mMessages.size() - 1; size >= 0; size--) {
                Message message = this.mMessages.get(size);
                if (message.getPerson() != null && !TextUtils.isEmpty(message.getPerson().getName())) {
                    return message;
                }
            }
            if (this.mMessages.isEmpty()) {
                return null;
            }
            return this.mMessages.get(r0.size() - 1);
        }

        private boolean hasMessagesWithoutSender() {
            for (int size = this.mMessages.size() - 1; size >= 0; size--) {
                Message message = this.mMessages.get(size);
                if (message.getPerson() != null && message.getPerson().getName() == null) {
                    return true;
                }
            }
            return false;
        }

        @NonNull
        private TextAppearanceSpan makeFontColorSpan(int i2) {
            return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(i2), null);
        }

        private CharSequence makeMessageLine(@NonNull Message message) {
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            CharSequence name = message.getPerson() == null ? "" : message.getPerson().getName();
            boolean zIsEmpty = TextUtils.isEmpty(name);
            int color = ViewCompat.MEASURED_STATE_MASK;
            if (zIsEmpty) {
                name = this.mUser.getName();
                if (this.f3460a.getColor() != 0) {
                    color = this.f3460a.getColor();
                }
            }
            CharSequence charSequenceUnicodeWrap = bidiFormatter.unicodeWrap(name);
            spannableStringBuilder.append(charSequenceUnicodeWrap);
            spannableStringBuilder.setSpan(makeFontColorSpan(color), spannableStringBuilder.length() - charSequenceUnicodeWrap.length(), spannableStringBuilder.length(), 33);
            spannableStringBuilder.append((CharSequence) "  ").append(bidiFormatter.unicodeWrap(message.getText() != null ? message.getText() : ""));
            return spannableStringBuilder;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void a(Bundle bundle) {
            super.a(bundle);
            bundle.remove(NotificationCompat.EXTRA_MESSAGING_STYLE_USER);
            bundle.remove(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            bundle.remove(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            bundle.remove(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE);
            bundle.remove(NotificationCompat.EXTRA_MESSAGES);
            bundle.remove(NotificationCompat.EXTRA_HISTORIC_MESSAGES);
            bundle.remove(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        public void addCompatExtras(@NonNull Bundle bundle) {
            super.addCompatExtras(bundle);
            bundle.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUser.getName());
            bundle.putBundle(NotificationCompat.EXTRA_MESSAGING_STYLE_USER, this.mUser.toBundle());
            bundle.putCharSequence(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE, this.mConversationTitle);
            if (this.mConversationTitle != null && this.mIsGroupConversation.booleanValue()) {
                bundle.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, Message.a(this.mMessages));
            }
            if (!this.mHistoricMessages.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_HISTORIC_MESSAGES, Message.a(this.mHistoricMessages));
            }
            Boolean bool = this.mIsGroupConversation;
            if (bool != null) {
                bundle.putBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION, bool.booleanValue());
            }
        }

        @NonNull
        public MessagingStyle addHistoricMessage(@Nullable Message message) {
            if (message != null) {
                this.mHistoricMessages.add(message);
                if (this.mHistoricMessages.size() > 25) {
                    this.mHistoricMessages.remove(0);
                }
            }
            return this;
        }

        @NonNull
        @Deprecated
        public MessagingStyle addMessage(@Nullable CharSequence charSequence, long j2, @Nullable CharSequence charSequence2) {
            this.mMessages.add(new Message(charSequence, j2, new Person.Builder().setName(charSequence2).build()));
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            setGroupConversation(isGroupConversation());
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 24) {
                Notification.MessagingStyle messagingStyleA = i2 >= 28 ? Api28Impl.a(this.mUser.toAndroidPerson()) : Api24Impl.b(this.mUser.getName());
                Iterator<Message> it = this.mMessages.iterator();
                while (it.hasNext()) {
                    Api24Impl.a(l.a(messagingStyleA), it.next().d());
                }
                if (Build.VERSION.SDK_INT >= 26) {
                    Iterator<Message> it2 = this.mHistoricMessages.iterator();
                    while (it2.hasNext()) {
                        Api26Impl.a(l.a(messagingStyleA), it2.next().d());
                    }
                }
                if (this.mIsGroupConversation.booleanValue() || Build.VERSION.SDK_INT >= 28) {
                    Api24Impl.c(l.a(messagingStyleA), this.mConversationTitle);
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    Api28Impl.b(l.a(messagingStyleA), this.mIsGroupConversation.booleanValue());
                }
                messagingStyleA.setBuilder(notificationBuilderWithBuilderAccessor.getBuilder());
                return;
            }
            Message messageFindLatestIncomingMessage = findLatestIncomingMessage();
            if (this.mConversationTitle != null && this.mIsGroupConversation.booleanValue()) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.mConversationTitle);
            } else if (messageFindLatestIncomingMessage != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle("");
                if (messageFindLatestIncomingMessage.getPerson() != null) {
                    notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(messageFindLatestIncomingMessage.getPerson().getName());
                }
            }
            if (messageFindLatestIncomingMessage != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentText(this.mConversationTitle != null ? makeMessageLine(messageFindLatestIncomingMessage) : messageFindLatestIncomingMessage.getText());
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            boolean z2 = this.mConversationTitle != null || hasMessagesWithoutSender();
            for (int size = this.mMessages.size() - 1; size >= 0; size--) {
                Message message = this.mMessages.get(size);
                CharSequence charSequenceMakeMessageLine = z2 ? makeMessageLine(message) : message.getText();
                if (size != this.mMessages.size() - 1) {
                    spannableStringBuilder.insert(0, (CharSequence) "\n");
                }
                spannableStringBuilder.insert(0, charSequenceMakeMessageLine);
            }
            new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(null).bigText(spannableStringBuilder);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected String f() {
            return TEMPLATE_CLASS_NAME;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        protected void g(Bundle bundle) {
            super.g(bundle);
            this.mMessages.clear();
            if (bundle.containsKey(NotificationCompat.EXTRA_MESSAGING_STYLE_USER)) {
                this.mUser = Person.fromBundle(bundle.getBundle(NotificationCompat.EXTRA_MESSAGING_STYLE_USER));
            } else {
                this.mUser = new Person.Builder().setName(bundle.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)).build();
            }
            CharSequence charSequence = bundle.getCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            this.mConversationTitle = charSequence;
            if (charSequence == null) {
                this.mConversationTitle = bundle.getCharSequence(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE);
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            if (parcelableArray != null) {
                this.mMessages.addAll(Message.c(parcelableArray));
            }
            Parcelable[] parcelableArray2 = bundle.getParcelableArray(NotificationCompat.EXTRA_HISTORIC_MESSAGES);
            if (parcelableArray2 != null) {
                this.mHistoricMessages.addAll(Message.c(parcelableArray2));
            }
            if (bundle.containsKey(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION)) {
                this.mIsGroupConversation = Boolean.valueOf(bundle.getBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION));
            }
        }

        @Nullable
        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        @NonNull
        public List<Message> getHistoricMessages() {
            return this.mHistoricMessages;
        }

        @NonNull
        public List<Message> getMessages() {
            return this.mMessages;
        }

        @NonNull
        public Person getUser() {
            return this.mUser;
        }

        @Nullable
        @Deprecated
        public CharSequence getUserDisplayName() {
            return this.mUser.getName();
        }

        public boolean isGroupConversation() {
            Builder builder = this.f3460a;
            if (builder != null && builder.mContext.getApplicationInfo().targetSdkVersion < 28 && this.mIsGroupConversation == null) {
                return this.mConversationTitle != null;
            }
            Boolean bool = this.mIsGroupConversation;
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        }

        @NonNull
        public MessagingStyle setConversationTitle(@Nullable CharSequence charSequence) {
            this.mConversationTitle = charSequence;
            return this;
        }

        @NonNull
        public MessagingStyle setGroupConversation(boolean z2) {
            this.mIsGroupConversation = Boolean.valueOf(z2);
            return this;
        }

        @Deprecated
        public MessagingStyle(@NonNull CharSequence charSequence) {
            this.mUser = new Person.Builder().setName(charSequence).build();
        }

        public static final class Message {

            @Nullable
            private String mDataMimeType;

            @Nullable
            private Uri mDataUri;
            private Bundle mExtras;

            @Nullable
            private final Person mPerson;
            private final CharSequence mText;
            private final long mTimestamp;

            @RequiresApi(24)
            static class Api24Impl {
                private Api24Impl() {
                }

                @DoNotInline
                static Notification.MessagingStyle.Message a(CharSequence charSequence, long j2, CharSequence charSequence2) {
                    return new Notification.MessagingStyle.Message(charSequence, j2, charSequence2);
                }

                @DoNotInline
                static Notification.MessagingStyle.Message b(Notification.MessagingStyle.Message message, String str, Uri uri) {
                    return message.setData(str, uri);
                }
            }

            @RequiresApi(28)
            static class Api28Impl {
                private Api28Impl() {
                }

                @DoNotInline
                static Parcelable a(android.app.Person person) {
                    return person;
                }

                @DoNotInline
                static Notification.MessagingStyle.Message b(CharSequence charSequence, long j2, android.app.Person person) {
                    return new Notification.MessagingStyle.Message(charSequence, j2, person);
                }
            }

            public Message(@Nullable CharSequence charSequence, long j2, @Nullable Person person) {
                this.mExtras = new Bundle();
                this.mText = charSequence;
                this.mTimestamp = j2;
                this.mPerson = person;
            }

            static Bundle[] a(List list) {
                Bundle[] bundleArr = new Bundle[list.size()];
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    bundleArr[i2] = ((Message) list.get(i2)).toBundle();
                }
                return bundleArr;
            }

            static Message b(Bundle bundle) {
                try {
                    if (bundle.containsKey("text") && bundle.containsKey("time")) {
                        Message message = new Message(bundle.getCharSequence("text"), bundle.getLong("time"), bundle.containsKey("person") ? Person.fromBundle(bundle.getBundle("person")) : (!bundle.containsKey("sender_person") || Build.VERSION.SDK_INT < 28) ? bundle.containsKey("sender") ? new Person.Builder().setName(bundle.getCharSequence("sender")).build() : null : Person.fromAndroidPerson(j.a(bundle.getParcelable("sender_person"))));
                        if (bundle.containsKey("type") && bundle.containsKey(ShareConstants.MEDIA_URI)) {
                            message.setData(bundle.getString("type"), (Uri) bundle.getParcelable(ShareConstants.MEDIA_URI));
                        }
                        if (bundle.containsKey("extras")) {
                            message.getExtras().putAll(bundle.getBundle("extras"));
                        }
                        return message;
                    }
                } catch (ClassCastException unused) {
                }
                return null;
            }

            static List c(Parcelable[] parcelableArr) {
                Message messageB;
                ArrayList arrayList = new ArrayList(parcelableArr.length);
                for (Parcelable parcelable : parcelableArr) {
                    if ((parcelable instanceof Bundle) && (messageB = b((Bundle) parcelable)) != null) {
                        arrayList.add(messageB);
                    }
                }
                return arrayList;
            }

            @NonNull
            private Bundle toBundle() {
                Bundle bundle = new Bundle();
                CharSequence charSequence = this.mText;
                if (charSequence != null) {
                    bundle.putCharSequence("text", charSequence);
                }
                bundle.putLong("time", this.mTimestamp);
                Person person = this.mPerson;
                if (person != null) {
                    bundle.putCharSequence("sender", person.getName());
                    if (Build.VERSION.SDK_INT >= 28) {
                        bundle.putParcelable("sender_person", Api28Impl.a(this.mPerson.toAndroidPerson()));
                    } else {
                        bundle.putBundle("person", this.mPerson.toBundle());
                    }
                }
                String str = this.mDataMimeType;
                if (str != null) {
                    bundle.putString("type", str);
                }
                Uri uri = this.mDataUri;
                if (uri != null) {
                    bundle.putParcelable(ShareConstants.MEDIA_URI, uri);
                }
                Bundle bundle2 = this.mExtras;
                if (bundle2 != null) {
                    bundle.putBundle("extras", bundle2);
                }
                return bundle;
            }

            Notification.MessagingStyle.Message d() {
                Notification.MessagingStyle.Message messageA;
                Person person = getPerson();
                if (Build.VERSION.SDK_INT >= 28) {
                    messageA = Api28Impl.b(getText(), getTimestamp(), person != null ? person.toAndroidPerson() : null);
                } else {
                    messageA = Api24Impl.a(getText(), getTimestamp(), person != null ? person.getName() : null);
                }
                if (getDataMimeType() != null) {
                    Api24Impl.b(messageA, getDataMimeType(), getDataUri());
                }
                return messageA;
            }

            @Nullable
            public String getDataMimeType() {
                return this.mDataMimeType;
            }

            @Nullable
            public Uri getDataUri() {
                return this.mDataUri;
            }

            @NonNull
            public Bundle getExtras() {
                return this.mExtras;
            }

            @Nullable
            public Person getPerson() {
                return this.mPerson;
            }

            @Nullable
            @Deprecated
            public CharSequence getSender() {
                Person person = this.mPerson;
                if (person == null) {
                    return null;
                }
                return person.getName();
            }

            @Nullable
            public CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            @NonNull
            public Message setData(@Nullable String str, @Nullable Uri uri) {
                this.mDataMimeType = str;
                this.mDataUri = uri;
                return this;
            }

            @Deprecated
            public Message(@Nullable CharSequence charSequence, long j2, @Nullable CharSequence charSequence2) {
                this(charSequence, j2, new Person.Builder().setName(charSequence2).build());
            }
        }

        @NonNull
        public MessagingStyle addMessage(@Nullable CharSequence charSequence, long j2, @Nullable Person person) {
            addMessage(new Message(charSequence, j2, person));
            return this;
        }

        @NonNull
        public MessagingStyle addMessage(@Nullable Message message) {
            if (message != null) {
                this.mMessages.add(message);
                if (this.mMessages.size() > 25) {
                    this.mMessages.remove(0);
                }
            }
            return this;
        }

        public MessagingStyle(@NonNull Person person) {
            if (!TextUtils.isEmpty(person.getName())) {
                this.mUser = person;
                return;
            }
            throw new IllegalArgumentException("User's name must not be empty.");
        }
    }
}

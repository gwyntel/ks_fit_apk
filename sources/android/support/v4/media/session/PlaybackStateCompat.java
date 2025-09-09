package android.support.v4.media.session;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.media.session.PlaybackStateCompatApi21;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.alipay.sdk.m.u.i;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class PlaybackStateCompat implements Parcelable {
    public static final long ACTION_FAST_FORWARD = 64;
    public static final long ACTION_PAUSE = 2;
    public static final long ACTION_PLAY = 4;
    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024;
    public static final long ACTION_PLAY_FROM_SEARCH = 2048;
    public static final long ACTION_PLAY_FROM_URI = 8192;
    public static final long ACTION_PLAY_PAUSE = 512;
    public static final long ACTION_PREPARE = 16384;
    public static final long ACTION_PREPARE_FROM_MEDIA_ID = 32768;
    public static final long ACTION_PREPARE_FROM_SEARCH = 65536;
    public static final long ACTION_PREPARE_FROM_URI = 131072;
    public static final long ACTION_REWIND = 8;
    public static final long ACTION_SEEK_TO = 256;
    public static final long ACTION_SET_CAPTIONING_ENABLED = 1048576;
    public static final long ACTION_SET_RATING = 128;
    public static final long ACTION_SET_REPEAT_MODE = 262144;
    public static final long ACTION_SET_SHUFFLE_MODE = 2097152;

    @Deprecated
    public static final long ACTION_SET_SHUFFLE_MODE_ENABLED = 524288;
    public static final long ACTION_SKIP_TO_NEXT = 32;
    public static final long ACTION_SKIP_TO_PREVIOUS = 16;
    public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096;
    public static final long ACTION_STOP = 1;
    public static final Parcelable.Creator<PlaybackStateCompat> CREATOR = new Parcelable.Creator<PlaybackStateCompat>() { // from class: android.support.v4.media.session.PlaybackStateCompat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackStateCompat createFromParcel(Parcel parcel) {
            return new PlaybackStateCompat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackStateCompat[] newArray(int i2) {
            return new PlaybackStateCompat[i2];
        }
    };
    public static final int ERROR_CODE_ACTION_ABORTED = 10;
    public static final int ERROR_CODE_APP_ERROR = 1;
    public static final int ERROR_CODE_AUTHENTICATION_EXPIRED = 3;
    public static final int ERROR_CODE_CONCURRENT_STREAM_LIMIT = 5;
    public static final int ERROR_CODE_CONTENT_ALREADY_PLAYING = 8;
    public static final int ERROR_CODE_END_OF_QUEUE = 11;
    public static final int ERROR_CODE_NOT_AVAILABLE_IN_REGION = 7;
    public static final int ERROR_CODE_NOT_SUPPORTED = 2;
    public static final int ERROR_CODE_PARENTAL_CONTROL_RESTRICTED = 6;
    public static final int ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED = 4;
    public static final int ERROR_CODE_SKIP_LIMIT_REACHED = 9;
    public static final int ERROR_CODE_UNKNOWN_ERROR = 0;
    private static final int KEYCODE_MEDIA_PAUSE = 127;
    private static final int KEYCODE_MEDIA_PLAY = 126;
    public static final long PLAYBACK_POSITION_UNKNOWN = -1;
    public static final int REPEAT_MODE_ALL = 2;
    public static final int REPEAT_MODE_GROUP = 3;
    public static final int REPEAT_MODE_INVALID = -1;
    public static final int REPEAT_MODE_NONE = 0;
    public static final int REPEAT_MODE_ONE = 1;
    public static final int SHUFFLE_MODE_ALL = 1;
    public static final int SHUFFLE_MODE_GROUP = 2;
    public static final int SHUFFLE_MODE_INVALID = -1;
    public static final int SHUFFLE_MODE_NONE = 0;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;

    /* renamed from: a, reason: collision with root package name */
    final int f1856a;

    /* renamed from: b, reason: collision with root package name */
    final long f1857b;

    /* renamed from: c, reason: collision with root package name */
    final long f1858c;

    /* renamed from: d, reason: collision with root package name */
    final float f1859d;

    /* renamed from: e, reason: collision with root package name */
    final long f1860e;

    /* renamed from: f, reason: collision with root package name */
    final int f1861f;

    /* renamed from: g, reason: collision with root package name */
    final CharSequence f1862g;

    /* renamed from: h, reason: collision with root package name */
    final long f1863h;

    /* renamed from: i, reason: collision with root package name */
    List f1864i;

    /* renamed from: j, reason: collision with root package name */
    final long f1865j;

    /* renamed from: k, reason: collision with root package name */
    final Bundle f1866k;
    private Object mStateObj;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Actions {
    }

    public static final class Builder {
        private long mActions;
        private long mActiveItemId;
        private long mBufferedPosition;
        private final List<CustomAction> mCustomActions;
        private int mErrorCode;
        private CharSequence mErrorMessage;
        private Bundle mExtras;
        private long mPosition;
        private float mRate;
        private int mState;
        private long mUpdateTime;

        public Builder() {
            this.mCustomActions = new ArrayList();
            this.mActiveItemId = -1L;
        }

        public Builder addCustomAction(String str, String str2, int i2) {
            return addCustomAction(new CustomAction(str, str2, i2, null));
        }

        public PlaybackStateCompat build() {
            return new PlaybackStateCompat(this.mState, this.mPosition, this.mBufferedPosition, this.mRate, this.mActions, this.mErrorCode, this.mErrorMessage, this.mUpdateTime, this.mCustomActions, this.mActiveItemId, this.mExtras);
        }

        public Builder setActions(long j2) {
            this.mActions = j2;
            return this;
        }

        public Builder setActiveQueueItemId(long j2) {
            this.mActiveItemId = j2;
            return this;
        }

        public Builder setBufferedPosition(long j2) {
            this.mBufferedPosition = j2;
            return this;
        }

        public Builder setErrorMessage(CharSequence charSequence) {
            this.mErrorMessage = charSequence;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setState(int i2, long j2, float f2) {
            return setState(i2, j2, f2, SystemClock.elapsedRealtime());
        }

        public Builder addCustomAction(CustomAction customAction) {
            if (customAction == null) {
                throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackStateCompat.");
            }
            this.mCustomActions.add(customAction);
            return this;
        }

        public Builder setErrorMessage(int i2, CharSequence charSequence) {
            this.mErrorCode = i2;
            this.mErrorMessage = charSequence;
            return this;
        }

        public Builder setState(int i2, long j2, float f2, long j3) {
            this.mState = i2;
            this.mPosition = j2;
            this.mUpdateTime = j3;
            this.mRate = f2;
            return this;
        }

        public Builder(PlaybackStateCompat playbackStateCompat) {
            ArrayList arrayList = new ArrayList();
            this.mCustomActions = arrayList;
            this.mActiveItemId = -1L;
            this.mState = playbackStateCompat.f1856a;
            this.mPosition = playbackStateCompat.f1857b;
            this.mRate = playbackStateCompat.f1859d;
            this.mUpdateTime = playbackStateCompat.f1863h;
            this.mBufferedPosition = playbackStateCompat.f1858c;
            this.mActions = playbackStateCompat.f1860e;
            this.mErrorCode = playbackStateCompat.f1861f;
            this.mErrorMessage = playbackStateCompat.f1862g;
            List list = playbackStateCompat.f1864i;
            if (list != null) {
                arrayList.addAll(list);
            }
            this.mActiveItemId = playbackStateCompat.f1865j;
            this.mExtras = playbackStateCompat.f1866k;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface MediaKeyAction {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface RepeatMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ShuffleMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    PlaybackStateCompat(int i2, long j2, long j3, float f2, long j4, int i3, CharSequence charSequence, long j5, List list, long j6, Bundle bundle) {
        this.f1856a = i2;
        this.f1857b = j2;
        this.f1858c = j3;
        this.f1859d = f2;
        this.f1860e = j4;
        this.f1861f = i3;
        this.f1862g = charSequence;
        this.f1863h = j5;
        this.f1864i = new ArrayList(list);
        this.f1865j = j6;
        this.f1866k = bundle;
    }

    public static PlaybackStateCompat fromPlaybackState(Object obj) {
        ArrayList arrayList = null;
        if (obj == null) {
            return null;
        }
        List<Object> customActions = PlaybackStateCompatApi21.getCustomActions(obj);
        if (customActions != null) {
            arrayList = new ArrayList(customActions.size());
            Iterator<Object> it = customActions.iterator();
            while (it.hasNext()) {
                arrayList.add(CustomAction.fromCustomAction(it.next()));
            }
        }
        Bundle extras = PlaybackStateCompatApi22.getExtras(obj);
        PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(PlaybackStateCompatApi21.getState(obj), PlaybackStateCompatApi21.getPosition(obj), PlaybackStateCompatApi21.getBufferedPosition(obj), PlaybackStateCompatApi21.getPlaybackSpeed(obj), PlaybackStateCompatApi21.getActions(obj), 0, PlaybackStateCompatApi21.getErrorMessage(obj), PlaybackStateCompatApi21.getLastPositionUpdateTime(obj), arrayList, PlaybackStateCompatApi21.getActiveQueueItemId(obj), extras);
        playbackStateCompat.mStateObj = obj;
        return playbackStateCompat;
    }

    public static int toKeyCode(long j2) {
        if (j2 == 4) {
            return 126;
        }
        if (j2 == 2) {
            return 127;
        }
        if (j2 == 32) {
            return 87;
        }
        if (j2 == 16) {
            return 88;
        }
        if (j2 == 1) {
            return 86;
        }
        if (j2 == 64) {
            return 90;
        }
        if (j2 == 8) {
            return 89;
        }
        return j2 == 512 ? 85 : 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getActions() {
        return this.f1860e;
    }

    public long getActiveQueueItemId() {
        return this.f1865j;
    }

    public long getBufferedPosition() {
        return this.f1858c;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public long getCurrentPosition(Long l2) {
        return Math.max(0L, this.f1857b + ((long) (this.f1859d * (l2 != null ? l2.longValue() : SystemClock.elapsedRealtime() - this.f1863h))));
    }

    public List<CustomAction> getCustomActions() {
        return this.f1864i;
    }

    public int getErrorCode() {
        return this.f1861f;
    }

    public CharSequence getErrorMessage() {
        return this.f1862g;
    }

    @Nullable
    public Bundle getExtras() {
        return this.f1866k;
    }

    public long getLastPositionUpdateTime() {
        return this.f1863h;
    }

    public float getPlaybackSpeed() {
        return this.f1859d;
    }

    public Object getPlaybackState() {
        ArrayList arrayList;
        if (this.mStateObj == null) {
            if (this.f1864i != null) {
                arrayList = new ArrayList(this.f1864i.size());
                Iterator it = this.f1864i.iterator();
                while (it.hasNext()) {
                    arrayList.add(((CustomAction) it.next()).getCustomAction());
                }
            } else {
                arrayList = null;
            }
            this.mStateObj = PlaybackStateCompatApi22.newInstance(this.f1856a, this.f1857b, this.f1858c, this.f1859d, this.f1860e, this.f1862g, this.f1863h, arrayList, this.f1865j, this.f1866k);
        }
        return this.mStateObj;
    }

    public long getPosition() {
        return this.f1857b;
    }

    public int getState() {
        return this.f1856a;
    }

    public String toString() {
        return "PlaybackState {state=" + this.f1856a + ", position=" + this.f1857b + ", buffered position=" + this.f1858c + ", speed=" + this.f1859d + ", updated=" + this.f1863h + ", actions=" + this.f1860e + ", error code=" + this.f1861f + ", error message=" + this.f1862g + ", custom actions=" + this.f1864i + ", active item id=" + this.f1865j + i.f9804d;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f1856a);
        parcel.writeLong(this.f1857b);
        parcel.writeFloat(this.f1859d);
        parcel.writeLong(this.f1863h);
        parcel.writeLong(this.f1858c);
        parcel.writeLong(this.f1860e);
        TextUtils.writeToParcel(this.f1862g, parcel, i2);
        parcel.writeTypedList(this.f1864i);
        parcel.writeLong(this.f1865j);
        parcel.writeBundle(this.f1866k);
        parcel.writeInt(this.f1861f);
    }

    public static final class CustomAction implements Parcelable {
        public static final Parcelable.Creator<CustomAction> CREATOR = new Parcelable.Creator<CustomAction>() { // from class: android.support.v4.media.session.PlaybackStateCompat.CustomAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomAction createFromParcel(Parcel parcel) {
                return new CustomAction(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomAction[] newArray(int i2) {
                return new CustomAction[i2];
            }
        };
        private final String mAction;
        private Object mCustomActionObj;
        private final Bundle mExtras;
        private final int mIcon;
        private final CharSequence mName;

        public static final class Builder {
            private final String mAction;
            private Bundle mExtras;
            private final int mIcon;
            private final CharSequence mName;

            public Builder(String str, CharSequence charSequence, int i2) {
                if (TextUtils.isEmpty(str)) {
                    throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
                }
                if (TextUtils.isEmpty(charSequence)) {
                    throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
                }
                if (i2 == 0) {
                    throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
                }
                this.mAction = str;
                this.mName = charSequence;
                this.mIcon = i2;
            }

            public CustomAction build() {
                return new CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }
        }

        CustomAction(String str, CharSequence charSequence, int i2, Bundle bundle) {
            this.mAction = str;
            this.mName = charSequence;
            this.mIcon = i2;
            this.mExtras = bundle;
        }

        public static CustomAction fromCustomAction(Object obj) {
            if (obj == null) {
                return null;
            }
            CustomAction customAction = new CustomAction(PlaybackStateCompatApi21.CustomAction.getAction(obj), PlaybackStateCompatApi21.CustomAction.getName(obj), PlaybackStateCompatApi21.CustomAction.getIcon(obj), PlaybackStateCompatApi21.CustomAction.getExtras(obj));
            customAction.mCustomActionObj = obj;
            return customAction;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getAction() {
            return this.mAction;
        }

        public Object getCustomAction() {
            Object obj = this.mCustomActionObj;
            if (obj != null) {
                return obj;
            }
            Object objNewInstance = PlaybackStateCompatApi21.CustomAction.newInstance(this.mAction, this.mName, this.mIcon, this.mExtras);
            this.mCustomActionObj = objNewInstance;
            return objNewInstance;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public CharSequence getName() {
            return this.mName;
        }

        public String toString() {
            return "Action:mName='" + ((Object) this.mName) + ", mIcon=" + this.mIcon + ", mExtras=" + this.mExtras;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.mAction);
            TextUtils.writeToParcel(this.mName, parcel, i2);
            parcel.writeInt(this.mIcon);
            parcel.writeBundle(this.mExtras);
        }

        CustomAction(Parcel parcel) {
            this.mAction = parcel.readString();
            this.mName = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mIcon = parcel.readInt();
            this.mExtras = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
        }
    }

    PlaybackStateCompat(Parcel parcel) {
        this.f1856a = parcel.readInt();
        this.f1857b = parcel.readLong();
        this.f1859d = parcel.readFloat();
        this.f1863h = parcel.readLong();
        this.f1858c = parcel.readLong();
        this.f1860e = parcel.readLong();
        this.f1862g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.f1864i = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.f1865j = parcel.readLong();
        this.f1866k = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
        this.f1861f = parcel.readInt();
    }
}

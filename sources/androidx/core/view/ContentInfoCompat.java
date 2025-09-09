package androidx.core.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.ContentInfo;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class ContentInfoCompat {
    public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
    public static final int SOURCE_APP = 0;
    public static final int SOURCE_AUTOFILL = 4;
    public static final int SOURCE_CLIPBOARD = 1;
    public static final int SOURCE_DRAG_AND_DROP = 3;
    public static final int SOURCE_INPUT_METHOD = 2;
    public static final int SOURCE_PROCESS_TEXT = 5;

    @NonNull
    private final Compat mCompat;

    @RequiresApi(31)
    private static final class Api31Impl {
        private Api31Impl() {
        }

        @NonNull
        @DoNotInline
        public static Pair<ContentInfo, ContentInfo> partition(@NonNull ContentInfo contentInfo, @NonNull final Predicate<ClipData.Item> predicate) {
            ClipData clip = contentInfo.getClip();
            if (clip.getItemCount() != 1) {
                Objects.requireNonNull(predicate);
                Pair pairC = ContentInfoCompat.c(clip, new androidx.core.util.Predicate() { // from class: androidx.core.view.b
                    @Override // androidx.core.util.Predicate
                    public /* synthetic */ androidx.core.util.Predicate and(androidx.core.util.Predicate predicate2) {
                        return androidx.core.util.k.a(this, predicate2);
                    }

                    @Override // androidx.core.util.Predicate
                    public /* synthetic */ androidx.core.util.Predicate negate() {
                        return androidx.core.util.k.b(this);
                    }

                    @Override // androidx.core.util.Predicate
                    public /* synthetic */ androidx.core.util.Predicate or(androidx.core.util.Predicate predicate2) {
                        return androidx.core.util.k.c(this, predicate2);
                    }

                    @Override // androidx.core.util.Predicate
                    public final boolean test(Object obj) {
                        return predicate.test((ClipData.Item) obj);
                    }
                });
                return pairC.first == null ? Pair.create(null, contentInfo) : pairC.second == null ? Pair.create(contentInfo, null) : Pair.create(new ContentInfo.Builder(contentInfo).setClip((ClipData) pairC.first).build(), new ContentInfo.Builder(contentInfo).setClip((ClipData) pairC.second).build());
            }
            boolean zTest = predicate.test(clip.getItemAt(0));
            ContentInfo contentInfo2 = zTest ? contentInfo : null;
            if (zTest) {
                contentInfo = null;
            }
            return Pair.create(contentInfo2, contentInfo);
        }
    }

    private interface BuilderCompat {
        @NonNull
        ContentInfoCompat build();

        void setClip(@NonNull ClipData clipData);

        void setExtras(@Nullable Bundle bundle);

        void setFlags(int i2);

        void setLinkUri(@Nullable Uri uri);

        void setSource(int i2);
    }

    private interface Compat {
        @NonNull
        ClipData getClip();

        @Nullable
        Bundle getExtras();

        int getFlags();

        @Nullable
        Uri getLinkUri();

        int getSource();

        @Nullable
        ContentInfo getWrapped();
    }

    @RequiresApi(31)
    private static final class Compat31Impl implements Compat {

        @NonNull
        private final ContentInfo mWrapped;

        Compat31Impl(ContentInfo contentInfo) {
            this.mWrapped = a.a(Preconditions.checkNotNull(contentInfo));
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        @NonNull
        public ClipData getClip() {
            return this.mWrapped.getClip();
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        @Nullable
        public Bundle getExtras() {
            return this.mWrapped.getExtras();
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        public int getFlags() {
            return this.mWrapped.getFlags();
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        @Nullable
        public Uri getLinkUri() {
            return this.mWrapped.getLinkUri();
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        public int getSource() {
            return this.mWrapped.getSource();
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        @NonNull
        public ContentInfo getWrapped() {
            return this.mWrapped;
        }

        @NonNull
        public String toString() {
            return "ContentInfoCompat{" + this.mWrapped + com.alipay.sdk.m.u.i.f9804d;
        }
    }

    private static final class CompatImpl implements Compat {

        @NonNull
        private final ClipData mClip;

        @Nullable
        private final Bundle mExtras;
        private final int mFlags;

        @Nullable
        private final Uri mLinkUri;
        private final int mSource;

        CompatImpl(BuilderCompatImpl builderCompatImpl) {
            this.mClip = (ClipData) Preconditions.checkNotNull(builderCompatImpl.f3743a);
            this.mSource = Preconditions.checkArgumentInRange(builderCompatImpl.f3744b, 0, 5, "source");
            this.mFlags = Preconditions.checkFlagsArgument(builderCompatImpl.f3745c, 1);
            this.mLinkUri = builderCompatImpl.f3746d;
            this.mExtras = builderCompatImpl.f3747e;
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        @NonNull
        public ClipData getClip() {
            return this.mClip;
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        @Nullable
        public Bundle getExtras() {
            return this.mExtras;
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        public int getFlags() {
            return this.mFlags;
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        @Nullable
        public Uri getLinkUri() {
            return this.mLinkUri;
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        public int getSource() {
            return this.mSource;
        }

        @Override // androidx.core.view.ContentInfoCompat.Compat
        @Nullable
        public ContentInfo getWrapped() {
            return null;
        }

        @NonNull
        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("ContentInfoCompat{clip=");
            sb.append(this.mClip.getDescription());
            sb.append(", source=");
            sb.append(ContentInfoCompat.d(this.mSource));
            sb.append(", flags=");
            sb.append(ContentInfoCompat.b(this.mFlags));
            if (this.mLinkUri == null) {
                str = "";
            } else {
                str = ", hasLinkUri(" + this.mLinkUri.toString().length() + ")";
            }
            sb.append(str);
            sb.append(this.mExtras != null ? ", hasExtras" : "");
            sb.append(com.alipay.sdk.m.u.i.f9804d);
            return sb.toString();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface Source {
    }

    ContentInfoCompat(Compat compat) {
        this.mCompat = compat;
    }

    static ClipData a(ClipDescription clipDescription, List list) {
        ClipData clipData = new ClipData(new ClipDescription(clipDescription), (ClipData.Item) list.get(0));
        for (int i2 = 1; i2 < list.size(); i2++) {
            clipData.addItem((ClipData.Item) list.get(i2));
        }
        return clipData;
    }

    static String b(int i2) {
        return (i2 & 1) != 0 ? "FLAG_CONVERT_TO_PLAIN_TEXT" : String.valueOf(i2);
    }

    static Pair c(ClipData clipData, androidx.core.util.Predicate predicate) {
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        for (int i2 = 0; i2 < clipData.getItemCount(); i2++) {
            ClipData.Item itemAt = clipData.getItemAt(i2);
            if (predicate.test(itemAt)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(itemAt);
            } else {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                }
                arrayList2.add(itemAt);
            }
        }
        return arrayList == null ? Pair.create(null, clipData) : arrayList2 == null ? Pair.create(clipData, null) : Pair.create(a(clipData.getDescription(), arrayList), a(clipData.getDescription(), arrayList2));
    }

    static String d(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? String.valueOf(i2) : "SOURCE_PROCESS_TEXT" : "SOURCE_AUTOFILL" : "SOURCE_DRAG_AND_DROP" : "SOURCE_INPUT_METHOD" : "SOURCE_CLIPBOARD" : "SOURCE_APP";
    }

    @NonNull
    @RequiresApi(31)
    public static ContentInfoCompat toContentInfoCompat(@NonNull ContentInfo contentInfo) {
        return new ContentInfoCompat(new Compat31Impl(contentInfo));
    }

    @NonNull
    public ClipData getClip() {
        return this.mCompat.getClip();
    }

    @Nullable
    public Bundle getExtras() {
        return this.mCompat.getExtras();
    }

    public int getFlags() {
        return this.mCompat.getFlags();
    }

    @Nullable
    public Uri getLinkUri() {
        return this.mCompat.getLinkUri();
    }

    public int getSource() {
        return this.mCompat.getSource();
    }

    @NonNull
    public Pair<ContentInfoCompat, ContentInfoCompat> partition(@NonNull androidx.core.util.Predicate<ClipData.Item> predicate) {
        ClipData clip = this.mCompat.getClip();
        if (clip.getItemCount() == 1) {
            boolean zTest = predicate.test(clip.getItemAt(0));
            return Pair.create(zTest ? this : null, zTest ? null : this);
        }
        Pair pairC = c(clip, predicate);
        return pairC.first == null ? Pair.create(null, this) : pairC.second == null ? Pair.create(this, null) : Pair.create(new Builder(this).setClip((ClipData) pairC.first).build(), new Builder(this).setClip((ClipData) pairC.second).build());
    }

    @NonNull
    @RequiresApi(31)
    public ContentInfo toContentInfo() {
        ContentInfo wrapped = this.mCompat.getWrapped();
        Objects.requireNonNull(wrapped);
        return a.a(wrapped);
    }

    @NonNull
    public String toString() {
        return this.mCompat.toString();
    }

    @RequiresApi(31)
    private static final class BuilderCompat31Impl implements BuilderCompat {

        @NonNull
        private final ContentInfo.Builder mPlatformBuilder;

        BuilderCompat31Impl(ClipData clipData, int i2) {
            this.mPlatformBuilder = i.a(clipData, i2);
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        @NonNull
        public ContentInfoCompat build() {
            return new ContentInfoCompat(new Compat31Impl(this.mPlatformBuilder.build()));
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setClip(@NonNull ClipData clipData) {
            this.mPlatformBuilder.setClip(clipData);
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setExtras(@Nullable Bundle bundle) {
            this.mPlatformBuilder.setExtras(bundle);
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setFlags(int i2) {
            this.mPlatformBuilder.setFlags(i2);
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setLinkUri(@Nullable Uri uri) {
            this.mPlatformBuilder.setLinkUri(uri);
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setSource(int i2) {
            this.mPlatformBuilder.setSource(i2);
        }

        BuilderCompat31Impl(ContentInfoCompat contentInfoCompat) {
            k.a();
            this.mPlatformBuilder = j.a(contentInfoCompat.toContentInfo());
        }
    }

    private static final class BuilderCompatImpl implements BuilderCompat {

        /* renamed from: a, reason: collision with root package name */
        ClipData f3743a;

        /* renamed from: b, reason: collision with root package name */
        int f3744b;

        /* renamed from: c, reason: collision with root package name */
        int f3745c;

        /* renamed from: d, reason: collision with root package name */
        Uri f3746d;

        /* renamed from: e, reason: collision with root package name */
        Bundle f3747e;

        BuilderCompatImpl(ClipData clipData, int i2) {
            this.f3743a = clipData;
            this.f3744b = i2;
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        @NonNull
        public ContentInfoCompat build() {
            return new ContentInfoCompat(new CompatImpl(this));
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setClip(@NonNull ClipData clipData) {
            this.f3743a = clipData;
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setExtras(@Nullable Bundle bundle) {
            this.f3747e = bundle;
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setFlags(int i2) {
            this.f3745c = i2;
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setLinkUri(@Nullable Uri uri) {
            this.f3746d = uri;
        }

        @Override // androidx.core.view.ContentInfoCompat.BuilderCompat
        public void setSource(int i2) {
            this.f3744b = i2;
        }

        BuilderCompatImpl(ContentInfoCompat contentInfoCompat) {
            this.f3743a = contentInfoCompat.getClip();
            this.f3744b = contentInfoCompat.getSource();
            this.f3745c = contentInfoCompat.getFlags();
            this.f3746d = contentInfoCompat.getLinkUri();
            this.f3747e = contentInfoCompat.getExtras();
        }
    }

    public static final class Builder {

        @NonNull
        private final BuilderCompat mBuilderCompat;

        public Builder(@NonNull ContentInfoCompat contentInfoCompat) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.mBuilderCompat = new BuilderCompat31Impl(contentInfoCompat);
            } else {
                this.mBuilderCompat = new BuilderCompatImpl(contentInfoCompat);
            }
        }

        @NonNull
        public ContentInfoCompat build() {
            return this.mBuilderCompat.build();
        }

        @NonNull
        public Builder setClip(@NonNull ClipData clipData) {
            this.mBuilderCompat.setClip(clipData);
            return this;
        }

        @NonNull
        public Builder setExtras(@Nullable Bundle bundle) {
            this.mBuilderCompat.setExtras(bundle);
            return this;
        }

        @NonNull
        public Builder setFlags(int i2) {
            this.mBuilderCompat.setFlags(i2);
            return this;
        }

        @NonNull
        public Builder setLinkUri(@Nullable Uri uri) {
            this.mBuilderCompat.setLinkUri(uri);
            return this;
        }

        @NonNull
        public Builder setSource(int i2) {
            this.mBuilderCompat.setSource(i2);
            return this;
        }

        public Builder(@NonNull ClipData clipData, int i2) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.mBuilderCompat = new BuilderCompat31Impl(clipData, i2);
            } else {
                this.mBuilderCompat = new BuilderCompatImpl(clipData, i2);
            }
        }
    }

    @NonNull
    @RequiresApi(31)
    public static Pair<ContentInfo, ContentInfo> partition(@NonNull ContentInfo contentInfo, @NonNull Predicate<ClipData.Item> predicate) {
        return Api31Impl.partition(contentInfo, predicate);
    }
}

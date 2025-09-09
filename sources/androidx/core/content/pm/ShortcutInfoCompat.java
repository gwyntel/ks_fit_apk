package androidx.core.content.pm;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArraySet;
import androidx.core.app.Person;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.net.UriCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ShortcutInfoCompat {
    private static final String EXTRA_LOCUS_ID = "extraLocusId";
    private static final String EXTRA_LONG_LIVED = "extraLongLived";
    private static final String EXTRA_PERSON_ = "extraPerson_";
    private static final String EXTRA_PERSON_COUNT = "extraPersonCount";
    private static final String EXTRA_SLICE_URI = "extraSliceUri";
    public static final int SURFACE_LAUNCHER = 1;
    int A;
    int B;

    /* renamed from: a, reason: collision with root package name */
    Context f3512a;

    /* renamed from: b, reason: collision with root package name */
    String f3513b;

    /* renamed from: c, reason: collision with root package name */
    String f3514c;

    /* renamed from: d, reason: collision with root package name */
    Intent[] f3515d;

    /* renamed from: e, reason: collision with root package name */
    ComponentName f3516e;

    /* renamed from: f, reason: collision with root package name */
    CharSequence f3517f;

    /* renamed from: g, reason: collision with root package name */
    CharSequence f3518g;

    /* renamed from: h, reason: collision with root package name */
    CharSequence f3519h;

    /* renamed from: i, reason: collision with root package name */
    IconCompat f3520i;

    /* renamed from: j, reason: collision with root package name */
    boolean f3521j;

    /* renamed from: k, reason: collision with root package name */
    Person[] f3522k;

    /* renamed from: l, reason: collision with root package name */
    Set f3523l;

    /* renamed from: m, reason: collision with root package name */
    LocusIdCompat f3524m;

    /* renamed from: n, reason: collision with root package name */
    boolean f3525n;

    /* renamed from: o, reason: collision with root package name */
    int f3526o;

    /* renamed from: p, reason: collision with root package name */
    PersistableBundle f3527p;

    /* renamed from: q, reason: collision with root package name */
    Bundle f3528q;

    /* renamed from: r, reason: collision with root package name */
    long f3529r;

    /* renamed from: s, reason: collision with root package name */
    UserHandle f3530s;

    /* renamed from: t, reason: collision with root package name */
    boolean f3531t;

    /* renamed from: u, reason: collision with root package name */
    boolean f3532u;

    /* renamed from: v, reason: collision with root package name */
    boolean f3533v;

    /* renamed from: w, reason: collision with root package name */
    boolean f3534w;

    /* renamed from: x, reason: collision with root package name */
    boolean f3535x;

    /* renamed from: y, reason: collision with root package name */
    boolean f3536y = true;

    /* renamed from: z, reason: collision with root package name */
    boolean f3537z;

    @RequiresApi(33)
    private static class Api33Impl {
        private Api33Impl() {
        }

        static void a(ShortcutInfo.Builder builder, int i2) {
            builder.setExcludedFromSurfaces(i2);
        }
    }

    public static class Builder {
        private Map<String, Map<String, List<String>>> mCapabilityBindingParams;
        private Set<String> mCapabilityBindings;
        private final ShortcutInfoCompat mInfo;
        private boolean mIsConversation;
        private Uri mSliceUri;

        public Builder(@NonNull Context context, @NonNull String str) {
            ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
            this.mInfo = shortcutInfoCompat;
            shortcutInfoCompat.f3512a = context;
            shortcutInfoCompat.f3513b = str;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder addCapabilityBinding(@NonNull String str) {
            if (this.mCapabilityBindings == null) {
                this.mCapabilityBindings = new HashSet();
            }
            this.mCapabilityBindings.add(str);
            return this;
        }

        @NonNull
        public ShortcutInfoCompat build() {
            if (TextUtils.isEmpty(this.mInfo.f3517f)) {
                throw new IllegalArgumentException("Shortcut must have a non-empty label");
            }
            ShortcutInfoCompat shortcutInfoCompat = this.mInfo;
            Intent[] intentArr = shortcutInfoCompat.f3515d;
            if (intentArr == null || intentArr.length == 0) {
                throw new IllegalArgumentException("Shortcut must have an intent");
            }
            if (this.mIsConversation) {
                if (shortcutInfoCompat.f3524m == null) {
                    shortcutInfoCompat.f3524m = new LocusIdCompat(shortcutInfoCompat.f3513b);
                }
                this.mInfo.f3525n = true;
            }
            if (this.mCapabilityBindings != null) {
                ShortcutInfoCompat shortcutInfoCompat2 = this.mInfo;
                if (shortcutInfoCompat2.f3523l == null) {
                    shortcutInfoCompat2.f3523l = new HashSet();
                }
                this.mInfo.f3523l.addAll(this.mCapabilityBindings);
            }
            if (this.mCapabilityBindingParams != null) {
                ShortcutInfoCompat shortcutInfoCompat3 = this.mInfo;
                if (shortcutInfoCompat3.f3527p == null) {
                    shortcutInfoCompat3.f3527p = new PersistableBundle();
                }
                for (String str : this.mCapabilityBindingParams.keySet()) {
                    Map<String, List<String>> map = this.mCapabilityBindingParams.get(str);
                    this.mInfo.f3527p.putStringArray(str, (String[]) map.keySet().toArray(new String[0]));
                    for (String str2 : map.keySet()) {
                        List<String> list = map.get(str2);
                        this.mInfo.f3527p.putStringArray(str + "/" + str2, list == null ? new String[0] : (String[]) list.toArray(new String[0]));
                    }
                }
            }
            if (this.mSliceUri != null) {
                ShortcutInfoCompat shortcutInfoCompat4 = this.mInfo;
                if (shortcutInfoCompat4.f3527p == null) {
                    shortcutInfoCompat4.f3527p = new PersistableBundle();
                }
                this.mInfo.f3527p.putString(ShortcutInfoCompat.EXTRA_SLICE_URI, UriCompat.toSafeString(this.mSliceUri));
            }
            return this.mInfo;
        }

        @NonNull
        public Builder setActivity(@NonNull ComponentName componentName) {
            this.mInfo.f3516e = componentName;
            return this;
        }

        @NonNull
        public Builder setAlwaysBadged() {
            this.mInfo.f3521j = true;
            return this;
        }

        @NonNull
        public Builder setCategories(@NonNull Set<String> set) {
            ArraySet arraySet = new ArraySet();
            arraySet.addAll(set);
            this.mInfo.f3523l = arraySet;
            return this;
        }

        @NonNull
        public Builder setDisabledMessage(@NonNull CharSequence charSequence) {
            this.mInfo.f3519h = charSequence;
            return this;
        }

        @NonNull
        public Builder setExcludedFromSurfaces(int i2) {
            this.mInfo.B = i2;
            return this;
        }

        @NonNull
        public Builder setExtras(@NonNull PersistableBundle persistableBundle) {
            this.mInfo.f3527p = persistableBundle;
            return this;
        }

        @NonNull
        public Builder setIcon(IconCompat iconCompat) {
            this.mInfo.f3520i = iconCompat;
            return this;
        }

        @NonNull
        public Builder setIntent(@NonNull Intent intent) {
            return setIntents(new Intent[]{intent});
        }

        @NonNull
        public Builder setIntents(@NonNull Intent[] intentArr) {
            this.mInfo.f3515d = intentArr;
            return this;
        }

        @NonNull
        public Builder setIsConversation() {
            this.mIsConversation = true;
            return this;
        }

        @NonNull
        public Builder setLocusId(@Nullable LocusIdCompat locusIdCompat) {
            this.mInfo.f3524m = locusIdCompat;
            return this;
        }

        @NonNull
        public Builder setLongLabel(@NonNull CharSequence charSequence) {
            this.mInfo.f3518g = charSequence;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setLongLived() {
            this.mInfo.f3525n = true;
            return this;
        }

        @NonNull
        public Builder setPerson(@NonNull Person person) {
            return setPersons(new Person[]{person});
        }

        @NonNull
        public Builder setPersons(@NonNull Person[] personArr) {
            this.mInfo.f3522k = personArr;
            return this;
        }

        @NonNull
        public Builder setRank(int i2) {
            this.mInfo.f3526o = i2;
            return this;
        }

        @NonNull
        public Builder setShortLabel(@NonNull CharSequence charSequence) {
            this.mInfo.f3517f = charSequence;
            return this;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder setSliceUri(@NonNull Uri uri) {
            this.mSliceUri = uri;
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder setTransientExtras(@NonNull Bundle bundle) {
            this.mInfo.f3528q = (Bundle) Preconditions.checkNotNull(bundle);
            return this;
        }

        @NonNull
        public Builder setLongLived(boolean z2) {
            this.mInfo.f3525n = z2;
            return this;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder addCapabilityBinding(@NonNull String str, @NonNull String str2, @NonNull List<String> list) {
            addCapabilityBinding(str);
            if (!list.isEmpty()) {
                if (this.mCapabilityBindingParams == null) {
                    this.mCapabilityBindingParams = new HashMap();
                }
                if (this.mCapabilityBindingParams.get(str) == null) {
                    this.mCapabilityBindingParams.put(str, new HashMap());
                }
                this.mCapabilityBindingParams.get(str).put(str2, list);
            }
            return this;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull ShortcutInfoCompat shortcutInfoCompat) {
            ShortcutInfoCompat shortcutInfoCompat2 = new ShortcutInfoCompat();
            this.mInfo = shortcutInfoCompat2;
            shortcutInfoCompat2.f3512a = shortcutInfoCompat.f3512a;
            shortcutInfoCompat2.f3513b = shortcutInfoCompat.f3513b;
            shortcutInfoCompat2.f3514c = shortcutInfoCompat.f3514c;
            Intent[] intentArr = shortcutInfoCompat.f3515d;
            shortcutInfoCompat2.f3515d = (Intent[]) Arrays.copyOf(intentArr, intentArr.length);
            shortcutInfoCompat2.f3516e = shortcutInfoCompat.f3516e;
            shortcutInfoCompat2.f3517f = shortcutInfoCompat.f3517f;
            shortcutInfoCompat2.f3518g = shortcutInfoCompat.f3518g;
            shortcutInfoCompat2.f3519h = shortcutInfoCompat.f3519h;
            shortcutInfoCompat2.A = shortcutInfoCompat.A;
            shortcutInfoCompat2.f3520i = shortcutInfoCompat.f3520i;
            shortcutInfoCompat2.f3521j = shortcutInfoCompat.f3521j;
            shortcutInfoCompat2.f3530s = shortcutInfoCompat.f3530s;
            shortcutInfoCompat2.f3529r = shortcutInfoCompat.f3529r;
            shortcutInfoCompat2.f3531t = shortcutInfoCompat.f3531t;
            shortcutInfoCompat2.f3532u = shortcutInfoCompat.f3532u;
            shortcutInfoCompat2.f3533v = shortcutInfoCompat.f3533v;
            shortcutInfoCompat2.f3534w = shortcutInfoCompat.f3534w;
            shortcutInfoCompat2.f3535x = shortcutInfoCompat.f3535x;
            shortcutInfoCompat2.f3536y = shortcutInfoCompat.f3536y;
            shortcutInfoCompat2.f3524m = shortcutInfoCompat.f3524m;
            shortcutInfoCompat2.f3525n = shortcutInfoCompat.f3525n;
            shortcutInfoCompat2.f3537z = shortcutInfoCompat.f3537z;
            shortcutInfoCompat2.f3526o = shortcutInfoCompat.f3526o;
            Person[] personArr = shortcutInfoCompat.f3522k;
            if (personArr != null) {
                shortcutInfoCompat2.f3522k = (Person[]) Arrays.copyOf(personArr, personArr.length);
            }
            if (shortcutInfoCompat.f3523l != null) {
                shortcutInfoCompat2.f3523l = new HashSet(shortcutInfoCompat.f3523l);
            }
            PersistableBundle persistableBundle = shortcutInfoCompat.f3527p;
            if (persistableBundle != null) {
                shortcutInfoCompat2.f3527p = persistableBundle;
            }
            shortcutInfoCompat2.B = shortcutInfoCompat.B;
        }

        @RequiresApi(25)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull Context context, @NonNull ShortcutInfo shortcutInfo) {
            ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
            this.mInfo = shortcutInfoCompat;
            shortcutInfoCompat.f3512a = context;
            shortcutInfoCompat.f3513b = shortcutInfo.getId();
            shortcutInfoCompat.f3514c = shortcutInfo.getPackage();
            Intent[] intents = shortcutInfo.getIntents();
            shortcutInfoCompat.f3515d = (Intent[]) Arrays.copyOf(intents, intents.length);
            shortcutInfoCompat.f3516e = shortcutInfo.getActivity();
            shortcutInfoCompat.f3517f = shortcutInfo.getShortLabel();
            shortcutInfoCompat.f3518g = shortcutInfo.getLongLabel();
            shortcutInfoCompat.f3519h = shortcutInfo.getDisabledMessage();
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 28) {
                shortcutInfoCompat.A = shortcutInfo.getDisabledReason();
            } else {
                shortcutInfoCompat.A = shortcutInfo.isEnabled() ? 0 : 3;
            }
            shortcutInfoCompat.f3523l = shortcutInfo.getCategories();
            shortcutInfoCompat.f3522k = ShortcutInfoCompat.d(shortcutInfo.getExtras());
            shortcutInfoCompat.f3530s = shortcutInfo.getUserHandle();
            shortcutInfoCompat.f3529r = shortcutInfo.getLastChangedTimestamp();
            if (i2 >= 30) {
                shortcutInfoCompat.f3531t = shortcutInfo.isCached();
            }
            shortcutInfoCompat.f3532u = shortcutInfo.isDynamic();
            shortcutInfoCompat.f3533v = shortcutInfo.isPinned();
            shortcutInfoCompat.f3534w = shortcutInfo.isDeclaredInManifest();
            shortcutInfoCompat.f3535x = shortcutInfo.isImmutable();
            shortcutInfoCompat.f3536y = shortcutInfo.isEnabled();
            shortcutInfoCompat.f3537z = shortcutInfo.hasKeyFieldsOnly();
            shortcutInfoCompat.f3524m = ShortcutInfoCompat.c(shortcutInfo);
            shortcutInfoCompat.f3526o = shortcutInfo.getRank();
            shortcutInfoCompat.f3527p = shortcutInfo.getExtras();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface Surface {
    }

    ShortcutInfoCompat() {
    }

    static List b(Context context, List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new Builder(context, f.a(it.next())).build());
        }
        return arrayList;
    }

    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private PersistableBundle buildLegacyExtrasBundle() {
        if (this.f3527p == null) {
            this.f3527p = new PersistableBundle();
        }
        Person[] personArr = this.f3522k;
        if (personArr != null && personArr.length > 0) {
            this.f3527p.putInt(EXTRA_PERSON_COUNT, personArr.length);
            int i2 = 0;
            while (i2 < this.f3522k.length) {
                PersistableBundle persistableBundle = this.f3527p;
                StringBuilder sb = new StringBuilder();
                sb.append(EXTRA_PERSON_);
                int i3 = i2 + 1;
                sb.append(i3);
                persistableBundle.putPersistableBundle(sb.toString(), this.f3522k[i2].toPersistableBundle());
                i2 = i3;
            }
        }
        LocusIdCompat locusIdCompat = this.f3524m;
        if (locusIdCompat != null) {
            this.f3527p.putString(EXTRA_LOCUS_ID, locusIdCompat.getId());
        }
        this.f3527p.putBoolean(EXTRA_LONG_LIVED, this.f3525n);
        return this.f3527p;
    }

    static LocusIdCompat c(ShortcutInfo shortcutInfo) {
        if (Build.VERSION.SDK_INT < 29) {
            return getLocusIdFromExtra(shortcutInfo.getExtras());
        }
        if (shortcutInfo.getLocusId() == null) {
            return null;
        }
        return LocusIdCompat.toLocusIdCompat(shortcutInfo.getLocusId());
    }

    static Person[] d(PersistableBundle persistableBundle) {
        if (persistableBundle == null || !persistableBundle.containsKey(EXTRA_PERSON_COUNT)) {
            return null;
        }
        int i2 = persistableBundle.getInt(EXTRA_PERSON_COUNT);
        Person[] personArr = new Person[i2];
        int i3 = 0;
        while (i3 < i2) {
            StringBuilder sb = new StringBuilder();
            sb.append(EXTRA_PERSON_);
            int i4 = i3 + 1;
            sb.append(i4);
            personArr[i3] = Person.fromPersistableBundle(persistableBundle.getPersistableBundle(sb.toString()));
            i3 = i4;
        }
        return personArr;
    }

    @Nullable
    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private static LocusIdCompat getLocusIdFromExtra(@Nullable PersistableBundle persistableBundle) {
        String string;
        if (persistableBundle == null || (string = persistableBundle.getString(EXTRA_LOCUS_ID)) == null) {
            return null;
        }
        return new LocusIdCompat(string);
    }

    Intent a(Intent intent) throws PackageManager.NameNotFoundException {
        intent.putExtra("android.intent.extra.shortcut.INTENT", this.f3515d[r0.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.f3517f.toString());
        if (this.f3520i != null) {
            Drawable activityIcon = null;
            if (this.f3521j) {
                PackageManager packageManager = this.f3512a.getPackageManager();
                ComponentName componentName = this.f3516e;
                if (componentName != null) {
                    try {
                        activityIcon = packageManager.getActivityIcon(componentName);
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
                if (activityIcon == null) {
                    activityIcon = this.f3512a.getApplicationInfo().loadIcon(packageManager);
                }
            }
            this.f3520i.addToShortcutIntent(intent, activityIcon, this.f3512a);
        }
        return intent;
    }

    @Nullable
    public ComponentName getActivity() {
        return this.f3516e;
    }

    @Nullable
    public Set<String> getCategories() {
        return this.f3523l;
    }

    @Nullable
    public CharSequence getDisabledMessage() {
        return this.f3519h;
    }

    public int getDisabledReason() {
        return this.A;
    }

    public int getExcludedFromSurfaces() {
        return this.B;
    }

    @Nullable
    public PersistableBundle getExtras() {
        return this.f3527p;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public IconCompat getIcon() {
        return this.f3520i;
    }

    @NonNull
    public String getId() {
        return this.f3513b;
    }

    @NonNull
    public Intent getIntent() {
        return this.f3515d[r0.length - 1];
    }

    @NonNull
    public Intent[] getIntents() {
        Intent[] intentArr = this.f3515d;
        return (Intent[]) Arrays.copyOf(intentArr, intentArr.length);
    }

    public long getLastChangedTimestamp() {
        return this.f3529r;
    }

    @Nullable
    public LocusIdCompat getLocusId() {
        return this.f3524m;
    }

    @Nullable
    public CharSequence getLongLabel() {
        return this.f3518g;
    }

    @NonNull
    public String getPackage() {
        return this.f3514c;
    }

    public int getRank() {
        return this.f3526o;
    }

    @NonNull
    public CharSequence getShortLabel() {
        return this.f3517f;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Bundle getTransientExtras() {
        return this.f3528q;
    }

    @Nullable
    public UserHandle getUserHandle() {
        return this.f3530s;
    }

    public boolean hasKeyFieldsOnly() {
        return this.f3537z;
    }

    public boolean isCached() {
        return this.f3531t;
    }

    public boolean isDeclaredInManifest() {
        return this.f3534w;
    }

    public boolean isDynamic() {
        return this.f3532u;
    }

    public boolean isEnabled() {
        return this.f3536y;
    }

    public boolean isExcludedFromSurfaces(int i2) {
        return (i2 & this.B) != 0;
    }

    public boolean isImmutable() {
        return this.f3535x;
    }

    public boolean isPinned() {
        return this.f3533v;
    }

    @RequiresApi(25)
    public ShortcutInfo toShortcutInfo() {
        j.a();
        ShortcutInfo.Builder intents = i.a(this.f3512a, this.f3513b).setShortLabel(this.f3517f).setIntents(this.f3515d);
        IconCompat iconCompat = this.f3520i;
        if (iconCompat != null) {
            intents.setIcon(iconCompat.toIcon(this.f3512a));
        }
        if (!TextUtils.isEmpty(this.f3518g)) {
            intents.setLongLabel(this.f3518g);
        }
        if (!TextUtils.isEmpty(this.f3519h)) {
            intents.setDisabledMessage(this.f3519h);
        }
        ComponentName componentName = this.f3516e;
        if (componentName != null) {
            intents.setActivity(componentName);
        }
        Set set = this.f3523l;
        if (set != null) {
            intents.setCategories(set);
        }
        intents.setRank(this.f3526o);
        PersistableBundle persistableBundle = this.f3527p;
        if (persistableBundle != null) {
            intents.setExtras(persistableBundle);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            Person[] personArr = this.f3522k;
            if (personArr != null && personArr.length > 0) {
                int length = personArr.length;
                android.app.Person[] personArr2 = new android.app.Person[length];
                for (int i2 = 0; i2 < length; i2++) {
                    personArr2[i2] = this.f3522k[i2].toAndroidPerson();
                }
                intents.setPersons(personArr2);
            }
            LocusIdCompat locusIdCompat = this.f3524m;
            if (locusIdCompat != null) {
                intents.setLocusId(locusIdCompat.toLocusId());
            }
            intents.setLongLived(this.f3525n);
        } else {
            intents.setExtras(buildLegacyExtrasBundle());
        }
        if (Build.VERSION.SDK_INT >= 33) {
            Api33Impl.a(intents, this.B);
        }
        return intents.build();
    }
}

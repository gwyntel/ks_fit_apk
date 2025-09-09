package androidx.core.app;

import android.app.Person;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.IconCompat;
import java.util.Objects;

/* loaded from: classes.dex */
public class Person {
    private static final String ICON_KEY = "icon";
    private static final String IS_BOT_KEY = "isBot";
    private static final String IS_IMPORTANT_KEY = "isImportant";
    private static final String KEY_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String URI_KEY = "uri";

    /* renamed from: a, reason: collision with root package name */
    CharSequence f3483a;

    /* renamed from: b, reason: collision with root package name */
    IconCompat f3484b;

    /* renamed from: c, reason: collision with root package name */
    String f3485c;

    /* renamed from: d, reason: collision with root package name */
    String f3486d;

    /* renamed from: e, reason: collision with root package name */
    boolean f3487e;

    /* renamed from: f, reason: collision with root package name */
    boolean f3488f;

    @RequiresApi(22)
    static class Api22Impl {
        private Api22Impl() {
        }

        @DoNotInline
        static Person a(PersistableBundle persistableBundle) {
            return new Builder().setName(persistableBundle.getString("name")).setUri(persistableBundle.getString("uri")).setKey(persistableBundle.getString("key")).setBot(persistableBundle.getBoolean(Person.IS_BOT_KEY)).setImportant(persistableBundle.getBoolean(Person.IS_IMPORTANT_KEY)).build();
        }

        @DoNotInline
        static PersistableBundle b(Person person) {
            PersistableBundle persistableBundle = new PersistableBundle();
            CharSequence charSequence = person.f3483a;
            persistableBundle.putString("name", charSequence != null ? charSequence.toString() : null);
            persistableBundle.putString("uri", person.f3485c);
            persistableBundle.putString("key", person.f3486d);
            persistableBundle.putBoolean(Person.IS_BOT_KEY, person.f3487e);
            persistableBundle.putBoolean(Person.IS_IMPORTANT_KEY, person.f3488f);
            return persistableBundle;
        }
    }

    @RequiresApi(28)
    static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static Person a(android.app.Person person) {
            return new Builder().setName(person.getName()).setIcon(person.getIcon() != null ? IconCompat.createFromIcon(person.getIcon()) : null).setUri(person.getUri()).setKey(person.getKey()).setBot(person.isBot()).setImportant(person.isImportant()).build();
        }

        @DoNotInline
        static android.app.Person b(Person person) {
            return new Person.Builder().setName(person.getName()).setIcon(person.getIcon() != null ? person.getIcon().toIcon() : null).setUri(person.getUri()).setKey(person.getKey()).setBot(person.isBot()).setImportant(person.isImportant()).build();
        }
    }

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        CharSequence f3489a;

        /* renamed from: b, reason: collision with root package name */
        IconCompat f3490b;

        /* renamed from: c, reason: collision with root package name */
        String f3491c;

        /* renamed from: d, reason: collision with root package name */
        String f3492d;

        /* renamed from: e, reason: collision with root package name */
        boolean f3493e;

        /* renamed from: f, reason: collision with root package name */
        boolean f3494f;

        public Builder() {
        }

        @NonNull
        public Person build() {
            return new Person(this);
        }

        @NonNull
        public Builder setBot(boolean z2) {
            this.f3493e = z2;
            return this;
        }

        @NonNull
        public Builder setIcon(@Nullable IconCompat iconCompat) {
            this.f3490b = iconCompat;
            return this;
        }

        @NonNull
        public Builder setImportant(boolean z2) {
            this.f3494f = z2;
            return this;
        }

        @NonNull
        public Builder setKey(@Nullable String str) {
            this.f3492d = str;
            return this;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.f3489a = charSequence;
            return this;
        }

        @NonNull
        public Builder setUri(@Nullable String str) {
            this.f3491c = str;
            return this;
        }

        Builder(Person person) {
            this.f3489a = person.f3483a;
            this.f3490b = person.f3484b;
            this.f3491c = person.f3485c;
            this.f3492d = person.f3486d;
            this.f3493e = person.f3487e;
            this.f3494f = person.f3488f;
        }
    }

    Person(Builder builder) {
        this.f3483a = builder.f3489a;
        this.f3484b = builder.f3490b;
        this.f3485c = builder.f3491c;
        this.f3486d = builder.f3492d;
        this.f3487e = builder.f3493e;
        this.f3488f = builder.f3494f;
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Person fromAndroidPerson(@NonNull android.app.Person person) {
        return Api28Impl.a(person);
    }

    @NonNull
    public static Person fromBundle(@NonNull Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("icon");
        return new Builder().setName(bundle.getCharSequence("name")).setIcon(bundle2 != null ? IconCompat.createFromBundle(bundle2) : null).setUri(bundle.getString("uri")).setKey(bundle.getString("key")).setBot(bundle.getBoolean(IS_BOT_KEY)).setImportant(bundle.getBoolean(IS_IMPORTANT_KEY)).build();
    }

    @NonNull
    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static Person fromPersistableBundle(@NonNull PersistableBundle persistableBundle) {
        return Api22Impl.a(persistableBundle);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == null || !(obj instanceof Person)) {
            return false;
        }
        Person person = (Person) obj;
        String key = getKey();
        String key2 = person.getKey();
        return (key == null && key2 == null) ? Objects.equals(Objects.toString(getName()), Objects.toString(person.getName())) && Objects.equals(getUri(), person.getUri()) && Boolean.valueOf(isBot()).equals(Boolean.valueOf(person.isBot())) && Boolean.valueOf(isImportant()).equals(Boolean.valueOf(person.isImportant())) : Objects.equals(key, key2);
    }

    @Nullable
    public IconCompat getIcon() {
        return this.f3484b;
    }

    @Nullable
    public String getKey() {
        return this.f3486d;
    }

    @Nullable
    public CharSequence getName() {
        return this.f3483a;
    }

    @Nullable
    public String getUri() {
        return this.f3485c;
    }

    public int hashCode() {
        String key = getKey();
        return key != null ? key.hashCode() : Objects.hash(getName(), getUri(), Boolean.valueOf(isBot()), Boolean.valueOf(isImportant()));
    }

    public boolean isBot() {
        return this.f3487e;
    }

    public boolean isImportant() {
        return this.f3488f;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public String resolveToLegacyUri() {
        String str = this.f3485c;
        if (str != null) {
            return str;
        }
        if (this.f3483a == null) {
            return "";
        }
        return "name:" + ((Object) this.f3483a);
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public android.app.Person toAndroidPerson() {
        return Api28Impl.b(this);
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this);
    }

    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("name", this.f3483a);
        IconCompat iconCompat = this.f3484b;
        bundle.putBundle("icon", iconCompat != null ? iconCompat.toBundle() : null);
        bundle.putString("uri", this.f3485c);
        bundle.putString("key", this.f3486d);
        bundle.putBoolean(IS_BOT_KEY, this.f3487e);
        bundle.putBoolean(IS_IMPORTANT_KEY, this.f3488f);
        return bundle;
    }

    @NonNull
    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public PersistableBundle toPersistableBundle() {
        return Api22Impl.b(this);
    }
}

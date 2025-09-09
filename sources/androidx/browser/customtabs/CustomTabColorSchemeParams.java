package androidx.browser.customtabs;

import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public final class CustomTabColorSchemeParams {

    @Nullable
    @ColorInt
    public final Integer navigationBarColor;

    @Nullable
    @ColorInt
    public final Integer navigationBarDividerColor;

    @Nullable
    @ColorInt
    public final Integer secondaryToolbarColor;

    @Nullable
    @ColorInt
    public final Integer toolbarColor;

    public static final class Builder {

        @Nullable
        @ColorInt
        private Integer mNavigationBarColor;

        @Nullable
        @ColorInt
        private Integer mNavigationBarDividerColor;

        @Nullable
        @ColorInt
        private Integer mSecondaryToolbarColor;

        @Nullable
        @ColorInt
        private Integer mToolbarColor;

        @NonNull
        public CustomTabColorSchemeParams build() {
            return new CustomTabColorSchemeParams(this.mToolbarColor, this.mSecondaryToolbarColor, this.mNavigationBarColor, this.mNavigationBarDividerColor);
        }

        @NonNull
        public Builder setNavigationBarColor(@ColorInt int i2) {
            this.mNavigationBarColor = Integer.valueOf(i2 | ViewCompat.MEASURED_STATE_MASK);
            return this;
        }

        @NonNull
        public Builder setNavigationBarDividerColor(@ColorInt int i2) {
            this.mNavigationBarDividerColor = Integer.valueOf(i2);
            return this;
        }

        @NonNull
        public Builder setSecondaryToolbarColor(@ColorInt int i2) {
            this.mSecondaryToolbarColor = Integer.valueOf(i2);
            return this;
        }

        @NonNull
        public Builder setToolbarColor(@ColorInt int i2) {
            this.mToolbarColor = Integer.valueOf(i2 | ViewCompat.MEASURED_STATE_MASK);
            return this;
        }
    }

    CustomTabColorSchemeParams(Integer num, Integer num2, Integer num3, Integer num4) {
        this.toolbarColor = num;
        this.secondaryToolbarColor = num2;
        this.navigationBarColor = num3;
        this.navigationBarDividerColor = num4;
    }

    static CustomTabColorSchemeParams a(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle(0);
        }
        return new CustomTabColorSchemeParams((Integer) bundle.get(CustomTabsIntent.EXTRA_TOOLBAR_COLOR), (Integer) bundle.get(CustomTabsIntent.EXTRA_SECONDARY_TOOLBAR_COLOR), (Integer) bundle.get(CustomTabsIntent.EXTRA_NAVIGATION_BAR_COLOR), (Integer) bundle.get(CustomTabsIntent.EXTRA_NAVIGATION_BAR_DIVIDER_COLOR));
    }

    Bundle b() {
        Bundle bundle = new Bundle();
        Integer num = this.toolbarColor;
        if (num != null) {
            bundle.putInt(CustomTabsIntent.EXTRA_TOOLBAR_COLOR, num.intValue());
        }
        Integer num2 = this.secondaryToolbarColor;
        if (num2 != null) {
            bundle.putInt(CustomTabsIntent.EXTRA_SECONDARY_TOOLBAR_COLOR, num2.intValue());
        }
        Integer num3 = this.navigationBarColor;
        if (num3 != null) {
            bundle.putInt(CustomTabsIntent.EXTRA_NAVIGATION_BAR_COLOR, num3.intValue());
        }
        Integer num4 = this.navigationBarDividerColor;
        if (num4 != null) {
            bundle.putInt(CustomTabsIntent.EXTRA_NAVIGATION_BAR_DIVIDER_COLOR, num4.intValue());
        }
        return bundle;
    }

    CustomTabColorSchemeParams c(CustomTabColorSchemeParams customTabColorSchemeParams) {
        Integer num = this.toolbarColor;
        if (num == null) {
            num = customTabColorSchemeParams.toolbarColor;
        }
        Integer num2 = this.secondaryToolbarColor;
        if (num2 == null) {
            num2 = customTabColorSchemeParams.secondaryToolbarColor;
        }
        Integer num3 = this.navigationBarColor;
        if (num3 == null) {
            num3 = customTabColorSchemeParams.navigationBarColor;
        }
        Integer num4 = this.navigationBarDividerColor;
        if (num4 == null) {
            num4 = customTabColorSchemeParams.navigationBarDividerColor;
        }
        return new CustomTabColorSchemeParams(num, num2, num3, num4);
    }
}

package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class AccountPicker {

    public static class AccountChooserOptions {

        @Nullable
        private Account zza;
        private boolean zzb;

        @Nullable
        private ArrayList zzc;

        @Nullable
        private ArrayList zzd;
        private boolean zze;

        @Nullable
        private String zzf;

        @Nullable
        private Bundle zzg;
        private boolean zzh;
        private int zzi;

        @Nullable
        private String zzj;
        private boolean zzk;

        @Nullable
        private zza zzl;

        @Nullable
        private String zzm;
        private boolean zzn;
        private boolean zzo;

        public static class Builder {

            @Nullable
            private Account zza;

            @Nullable
            private ArrayList zzb;

            @Nullable
            private ArrayList zzc;
            private boolean zzd = false;

            @Nullable
            private String zze;

            @Nullable
            private Bundle zzf;

            @NonNull
            public AccountChooserOptions build() {
                Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
                Preconditions.checkArgument(true, "Consent is only valid for account chip styled account picker");
                AccountChooserOptions accountChooserOptions = new AccountChooserOptions();
                accountChooserOptions.zzd = this.zzc;
                accountChooserOptions.zzc = this.zzb;
                accountChooserOptions.zze = this.zzd;
                accountChooserOptions.zzl = null;
                accountChooserOptions.zzj = null;
                accountChooserOptions.zzg = this.zzf;
                accountChooserOptions.zza = this.zza;
                accountChooserOptions.zzb = false;
                accountChooserOptions.zzh = false;
                accountChooserOptions.zzm = null;
                accountChooserOptions.zzi = 0;
                accountChooserOptions.zzf = this.zze;
                accountChooserOptions.zzk = false;
                accountChooserOptions.zzn = false;
                accountChooserOptions.zzo = false;
                return accountChooserOptions;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setAllowableAccounts(@Nullable List<Account> list) {
                this.zzb = list == null ? null : new ArrayList(list);
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setAllowableAccountsTypes(@Nullable List<String> list) {
                this.zzc = list == null ? null : new ArrayList(list);
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setAlwaysShowAccountPicker(boolean z2) {
                this.zzd = z2;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setOptionsForAddingAccount(@Nullable Bundle bundle) {
                this.zzf = bundle;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setSelectedAccount(@Nullable Account account) {
                this.zza = account;
                return this;
            }

            @NonNull
            @CanIgnoreReturnValue
            public Builder setTitleOverrideText(@Nullable String str) {
                this.zze = str;
                return this;
            }
        }

        static /* bridge */ /* synthetic */ boolean D(AccountChooserOptions accountChooserOptions) {
            boolean z2 = accountChooserOptions.zzn;
            return false;
        }

        static /* bridge */ /* synthetic */ boolean a(AccountChooserOptions accountChooserOptions) {
            boolean z2 = accountChooserOptions.zzo;
            return false;
        }

        static /* bridge */ /* synthetic */ boolean b(AccountChooserOptions accountChooserOptions) {
            boolean z2 = accountChooserOptions.zzb;
            return false;
        }

        static /* bridge */ /* synthetic */ boolean c(AccountChooserOptions accountChooserOptions) {
            boolean z2 = accountChooserOptions.zzh;
            return false;
        }

        static /* bridge */ /* synthetic */ boolean d(AccountChooserOptions accountChooserOptions) {
            boolean z2 = accountChooserOptions.zzk;
            return false;
        }

        static /* bridge */ /* synthetic */ int e(AccountChooserOptions accountChooserOptions) {
            int i2 = accountChooserOptions.zzi;
            return 0;
        }

        static /* bridge */ /* synthetic */ zza h(AccountChooserOptions accountChooserOptions) {
            zza zzaVar = accountChooserOptions.zzl;
            return null;
        }

        static /* bridge */ /* synthetic */ String i(AccountChooserOptions accountChooserOptions) {
            String str = accountChooserOptions.zzj;
            return null;
        }

        static /* bridge */ /* synthetic */ String j(AccountChooserOptions accountChooserOptions) {
            String str = accountChooserOptions.zzm;
            return null;
        }
    }

    private AccountPicker() {
    }

    @NonNull
    @ResultIgnorabilityUnspecified
    @Deprecated
    public static Intent newChooseAccountIntent(@Nullable Account account, @Nullable ArrayList<Account> arrayList, @Nullable String[] strArr, boolean z2, @Nullable String str, @Nullable String str2, @Nullable String[] strArr2, @Nullable Bundle bundle) {
        Intent intent = new Intent();
        Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
        intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
        intent.setPackage("com.google.android.gms");
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST, arrayList);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY, strArr);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE, bundle);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_SELECTED_ACCOUNT, account);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ALWAYS_PROMPT_FOR_ACCOUNT, z2);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_DESCRIPTION_TEXT_OVERRIDE, str);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING, str2);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY, strArr2);
        intent.putExtra("setGmsCoreAccount", false);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_OVERRIDE_THEME, 0);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_OVERRIDE_CUSTOM_THEME, 0);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_HOSTED_DOMAIN_FILTER, (String) null);
        return intent;
    }

    @NonNull
    public static Intent newChooseAccountIntent(@NonNull AccountChooserOptions accountChooserOptions) {
        Intent intent = new Intent();
        AccountChooserOptions.d(accountChooserOptions);
        AccountChooserOptions.i(accountChooserOptions);
        Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
        AccountChooserOptions.h(accountChooserOptions);
        Preconditions.checkArgument(true, "Consent is only valid for account chip styled account picker");
        AccountChooserOptions.b(accountChooserOptions);
        Preconditions.checkArgument(true, "Making the selected account non-clickable is only supported for the THEME_DAY_NIGHT_GOOGLE_MATERIAL2, THEME_LIGHT_GOOGLE_MATERIAL3, THEME_DARK_GOOGLE_MATERIAL3 or THEME_DAY_NIGHT_GOOGLE_MATERIAL3 themes");
        AccountChooserOptions.d(accountChooserOptions);
        intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
        intent.setPackage("com.google.android.gms");
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST, accountChooserOptions.zzc);
        if (accountChooserOptions.zzd != null) {
            intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY, (String[]) accountChooserOptions.zzd.toArray(new String[0]));
        }
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE, accountChooserOptions.zzg);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_SELECTED_ACCOUNT, accountChooserOptions.zza);
        AccountChooserOptions.b(accountChooserOptions);
        intent.putExtra("selectedAccountIsNotClickable", false);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_ALWAYS_PROMPT_FOR_ACCOUNT, accountChooserOptions.zze);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_DESCRIPTION_TEXT_OVERRIDE, accountChooserOptions.zzf);
        AccountChooserOptions.c(accountChooserOptions);
        intent.putExtra("setGmsCoreAccount", false);
        AccountChooserOptions.j(accountChooserOptions);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_REAL_CLIENT_PACKAGE, (String) null);
        AccountChooserOptions.e(accountChooserOptions);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_OVERRIDE_THEME, 0);
        AccountChooserOptions.d(accountChooserOptions);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_OVERRIDE_CUSTOM_THEME, 0);
        AccountChooserOptions.i(accountChooserOptions);
        intent.putExtra(com.huawei.hms.common.AccountPicker.EXTRA_HOSTED_DOMAIN_FILTER, (String) null);
        Bundle bundle = new Bundle();
        AccountChooserOptions.d(accountChooserOptions);
        AccountChooserOptions.h(accountChooserOptions);
        AccountChooserOptions.D(accountChooserOptions);
        AccountChooserOptions.a(accountChooserOptions);
        if (!bundle.isEmpty()) {
            intent.putExtra("first_party_options_bundle", bundle);
        }
        return intent;
    }
}

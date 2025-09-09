package com.google.android.gms.auth.api.identity;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

@SafeParcelable.Class(creator = "AuthorizationRequestCreator")
/* loaded from: classes3.dex */
public class AuthorizationRequest extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final Parcelable.Creator<AuthorizationRequest> CREATOR = new zbd();

    @SafeParcelable.Field(getter = "getRequestedScopes", id = 1)
    private final List zba;

    @Nullable
    @SafeParcelable.Field(getter = "getServerClientId", id = 2)
    private final String zbb;

    @SafeParcelable.Field(getter = "isOfflineAccessRequested", id = 3)
    private final boolean zbc;

    @SafeParcelable.Field(getter = "isIdTokenRequested", id = 4)
    private final boolean zbd;

    @Nullable
    @SafeParcelable.Field(getter = "getAccount", id = 5)
    private final Account zbe;

    @Nullable
    @SafeParcelable.Field(getter = "getHostedDomain", id = 6)
    private final String zbf;

    @Nullable
    @SafeParcelable.Field(getter = "getSessionId", id = 7)
    private final String zbg;

    @SafeParcelable.Field(getter = "isForceCodeForRefreshToken", id = 8)
    private final boolean zbh;

    public static final class Builder {
        private List zba;

        @Nullable
        private String zbb;
        private boolean zbc;
        private boolean zbd;

        @Nullable
        private Account zbe;

        @Nullable
        private String zbf;

        @Nullable
        private String zbg;
        private boolean zbh;

        private final String zbc(String str) {
            Preconditions.checkNotNull(str);
            String str2 = this.zbb;
            boolean z2 = true;
            if (str2 != null && !str2.equals(str)) {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "two different server client ids provided");
            return str;
        }

        @NonNull
        public AuthorizationRequest build() {
            return new AuthorizationRequest(this.zba, this.zbb, this.zbc, this.zbd, this.zbe, this.zbf, this.zbg, this.zbh);
        }

        @NonNull
        public Builder filterByHostedDomain(@NonNull String str) {
            this.zbf = Preconditions.checkNotEmpty(str);
            return this;
        }

        @NonNull
        public Builder requestOfflineAccess(@NonNull String str) {
            requestOfflineAccess(str, false);
            return this;
        }

        @NonNull
        public Builder setAccount(@NonNull Account account) {
            this.zbe = (Account) Preconditions.checkNotNull(account);
            return this;
        }

        @NonNull
        public Builder setRequestedScopes(@NonNull List<Scope> list) {
            boolean z2 = false;
            if (list != null && !list.isEmpty()) {
                z2 = true;
            }
            Preconditions.checkArgument(z2, "requestedScopes cannot be null or empty");
            this.zba = list;
            return this;
        }

        @NonNull
        @ShowFirstParty
        public final Builder zba(@NonNull String str) {
            zbc(str);
            this.zbb = str;
            this.zbd = true;
            return this;
        }

        @NonNull
        public final Builder zbb(@NonNull String str) {
            this.zbg = str;
            return this;
        }

        @NonNull
        public Builder requestOfflineAccess(@NonNull String str, boolean z2) {
            zbc(str);
            this.zbb = str;
            this.zbc = true;
            this.zbh = z2;
            return this;
        }
    }

    AuthorizationRequest(List list, String str, boolean z2, boolean z3, Account account, String str2, String str3, boolean z4) {
        boolean z5 = false;
        if (list != null && !list.isEmpty()) {
            z5 = true;
        }
        Preconditions.checkArgument(z5, "requestedScopes cannot be null or empty");
        this.zba = list;
        this.zbb = str;
        this.zbc = z2;
        this.zbd = z3;
        this.zbe = account;
        this.zbf = str2;
        this.zbg = str3;
        this.zbh = z4;
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public static Builder zba(@NonNull AuthorizationRequest authorizationRequest) {
        Preconditions.checkNotNull(authorizationRequest);
        Builder builder = builder();
        builder.setRequestedScopes(authorizationRequest.getRequestedScopes());
        boolean zIsForceCodeForRefreshToken = authorizationRequest.isForceCodeForRefreshToken();
        String hostedDomain = authorizationRequest.getHostedDomain();
        Account account = authorizationRequest.getAccount();
        String serverClientId = authorizationRequest.getServerClientId();
        String str = authorizationRequest.zbg;
        if (str != null) {
            builder.zbb(str);
        }
        if (hostedDomain != null) {
            builder.filterByHostedDomain(hostedDomain);
        }
        if (account != null) {
            builder.setAccount(account);
        }
        if (authorizationRequest.zbd && serverClientId != null) {
            builder.zba(serverClientId);
        }
        if (authorizationRequest.isOfflineAccessRequested() && serverClientId != null) {
            builder.requestOfflineAccess(serverClientId, zIsForceCodeForRefreshToken);
        }
        return builder;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AuthorizationRequest)) {
            return false;
        }
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) obj;
        return this.zba.size() == authorizationRequest.zba.size() && this.zba.containsAll(authorizationRequest.zba) && this.zbc == authorizationRequest.zbc && this.zbh == authorizationRequest.zbh && this.zbd == authorizationRequest.zbd && Objects.equal(this.zbb, authorizationRequest.zbb) && Objects.equal(this.zbe, authorizationRequest.zbe) && Objects.equal(this.zbf, authorizationRequest.zbf) && Objects.equal(this.zbg, authorizationRequest.zbg);
    }

    @Nullable
    public Account getAccount() {
        return this.zbe;
    }

    @Nullable
    public String getHostedDomain() {
        return this.zbf;
    }

    @NonNull
    public List<Scope> getRequestedScopes() {
        return this.zba;
    }

    @Nullable
    public String getServerClientId() {
        return this.zbb;
    }

    public int hashCode() {
        return Objects.hashCode(this.zba, this.zbb, Boolean.valueOf(this.zbc), Boolean.valueOf(this.zbh), Boolean.valueOf(this.zbd), this.zbe, this.zbf, this.zbg);
    }

    public boolean isForceCodeForRefreshToken() {
        return this.zbh;
    }

    public boolean isOfflineAccessRequested() {
        return this.zbc;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, getRequestedScopes(), false);
        SafeParcelWriter.writeString(parcel, 2, getServerClientId(), false);
        SafeParcelWriter.writeBoolean(parcel, 3, isOfflineAccessRequested());
        SafeParcelWriter.writeBoolean(parcel, 4, this.zbd);
        SafeParcelWriter.writeParcelable(parcel, 5, getAccount(), i2, false);
        SafeParcelWriter.writeString(parcel, 6, getHostedDomain(), false);
        SafeParcelWriter.writeString(parcel, 7, this.zbg, false);
        SafeParcelWriter.writeBoolean(parcel, 8, isForceCodeForRefreshToken());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}

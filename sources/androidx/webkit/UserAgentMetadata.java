package androidx.webkit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class UserAgentMetadata {
    public static final int BITNESS_DEFAULT = 0;
    private final String mArchitecture;
    private int mBitness;
    private final List<BrandVersion> mBrandVersionList;
    private final String mFullVersion;
    private boolean mMobile;
    private final String mModel;
    private final String mPlatform;
    private final String mPlatformVersion;
    private boolean mWow64;

    public static final class BrandVersion {
        private final String mBrand;
        private final String mFullVersion;
        private final String mMajorVersion;

        public static final class Builder {
            private String mBrand;
            private String mFullVersion;
            private String mMajorVersion;

            public Builder() {
            }

            @NonNull
            public BrandVersion build() {
                String str;
                String str2;
                String str3 = this.mBrand;
                if (str3 == null || str3.trim().isEmpty() || (str = this.mMajorVersion) == null || str.trim().isEmpty() || (str2 = this.mFullVersion) == null || str2.trim().isEmpty()) {
                    throw new IllegalStateException("Brand name, major version and full version should not be null or blank.");
                }
                return new BrandVersion(this.mBrand, this.mMajorVersion, this.mFullVersion);
            }

            @NonNull
            public Builder setBrand(@NonNull String str) {
                if (str.trim().isEmpty()) {
                    throw new IllegalArgumentException("Brand should not be blank.");
                }
                this.mBrand = str;
                return this;
            }

            @NonNull
            public Builder setFullVersion(@NonNull String str) {
                if (str.trim().isEmpty()) {
                    throw new IllegalArgumentException("FullVersion should not be blank.");
                }
                this.mFullVersion = str;
                return this;
            }

            @NonNull
            public Builder setMajorVersion(@NonNull String str) {
                if (str.trim().isEmpty()) {
                    throw new IllegalArgumentException("MajorVersion should not be blank.");
                }
                this.mMajorVersion = str;
                return this;
            }

            public Builder(@NonNull BrandVersion brandVersion) {
                this.mBrand = brandVersion.getBrand();
                this.mMajorVersion = brandVersion.getMajorVersion();
                this.mFullVersion = brandVersion.getFullVersion();
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BrandVersion)) {
                return false;
            }
            BrandVersion brandVersion = (BrandVersion) obj;
            return Objects.equals(this.mBrand, brandVersion.mBrand) && Objects.equals(this.mMajorVersion, brandVersion.mMajorVersion) && Objects.equals(this.mFullVersion, brandVersion.mFullVersion);
        }

        @NonNull
        public String getBrand() {
            return this.mBrand;
        }

        @NonNull
        public String getFullVersion() {
            return this.mFullVersion;
        }

        @NonNull
        public String getMajorVersion() {
            return this.mMajorVersion;
        }

        public int hashCode() {
            return Objects.hash(this.mBrand, this.mMajorVersion, this.mFullVersion);
        }

        @NonNull
        public String toString() {
            return this.mBrand + "," + this.mMajorVersion + "," + this.mFullVersion;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        private BrandVersion(@NonNull String str, @NonNull String str2, @NonNull String str3) {
            this.mBrand = str;
            this.mMajorVersion = str2;
            this.mFullVersion = str3;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserAgentMetadata)) {
            return false;
        }
        UserAgentMetadata userAgentMetadata = (UserAgentMetadata) obj;
        return this.mMobile == userAgentMetadata.mMobile && this.mBitness == userAgentMetadata.mBitness && this.mWow64 == userAgentMetadata.mWow64 && Objects.equals(this.mBrandVersionList, userAgentMetadata.mBrandVersionList) && Objects.equals(this.mFullVersion, userAgentMetadata.mFullVersion) && Objects.equals(this.mPlatform, userAgentMetadata.mPlatform) && Objects.equals(this.mPlatformVersion, userAgentMetadata.mPlatformVersion) && Objects.equals(this.mArchitecture, userAgentMetadata.mArchitecture) && Objects.equals(this.mModel, userAgentMetadata.mModel);
    }

    @Nullable
    public String getArchitecture() {
        return this.mArchitecture;
    }

    public int getBitness() {
        return this.mBitness;
    }

    @NonNull
    public List<BrandVersion> getBrandVersionList() {
        return this.mBrandVersionList;
    }

    @Nullable
    public String getFullVersion() {
        return this.mFullVersion;
    }

    @Nullable
    public String getModel() {
        return this.mModel;
    }

    @Nullable
    public String getPlatform() {
        return this.mPlatform;
    }

    @Nullable
    public String getPlatformVersion() {
        return this.mPlatformVersion;
    }

    public int hashCode() {
        return Objects.hash(this.mBrandVersionList, this.mFullVersion, this.mPlatform, this.mPlatformVersion, this.mArchitecture, this.mModel, Boolean.valueOf(this.mMobile), Integer.valueOf(this.mBitness), Boolean.valueOf(this.mWow64));
    }

    public boolean isMobile() {
        return this.mMobile;
    }

    public boolean isWow64() {
        return this.mWow64;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    private UserAgentMetadata(@NonNull List<BrandVersion> list, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, boolean z2, int i2, boolean z3) {
        this.mBrandVersionList = list;
        this.mFullVersion = str;
        this.mPlatform = str2;
        this.mPlatformVersion = str3;
        this.mArchitecture = str4;
        this.mModel = str5;
        this.mMobile = z2;
        this.mBitness = i2;
        this.mWow64 = z3;
    }

    public static final class Builder {
        private String mArchitecture;
        private int mBitness;
        private List<BrandVersion> mBrandVersionList;
        private String mFullVersion;
        private boolean mMobile;
        private String mModel;
        private String mPlatform;
        private String mPlatformVersion;
        private boolean mWow64;

        public Builder() {
            this.mBrandVersionList = new ArrayList();
            this.mMobile = true;
            this.mBitness = 0;
            this.mWow64 = false;
        }

        @NonNull
        public UserAgentMetadata build() {
            return new UserAgentMetadata(this.mBrandVersionList, this.mFullVersion, this.mPlatform, this.mPlatformVersion, this.mArchitecture, this.mModel, this.mMobile, this.mBitness, this.mWow64);
        }

        @NonNull
        public Builder setArchitecture(@Nullable String str) {
            this.mArchitecture = str;
            return this;
        }

        @NonNull
        public Builder setBitness(int i2) {
            this.mBitness = i2;
            return this;
        }

        @NonNull
        public Builder setBrandVersionList(@NonNull List<BrandVersion> list) {
            this.mBrandVersionList = list;
            return this;
        }

        @NonNull
        public Builder setFullVersion(@Nullable String str) {
            if (str == null) {
                this.mFullVersion = null;
                return this;
            }
            if (str.trim().isEmpty()) {
                throw new IllegalArgumentException("Full version should not be blank.");
            }
            this.mFullVersion = str;
            return this;
        }

        @NonNull
        public Builder setMobile(boolean z2) {
            this.mMobile = z2;
            return this;
        }

        @NonNull
        public Builder setModel(@Nullable String str) {
            this.mModel = str;
            return this;
        }

        @NonNull
        public Builder setPlatform(@Nullable String str) {
            if (str == null) {
                this.mPlatform = null;
                return this;
            }
            if (str.trim().isEmpty()) {
                throw new IllegalArgumentException("Platform should not be blank.");
            }
            this.mPlatform = str;
            return this;
        }

        @NonNull
        public Builder setPlatformVersion(@Nullable String str) {
            this.mPlatformVersion = str;
            return this;
        }

        @NonNull
        public Builder setWow64(boolean z2) {
            this.mWow64 = z2;
            return this;
        }

        public Builder(@NonNull UserAgentMetadata userAgentMetadata) {
            this.mBrandVersionList = new ArrayList();
            this.mMobile = true;
            this.mBitness = 0;
            this.mWow64 = false;
            this.mBrandVersionList = userAgentMetadata.getBrandVersionList();
            this.mFullVersion = userAgentMetadata.getFullVersion();
            this.mPlatform = userAgentMetadata.getPlatform();
            this.mPlatformVersion = userAgentMetadata.getPlatformVersion();
            this.mArchitecture = userAgentMetadata.getArchitecture();
            this.mModel = userAgentMetadata.getModel();
            this.mMobile = userAgentMetadata.isMobile();
            this.mBitness = userAgentMetadata.getBitness();
            this.mWow64 = userAgentMetadata.isWow64();
        }
    }
}

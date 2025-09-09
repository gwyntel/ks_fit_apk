package com.xiaomi.infra.galaxy.fds.auth.sso;

/* loaded from: classes4.dex */
public class ServiceToken {
    private long developerId;
    private String secret;
    private long timestamp;
    private boolean tsl;
    private long uid;
    private String version;

    public static class Builder {
        private long developerId;
        private String secret;
        private long timestamp;
        private boolean tsl;
        private long uid;
        private String version;

        public ServiceToken build() {
            ServiceToken serviceToken = new ServiceToken();
            serviceToken.tsl = this.tsl;
            serviceToken.uid = this.uid;
            serviceToken.secret = this.secret;
            serviceToken.timestamp = this.timestamp;
            serviceToken.version = this.version;
            serviceToken.developerId = this.developerId;
            return serviceToken;
        }

        public Builder developerId(long j2) {
            this.developerId = j2;
            return this;
        }

        public Builder secret(String str) {
            this.secret = str;
            return this;
        }

        public Builder timestamp(long j2) {
            this.timestamp = j2;
            return this;
        }

        public Builder tsl(boolean z2) {
            this.tsl = z2;
            return this;
        }

        public Builder uid(long j2) {
            this.uid = j2;
            return this;
        }

        public Builder version(String str) {
            this.version = str;
            return this;
        }
    }

    public long getDeveloperId() {
        return this.developerId;
    }

    public String getSecret() {
        return this.secret;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public long getUid() {
        return this.uid;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean isTsl() {
        return this.tsl;
    }

    public String toString() {
        return "[ServiceToken: tsl=" + this.tsl + ", uid=" + this.uid + ", timestamp=" + this.timestamp + ", version=" + this.version + "]";
    }
}

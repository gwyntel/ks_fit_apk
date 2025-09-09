package anet.channel;

import android.text.TextUtils;
import anet.channel.entity.ENV;
import anet.channel.security.ISecurity;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import com.umeng.analytics.pro.ay;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Config {

    /* renamed from: b, reason: collision with root package name */
    private String f6595b;

    /* renamed from: c, reason: collision with root package name */
    private String f6596c;

    /* renamed from: d, reason: collision with root package name */
    private ENV f6597d = ENV.ONLINE;

    /* renamed from: e, reason: collision with root package name */
    private ISecurity f6598e;

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, Config> f6594a = new HashMap();
    public static final Config DEFAULT_CONFIG = new Builder().setTag("[default]").setAppkey("[default]").setEnv(ENV.ONLINE).build();

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        private String f6599a;

        /* renamed from: b, reason: collision with root package name */
        private String f6600b;

        /* renamed from: c, reason: collision with root package name */
        private ENV f6601c = ENV.ONLINE;

        /* renamed from: d, reason: collision with root package name */
        private String f6602d;

        /* renamed from: e, reason: collision with root package name */
        private String f6603e;

        public Config build() {
            if (TextUtils.isEmpty(this.f6600b)) {
                throw new RuntimeException("appkey can not be null or empty!");
            }
            synchronized (Config.f6594a) {
                try {
                    for (Config config : Config.f6594a.values()) {
                        if (config.f6597d == this.f6601c && config.f6596c.equals(this.f6600b)) {
                            ALog.w("awcn.Config", "duplicated config exist!", null, "appkey", this.f6600b, ay.f21366a, this.f6601c);
                            if (!TextUtils.isEmpty(this.f6599a)) {
                                Config.f6594a.put(this.f6599a, config);
                            }
                            return config;
                        }
                    }
                    Config config2 = new Config();
                    config2.f6596c = this.f6600b;
                    config2.f6597d = this.f6601c;
                    if (TextUtils.isEmpty(this.f6599a)) {
                        config2.f6595b = StringUtils.concatString(this.f6600b, "$", this.f6601c.toString());
                    } else {
                        config2.f6595b = this.f6599a;
                    }
                    if (TextUtils.isEmpty(this.f6603e)) {
                        config2.f6598e = anet.channel.security.c.a().createSecurity(this.f6602d);
                    } else {
                        config2.f6598e = anet.channel.security.c.a().createNonSecurity(this.f6603e);
                    }
                    synchronized (Config.f6594a) {
                        Config.f6594a.put(config2.f6595b, config2);
                    }
                    return config2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public Builder setAppSecret(String str) {
            this.f6603e = str;
            return this;
        }

        public Builder setAppkey(String str) {
            this.f6600b = str;
            return this;
        }

        public Builder setAuthCode(String str) {
            this.f6602d = str;
            return this;
        }

        public Builder setEnv(ENV env) {
            this.f6601c = env;
            return this;
        }

        public Builder setTag(String str) {
            this.f6599a = str;
            return this;
        }
    }

    protected Config() {
    }

    public static Config getConfig(String str, ENV env) {
        synchronized (f6594a) {
            try {
                for (Config config : f6594a.values()) {
                    if (config.f6597d == env && config.f6596c.equals(str)) {
                        return config;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static Config getConfigByTag(String str) {
        Config config;
        synchronized (f6594a) {
            config = f6594a.get(str);
        }
        return config;
    }

    public String getAppkey() {
        return this.f6596c;
    }

    public ENV getEnv() {
        return this.f6597d;
    }

    public ISecurity getSecurity() {
        return this.f6598e;
    }

    public String getTag() {
        return this.f6595b;
    }

    public String toString() {
        return this.f6595b;
    }
}

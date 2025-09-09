package com.bumptech.glide.load.model;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class LazyHeaders implements Headers {
    private volatile Map<String, String> combinedHeaders;
    private final Map<String, List<LazyHeaderFactory>> headers;

    public static final class Builder {
        private static final Map<String, List<LazyHeaderFactory>> DEFAULT_HEADERS;
        private static final String DEFAULT_USER_AGENT;
        private static final String USER_AGENT_HEADER = "User-Agent";
        private boolean copyOnModify = true;
        private Map<String, List<LazyHeaderFactory>> headers = DEFAULT_HEADERS;
        private boolean isUserAgentDefault = true;

        static {
            String strA = a();
            DEFAULT_USER_AGENT = strA;
            HashMap map = new HashMap(2);
            if (!TextUtils.isEmpty(strA)) {
                map.put("User-Agent", Collections.singletonList(new StringHeaderFactory(strA)));
            }
            DEFAULT_HEADERS = Collections.unmodifiableMap(map);
        }

        static String a() {
            String property = System.getProperty("http.agent");
            if (TextUtils.isEmpty(property)) {
                return property;
            }
            int length = property.length();
            StringBuilder sb = new StringBuilder(property.length());
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = property.charAt(i2);
                if ((cCharAt > 31 || cCharAt == '\t') && cCharAt < 127) {
                    sb.append(cCharAt);
                } else {
                    sb.append('?');
                }
            }
            return sb.toString();
        }

        private Map<String, List<LazyHeaderFactory>> copyHeaders() {
            HashMap map = new HashMap(this.headers.size());
            for (Map.Entry<String, List<LazyHeaderFactory>> entry : this.headers.entrySet()) {
                map.put(entry.getKey(), new ArrayList(entry.getValue()));
            }
            return map;
        }

        private void copyIfNecessary() {
            if (this.copyOnModify) {
                this.copyOnModify = false;
                this.headers = copyHeaders();
            }
        }

        private List<LazyHeaderFactory> getFactories(String str) {
            List<LazyHeaderFactory> list = this.headers.get(str);
            if (list != null) {
                return list;
            }
            ArrayList arrayList = new ArrayList();
            this.headers.put(str, arrayList);
            return arrayList;
        }

        public Builder addHeader(@NonNull String str, @NonNull String str2) {
            return addHeader(str, new StringHeaderFactory(str2));
        }

        public LazyHeaders build() {
            this.copyOnModify = true;
            return new LazyHeaders(this.headers);
        }

        public Builder setHeader(@NonNull String str, @Nullable String str2) {
            return setHeader(str, str2 == null ? null : new StringHeaderFactory(str2));
        }

        public Builder addHeader(@NonNull String str, @NonNull LazyHeaderFactory lazyHeaderFactory) {
            if (this.isUserAgentDefault && "User-Agent".equalsIgnoreCase(str)) {
                return setHeader(str, lazyHeaderFactory);
            }
            copyIfNecessary();
            getFactories(str).add(lazyHeaderFactory);
            return this;
        }

        public Builder setHeader(@NonNull String str, @Nullable LazyHeaderFactory lazyHeaderFactory) {
            copyIfNecessary();
            if (lazyHeaderFactory == null) {
                this.headers.remove(str);
            } else {
                List<LazyHeaderFactory> factories = getFactories(str);
                factories.clear();
                factories.add(lazyHeaderFactory);
            }
            if (this.isUserAgentDefault && "User-Agent".equalsIgnoreCase(str)) {
                this.isUserAgentDefault = false;
            }
            return this;
        }
    }

    static final class StringHeaderFactory implements LazyHeaderFactory {

        @NonNull
        private final String value;

        StringHeaderFactory(String str) {
            this.value = str;
        }

        @Override // com.bumptech.glide.load.model.LazyHeaderFactory
        public String buildHeader() {
            return this.value;
        }

        public boolean equals(Object obj) {
            if (obj instanceof StringHeaderFactory) {
                return this.value.equals(((StringHeaderFactory) obj).value);
            }
            return false;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return "StringHeaderFactory{value='" + this.value + "'}";
        }
    }

    LazyHeaders(Map map) {
        this.headers = Collections.unmodifiableMap(map);
    }

    @NonNull
    private String buildHeaderValue(@NonNull List<LazyHeaderFactory> list) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            String strBuildHeader = list.get(i2).buildHeader();
            if (!TextUtils.isEmpty(strBuildHeader)) {
                sb.append(strBuildHeader);
                if (i2 != list.size() - 1) {
                    sb.append(',');
                }
            }
        }
        return sb.toString();
    }

    private Map<String, String> generateHeaders() {
        HashMap map = new HashMap();
        for (Map.Entry<String, List<LazyHeaderFactory>> entry : this.headers.entrySet()) {
            String strBuildHeaderValue = buildHeaderValue(entry.getValue());
            if (!TextUtils.isEmpty(strBuildHeaderValue)) {
                map.put(entry.getKey(), strBuildHeaderValue);
            }
        }
        return map;
    }

    public boolean equals(Object obj) {
        if (obj instanceof LazyHeaders) {
            return this.headers.equals(((LazyHeaders) obj).headers);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.model.Headers
    public Map<String, String> getHeaders() {
        if (this.combinedHeaders == null) {
            synchronized (this) {
                try {
                    if (this.combinedHeaders == null) {
                        this.combinedHeaders = Collections.unmodifiableMap(generateHeaders());
                    }
                } finally {
                }
            }
        }
        return this.combinedHeaders;
    }

    public int hashCode() {
        return this.headers.hashCode();
    }

    public String toString() {
        return "LazyHeaders{headers=" + this.headers + '}';
    }
}

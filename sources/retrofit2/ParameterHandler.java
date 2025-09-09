package retrofit2;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/* loaded from: classes5.dex */
abstract class ParameterHandler<T> {

    static final class Body<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;

        Body(Converter<T, RequestBody> converter) {
            this.converter = converter;
        }

        @Override // retrofit2.ParameterHandler
        void apply(RequestBuilder requestBuilder, T t2) {
            if (t2 == null) {
                throw new IllegalArgumentException("Body parameter value must not be null.");
            }
            try {
                requestBuilder.setBody(this.converter.convert(t2));
            } catch (IOException e2) {
                throw new RuntimeException("Unable to convert " + t2 + " to RequestBody", e2);
            }
        }
    }

    static final class Field<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Field(String str, Converter<T, String> converter, boolean z2) {
            this.name = (String) Utils.checkNotNull(str, "name == null");
            this.valueConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        void apply(RequestBuilder requestBuilder, T t2) throws IOException {
            if (t2 == null) {
                return;
            }
            requestBuilder.addFormField(this.name, this.valueConverter.convert(t2), this.encoded);
        }
    }

    static final class FieldMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Converter<T, String> valueConverter;

        FieldMap(Converter<T, String> converter, boolean z2) {
            this.valueConverter = converter;
            this.encoded = z2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, Map<String, T> map) throws IOException {
            if (map == null) {
                throw new IllegalArgumentException("Field map was null.");
            }
            for (Map.Entry<String, T> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) {
                    throw new IllegalArgumentException("Field map contained null key.");
                }
                T value = entry.getValue();
                if (value == null) {
                    throw new IllegalArgumentException("Field map contained null value for key '" + key + "'.");
                }
                requestBuilder.addFormField(key, this.valueConverter.convert(value), this.encoded);
            }
        }
    }

    static final class Header<T> extends ParameterHandler<T> {
        private final String name;
        private final Converter<T, String> valueConverter;

        Header(String str, Converter<T, String> converter) {
            this.name = (String) Utils.checkNotNull(str, "name == null");
            this.valueConverter = converter;
        }

        @Override // retrofit2.ParameterHandler
        void apply(RequestBuilder requestBuilder, T t2) throws IOException {
            if (t2 == null) {
                return;
            }
            requestBuilder.addHeader(this.name, this.valueConverter.convert(t2));
        }
    }

    static final class Part<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final Headers headers;

        Part(Headers headers, Converter<T, RequestBody> converter) {
            this.headers = headers;
            this.converter = converter;
        }

        @Override // retrofit2.ParameterHandler
        void apply(RequestBuilder requestBuilder, T t2) {
            if (t2 == null) {
                return;
            }
            try {
                requestBuilder.addPart(this.headers, this.converter.convert(t2));
            } catch (IOException e2) {
                throw new RuntimeException("Unable to convert " + t2 + " to RequestBody", e2);
            }
        }
    }

    static final class PartMap<T> extends ParameterHandler<Map<String, T>> {
        private final String transferEncoding;
        private final Converter<T, RequestBody> valueConverter;

        PartMap(Converter<T, RequestBody> converter, String str) {
            this.valueConverter = converter;
            this.transferEncoding = str;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, Map<String, T> map) throws IOException {
            if (map == null) {
                throw new IllegalArgumentException("Part map was null.");
            }
            for (Map.Entry<String, T> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) {
                    throw new IllegalArgumentException("Part map contained null key.");
                }
                T value = entry.getValue();
                if (value == null) {
                    throw new IllegalArgumentException("Part map contained null value for key '" + key + "'.");
                }
                requestBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\"", "Content-Transfer-Encoding", this.transferEncoding), this.valueConverter.convert(value));
            }
        }
    }

    static final class Path<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Path(String str, Converter<T, String> converter, boolean z2) {
            this.name = (String) Utils.checkNotNull(str, "name == null");
            this.valueConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        void apply(RequestBuilder requestBuilder, T t2) throws IOException {
            if (t2 != null) {
                requestBuilder.addPathParam(this.name, this.valueConverter.convert(t2), this.encoded);
                return;
            }
            throw new IllegalArgumentException("Path parameter \"" + this.name + "\" value must not be null.");
        }
    }

    static final class Query<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Query(String str, Converter<T, String> converter, boolean z2) {
            this.name = (String) Utils.checkNotNull(str, "name == null");
            this.valueConverter = converter;
            this.encoded = z2;
        }

        @Override // retrofit2.ParameterHandler
        void apply(RequestBuilder requestBuilder, T t2) throws IOException {
            if (t2 == null) {
                return;
            }
            requestBuilder.addQueryParam(this.name, this.valueConverter.convert(t2), this.encoded);
        }
    }

    static final class QueryMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Converter<T, String> valueConverter;

        QueryMap(Converter<T, String> converter, boolean z2) {
            this.valueConverter = converter;
            this.encoded = z2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, Map<String, T> map) throws IOException {
            if (map == null) {
                throw new IllegalArgumentException("Query map was null.");
            }
            for (Map.Entry<String, T> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) {
                    throw new IllegalArgumentException("Query map contained null key.");
                }
                T value = entry.getValue();
                if (value == null) {
                    throw new IllegalArgumentException("Query map contained null value for key '" + key + "'.");
                }
                requestBuilder.addQueryParam(key, this.valueConverter.convert(value), this.encoded);
            }
        }
    }

    static final class RawPart extends ParameterHandler<MultipartBody.Part> {
        static final RawPart INSTANCE = new RawPart();

        private RawPart() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // retrofit2.ParameterHandler
        public void apply(RequestBuilder requestBuilder, MultipartBody.Part part) throws IOException {
            if (part != null) {
                requestBuilder.addPart(part);
            }
        }
    }

    static final class RelativeUrl extends ParameterHandler<Object> {
        RelativeUrl() {
        }

        @Override // retrofit2.ParameterHandler
        void apply(RequestBuilder requestBuilder, Object obj) {
            requestBuilder.setRelativeUrl(obj);
        }
    }

    ParameterHandler() {
    }

    abstract void apply(RequestBuilder requestBuilder, T t2) throws IOException;

    final ParameterHandler<Object> array() {
        return new ParameterHandler<Object>() { // from class: retrofit2.ParameterHandler.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // retrofit2.ParameterHandler
            void apply(RequestBuilder requestBuilder, Object obj) throws IOException {
                if (obj == null) {
                    return;
                }
                int length = Array.getLength(obj);
                for (int i2 = 0; i2 < length; i2++) {
                    ParameterHandler.this.apply(requestBuilder, Array.get(obj, i2));
                }
            }
        };
    }

    final ParameterHandler<Iterable<T>> iterable() {
        return new ParameterHandler<Iterable<T>>() { // from class: retrofit2.ParameterHandler.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // retrofit2.ParameterHandler
            public void apply(RequestBuilder requestBuilder, Iterable<T> iterable) throws IOException {
                if (iterable == null) {
                    return;
                }
                Iterator<T> it = iterable.iterator();
                while (it.hasNext()) {
                    ParameterHandler.this.apply(requestBuilder, it.next());
                }
            }
        };
    }
}

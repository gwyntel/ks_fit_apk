package retrofit;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.RequestBody;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Map;

/* loaded from: classes5.dex */
abstract class RequestBuilderAction {

    static final class Body<T> extends RequestBuilderAction {
        private final Converter<T, RequestBody> converter;

        Body(Converter converter) {
            this.converter = converter;
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) {
            if (obj == null) {
                throw new IllegalArgumentException("Body parameter value must not be null.");
            }
            try {
                requestBuilder.i(this.converter.convert(obj));
            } catch (IOException unused) {
                throw new RuntimeException("Unable to convert " + obj + " to RequestBody");
            }
        }
    }

    static final class Field extends RequestBuilderAction {
        private final boolean encoded;
        private final String name;

        Field(String str, boolean z2) {
            this.name = (String) Utils.a(str, "name == null");
            this.encoded = z2;
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
            if (obj == null) {
                return;
            }
            if (obj instanceof Iterable) {
                for (Object obj2 : (Iterable) obj) {
                    if (obj2 != null) {
                        requestBuilder.a(this.name, obj2.toString(), this.encoded);
                    }
                }
                return;
            }
            if (!obj.getClass().isArray()) {
                requestBuilder.a(this.name, obj.toString(), this.encoded);
                return;
            }
            int length = Array.getLength(obj);
            for (int i2 = 0; i2 < length; i2++) {
                Object obj3 = Array.get(obj, i2);
                if (obj3 != null) {
                    requestBuilder.a(this.name, obj3.toString(), this.encoded);
                }
            }
        }
    }

    static final class FieldMap extends RequestBuilderAction {
        private final boolean encoded;

        FieldMap(boolean z2) {
            this.encoded = z2;
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) {
            if (obj == null) {
                return;
            }
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                if (key == null) {
                    throw new IllegalArgumentException("Field map contained null key.");
                }
                Object value = entry.getValue();
                if (value != null) {
                    requestBuilder.a(key.toString(), value.toString(), this.encoded);
                }
            }
        }
    }

    static final class Header extends RequestBuilderAction {
        private final String name;

        Header(String str) {
            this.name = (String) Utils.a(str, "name == null");
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
            if (obj == null) {
                return;
            }
            if (obj instanceof Iterable) {
                for (Object obj2 : (Iterable) obj) {
                    if (obj2 != null) {
                        requestBuilder.b(this.name, obj2.toString());
                    }
                }
                return;
            }
            if (!obj.getClass().isArray()) {
                requestBuilder.b(this.name, obj.toString());
                return;
            }
            int length = Array.getLength(obj);
            for (int i2 = 0; i2 < length; i2++) {
                Object obj3 = Array.get(obj, i2);
                if (obj3 != null) {
                    requestBuilder.b(this.name, obj3.toString());
                }
            }
        }
    }

    static final class Part<T> extends RequestBuilderAction {
        private final Converter<T, RequestBody> converter;
        private final Headers headers;

        Part(Headers headers, Converter converter) {
            this.headers = headers;
            this.converter = converter;
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) {
            if (obj == null) {
                return;
            }
            try {
                requestBuilder.c(this.headers, this.converter.convert(obj));
            } catch (IOException unused) {
                throw new RuntimeException("Unable to convert " + obj + " to RequestBody");
            }
        }
    }

    static final class PartMap extends RequestBuilderAction {
        private final Annotation[] annotations;

        /* renamed from: retrofit, reason: collision with root package name */
        private final Retrofit f26828retrofit;
        private final String transferEncoding;

        PartMap(Retrofit retrofit3, String str, Annotation[] annotationArr) {
            this.f26828retrofit = retrofit3;
            this.transferEncoding = str;
            this.annotations = annotationArr;
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) {
            if (obj == null) {
                return;
            }
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                if (key == null) {
                    throw new IllegalArgumentException("Part map contained null key.");
                }
                Object value = entry.getValue();
                if (value != null) {
                    try {
                        requestBuilder.c(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\"", "Content-Transfer-Encoding", this.transferEncoding), (RequestBody) this.f26828retrofit.requestConverter(value.getClass(), this.annotations).convert(value));
                    } catch (IOException unused) {
                        throw new RuntimeException("Unable to convert " + value + " to RequestBody");
                    }
                }
            }
        }
    }

    static final class Path extends RequestBuilderAction {
        private final boolean encoded;
        private final String name;

        Path(String str, boolean z2) {
            this.name = (String) Utils.a(str, "name == null");
            this.encoded = z2;
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) {
            if (obj != null) {
                requestBuilder.d(this.name, obj.toString(), this.encoded);
                return;
            }
            throw new IllegalArgumentException("Path parameter \"" + this.name + "\" value must not be null.");
        }
    }

    static final class Query extends RequestBuilderAction {
        private final boolean encoded;
        private final String name;

        Query(String str, boolean z2) {
            this.name = (String) Utils.a(str, "name == null");
            this.encoded = z2;
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
            if (obj == null) {
                return;
            }
            if (obj instanceof Iterable) {
                for (Object obj2 : (Iterable) obj) {
                    if (obj2 != null) {
                        requestBuilder.e(this.name, obj2.toString(), this.encoded);
                    }
                }
                return;
            }
            if (!obj.getClass().isArray()) {
                requestBuilder.e(this.name, obj.toString(), this.encoded);
                return;
            }
            int length = Array.getLength(obj);
            for (int i2 = 0; i2 < length; i2++) {
                Object obj3 = Array.get(obj, i2);
                if (obj3 != null) {
                    requestBuilder.e(this.name, obj3.toString(), this.encoded);
                }
            }
        }
    }

    static final class QueryMap extends RequestBuilderAction {
        private final boolean encoded;

        QueryMap(boolean z2) {
            this.encoded = z2;
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) {
            if (obj == null) {
                return;
            }
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                if (key == null) {
                    throw new IllegalArgumentException("Query map contained null key.");
                }
                Object value = entry.getValue();
                if (value != null) {
                    requestBuilder.e(key.toString(), value.toString(), this.encoded);
                }
            }
        }
    }

    static final class Url extends RequestBuilderAction {
        Url() {
        }

        @Override // retrofit.RequestBuilderAction
        void a(RequestBuilder requestBuilder, Object obj) {
            requestBuilder.j((String) obj);
        }
    }

    RequestBuilderAction() {
    }

    abstract void a(RequestBuilder requestBuilder, Object obj);
}
